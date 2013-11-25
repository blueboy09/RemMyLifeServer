package com.remmylife.diary;

import java.io.Serializable;
import java.util.Date;




public class User implements Serializable{
	private int userID;
	private String userName;
	private String password;
	private String nickName;
	private Sex sex;
	private Date birthday;
	private byte[] headportrait;


	public void init(){
		this.userID=0;
		this.sex=Sex.male;
		this.birthday = new Date(); 
	}
	public User() {
		super();
		init();
	}

	public User(int userID, String userName, String password, String nickName,
			Sex sex, Date birthday, byte[] headportrait) {
		super();
		this.userID = userID;
		this.userName = userName;
		this.password = password;
		this.nickName = nickName;
		this.sex = sex;
		this.birthday = birthday;
		this.headportrait = headportrait;
	}

	public User(User user){
		super();
		this.userID = user.getUserID();
		this.userName = user.getUserName();
		this.password = user.getPassword();
		this.nickName = user.getNickName();
		this.sex = user.getSex();
		this.birthday = user.getBirthday();
		this.headportrait = user.getHeadportrait();
	}

	
	public int getUserID() {
		return userID;
	}
	
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getNickName() {
		return nickName;
	}
	
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public Sex getSex() {
		return sex;
	}
	
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	
	public Date getBirthday() {
		return birthday;
	}
	
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public byte[] getHeadportrait() {
		return headportrait;
	}
	
	public void setHeadportrait(byte[] headportrait) {
		this.headportrait = headportrait;
	}

}