package xupt.se.ttms.view.schedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.swing.JOptionPane;

import xupt.se.ttms.model.Play;
import xupt.se.ttms.model.Schedule;
import xupt.se.ttms.model.Seat;
import xupt.se.ttms.model.Studio;
import xupt.se.ttms.model.Ticket;
import xupt.se.ttms.service.PlaySrv;
import xupt.se.ttms.service.ScheduleSrv;
import xupt.se.ttms.service.SeatSrv;
import xupt.se.ttms.service.StudioSrv;
import xupt.se.ttms.service.TicketSrv;
import xupt.se.ttms.view.studio.StudioAddUI;;

public class ScheduleEditUI extends ScheduleAddUI{
	/**
	 * 演出计划修改
	 */
	private static final long serialVersionUID = 1L;
	private Schedule stud;
	private int fstudioID = 0;

	public ScheduleEditUI(Schedule stu){
		initData(stu);
	}
	
	public void initData(Schedule stu) {
		if(null== stu){
			return;
		}

		fstudioID = stu.getStudio_id();
		String sname = (new StudioSrv().FetchOneById("studio_id = "+stu.getStudio_id())).getName();
		for(int i=0;i<Lstudio.size();i++){
			if(Lstudio.get(i).getName().equals(sname)){
				jstudioID.setSelectedIndex(i);
				break;
			}
		}
		
		String pname = (new PlaySrv().FetchOneById("play_id = "+stu.getPlay_id())).getName();
		for(int i=0;i<Lplay.size();i++){
			if(Lplay.get(i).getName().equals(pname)){
				jplayID.setSelectedIndex(i);
				break;
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");  
//		pt.setText(sdf.format(stu.getSched_time()));
		Calendar c =  Calendar.getInstance();
		c.setTime(stu.getSched_time());
		year.setSelectedItem(c.get(Calendar.YEAR));
		month.setSelectedItem(c.get(Calendar.MONTH)+1);
		day.setSelectedItem(c.get(Calendar.DAY_OF_MONTH));
		hour.setSelectedItem(c.get(Calendar.HOUR_OF_DAY));
		minute.setSelectedItem(c.get(Calendar.MINUTE));
		pprice.setText(stu.getSched_ticket_price()+"");
		stud=stu;
		this.invalidate();
	}

	@Override
	protected void btnSaveClicked(){
		if (jstudioID!= null && jplayID!= null
				&&pprice.getText()!=null) {
			ScheduleSrv stuSrv = new ScheduleSrv();
			Schedule stu= stud;
			stu.setPlay_id((new PlaySrv().FetchOneById("play_name = '"+jplayID.getSelectedItem()+"'")).getId());
			stu.setSched_ticket_price(Double.parseDouble(pprice.getText()));
			stu.setStudio_id((new StudioSrv().FetchOneById("studio_name = '"+jstudioID.getSelectedItem()+"'")).getID());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
//			try {
////				stu.setSched_time(sdf.parse(pt.getText()));
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			String time = year.getSelectedItem()+"-"
					+month.getSelectedItem()+"-"+day.getSelectedItem()+" "
					+hour.getSelectedItem()+":"+minute.getSelectedItem();
			int dayCount = getDayCount(Integer.parseInt(year.getSelectedItem()+""),Integer.parseInt(month.getSelectedItem()+""));
			if(Integer.parseInt(day.getSelectedItem()+"")>dayCount){
				JOptionPane.showMessageDialog(null, "日期天数超出当月范围");
				System.out.println("日期天数超出当月范围");
				return ;
			}
			try {
//				sch.setSched_time(DF.parse(pt.getText()));
				stu.setSched_time(sdf.parse(time));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			stuSrv.modify(stu);
			
			PlaySrv ps = new PlaySrv();
			Play p  = ps.FetchOneById(" play_id = "+stu.getPlay_id());
			p.setStatus(1);
			ps.modify(p);
			
			TicketSrv tsrv = new TicketSrv();
			List<Ticket> lt = tsrv.Fetch(" sched_id = "+stu.getSched_id());
			for(Ticket t:lt){
				t.setPrice(stu.getSched_ticket_price());
				tsrv.modify(t);
//				System.out.println(t.getPrice());
			}

			
			if(fstudioID != stu.getStudio_id()){
				List<Seat> Ls = new SeatSrv().Fetch(" studio_id = "+stu.getStudio_id());
				TicketSrv ts = new TicketSrv();
				ts.delete(" sched_id = "+stu.getSched_id());
				for(int i=0;i<Ls.size();i++){
					Ticket t = new Ticket();
					t.setPrice(stu.getSched_ticket_price());
					t.setScheduleId(stu.getSched_id());
					t.setSeatId(Ls.get(i).getId());
					t.setStatus(0);
					ts.add(t);
				}
			}
			this.setVisible(false);
			rst=true;
			
		} else {
			JOptionPane.showMessageDialog(null, "数据不完整");
		}		
	}
	
}