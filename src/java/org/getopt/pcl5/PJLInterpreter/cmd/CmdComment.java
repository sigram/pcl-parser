package org.getopt.pcl5.PJLInterpreter.cmd;

import org.getopt.pcl5.PrinterState;

public class CmdComment extends CommandPJL {

  private final String COMMAND = "COMMENT";

  public CmdComment(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(String[] cmd) {
    if (cmd[1].equalsIgnoreCase(COMMAND)) {
      String comment = "";
      for (int i = 2; i < cmd.length; i++)
        comment += cmd[i] + " ";

      _printerState.trace(this, comment);
    }

    return false;
  }

  public String getCommandString() {
    return COMMAND;
  }
}
