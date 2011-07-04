/*
 * Created on 2004-10-09
 *
 */
package org.getopt.pcl5.PCL5Interpreter;

public class PrinterTIFF {
  // TIFF code masks
  static final int XFER = 0x20;
  static final int MOVX = 0x40;
  static final int MOVY = 0x60;
  static final int COLR = 0x80;
  static final int CR = 0xE2;
  static final int EXIT = 0xE3;
  static final int MOVXBYTE = 0x24; // conflict with XFER
  static final int MOVXDOT = 0x25; // conflict with XFER

}
