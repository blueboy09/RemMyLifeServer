package com.remmylife.manager;

import java.util.ArrayList;
import java.util.Date;

import com.remmylife.diary.*;

public class MainTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ManagerAccess aa = new ManagerAccess();
		if(aa.loginByName("aa", "aa")){
			System.out.println("dsaf");
		}
		User user = new User();
		user.setUserID(1);
		String userName ="yfjin";
		String password = "1234";
		String nickName ="blueboy";
		String sexq = "male";
		String dateq = "1990-09-03";
		
		System.out.println("insert into `userlist`(userName,password,nickName,sex,birthday,headPortrait) values ('"
		 +"', '"+ userName +"', '"+password+"', '"+nickName+"', '"+sexq+ "', '"+dateq+"', ?);");
		aa.getUser(user);
		user.setUserID(1);
		user.setUserName("bb");
		user.setPassword("1234");
		byte[] head = new byte[]{1,2};
		user.setHeadportrait(head);
		aa.saveUser(user);
		//user.setUserID(3);
		//aa.deleteUser(user);
		String username="aa";
		System.out.println("Select * from USERLIST where `userName` = '" +username+"' and `password` = '"+ password+"';");
		System.out.println("SELECT * FROM userlist where userid ="+1);
		/*
		Diary diary = new Diary(6,DiaryType.TEXT_DIARY,"helloWorld",new Date(),new Date(),Weather.CLOUDY );
		diary.setId(7);
		aa.deleteDiary(diary);
		diary.setId(8);
		diary.setShared(true);
		TextDiary textDiary = new TextDiary(diary,"fsadf");
		aa.saveDiary(textDiary);
		diary.setId(0);
		ImageDiary imageDiary = new ImageDiary(diary,null,"meihaha");
		imageDiary.setNote("miehaha");
		imageDiary.setType(DiaryType.IMAGE_DIARY);
		imageDiary.setImages(new byte[]{3,2});
		aa.saveDiary(imageDiary);
		user.setUserID(2);

		VoiceDiary voiceDiary = new VoiceDiary(diary,null,"meihaha");
		voiceDiary.setNote("miehaha");
		voiceDiary.setType(DiaryType.VOICE_DIARY);
		voiceDiary.setVoice(new byte[]{3,2});
		aa.saveDiary(voiceDiary);
		
		Date date = new Date();
		ArrayList<Diary> diaryList=aa.sortByDate(user, true);
		for(int i=0;i<diaryList.size();i++){
			System.out.println(diaryList.get(i).getId());
		}
		
		Comment comment = new Comment(2,5,"liyi is stupid");
		Diary dd = new Diary();
		dd.setId(5);
		dd.setUserid(2);
		aa.saveComment(dd,user,"liyi is stupid");
		*/
		
		String re ="dfafadsf\"af\'dfs`a fasd";
		System.out.println(re.replace('"','_').replace('\'', '_').replace('`', '_'));
	}

}
