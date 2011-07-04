/*
 * Created on 2004-09-17
 *
 */
package org.getopt.pcl5.PCL5Interpreter;

import org.getopt.pcl5.PrinterState;

/**
 * Base class for all command classes Contains instance of PrinterState
 */
class BaseCommand {
  PrinterState _printerState;

  BaseCommand(PrinterState printerState) {
    this._printerState = printerState;
  }

  /**
   * Can return command code in derived class, if returned class will be put in
   * hashtable
   * 
   * @return code for command, 0 means not set
   */
  public char getCommandCode() {
    return 0;
  }
}
