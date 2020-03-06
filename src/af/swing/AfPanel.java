package af.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import ed.swing.border.EdBorder;

/* Swing入门篇 7.5 节 */

public class AfPanel extends JPanel
{
	public Color bgColor;
	
	public AfPanel()
	{
		this.setOpaque(false); // 默认透明 
	}
	
	public void setBgColor(Color color)
	{
		this.bgColor = color;
		this.repaint();
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		// 绘制背景色
		if( bgColor != null)
		{
			Graphics2D g2d = (Graphics2D)g;	
			g2d.setPaint(bgColor);
			g2d.fillRect(0, 0, getWidth(), getHeight());
		}	
	}
	
	public AfPanel layout( LayoutManager manager)
	{
		this.setLayout(manager);
		return this;
	}
	
	///////////// padding /////////////
	public AfPanel padding( int size)
	{
		return padding(size, size, size, size);
	}
	
	public AfPanel padding( int top, int left, int bottom, int right)
	{
		EdBorder.addPadding(this, top,left, bottom,right);
		return this;
	}

	////////////// margin ////////////////
	public AfPanel margin( int size)
	{
		return margin(size, size, size, size);
	}
	
	public AfPanel margin( int top, int left, int bottom, int right)
	{
		EdBorder.addMargin(this, top,left, bottom,right);
		return this;
	}
	
	//////////// max size ///////////
	public AfPanel maxSize(int w, int h)
	{
		this.setMaximumSize(new Dimension(w, h));
		return this;
	}
	
	public AfPanel maxWidth(int w)
	{
		Dimension size = this.getMaximumSize();
		if(size == null)
			size = new Dimension(0,0);
		size.width = w;
		this.setMaximumSize( size);
		return this;
	}
	
	public AfPanel maxHeight(int h)
	{
		Dimension size = this.getMaximumSize();
		if(size == null)
			size = new Dimension(0,0);
		size.height = h;
		this.setMaximumSize( size);
		return this;
	}
	
	//////////// perferred size ///////////
	public AfPanel preferredSize(int w, int h)
	{
		this.setPreferredSize(new Dimension(w, h));
		return this;
	}
	
	public AfPanel preferredWidth(int w)
	{
		Dimension size = this.getPreferredSize();
		if(size == null)
			size = new Dimension(0,0);
		size.width = w;
		this.setPreferredSize( size);
		return this;
	}
	
	public AfPanel preferredHeight(int h)
	{
		Dimension size = this.getPreferredSize();
		if(size == null)
			size = new Dimension(0,0);
		size.height = h;
		this.setPreferredSize( size);
		return this;
	}

	//////////// min size ///////////
	public AfPanel minSize(int w, int h)
	{
		this.setMinimumSize(new Dimension(w, h));
		return this;
	}
	
	public AfPanel minWidth(int w)
	{
		Dimension size = this.getMinimumSize();
		if(size == null)
			size = new Dimension(0,0);
		size.width = w;
		this.setMinimumSize( size);
		return this;
	}
	
	public AfPanel minHeight(int h)
	{
		Dimension size = this.getMinimumSize();
		if(size == null)
			size = new Dimension(0,0);
		size.height = h;
		this.setMinimumSize( size);
		return this;
	}
	
}
