package com.readytochargeandgo.dao;

import java.util.Calendar;
import java.util.Date;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.readytochargeandgo.domainObjects.AvailableTime;
import com.readytochargeandgo.domainObjects.Booking;
import com.readytochargeandgo.domainObjects.User;



public class Dao implements IDao {
	
	private static Dao dao=null;
	
	private Dao(){
		
	}

	public static Dao getDaoInstance() {
		if (dao == null)		
			dao = new Dao();		
		return dao;			
	}
	
	//Returns the user object or null if don't exists.
	public User getUser(String idUser,String userPass)
	{
		 	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();	
			Key userKey = KeyFactory.createKey("User", idUser);
			Entity user;
			
			String userId = null;
			String userP = null;
			String userName = null;
			String userLastName = null;
			String userAge = null;
			String userSex = null;	
						
			User u=null;			
		
			try {
			    user= datastore.get(userKey);			    
			    userId =   user.getKey().getName();
			    userP = (String) user.getProperty("pass");
			    
			    if (userP.equals(userPass))
			    {
			    userName=(String) user.getProperty("name");
			    userLastName=(String) user.getProperty("lastName");
			    userAge=(String) user.getProperty("age");
			    userSex=(String) user.getProperty("sex");			  		    
			    u=new User(userId,userP,userName,userLastName,userAge,userSex);
			    }
			    
			} catch (EntityNotFoundException e) {
			    // El usuario no existe
			}

			return u;
	}
	
	
	//Creates a new user and returns the created User object.
	@Override
	public User newUser(String id,String pass,String name,String lastName,String age,String sex,String kind)
	{		
			
		if (this.getUser(id,pass)==null)
		{	Entity newUser = new Entity(kind,id);
			newUser.setProperty("pass", pass);
			newUser.setProperty("name", name);
			newUser.setProperty("lastName", lastName);
			newUser.setProperty("age", age);
			newUser.setProperty("sex", sex);		
					
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			datastore.put(newUser);			
			return this.getUser(id,pass); //looking for the User after inserting it.
		}
		else return null;
		
	}
	
	
	
	//Creates a new user and returns the created User object.
		@Override
		public void newUser1(User u)
		{		
				
			if (this.getUser(u.getUserId(),u.getUserPass())==null)
			{	Entity newUser = new Entity("User",u.getUserId());
				newUser.setProperty("pass", u.getUserPass());
				newUser.setProperty("name", u.getUserName());
				newUser.setProperty("lastName", u.getUserLastName());
				newUser.setProperty("age", u.getUserAge());
				newUser.setProperty("sex", u.getUserSex());		
						
				DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
				datastore.put(newUser);			
				
			}
			
			
		}
	
	
	
	//Detetes the specified user if pass ok.
	public boolean deleteUser(String idUser,String pass,String kind)
	{
		 	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();	
			Key userKey = KeyFactory.createKey(kind, idUser);
			Entity user = null;
			String userPass = null;				
			boolean deleted=false;
			try {
			    user= datastore.get(userKey);
			    userPass = (String) user.getProperty("pass");
			    if (userPass.equals(pass))	
			    {		
				   datastore.delete(user.getKey());
				   deleted=true; 
			    }
			    	
			} catch (EntityNotFoundException e) {
			    // El usuario no existe
			}		
			
			return deleted;
	}

	
	//Updates an User.
	@Override
	public User updateUser(String id, String pass, String name,String lastName, String age, String sex) {
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();	
		Key userKey = KeyFactory.createKey("User", id);
		Entity user = null;
		String userPass = null;				
		boolean updated=false;
		try {
		    user= datastore.get(userKey);
		    userPass = (String) user.getProperty("pass");
		    if (userPass.equals(pass))	
		    {				    	
		    	user.setProperty("id", id);
		    	user.setProperty("name", name);
		    	user.setProperty("pass", pass);
		    	user.setProperty("lastName", lastName);
		    	user.setProperty("age", age);
		    	user.setProperty("sex", sex);			   
		    	datastore.put(user);
		    }
		    	
		} catch (EntityNotFoundException e) {
		    // El usuario no existe
		}		
		
		return this.getUser(id,pass);
	}


	
	
