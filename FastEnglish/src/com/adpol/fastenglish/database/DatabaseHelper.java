package com.adpol.fastenglish.database;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	
	// name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "FastEnglish.sqlite";

    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 4;
    
    // the DAO object we use to access the SimpleData table
    private Dao<Word, Integer> wordDao = null;
    private Dao<Version, Integer> versionDao = null;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
		// TODO Auto-generated method stub
		 try {
	            TableUtils.createTable(connectionSource, Word.class); 
	            TableUtils.createTable(connectionSource, Version.class);
	            createNewVersion(1,1,1);
	        } catch (SQLException e) {
	            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
	            throw new RuntimeException(e);
	        } catch (java.sql.SQLException e) {
	            e.printStackTrace();
	        }
		
	}

	@Override
    public void onUpgrade(SQLiteDatabase db,ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
        	TableUtils.dropTable(connectionSource, Word.class, true);        	
        }
        catch (java.sql.SQLException e) {
        	e.printStackTrace();
        }
        catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "exception during onUpgrade", e);
            throw new RuntimeException(e);
        }
        onCreate(db);
    }
	
	public Dao<Word, Integer> getWordDao() {
        if (null == wordDao) {
            try {
            	wordDao = getDao(Word.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return wordDao;
    }
	
	public Dao<Version, Integer> getVersionDao() {
        if (null == versionDao) {
            try {
            	versionDao = getDao(Version.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return versionDao;
    }
	
	public void addWordsFromServer(ArrayList<HashMap<String, String>> wordsList){
		int numberOfWords = DatabaseManager.getInstance().getAllWords().size();
		for(int i=numberOfWords;i<wordsList.size();i++){
			createNewWord(Integer.parseInt(wordsList.get(i).get("id_word")), wordsList.get(i).get("plWord"), wordsList.get(i).get("enWord"), Integer.parseInt(wordsList.get(i).get("id_category")), 
					Integer.parseInt(wordsList.get(i).get("correctRepeats")), Integer.parseInt(wordsList.get(i).get("incorrectRepeats")), Boolean.parseBoolean(wordsList.get(i).get("isLearned")));
		}
	}
	
	private void createNewWord(int id_word, String plWord, String enWord, int id_category,
			int correctRepeats, int incorrectRepeats, boolean isLearned) {		
		Word word = new Word(id_word, plWord, enWord, id_category, correctRepeats, incorrectRepeats, isLearned);
        try {
        	getWordDao().create(word);
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
    }
	
	private void createNewVersion(int id_version, int ver, int lastWord) {		
		Version version = new Version(id_version, ver, lastWord);
        try {
        	getVersionDao().create(version);
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
    }

}
