/**
 * <b>Command Esc ( s # T</b>
 * The Typeface Family command designates the design of the font.
 * 
 * # = Typeface family value
 * 
 * Default = 4099, Body Text
 * Range = 10 - 65535 (values greater than 65535 are set to 65535)
 * 
 * <b>Notes</b>
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

public class CmdTypefaceFamily extends EscExtendedCommandPCL5 {

  public CmdTypefaceFamily(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if ((family == '(' || family == ')') && subfamily == 's' && cmd == 'T') {
      int param = Integer.parseInt((parameter));
      _printerState.setTypefaceFamily(param, family == '(');

      return true;
    }

    return false;
  }
}
