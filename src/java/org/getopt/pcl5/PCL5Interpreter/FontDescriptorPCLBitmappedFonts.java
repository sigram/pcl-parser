/*
 * Created on 2005-09-22 by piotrm
 *
 */
package org.getopt.pcl5.PCL5Interpreter;

import java.io.IOException;
import java.io.InputStream;

public class FontDescriptorPCLBitmappedFonts {
  public class FontType {
    public final static int LaserJetFamily = 4;
    public final static int IntellifontScalable = 10;
    public final static int TrueTypeScalable = 15;
  }

  public class FontClass {
    public final static int Bitmap = 1;
    public final static int CompressedBitmap = 2;
    public final static int Contour = 3;
    public final static int CompoundContour = 4;
    public final static int TrueTypeScalable = 5;
  }

  // private int _format;
  // private boolean _continuation;
  private int _descriptorSize;
  private int _class;
  private int _orientation;
  private int _reserved;
  private int _leftOffset;
  private int _topOffset;
  private int _characterWidth;
  private int _characterHeight;
  private int _deltaX;
  private byte[] _rasterCharacterData;

  public FontDescriptorPCLBitmappedFonts(int numOfBytes, InputStream in)
          throws IOException {
    // _format = 4; // LaserJet Family
    _descriptorSize = in.read(); // The descriptor size used by the HP LaserJet
                                 // printer family for bitmap fonts is 14.
    _class = in.read();
    _orientation = in.read();
    _reserved = in.read();
    _leftOffset = 256 * in.read() + in.read();
    _topOffset = 256 * in.read() + in.read();
    _characterWidth = 256 * in.read() + in.read();
    _characterHeight = 256 * in.read() + in.read();
    _deltaX = 256 * in.read() + in.read();
    _rasterCharacterData = new byte[numOfBytes - 16];
    in.read(_rasterCharacterData);
  }

  public void continueFont(int numOfBytes, InputStream in) throws IOException {
    int newLen = _rasterCharacterData.length + numOfBytes - 2;
    byte[] characterData = new byte[numOfBytes - 2];
    in.read(characterData);
  }

  public int getCharacterHeight() {
    return _characterHeight;
  }

  public int getCharacterWidth() {
    return _characterWidth;
  }

  public int getLeftOffset() {
    return _leftOffset;
  }
}
