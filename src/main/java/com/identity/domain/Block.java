package com.identity.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Block {
	private String number;
	private String hash;
    private String gasUsed;
    private String timestamp;
    public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getGasUsed() {
		return gasUsed;
	}
	public void setGasUsed(String gasUsed) {
		
		this.gasUsed = gasUsed;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp)  {
		
		/*String now=new String(""+Calendar.DAY_OF_YEAR)+"-01-01 00:00:00";
		String t="1970-01-01 00:00:00";
		long _date;*/
		/*try {
			_date = sf.parse(now).getTime()-sf.parse(t).getTime();
			Date date=new Date(new Long(timestamp)+_date);
			this.timestamp=sf.format(date);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        */
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=new Date(new Long(timestamp)*1000);
		this.timestamp=sf.format(date);
	}
	

}
