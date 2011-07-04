/**
 * <b>Command ESC * v # O</b>
 * The Character Descriptor and Data command is used to download
 * character data blocks to the printer for both bitmap and scalable fonts.
 *  
 * <b>Default</b> = N/A
 * <b>Range</b>   = 0, 1 (other values cause the command to be ignored)
 *
 * # =0 - Transparent
 * 	1 - Opaque
 * 
 * <b>Notes</b>
 * A transparency mode of �0� (transparent) means that the white
 * regions of the pattern image are not copied onto the destination.
 * A transparency mode of �1� (opaque) means that the white pixels
 * in the pattern are applied directly onto the destination.
 * When printing white rules, the pattern transparency is treated as if it
 * were �opaque�; white rules erase black rules regardless of the transparency mode.
 * 
 * <i>implemented Sep 21, 2005</i>
 * 
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;
import org.getopt.pcl5.PCL5Interpreter.FontDescriptorPCLBitmappedFonts;

public class CmdPatternTransparencyMode extends EscExtendedCommandPCL5 {

  public CmdPatternTransparencyMode(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if (family == '*' && subfamily == 'v' && cmd == 'O') {
      int param = Integer.parseInt((parameter));

      if (param != 0 && param != 1)
        _printerState.assertCondition(this, "Parameter should be 0 or 1.");

      _printerState.setPatternTransparencyMode(param);

      return true;
    }

    return false;
  }
}
