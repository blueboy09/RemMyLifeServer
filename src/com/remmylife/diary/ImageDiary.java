package com.remmylife.diary;

import java.util.Date;






public class ImageDiary extends Diary
{
	byte[] images = null;//Í¼Æ¬µÄ×Ö½ÚÁ÷
	String note = null;//ÕÕÆ¬µÄ×¢ÊÍ
	
	
	public ImageDiary() {
		super();
		this.setType(DiaryType.IMAGE_DIARY);
	}

	public ImageDiary(byte[] images, String note) {
		super();
		this.images = images;
		this.note = note;
	}
	
	public ImageDiary(int id, DiaryType type, String title, Date createDate,
			Date lastSaveDate, Weather weather, byte[] images, String note) {
		super(id, type, title, createDate, lastSaveDate, weather);
		this.images = images;
		this.note = note;
	}
	
	public ImageDiary(Diary diary,byte[]images,String note){
		super(diary);
		this.images=images;
		this.note = note;
	}
	
	
	
	public ImageDiary(ImageDiary diary){
		super(diary);
		this.images=diary.getImages();
		this.note = diary.getNote();
	}



	public byte[] getImages() {
		return images;
	}

	public void setImages(byte[] images) {
		this.images = images;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	
	

}