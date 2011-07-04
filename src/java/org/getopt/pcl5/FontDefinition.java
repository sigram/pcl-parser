package org.getopt.pcl5;

import org.getopt.pcl5.PCL5Interpreter.TypefaceInfo;
import org.getopt.pcl5.PrinterState.Spacing;
import org.getopt.pcl5.PrinterState.StrokeWeight;
import org.getopt.pcl5.PrinterState.Style;

class FontDefinition {
  private int _spacing;
  private int _pitch;
  private float _height;
  private int _style;
  private int _strokeWeight;
  private int _typeface;
  private String _fontName;
  private String _vendorName;

  FontDefinition() {
    resetPCL();
  }

  void resetPCL() {
    _spacing = Spacing.Fixed;
    _pitch = 10; // cpi
    _height = 12; // point
    _style = Style.Upright;
    _strokeWeight = StrokeWeight.Medium;
    setTypeface(0);
  }

  void resetHPGL() {
    _spacing = Spacing.Fixed;
    _pitch = 9; // cpi
    _height = 11.5f; // point
    _style = Style.Upright;
    _strokeWeight = StrokeWeight.Medium;
    setTypeface(0);
  }

  public float getHeight() {
    return _height;
  }

  public void setHeight(float height) {
    _height = height;
  }

  public int getPitch() {
    return _pitch;
  }

  public void setPitch(int pitch) {
    _pitch = pitch;
  }

  public int getSpacing() {
    return _spacing;
  }

  public void setSpacing(int spacing) {
    _spacing = spacing;
  }

  public int getStrokeWeight() {
    return _strokeWeight;
  }

  public void setStrokeWeight(int strokeWeight) {
    _strokeWeight = strokeWeight;
  }

  public int getStyle() {
    return _style;
  }

  public void setStyle(int style) {
    _style = style;
  }

  public int getTypeface() {
    return _typeface;
  }

  public void setTypeface(int typeface) {
    _typeface = typeface;
    _fontName = TypefaceInfo.getFontName(typeface);
    _vendorName = TypefaceInfo.getVendorName(typeface);
  }

  public String getFontName() {
    return _fontName;
  }

  public String getVendorName() {
    return _vendorName;
  }
}
