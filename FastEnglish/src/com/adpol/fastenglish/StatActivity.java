package com.adpol.fastenglish;

import com.example.fastenglish.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class StatActivity extends Activity implements OnClickListener  {
	
	private TextView time;
	private TextView CountWords;
	private TextView CountReplay;
	private TextView CountReplayYes;
	private TextView CountReplayNo;
	private TextView CountQuiz;
	private TextView CountQuizPoints;
	private ImageView graph;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stat);
		initialize();
	}
	
	void initialize()
    {

   
       
        
		time= (TextView) findViewById(R.id.txTime);
		CountWords= (TextView) findViewById(R.id.txCountWords);
		CountReplay= (TextView) findViewById(R.id.txCountReplay);
		CountReplayYes= (TextView) findViewById(R.id.txCountReplayYes);
		CountReplayNo= (TextView) findViewById(R.id.txCountReplayNo);
		CountQuiz= (TextView) findViewById(R.id.txCountQuiz);
		CountQuizPoints= (TextView) findViewById(R.id.txCountQuizPoints);
	//	graph = (ImageView) findViewById(R.id.iGraph);
	//	graph.setOnClickListener(this);
       
        


    }
	  @Override
	    public void onClick(View arg0) {
	        // TODO Auto-generated method stub
	        switch(arg0.getId())
	        {
	      //  case R.id.iGraph:
	        	
	        }
	  }
	  

}
