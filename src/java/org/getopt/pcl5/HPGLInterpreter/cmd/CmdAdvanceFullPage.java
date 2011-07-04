package org.getopt.pcl5.HPGLInterpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public class CmdAdvanceFullPage extends CommandHPGL {

  public CmdAdvanceFullPage(PrinterState printerState) {
    super(printerState);
    _command = "PG";
  }

  protected void execute(InputStream in) throws IOException {
    _printerState.trace(this, "Not implemented");
  }

}
