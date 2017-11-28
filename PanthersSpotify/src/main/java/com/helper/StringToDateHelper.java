package com.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDateHelper {
	public static Date parseToMonthYear(String str) {
		DateFormat df = new SimpleDateFormat("MM-yyyy"); 
		Date startDate;
		try {
		    startDate = df.parse(str);
		    return startDate;
		} catch (ParseException e) {
		    return null;
		}
	}
	
	public static Date pareToYMD(String str) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		Date startDate;
		try {
			startDate = df.parse(str);
		    return startDate;
		} catch (ParseException e) {
		    return null;
		}
	}
}
