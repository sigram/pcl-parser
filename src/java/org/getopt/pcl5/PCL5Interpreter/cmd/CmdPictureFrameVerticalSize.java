/**
 * <b>Command ESC * c # Y</b>
 * This PCL command specifies the vertical dimension of the window
 * to be used for printing an HP-GL/2 plot.
 * 
 * # =Vertical size in decipoints (1/720th inch)
 * 
 * <b>Default</b> = The distance between the default top and bottom margins (the default text length)
 * <b>Range</b>   = 0 - 32767 (valid to 4 decimal places)
 *
 * <b>Notes</b>
 *  
 * <i>implemented Sep 22, 2005</i>
 * 
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public class CmdPictureFrameVerticalSize extends EscExtendedCommandPCL5 {

  public CmdPictureFrameVerticalSize(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if (family == '*' && subfamily == 'c' && cmd == 'Y') {
      int param = Integer.parseInt((parameter));
      _printerState.setVerticalPictureFrameSize(param);
      return true;
    }

    return false;
  }
}
