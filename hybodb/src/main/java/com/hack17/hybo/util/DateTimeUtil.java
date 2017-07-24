package com.hack17.hybo.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
	private static DateFormat sdf = DateFormat.getDateInstance(DateFormat.DEFAULT);
	private static DateFormat sdf2 = new SimpleDateFormat("dd-MMM-yy");
	public static Date getDate(String date){
		try {
			return sdf.parse(date);
		} catch (ParseException e) {			
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static Date getDate2(String date){
		try {
			return sdf2.parse(date);
		} catch (ParseException e) {			
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
