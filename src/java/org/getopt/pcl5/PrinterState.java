/*
 * Created on 2004-09-17
 *
 */
package org.getopt.pcl5;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import org.getopt.pcl5.PCL5Interpreter.ControlCodes;
import org.getopt.pcl5.PCL5Interpreter.FontDescriptorPCLBitmappedFonts;
import org.getopt.pcl5.PCL5Interpreter.FontHeaderCommon;
import org.getopt.pcl5.PCL5Interpreter.FontsetPCLBitmappedFonts;
import org.getopt.pcl5.PCL5Interpreter.PatternGraphics;
import org.getopt.pcl5.PCL5Interpreter.PrinterImage;
import org.getopt.pcl5.PCL5Interpreter.RasterGraphics;
import org.getopt.pcl5.PCL5Interpreter.ScaleBW2Gray;
import org.getopt.pcl5.PCL5Interpreter.UserDefinedPattern;

/**
 * This class implements state of the printer, contains current printer object
 * Support basic printer operations, commands changes state of this object
 */
public class PrinterState implements IPrinterState {

  // Internally, the printer uses a different unit of measure. It maps PCL
  // Units, decipoints, and columns and rows to this unit of measure. This
  // internal unit is 1/7200 inch. All positioning is kept in internal units and
  // rounded to physical dot positions when data is printed.

  public class Language {
    public static final int PCL5 = 0;
    public static final int PJL = 1;
    public static final int HPGL = 2;
  }

  public class CompressionMode {
    final static int UNENCODED = 0;
    final static int RunLengthEncoding = 1;
    final public static int TIFF = 2;
    final static int DeltaRowCompression = 3;
    final static int Reserved = 4;
    final static int AdaptiveCompression = 5;
  }

  class Binding {
    final static int LongEdge = 0;
  }

  class TrayLock {
    final static int AllTraysUnlocked = 0;
  }

  class OutputBin {
    final static int Upper = 0;
  }

  class PrintDirection {
    final static int Down = 0;
  }

  class TextPathDirection {
    final static int Down = 0;
  }

  class TextParsingMethod {
    final static int Normal = 0;
  }

  class MediaSource {
    final static int MainSource = 0;
  }

  class Spacing {
    final static int Fixed = 0;
    final static int Proportional = 1;
  }

  class Style {
    final static int Upright = 0;
    final static int Italic = 1;
    final static int Condensed = 4;
    final static int CondensedItalic = 5;
    final static int Compressed = 8; // or extra condensed
    final static int Expanded = 24;
    final static int Outline = 32;
    final static int Inline = 64;
    final static int Shadowed = 128;
    final static int OutlineShadowed = 160;
  }

  class StrokeWeight {
    final static int Medium = 0;
    final static int UltraThin = -7;
    final static int ExtraThin = -6;
    final static int Thin = -5;
    final static int ExtraLight = -4;
    final static int Light = -3;
    final static int DemiLight = -2;
    final static int SemiLight = -1;
    final static int SemiBold = 1;
    final static int DemiBold = 2;
    final static int Bold = 3;
    final static int ExtraBold = 4;
    final static int Black = 5;
    final static int ExtraBlack = 6;
    final static int UltraBlack = 7;
  }

  class Pattern {
    final static int SOLID_BLACK = 0;
    final static int SOLID_WHITE = 1;
    final static int SHADING_PATTERN = 2;
    final static int CROSS_HATCH_PATTERN = 3;
    final static int USER_DEFINED_PATTERN = 4;
    final static int CURRENT_PATTERN = 5;
  }

  class UnderlineMode {
    public final static int Disabled = -1;
    public final static int FixedPosition = 0;
    public final static int FloatingPosition = 3;
  }

  final static int INTERNAL_RESOLUTION = 7200; // dpi
  final static int OUTPUT_RESOLUTION = 72; // dpi
  final static float OUTPUT_SCALE = INTERNAL_RESOLUTION / OUTPUT_RESOLUTION;
  private static final int PRIMARY_SET = 0;
  private static final int SECONDARY_SET = 1;

  public class HPGLState {
    final static int SolidFill = 0;

    private static final int LeftToRight = 0;

    private static final int ABSOLUTE_MODE = 0;
    private static final int SOLID_BLACK = 1;

    private static final int SCALABLE_FONTS = 0;

    private static final int NO_SCREENING = 0;

    private static final int NORMAL_PRINTING_MODE = 0;

    private static final int UNITS_MM = 0;

    private static final int SCALING_ANISOTROPIC = 0;
    private static final int SCALING_ISOTROPIC = 1;
    private static final int SCALING_POINT_FACTOR = 2;

