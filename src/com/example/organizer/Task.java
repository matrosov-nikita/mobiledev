package com.example.organizer;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
@SuppressWarnings("serial")
public class Task implements Serializable {
	int id;
	String  category;
	boolean status;
	String text;
	Date date;
	final static String myLog="myLog";
	SimpleDateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Task(Context c) {
		id=10;
		category = c.getResources().getString(R.string.important);
		status = false;
		text = "";
		date = Calendar.getInstance().getTime();
	}
	Task(int id, String category, boolean status, String text, Date date)
	{
		this.id=id;
		this.category = category;
		this.status=status;
		this.text = text;
		this.date = date;
	}
	
	public int  getBgcColor(Context c) {
		if (c.getString(R.string.important).equals(category)) {
		
			return R.color.importantTask;
		} else if (c.getString(R.string.usual).equals(category)) {
			
			return R.color.usualTask;
		} else if (c.getString(R.string.pushover).equals(category)) {
			
			return R.color.pushoverTask;
		}
		return R.color.pushoverTask;
	}
	
	public String getStringTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		return dateFormat.format(date);	
	}
	public String getStringDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		return dateFormat.format(date);	
	}
	public View Draw(LayoutInflater li, Context c, ViewGroup parentContainer) {
		
		  View item = li.inflate(R.layout.item,  parentContainer, false);		      
	      item.setBackgroundResource(getBgcColor(c));
	      CheckBox cbx = (CheckBox) item.findViewById(R.id.checkBox1);
	      cbx.setChecked(status);
	      TextView tvTask = (TextView) item.findViewById(R.id.textView1);
	      tvTask.setText(text.toString());
	      TextView tvDate = (TextView) item.findViewById(R.id.textView2);
	      tvDate.setText(getStringTime()); 
	      item.getLayoutParams().width = LayoutParams.MATCH_PARENT;
	      return item;
	}
	
	
	}
	
