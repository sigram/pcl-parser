/*
 * Created on 2004-09-12
 *
 */
package org.getopt.pcl5;

import java.awt.*;
import java.util.Map;

/**
 * Printer state interface, allows access to internal printer state
 * 
 *         TODO Exception specification
 */
public interface IPrinterState {
  /**
   * Returns current font used by printer. <b>Do not cache this value.<b>
   * 
   * @return current Font class
   */
  Font getFont();

  /**
   * Returns current font attributes
   * 
   * @return current font attribute
   */
  Map getFontAttributes();

  /**
   * Return current color used by printer <b>Do not cache this value.<b>
   * 
   * @return Current color
   */
  Color getCurrentColor();

  /**
   * Return true is printer has selected condensed font
   * 
   * @return condensed printer state
   */
  boolean isCondensed();

  int getUnderliningMode();
}
