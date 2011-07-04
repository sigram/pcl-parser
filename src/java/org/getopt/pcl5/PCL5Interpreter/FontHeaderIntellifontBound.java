package org.getopt.pcl5.PCL5Interpreter;

import java.io.IOException;
import java.io.InputStream;

public class FontHeaderIntellifontBound extends FontHeaderCommon {
  private final static int DESCRIPTOR_SIZE = 80;

  private int _firstCode;
  private int _lastCode;
  private int _pitchExtended;
  private int _heightExtended;
  private int _capHeight;
  private int _scaleFactor;
  private int _xResolution;
  private int _yResolution;
  private int _masterUnderlinePosition;
  private int _masterUnderlineThickness;
  private int _orThreshold;
  private int _globalItalicAngle;
  private int _globalIntellifontDataSize;
  private byte[] _globalIntellifontData;
  private int _reserved2;
  private int _checksum;

  public FontHeaderIntellifontBound(int numOfBytes, InputStream in)
          throws IOException {
    super(numOfBytes, in);

    _descriptorSize = numOfBytes;
    _headerFormat = 10;

    _firstCode = 256 * in.read() + in.read();
    _lastCode = 256 * in.read() + in.read();
    _pitchExtended = in.read();
    _heightExtended = in.read();
    _capHeight = 256 * in.read() + in.read();
    _nativeFont = in.read() == 0;
    _fontNumber = readFontNumber(in);
    _fontName = readFontName(in);
    _scaleFactor = 256 * in.read() + in.read();
    _xResolution = 256 * in.read() + in.read();
    _yResolution = 256 * in.read() + in.read();
    _masterUnderlinePosition = 256 * in.read() + in.read();
    _masterUnderlineThickness = 256 * in.read() + in.read();
    _orThreshold = 256 * in.read() + in.read();
    _globalItalicAngle = 256 * in.read() + in.read();
    _globalIntellifontDataSize = 256 * in.read() + in.read();
    _globalIntellifontData = new byte[_globalIntellifontDataSize];
    in.read(_globalIntellifontData);
    _copyright = readCopyright(in, numOfBytes - DESCRIPTOR_SIZE); // (optional)
    _reserved2 = in.read();
    _checksum = in.read();
  }

  public static int getDESCRIPTOR_SIZE() {
    return DESCRIPTOR_SIZE;
  }

  public int getCapHeight() {
    return _capHeight;
  }

  public int getChecksum() {
    return _checksum;
  }

  public int getFirstCode() {
    return _firstCode;
  }

  public byte[] getGlobalIntellifontData() {
    return _globalIntellifontData;
  }

  public int getGlobalIntellifontDataSize() {
    return _globalIntellifontDataSize;
  }

  public int getGlobalItalicAngle() {
    return _globalItalicAngle;
  }

  public int getHeightExtended() {
    return _heightExtended;
  }

  public int getLastCode() {
    return _lastCode;
  }

  public int getMasterUnderlinePosition() {
    return _masterUnderlinePosition;
  }

  public int getMasterUnderlineThickness() {
    return _masterUnderlineThickness;
  }

  public int getOrThreshold() {
    return _orThreshold;
  }

  public int getPitchExtended() {
    return _pitchExtended;
  }

  public int getScaleFactor() {
    return _scaleFactor;
  }

  public int getXResolution() {
    return _xResolution;
  }

  public int getYResolution() {
    return _yResolution;
  }

  public boolean isScalable() {
    return true;
  }

}
