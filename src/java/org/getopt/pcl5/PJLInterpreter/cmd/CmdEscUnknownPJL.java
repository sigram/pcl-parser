package org.getopt.pcl5.PJLInterpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

/**
 * Unknown Esc command, traces out info about unrecognized command
 */
public class CmdEscUnknownPJL extends BaseCommandPJL {

  public CmdEscUnknownPJL(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char cmd, InputStream in) throws IOException {
    _printerState.trace(this.toString(), "Unknown Esc printer command [0x"
            + Integer.toHexString(cmd) + "]");

    return true;
  }
}
