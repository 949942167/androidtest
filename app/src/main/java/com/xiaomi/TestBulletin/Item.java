/* 
 * Have a nice day!
 * author:yangsong
 * email:yangsong1@xiaomi.com 
 */
package com.xiaomi.TestBulletin;

import java.io.Serializable;

public class Item implements Serializable {
	public Item() {
	}

	public Item(String time, String url) {
		this.time = time;
		this.url = url;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	private String time;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	private String url;
}