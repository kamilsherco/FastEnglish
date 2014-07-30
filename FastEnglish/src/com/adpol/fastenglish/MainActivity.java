package com.adpol.fastenglish;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.fastenglish.R;
import com.adpol.fastenglish.database.*;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		DatabaseManager.init(this);	
		 // Loading products in Background Thread
		new LoadAllWords().execute();
		Log.d("Wielkosc bazy s³ów: ", Integer.toString(DatabaseManager.getInstance().getAllWords().size()));
      
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
