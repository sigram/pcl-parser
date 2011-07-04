/**
 * <b>Command ESC & l # O</b>
 * Orientation defines the position of the logical page and the default
 * direction of print with respect to the physical page.
 *
 * <b>Default</b> = 3
 * <b>Range</b> = 0-3 (Other values ignored)
 * 
 * 	# = 0 - Portrait
 * 		1 - Landscape
 * 		2 - Reverse Portrait
 * 		3 - Reverse Landscape
 *
 * <b>Notes</b>
 * This command can be used only once per page. To print multiple
 * directions per page use the Print Direction command.
 * This command affects the HP-GL/2 environment
 *  
 * <i>implemented Sep 19, 2005</i>
 * 
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.IPrinterState;
import org.getopt.pcl5.PrinterState;

public class CmdLogicalPageOrientation extends EscExtendedCommandPCL5 {

  public CmdLogicalPageOrientation(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if (family == '&' && subfamily == 'l' && cmd == 'O') {
      int param = Integer.parseInt((parameter));

      if (param < 0 || param > 3)
        _printerState.assertCondition(this,
                "Parameter should be in 0 - 3 range");

      _printerState.setPageOrientation(param);
      return true;
    }

    return false;
  }
}
