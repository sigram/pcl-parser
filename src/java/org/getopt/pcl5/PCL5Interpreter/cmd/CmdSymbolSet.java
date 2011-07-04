/**
 * <b>Command Esc ( ID</b>
 * The Symbol Set command identifies the specific set of symbols in a font.
 * 
 * <b>Notes</b>
 *
 * <i>implemented Sep 18, 2005</i>
 * 
 * @author piotrm
 *
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public class CmdSymbolSet extends EscExtendedCommandPCL5 {

  public CmdSymbolSet(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if ((family == '(' || family == ')') && subfamily == 0 && cmd != 'X') {
      _printerState.setSymbolSet(parameter + cmd, family == '(');

      return true;
    }

    return false;
  }
}
