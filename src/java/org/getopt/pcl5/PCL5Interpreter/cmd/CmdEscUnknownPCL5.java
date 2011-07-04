/*
 * Created on 2004-09-17
 *
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

/**
 * Unknown Esc command, traces out info about unrecognized command
 */
public class CmdEscUnknownPCL5 extends BaseCommandPCL5 {
  /**
   * @param printerState
   */
  public CmdEscUnknownPCL5(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char cmd, InputStream in) throws IOException {
    _printerState.trace(this.toString(), "Unknown Esc printer command [0x"
            + Integer.toHexString(cmd) + "]");

    return true;
  }
}
