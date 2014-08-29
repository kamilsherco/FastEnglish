package com.adpol.fastenglish.database;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.adpol.fastenglish.MainActivity;
import com.adpol.fastenglish.SettingsActivity;

public class Update {
	
	private static Context mContext;
	private static int version;
	private static boolean isAutomatic;
	
	public static void firstUpdate(Context context){
		mContext = context;
		if(isOnline()){
			showFirstUpdateDialog();
			//new LoadVersion().execute();
		}
		else{
			showOfflineDialog();
		}
	}
	
	// isAuto=true - odpalany automatycznie po uruchomieniu aplikacji w MainActivity. Wyrzuca AlertDialog.
	// isAuto=false - odpalany po klikniêciu buttona w ustawieniach. Nie wyrzuca AlertDialog.
	public static void update(Context context, boolean isAuto){
		isAutomatic = isAuto;
		mContext = context;		
		if(isOnline()){
			//showDialog();
			new LoadVersion(mContext).execute();
			//continueUpdate();
		}
		else if(!isAuto){
			Toast.makeText(mContext, "Brak po³¹czenia z internetem", Toast.LENGTH_LONG).show();
		}
	}

	static void continueUpdate(int ver){
		if(isOnline()){
			version = ver;
			showUpdateDialog();
			//new LoadVersion().execute();
		}
	}
	
	static void updateCompleted(){
		SettingsActivity.all=DatabaseManager.getInstance().countAllWordsFromCategories();
		Toast.makeText(mContext, "Aktualizacja zakoñczona", Toast.LENGTH_LONG).show();
	}
	
	public static void toastNoUpdates(){
		Toast.makeText(mContext, "Brak nowych s³ów na serwerze", Toast.LENGTH_LONG).show();
	}
	
	private static void showUpdateDialog(){

		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

	    builder.setTitle("Aktualizacja");
	    builder.setMessage("Nowe s³owa s¹ dostêpne do pobrania z serwera");

	    builder.setPositiveButton("Pobierz", new DialogInterface.OnClickListener() {

	        public void onClick(DialogInterface dialog, int which) {
	        	DatabaseManager.getInstance().updateVersion(version);
	        	new LoadAllWords(mContext).execute();
	            dialog.dismiss();
	        }

	    });

	    builder.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	            // Do nothing
	            dialog.dismiss();
	        }
	    });
	    
	    AlertDialog alert = builder.create();
	    alert.show();
	}
	
	private static void showFirstUpdateDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

	    builder.setTitle("Aktualizacja");
	    builder.setMessage("Baza s³ów jest pusta. Musisz pobraæ dane z serwera, aby korzystaæ z aplikacji.");

	    builder.setPositiveButton("Pobierz", new DialogInterface.OnClickListener() {

	        public void onClick(DialogInterface dialog, int which) {
	        	update(mContext, false);
	        	/*DatabaseManager.getInstance().updateVersion(version);
	        	new LoadAllWords().execute();*/
	            dialog.dismiss();
	            ((MainActivity) mContext).afterFirstUpdate();
	        }

	    });

	    builder.setNegativeButton("WyjdŸ", new DialogInterface.OnClickListener() {
	        @Override
	        public void onClick(DialogInterface dialog, int which) {

	        	((Activity) mContext).finish();
	            dialog.dismiss();
	        }
	    });
	    
	    AlertDialog alert = builder.create();
	    alert.show();
	}
	
	private static void showOfflineDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

	    builder.setTitle("Brak po³¹czenia");
	    builder.setMessage("Nie mo¿na pobraæ bazy z serwera. Po³¹cz siê z internetem i spróbuj ponownie.");

	    builder.setPositiveButton("Ponów próbê", new DialogInterface.OnClickListener() {

	        public void onClick(DialogInterface dialog, int which) {
	        	firstUpdate(mContext);
	            dialog.dismiss();
	        }

	    });

	    builder.setNegativeButton("WyjdŸ", new DialogInterface.OnClickListener() {
	        @Override
	        public void onClick(DialogInterface dialog, int which) {

	        	((Activity) mContext).finish();
	            dialog.dismiss();
	        }
	    });
	    
	    AlertDialog alert = builder.create();
	    alert.show();
	}
	
	private static boolean isOnline() {
	    ConnectivityManager cm =
	        (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}

	public static boolean isAutomatic() {
		return isAutomatic;
	}

	
}
