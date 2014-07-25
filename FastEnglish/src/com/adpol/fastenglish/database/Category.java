package com.adpol.fastenglish.database;

import com.j256.ormlite.field.DatabaseField;

public class Category {
	
	@DatabaseField(generatedId=true)
	private int id_category;
	
	@DatabaseField
	private String name;

	public Category(int id_category, String name) {
		super();
		this.id_category = id_category;
		this.name = name;
	}

	public int getId_category() {
		return id_category;
	}
	
	public Category() {
		super();
	}

	public void setId_category(int id_category) {
		this.id_category = id_category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	

}
