/*
 * Created on 2004-09-17
 *
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.IPrinterState;
import org.getopt.pcl5.PrinterState;

/**
 * Base class to handle Esc command Main reason for this class is to create
 * dinstinct type of commands
 */
public class EscCommandPCL5 extends BaseCommandPCL5 {
  /**
   * @param printerState
   */
  public EscCommandPCL5(PrinterState printerState) {
    super(printerState);
  }

  /**
   * Main method class, it should check in can interpret command and interpret
   * one
   * 
   * @param cmd
   *          Command code (after Esc ( )
   * @param in
   *          Input stream for reading parameters
   * 
   * @return should return true if handled else false
   * 
   * @throws IOException
   */
  public boolean execute(char cmd, InputStream in) throws IOException {
    return false;
  }
}
