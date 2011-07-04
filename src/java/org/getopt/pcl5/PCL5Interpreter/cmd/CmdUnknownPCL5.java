/*
 * Created on 2004-09-17
 *
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import org.getopt.pcl5.PrinterState;

/**
 * Unknown command, traces out info about unrecognized command
 */
public class CmdUnknownPCL5 extends BaseCommandPCL5 {
  /**
   * @param printerState
   */
  public CmdUnknownPCL5(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(int data) {
    _printerState.trace(this.toString(), "Unknown printer command [0x"
            + Integer.toHexString(data) + "]");

    return true;
  }
}
