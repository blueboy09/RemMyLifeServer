package com.remmylife.weibo;





import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.remmylife.diary.*;
import com.remmylife.manager.*;


import weibo4j.Comments;
import weibo4j.Timeline;
import weibo4j.Users;
import weibo4j.http.ImageItem;
import weibo4j.model.Comment;
import weibo4j.model.Status;
import weibo4j.model.User;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONObject;


public class WeiboApp {

		DiaryManager diaryManager = new DiaryManager();
		UserManager userManager = new UserManager();
		CommentManager commentManager = new CommentManager();
		
		
		
		public boolean upload(Diary diary, String accessToken) {
			try {
				try {
					
					//judge mid
					boolean isShared = diaryManager.IsshareToWeibo(diary);
					if(isShared){
						this.destroy(diary,accessToken);
						try { Thread.sleep (5000) ; 
						} catch (InterruptedException ie){}

					}
					
					ImageDiaryManager imageDiaryManager = new ImageDiaryManager();
					ImageDiary imageDiary =  (ImageDiary)imageDiaryManager.getDiary(diary);
					
					byte[] contents = imageDiary.getImages();
					byte[] content =Utils.getImageBytes(ImageStorage.getImages(contents).get(0));
					
					System.out.println("content length:" + content.length);
					ImageItem pic = new ImageItem("pic", content);
					String s = java.net.URLEncoder.encode(imageDiary.getNote(), "utf-8");
					Timeline tl = new Timeline();
					tl.client.setToken(accessToken);// access_token
					Status status = tl.UploadStatus(s, pic);

					//save the mid 
					String mid = status.getMid();
					diaryManager.setWeiboMid(diary, mid);
					diaryManager.shareToWeibo(diary);					

					
					// upload comments
					ArrayList<com.remmylife.diary.Comment> comments = commentManager.getCommentList(diary);
					for(int i=0;i<comments.size();i++){
						if(diary.getUserid()==comments.get(i).getUserid()){
							this.createComment(accessToken,mid,comments.get(i).getContent());
						}
					}
					
					
					System.out.println("Successfully upload the status to ["
							+ status.getText() + "].");
					return true;
				} catch (Exception e1) {
					e1.printStackTrace();
					return false;
				}
			} catch (Exception ioe) {
				System.out.println("Failed to read the system input.");
				return false;
			}
		}
		
		public  boolean updateStatus(Diary diary, String accessToken) {
			
			boolean isShared = diaryManager.IsshareToWeibo(diary);
			
			if(isShared){
				this.destroy(diary,accessToken);
				try { Thread.sleep (5000) ; 
				} catch (InterruptedException ie){}

			}
		
			TextDiaryManager  textDiaryManager = new TextDiaryManager();
			TextDiary textDiary =  (TextDiary)textDiaryManager.getDiary(diary);
			String access_token = accessToken;
			String statuses = textDiary.getText();
			Timeline tm = new Timeline();
			tm.client.setToken(access_token);
			try {
				Status status = tm.UpdateStatus(statuses);
				System.out.println(status.getMid());
				
				//save the mid 
				String mid = status.getMid();
				diaryManager.setWeiboMid(diary, mid);
				diaryManager.shareToWeibo(diary);

				
				// upload comments
				ArrayList<com.remmylife.diary.Comment> comments = commentManager.getCommentList(diary);
				for(int i=0;i<comments.size();i++){
					if(diary.getUserid()==comments.get(i).getUserid()){
						this.createComment(accessToken,mid,comments.get(i).getContent());
					}
				}
				
				Log.logInfo(status.toString());
				return true;
			} catch (WeiboException e) {
				e.printStackTrace();
				return false;
			}	
		
		}

		public String queryMid(String accessToken, String Id) {
			String access_token = accessToken;
			String id = Id;
			Timeline tm = new Timeline();
			tm.client.setToken(access_token);
			try {
				JSONObject mid = tm.QueryMid( 1, id);
					Log.logInfo(mid.toString());
				return mid.toString();
			} catch (WeiboException e) {
				e.printStackTrace();
				return null;
			}
		}

		public String queryId(String accessToken, String Mid) {
			String access_token = accessToken;
			String mid =  Mid;
			Timeline tm = new Timeline();
			tm.client.setToken(access_token);
			try {
				JSONObject id = tm.QueryId( mid, 1,1);
					Log.logInfo(String.valueOf(id));
					return id.toString();
			} catch (WeiboException e) {
				e.printStackTrace();
				return null;
			}

		}
		

		public boolean destroy(Diary diary, String accessToken) {
				String mid = diaryManager.getWeiboMid(diary);
				String access_token = accessToken;
				Timeline tm = new Timeline();
				tm.client.setToken(access_token);
				try {
					Status status = tm.Destroy(mid);
					//Log.logInfo(status.toString());
					diaryManager.setWeiboMid(diary, "0");
					diaryManager.unshareToWeibo(diary);
					return true;
				} catch (WeiboException e) {
					e.printStackTrace();
					return false;
				}
			}

		public boolean showUser(String accessToken, String uid) {
				String access_token = accessToken;
				Users um = new Users();
				um.client.setToken(access_token);
				try {
					User user = um.showUserById(uid);
					//Log.logInfo(user.toString());
					return true;
				} catch (WeiboException e) {
					e.printStackTrace();
					return false;
				}
			}
			
		public boolean createComment(String accessToken, String Mid, String comments) {
				String access_token = accessToken;
				String id = Mid;
				Comments cm = new Comments();
				cm.client.setToken(access_token);
				try {
						Comment comment = cm.createComment(comments, id);
						Log.logInfo(comment.toString());
						return true;
					} catch (WeiboException e) {
						e.printStackTrace();
						return false;
				}
				
			}
						
			
}
