package xupt.se.ttms.dao;

import java.util.LinkedList;
import java.util.List;
import java.sql.ResultSet;

import xupt.se.ttms.idao.iSeatDAO;
import xupt.se.ttms.model.Seat;
import xupt.se.util.DBUtil;

public class SeatDAO implements iSeatDAO {
	 
	public int insert(Seat stu) {

		try {
			String sql = "insert into seat(seat_id, studio_id, seat_row, seat_column )"
					+ " values("
					+ stu.getId()
					+ ", "
					+ stu.getStudioId()
					+ ", " + stu.getRow()
					+ ", " + stu.getColumn()
					+ ")";
			DBUtil db = new DBUtil();
			db.openConnection();
			System.out.println("数据库连接成功");
			ResultSet rst = db.getInsertObjectIDs(sql);

//				      
			if (rst!=null && rst.first()) {
				stu.setId(rst.getInt(1));
			}
			System.out.println("hello");
//			if(rst.next()&&rst.first()){
//				stu.setID(rst.getInt(1));
//			}
			db.close(rst);
			db.close();
			return 1;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return 0;
	}

	
	public int update(Seat stu) {
		int rtn = 0;
		try {
			String sql = "update seat set " + " studio_id ="
					+ stu.getStudioId() + ", " + " seat_row = "
					+ stu.getRow() + ", "  + " seat_column = "
					+ stu.getColumn() + " ";

			sql += " where sched_id = " + stu.getId();
			DBUtil db = new DBUtil();
			db.openConnection();
			rtn = db.execCommand(sql);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtn;
	}

	 
	public int delete(int ID) {
		int rtn = 0;
		try {
			String sql = "delete from seat ";
			sql += " where seat_id = " + ID;
			DBUtil db = new DBUtil();
			db.openConnection();
			rtn = db.execCommand(sql);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtn;
	}

	 
	public List<Seat> select(String condt) {
		List<Seat> stuList = null;
		stuList = new LinkedList<Seat>();
		try {
			String sql = "select * from seat ";
			condt.trim();
			if (!condt.isEmpty())
				sql += " where " + condt;
			DBUtil db = new DBUtil();
			if (!db.openConnection()) {
				System.out.print("fail to connect database");
				return null;
			}
			ResultSet rst = db.execQuery(sql);
			if (rst != null) {
				while (rst.next()) {
					Seat stu = new Seat();
					stu.setId(rst.getInt("seat_id"));
					stu.setStudioId(rst.getInt("studio_id"));
					stu.setRow(rst.getInt("seat_row"));
					stu.setColumn(rst.getInt("seat_column"));
					stuList.add(stu);
				}
			}
			db.close(rst);
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return stuList;
	}
}