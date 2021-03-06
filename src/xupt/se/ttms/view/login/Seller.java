package xupt.se.ttms.view.login;
/**
 * 售票员UI
 */
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import xupt.se.ttms.view.datasale.PersonalDataSale;
import xupt.se.ttms.view.sellticket.SellTicketMgUI;
import xupt.se.ttms.view.tmpl.MainUITmpl;

public class Seller extends MainUITmpl{

	//To be override by the detailed business block interface 
		protected void btnExitClicked(ActionEvent Event){
			this.dispose();
			new Login().setVisible(true);;
		}	
	
	private static final long serialVersionUID = 1025028999012028956L;//序列化

	public Seller(){
		initContent();
	}

	@Override
	protected void initContent() {
		JPanel workPanel = new JPanel();
		workPanel.setLayout(null);
		workPanel.setBounds(0, 0, 1024, 600);
		
		JButton sale = new JButton();
		sale.setVerticalTextPosition(SwingConstants.BOTTOM);
		sale.setHorizontalTextPosition(SwingConstants.CENTER);
		sale.setIcon(new ImageIcon("resource/image/p3.jpg"));
		sale.setBackground(Color.WHITE);
		sale.setText(" 退售 票 ");
		sale.setBounds(300, 100, 160, 160);
		
		sale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				SellTicketMgUI sellTicketUI = new SellTicketMgUI();
				sellTicketUI.setVisible(true);
				Seller.this.dispose();
			
			}
		});
		
		JButton refund = new JButton();
		refund.setVerticalTextPosition(SwingConstants.BOTTOM);
		refund.setHorizontalTextPosition(SwingConstants.CENTER);
		refund.setIcon(new ImageIcon("resource/image/p4.jpg"));
		refund.setBackground(Color.WHITE);
		refund.setText(" 查看销售记录 ");
		refund.setBounds(600, 100, 160, 160);
		refund.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {

				PersonalDataSale pds = new PersonalDataSale();
				pds.setVisible(true);
				Seller.this.dispose();
				
			}
		});
		
		
		JLabel jb = new JLabel();
		jb.setIcon(new ImageIcon("resource/image/study.gif"));
		jb.setBounds(450,200,1024,400);
		
		workPanel.add(sale);
		workPanel.add(refund);
		workPanel.add(jb);
		workPanel.setBackground(Color.white);
		
		contPan.add(workPanel);
		contPan.validate();
		
	}

}
