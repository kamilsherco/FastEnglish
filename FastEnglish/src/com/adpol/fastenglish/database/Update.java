package com.adpol.fastenglish.database;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;
import android.widget.Toast;

public class Update {
	
	private static Context mContext;
	private static int version;
	private static boolean isAutomatic;
	
	// isAuto=true - odpalany automatycznie po uruchomieniu aplikacji w MainActivity. Wyrzuca AlertDialog.
	// isAuto=false - odpalany po klikni�ciu buttona w ustawieniach. Nie wyrzuca AlertDialog.
	public static void update(Context context, boolean isAuto){
		isAutomatic = isAuto;
		mContext = context;		
		if(isOnline()){
			//showDialog();
			new LoadVersion().execute();
			//continueUpdate();
		}
		else if(!isAuto){
			Toast.makeText(mContext, "Brak po��czenia z internetem", Toast.LENGTH_LONG).show();
		}
	}

	static void continueUpdate(int ver){
		if(isOnline()){
			version = ver;
			showDialog();
			//new LoadVersion().execute();
		}
	}
	
	static void updateCompleted(){
		Toast.makeText(mContext, "Aktualizacja zako�czona", Toast.LENGTH_LONG).show();
	}
	
	public static void toastNoUpdates(){
		Toast.makeText(mContext, "Brak nowych s��w na serwerze", Toast.LENGTH_LONG).show();
	}
	
	private static void showDialog(){

		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

	    builder.setTitle("Aktualizacja");
	    builder.setMessage("Nowe s�owa s� dost�pne do pobrania z serwera");

	    builder.setPositiveButton("Pobierz", new DialogInterface.OnClickListener() {

	        public void onClick(DialogInterface dialog, int which) {
	        	DatabaseManager.getInstance().updateVersion(version);
	        	new LoadAllWords().execute();
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
