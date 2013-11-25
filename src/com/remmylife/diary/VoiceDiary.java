package com.remmylife.diary;

import java.util.Date;




public class VoiceDiary extends Diary
{
	private byte[] voice = null;//ÒôÆµ×Ö½ÚÊý×é
	private String note = null;
	
	public VoiceDiary()
	{
		super();
		this.setType(DiaryType.VOICE_DIARY);
	}
	
	public VoiceDiary(int userid,String title, Date date)
	{
		super(userid,title, date);
		this.setType(DiaryType.VOICE_DIARY);
	}

	public VoiceDiary(int id, DiaryType type, String title, Date createDate,
			Date lastSaveDate, Weather weather, byte[] voice, String note) {
		super(id, type, title, createDate, lastSaveDate, weather);
		this.voice = voice;
		this.note = note;
	}
	
	public VoiceDiary(Diary diary,byte[] voice, String note){
		super(diary);
		this.voice=voice;
		this.note=note;
	}

	
	public VoiceDiary(VoiceDiary diary){
		super(diary);
		this.voice=diary.getVoice();
		this.note=diary.getNote();
	}


	public byte[] getVoice() {
		return voice;
	}

	public void setVoice(byte[] voice) {
		this.voice = voice;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	
	

}