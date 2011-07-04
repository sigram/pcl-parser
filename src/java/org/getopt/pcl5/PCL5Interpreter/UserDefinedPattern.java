package org.getopt.pcl5.PCL5Interpreter;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UserDefinedPattern {
  private int _format;
  private int _continuation;
  private int _pixelEncoding;
  private int _reserved;
  private int _height;
  private int _width;
  private int _masterXResolution;
  private int _masterYResoulution;

  private byte[] _data;

  private BufferedImage _image;

  public UserDefinedPattern(int numOfBytes, InputStream in) throws IOException {
    _format = in.read();
    _continuation = in.read();
    _pixelEncoding = in.read();
    _reserved = in.read();
    _height = 256 * in.read() + in.read();
    _width = 256 * in.read() + in.read();
    _masterXResolution = 256 * in.read() + in.read();
    _masterYResoulution = 256 * in.read() + in.read();

    _data = new byte[numOfBytes - 12];
    in.read(_data);

    _image = null;
  }

  public BufferedImage getImage() {
    if (_image == null)
      createImage();

    return _image;
  }

  protected void createImage() {
    _image = new BufferedImage(_width, _height, BufferedImage.TYPE_BYTE_GRAY);

    Graphics2D graphics;
    graphics = _image.createGraphics();
    Rectangle2D.Double rect = new Rectangle2D.Double(0, 0, _width, _height);
    graphics.setColor(Color.WHITE);
    graphics.fill(rect);

    fillPattern();
  }

  protected void fillPattern() {
    int bytesInRow = (_width / 8);

    for (int y = 0; y < _height; y++) {
      for (int i = 0; i < bytesInRow; i++) {
        int offset = y * bytesInRow + i;

        if (_data[offset] != 0x00)
          draw8bits(_data[offset], i * 8, y);
      }
    }
  }

  protected void draw8bits(byte bits, int x, int y) {
    px(bits & 0x01, x + 7, y);
    px(bits & 0x02, x + 6, y);
    px(bits & 0x04, x + 5, y);
    px(bits & 0x08, x + 4, y);
    px(bits & 0x10, x + 3, y);
    px(bits & 0x20, x + 2, y);
    px(bits & 0x40, x + 1, y);
    px(bits & 0x80, x + 0, y);
  }

  private void px(int bit, int x, int y) {
    if (bit != 0 && x < _width && y < _height)
      _image.setRGB(x, y, 0xFF000000);
  }

  public int getHeight() {
    return _height;
  }

  public int getWidth() {
    return _width;
  }
}
