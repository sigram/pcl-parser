/*
 * Created on 2004-09-17
 *
 */
package org.getopt.pcl5.PCL5Interpreter;

public class Resolution {
  static final int RESOLUTION = 3600;
  static final int MAX_PAGE_HEIGHT_INCH = 22;
  static final int MAX_PAGE_HEIGHT_PIXEL = MAX_PAGE_HEIGHT_INCH * RESOLUTION;
  static final int A4_WIDTH = 2100;
  static final int A4_HEIGHT = 2970;
  static final int DECIMAL_MM_PER_INCH = 254;
  static final int DEFAULT_WIDTH = (RESOLUTION * A4_WIDTH)
          / DECIMAL_MM_PER_INCH;
  static final int DEFAULT_HEIGHT = (RESOLUTION * A4_HEIGHT)
          / DECIMAL_MM_PER_INCH;
}
