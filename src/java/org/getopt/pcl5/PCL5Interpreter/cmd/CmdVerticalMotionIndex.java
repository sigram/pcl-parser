/**
 * <b>Command ESC & l # C</b>
 * The Vertical Motion Index (VMI) command designates the height of
 * the rows. (The vertical distance the cursor moves for a Line Feed operation.)
 *
 * <b>Default</b> = 8
 * <b>Range</b> = 0 - Current logical page length up to a maximum of 32767
 * 
 * 	# = number of 1/48 inch increments between rows.
 *
 * <b>Notes</b>
 * If the specified VMI is greater than the current logical page length, 
 * the command is ignored.
 * 
 * The value field is valid to 4 decimal places. A ? in the value field indicates 
 * no vertical movement. This command affects the Line Feed and Half-Line Feed spacing.
 * The factory default VMI is 8, which corresponds to 6 lines-per-inch. 
 * A user default VMI can be selected from the control panel using the
 * FORM menu item (refer to the printer User�s Manual for additional information).
 * 
 * <i>implemented Sep 20, 2005</i>
 * 
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.IPrinterState;
import org.getopt.pcl5.PrinterState;

public class CmdVerticalMotionIndex extends EscExtendedCommandPCL5 {

  public CmdVerticalMotionIndex(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if (family == '&' && subfamily == 'l' && cmd == 'C') {
      int param = Integer.parseInt((parameter));
      _printerState.setVerticalMotionIndex(param);

      return true;
    }

    return false;
  }
}
