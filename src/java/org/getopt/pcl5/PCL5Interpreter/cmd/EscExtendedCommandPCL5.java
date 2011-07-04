/*
 * Created on 2004-09-17
 *
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

/**
 * Base class to handle extended Esc command, extended Esc command is command
 * that starts with Esc (
 */
public class EscExtendedCommandPCL5 extends BaseCommandPCL5 {
  /**
   * @param printerState
   */
  public EscExtendedCommandPCL5(PrinterState printerState) {
    super(printerState);
  }

  /**
   * Main method class, it should check if can interpret command and interpret
   * one
   * 
   * @param family
   *          Command family (first char after Esc)(non letter and non digit)
   * @param subfamily
   *          Additional parameter (second char after Esc)(small letter)
   * @param parameter
   *          Numeric parameter for command (if missing set to 0)
   * @param cmd
   *          Command code
   * @param in
   *          Input stream for reading parameters
   * 
   * @return should return true if handled else false
   * 
   * @throws IOException
   */
  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    return false;
  }

  /**
   * Helper method to read number of bytes from stream
   * 
   * @param in
   *          Input stream
   * @param size
   *          Number of bytes to read
   * @return Byte array of given size filled from stream
   * @throws IOException
   */
  protected byte[] loadFromStream(InputStream in, int size) throws IOException {
    byte[] data = new byte[size];

    in.read(data);

    return data;
  }
}
