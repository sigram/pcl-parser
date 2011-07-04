/**
 * <b>Command ESC & l # H</b>
 * Prints front side of a page or both sides (front and back - in either of two binding modes).
 *
 * <b>Default</b> = Printer Dependent (Configurable from Control Panel)
 * <b>Range</b> = Printer Dependent
 * 
 * 	# = 0 - Print the current page (paper source remains unchanged).
 * 		1 - Feed paper from the a printer-specific tray.
 * 		2 - Feed paper from manual input.
 * 		3 - Feed envelope from manual input.
 * 		4 - Feed paper from lower tray.
 * 		5 - Feed from optional paper source.
 * 		6 - Feed envelope from optional envelope feeder (Must be used in conjunction with Page Size command, envelope selection.)
 *
 * <b>Notes</b>
 * Not all HP LaserJet printers support all possible paper sources. The
 * implementation of paper source locations varies slightly from printer
 * to printer.
 * The Paper Source command causes the current page to be printed
 * and the cursor to be moved to the left edge of the logical page at the
 * top margin position for the next page (see Figure 5-5).
 * 
 * <b>Implementation notes</b>
 * This command is ignored by interpreter.
 * 
 * <i>implemented Sep 18, 2005</i>
 * 
 */

package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.IPrinterState;
import org.getopt.pcl5.PrinterState;

public class CmdPaperSource extends EscExtendedCommandPCL5 {

  public CmdPaperSource(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if (family == '&' && subfamily == 'l' && cmd == 'H') {
      _printerState.newPage();

      return true;
    }

    return false;
  }
}
