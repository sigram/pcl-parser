/*
 * Created on 2004-09-14
 *
 */
package org.getopt.pcl5.PCL5Interpreter;

import java.util.*;

/**
 * Printer typefaces
 */
public class PrinterTypeface {
  static final int TF_ROMAN = 0;
  static final int TF_SANS_SERIF = 1;
  static final int TF_COURIER = 2;
  static final int TF_PRESTIGE = 3;
  static final int TF_SCRIPT = 4;
  static final int TF_OCR_B = 5;
  static final int TF_OCR_A = 6;
  static final int TF_ORATOR = 7;
  static final int TF_ORATOR_S = 8;
  static final int TF_SCRIPT_C = 9;
  static final int TF_ROMAN_T = 10;
  static final int TF_SANS_SERIF_H = 11;
  static final int TF_SV_BUSABA = 30;
  static final int TF_SV_JITTRA = 31;

  static private Hashtable typefaceMap;

  static String getFontTypeface(int typeface) {
    if (typefaceMap == null)
      loadTypefaceMap();

    String result = (String) typefaceMap.get(new Integer(typeface));

    if (result == null)
      result = "Default";

    return result;
  }

  // TODO: for next release, load params from config file
  static private void loadTypefaceMap() {
    typefaceMap = new Hashtable();

    typefaceMap.put(new Integer(TF_ROMAN), "Serif");
    typefaceMap.put(new Integer(TF_ROMAN_T), "Serif");
    typefaceMap.put(new Integer(TF_SANS_SERIF), "SansSerif");
    typefaceMap.put(new Integer(TF_SANS_SERIF_H), "SansSerif");
    typefaceMap.put(new Integer(TF_COURIER), "Monospaced");
  }
}
