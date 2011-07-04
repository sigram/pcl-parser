package org.getopt.pcl5.HPGLInterpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public class CmdRotateCoordinateSystem extends CommandHPGL {

  public CmdRotateCoordinateSystem(PrinterState printerState) {
    super(printerState);
    _command = "RO";
  }

  protected void execute(InputStream in) throws IOException {
    String s = readInput(in);

    int rotation = 0;

    if (s.length() > 0)
      rotation = Integer.parseInt(s);

    _hpgl.setDrawingRotation(rotation);
  }

}