    private Point _anchorCorner;
    private FontDefinition[] _fontDefinitionHPGL;
    private int _characterFillMode;
    private int _absoluteDirection;
    private int _labelTerminator;
    private int _variableTextPath;
    private int _extraSpace;
    private int _fillType;
    private Rectangle _inputWindow;
    // private _lineAttributes ;
    Point _labelOrigin;
    // private _lineType
    private int _plottingMode;
    // _polygonMode
    private int _rasterFill;
    private int _fontsType;
    private int _scale;
    private int _screenedVectors;
    private int _absoluteCharacterSize;
    // private [] _userLineType
    private boolean _penDown;
    private int _characterSlant;
    private boolean _symbolMode;
    private boolean _transparencyMode;
    private int _transparentData;
    private Point _penLocation;
    private int _drawingRotation;
    private Point _p1;
    private Point _p2;
    private int _units;
    private int _penUnits;
    private double _penWidth;
    private Color[] _pens;

    HPGLState() {
      _fontDefinitionHPGL = new FontDefinition[2];
      _fontDefinitionHPGL[0] = new FontDefinition();
      _fontDefinitionHPGL[1] = new FontDefinition();
      // _userLineType = new [8];

      initialize();
    }

    public void reset() {
      _anchorCorner = new Point(0, 0);
      _fontDefinitionHPGL[0].resetHPGL();
      _fontDefinitionHPGL[1].resetHPGL();
      _characterFillMode = SolidFill;
      _absoluteDirection = 1;
      _labelTerminator = ControlCodes.ETX;
      _variableTextPath = LeftToRight;
      _extraSpace = 0;
      _fillType = SolidFill;
      _inputWindow = new Rectangle(0, 0, _horizontaPictureFrameSize,
              _verticalPictureFrameSize);
      // _lineAttributes =
      // _labelOrigin =
      // _lineType
      _plottingMode = ABSOLUTE_MODE;
      // _polygonMode
      _rasterFill = SOLID_BLACK;
      _fontsType = SCALABLE_FONTS;
      _scale = 1;
      _screenedVectors = NO_SCREENING;
      _absoluteCharacterSize = 0;
      _characterSlant = 0;
      _symbolMode = false;
      _transparencyMode = true;
      _transparentData = NORMAL_PRINTING_MODE;
      // for(int i = 0; i < _userLineType.length; i++)
      // _userLineType = _lineType;
    }

    public void initialize() {
      _penDown = false;
      _penLocation = new Point(0, 0);
      _drawingRotation = 0;
      _p1 = new Point(0, 0);
      _p2 = new Point(_horizontaPictureFrameSize, _verticalPictureFrameSize);
      _units = UNITS_MM;
      _penUnits = UNITS_MM;
      _penWidth = 0.35;
      _pens = new Color[2];
      _pens[0] = Color.BLACK;
      _pens[1] = Color.WHITE;

      reset();
    }

    public int getAbsoluteCharacterSize() {
      return _absoluteCharacterSize;
    }

    public void setAbsoluteCharacterSize(int absoluteCharacterSize) {
      _absoluteCharacterSize = absoluteCharacterSize;
    }

    public int getAbsoluteDirection() {
      return _absoluteDirection;
    }

    public void setAbsoluteDirection(int absoluteDirection) {
      _absoluteDirection = absoluteDirection;
    }

    public Point getAnchorCorner() {
      return _anchorCorner;
    }

    public void setAnchorCorner(Point anchorCorner) {
      _anchorCorner = anchorCorner;
    }

    public int getCharacterFillMode() {
      return _characterFillMode;
    }

    public void setCharacterFillMode(int characterFillMode) {
      _characterFillMode = characterFillMode;
    }

    public int getCharacterSlant() {
      return _characterSlant;
    }

    public void setCharacterSlant(int characterSlant) {
      _characterSlant = characterSlant;
    }

    public int getDrawingRotation() {
      return _drawingRotation;
    }

    public void setDrawingRotation(int drawingRotation) {
      _drawingRotation = drawingRotation;
    }

    public int getExtraSpace() {
      return _extraSpace;
    }

    public void setExtraSpace(int extraSpace) {
      _extraSpace = extraSpace;
    }

    public int getFillType() {
      return _fillType;
    }

    public void setFillType(int fillType) {
      _fillType = fillType;
    }

    public FontDefinition[] getFontDefinitionHPGL() {
      return _fontDefinitionHPGL;
    }

    public void setFontDefinitionHPGL(FontDefinition[] fontDefinition) {
      _fontDefinitionHPGL = fontDefinition;
    }

    public int getFontsType() {
      return _fontsType;
    }

    public void setFontsType(int fontsType) {
      _fontsType = fontsType;
    }

    public Rectangle getInputWindow() {
      return _inputWindow;
    }

    public void setInputWindow(Rectangle rectangle) {
      _inputWindow = rectangle;
    }

    public Point getLabelOrigin() {
      return _labelOrigin;
    }

    public void setLabelOrigin(Point labelOrigin) {
      _labelOrigin = labelOrigin;
    }

