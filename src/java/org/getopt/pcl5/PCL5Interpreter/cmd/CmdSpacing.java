/**
 * <b>Command Esc ( s # P</b>
 * Inter-character spacing can be specified as either proportional or fixed.
 * 
 * # = 	0 - Fixed spacing
 * 		1 - Proportional spacing
 * 
 * Default = 4099, Body Text
 * Range = 0, 1 (values outside the range are ignored)
 * 
 * <b>Notes</b>
 * When proportional spacing is specified and a proportionally-spaced
 * font is not available (in the requested symbol set), a fixed pitch font
 * with the current pitch specification is selected. If fixed spacing is
 * specified but is not available, a proportional-spaced font is selected
 * and the pitch characteristic is ignored.
 * 
 * <i>implemented Sep 27, 2005</i>
 * 
 * @author piotrm
 *
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public class CmdSpacing extends EscExtendedCommandPCL5 {

  public CmdSpacing(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if ((family == '(' || family == ')') && subfamily == 's' && cmd == 'P') {
      int param = Integer.parseInt((parameter));

      if (param != 0 && param != 1)
        _printerState.assertCondition(this, "Paramater should be in range 0-1");

      _printerState.setSpacing(param, family == '(');

      return true;
    }

    return false;
  }
}
