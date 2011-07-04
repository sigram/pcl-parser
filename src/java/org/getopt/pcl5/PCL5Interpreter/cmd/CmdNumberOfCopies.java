/**
 * <b>Command ESC & l # X</b>
 * The Number of Copies command designates the number of printed
 * copies of each page.
 * 
 * <b>Default</b> = 1 (Configurable from control panel)
 * <b>Range</b>   = 1-32767
 *
 * #   = Number of copies (1 to 32767 maximum)
 * 
 * <b>Notes</b>
 * This command can be received anywhere within a page and affects
 * the current page as well as subsequent pages.
 * The HP-GL/2 Replot (RP) command is inactive for PCL 5 printers;
 * use the Number of Copies command for multiple HP-GL/2 plots. 
 * To be effective, the Number of Copies command must be issued from
 * PCL prior to closing the page on which the plot is defined.
 * 
 *  <b>Implementation notes</b>
 *  This command is ignored by interpreter
 *  
 * <i>implemented Sep 18, 2005</i>
 * 
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public class CmdNumberOfCopies extends EscExtendedCommandPCL5 {

  public CmdNumberOfCopies(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if (family == '&' && subfamily == 'l' && cmd == 'X') {
      return true;
    }

    return false;
  }

}
