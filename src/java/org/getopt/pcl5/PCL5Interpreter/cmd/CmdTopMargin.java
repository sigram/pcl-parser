/**
 * <b>Command ESC & l # E</b>
 * The Top Margin command designates the number of lines between
 * the top of the logical page and the top of the text area.
 * 
 * <b>Default</b> = 1/2 inch down from top of logical page
 * 					If logical page length is <? inch, then the top margin 
 * 					is set to top of logical page.
 * <b>Range</b>   = 0 - Length of logical page (Other values ignored)
 *
 * #   = Number of lines
 * 
 * <b>Notes</b>
 * The Top Margin command is ignored if the value field (#) is greater
 * than the current logical page length or if the current VMI is 0 (VMI
 * defines the distance between lines of text).
 * Receipt of a Top Margin command resets the text length according to
 * the following equation:
 * 	Text Length = (logical page len in inches) - (top margin in inches + 1/2 inch)
 * 
 * The top margin represents a physical position and once set does not
 * change with subsequent changes in VMI or line spacing.
 * 
 * The vertical cursor position for the first line of print is 
 * determined by the current values of the top margin and VMI 
 * using the following equation:
 *  first line in inches = top margin in inches + (0.75 * VMI)
 *  
 * Note The default cursor position is not located at the intersection of the
 * top margin and the left bound of the logical page.
 * The cursor is actually positioned down 75% of the VMI distance
 * (0.75 � VMI) from the top margin. This positions the cursor at the
 * relative base line position of a character cell for correct character
 * positioning.
 *  
 *  
 * <i>implemented Sep 18, 2005</i>
 * 
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public class CmdTopMargin extends EscExtendedCommandPCL5 {
  public CmdTopMargin(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if (family == '&' && subfamily == 'l' && cmd == 'E') {
      int param = Integer.parseInt((parameter));
      _printerState.setTopMargin(param);

      return true;
    }

    return false;
  }
}
