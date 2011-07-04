package org.getopt.pcl5.HPGLInterpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public abstract class CommandHPGL {

  PrinterState _printerState;
  String _command;
  PrinterState.HPGLState _hpgl;

  public CommandHPGL(PrinterState printerState) {
    _printerState = printerState;
    _hpgl = _printerState.getHPGLState();
  }

  public boolean execute(String cmd, InputStream in) throws IOException {
    if (cmd.equalsIgnoreCase(_command)) {
      execute(in);

      return true;
    }

    return false;
  }

  abstract protected void execute(InputStream in) throws IOException;

  protected String readInput(InputStream in) throws IOException {
    StringBuilder sb = new StringBuilder();

    int data = in.read();

    while (data != -1 && (char) data != ';') {
      sb.append((char) data);
      data = in.read();
    }

    return sb.toString();
  }

  /**
   * Can return command code in derived class, if returned class will be put in
   * hashtable
   * 
   * @return code for command, null means not set
   */
  public String getCommandString() {
    return _command;
  }
}
