package org.getopt.pcl5.HPGLInterpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public class CmdReplot extends CommandHPGL {

  public CmdReplot(PrinterState printerState) {
    super(printerState);
    _command = "RP";
  }

  protected void execute(InputStream in) throws IOException {
    _printerState.trace(this, "Not implemented");
  }

}
