﻿package xupt.se.ttms.view.studio;

import java.awt.Color;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.lang.model.type.TypeKind;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import java.util.List;
import java.util.Iterator;

import xupt.se.ttms.model.Studio;
import xupt.se.ttms.service.StudioSrv;
import xupt.se.ttms.view.login.SystemMgUI;
import xupt.se.ttms.view.tmpl.*;

class StudioTable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable jt;

	public StudioTable(JScrollPane jp) {
		
		DefaultTableModel tabModel=new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			@Override              
			public boolean isCellEditable(int row,int column){
				return false;              
			};
		};
		tabModel.addColumn("id");
		tabModel.addColumn("name");
		tabModel.addColumn("row");
		tabModel.addColumn("column");
		tabModel.addColumn("desciption");
		//初始化列明
		jt=new JTable(tabModel);	
		
		//设置各列的宽度
	    TableColumnModel columnModel = jt.getColumnModel();
	    
	    //隐藏ID这一列
        TableColumn column = columnModel.getColumn(0);
//        column.setMinWidth(0);
//        column.setMaxWidth(0);
//        column.setWidth(0);
        column.setPreferredWidth(10);

        column = columnModel.getColumn(1);
        column.setPreferredWidth(10);
        column = columnModel.getColumn(2);
        column.setPreferredWidth(10);
        column = columnModel.getColumn(3);
        column.setPreferredWidth(10);
        column = columnModel.getColumn(4);
        column.setPreferredWidth(500);        

		
		jp.add(jt);
		jp.setViewportView(jt);
		
	}
	
	public Studio getStudio() {
		int rowSel=jt.getSelectedRow();
		if(rowSel>=0){
			Studio stud = new Studio();
			stud.setID(Integer.parseInt(jt.getValueAt(rowSel, 0).toString()));
			stud.setName(jt.getValueAt(rowSel, 1).toString());
			stud.setRowCount(Integer.parseInt(jt.getValueAt(rowSel, 2).toString())); // 0
			stud.setColCount(Integer.parseInt(jt.getValueAt(rowSel, 3).toString()));
			if (jt.getValueAt(rowSel, 4) != null)
				stud.setIntroduction(jt.getValueAt(rowSel, 4).toString());
			else
				stud.setIntroduction("");

			return stud;
		}
		else{
			return null;
		}
			
	}
	
	// 创建JTable
	public void showStudioList(List<Studio> stuList) {
		try {
			DefaultTableModel tabModel = (DefaultTableModel) jt.getModel();
			tabModel.setRowCount(0);
			
			Iterator<Studio> itr = stuList.iterator();
			while (itr.hasNext()) {
				Studio stu = itr.next();
				Object data[] = new Object[5];
				data[0] = Integer.toString(stu.getID());
				data[1] = stu.getName();
				data[2] = Integer.toString(stu.getRowCount());
				data[3] = Integer.toString(stu.getColCount());
				data[4] = stu.getIntroduction();
				tabModel.addRow(data);;
			}
			jt.invalidate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

public class StudioMgrUI extends MainUITmpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel ca1 = null; // 界面提示
	// 用来放表格的滚动控件
	private JScrollPane jsc;
	// 查找的提示和输出
	private JLabel hint;
	private JTextField input;

	// 查找，编辑和删除按钮
	private JButton btnAdd, btnEdit, btnDel, btnQuery;
	
	StudioTable tms; //显示演出厅列表


	public StudioMgrUI() {
		
	}

	// To be override by the detailed business block interface
	@Override
	protected void initContent() {
		Rectangle rect = contPan.getBounds();

		ca1 = new JLabel("演出厅管理", JLabel.CENTER);
		ca1.setBounds(0, 5, rect.width, 30);
		ca1.setFont(new java.awt.Font("宋体", 1, 20));
		ca1.setForeground(Color.blue);
		contPan.add(ca1);

		jsc = new JScrollPane();
		jsc.setBounds(0, 40, rect.width, rect.height - 90);
		contPan.add(jsc);

		hint = new JLabel("请输入演出厅名称:", JLabel.RIGHT);
		hint.setBounds(60, rect.height - 45, 150, 30);
		contPan.add(hint);

		input = new JTextField();
		input.setBounds(220, rect.height - 45, 200, 30);
		contPan.add(input);

		// 查找 ，删除和编辑的按钮，其中含有相关的事件处理！
		btnQuery = new JButton("查找");
		btnQuery.setBounds(440, rect.height - 45, 60, 30);
		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnQueryClicked();
			}
		});
		contPan.add(btnQuery);

		btnAdd = new JButton("添加");
		btnAdd.setBounds(rect.width - 220, rect.height - 45, 60, 30);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnAddClicked();
			}
		});
		contPan.add(btnAdd);

		btnEdit = new JButton("修改");
		btnEdit.setBounds(rect.width - 150, rect.height - 45, 60, 30);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnModClicked();
			}
		});
		contPan.add(btnEdit);

		btnDel = new JButton("删除");
		btnDel.setBounds(rect.width - 80, rect.height - 45, 60, 30);
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnDelClicked();
			}
		});
		contPan.add(btnDel);
		contPan.add(ca1);
		
		tms = new StudioTable(jsc);
		
		showTable();
	}

	private void btnAddClicked() {

		StudioAddUI addStuUI=null;
		
		addStuUI = new StudioAddUI();
		addStuUI.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		addStuUI.setWindowName("添加演出厅");
		addStuUI.toFront();
		addStuUI.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
		addStuUI.setVisible(true);
		if (addStuUI.getReturnStatus()) {
			showTable();
		}
	}

	private void btnModClicked() {
		Studio stud = tms.getStudio();
		if(null== stud){
			JOptionPane.showMessageDialog(null, "请选择要修改的演出厅");
			return; 
		}
		
		StudioEditUI modStuUI = new StudioEditUI(stud);
		modStuUI.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		modStuUI.setWindowName("修改演出厅");
		modStuUI.initData(stud);
		modStuUI.toFront();
		modStuUI.setModal(true);
		modStuUI.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
		modStuUI.setVisible(true);

		if (modStuUI.getReturnStatus()) {
			showTable();
		}	
	}

	private void btnDelClicked() {
		Studio stud = tms.getStudio();
		if(null== stud){
			JOptionPane.showMessageDialog(null, "请选择要删除的演出厅");
			return; 
		}		
		
		int confirm = JOptionPane.showConfirmDialog(null, "确认删除所选？", "删除", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			StudioSrv stuSrv = new StudioSrv();
			stuSrv.delete(stud.getID());
			showTable();
		}
	}

	private void btnQueryClicked() {
		if (!input.getText().equals("")) {
			//请自行补充

		} else {
			JOptionPane.showMessageDialog(null, "请输入检索条件");
		}
	}

	private void showTable() {
		List<Studio> stuList = new StudioSrv().FetchAll();
		tms.showStudioList(stuList);
	}
	

	public static void main(String[] args) {
		StudioMgrUI frmStuMgr = new StudioMgrUI();
		frmStuMgr.setVisible(true);
	}
	//To be override by the detailed business block interface 
	protected void btnExitClicked(ActionEvent Event){
		new SystemMgUI().setVisible(true);
		this.dispose();
		
	}	
}


