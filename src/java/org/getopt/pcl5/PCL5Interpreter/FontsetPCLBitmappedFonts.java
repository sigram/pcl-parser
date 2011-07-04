/**
 * Implementation of char set
 */
package org.getopt.pcl5.PCL5Interpreter;

import java.util.ArrayList;

public class FontsetPCLBitmappedFonts {
  ArrayList _fonts;

  public FontsetPCLBitmappedFonts() {
    _fonts = new ArrayList(256);
    for (int i = 0; i < 256; i++)
      _fonts.add(null);
  }

  public void addFont(int code, FontDescriptorPCLBitmappedFonts font) {
    // _fonts.ensureCapacity(code);

    _fonts.set(code, font);
  }

  public FontDescriptorPCLBitmappedFonts getFont(int code) {
    return (FontDescriptorPCLBitmappedFonts) _fonts.get(code);
  }

  public int getCharacterWidth(int code) {
    FontDescriptorPCLBitmappedFonts font = getFont(code);

    if (font == null)
      return 0; // TODO: get value from font header

    return font.getCharacterWidth();
  }

  public int getCharacterHeight(int code) {
    FontDescriptorPCLBitmappedFonts font = getFont(code);

    if (font == null)
      return 0;

    return font.getCharacterHeight();
  }
}
