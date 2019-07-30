package com.regnant.freeze.servicesimpl;

import javax.jcr.LoginException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.regnant.freeze.services.SessionService;

@Component(service = SessionService.class, immediate = true)
public class SessionServiceImpl implements SessionService {

	@Reference
	SlingRepository repository;

	@Override
	public Session createSession() {

		Session systemUserSession = null;
		/*
		 * System User --> repository.loginService(subServiceName = SessionService,
		 * workspace) Spl users where they can be linked to a bundle via service,
		 * replaces the admin user service called UserMapping Service
		 */
		try {
			systemUserSession = repository.loginService("SessionService", null);
		} catch (LoginException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		return systemUserSession;
	}

}
