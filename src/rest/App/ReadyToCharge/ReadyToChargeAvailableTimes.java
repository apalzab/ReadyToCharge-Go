package rest.App.ReadyToCharge;


import java.util.Arrays;
import java.util.Date;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import com.google.appengine.api.datastore.Entity;
import com.readytochargeandgo.dao.DAOService;
import com.readytochargeandgo.domainObjects.AvailableTime;




@Path("/ReadyToChargeAvailableTimes")
public class ReadyToChargeAvailableTimes {	
	
	//@GET
	//Returns the AvailableTimes.
	 @GET
	 @Path("/{locationId}/{chargeType}/{hours}")
	 @Produces("application/json")
	 	public AvailableTime[] getHorarios(@PathParam("locationId") String locationId,@PathParam("chargeType") String chargeType,@PathParam("hours") String hours ) {	 
	
		 DAOService service=new DAOService();
	
	//en el array tenemos todos los Entitys que est??n libres para la sede y el tipo de carga
	Entity arrayRecargas[]=service.getAvailableTimes(locationId, chargeType);


	/*Creamos un objeto date con los par??metros de hora pasados por el usuario para poder compararlos
	con los que tenemos en el array de Entitys Recarga*/
	Date fechaUsuario=new Date();	
	int horaU=Integer.parseInt(hours.substring(0,2));
	int minU=Integer.parseInt(hours.substring(3,5));	
	fechaUsuario.setHours(horaU);
	fechaUsuario.setMinutes(minU);
	fechaUsuario.setSeconds(00);
	
	
	//metemos en un arrayFechas los objetos Date que 
	//est??n en el arrayRecargas	
	
	Date arrayFechas[]=new Date[arrayRecargas.length];
	for (int i=0;i<arrayRecargas.length;i++)
	{
		Date d=(Date)arrayRecargas[i].getProperty("date");
		d.setMonth(d.getMonth()+1);
		if (d.after(fechaUsuario) )			
			arrayFechas[i]=d;
		if (d.compareTo(fechaUsuario)==0 )
			arrayFechas[i]=d;
		
			
	}	
	
				
	
	//Fuunciona!
	
	//Contamos cu??ntas posiciones distinas de null tiene el array para poder inicializar el otro.
	int cont=0;
	for (int i=0;i<arrayFechas.length;i++)
	{
		if (arrayFechas[i]!=null)
			cont++;
	}
	
	int pos=0;
	Date arrayFinal[]=new Date[cont];
	for (int i=0;i<arrayFechas.length;i++)
		{
		if (arrayFechas[i]!=null)
			{
			arrayFinal[pos]=arrayFechas[i];
			pos++;
			}
		}
		
	Arrays.sort(arrayFinal);	
	

	AvailableTime availableTime[]=new AvailableTime[arrayFinal.length];
for (int i=0;i<arrayFinal.length;i++)
{

Date d=arrayFinal[i];

String year=Integer.toString(d.getYear()+1900);
String month=Integer.toString(d.getMonth());
String day=Integer.toString(d.getDate());
String hour=Integer.toString(d.getHours());
String minutes=Integer.toString(d.getMinutes());

AvailableTime avblTime=new AvailableTime(year,month,day,hour,minutes);
availableTime[i] = avblTime;

}
	
return availableTime;
	 }
	 
	//@POST
	//Creates all the AvailableTimes.
	 @POST		
	 	public Response generarHorarios() {
		 
			 DAOService service=new DAOService();
			 service.createAvailableTimes();
			 	return Response.status(200).type("text/plain").entity("Horarios generados!").build(); 	
				
		      } 
									
	//@DELETE
	//Deletes all AvailableTimes from the Datastore.
	@DELETE
		public Response deleteAvailableTimes() {	
		
		DAOService service=new DAOService();
		service.deleteAvailableTimes();
			 return Response.status(200).type("text/plain").entity("Horarios borrados!").build(); 	
		  }


}
