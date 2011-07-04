/**
 * <b>Command ESC & d # D</b>
 * <b>Command ESC & d @</b>
 * The Underline command controls automatic text underlining.
 *  
 * <b>Default</b> = 0
 * <b>Range</b>   = 0, 3 (values outside range are ignored)
 *
 * # = 	0 - Fixed position
 * 		3 - Floating position 
 * ESC & d @ - disable underline
 * 
 * <b>Notes</b>
 * Once underlining is enabled, any positive horizontal movement causes an underline 
 * to be drawn. Positive horizontal movement includes the printing of text and positive 
 * horizontal cursor motion.
 * When fixed position underlining is enabled, the underline is drawn five dots below 
 * the baseline and is three dots thick. (The baseline is the dot row on which 
 * all of the characters in a given line appear to stand, see Chapter 11.) 
 * When floating position underline is enabled, the underline position is determined by 
 * the greatest underline distance below the baseline of all of the fonts printed on 
 * the current line.
 *  
 * <i>implemented Sep 21, 2005</i>
 * 
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public class CmdUnderline extends EscExtendedCommandPCL5 {

  public CmdUnderline(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if (family == '&' && subfamily == 'd' && cmd == 'D') {
      int param = Integer.parseInt((parameter));
      _printerState.setUnderlineType(param);

      return true;
    }

    if (family == '&' && subfamily == 'd' && cmd == '@') {
      _printerState.setUnderlineType(-1);

      return true;
    }

    return false;
  }
}