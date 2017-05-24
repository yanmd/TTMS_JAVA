package xupt.se.ttms.view.schedule;


import java.awt.Color;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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

import xupt.se.ttms.model.Schedule;
import xupt.se.ttms.model.Studio;
import xupt.se.ttms.service.ScheduleSrv;
import xupt.se.ttms.service.StudioSrv;
import xupt.se.ttms.view.login.Manager;
import xupt.se.ttms.view.login.SystemMgUI;
import xupt.se.ttms.view.tmpl.*;

class ScheduleTable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable jt;

	public ScheduleTable(JScrollPane jp) {
		
		DefaultTableModel tabModel=new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

			@Override              
			public boolean isCellEditable(int row,int column){
				return false;              
			};
		};
//		Object[] in = { "ID", "演出厅ID", "剧目ID", "演出时间", "票单价" };
		tabModel.addColumn("演出计划ID");
		tabModel.addColumn("演出厅ID");
		tabModel.addColumn("剧目ID");
		tabModel.addColumn("演出时间");
		tabModel.addColumn("票价");
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
        column.setPreferredWidth(200);
        column = columnModel.getColumn(4);
        column.setPreferredWidth(20);        

		
		jp.add(jt);
		jp.setViewportView(jt);
		
	}
	
//	sche.setSched_id(Integer.parseInt(jt.getValueAt(row, 0).toString()));
//	sche.setStudio_id(Integer.parseInt(jt.getValueAt(row, 1).toString()));
//	sche.setPlay_id((Integer.parseInt(jt.getValueAt(row, 2).toString())));
////	sche.setStudio_id((int)jt.getValueAt(row, 1));
////	sche.setPlay_id(((int)jt.getValueAt(row, 2)));
////	sche.setSched_time(((Date)jt.getValueAt(row, 3)));
////	sche.setSched_time(DateFormat.parse(jt.getValueAt(row, 3).toString()));
//	try  
//	{  
//	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");  
//	    System.out.println(jt.getValueAt(row, 3));
//	    Date date = sdf.parse(jt.getValueAt(row, 3)+"");  
//	    sche.setSched_time(date);
//	}  
//	catch (Exception e)  
//	{  
////		e.printStackTrace();
//	    System.out.println("异常："+e.getMessage());  
//	} 
////	sche.setSched_ticket_price(((double)jt.getValueAt(row, 4)));
//	sche.setSched_ticket_price((Double.parseDouble(jt.getValueAt(row, 4).toString())));
//	System.out.println(jt.getValueAt(row, 0).toString());
//}
	public Schedule getSchedule() {
		int rowSel=jt.getSelectedRow();
		if(rowSel>=0){
//			Object[] in = { "ID", "演出厅ID", "剧目ID", "演出时间", "票单价" };
			Schedule stud = new Schedule();
			stud.setSched_id(Integer.parseInt(jt.getValueAt(rowSel, 0)+""));
			stud.setStudio_id(Integer.parseInt(jt.getValueAt(rowSel, 1)+""));
			stud.setPlay_id(Integer.parseInt(jt.getValueAt(rowSel, 2)+""));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
			try {
				stud.setSched_time(sdf.parse(jt.getValueAt(rowSel,3)+""));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("???");
			}
//			try  
//			{  
//			    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");  
//			    System.out.println(jt.getValueAt(rowSel, 3));
//			    Date date = sdf.parse(jt.getValueAt(rowSel, 3));
//			    stud.setSched_time(date);
//			}  
//			catch (Exception e)  
//			{  
//				e.printStackTrace();
////			    System.out.println("异常："+e.getMessage());  
//			} 
			stud.setSched_ticket_price(Double.parseDouble(jt.getValueAt(rowSel, 4)+""));

			return stud;
		}
		else{
			return null;
		}
			
	}
	
	// 创建JTable
	public void showScheduleList(List<Schedule> stuList) {
		try {
			DefaultTableModel tabModel = (DefaultTableModel) jt.getModel();
			tabModel.setRowCount(0);
			
			Iterator<Schedule> itr = stuList.iterator();
			while (itr.hasNext()) {
				Schedule stu = itr.next();
				Object data[] = new Object[5];
				data[0] = Integer.toString(stu.getSched_id());
				data[1] = Integer.toString(stu.getStudio_id());
				data[2] = Integer.toString(stu.getPlay_id());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");  
				data[3] = sdf.format(stu.getSched_time());
//				data[3] = stu.getSched_time();
				data[4] = stu.getSched_ticket_price();
				tabModel.addRow(data);;
			}
			jt.invalidate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

public class ScheduleMgUI extends MainUITmpl {
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
	
	ScheduleTable tms; //显示演出厅列表


	public ScheduleMgUI() {
		
	}

	// To be override by the detailed business block interface
	@Override
	protected void initContent() {
		Rectangle rect = contPan.getBounds();

		ca1 = new JLabel("演出计划管理", JLabel.CENTER);
		ca1.setBounds(0, 5, rect.width, 30);
		ca1.setFont(new java.awt.Font("宋体", 1, 20));
		ca1.setForeground(Color.blue);
		contPan.add(ca1);

		jsc = new JScrollPane();
		jsc.setBounds(0, 40, rect.width, rect.height - 90);
		contPan.add(jsc);

		hint = new JLabel("请输入演出计划名称:", JLabel.RIGHT);
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
		
		tms = new ScheduleTable(jsc);
		
		showTable();
	}

	private void btnAddClicked() {

		ScheduleAddUI addStuUI=null;
		
		addStuUI = new ScheduleAddUI();
		addStuUI.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		addStuUI.setWindowName("添加演出计划");
		addStuUI.toFront();
		addStuUI.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
		addStuUI.setVisible(true);
		if (addStuUI.getReturnStatus()) {
			showTable();
		}
	}

	private void btnModClicked() {
		

		
		
		Schedule stud = tms.getSchedule();
		if(null== stud){
			JOptionPane.showMessageDialog(null, "请选择要修改的演出计划");
			return; 
		}
		
		ScheduleEditUI modStuUI = new ScheduleEditUI(stud);
		modStuUI.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		modStuUI.setWindowName("修改演出计划");
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
		Schedule stud = tms.getSchedule();
		if(null== stud){
			JOptionPane.showMessageDialog(null, "请选择要删除的演出厅");
			return; 
		}		
		
		int confirm = JOptionPane.showConfirmDialog(null, "确认删除所选？", "删除", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			ScheduleSrv stuSrv = new ScheduleSrv();
			stuSrv.delete(stud.getSched_id());
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
		List<Schedule> stuList = new ScheduleSrv().FetchAll();
		tms.showScheduleList(stuList);
	}
	

	public static void main(String[] args) {
		ScheduleMgUI frmStuMgr = new ScheduleMgUI();
		frmStuMgr.setVisible(true);
	}
	//To be override by the detailed business block interface 
	protected void btnExitClicked(ActionEvent Event){
		new Manager().setVisible(true);
		this.dispose();
		
	}	
}

