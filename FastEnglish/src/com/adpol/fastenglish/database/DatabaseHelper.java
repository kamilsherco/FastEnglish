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
    private static final int DATABASE_VERSION = 2;
    
    // the DAO object we use to access the SimpleData table
    private Dao<Word, Integer> wordDao = null;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
		// TODO Auto-generated method stub
		 try {
	            TableUtils.createTable(connectionSource, Word.class);         
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
	
	public void addWordsFromServer(ArrayList<HashMap<String, String>> wordsList){
		for(HashMap<String,String> map : wordsList){
			createNewWord(Integer.parseInt(map.get("id_word")), map.get("plWord"), map.get("enWord"), Integer.parseInt(map.get("id_category")), 
					Integer.parseInt(map.get("correctRepeats")), Integer.parseInt(map.get("incorrectRepeats")), Boolean.parseBoolean(map.get("isLearned")));
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

}
