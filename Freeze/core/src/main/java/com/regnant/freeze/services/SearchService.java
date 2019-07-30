package com.regnant.freeze.services;

import org.apache.sling.api.resource.ResourceResolver;

import com.day.cq.search.result.SearchResult;

public interface SearchService {
	public SearchResult getSearchResult(String searchText, ResourceResolver resolver);

}
