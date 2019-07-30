package com.regnant.freeze.models;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables=Resource.class)

public class TestAdapter {

	
	
	@Inject @Named("sling:resourceType")
	String dada;
	
	public String getDada() {
		return dada;
		
	}
}
