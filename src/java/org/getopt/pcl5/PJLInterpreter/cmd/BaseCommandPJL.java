package org.getopt.pcl5.PJLInterpreter.cmd;

import org.getopt.pcl5.PrinterState;

/**
 * Base class for all command classes Contains instance of PrinterState
 */
public class BaseCommandPJL {
  PrinterState _printerState;

  public BaseCommandPJL(PrinterState printerState) {
    this._printerState = printerState;
  }

  /**
   * Can return command code in derived class, if returned class will be put in
   * hashtable
   * 
   * @return code for command, null means not set
   */
  public String getCommandString() {
    return null;
  }
}
