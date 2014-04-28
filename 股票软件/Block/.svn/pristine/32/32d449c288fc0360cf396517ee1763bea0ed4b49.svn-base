package com.block.model.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteOpenHelper {
	
	private static String dbPath = null;
	
//	private static SQLiteOpenHelper instance = null;
	
	static {
		System.out.println("static");
		try {
			String[] sql = new String[] {
					"create table block_data(id INTEGER primary key autoincrement," +
				    "importDate date," +
					"fileName varchar(32)," +
				    "releaseTime time," +
					"releaseDate date," +
				    "releaseHour time," +
					"price double," +
					"isLast int)",
					"create table block_args(id INTEGER primary key autoincrement," +
					"strategyId varchar(10)," +
					"strategyName varchar(10)," + 
					"argsValue varchar(400))"
			};
			Class.forName("org.sqlite.JDBC");
			dbPath = System.getProperty("user.dir");
			String url = SQLiteOpenHelper.dbPath + "/block"; 
			Connection conn = null;
			Statement judgeStmt = null;
			Statement judgeStmt2 = null;
			Statement createStmt = null;
			Statement createStmt2 = null;
			String judgeSql = "select count(*) from sqlite_master where type='table' and name='block_data'";
			String judgeSql2 = "select count(*) from sqlite_master where type='table' and name='block_args'";
			ResultSet rs = null;
			ResultSet rs2 = null;
			int count = 0;
			int count2 = 0;
			try {
				conn = DriverManager.getConnection("jdbc:sqlite:" + url);
				judgeStmt = conn.createStatement();
				judgeStmt2 = conn.createStatement();
				createStmt = conn.createStatement();
				createStmt2 = conn.createStatement();
				rs = judgeStmt.executeQuery(judgeSql);
				rs2 = judgeStmt2.executeQuery(judgeSql2);
				System.out.println(rs.next());
				count = rs.getInt(1);
				if (count <= 0) {
					createStmt.execute(sql[0]);
				}
				rs2.next();
				count2 = rs.getInt(1);
				if (count2 <= 0) {
					createStmt2.execute(sql[1]);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (createStmt != null) {
						createStmt.close();
						createStmt = null;
					}
					if (judgeStmt != null) {
						judgeStmt.close();
						judgeStmt = null;
					}
					if (rs != null) {
						rs.close();
						rs = null;
					}
					if (createStmt2 != null) {
						createStmt2.close();
						createStmt2 = null;
					}
					if (judgeStmt2 != null) {
						judgeStmt2.close();
						judgeStmt2 = null;
					}
					if (rs2 != null) {
						rs2.close();
						rs2 = null;
					}
					if (conn != null) {
						conn.close();
						conn = null;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private SQLiteOpenHelper() {}
	
	/**
	 * <p>该方法用于生成需要的表和数据库名,可以同时生成多张数据库表</p>
	 * 
	 * @param dbName 数据库名称
	 * @param sql 建表语句
	 * 
	 * *//*
	private SQLiteOpenHelper(String dbName,String[] sql) {
		String url = SQLiteOpenHelper.dbPath + "/" + dbName; 
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:" + url);
			stmt = conn.createStatement();
			for (String s : sql) {
				stmt.execute(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
					conn = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}*/
	
	//该方法用于生成单例的SQLiteOpenHelper实例
	//客户端应该调用这个方法获取数据库连接，并且建立相应的数据库和表格
	//为了避免database is locked错误，改为多例模式
	public static SQLiteOpenHelper newInstance() {
		/*if (instance == null) {
			instance = new SQLiteOpenHelper();
		}*/
		return new SQLiteOpenHelper();
	}
	
	//驱动数据库连接
	public Connection getConnection(String dbName) {
		String url = SQLiteOpenHelper.dbPath + "/" + dbName; 
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:" + url);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	
	/**
	 * 该方法是对插入数据到数据库的简单封装
	 * @param tableName是表名
	 * @param columns是要插入到的列名
	 * @param values是对于的值
	 * 
	 * 列名和值必须一一对应
	 * 
	 * 单机程序，不考虑SQL注入
	 * */
	public void insert(String dbName,String tableName,String[] columns,Object[] values) {
		if (columns == null || values == null) {
			throw new RuntimeException("不能插入空值列");
		}
		if (columns.length != values.length) {
			throw new RuntimeException("列数量和插入数据值不匹配");
		}
		Connection conn = getConnection(dbName);
		Statement stmt = null;
		StringBuilder builder = new StringBuilder("insert into ");
		builder.append(tableName).append("(");
		for (int i=0;i<columns.length;i++) {
			builder.append(columns[i]).append(",");
		}
		builder.deleteCharAt(builder.length() - 1).append(") values(");
		for (int i=0;i<values.length;i++) {
			if (values[i] instanceof Integer
				|| values[i] instanceof Long
				|| values[i] instanceof Float
				|| values[i] instanceof Double
				) {
				builder.append(values[i]).append(",");
			} else {
				builder.append("'").append(values[i]).append("'").append(",");
			}
		}
		builder.deleteCharAt(builder.length() - 1).append(")");
		try {
			stmt = conn.createStatement();
			stmt.setQueryTimeout(200);
			stmt.executeUpdate(builder.toString());
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} /*finally {
			try {
				if (conn != null) {
					conn.close();
					conn = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}*/
		
	}
	
	
	public static void main(String[] args) {
		SQLiteOpenHelper s1 = newInstance();
		SQLiteOpenHelper s2 = newInstance();
		System.out.println("s1 == s2 ? " + (s1 == s2));
	}
	
}
