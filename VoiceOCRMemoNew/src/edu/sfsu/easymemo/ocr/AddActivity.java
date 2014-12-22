package edu.sfsu.easymemo.ocr;






import edu.sfsu.easymemo.ocr.MySQLiteHelper;

import edu.sfsu.easymemo.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddActivity extends Activity {
	TextView task;
	Button taskSave;
	TextView d;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addagain);
	    Bundle extras = getIntent().getExtras();  
	    String res = extras.getString("voice"); 
	    String date = extras.getString("date");
	    
	    if(date.equals(" ")){
	    	String [] splits = res.split("\\|");
	    	res = splits[0];
	    	date = splits[1];
	      //  Toast.makeText(getApplicationContext(),"Values are:\n First value: "+splits[0]+  
	     //	       "\n Second Value: "+splits[1],Toast.LENGTH_LONG).show(); 
	    	
	    }
    	
	   // Toast.makeText(getApplicationContext(),"Values are:\n First value: "+res+  
	    //       "\n Second Value: "+date,Toast.LENGTH_LONG).show(); 
	    String [] days = date.split("/");
    	String year = days[0];
    	String month = days[1];
    	String day = days[2];
    	String dateNOslash = year+ month + day;
    	

		task = (TextView) findViewById(R.id.voiceResult);
		d = (TextView) findViewById(R.id.voiceD);
		
		if(res!=null){
		    task.setText(res);
		}else{
			task.setText("Invalid task, please retry");
		}
		d.setText(date);
		
		
			MySQLiteHelper db = new MySQLiteHelper(this);
			db.addTask(new Task(res, dateNOslash)); 
		
		taskSave = (Button)findViewById(R.id.TaskSave);
		

		
        taskSave.setOnClickListener(new View.OnClickListener() {
        	 
            @Override
            public void onClick(View v) {
            	String date = d.getText().toString();
            	String [] days = date.split("/");
            	String year = days[0];
            	String month = days[1];
            	String day = days[2];
                Intent i = new Intent(getApplicationContext(), edu.sfsu.easymemo.ocr.DayActivity.class);
				i.putExtra("day", day);
				i.putExtra("month", month);
				i.putExtra("year", year);
                

				startActivity(i);

            }
        });
		
		
 
		 
		 
	}		

}
