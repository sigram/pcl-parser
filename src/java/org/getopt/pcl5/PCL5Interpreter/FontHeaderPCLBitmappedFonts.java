package org.getopt.pcl5.PCL5Interpreter;

import java.io.IOException;
import java.io.InputStream;

public class FontHeaderPCLBitmappedFonts extends FontHeaderCommon {
  private final static int DESCRIPTOR_SIZE = 64;

  private int _firstCode;
  private int _lastCode;
  private int _pitchExtended;
  private int _heightExtended;
  private int _capHeight;

  public FontHeaderPCLBitmappedFonts(int numOfBytes, InputStream in)
          throws IOException {
    super(numOfBytes, in);

    _descriptorSize = numOfBytes;
    _headerFormat = 0;

    _firstCode = 256 * in.read() + in.read();
    _lastCode = 256 * in.read() + in.read();
    _pitchExtended = in.read();
    _heightExtended = in.read();
    _capHeight = 256 * in.read() + in.read();
    byte fontInfo = (byte) in.read();
    _nativeFont = (fontInfo & 0x80) == 0;
    if ((fontInfo & 0x7F) != 0)
      _vendorInitial = (char) (fontInfo & 0x7F);
    else
      _vendorInitial = ' ';

    _fontNumber = readFontNumber(in);
    _fontName = readFontName(in);
    _copyright = readCopyright(in, numOfBytes - DESCRIPTOR_SIZE); // (optional)
  }

  public int getCapHeight() {
    return _capHeight;
  }

  public int getFirstCode() {
    return _firstCode;
  }

  public int getLastCode() {
    return _lastCode;
  }

  public int getPitchExtended() {
    return _pitchExtended;
  }

  public int getHeightExtended() {
    return _heightExtended;
  }

  public boolean isScalable() {
    return false;
  }
}
