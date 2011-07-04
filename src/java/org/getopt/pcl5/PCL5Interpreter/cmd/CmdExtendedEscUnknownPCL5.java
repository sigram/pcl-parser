/*
 * Created on 2004-09-17
 *
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

/**
 * Unknown Extended Esc command, traces out info about unrecognized command
 */
public class CmdExtendedEscUnknownPCL5 extends BaseCommandPCL5 {
  /**
   * @param printerState
   */
  public CmdExtendedEscUnknownPCL5(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    _printerState.trace(this.toString(),
            "Unknown extended Esc printer command [" + family + subfamily
                    + parameter + cmd + "]");

    return true;
  }

}
