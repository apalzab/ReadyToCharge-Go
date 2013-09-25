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
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getId(),user.getName()));
            msg.setSubject("Bienvenido," + user.getName() );

	        Multipart mp = new MimeMultipart();
	        MimeBodyPart htmlPart = new MimeBodyPart();
	        
	        String link="http://rtcandg.appspot.com";
	        String userid = user.getId();
	        String l = "http://readytocharge.appspot.com/app/Users/" + userid;
	        String a = user.getName().toUpperCase() + ", bienvenido a ReadyToCharge&Go!!!" + "<p>Haz click en el botón para confirmar el alta en el sistema</p>" + "<form name='input' action='" + l + "' method='post'> <input type='submit' value='Confirmar mi cuenta'></form>";
	        
	        htmlPart.setContent(a, "text/html");
	        mp.addBodyPart(htmlPart);
	        msg.setContent(mp);            
            Transport.send(msg);	    
	        } 	    	
	    catch (AddressException e) {} 
	    catch (MessagingException e) {} 
	    catch (UnsupportedEncodingException e) {}
	}
	
	
	
}
