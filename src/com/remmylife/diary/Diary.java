package com.remmylife.diary;

import java.io.Serializable;
import java.util.Date;


public class Diary implements Serializable
{

	private int id;
	private int userid;
	private DiaryType type;
	private String title;
	private Date createDate;
	private Date lastSaveDate;
	private Weather weather;
	private boolean shared;
	
	
	
	public void init(){
		this.id=0;
		this.shared= false;
	}
	public Diary() {
		super();
	}

	

	public Diary(int userid,String title, Date createDate) {
		super();
		init();
		this.userid = userid;
		this.title = title;
		this.createDate = createDate;
	}



	public Diary(int userid,DiaryType type, String title, Date createDate,
			Date lastSaveDate, Weather weather) {
		super();
		init();
		this.userid=userid;
		this.type = type;
		this.title = title;
		this.createDate = createDate;
		this.lastSaveDate = lastSaveDate;
		this.weather = weather;
	}



	public Diary(int id, int userid,DiaryType type, String title, Date createDate,
			Date lastSaveDate, Weather weather, boolean shared) {
		super();
		this.id = id;
		this.userid=userid;
		this.type = type;
		this.title = title;
		this.createDate = createDate;
		this.lastSaveDate = lastSaveDate;
		this.weather = weather;
		this.shared = shared;
	}

	public Diary(Diary diary){
		this.id = diary.getId();
		this.userid=diary.getUserid();
		this.type = diary.getType();
		this.title = diary.getTitle();
		this.createDate = diary.getCreateDate();
		this.lastSaveDate = diary.getLastSaveDate();
		this.weather = diary.getWeather();
		this.shared = diary.isShared();
	}


	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public DiaryType getType() {
		return type;
	}



	public void setType(DiaryType type) {
		this.type = type;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public Date getCreateDate() {
		return createDate;
	}



	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}



	public Date getLastSaveDate() {
		return lastSaveDate;
	}



	public void setLastSaveDate(Date lastSaveDate) {
		this.lastSaveDate = lastSaveDate;
	}



	public Weather getWeather() {
		return weather;
	}



	public void setWeather(Weather weather) {
		this.weather = weather;
	}
	
	public boolean isShared() {
		return shared;
	}
	public void setShared(boolean shared) {
		this.shared = shared;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	public String getTextContent()
	{
		switch(type)
		 {
		 case TEXT_DIARY:
			 return ((TextDiary)(this)).getText();
		 case VOICE_DIARY:
			 return ((VoiceDiary)(this)).getNote();
		 case IMAGE_DIARY:
			 return ((ImageDiary)(this)).getNote();
		 }
		return "";
	}

}