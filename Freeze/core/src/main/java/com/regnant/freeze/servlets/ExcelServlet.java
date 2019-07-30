package com.regnant.freeze.servlets;

import java.io.IOException;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.rmi.ServerException;
import java.util.Iterator;
import java.util.Map;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

@Component(service=Servlet.class,
property= {
		   ServletResolverConstants.SLING_SERVLET_NAME+"=Excel",
		   ServletResolverConstants.SLING_SERVLET_PATHS+"=/bin/excel",
		   ServletResolverConstants.SLING_SERVLET_METHODS+"=GET, POST"
})
public class ExcelServlet extends SlingAllMethodsServlet {
	@Reference
	private Session session;
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	private ResourceResolver serviceResourceResolver;
	private Node productNode;
	private Node custNode;
	private Node nodeAdd;
	private Node nodeAdd1;
	private Node node2;
	private Sheet sheet;
	private Node content;
	private int var;
	
	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		try {
		ResourceResolver serviceResourceResolver = request.getResourceResolver();
		session = serviceResourceResolver.adaptTo(Session.class);
		content = session.getNode("/content");
		if (content.hasNode("merckproductdetails")) {
			content.getNode("merckproductdetails").remove();
		}
		}catch (RepositoryException e) {
		e.printStackTrace();
	}

	try {
		final boolean isMultipart = org.apache.commons.fileupload.servlet.ServletFileUpload.isMultipartContent(request);
		PrintWriter out = null;
		out = response.getWriter();
		if (isMultipart) {
			final Map<String, org.apache.sling.api.request.RequestParameter[]> params = request.getRequestParameterMap();
			for (final Map.Entry<String, org.apache.sling.api.request.RequestParameter[]> pairs : params.entrySet()) {
				final String k = pairs.getKey();
				final org.apache.sling.api.request.RequestParameter[] pArr = pairs.getValue();
				final org.apache.sling.api.request.RequestParameter param = pArr[0];
				final InputStream stream = param.getInputStream();
				int excelValue = injectSpreadSheet(stream);
				if (excelValue == 0)
					out.println("Excel Spread Sheet has been successfully imported into the AEM JCR");
				else
					out.println("Excel Spread Sheet could not be imported into the AEM JCR");
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	}

public int injectSpreadSheet(InputStream is) {
	try {
		Workbook workbook = Workbook.getWorkbook(is);
		sheet = workbook.getSheet(0);
		String products = "";
		String packs = "";
		for (int index = 1; index < sheet.getColumns(); index++) {
			Cell a1 = sheet.getCell(index, 0);
			Cell b1 = sheet.getCell(index, 1);
			if (!(a1.getContents().isEmpty())) {
				products = a1.getContents();
			}
			packs = b1.getContents();
			injestCustData(products, packs);
		}
		return 0;
	} catch (Exception e) {
		e.printStackTrace();
	}
	return -1;
}

public int injestCustData(String products, String packs) {
	try {
		Node customerRoot = null;
		int custRec = doesNodeExist(content);
		if (custRec == -1) {
			customerRoot = content.addNode("merckproductdetails");
			productNode = customerRoot.addNode("Products");
		} else {
			customerRoot = content.getNode("merckproductdetails");
			productNode = customerRoot.getNode("Products");
		}
		int custId = custRec + 1;
		log.info("node names" + packs);
		if (!(productNode.hasNode(products))) {
			custNode = productNode.addNode(products, "nt:unstructured");
			nodeAdd = custNode.addNode(packs, "nt:unstructured");
			addProp(nodeAdd);
		} else {
			Node node = session.getNode(custNode.getPath());
			node2 = node.addNode(packs, "nt:unstructured");
			addProp(node2);
		}
		session.save();
		return custId;
	} catch (Exception e) {
		log.error("RepositoryException: " + e);
	}
	return 0;
}

private void addProp(Node nodeAdd2) throws RepositoryException {
	Cell cell = null;
	try {
		cell = sheet.findCell(nodeAdd2.getParent().getName());
	} catch (RepositoryException e1) {
		e1.printStackTrace();
	}
	int column = cell.getColumn();
	Cell cell2 = sheet.getCell(column, 1);
	Cell cell3 = sheet.getCell(column + 1, 1);
	if (cell3.getContents().equals(nodeAdd2.getName())) {
		var = cell3.getColumn();
	} else if (cell2.getContents().equals(nodeAdd2.getName())) {
		var = cell2.getColumn();
	}
	for (int index = 0; index < sheet.getRows() - 2; index++) {
		Cell a1 = sheet.getCell(0, index + 2);
		Cell b3 = sheet.getCell(var, index + 2);
		String sellerNa = a1.getContents();
		String path = b3.getContents();
		try {
			nodeAdd2.setProperty(sellerNa, path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

@SuppressWarnings("unused")
private int doesNodeExist(Node content) {
	try {
		int childRecs = 0;
		java.lang.Iterable<Node> custNode = JcrUtils.getChildNodes(content, "merckproductdetails");
		Iterator<Node> it = custNode.iterator();
		if (it.hasNext()) {
			Node productRoot = content.getNode("merckproductdetails");
			Iterable<Node> itCust = JcrUtils.getChildNodes(productRoot);
			Iterator<Node> childNodeIt = itCust.iterator();
			while (childNodeIt.hasNext()) {
				childRecs++;
				childNodeIt.next();
			}
			return childRecs;
		} else
			return -1;
	} catch (Exception e) {
		e.printStackTrace();
	}
	return 0;
}


}
