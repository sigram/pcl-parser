package org.getopt.pcl5.HPGLInterpreter.cmd;

import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public class CmdUnknownHPGL extends CommandHPGL {

  public CmdUnknownHPGL(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(String cmd, InputStream in) {
    _printerState.trace(this.toString(), "Unknown HPGL command " + cmd);

    return false;
  }

  protected void execute(InputStream in) {
  }

}
