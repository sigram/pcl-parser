/**
 * <b>Command ESC * c # X</b>
 * This PCL command specifies the horizontal dimension of the window
 * to be used for printing an HP-GL/2 plot.
 * 
 * # =Horizontal size in decipoints (1/720th inch)
 * 
 * <b>Default</b> = width of the current logical page
 * <b>Range</b>   = 0 - 32767 (valid to 4 decimal places)
 *
 * <b>Notes</b>
 * Using this command defaults the location of P1 to the lower left
 * corner of the picture frame, and P2 to the upper right corner of the
 * picture frame. It also resets the soft-clip window to the PCL Picture
 * Frame boundaries, clears the polygon buffer, and updates the
 * HP-GL/2 pen position to the lower-left corner of the picture frame
 * (P1), as viewed from the current orientation.
 *  
 * <i>implemented Sep 22, 2005</i>
 * 
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public class CmdPictureFrameHorizontalSize extends EscExtendedCommandPCL5 {

  public CmdPictureFrameHorizontalSize(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if (family == '*' && subfamily == 'c' && cmd == 'X') {
      int param = Integer.parseInt((parameter));
      _printerState.setHorizontalPictureFrameSize(param);
      return true;
    }

    return false;
  }
}
