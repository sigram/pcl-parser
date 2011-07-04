/**
 * <b>Command ESC * c # P</b>
 * This command fills (prints) a rectangular area of the specified width
 * and height with the specified area fill.
 *  
 * <b>Default</b> = 0
 * <b>Range</b>   = 0 - 5 (values outside the range are ignored)
 *
 * # =0 - Black fill (rule)
 * 	1 - Erase (white) fill
 * 	2 - Shaded fill
 * 	3 - Cross-hatch fill
 * 	4 - User-defined pattern fill
 * 	5 - Current pattern fill
 * 
 * <b>Notes</b>
 * <b>Black fill</b> � fills the rectangular area with black fill.
 * <b>White fill</b> � erases any fill in the rectangular area 
 * 							(it fills the rectangular area with white fill).
 * <b>Shaded fill</b> � fills the rectangular area with one of the eight shading patterns 
 * 							as specified by the Pattern ID command.
 * <b>Cross-Hatch fill</b> � fills the rectangular area with one of the six cross-hatched 
 * 									patterns as specified by the Pattern ID command.
 * <b>User-defined fill</b> � fills the rectangular area with custom pattern data 
 * 									as specified by the Pattern ID command and downloaded 
 * 									by the User-Defined Pattern command.
 * <b>Current Pattern</b> � fills the rectangular area with the current pattern.
 * 
 * <i>implemented Sep 21, 2005</i>
 * 
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public class CmdFillRectangularArea extends EscExtendedCommandPCL5 {
  public CmdFillRectangularArea(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if (family == '*' && subfamily == 'c' && cmd == 'P') {
      int param = Integer.parseInt((parameter));

      if (param < 0 || param > 5)
        _printerState.assertCondition(this, "Parameer should be in 0-5 range.");

      _printerState.fillRectangularArea(param);

      return true;
    }

    return false;
  }
}
