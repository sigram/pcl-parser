/**
 * <b>Command ESC ) # X</b>
 * Soft fonts can be specified using their associated ID numbers. 
 * (ID numbers are assigned to soft fonts using the Font ID command)
 *  
 * <b>Default</b> = 0
 * <b>Range</b>   = 0 - 32767
 *
 * #   = font ID 
 * 
 * <b>Notes</b>
 * If the designated font is present, the font is selected as the primary/secondary 
 * font and all primary/secondary font characteristics in the printer�s Font 
 * Select Table are set to those of the selected font.
 * However, if the selected font is proportionally spaced, the pitch
 * characteristic is not changed.
 * If the designated font is not present, the current font is retained.
 *  
 * <i>implemented Sep 21, 2005</i>
 * 
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;
import org.getopt.pcl5.PCL5Interpreter.FontHeaderCommon;

public class CmdFontSelectionbyID extends EscExtendedCommandPCL5 {

  public CmdFontSelectionbyID(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if ((family == '(' || family == ')') && subfamily == 0 && cmd == 'X') {
      int param = Integer.parseInt((parameter));
      _printerState.setFontID(param, family == '(');

      return true;
    }

    return false;
  }
}
