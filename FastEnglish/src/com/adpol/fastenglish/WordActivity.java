package com.adpol.fastenglish;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.adpol.fastenglish.database.DatabaseManager;
import com.adpol.fastenglish.database.Word;
import com.example.fastenglish.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WordActivity extends Activity implements OnClickListener{
	
	private final int REPEAT = 0;
	private final int LEARN = 1;
	private int activityType = REPEAT;
	
	private boolean isEngPol;
	private boolean areWordsLearned = false;
	
	private int index;
	
	private ImageView showWord;
    private ImageView btYes;
    private ImageView btNo;
    private TextView wordText;
    private TextView isAnswerKnownText;
    private TextView answerText;   
    
    private List<Word> wordsList;
    private List<Word> learnedWordsList;
   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_word);
		initialize();
	}
	
	private void initialize(){			
		Bundle bundle = getIntent().getExtras();
		
		int lastActivity = bundle.getInt("lastActivity");
		isEngPol = bundle.getBoolean("engPol");
		
		if(lastActivity == LEARN){
			activityType = LEARN;
			wordsList = DatabaseManager.getInstance().getNewRandomWords(bundle.getInt("newWords"));
			learnedWordsList = new LinkedList<Word>();
		}
		else{
			wordsList = DatabaseManager.getInstance().getLearnedWords();
		}
		
		//wordsList = DatabaseManager.getInstance().getAllWords();		
		
		wordText = (TextView) findViewById(R.id.txWordText);
		answerText = (TextView) findViewById(R.id.txAnswer);
		
		
		isAnswerKnownText = (TextView) findViewById(R.id.txIsAnswerKnownText);		
		
		showWord = (ImageView) findViewById(R.id.iShowWord);
	    showWord.setOnClickListener(this);
	        
	    btYes = (ImageView) findViewById(R.id.iYes);
	    btYes.setOnClickListener(this);

	    btNo = (ImageView) findViewById(R.id.iNo);
	    btNo.setOnClickListener(this);
	    
	    
	    
	    randomText();
	}
	
	private String returnFirstWord(){
		if(isEngPol) return wordsList.get(index).getEnWord();
		else return wordsList.get(index).getPlWord();
	}
	
	private String returnSecondWord(){
		if(isEngPol) return wordsList.get(index).getPlWord();
		else return wordsList.get(index).getEnWord();
	}
	
	private void randomText(){
		index = new Random().nextInt(wordsList.size());
		wordText.setText(returnFirstWord());
		answerText.setText(returnSecondWord());
		wordsList.get(index).setLearned(true);
		Log.d("Poprawnych: ", Integer.toString(wordsList.get(index).getCorrectRepeats()));
		Log.d("Niepoprawnych: ", Integer.toString(wordsList.get(index).getIncorrectRepeats()));
	}

	private void setVisibility(int vis){
		isAnswerKnownText.setVisibility(vis);
		answerText.setVisibility(vis);
		btYes.setVisibility(vis);
		btNo.setVisibility(vis);
	}
	
	private void clickedAnswer(boolean isKnown){
		if(isKnown) wordsList.get(index).correctIncrement();
		else wordsList.get(index).incorrectIncrement();
		DatabaseManager.getInstance().updateWord(wordsList.get(index));
		if(activityType == LEARN && areWordsLearned == false){
			learnedWordsList.add(wordsList.remove(index));			
		}
		setVisibility(View.INVISIBLE);
		if(wordsList.size()==0 && areWordsLearned == false){
			areWordsLearned = true;
			Toast.makeText(getBaseContext(), "Iloœæ podanych s³ów zosta³a wyuczona. Mo¿esz je teraz powtarzaæ lub wróciæ do poprzedniego widoku i dodaæ nowe s³owa do nauki.", Toast.LENGTH_LONG).show();
			wordsList = learnedWordsList;
		}
		randomText();
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
	        	clickedAnswer(true);
	            break;
	            
	        case R.id.iNo:
	        	clickedAnswer(false);
	            break;   

	        }

}

}
