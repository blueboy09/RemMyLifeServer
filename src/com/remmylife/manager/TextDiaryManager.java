package com.remmylife.manager;

import java.sql.SQLException;
import java.util.ArrayList;

import com.remmylife.diary.*;
import com.remmylife.dbacess.*;

public class TextDiaryManager extends DiaryManager {

	public TextDiaryManager() {
		super();
	}

	public TextDiaryManager(String driver, String url, String user,
			String password) {
		super(driver, url, user, password);
	}
	
	
	@Override
	public boolean save(Diary diary){
		super.save(diary);
		try {
			dataManager.connectToDatabase();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		int id = diary.getId();
		String text=((TextDiary)diary).getText();
		if(id==0){
			id = super.getId();
			String stext = "insert into `textlist`(`diaryid`,`text`) values ('"
					+id+"', '"+ text+"');";
			try {
				dataManager.connectToDatabase();
				dataManager.setUpdate(stext);
				dataManager.disconnectFromDatabase();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}else{
			String utext = "update `textlist` SET `text` = '"+ text +"' where diaryid = " +id+";";
			try {
				dataManager.setUpdate(utext);
				dataManager.disconnectFromDatabase();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}

		}
	}
	
	
	public boolean deleteself(Diary diary){
		int id = diary.getId();
		String stext="delete from textlist where diaryid = "+id;
		try {
			dataManager.connectToDatabase();
			dataManager.setUpdate(stext);
			dataManager.disconnectFromDatabase();
			return true;			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public boolean delete(Diary diary){
		int id = diary.getId();
		String stext="delete from textlist where diaryid ="+id;
		try {
			dataManager.connectToDatabase();
			dataManager.setUpdate(stext);
			dataManager.disconnectFromDatabase();
			return super.delete(diary);			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}


	}

	
	@Override
	public Diary getDiary(Diary diary){
		String getlist="SELECT * FROM textlist where diaryid = " +diary.getId();

		try {
			dataManager.connectToDatabase();
			dataManager.setQuery(getlist);
			String text = (String)dataManager.getValueAt(0, 1);
			dataManager.disconnectFromDatabase();
			return new TextDiary(diary,text);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		

	}
	
	public ArrayList<Diary> searchByContent(String content, User self, boolean own){ 
		String query="Select * from diarylist NATURAL LEFT OUTER JOIN textlist where `text` like \"%"+ content +"%\"";
		String listown=query+" and `userid`="+ self.getUserID();
		String listshare = query + " and `shared` = 1 and `userid`<>"+ self.getUserID();
		
		ArrayList<Diary> diaryList = new ArrayList<Diary>();
		try {
			dataManager.connectToDatabase();
			
			if(own==true){
				dataManager.setQuery(listown);
			
			}else{
				dataManager.setQuery(listshare);		
			}
			
			int numberOfRow= dataManager.getRowCount();
			for(int i = 0; i<numberOfRow; i++){
				TextDiary diary = new TextDiary();
				diary.setId(Integer.valueOf(dataManager.getValueAt(i, 0).toString()));
				diaryList.add(diary);
			}
			dataManager.disconnectFromDatabase();
		} catch (Exception e) {
			e.printStackTrace();
		}
			return diaryList;
	}
	
	
	
	
	
	@Override
	public ArrayList<Diary> constructList(DataManager dataManager){
		ArrayList<Diary> diaryListOrigin = super.constructList(dataManager);
		ArrayList<Diary> diaryList = new ArrayList<Diary>();
		for(int i=0 ; i < diaryListOrigin.size(); i++){
			Diary diary =diaryListOrigin.get(i);
			try {
				dataManager.setQuery("SELECT * FROM textlist where diaryid="+diary.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			String text = (String)dataManager.getValueAt(0, 1);
			diaryList.add(new TextDiary(diary,text));
		}
		return diaryList;
	}
	
	

	
}
