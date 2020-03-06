package my;

import javax.swing.JFrame;
public class SwingDemo1
{
	private static void createGUI()
	{
		MyFrame1 frame = new MyFrame1("学生信息管理系统");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		frame.setSize(600, 400);
			
		frame.setVisible(true);
	}
		
	public static void main(String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
				createGUI();
			}
	});

		}
}
