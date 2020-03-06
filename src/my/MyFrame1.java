package my;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import af.common.json.AfJSON;
import af.swing.AfPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.json.JSONArray;
import org.json.JSONObject;


public class MyFrame1 extends JFrame
{
	DefaultTableModel tableModel = new DefaultTableModel();
	JTable table = null;
	
	JButton add = new JButton("添加", new ImageIcon(getClass().getResource("/icon/add.png")));
	JButton delete = new JButton("删除",new ImageIcon(getClass().getResource("/icon/delete.png")));
	JButton modify = new JButton("修改",new ImageIcon(getClass().getResource("/icon/modify.png")));
	
	List<Student> dataList = new ArrayList<>();
	JTextField search = new JTextField();
	
	public MyFrame1(String title)
	{
		super(title);
		
		AfPanel root = new AfPanel();
		this.setContentPane(root);
		root.setLayout(new BorderLayout());
		root.padding(10);
		
		initTable(root);
		initTool(root);
		
		loadData();
		
	}
	
	private void initTable(AfPanel root)
	{
		table = new JTable(tableModel) {
	
			@Override
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
			
		};
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setRowHeight(30);
		root.add(scrollPane,BorderLayout.CENTER);
		
		tableModel.addColumn("学号");
		tableModel.addColumn("姓名");
		tableModel.addColumn("性别");
		tableModel.addColumn("手机号");
		
		table.getColumnModel().getColumn(2).setCellRenderer(new SexCellRenderer());
		table.getColumnModel().getColumn(0).setCellRenderer(new IDCellRenderer());
		table.getColumnModel().getColumn(0).setPreferredWidth(110);
		
	}