	//Return AvailableTimesEntity array
	public Entity[] getAvailableTimes(String locationId,String chargeType)
	{
				// Get the Datastore Service
				DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
				// The Query interface assembles a query
				Query q = new Query("AvailableTime");
			
				
				q.setFilter(CompositeFilterOperator.and(  new FilterPredicate("locationId", Query.FilterOperator.EQUAL, locationId),new FilterPredicate("chargeType", Query.FilterOperator.EQUAL,chargeType),new FilterPredicate("free", Query.FilterOperator.EQUAL,true)));
				PreparedQuery pq = datastore.prepare(q);
				
				
				Entity array[]=new Entity[pq.countEntities()];
				int i=0;

				for (Entity result : pq.asIterable()) 
					{
					array[i]=result;
					i++;
					  		
					}
				return array;
	}
	
	
	public Booking getAvailableTime(Booking booking)
	{
		Booking returnBooking=booking;
		
		
		Date fecha=new Date();
		//Establecemos la hora de apertura de estaciones
		Calendar calendario=Calendar.getInstance();
		
		
		calendario.set(Integer.valueOf(booking.getYear()), Integer.valueOf(booking.getMonth()), Integer.valueOf(booking.getDay()), Integer.valueOf(booking.getHour()), Integer.valueOf(booking.getMinutes()));
		
		Date date=new Date();
		date.setDate(Integer.valueOf(booking.getDay()));
		date.setHours(Integer.valueOf(booking.getHour()));
		date.setMinutes(Integer.valueOf(booking.getMinutes()));
		date.setMonth(Integer.valueOf(booking.getMonth())-1);
		date.setYear(Integer.valueOf(booking.getYear())-1900);
		date.setSeconds(00);
		
		
		
		
		
		calendario.setTime(date);
		
		
		
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		
		
		/*
		//crear una Kind Reserva Entity
		Entity reserva = new Entity("Booking");
		
		
		reserva.setProperty("date",calendario.getTime());
		reserva.setProperty("locationId",booking.getChargeStation());				
		reserva.setProperty("chargeType",booking.getChargeType());
		reserva.setProperty("socketId", booking.getSocketId());
		reserva.setProperty("userId", booking.getUserId());
		
		
		
		
	
		*/
		
		
		
		
		
		
	
		
		// Get the Datastore Service

		// The Query interface assembles a query
		Query q = new Query("AvailableTime");
	
		
		q.setFilter(CompositeFilterOperator.and(  new FilterPredicate("locationId", Query.FilterOperator.EQUAL, booking.getChargeStation()),new FilterPredicate("chargeType", Query.FilterOperator.EQUAL,booking.getChargeType()),new FilterPredicate("free", Query.FilterOperator.EQUAL,true),new FilterPredicate("date", Query.FilterOperator.EQUAL,calendario.getTime())));
		PreparedQuery pq = datastore.prepare(q);
		
		
		

		for (Entity result : pq.asIterable()) 
			{
		
			
			returnBooking.setSocketId(String.valueOf(result.getProperty("socketId")));
			
			Entity reserva = new Entity("Booking");
			
			
			reserva.setProperty("date",calendario.getTime());
			reserva.setProperty("locationId",booking.getChargeStation());				
			reserva.setProperty("chargeType",booking.getChargeType());
			reserva.setProperty("socketId",returnBooking.getSocketId() );
			reserva.setProperty("userId", booking.getUserId());
			datastore.put(reserva);
			
			
			  		
			}
		
		
		

		
		
		
		return returnBooking;
		
	}
	
	
	
	
	
	
	
	//Deletes ReadyToChargeAvailableTimes
	public void deleteReadyToChargeAvailableTimes(){

		// Get the Datastore Service
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		// The Query interface assembles a query
		Query q = new Query("AvailableTime");	
		PreparedQuery pq = datastore.prepare(q);
		
		Entity array[]=new Entity[pq.countEntities()];
		int i=0;
		for (Entity result : pq.asIterable()) 
			{
			datastore.delete(result.getKey());
			}
	}
	
	
	
	//Creates availableTimes of bikes, cars and motorbikes.
	public void createAvailableTimes(){
		
		//Cars	

		Date fecha=new Date();
		//Establecemos la hora de apertura de estaciones
		Calendar calendario=Calendar.getInstance();
		calendario.set(fecha.getYear()+1900, fecha.getMonth(), fecha.getDate(), 8, 00,00);	
		
		//establecemos la hora de cierre de estaciones
		Calendar calendarioFin=Calendar.getInstance();	
		calendarioFin.set(fecha.getYear()+1900, fecha.getMonth(), fecha.getDate(), 23, 00,00);	
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();	
		
		while (calendario.before(calendarioFin))
		{
		Entity recargaCoche = new Entity("AvailableTime");
		recargaCoche.setProperty("locationId", "Deusto");
		recargaCoche.setProperty("chargeType", "coche");
		recargaCoche.setProperty("date",calendario.getTime());
		
		recargaCoche.setProperty("socketId", "C00");
		recargaCoche.setProperty("free", true);
		
		datastore.put(recargaCoche);
		calendario.add(Calendar.MINUTE,30);
		
		}		
		
		
		
		//Motorbikes
		
		
		 fecha=new Date();
		//Establecemos la hora de apertura de estaciones
		 calendario=Calendar.getInstance();
		calendario.set(fecha.getYear()+1900, fecha.getMonth(), fecha.getDate(), 8, 00,00);	
		
		//establecemos la hora de cierre de estaciones
		 calendarioFin=Calendar.getInstance();	
		calendarioFin.set(fecha.getYear()+1900, fecha.getMonth(), fecha.getDate(), 23, 00,00);	
		
			datastore = DatastoreServiceFactory.getDatastoreService();	
		
		
		
		while (calendario.before(calendarioFin))
		{
		Entity recargaMoto = new Entity("AvailableTime");
		recargaMoto.setProperty("locationId", "Deusto");
		recargaMoto.setProperty("chargeType", "moto");
		recargaMoto.setProperty("date",calendario.getTime());
		recargaMoto.setProperty("socketId", "M00");
		recargaMoto.setProperty("free", true);
		
		datastore.put(recargaMoto);
		calendario.add(Calendar.MINUTE,15);
		}
		
		
		
		
		
		//Bikes
		
		
		fecha=new Date();	
		
		//Establecemos la hora de apertura de estaciones
		calendario=Calendar.getInstance();
		calendario.set(fecha.getYear()+1900, fecha.getMonth(), fecha.getDate(), 8, 00,00);	
		
		//establecemos la hora de cierre de estaciones
		calendarioFin=Calendar.getInstance();	
		calendarioFin.set(fecha.getYear()+1900, fecha.getMonth(), fecha.getDate(), 23, 00,00);	
		
		datastore = DatastoreServiceFactory.getDatastoreService();	
		
		while (calendario.before(calendarioFin))
		{
		Entity recargaBici = new Entity("AvailableTime");
		recargaBici.setProperty("locationId", "Deusto");
		recargaBici.setProperty("chargeType", "bici");
		recargaBici.setProperty("date",calendario.getTime());
		
		recargaBici.setProperty("socketId", "B00");
		recargaBici.setProperty("free", true);
		
		datastore.put(recargaBici);
		calendario.add(Calendar.MINUTE,15);
		}
		
		
		
	}

