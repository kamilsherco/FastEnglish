package com.adpol.fastenglish;


import com.example.fastenglish.R;
import com.adpol.fastenglish.database.*;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	 	private ImageView quiz;
	    private ImageView learn;
	    private ImageView stat;
	    private ImageView settings;
	    private SharedPreferences settingsPref;
	    private boolean isEngPol;
	    private boolean firstRun;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		DatabaseManager.init(this);	
		//DatabaseManager.getInstance().updateVersion(0); //Wyzerowanie wersji
		 // Loading products in Background Thread
		//if(isOnline()) new LoadVersion().execute();
		initialize();
		if(settingsPref.getBoolean("firstUpdate", true)){
			Update.firstUpdate(this);
		}
		else Update.update(this, true);

		//new LoadAllWords().execute();
		Log.d("Wielkosc bazy s³ów: ", Integer.toString(DatabaseManager.getInstance().getAllWords().size()));
		//Log.d("Wielkosc bazy wersji: ", Integer.toString(DatabaseManager.getInstance().getAllVersions().get(0).getVersion()));
		Log.d("Wersja: ", Integer.toString(DatabaseManager.getInstance().getVersionById(1).getVersion()));
		
	}
	
	/*public static boolean isOnline() {
	    ConnectivityManager cm =
	        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}*/

    void initialize()
    {

   
        quiz = (ImageView) findViewById(R.id.iQuiz);
        quiz.setOnClickListener(this);
        learn = (ImageView) findViewById(R.id.iNauka);
        learn.setOnClickListener(this);

        stat = (ImageView) findViewById(R.id.iStat);
        stat.setOnClickListener(this);

        settings = (ImageView) findViewById(R.id.iUstawienia);
        settings.setOnClickListener(this);
        
        settingsPref = getSharedPreferences("prefs", MODE_PRIVATE);
        firstRun = settingsPref.getBoolean("firstRun", true);
        if ( firstRun )
        {
          firstRuner(); 
              
        }

        


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

}
