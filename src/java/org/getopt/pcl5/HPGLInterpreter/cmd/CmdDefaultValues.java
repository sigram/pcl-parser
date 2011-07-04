package org.getopt.pcl5.HPGLInterpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public class CmdDefaultValues extends CommandHPGL {

  public CmdDefaultValues(PrinterState printerState) {
    super(printerState);
  }

  protected void execute(InputStream in) throws IOException {
    _printerState.resetHPGLDefaultValues();
  }
}
