package com.regnant.freeze.models;

import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.components.Component;

@Model(adaptables=SlingHttpServletRequest.class, defaultInjectionStrategy=DefaultInjectionStrategy.OPTIONAL)
public class HttpModel {
	
	//Injects currentPage using ScriptVariable Annotation
	@ScriptVariable(name="currentPage") //Mapped to com.day.cq.wcm.api.Page
	@Optional
	@Default(values="defaultvalue")
	//Page page;     // Page API object
	Page myOwn ;
	public String getCurrentPage() {
		//return page.getPath();  //call a method from Page API object 
		return myOwn.getTitle();
	}
	
	
	@ScriptVariable
	@Optional
	Component component;
	public String getComponet() {
		return component.getTitle();
	}
	
	
	
	

}
