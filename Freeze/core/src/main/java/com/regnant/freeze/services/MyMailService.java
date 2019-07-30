package com.regnant.freeze.services;

import java.util.Map;

import javax.activation.DataSource;
import com.adobe.acs.commons.email.EmailService;


public interface MyMailService {
	public void sendEmail(String[] toAddress, String emailTemplate, Map<String,String> templateAttributes, Map<String,DataSource> attatchments);
	
	
}
