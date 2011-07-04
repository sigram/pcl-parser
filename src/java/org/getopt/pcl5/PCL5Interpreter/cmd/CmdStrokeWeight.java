/**
 * <b>Command Esc ( s # S</b>
 * The Stroke Weight command designates the thickness of the strokes
 * that compose the characters of a font.
 * 
 * # = 	stroke weight
 * 
 * Default = 0
 * Range = - 7 to 7 (less than -7 maps to -7; greater than 7 maps to 7)
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

public class CmdStrokeWeight extends EscExtendedCommandPCL5 {

  public CmdStrokeWeight(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if ((family == '(' || family == ')') && subfamily == 's' && cmd == 'B') {
      int param = Integer.parseInt((parameter));

      if (param < -7 || param > 7)
        _printerState.assertCondition(this,
                "Parameter should be in range -7 - +7");

      _printerState.setStrokeWeight(param, family == '(');

      return true;
    }

    return false;
  }
}
