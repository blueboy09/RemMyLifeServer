package com.remmylife.diary;

import java.util.Date;




public class TextDiary extends Diary
{
	String text = null;
	
	public TextDiary()
	{
		super();
		this.setType(DiaryType.TEXT_DIARY);
	}
	
	public TextDiary(int userid,String title, Date date)
	{
		super(userid,title, date);
		this.setType(DiaryType.TEXT_DIARY);
	}


	public TextDiary(int id, DiaryType type, String title, Date createDate,
			Date lastSaveDate, Weather weather, String text) {
		super(id, type, title, createDate, lastSaveDate, weather);
		this.text = text;
	}
	
	public TextDiary(Diary diary,String text){
		super(diary);
		this.text= text;
	}
	
	public TextDiary(TextDiary diary){
		super(diary);
		this.text= diary.getText();
	}



	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	
	
}