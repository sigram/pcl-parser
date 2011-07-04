/**
 * <b>Command CR</b>
 * Moves the print position to the left margin position.
 * 
 * <i>implemented Sep 20, 2005</i>
 *  
 * @author Piotrm
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import org.getopt.pcl5.PrinterState;
import org.getopt.pcl5.PCL5Interpreter.ControlCodes;

public class CmdCarriageReturn extends CommandPCL5 {

  public CmdCarriageReturn(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(int data) {
    if (data == ControlCodes.CR) {
      _printerState.setColumn(_printerState.getLeftMargin());

      return true;
    }

    return false;
  }
}
