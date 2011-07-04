package org.getopt.pcl5.PJLInterpreter.cmd;

import org.getopt.pcl5.PrinterState;

public class CmdUnknownPJL extends CommandPJL {

  public CmdUnknownPJL(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(String[] cmd) {
    _printerState.trace(this.toString(), "Unknown PJL printer command "
            + cmd[1]);

    return false;
  }

}
