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

import com.sun.jersey.core.util.Base64;



public class SecurityFilter implements javax.servlet.Filter{
	   @Override
	   public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter) throws IOException, ServletException {
		   
		   HttpServletResponse res = (HttpServletResponse) response;
		   
		   if (request instanceof HttpServletRequest)
		   {
	         HttpServletRequest req = (HttpServletRequest) request;
	         String credStr = req.getHeader("authorization");
	         if (null != credStr) 	         
	         {	           
	        	 User user = null;	 
	        	 
	        	 String basic_prefix = "Basic ";
		         if (credStr.startsWith(basic_prefix)) 
		         {
		             String auth_data = new String(Base64.decode(credStr.substring(basic_prefix.length())));
		             String [] user_and_key = auth_data.split(":", 2);
		             String id = user_and_key[0].toString();
		             String pass = user_and_key[1].toString();
		             
		             
		            DAOService service = new DAOService();
		            user = service.getUser(id,pass);	
		 			if (user != null) 
		 				filter.doFilter(request, response);	            
		          
			        else 
			        	
			        {
			        	if (response instanceof HttpServletResponse) 			        
			        	{
			        		
			        		res.setStatus(403);
			        	}
			        }
		         }
		         else res.setStatus(403);
	         }
	         else res.setStatus(403);
	      }
		   else res.setStatus(403);
	   }
	   
	   @Override
	   public void destroy() {}
	   
	   @Override
	   public void init(FilterConfig arg0) throws ServletException {}	
	
	}