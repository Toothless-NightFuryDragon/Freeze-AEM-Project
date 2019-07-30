package com.regnant.freeze.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

@Model(adaptables=Resource.class)
public class Products {
	@Inject
	@Optional
	private Resource multi;
	
	List<productFieldBean> productList;
	
	

 
 public List<productFieldBean> getProductList() {
		return productList;
	}


@PostConstruct
 private void init() {
	 if(multi != null) {
		 productList = new ArrayList<>();
		 Iterator<Resource> listChildren = multi.listChildren();
		 while(listChildren.hasNext()) {
			productFieldBean bean = new productFieldBean();
			Resource resource = listChildren.next();
			ValueMap valueMap = resource.getValueMap();
			bean.setImageURL(valueMap.get("imageURL", String.class));
			bean.setLinkTitle(valueMap.get("linkTitle", String.class));
			productList.add(bean);
		 }
		 
	 }
	 
 }


public class productFieldBean{
	 private String imageURL,linkTitle;

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getLinkTitle() {
		return linkTitle;
	}

	public void setLinkTitle(String linkTitle) {
		this.linkTitle = linkTitle;
	}

	
	 
 }
	
	
}
