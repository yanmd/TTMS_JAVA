package xupt.se.ttms.dao;

import java.util.LinkedList;
import java.util.List;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import xupt.se.ttms.idao.iStudioDAO;
import xupt.se.ttms.model.Seat;
import xupt.se.ttms.model.Studio;
import xupt.se.ttms.service.SeatSrv;
import xupt.se.util.DBUtil;



public class StudioDAO implements iStudioDAO {

	@Override
	public int insert(Studio stu) {
		try {
	
			String sql = "insert into studio(studio_name, studio_row_count, studio_col_count, studio_introduction )"
					+ " values('"
					+ stu.getName()
					+ "', "
					+ stu.getRowCount()
					+ ", " + stu.getColCount() 
					+ ", '" + stu.getIntroduction()
					+ "' )";
			DBUtil db = new DBUtil();
			db.openConnection();
			System.out.println("数据库连接成功");
			ResultSet rst = db.getInsertObjectIDs(sql);

			if(rst.next()&&rst.first()){
				stu.setID(rst.getInt(1));
			}
			db.close(rst);
			db.close();
			
			
			
//			
//			addSeat(stu);
			System.out.println(stu.getID());
			return stu.getID();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

//	public void addSeat(Studio stu){
//		
//		SeatSrv  ss = new SeatSrv();
//		for(int i=0;i<stu.getColCount();i++){
//			for(int j =0;j<stu.getRowCount();j++){
//				ss.add(new Seat(stu.getID(),j,i));
//			}
//		}
//	}
	
	@Override
	public int update(Studio stu) {
		int rtn=0;
		try {
			String sql = "update studio set " + " studio_name ='"
					+ stu.getName() + "', " + " studio_row_count = "
					+ stu.getRowCount() + ", " + " studio_col_count = "
					+ stu.getColCount() + ", " + " studio_introduction = '"
					+ stu.getIntroduction() + "' \n";

			sql += " where studio_id = " + stu.getID();
			DBUtil db = new DBUtil();
			db.openConnection();
			rtn =db.execCommand(sql);
			System.out.println("数据库连接成功--修改");
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtn;
	}

	@Override
	public int delete(int ID) {
		int rtn=0;		
		try{
			String sql = "delete from  studio ";
			sql += " where studio_id = " + ID;
			DBUtil db = new DBUtil();
			db.openConnection();
			System.out.println("数据库连接成功__删除");
			rtn=db.execCommand(sql);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtn;		
	}

	@Override
	public List<Studio> select(String condt) {
		List<Studio> stuList = null;
		stuList=new LinkedList<Studio>();
		try {
			String sql = "select studio_id, studio_name, studio_row_count, studio_col_count, studio_introduction from studio";
			condt.trim();
			if(!condt.isEmpty())
				sql+= " where " + condt;
			DBUtil db = new DBUtil();
			if(!db.openConnection()){
				System.out.print("fail to connect database");
				return null;
			}
			ResultSet rst = db.execQuery(sql);
			if (rst!=null) {
				while(rst.next()){
					Studio stu=new Studio();
					stu.setID(rst.getInt("studio_id"));
					stu.setName(rst.getString("studio_name"));
					stu.setRowCount(rst.getInt("studio_row_count"));
					stu.setColCount(rst.getInt("studio_col_count"));
					stu.setIntroduction(rst.getString("studio_introduction"));
					stuList.add(stu);
				}
			}
			db.close(rst);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			
		}
		
		return stuList;
	}

	@Override
	public Studio selectById(String condt) {
		// TODO Auto-generated method stub
		Studio stu=new Studio();
		try {
			String sql = "select studio_id, studio_name, studio_row_count, studio_col_count, studio_introduction from studio";
			
			sql+= " where  " + condt;
			DBUtil db = new DBUtil();
			if(!db.openConnection()){
				System.out.print("fail to connect database");
				return null;
			}
			
			ResultSet rst = db.execQuery(sql);
			if(rst!=null&&rst.next()){
			stu.setID(rst.getInt("studio_id"));
			stu.setName(rst.getString("studio_name"));
			stu.setRowCount(rst.getInt("studio_row_count"));
			stu.setColCount(rst.getInt("studio_col_count"));
			stu.setIntroduction(rst.getString("studio_introduction"));
			db.close(rst);
			}

			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			
		}
		return stu;
	}
}
