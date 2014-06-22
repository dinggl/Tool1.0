package ui;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import excel.Excel2007;


public class Excel2Js extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField excel, tpl, key, sheet, data;
	private JButton btn1, btn2, convert, open;
	private File save;
	private JFileChooser fileChooser = new MyFileChooser(".");  //对话框
	public Excel2Js() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(360, 240);
		this.setLayout(null);
		excel = new JTextField("excel path:");
		excel.setBounds(5, 5, 290, 30);
		btn1 = new JButton("...");
		btn1.setBounds(300, 5, 30, 30);
		this.add(excel);
		this.add(btn1);
		btn1.addActionListener(this);
		tpl = new JTextField("tpl path:");
		tpl.setBounds(5, 40, 290, 30);
		btn2 = new JButton("...");
		btn2.setBounds(300, 40, 30, 30);
		this.add(tpl);
		this.add(btn2);
		btn2.addActionListener(this);
		key = new JTextField("city");
		key.setBounds(5, 80, 60, 30);
		this.add(key);
		sheet = new JTextField("Sheet1");
		sheet.setBounds(70, 80, 60, 30);
		this.add(sheet);
		convert = new JButton("covert");
		convert.setBounds(140, 80, 120, 30);
		convert.addActionListener(this);
		this.add(convert);
		data = new JTextField(new File(".").getAbsolutePath());
		data.setBounds(5, 120, 260, 30);
		this.add(data);
		open = new JButton("open");
		open.setBounds(270, 120, 70 ,30);
		open.addActionListener(this);
		this.add(open);
		this.setResizable(false);
		this.setVisible(true);
	}
	public static void main(String args[]) {
		//C:\Users\dingye\Desktop\CFS  FFS store list.xlsx
		/* JAVA_TOOL_OPTIONS， 变量值为：-Dfile.encoding=UTF-8*/
		new Excel2Js();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton btn = (JButton) e.getSource();
		if (btn == convert) {
			String keyValue = key.getText();
			String excelValue = excel.getText();
			String tplValue = tpl.getText();
			String sheetValue = sheet.getText();
			Excel2007 excel = new Excel2007();
			try {
				excel.readExcelFromModel(excelValue, sheetValue, keyValue);
				save = excel.save2File(tplValue);
				data.setText(save.getAbsolutePath());
				if(save != null)
				JOptionPane.showMessageDialog(this, "请校验转换量:"+ excel.getCount()+"，保存文件路径为：" + save.getAbsolutePath());
			}  catch (Exception ee) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(this, "转换失败：" + ee.getMessage());
			}
		} else if (btn == btn1) {
			
            int i = fileChooser.showOpenDialog(getContentPane());  //opendialog
            if(i==JFileChooser.APPROVE_OPTION)  //判断是否为打开的按钮
            {
                File selectedFile = fileChooser.getSelectedFile();  //取得选中的文件
                excel.setText(selectedFile.getPath());   //取得路径
            }
		} else if (btn == btn2) {
			 int i = fileChooser.showOpenDialog(getContentPane());  //opendialog
	            if(i==JFileChooser.APPROVE_OPTION)  //判断是否为打开的按钮
	            {
	                File selectedFile = fileChooser.getSelectedFile();  //取得选中的文件
	                tpl.setText(selectedFile.getPath());   //取得路径
	            }
		} else {
			Desktop dt = Desktop.getDesktop();
			try {
				dt.open(save);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

}
