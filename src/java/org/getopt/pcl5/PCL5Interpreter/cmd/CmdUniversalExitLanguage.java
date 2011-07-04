/**
 * <b>Command ESC % � 1 2 3 4 5 X</b>
 * Causes the printer to exit the current language and return control to PJL.
 *
 * <b>Default</b> = N/A
 * <b>Range</b> = �12345
 * 
 * <b>Notes</b>
 * This command performs the following actions:
 * Prints all data received before the Exit Language command.
 * Performs a printer reset (same effect as ? E).
 * Shuts down the PCL 5 printer language processor.
 * Turns control over to PJL.
 * 
 * <i>implemented Sep 17, 2005</i>
 * 
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.IPrinterState;
import org.getopt.pcl5.PrinterState;

public class CmdUniversalExitLanguage extends EscExtendedCommandPCL5 {
  /**
   * @param printerState
   */
  public CmdUniversalExitLanguage(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if (family == '%' && cmd == 'X') {
      int param = Integer.parseInt((parameter));
      if (subfamily != '\0')
        _printerState.assertCondition(this, "Subfamily should be 0");

      if (param != -12345)
        _printerState.assertCondition(this, "Parameter should be -12345");

      _printerState.reset();
      _printerState.setActiveLanguage(PrinterState.Language.PJL);

      return true;
    }

    return false;
  }
}
