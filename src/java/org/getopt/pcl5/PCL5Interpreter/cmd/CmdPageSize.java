/**
 * <b>Command ESC & l # A</b>
 * The Page Size command designates the size of the paper which in
 * turn defines the size of the logical page.
 *
 * <b>Default</b> = 2 (Configurable from Control Panel)
 * <b>Range</b> = 1, 2, 3, 6, 26, 27, 80, 81, 90, 91, 100 (Other values ignored)
 * 
 * PAPER:
 *  # = 1 - Executive (7? x 10? in.)
 * 		2 - Letter (8? x 11 in.)
 * 		3 - Legal (8? x 14 in.)
 * 		6 - Ledger (11 x 17 in.)
 * 	   26 - A4 (210mm x 297mm)
 * 	   27 - A3 (297mm x 420mm)
 * ENVELOPES:
 *  # = 80 - Monarch (Letter - 3 7/8 x 7? in.)
 *  	81 - Com-10 (Business - 4 1/8 x 9? in.)
 *  	90 - International DL (110mm x 220mm)
 *  	91 - International C5 (162mm x 229mm)
 *     100 - International B5 (176mm x 250mm)
 *     
 * <b>Notes</b>
 * Upon receipt of this command any unprinted pages are printed, the
 * top margin, text length, and left and right margins are set to their user
 * defaults, and any automatic macro overlay is disabled. The cursor is moved to 
 * the left edge of the logical page at the top margin on the following page. 
 * Also, certain HP-GL/2 state variables are reset
 * 
 * <i>implemented Sep 19, 2005</i>
 * 
 */

package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.IPrinterState;
import org.getopt.pcl5.PrinterState;

public class CmdPageSize extends EscExtendedCommandPCL5 {

  public CmdPageSize(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if (family == '&' && subfamily == 'l' && cmd == 'A') {
      int param = Integer.parseInt((parameter));

      if (param != 1 && param != 2 && param != 3 && param != 6 && param != 26
              && param != 27 && param != 80 && param != 81 && param != 90
              && param != 91 && param != 100)
        _printerState.assertCondition(this, "Parameter has incorrect value");

      _printerState.finishPage();
      _printerState.setPageSize(param);

      return true;
    }

    return false;
  }
}
