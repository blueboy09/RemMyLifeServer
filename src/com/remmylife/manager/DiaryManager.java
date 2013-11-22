package com.remmylife.manager;
import java.sql.SQLException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.remmylife.dbacess.*;
import com.remmylife.diary.*;

public class DiaryManager extends Manager{
	
	
	Diary diary = new Diary();
	public DiaryManager() {
		super();
	}

	public DiaryManager(String driver, String url, String user, String password) {
		super(driver, url, user, password);
	}

	
	public ArrayList<Diary> getList(User user, boolean own){
		if(own==true){
			String listown="select * from diarylist where `userid`="+ user.getUserID();
			ArrayList<Diary> DiaryList = execSqlQuery(listown);
			return DiaryList;
		}else{
			String listshare="select * from diarylist where `shared` = 1 and `userid`<>"+ user.getUserID();
			ArrayList<Diary> DiaryList = execSqlQuery(listshare);
			return DiaryList;
		}
	}
	
	public ArrayList<Diary> search(String query, User user, boolean own){
		String listown=query+" and `userid`= "+ user.getUserID();
		String listshare = query + " and `shared` = 1 and `userid`<>"+ user.getUserID();
		if(own==true){
			return execSqlQuery(listown);
		}else{
			return execSqlQuery(listshare);
		}
	}
	
	public ArrayList<Diary> sort(String query, User user, boolean own){
		String listown="SELECT * FROM diarylist  where `userid`= "+ user.getUserID()+ query;
		String listshare = query + " where `shared` = 1 and `userid`<>"+ user.getUserID()+" ORDER BY date DESC ";		
		if(own==true){
			return execSqlQuery(listown);
		}else{
			return execSqlQuery(listshare);		
		}
	}
		
	public Diary getDiary(Diary diary){
		String getlist="SELECT * FROM DiaryList where diaryid = " +diary.getId();
		ArrayList<Diary> diaryList = execSqlQuery(getlist);
		return diaryList.get(0);
	}
	
	public void shareDiary(Diary diary,User self){
		if(diary.getUserid()==self.getUserID()){
			String shared = "update diarylist where `diaryid`="+diary.getId()+" SET shared = 1";
			
			try {
				dataManager.connectToDatabase();
				dataManager.setUpdate(shared);
				dataManager.disconnectFromDatabase();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
	public void unshareDiary(Diary diary, User self){
		if(diary.getUserid()==self.getUserID()){
			String unshared = "update diarylist where `diaryid`="+diary.getId()+" SET shared = 0";
			
			try {
				dataManager.connectToDatabase();
				dataManager.setUpdate(unshared);
				dataManager.disconnectFromDatabase();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
	
	public int getId(){
		String cValue = "SELECT max(diaryid) FROM diarylist";
		try {
			dataManager.connectToDatabase();
		
			dataManager.setQuery(cValue);
			int count = 0;
			if(dataManager.getValueAt(0,0)!=null ){
				String ts = dataManager.getValueAt(0,0).toString();
				count=Integer.valueOf(ts);
			//System.out.println(count);
			}
			dataManager.disconnectFromDatabase();
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}
	
	public boolean save(Diary diary) {
		try {
			dataManager.connectToDatabase();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		int id = diary.getId();
		
		int userid = diary.getUserid();
		
		String type = diary.getType().name();
		
		String title = diary.getTitle();
		
		Date createDate = diary.getCreateDate();
		Date lastSaveDate = diary.getLastSaveDate();
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
        String dateq1 = sDateFormat.format(createDate);
        String dateq2 = sDateFormat.format(lastSaveDate);
        
        String weather = diary.getWeather().name();
        int shared = 0;
        if(diary.isShared()){
        	 shared = 1;
        }
        
        
        if(id==0){
        	String savediary = "insert into `diarylist`(`userid`,`type`,`title`,`createdate`,`lastsavedate`,`weather`,`shared`) values ('"
				+userid+"', '"+ type+"', '" + title +"', '"+dateq1+"', '"+dateq2+"', '"+weather+"', '"+shared+"');" ;
        	try {
				dataManager.setUpdate(savediary);
				dataManager.disconnectFromDatabase();
        	} catch (Exception e) {
    			e.printStackTrace();
    			return false;
    		}
		}else{
			//update `diarylist` SET `userid` = userid, `type`=type, `title`= title, `createdate` = dateq1, `lastsavedate`= dateq2, `weather`=weather, `shared`=shared where `diaryid`=id;
			String updatediary = "update `diarylist` SET `userid` ='"+ userid +"', `type`='"+type+"', `title`= '"+title+"', `createdate` ='"
			+dateq1 +"',`lastsavedate`='"+dateq2+"',`weather`='"+weather+"', `shared`="+shared+" where `diaryid`= "+id+" ;";
        	try {
				dataManager.setUpdate(updatediary);
				dataManager.disconnectFromDatabase();
        	} catch (Exception e) {
    			e.printStackTrace();
    			return false;
    		}
		}
		return true;
	}

	
	public boolean delete(Diary diary) {
		try {
			dataManager.connectToDatabase();
			int id=diary.getId();
			String del ="delete from diarylist where diaryid ="+id;
			dataManager.setUpdate(del);
			dataManager.disconnectFromDatabase();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}


	}

	public boolean deleteList(ArrayList<Diary> ids) {
		for(int i = 0 ; i < ids.size() ; i ++){
			delete(ids.get(i));
		}
		return false;
	}
	
	

	@Override
	public ArrayList<Diary> constructList(DataManager dataManager) {
		ArrayList<Diary> DiaryList= new ArrayList<Diary>();
		try {
			dataManager.connectToDatabase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		int numberOfRow= dataManager.getRowCount();
		
		String typeq;		
		String createDateq;
		String lastSaveDateq;
		String weatherq;
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		ParsePosition pos;  
		
		for(int i =0; i<numberOfRow; i++){
			String ts = dataManager.getValueAt(i,0).toString();
			diary.setId(Integer.valueOf(ts));

			String ds = dataManager.getValueAt(i,1).toString();
			diary.setUserid(Integer.valueOf(ds));
			
			typeq=(String)dataManager.getValueAt(i, 2);			
			
			diary.setType(DiaryType.valueOf(typeq));		
			
			diary.setTitle((String)dataManager.getValueAt(i, 3));
			
			createDateq=(String)dataManager.getValueAt(i, 4);
			pos = new ParsePosition(0);
			diary.setCreateDate(sDateFormat.parse(createDateq, pos)) ;
			
			lastSaveDateq=(String)dataManager.getValueAt(i, 5);
			pos = new ParsePosition(0);
			diary.setLastSaveDate(sDateFormat.parse(lastSaveDateq, pos)) ;
		
			weatherq=(String)dataManager.getValueAt(i, 6);
			diary.setWeather(Weather.valueOf(weatherq));
		
			String shared=(String)dataManager.getValueAt(i, 7).toString();
			diary.setShared(Boolean.valueOf(shared));
			
			DiaryList.add(new Diary(diary));//用不用新建一个class
			}
		return DiaryList;
	}

	@Override
	public ArrayList<Diary> execSqlQuery(String query) {
		try {
			dataManager.connectToDatabase();
			dataManager.setQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ArrayList<Diary> DiaryList = constructList(dataManager);
		dataManager.disconnectFromDatabase();
		return DiaryList;
	}

}
