package xupt.se.ttms.view.play;

import java.util.List;

import javax.swing.JOptionPane;
import xupt.se.ttms.model.Play;
import xupt.se.ttms.model.Schedule;
import xupt.se.ttms.service.PlaySrv;
import xupt.se.ttms.service.ScheduleSrv;

public class PlayEditUI extends PlayAddUI{
	/**
	 * anthor zuo
	 * 修改剧目
	 */
	private static final long serialVersionUID = 1L;
	private Play stud;

	public PlayEditUI(Play stu){
		initData(stu);
	}
	
	public void initData(Play stu) {
		if(null== stu){
			return;
		}

		txtName.setText(stu.getName());
		txtintroduction.setText(stu.getIntroduction());
		txtlength.setText(stu.getLength()+"");
		if(stu.getStatus()==0){
			playstatus.setSelectedIndex(0);
		}else if(stu.getStatus()==1){
			playstatus.setSelectedIndex(1);
		}else {
			playstatus.setSelectedIndex(2);
		}

		txtTicketPrice.setText(stu.getTicketPrice()+"");
		stud=stu;
		this.invalidate();
	}

	@Override
	protected void btnSaveClicked(){
		if (txtName.getText() != null && txtintroduction.getText() != null
				&& txtlength.getText() != null&&playstatus.getSelectedItem()!=null
				&&txtTicketPrice.getText()!=null) {
			PlaySrv stuSrv = new PlaySrv();
			Play stu= stud;
			stu.setName(txtName.getText());
			stu.setIntroduction(txtintroduction.getText());
			stu.setLength(Integer.parseInt(txtlength.getText()));
			if(playstatus.getSelectedItem().equals("待安排演出")){
				stu.setStatus(0);
			}else if(playstatus.getSelectedItem().equals("已安排演出")){
				stu.setStatus(1);
			}else{
				stu.setStatus(-1);
			}
			stu.setTicketPrice(Float.parseFloat(txtTicketPrice.getText()));
			stuSrv.modify(stu);
			this.setVisible(false);
			rst=true;
			
			
			//当剧目下线，删除所有相关联的演出计划
//			if(stu.getStatus()==-1){
//				
//				ScheduleSrv ss = new ScheduleSrv();
//				List<Schedule> sdList = ss.Fetch(" play_id = "+stu.getId());
//				for(Schedule s:sdList){
//					ss.delete(s.getSched_id());
//				}
//			}
			//外键冲突，删不了
			
		} else {
			JOptionPane.showMessageDialog(null, "数据不完整");
		}		
	}
	
}

