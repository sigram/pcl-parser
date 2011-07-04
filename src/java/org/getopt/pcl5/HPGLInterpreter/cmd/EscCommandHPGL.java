package org.getopt.pcl5.HPGLInterpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public class EscCommandHPGL {
  PrinterState _printerState;

  public EscCommandHPGL(PrinterState printerState) {
    _printerState = printerState;
  }

  public boolean execute(char cmd, InputStream in) throws IOException {
    return false;
  }

}
