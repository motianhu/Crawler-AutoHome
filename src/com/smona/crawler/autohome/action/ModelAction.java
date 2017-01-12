package com.smona.crawler.autohome.action;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.smona.crawler.autohome.http.HttpClient;

//车型
public class ModelAction implements IAction {
	private String mUrl = null;

	public ModelAction(String url) {
		mUrl = url;
	}

	public void execute() {
		HttpClient client = new HttpClient();
		processOnline(mUrl, client);
	}

	private void processOnline(String url, HttpClient client) {
		String result = client.requestHttp(url);
		if (result != null) {
			// 获取html里面的js
			Document root = Jsoup.parse(result);
			Elements script = root.select("script");
			if (script.size() <= 0) {
				return;
			}
			System.out
					.println("=========================ModelAction====start=====================");
			for (Element element : script) {
				// 切割js中的var
				String[] data = element.data().toString().split("var");
				for (String variable : data) {
					if (variable.contains("=")) {
						if (variable.contains("config = {")) {
							String[] kvp = variable.split("=");
							System.out.println(kvp[1].trim());
						} else if (variable.contains("option = {")) {
							String[] kvp = variable.split("=");
							System.out.println(kvp[1].trim());
						} else if (variable.contains("color = {")) {
							String[] kvp = variable.split("=");
							System.out.println(kvp[1].trim());
						} else if (variable.contains("bag = {")) {
							String[] kvp = variable.split("=");
							System.out.println(kvp[1].trim());
						}
					}
				}
			}
			System.out
					.println("=========================ModelAction====end====================");
		}
	}
}
