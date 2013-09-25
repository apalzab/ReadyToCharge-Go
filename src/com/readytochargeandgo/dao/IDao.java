package com.readytochargeandgo.dao;

import java.util.ArrayList;
import java.util.Date;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.readytochargeandgo.domainObjects.AvailableTime;
import com.readytochargeandgo.domainObjects.Booking;
import com.readytochargeandgo.domainObjects.User;



public interface IDao {
	
//User methods
	public User getUser(String idUsuario,String userPass);
	public User newUser(String id,String pass,String name,String lastName,String age,String sex, String kind);
	public User getTemporalyUser(String idUser);
	public boolean deleteUser(String userId,String pass,String kind);
	public User updateUser(String id,String pass,String name,String lastName,String age,String sex);
	public boolean existsUser(String userId,String kind);
	public void newUser1(User u);
	
//ReadytoCharge methods
	public ArrayList<AvailableTime> rtc_getAvailableTimes(String chargeStation,String chargeType, Date date);
	public Booking rtc_makeABooking(String chargeStation, String chargeType, Date date, User user);
	public ArrayList<Booking> rtc_getBookings(String userId);
	public boolean rtc_cancelBooking(String userId, String bookingI);
	
//ReadyToGo methods
}
