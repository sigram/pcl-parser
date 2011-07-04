package org.getopt.pcl5.HPGLInterpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public class CmdScale extends CommandHPGL {

  public CmdScale(PrinterState printerState) {
    super(printerState);

    _command = "SC";
  }

  protected void execute(InputStream in) throws IOException {
    String s = readInput(in);
    String[] params = s.split(",");

    if (params.length == 0) {
      // _hpgl.setScale(1.0, 1.0);
    } else if (params.length == 4) {
      // _hpgl.setScale(1.0, 1.0);
    } else if (params.length == 5) {

    }
  }

}
