package com.smona.crawler.autohome.action;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import com.smona.crawler.autohome.http.HttpClient;
import com.smona.crawler.autohome.util.Constants;

//品牌
public class BrandAction implements IAction {

	public void execute() {
		HttpClient client = new HttpClient();
		// for (int i = 0; i < 500; i++) {
		int i = 15;
		processOnline(Constants.BRAND_URL + i + Constants.BRAND_URL_SUFFIX,
				client);
	}

	private void processOnline(String url, HttpClient client) {
		String result = client.requestHttp(url);
		if (result != null) {
			// 品牌下车系
			Document root = Jsoup.parse(result);
			Elements breadnav = root.getElementsByAttributeValue("class",
					"list-dl-text");
			if (breadnav.size() <= 0) {
				return;
			}
			SeriesAction series = null;
			for (Element divs : breadnav) {
				Elements as = divs.getElementsByTag("a");
				System.out
						.println("===============车系====start=======================总数"
								+ as.size());
				for (Element link : as) {
					String linkHref = link.attr("href");
					String linkText = link.text();
					System.out.println("车系:" + linkText + ", linkHref="
							+ linkHref);
					series = new SeriesAction(Constants.AUTO_HOME_DOMAIN
							+ linkHref);
					series.execute();
				}
				System.out
						.println("===============车系====end=========================");
			}

		}
	}

}
