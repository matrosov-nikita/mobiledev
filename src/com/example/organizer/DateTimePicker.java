package com.example.organizer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class DateTimePicker extends Activity {
	public int myYear, myMonth, myDay, myHour, myMinute;
	
	private boolean edit;
	Context context;
	final String myLog = "myLog";
	public DateTimePicker(Context c, boolean edit) {
		context = c;
		this.edit = edit;
		myYear =Calendar.getInstance().get(Calendar.YEAR);
		myMonth = Calendar.getInstance().get(Calendar.MONTH);
		myDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		myHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		myMinute=Calendar.getInstance().get(Calendar.MINUTE);
	}

	public Dialog onCreateDialog(int id) {
	      if (id == context.getResources().getInteger(R.integer.dialogDate)) {
	        DatePickerDialog tpd = new DatePickerDialog(context, myCallBack, myYear, myMonth, myDay);
	        
	        tpd.show();     
	        return tpd;
	      }
	      if (id == context.getResources().getInteger(R.integer.dialogTime)) {
	          TimePickerDialog tpd = new TimePickerDialog(context, myCallBack2, myHour, myMinute, true);
	          tpd.show(); 
	          return tpd;
	        }
	      return super.onCreateDialog(id);
	    }
	
	    OnDateSetListener myCallBack = new OnDateSetListener() {

	    public void onDateSet(DatePicker view, int year, int monthOfYear,
	        int dayOfMonth) {
	      myYear = year;
	      myMonth = monthOfYear;
	      myDay = dayOfMonth;
	      String s = String.valueOf(myYear)+"-"+String.valueOf(myMonth+1)+"-"+String.valueOf(myDay);
	      if (!edit) 
	      {
	    	  TaskActivity.self.reload(s);
	      }
	      EditTask.textDate.setText(s);
	           
	    }
	    };
	    OnTimeSetListener myCallBack2 = new OnTimeSetListener() {
	        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
	          myHour = hourOfDay;
	          myMinute = minute; 
	          EditTask.textTime.setText(myHour + ":" + myMinute);
	        }
	      };

}
