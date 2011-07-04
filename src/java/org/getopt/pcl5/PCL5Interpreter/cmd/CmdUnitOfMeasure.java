/**
 * <b>Command ESC & u # D</b>
 * The Unit of Measure command establishes the unit of measure for
 * PCL Unit cursor movements.
 * 
 * <b>Default</b> = 75
 * <b>Range</b>   = 96, 100, 120, 144, 150, 160, 180, 200, 225, 240, 288, 300, 
 * 					360, 400, 450, 480, 600, 720, 800, 900, 1200, 1440, 1800, 
 * 					2400, 3600, 7200.
 *
 * # =Number of units-per-inch
 * 
 * <b>Notes</b>
 * The value field defines the number of units-per-inch used in the following commands:
 * -? Vertical Cursor Position (PCL Units).
 * - Horizontal Cursor Position (PCL Units).
 * - Vertical Rectangle Size (PCL Units).
 * -? Horizontal Rectangle Size (PCL Units).
 * In addition, the current unit of measure setting affects the HMI setting,
 * which in turn determines how cursor movement values are rounded.
 * This affects the result of the following commands:
 * -? Horizontal Cursor Position (Columns).
 * -? Horizontal Tab (HT control code).
 * -? Space (SP control code).
 * -? Backspace (BS control code).
 * -? Bitmap Character Delta X.
 * For example, if the unit of measure is set to 96 (one PCL Unit = 1/96 inch), 
 * then the HMI is rounded to the nearest 1/96 inch. If the unit of measure 
 * is set to 300 (one PCL Unit = 1/300 inch), the HMI is rounded to the nearest 1/300 inch.
 *  
 * <i>implemented Sep 22, 2005</i>
 * 
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public class CmdUnitOfMeasure extends EscExtendedCommandPCL5 {

  public CmdUnitOfMeasure(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if (family == '&' && subfamily == 'u' && cmd == 'D') {
      int param = Integer.parseInt((parameter));
      _printerState.setUnitOfMeasure(param);
      return true;
    }

    return false;
  }
}
