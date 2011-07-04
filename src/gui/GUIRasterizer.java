import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

import org.getopt.pcl5.IPrint;
import org.getopt.pcl5.IPrinterState;
import org.getopt.pcl5.Interpreter;

/**
 * Sample rasterizer for PCL5 interpreter
 * 
 * @author Piotrm
 */
public class GUIRasterizer extends JFrame
{
	private javax.swing.JPanel jContentPane = null;
	private VectorButton btnOpen = null;
	VectorButton btnStart = null;
	private JToolBar jToolBar = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel messagesPanel = null;
	JTextArea traceText = null;
	private JScrollPane traceTextPanel = null;
	JTextArea notImplementedText = null;
	private JScrollPane notImplementedTextPanel = null;
	JTextArea assertText = null;
	private JScrollPane assertTextPanel= null;
	JComboBox pageNo = null;
	BitmapPanel pagePanel = null;
	JCheckBox boundBox = null;
	
	boolean importInProgress;
	private String _inputFile;
	private Printer rasterizer;
	BufferedImage currentPage;
	ArrayList pagesList = new ArrayList();

	Graphics2D graphics;
	
	class Printer implements IPrint
	{
		private int pagesCnt;
		final static int MARGIN = 20;
		final static int PAGE_WIDTH = 72 * 2100 / 254 + 2*MARGIN;   // DPI * A4W / inch + 2*20px margins
		final static int PAGE_HEIGHT = 72 * 2970 / 254 + 2*MARGIN;  // DPI * A4H / inch + 2*20px margins
		
		final static boolean GUI_DEBUG = false;
		
		final static int PAGE_OFFSET = 40;
		
