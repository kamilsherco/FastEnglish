package com.adpol.fastenglish.database;

import com.j256.ormlite.field.DatabaseField;

public class Version {
	
	@DatabaseField(generatedId=true)
	private int id_version;
	
	@DatabaseField
	private int version;
	
	@DatabaseField
	private int lastWord;

	public Version(int id_version, int version, int lastWord) {
		super();
		this.id_version = id_version;
		this.version = version;
		this.lastWord = lastWord;
	}

	public Version() {
		super();
	}

	public int getId_version() {
		return id_version;
	}

	public int getVersion() {
		return version;
	}

	public int getLastWord() {
		return lastWord;
	}
	
	

}
