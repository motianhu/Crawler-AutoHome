package com.smona.crawler.autohome.action;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import com.smona.crawler.autohome.http.HttpClient;
import com.smona.crawler.autohome.util.Constants;
import com.smona.crawler.autohome.util.Debug;

//品牌
public class BrandAction implements IAction {

	public void execute() {
		HttpClient client = new HttpClient();
		// for (int i = 0; i < 500; i++) {
		int i = 15;
		String url = Constants.BRAND_URL + i + Constants.BRAND_URL_SUFFIX;
		if (Debug.BRAND_DEBUG) {
			System.out.println("品牌url:" + url);
		}
		searchBrand(url, client);
	}

	private void searchBrand(String url, HttpClient client) {
		String result = client.requestHttp(url);
		if (result != null) {
			// 品牌名称
			Document root = Jsoup.parse(result);
			Elements breadnav = root.getElementsByAttributeValue("class", "list-dl");
			if (breadnav.size() <= 0) {
				return;
			}
			if (Debug.BRAND_DEBUG) {
				// 打印品牌及其车系的html代码
				System.out.println(breadnav);
			}
			for (Element divs : breadnav) {
				Elements as = divs.getElementsByTag("a");
				if (Debug.BRAND_DEBUG) {
					// 打印每个<a>标签代码
					System.out.println("品牌名称：" + as.size());
					System.out.println(as);
					System.out.println("=====================================================");
				}
				boolean isFirstBrand = true;
				for (Element link : as) {
					String linkHref = link.attr("href");
					String linkText = link.text();
					// 第一个永远是品牌，第二个开始到最后都是车系
					if (isFirstBrand) {
						processBrand(linkText);
						isFirstBrand = false;
					} else {
						processSeriesForBrand(Constants.AUTO_HOME_DOMAIN + linkHref, client);
					}
				}
			}
		}

	}

	private void processBrand(String brandName) {

	}

	private void processSeriesForBrand(String url, HttpClient client) {
		SeriesAction series = new SeriesAction(url);
		series.execute();
	}

}
