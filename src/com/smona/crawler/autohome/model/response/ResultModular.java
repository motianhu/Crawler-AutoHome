package com.smona.crawler.autohome.model.response;

public class ResultModular<T> {
	public int returncode;
	public String message;
	public T result;

	public String toString() {
		return "returncode: " + returncode + ",message:" + message + ",T: " + result.getClass();
	}
}