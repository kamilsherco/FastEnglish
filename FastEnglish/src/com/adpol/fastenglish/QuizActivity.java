package com.adpol.fastenglish;

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
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		initialize();
	}
	
	void initialize()
    {

   
        answerA = (ImageView) findViewById(R.id.iQuizAnswerA);
        answerA.setOnClickListener(this);
       
        answerB = (ImageView) findViewById(R.id.iQuizAnswerB);
        answerB.setOnClickListener(this);

        answerC = (ImageView) findViewById(R.id.iQuizAnswerC);
        answerC.setOnClickListener(this);

        answerD = (ImageView) findViewById(R.id.iQuizAnswerD);
        answerD.setOnClickListener(this);
        
        word = (TextView) findViewById(R.id.tvQuizWord);
        


    }


    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch(arg0.getId())
        {
        case R.id.iQuizAnswerA:
     
       

        break;
        case R.id.iQuizAnswerB:
            

            break;
        case R.id.iQuizAnswerC:
        	  
            


            break;
        case R.id.iQuizAnswerD:
        	

        }

    }

}
