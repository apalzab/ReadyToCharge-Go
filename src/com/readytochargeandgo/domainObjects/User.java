package com.readytochargeandgo.domainObjects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement 
public class User {
	
	private String userId;
	private String userPass;
	private String userLastName;
	private String userAge;
	private String userSex;
	private String userName;

	
	User(){
		
	}
	
	 public User(String id,String pass,String name,String lastName,String age,String sex)
	{
		 this.userId=id;
		 this.userPass=pass;
		 this.userName=name;
		 this.userLastName=lastName;
		 this.userAge=age;
		 this.userSex=sex;
	
	}
	 

	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}



	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getUserAge() {
		return userAge;
	}

	public void setUserAge(String userAge) {
		this.userAge = userAge;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	



}
