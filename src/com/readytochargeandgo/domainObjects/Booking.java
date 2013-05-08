package com.readytochargeandgo.domainObjects;

import javax.ws.rs.Consumes;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.appengine.api.datastore.Key;

@XmlRootElement
public class Booking {
	
	
	String userId;
	String userPass;
	String chargeType;
	String chargeStation;
	String socketId;
	String hour; 
	String minutes;
	String year;
	String month;
	String day;
	long bookingId;
	
	
	public long getBookingId() {
		return bookingId;
	}
	

	public String getUserPass() {
		return userPass;
	}
	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}
	public void setBookingId(long l) {
		this.bookingId = l;
	}
	public String getMinutes() {
		return minutes;
	}
	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getChargeType() {
		return chargeType;
	}
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	public String getChargeStation() {
		return chargeStation;
	}
	public void setChargeStation(String chargeStation) {
		this.chargeStation = chargeStation;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getSocketId() {
		return socketId;
	}
	public void setSocketId(String socketId) {
		this.socketId = socketId;
	}
	

	
	
}
