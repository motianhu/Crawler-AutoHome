package com.smona.crawler.autohome.db;

public class DBDelegator {
	private static DBDelegator sInstance = null;
	private DBOperater mDBOperator;

	private DBDelegator() {
		mDBOperator = new DBOperater();
	}

	public static DBDelegator getInstance() {
		if (sInstance == null) {
			sInstance = new DBDelegator();
		}
		return sInstance;
	}

	public void createBridge() {
		mDBOperator.onCreate();
	}

	public void destoryBride() {
		mDBOperator.onDestory();
	}

	public Object executeQuery(String sql) {
		return mDBOperator.executeTask(sql);
	}
}
