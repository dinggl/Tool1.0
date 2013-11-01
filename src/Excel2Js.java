import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import excel.Excel2007;


public class Excel2Js extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField excel, tpl, key, sheet;
	public Excel2Js() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(320, 240);
		this.setLayout(null);
		excel = new JTextField("excel path:");
		excel.setBounds(5, 5, 290, 30);
		this.add(excel);
		tpl = new JTextField("tpl path:");
		tpl.setBounds(5, 40, 290, 30);
		this.add(tpl);
		key = new JTextField("city");
		key.setBounds(5, 80, 60, 30);
		this.add(key);
		sheet = new JTextField("Sheet1");
		sheet.setBounds(70, 80, 60, 30);
		this.add(sheet);
		JButton jb = new JButton("covert");
		jb.setBounds(140, 80, 120, 30);
		jb.addActionListener(this);
		this.add(jb);
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
		String keyValue = key.getText();
		String excelValue = excel.getText();
		String tplValue = tpl.getText();
		String sheetValue = sheet.getText();
		Excel2007 excel = new Excel2007();
		try {
			excel.readExcelFromModel(excelValue, sheetValue, keyValue);
			File save = excel.save2File(tplValue);
			if(save != null)
			JOptionPane.showMessageDialog(this, "请校验转换量:"+ excel.getCount()+"，保存文件路径为：" + save.getAbsolutePath());
		}  catch (Exception ee) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this, "转换失败：" + ee.getMessage());
		}
	}

}