    public int getLabelTerminator() {
      return _labelTerminator;
    }

    public void setLabelTerminator(int labelTerminator) {
      _labelTerminator = labelTerminator;
    }

    public Point getP1() {
      return _p1;
    }

    public void setP1(Point p1) {
      _p1 = p1;
    }

    public Point getP2() {
      return _p2;
    }

    public void setP2(Point p2) {
      _p2 = p2;
    }

    public boolean isPenDown() {
      return _penDown;
    }

    public void setPenDown(boolean penDown) {
      _penDown = penDown;
    }

    public Point getPenLocation() {
      return _penLocation;
    }

    public void setPenLocation(Point penLocation) {
      _penLocation = penLocation;
    }

    public Color[] getPens() {
      return _pens;
    }

    public void setPens(Color[] pens) {
      _pens = pens;
    }

    public int getPenUnits() {
      return _penUnits;
    }

    public void setPenUnits(int penUnits) {
      _penUnits = penUnits;
    }

    public double getPenWidth() {
      return _penWidth;
    }

    public void setPenWidth(double penWidth) {
      _penWidth = penWidth;
    }

    public int getPlottingMode() {
      return _plottingMode;
    }

    public void setPlottingMode(int plottingMode) {
      _plottingMode = plottingMode;
    }

    public int getRasterFill() {
      return _rasterFill;
    }

    public void setRasterFill(int rasterFill) {
      _rasterFill = rasterFill;
    }

    public int getScale() {
      return _scale;
    }

    public void setScale(int scale) {
      _scale = scale;
    }

    public int getScreenedVectors() {
      return _screenedVectors;
    }

    public void setScreenedVectors(int screenedVectors) {
      _screenedVectors = screenedVectors;
    }

    public boolean isSymbolMode() {
      return _symbolMode;
    }

    public void setSymbolMode(boolean symbolMode) {
      _symbolMode = symbolMode;
    }

    public boolean isTransparencyMode() {
      return _transparencyMode;
    }

    public void setTransparencyMode(boolean transparencyMode) {
      _transparencyMode = transparencyMode;
    }

    public int getTransparentData() {
      return _transparentData;
    }

    public void setTransparentData(int transparentData) {
      _transparentData = transparentData;
    }

    public int getUnits() {
      return _units;
    }

    public void setUnits(int units) {
      _units = units;
    }

    public int getVariableTextPath() {
      return _variableTextPath;
    }

    public void setVariableTextPath(int variableTextPath) {
      _variableTextPath = variableTextPath;
    }

    public void drawArc(int x, int y, double angle) {
      // TODO Auto-generated method stub

    }

    public int getX() {
      return _penLocation.x;
    }

    public int getY() {
      return _penLocation.y;
    }
  }

  // buffer for text output
  private StringBuffer _text = new StringBuffer();

  // JOB CONTROL
  private int _numberOfCopies;
  private boolean _duplex;
  private boolean _jobSeparation;
  private boolean _manualFeed;
  private Point _registration;
  private int _unitsOfMeasure;
  private int _binding;
  private int _trayLock;
  private int _outputBin;

  // PAGE CONTROL
  private int _printDirection;
  private int _characterTextPathDirection;
  private int _textParsingMethod;
  private PageSize _pageInfo; // size and orientation
  private int _mediaSource;
  private int _verticalMotionIndex;
  private int _horizontalMotionIndex;
  private int _topMargin;
  private int _textLength;
  private int _leftMargin;
  private int _rightMargin;
  private boolean _perforationSkip;
  private int _lineTerminationCR;
  private int _lineTerminationLF;
  private int _lineTerminationFF;

  // FONT SELECTION
  private int _symbolSet;
  private int _underliningMode;
  private FontDefinition[] _fontDefinition;

  // FONT MANAGEMENT
  private int _fontID;
  private int _characterCode;
  private String[] _symbolSetID;

  // MACRO
  private int _macroID;

  // PRINT MODEL
  private boolean _sourceTransparencyMode;
  private boolean _patternTransparencyMode;
  private int _currentPattern;
  private Point _patternReferencePoint;
  private int _patternRotation;

  // RECTANGULAR AREA FILL
  // keep in printer resolution not internal resolution
  private int _horizontalRectangleSize;
  private int _verticalRectangleSize;
  private int _patternID;

  // RASTER GRAPHICS
  private int _graphicsResolution;
  private int _presentation;
  private int _compressionMode;
  private int _leftGraphicsMargin;
  private int _rasterHeight;
  private int _rasterWidth;

  // TROUBLESHOOTING COMMANDS
  private boolean _endOfLineWrap;
  private boolean _displayFunctions;

  // STATUS READBACK
  private int _currentLocationType;
  private int _currentLocationUnit;

  // Printer object
  private IPrint _printer;

  // Raster mode
  private int _rasterMode;

