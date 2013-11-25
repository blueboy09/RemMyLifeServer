package com.remmylife.diary;

import java.io.Serializable;

public class Comment implements Serializable{
	private int commentid;
	private int userid;
	private int diaryid;
	private String content;
	
	public void init(){
		this.commentid = 0;
	}
	
	
	public Comment() {
		super();
		init();
	}


	public Comment(String content) {
		super();
		init();
		this.content = content;
	}

	

	public Comment(int userid, int diaryid, String content) {
		super();
		init();
		this.userid = userid;
		this.diaryid = diaryid;
		this.content = content;
	}



	public Comment(int commentid, int userid, int diaryid, String content) {
		super();
		this.commentid = commentid;
		this.userid = userid;
		this.diaryid = diaryid;
		this.content = content;
	}

	public Comment(Comment comment){
		super();
		this.commentid = comment.getCommentid();
		this.userid = comment.getUserid();
		this.diaryid = comment.getDiaryid();
		this.content = comment.getContent();
	}

	public int getCommentid() {
		return commentid;
	}



	public void setCommentid(int commentid) {
		this.commentid = commentid;
	}



	public int getUserid() {
		return userid;
	}



	public void setUserid(int userid) {
		this.userid = userid;
	}



	public int getDiaryid() {
		return diaryid;
	}



	public void setDiaryid(int diaryid) {
		this.diaryid = diaryid;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}
	
	
	
}


