/**
 * <b>Command ESC * v # T</b>
 * The Select Current Pattern command identifies the type of pattern to
 * be applied onto the destination.
 *  
 * <b>Default</b> = 0
 * <b>Range</b>   = 0 - 4 (other values cause the command to be ignored)
 *
 * # =0 - Solid black (default)
 * 	1 - Solid white
 * 	2 - Shading pattern
 * 	3 - Cross-hatch pattern
 * 	4 - User-defined pattern
 * 
 * <b>Notes</b>
 * This command selects which type of pattern is applied.
 * 
 * <i>implemented Sep 21, 2005</i>
 * 
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public class CmdSelectCurrentPattern extends EscExtendedCommandPCL5 {

  public CmdSelectCurrentPattern(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if (family == '*' && subfamily == 'v' && cmd == 'T') {
      int param = Integer.parseInt((parameter));
      if (param < 0 || param > 4)
        _printerState.assertCondition(this,
                "Parameter should be in 0 - 4 range.");

      _printerState.setCurrentPattern(param);

      return true;
    }

    return false;
  }
}
