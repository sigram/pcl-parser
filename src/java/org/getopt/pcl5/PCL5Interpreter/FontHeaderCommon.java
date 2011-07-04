package org.getopt.pcl5.PCL5Interpreter;

import java.io.IOException;
import java.io.InputStream;

public abstract class FontHeaderCommon {

  public final static int PCL_BITMAPPED = 0;
  public final static int INTELLIFONT_BOUND = 10;
  public final static int INTELLIFONT_UNBOUND = 11;
  public final static int RESOLUTION_SPECIFIED_BITMAPPED = 20;
  public final static int TRUE_TYPE = 15;
  private static final int RADIX_DOTS = 4;
  private static final int BASE_FONT_DPI = 72;

  class Posture {
    private static final int UPRIGHT = 0;
    private static final int ITALIC = 1;
    private static final int ALTERNATE_ITALIC = 2;
    private static final int RESERVED = 3;
  }

  class Appearance {
    private static final int NORMAL = 0;
    private static final int CONDENSED = 1;
    private static final int COMPRESSED = 2;
    private static final int EXTRA_COMPRESSED = 3;
    private static final int ULTRA_COMPRESSED = 4;
    private static final int RESERVED = 5;
    private static final int EXTENDED = 6;
    private static final int EXTRA_EXTENDED = 7;
  }

  class SerifStyle {
    private static final int SANS_SERIF_SQUARE = 0;
    private static final int SANS_SERIF_ROUND = 1;
    private static final int SERIF_LINE = 2;
    private static final int SERIF_TRIANGLE = 3;
    private static final int SERIF_SWATH = 4;
    private static final int SERIF_BLOCK = 5;
    private static final int SERIF_BRACKET = 6;
    private static final int ROUNDED_BRACKET = 7;
    private static final int FLAIR_SERIF = 8;
    private static final int SCRIPT_NONCONNECTING = 9;
    private static final int SCRIPT_JOINING = 10;
    private static final int SCRIPT_CALLIGRAPHIC = 11;
    private static final int SCRIPT_BROKEN_LETTER = 12;
    private static final int SANS_SERIF = 64;
    private static final int SERIF = 128;
  }

  protected int _descriptorSize;
  protected int _headerFormat;
  protected int _fontType;
  protected int _style;
  protected int _reserved;
  protected int _baselinePosition;
  protected int _cellWidth;
  protected int _cellHeight;
  protected int _orientation;
  protected int _spacing;
  protected int _symbolSet;
  protected int _pitch;
  protected int _height;
  protected int _xHeight;
  protected int _widthType;
  protected int _strokeWeight;
  protected int _typeface;
  protected int _serifStyle;
  protected int _quality;
  protected int _placement;
  protected int _underlinePosition;
  protected int _underlineThickness;
  protected int _textHeight;
  protected int _textWidth;

  protected boolean _nativeFont;
  protected int _fontNumber;
  protected String _fontName;
  protected String _copyright;
  protected char _vendorInitial;

  protected int _resolution = 300; // dpi

  protected FontHeaderCommon(int numOfBytes, InputStream in) throws IOException {
    _fontType = in.read();
    int styleMSB = in.read();
    _reserved = in.read();
    _baselinePosition = 256 * in.read() + in.read();
    _cellWidth = 256 * in.read() + in.read();
    _cellHeight = 256 * in.read() + in.read();
    _orientation = in.read();
    _spacing = in.read();
    _symbolSet = 256 * in.read() + in.read();
    _pitch = 256 * in.read() + in.read();
    _height = 256 * in.read() + in.read();
    _xHeight = 256 * in.read() + in.read();
    _widthType = in.read();
    _style = 256 * styleMSB + in.read();
    _strokeWeight = in.read();
    _typeface = in.read() + 256 * in.read();
    _serifStyle = in.read();
    _quality = in.read();
    _placement = in.read();
    // TODO: signed byte, convert to signed
    _underlinePosition = in.read(); // (Distance)
    _underlineThickness = in.read(); // Height
    _textHeight = 256 * in.read() + in.read();
    _textWidth = 256 * in.read() + in.read();
  }

  protected String readCopyright(InputStream in, int length) throws IOException {
    String copyright = "";

    while (length > 0) {
      copyright += (char) in.read();
      length--;
    }

    return copyright;
  }

  protected int readFontNumber(InputStream in) throws IOException {
    String hexNo = "";

    for (int i = 0; i < 3; i++) {
      char c = (char) in.read();
      if (c != 0)
        hexNo += c;
    }

    if (hexNo.length() == 0)
      return 0;

    return Integer.parseInt(hexNo, 16);
  }

  protected String readFontName(InputStream in) throws IOException {
    String name = "";

    for (int i = 0; i < 16; i++) {
      char c = (char) in.read();
      if (c != 0)
        name += c;
    }

    return name;
  }

  public int getBaselinePosition() {
    return _baselinePosition;
  }

  public String getCopyright() {
    return _copyright;
  }

  public String getFontName() {
    return _fontName;
  }

  public int getFontNumber() {
    return _fontNumber;
  }

  public int getFontType() {
    return _fontType;
  }

  public int getStyle() {
    return _style;
  }

  public int getSymbolSet() {
    return _symbolSet;
  }

  public int getTextHeight() {
    return _textHeight;
  }

  public int getTextWidth() {
    return _textWidth;
  }

  public int getUnderlinePosition() {
    return _underlinePosition;
  }

  public int getUnderlineThickness() {
    return _underlineThickness;
  }

  public int getXHeight() {
    return _xHeight;
  }

  public boolean isNativeFont() {
    return _nativeFont;
  }

  public int getOrientation() {
    return _orientation;
  }

  public char getVendorInitial() {
    return _vendorInitial;
  }

  public boolean isItalic() {
    int posture = _style & 0x3;

    return posture == Posture.ITALIC || posture == Posture.ALTERNATE_ITALIC;
  }

  public boolean isCondensed() {
    int appearance = (_style & 0x1C) >> 2;

    return appearance == Appearance.COMPRESSED
            || appearance == Appearance.CONDENSED
            || appearance == Appearance.EXTRA_COMPRESSED
            || appearance == Appearance.ULTRA_COMPRESSED;
  }

  public boolean isExtended() {
    int appearance = (_style & 0x1C) >> 2;

    return appearance == Appearance.EXTENDED
            || appearance == Appearance.EXTRA_EXTENDED;
  }

  public boolean isBold() {
    return _strokeWeight > 3;
  }

  public boolean isSansSerif() {
    return _serifStyle == SerifStyle.SANS_SERIF
            || _serifStyle == SerifStyle.SANS_SERIF_ROUND
            || _serifStyle == SerifStyle.SANS_SERIF_SQUARE;
  }

  /**
   * 
   * @return font pitch in dpi
   */
  public int getPitch() {
    return (_resolution * RADIX_DOTS) / _pitch;
  }

  public int getHeight() {
    return (BASE_FONT_DPI * _height) / (_resolution * RADIX_DOTS);
  }

  public abstract boolean isScalable();
}
