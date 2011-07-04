package org.getopt.pcl5.PCL5Interpreter;

import java.io.IOException;
import java.io.InputStream;

public class FontHeaderResolutionSpecifiedBitmapped extends FontHeaderCommon {
  private final static int DESCRIPTOR_SIZE = 68;

  private int _firstCode;
  private int _lastCode;
  private int _pitchExtended;
  private int _heightExtended;
  private int _capHeight;
  private int _xResolution;
  private int _yResolution;

  public FontHeaderResolutionSpecifiedBitmapped(int numOfBytes, InputStream in)
          throws IOException {
    super(numOfBytes, in);

    _descriptorSize = numOfBytes;
    _headerFormat = 20;

    _firstCode = 256 * in.read() + in.read();
    _lastCode = 256 * in.read() + in.read();
    _pitchExtended = in.read();
    _heightExtended = in.read();
    _capHeight = 256 * in.read() + in.read();
    _nativeFont = in.read() == 0;
    _fontNumber = readFontNumber(in);
    _fontName = readFontName(in);
    _xResolution = 256 * in.read() + in.read();
    _yResolution = 256 * in.read() + in.read();
    _copyright = readCopyright(in, numOfBytes - DESCRIPTOR_SIZE); // (optional)
  }

  public static int getDESCRIPTOR_SIZE() {
    return DESCRIPTOR_SIZE;
  }

  public int getCapHeight() {
    return _capHeight;
  }

  public int getFirstCode() {
    return _firstCode;
  }

  public int getHeightExtended() {
    return _heightExtended;
  }

  public int getLastCode() {
    return _lastCode;
  }

  public int getPitchExtended() {
    return _pitchExtended;
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