//package xupt.se.ttms.view.studio;
///**
// * 演出厅管理
// */
//import java.awt.Color;
//import java.awt.Label;
//import java.awt.Rectangle;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//
//import javax.swing.JButton;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JScrollPane;
//import javax.swing.JTable;
//import javax.swing.JTextField;
//
//import java.util.List;
//import java.util.Iterator;
//
//import xupt.se.ttms.model.Studio;
//import xupt.se.ttms.service.StudioSrv;
//import xupt.se.ttms.view.login.Login;
//import xupt.se.ttms.view.login.SystemMgUI;
//import xupt.se.ttms.view.tmpl.*;
//
//class StudioTableMouseListener extends MouseAdapter {
//
//	private JTable jt;
//	private static Studio stud;
//
//	public Studio getStudio() {
//		return stud;
//	}
//
//	public StudioTableMouseListener(JTable jt, Object[] number, Studio stud) {
//		this.stud = stud;
//		this.jt = jt;
//	}
//
//	// 监听到行号，将所选行的内容依次赋到 stud对象，以便传有值对象到修改面板进行修改
//	public void mouseClicked(MouseEvent event) {
//		int row = jt.getSelectedRow();
//		stud.setID(Integer.parseInt(jt.getValueAt(row, 0).toString()));
//		stud.setName(jt.getValueAt(row, 1).toString());
//		stud.setRowCount(Integer.parseInt(jt.getValueAt(row, 2).toString())); // 0
//		stud.setColCount(Integer.parseInt(jt.getValueAt(row, 3).toString()));
//		if (jt.getValueAt(row, 4) != null)
//			stud.setIntroduction(jt.getValueAt(row, 4).toString());
//		else
//			stud.setIntroduction("");
//		System.out.println(jt.getValueAt(row, 0).toString());
//	}
//}
//
//class StudioTable {
//
//	private Studio stud;
//	private JTable jt = null;
//
//	public StudioTable(Studio stud) {
//		this.stud = stud;
//	}
//
//	// 创建JTable
//	public void createTable(JScrollPane jp, Object[] columnNames, List<Studio> stuList) {
//		try {
//
//			Object data[][] = new Object[stuList.size()][columnNames.length];
//
//			Iterator<Studio> itr = stuList.iterator();
//			int i = 0;
//			while (itr.hasNext()) {
//				Studio stu = itr.next();
//				data[i][0] = Integer.toString(stu.getID());
//				data[i][1] = stu.getName();
//				data[i][2] = Integer.toString(stu.getRowCount());
//				data[i][3] = Integer.toString(stu.getColCount());
//				data[i][4] = stu.getIntroduction();
//				i++;
//			}
//
//			// 生成JTable
//			jt = new JTable(data, columnNames);
//			jt.setBounds(0, 0, 700, 450);
//
//			// 添加鼠标监听，监听到所选行
//			StudioTableMouseListener tml = new StudioTableMouseListener(jt, columnNames, stud);
//			jt.addMouseListener(tml);
//
//			// 设置可调整列宽
//			jt.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
//
//			jp.add(jt);
//			jp.setViewportView(jt);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}
//
//public class StudioMgrUI extends MainUITmpl {
//	/**
//	 * 
//	 */
//	
//	protected void onWindowClosing(){
//		this.dispose();
//	}
//	
//	private static final long serialVersionUID = 1L;
//	private Studio stud=new Studio();
//	private JLabel ca1 = null; // 界面提示
//	// 用来放表格的滚动控件
//	private JScrollPane jsc;
//	// 查找的提示和输出
//	private JLabel hint;
//	private JTextField input;
//
//	// 查找，编辑和删除按钮
//	private JButton btnAdd, btnEdit, btnDel, btnQuery;
//
//	public StudioMgrUI() {
//		showTable();
//	}
//
//	// To be override by the detailed business block interface
//	@Override
//	protected void initContent() {
//		Rectangle rect = contPan.getBounds();
//
//		ca1 = new JLabel("演出厅管理", JLabel.CENTER);
//		ca1.setBounds(0, 5, rect.width, 30);
//		ca1.setFont(new java.awt.Font("宋体", 1, 20));
//		ca1.setForeground(Color.blue);
//		contPan.add(ca1);
//
//		jsc = new JScrollPane();
//		jsc.setBounds(0, 40, rect.width, rect.height - 150);
//		contPan.add(jsc);
//
//		hint = new JLabel("请输入演出厅名称:", JLabel.RIGHT);
//		hint.setBounds(60, rect.height - 80, 150, 30);
//		contPan.add(hint);
//
//		input = new JTextField();
//		input.setBounds(220, rect.height - 80, 200, 30);
//		contPan.add(input);
//
//		// 查找 ，删除和编辑的按钮，其中含有相关的事件处理！
//		btnQuery = new JButton("查找");
//		btnQuery.setBounds(440, rect.height - 80, 60, 30);
//		btnQuery.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent Event) {
//				btnQueryClicked();
//			}
//		});
//		contPan.add(btnQuery);
//
//		btnAdd = new JButton("添加");
//		btnAdd.setBounds(rect.width - 220, rect.height - 80, 60, 30);
//		btnAdd.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent Event) {
//				btnAddClicked();
//			}
//		});
//		contPan.add(btnAdd);
//
//		btnEdit = new JButton("修改");
//		btnEdit.setBounds(rect.width - 150, rect.height - 80, 60, 30);
//		btnEdit.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent Event) {
//				btnModClicked();
//			}
//		});
//		contPan.add(btnEdit);
//
//		btnDel = new JButton("删除");
//		btnDel.setBounds(rect.width - 80, rect.height - 80, 60, 30);
//		btnDel.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent Event) {
//				btnDelClicked();
//			}
//		});
//		contPan.add(btnDel);
//		contPan.add(ca1);
//		showTable();
//	}
//
//	private void btnAddClicked() {
//		StudioAddUI addStud = new StudioAddUI();
//		addStud.setWindowName("添加演出厅");
//		addStud.toFront();
//		addStud.setModal(true);
//		addStud.setVisible(true);
//
//		if (addStud.getReturnStatus()) {
//			showTable();
//		}
//	}
//
//	private void btnModClicked() {
//			
//		StudioEditUI modStu = new StudioEditUI(stud);
//		modStu.setWindowName("修改演出厅");
//		modStu.toFront();
//		modStu.setModal(true);
//		modStu.setVisible(true);
//		if (modStu.getReturnStatus()) {
//			showTable();
//		}
//		showTable();
//	}
//
//	private void btnDelClicked() {
//		int confirm = JOptionPane.showConfirmDialog(null, "确认删除所选？", "删除", JOptionPane.YES_NO_OPTION);
//		if (confirm == JOptionPane.YES_OPTION) {
//			StudioSrv stuSrv = new StudioSrv();
//			stuSrv.delete(stud.getID());
//			showTable();
//		}
//	}
//
//	private void btnQueryClicked() {
//		if (!input.getText().equals("")) {
//
//			JOptionPane.showMessageDialog(null, "哈哈哈哈");
//
//		} else {
//			JOptionPane.showMessageDialog(null, "请输入检索条件");
//		}
//	}
//
//	public void showTable() {
//		StudioTable tms = new StudioTable(stud);
//		Object[] in = { "id", "name", "row", "column", "studio desciption" };
//		List<Studio> stuList = new StudioSrv().FetchAll();
//
//		tms.createTable(jsc, in, stuList);
//		jsc.repaint();
//	}
//
//	public static void main(String[] args) {
//		StudioMgrUI frmStuMgr = new StudioMgrUI();
//		frmStuMgr.setVisible(true);
//	}
//	protected void btnExitClicked(ActionEvent Event){
//		
//		new SystemMgUI().setVisible(true);
//		this.dispose();
//
////		this.getParent().setVisible(true);
////		new Login().setVisible(true);;
////		SystemMgUI  smg = new SystemMgUI();
////		smg.setVisible(true);
//	}	
//}
