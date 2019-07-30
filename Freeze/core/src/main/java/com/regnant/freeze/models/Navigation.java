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
public class Navigation {

	@Inject
	@Optional
	private Resource navitems;
	
	private List<MultifieldBean> navItems;
	
	public List<MultifieldBean> getNavItems() {
		return navItems;
	}



	@PostConstruct
	private void init() {
		if(navitems != null) {
			navItems = new ArrayList<>();
			Iterator<Resource> listChildren = navitems.listChildren();
			while(listChildren.hasNext()) {
				MultifieldBean bean = new MultifieldBean();
				Resource resource = listChildren.next();
				ValueMap valueMap = resource.getValueMap();
				bean.setTitle(valueMap.get("linkTitle",String.class));
				bean.setLink(valueMap.get("linkURL",String.class));
				navItems.add(bean);				
			}
		}
	}
	
	public class MultifieldBean{
		private String link,title;

		public String getLink() {
			return link;
		}

		public void setLink(String link) {
			this.link = link;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}
	}
}
