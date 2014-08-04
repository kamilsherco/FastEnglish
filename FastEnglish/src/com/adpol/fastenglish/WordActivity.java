package com.adpol.fastenglish;

import com.example.fastenglish.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class WordActivity extends Activity implements OnClickListener{
	
	private ImageView showWord;
    private ImageView btYes;
    private ImageView btNo;
   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_word);
		initialize();
	}
	
	void initialize()
	    {
		 	showWord = (ImageView) findViewById(R.id.iShowWord);
	        showWord.setOnClickListener(this);
	        
	        btYes = (ImageView) findViewById(R.id.iYes);
	        btYes.setOnClickListener(this);

	        btNo = (ImageView) findViewById(R.id.iNo);
	        btNo.setOnClickListener(this);
	   
	      


	    }


	    @Override
	    public void onClick(View arg0) {
	        // TODO Auto-generated method stub
	        switch(arg0.getId())
	        {
	        case R.id.iShowWord:
	        	
	       

	        break;
	        case R.id.iYes:
	            
	        	


	            break;
	        case R.id.iNo:
	        	  
	            


	            break;
	       
	        	 
	       

	        }

}

}
