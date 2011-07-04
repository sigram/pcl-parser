/**
 * <b>Command Esc E</b>
 * Restores the User Default Environment, deletes temporary fonts and macros, 
 * and prints any remaining data.
 * 
 * <b>Notes</b>
 *	Receipt of the Printer Reset command restores the User Default Environment, 
 * deletes temporary fonts, macros, user-defined symbol sets and patterns. 
 * It also prints any partial pages of data which may have been received.
 *
 * <i>implemented Sep 18, 2005</i>
 * 
 * @author piotrm
 *
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.IPrinterState;
import org.getopt.pcl5.PrinterState;

public class CmdPrinterReset extends EscCommandPCL5 {
  /**
   * @param printerState
   */
  public CmdPrinterReset(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char cmd, InputStream in) throws IOException {
    if (cmd == 'E') {
      _printerState.reset();

      return true;
    }

    return false;
  }
}
