/*
 * Created on 2005-10-04 by Piotrm
 *
 */
package org.getopt.pcl5.PCL5Interpreter;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import org.getopt.pcl5.PrinterState;

public class RasterGraphics {
  private final static int BUFFER_INCREMENT = 128;
  private int _height;
  private int _width;
  private int _position;
  private BufferedImage _image;
  // private Graphics2D _graphics;
  private Point _start;
  private Point _end;

  public RasterGraphics(int width, int height) {
    _width = width;
    _height = height;

    createImage();
  }

  protected void createImage() {
    _image = new BufferedImage(_width, _height, BufferedImage.TYPE_BYTE_GRAY);

    Graphics2D graphics = _image.createGraphics();
    // _graphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
    // RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

    // Clear image with transparent alpha by drawing a rectangle
    // _graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR,
    // 0.0f));
    Rectangle2D.Double rect = new Rectangle2D.Double(0, 0, _width, _height);
    graphics.setColor(Color.WHITE);
    graphics.fill(rect);
    // _graphics.setComposite(AlphaComposite.SrcOver);
    graphics.setColor(Color.BLACK);

    _start = new Point(_width, _height);
    _end = new Point(0, 0);
  }

  public int decodeImage(byte[] data, int compressionMethod, int x, int y,
          int w, int h) {
    byte[] decodedData;

    if (compressionMethod == PrinterState.CompressionMode.TIFF)
      decodedData = decompressTIFF(data);
    else
      decodedData = data;

    if (h == 0)
      h = 1;

    if (w == 0)
      w = decodedData.length * 8;

    // calculate 'real' side of image, for cropping
    if (x < _start.x)
      _start.x = x;

    if (y < _start.y)
      _start.y = y;

    if (x + w > _end.x)
      _end.x = x + w;

    if (y + h > _end.y)
      _end.y = y + h;

    fillPattern(decodedData, x, y, w, h);

    return h;
  }

  // -3 Literal Pattern Values
  // # of Bytes Binary value Decimal value
  // 1 0000 0000 1
  // to to to
  // 127 0111 1111 127
  //
  // No Operation Value
  // NOP value Binary value Decimal value
  // 128 (-128) 1000 000 128
  //
  // Repeated Pattern Values
  // # of Repetitions Binary value Decimal value
  // 1 (-1) 1111 1111 255
  // to to to
  // 127 (-127) 1000 0001 129
  private byte[] decompressTIFF(byte[] data) {
    int offset = 0;
    byte[] decodedData = new byte[BUFFER_INCREMENT];
    _position = 0;

    while (offset < data.length) {
      offset = decompress(decodedData, offset, data);
      if (_position + 128 > decodedData.length) {
        byte[] newData = new byte[decodedData.length + BUFFER_INCREMENT];
        System.arraycopy(decodedData, 0, newData, 0, _position);
        decodedData = newData;
      }
    }

    byte[] newData = new byte[_position];
    System.arraycopy(decodedData, 0, newData, 0, _position);
    decodedData = newData;

    return decodedData;
  }

  private int decompress(byte[] dest, int offset, byte[] src) {
    int n = src[offset++];

    if (n >= 0 && n < 128) {
      n++;
      copyData(dest, src, offset, n);
      offset += n;
    } else if (n < 0 && n != 128) {
      n = Math.abs(n);
      n++;
      repeatByte(dest, src[offset], n);
      offset++;
    }

    return offset;
  }

  private void repeatByte(byte[] dest, byte b, int n) {
    while (n > 0) {
      dest[_position] = b;
      n--;
      _position++;
    }
  }

  private void copyData(byte[] dest, byte[] src, int offset, int n) {
    while (n > 0) {
      dest[_position] = src[offset++];
      n--;
      _position++;
    }
  }

  protected void fillPattern(byte[] src, int dx, int dy, int w, int h) {
    int bytesInRow = (w / 8) + 1;

    for (int y = 0; y < _height; y++) {
      for (int i = 0; i < bytesInRow; i++) {
        int offset = y * bytesInRow + i;
        if (offset < src.length) {
          byte b = src[offset];

          if (b != 0x00)
            draw8bits(b, i * 8 + dx, y + dy);
        }
      }
    }
  }

  protected void draw8bits(byte bits, int x, int y) {
    // if (bits == -1)
    // _graphics.fillRect(x, y, 8, 1);
    // else
    {
      px(bits & 0x01, x + 7, y);
      px(bits & 0x02, x + 6, y);
      px(bits & 0x04, x + 5, y);
      px(bits & 0x08, x + 4, y);
      px(bits & 0x10, x + 3, y);
      px(bits & 0x20, x + 2, y);
      px(bits & 0x40, x + 1, y);
      px(bits & 0x80, x + 0, y);
    }
  }

  private void px(int bit, int x, int y) {
    if (bit != 0)
      _image.setRGB(x, y, 0xFF000000);
    // _graphics.fillRect(x, y, 1, 1 );
  }

  /**
   * @return Returns the image.
   */
  public BufferedImage getImage() {
    int w = _end.x - _start.x;
    int h = _end.y - _start.y;

    BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);

    Graphics2D graphics = image.createGraphics();

    graphics.drawImage(_image, -_start.x, -_start.y, null);

    return image;
  }

  public Point getStart() {
    return _start;
  }
}
