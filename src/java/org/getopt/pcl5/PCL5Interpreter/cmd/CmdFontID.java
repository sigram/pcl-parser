/**
 * <b>Command ESC * c # D</b>
 * The Font ID command is used to specify an ID number for use in
 * subsequent font management commands. The ID number of a font
 * can be used to select the font for printing
 *  
 * <b>Default</b> = 0
 * <b>Range</b>   = 0 - 32767
 *
 * #   = ID number
 * 
 * <b>Notes</b>
 * The font ID number is used during subsequent soft font downloads, selections or deletions.
 * The factory default font ID is 0 (if no Font ID command is sent, an ID of 0 is assigned).
 *  
 * <i>implemented Sep 18, 2005</i>
 * 
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public class CmdFontID extends EscExtendedCommandPCL5 {

  public CmdFontID(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if (family == '*' && subfamily == 'c' && cmd == 'D') {
      int param = Integer.parseInt((parameter));
      _printerState.setFontID(param);

      return true;
    }

    return false;
  }
}
