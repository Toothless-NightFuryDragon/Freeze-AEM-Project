package com.regnant.freeze.models;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.regnant.freeze.services.RegistrationService;

@Component(service= {Servlet.class},
		   property= { 	
				   		ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES+"=",
				   		ServletResolverConstants.SLING_SERVLET_PATHS+"=/bin/register",
				   	   ServletResolverConstants.SLING_SERVLET_NAME+"=Register Servlet",
				   	   ServletResolverConstants.SLING_SERVLET_METHODS+"=POST,GET"
					 }
		  )
public class Registration extends SlingAllMethodsServlet {

	@Reference
	RegistrationService register;
	
	
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		
		System.out.println(name);
		int injectCustData = register.injectCustData(name);
		
		
		
		
	}
}
