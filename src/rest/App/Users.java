package rest.App;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import com.readytochargeandgo.dao.DAOService;
import com.readytochargeandgo.domainObjects.User;
import com.readytochargeandgo.utilities.Authentication;
import com.readytochargeandgo.utilities.EmailSender;
import com.sun.jersey.core.util.Base64;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;



@Path("/users")
public class Users {	
	 
 
 
	 @GET
	 @Produces("application/json")	
	 
	 	public Response getUser(@Context HttpHeaders headers) {		
		 
		 DAOService service = new DAOService();
		 User user = new User();
		 
		 //If the request only contains auth header it means that is the first time that the client visit us.
		 //Afterwars the request contains a cookie wich value is a SIGNATURE + PUBLIC_PART
		 
			//First we check if the request header contains the Basic Auth
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
	    		  Date date = new Date();
	    		  Timestamp timeStamp = new Timestamp (date.getTime());
	    		  String publicPart = UUID.randomUUID().toString() + ":" + "user" + ":" + timeStamp.getTime();
	    		  String signature = Authentication.hmacDigest(publicPart, "HmacSHA256");
	    		  String token = signature + "_" + publicPart;
	    		  Cookie cook = new Cookie("sessionid", token, "/", headers.getRequestHeader("host").get(0));
	    		  NewCookie cookie = new NewCookie(cook);
	    		  //sends to the client the user and adds sessionid cookie
	    		  return Response.ok(user, MediaType.APPLICATION_JSON).cookie(cookie).build();
	    		  //return Response.status(Status.OK).cookie(new NewCookie("sessionid", token)).build();
		       } else {
		    	   return Response.status(Response.Status.NOT_FOUND).entity("No user found").build();
       			}				
		    	   
		   }
		   
		 	
		 	/*
		 	
		 	//If there's session cookie in the request... 
		   else if (sessionCookie.getValue() != null ) {
			   //no basic auth, looking for the session cookie in order to send back the user data details.

			 //Response.ok(user, MediaType.APPLICATION_JSON).cookie(new NewCookie("sessionid", token)).build();
		   		
		   		if (Authentication.getAuthenticationInstance().authenticate(sessionCookie)) {
		   			service.getUser(requestedUser.getId(), requestedUser.getPass());
		   			System.out.println("The cookie is still 'alive'");
		   			
		   			
		   			//how do we know which user is if we only have the session cookie?
		   			
		   		} else {
		   			System.out.println("The cookie is invalid. The user must reauthenticate");
		   		}
		   		
   				}
   				
   				
   			*/
		return null;
	 
 		}      
	 
	 @POST	 
	 @Consumes("application/json")
	 	public Response postUser(User user) 
	 	{		 	
			 DAOService service = new DAOService();			 	
			 //Looks if the user exists between Users and TemporalUsers
			 if (service.existsUser(user.getId(),"User") || service.existsUser(user.getId(),"TemporalUsers"))
				 return Response.status(200).type("text/plain").entity("ReadyToCharge&Go informa de que el email introducido ya existe en el sistema!").build();
			 
			 else 
			 {	//Creates a new TemporalUser and sends a confirmation email.  			 	
			 	service.newUser(user.getId(),user.getPass(),user.getName(),user.getLastName(),user.getAge(),user.getSex(),"TemporalUsers");			
			 	EmailSender.getEmailSenderInstance().newUserConfirmationEmail(user);
				return Response.status(201).type("text/plain").entity("Se te ha enviado un email de confirmación a la dirección de correo " + user.getId()+ ". ¡Es necesario que revises tu correo y confirmes la solicitud para poder utilizar ReadyToCharge&GO!").build();
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
		 		service.deleteUser(u.getId(), u.getPass(), "TemporalUsers");
		 		String link = "http://readytocharge.appspot.com/";
			 	return Response.status(200).type("text/html").entity("<div><p>Felicidades " +u.getName() + ", hemos creado tu perfil en nuestro sistema. ¡Ya puedes utilizar <a href="+ link +">ReadyToCharge&Go!</a></p></div>").build();
		 	}
		 	else return Response.status(500).type("text/plain").entity("Lo sentimos pero el usuario ya existe").build();		 	
	    }
	 
	 @DELETE	 
	 public Response deleteUser(@Context HttpHeaders headers) 
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
	    		  service.deleteUser(user.getId(),user.getPass(), "User");
	    		  //send to the client the user and adds sessionid cookie
	    		  return Response.status(Response.Status.OK).entity("User deleted from the the system.").build();
	    		  //return Response.status(Status.OK).cookie(new NewCookie("sessionid", token)).build();
		       } else return Response.status(Response.Status.NOT_FOUND).entity("User not deleted.").build();  
		   } else	return Response.status(Response.Status.NOT_FOUND).entity("User not deleted.").build();
		}
	 
	 @PUT
	 @Consumes("application/json")
	 	public User putUser(User user) 
	 	{		 	
		 	DAOService service=new DAOService();
			User usr=service.updateUser(user.getName(),user.getPass(),user.getName(),user.getLastName(),user.getAge(),user.getSex());	
			
			return usr;			 				          
	    }
	 
	 
	 
}
