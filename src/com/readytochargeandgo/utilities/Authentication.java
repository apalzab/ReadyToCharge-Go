package com.readytochargeandgo.utilities;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.core.Cookie;

import com.sun.jersey.core.util.Base64;


public class Authentication {
	
	private static Authentication auth = null;
	
	private Authentication(){
		
	}

	public static Authentication getAuthenticationInstance() {
		
		if (auth == null)		
			auth = new Authentication();		
		return auth;			
	}

	public boolean authenticate(Cookie cookie) {
		//Looks if the cookie has not expired or if it's a valid cookie		
		if (cookie != null)
		{	
			String [] token = cookie.getValue().split("_", 2);
	        String signature = token[0].toString();
	        String publicPart = token[1].toString();
	        String [] splitedPublicPart = publicPart.split(":");
	        String uuid = splitedPublicPart[0]; 
	        String securityLevel = splitedPublicPart[1];
	        Timestamp cookieTimestamp = new Timestamp(Long.valueOf(splitedPublicPart[2]));        
	        Timestamp expiringTimestamp = new Timestamp(cookieTimestamp.getTime() + 259200000); //3 days
	        String suposedSignature = this.hmacDigest(publicPart, "HmacSHA256");
	       
	        if (cookieTimestamp.before(expiringTimestamp) && signature.equals(suposedSignature))
	        	return true;
	        else return false;
		}
		
		else {
			System.out.println("The cookie is null!");
			return false;
		} 
			
	}
	
	
	
	 public static String hmacDigest(String msg, String algo) {
		 	String keyString = "ReadyToCharge&Go Rocks!!!";
		 	String digest = null;
		    try {
		      SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), algo);
		      Mac mac = Mac.getInstance(algo);
		      mac.init(key);

		      byte[] bytes = mac.doFinal(msg.getBytes("ASCII"));

		      StringBuffer hash = new StringBuffer();
		      for (int i = 0; i < bytes.length; i++) {
		        String hex = Integer.toHexString(0xFF & bytes[i]);
		        if (hex.length() == 1) {
		          hash.append('0');
		        }
		        hash.append(hex);
		      }
		      digest = hash.toString();
		    } catch (UnsupportedEncodingException e) {
		    } catch (InvalidKeyException e) {
		    } catch (NoSuchAlgorithmException e) {
		    }
		    return digest;
		  }
}
