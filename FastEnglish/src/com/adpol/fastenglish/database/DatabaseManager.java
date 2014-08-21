package com.adpol.fastenglish.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.j256.ormlite.stmt.UpdateBuilder;

import android.content.Context;
import android.content.SharedPreferences;

public class DatabaseManager {
	
	private final int VERSION_ID = 1;

    static private DatabaseManager instance;
    static private Context context;

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
    	this.context = ctx;
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
    	List<Word> wordsList = getAllWordsFromCategories();
    	
    	for(int i=0;i<wordsList.size();i++){
    		if(wordsList.get(i).isLearned()) return false;
    	}    	
    	/*try {
    		countOf = (int) getHelper().getWordDao().queryBuilder().where().eq("isLearned", true).countOf();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//Log.d("Liczba", Integer.toString(countOf));
    	if(countOf==0) return true;*/
    	return true;
    }
    
    public boolean isEverythingLearned(){
    	List<Word> wordsList = getAllWordsFromCategories();
    	
    	for(int i=0;i<wordsList.size();i++){
    		if(!wordsList.get(i).isLearned()) return false;
    	}    	
    	/*int countOf=-1;
    	try {
    		countOf = (int) getHelper().getWordDao().queryBuilder().where().eq("isLearned", false).countOf();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//Log.d("Liczba", Integer.toString(countOf));
    	if(countOf==0) return true;*/
    	return true;
    }
    
    public List<Word> getNewRandomWords(int number){
    	List<Word> wordsList = getUnlearnedWordsFromCategories();
    	
    	if(number >= wordsList.size()) return wordsList;
    	
    	Collections.shuffle(wordsList);
    	
    	return wordsList.subList(0, number);
    	/*List<Word> wordsList = null;
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
    	}*/
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
    
    // pobiera nauczone s³owa z danych kategorii
    public List<Word> getLearnedWordsFromCategories(){
    	List<Word> wordsList = new ArrayList<Word>();
    	
    	SharedPreferences settingsPref = context.getSharedPreferences("prefs", 0);
    	int catCount = settingsPref.getInt("SizeChbx", 18);
    	for(int i=0;i<catCount;i++){
    		if(settingsPref.getBoolean(Integer.toString(i), true)){
    			try {
					wordsList.addAll(getHelper().getWordDao().queryBuilder().where().eq("id_category", i).and().eq("isLearned", true).query());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}    	
		return wordsList;    	
    }
    
    public List<Word> getUnlearnedWordsFromCategories(){
    	List<Word> wordsList = new ArrayList<Word>();
    	
    	SharedPreferences settingsPref = context.getSharedPreferences("prefs", 0);
    	int catCount = settingsPref.getInt("SizeChbx", 18);
    	for(int i=0;i<catCount;i++){
    		if(settingsPref.getBoolean(Integer.toString(i), true)){
    			try {
					wordsList.addAll(getHelper().getWordDao().queryBuilder().where().eq("id_category", i).and().eq("isLearned", false).query());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}    	
		return wordsList;    	
    }
    
    // pobiera wszystkie s³owa z danych kategorii
    public List<Word> getAllWordsFromCategories(){
    	List<Word> wordsList = new ArrayList<Word>();
    	
    	SharedPreferences settingsPref = context.getSharedPreferences("prefs", 0);
    	int catCount = settingsPref.getInt("SizeChbx", 18);
    	for(int i=0;i<catCount;i++){
    		if(settingsPref.getBoolean(Integer.toString(i), true)){
    			try {
					wordsList.addAll(getHelper().getWordDao().queryBuilder().where().eq("id_category", i).query());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}    	
		return wordsList;    	
    }
    
    public int countAllWordsFromCategory(int categoryId){
    	int count=-1;

    	try {
			count = (int) getHelper().getWordDao().queryBuilder().where().eq("id_category", categoryId).countOf();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return count;
    }
    
    public int countLearnedWordsFromCategory(int categoryId){
    	int count=-1;

    	try {
			count = (int) getHelper().getWordDao().queryBuilder().where().eq("id_category", categoryId).and().eq("isLearned", true).countOf();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return count;
    }
    
    public int countLearnedWordsFromCategories(){
    	List<Word> wordsList = getLearnedWordsFromCategories();
    	    	
    	return wordsList.size();
    }
}