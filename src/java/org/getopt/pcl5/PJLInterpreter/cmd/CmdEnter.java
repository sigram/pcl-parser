package org.getopt.pcl5.PJLInterpreter.cmd;

import org.getopt.pcl5.PrinterState;

public class CmdEnter extends CommandPJL {

  private final String COMMAND = "ENTER";

  public CmdEnter(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(String[] cmd) {
    if (cmd[1].equalsIgnoreCase(COMMAND)) {
      _printerState.trace(this, cmd.toString());

      String[] params = cmd[2].split("=");

      if (params[1].equalsIgnoreCase("PCL"))
        _printerState.setActiveLanguage(PrinterState.Language.PCL5);
      else if (params[1].equalsIgnoreCase("HPGL"))
        _printerState.setActiveLanguage(PrinterState.Language.HPGL);
      else
        _printerState.assertCondition(this, "Unknown language: " + params[0]);
    }

    return false;
  }

  public String getCommandString() {
    return COMMAND;
  }
}
