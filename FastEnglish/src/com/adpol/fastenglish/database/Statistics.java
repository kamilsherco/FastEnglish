package com.adpol.fastenglish.database;

import java.sql.Date;

import com.j256.ormlite.field.DatabaseField;

public class Statistics {
	
	@DatabaseField(generatedId=true)
	private int id_statistics;
	
	@DatabaseField
	private Date date;
	
	@DatabaseField
	private int points;
	
	@DatabaseField
	private int repeats;

	public Statistics(int id_statistics, Date date, int points, int repeats) {
		super();
		this.id_statistics = id_statistics;
		this.date = date;
		this.points = points;
		this.repeats = repeats;
	}

	public Statistics() {
		super();
	}

	public int getId_statistics() {
		return id_statistics;
	}

	public void setId_statistics(int id_statistics) {
		this.id_statistics = id_statistics;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getRepeats() {
		return repeats;
	}

	public void setRepeats(int repeats) {
		this.repeats = repeats;
	}

}
