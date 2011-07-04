package org.getopt.pcl5;

import java.util.*;

public class PageSize {
  final public static int Letter = 2;
  final public static int LegalList1 = 3;
  final public static int LEDGER = 6;
  final public static int EXECUTIVE = 1;
  final public static int A4 = 26;
  final public static int A3 = 27;
  final public static int COM10 = 81;
  final public static int MONARCH = 80;
  final public static int C5 = 91;
  final public static int B5 = 100;
  final public static int DL = 90;

  final public static int Portrait = 0;
  final public static int Landscape = 1;
  final public static int ReversePortrait = 2;
  final public static int ReverseLandscape = 3;

  private int _orientation;
  private int _size;
  private PageDimensions _selectedSize;

  static class PageDimensions {
    // pages parameters are for 300 DPI we are using internally 7200 DPI
    final private static int RESOLUTION_MULT = 7200 / 300;
    private int _physicalPageWidth;
    private int _physicalPageLength;
    private int _logicalPageWidth;
    private int _maxLogicalPageLength;
    private int _distSizeToLogicalPage;
    private int _distTopToLogicalPage;
    private int _distSideToPrintableArea;
    private int _distTopToPrintableArea;

    public PageDimensions(int physicalPageWidth, int physicalPageLength,
            int logicalPageWidth, int maxLogicalPageLength,
            int distSizeToLogicalPage, int distTopToLogicalPage,
            int distSideToPrintableArea, int distTopToPrintableArea) {
      _physicalPageWidth = physicalPageWidth * RESOLUTION_MULT;
      _physicalPageLength = physicalPageLength * RESOLUTION_MULT;
      _logicalPageWidth = logicalPageWidth * RESOLUTION_MULT;
      _maxLogicalPageLength = maxLogicalPageLength * RESOLUTION_MULT;
      _distSizeToLogicalPage = distSizeToLogicalPage * RESOLUTION_MULT;
      _distTopToLogicalPage = distTopToLogicalPage * RESOLUTION_MULT;
      _distSideToPrintableArea = distSideToPrintableArea * RESOLUTION_MULT;
      _distTopToPrintableArea = distTopToPrintableArea * RESOLUTION_MULT;
    }

    /**
     * 
     * @return distance from edge to printable area in 7200 dpi
     */
    public int getDistSideToPrintableArea() {
      return _distSideToPrintableArea;
    }

    /**
     * 
     * @return distance from edge to logical page in 7200 dpi
     */
    public int getDistSizeToLogicalPage() {
      return _distSizeToLogicalPage;
    }

    public int getDistTopToLogicalPage() {
      return _distTopToLogicalPage;
    }

    public int getDistTopToPrintableArea() {
      return _distTopToPrintableArea;
    }

    /**
     * 
     * @return logical page width in 7200 dpi units
     */
    public int getLogicalPageWidth() {
      return _logicalPageWidth;
    }

    public int getMaxLogicalPageLength() {
      return _maxLogicalPageLength;
    }

    public int getPhysicalPageLength() {
      return _physicalPageLength;
    }

    public int getPhysicalPageWidth() {
      return _physicalPageWidth;
    }
  }

  final static HashMap _portaitPages = new HashMap();
  final static HashMap _landscapePages = new HashMap();

  static {
    // default if for 300 dpi
    // because we are using internally 7200 dpi all paramteres are multiplied by
    // 24
    // inside page dimensions class
    _portaitPages.put(new Integer(Letter), new PageDimensions(2550, 3300, 2400,
            3300, 75, 0, 50, 150));

    _portaitPages.put(new Integer(LegalList1), new PageDimensions(2550, 4200,
            2400, 4200, 75, 0, 50, 150));

    _portaitPages.put(new Integer(LEDGER), new PageDimensions(3300, 5100, 3150,
            5100, 75, 0, 50, 150));

    _portaitPages.put(new Integer(EXECUTIVE), new PageDimensions(2175, 3150,
            2025, 3150, 75, 0, 50, 150));

    _portaitPages.put(new Integer(A4), new PageDimensions(2480, 3507, 2338,
            3507, 71, 0, 50, 150));

    _portaitPages.put(new Integer(A3), new PageDimensions(3507, 4960, 3365,
            4960, 71, 0, 50, 150));

    _portaitPages.put(new Integer(COM10), new PageDimensions(1237, 2850, 1087,
            2850, 75, 0, 50, 150));

    _portaitPages.put(new Integer(MONARCH), new PageDimensions(1162, 2250,
            1012, 2250, 75, 0, 50, 150));

    _portaitPages.put(new Integer(C5), new PageDimensions(1913, 2704, 1771,
            2704, 71, 0, 50, 150));

    _portaitPages.put(new Integer(B5), new PageDimensions(2078, 2952, 1936,
            2952, 71, 0, 50, 150));

    _portaitPages.put(new Integer(DL), new PageDimensions(1299, 2598, 1157,
            2598, 71, 0, 50, 150));

    _landscapePages.put(new Integer(Letter), new PageDimensions(3300, 2550,
            3180, 2550, 60, 0, 50, 150));

    _landscapePages.put(new Integer(LegalList1), new PageDimensions(4200, 2550,
            4080, 2550, 60, 0, 50, 150));

    _landscapePages.put(new Integer(LEDGER), new PageDimensions(5100, 3300,
            4980, 3300, 60, 0, 50, 150));

    _landscapePages.put(new Integer(EXECUTIVE), new PageDimensions(3150, 2175,
            3030, 2175, 60, 0, 50, 150));

    _landscapePages.put(new Integer(A4), new PageDimensions(3507, 2480, 3389,
            2480, 59, 0, 50, 150));

    _landscapePages.put(new Integer(A3), new PageDimensions(4960, 3507, 4842,
            3507, 59, 0, 50, 150));

    _landscapePages.put(new Integer(COM10), new PageDimensions(2850, 1237,
            2730, 1237, 60, 0, 50, 150));

    _landscapePages.put(new Integer(MONARCH), new PageDimensions(2250, 1162,
            2130, 1162, 60, 0, 50, 150));

    _landscapePages.put(new Integer(C5), new PageDimensions(2704, 1913, 2586,
            1913, 59, 0, 50, 150));

    _landscapePages.put(new Integer(B5), new PageDimensions(2952, 2078, 2834,
            2078, 59, 0, 50, 150));

    _landscapePages.put(new Integer(DL), new PageDimensions(2598, 1299, 2480,
            1299, 59, 0, 50, 150));
  }

  public PageSize() {
  }

  public void setOrientation(int orientation) {
    _orientation = orientation;
  }

  public int getOrientation() {
    return _orientation;
  }

  public void setSize(int size) {
    _size = size;
  }

  private PageDimensions getDimensions() {
    if (_orientation == Portrait)
      return (PageDimensions) _portaitPages.get(new Integer(_size));

    return (PageDimensions) _landscapePages.get(new Integer(_size));
  }

  public int getPhysicalPageWidth() {
    return getDimensions().getPhysicalPageWidth();
  }

  public int getPhysicalPageLength() {
    return getDimensions().getPhysicalPageLength();
  }

  public int getPrintableAreaWidth() {
    return getPhysicalPageWidth() - 2
            * getDimensions().getDistSideToPrintableArea();
  }

  public int getPrintableAreaLength() {
    return getPhysicalPageLength() - 2
            * getDimensions().getDistSideToPrintableArea();
  }

}
