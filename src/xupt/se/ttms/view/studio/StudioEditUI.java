
package xupt.se.ttms.view.studio;


import javax.swing.JOptionPane;

import xupt.se.ttms.model.Studio;
import xupt.se.ttms.service.StudioSrv;
import xupt.se.ttms.view.studio.StudioAddUI;;

public class StudioEditUI extends StudioAddUI{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Studio stud;

	public StudioEditUI(Studio stu){
		initData(stu);
	}
	
	public void initData(Studio stu) {
		if(null== stu){
			return;
		}
		txtName.setText(stu.getName());
		txtRow.setText(Integer.toString(stu.getRowCount()));
		txtColumn.setText(Integer.toString(stu.getColCount()));
		txtIntro.setText(stu.getIntroduction());
		stud=stu;
		this.invalidate();
	}

	@Override
	protected void btnSaveClicked(){
		if (txtName.getText() != null && txtRow.getText() != null
				&& txtColumn.getText() != null) {
			StudioSrv stuSrv = new StudioSrv();
			Studio stu= stud;
			stu.setName(txtName.getText());
			stu.setRowCount(Integer.parseInt(txtRow.getText()));
			stu.setColCount(Integer.parseInt(txtColumn.getText()));
			stu.setIntroduction(txtIntro.getText());
			stuSrv.modify(stu);
			this.setVisible(false);
			rst=true;
			
		} else {
			JOptionPane.showMessageDialog(null, "数据不完整");
		}		
	}
	
}


//package xupt.se.ttms.view.studio;
//
//import javax.swing.JDialog;
//
//import java.awt.Color;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//
//import javax.swing.ImageIcon;
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JTextField;
//
////import view.studioUI.ImageJPanel;
//import xupt.se.ttms.model.Studio;
//import xupt.se.ttms.service.StudioSrv;
//import xupt.se.ttms.view.studio.StudioAddUI;
//import xupt.se.ttms.view.tmpl.PopUITmpl;;
//
//public class StudioEditUI extends PopUITmpl implements ActionListener{
//
//
//	private JButton btnCancel, btnSave; 	//取消，保存按鈕
//
//	protected boolean rst=false; 				//操作结果
//	private JLabel lblName, lblRow, lblColumn,introduction;
//	protected JTextField txtName, txtRow, txtColumn,txtintroduction;
//	
//	
//	protected void onWindowClosing(){
//		this.dispose();
//	}
//	
//	public StudioEditUI(Studio stu) {
//		this.setTitle("修改演出厅");
//		initData(stu);
//	}
//
//	private void initData(Studio stu) {
//		txtName.setText(stu.getName());
//		txtRow.setText(Integer.toString(stu.getRowCount()));
//		txtColumn.setText(Integer.toString(stu.getColCount()));
//		txtintroduction.setText(stu.getIntroduction());
//	}
//
//
//	protected void btnSaveClicked(){
//		if (txtName.getText() != null && txtRow.getText() != null
//				&& txtColumn.getText() != null) {
//			StudioSrv stuSrv = new StudioSrv();
//			Studio stu=new Studio();
//			stu.setName(txtName.getText());
//			stu.setRowCount(Integer.parseInt(txtRow.getText()));
//			stu.setColCount(Integer.parseInt(txtColumn.getText()));
//			stu.setIntroduction(txtintroduction.getText());
//
//			stuSrv.modify(stu);
//			this.setVisible(false);
////			this.dispose();
//			rst=true;
////			getParent().setVisible(true);
//		} else {
//			JOptionPane.showMessageDialog(null, "数据不完整");
//		}		
//	}
//
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		// TODO Auto-generated method stub
//		if (e.getSource() == btnCancel) {
//			rst=false;
////			this.dispose();
//			this.setVisible(false);
////			getParent().setVisible(true);
//
//		} else if (e.getSource() == btnSave) {
//			btnSaveClicked();		//以前未调用，新添加的调用语句
//		}
//		
//	}
//	
//	@Override
//	protected void initContent(){
//		this.setTitle("添加演出厅");
//
//		lblName = new JLabel("演出厅名称：");
//		lblName.setBounds(60, 30, 80, 30);
//		contPan.add(lblName);
//		txtName = new JTextField();
//		txtName.setBounds(150, 30, 120, 30);
//		contPan.add(txtName);
//
//		lblRow = new JLabel("座位行数：");
//		lblRow.setBounds(60, 80, 50, 30);
//		contPan.add(lblRow);
//		txtRow = new JTextField();
//		txtRow.setBounds(150, 80, 120, 30);
//		contPan.add(txtRow);
//
//		lblColumn = new JLabel("座位列数：");
//		lblColumn.setBounds(60, 130, 90, 30);
//		contPan.add(lblColumn);
//		txtColumn = new JTextField();
//		txtColumn.setBounds(150, 130, 120, 30);
//		contPan.add(txtColumn);
//
//		
//		introduction = new JLabel("演出厅简介：");
//		introduction.setBounds(60, 180, 80, 30);
//		contPan.add(introduction);
//		txtintroduction = new JTextField();
//		txtintroduction.setBounds(150, 180, 120, 30);
//		contPan.add(txtintroduction);
//
//		
//		btnSave = new JButton("保存");
//
//		btnSave.addActionListener(this);
//		btnSave.setBounds(60, 220, 60, 30);
//		contPan.add(btnSave);
//
//		btnCancel = new JButton("取消");
//		btnCancel.addActionListener(this);
//		btnCancel.setBounds(180, 220, 60, 30);
//		contPan.add(btnCancel);
////
////		ImageJPanel imageJP = new ImageJPanel(new ImageIcon(
////				"files/imgs/pencil.jpg").getImage());
////		imageJP.setBounds(360, 160, 100, 100);
////		imageJP.setLayout(null);
////		this.add(imageJP);
//	}
//	public boolean getReturnStatus(){
//		   return rst;
//	}
//
//}
