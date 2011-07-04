/**
 * <b>Command ESC * c # W</b>
 * The User-Defined Pattern command provides the means for
 * downloading the binary pattern data that defines the user pattern.
 * 
 * # =Number of pattern data bytes
 * 
 * <b>Default</b> = 0
 * <b>Range</b>   = 0 - 32767 (values outside the range are ignored)
 *
 * <b>Notes</b>
 *  
 * <i>implemented Sep 22, 2005</i>
 * 
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;
import org.getopt.pcl5.PCL5Interpreter.UserDefinedPattern;

public class CmdUserDefinedPattern extends EscExtendedCommandPCL5 {

  public CmdUserDefinedPattern(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if (family == '*' && subfamily == 'c' && cmd == 'W') {
      int param = Integer.parseInt((parameter));
      UserDefinedPattern pattern = new UserDefinedPattern(param, in);

      _printerState.setUserDefinedPattern(pattern);
      return true;
    }

    return false;
  }
}
