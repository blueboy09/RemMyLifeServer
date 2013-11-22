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
	
	public User getUser(User user){
		return MA.getUser(user);
	}
	
	public boolean saveUser(User user){
		return MA.saveUser(user);
	}
	
	public boolean deleteUser(User user){
		return MA.deleteUser(user);
	}
	
	// diary 的操作
	
	public boolean deleteDiary(Diary diary){
		return MA.deleteDiary(diary);
				
	}
	
	public boolean saveDiary(Diary diary){
		return MA.saveDiary(diary);
	}
	
	public void shareDiary(Diary diary, User self){
		MA.shareDiary(diary, self);
	}
	
	public void unshareDiary(Diary diary, User self){
		MA.unshareDiary(diary, self);
	}
	
	public ArrayList<Diary> getDiaryList(User self, boolean own){
		return MA.getDiaryList(self, own);
	}
	
	public Diary getDiary(Diary diary){
		return MA.getDiary(diary);
	}
	
	
	// Comment
	
	public void saveComment(Diary diary,User user,String note){
		MA.saveComment(diary, user, note);
	}
	public ArrayList<Comment> getComment(Diary diary){
		return MA.getComment(diary);
	}
	
	// search
	
	public ArrayList<Diary> searchByTitle(String title, User self, boolean own){
		return MA.searchByTitle(title, self, own);
	}

	public ArrayList<Diary> searchByDate(Date date, User self, boolean own){ 
		return MA.searchByDate(date, self, own);
	}
	
	public ArrayList<Diary> searchByType(DiaryType type, User self, boolean own){
		return MA.searchByType(type, self, own);
	}
	
	public ArrayList<Diary> searchByContent(String content, User self, boolean own){
		return MA.searchByContent(content, self, own);
	}
	
	public ArrayList<Diary> sortByDate(User self, boolean own){
		return MA.sortByDate(self, own);
	}
	

}



