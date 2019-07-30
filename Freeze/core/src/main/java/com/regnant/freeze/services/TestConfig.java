package com.regnant.freeze.services;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "OCD Service")
public @interface TestConfig {
	@AttributeDefinition(name="Namaste",description="This is a demo")
	String getMessage();

}
