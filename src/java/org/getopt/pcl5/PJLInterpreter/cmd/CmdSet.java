package org.getopt.pcl5.PJLInterpreter.cmd;

import org.getopt.pcl5.PrinterState;

public class CmdSet extends CommandPJL {

  private static final String COMMAND = "SET";

  public CmdSet(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(String[] cmd) {
    if (cmd[1].equalsIgnoreCase(COMMAND)) {
      _printerState.trace(this, cmd.toString());

      String[] params = cmd[2].split("=");
      _printerState.setEnvironment(params[0], params[1]);
    }

    return false;
  }

  public String getCommandString() {
    return COMMAND;
  }
}
