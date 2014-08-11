package com.adpol.fastenglish;


import com.adpol.fastenglish.database.DatabaseManager;
import com.example.fastenglish.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LearnActivity extends Activity implements OnClickListener   {
	
	private final int REPEAT = 0;
	private final int LEARN = 1;

	private ImageView replay;
    private ImageView learnNew;
    private ImageView PolEng;
    private ImageView change;
    private TextView countWords;
    private TextView learnCategories;
    private EditText countNewWords;
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
	        
	        learnNew = (ImageView) findViewById(R.id.iLearnNew);
	        learnNew.setOnClickListener(this);

	        PolEng = (ImageView) findViewById(R.id.iPolEng);
	        PolEng.setOnClickListener(this);

	        change = (ImageView) findViewById(R.id.iChange);
	        change.setOnClickListener(this);
	        
	        countWords= (TextView) findViewById(R.id.tvLeanrCountWords);
	        learnCategories= (TextView) findViewById(R.id.tvLearnCategories);
	        countNewWords = (EditText) findViewById(R.id.edLearnNewWords);
	        


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
		            startActivity(repeat);
	        	}

	        break;
	        case R.id.iLearnNew:	            
	        	Intent lernnew = new Intent(this, WordActivity.class);
	        	lernnew.putExtra("lastActivity", LEARN);
	        	lernnew.putExtra("newWords", Integer.parseInt(countNewWords.getText().toString()));
	            startActivity(lernnew);

	            break;
	        case R.id.iPolEng:
	        	  
	            


	            break;
	        case R.id.iChange:
	        	 
	       

	        }

}
}
