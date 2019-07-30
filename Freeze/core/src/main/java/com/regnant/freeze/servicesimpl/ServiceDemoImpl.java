package com.regnant.freeze.servicesimpl;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;

import com.regnant.freeze.services.ServiceDemo;
import com.regnant.freeze.services.TestConfig;

@Component(service = ServiceDemo.class,immediate=true)
@Designate(ocd=TestConfig.class)
public class ServiceDemoImpl implements ServiceDemo {
	
	private String message;
	
	@Override
	public String getMessage() {

		//return "This is Service Demo implemented by ServiceDemoImpl class";
		return message;
	}
	@Activate
	protected void activate(TestConfig testconfig) {
		 message = testconfig.getMessage();
		
	}

}
