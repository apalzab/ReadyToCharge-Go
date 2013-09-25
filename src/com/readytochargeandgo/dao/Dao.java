package com.readytochargeandgo.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

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
import com.readytochargeandgo.utilities.BCrypt;

public class Dao implements IDao {
	
	private static Dao dao ;
	
	private Dao(){
		
	}

	public static Dao getDaoInstance() {
		if (dao == null)		
			dao = new Dao();		
		return dao;			
	}
	
	
// USER METHODS
	
	//Returns the user object or null if don't exists.
	public User getUser(String userId, String pass) {
		
	 	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();	
		Key userKey = KeyFactory.createKey("User", userId);
		Entity user;			
		String id = null;
		String password = null;
		String name = null;
		String lastName = null;
		String age = null;
		String sex = null;							
		User u = new User();			
	
		try {
			    user = datastore.get(userKey);	
			    id = user.getKey().getName();			    
			    if (BCrypt.checkpw(pass, (String) user.getProperty("pass"))) 
			    {
				    name = (String) user.getProperty("name");
				    lastName = (String) user.getProperty("lastName");
				    age = (String) user.getProperty("age");
				    sex = (String) user.getProperty("sex");			  		    
				    u = new User(id, password, name, lastName, age, sex);
		    	}
		    } catch (Exception e) {
		    	return u;
		}
	return u;
	}
	
	//Creates a new user and returns the created User object.
	@Override
	public User newUser(String id, String pass, String name, String lastName, String age, String sex, String kind) {		

			pass = BCrypt.hashpw(pass,  BCrypt.gensalt());
			Entity newUser = new Entity(kind,id);
			newUser.setProperty("pass", pass);
			newUser.setProperty("name", name);
			newUser.setProperty("lastName", lastName);
			newUser.setProperty("age", age);
			newUser.setProperty("sex", sex);		
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			datastore.put(newUser);
			return this.getUser(id,pass); //looking for the User after inserting it.
	}
	
		@Override
	public void newUser1(User u)
		{		
			
			if (!existsUser(u.getId(),"Users")) {
				Entity newUser = new Entity("User",u.getId());
				newUser.setProperty("pass", u.getPass());
				newUser.setProperty("name", u.getName());
				newUser.setProperty("lastName", u.getLastName());
				newUser.setProperty("age", u.getAge());
				newUser.setProperty("sex", u.getSex());
				DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
				datastore.put(newUser);				
			}	
			
		}
		
	//Deletes the specified user if pass ok.
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
			    // No user found
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
		    // No user found
			}		
		
		return this.getUser(id,pass);
	}
		
	@Override
	public boolean existsUser(String userId,String kind) {
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();	
		Key userKey = KeyFactory.createKey(kind, userId);
		Entity user = null;		
	
		try {
		    user = datastore.get(userKey);			    
		} catch (EntityNotFoundException e) {
		    // No user found
		}

		if (user != null)
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
		    user = datastore.get(userKey);			    
		    userId = user.getKey().getName();
		    userP = (String) user.getProperty("pass");
		    userName = (String) user.getProperty("name");
		    userLastName = (String) user.getProperty("lastName");
		    userAge = (String) user.getProperty("age");
		    userSex = (String) user.getProperty("sex");			  		    
		    u = new User(userId,userP,userName,userLastName,userAge,userSex);
		    } catch (EntityNotFoundException e) {
		    // No user found.
		    }

		return u;	
	}

