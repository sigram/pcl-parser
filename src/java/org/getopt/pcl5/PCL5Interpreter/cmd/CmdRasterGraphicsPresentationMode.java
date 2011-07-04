/**
 * <b>Command ESC * r # F</b>
 * The Raster Graphics Presentation command specifies the orientation
 * of the raster image on the logical page.
 *
 * <b>Default</b> = 3
 * <b>Range</b> = 0, 3
 * 
 * 	# =0 - Raster image prints in orientation of logical page
 * 		3 - Raster image prints along the width of the physical page
 *
 * <b>Notes</b>
 * A value of 0 indicates that a raster row �will be printed in the positive
 * X-direction of the PCL coordinate system. (The print direction
 * translates the PCL coordinate system.)
 * A value of 3 indicates that the raster graphics will be printed along
 * the width of the physical page, regardless of logical page orientation.
 * In portrait orientation, a raster row is printed in the positive X-direction
 * of the PCL coordinate system and a subsequent raster row is printed
 * beginning at the next dot row position in the positive Y-direction.
 * In landscape orientation, a raster row is printed in the positive
 * Y-direction of the PCL coordinate system and a subsequent raster
 * row is printed beginning at the next dot row position in the negative
 * X-direction.
 * 
 * <i>implemented Sep 18, 2005</i>
 * 
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.IPrinterState;
import org.getopt.pcl5.PrinterState;

public class CmdRasterGraphicsPresentationMode extends EscExtendedCommandPCL5 {

  public CmdRasterGraphicsPresentationMode(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if (family == '*' && subfamily == 'r' && cmd == 'F') {
      int param = Integer.parseInt((parameter));

      if (param != 0 && param != 3)
        _printerState.assertCondition(this, "Parameter should be 0 or 3");

      _printerState.setRasterMode(param);
      return true;
    }

    return false;
  }

}
