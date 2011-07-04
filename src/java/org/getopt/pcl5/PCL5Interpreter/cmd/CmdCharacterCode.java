/**
 * <b>Command ESC * c # E</b>
 * The Character Code command establishes the decimal code that is
 * associated with the next character downloaded. This value is used to
 * reference the character for printing.
 *  
 * <b>Default</b> = 0
 * <b>Range</b>   = 0 - 65535
 *
 * # = 	character code
 * 
 * <b>Notes</b>
 * For unbound fonts, the character code for a given character equals its symbol index value.
 * For TrueType fonts, a special code must be used to download glyphs
 * which never stand alone as characters. FFFF (hex) should be used for this purpose.
 *  
 * <i>implemented Sep 21, 2005</i>
 * 
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public class CmdCharacterCode extends EscExtendedCommandPCL5 {
  public CmdCharacterCode(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if (family == '*' && subfamily == 'c' && cmd == 'E') {
      int param = Integer.parseInt(parameter);
      _printerState.setCharacterCode(param);

      return true;
    }

    return false;
  }
}
