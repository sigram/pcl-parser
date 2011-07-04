/*
 * Created on 2005-10-03 by Piotrm
 *
 */
package org.getopt.pcl5.PCL5Interpreter;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class PatternGraphics {
  final static int SOLID = 0;

  private int _width;
  private int _height;
  private BufferedImage _image;

  public PatternGraphics(int width, int height) {
    _image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
    _width = width;
    _height = height;

    Graphics2D graphics;
    graphics = _image.createGraphics();

    Rectangle2D.Double rect = new Rectangle2D.Double(0, 0, _width, _height);
    graphics.setColor(Color.WHITE);
    graphics.fill(rect);
  }

  public void setPatternColor(Color color) {
    Graphics2D graphics;
    graphics = _image.createGraphics();

    Rectangle2D.Double rect = new Rectangle2D.Double(0, 0, _width, _height);
    graphics.setColor(color);
    graphics.fill(rect);
  }

  public void setPattern(UserDefinedPattern pattern) {
    Graphics2D graphics;
    graphics = _image.createGraphics();

    for (int x = 0; x < _width; x += pattern.getWidth())
      for (int y = 0; y < _height; y += pattern.getHeight())
        graphics.drawImage(pattern.getImage(), x, y, null);
  }

  public BufferedImage getImage() {
    return _image;
  }
}
