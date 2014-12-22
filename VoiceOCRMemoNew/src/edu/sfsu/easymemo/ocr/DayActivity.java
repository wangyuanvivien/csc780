package edu.sfsu.easymemo.ocr;

import java.util.List;


import edu.sfsu.easymemo.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class DayActivity extends Activity{
	TextView dateText;
	TextView tasklist;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//sets the main layout of the activity
		setContentView(R.layout.activity_day);
		
	    Bundle extras = getIntent().getExtras();  
	    String day = extras.getString("day");  
	    String month = extras.getString("month");  
	    String year = extras.getString("year");  
	   // String voiceResult = extras.getString("voice"); 
	    dateText = (TextView)findViewById(R.id.daytext);
	    dateText.setText(year + "/" + month + "/" + day);
	   // Toast.makeText(getApplicationContext(),"Values are:\n First value: "+Integer.parseInt(day)+  
	    //     "\n Second Value: "+Integer.parseInt(month),Toast.LENGTH_LONG).show();  
	    MySQLiteHelper db = new MySQLiteHelper(this);
	    
       
         tasklist = (TextView) findViewById(R.id.taskList);
      
        List<Task> list = db.getAllTasksByDate(year + month + day);
        int size = list.size();
        int counter = 1;
        for(int i=0; i<size;i++){
        	Task ct = list.get(i);

            tasklist.append( counter + " : "+ct.getContent() + "\n");
            counter++;
        	
        }
	    
     
	    //clean db
	  /*  List<Task> list = db.getAllTasks();
        int size = list.size();
        for(int i=0; i<size;i++){
        	Task ct = list.get(i);
            db.deleteTask(ct);
        	
        }*/
	    
		ImageButton backbutton = (ImageButton) findViewById(R.id.backbutton);
		backbutton.setOnClickListener(new OnClickListener()
		{
             @Override
             public void onClick(View v)
             {
                final Intent i = new Intent(getApplicationContext(), edu.sfsu.easymemo.ocr.MainActivity.class);               
                // i.setComponent(component)
            	 //final Intent i = new Intent("ACTION_OCR");
            	 //i.setComponent(new ComponentName("", "edu.sfsu.easymemo.ocr.OCRActivity"));
                 startActivity(i);
             } 
	    });	    
		
		
		ImageButton button = (ImageButton) findViewById(R.id.addbutton);
		button.setOnClickListener(new OnClickListener()
		{
             @Override
             public void onClick(View v)
             {
                final Intent i = new Intent(getApplicationContext(), edu.sfsu.easymemo.ocr.OCRActivity.class);  
                String date = dateText.getText().toString();
                i.putExtra("date", date);
                // i.setComponent(component)
            	 //final Intent i = new Intent("ACTION_OCR");
            	 //i.setComponent(new ComponentName("", "edu.sfsu.easymemo.ocr.OCRActivity"));
                 startActivity(i);
             } 
	    });
		   
		ImageButton vcbutton = (ImageButton) findViewById(R.id.addvcbutton);
		vcbutton.setOnClickListener(new OnClickListener()
		{
             @Override
             public void onClick(View v)
             {
                 final Intent i = new Intent(getApplicationContext(), edu.sfsu.easymemo.ocr.VoiceRecognitionActivity.class);               
                 String date = dateText.getText().toString();
                 i.putExtra("date", date);
                 // i.setComponent(component)
            	 //final Intent i = new Intent("ACTION_OCR");
            	 //i.setComponent(new ComponentName("", "edu.sfsu.easymemo.ocr.OCRActivity"));
                 startActivity(i);
             } 
	    });	   
		   
	

	}

}
