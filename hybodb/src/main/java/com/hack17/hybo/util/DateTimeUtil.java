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
	public static Date getDateMMMddyyyy(String date){
		try {
			return sdf.parse(date);
		} catch (ParseException e) {			
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static Date getDatedd_MMM_yyyy(String date){
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
			if(cal.get(Calendar.MONTH)<=8)
				cal.add(Calendar.YEAR, -1);
			cal.set(Calendar.MONTH, 9);
			cal.set(Calendar.DATE, 1);
			return cal.getTime();
		case TO:
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
	public static Date add(Date date, int type, int numberToAdd){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(type, numberToAdd);
		return cal.getTime();
	}
	
	public static boolean isMoreThanYearOld(Date date1, Date date2){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date2);
		cal.add(Calendar.YEAR, -1);
		Date date2PreviousYear = cal.getTime();
		return date2PreviousYear.after(date1);
	}

	public static boolean isDay(Date today, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		
		return cal.get(Calendar.DAY_OF_MONTH)==day;
		
	}

	public static boolean isMonth(Date today, int month) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		
		return cal.get(Calendar.MONTH)==month-1;
	}
	
	public static boolean isWeekend(Date today) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		
		return cal.get(Calendar.DAY_OF_WEEK)==1 || cal.get(Calendar.DAY_OF_WEEK)==7;
	}
	
	public static Date getNextWorkingDay(Date today) {
		if(!isWeekend(today))
			return today;
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		while(isWeekend(cal.getTime()))
			cal.add(Calendar.DATE, 1);
		return cal.getTime();
	}
	
	public static Date getPreviousWorkingDay(Date today) {
		if(!isWeekend(today))
			return today;
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		while(isWeekend(cal.getTime()))
			cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}
}
