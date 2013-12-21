package com.remmylife.manager;

import java.io.*;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.remmylife.service.*;

import com.remmylife.diary.*;

public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//demo
		
		ManagerAccess MA = new ManagerAccess();
		
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
		
		if(MA.loginByName(user3,password3)){
			System.out.println("The first pass");
		}
		
		

		//2: getUser
		
		User user = new User();
		user.setUserID(1);
		user.setUserName(user2);
		user.setPassword(password2);
		System.out.println(MA.getUser(user).getNickName());
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
		MA.saveUser(user);	
		System.out.println(MA.getUser(user).getUserName());
		

		MA.saveUser(user);	
		
		//4:deleteUser
		
		user.setUserID(3);
		user.setUserName(user3);
		MA.deleteUser(user);

		//* diary 
		
		//1: saveDiary
		Diary diary = new Diary(2,DiaryType.TEXT_DIARY,"HelloWorld",new Date(),new Date(),Weather.CLOUDY );
		TextDiary textDiary1 = new TextDiary(diary,"Welcome to the world, enjoy your happy life!");
				
		MA.saveDiary(textDiary1);

		//2: deleteDiary
		TextDiary textDiary2 = new TextDiary();
		textDiary2.setId(3);
		MA.deleteDiary(textDiary2);		
		

		//3: shareDiary or UnshareDiary
		

		user.setUserID(2);
		user.setUserName(user2);
		diary.setId(8);
		MA.shareDiary(diary, user);
		diary.setId(12);
		MA.unshareDiary(diary, user);
		
		//4:getDiaryList
		
		user.setUserID(4);
		user.setUserName(user4);
		ArrayList<Diary> diarylist = MA.getDiaryList(user, true);
		for (int i= 0; i< diarylist.size();i++){
			System.out.println(diarylist.get(i).getTitle());
		}
		
		//Comment
		//1:Save Comment
		
		diary.setId(5);
		diary.setUserid(4);
		user.setUserID(4);
		user.setUserName(user4);
		MA.saveComment(diary,user,"liyi is stupid");
		
		//2: get commentlist
		diary.setId(1);
		ArrayList<Comment> commentlist=MA.getComment(diary);
		for(int i=0;i<commentlist.size();i++){
			System.out.println(commentlist.get(i).getContent());
		}
		
		// operator 
		//1: searchByTitle
		System.out.println("--------------------���Ƿָ���");
		String title = "��";
		user.setUserID(1);
		ArrayList<Diary> dl1 = MA.searchByTitle(title,user,true);
		for (int i=0;i<dl1.size();i++){
			System.out.println("My own diaries:"+dl1.get(i).getTitle());
		}
		ArrayList<Diary> dl2 = MA.searchByTitle(title,user,false);
		for (int i=0;i<dl2.size();i++){
			System.out.println("Others' diaries:"+dl2.get(i).getTitle());
		}

		System.out.println("--------------------���Ƿָ���");
		//2: searchByDate
		String date1 = "2013-09-03";
		pos.setIndex(0);
		Date date = sDateFormat.parse(date1,pos);
		user.setUserID(1);
		ArrayList<Diary> dl3 = MA.searchByDate(date,user,true);
		for (int i=0;i<dl3.size();i++){
			System.out.println("My own diaries:"+dl3.get(i).getCreateDate());
		}
		ArrayList<Diary> dl4 = MA.searchByDate(date,user,false);
		for (int i=0;i<dl4.size();i++){
			System.out.println("Others' diaries:"+dl4.get(i).getCreateDate());
		}
		
		System.out.println("--------------------���Ƿָ���");
		//3: searchByContent
		String content = "��";
		user.setUserID(1);
		ArrayList<Diary> dl5 = MA.searchByContent(content,user,true);
		for (int i=0;i<dl5.size();i++){
			System.out.println("My own diaries:"+dl5.get(i).getTitle());
		}
		ArrayList<Diary> dl6 = MA.searchByContent(content,user,false);
		for (int i=0;i<dl6.size();i++){
			System.out.println("Others' diaries:"+dl6.get(i).getTitle());
		}
				
		System.out.println("--------------------���Ƿָ���");
		//4: sortbydate
		user.setUserID(1);
		ArrayList<Diary> dl7 = MA.sortByDate(user,true);
		for (int i=0;i<dl7.size();i++){
			System.out.println("My own diaries:"+dl7.get(i).getCreateDate());
		}
		ArrayList<Diary> dl8 = MA.sortByDate(user,false);
		for (int i=0;i<dl8.size();i++){
			System.out.println("Others' diaries:"+dl8.get(i).getCreateDate());
		}
	}

}
