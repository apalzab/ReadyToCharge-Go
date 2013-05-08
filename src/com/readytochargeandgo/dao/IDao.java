package com.readytochargeandgo.dao;

import com.google.appengine.api.datastore.Entity;
import com.readytochargeandgo.domainObjects.Booking;
import com.readytochargeandgo.domainObjects.User;



public interface IDao {
	
	public User getUser(String idUsuario,String userPass);
	public User newUser(String id,String pass,String name,String lastName,String age,String sex, String kind);
	public User getTemporalyUser(String idUser);
	public boolean deleteUser(String userId,String pass,String kind);
	public User updateUser(String id,String pass,String name,String lastName,String age,String sex);
	public Entity[] getAvailableTimes(String locationId,String chargeType);
	public Booking getAvailableTime(Booking b);
	public void deleteReadyToChargeAvailableTimes();
	public void createAvailableTimes();
	public boolean existsUser(String userId,String kind);
	public void newUser1(User u);
	public Booking readyToChargeBooking(Booking b);
}
