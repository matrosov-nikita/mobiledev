package com.example.organizer;

import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

@SuppressWarnings({ "deprecation", "deprecation" })
public class EditTask extends Activity implements OnClickListener {

	final static String myLog = "myLog";
	Button btnDate,btnTime,btnSave;
	public static TextView textDate, textTime;
	EditText editArea;
	Spinner spinner;
	Switch switc;
	Task getTask;
	DateTimePicker picker = new DateTimePicker(this,true);
	 protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_task); 
		btnDate = (Button) findViewById(R.id.button1);
		btnTime = (Button) findViewById(R.id.button2);
		btnSave = (Button) findViewById(R.id.button3);
		textDate = (TextView) findViewById(R.id.textView6);
		textTime = (TextView) findViewById(R.id.textView7);
		editArea = (EditText) findViewById(R.id.editText1);
		spinner = (Spinner) findViewById(R.id.spinner1);
		switc = (Switch) findViewById(R.id.switch1);
		btnDate.setOnClickListener(this);
		btnTime.setOnClickListener(this);
		btnSave.setOnClickListener(this);
		Intent i = getIntent();
		getTask = (Task) i.getSerializableExtra("editTask");	
		textDate.setText(getTask.getStringDate());
		textTime.setText(getTask.getStringTime());
		editArea.setText(getTask.text);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		String findValue = getTask.category;
		if (!findValue.equals(null)) {
		    int spinnerPosition = adapter.getPosition(findValue);
		    spinner.setSelection(spinnerPosition);
		}
		switc.setChecked(getTask.status);
		
	 }
	 public void onClick(View v) {
		 switch(v.getId())
		 {
		 case R.id.button1:  picker.onCreateDialog(getResources().getInteger(R.integer.dialogDate)); break;
		 case R.id.button2:  picker.onCreateDialog(getResources().getInteger(R.integer.dialogTime)); break;
		 case R.id.button3: 
			 getTask.date = new Date(picker.myYear-1900,picker.myMonth,picker.myDay,picker.myHour,picker.myMinute);
			 getTask.text = editArea.getText().toString();
			 getTask.category = spinner.getSelectedItem().toString();
			 getTask.status = switc.isChecked();
			 Intent intent = new Intent();
			 intent.putExtra("editTask", getTask);
			 setResult(RESULT_OK,intent);		 
			 finish();
			 break;
				
		 }
		 	
	}

}
