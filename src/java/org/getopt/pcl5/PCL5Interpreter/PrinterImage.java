/*
 * Created on 2004-09-23
 *
 */
package org.getopt.pcl5.PCL5Interpreter;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

/**
 * Support for printer-specific graphics
 */
public class PrinterImage extends BufferedImage {
  WritableRaster raster;
  Graphics2D graphics;
  int x;
  final int width;
  final int height;
  static final int[] SET_PIXEL = { 127, 127, 127, 0 };
  static final int[] EMPTY_PIXEL = { 200, 200, 200, 255 };
  Color _printColor = Color.BLACK; // new Color(0, 0, 0, 255);
  public static final Color EMPTY_COLOR = new Color(255, 255, 255, 0);

  /**
   * Creates BufferedImage
   * 
   * @param w
   *          width
   * @param h
   *          height
   */
  public PrinterImage(int w, int h) {
    super(w, h, BufferedImage.TYPE_INT_ARGB);
    // Raster r = getData();
    // raster = r.createCompatibleWritableRaster();

    height = h;
    width = w;

    graphics = createGraphics();
    graphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
            RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

    // Clear image with transparent alpha by drawing a rectangle
    graphics.setComposite(AlphaComposite
            .getInstance(AlphaComposite.CLEAR, 0.0f));
    Rectangle2D.Double rect = new Rectangle2D.Double(0, 0, w, h);
    graphics.fill(rect);
    graphics.setComposite(AlphaComposite.SrcOver);
    graphics.setColor(_printColor);
  }

  /**
   * Set pixel at location
   * 
   * @param bit
   *          bit to test
   * @param offY
   *          coordination offset
   * @param offset
   *          bit offset
   */
  private void px(int bit, int offY, int offset) {
    if (bit != 0)
      graphics.fillRect(x, offset + offY, 1, 1);
  }

  /**
   * Set pixel at location
   * 
   * @param bit
   *          bit to test
   * @param offY
   *          coordination offset
   * @param offset
   *          bit offset
   */
  private void pxH(int bit, int offY, int offset) {
    if (bit != 0)
      graphics.fillRect(x + offset, offY, 1, 1);
  }

  /**
   * Interpret 8 bit data as print data
   * 
   * @param data
   *          data to interpret
   * @param offset
   *          coordintion offset
   */
  private void print8bit(byte data, int offset) {
    px(data & 0x01, 7, offset);
    px(data & 0x02, 6, offset);
    px(data & 0x04, 5, offset);
    px(data & 0x08, 4, offset);
    px(data & 0x10, 3, offset);
    px(data & 0x20, 2, offset);
    px(data & 0x40, 1, offset);
    px(data & 0x80, 0, offset);
  }

  private void print9bit(byte data) {
    if ((data & 0x01) == 0x01)
      graphics.fillRect(x, 8, 1, 1);
  }

  private void print(byte data, int repeat, int offset) {
    for (int i = 0; i < repeat; i++) {
      print8bit(data, offset);
      x++;
    }
  }

  /**
   * Print array of 8bit graphics data
   * 
   * @param data
   *          data to print
   */
  public void print8bit(byte[] data) {
    for (int i = 0; i < data.length; i++) {
      print8bit(data[i], 0);
      x++;
    }
  }

  /**
   * Print array of 9bit graphics data
   * 
   * @param data
   *          data to print
   */
  public void print9bit(byte[] data) {
    for (int i = 0; i < data.length; i += 2) {
      print8bit(data[i], 0);
      print9bit(data[i + 1]);
      x++;
    }
  }

  /**
   * Print array of 24bit graphics data (3 rows)
   * 
   * @param data
   *          data to print
   */
  public void print24bit(byte[] data) {
    for (int i = 0; i < data.length; i += 3) {
      print8bit(data[i], 0);
      print8bit(data[i + 1], 8);
      print8bit(data[i + 2], 16);
      x++;
    }
  }

  /**
   * Print array of 48bit graphics data (6 rows)
   * 
   * @param data
   *          data to print
   */
  public void print48bit(byte[] data) {
    for (int i = 0; i < data.length; i += 6) {
      print8bit(data[i], 0);
      print8bit(data[i + 1], 8);
      print8bit(data[i + 2], 16);
      print8bit(data[i + 3], 24);
      print8bit(data[i + 4], 32);
      print8bit(data[i + 5], 40);
      x++;
    }
  }

  int y;
  int rows;

  /**
   * Interpret 8 bit data as print data
   * 
   * @param data
   *          data to interpret
   * @param offset
   *          coordintion offset
   */
  private void print8bitH(byte data, int offset) {
    pxH(data & 0x01, offset, 7);
    pxH(data & 0x02, offset, 6);
    pxH(data & 0x04, offset, 5);
    pxH(data & 0x08, offset, 4);
    pxH(data & 0x10, offset, 3);
    pxH(data & 0x20, offset, 2);
    pxH(data & 0x40, offset, 1);
    pxH(data & 0x80, offset, 0);
  }

  boolean printRLE(byte data) {
    print8bitH(data, y);

    x += 8;

    if (x >= width) {
      x = 0;
      y++;
    }

    return y < rows;
  }

  public void startRLE(int rows) {
    y = 0;
    x = 0;
    this.rows = rows;
  }

  /**
   * Print array of RLE encoded graphics data
   * 
   * @param data
   *          data to print
   * @param rows
   *          number of 8 bit rows to print
   */
  public boolean printRLE(byte[] data) {
    int i;
    int n;

    if (data[0] > 0) {
      n = data[0] + 1;
      for (i = 0; i < n; i++)
        printRLE(data[i + 1]);
    } else {
      n = 1 - data[0]; // 257-data[pos] but data[pos] is < 0
      for (i = 0; i < n; i++)
        printRLE(data[1]);
    }

    return y < rows;
  }

  /**
   * Print array of TIFF encoded graphics data
   * 
   * @param data
   *          data to print
   */
  public void printTIFF(byte[] data) {

  }

  /**
   * @return Returns the height.
   */
  public int getHeight() {
    return height;
  }

  /**
   * @return Returns the width.
   */
  public int getWidth() {
    return width;
  }

  /**
   * @return Returns the printColor.
   */
  public Color getPrintColor() {
    return _printColor;
  }

  /**
   * @param printColor
   *          The printColor to set.
   */
  public void setPrintColor(Color printColor) {
    _printColor = printColor;
  }
}
