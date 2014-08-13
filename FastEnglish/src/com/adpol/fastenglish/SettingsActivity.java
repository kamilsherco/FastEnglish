package com.adpol.fastenglish;

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
import android.widget.TextView;

public class SettingsActivity extends Activity implements OnClickListener  {
	
	private ImageView selectAll;
    private ImageView clearAll;
    private ImageView changeLanguage;
    private ImageView checkUpdate;
    private TextView countWords;
    private TextView changeLanguagetxt;
    private CheckBox categoryA;
    private CheckBox categoryB;
    private CheckBox categoryC;
    private CheckBox categoryD;
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
        
        categoryA = (CheckBox)findViewById(R.id.cbA);
        categoryB = (CheckBox)findViewById(R.id.cbB);
        categoryC = (CheckBox)findViewById(R.id.cbC);
        categoryD = (CheckBox)findViewById(R.id.cbD);
        
        changeLanguagetxt=(TextView) findViewById(R.id.tvchangeLanguagetxt);
        if(engPol) changeLanguagetxt.setText("POL->ENG");
    	else changeLanguagetxt.setText("ENG->POL");
    	engPol = !engPol;
        


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
        	 
       

        }

}

}