  // Font maps
  HashMap _fontHeaderMap;
  // map of ArrayList of FontDescriptorPCLBitmappedFonts
  HashMap _fontDefinitionMap;

  // Selected fonts
  private int[] _selectedFontID;

  // defined fonts
  private FontDescriptorPCLBitmappedFonts _lastDefinedChar; // we need this
                                                            // because font can
                                                            // be loaded by more
                                                            // than one command

  // used font set 0 - primary, 1 - secondary
  private int _fontSet;

  // Current column
  private int _column;

  // position in 7200 DPI units!!
  private int _x;
  private int _y;

  // graphics mode
  private boolean _graphicsMode;
  private byte[] _rasterData;

  // position in graphics mode in printer units!!
  private int _grX;
  private int _grY;

  // picture frame
  private Point _frameAnchorPoint;
  private int _horizontaPictureFrameSize;
  private int _verticalPictureFrameSize;

  // user defined pattern
  private HashMap _userDefinedPatterns;

  private HashMap _environment;

  private int _activeLanguage;

  private HPGLState _hpglState;
  private int _compressionMethod;

  // font definitions for renderer
  // Font[] _fonts;
  private RasterGraphics _canvas;

  // color for bound box
  private boolean _showBoundBox;

  public PrinterState() {
    _fontHeaderMap = new HashMap();
    _fontDefinitionMap = new HashMap();

    _pageInfo = new PageSize();
    _symbolSetID = new String[2];
    _selectedFontID = new int[2];
    _fontDefinition = new FontDefinition[2];
    _fontDefinition[0] = new FontDefinition();
    _fontDefinition[1] = new FontDefinition();
    _hpglState = new HPGLState();

    // _fonts = new Font[2];

    _showBoundBox = false;

    reset();
  }

  /**
   * Resets printer state Factory Default Print Environment Feature Settings
   * (PCL)
   * 
   * @see bpl13205.pdf
   */
  public void reset() {
    resetJobControl();
    resetPageControl();
    resetFontSelection();
    resetFontManagement();
    resetMacro();
    resetRectangularAreaFill();
    resetRasterGraphics();
    resetTroubleshootingCommands();
    resetStatusReadback();

    _fontSet = PRIMARY_SET;
    _activeLanguage = Language.PCL5;
    _rasterMode = CommonPrinterState.RasterMode.PhysicalPage;
    _column = 0;
    _x = 0;
    _y = 0;
    _graphicsMode = false;
    _frameAnchorPoint = new Point(0, 0);
    _userDefinedPatterns = new HashMap();
    _environment = new HashMap();
    _rasterHeight = 0;
    _rasterWidth = 0;
  }

  /**
   * Factory Default Print Environment Feature Settings (PCL)
   */

  /**
   * JOB CONTROL
   * 
   * some items are user defined For these items, select User Default values
   * using the printer driver or control panel (or remote control panel for
   * LaserJet 4L, 5L, and 5P).
   */
  void resetJobControl() {
    _numberOfCopies = 1; // select User Default values
    _duplex = false; // select User Default values
    _binding = Binding.LongEdge; // select User Default values
    _trayLock = TrayLock.AllTraysUnlocked;
    _jobSeparation = false;
    _manualFeed = false; // select User Default values
    _registration = new Point(0, 0);
    _outputBin = OutputBin.Upper;
    _unitsOfMeasure = 300; // Units/Inch
  }

  /**
   * PAGE CONTROL
   * 
   * some items are user defined For these items, select User Default values
   * using the printer driver or control panel (or remote control panel for
   * LaserJet 4L, 5L, and 5P).
   */
  void resetPageControl() {
    _printDirection = PrintDirection.Down;
    _characterTextPathDirection = TextPathDirection.Down; // select User Default
                                                          // values
    _textParsingMethod = TextParsingMethod.Normal; // select User Default values
    _pageInfo.setOrientation(PageSize.Portrait); // select User Default values
    _pageInfo.setSize(PageSize.Letter); // select User Default values
    _mediaSource = MediaSource.MainSource; // (Printer Specific)
    _verticalMotionIndex = 8; // 6 lpi select User Default values
    _horizontalMotionIndex = 12; // 10 cpi
    _topMargin = 150 * getUnitConversion(); // 1/2" (150 dots or 3 lines)
    _textLength = 60; // lines
    _leftMargin = 0; // Left logical page boundary
    _rightMargin = 0; // Right logical page boundary
    _perforationSkip = true;
    _lineTerminationCR = ControlCodes.CR;
    _lineTerminationLF = ControlCodes.LF;
    _lineTerminationFF = ControlCodes.FF;
  }

