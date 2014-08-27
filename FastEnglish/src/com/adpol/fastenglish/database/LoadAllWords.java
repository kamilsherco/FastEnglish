package com.adpol.fastenglish.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.adpol.fastenglish.database.JSONParser;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;



/**
 * Background Async Task to Load all product by making HTTP Request
 * */
public class LoadAllWords extends AsyncTask<String, String, String> {

    /**
     * Before starting background thread Show Progress Dialog
     * */
	
	/// Progress Dialog
   private ProgressDialog pDialog;
    private Context currentContext;
    
    public LoadAllWords(Context cxt) {
    	currentContext = cxt;
    	pDialog = new ProgressDialog(currentContext);
    }
    
    
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
 
    private ArrayList<HashMap<String, String>> wordsList = new ArrayList<HashMap<String, String>>();
 
    // url to get all products list
    private static String url_all_words = "http://smacznesery.com/android_content/get_all_words.beta";
 
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_WORDS = "words";
    private static final String TAG_ID = "id_word";
    private static final String TAG_plWord = "plWord";
    private static final String TAG_enWord= "enWord";
    private static final String TAG_id_category = "id_category";
    private static final String TAG_correctRepeats = "correctRepeats";
    private static final String TAG_incorrectRepeats = "incorrectRepeats";
    private static final String TAG_isLearned = "isLearned";
 
    // products JSONArray
    JSONArray words = null;
    public void LoadingDataFromServer(Context context) {
        currentContext = context;

    }
    
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
      
        pDialog.setMessage("Aktualizowanie s³ów. Proszê czekaæ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    /**
     * getting All products from url
     * */
    protected String doInBackground(String... args) {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(url_all_words, "GET", params);

        // Check your log cat for JSON reponse
       // Log.d("All Words: ", json.toString());

        try {
            // Checking for SUCCESS TAG
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
               
                words = json.getJSONArray(TAG_WORDS);

                // looping through All Products
                for (int i = 0; i < words.length(); i++) {
                    JSONObject c = words.getJSONObject(i);

                    // Storing each json item in variable
                    String id = c.getString(TAG_ID);
                    String plWord  = c.getString(TAG_plWord);
                    String enWord  = c.getString(TAG_enWord);
                    String id_category   = c.getString(TAG_id_category);
                    String correctRepeats  = c.getString(TAG_correctRepeats);
                    String incorrectRepeats = c.getString(TAG_incorrectRepeats);
                    String isLearned = c.getString(TAG_isLearned);
                 
                    

                    // creating new HashMap
                    HashMap<String, String> map = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    map.put(TAG_ID, id);
                    map.put(TAG_plWord, plWord );
                    map.put(TAG_enWord, enWord );
                    map.put(TAG_id_category, id_category  );
                    map.put(TAG_correctRepeats, correctRepeats );
                    map.put(TAG_incorrectRepeats, incorrectRepeats );
                    map.put(TAG_isLearned, isLearned );

                    // adding HashList to ArrayList
                 wordsList.add(map);
                }
            } else {
               
            	System.out.println("ERRRR");
            }
            Log.d("All Words: ", wordsList.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        DatabaseManager.getInstance().getHelper().addWordsFromServer(wordsList);
        return null;
    }

    /**
     * After completing background task Dismiss the progress dialog
     * **/
    protected void onPostExecute(String file_url) {
    	Update.updateCompleted();
        
      pDialog.dismiss();
        

    }




}