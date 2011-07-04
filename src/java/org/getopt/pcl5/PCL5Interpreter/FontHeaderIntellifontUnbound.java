package org.getopt.pcl5.PCL5Interpreter;

import java.io.IOException;
import java.io.InputStream;

public class FontHeaderIntellifontUnbound extends FontHeaderCommon {
  private final static int DESCRIPTOR_SIZE = 88;

  private int _reserved2;
  private int _numberOfCharacters;
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
  private byte[] _characterComplement;
  private int _globalIntellifontDataSize;
  private byte[] _globalIntellifontData;
  private int _reserved3;
  private int _checksum;

  public FontHeaderIntellifontUnbound(int numOfBytes, InputStream in)
          throws IOException {
    super(numOfBytes, in);

    _descriptorSize = numOfBytes;
    _headerFormat = 11;

    _reserved2 = 256 * in.read() + in.read();
    _numberOfCharacters = 256 * in.read() + in.read();
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
    _characterComplement = new byte[8];
    in.read(_characterComplement);
    _globalIntellifontDataSize = 256 * in.read() + in.read();
    _globalIntellifontData = new byte[_globalIntellifontDataSize];
    in.read(_globalIntellifontData);
    _copyright = readCopyright(in, numOfBytes - DESCRIPTOR_SIZE); // (optional)
    _reserved3 = in.read();
    _checksum = in.read();
  }

  public static int getDESCRIPTOR_SIZE() {
    return DESCRIPTOR_SIZE;
  }

  public int getCapHeight() {
    return _capHeight;
  }

  public byte[] getCharacterComplement() {
    return _characterComplement;
  }

  public int getChecksum() {
    return _checksum;
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

  public int getMasterUnderlinePosition() {
    return _masterUnderlinePosition;
  }

  public int getMasterUnderlineThickness() {
    return _masterUnderlineThickness;
  }

  public int getNumberOfCharacters() {
    return _numberOfCharacters;
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
