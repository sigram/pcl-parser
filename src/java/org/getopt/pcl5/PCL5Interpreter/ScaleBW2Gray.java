/*
 * Created on 2005-10-09 by Piotrm
 *
 * Scale B&W image (2 color) into gray image
 */

package org.getopt.pcl5.PCL5Interpreter;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class ScaleBW2Gray {
  BufferedImage _destImage;
  int _boundBoxColor;

  // Graphics2D _graphics;

  public ScaleBW2Gray(BufferedImage srcImage, float width, float height,
          boolean boundBox) {
    _boundBoxColor = 0x00000000;

    if (boundBox)
      _boundBoxColor = 0x20080808;

    float probeWidth = srcImage.getWidth() / width;
    float probeHeight = srcImage.getHeight() / height;

    if (probeWidth == 0 || probeHeight == 0)
      throw new IllegalArgumentException(
              "Dest image must be smaller than original");

    createDestImage((int) width, (int) height);

    // _graphics = _img.createGraphics();

    float probeScale = 256 / (probeWidth * probeHeight);

    float probesX = srcImage.getWidth() / probeWidth + 1;
    float probesY = srcImage.getHeight() / probeHeight + 1;

    for (int x = 0; x < (int) width; x++)
      for (int y = 0; y < (int) height; y++)
        processProbe(srcImage, x, y, probeWidth, probeHeight, probeScale);
  }

  private void createDestImage(int width, int height) {
    _destImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    Graphics2D graphics = _destImage.createGraphics();
    graphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
            RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

    // Clear image with transparent alpha by drawing a rectangle
    graphics.setComposite(AlphaComposite
            .getInstance(AlphaComposite.CLEAR, 0.5f));
    // graphics.setBackground(boundBox);
    Rectangle2D.Double rect = new Rectangle2D.Double(0, 0, width, height);
    graphics.fill(rect);
    graphics.setComposite(AlphaComposite.SrcOver);
    graphics.setColor(Color.BLACK);
  }

  private void processProbe(BufferedImage srcImage, int x, int y,
          float probeWidth, float probeHeight, float probeScale) {
    int probeValue = 0;

    for (int dx = 0; dx < Math.round(probeWidth); dx++)
      for (int dy = 0; dy < Math.round(probeHeight); dy++) {
        int srcX = Math.round(x * probeWidth + dx);
        int srcY = Math.round(y * probeHeight + dy);

        if (srcX < srcImage.getWidth() && srcY < srcImage.getHeight()) {
          if (srcImage.getRGB(srcX, srcY) == 0xFF000000) // black
            probeValue++;
        }
      }

    int value = 0xFF & Math.round(255.0f - probeValue * probeScale);
    int color = 0xFF000000;

    if (probeValue == 0) // if empty
      color = _boundBoxColor; // set transparent color
    else {
      // color |= value;
      // color |= value << 8;
      color |= value << 16;
    }

    _destImage.setRGB(x, y, color);
  }

  public BufferedImage getImage() {
    return _destImage;
  }
}
