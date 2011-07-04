/**
 * <b>Command ESC * b # W</b>
 * The Transfer Raster Data command is used to transfer a row of raster data to the printer.
 *  
 * <b>Default</b> = N/A
 * <b>Range</b>   = 0 - 32767
 *
 * # = identifies the number of bytes in the raster row.
 * 
 * <b>Notes</b>
 * 
 * <i>implemented Sep 21, 2005</i>
 * 
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public class CmdTransferRasterData extends EscExtendedCommandPCL5 {

  public CmdTransferRasterData(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if (family == '*' && subfamily == 'b' && cmd == 'W') {
      int param = Integer.parseInt((parameter));
      byte[] data = new byte[param];
      in.read(data);

      _printerState.transferRasterData(data);

      return true;
    }

    return false;
  }
}
