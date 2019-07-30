package com.regnant.freeze.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

@Component(service=Servlet.class,
		   property= {
				   ServletResolverConstants.SLING_SERVLET_NAME+"=NavBar",
				   ServletResolverConstants.SLING_SERVLET_PATHS+"=/bin/nav",
				   ServletResolverConstants.SLING_SERVLET_METHODS+"=GET"
		   })
public class NavServlet extends SlingSafeMethodsServlet {
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		ResourceResolver resourceResolver = request.getResourceResolver();
		PageManager pm = resourceResolver.adaptTo(PageManager.class);
		List<String> arrayList = new ArrayList();
		Page rootPage = pm.getPage("/content/Freeze-Homepage");
		Iterator<Page> listChildrenIterator = rootPage.listChildren(null,true);
		while(listChildrenIterator.hasNext()) {
			Page childPg = listChildrenIterator.next();
			String path = childPg.getPath();
			String title = childPg.getTitle();
			response.getWriter().println(title+" --> "+path);
		}
			
		
		
	}
	
}
