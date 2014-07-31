package com.adpol.fastenglish.database;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.stmt.UpdateBuilder;

import android.content.Context;

public class DatabaseManager {
	
	private final int VERSION_ID = 1;

    static private DatabaseManager instance;

    static public void init(Context ctx) {
        if (null==instance) {
            instance = new DatabaseManager(ctx);
        }
    }

    static public DatabaseManager getInstance() {
        return instance;
    }

    private DatabaseHelper helper;
    private DatabaseManager(Context ctx) {
        helper = new DatabaseHelper(ctx);
    }

    public DatabaseHelper getHelper() {
        return helper;
    }
    
    public List<Word> getAllWords() {
        List<Word> wordsList = null;
        try {
        	wordsList = getHelper().getWordDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wordsList;
    }
    
    public List<Version> getAllVersions() {
        List<Version> versionsList = null;
        try {
        	versionsList = getHelper().getVersionDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return versionsList;
    }
    
    public Version getVersionById(int id){
    	Version ver = null;
    	try {
        	ver = getHelper().getVersionDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	return ver;
    }
    
    public void updateVersion(int version){
    	UpdateBuilder<Version, Integer> updateBuilder = getHelper().getVersionDao().updateBuilder();
    	// set the criteria like you would a QueryBuilder
    	try {
			updateBuilder.where().eq("id_version", VERSION_ID);
			updateBuilder.updateColumnValue("version", version);
	    	updateBuilder.update();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	// update the value of your field(s)
    	
    }
}