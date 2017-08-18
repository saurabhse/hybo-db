package com.hack17.hybo.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {
	public static final String TO = "to";
	public static final String FROM = "from";
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
	
	public static Date getFinancialYearDate(String dateType, Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		switch(dateType){
		case FROM:
			System.out.println(FROM);
			cal.set(Calendar.MONTH, 9);
			cal.set(Calendar.DATE, 1);
			if(cal.get(Calendar.MONTH)<=8)
				cal.add(Calendar.YEAR, -1);
			return cal.getTime();
		case TO:
			System.out.println(TO);
			if(cal.get(Calendar.MONTH)>=9)
				cal.add(Calendar.YEAR, 1);
			cal.set(Calendar.MONTH, 8);
			cal.set(Calendar.DATE, 30);
			return cal.getTime();
		default:
			throw new IllegalArgumentException(dateType);
			
		}
	}
	
	public static String format2(Date date){
		return sdf2.format(date);
	}
	public static int getDateDifferenceInDays(Date presentDate,Date pastDate){
		int diff = 0;
		try{
			long diffInMills = presentDate.getTime()-pastDate.getTime();
			diff = Long.valueOf(diffInMills/1000/60/60/24).intValue();
		}catch(Exception e){
			e.printStackTrace();
		}
		return diff;
	}
}
