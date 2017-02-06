package com.smona.crawler.autohome;

import com.smona.crawler.autohome.action.BrandAction;
import com.smona.crawler.autohome.action.IAction;
import com.smona.crawler.autohome.db.DBDelegator;
import com.smona.crawler.autohome.util.Logger;

public class Main {

	public static void main(String[] args) {
		Logger.init();
		String encode = System.getProperty("file.encoding");
		println(encode);
		String path = System.getProperty("user.dir");
		println(path);
		action();
	}

	private static void action() {
		DBDelegator.getInstance().createBridge();
		IAction action = new BrandAction();
		action.execute();
		DBDelegator.getInstance().destoryBride();
	}

	private static void println(String msg) {
		Logger.printDetail(msg);
	}

}
