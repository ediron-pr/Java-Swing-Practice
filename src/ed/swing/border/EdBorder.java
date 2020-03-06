package ed.swing.border;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;

public class EdBorder
{
	//设置内间距
	public static void addPadding(JComponent c,int size)
	{
		addInnerBorder(c,BorderFactory.createEmptyBorder(size, size, size, size));
	}
	public static void addPadding(JComponent c,int top,int left,int bottom,int right)
	{
		addInnerBorder(c,BorderFactory.createEmptyBorder(top,left,bottom,right));
	}
	
	//设置外间距
	public static void addMargin(JComponent c,int size)
	{
		addOuterBorder(c,BorderFactory.createEmptyBorder(size, size, size, size));	
	}
	public static void addMargin(JComponent c,int top,int left,int bottom,int right)
	{
		addOuterBorder(c,BorderFactory.createEmptyBorder(top, left, bottom, right));
	}
	
	//附加外边框
	public static void addOuterBorder(JComponent c,Border outerBorder)
	{
		Border border = c.getBorder();
		if(border!=null)
		{
			//如果原来有边框，则进行复合
			border =BorderFactory.createCompoundBorder(outerBorder, border);
			c.setBorder(border);
		}
		else
		{
			c.setBorder(outerBorder);
		}
	}
	//附加内边框
	public static void addInnerBorder(JComponent c,Border innerBorder)
	{
		Border border = c.getBorder();
		if(border!=null)
		{
			//原来有边框，则复合
			border = BorderFactory.createCompoundBorder(border, innerBorder);
			c.setBorder(border);
		}
		else
		{
			c.setBorder(innerBorder);
		}
	}
}
