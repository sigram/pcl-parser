/*
 * Created on 2004-09-12
 *
 */
package org.getopt.pcl5.PCL5Interpreter;

import java.awt.image.BufferedImage;

import org.getopt.pcl5.IPrinterState;

/**
 * Callback interface, called when something to print happens Should be
 * implemented by rasterizer
 * 
 * Every parameter is scaled in 72 DPI
 * 
 */
public interface IPrint {
  /**
   * Called when new page
   * 
   */
  void newPage();

  /**
   * Called when page size changed
   * 
   * @param w
   *          New width of page, in 72 DPI
   * @param h
   *          New height of page, in 72 DPI
   */
  void pageSize(float w, float h);

  /**
   * Called after every margins change and after each new page
   * 
   * @param top
   *          top margin, in 72 DPI
   * @param bottom
   *          bottom margin, in 72 DPI
   * @param left
   *          left margin, in 72 DPI
   * @param right
   *          right margin, in 72 DPI
   */
  void newMargins(float top, float bottom, float left, float right);

  /**
   * Called on beginning of processing, before any other method from this
   * interface is called
   * 
   */
  void processingStart();

  /**
   * Called on end of processing, after last method from this interface was
   * called
   * 
   */
  void processingEnd();

  /**
   * Called when text should be printed, text is limited to end of line (CR) or
   * to change in pronter state (ie font)
   * 
   * @param x
   *          Print location X in 72 DPI
   * @param y
   *          Print location Y in 72 DPI
   * @param w
   *          Width of text (bound box) in 72 DPI
   * @param h
   *          Height of text in (bound box) 72 DPI
   * @param text
   *          Text to print
   * @param state
   *          Interface to query state of printer
   */
  void printText(float x, float y, float w, float h, String text,
          IPrinterState state);

  /**
   * Called when bitmap should be printed, bitmap is always complete method is
   * called when graphics mode is terminated
   * 
   * @param x
   *          X location of image in 72 DPI
   * @param y
   *          Y location of image in 72 DPI
   * @param w
   *          Width of bitmap in (bound box) 72 DPI
   * @param h
   *          Height of bitmap in (bound box) 72 DPI
   * @param image
   *          Image for output
   * @param state
   *          Interface to query state of printer
   */
  void printBitmap(float x, float y, float w, float h, BufferedImage image,
          IPrinterState state);

  /**
   * Called on condition like command without meaning for interpreter like BEL
   * 
   * @param command
   *          class
   * @param message
   *          command name
   */
  void trace(Object command, String message);

  /**
   * Called when command parameters are incorrect ie out of range
   * 
   * @param command
   *          class that detected problem
   * @param message
   *          kind of problem
   */
  void assertCondition(Object command, String message);

  /**
   * Called when found command that shouldn't be implemented ie CAN Cancel line
   * 
   * @param command
   *          class that detected problem
   * @param message
   *          kind of problem
   */
  void notImplemented(Object command, String message);
}
