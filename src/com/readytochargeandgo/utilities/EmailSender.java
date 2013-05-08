package com.readytochargeandgo.utilities;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import com.readytochargeandgo.domainObjects.User;


public class EmailSender {

	
private static EmailSender emailSender=null;
	
	private EmailSender(){
		
	}

	public static EmailSender getEmailSenderInstance() {
		if (emailSender == null)		
			emailSender = new EmailSender();		
		return emailSender;			
	}
	
	
	
	
	
	public void newUserConfirmationEmail(User user)
	{	

		
        
		
		Properties props = new Properties();
	    Session session = Session.getDefaultInstance(props, null);
	    
	   
	   
	    
	    
	    
	   
	    
	    
	    
	    
	    
	    
	    
	    try {
	    	
	    	 
		    Message msg = new MimeMessage(session);	    
		    msg.setFrom(new InternetAddress("readytochargeandgo@gmail.com", "ReadyToCharge&Go email delivery"));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getUserId(),user.getUserName()));
            msg.setSubject("Bienvenido," + user.getUserName() );

	      

	        Multipart mp = new MimeMultipart();

	        MimeBodyPart htmlPart = new MimeBodyPart();
	        
	         
	       String request= "http://readytocharge.appspot.com/app/Users/" + user.getUserId();
	 	   String htmlBody = user.getUserName().toUpperCase() + ", bienvenido a ReadyToCharge&Go!!!" +	 			   			
	 			  "<p>Haz click en el link para confirmar el alta en el sistema:</p>"+
	 			   "<a href=" + request + ">??Darme de alta!</a>";


	        
	        
	        
	        htmlPart.setContent(htmlBody, "text/html");
	        mp.addBodyPart(htmlPart);


	        msg.setContent(mp);
	    	
	    	
	    	
            
                        
            Transport.send(msg);	    
	        } 	    	
	    catch (AddressException e) {} 
	    catch (MessagingException e) {} 
	    catch (UnsupportedEncodingException e) {}
	}
	
	
	
}
