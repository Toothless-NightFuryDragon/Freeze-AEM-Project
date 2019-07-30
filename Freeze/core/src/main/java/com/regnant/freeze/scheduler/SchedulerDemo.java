package com.regnant.freeze.scheduler;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.regnant.freeze.services.TestConfig;

@Component(service=Runnable.class,immediate=true)
@Designate(ocd= SchedulerDemo.Configuration.class)
public class SchedulerDemo implements Runnable {
	Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public void run(){
		log.info("Scheduler is called");
		
	}
	
	@Activate
	protected void activate(Configuration config) {
		log.info("Inside Activate");
	}
	
	@ObjectClassDefinition(name = "Scheduler Service")
	public @interface Configuration{
		//@AttributeDefinition(name="CRON Expressions")
		//String scheduler_expression() default "* * * ? * *";
	}

}
