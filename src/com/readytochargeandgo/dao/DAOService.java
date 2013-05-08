package com.readytochargeandgo.dao;

import com.google.appengine.api.datastore.Entity;
import com.readytochargeandgo.domainObjects.Booking;
import com.readytochargeandgo.domainObjects.User;



public class DAOService {
	
	private Dao dao;
	
	public DAOService(){
		this.dao=Dao.getDaoInstance();
	}		
	
	
	public User getUser(String idUser,String userPass){
		return dao.getUser(idUser,userPass);
	}
	
	public User getTemporalyUser(String idUser){
		return dao.getTemporalyUser(idUser);
	}
	
	public User newUser(String id,String pass,String name,String lastName,String age,String sex,String kind){
		return dao.newUser(id, pass, name, lastName, age, sex, kind);
	}
	
	public void newUser1(User u){
		dao.newUser1(u);
	}
	
	public boolean deleteUser(String id,String pass,String kind){
		return dao.deleteUser(id, pass,kind);
	}
	
	public User updateUser(String id,String pass,String name,String lastName,String age,String sex){
		return dao.updateUser(id, pass, name, lastName, age, sex);
	}
	
	public Entity[] getAvailableTimes(String locationId,String chargeType){
		return dao.getAvailableTimes(locationId, chargeType);
	}
		public void deleteAvailableTimes(){
			dao.deleteReadyToChargeAvailableTimes();		
	}
		
	public Booking getAvailableTime(Booking b){
			return dao.getAvailableTime(b);
	}



		public void createAvailableTimes() {
			dao.createAvailableTimes();
		}


		public boolean existsUser(String userId,String kind) {
			// TODO Auto-generated method stub			
			return dao.existsUser(userId,kind);
		}
	
		
		public Booking readyToChargeBooking(Booking b){
			return dao.readyToChargeBooking(b);
		}
	

}
