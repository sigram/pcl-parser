package org.getopt.pcl5.PCL5Interpreter;

import java.io.IOException;
import java.io.InputStream;

public class FontHeaderTrueType extends FontHeaderCommon {
  private final static int DESCRIPTOR_SIZE = 72;

  private int _firstCode;
  private int _lastCode;
  private int _pitchExtended;
  private int _heightExtended;
  private int _capHeight;
  private int _scaleFactor;
  private int _masterUnderlinePosition;
  private int _masterUnderlineThickness;
  private int _fontScalingTechnology;
  private int _variety;
  private byte[] _additionalData;
  private byte[] _segmentFontData;
  private int _reserved2;
  private int _checksum;

  public FontHeaderTrueType(int numOfBytes, InputStream in) throws IOException {
    super(numOfBytes, in);

    _descriptorSize = numOfBytes;
    _headerFormat = 15;

    _firstCode = 256 * in.read() + in.read();
    _lastCode = 256 * in.read() + in.read();
    _pitchExtended = in.read();
    _heightExtended = in.read();
    _capHeight = 256 * in.read() + in.read();
    _nativeFont = in.read() == 0;
    _fontNumber = readFontNumber(in);
    _fontName = readFontName(in);
    _scaleFactor = 256 * in.read() + in.read();
    _masterUnderlinePosition = 256 * in.read() + in.read();
    _masterUnderlineThickness = 256 * in.read() + in.read();
    _fontScalingTechnology = in.read();
    _variety = in.read();
    int additionalDataSize = numOfBytes - 72 - 2;
    if (additionalDataSize > 0) {
      _additionalData = new byte[additionalDataSize];
      in.read(_additionalData);
    }
    _segmentFontData = new byte[1];
    in.read(_segmentFontData);
    _reserved2 = in.read();
    _checksum = in.read();
  }

  public static int getDESCRIPTOR_SIZE() {
    return DESCRIPTOR_SIZE;
  }

  public byte[] getAdditionalData() {
    return _additionalData;
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

  public int getFontScalingTechnology() {
    return _fontScalingTechnology;
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

  public int getPitchExtended() {
    return _pitchExtended;
  }

  public int getScaleFactor() {
    return _scaleFactor;
  }

  public byte[] getSegmentFontData() {
    return _segmentFontData;
  }

  public int getVariety() {
    return _variety;
  }

  public boolean isScalable() {
    return true;
  }

}
