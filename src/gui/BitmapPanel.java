/*
 * Created on 2004-09-26
 *
 */

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * @author Piotrm
 */
public class BitmapPanel extends JPanel
{
	private Image _img;
	
	/** Called when the window needs painting.
	* Computes X and Y range, scales.
	*/
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);

		if (_img != null)
			g.drawImage(_img, 0, 0, null);
	}
	
	/**
	 * @return Returns the img.
	 */
	public Image getImg()
	{
		return _img;
	}
	/**
	 * @param img The img to set.
	 */
	public void setImg(Image img)
	{
		this._img = img;
	}
}
