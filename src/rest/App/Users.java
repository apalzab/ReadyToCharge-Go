package rest.App;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import com.readytochargeandgo.dao.DAOService;
import com.readytochargeandgo.domainObjects.User;
import com.readytochargeandgo.utilities.EmailSender;


@Path("/Users")
public class Users {	
	 
 @Context
 private HttpServletRequest request;
	
	
	 @GET
	 @Produces("application/json")	 	
	 	public User getUser()
 		{			 
		 	DAOService service = new DAOService();
		 	
		 	javax.servlet.http.Cookie[] cookie = request.getCookies();
            String auth_data =cookie[0].getValue();
            String [] user_and_key = auth_data.split(":", 2);
            String id = user_and_key[0].toString();
            String pass = user_and_key[1].toString();
            
			User user=service.getUser(id, pass);	
		
		return user;
 		}      
	 
	 @POST	 
	 @Consumes("application/json")
	 	public Response postUser(User user) 
	 	{		 	
			 DAOService service = new DAOService();			 	
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
	 
	 
	 @POST
	 @Path("/{userId}")
	 	public Response postUser(@PathParam("userId") String userId) 
	 	{		 	
		 	DAOService service = new DAOService();
		 	
		 	if (!service.existsUser(userId,"User"))
		 	{		 	
		 		User u = service.getTemporalyUser(userId);
		 		service.newUser1(u);
		 		service.deleteUser(u.getUserId(), u.getUserPass(), "TemporalUsers");
		 		String link = "http://rtcandg.appspot.com/";
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