		/* (non-Javadoc)
		 * @see com.ccginc.pcl5.PCL5Interpreter.IPrint#newPage()
		 */
		public void newPage()
		{
			pagesCnt++;
			currentPage = new BufferedImage(PAGE_WIDTH, PAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
			pagesList.add(currentPage);
			pageNo.addItem("Page " + pagesCnt);
			
			graphics = currentPage.createGraphics();
			
			graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			graphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
			
			graphics.setBackground(Color.LIGHT_GRAY);
			graphics.clearRect(0, 0, PAGE_WIDTH, PAGE_HEIGHT);
			
			graphics.setColor(Color.WHITE);
			graphics.fillRect(MARGIN, MARGIN, PAGE_WIDTH-2*MARGIN, PAGE_HEIGHT-2*MARGIN);
			
			graphics.setColor(Color.BLUE);
			graphics.drawRect(MARGIN, MARGIN, PAGE_WIDTH-2*MARGIN, PAGE_HEIGHT-2*MARGIN);
			
			graphics.drawString("Page: " + pagesCnt, 10, 15);
			graphics.setColor(Color.BLACK);
			
			pagePanel.setImg(currentPage);
			pagePanel.repaint();

			if (GUI_DEBUG)
			{
				final int step = 72;
				graphics.setColor(new Color(0, 0, 255, 127));
				for(int i = step; i < PAGE_WIDTH; i+=step)
					graphics.drawLine(MARGIN+i, MARGIN, MARGIN+i, MARGIN+PAGE_HEIGHT-2*MARGIN);
			
				graphics.setColor(new Color(255, 0, 0, 127));
				for(int i = step; i < PAGE_HEIGHT; i+=step)
					graphics.drawLine(MARGIN, MARGIN+i, MARGIN+PAGE_WIDTH-2*MARGIN, MARGIN+i);
			
				Graphics gr = getGlassPane().getGraphics();

				gr.setColor(Color.WHITE);
				gr.fillRect(MARGIN, MARGIN+PAGE_OFFSET, PAGE_WIDTH-2*MARGIN, PAGE_HEIGHT-2*MARGIN);
				gr.setColor(Color.BLUE);
				gr.drawRect(MARGIN, MARGIN+PAGE_OFFSET, PAGE_WIDTH-2*MARGIN, PAGE_HEIGHT-2*MARGIN);
			
				gr.drawString("Page: " + pagesCnt, 10, 15+PAGE_OFFSET);
			
				//final int step = 72;
				gr.setColor(new Color(0, 0, 255, 127));
				for(int i = step; i < PAGE_WIDTH; i+=step)
					gr.drawLine(MARGIN+i, MARGIN+PAGE_OFFSET, MARGIN+i, MARGIN+PAGE_HEIGHT-2*MARGIN+PAGE_OFFSET);
			
				gr.setColor(new Color(255, 0, 0, 127));
				for(int i = step; i < PAGE_HEIGHT; i+=step)
					gr.drawLine(MARGIN, MARGIN+i+PAGE_OFFSET, MARGIN+PAGE_WIDTH-2*MARGIN, MARGIN+i+PAGE_OFFSET);
			}
		}

		/* (non-Javadoc)
		 * @see com.ccginc.pcl5.PCL5Interpreter.IPrint#pageSize(float, float)
		 */
		public void pageSize(float w, float h)
		{
		}

		/* (non-Javadoc)
		 * @see com.ccginc.pcl5.PCL5Interpreter.IPrint#newMargins(float, float, float, float)
		 */
		public void newMargins(float top, float bottom, float left, float right)
		{
			graphics.setColor(Color.RED);
			if (top != 0)
				graphics.drawLine(20, 20+(int)top, PAGE_WIDTH-40, 20+(int)top);
			
			if (bottom != 0)
				graphics.drawLine(20, PAGE_HEIGHT-40-(int)bottom, PAGE_WIDTH-40, PAGE_HEIGHT-40-(int)bottom);
			
			if (left != 0)
				graphics.drawLine(20+(int)left, 20, 20+(int)left, PAGE_HEIGHT-40);
				
			if (right != 0)
				graphics.drawLine(PAGE_WIDTH-40-(int)right, 20, PAGE_WIDTH-40-(int)right, PAGE_HEIGHT-40);
			
			graphics.setColor(Color.BLACK);
		}

		/* (non-Javadoc)
		 * @see com.ccginc.pcl5.PCL5Interpreter.IPrint#processingStart()
		 */
		public void processingStart()
		{
			pagesCnt = 0;
			btnStart.setEnabled(false);
		}

		/* (non-Javadoc)
		 * @see com.ccginc.pcl5.PCL5Interpreter.IPrint#processingEnd()
		 */
		public void processingEnd()
		{
			btnStart.setEnabled(true);
			graphics = null;
		}

		/* (non-Javadoc)
		 * @see com.ccginc.pcl5.PCL5Interpreter.IPrint#printText(float, float, float, float, java.lang.String, com.ccginc.pcl5.PCL5Interpreter.IPrinterState)
		 */
		public void printText(float x, float y, float w, float h, String text, int[] kerning, IPrinterState state)
		{
			int newX = Math.round(x);
			int newY = Math.round(y);
			int newW = Math.round(w);
			int newH = Math.round(h);
			
			//graphics.setColor(Color.PINK);
			//graphics.drawRect(newX, newYy, newW, newH);   // bound box
			Font fnt = state.getFont();
			if (fnt != null)
				graphics.setFont(fnt);
			
			//graphics.setColor(state.getCurrentColor());
			
			for(int i = 0; i < text.length(); i++)
				graphics.drawString(
						"" + text.charAt(i), 
						newX+MARGIN + kerning[i], 
						newY+h+MARGIN);
			
			if (boundBox.isSelected())
			{
				graphics.setColor(Color.PINK);
				graphics.drawRect(newX+MARGIN, newY+PAGE_OFFSET+MARGIN, newW, newH);   // bound box
				graphics.setColor(Color.BLACK);
			}
			
			if (GUI_DEBUG)
			{
				
				Graphics gr = getGlassPane().getGraphics();
				gr.setFont(state.getFont());
				gr.setColor(Color.PINK);
				gr.drawRect(newX+MARGIN, newY+PAGE_OFFSET+MARGIN, newW, newH);   // bound box
				gr.setColor(Color.BLACK);
				gr.drawString(text, newX+MARGIN, newY+PAGE_OFFSET+newH+MARGIN);
			
				if (state.getUnderliningMode() != -1)
					gr.drawLine(newX+MARGIN, newY+newH+PAGE_OFFSET+MARGIN, newX+MARGIN+newW, newY+newH+PAGE_OFFSET+MARGIN);   // underline

				gr.setColor(Color.BLUE);
				for(int i = 0; i < kerning.length; i++)
					graphics.fillRect(newX+MARGIN + kerning[i],	(int)(newY+h+MARGIN), 1, 1);
			}
		}

		/* (non-Javadoc)
		 * @see com.ccginc.pcl5.PCL5Interpreter.IPrint#printBitmap(float, float, float, float, java.awt.image.BufferedImage, com.ccginc.pcl5.PCL5Interpreter.IPrinterState)
		 */
		public void printBitmap(float x, float y, float w, float h, BufferedImage image, IPrinterState state)
		{
			if (image == null || w == 0 || h == 0)
				return;
				
			int newX = Math.round(x);
			int newY = Math.round(y);
			int newW = Math.round(w);
			int newH = Math.round(h);
			
			//graphics.setColor(Color.GREEN);
			//graphics.drawRect(newX, newY, newW, newH);   // bound box
			graphics.drawImage(image, newX+MARGIN, newY+MARGIN, newW, newH, null);

			if (boundBox.isSelected())
			{
				graphics.setColor(Color.GREEN);
				graphics.drawRect(newX+MARGIN, newY+MARGIN, newW, newH);   // bound box
				graphics.setColor(Color.BLACK);
			}
			
			if (GUI_DEBUG)
			{
				Graphics2D gr = (Graphics2D)getGlassPane().getGraphics();
				gr.setColor(Color.GREEN);
				gr.setBackground(Color.YELLOW);
				gr.drawRect(newX+MARGIN, newY+PAGE_OFFSET+MARGIN, newW, newH);   // bound box
				gr.drawImage(image, newX+MARGIN, newY+PAGE_OFFSET+MARGIN, newW, newH, null);	// transparent background
				//gr.drawImage(image, newX+MARGIN, newY+PAGE_OFFSET+MARGIN, null);	// transparent background
			}
		}

		/* (non-Javadoc)
		 * @see com.ccginc.pcl5.PCL5Interpreter.IPrint#trace(java.lang.Object, java.lang.String)
		 */
		public void trace(Object command, String message)
		{
			traceText.append("\n");
			traceText.append(message);
			traceText.append(command.toString());
		}

		/* (non-Javadoc)
		 * @see com.ccginc.pcl5.PCL5Interpreter.IPrint#assertCondition(java.lang.Object, java.lang.String)
		 */
		public void assertCondition(Object command, String message)
		{
			assertText.append("\n");
			assertText.append(message);
			assertText.append(command.toString());
		}

		/* (non-Javadoc)
		 * @see com.ccginc.pcl5.PCL5Interpreter.IPrint#notImplemented(java.lang.Object, java.lang.String)
		 */
		public void notImplemented(Object command, String message)
		{
			notImplementedText.append("\n");
			notImplementedText.append(message);
			notImplementedText.append(command.toString());
		}
	}
	
