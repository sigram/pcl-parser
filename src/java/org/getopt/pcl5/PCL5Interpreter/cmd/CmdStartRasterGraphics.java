/**
 * <b>Command ESC * r # A</b>
 * The Start Raster Graphics command identifies the beginning of the
 * raster data and also specifies the left graphics margin.
 *  
 * <b>Default</b> = 0
 * <b>Range</b>   = 0, 1 (values outside the range default to 0)
 *
 * # =0- Start graphics at default left graphics margin X-position 0).
 * 	1 - Start graphics at current cursor position (current X-position).
 * 
 * <b>Notes</b>
 * A value of 0 specifies that the left graphics margin is at the default left
 * margin of the page (X-position 0). A value of 1 specifies that the left
 * graphics margin is at the current X-position. In presentation mode 3,
 * the location of the left graphics margin varies depending on the orientation.
 * Once a Start Raster Graphics command is received by the printer,
 * raster graphics resolution, raster graphics presentation mode, raster
 * height, raster width, and left raster graphics margin are fixed until an
 * end raster graphics command is received.
 * 
 * Once in Raster Graphics Mode, PCL commands and text imply an End Raster Graphics 
 * (ESC*rC) except for the following commands:
 * - Transfer Raster Data
 * - Set Raster Compression Method
 * - Raster Y Offset
 * 
 * In addition, the following commands are ignored (i.e., locked out)
 * while in Raster Graphics Mode and do not imply an End Raster Graphics command:
 * -? Start Raster Graphics
 * - ?Set Raster Width
 * - Set Raster Height
 * - Set Raster Presentation Mode
 * - Set Raster Graphics Resolution
 * 
 * <i>implemented Sep 21, 2005</i>
 * 
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public class CmdStartRasterGraphics extends EscExtendedCommandPCL5 {

  public CmdStartRasterGraphics(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if (family == '*' && subfamily == 'r' && cmd == 'A') {
      int param = Integer.parseInt((parameter));

      if (param < 0 || param > 1)
        _printerState.assertCondition(this, "Parameer should be 0 or 1.");

      _printerState.startRasterGraphics(param);

      return true;
    }

    return false;
  }
}
