package rest.App.ReadyToCharge;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.appengine.api.datastore.Entity;
import com.readytochargeandgo.dao.DAOService;
import com.readytochargeandgo.domainObjects.AvailableTime;
import com.readytochargeandgo.utilities.Authentication;

@Path("/rtc-available-times")
public class Rtc_available_times {
	
	 @GET
	 @Path("/{chargeStation}/{chargeType}/{date}")
	 @Produces("application/json")
	 	public ArrayList<AvailableTime> getHorarios(@PathParam("chargeStation") String chargeStation, @PathParam("chargeType") String chargeType,@PathParam("date") String date, @CookieParam("sessionid") Cookie sessionCookie) {
		
		//if (Authentication.getAuthenticationInstance().authenticate(sessionCookie))
		//{ 
			String[] dates = date.split("-");
			String year = dates[0]; 
			String month = dates[1];
			String day = dates[2];
			
			Date today = new Date();
			today.setHours(today.getHours()+2);
			System.out.println("TODAAAYYYYYY" + today.toString());
			Date open = new Date();
			Date close = new Date();
			open.setHours(8);
			close.setHours(23);
			close.setMinutes(59);			
	
			Date d = new Date(Integer.valueOf(year) - 1900, Integer.valueOf(month) - 1, Integer.valueOf(day));
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			
			
			if (today.getDate() == d.getDate() && today.getMonth() == d.getMonth() && today.getYear() == d.getYear())
			{
				System.out.println("Requesting today's available times!");
				d.setHours(today.getHours());
				d.setMinutes(today.getMinutes());
				d.setSeconds(00);
				if (d.before(open) || d.after(close)){
					d.setHours(8);
					d.setMinutes(00);
					d.setSeconds(00);
				}
					
				
			} else {
				System.out.println("Requesting future available times!");
				d.setHours(8);
				d.setMinutes(00);
				d.setSeconds(00);
			}
			
			DAOService service = new DAOService();	
			
			System.out.println("LOOKING FOR TIME:" + d.toString());
			return service.getAvailableTimes(chargeStation, chargeType, d);	
		//} else return null;
	 
 	} 

}
