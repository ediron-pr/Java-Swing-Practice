package my;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import af.swing.AfPanel;
import af.swing.layout.AfColumnLayout;
import af.swing.layout.AfRowLayout;

public class AddDialog extends JDialog
{
	JTextField id = new JTextField(20);
	JTextField name = new JTextField(20);
	JComboBox<String> sex = new JComboBox<>(); 
	JTextField phone = new JTextField(20);
	
	JButton ok = new JButton("确定");
	boolean isOK = false;
	
	public AddDialog(JFrame owner)
	{
		super(owner,"添加学生信息",true);
		this.setSize(300, 300);
		
		AfPanel panel = new AfPanel();
		panel.setLayout(new AfColumnLayout(10));
		this.setContentPane(panel);
		panel.padding(10);
		
		AfPanel main = new AfPanel();
		panel.add(main,"1w");
		main.setLayout(new AfColumnLayout(10));
		main.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		main.padding(10);
		
		if(true)
		{
			AfPanel row = new AfPanel();
			main.add(row,"24px");
			row.setLayout(new AfRowLayout(10));
			row.add(new JLabel("学号"),"50px");
			row.add(id,"1w");
		}
		if(true)
		{
			AfPanel row = new AfPanel();
			main.add(row,"24px");
			row.setLayout(new AfRowLayout(10));
			row.add(new JLabel("姓名"),"50px");
			row.add(name,"1w");
		}
		if(true)
		{
			AfPanel row = new AfPanel();
			main.add(row,"24px");
			row.setLayout(new AfRowLayout(10));
			row.add(new JLabel("性别"),"50px");
			row.add(sex,"1w");
			sex.addItem("男");
			sex.addItem("女");
		}
		if(true)
		{
			AfPanel row = new AfPanel();
			main.add(row,"24px");
			row.setLayout(new AfRowLayout(10));
			row.add(new JLabel("电话号"),"50px");
			row.add(phone,"1w");
		}
		
		AfPanel bottom  = new AfPanel();
		panel.add(bottom,"30px");
		bottom.setLayout(new AfRowLayout(10));
		bottom.add(new JLabel(),"1w");
		bottom.add(ok,"auto");
		
		ok.addActionListener((e) ->{
			if(checkValid())
			{
				isOK = true;
				setVisible(false);
			}
		});
	}
	
	public boolean exe()
	{
		Rectangle rect = this.getOwner().getBounds();
		int width = this.getWidth();
		int height = this.getHeight();
		int x = rect.x+(rect.width-width)/2;
		int y = rect.y+(rect.height-height)/2;
		this.setBounds(x, y, width, height);
		setVisible(true);
		return isOK;
	}
	
	private boolean checkValid()
	{
		Student s = getValue();
		if(s.id.isEmpty())
		{
			JOptionPane.showMessageDialog(this, "学号不能为空!");
			return false;
		}
		if(s.name.isEmpty())
		{
			JOptionPane.showMessageDialog(this, "姓名不能为空!");
			return false;
		}
		return true;
	}
	
	public void setValue(Student s)
	{
		this.id.setText(s.id);
		this.name.setText(s.name);
		this.sex.setSelectedItem(s.sex ? "男":"女");
		this.phone.setText(s.phone);
	}
	
	public Student getValue()
	{
		Student s = new Student(id.getText(),name.getText(),sex.getSelectedItem().equals("男"),phone.getText());
		return s;
	}
	
	
	
	
	
}
