package com.adpol.fastenglish;

import com.adpol.fastenglish.database.DatabaseManager;
import com.example.fastenglish.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StatActivity extends Activity implements OnClickListener  {
	
	private TextView time;
	private TextView CountWords;
	private TextView CountReplay;
	private TextView CountReplayYes;
	private TextView CountReplayNo;
	private TextView CountQuiz;
	private TextView CountQuizPoints;
	//private ImageView graph;
	private SharedPreferences prefs ;
	private float pointsSum;
	private int countQuiz;
	
	  private AdView adView;
	  private static final String AD_UNIT_ID = "ca-app-pub-1169622431309142/7079354317";
	  private LinearLayout layoutAds;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stat);
		initialize();
		
		
	}
	
	void initialize()
    {

		prefs = getSharedPreferences("prefs", 0);
       
        
		time= (TextView) findViewById(R.id.txTime);
		CountWords= (TextView) findViewById(R.id.txCountWords);
		CountReplay= (TextView) findViewById(R.id.txCountReplay);
		CountReplayYes= (TextView) findViewById(R.id.txCountReplayYes);
		CountReplayNo= (TextView) findViewById(R.id.txCountReplayNo);
		CountQuiz= (TextView) findViewById(R.id.txCountQuiz);
		CountQuizPoints= (TextView) findViewById(R.id.txCountQuizPoints);
		
		pointsSum = prefs.getFloat("pointsSum",0.0f);
		CountQuizPoints.setText(String.format( "%.2f", pointsSum ));
		
		countQuiz= prefs.getInt("countQuiz",0);
		CountQuiz.setText(""+countQuiz);
		
		CountWords.setText(Integer.toString(DatabaseManager.getInstance().countLearnedWords()));
		CountReplay.setText(Integer.toString(DatabaseManager.getInstance().countRepeats()));
		CountReplayYes.setText(Integer.toString(DatabaseManager.getInstance().countCorrectRepeats()));
		CountReplayNo.setText(Integer.toString(DatabaseManager.getInstance().countIncorrectRepeats()));
		
	//	graph = (ImageView) findViewById(R.id.iGraph);
	//	graph.setOnClickListener(this);
		adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(AD_UNIT_ID);
        
        layoutAds = (LinearLayout) findViewById(R.id.lAddsStat);
        layoutAds.addView(adView);
        
        AdRequest adRequest = new AdRequest.Builder()
        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
        .addTestDevice(MainActivity.testDevice)
        .build();
        
        adView.loadAd(adRequest);
        


    }
	  @Override
	    public void onClick(View arg0) {
	        // TODO Auto-generated method stub
	        switch(arg0.getId())
	        {
	      //  case R.id.iGraph:
	        	
	        }

	  

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

	  /** Called before the activity is destroyed. */
	  @Override
	  public void onDestroy() {
	    // Destroy the AdView.
	    if (adView != null) {
	      adView.destroy();
	    }
	    super.onDestroy();
	  }  
	  
}
