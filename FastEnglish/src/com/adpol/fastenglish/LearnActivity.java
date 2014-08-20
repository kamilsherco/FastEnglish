package com.adpol.fastenglish;


import com.adpol.fastenglish.database.DatabaseManager;
import com.example.fastenglish.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LearnActivity extends Activity implements OnClickListener , OnTouchListener   {
	
	private final int REPEAT = 0;
	private final int LEARN = 1;
	
	

	private ImageView replay;
    private ImageView learnNew;
    private ImageView polEng;
    private ImageView change;
    private TextView countWords;
    private TextView polEngtxt;
    
    private EditText countNewWords;
    
    private SharedPreferences prefs ;
    private boolean engPol;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_learn);
		initialize();
	}
	
	void initialize()
	    {

	   
	        replay = (ImageView) findViewById(R.id.iReplay);
	        replay.setOnClickListener(this);
	        replay.setOnTouchListener(this);
	        
	        learnNew = (ImageView) findViewById(R.id.iLearnNew);
	        learnNew.setOnClickListener(this);
	        learnNew.setOnTouchListener(this);

	        polEng = (ImageView) findViewById(R.id.iPolEng);
	        polEng.setOnClickListener(this);
	        polEng.setOnTouchListener(this);

	        change = (ImageView) findViewById(R.id.iChange);
	        change.setOnClickListener(this);
	        change.setOnTouchListener(this);
	        
	        countWords= (TextView) findViewById(R.id.tvLeanrCountWords);
	    
	        countNewWords = (EditText) findViewById(R.id.edLearnNewWords);
	        countNewWords.setSelection(countNewWords.length());
	        polEngtxt= (TextView) findViewById(R.id.tvPolEngtxt);
	        prefs = getSharedPreferences("prefs", 0);
	        
	        engPol= prefs.getBoolean("engPol", true);
	        
	        
	        if(engPol) polEngtxt.setText("ENG->POL");
        	else polEngtxt.setText("POL->ENG");
	        engPol=!engPol;
        	


	    }


	    @Override
	    public void onClick(View arg0) {
	        // TODO Auto-generated method stub
	        switch(arg0.getId())
	        {
	        case R.id.iReplay:
	        	if(DatabaseManager.getInstance().isNothingLearned()){
	        		Toast.makeText(getBaseContext(), "Nie nauczy³eœ siê jeszcze ¿adnych s³ów. PrzejdŸ najpierw do dzia³u \"Ucz siê nowych.\"", Toast.LENGTH_LONG).show();
	        	}
	        	else{
		        	Intent repeat = new Intent(this, WordActivity.class);
		        	repeat.putExtra("lastActivity", REPEAT);
		        	//repeat.putExtra("engPol", engPol);
		            startActivity(repeat);
	        	}

	        break;
	        case R.id.iLearnNew:	 
	        	if(DatabaseManager.getInstance().isEverythingLearned()){
	        		Toast.makeText(getBaseContext(), "Wszystkie s³owa z podanych kategorii zosta³y przez Ciebie wyuczone. Mo¿esz je teraz utrwalaæ w dziale \"Powtarzaj\".", Toast.LENGTH_LONG).show();
	        	}
	        	else{
		        	Intent lernnew = new Intent(this, WordActivity.class);
		        	lernnew.putExtra("lastActivity", LEARN);
		        	//lernnew.putExtra("engPol", engPol);
		        	lernnew.putExtra("newWords", Integer.parseInt(countNewWords.getText().toString()));
		            startActivity(lernnew);
	        	}
	            break;
	        case R.id.iPolEng:
	        	if(engPol)
	        		{
	        		polEngtxt.setText("ENG->POL");
	        		 SharedPreferences.Editor editor = prefs.edit();    
	    	       	 editor.putBoolean("engPol", true);       
	    	       	 editor.commit();
	        		}
	        	else 
	        		{
	        		polEngtxt.setText("POL->ENG");
	        		 SharedPreferences.Editor editor = prefs.edit();    
	    	       	 editor.putBoolean("engPol", false);       
	    	       	 editor.commit();
	        		}
	        	engPol=!engPol;
	        
	        	
	        	
	          
	       	
	            break;
	            
	        case R.id.iChange:
	        	Intent settings = new Intent(this, SettingsActivity.class);
	              startActivity(settings);
	        	 
	       

	        }

}

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			
			  switch(v.getId())
		        {
		        case R.id.iReplay:
		        	if(event.getAction() == MotionEvent.ACTION_DOWN)
		        		replay.setImageResource(R.drawable.btclearsel);
	                else
	                	replay.setImageResource(R.drawable.btclear);

		        break;
		        case R.id.iLearnNew:
		        	if(event.getAction() == MotionEvent.ACTION_DOWN)
		        		learnNew.setImageResource(R.drawable.btclearsel);
	                else
	                	learnNew.setImageResource(R.drawable.btclear);
		        	
		            break;
		        case R.id.iPolEng:
		        	if(event.getAction() == MotionEvent.ACTION_DOWN)
		        		polEng.setImageResource(R.drawable.btclearsel);
	                else
	                	polEng.setImageResource(R.drawable.btclear);
		        	
		        
		        	
		        	
		          
		       	
		            break;
		            
		        case R.id.iChange:
		        	if(event.getAction() == MotionEvent.ACTION_DOWN)
		        		change.setImageResource(R.drawable.btclearsel);
	                else
	                	change.setImageResource(R.drawable.btclear);
		        	
		        	 
		       

		        }
			return false;
		}
}
