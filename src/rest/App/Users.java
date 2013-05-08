package rest.App;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.readytochargeandgo.dao.DAOService;
import com.readytochargeandgo.domainObjects.User;
import com.readytochargeandgo.utilities.EmailSender;





@Path("/Users")
public class Users {	


	 @GET
	 @Path("/{userId}/{userPass}")
	 @Produces("application/json")
	 	public User getUser(@PathParam("userId") String userId,@PathParam("userPass") String userPass) 
	 {			 
		 	DAOService service=new DAOService();
			User user=service.getUser(userId,userPass);	
			if (user==null)
			{
			
			User u=new User("null","null","null","null","null","null");
			return u;
			}	
			return user;
	}
	 
	 
	 @POST	 
	 @Consumes("application/json")
	 	public Response postUser(User user) 
	 	{		 	
			 DAOService service=new DAOService();
			 	
			 //Looks if the user exists between Users and TemporalUsers
			 if (service.existsUser(user.getUserId(),"User") || service.existsUser(user.getUserId(),"TemporalUsers"))
				 return Response.status(500).type("text/plain").entity("ReadyToCharge&Go informa de que el email introducido ya existe en el sistema!").build();
			 
			 else 
			 {	
				//Creates a new TemporalUser and sends a confirmation email.  			 	
			 	service.newUser(user.getUserId(),user.getUserPass(),user.getUserName(),user.getUserLastName(),user.getUserAge(),user.getUserSex(),"TemporalUsers");			
			 	EmailSender.getEmailSenderInstance().newUserConfirmationEmail(user);
				return Response.status(200).type("text/plain").entity("Se te ha enviado un email de confirmación a la dirección proporcionada. ¡Es necesario que revises tu correo y confirmes la solicitud para poder utilizar ReadyToCharge&GO!").build();
			 }
		}
	 
	 
	 
	 @GET
	 @Path("/{userId}")
	 	public Response postUser(@PathParam("userId") String userId) 
	 	{		 	
		 	DAOService service=new DAOService();

		 	if (!service.existsUser(userId,"User"))
		 	{		 	
		 		User u=service.getTemporalyUser(userId);
		 		service.newUser1(u);
		 		service.deleteUser(u.getUserId(), u.getUserPass(), "TemporalUsers");
		 		String link="http://rtcandg	.appspot.com/";
			 	return Response.status(200).type("text/html").entity("<div><p>Felicidades " +u.getUserName() + ", hemos creado tu perfil en nuestro sistema. ¡Ya puedes utilizar <a href="+ link +">ReadyToCharge&Go!</a></p></div>").build();
		 		
		 	}
		 	else return Response.status(500).type("text/plain").entity("Lo sentimos pero el usuario ya existe").build();
		 	
		 	
		 	
	    }
	 
	 
	 
	 
	 @DELETE	 
	 @Path("/{userId}/{userPass}")	 
	 	public void deleteUser(@PathParam("userId") String userId,@PathParam("userPass") String userPass) 
	 	{		 	
		 	DAOService service=new DAOService();
			boolean deleted=service.deleteUser(userId,userPass,"User");			
			if (deleted)
				Response.status(200);	
			else
				Response.status(406);
		}
	 
	 
	 @PUT
	 @Path("/{userId}/{userPass}/{userName}/{userLastName}/{userAge}/{userSex}")
	 
	 @Consumes("application/json")
	 	public User putUser(User user) 
	 	{		 	
		 	DAOService service=new DAOService();
			User usr=service.updateUser(user.getUserName(),user.getUserPass(),user.getUserName(),user.getUserLastName(),user.getUserAge(),user.getUserSex());	
			
			return usr;			 				          
	    }
	 
	 
	 
}
