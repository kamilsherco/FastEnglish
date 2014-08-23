package com.adpol.fastenglish;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.adpol.fastenglish.database.DatabaseManager;
import com.adpol.fastenglish.database.Word;
import com.example.fastenglish.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WordActivity extends Activity implements OnClickListener, TextToSpeech.OnInitListener,OnTouchListener{
	
	private final int REPEAT = 0;
	private final int LEARN = 1;
	private int activityType = REPEAT;
	
	private boolean isEngPol;
	private boolean areWordsLearned = false;
	
	private int index;
	
	private ImageView showWord;
    private ImageView btYes;
    private ImageView btNo;
    private ImageView soundImage;
    private TextView wordText;
    private TextView isAnswerKnownText;
    private TextView answerText; 
    private TextView btYestxt;
    private TextView btNotxt; 
    
    private List<Word> wordsList;
    private List<Word> learnedWordsList;
    
    private TextToSpeech textToSpeech;
   private boolean sound;
   private SharedPreferences prefs ;
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
	//	isEngPol = bundle.getBoolean("engPol");
		
		prefs = getSharedPreferences("prefs", 0);    
		isEngPol = prefs.getBoolean("engPol", true);
		
		
		if(lastActivity == LEARN){
			activityType = LEARN;
			wordsList = DatabaseManager.getInstance().getNewRandomWords(bundle.getInt("newWords"));
			learnedWordsList = new LinkedList<Word>();
		}
		else{
			wordsList = DatabaseManager.getInstance().getLearnedWordsFromCategories();
		}
		
		//wordsList = DatabaseManager.getInstance().getAllWords();		
		
		wordText = (TextView) findViewById(R.id.txWordText);
		answerText = (TextView) findViewById(R.id.txAnswer);
		
		
		isAnswerKnownText = (TextView) findViewById(R.id.txIsAnswerKnownText);		
		
		showWord = (ImageView) findViewById(R.id.iShowWord);
	    showWord.setOnClickListener(this);
	    showWord.setOnTouchListener(this);
	        
	    btYes = (ImageView) findViewById(R.id.iYes);
	    btYes.setOnClickListener(this);
	    btYes.setOnTouchListener(this);
	    
	    soundImage=(ImageView) findViewById(R.id.iSoundWord);
	    soundImage.setOnClickListener(this);
	    soundImage.setOnTouchListener(this);
	    
	    btYestxt=(TextView) findViewById(R.id.iYestxt);

	    btNo = (ImageView) findViewById(R.id.iNo);
	    btNo.setOnClickListener(this);
	    btNo.setOnTouchListener(this);
	    btNotxt=(TextView) findViewById(R.id.iNotxt);
	    
	    textToSpeech = new TextToSpeech(this, this);
	    
	    randomText();
	    
	    sound= prefs.getBoolean("sound", true);
        
        
        if(sound) soundImage.setImageResource(R.drawable.sound);
    	else soundImage.setImageResource(R.drawable.sound_mute);
        sound=!sound;
	}
	
	private String returnFirstWord(){
		if(isEngPol) return wordsList.get(index).getEnWord();
		else return wordsList.get(index).getPlWord();
	}
	
	private String returnSecondWord(){
		if(isEngPol) return wordsList.get(index).getPlWord();
		else return wordsList.get(index).getEnWord();
	}
	
	private String returnEnglishWord(){
		if(isEngPol) return returnFirstWord();
		else return returnSecondWord();
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
		btYestxt.setVisibility(vis);
		btNotxt.setVisibility(vis);
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
	        	convertTextToSpeech();
	        	break;
	        	
	        case R.id.iYes:
	        	clickedAnswer(true);
	            break;
	            
	        case R.id.iNo:
	        	clickedAnswer(false);
	            break;
	            
	        case R.id.iSoundWord:
	        	 if(sound)
	        		 {
	        		 soundImage.setImageResource(R.drawable.sound);
	        		 SharedPreferences.Editor editor = prefs.edit();    
	    	       	 editor.putBoolean("sound", true);       
	    	       	 editor.commit();
	        		 }
	         	else 
	         		{
	         		soundImage.setImageResource(R.drawable.sound_mute);
	         		 SharedPreferences.Editor editor = prefs.edit();    
	    	       	 editor.putBoolean("sound", false);       
	    	       	 editor.commit();
	         		}
	             sound=!sound;
	        	break;

	        }

}

		@Override
		public void onInit(int status) {
			if (status == TextToSpeech.SUCCESS) {
				int result = textToSpeech.setLanguage(Locale.UK);
				if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
					Log.e("error", "This Language is not supported");
				} else {
					//convertTextToSpeech();
				}
			} else {
				Log.e("error", "Initilization Failed!");
			}			
		}
		
		private void convertTextToSpeech() {
		
			String text = returnEnglishWord();
			if (null == text || "".equals(text)) {
				text = "Please give some input.";
			}
			textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			
			switch(v.getId())
	        {
	        case R.id.iShowWord:
	        	if(event.getAction() == MotionEvent.ACTION_DOWN)
	        		showWord.setImageResource(R.drawable.btshowtranslatesel);
                else
                	showWord.setImageResource(R.drawable.btshowtranslate);
	        	
	        	break;
	        	
	        case R.id.iYes:
	        	
	        	if(event.getAction() == MotionEvent.ACTION_DOWN)
	        		btYes.setImageResource(R.drawable.btyessel);
                else
                	btYes.setImageResource(R.drawable.btyes);
	        	
	            break;
	            
	        case R.id.iNo:
	        	if(event.getAction() == MotionEvent.ACTION_DOWN)
	        		btNo.setImageResource(R.drawable.btnosel);
                else
                	btNo.setImageResource(R.drawable.btno);
	        	
	            break; 
	            
	   	 case R.id.iSoundWord:
	      	if(event.getAction() == MotionEvent.ACTION_DOWN)
	      		soundImage.setImageResource(R.drawable.sound);
	          else
	          {
	          	
	         		soundImage.setImageResource(R.drawable.soundsel);
	            
	              
	          }
	      	
	      	break;

	        }
			
			return false;
		}
		
		@Override
		protected void onDestroy() {
			// TODO Auto-generated method stub
			textToSpeech.shutdown();
			super.onDestroy();
			
		}

}
