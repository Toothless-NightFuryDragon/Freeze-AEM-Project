package com.regnant.freeze.servicesimpl;

import java.util.HashMap;

import javax.jcr.Session;

import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.regnant.freeze.services.SearchService;

@Component(service=SearchService.class)
public class SearchServiceImpl implements SearchService{

	@Override
	public SearchResult getSearchResult(String searchText, ResourceResolver resolver) {
		
		QueryBuilder queryBuilder = resolver.adaptTo(QueryBuilder.class);
				
		HashMap<String, String> queryMap = new HashMap<String,String>();
		queryMap.put("type", "cq:PageContent");
		queryMap.put("path", "/content");
		queryMap.put("property","navTitle");
		queryMap.put("property.value", "Home");
		queryMap.put("p.limit", "-1");
		
		Query query = queryBuilder.createQuery(PredicateGroup.create(queryMap), resolver.adaptTo(Session.class));
		SearchResult result = query.getResult();
		if(result != null) {	
		return result;
		}
		else {
			return null;
		}
	}
	
	

}
