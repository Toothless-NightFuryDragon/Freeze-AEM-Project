package com.regnant.freeze.servlets;


import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.day.cq.search.result.SearchResult;
import com.google.gson.Gson;
import com.regnant.freeze.services.SearchService;

@Component(service=Servlet.class,
			property= {ServletResolverConstants.SLING_SERVLET_PATHS+"=/bin/search",
					   ServletResolverConstants.SLING_SERVLET_NAME+"=Search Servlet",
					   ServletResolverConstants.SLING_SERVLET_METHODS+"= GET,POST"
						})
public class SearchServlet extends SlingAllMethodsServlet{
	@Reference
	SearchService ss;
	
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().println("In search Servlet");
		 String input = request.getParameter("search");
			response.getWriter().println("search = "+input);
			SearchResult searchResult = ss.getSearchResult(input, request.getResourceResolver());
			Iterator<Resource> resource = searchResult.getResources();
			HashMap<String,String> hmap=new HashMap<>();
			Gson gson = new Gson();
				while(resource.hasNext()) {
					Resource next = resource.next();
					//next.getName()+"*"+next.getPath();

					hmap.put(next.getName(), next.getPath());
					
				}
				String json = gson.toJson(hmap);
				response.getWriter().println(json);
				
				
				
			

	}

}
