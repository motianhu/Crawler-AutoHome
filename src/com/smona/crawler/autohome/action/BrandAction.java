package com.smona.crawler.autohome.action;

import java.sql.ResultSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import com.smona.crawler.autohome.db.DBDelegator;
import com.smona.crawler.autohome.http.HttpClient;
import com.smona.crawler.autohome.util.Constants;
import com.smona.crawler.autohome.util.Debug;

//品牌
public class BrandAction implements IAction {

	private String querySql = "SELECT id FROM CarBrand WHERE brandName=%s";
	private String insertSql = "insert into CarBrand(recordFrom,brandName,brandFirstName,createUser,createTime) values(%s,%s,%s,%s,%s)";

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
			Elements breadnav = root.getElementsByAttributeValue("class",
					"list-dl");
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
					System.out
							.println("=====================================================");
				}
				boolean isFirstBrand = true;
				int brandId = -1;
				for (Element link : as) {
					String linkHref = link.attr("href");
					String linkText = link.text();
					// 第一个永远是品牌，第二个开始到最后都是车系
					if (isFirstBrand) {
						brandId = processBrand(linkText);
						isFirstBrand = false;
					} else {
						processSeries(brandId, linkHref, client);
					}
				}
			}
		}

	}

	private int processBrand(String brandName) {
		Object result = DBDelegator.getInstance().executeQuery(querySql);
		if (result instanceof ResultSet) {

		} else {
			addBrand(brandName);
		}
		return -1;
	}

	private int addBrand(String brandName) {
		Object result = DBDelegator.getInstance().executeQuery(insertSql);
		if (result instanceof ResultSet) {

		}
		return -1;
	}

	private void processSeries(int brandId, String linkHref, HttpClient client) {
		if (Debug.ONLY_BRAND_DEBUG) {
			return;
		}
		SeriesAction series = new SeriesAction();
		series.setUrl(Constants.AUTO_HOME_DOMAIN + linkHref);
		series.setBrandId(brandId);

		series.execute();
	}

}
