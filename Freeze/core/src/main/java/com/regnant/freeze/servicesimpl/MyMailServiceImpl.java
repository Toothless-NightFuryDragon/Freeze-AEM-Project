package com.regnant.freeze.servicesimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataSource;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.UnsupportedRepositoryOperationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.Group;
import org.apache.jackrabbit.api.security.user.User;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.acs.commons.email.EmailService;
import com.regnant.freeze.services.MyMailService;
import com.regnant.freeze.utils.Utility;

@Component(service=MyMailService.class, immediate=true)
public class MyMailServiceImpl implements MyMailService {
	
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	private static final String PROFILE = "profile";
	private static final String EMAIL = "email";
	private static final String Freeze_COMMERCE_SUB_SERVICE = "SessionService";
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@+[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	@Reference
	EmailService acsEmailService;
		
	@Reference
	ResourceResolverFactory factory;
	
	@Reference
	SlingRepository repository;

	@Override
	public void sendEmail(String[] authorizables, String emailTemplate, Map<String, String> templateAttributes,
			Map<String, DataSource> attatchments) {
	System.out.println("In send email method ");	
		ResourceResolver resourceResolver = null;
		Session session = null;
		try {
			/*
			 * Now the new methods are introduced to replace loginAdministrative methods:
			1. ResourceResolver getServiceResourceResolver(Map<String, Object> authenticationInfo) throws LoginException;
			2. Session loginService(String serviceInfo, String workspace) throws LoginException, RepositoryException;

			Note: Each bundle using the ResourceResolverFactory or SlingRepository service 
      				actually gets an instance bound to the using bundle.
        		  That bundle is used to identify the service.
			 */
			//session = repository.loginService(Freeze_COMMERCE_SUB_SERVICE, null);
			//resourceResolver = Utility.getResourceResolver(session, factory);
			//userService
			
			Map<String, Object> param = new HashMap<>();
			param.put(ResourceResolverFactory.SUBSERVICE, "userService");
			resourceResolver = factory.getServiceResourceResolver(param);
			
			if( (authorizables != null && authorizables.length>0) && StringUtils.isNotBlank(emailTemplate) && templateAttributes!= null) {
				log.debug("Send Email Triggered");
				System.out.println("Send Email Triggered ");
//				String[] mailIds = getAllMailIds(authorizables, resourceResolver);
//				System.out.println("Mail Ids ="+mailIds);
				if(attatchments != null) {
				log.debug("Email has Attatchment");
				System.out.println("Email has attachment.");
//				acsEmailService.sendEmail(emailTemplate, templateAttributes, attatchments, mailIds);
				acsEmailService.sendEmail(emailTemplate, templateAttributes, attatchments, authorizables);
				} else {
//					acsEmailService.sendEmail(emailTemplate, templateAttributes, mailIds);
					acsEmailService.sendEmail(emailTemplate, templateAttributes, authorizables);
					System.out.println("Email doesnt have attachment & mail is sent.");
				}
			}
			
		} //catch (LoginException e) {
			//e.printStackTrace();
	        //	} 
//	catch (RepositoryException e) {
//			e.printStackTrace();
//		}
		catch (org.apache.sling.api.resource.LoginException e) {
			e.printStackTrace();
		}
		
	}
	
	
//	private String[] getAllMailIds(String[] toAddress,ResourceResolver systemUserResolver) throws RepositoryException {
//		List<String>  emailIds = new ArrayList<String>();
//		UserManager userManager = systemUserResolver.adaptTo(UserManager.class);
//		if(userManager != null) {
//			for(String authorizable : toAddress) {
//				if(null != userManager.getAuthorizable(authorizable)) {
//				Authorizable userOrGroup = userManager.getAuthorizable(authorizable);
//				if (userOrGroup instanceof User) {
//					User user = (User) userOrGroup;
//					log.debug("The authorizable : {} is User.",user.getID());
//					System.out.println("The authorizable : {} is User."+user.getID());
//					emailIds.addAll(addEmailProperty(userOrGroup,systemUserResolver));
//				}else if(userOrGroup instanceof Group) {
//					Group group = (Group) userOrGroup;
//					log.debug("The authorizable : {} is Group.",group.getID());
//					System.out.println("The authorizable : {} is Group."+group.getID());
//					Iterator<Authorizable> declaredMembers = group.getDeclaredMembers();
//					while(declaredMembers.hasNext()) {
//						Authorizable nextUser = declaredMembers.next();
//						emailIds.addAll(addEmailProperty(nextUser,systemUserResolver));
//					}
//				}else {
//					Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
//					Matcher matcher = emailPattern.matcher(authorizable);
//					if(matcher.matches()) {
//						log.debug("Pattern Matched for Email-Id",authorizable);
//						System.out.println("Pattern Matched for Email-Id"+authorizable);
//						emailIds.add(authorizable);
//					}
//				}
//				}
//			}
//		}
//		return emailIds.toArray(new String[emailIds.size()] );
//	}
//
//	
//	private List<String> addEmailProperty(Authorizable authorizable, ResourceResolver systemUserResolver) throws UnsupportedRepositoryOperationException, RepositoryException{
//		List<String> ids = new ArrayList<String>();
//		if(authorizable instanceof User) {
//			User user = (User) authorizable;
//			Resource userResource = systemUserResolver.resolve(user.getPath());
//			if(null != userResource.getChild(PROFILE)) {
//				Resource profile = userResource.getChild(PROFILE);
//				if(null != profile) {
//					ValueMap profileProperties = profile.getValueMap();
//					if(profileProperties.containsKey(EMAIL)) {
//						ids.add(profileProperties.get(EMAIL,String.class));
//			
//					}
//				}
//			}
//		}
//		return ids;
//	}
}
