/**
 * 
 */
package org.getopt.pcl5.HPGLInterpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

/**
 * @author pmarkiew
 * 
 */
public class CmdComment extends CommandHPGL {

  /**
   * @param printerState
   */
  public CmdComment(PrinterState printerState) {
    super(printerState);

    _command = "CO";
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.ccginc.pcl5.HPGLInterpreter.cmd.CommandHPGL#execute(java.io.InputStream
   * )
   */
  protected void execute(InputStream in) throws IOException {
    char c = (char) in.read();

    while (c != '"')
      // first quote
      c = (char) in.read();

    StringBuilder sb = new StringBuilder();
    c = (char) in.read();
    while (c != '"') // end quote
    {
      sb.append(c);
      c = (char) in.read();
    }

    _printerState.trace(this, sb.toString());
  }

}
