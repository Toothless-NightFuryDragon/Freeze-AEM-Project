package com.regnant.freeze.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.regnant.freeze.services.MyMailService;


@Component(service  = Servlet.class,
		   property = { ServletResolverConstants.SLING_SERVLET_PATHS+"=/bin/email",
				     	ServletResolverConstants.SLING_SERVLET_NAME+"=Email Demo",
				     	ServletResolverConstants.SLING_SERVLET_METHODS+"=GET , POST"
					  }
		)
public class EmailDemo extends SlingAllMethodsServlet{

	@Reference
	MyMailService emailService;
	
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {

		doPost(request,response);
	}
	
	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().write("Request Received");
		System.out.println("Request Received to servlet");
		String templatePath = "/etc/notification/email/default/com.regnant.freeze-templates/emaildemo.html";
		String[] emailIds = {"k.prahitha@gmail.com","k.prahitha@yahoo.com"};
		Map<String,String> templateAttributes = new HashMap<String,String>();
		templateAttributes.put("ccrecipient", "iamthepkness@gmail.com");
		templateAttributes.put("recipientName", "User");
		templateAttributes.put("message", "Hello There !!");
		
		emailService.sendEmail(emailIds, templatePath, templateAttributes, null);
		response.getWriter().write("Success");
		System.out.println("Success");
	}
}


