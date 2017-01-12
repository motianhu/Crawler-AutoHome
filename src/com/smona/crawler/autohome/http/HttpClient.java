package com.smona.crawler.autohome.http;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpClient {
	public String requestHttp(String url) {
		try {
			OkHttpClient client = new OkHttpClient();
			Request request = new Request.Builder().url(url).build();
			Response response = client.newCall(request).execute();
			if (!response.isSuccessful()) {
				System.out.print("服务器端错误: " + response);
			}
			return response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
