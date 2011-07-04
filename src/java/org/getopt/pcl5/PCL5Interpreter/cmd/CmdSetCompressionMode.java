/**
 * <b>Command ESC * b # M</b>
 * The Set Compression Method command allows you to code raster
 * data in one of four compressed formats: Run-length encoding,
 * tagged imaged file format (TIFF) rev. 4.0, delta row compression,
 * and adaptive compression. The choice of compression methods
 * affects both the amount of code needed to generate a raster
 * graphic image and the efficiency with which the image is printed.
 * 
 *  
 * <b>Default</b> = 0
 * <b>Range</b>   = 0 - 5 (values outside the range are ignored)
 *
 *	# = 0- Unencoded
 *		1 - Run-length encoding
 *		2 - Tagged Imaged File Format (TIFF) rev. 4.0
 *		3 - Delta row compression
 *		4 - Reserved
 *		5 - Adaptive compression
 * 
 * <b>Notes</b>
 *  
 * <i>implemented Sep 18, 2005</i>
 * 
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public class CmdSetCompressionMode extends EscExtendedCommandPCL5 {

  public CmdSetCompressionMode(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if (family == '*' && subfamily == 'b' && cmd == 'M') {
      int param = Integer.parseInt((parameter));

      if (param < 0 || param > 5)
        _printerState.assertCondition(this, "Parameter should be in 0-5 range");

      _printerState.setCompressionMode(param);

      return true;
    }

    return false;
  }
}
