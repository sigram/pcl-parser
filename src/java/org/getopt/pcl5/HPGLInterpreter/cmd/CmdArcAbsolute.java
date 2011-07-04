package org.getopt.pcl5.HPGLInterpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public class CmdArcAbsolute extends CommandHPGL {

  public CmdArcAbsolute(PrinterState printerState) {
    super(printerState);

    _command = "AA";
  }

  protected void execute(InputStream in) throws IOException {
    String s = readInput(in);
    String[] params = s.split(",");

    // begining of arc is in current point
    int x = Integer.parseInt(params[0]);
    int y = Integer.parseInt(params[1]);
    double angle = Double.parseDouble(params[2]);

    _hpgl.drawArc(x, y, angle);
  }

}
