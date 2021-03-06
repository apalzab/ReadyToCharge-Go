package com.readytochargeandgo.domainObjects;

import java.util.Date;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AvailableTime {
	
	private String year;
	private String month;
	private String day;
	private String hour;
	private String minutes;
	private String chargeStation;
	private String chargeType;

	public String getChargeStation() {
		return chargeStation;
	}

	public void setChargeStation(String chargeStation) {
		this.chargeStation = chargeStation;
	}

	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public AvailableTime() {}
	
	public AvailableTime(String year,String month,String day,String hour,String minutes)
	{
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
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

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getMinutes() {
		return minutes;
	}

	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}




	

}
