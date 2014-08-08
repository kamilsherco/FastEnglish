package com.adpol.fastenglish;

import java.util.List;
import java.util.Random;

import com.adpol.fastenglish.database.DatabaseManager;
import com.adpol.fastenglish.database.Word;
import com.example.fastenglish.R;

import android.app.Activity;

import android.os.Bundle;
import android.util.Log;
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
    private List<Word> wordsList;
    
    
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		initialize();
		setTextAnswer();
	}
	
	void initialize()
    {

		wordsList = DatabaseManager.getInstance().getAllWords();
		
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
        
        
        randomText(word);
        


    }
	
	private void randomText(TextView answer){
		index = new Random().nextInt(wordsList.size());
		answer.setText(wordsList.get(index).getEnWord());
		
	}
	
	private void setTextAnswer()
	{
		randomText(answerAtxt);
		randomText(answerBtxt);
		randomText(answerCtxt);
		randomText(answerDtxt);
		
	}
	
	


    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch(arg0.getId())
        {
        case R.id.iQuizAnswerA:
        	
        	if(answerAtxt.getText().toString().equals(word.getText().toString()))
        	{
        		answerA.setImageResource(R.drawable.btcorrect);
        	
        	}
        	else
        	{
        		answerA.setImageResource(R.drawable.btfail);
        	
        	}
       

        break;
        case R.id.iQuizAnswerB:
        	if(answerBtxt.getText().toString().equals(word.getText().toString()))
        	{
        		answerB.setImageResource(R.drawable.btcorrect);
        	
        	}
        	else
        	{
        		answerB.setImageResource(R.drawable.btfail);
        	
        	}
            

            break;
        case R.id.iQuizAnswerC:
        	if(answerCtxt.getText().toString().equals(word.getText().toString()))
        	{
        		answerC.setImageResource(R.drawable.btcorrect);
        	
        	}
        	else
        	{
        		answerC.setImageResource(R.drawable.btfail);
        	
        	}
        	  
            


            break;
        case R.id.iQuizAnswerD:
        	if(answerDtxt.getText().toString().equals(word.getText().toString()))
        	{
        		answerD.setImageResource(R.drawable.btcorrect);
        	
        	}
        	else
        	{
        		answerD.setImageResource(R.drawable.btfail);
        	
        	}
        	
        	
        	break;
        }

    }

}
