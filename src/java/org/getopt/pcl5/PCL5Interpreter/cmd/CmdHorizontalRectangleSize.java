/**
 * <b>Command ESC * c # A</b>
 * This Horizontal Rectangle Size command specifies the rectangle width in PCL Units.
 *  
 * <b>Default</b> = 0
 * <b>Range</b>   = 0 - 32767
 *
 * # =	Number of PCL Units
 * 
 * <b>Notes</b>
 * For example, if the unit of measure is set to 300 units-per-inch, to
 * specify a two-inch wide rectangle, send the command: Esc*c600A
 * The same command specifies a one-inch wide rectangle if the unit of
 * measure is set to 600 units-per-inch.
 * 
 * <i>implemented Sep 21, 2005</i>
 * 
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public class CmdHorizontalRectangleSize extends EscExtendedCommandPCL5 {
  public CmdHorizontalRectangleSize(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if (family == '*' && subfamily == 'c' && cmd == 'A') {
      int param = Integer.parseInt((parameter));
      _printerState.setHorizontalRectangleSize(param);

      return true;
    }

    return false;
  }
}
