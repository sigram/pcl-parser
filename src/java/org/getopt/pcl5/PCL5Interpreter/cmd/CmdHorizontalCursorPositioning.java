/**
 * <b>Command ESC * p # X</b>
 * This Vertical Cursor Positioning command moves the cursor to a new
 * position along the vertical axis.
 *  
 * <b>Default</b> = N/A
 * <b>Range</b>   = 0 - logical page right bound
 *
 * # =	Number of PCL Units
 * 
 * <b>Notes</b>
 * A value field (#) with a plus sign (+) indicates the new position is to
 * the right of and relative to the current cursor position; a minus sign (�)
 * indicates the new position is to the left of and relative to the current
 * cursor position. No sign indicates an absolute distance which is
 * referenced from the left edge of the logical page. The left most
 * position is 0 and the right most position is the right bound of the
 * logical page.
 * If a request is made for a location outside the printer�s logical page,
 * the current active position (CAP) is moved to the appropriate logical
 * page limit.
 * 
 * <i>implemented Sep 21, 2005</i>
 * 
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public class CmdHorizontalCursorPositioning extends EscExtendedCommandPCL5 {
  public CmdHorizontalCursorPositioning(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if (family == '*' && subfamily == 'p' && cmd == 'X') {
      int param = Integer.parseInt((parameter));

      // if first char is + or - it's relative posiotioning
      if (Character.isDigit(parameter.charAt(0)))
        _printerState.setAbsoluteHorizontalCursorPosition(param);
      else
        _printerState.setRelativeHorizontalCursorPosition(param);

      return true;
    }

    return false;
  }
}
