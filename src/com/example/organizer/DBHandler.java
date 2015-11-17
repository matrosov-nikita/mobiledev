package com.example.organizer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHandler extends Activity {
	
	DBHelper dbHelper;
	SQLiteDatabase database;
	final static String myLog="myLog";
	SimpleDateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public DBHandler(Context c) {
		dbHelper = new DBHelper(c);
		database = dbHelper.getWritableDatabase();
	}
	public ContentValues fillTask(Task task) {
		ContentValues Values = new ContentValues();
		Values.put("text", task.text);
		Values.put("category", task.category);
		Values.put("date", formatter.format(task.date));
		Log.d(myLog,task.date.toString());
		Log.d(myLog, formatter.format(task.date));
		Values.put("status", task.status);
		return Values;
	}
	public ContentValues fillNote(Note note) {
		ContentValues Values = new ContentValues();
		Values.put("text", note.text);
		Values.put("date", formatter.format(note.date));
		return Values;
	}
	public void insertTask(Task task) {	
		Log.d(myLog, "insert");
		Log.d(myLog, task.date.toString());
		database.insert("tasks", null, fillTask(task));
		database.close();
	}
	public void insertNote(Note note) {
		database.insert("notes", null, fillNote(note));
		database.close();
	}
	public void updateTask(Task task) {
		database.update("tasks", fillTask(task), "id = ?", new String[]{String.valueOf(task.id)});
		database.close();
	}
	public void updateNote(Note note) {
		Log.d(myLog, String.valueOf(note.id));
		database.update("notes", fillNote(note), "id = ?", new String[]{String.valueOf(note.id)});
		database.close();
	}
	public List<Task> getAllTasks(String dates) 
	{
		List<Task> list = new LinkedList<Task>();
		
		Cursor c = database.query("tasks", null, "date like " + "'"+dates+"%'", null, null, null, null);
		 if (c.moveToFirst()) {		 
		        int idColIndex = c.getColumnIndex("id");
		        int categoryColIndex = c.getColumnIndex("category");
		        int dateColIndex = c.getColumnIndex("date");
		        int statusColIndex = c.getColumnIndex("status");
		        int textColIndex = c.getColumnIndex("text");
		        do {
		        	Date date = null;
		        	 try {
		        		 	date =  formatter.parse(c.getString(dateColIndex));
						} 
		        	 catch (ParseException e) {
							e.printStackTrace();
						}
		        	list.add(new Task(c.getInt(idColIndex),
		        					c.getString(categoryColIndex),
		        					c.getInt(statusColIndex)!=0,
		        					c.getString(textColIndex),		        				
		        					date));
		        } while (c.moveToNext());
		 
	}
		 c.close();
		 return list;
}
	public List<Note> getAllNotes() {    
		List<Note> list = new ArrayList<Note>();	
		Cursor c = database.query("notes", null, null, null, null, null, null);
		 if (c.moveToFirst()) {		 
		        int idColIndex = c.getColumnIndex("id");
		        int dateColIndex = c.getColumnIndex("date");
		        int textColIndex = c.getColumnIndex("text");
		        do {
		        	Date date = null;
		        	 try {
		        		 	date =  formatter.parse(c.getString(dateColIndex));
						} 
		        	 catch (ParseException e) {
							e.printStackTrace();
						}
			    	list.add(new Note(c.getInt(idColIndex),
			    					c.getString(textColIndex),		        				
			    					date));
		        } while (c.moveToNext());	 
	}
		 c.close();
		 return list;
	}
	
	public  int getCountTasks(String category,String date) {
		date+="%";
		Cursor mCount= database.rawQuery("select count(*) from tasks where category='" + category+"' and date LIKE '" + date +"'", null);
		mCount.moveToFirst();
		int count= mCount.getInt(0);
		mCount.close();
		return count;
	}
	public class DBHelper  extends SQLiteOpenHelper {

		 public DBHelper(Context context) {
		      super(context, "myDB", null, 1);
		    }

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			String initialQuery;
			initialQuery = "create table tasks ("
			          + "id integer primary key autoincrement," 
			          + "text text,"
			          + "category text," 
			          + "date datetime,"
			          + "status boolean" + ");";
			 db.execSQL(initialQuery);
			 Log.d(myLog, "create table");
			 initialQuery = "create table notes ("
					 + "id integer primary key autoincrement,"
					 + "text text,"
					 + "date datetime" +");";
			 db.execSQL(initialQuery);		 
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
	}

}
