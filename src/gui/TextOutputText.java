/*
 * Created on 2004-09-12
 *
 */

import java.util.ArrayList;

/**
 * Sample rasterizer
 * 
 * @author Piotrm
 */
public class TextOutputText
{
	/**
	 * run text-output rasterizer
	 * 
	 * @param filename file to interpret
	 */
//	static private void runText(String filename)
//	{
//		if (filename == null)
//		{
//			System.out.println("Usage: TextOutputText file");
//			return;
//		}
//
//		InputStream is;
//		try
//		{
//			is = new FileInputStream(filename);
//			
//			TextRasterizer txtRasterizer = new TextRasterizer(new PrintWriter(System.out, true));
//			
//			Interpreter interpreter = new Interpreter();
//			
//			interpreter.parse(is, txtRasterizer);
//		}
//		catch (FileNotFoundException e)
//		{
//			e.printStackTrace();
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * run graphics-output rasterizer
	 * 
	 * @param filename file to interpret
	 */
	static private void runGUI(String filename)
	{
		GUIRasterizer guiRasterizer = new GUIRasterizer();
		guiRasterizer.setInputFile(filename);
		guiRasterizer.setSize(500, 400);
		guiRasterizer.setVisible(true);
	}
	
	public static void main(String[] args)
	{
		ArrayList a = new ArrayList(100);
		for(int i = 0; i < 20; i++)
			a.add("abc");
		
		a.add(5, "cde");
		a.set(10, "fgh");
		a.set(8, null);
		a.set(8, "xxx");
		
		if (args.length == 1)
			runGUI(args[0]);
		else
			runGUI(null);

	}
}
