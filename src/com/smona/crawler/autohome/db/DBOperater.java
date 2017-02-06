package com.smona.crawler.autohome.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.smona.crawler.autohome.util.Debug;

public class DBOperater {
	private static final String name = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost/crawler";
	private static final String user = "root";
	private static final String password = "hhhh";

	private Connection mConn = null;
	private Statement mStmt = null;

	public void onCreate() {
		if(Debug.DB_CLOSE_DEBUG) {
			return;
		}
		Connection conn = null;
		try {
			Class.forName(name);
			conn = DriverManager.getConnection(url, user, password);
			mStmt = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onDestory() {
		if(Debug.DB_CLOSE_DEBUG) {
			return;
		}
		if (mConn != null) {
			try {
				mConn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (mStmt != null) {
			try {
				mStmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Object executeTask(String sql) {
		if(Debug.DB_CLOSE_DEBUG) {
			return null;
		}
		try {
			ResultSet set = mStmt.executeQuery(sql);
			return set;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
