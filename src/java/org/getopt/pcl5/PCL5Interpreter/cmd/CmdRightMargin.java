/**
 * <b>Command ESC & a # M</b>
 * The Right Margin command sets the right margin to the right edge of
 * the specified column.
 * 
 * <b>Default</b> = Logical Page right bound
 * <b>Range</b>   = Current left margin - Logical page right bound
 *
 * #   = Column number
 * 
 * <b>Notes</b>
 * The maximum right column is located at the right edge of the logical
 * page (the HMI setting defines the distance between columns, which
 * thereby defines the maximum number of columns on the logical
 * page). If the value field specifies a column which is greater than the
 * right edge of the logical page, the right margin is set to the right edge
 * of the logical page. If the value field specifies a column less than the
 * left margin, the command is ignored.
 * Margins represent a physical position and once set do not change
 * with subsequent changes in HMI.
 * If the cursor position is to the right of the new right margin, the cursor
 * is moved to the new right margin.
 *   
 * <i>implemented Sep 18, 2005</i>
 * 
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public class CmdRightMargin extends EscExtendedCommandPCL5 {
  public CmdRightMargin(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if (family == '&' && subfamily == 'a' && cmd == 'M') {
      int param = Integer.parseInt((parameter));
      _printerState.setRightMargin(param);

      return true;
    }

    return false;
  }
}
