package com.regnant.freeze.utils;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Session;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.Externalizer;

public final class Utility {
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	private Utility() {
		LOG.error("This is an utility class which is intended to be instantiated");
	}
	
	@Reference
	private static Externalizer externalizer;
	
	public static ResourceResolver getResourceResolver(Session session, ResourceResolverFactory resolverFactory) throws LoginException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user.jcr.session", session);
		return resolverFactory.getResourceResolver(map);
	}
}
