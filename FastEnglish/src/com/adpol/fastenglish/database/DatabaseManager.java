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
    
    public List<Category> getAllCategory() {
        List<Category> categoryList = null;
        try {
        	categoryList = getHelper().getCategoryDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryList;
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
    
    public void updateWord(Word word){
    	try {
			getHelper().getWordDao().update(word);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
    }
    
    public boolean isNothingLearned(){
    	int countOf=-1;
    	try {
    		countOf = (int) getHelper().getWordDao().queryBuilder().where().eq("isLearned", true).countOf();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//Log.d("Liczba", Integer.toString(countOf));
    	if(countOf==0) return true;
    	return false;
    }
    
    public boolean isEverythingLearned(){
    	int countOf=-1;
    	try {
    		countOf = (int) getHelper().getWordDao().queryBuilder().where().eq("isLearned", false).countOf();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//Log.d("Liczba", Integer.toString(countOf));
    	if(countOf==0) return true;
    	return false;
    }
    
    public List<Word> getNewRandomWords(int number){
    	List<Word> wordsList = null;
    	int count=-1;
    	try {
    		count = (int) getHelper().getWordDao().queryBuilder().where().eq("isLearned", false).countOf();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if(number >= count){
    		try {
				wordsList = getHelper().getWordDao().queryBuilder().where().eq("isLearned", false).query();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		return wordsList;
    	}
    	else{
    		try {
    			wordsList = getHelper().getWordDao().queryBuilder().orderByRaw("RANDOM()").limit(number).where().eq("isLearned", false).query();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		return wordsList;
    	}
    }
    
    public List<Word> getLearnedWords(){
    	List<Word> wordsList = null;
    	try {
			wordsList = getHelper().getWordDao().queryBuilder().where().eq("isLearned", true).query();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return wordsList;
    }
}