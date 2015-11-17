package com.example.organizer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TextView;

public class TaskActivity extends Activity implements OnClickListener{
	
	
	TableLayout parentLayout,parentImp, parentUsual,parentPushover;
	final String myLog="myLog";
	 Button btn, addButton;
	 public static List<Task> tasks= new LinkedList<Task>();
	 List<Task> tasksImportant= new LinkedList<Task>();
	 List<Task> tasksUsual= new LinkedList<Task>();
	 List<Task> tasksPushover= new LinkedList<Task>();
	 DateTimePicker picker;
	 static TextView textDate;
	 static TaskActivity self;
	 @SuppressWarnings("deprecation")
	private void prepareTabs() {	 
		 TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
	     tabHost.setup();
	     TabHost.TabSpec tabSpec;
	     tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setIndicator("Все");
        tabSpec.setContent(R.id.tvTab1);        
        tabHost.addTab(tabSpec);              
        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setIndicator("Важные");
        tabSpec.setContent(R.id.tvTab2);        
        tabHost.addTab(tabSpec);
        
        tabSpec = tabHost.newTabSpec("tag3");
        tabSpec.setIndicator("Обычные");
        tabSpec.setContent(R.id.tvTab3);        
        tabHost.addTab(tabSpec);   
        
        tabSpec = tabHost.newTabSpec("tag4");
        tabSpec.setIndicator("Несрочные");
        tabSpec.setContent(R.id.tvTab4);        
        tabHost.addTab(tabSpec); 
        for (int i = 0; i < 4; i++) {
        	TextView x = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            x.setTextSize(9);
        }
	 }
	 
	 
	 public void addTask(Task task, int i) {
		  View item = task.Draw( getLayoutInflater(),this, parentLayout);
	      parentLayout.addView(item);
	      item.setTag(i);	
		 if (task.category.equals(getResources().getString(R.string.important))) {
				//tasksImportant.add(task);
				  View item2 = task.Draw( getLayoutInflater(),this, parentImp);
			      parentImp.addView(item2);
			      item2.setTag(i);	
			}
			else if (task.category.equals(getResources().getString(R.string.usual))) {
				//tasksUsual.add(task);
				 View item3 = task.Draw( getLayoutInflater(),this, parentUsual);
		         parentUsual.addView(item3);
			     item3.setTag(i);
			}
			else {
			//	tasksPushover.add(task);
				 View item4 = task.Draw( getLayoutInflater(),this, parentPushover);
			      parentPushover.addView(item4);
			      item4.setTag(i);	
			}	 
	 }
	 
	 public void fillCollections() {
			for (int i = 0; i < tasks.size(); i++) {
				Task task = tasks.get(i);
				addTask(task,i);
			} 
	 }
	 
	 
	 @SuppressWarnings("deprecation")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.taskactivity);		 
		prepareTabs(); 
		self=this;
	    parentLayout = (TableLayout) findViewById(R.id.tableLayout);
	    parentImp = (TableLayout) findViewById(R.id.tableLayout2);
	    parentUsual = (TableLayout) findViewById(R.id.tableLayout3);
	    parentPushover = (TableLayout) findViewById(R.id.tableLayout4);
		picker = new DateTimePicker(this,false);
		Intent intent = getIntent();
		textDate = (TextView) findViewById(R.id.textDate);
		String date = intent.getStringExtra("date");
		textDate.setText(date);	
		tasks =  new DBHandler(this).getAllTasks(date);
		fillCollections();
		btn = (Button) findViewById(R.id.datePicker);
		addButton = (Button) findViewById(R.id.addButton);
		btn.setOnClickListener(this);
		addButton.setOnClickListener(this);
	}
	 
	 
	 public void rowClick(View v) {
		 Intent intent = new Intent(this, EditTask.class);
		 intent.putExtra("editTask", tasks.get((int) v.getTag()));
		 startActivityForResult(intent,(int) v.getTag());  	 
	 }
	
	
		@SuppressWarnings("deprecation")
		@Override
		public void onClick(View v) {
			switch(v.getId())
			{
			case R.id.datePicker:   
				picker.onCreateDialog(getResources().getInteger(R.integer.dialogDate));
				break;
			case R.id.addButton: 
				Intent intent = new Intent(this, EditTask.class);
				intent.putExtra("editTask", new Task(this));
				startActivityForResult(intent,tasks.size());  	 
				break;
			}
					
		}
		public void reload(String date) 
		{
			 Intent intent = getIntent();
			 intent.putExtra("date", date);
			 finish();
			 startActivity(intent);
		}
		 @Override
		  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			 super.onActivityResult(requestCode, resultCode, data);
		    if (data == null) {return;}
		    if (requestCode<tasks.size())
		    {
		    	 Task temp = (Task) data.getSerializableExtra("editTask");
				    tasks.get(requestCode).category = temp.category;  
				    tasks.get(requestCode).status = temp.status;
				    tasks.get(requestCode).text = temp.text;
				    tasks.get(requestCode).date = temp.date;
				    new DBHandler(this).updateTask(temp);
				    Intent intent = getIntent();
				    finish();
				    startActivity(intent);
		    }
		    else
		    {
		    	  Task temp = (Task) data.getSerializableExtra("editTask"); 
		    	  new DBHandler(this).insertTask(temp);
		    	  if (Global.compareDateWithCurrent(temp.date))
		    	  {
		    		  tasks.add(temp);
			    	  addTask(temp,requestCode);
		    	  }	    	
		    }		   
		   
		  }
		 public void onCheckboxClicked(View v)
		 {
			 boolean checked = ((CheckBox) v).isChecked();
			 int position = (int) ((View) v.getParent()).getTag();
		 	tasks.get(position).status  = checked;		
			 
		 }
	
	
}
