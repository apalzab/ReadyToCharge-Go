package com.readytochargeandgo.dao;

import java.util.ArrayList;
import java.util.Date;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.readytochargeandgo.domainObjects.AvailableTime;
import com.readytochargeandgo.domainObjects.Booking;
import com.readytochargeandgo.domainObjects.User;



public class DAOService {
	
	private Dao dao;
	
	public DAOService() {
		this.dao = Dao.getDaoInstance();
	}		
	
	
//User methods
	public User getUser(String idUser,String userPass) {
		return dao.getUser(idUser,userPass);
	}
	
	public User getTemporalyUser(String idUser) {
		return dao.getTemporalyUser(idUser);
	}
	
	public User newUser(String id,String pass,String name,String lastName,String age,String sex,String kind) {
		return dao.newUser(id, pass, name, lastName, age, sex, kind);
	}
	
	public void newUser1(User u) {
		dao.newUser1(u);
	}
	
	public boolean deleteUser(String id,String pass,String kind) {
		return dao.deleteUser(id, pass,kind);
	}
	
	public User updateUser(String id,String pass,String name,String lastName,String age,String sex) {
		return dao.updateUser(id, pass, name, lastName, age, sex);
	}

	public boolean existsUser(String userId,String kind) {		
		return dao.existsUser(userId,kind);
	}
		
	
//ReadyToCharge methods
	
	public ArrayList<AvailableTime> getAvailableTimes(String chargeStation, String chargeType, Date date) {
		return dao.rtc_getAvailableTimes(chargeStation, chargeType, date);
	}
	
	public Booking rtc_makeABooking(String chargeStation, String chargeType, Date date, User user ) {
		return dao.rtc_makeABooking(chargeStation, chargeType, date, user);
	}
	
	public ArrayList<Booking> rtc_getBookings(String userId) {
		return dao.rtc_getBookings(userId);
	}
	
	public boolean rtc_cancelBooking(String userId, String bookingId) {
		return dao.rtc_cancelBooking(userId, bookingId);
		
	}
		
	
//ReadyToGo methods
}
