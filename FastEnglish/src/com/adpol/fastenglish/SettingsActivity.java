package com.adpol.fastenglish;

import com.adpol.fastenglish.database.Update;
import com.example.fastenglish.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class SettingsActivity extends Activity implements OnClickListener  {
	
	private ImageView selectAll;
    private ImageView clearAll;
    private ImageView changeLanguage;
    private ImageView checkUpdate;
    private TextView countWords;
    private TextView changeLanguagetxt;
    private CheckBox categorySelect;
    private int selectorChecbox;
   
    private LinearLayout layoutCategories;
    private ScrollView scrollCategories;
    private  CheckBox categories;
	private boolean engPol = true;
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		initialize();
	}
	
	void initialize()
    {

   
        selectAll = (ImageView) findViewById(R.id.iSelectAll);
        selectAll.setOnClickListener(this);
        
        clearAll = (ImageView) findViewById(R.id.iClearAll);
        clearAll.setOnClickListener(this);

        changeLanguage = (ImageView) findViewById(R.id.ichangeLanguage);
        changeLanguage.setOnClickListener(this);

        checkUpdate = (ImageView) findViewById(R.id.icheckUpdate);
        checkUpdate.setOnClickListener(this);
        
        countWords= (TextView) findViewById(R.id.tvSettingsCountWords);
        

      
        layoutCategories = (LinearLayout) findViewById(R.id.llCategories);
        scrollCategories =  (ScrollView) findViewById(R.id.svCategories);
        
      
        
        changeLanguagetxt=(TextView) findViewById(R.id.tvchangeLanguagetxt);
        if(engPol) changeLanguagetxt.setText("POL->ENG");
    	else changeLanguagetxt.setText("ENG->POL");
    	engPol = !engPol;
    	
    	for(int i = 0; i < 20; i++) {
            categories = new CheckBox(this);
            categories.setId(i);
            categories.setText("Kategoria  "+i);
            layoutCategories.addView(categories);
           }
    	
    	selectorChecbox=2;
    	categorySelect = (CheckBox)findViewById(selectorChecbox);


    }


    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch(arg0.getId())
        {
        case R.id.iSelectAll:
        	
       

        break;
        case R.id.iClearAll:
            
        	


            break;
        case R.id.ichangeLanguage:
        	if(engPol) changeLanguagetxt.setText("POL->ENG");
        	else changeLanguagetxt.setText("ENG->POL");
        	engPol = !engPol;
            


            break;
        case R.id.icheckUpdate:
        	 Update.update(this, false);
        	 break;

        }

}

}
