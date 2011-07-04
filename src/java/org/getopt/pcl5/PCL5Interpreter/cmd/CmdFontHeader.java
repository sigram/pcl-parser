/**
 * <b>Command ESC ) s # W</b>
 * The Font Header command is used to download font header data to
 * the printer.
 *  
 * <b>Default</b> = 0
 * <b>Range</b>   = 0 - 32767
 *
 * #   = identifies the number of bytes in the font header.
 * 
 * <b>Notes</b>
 * There are different font formats
 * 	Format 0 Font Header for PCL Bitmapped Fonts
 * ? Format 20 Font Header for Resolution-Specified Bitmapped
 *  Format 10 Font Header for Intellifont Bound Scalable Fonts
 *  Format 11 Font Header for Intellifont Unbound Scalable
 *  Format 15 TrueType Scalable Font Header
 *  
 * <i>implemented Sep 20, 2005</i>
 * 
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;
import org.getopt.pcl5.PCL5Interpreter.FontHeaderCommon;
import org.getopt.pcl5.PCL5Interpreter.FontHeaderIntellifontBound;
import org.getopt.pcl5.PCL5Interpreter.FontHeaderIntellifontUnbound;
import org.getopt.pcl5.PCL5Interpreter.FontHeaderPCLBitmappedFonts;
import org.getopt.pcl5.PCL5Interpreter.FontHeaderResolutionSpecifiedBitmapped;
import org.getopt.pcl5.PCL5Interpreter.FontHeaderTrueType;

public class CmdFontHeader extends EscExtendedCommandPCL5 {

  public CmdFontHeader(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if (family == ')' && subfamily == 's' && cmd == 'W') {
      int descriptorSize = 256 * in.read() + in.read();
      int headerFormat = in.read();

      FontHeaderCommon fontHeader = null;

      switch (headerFormat) {
      case FontHeaderCommon.PCL_BITMAPPED:
        fontHeader = new FontHeaderPCLBitmappedFonts(descriptorSize, in);
        break;

      case FontHeaderCommon.INTELLIFONT_BOUND:
        fontHeader = new FontHeaderIntellifontBound(descriptorSize, in);
        break;

      case FontHeaderCommon.INTELLIFONT_UNBOUND:
        fontHeader = new FontHeaderIntellifontUnbound(descriptorSize, in);
        break;

      case FontHeaderCommon.RESOLUTION_SPECIFIED_BITMAPPED:
        fontHeader = new FontHeaderResolutionSpecifiedBitmapped(descriptorSize,
                in);
        break;

      case FontHeaderCommon.TRUE_TYPE:
        fontHeader = new FontHeaderTrueType(descriptorSize, in);
        break;

      default:
        _printerState.assertCondition(this, "Unknown font type");
      }

      _printerState.setFontHeader(fontHeader);

      return true;
    }

    return false;
  }
}
