package com.remmylife.manager;
import java.io.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.remmylife.dbacess.*;
import com.remmylife.diary.*;

public class ImageDiaryManager extends DiaryManager {

	public ImageDiaryManager() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ImageDiaryManager(String driver, String url, String user,
			String password) {
		super(driver, url, user, password);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean save(Diary diary) {
		super.save(diary);
		int id = diary.getId();
		try {
			dataManager.connectToDatabase();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		String note=((ImageDiary)diary).getNote();
		byte[] images=((ImageDiary)diary).getImages();

		if(id==0){
			id=super.getId();
				
			try{
				java.sql.Connection con = DriverManager.getConnection(this.url,this.user,this.password);
				java.sql.PreparedStatement pS = con.prepareStatement("insert into `imagelist`(diaryid,note,imagedata) values ('"
						+ id +"', '"+ note +"', ?);");
									
				InputStream iS= new ByteArrayInputStream(images);
				pS.setBinaryStream(1, iS,(int)(images.length));
				pS.executeUpdate();
				
				return true;
			}catch(SQLException e) {
				e.printStackTrace();
				return false;
			}
		}else{
			try{
				java.sql.Connection con = DriverManager.getConnection(this.url,this.user,this.password);
				String update;
				if(images!=null){

					update="update `imagelist` SET `note`='"+note+"', `imagedata` = ? where `diaryid` ='"+id+"'";
					java.sql.PreparedStatement pS = con.prepareStatement(update);
					//java.sql.PreparedStatement pS = con.prepareStatement("insert into `imagelist`(id,note,imagedata) values ('"+ id +"', '"+ note +"', ?);");
					InputStream iS= new ByteArrayInputStream(images);
					pS.setBinaryStream(1, iS,(int)(images.length));
					pS.executeUpdate();
					return true;
				}else{
					update="update `imagelist` SET `note`='"+note+"' where `diaryid` = '"+id+"'";
					java.sql.PreparedStatement pS = con.prepareStatement(update);
					pS.executeUpdate();
					return true;
				}

			}catch(SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
	}
	
	
	public boolean deleteself(Diary diary){
		int id = diary.getId();
		String del="delete from imagelist where diaryid ="+id;
		try {
			dataManager.connectToDatabase();
			dataManager.setUpdate(del);
			dataManager.disconnectFromDatabase();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean delete(Diary diary){
		int id = diary.getId();
		String del="delete from imagelist where diaryid ="+id;
		try {
			dataManager.connectToDatabase();
			dataManager.setUpdate(del);
			dataManager.disconnectFromDatabase();
			return super.delete(diary);			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}


	}

	
	@Override
	public Diary getDiary(Diary diary){
		String getlist="SELECT * FROM imagelist where diaryid = " +diary.getId();
		try{
			dataManager.connectToDatabase();
			dataManager.setQuery(getlist);
			String note = (String)dataManager.getValueAt(0, 1);
					
			java.sql.Connection con = DriverManager.getConnection(this.url,this.user,this.password);
			java.sql.PreparedStatement pS = con.prepareStatement("SELECT * FROM imagelist where diaryid = "+diary.getId());
			ResultSet rS = pS.executeQuery();
			
			if(rS.next()){
				java.sql.Blob blob = rS.getBlob("imagedata");
				byte[] image=null;
				if(blob!=null){
					image= blob.getBytes(1, (int) blob.length());
					dataManager.disconnectFromDatabase();
				}
				return new ImageDiary(diary,image,note);
			}else{
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
			

	}
	
	public ArrayList<Diary> searchByContent(String content, User self, boolean own){ 
		String query="Select * from diarylist NATURAL LEFT OUTER JOIN imagelist where `note` like \"%"+ content +"%\"";
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
				ImageDiary diary = new ImageDiary();
				diary.setId(Integer.valueOf(dataManager.getValueAt(i, 0).toString()));
				diaryList.add(diary);
			}
			dataManager.disconnectFromDatabase();
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
				dataManager.setQuery("SELECT * FROM imagelist where diaryid="+diary.getId());
				String note = (String)dataManager.getValueAt(0, 1);
				
				java.sql.Connection con = DriverManager.getConnection(this.url,this.user,this.password);
				java.sql.PreparedStatement pS = con.prepareStatement("SELECT * FROM imagelist where id ="+diary.getId());
				ResultSet rS = pS.executeQuery();
				if(rS.next()){
					java.sql.Blob blob = rS.getBlob("imagedata");
					if(blob!=null){
						byte[]image= blob.getBytes(1, (int) blob.length());
						diaryList.add(new ImageDiary(diary,image,note));
					}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return diaryList;
	}
	
	

}
