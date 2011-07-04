package org.getopt.pcl5.PJLInterpreter.cmd;

import org.getopt.pcl5.PrinterState;

public abstract class CommandPJL extends BaseCommandPJL {

  public CommandPJL(PrinterState printerState) {
    super(printerState);
  }

  /**
   * Execute command if possible
   * 
   * @param data
   *          command to execute
   * 
   * @return true if handled
   */
  abstract public boolean execute(String[] cmd);
}
