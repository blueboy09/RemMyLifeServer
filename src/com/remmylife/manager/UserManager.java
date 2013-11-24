package com.remmylife.manager;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.remmylife.diary.*;
import com.remmylife.dbacess.*;

public class UserManager extends Manager {

	User user = new User();
	public UserManager() {
		super();
	}

	public UserManager(String driver, String url, String user, String password) {
		super(driver, url, user, password);
	}
	

	
	public boolean signin(String userName,String password){
		return  check(userName,password);
	}
	
	public boolean signup(User user){
		boolean validity = check(user.getUserName());
		if(validity){
			System.out.println("The user name has been registered");
			return false;
		}else{
			save(user);
			return true;
		}
	}
	
	public boolean save(User user){
		try {
			dataManager.connectToDatabase();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		int userid = user.getUserID();
		
		String userName =user.getUserName();
		String password = user.getPassword(); 
		String nickName = user.getNickName();
		Sex sex= user.getSex();
		String sexq = sex.name();
		Date birthday = user.getBirthday();
		byte[] headportrait =user.getHeadportrait();
		
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
        String dateq = sDateFormat.format(birthday);

        if(userid==0){    			
    			
			try{
				java.sql.Connection con = DriverManager.getConnection(super.url,super.user,super.password);
				java.sql.PreparedStatement pS = con.prepareStatement("insert into `userlist`(userName,password,nickName,sex,birthday,headPortrait) values ('"
						 +userName +"', '"+password+"', '"+nickName+"', '"+sexq+ "', '"+dateq+"', ?);");
				InputStream iS= new ByteArrayInputStream(headportrait);
				pS.setBinaryStream(1, iS,(int)(headportrait.length));
				pS.executeUpdate();
				return true;
    				
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
		}else{
			try{
				java.sql.Connection con = DriverManager.getConnection(super.url,super.user,super.password);
				String update;
				if(headportrait!=null){
					update ="update `userlist` SET `userName`= '"+ userName+"', `password`= '"+ password+"', `nickName`= '"
							+nickName+"', `sex`='"+ sexq+"',`birthday` ='"+dateq+"', `headPortrait` = ? where `userid`= "+userid;                    
					java.sql.PreparedStatement pS = con.prepareStatement(update);
					//java.sql.PreparedStatement pS = con.prepareStatement("insert into `userlist`(userid,uerName,password,nickName,sex,birthday,headPortrait) values ('"+userid+"', '"+ userName +"', '"+password+"', '"+nickName+"', '"+sexq+ "', '"+dateq+"', ?);");
					InputStream iS= new ByteArrayInputStream(headportrait);
					pS.setBinaryStream(1, iS,(int)(headportrait.length));
					pS.executeUpdate();					
				}else{
					update = "update `userlist` SET `userName`= '"+ userName+"', `password`= '"+ password+"', `nickName`= '"
							+nickName+"', `sex`='"+ sexq+"',`birthday` ='"+dateq+"';";
					java.sql.PreparedStatement pS = con.prepareStatement(update);
					pS.executeUpdate();
				}
			
				return true;
    				
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
		}

	}
	
	
	public boolean delete(User user){
		try {
			dataManager.connectToDatabase();
			int userid= user.getUserID();
			String del ="delete from userlist where userid ="+userid;
			dataManager.setUpdate(del);
			dataManager.disconnectFromDatabase();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}
	
	public User getUser(User user){
		String getlist = "Select * from UserList where `userid` = " + user.getUserID();
		ArrayList<User> UserList = execSqlQuery(getlist);
		return UserList.get(0);
		
		
	}
	
	public boolean check(String username, String password){
		String check = "Select * from USERLIST where `userName` = '" +username+"' and `password` = '"+ password+"';";
		ArrayList<User> UserList = execSqlQuery(check);
		if(UserList.get(0).getUserName()!= null){
			return true;
		}else{
			return false;
		}
		
	}
	
	public boolean check(String username){

		String check = "Select * from USERLIST where `userName` = '" +username+"';";
		try {
			dataManager.connectToDatabase();
			dataManager.setQuery(check);
			if(dataManager.getRowCount()>0){
				dataManager.disconnectFromDatabase();
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public ArrayList<User> execSqlQuery(String query){
		try {
			dataManager.connectToDatabase();
			dataManager.setQuery(query);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<User> UserList = constructList(dataManager);
		dataManager.disconnectFromDatabase();
		return UserList;
	}


	public ArrayList<User> constructList(DataManager dataManager){
		ArrayList<User> UserList= new ArrayList<User>();
		try {
			dataManager.connectToDatabase();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int numberOfRow= dataManager.getRowCount();
		
		String sex;
		String birthday;
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");  
		ParsePosition pos;  
		byte [] headportrait =null;
		
		for(int i =0; i<numberOfRow; i++){
			String ts = dataManager.getValueAt(i,0).toString();
			user.setUserID(Integer.valueOf(ts));

			user.setUserName((String)dataManager.getValueAt(i, 1));			
					
			user.setPassword((String)dataManager.getValueAt(i, 2));
			
			user.setNickName((String)dataManager.getValueAt(i, 3));
			
			sex=(String)dataManager.getValueAt(i, 4);
			user.setSex(Sex.valueOf(sex));

			birthday = (String)dataManager.getValueAt(i, 5);
			pos = new ParsePosition(0);
			user.setBirthday(sDateFormat.parse(birthday,pos));
		}
		UserList.add(new User(user));//用不用新建一个class
		for(int i=0 ; i < UserList.size(); i++){
			user =UserList.get(i);
			try {
				java.sql.Connection con = DriverManager.getConnection(super.url,super.user,super.password);
				java.sql.PreparedStatement pS = con.prepareStatement("SELECT * FROM userlist where userid ="+UserList.get(i).getUserID());
				ResultSet rS = pS.executeQuery();
				if(rS.next()){
					java.sql.Blob blob = rS.getBlob("headPortrait");
					if(blob!=null){
						headportrait= blob.getBytes(1, (int) blob.length());
						UserList.get(i).setHeadportrait(headportrait);
					}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return UserList;//这里只把add进去的类修改了,用不用重新add一遍？

	}
}
