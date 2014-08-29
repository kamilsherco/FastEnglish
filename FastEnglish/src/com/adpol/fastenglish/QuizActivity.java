package com.adpol.fastenglish;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.adpol.fastenglish.database.DatabaseManager;
import com.adpol.fastenglish.database.Word;
import com.example.fastenglish.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;



import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class QuizActivity extends Activity implements OnClickListener, TextToSpeech.OnInitListener, OnTouchListener {

	private ImageView answerA;
    private ImageView answerB;
    private ImageView answerC;
    private ImageView answerD;
    private ImageView soundImage;
    private TextView word;
    private TextView answerAtxt;
    private TextView answerBtxt;
    private TextView answerCtxt;
    private TextView answerDtxt;
    private TextView pointsWin;
    private TextView pointsView;
   
    
    private int index;
    private int answerPos;
    private int tempPos;
    private int tempIndex;
    private ArrayList<Integer> listPos ;
    private ArrayList<Integer> listAnswer ;
    private List<Word> wordsList;
    
    private SharedPreferences prefs ;
    private boolean engPol;
    private String selectAnswer;
    private String failAnswer;
    
    private boolean answerAclicked;
    private boolean answerBclicked;
    private boolean answerCclicked;
    private boolean answerDclicked;
    private boolean answerClickPermission;
    private boolean sound;
    
    private float points;
    private float pointsSum;
    private Handler mUIHandler;
    private TimerTask timerTask;
    private Timer timer;
    private int countQuiz;
    
    private boolean isStarted = false;
    
    private TextToSpeech textToSpeech = null;
   
    private AdView adView;
    private static final String AD_UNIT_ID = "ca-app-pub-1169622431309142/7079354317";
    private LinearLayout layoutAds;
    
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		 prefs = getSharedPreferences("prefs", 0);
		 pointsSum = prefs.getFloat("pointsSum",0.0f);
		 countQuiz= prefs.getInt("countQuiz",0);
		 countQuiz++;
		wordsList = DatabaseManager.getInstance().getLearnedWordsFromCategories();
		initialize();
		
		
			adView = new AdView(this);
			adView.setAdSize(AdSize.BANNER);
			adView.setAdUnitId(AD_UNIT_ID);
			layoutAds = (LinearLayout) findViewById(R.id.lAddsQuiz);
	        layoutAds.addView(adView);
	        
	        AdRequest adRequest = new AdRequest.Builder().build();
	        
	        adView.loadAd(adRequest);
		
	}
	
	void initialize()
    {
		
		points=20;
		
		listPos = new ArrayList<Integer>();
		listAnswer = new ArrayList<Integer>();
		
		
		
		
        answerA = (ImageView) findViewById(R.id.iQuizAnswerA);
        answerA.setOnClickListener(this);
        answerA.setOnTouchListener(this);
       
        answerB = (ImageView) findViewById(R.id.iQuizAnswerB);
        answerB.setOnClickListener(this);
        answerB.setOnTouchListener(this);
        
        answerC = (ImageView) findViewById(R.id.iQuizAnswerC);
        answerC.setOnClickListener(this);
        answerC.setOnTouchListener(this);
        
        answerD = (ImageView) findViewById(R.id.iQuizAnswerD);
        answerD.setOnClickListener(this);
        answerD.setOnTouchListener(this);
        
        soundImage=(ImageView) findViewById(R.id.iSound);
        soundImage.setOnClickListener(this);
        soundImage.setOnTouchListener(this);
        
        word = (TextView) findViewById(R.id.tvQuizWord);
        
        answerAtxt = (TextView) findViewById(R.id.iQuizAnswerAtxt);
        answerBtxt = (TextView) findViewById(R.id.iQuizAnswerBtxt);
        answerCtxt = (TextView) findViewById(R.id.iQuizAnswerCtxt);
        answerDtxt = (TextView) findViewById(R.id.iQuizAnswerDtxt);
        
        pointsView=(TextView) findViewById(R.id.tvQuizPoints);
        pointsView.setText("Punkty do zdobycia: "+points);
        pointsView.setVisibility(View.VISIBLE);
        
        pointsWin=(TextView) findViewById(R.id.tvQuizPointsWin);
        pointsWin.setVisibility(View.INVISIBLE);
        
       
        engPol= prefs.getBoolean("engPol", true);
        
        answerAclicked=false;
		answerBclicked=false;
		answerCclicked=false;
		answerDclicked=false;
		answerClickPermission=true;
		
		if(!isStarted){
			//if(textToSpeech != null) textToSpeech.shutdown();
			textToSpeech = new TextToSpeech(this, this);
			isStarted=true;
		}
        randomTextAndPosition(word);
        answerQue();
      
     
        updatePointsTime();
     
        sound= prefs.getBoolean("sound", true);
        
        
        if(sound) soundImage.setImageResource(R.drawable.sound);
    	else soundImage.setImageResource(R.drawable.sound_mute);
        sound=!sound;
       
       
        

    }
	
	private void randomTextAndPosition(TextView answer){
		index = new Random().nextInt(wordsList.size());
		answerPos=new Random().nextInt(4); 
		answer.setText(wordsList.get(index).getPlWord());
		if(engPol)
		{
			answer.setText(wordsList.get(index).getEnWord());
			 
		}
		else
		{
			answer.setText(wordsList.get(index).getPlWord());
		}
		
	}
	
	private void answerQue()
	{
		

		setTextAnswer(index,answerPos);
		
		listPos.add(answerPos);
		listAnswer.add(index);
	
		while (listPos.size() < 4) {

			tempPos = new Random().nextInt(4);
			tempIndex =new Random().nextInt(wordsList.size());
		    if (!listPos.contains(tempPos) && !listAnswer.contains(tempIndex)) {
		    	  setTextAnswer(tempIndex,tempPos);
		    	
		    	listPos.add(tempPos);
		    	listAnswer.add(tempIndex);
		    }
		}
	
		
		
	    
		
		
	}
	
	private void setTextAnswer(int answerIndex,int answerPos)
	{
		
		switch(answerPos){
		case 0:
			if(engPol)
			{
			answerAtxt.setText(wordsList.get(answerIndex).getPlWord());
			}else
			{
				answerAtxt.setText(wordsList.get(answerIndex).getEnWord());
			}
			break;
		case 1:
			if(engPol)
			{
				answerBtxt.setText(wordsList.get(answerIndex).getPlWord());
			}
			else
			{
				answerBtxt.setText(wordsList.get(answerIndex).getEnWord());
			}
			break;
		case 2:
			if(engPol)
			{
				answerCtxt.setText(wordsList.get(answerIndex).getPlWord());
			}
			else
			{
				answerCtxt.setText(wordsList.get(answerIndex).getEnWord());
			}
			break;
		case 3:
			if(engPol)
			{
				answerDtxt.setText(wordsList.get(answerIndex).getPlWord());
			}
			else
			{
				answerDtxt.setText(wordsList.get(answerIndex).getEnWord());
			}
			break;
			
		}
	
		
	}
	private void clearAnswer()
	{
		answerA.setImageResource(R.drawable.btshowtranslate);
		answerB.setImageResource(R.drawable.btshowtranslate);
		answerC.setImageResource(R.drawable.btshowtranslate);
		answerD.setImageResource(R.drawable.btshowtranslate);
		
		initialize();
	
	}
	
	private void showWinPoints()
	{
		
		pointsView.setVisibility(View.INVISIBLE);
    	pointsWin.setText("Zdobyte punkty to : "+String.format( "%.2f", points )+" !!!");
    	pointsSum=pointsSum+points;
    	
    	pointsWin.setVisibility(View.VISIBLE);
		
	}
	
	private void updatePoints()
	{
		if(points>5)
		{
		points=points-5;
		pointsView.setText("Punkty do zdobycia: "+String.format( "%.2f", points ));
		}
		
		
	}
	public void updatePointsTime()
	{
		 timer = new Timer();
	        
	        timerTask = new TimerTask() {
	         @Override
	         public void run() {
	            //refresh your textview
	        	 if(points>=0.1)
	     	        	points=(float) (points-0.1);
	        	 
	        	 
	         }
	        };
	       
	        
	        
		 
		
	        
	        mUIHandler = new Handler(Looper.getMainLooper());
	        mUIHandler.post(new Runnable() {
	        	  

	            @Override
	            public void run() {
	            	
	            	pointsView.setText("Punkty do zdobycia: " + String.format( "%.2f", points ));
	                mUIHandler.postDelayed(this, 200);  
	               
	            }
	        });
	       
	        timer.schedule(timerTask, 500, 300);
	        
	}

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
    	
    	if(engPol)
		{
    		selectAnswer=wordsList.get(index).getPlWord();
		}
    	else
    	{
    		selectAnswer=wordsList.get(index).getEnWord();
    	}
    	
    	
    	
        switch(arg0.getId())
        {
        case R.id.iQuizAnswerA:
        	if(!answerAclicked && answerClickPermission)
        	{
        	if(answerAtxt.getText().toString().equals(selectAnswer))
        	{
        		
        	answerA.setImageResource(R.drawable.btcorrect);
        	
        	if(prefs.getBoolean("sound", true))
        	convertTextToSpeech(wordsList.get(index).getEnWord());
        	
        	
        	answerAclicked=true;
        	answerClickPermission=false;
        	
        	showWinPoints();
        	
        	answerA.postDelayed(new Runnable() {

                @Override
                public void run() {
                	
                	
                	clearAnswer();
                	
                }
            }, 1500);
        	
        	}
        	else
        	{
        		answerA.setImageResource(R.drawable.btfail);
        		answerAclicked=true;
        		updatePoints();
        		
        		failAnswer=answerAtxt.getText().toString();
        		
        	   	if(engPol)
        		{
        		answerAtxt.setText(failAnswer+" - "+wordsList.get(listAnswer.get(listPos.indexOf(0))).getEnWord());
        	
        		}
        	   	else
        	   	{
        	   	answerAtxt.setText(failAnswer+" - "+wordsList.get(listAnswer.get(listPos.indexOf(0))).getPlWord());
        	   	}
        	
        	
        	}
        	
        	}
       

        break;
        case R.id.iQuizAnswerB:
        	
        	if(!answerBclicked && answerClickPermission)
        	{
        	if(answerBtxt.getText().toString().equals(selectAnswer))
        	{
        		answerB.setImageResource(R.drawable.btcorrect);

        		if(prefs.getBoolean("sound", true))
                	convertTextToSpeech(wordsList.get(index).getEnWord());
        		
        		answerBclicked=true;
        		answerClickPermission=false;
        		
        		showWinPoints();
        		
        	   	answerB.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                    	clearAnswer();
                    	timer.cancel();
                    	
                    }
                }, 1500);
        	
        	}
        	else
        	{
        		answerB.setImageResource(R.drawable.btfail);
        		answerBclicked=true;
        		updatePoints();
        		failAnswer=answerBtxt.getText().toString();
        		
        	   	if(engPol)
        		{
        		answerBtxt.setText(failAnswer+" - "+wordsList.get(listAnswer.get(listPos.indexOf(1))).getEnWord());
        		
        		}
        	   	else
        	   	{
        	   	answerBtxt.setText(failAnswer+" - "+wordsList.get(listAnswer.get(listPos.indexOf(1))).getPlWord());
        	   	}
        	
        	}
        	}
        	
            break;
        case R.id.iQuizAnswerC:
        	if(!answerCclicked && answerClickPermission)
        	{
        	if(answerCtxt.getText().toString().equals(selectAnswer))
        	{
        		answerC.setImageResource(R.drawable.btcorrect);
        		
        		if(prefs.getBoolean("sound", true))
                	convertTextToSpeech(wordsList.get(index).getEnWord());
        		
        		answerCclicked=true;
        		answerClickPermission=false;
        		
        		showWinPoints();
        		
        	   	answerC.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                    	clearAnswer();
                    	timer.cancel();
                    	
                    }
                }, 1500);
        		
        	
        	}
        	else
        	{
        		answerC.setImageResource(R.drawable.btfail);
        		answerCclicked=true;
        		failAnswer=answerCtxt.getText().toString();
        		updatePoints();
        	   	if(engPol)
        		{
        		answerCtxt.setText(failAnswer+" - "+wordsList.get(listAnswer.get(listPos.indexOf(2))).getEnWord());
        		
        		}
        	   	else
        	   	{
        	   	answerCtxt.setText(failAnswer+" - "+wordsList.get(listAnswer.get(listPos.indexOf(2))).getPlWord());
        	   	}
        		
        	
        	}
        	}
        	  
        	

            break;
        case R.id.iQuizAnswerD:
        	if(!answerDclicked && answerClickPermission)
        	{
        	if(answerDtxt.getText().toString().equals(selectAnswer))
        	{
        		answerD.setImageResource(R.drawable.btcorrect);
        		
        		if(prefs.getBoolean("sound", true))
                	convertTextToSpeech(wordsList.get(index).getEnWord());
        		
        		answerDclicked=true;
        		answerClickPermission=false;
        		
        		showWinPoints();
            	
        	   	answerD.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                    	clearAnswer();
                    	timer.cancel();
                    	
                    }
                }, 1500);
        		
        		
        	
        	}
        	else
        	{
        		answerD.setImageResource(R.drawable.btfail);
        		answerDclicked=true;
        		failAnswer=answerDtxt.getText().toString();
        		updatePoints();
        	   	if(engPol)
        		{
        		answerDtxt.setText(failAnswer+" - "+wordsList.get(listAnswer.get(listPos.indexOf(3))).getEnWord());
        		
        		}
        	   	else
        	   	{
        	   	answerDtxt.setText(failAnswer+" - "+wordsList.get(listAnswer.get(listPos.indexOf(3))).getPlWord());
        	   	}
        		
        		
        	
        	}
        	}
        	
        	break;
        	
        case R.id.iSound:
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
    
    private void convertTextToSpeech(String text) {
		
		//String text = selectAnswer;
		if (null == text || "".equals(text)) {
			text = " ";
		}
		textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	}
    @Override
	  public void onResume() {
	    super.onResume();
	    if (adView != null) {
	      adView.resume();
	    }
	  }

	  @Override
	  public void onPause() {
	    if (adView != null) {
	      adView.pause();
	    }
	    super.onPause();
	  }
 

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		 // Destroy the AdView.
	    if (adView != null) {
	      adView.destroy();
	    }
		textToSpeech.shutdown();
		super.onDestroy();
		SharedPreferences.Editor editor = prefs.edit();    
      	 editor.putFloat("pointsSum",pointsSum );  
      	 editor.putInt("countQuiz", countQuiz);
      	 editor.commit();
		
	}
	
	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			int result = textToSpeech.setLanguage(Locale.UK);
			if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
			//	Log.e("error", "This Language is not supported");
			} else {
				//convertTextToSpeech();
			}
		} else {
			//Log.e("error", "Initilization Failed!");
		}					
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		
		
		 switch(v.getId())
	        {
	        case R.id.iQuizAnswerA:
	        	if(event.getAction() == MotionEvent.ACTION_DOWN)
	        		answerA.setImageResource(R.drawable.btshowtranslatesel);
                else
                {
                	if(!answerAclicked)
                	answerA.setImageResource(R.drawable.btshowtranslate);
                	else
                	answerA.setImageResource(R.drawable.btfail);	
                }
	       

	        break;
	        case R.id.iQuizAnswerB:
	        	if(event.getAction() == MotionEvent.ACTION_DOWN)
	        		answerB.setImageResource(R.drawable.btshowtranslatesel);
                else
                {
                	if(!answerBclicked)
                	answerB.setImageResource(R.drawable.btshowtranslate);
                	else
                	answerB.setImageResource(R.drawable.btfail);	
                }
	        
	        	
	            break;
	        case R.id.iQuizAnswerC:
	        	if(event.getAction() == MotionEvent.ACTION_DOWN)
	        		answerC.setImageResource(R.drawable.btshowtranslatesel);
                else
                {
                	if(!answerCclicked)
                    	answerC.setImageResource(R.drawable.btshowtranslate);
                    	else
                    	answerC.setImageResource(R.drawable.btfail);
                }
	        	

	            break;
	        case R.id.iQuizAnswerD:
	        	if(event.getAction() == MotionEvent.ACTION_DOWN)
	        		answerD.setImageResource(R.drawable.btshowtranslatesel);
                else
                {
                	if(!answerDclicked)
                    	answerD.setImageResource(R.drawable.btshowtranslate);
                    	else
                    	answerD.setImageResource(R.drawable.btfail);
                }
	        	
	        	break;
	        
		 
	 case R.id.iSound:
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
    
  

}
