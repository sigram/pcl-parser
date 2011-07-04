/**
 * <b>Command ESC & a # L</b>
 * The Left Margin command sets the left margin to the left edge of the
 * specified column.
 * 
 * <b>Default</b> = Column 0 (Left bound of logical page)
 * <b>Range</b>   = 0 - Right margin
 *
 * #   = Column number
 * 
 * <b>Notes</b>
 * The first column within a line is column 0, which is located at the left
 * edge of the logical page (the HMI setting defines the distance
 * between columns, which thereby defines the maximum number of
 * columns on the logical page). If the value field specifies a column
 * greater than the current right margin, the command is ignored.
 * Margins represent a physical position and once set do not change
 * with subsequent changes in HMI.
 * If the cursor is to the left of the new left margin, the cursor is moved to
 * the new left margin.  
 *  
 * <i>implemented Sep 18, 2005</i>
 * 
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public class CmdLeftMargin extends EscExtendedCommandPCL5 {
  public CmdLeftMargin(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if (family == '&' && subfamily == 'a' && cmd == 'L') {
      int param = Integer.parseInt((parameter));
      _printerState.setLeftMargin(param);

      return true;
    }

    return false;
  }
}
