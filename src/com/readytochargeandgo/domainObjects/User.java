package com.readytochargeandgo.domainObjects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement 
public class User {
	
	private String id;
	private String pass;
	private String lastName;
	private String age;
	private String sex;
	private String name;

	
	public User(){
		
	}
	
	 public User(String id,String pass,String name,String lastName,String age,String sex)
	{
		 this.id=id;
		 this.pass=pass;
		 this.name=name;
		 this.lastName=lastName;
		 this.age=age;
		 this.sex=sex;
	
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	 

	

	



}
