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
		 
		 //comprobamos usuario
		 
		 DAOService service=new DAOService();
			
			
			Entity arrayRecargas[]=service.getAvailableTimes(bookingRequest.getChargeStation(),bookingRequest.getChargeType());
			
			for (int i=0;i<arrayRecargas.length;i++)
			{
				Date d=(Date)arrayRecargas[i].getProperty("date");
				if (d.getHours()==Integer.valueOf(bookingRequest.getHour()) && d.getMinutes()==Integer.valueOf(bookingRequest.getMinutes()))
					{
					
					bookingRequest.setSocketId((String) arrayRecargas[i].getProperty("socketId"));
					bookingRequest.setBookingId(arrayRecargas[i].getKey().getId());
					bookingResponse=service.readyToChargeBooking(bookingRequest);
					return bookingResponse;
					}
			}
			
			
		//return service.getAvailableTime(bookng);
			return bookingResponse;
	    }
	
}
