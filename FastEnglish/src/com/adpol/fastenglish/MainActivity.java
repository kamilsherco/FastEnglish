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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MainActivity extends Activity implements OnClickListener {

	 	private ImageView quiz;
	    private ImageView learn;
	    private ImageView stat;
	    private ImageView settings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		DatabaseManager.init(this);	
		//DatabaseManager.getInstance().updateVersion(0); //Wyzerowanie wersji
		 // Loading products in Background Thread
		//if(isOnline()) new LoadVersion().execute();
		initialize();

		Update.update(this, true);

		//new LoadAllWords().execute();
		Log.d("Wielkosc bazy s��w: ", Integer.toString(DatabaseManager.getInstance().getAllWords().size()));
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


    }

    
    

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch(arg0.getId())
        {
        case R.id.iQuiz:
     
        Intent quiz = new Intent(this, QuizActivity.class);
        startActivity(quiz);

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
