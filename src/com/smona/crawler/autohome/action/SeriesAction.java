package com.smona.crawler.autohome.action;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import com.smona.crawler.autohome.http.HttpClient;
import com.smona.crawler.autohome.util.Constants;

//车系
public class SeriesAction implements IAction {
	private String mUrl = null;

	public SeriesAction(String url) {
		mUrl = url;
	}

	public void execute() {
		HttpClient client = new HttpClient();
		processOnline(mUrl, client);
	}

	private void processOnline(String url, HttpClient client) {
		String result = client.requestHttp(url);
		System.out.println("SeriesAction url=" + url);
		if (result != null) {
			// 车系下车型
			Document root = Jsoup.parse(result);
			Elements carsInfor = root.getElementsByAttributeValue("class",
					"interval01-list-cars-infor");
			if (carsInfor.size() <= 0) {
				return;
			}
			// 获取车型名称
			Element carType = carsInfor.get(0);
			Elements links = carType.getElementsByTag("a");
			Element link = links.get(0);

			String linkText = link.text();

			System.out.println("车型名称:" + linkText);
			Elements config = root.getElementsByAttributeValue("class",
					"interval01-list-related");
			if (config.size() <= 0) {
				return;
			}

			// 获取车型配置
			String linkHref = null;
			Elements as = config.select("a");
			for (Element a : as) {
				String name = a.text();
				if (Constants.A_NAME.equals(name)) {
					linkHref = a.attr("href");
				}
			}
			System.out.println("车型配置:" + linkHref);
			if (linkHref == null || "".equals(linkHref)) {
				return;
			}

			ModelAction model = new ModelAction(Constants.AUTO_HOME_DOMAIN
					+ linkHref);
			model.execute();
		}
	}

}
