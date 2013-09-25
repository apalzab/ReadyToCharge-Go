package rest.App.ReadyToCharge;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.appengine.api.datastore.Entity;
import com.readytochargeandgo.dao.DAOService;
import com.readytochargeandgo.domainObjects.Booking;
import com.readytochargeandgo.domainObjects.User;
import com.sun.jersey.core.util.Base64;

@Path("/rtc-bookings")
public class Rtc_bookings {
	 
	@POST		
	 @Consumes("application/json")
	 @Produces("application/json")
	 public Response booking(Booking bookingRequest, @Context HttpHeaders headers) 
	 	{
		 
		DAOService service = new DAOService();
		User user = new User();
		Booking bookingResponse = new Booking();
		
	 	String header = null;
	  	try {
	  		header = headers.getRequestHeader("authorization").get(0);	
	  	} catch (NullPointerException e) {
	  		
	  	}
	 	
	 	if (header != null) {
		   header = header.substring("Basic ".length());
		   String[] creds = new String(Base64.base64Decode(header)).split(":");
		   String username = creds[0];
		   String password = creds[1];
	       user = service.getUser(username, password);
	       
	       if (user.getId() != null) {
	    	System.out.println("Here is the user..." + user.getName());	    	
			Date bookingDate = new Date(Integer.valueOf(bookingRequest.getYear())-1900, Integer.valueOf(bookingRequest.getMonth())-1,Integer.valueOf(bookingRequest.getDay()), Integer.valueOf(bookingRequest.getHour()), Integer.valueOf(bookingRequest.getMinutes()), 00);
	    	bookingResponse = service.rtc_makeABooking(bookingRequest.getChargeStation(), bookingRequest.getChargeType(), bookingDate, user);
	    	return Response.ok(bookingResponse, MediaType.APPLICATION_JSON).build();
	    	 
	       } else return Response.status(Response.Status.UNAUTHORIZED).build();
					
	 	} else return Response.status(Response.Status.UNAUTHORIZED).build();
	 		
 	}
	
	 @GET
	 @Produces("application/json")
	 public Response getBookings(@Context HttpHeaders headers)
	 {
	 	DAOService service = new DAOService();
		User user = new User();
	 	String header = null;
	  	try {
	  		header = headers.getRequestHeader("authorization").get(0);	
	  	} catch (NullPointerException e) {}
		 	
	 	if (header != null) {
		   header = header.substring("Basic ".length());
		   String[] creds = new String(Base64.base64Decode(header)).split(":");
		   String username = creds[0];
		   String password = creds[1];
	       user = service.getUser(username, password);
	       
	       if (user.getId() != null) {
	    	return Response.ok(service.rtc_getBookings(user.getId()), MediaType.APPLICATION_JSON).build();
	       } else return Response.status(Response.Status.UNAUTHORIZED).build();
					
	 	} else return Response.status(Response.Status.UNAUTHORIZED).build();

	 }
	 
	 
	 @DELETE
	 @Path("/{bookingId}")
	 public Response cancelBooking(@Context HttpHeaders headers, @PathParam("bookingId") String bookingId)
	 {
	 	DAOService service = new DAOService();
		User user = new User();
	 	String header = null;
	  	try {
	  		header = headers.getRequestHeader("authorization").get(0);	
	  	} catch (NullPointerException e) {}
		 	
	 	if (header != null) {
		   header = header.substring("Basic ".length());
		   String[] creds = new String(Base64.base64Decode(header)).split(":");
		   String username = creds[0];
		   String password = creds[1];
	       user = service.getUser(username, password);
	       
	       if (user.getId() != null) {
	    	   if (service.rtc_cancelBooking(user.getId(), bookingId)) {
	    		   return Response.status(Response.Status.OK).build();
	    	   }	    		  
	    	   else  Response.status(Response.Status.NOT_MODIFIED).build();
	    		   
	       } else return Response.status(Response.Status.UNAUTHORIZED).build();
					
	 	} else return Response.status(Response.Status.UNAUTHORIZED).build();
		return null;
		

	 }
}
