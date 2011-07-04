/**
 * <b>Command ESC * p # Y</b>
 * This Vertical Cursor Positioning command moves the cursor to a new
 * position along the vertical axis.
 *  
 * <b>Default</b> = N/A
 * <b>Range</b>   = �32767 to 32767
 *
 * # =	Number of PCL Units
 * 
 * <b>Notes</b>
 * A value field (#) with a plus sign (+) indicates the new position is
 * downward from and relative to the current cursor position; a minus
 * sign (�) indicates the new position is upward from and relative to the
 * current cursor position. No sign indicates an absolute distance from
 * the top margin. The top position, defined by the top margin, is 0 and
 * the bottom position is determined by the bottom of the logical page.
 * If a request is made for a location outside the printer�s logical page,
 * the current active position (CAP) is moved to the appropriate logical
 * page limit.
 * The current unit size used in PCL Unit moves is determined by the
 * value specified in the Unit of Measure command. If no other value is
 * specified, the number of units-per-inch for PCL unit moves is one unit
 * equals 1/300 inch.
 * 
 * <i>implemented Sep 21, 2005</i>
 * 
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public class CmdVerticalCursorPositioning extends EscExtendedCommandPCL5 {
  public CmdVerticalCursorPositioning(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if (family == '*' && subfamily == 'p' && cmd == 'Y') {
      int param = Integer.parseInt((parameter));

      // if first char is + or - it's relative posiotioning
      if (Character.isDigit(parameter.charAt(0)))
        _printerState.setAbsoluteVerticalCursorPosition(param);
      else
        _printerState.setRelativeVerticalCursorPosition(param);

      return true;
    }

    return false;
  }
}