	private void initTool(AfPanel root)
	{
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		root.add(toolBar,BorderLayout.PAGE_START);
		
		
		toolBar.add(add);
		toolBar.add(delete);
		toolBar.add(modify);
		add.setFocusPainted(false);
		delete.setFocusPainted(false);
		modify.setFocusPainted(false);
		add.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				showDialog();
			}
		});
		
		delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				deleteRow();
			}
		});
		modify.addActionListener((e) ->{
			modifyRow();
		});
		
		toolBar.addSeparator(new Dimension(40, 10));
		toolBar.add(new JLabel("查询"));
		toolBar.add(search);
		search.setMaximumSize(new Dimension(120, 25));
		search.addActionListener( (e) ->{
			search();
		});
		
	}
	
	
	private class SexCellRenderer extends JLabel implements TableCellRenderer
	{
		public SexCellRenderer()
		{
			this.setHorizontalAlignment(SwingConstants.CENTER);
			this.setFont(this.getFont().deriveFont(Font.PLAIN));
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column)
		{
			Boolean sex = (Boolean)value;
			if(sex!=null && sex)
				this.setText("男");
			else
				this.setText("女");
			
			this.setOpaque(true);
			
			if (isSelected) {
		    	this.setBackground(table.getSelectionBackground());
		    	this.setForeground(table.getSelectionForeground());
		    	
	        } else {
	        	this.setBackground(table.getBackground());
	        	this.setForeground(table.getForeground());
	        }
		
			return this;
		}
	}
	
	public class IDCellRenderer extends JCheckBox implements TableCellRenderer
	{

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column)
		{
			this.setSelected(isSelected);
			if(value!=null)
				this.setText(value.toString());
			
			this.setOpaque(true);			
		    if (isSelected) {
		    	this.setBackground(table.getSelectionBackground());
		    	this.setForeground(table.getSelectionForeground());
		    	
	        } else {
	        	this.setBackground(table.getBackground());
	        	this.setForeground(table.getForeground());
	        }
		
			return this;
		}
		
	}
	
	private void showDialog()
	{
		AddDialog addDialog = new AddDialog(this);
		if(addDialog.exe())
		{
			addRow(addDialog.getValue());
			dataList.add(addDialog.getValue());
			saveData();
		}
	}
	
	private void deleteRow()
	{
		int [] rows = table.getSelectedRows();
		if(rows.length==0)return;
		
		int select = JOptionPane.showConfirmDialog(this, "确认要删除吗？", "删除所选"+rows.length+"项", JOptionPane.YES_NO_OPTION);
		if(select==0)
		{
			for(int i=rows.length-1;i>=0;i--)
			{
				String id = (String)tableModel.getValueAt(rows[i], 0);
				removeData(id);
				tableModel.removeRow(rows[i]);
			}
		}
		saveData();
	}

	private void modifyRow()
	{
		int [] rows = table.getSelectedRows();
		if(rows.length==0)return;
		
		int row =rows[0];
		Student s = getTableRow(row);
		
		AddDialog dia = new AddDialog(this);
		dia.setValue(s);
		if(dia.exe())
		{
			editData(dia.getValue());
			setTableRow(dia.getValue(), row);
			saveData();
		}
	}

	private void search()
	{
		String filter = search.getText().trim();
		
		if(filter.length()==0)
		{
			tableModel.setRowCount(0);
			for(Student s:dataList)
			{
				addRow(s);
			}
			
			this.add.setEnabled(true);
			return;
		}
		
		tableModel.setRowCount(0);
		for(Student s:dataList)
		{
			if(s.name.indexOf(filter)>=0)
			{
				addRow(s);
			}
		}
		
		this.add.setEnabled(false);
	}

	private void loadData()
	{
		File file = new File("students.json");
		if(!file.exists())return;
		
		JSONArray array= null;
		
		try
		{
			array = (JSONArray)AfJSON.fromFile(file, "UTF-8");
		}catch(Exception e)
		{
			JOptionPane.showMessageDialog(this, e.getMessage());
			e.printStackTrace();
			return;
		}
		
		//读取文件并显示
		dataList.clear();
		tableModel.setRowCount(0);
		for(int i=0;i<array.length();i++)
		{
			JSONObject j1 = array.getJSONObject(i);
			Student s = new Student();
			s.id = j1.getString("id");
			s.name = j1.getString("name");
			s.sex = j1.getBoolean("sex");
			s.phone = j1.getString("phone");
			
			dataList.add(s);
			addRow(s);
		}
	}

	private void saveData()
	{
		JSONArray array = new JSONArray();
		for(int i=0;i<dataList.size();i++)
		{
			Student s = dataList.get(i);
			JSONObject j1 = new JSONObject();
			j1.put("id", s.id);
			j1.put("name", s.name);
			j1.put("sex", s.sex);
			j1.put("phone", s.phone);
			
			array.put(j1);
		}
		File file = new File("students.json");
		try
		{
			AfJSON.toFile(array, file, "UTF-8");
		}catch(Exception e)
		{
			JOptionPane.showMessageDialog(this, e.getMessage());
			e.printStackTrace();
		}
	}

	private void removeData(String id)
	{
		Iterator<Student> iter = dataList.iterator();
		while(iter.hasNext())
		{
			Student s = iter.next();
			if(s.id.equals(id))
			{
				iter.remove();
				break;
			}
		}
	}

	private void editData(Student s)
	{
		for(int i=0;i<dataList.size();i++)
		{
			if(dataList.get(i).id.equals(s.id))
			{
				dataList.set(i, s);
			}	
		}
	}

	private void addRow(Student stu)
	{
		Vector<Object> rowData = new Vector<>();
		rowData.add(stu.id);
		rowData.add(stu.name);
		rowData.add(stu.sex);
		rowData.add(stu.phone);
		tableModel.addRow(rowData);
	}
	
	private Student getTableRow(int row)
	{
		Student s = new Student();
		s.id = (String)tableModel.getValueAt(row, 0);
		s.name = (String)tableModel.getValueAt(row, 1);
		s.sex = (boolean)tableModel.getValueAt(row, 2);
		s.phone = (String)tableModel.getValueAt(row, 3);
		return s;
	}
	
	private void setTableRow(Student s,int row)
	{
		tableModel.setValueAt(s.id, row, 0);
		tableModel.setValueAt(s.name, row, 1);
		tableModel.setValueAt(s.sex, row, 2);
		tableModel.setValueAt(s.phone, row, 3);
	}
}
