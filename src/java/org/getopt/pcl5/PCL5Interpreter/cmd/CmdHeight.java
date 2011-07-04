/**
 * <b>Command Esc ( s # V</b>
 * The Height command specifies the height of the font in points. 
 * This characteristic is ignored when selecting a fixed-spaced scalable font;
 * however, the value is saved and available when a bitmap font or 
 * a proportionally-spaced scalable font is selected.
 * 
 * # = 	Height in points
 * 
 * Default = 12
 * Range = 0.25 - 999.75
 * 
 * <b>Notes</b>
 * The value field (#) is valid to two decimal places.
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

public class CmdHeight extends EscExtendedCommandPCL5 {

  public CmdHeight(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if ((family == '(' || family == ')') && subfamily == 's' && cmd == 'V') {
      int param = Integer.parseInt((parameter));
      _printerState.setHeight(param, family == '(');

      return true;
    }

    return false;
  }
}
