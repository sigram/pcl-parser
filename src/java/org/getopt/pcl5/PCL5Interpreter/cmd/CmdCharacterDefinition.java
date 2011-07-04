/**
 * <b>Command ESC ( s # W</b>
 * The Character Descriptor and Data command is used to download
 * character data blocks to the printer for both bitmap and scalable fonts.
 *  
 * <b>Default</b> = N/A
 * <b>Range</b>   = 0 - 23767
 *
 * # = 	identifies the number of bytes in the immediately
 * 		following character data block. The maximum number is 32767.
 * 
 * <b>Notes</b>
 *
 * <b>Implementation</b>
 * User defined fonts are not implemented
 * 
 * <i>implemented Sep 21, 2005</i>
 * 
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;
import org.getopt.pcl5.PCL5Interpreter.FontDescriptorPCLBitmappedFonts;

public class CmdCharacterDefinition extends EscExtendedCommandPCL5 {

  public CmdCharacterDefinition(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if (family == '(' && subfamily == 's' && cmd == 'W') {
      int param = Integer.parseInt(parameter);

      int format = in.read();
      boolean continuation = in.read() != 0;

      FontDescriptorPCLBitmappedFonts font = null;

      if (continuation) {
        font = _printerState.getDefinedCharacter();
        font.continueFont(param, in);
      } else {
        switch (format) {
        case FontDescriptorPCLBitmappedFonts.FontType.LaserJetFamily:
          font = new FontDescriptorPCLBitmappedFonts(param, in);
          break;
        }
      }

      _printerState.setDefinedCharacter(font);

      return true;
    }

    return false;
  }
}
