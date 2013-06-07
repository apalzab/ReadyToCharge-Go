package rest.App.ReadyToCharge;


import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;


import com.google.appengine.api.datastore.Entity;
import com.google.gson.Gson;
import com.readytochargeandgo.dao.DAOService;
import com.readytochargeandgo.domainObjects.Booking;




@Path("/ReadyToChargeBookings")
public class ReadyToChargeBookings {


	 @POST
	
	 @Consumes("application/json")
	 @Produces("application/json")
	 public Booking booking(Booking bookingRequest) 
	 	{
			
		 Booking bookingResponse=new Booking(); 
		 DAOService service=new DAOService();	
		 Integer hour = Integer.valueOf(bookingRequest.getHour()); 
		 Integer minutes = Integer.valueOf(bookingRequest.getMinutes());
		 Entity arrayRecargas[]=service.getAvailableTimes(bookingRequest.getChargeStation(),bookingRequest.getChargeType());			
			for (int i=0;i<arrayRecargas.length;i++)
			{ 
				boolean free = (boolean) arrayRecargas[i].getProperty("free");
				Date d=(Date)arrayRecargas[i].getProperty("date");
				if (d.getHours()==hour && d.getMinutes()== minutes && free==true  )
					{
					
					bookingRequest.setSocketId((String) arrayRecargas[i].getProperty("socketId"));
					bookingRequest.setBookingId(arrayRecargas[i].getKey().getId());
					bookingResponse=service.readyToChargeBooking(bookingRequest, arrayRecargas[i].getKey());
					bookingResponse = bookingRequest;
					return bookingResponse;
					}
			}
			return bookingResponse;
	    }
	
}
