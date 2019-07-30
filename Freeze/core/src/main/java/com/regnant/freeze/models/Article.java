package com.regnant.freeze.models;

import java.io.IOException;

import javax.annotation.Resource;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.models.annotations.Model;
import org.osgi.service.component.annotations.Component;

@Component(service= {Servlet.class},
property= {
		ServletResolverConstants.SLING_SERVLET_PATHS+"=/bin/node",
		ServletResolverConstants.SLING_SERVLET_NAME+"=Node Servlet",
		ServletResolverConstants.SLING_SERVLET_METHODS+"=GET"
		
})
public class Article extends SlingAllMethodsServlet{
	
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
			String parameter = request.getParameter("path");
			if(parameter != null) {
				Session session = request.getResourceResolver().adaptTo(Session.class);
				try {
					Node node = session.getNode(parameter); 
					
					if(node != null) {
						PropertyIterator properties = node.getProperties();
						while(properties.hasNext()) {
						 Value value = properties.nextProperty().getValue();
						 response.getWriter().write("property value "+value.toString()+"/n");
						}
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
	}
	
@Override
protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
		throws ServletException, IOException {
	
	String parameter = request.getParameter("path");
	String name = request.getParameter("name");
	String value = request.getParameter("value");
	if(parameter != null) {
		Session session = request.getResourceResolver().adaptTo(Session.class);
		Node node;
		try {
			node = session.getNode(parameter);
			if(node != null) {
				Property setProperty = node.setProperty(name, value);
				session.save();
				response.getWriter().write("success");
			}
		} catch (PathNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
}
