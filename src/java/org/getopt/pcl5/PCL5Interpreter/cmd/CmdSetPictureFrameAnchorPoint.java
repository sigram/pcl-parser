/**
 * <b>Command ESC * c 0 T</b>
 * The Unit of Measure command establishes the unit of measure for
 * PCL Unit cursor movements.
 * 
 * <b>Default</b> = 0
 * <b>Range</b>   = 0
 *
 * <b>Notes</b>
 * The position of the picture frame anchor point defines the location of
 * the upper left corner of the PCL Picture Frame. The �upper left� refers
 * to the corner for which X and Y coordinates are minimized when the
 * print direction is 0.
 * A parameter value of zero (ESC*c0T) specifies that the picture frame
 * anchor point should be set to the cursor position. Sending a cursor
 * move command prior to sending this command places the picture
 * frame anchor in the desired location. All parameter values other than
 * zero are ignored, but if you do not send a Set Picture Frame Anchor
 * command, the printer defaults the anchor point to the left edge of the
 * logical page and the default top margin.
 *  
 * <i>implemented Sep 22, 2005</i>
 * 
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public class CmdSetPictureFrameAnchorPoint extends EscExtendedCommandPCL5 {

  public CmdSetPictureFrameAnchorPoint(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if (family == '*' && subfamily == 'c' && cmd == 'T') {
      int param = Integer.parseInt((parameter));

      if (param != 0)
        _printerState.assertCondition(this, "Parameter should be 0");

      _printerState.setPictureFrameAnchorPoint(param);
      return true;
    }

    return false;
  }
}
