package com.adpol.fastenglish;


import com.example.fastenglish.R;
import com.google.android.gms.ads.AdRequest;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdSize;

import com.adpol.fastenglish.database.*;


import android.os.Bundle;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener, OnTouchListener {

	 	private ImageView quiz;
	    private ImageView learn;
	    private ImageView stat;
	    private ImageView settings;
	    private SharedPreferences settingsPref;
	
	    private boolean firstRun;
	    private AdView adView;
	    private static final String AD_UNIT_ID = "ca-app-pub-1169622431309142/7079354317";
	    private LinearLayout layoutAds;
	    public static String testDevice="9CC4C7BDED0E7DD70138F325A04ADEA1";


	    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		DatabaseManager.init(this);	
		
		initialize();
		if(settingsPref.getBoolean("firstUpdate", true)){
			Update.firstUpdate(this);
		}
	
		//Log.d("Wielkosc bazy s³ów: ", Integer.toString(DatabaseManager.getInstance().getAllWords().size()));

	///	Log.d("Wersja: ", Integer.toString(DatabaseManager.getInstance().getVersionById(1).getVersion()));
		
	}
	


    void initialize()
    {

   
        quiz = (ImageView) findViewById(R.id.iQuiz);
        quiz.setOnClickListener(this);
        quiz.setOnTouchListener(this);
        learn = (ImageView) findViewById(R.id.iNauka);
        learn.setOnClickListener(this);
        learn.setOnTouchListener(this);
        

        stat = (ImageView) findViewById(R.id.iStat);
        stat.setOnClickListener(this);
        stat.setOnTouchListener(this);

        settings = (ImageView) findViewById(R.id.iUstawienia);
        settings.setOnClickListener(this);
        settings.setOnTouchListener(this);
        
        settingsPref = getSharedPreferences("prefs", MODE_PRIVATE);
        firstRun = settingsPref.getBoolean("firstRun", true);
        if ( firstRun )
        {
          firstRuner(); 
              
        }
        
        adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(AD_UNIT_ID);
        
        layoutAds = (LinearLayout) findViewById(R.id.lAddsMain);
        layoutAds.addView(adView);
        
        AdRequest adRequest = new AdRequest.Builder()
        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
        .addTestDevice(testDevice)
        .build();
        
        adView.loadAd(adRequest);



    }
    
    void firstRuner()
    {
    	 settingsPref.edit().putBoolean("firstRun", false).commit();
    	 
    
    	 SharedPreferences.Editor editor = settingsPref.edit();    
    	 editor.putBoolean("engPol", true);    
    	
      	
    	 //editor.putBoolean("firstUpdate", false);
    	 editor.commit();
  	
    }

    public void afterFirstUpdate(){
    	SharedPreferences.Editor editor = settingsPref.edit();    
   	 	editor.putBoolean("firstUpdate", false);
   	 	editor.commit();
    }
    

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch(arg0.getId())
        {
        case R.id.iQuiz:
        	if(DatabaseManager.getInstance().countLearnedWordsFromCategories() < 4){
        		Toast.makeText(getBaseContext(), "Musisz nauczyæ siê przynajmniej czterech s³ów aby zagraæ w quiz.", Toast.LENGTH_LONG).show();
        	}
        	else{
		        Intent quiz = new Intent(this, QuizActivity.class);
		        startActivity(quiz);
        	}

        break;
        case R.id.iNauka:
            Intent learn = new Intent(this, LearnActivity.class);
            startActivity(learn);


            break;
        case R.id.iStat:
        	  Intent stat = new Intent(this, StatActivity.class);
              startActivity(stat);
            


            break;
        case R.id.iUstawienia:
        	  Intent settings = new Intent(this, SettingsActivity.class);
              startActivity(settings);
       

        }

    }

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		
		 switch(v.getId())
	        {
	        case R.id.iQuiz:
	        	if(event.getAction() == MotionEvent.ACTION_DOWN)
	        		quiz.setImageResource(R.drawable.btclearsel);
                else
                	quiz.setImageResource(R.drawable.btclear);	
                

	        break;
	        case R.id.iNauka:
	            
	        	if(event.getAction() == MotionEvent.ACTION_DOWN)
	        		learn.setImageResource(R.drawable.btclearsel);
                else
                	learn.setImageResource(R.drawable.btclear);	

	            break;
	        case R.id.iStat:
	        	  
	        	if(event.getAction() == MotionEvent.ACTION_DOWN)
	        		stat.setImageResource(R.drawable.btclearsel);
                else
                	stat.setImageResource(R.drawable.btclear);	


	            break;
	        case R.id.iUstawienia:
	        	  
	        	if(event.getAction() == MotionEvent.ACTION_DOWN)
	        		settings.setImageResource(R.drawable.btclearsel);
                else
                	settings.setImageResource(R.drawable.btclear);	

	        }
		
		return false;
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
