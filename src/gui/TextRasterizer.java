/*
 * Created on 2004-09-12
 *
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import org.getopt.pcl5.IPrint;
import org.getopt.pcl5.IPrinterState;


/**
 * @author Piotrm
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TextRasterizer implements IPrint  
{
	private PrintWriter writer;
	
	public TextRasterizer(PrintWriter wr)
	{
		writer = wr;
	}

	public void pageSize(float w, float h)
	{
		writer.println("New page size: [" + w + ", " + h + "]");
	}

	public void printText(float x, float y, float w, float h, String text, int[] kerning, IPrinterState state)
	{
		Font fnt = state.getFont();
		Color clr = state.getCurrentColor();
		
		writer.print("[" + x + ", " + y + "] ");
		writer.print("[" + w + ", " + h + "] ");
		writer.print("[F:" + fnt.getFontName() + ", " + fnt.getSize() + "]");
		writer.print("[C:" + clr + "]");
		writer.println("|" + text + "|");
	}

	/* (non-Javadoc)
	 * @see pl.biz.coda.ESCP2Interpreter.IPrint#PrintBitmap(float, float, java.awt.Image, pl.biz.coda.ESCP2Interpreter.IPrinterState)
	 */
	public void printBitmap(float x, float y, float w, float h, BufferedImage image, IPrinterState state)
	{
		writer.print("[" + x + ", " + y + "] ");
		writer.print("[" + w + ", " + h + "] ");
		writer.print("[" + image.getWidth() + ", " + image.getHeight() + "]");
		writer.println(" bitmap");
	}

	/* (non-Javadoc)
	 * @see com.ccginc.escp.ESCP2Interpreter.IPrint#trace(java.lang.String, java.lang.String)
	 */
	public void trace(Object command, String message)
	{
		writer.println("**TRACE** " + command.toString() + " " + message);
	}

	/* (non-Javadoc)
	 * @see com.ccginc.escp.ESCP2Interpreter.IPrint#assertCondition(java.lang.String, java.lang.String)
	 */
	public void assertCondition(Object command, String message)
	{
		writer.println("**ASSERT** " + command.toString() + " " + message);
	}

	/* (non-Javadoc)
	 * @see com.ccginc.escp.ESCP2Interpreter.IPrint#notImplemented(java.lang.String, java.lang.String)
	 */
	public void notImplemented(Object command, String message)
	{
		writer.println("**NOT IMPLEMENTED** " + command.toString() + " " + message);
	}

	/* (non-Javadoc)
	 * @see com.ccginc.escp.ESCP2Interpreter.IPrint#newPage()
	 */
	public void newPage()
	{
		writer.println("########## NEW PAGE ##########");
	}

	/* (non-Javadoc)
	 * @see com.ccginc.escp.ESCP2Interpreter.IPrint#newMargins(float, float, float, float)
	 */
	public void newMargins(float top, float bottom, float left, float right)
	{
		writer.println("New margins set: [" + top + " " + bottom + " " + left + " " + right + "]");
	}

	/* (non-Javadoc)
	 * @see com.ccginc.escp.ESCP2Interpreter.IPrint#processingStart()
	 */
	public void processingStart()
	{
		writer.println("*** Process start ***");
	}

	/* (non-Javadoc)
	 * @see com.ccginc.escp.ESCP2Interpreter.IPrint#processingEnd()
	 */
	public void processingEnd()
	{
		writer.println("*** Process end ***");
	}

}
