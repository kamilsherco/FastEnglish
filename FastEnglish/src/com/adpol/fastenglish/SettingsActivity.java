package com.adpol.fastenglish;

import java.util.ArrayList;
import java.util.List;

import com.adpol.fastenglish.database.Category;
import com.adpol.fastenglish.database.DatabaseManager;
import com.adpol.fastenglish.database.Update;
import com.adpol.fastenglish.database.Word;
import com.example.fastenglish.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

public class SettingsActivity extends Activity implements OnClickListener  {
	
	private ImageView selectAll;
	private ImageView clearAll;
    private ImageView changeLanguage;
    private ImageView checkUpdate;
    private TextView countWords;
    private TextView changeLanguagetxt;
    private TextView selectAlltxt;
    private ScrollView scrollCategories;
    private LinearLayout layoutCategories;
    
    private  CheckBox categories;

	private boolean engPol;
	private static SharedPreferences prefs;
	private List<Category> categoryList;
	private boolean selectAllcheck;
	public static ArrayList<CheckBox> checkArray;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		initialize();
	}
	
	void initialize()
    {
		categoryList=DatabaseManager.getInstance().getAllCategory();
   
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
        scrollCategories = (ScrollView) findViewById(R.id.svCategories);
        scrollCategories.setScrollbarFadingEnabled(false);
        
    
        changeLanguagetxt=(TextView) findViewById(R.id.tvchangeLanguagetxt);
        prefs = getSharedPreferences("prefs", 0);
        
        engPol= prefs.getBoolean("engPol", true);
        
        
        if(engPol) changeLanguagetxt.setText("ENG->POL");
    	else changeLanguagetxt.setText("POL->ENG");
        engPol=!engPol;
        selectAllcheck=prefs.getBoolean("selectAll", false);
        createCategoriesCheckBox();


    }
	
	
	private void createCategoriesCheckBox()
	{
		
       
       checkArray = new ArrayList<CheckBox>();
      StringBuilder textTemp;
		for(int i = 0; i < prefs.getInt("SizeChbx", 20); i++) {
            categories = new CheckBox(this);
            categories.setId(categoryList.get(i).getId_category());  
            textTemp = new StringBuilder("");
            textTemp.append(categoryList.get(i).getName());
            textTemp.append(" ("+DatabaseManager.getInstance().countLearnedWordsFromCategory(i));
            textTemp.append("/"+DatabaseManager.getInstance().countAllWordsFromCategory(i));
            textTemp.append(")");
            categories.setText(textTemp.toString());
            categories.setChecked(prefs.getBoolean(Integer.toString(i), true));
            layoutCategories.addView(categories);
            checkArray.add(categories);
            
            
           }
		saveArray();
		
	
    	
	}
	
	
	public boolean saveArray()
	{
		SharedPreferences.Editor editor = prefs.edit(); 
	   
		editor.putInt("SizeChbx",checkArray.size()); 

	    for(int i=0;i<checkArray.size();i++)  
	    {
	    	editor.remove( Integer.toString(i));
	    	editor.putBoolean(Integer.toString(i), checkArray.get(i).isChecked());  
	    	if(!checkArray.get(i).isChecked())
	    	{
	    		editor.putBoolean("selectAll", true);  	
	    	}
	    	
	    }
	    
	    

	    return editor.commit();     
	}
	
	
    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch(arg0.getId())
        {
        case R.id.iSelectAll:
        	
        		for(int i=0; i<checkArray.size(); i++)
        		{
        		    if (!checkArray.get(i).isChecked())  
        		       checkArray.get(i).setChecked(true);
        		
        		}
       

        break;
        
        case R.id.iClearAll:
        	
        	for(int i=0; i<checkArray.size(); i++)
    		{
    		    if (checkArray.get(i).isChecked()) 
    		       checkArray.get(i).setChecked(false);
    		
    		}
        	
        	break;
      
        case R.id.ichangeLanguage:
        	
        	
        	if(engPol)
    		{
        		changeLanguagetxt.setText("ENG->POL");
    		 SharedPreferences.Editor editor = prefs.edit();    
	       	 editor.putBoolean("engPol", true);       
	       	 editor.commit();
    		}
    	else 
    		{
    		changeLanguagetxt.setText("POL->ENG");
    		 SharedPreferences.Editor editor = prefs.edit();    
	       	 editor.putBoolean("engPol", false);       
	       	 editor.commit();
    		}
    	engPol=!engPol;


            break;
        case R.id.icheckUpdate:
        	 Update.update(this, false);
        	 break;

        }

}
    
    @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		saveArray();
		
	}

}
