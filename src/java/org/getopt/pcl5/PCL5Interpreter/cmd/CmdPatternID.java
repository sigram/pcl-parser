/**
 * <b>Command ESC * c # G</b>
 * The Pattern ID command identifies the specific shading, cross-hatch, 
 * or user-defined pattern to be used when filling a rectangular area.
 * 
 * # = 	  1 thru  2 = 1- 2% shade 			# = 1 - Pattern #1
 * 		  3 thru 10 = 3-10% shade 				2 - Pattern #2
 * 		 11 thru 20 = 11-20% shade 				3 - Pattern #3
 * 		 21 thru 35 = 21-35% shade 				4 - Pattern #4
 * 		 36 thru 55 = 36-55% shade 				5 - Pattern #5
 * 		 56 thru 80 = 56-80% shade 				6 - Pattern #6
 * 		 81 thru 99 = 81-99% shade
 * 		100 = 100% shade
 * 
 * <b>Default</b> = 0 (no pattern)
 * <b>Range</b>   = 0 - 32767 (values outside the range are ignored)
 *
 * <b>Notes</b>
 * The value field (#) identifies the level of shading, the cross-hatch
 * pattern, or the user-defined pattern.
 * There are eight HP defined shading patterns defined within the PCL
 * language. To specify one of the eight shading patterns, use any value
 * within the value field range for the desired shade. 
 * For example, to select the 56-80% shade use a value of 56, or 80, or any value in between such as 73.
 *  
 * <i>implemented Sep 22, 2005</i>
 * 
 */
package org.getopt.pcl5.PCL5Interpreter.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.getopt.pcl5.PrinterState;

public class CmdPatternID extends EscExtendedCommandPCL5 {

  public CmdPatternID(PrinterState printerState) {
    super(printerState);
  }

  public boolean execute(char family, char subfamily, String parameter,
          char cmd, InputStream in) throws IOException {
    if (family == '*' && subfamily == 'c' && cmd == 'G') {
      int param = Integer.parseInt((parameter));
      int pattern = 0;

      if (param >= 1 && param <= 2)
        pattern = 1;
      else if (param >= 3 && param <= 10)
        pattern = 2;
      else if (param >= 11 && param <= 20)
        pattern = 3;
      else if (param >= 21 && param <= 35)
        pattern = 4;
      else if (param >= 36 && param <= 55)
        pattern = 5;
      else if (param >= 56 && param <= 80)
        pattern = 6;
      else if (param >= 81 && param <= 99)
        pattern = 7;
      else
        pattern = 8;

      _printerState.setPatternID(pattern);
      return true;
    }

    return false;
  }
}