	@Override
	public boolean existsUser(String userId,String kind) {
		// TODO Auto-generated method stub
		
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();	
		Key userKey = KeyFactory.createKey(kind, userId);
		Entity user = null;		
	
		try {
		    user= datastore.get(userKey);			    
		   
		    
		} catch (EntityNotFoundException e) {
		    // El usuario no existe
		}

		if (user!=null)
			return true;
		else return false;
		}

	@Override
	public User getTemporalyUser(String idUser) {
		
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();	
		Key userKey = KeyFactory.createKey("TemporalUsers", idUser);
		Entity user;
		
		String userId = null;
		String userP = null;
		String userName = null;
		String userLastName = null;
		String userAge = null;
		String userSex = null;	
					
		User u=null;			
	
		try {
		    user= datastore.get(userKey);			    
		    userId =   user.getKey().getName();
		    userP = (String) user.getProperty("pass");
		    
		 
		    userName=(String) user.getProperty("name");
		    userLastName=(String) user.getProperty("lastName");
		    userAge=(String) user.getProperty("age");
		    userSex=(String) user.getProperty("sex");			  		    
		    u=new User(userId,userP,userName,userLastName,userAge,userSex);
		    
		    
		} catch (EntityNotFoundException e) {
		    // El usuario no existe
		}

		return u;
		
		
	}

	@Override
	public Booking readyToChargeBooking(Booking bookingRequest) {
		// TODO Auto-generated method stub
	
		
		

		
		try {
			
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			
			
			Date fecha=new Date();
			//Establecemos la hora de apertura de estaciones
			Calendar calendario=Calendar.getInstance();
			calendario.set(fecha.getYear()+1900, fecha.getMonth(), fecha.getDate(), 8, 00,00);		
			calendario.set(Integer.valueOf(bookingRequest.getYear()), Integer.valueOf(bookingRequest.getMonth()), Integer.valueOf(bookingRequest.getDay()), Integer.valueOf(bookingRequest.getHour()), Integer.valueOf(bookingRequest.getMinutes()));
				
						
				
				//crear una Kind Reserva Entity
				Entity booking = new Entity("Booking");				
				booking.setProperty("date",calendario.getTime());
				booking.setProperty("locationId",bookingRequest.getChargeStation());				
				booking.setProperty("chargeType",bookingRequest.getChargeType());
				booking.setProperty("socketId",  bookingRequest.getSocketId());
				booking.setProperty("userId", bookingRequest.getUserId());
				
				
				
				
				datastore.put(booking);
		
				
				
				
				
				
				

				
							
							
			//

				
				
				Key userKey = KeyFactory.createKey("AvailableTime", bookingRequest.getBookingId());
				Entity availableTime = null;
				
				try {
					availableTime= datastore.get(userKey);
				    
				    	
				    				    	
				    	availableTime.setProperty("free", false);
				    				   
				    	datastore.put(availableTime);
				    
				    	
				} catch (EntityNotFoundException e) {
				    // El usuario no existe
				}		
				
				
				
				
				
				
				
				
				bookingRequest.setBookingId(booking.getKey().getId());
				
				
				
				
				
				
				
				
				
		//
				return bookingRequest;
				
				
		}
		
		catch (NullPointerException e){e.printStackTrace();}
		return bookingRequest;
		
		
		
		
		
		
		
		
	}



}
