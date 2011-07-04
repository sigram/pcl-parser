/**
 * <b>Command Esc ( s # H</b>
 * Inter-character spacing can be specified as either proportional or fixed.
 * 
 * # = 	Pitch in characters/inch
 * 
 * Default = 10
 * Range = 0.00
 * 
 * <b>Notes</b>
 * The range of valid pitch selections for a fixed-spaced scalable font is
 * 576 to .10 characters/inch.
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

public class CmdPitch extends EscExtendedCommandPCL5 {

  public CmdPitch(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if ((family == '(' || family == ')') && subfamily == 's' && cmd == 'H') {
      int param = Integer.parseInt((parameter));
      _printerState.setPitch(param, family == '(');

      return true;
    }

    return false;
  }
}
