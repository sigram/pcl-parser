/**
 * <b>Command ESC * r B</b>
 * The End Raster Graphics command signifies the end of a raster graphic data transfer.
 *  
 * 
 * <b>Notes</b>
 * Receipt of this command causes 5 operations:
 * - Resets the raster compression seed row to zeros.
 * - Moves the cursor to the raster row immediately following the end of the raster area 
 * 	(if a source raster height was specified).
 * - Allows raster commands which were previously locked out to be processed.

 * <i>implemented Sep 21, 2005</i>
 * 
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public class CmdEndRasterGraphics extends EscExtendedCommandPCL5 {

  public CmdEndRasterGraphics(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if (family == '*' && subfamily == 'r' && cmd == 'B') {
      _printerState.endGraphicsMode();

      return true;
    }

    return false;
  }
}
