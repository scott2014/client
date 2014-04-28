package com.block.model.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.block.model.sqlite.SQLiteOpenHelper;

public class DataService {
	public static final String DB_NAME="block";
	
	public static final int ISLAST = 1;
	
	public static final int NOTLAST = 0;
	
	//15分钟为周期
	public static final int BYFIF = 0;
	//30分钟为周期
	public static final int BYTH = 1;
	//60分钟为周期
	public static final int BYSIX = 2;
	//1天为周期
	public static final int BYDAY = 4;
	
	public static void insert(List<String[]> data,String dbName,String tableName,String fileName) {
		System.out.println(data.size());
		SQLiteOpenHelper soh = SQLiteOpenHelper.newInstance();
		Connection conn = soh.getConnection(dbName);
		String sql = "insert into block_data(importDate,fileName,releaseTime,releaseDate,releaseHour,price,isLast) values(?,?,?,?,?,?,?)";
		PreparedStatement stmt = null;
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			
			Date now = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String nowStr = formatter.format(now);
			
			String tempDate = null;
			String tempHour = null;
			String temp = null;
			int index = -1;
			for (int i=0;i<data.size();i++) {
				stmt.setString(1, nowStr);
				stmt.setString(2, fileName);
				stmt.setString(3, data.get(i)[0]);
				
				tempDate = data.get(i)[0].split("[ ]")[0];
				temp = data.get(i)[0].split("[ ]")[1];
				
				index = temp.lastIndexOf(":");
				tempHour = temp.substring(0,index);
				stmt.setString(4, tempDate);
				stmt.setString(5, tempHour);
				String temp2 = data.get(i)[1];
				if (temp2.contains("#")) {
					stmt.setString(6, temp2.replace("#", ""));
					stmt.setInt(7, ISLAST);
				} else {
					stmt.setString(6, data.get(i)[1]);
					stmt.setInt(7, NOTLAST);
				}
				stmt.execute();
			}
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static List<Double> findAllLast(String start,String end,int type) {
		List<Double> result = new ArrayList<Double>();
		Connection conn = SQLiteOpenHelper.newInstance().getConnection("block");
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		if (type == DataService.BYDAY) {
			sql =  "select price from block_data where isLast = " + DataService.ISLAST
				    + " and releaseDate >= '" + start + "' and releaseDate <= '" + end + "' order by releaseTime asc";
		} else {
			sql = "select price from block_data where isLast = " + DataService.ISLAST
				    + " and releaseTime >= '" + start + "' and releaseTime <= '" + end + "'";
		}
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				result.add(rs.getDouble("price"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static Map<String,String> getArgs() {
		Map<String,String> map = new HashMap<String,String>();
		Connection conn = SQLiteOpenHelper.newInstance().getConnection("block");
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select strategyId,argsValue from block_args";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String key = rs.getString(1);
				String value = rs.getString(2);
				map.put(key, value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return map;
		
	}
	
	/*满足其一：①N+X1+Y1+F1持续T分钟或120笔，则价格回到N+X1+Y1平仓；
	②N+X1+Y1+F1在T分钟或120笔内，N+X1+Y1-M1平仓；
	③N+X1+Y1+F1持续T分钟或120笔后持续向上，则到下一个平仓点按①②平仓*/
	public static int isKeepUp(double n,String start,double t) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate = null;
		try {
			startDate = formatter.parse(start);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(startDate);
		long startTime = c.getTimeInMillis();
		
		Date endDate = new Date(startTime + (long)t*60*1000);
		String endTime = formatter.format(endDate);
		
		Connection conn = SQLiteOpenHelper.newInstance().getConnection("block");
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select price from block_data where releaseTime > '"
					+ start + "' and releaseTime < '" + endTime + "'";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				if (rs.getDouble(1) < n) {
					return 2;
				}
				if (rs.getDouble(1) == n) {
					return 1;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 3;
	}
	
	public static void main(String[] args) {
		List<String[]> data = ExcelService.getData("e:/1.xlsx");
		insert(data, DB_NAME, "block", "1.xlsx");
	}
}
