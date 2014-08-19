package com.adpol.fastenglish;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.adpol.fastenglish.database.DatabaseManager;
import com.adpol.fastenglish.database.Word;
import com.example.fastenglish.R;

import android.app.Activity;
import android.content.SharedPreferences;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class QuizActivity extends Activity implements OnClickListener  {

	private ImageView answerA;
    private ImageView answerB;
    private ImageView answerC;
    private ImageView answerD;
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
    
    private float points;
    private float pointsSum;
    private Handler mUIHandler;
    private TimerTask timerTask;
    private Timer timer;
    private int countQuiz;
   
    
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		 prefs = getSharedPreferences("prefs", 0);
		 pointsSum = prefs.getFloat("pointsSum",0.0f);
		 countQuiz= prefs.getInt("countQuiz",0);
		 countQuiz++;
		wordsList = DatabaseManager.getInstance().getAllWords();
		initialize();
		
	}
	
	void initialize()
    {
		
		points=20;
		
		listPos = new ArrayList<Integer>();
		listAnswer = new ArrayList<Integer>();
		
        answerA = (ImageView) findViewById(R.id.iQuizAnswerA);
        answerA.setOnClickListener(this);
       
        answerB = (ImageView) findViewById(R.id.iQuizAnswerB);
        answerB.setOnClickListener(this);

        answerC = (ImageView) findViewById(R.id.iQuizAnswerC);
        answerC.setOnClickListener(this);

        answerD = (ImageView) findViewById(R.id.iQuizAnswerD);
        answerD.setOnClickListener(this);
        
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
        
        randomTextAndPosition(word);
        answerQue();
      
     
        updatePointsTime();
     
       
        
        

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
		answerA.setImageResource(R.drawable.btcle);
		answerB.setImageResource(R.drawable.btcle);
		answerC.setImageResource(R.drawable.btcle);
		answerD.setImageResource(R.drawable.btcle);
		
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
	                mUIHandler.postDelayed(this, 450);  
	               
	            }
	        });
	       
	        timer.schedule(timerTask, 1500, 500);
	        
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
        	answerAclicked=true;
        	answerClickPermission=false;
        	
        	showWinPoints();
        	
        	answerA.postDelayed(new Runnable() {

                @Override
                public void run() {
                	
                	
                	clearAnswer();
                	timer.cancel();
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
        }

    }

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		SharedPreferences.Editor editor = prefs.edit();    
      	 editor.putFloat("pointsSum",pointsSum );  
      	 editor.putInt("countQuiz", countQuiz);
      	 editor.commit();
		
	}
    
  

}
