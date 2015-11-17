package com.example.organizer;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class CalendarActivity extends Activity {
	CalendarView calendar; 
	TextView dangerCalendar, warningCalendar, usualCalendar;
	static String myLog = "myLog";
	
	public String getCount(String category, String date)
	{
		return String.valueOf(new DBHandler(this).getCountTasks(category, date));
	}
	public void setCount(String count, TextView t) 
	{
		switch(t.getId()){
		case R.id.dangerCalendar: 
			t.setText(count+" важные задачи");
			break;
		case R.id.warningCalendar:
			t.setText(count+" обычные задачи");
			break;
		case R.id.usualCalendar:
			t.setText(count+" несрочные задачи");		
			break;
		}
		
	}
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar);
		calendar = (CalendarView) findViewById(R.id.calendarView1);
		dangerCalendar = (TextView) findViewById(R.id.dangerCalendar);
		warningCalendar = (TextView) findViewById(R.id.warningCalendar);
		usualCalendar = (TextView) findViewById(R.id.usualCalendar);
		calendar.setOnDateChangeListener(new OnDateChangeListener() {
		        public void onSelectedDayChange(CalendarView view, int year, int month,
		                int dayOfMonth) {
		        	
		        	String date = String.valueOf(year)+"-"+String.valueOf(month+1)+"-"+String.valueOf(dayOfMonth);
		        	Log.d(myLog, date);
					setCount(getCount((getResources().getString(R.string.important)),date),dangerCalendar);
					setCount(getCount((getResources().getString(R.string.usual)),date),warningCalendar);
					setCount(getCount((getResources().getString(R.string.pushover)),date),usualCalendar);
		   
		        }
		    });
	}
}
