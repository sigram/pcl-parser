/*
 * Created on 2004-09-17
 *
 */
package org.getopt.pcl5.PCL5Interpreter;

import org.getopt.pcl5.PrinterState;

/**
 * Base class to handle control codes commands Main reason for this class is to
 * create dinstinct type of commands
 */
abstract class Command extends BaseCommand {
  /**
   * @param printerState
   */
  Command(PrinterState printerState) {
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
