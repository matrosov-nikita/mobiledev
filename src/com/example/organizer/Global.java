package com.example.organizer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Global {

	static SimpleDateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	public static String getCurrentDate() {
		
		Date current = Calendar.getInstance().getTime();
		String date = formatter.format(current);
		return date;
	}
	
	public static boolean compareDateWithCurrent(Date date1)
	{
		String date = getCurrentDate();
		String date2 = formatter.format(date1);
		return date.equals(date2);
	}
}
