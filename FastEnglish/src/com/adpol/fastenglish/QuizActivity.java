package com.adpol.fastenglish;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.adpol.fastenglish.database.DatabaseManager;
import com.adpol.fastenglish.database.Word;
import com.example.fastenglish.R;

import android.app.Activity;

import android.os.Bundle;

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
    
    private int index;
    private int answerPos;
    
    private ArrayList<Integer> listPos ;
    private ArrayList<Integer> listAnswer ;
    private List<Word> wordsList;
    
  
    
    private int tempPos;
    private int tempIndex;
    
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		wordsList = DatabaseManager.getInstance().getAllWords();
		initialize();
		
	}
	
	void initialize()
    {

		
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
        
        
        randomTextAndPosition(word);
        answerQue();
        


    }
	
	private void randomTextAndPosition(TextView answer){
		index = new Random().nextInt(wordsList.size());
		answerPos=new Random().nextInt(4); 
		answer.setText(wordsList.get(index).getPlWord());
		
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
			answerAtxt.setText(wordsList.get(answerIndex).getEnWord());
			break;
		case 1:
			answerBtxt.setText(wordsList.get(answerIndex).getEnWord());
			break;
		case 2:
			answerCtxt.setText(wordsList.get(answerIndex).getEnWord());
			break;
		case 3:
			answerDtxt.setText(wordsList.get(answerIndex).getEnWord());
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
	
	private void showCorrectAnswer(int correct)
	{
		switch(correct){
		case 0:
			answerA.setImageResource(R.drawable.btshowcorrect);
			break;
		case 1:
			answerB.setImageResource(R.drawable.btshowcorrect);
			break;
		case 2:
			answerC.setImageResource(R.drawable.btshowcorrect);
			break;
		case 3:
			answerD.setImageResource(R.drawable.btshowcorrect);
			break;
			
		}
		
	}
	
	
	


    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch(arg0.getId())
        {
        case R.id.iQuizAnswerA:
        	
        	if(answerAtxt.getText().toString().equals(wordsList.get(index).getEnWord()))
        	{
        		
        	answerA.setImageResource(R.drawable.btcorrect);
      
        	answerA.postDelayed(new Runnable() {

                @Override
                public void run() {
                	clearAnswer();
                	
                }
            }, 500);
        	
        	}
        	else
        	{
        		answerA.setImageResource(R.drawable.btfail);
        		showCorrectAnswer(answerPos);
        		answerA.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                    	clearAnswer();
                    
                    }
                }, 1000);
        		
        	
        	}
        	
        	
       

        break;
        case R.id.iQuizAnswerB:
        	if(answerBtxt.getText().toString().equals(wordsList.get(index).getEnWord()))
        	{
        		answerB.setImageResource(R.drawable.btcorrect);
        	   	answerB.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                    	clearAnswer();
                    	
                    }
                }, 500);
        	
        	}
        	else
        	{
        		answerB.setImageResource(R.drawable.btfail);
        		showCorrectAnswer(answerPos);
        		answerB.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                    	clearAnswer();
                    
                    }
                }, 1000);
        		
        	
        	}
        	
            break;
        case R.id.iQuizAnswerC:
        	if(answerCtxt.getText().toString().equals(wordsList.get(index).getEnWord()))
        	{
        		answerC.setImageResource(R.drawable.btcorrect);
        	   	answerC.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                    	clearAnswer();
                    	
                    }
                }, 500);
        		
        	
        	}
        	else
        	{
        		answerC.setImageResource(R.drawable.btfail);
        		showCorrectAnswer(answerPos);
        		answerC.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                    	clearAnswer();
                    
                    }
                }, 1000);
        		
        	
        	}
        	  
        	

            break;
        case R.id.iQuizAnswerD:
        	if(answerDtxt.getText().toString().equals(wordsList.get(index).getEnWord()))
        	{
        		answerD.setImageResource(R.drawable.btcorrect);
        	   	answerD.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                    	clearAnswer();
                    	
                    }
                }, 500);
        		
        		
        	
        	}
        	else
        	{
        		answerD.setImageResource(R.drawable.btfail);
        		showCorrectAnswer(answerPos);
        		answerD.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                    	clearAnswer();
                    
                    }
                }, 1000);
        		
        	
        	}
        	
        	break;
        }

    }

}
