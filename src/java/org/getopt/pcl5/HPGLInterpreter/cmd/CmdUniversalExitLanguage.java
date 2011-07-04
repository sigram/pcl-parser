package org.getopt.pcl5.HPGLInterpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public class CmdUniversalExitLanguage extends CommandHPGL {

  public CmdUniversalExitLanguage(PrinterState printerState) {
    super(printerState);
  }

  char _cmd;

  protected int readNum(InputStream in) throws IOException {
    char c = (char) in.read();
    String number = "";

    while (c == '+' || c == '-' || Character.isDefined(c))
      number += c;

    // TODO: state class variable, but I can't return 2 values
    _cmd = c;

    return Integer.parseInt(number);
  }

  public boolean execute(char cmd, InputStream in) throws IOException {
    if (cmd == '%') {
      int param = readNum(in);

      if ((param == -12345) && (_cmd == 'X')) {
        // _printerState.reset();
        _printerState.setActiveLanguage(PrinterState.Language.PJL);

        return true;
      }
    }

    return false;
  }

  public boolean execute(String[] cmd) {
    // TODO Auto-generated method stub
    return false;
  }

  protected void execute(InputStream in) throws IOException {
    // TODO Auto-generated method stub

  }
}