	/**
	 * @return Returns the inputFile.
	 */
	public String getInputFile()
	{
		return _inputFile;
	}
	/**
	 * @param inputFile The inputFile to set.
	 */
	public void setInputFile(String inputFile)
	{
		this._inputFile = inputFile;
		if (inputFile != null)
		{
			setTitle(inputFile);
			btnStart.setEnabled(true);
		}
		else
			setTitle("-no file selected-");
	}
	
	/**
	 * @return Returns the rasterizer.
	 */
	public IPrint getRasterizer()
	{
		return rasterizer;
	}
	
	/**
	 * This is the default constructor
	 */
	public GUIRasterizer() {
		super();
		initialize();
		
		rasterizer = new Printer();
	}
	
	void startParser()
	{
		importInProgress = true;
		
		InputStream is;
		try
		{
			is = new BufferedInputStream(new FileInputStream(_inputFile));
			
			Interpreter interpreter = new Interpreter();
			
			if (boundBox.isSelected())
				interpreter.setBoundBoxColor(true);
			
			interpreter.parse(is, rasterizer);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			importInProgress = false;
			repaint();
		}
	}
	
	/**
	 * This method initializes controls
	 */
	private void initialize() {
		this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);  // Generated
		this.setSize(800, 900);
		this.setLocation(600, 10);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
	}
	
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new BorderLayout());  // Generated
			jContentPane.add(getJToolBar(), java.awt.BorderLayout.NORTH);  // Generated
			jContentPane.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);  // Generated
		}
		return jContentPane;
	}
	
	/**
	 * This method initializes btnOpen	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getBtnOpen() {
		if (btnOpen == null) {
			btnOpen = new VectorButton();
			btnOpen.setText("Open file");
			btnOpen.setForeground(new Color(30,132,255));
			btnOpen.setBackground(new Color(219, 243, 247));

			btnOpen.addActionListener(new java.awt.event.ActionListener() 
			{ 
				public void actionPerformed(java.awt.event.ActionEvent e) 
				{   
					JFileChooser fc = new JFileChooser();

					JFileFilter filter = new JFileFilter();
					filter.addType("prn");
					filter.addType("PRN");
					filter.setDescription("Printer output");
					fc.addChoosableFileFilter(filter);
					
					int retVal = fc.showOpenDialog(GUIRasterizer.this);
					
					if (retVal == JFileChooser.APPROVE_OPTION)
						setInputFile(fc.getSelectedFile().getPath());
				}
			});
		}
		return btnOpen;
	}
	
	/**
	 * This method initializes btnStart	
	 * 	
	 * @return javax.swing.JButton	
	 */    
	private JButton getBtnStart() {
		if (btnStart == null) {
			btnStart = new VectorButton();
			btnStart.setText("Start");  // Generated
			btnStart.setForeground(new Color(50,50,255));
			btnStart.setBackground(Color.white);
			//btnStart.setForeground(new Color(50,255,0));

			btnStart.setPreferredSize(new java.awt.Dimension(84,26));  // Generated
			btnStart.setEnabled(false);  // Generated
			
			btnStart.addActionListener(new java.awt.event.ActionListener() 
			{ 
				public void actionPerformed(java.awt.event.ActionEvent e) 
				{    
					pagesList = new ArrayList();

					// temporary disable combo box
					importInProgress = true;
					pageNo.removeAllItems();
					importInProgress = false;
					
					pagePanel.setImg(null);
					pagePanel.repaint();
					
					startParser();
				}
			});
		}
		return btnStart;
	}
	
	private JCheckBox getBoundBox()
	{
		if (boundBox == null) {
			boundBox = new JCheckBox();
			boundBox.setText("Bound Box");
			boundBox.setBackground(new java.awt.Color(211,211,211)); 
			boundBox.setPreferredSize(new java.awt.Dimension(84,26));  // Generated
			boundBox.setSelected(true);
		}
		
		return boundBox;
	}
	
	/**
	 * This method initializes jToolBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */    
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setPreferredSize(new java.awt.Dimension(164,30));  // Generated
			jToolBar.setBackground(new java.awt.Color(211,211,211));  // Generated
			jToolBar.add(getBtnOpen());  // Generated
			jToolBar.add(getBtnStart());  // Generated
			jToolBar.add(getBoundBox());
			jToolBar.add(getPageNo());  // Generated
		}
		return jToolBar;
	}
	
	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */    
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("Page", null, getPagePanel(), null);  // Generated
			jTabbedPane.addTab("Messages", null, getMessagesPanel(), null);  // Generated
		}
		return jTabbedPane;
	}
	
	/**
	 * This method initializes pagePanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private BitmapPanel getPagePanel() {
		if (pagePanel == null) {
			pagePanel = new BitmapPanel();
			pagePanel.setName("Page");  // Generated
		}
		return pagePanel;
	}
	/**
	 * This method initializes messagesPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */    
	private JPanel getMessagesPanel() {
		if (messagesPanel == null) {
			GridLayout gridLayout1 = new GridLayout();
			messagesPanel = new JPanel();
			messagesPanel.setLayout(gridLayout1);  // Generated
			gridLayout1.setRows(3);  // Generated
			gridLayout1.setVgap(5);  // Generated
			messagesPanel.add(getNotImplementedTextPanel(), null);  // Generated
			messagesPanel.add(getAssertTextPanel(), null);  // Generated
			messagesPanel.add(getTraceTextPanel(), null);  // Generated
		}
		return messagesPanel;
	}

	/**
	 * This method initializes traceTextPanel	
	 * 	
	 * @return javax.swing.JScrollPane
	 */    
	private JScrollPane getTraceTextPanel() {
		if (traceTextPanel == null) {
			traceTextPanel = new JScrollPane(getTraceText()); 
			traceTextPanel.setBackground(new java.awt.Color(250,250,250));  // Generated
			traceTextPanel.setName("tracePanel");  // Generated
		}
		return traceTextPanel;
	}

	/**
	 * This method initializes traceText	
	 * 	
	 * @return javax.swing.JTextArea	
	 */    
	private JTextArea getTraceText() {
		if (traceText == null) {
			traceText = new JTextArea(); 
			traceText.setBackground(new java.awt.Color(250,250,250));  // Generated
			traceText.setLineWrap(false);  // Generated
			traceText.setName("trace");  // Generated
			traceText.setText("Trace messages goes here:");  // Generated
			traceText.setWrapStyleWord(true);  // Generated
		}
		return traceText;
	}
	
	/**
	 * This method initializes notImplementedTextPanel	
	 * 	
	 * @return javax.swing.JScrollPane
	 */    
	private JScrollPane getNotImplementedTextPanel() {
		if (notImplementedTextPanel == null) {
			notImplementedTextPanel = new JScrollPane(getNotImplementedText()); 
			notImplementedTextPanel.setBackground(new java.awt.Color(250,250,250));  // Generated
			notImplementedTextPanel.setName("notImplementedPanel");  // Generated
		}
		return notImplementedTextPanel;
	}

	/**
	 * This method initializes notImplementedText	
	 * 	
	 * @return javax.swing.JTextArea	
	 */    
	private JTextArea getNotImplementedText() {
		if (notImplementedText == null) {
			notImplementedText = new JTextArea();
			notImplementedText.setLineWrap(false);  // Generated
			notImplementedText.setName("notImplemented");  // Generated
			notImplementedText.setText("Not implemented messages goes here:");  // Generated
			notImplementedText.setWrapStyleWord(true);  // Generated
			notImplementedText.setBackground(new java.awt.Color(250,250,250));  // Generated
		}
		return notImplementedText;
	}
	
	/**
	 * This method initializes assertTextPanel	
	 * 	
	 * @return javax.swing.JScrollPane
	 */    
	private JScrollPane getAssertTextPanel() {
		if (assertTextPanel == null) {
			assertTextPanel = new JScrollPane(getAssertText()); 
			assertTextPanel.setBackground(new java.awt.Color(250,250,250));  // Generated
			assertTextPanel.setName("assertText");  // Generated
		}
		return assertTextPanel;
	}

	/**
	 * This method initializes assertText	
	 * 	
	 * @return javax.swing.JTextArea	
	 */    
	private JTextArea getAssertText() {
		if (assertText == null) {
			assertText = new JTextArea();
			assertText.setLineWrap(false);  // Generated
			assertText.setName("assert");  // Generated
			assertText.setText("Assert messages goes here:");  // Generated
			assertText.setWrapStyleWord(true);  // Generated
		}
		return assertText;
	}
	
	/**
	 * This method initializes pageNo	
	 * 	
	 * @return javax.swing.JComboBox	
	 */    
	private JComboBox getPageNo() {
		if (pageNo == null) 
		{
			pageNo = new JComboBox();
			pageNo.setName("pageNo");  // Generated
			
			pageNo.addActionListener(new java.awt.event.ActionListener() 
			{ 
				public void actionPerformed(java.awt.event.ActionEvent e) 
				{
					if (!importInProgress)
					{
						currentPage = (BufferedImage)pagesList.get(pageNo.getSelectedIndex());
						pagePanel.setImg(currentPage);
						pagePanel.repaint();
					}
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
				}
			});
		}
		return pageNo;
	}
  }
