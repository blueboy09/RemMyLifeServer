package com.remmylife.service;

import java.io.*;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.remmylife.manager.ManagerAccess;
import com.remmylife.service.*;

import com.remmylife.diary.*;

public class TestService {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//从今天起，好好做demo
		
		ManagerAccess MA = new ManagerAccess();
		ManagerService MS = new ManagerService();
		
		String user1="Kabul@163.com";
		String password1="123";
		
		String user2="blueboy@yeah.com";
		String password2="123";
		
		String user3="sbyl@163.com";
		String password3="123";
		
		String user4="kawayi@163.com";
		String password4="123";
		
		// test userManager
		
		//1: loginByName 
		
		if(MA.loginByName(user3,password3)==MS.loginByName(user3,password3)){
			System.out.println("The first pass");
		}
		
		

		//2: getUser
		
		User user = new User();
		user.setUserID(1);
		user.setUserName(user2);
		user.setPassword(password2);
		byte[] userByte = Utils.convertToByteArray(user);
		System.out.println(MA.getUser(user).getNickName());
		User userTest = (User)Utils.convertToObject(MS.getUser(userByte));
		System.out.println(userTest.getNickName());		
		System.out.println("Second pass");
		 
		
		//3: saveUser
		String userName ="yfjin@126.com";
		String password = "1234";
		String nickName ="blueboy";
		String dateq = "1990-09-03";

		user.setUserID(0);
		user.setUserName(userName);
		user.setPassword(password);
		user.setNickName(nickName);
		user.setSex(Sex.male);

		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");  
		ParsePosition pos;  
		pos = new ParsePosition(0);
		user.setBirthday(sDateFormat.parse(dateq,pos));
		
    	String filedir="C:\\Users\\yfjin\\Downloads\\SmallImage\\1 (1).png"; 
        File file = new File(filedir);
        FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(file);
	        byte[] head = new byte[(int) file.length()];
		    fileInputStream.read(head);
		    user.setHeadportrait(head);
		    fileInputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		System.out.println(user.getUserID());
		userByte = Utils.convertToByteArray(user);
		//MA.saveUser(user);	
		//System.out.println(MA.getUser(user).getUserName());
		MS.saveUser(userByte);
		userTest = (User)Utils.convertToObject(MS.getUser(userByte));
		System.out.println(userTest.getNickName());	
	
		
		//4:deleteUser
		
		user.setUserID(3);
		user.setUserName(user3);
		userByte = Utils.convertToByteArray(user);
		MS.deleteUser(userByte);

		//* diary 
		
		//1: saveDiary
		Diary diary = new Diary(2,DiaryType.TEXT_DIARY,"HelloWorld",new Date(),new Date(),Weather.CLOUDY );
		TextDiary textDiary1 = new TextDiary(diary,"Welcome to the world, enjoy your happy life!");
		byte[] diaryByte = Utils.convertToByteArray(textDiary1);
		MS.saveDiary(diaryByte);

		//2: deleteDiary
		TextDiary textDiary2 = new TextDiary();
		textDiary2.setId(3);
		diaryByte = Utils.convertToByteArray(textDiary1);		
		MS.deleteDiary(diaryByte);		
		

		//3: shareDiary or UnshareDiary
		

		user.setUserID(2);
		user.setUserName(user2);
		diary.setId(8);
		diaryByte=Utils.convertToByteArray(diary);
		userByte=Utils.convertToByteArray(user);
		MS.shareDiary(diaryByte, userByte);
		diary.setId(12);
		userByte=Utils.convertToByteArray(user);
		MS.unshareDiary(diaryByte, userByte);
		
		//4:getDiaryList
		
		user.setUserID(4);
		user.setUserName(user4);
		userByte=Utils.convertToByteArray(user);
		byte[] diarylistByte = MS.getDiaryList(userByte, true);
		ArrayList<Diary> diarylist = (ArrayList<Diary>) Utils.convertToObject(diarylistByte);
		for (int i= 0; i< diarylist.size();i++){
			System.out.println(diarylist.get(i).getTitle());
		}
		
