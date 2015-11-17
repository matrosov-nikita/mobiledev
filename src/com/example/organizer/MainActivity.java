package com.example.organizer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{

	Button btnTasks, btnNotes, btnCalendar;
	private static final String TAG="MY log";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		btnTasks = (Button) findViewById(R.id.btnTasks);
		btnNotes = (Button) findViewById(R.id.btnNotes);
		btnCalendar = (Button) findViewById(R.id.btnCalendar);
		btnTasks.setOnClickListener(this);
		btnNotes.setOnClickListener(this);
		btnCalendar.setOnClickListener(this);
	}
	public void startAnimationButton(final MainActivity self, final int btn_id, Button btn)
	{
		Animation anim = null;
		anim  = AnimationUtils.loadAnimation(this, R.anim.shake_anim);
		anim.setAnimationListener(new AnimationListener() {
			 @Override
			   public void onAnimationStart(Animation animation){}

			   @Override
			   public void onAnimationRepeat(Animation animation){}

			   @Override
			   public void onAnimationEnd(Animation animation){
				   switch(btn_id) {
				   case R.id.btnTasks: 
					   Intent intent = new Intent(self,TaskActivity.class);
					   intent.putExtra("date", Global.getCurrentDate());
					   startActivity(intent);
					   break;
				   case R.id.btnNotes: 
					   Intent intent2 = new Intent(self,NoteActivity.class);
					   startActivity(intent2);
					   break;	
				   case R.id.btnCalendar: 
					   Intent intent3 = new Intent(self,CalendarActivity.class);
					   startActivity(intent3);
					   break;	
				   		
			   }
			   }
		});
		
		btn.startAnimation(anim);
	}
	public void onClick(View v) {
		switch(v.getId()){		
		case R.id.btnTasks: 
			startAnimationButton(this, R.id.btnTasks, btnTasks );		
			break;	
		case R.id.btnNotes: 
			startAnimationButton(this, R.id.btnNotes, btnNotes);	
			break;
		case R.id.btnCalendar: 
			startAnimationButton(this, R.id.btnCalendar, btnCalendar);	
			break;

	}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		return super.onOptionsItemSelected(item);
	}
}
