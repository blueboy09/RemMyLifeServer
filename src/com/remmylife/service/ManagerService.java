package com.remmylife.service;

import java.util.ArrayList;
import java.util.Date;

import com.remmylife.diary.*;
import com.remmylife.dbacess.*;
import com.remmylife.manager.*;



public class ManagerService {
	
	ManagerAccess MA = new ManagerAccess();

	public ManagerService() {
		super();
	}
	
	// user 的操作
	public boolean loginByName(String userName, String password){
		return MA.loginByName(userName, password);
	}
	
	public byte[] getUser(byte[] user){
		User user1 = (User) Utils.convertToObject(user);
		return Utils.convertToByteArray(MA.getUser(user1));
	}
	
	public boolean saveUser(byte[] user){
		User user1 = (User) Utils.convertToObject(user);
		return MA.saveUser(user1);
	}
	
	public boolean deleteUser(byte[] user){
		User user1 = (User) Utils.convertToObject(user);
		return MA.deleteUser(user1);
	}
	
	// diary 的操作
	
	public boolean deleteDiary(byte[] diary){
		Diary diary1 = (Diary) Utils.convertToObject(diary);		
		return MA.deleteDiary(diary1);
				
	}
	
	public boolean saveDiary(byte[] diary){
		Diary diary1 = (Diary) Utils.convertToObject(diary);
		return MA.saveDiary(diary1);
	}
	
	public void shareDiary(byte[] diary, byte[] self){
		Diary diary1 = (Diary) Utils.convertToObject(diary);
		User self1 = (User) Utils.convertToObject(self);		
		MA.shareDiary(diary1, self1);
	}
	
	public void unshareDiary(byte[] diary, byte[] self){
		Diary diary1 = (Diary) Utils.convertToObject(diary);
		User self1 = (User) Utils.convertToObject(self);
		MA.unshareDiary(diary1, self1);
	}
	
	public byte[] getDiaryList(byte[] self, boolean own){
		User self1 = (User) Utils.convertToObject(self);
		return Utils.convertToByteArray(MA.getDiaryList(self1, own));
	}
	
	public byte[] getDiary(byte[] diary){
		Diary diary1 = (Diary)Utils.convertToObject(diary);
		return Utils.convertToByteArray(MA.getDiary(diary1));
	}
	
	
	// Comment
	
	public void saveComment(byte[] diary,byte[] user,String note){
		Diary diary1 = (Diary)Utils.convertToObject(diary);
		User user1 = (User) Utils.convertToObject(user);
		MA.saveComment(diary1, user1, note);
	}
	public byte[] getComment(byte[] diary){
		Diary diary1 = (Diary)Utils.convertToObject(diary); 
		return Utils.convertToByteArray(MA.getComment(diary1));
	}
	
	// search
	
	public byte[] searchByTitle(String title, byte[] self, boolean own){
		User self1 = (User) Utils.convertToObject(self);
		return Utils.convertToByteArray(MA.searchByTitle(title, self1, own));
	}

	public byte[] searchByDate(Date date, byte[] self, boolean own){ 
		User self1 = (User) Utils.convertToObject(self);
		return Utils.convertToByteArray(MA.searchByDate(date, self1, own));
	}
	
	public byte[] searchByType(String type, byte[] self, boolean own){
		DiaryType type1= DiaryType.valueOf(type);
		User self1 = (User) Utils.convertToObject(self);
		return Utils.convertToByteArray(MA.searchByType(type1, self1, own));
	}
	
	public byte[] searchByContent(String content, byte[] self, boolean own){
		User self1 = (User) Utils.convertToObject(self);
		return Utils.convertToByteArray(MA.searchByContent(content, self1, own));
	}
	
	public byte[] sortByDate(byte[] self, boolean own){
		User self1 = (User) Utils.convertToObject(self);
		return Utils.convertToByteArray(MA.sortByDate(self1, own));
	}
	

}



