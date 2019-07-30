 package com.regnant.freeze.servlets;

import java.io.IOException;

import javax.inject.Named;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.models.factory.ModelFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

//import com.regnant.freeze.models.InterfaceModel;
import com.regnant.freeze.models.TestAdapter;

@Component(service=Servlet.class,
		 property= {
			ServletResolverConstants.SLING_SERVLET_PATHS+"=/bin/model",
			ServletResolverConstants.SLING_SERVLET_NAME+"=Model Servlet",
			ServletResolverConstants.SLING_SERVLET_METHODS+"= GET",
			ServletResolverConstants.SLING_SERVLET_EXTENSIONS+"=txt"
		 }
		 )
public class ModelServlet extends SlingSafeMethodsServlet{
	@Reference
	ModelFactory mf;
	
	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().print("Servlet called ");
		Resource resource = request.getResourceResolver().getResource("/content/Freeze-Homepage/jcr:content");
//		TestAdapter test = resource.adaptTo(TestAdapter.class);
//		response.getWriter().print(mf.canCreateFromAdaptable(resource, TestAdapter.class));
//		response.getWriter().print(test.getType());
		
		TestAdapter test = mf.createModel(resource, TestAdapter.class);
		response.getWriter().println(mf.canCreateFromAdaptable(resource, TestAdapter.class));
		response.getWriter().println(mf.canCreateFromAdaptable(request, TestAdapter.class));
		
		//response.getWriter().println(test.getType());
		
		

	}

}
