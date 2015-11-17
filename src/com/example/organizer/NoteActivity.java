package com.example.organizer;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class NoteActivity extends Activity implements OnClickListener {
	TextView txtNote;
	Button addNote, btnEdit;
	EditText editNote;
	int edit_id;
	final String myLog="myLog";
	  ArrayList<Note> arrayList;
	  ArrayList<String> arrayNotes;
	  ArrayAdapter<String> adapter;
	  
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note);
		addNote = (Button)  findViewById(R.id.addNote);
		editNote = (EditText) findViewById(R.id.editNote);
		txtNote = (EditText) findViewById(R.id.txtNote);
		btnEdit = (Button) findViewById(R.id.btnEdit);
		addNote.setOnClickListener(this);
		btnEdit.setOnClickListener(this);
		arrayList = new ArrayList<Note>(new DBHandler(this).getAllNotes());
		Log.d(myLog,String.valueOf(arrayList.size()) );
		arrayNotes = new ArrayList<String>();
		for (int i = 0; i < arrayList.size(); i++) {
			arrayNotes.add(arrayList.get(i).text);
		}
		
	    ListView listNotes = (ListView) findViewById(R.id.listNotes);

	    adapter = new ArrayAdapter<String>(this,
	        android.R.layout.simple_list_item_1, arrayNotes);
	    listNotes.setAdapter(adapter);
	    
	    listNotes.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View view,
	            int position, long id) {
	        	Log.d(myLog, "position = " + String.valueOf(id));
	          editNote.setText(arrayNotes.get(position));
	          edit_id = position;
	        }
	      });
	}

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.addNote :
			Log.d(myLog, "addNote");
			String textNote = txtNote.getText().toString();
			arrayNotes.add(0,textNote);
			adapter.notifyDataSetChanged();
			Note newNote = new Note(textNote);
			new DBHandler(this).insertNote(newNote);
			arrayList = new ArrayList<Note>(new DBHandler(this).getAllNotes());
			break;
		case R.id.btnEdit :
			arrayList.get(edit_id).text = editNote.getText().toString();
			arrayNotes.remove(edit_id);
			arrayNotes.add(edit_id,editNote.getText().toString());
			
			new DBHandler(this).updateNote(arrayList.get(edit_id));
			adapter.notifyDataSetChanged();
			
			break;
			
			
	}
	

}
}
