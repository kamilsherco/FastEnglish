package com.adpol.fastenglish.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class LoadVersion extends AsyncTask<String, String, String> {
	
	/**
     * Before starting background thread Show Progress Dialog
     * */
	
	 // Progress Dialog
   // private ProgressDialog pDialog;
    private Context currentContext = null;
    
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
 
    private ArrayList<HashMap<String, String>> versionArray = new ArrayList<HashMap<String, String>>();
 
    // url to get all products list
    private static String url_check_version = "http://smacznesery.com/android_content/check_version.beta";
 
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_VERSION = "version";
    private static final String TAG_ID_VERSION = "version";
    private static final String TAG_lastWord = "lastWord";
    
 
    // products JSONArray
    JSONArray versionJSON = null;
    public void LoadingDataFromServer(Context context) {
        currentContext = context;

    }
    
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
      /*  pDialog = new ProgressDialog(currentContext);
        pDialog.setMessage("Loading products. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();*/
    }

    /**
     * getting All products from url
     * */
    protected String doInBackground(String... args) {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(url_check_version, "GET", params);

        // Check your log cat for JSON reponse
       // Log.d("All Words: ", json.toString());

        try {
            // Checking for SUCCESS TAG
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                // products found
                // Getting Array of Products
                versionJSON = json.getJSONArray(TAG_VERSION);

                // looping through All Products
                for (int i = 0; i < versionJSON .length(); i++) {
                    JSONObject c = versionJSON .getJSONObject(i);

                    // Storing each json item in variable
                    String version = c.getString(TAG_ID_VERSION);
                    String lastWord  = c.getString(TAG_lastWord);
                    
                 
                    

                    // creating new HashMap
                    HashMap<String, String> map = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    map.put(TAG_ID_VERSION, version);
                    map.put(TAG_lastWord, lastWord );
                    

                    // adding HashList to ArrayList
                 versionArray.add(map);
                }
            } else {
               
            	System.out.println("ERRRR");
            }
            Log.d("Version : ", versionArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
       
        return null;
    }

    /**
     * After completing background task Dismiss the progress dialog
     * **/
    protected void onPostExecute(String file_url) {
        // dismiss the dialog after getting all products
       // pDialog.dismiss();
        // updating UI from Background Thread
       /* runOnUiThread(new Runnable() {
            public void run() {
                /**
                 * Updating parsed JSON data into ListView
                 * */
              /*  ListAdapter adapter = new SimpleAdapter(
                        AllProductsActivity.this, productsList,
                        R.layout.list_item, new String[] { TAG_PID,
                                TAG_NAME},
                        new int[] { R.id.pid, R.id.name });
                // updating listview
                setListAdapter(adapter);
            }
        });*/

    }


}
