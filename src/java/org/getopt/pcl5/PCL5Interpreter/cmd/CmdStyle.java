/**
 * <b>Command Esc ( s # S</b>
 * The Style command identifies the posture of a character, its width,
 * and structure of the font symbols.
 * 
 * # = 	0 (upright, solid)
 * 		1 italic
 * 		4 condensed
 * 		5 condensed italic
 * 		8 compressed, or extra condensed
 * 		24 expanded
 * 		32 outline
 * 		64 inline
 * 		128 shadowed
 * 		160 outline shadowed
 * 
 * Default = 0
 * Range = 0 - 32767 (values greater than 32767 are set to 32767)
 * 
 * <b>Notes</b>
 * For selecting style, an exact match is required. If there is no match,
 * this characteristic is ignored, but stored in the font select table,
 * available for the next selection.
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

public class CmdStyle extends EscExtendedCommandPCL5 {

  public CmdStyle(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if ((family == '(' || family == ')') && subfamily == 's' && cmd == 'S') {
      int param = Integer.parseInt((parameter));
      _printerState.setStyle(param, family == '(');

      return true;
    }

    return false;
  }
}
