package com.readytochargeandgo.utilities;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.readytochargeandgo.dao.DAOService;
import com.readytochargeandgo.domainObjects.User;


public class SecurityFilter implements javax.servlet.Filter{
	   @Override
	   public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter) throws IOException, ServletException {
		   
		   HttpServletRequest req = (HttpServletRequest) request;
		   HttpServletResponse resp = (HttpServletResponse) response;
		   
		   
		     javax.servlet.http.Cookie[] cookie = req.getCookies();
             //comprobar que cookie distinto de null
	         
		     String auth_data = null;
		     boolean ok = false;
		     try{
		    	 auth_data =cookie[0].getValue().toString();
		    	 ok = true;
		     } catch (Exception e) {}  
		     
		   
		     if (ok == true) {
			   if (req instanceof HttpServletRequest) {		         
		         DAOService service = new DAOService();			     
	             String [] user_and_key = auth_data.split(":", 2);
	             String id = user_and_key[0].toString();
	             String pass = user_and_key[1].toString();	           
	             User user = service.getUser(id,pass);	
	             if (user != null) 
	 				filter.doFilter(request, response);
	             else {
	            	 resp.setStatus(403);		        	
		        }         
	     
		      }
			   else resp.setStatus(403);
		   }
		   else {
			   resp.setStatus(403);
		   }
   }
	   
	   @Override
	   public void destroy() {}
	   
	   @Override
	   public void init(FilterConfig arg0) throws ServletException {}	
	
	}