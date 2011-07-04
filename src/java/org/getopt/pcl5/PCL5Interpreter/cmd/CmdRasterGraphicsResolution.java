/**
 * <b>Command ESC * t # R</b>
 * Raster graphics can be printed at various resolutions. This command
 * designates the resolution of subsequent raster data transfers in
 * dots-per inch.
 * 
 * <b>Default</b> = 75
 * <b>Range</b>   = 75, 100, 150, 200, 300, 600
 *
 * #   = 75 - 75 dots-per-inch
 * 		100 - 100 dots-per-inch
 * 		150 - 150 dots-per-inch
 * 		200 - 200 dots-per-inch
 * 		300 - 300 dots-per-inch
 * 		600 - 600 dots-per-inch
 * 
 * <b>Notes</b>
 * When configured for 300 dpi resolution, the printer automatically
 * expands raster graphics transferred at resolutions less than 300
 * dots-per-inch to 300 dots-per-inch during printing.
 *  
 * <i>implemented Sep 18, 2005</i>
 * 
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public class CmdRasterGraphicsResolution extends EscExtendedCommandPCL5 {

  public CmdRasterGraphicsResolution(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if (family == '*' && subfamily == 't' && cmd == 'R') {
      int param = Integer.parseInt((parameter));

      if (param != 75 && param != 100 && param != 150 && param != 200
              && param != 300 && param != 600)
        _printerState.assertCondition(this,
                "Parameter should be 75, 100, 150, 200, 300, 600");

      _printerState.setResolution(param);
      return true;
    }

    return false;
  }

}
