/*
 * Created on 2004-09-12
 *
 */
package org.getopt.pcl5.PCL5Interpreter;

import java.io.*;

import org.getopt.pcl5.IPrint;

/**
 * PCL parser interface
 */
public interface IParse {
  /**
   * Main parser
   * 
   * @param in
   *          input stream
   * @param print
   *          callback interface to rasterizer
   */
  void parse(InputStream in, IPrint print) throws IOException;
}
