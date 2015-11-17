package com.example.organizer;

import java.util.Date;

public class Note {
	
	int id;
	String text;
	Date date;
	public Note(String text) {		
		this.text = text;
		this.date  = new Date();
	}
	public Note(int id, String text, Date date)
	{
		this.id = id;
		this.text = text;
		this.date = date;
	}
	
	
}
