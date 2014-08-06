package com.adpol.fastenglish.database;

import com.j256.ormlite.field.DatabaseField;

public class Word {
	
	@DatabaseField(generatedId=true)
	private int id_word;
	
	@DatabaseField
	private String plWord;
	
	@DatabaseField
	private String enWord;
	
	@DatabaseField
	private int id_category;
	
	@DatabaseField
	private int correctRepeats;
	
	@DatabaseField
	private int incorrectRepeats;
	
	@DatabaseField
	private boolean isLearned;

	public Word(int id_word, String plWord, String enWord, int id_category,
			int correctRepeats, int incorrectRepeats, boolean isLearned) {
		super();
		this.id_word = id_word;
		this.plWord = plWord;
		this.enWord = enWord;
		this.id_category = id_category;
		this.correctRepeats = correctRepeats;
		this.incorrectRepeats = incorrectRepeats;
		this.isLearned = isLearned;
	}

	public Word() {
		super();
	}

	public int getId_word() {
		return id_word;
	}

	public void setId_word(int id_word) {
		this.id_word = id_word;
	}

	public String getPlWord() {
		return plWord;
	}

	public void setPlWord(String plWord) {
		this.plWord = plWord;
	}

	public String getEnWord() {
		return enWord;
	}

	public void setEnWord(String enWord) {
		this.enWord = enWord;
	}

	public int getId_category() {
		return id_category;
	}

	public void setId_category(int id_category) {
		this.id_category = id_category;
	}

	public int getCorrectRepeats() {
		return correctRepeats;
	}

	public void setCorrectRepeats(int correctRepeats) {
		this.correctRepeats = correctRepeats;
	}

	public int getIncorrectRepeats() {
		return incorrectRepeats;
	}

	public void setIncorrectRepeats(int incorrectRepeats) {
		this.incorrectRepeats = incorrectRepeats;
	}

	public boolean isLearned() {
		return isLearned;
	}

	public void setLearned(boolean isLearned) {
		this.isLearned = isLearned;
	}
	
	public void correctIncrement(){
		this.correctRepeats++;
	}
	
	public void incorrectIncrement(){
		this.incorrectRepeats++;
	}
	
}