		//Comment
		//1:Save Comment
		
		diary.setId(5);
		diary.setUserid(4);
		user.setUserID(4);
		user.setUserName(user4);
		userByte= Utils.convertToByteArray(user);
		diaryByte= Utils.convertToByteArray(diary);
		MS.saveComment(diaryByte,userByte,"liyi is stupid");
		
		//2: get commentlist
		diary.setId(1);
		diaryByte= Utils.convertToByteArray(diary);
		byte[] commentlistByte = MS.getComment(diaryByte);
		ArrayList<Comment> commentlist=(ArrayList<Comment>)Utils.convertToObject(commentlistByte);
		
		for(int i=0;i<commentlist.size();i++){
			System.out.println(commentlist.get(i).getContent());
		}
		
		// operator 
		//1: searchByTitle
		System.out.println("--------------------我是分隔符");
		String title = "心";
		user.setUserID(1);
		userByte = Utils.convertToByteArray(user);
		diarylistByte = MS.searchByTitle(title, userByte, true);
		ArrayList<Diary> dl1 = (ArrayList<Diary>)Utils.convertToObject(diarylistByte);
		for (int i=0;i<dl1.size();i++){
			System.out.println("My own diaries:"+dl1.get(i).getTitle());
		}
		diarylistByte = MS.searchByTitle(title, userByte, true);
		ArrayList<Diary> dl2 = (ArrayList<Diary>)Utils.convertToObject(diarylistByte);
		for (int i=0;i<dl2.size();i++){
			System.out.println("Others' diaries:"+dl2.get(i).getTitle());
		}

		System.out.println("--------------------我是分隔符");
		//2: searchByDate
		String date1 = "2013-09-03";
		pos.setIndex(0);
		Date date = sDateFormat.parse(date1,pos);
		user.setUserID(1);
		userByte = Utils.convertToByteArray(user);
		diarylistByte = MS.searchByDate(date, userByte, true);
		ArrayList<Diary> dl3 = (ArrayList<Diary>)Utils.convertToObject(MS.searchByDate(date,userByte,true));
		
		for (int i=0;i<dl3.size();i++){
			System.out.println("My own diaries:"+dl3.get(i).getCreateDate());
		}
		ArrayList<Diary> dl4 = (ArrayList<Diary>)Utils.convertToObject(MS.searchByDate(date,userByte,false));
		for (int i=0;i<dl4.size();i++){
			System.out.println("Others' diaries:"+dl4.get(i).getCreateDate());
		}
		
		System.out.println("--------------------我是分隔符");
		//3: searchByContent
		String content = "的";
		user.setUserID(1);
		userByte = Utils.convertToByteArray(user);
		diarylistByte = Utils.convertToByteArray(user);
		ArrayList<Diary> dl5 = (ArrayList<Diary>)Utils.convertToObject(MS.searchByContent(content,userByte,true));		
		for (int i=0;i<dl5.size();i++){
			System.out.println("My own diaries:"+dl5.get(i).getTitle());
		}
		
		ArrayList<Diary> dl6 = (ArrayList<Diary>)Utils.convertToObject(MS.searchByContent(content,userByte,false));
		for (int i=0;i<dl6.size();i++){
			System.out.println("Others' diaries:"+dl6.get(i).getTitle());
		}
				
		System.out.println("--------------------我是分隔符");
		
		//4: sortbydate
		user.setUserID(1);
		userByte= Utils.convertToByteArray(user);
		ArrayList<Diary> dl7 = (ArrayList<Diary>)Utils.convertToObject(MS.sortByDate(userByte,true));
		for (int i=0;i<dl7.size();i++){
			System.out.println("My own diaries:"+dl7.get(i).getCreateDate());
		}
		ArrayList<Diary> dl8 = (ArrayList<Diary>)Utils.convertToObject(MS.sortByDate(userByte,false));
		for (int i=0;i<dl8.size();i++){
			System.out.println("Others' diaries:"+dl8.get(i).getCreateDate());
		}
	}

}