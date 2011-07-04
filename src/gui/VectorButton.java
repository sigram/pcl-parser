// from Swing Hacks

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;

public class VectorButton extends JButton implements MouseListener {
	public VectorButton() {
		this.addMouseListener(this);
	}

	public Dimension getPreferredSize() {
		String text = getText();
		FontMetrics fm = this.getFontMetrics(getFont());
		float scale = (50f / 30f) * this.getFont().getSize2D();
		int w = fm.stringWidth(text);
		w += (int) (scale * 1.4f);
		int h = fm.getHeight();
		h += (int) (scale * .3f);
		return new Dimension(w, h);
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(this.getBackground());
		g2.fillRect(0, 0, this.getWidth(), this.getHeight());

		float scale = (50f / 30f) * this.getFont().getSize2D();

		drawLiquidButton(this.getForeground(), this.getWidth(), this
				.getHeight(), getText(), scale, g2);
	}

	protected void drawLiquidButton(Color base, int width, int height,
			String text, float scale, Graphics2D g2) {

		// calculate inset
		int inset = (int) (scale * 0.04f);
		int w = width - inset * 2 - 1;
		int h = height - (int) (scale * 0.1f) - 1;

		g2.translate(inset, 0);
		drawDropShadow(w, h, scale, g2);

		if (pressed) {
			g2.translate(0, 0.04f * scale);
		}

		drawButtonBody(w, h, scale, base, g2);
		drawText(w, h, scale, text, g2);
		drawHighlight(w, h, scale, base, g2);
		drawBorder(w, h, scale, g2);

		if (pressed) {
			g2.translate(0, 0.04f * scale);
		}
		g2.translate(-inset, 0);
	}

	protected void drawDropShadow(int w, int h, float scale, Graphics2D g2) {
		// draw the outer drop shadow
		g2.setColor(new Color(0, 0, 0, 50));
		VectorButton.fillRoundRect(g2, (-.04f) * scale, (.02f) * scale, w + .08f
				* scale, h + 0.08f * scale, scale * 1.04f, scale * 1.04f);
		g2.setColor(new Color(0, 0, 0, 100));
		VectorButton.fillRoundRect(g2, 0, 0.06f * scale, w, h, scale, scale);
	}

	protected void drawButtonBody(int w, int h, float scale, Color base,
			Graphics2D g2) {
		// draw the button body
		Color grad_top = base.brighter();
		Color grad_bot = base.darker();
		GradientPaint bg = new GradientPaint(new Point(0, 0), grad_top,
				new Point(0, h), grad_bot);
		g2.setPaint(bg);
		VectorButton.fillRoundRect(g2, (0) * scale, (0) * scale, w, h, 1 * scale,
				1 * scale);

		// draw the inner color
		Color inner = base.brighter();
		inner = alphaColor(inner, 75);
		g2.setColor(inner);
		VectorButton.fillRoundRect(g2, scale * (.4f), scale * (.4f), w - scale * .8f, h
				- scale * .5f, scale * .6f, scale * .4f);
	}

	protected void drawText(int w, int h, float scale, String text,
			Graphics2D g2) {
		// calculate the width and height
		int fw = g2.getFontMetrics().stringWidth(text);
		int fh = g2.getFontMetrics().getAscent()
				- g2.getFontMetrics().getDescent();
		int textx = (w - fw) / 2;
		int texty = h / 2 + fh / 2;

		// draw the text
		g2.setColor(new Color(0, 0, 0, 70));
		g2.drawString(text, (int) ((float) textx + scale * (0.04f)),
				(int) ((float) texty + scale * (0.04f)));
		g2.setColor(Color.black);
		g2.drawString(text, textx, texty);
	}

	protected void drawHighlight(int w, int h, float scale, Color base, Graphics2D g2) {
		// create the highlight
		GradientPaint highlight = new GradientPaint(new Point2D.Float(
				scale * 0.2f, scale * 0.2f), new Color(255, 255, 255, 175),
				new Point2D.Float(scale * 0.2f, scale * 0.55f), new Color(255,
						255, 255, 0));
		g2.setPaint(highlight);
		VectorButton.fillRoundRect(g2, scale * 0.2f, scale * 0.1f, w - scale * 0.4f,
				scale * 0.4f, scale * 0.8f, scale * 0.4f);
		VectorButton.drawRoundRect(g2, scale * 0.2f, scale * 0.1f, w - scale * 0.4f,
				scale * 0.4f, scale * 0.8f, scale * 0.4f);
	}

	protected void drawBorder(int w, int h, float scale, Graphics2D g2) {
		// draw the border
		g2.setColor(new Color(0, 0, 0, 150));
		VectorButton.drawRoundRect(g2, scale * (0f), scale * (0f), w, h, scale, scale);
	}

	// float version of fill round rect
	protected static void fillRoundRect(Graphics2D g2, float x, float y,
			float w, float h, float ax, float ay) {
		g2
				.fillRoundRect((int) x, (int) y, (int) w, (int) h, (int) ax,
						(int) ay);
	}

	// float version of draw round rect
	protected static void drawRoundRect(Graphics2D g2, float x, float y,
			float w, float h, float ax, float ay) {
		g2
				.drawRoundRect((int) x, (int) y, (int) w, (int) h, (int) ax,
						(int) ay);
	}

	// generate the alpha version of this color
	protected static Color alphaColor(Color color, int alpha) {
		return new Color(color.getRed(), color.getGreen(), color.getBlue(),
				alpha);
	}

	/* mouse listener implementation */
	protected boolean pressed = false;

	public void mouseExited(MouseEvent evt) {
	}

	public void mouseEntered(MouseEvent evt) {
	}

	public void mouseClicked(MouseEvent evt) {
	}

	public void mouseReleased(MouseEvent evt) {
		pressed = false;
	}

	public void mousePressed(MouseEvent evt) {
		pressed = true;
	}

	public static void p(String s) {
		System.out.println(s);
	}
}