  /**
   * FONT SELECTION
   * 
   * The font characteristics are determined by the default font. The default
   * font can be the factory default font or the user selected default font from
   * the control panel or from a font cartridge with a default font.
   */
  void resetFontSelection() {
    _symbolSet = CommonPrinterState.SymbolSet.Roman_8; // ROMAN-8 // PC-8 is the
                                                       // default symbol set for
                                                       // the LaserJet
                                                       // 5L/5Si/5SiMx
    _fontDefinition[0].resetPCL();
    _fontDefinition[1].resetPCL();
    _underliningMode = UnderlineMode.Disabled;
  }

  /**
   * FONT MANAGEMENT
   */
  void resetFontManagement() {
    _fontID = 0;
    _characterCode = 0;
    _symbolSetID[0] = "8U";
    _symbolSetID[1] = "8U";
  }

  /**
   * MACRO
   */
  void resetMacro() {
    _macroID = 0;
  }

  /**
   * PRINT MODEL
   */
  void resetPrintModel() {
    _sourceTransparencyMode = true; // 0 (Transparent)
    _patternTransparencyMode = true; // 0 (Transparent)
    _currentPattern = Pattern.SOLID_BLACK; // (Black)
    _patternReferencePoint = new Point(0, 0);
    _patternRotation = 0;
  }

  /**
   * RECTANGULAR AREA FILL
   */
  void resetRectangularAreaFill() {
    _horizontalRectangleSize = 0;
    _verticalRectangleSize = 0;
    _patternID = 0; // (AREA FILL)
  }

  /**
   * RASTER GRAPHICS
   */
  void resetRasterGraphics() {
    _graphicsResolution = 75; // dpi
    _presentation = 3;
    _compressionMode = CompressionMode.UNENCODED;
    _leftGraphicsMargin = 0;
    // _rasterWidth = Logical Page
    // _rasterHeight = N/A
  }

  /**
   * TROUBLESHOOTING COMMANDS
   */
  void resetTroubleshootingCommands() {
    _endOfLineWrap = false;
    _displayFunctions = false;
  }

  /**
   * STATUS READBACK
   */
  void resetStatusReadback() {
    _currentLocationType = 0;
    _currentLocationUnit = 0;
  }

  /**
   * Calculates length of string with current font
   * 
   * @param text
   *          text to calculate
   * 
   * @return length of text in 7200DPI
   */
  float getStringWidth(String text) {
    float w = 0.0f;

    return w;
  }

  /**
   * Output image to printer object
   * 
   * @param img
   *          image to print
   * @param hResolution
   *          image resolution
   * @param wResolution
   *          image resolution
   */
  void printGraphics(PrinterImage img, int hResolution, int wResolution) {
  }

  /**
   * Build Font from current printer state
   * 
   * TODO: Should be refactored
   * 
   */
  void buildFont() {
  }

  /**
   * Output text buffer to printer object
   * 
   */
  public void printText() {
    if (_text.length() > 0) {
      int[] kerning = calculateKerning(_text.toString(), OUTPUT_SCALE);
      float w = calculateWidth(_text.toString()) / OUTPUT_SCALE;
      float h = calculateFontHeight() / OUTPUT_SCALE;
      float x = _x / OUTPUT_SCALE;
      float y = _y / OUTPUT_SCALE;

      _printer.printText(x, y, w, h, _text.toString(), kerning, this);

      _x += calculateWidth(_text.toString());
      _text = new StringBuffer();
    }
  }

  private int calculateFontHeight() {
    return 0;
  }

  private int calculateWidth(String text) {
    int width = 0;
    int len = text.length();

    for (int i = 1; i < len; i++) {
      width += calculateCharsKerning(text.charAt(i - 1), text.charAt(i))
              + getCharWidth(text.charAt(i - 1));
    }

    return width;
  }

  private int[] calculateKerning(String text, float scaleFactor) {
    int len = text.length();
    int[] kerning = new int[len + 1];

    kerning[0] = 0;

    for (int i = 1; i < len; i++) {
      kerning[i] = (int) (kerning[i - 1]
              + calculateCharsKerning(text.charAt(i - 1), text.charAt(i))
              / scaleFactor + getCharWidth(text.charAt(i - 1)) / scaleFactor);
    }

    kerning[len] = kerning[len - 1]
            + (int) (getCharWidth(text.charAt(len - 1)) / scaleFactor);
    return kerning;
  }

  private int getCharWidth(char c) {
    FontsetPCLBitmappedFonts fonts = (FontsetPCLBitmappedFonts) _fontDefinitionMap
            .get(new Integer(_selectedFontID[_fontSet]));

    if (fonts == null)
      return INTERNAL_RESOLUTION / _fontDefinition[_fontSet].getPitch();

    return fonts.getCharacterWidth(c) * INTERNAL_RESOLUTION
            / _graphicsResolution;
  }

  private int calculateCharsKerning(char c, char d) {
    return 0;
  }

  /**
   * Output to printer new page
   * 
   */
  public void newPage() {
    printText();
    _printer.newPage();
  }

