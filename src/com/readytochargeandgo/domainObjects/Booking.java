package com.readytochargeandgo.domainObjects;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.appengine.api.datastore.Key;

@XmlRootElement
public class Booking {
	
	String userId;
	String chargeType;
	String chargeStation;
	String socketId;
	String hour; 
	String minutes;
	String year;
	String month;
	String day;
	String date;
	String served;
	String cancellable;
	
	public String getCancellable() {
		return cancellable;
	}

	public void setCancellable(String cancellable) {
		this.cancellable = cancellable;
	}

	public String getServed() {
		return served;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date.toString();
	}

	String bookingId;
		

	public String getBookingId() {
		return bookingId;
	}
	
	public void setBookingId(String id) {
		this.bookingId = id;
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

	public void setServed(String served) {
		this.served = served;
	}


	
	
}
