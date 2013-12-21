package com.remmylife.weibo;

import com.remmylife.diary.Diary;
import com.remmylife.diary.DiaryType;

public class WeiboAccess {
	
	WeiboApp weiboApp = new WeiboApp();
	
	
	// Weibo synchronization

	public boolean deleteFromWeibo(Diary diary, String accessToken){
		return weiboApp.destroy(diary, accessToken);
	}
	
	
	public boolean shareToWeibo(Diary diary, String accessToken){
		DiaryType type = diary.getType();
		switch(type){
		case TEXT_DIARY: return weiboApp.updateStatus(diary, accessToken);
		case IMAGE_DIARY: return weiboApp.upload(diary, accessToken);
		case VOICE_DIARY:  System.out.println("The voice funciton have not been realized, sorry"); return false;
		}
		return false;
	}



}
