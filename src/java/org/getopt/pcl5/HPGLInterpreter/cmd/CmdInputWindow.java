package org.getopt.pcl5.HPGLInterpreter.cmd;

import java.awt.Rectangle;
import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public class CmdInputWindow extends CommandHPGL {

  public CmdInputWindow(PrinterState printerState) {
    super(printerState);

    _command = "IW";
  }

  protected void execute(InputStream in) throws IOException {
    String s = readInput(in);
    String[] params = s.split(",");

    if (params.length == 0) // no params
      _hpgl.setInputWindow(new Rectangle(0, 0, _printerState
              .getHorizontaPictureFrameSize(), _printerState
              .getVerticalPictureFrameSize()));
    else if (params.length == 4)
      _hpgl.setInputWindow(new Rectangle(Integer.parseInt(params[0]), Integer
              .parseInt(params[1]), Integer.parseInt(params[2]), Integer
              .parseInt(params[3])));
  }

}
