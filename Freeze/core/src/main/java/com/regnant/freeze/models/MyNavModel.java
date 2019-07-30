package com.regnant.freeze.models;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ResourcePath;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

@Model(adaptables = Resource.class)
public class MyNavModel {
	@Inject
	ResourceResolver resolver;

	private List<String> pg;

	public List<String> getPg() {
		return pg;
	}
	public String path;

	@PostConstruct
	private void init() {
		String path = "/content/Freeze-Homepage";
		PageManager pageManager = resolver.adaptTo(PageManager.class);
		Page page = pageManager.getPage(path);
		
		Iterator<Page> listChildren = page.listChildren(null, true);
		System.out.println("listchildren---> "+listChildren.toString());
		
		while (listChildren.hasNext()) {
			Page childPg = listChildren.next();
			System.out.println("child pages inside while loop"+childPg.getName());
		    path = childPg.getPath();
		    
//			String title = childPg.getTitle();
//			MultifieldBean bean = new MultifieldBean();
//			bean.setTitle(path);
//			bean.setLink(title);
//			System.out.println(path+"((((()))))"+title);
//			pg.add(bean);
			pg.add(path);

		}
	}

	public class MultifieldBean {
		private String link, title;

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
