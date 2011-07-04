package org.getopt.pcl5.HPGLInterpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public class CmdEscCommand extends EscCommandHPGL {

  public CmdEscCommand(PrinterState printerState) {
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
      } else if (_cmd == 'A') {
        _printerState.setActiveLanguage(PrinterState.Language.PCL5);

        return true;
      }
    }

    return false;
  }
}
