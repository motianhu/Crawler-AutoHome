package com.smona.crawler.autohome.action;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.smona.crawler.autohome.http.HttpClient;
import com.smona.crawler.autohome.util.Debug;

//车型
public class ModelAction implements IAction {
	private String mUrl = null;
	private int mBrandId = -1;
	private int mSeriesId = -1;

	public void setUrl(String mUrl) {
		this.mUrl = mUrl;
	}

	public void setBrandId(int brandId) {
		this.mBrandId = brandId;
	}

	public void setSeriesId(int seriesId) {
		this.mSeriesId = seriesId;
	}

	public void execute() {
		HttpClient client = new HttpClient();
		if (Debug.MODEL_DEBUG) {
			System.out.println("车型url:" + mUrl);
		}
		searchModel(mUrl, client);
	}

	private void searchModel(String url, HttpClient client) {
		String result = client.requestHttp(url);
		if (result != null) {
			// 获取html里面的js
			Document root = Jsoup.parse(result);
			Elements script = root.select("script");
			if (script.size() <= 0) {
				return;
			}
			processModel(script);
		}
	}

	private void processModel(Elements script) {
		if (Debug.MODEL_DEBUG) {
			System.out
					.println("=========================ModelAction====start=====================");
		}
		for (Element element : script) {
			// 切割js中的var
			String[] data = element.data().toString().split("var");
			for (String variable : data) {
				if (variable.contains("=")) {
					if (variable.contains("config = {")) {
						String[] kvp = variable.split("=");
						if (Debug.MODEL_DEBUG) {
							System.out.println(kvp[1].trim());
						}
					} else if (variable.contains("option = {")) {
						String[] kvp = variable.split("=");
						if (Debug.MODEL_DEBUG) {
							System.out.println(kvp[1].trim());
						}
					} else if (variable.contains("color = {")) {
						String[] kvp = variable.split("=");
						if (Debug.MODEL_DEBUG) {
							System.out.println(kvp[1].trim());
						}
					} else if (variable.contains("bag = {")) {
						String[] kvp = variable.split("=");
						if (Debug.MODEL_DEBUG) {
							System.out.println(kvp[1].trim());
						}
					}
				}
			}
		}
		if (Debug.MODEL_DEBUG) {
			System.out
					.println("=========================ModelAction====end====================");
		}
	}
}
