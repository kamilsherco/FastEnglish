package com.adpol.fastenglish;

import java.util.List;
import java.util.Random;

import com.adpol.fastenglish.database.DatabaseManager;
import com.adpol.fastenglish.database.Word;
import com.example.fastenglish.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WordActivity extends Activity implements OnClickListener{
	
	private ImageView showWord;
    private ImageView btYes;
    private ImageView btNo;
    private TextView wordText;
    private TextView isAnswerKnownText;
    private TextView answerText;
   
    
    private List<Word> wordsList;
   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_word);
		initialize();
	}
	
	private void initialize(){	
		
		wordsList = DatabaseManager.getInstance().getAllWords();		
		
		wordText = (TextView) findViewById(R.id.iWordText);
		answerText = (TextView) findViewById(R.id.txAnswer);
		
		
		isAnswerKnownText = (TextView) findViewById(R.id.iIsAnswerKnownText);		
		
		showWord = (ImageView) findViewById(R.id.iShowWord);
	    showWord.setOnClickListener(this);
	        
	    btYes = (ImageView) findViewById(R.id.iYes);
	    btYes.setOnClickListener(this);

	    btNo = (ImageView) findViewById(R.id.iNo);
	    btNo.setOnClickListener(this);
	    
	    
	    
	    randomText();
	}
	
	private void randomText(){
		int index = new Random().nextInt(wordsList.size());
		wordText.setText(wordsList.get(index).getEnWord());
		answerText.setText(wordsList.get(index).getEnWord());
	}

	private void setVisibility(int vis){
		isAnswerKnownText.setVisibility(vis);
		answerText.setVisibility(vis);
		btYes.setVisibility(vis);
		btNo.setVisibility(vis);
	}

	    @Override
	    public void onClick(View arg0) {
	        // TODO Auto-generated method stub
	        switch(arg0.getId())
	        {
	        case R.id.iShowWord:
	        	setVisibility(View.VISIBLE); 
	        	break;
	        	
	        case R.id.iYes:
	            setVisibility(View.INVISIBLE);
	            randomText();
	            break;
	            
	        case R.id.iNo:
	        	setVisibility(View.INVISIBLE);
	        	randomText();
	            break;
	       
	        	 
	       

	        }

}

}
