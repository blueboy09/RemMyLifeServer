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
		String listshare = "SELECT * FROM diarylist  where `shared` = 1 and `userid`<>"+ user.getUserID()+ query;		
		if(own==true){
			return execSqlQuery(listown);
		}else{
			return execSqlQuery(listshare);		
		}
	}
		
	public Diary getDiary(Diary diary){
		String getlist="SELECT * FROM diarylist where diaryid = " +diary.getId();
		ArrayList<Diary> diaryList = execSqlQuery(getlist);
		return diaryList.get(0);
	}
	
	
	

	public boolean shareDiary(Diary diary,User self){
		if(diary.getUserid()==self.getUserID()){
			String shared =  "update diarylist  set SHARED = 1 where `diaryid`= " + diary.getId() ;
			try {
				dataManager.connectToDatabase();
				dataManager.setUpdate(shared);
				dataManager.disconnectFromDatabase();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
				
		}
		return false;
	}
	

	
	public boolean unshareDiary(Diary diary, User self){
		if(diary.getUserid()==self.getUserID()){
			String unshared = "update diarylist  set shared = 0 where `diaryid`= "+diary.getId() ;
			try {
				dataManager.connectToDatabase();
				dataManager.setUpdate(unshared);
				dataManager.disconnectFromDatabase();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}

		}
		return false;
	}
	
	public boolean shareToWeibo(Diary diary){
			String shared =  "update diarylist  set sharedToWeibo = 1 where `diaryid`= "+diary.getId() ;
			
			try {
				dataManager.connectToDatabase();
				dataManager.setUpdate(shared);
				dataManager.disconnectFromDatabase();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
	}
	

	public boolean unshareToWeibo(Diary diary){
		String unshared =  "update diarylist  set sharedToWeibo = 0 where `diaryid`= "+diary.getId() ;
		
		try {
			dataManager.connectToDatabase();
			dataManager.setUpdate(unshared);
			dataManager.disconnectFromDatabase();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public boolean IsshareToWeibo(Diary diary){
		String getmid = "select sharedToWeibo from diarylist where `diaryid` =" + diary.getId();
		try {
			dataManager.connectToDatabase();
			dataManager.setQuery(getmid);			
			String isSharedq =(String)dataManager.getValueAt(0, 0).toString();
			boolean isShared = Boolean.valueOf(isSharedq);
			dataManager.disconnectFromDatabase();
			return isShared;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean setWeiboMid(Diary diary, String mid){
		String setmid = "update diarylist  set Mid = '"+ mid +"' where `diaryid`= "+diary.getId() ;
		try {
			dataManager.connectToDatabase();
			dataManager.setUpdate(setmid);
			dataManager.disconnectFromDatabase();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	//TODO
	public String getWeiboMid(Diary diary){
		String getmid = "select MID from diarylist where `diaryid` =" + diary.getId();
		try {
			dataManager.connectToDatabase();
			dataManager.setQuery(getmid);			
			String mid =(String)dataManager.getValueAt(0, 0).toString();
			dataManager.disconnectFromDatabase();
			return mid;
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
	
	
	public int getId(){
		String cValue = "SELECT max(diaryID) FROM diarylist";
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
			
			String sharedToWeibo=(String)dataManager.getValueAt(i, 8).toString();
			diary.setSharedToWeibo(Boolean.valueOf(sharedToWeibo));
			
			diary.setMid((String)dataManager.getValueAt(i, 9));
			
			DiaryList.add(new Diary(diary));//�ò����½�һ��class
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
