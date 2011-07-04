package org.getopt.pcl5.HPGLInterpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public class CmdArcRelative extends CommandHPGL {

  public CmdArcRelative(PrinterState printerState) {
    super(printerState);

    _command = "AC";
  }

  protected void execute(InputStream in) throws IOException {
    String s = readInput(in);
    String[] params = s.split(",");

    // begining of arc is in current point
    int x = _hpgl.getX() + Integer.parseInt(params[0]);
    int y = _hpgl.getY() + Integer.parseInt(params[1]);
    double angle = Double.parseDouble(params[2]);

    _hpgl.drawArc(x, y, angle);

  }

}
