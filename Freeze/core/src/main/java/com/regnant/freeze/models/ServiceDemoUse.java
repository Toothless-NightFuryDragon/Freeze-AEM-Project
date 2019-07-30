package com.regnant.freeze.models;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.regnant.freeze.services.ServiceDemo;

@Component(service= {Servlet.class},
property= {
		ServletResolverConstants.SLING_SERVLET_PATHS+"=/bin/service",
		ServletResolverConstants.SLING_SERVLET_NAME+"=Service Demo",
		ServletResolverConstants.SLING_SERVLET_METHODS+"=GET"
})
public class ServiceDemoUse extends SlingSafeMethodsServlet {

	@Reference
	ServiceDemo service;
	
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		String message = service.getMessage();
		response.getWriter().write(message);
		
	}
}
