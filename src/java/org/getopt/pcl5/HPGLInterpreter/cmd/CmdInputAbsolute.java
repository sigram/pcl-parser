package org.getopt.pcl5.HPGLInterpreter.cmd;

import java.awt.Point;
import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public class CmdInputAbsolute extends CommandHPGL {

  public CmdInputAbsolute(PrinterState printerState) {
    super(printerState);

    _command = "IP";
  }

  protected void execute(InputStream in) throws IOException {
    String s = readInput(in);
    String[] params = s.split(",");

    Point p1 = _printerState.getHPGLState().getP1();
    Point p2 = _printerState.getHPGLState().getP2();

    if (params.length == 0) // no params
    {
      p1 = new Point(0, 0);
      p2 = new Point(_printerState.getHorizontaPictureFrameSize(),
              _printerState.getVerticalPictureFrameSize());
    } else if (params.length == 2) // P1 only
    {
      int x = Integer.parseInt(params[0]);
      int y = Integer.parseInt(params[1]);

      p2.setLocation(p2.getX() + x - p1.getX(), p2.getY() + y - p1.getY());
      p1.setLocation(x, y);
    } else if (params.length == 4) // P1 and P2
    {
      int x = Integer.parseInt(params[0]);
      int y = Integer.parseInt(params[1]);
      p1.setLocation(x, y);

      x = Integer.parseInt(params[2]);
      y = Integer.parseInt(params[3]);
      p2.setLocation(x, y);
    } else {
      _printerState.assertCondition(this, "Incorrect numer of parameters");
    }

    _printerState.getHPGLState().setP1(p1);
    _printerState.getHPGLState().setP2(p2);
  }

}
