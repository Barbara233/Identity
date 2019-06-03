package com.identity.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transfer {
	private String from;
	private String to;
	private String type;
	private String count;
	private String timestamp;
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=new Date(new Long(timestamp)*1000);
		this.timestamp=sf.format(date);
	}


}