  private Font createFont() {
    FontDefinition def = _fontDefinition[_fontSet];

    // FontHeaderCommon fh = (FontHeaderCommon)_fontHeaderMap.get(new
    // Integer(_selectedFontID[_fontSet]));
    // String name = "Serif";
    // if (fh.isSansSerif())
    // name = "SansSerif";
    // int height = fh.getHeight();
    // int style = Font.PLAIN;
    // if (fh.isBold())
    // style += Font.BOLD;
    // if (fh.isItalic())
    // style += Font.ITALIC;

    return new Font(def.getFontName(), def.getStyle(), Math.round(def
            .getHeight()));
  }

  public Font getFont() {
    return createFont();
    // return _fonts[_fontSet];
  }

  public Map getFontAttributes() {
    // TODO Auto-generated method stub
    return null;
  }

  public Color getCurrentColor() {
    return Color.BLACK;
  }

  public boolean isCondensed() {
    // TODO Auto-generated method stub
    return false;
  }

  public float getSpaceWidth() {
    // TODO Auto-generated method stub
    return 0;
  }

  public void assertCondition(Object o, String msg) {
    _printer.assertCondition(o, msg);
  }

  public void notImplemented(Object o, String msg) {
    _printer.notImplemented(o, msg);
  }

  public void trace(Object o, String msg) {
    _printer.trace(o, msg);
  }

  int getUnitConversion() {
    return INTERNAL_RESOLUTION / _unitsOfMeasure;
  }

  public void setPrinter(IPrint print) {
    _printer = print;
  }

  public void addText(char c) {
    _text.append(c);
  }

  public void setRasterMode(int parameter) {
    _rasterMode = parameter;
  }

  public void setPageOrientation(int orientation) {
    _pageInfo.setOrientation(orientation);
  }

  public void setPageSize(int size) {
    _pageInfo.setSize(size);

    float w = _pageInfo.getPhysicalPageWidth() / OUTPUT_SCALE;
    float h = _pageInfo.getPhysicalPageLength() / OUTPUT_SCALE;

    _printer.pageSize(w, h);
  }

  public void finishPage() {
    printText();
  }

  public void setVerticalMotionIndex(int vmi) {
    // number of 1/48 inch increments between rows
    _verticalMotionIndex = vmi * INTERNAL_RESOLUTION / 48;
  }

  public void setResolution(int resolution) {
    _graphicsResolution = resolution;
  }

  public void setTopMargin(int lines) {
    if (_verticalMotionIndex > 0) {
      _topMargin = _verticalMotionIndex * lines;
      // _textLength = (logical page len in inches) - (top margin in inches +
      // 1/2 inch)
    }
  }

  public void setLeftMargin(int columns) {
    _leftMargin = columns;
  }

  public int getLeftMargin() {
    return _leftMargin;
  }

  public void setRightMargin(int columns) {
    _rightMargin = columns;
  }

  public int getLineTerminationCR() {
    return _lineTerminationCR;
  }

  public void setLineTerminationCR(int lineTerminationCR) {
    _lineTerminationCR = lineTerminationCR;
  }

  public int getLineTerminationFF() {
    return _lineTerminationFF;
  }

  public void setLineTerminationFF(int lineTerminationFF) {
    _lineTerminationFF = lineTerminationFF;
  }

  public int getLineTerminationLF() {
    return _lineTerminationLF;
  }

  public void setLineTerminationLF(int lineTerminationLF) {
    _lineTerminationLF = lineTerminationLF;
  }

  public void setColumn(int column) {
    printText();
    _x = column;
  }

  public void setFontID(int fontID) {
    printText();
    _fontID = fontID;
  }

  public void setFontHeader(FontHeaderCommon fontHeader) {
    _fontHeaderMap.put(new Integer(_fontID), fontHeader);

    if (!fontHeader.isScalable()) // fixed size font
    {
      FontDefinition fontDef = new FontDefinition();
      fontDef.setSpacing(Spacing.Proportional);
      fontDef.setHeight(fontHeader.getHeight());
      fontDef.setPitch(fontHeader.getPitch());
      fontDef.setStrokeWeight(StrokeWeight.Medium);
      fontDef.setStyle(fontHeader.getStyle());

      if (fontHeader.isSansSerif())
        fontDef.setTypeface(2801);
      else
        fontDef.setTypeface(2800);

    }
  }

  public void setFontID(int fontID, boolean primary) {
    printText();

    if (primary) {
      _selectedFontID[0] = fontID;
    } else {
      _selectedFontID[1] = fontID;
    }
  }

  public void setUnderlineType(int mode) {
    printText();
    _underliningMode = mode;
  }

  public void setCharacterCode(int characterCode) {
    printText();
    _characterCode = characterCode;
  }

