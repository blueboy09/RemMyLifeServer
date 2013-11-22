package com.remmylife.manager;
import java.util.ArrayList;

import com.remmylife.diary.*;
import com.remmylife.dbacess.*;

public  class Manager {
	
	protected String driver ; 
	protected String url ;
	protected String user ;
	protected String password;
	protected DataManager dataManager;
	
	public Manager() {
		super();
		dataManager = new DataManager();
		dataManager.execSqlFile("initial.sql");
		dataManager.setUrl(dataManager.getUrl()+"remembermylife");
		this.driver = dataManager.getDriver();
		this.url = dataManager.getUrl();
		this.user = dataManager.getUser();
		this.password = dataManager.getPassword();
	}

	public Manager(String driver, String url, String user, String password) {
		super();
		dataManager = new DataManager();
		dataManager.execSqlFile("initial.sql");
		dataManager.setUrl(dataManager.getUrl()+"remembermylife");
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.password = password;
		dataManager = new DataManager(driver,url,user,password);
	}

	
	@SuppressWarnings("rawtypes")
	public  ArrayList getList(){
		return null;
	}
	
	public  boolean save(){
		return true;
	}
		
	public  boolean delete(){
		return true;
	}
	

	
	public  boolean deleteList(){
		return true;
	}
	
	@SuppressWarnings("rawtypes")
	public  ArrayList constructList(DataManager dataManager){
		return null; 
	}
	
	@SuppressWarnings("rawtypes")
	public  ArrayList execSqlQuery(String query){
		return null;
	}
		
	
	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
