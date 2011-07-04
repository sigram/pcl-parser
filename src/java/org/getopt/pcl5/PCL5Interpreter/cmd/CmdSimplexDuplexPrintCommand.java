/**
 * <b>Command ESC & l # S</b>
 * Prints front side of a page or both sides (front and back - in either of two binding modes).
 *
 * <b>Default</b> = 0
 * <b>Range</b> = 0 - 2 (Other values ignored)
 * 
 * 	# = 0 - Simplex
 *		1 - Duplex, Long-Edge Binding
 *		2 - Duplex, Short-Edge Binding
 *
 * <b>Notes</b>
 * This command designates either simplex or duplex printing mode for
 * duplex printers. Simplex mode prints an image on only one side of a
 * sheet (page). Duplex mode prints images on both sides of a sheet.
 * If this command is received by a printer which does not contain the
 * duplex feature, it is ignored. Printers which do not contain the duplex
 * feature print in simplex mode (front side of sheet) only.
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

public class CmdSimplexDuplexPrintCommand extends EscExtendedCommandPCL5 {

  /**
   * @param printerState
   */
  public CmdSimplexDuplexPrintCommand(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if (family == '&' && subfamily == 'l' && cmd == 'S') {
      int param = Integer.parseInt((parameter));
      if (param < 0 || param > 2)
        _printerState.assertCondition(this, "Parameter should be 0-2 range");

      return true;
    }

    return false;
  }
}