  public void setDefinedCharacter(FontDescriptorPCLBitmappedFonts font) {
    printText();

    _lastDefinedChar = font;

    FontsetPCLBitmappedFonts fonts = (FontsetPCLBitmappedFonts) _fontDefinitionMap
            .get(new Integer(_fontID));
    if (fonts == null) { // font not defined yet
      fonts = new FontsetPCLBitmappedFonts();
      _fontDefinitionMap.put(new Integer(_fontID), fonts);
    }

    fonts.addFont(_characterCode, font);
  }

  public FontDescriptorPCLBitmappedFonts getDefinedCharacter() {
    return _lastDefinedChar;
  }

  public void setPatternTransparencyMode(int transparencyMode) {
    _patternTransparencyMode = transparencyMode == 0;
  }

  public void setCurrentPattern(int pattern) {
    _currentPattern = pattern;
  }

  public void setRelativeVerticalCursorPosition(int position) {
    printText();
    _y += position * INTERNAL_RESOLUTION / _graphicsResolution;
  }

  public void setRelativeHorizontalCursorPosition(int position) {
    printText();
    _x += position * INTERNAL_RESOLUTION / _graphicsResolution;
  }

  public void setAbsoluteVerticalCursorPosition(int position) {
    printText();

    if (_graphicsMode)
      _grY = position;
    else
      _y = position * INTERNAL_RESOLUTION / _graphicsResolution;
  }

  public void setAbsoluteHorizontalCursorPosition(int position) {
    printText();

    if (_graphicsMode)
      _grX = position;
    else
      _x = position * INTERNAL_RESOLUTION / _graphicsResolution;
  }

  public void setHorizontalRectangleSize(int size) {
    // !! keep in printer resolution !!
    _horizontalRectangleSize = size;
  }

  public void setVerticalRectangleSize(int size) {
    // !! keep in printer resolution !!
    _verticalRectangleSize = size;
  }

  public void fillRectangularArea(int pattern) {
    printText();

    PatternGraphics pg = new PatternGraphics(_horizontalRectangleSize,
            _verticalRectangleSize);

    if (_currentPattern == Pattern.SOLID_BLACK)
      pg.setPatternColor(Color.BLACK);
    else if (_currentPattern == Pattern.SOLID_WHITE)
      pg.setPatternColor(Color.WHITE);
    else if (_currentPattern == Pattern.USER_DEFINED_PATTERN)
      pg.setPattern((UserDefinedPattern) _userDefinedPatterns.get(new Integer(
              _patternID)));
    else
      assertCondition(this,
              "Not implemented fillRectangularArea other patterns");

    float x = Math.round(_x / OUTPUT_SCALE);
    float y = Math.round(_y / OUTPUT_SCALE);
    float w = Math.round(_horizontalRectangleSize * OUTPUT_RESOLUTION
            / _graphicsResolution);
    float h = Math.round(_verticalRectangleSize * OUTPUT_RESOLUTION
            / _graphicsResolution);

    if (w < 1.0)
      w = 1;

    if (h < 1.0)
      h = 1;

    BufferedImage img = pg.getImage();
    ScaleBW2Gray scale = new ScaleBW2Gray(img, w, h, _showBoundBox);

    _printer.printBitmap(x, y, w, h, scale.getImage(), this);

    // _x += _horizontalRectangleSize;
    // _y += _verticalRectangleSize;
  }

  public void startRasterGraphics(int start) {
    printText();

    _grX = (_x * _graphicsResolution) / INTERNAL_RESOLUTION;
    _grY = (_y * _graphicsResolution) / INTERNAL_RESOLUTION;

    if (start == 0)
      _grX = _leftMargin;

    _graphicsMode = true;

    int w = _horizontaPictureFrameSize;
    int h = _verticalPictureFrameSize;

    if (w == 0)
      w = (_pageInfo.getPrintableAreaWidth() * _graphicsResolution)
              / INTERNAL_RESOLUTION;

    if (h == 0)
      h = (_pageInfo.getPrintableAreaLength() * _graphicsResolution)
              / INTERNAL_RESOLUTION;

    _canvas = new RasterGraphics(w, h);
  }

  public void endGraphicsMode() {
    _graphicsMode = false;

    Point offset = _canvas.getStart();
    BufferedImage img = _canvas.getImage();

    float x = (float) _frameAnchorPoint.getX() * OUTPUT_SCALE
            + (float) offset.getX() * OUTPUT_RESOLUTION / _graphicsResolution;

    float y = (float) _frameAnchorPoint.getY() * OUTPUT_SCALE
            + (float) offset.getY() * OUTPUT_RESOLUTION / _graphicsResolution;

    // this values are in graphics resolution
    float w = ((float) img.getWidth() * OUTPUT_RESOLUTION)
            / _graphicsResolution + 1.0f;
    float h = ((float) img.getHeight() * OUTPUT_RESOLUTION)
            / _graphicsResolution + 1.0f;

    if (w < 1.0f)
      w = 1.0f;

    if (h < 1.0f)
      h = 1.0f;

    ScaleBW2Gray scale = new ScaleBW2Gray(img, w, h, _showBoundBox);
    _printer.printBitmap(x, y, w, h, scale.getImage(), this);

    // _grX = 0;
    // _grY = 0;
    _y = (_grY * INTERNAL_RESOLUTION) / _graphicsResolution;

    _canvas = null;
  }

