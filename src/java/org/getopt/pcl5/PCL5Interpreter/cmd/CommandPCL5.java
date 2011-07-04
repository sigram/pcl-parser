/*
 * Created on 2004-09-17
 *
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import org.getopt.pcl5.PrinterState;

/**
 * Base class to handle control codes commands Main reason for this class is to
 * create dinstinct type of commands
 */
public abstract class CommandPCL5 extends BaseCommandPCL5 {
  /**
   * @param printerState
   */
  CommandPCL5(PrinterState printerState) {
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
  abstract public boolean execute(int data);
}