//READYTOCHARGE METHODS

	public ArrayList<AvailableTime> rtc_getAvailableTimes(String chargeStation, String chargeType, Date date) {
		
		System.out.println("rtc_getAvailableTimes" + date.toString());
		
		if (date.getMinutes() < 15 && date.getMinutes() > 0 )
			date.setMinutes(15);
		else if (date.getMinutes() >= 15 && date.getMinutes() <= 30)
			date.setMinutes(30);
		else if (date.getMinutes() > 30 && date.getMinutes() <= 45)
			date.setMinutes(45);
		else if (date.getMinutes() > 45) {
			date.setHours(date.getHours()+1);
			date.setMinutes(00);
		}
		
		System.out.println("rtc_getAvailableTimes After first if" + date.toString());
	
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("Booking-RTC");
		
		Date datePlus24 = new Date(date.getYear(), date.getMonth(), date.getDate()+1);
		
		//Loads all the booked hours of the day. Later will check the dates
		q.setFilter(CompositeFilterOperator.and(new FilterPredicate("chargeStation", Query.FilterOperator.EQUAL,chargeStation), new FilterPredicate("chargeType", Query.FilterOperator.EQUAL, chargeType), new FilterPredicate("date", Query.FilterOperator.GREATER_THAN_OR_EQUAL,date) , new FilterPredicate("date", Query.FilterOperator.LESS_THAN_OR_EQUAL,datePlus24)));  //, new FilterPredicate("free", Query.FilterOperator.EQUAL,false)
		PreparedQuery pq = datastore.prepare(q);
		
		System.out.println("We ha found .....some entities..." + pq.countEntities());
		
		Calendar calendarioDate =  Calendar.getInstance();
		calendarioDate.setTime(date);
		//calendarioDate.setTimeZone(TimeZone.getTimeZone("GMT+2"));
		
		Calendar calendarioPlus24 = Calendar.getInstance();
		calendarioPlus24.setTime(datePlus24);
		
		int amount = 0;
		if (chargeType.equals("coche"))
			amount = 30;
		else amount = 15;
		
		ArrayList<Date> availableDates = new ArrayList<Date>();
		int i = 0;
		boolean isInside = false;
			
		while (calendarioDate.before(calendarioPlus24))
		{
			isInside = false;
			Date generatedDate = new Date (calendarioDate.getTimeInMillis());
			
			if (pq.countEntities() != 0)
			{
				for (Entity result : pq.asIterable()) 
				{
					Date dat = (Date) result.getProperty("date");
					//Checks if the generated date is already booked. If not, adds to the availableDates
					if (generatedDate.equals(dat)) {
						isInside = true;
					} 
				}
				if (isInside == false) {
					availableDates.add(generatedDate);
					i++;
				}
			}
			else availableDates.add(generatedDate);
			
			calendarioDate.add(Calendar.MINUTE, amount);
		}
		
		ArrayList<AvailableTime> availableTimes = new ArrayList<AvailableTime>();
		for (int j = 0; j < availableDates.size(); j++)
		{
			Date d = availableDates.get(j);
			AvailableTime availableTime = new AvailableTime();
			availableTime.setDay(String.valueOf(d.getDate()));
			availableTime.setMonth(String.valueOf(d.getMonth()+1));
			availableTime.setYear(String.valueOf(d.getYear()+1900));
			availableTime.setHour(String.valueOf(d.getHours()));
			availableTime.setMinutes(String.valueOf(d.getMinutes()));
			if (availableTime.getMinutes().equals("0") )
				availableTime.setMinutes("00");
			availableTime.setChargeStation(chargeStation);
			availableTime.setChargeType(chargeType);
			
			availableTimes.add(j, availableTime);
			
			System.out.println(availableTimes.get(j).getHour());
		}

		return availableTimes;
	}
	

	public Booking rtc_makeABooking(String chargeStation, String chargeType, Date date, User user ) {
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("Booking-RTC");		
		Date datePlus24 = new Date(date.getYear(), date.getMonth(), date.getDate()+1);
		
		q.setFilter(CompositeFilterOperator.and(new FilterPredicate("date", Query.FilterOperator.EQUAL,date), new FilterPredicate("chargeStation", Query.FilterOperator.EQUAL,chargeStation), new FilterPredicate("chargeType", Query.FilterOperator.EQUAL, chargeType)));
		PreparedQuery pq = datastore.prepare(q);
		
		System.out.println("Number of entiBhSBHSBAHBSHGSV" + pq.countEntities());
		
		if (pq.countEntities() == 0) {
			Entity booking = new Entity("Booking-RTC");
			booking.setProperty("userId", user.getId());
			booking.setProperty("chargeStation", chargeStation);
			booking.setProperty("chargeType", chargeType);
			booking.setProperty("socketId", "1A");
			booking.setProperty("date", date);
			booking.setProperty("served", false);
			datastore.put(booking);
			
			System.out.println(booking.getKey().getId() + "HOOOLAAAA");
			
			
			Booking responseBooking = new Booking();
			responseBooking.setBookingId(String.valueOf(booking.getKey().getId()));
			responseBooking.setUserId(String.valueOf(booking.getProperty("userId")));
			responseBooking.setChargeStation(String.valueOf(booking.getProperty("chargeStation")));
			responseBooking.setChargeType(String.valueOf(booking.getProperty("chargeType")));
			responseBooking.setSocketId(String.valueOf(booking.getProperty("socketId")));			
			responseBooking.setDay(String.valueOf(date.getDate()));
			responseBooking.setMonth(String.valueOf(date.getMonth()+1));
			responseBooking.setYear(String.valueOf(date.getYear()+1900));
			responseBooking.setHour(String.valueOf(date.getHours()));
			responseBooking.setMinutes(String.valueOf(date.getMinutes()));
			responseBooking.setDate(date.toString());
			
			return responseBooking;
		} else return null;
		
	}

	public ArrayList<Booking> rtc_getBookings(String userId) {
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("Booking-RTC");		
		q.setFilter(new FilterPredicate("userId", Query.FilterOperator.EQUAL, userId));
		q.addSort("date", Query.SortDirection.DESCENDING);
		PreparedQuery pq = datastore.prepare(q);
		
		ArrayList<Booking> bookings = new ArrayList<Booking>();
		if (pq.countEntities() != 0) {
			for (Entity result : pq.asIterable()) {
				Booking booking = new Booking();
				booking.setBookingId(String.valueOf(result.getKey().getId()));
				booking.setUserId(String.valueOf(result.getProperty("userId")));
				booking.setChargeStation(String.valueOf(result.getProperty("chargeStation")));
				booking.setChargeType(String.valueOf(result.getProperty("chargeType")));
				booking.setSocketId(String.valueOf(result.getProperty("socketId")));			
				booking.setDate(String.valueOf(result.getProperty("date")));
				Boolean served = (Boolean) result.getProperty("served");
				booking.setServed(served.toString());
				
				if (booking.getServed() == "false") {
					Date bookingDate = (Date) result.getProperty("date");
					Date now = new Date();
					
					//7200000 = 2 hours
					if (bookingDate.getTime() - now.getTime() > 7200000 )  {
						booking.setCancellable("true");
					} else booking.setCancellable("false");
				}

			bookings.add(booking);
			}
		}
		return bookings;
	}

	
	public boolean rtc_cancelBooking(String userId, String bookingId) {
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity booking;
		Key bookingKey = KeyFactory.createKey("Booking-RTC", Integer.valueOf(bookingId));
		try {
			booking = datastore.get(bookingKey);
			String uId = String.valueOf(booking.getProperty("userId")); 
			if (uId.equals(userId)) {
				datastore.delete(bookingKey);
			}
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
	}

	
//READYTOGO METHODS
}