  public void transferRasterData(byte[] data) {
    int height = 0;

    if (_compressionMethod == CompressionMode.UNENCODED
            || _compressionMethod == CompressionMode.TIFF) {
      height = _canvas.decodeImage(data, _compressionMethod, _grX, _grY,
              _rasterWidth, _rasterHeight);
    } else
      assertCondition(this, "Unimplemented compression method "
              + _compressionMethod);

    // BufferedImage img = _canvas.getImage();
    // _printer.printBitmap(0, 0, img.getWidth(), img.getHeight(), img, null);
    // _grX = 0; //_rasterWidth * INTERNAL_RESOLUTION / _graphicsResolution;

    // if (_rasterHeight == 0)
    _grY += height;
    // else
    // _grY += _rasterHeight;
  }

  public boolean isGraphicsMode() {
    return _graphicsMode;
  }

  // TODO: compare methods
  public void setCompressionMode(int compressionMode) {
    _compressionMode = compressionMode;
  }

  public void setCompressionMethod(int method) {
    _compressionMethod = method;
  }

  public void setUnitOfMeasure(int unitOfMeasure) {
    printText();
    _unitsOfMeasure = unitOfMeasure;
  }

  public void setPictureFrameAnchorPoint(int parameter) {
    printText();
    _frameAnchorPoint = new Point(_x, _y);
  }

  // graphics resolution !! not internal
  public void setHorizontalPictureFrameSize(int size) {
    printText();
    _horizontaPictureFrameSize = size;
  }

  // graphics resolution !! not internal
  public void setVerticalPictureFrameSize(int size) {
    printText();
    _verticalPictureFrameSize = size;
  }

  public void setPatternID(int pattern) {
    printText();
    _patternID = pattern;
  }

  public void setUserDefinedPattern(UserDefinedPattern pattern) {
    _userDefinedPatterns.put(new Integer(_patternID), pattern);
  }

  public int getActiveLanguage() {
    return _activeLanguage;
  }

  public void setActiveLanguage(int activeLanguage) {
    printText();
    _activeLanguage = activeLanguage;
  }

  public void setEnvironment(String name, String value) {
    _environment.put(name, value);
  }

  public void setSymbolSet(String symbolSet, boolean primary) {
    printText();
    if (primary)
      _symbolSetID[0] = symbolSet;
    else
      _symbolSetID[1] = symbolSet;
  }

  public void setTypefaceFamily(int typeface, boolean primary) {
    printText();
    if (primary)
      _fontDefinition[0].setTypeface(typeface);
    else
      _fontDefinition[1].setTypeface(typeface);
  }

  public void setSpacing(int spacing, boolean primary) {
    printText();
    if (primary)
      _fontDefinition[0].setSpacing(spacing);
    else
      _fontDefinition[1].setSpacing(spacing);
  }

  public void setPitch(int pitch, boolean primary) {
    printText();
    if (primary)
      _fontDefinition[0].setPitch(pitch);
    else
      _fontDefinition[1].setPitch(pitch);
  }

  public void setHeight(int height, boolean primary) {
    printText();
    if (primary)
      _fontDefinition[0].setHeight(height);
    else
      _fontDefinition[1].setHeight(height);
  }

  public void setStyle(int style, boolean primary) {
    printText();
    if (primary)
      _fontDefinition[0].setStyle(style);
    else
      _fontDefinition[1].setStyle(style);
  }

  public void setStrokeWeight(int strokeWeight, boolean primary) {
    printText();
    if (primary)
      _fontDefinition[0].setStrokeWeight(strokeWeight);
    else
      _fontDefinition[1].setStrokeWeight(strokeWeight);
  }

  public void resetHPGLDefaultValues() {
    _hpglState.reset();
  }

  public HPGLState getHPGLState() {
    return _hpglState;
  }

  public int getHorizontaPictureFrameSize() {
    return _horizontaPictureFrameSize;
  }

  public int getVerticalPictureFrameSize() {
    return _verticalRectangleSize;
  }

  public int getUnderliningMode() {
    return _underliningMode;
  }

  public void setUnderliningMode(int underliningMode) {
    _underliningMode = underliningMode;
  }

  public boolean getBoundBoxColor() {
    return _showBoundBox;
  }

  public void setBoundBoxColor(boolean boxColor) {
    _showBoundBox = boxColor;
  }
}