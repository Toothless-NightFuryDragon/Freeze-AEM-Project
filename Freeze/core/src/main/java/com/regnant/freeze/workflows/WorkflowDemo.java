package com.regnant.freeze.workflows;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;

@Component(service=WorkflowProcess.class,
property= {
		Constants.SERVICE_VENDOR+"= Freeze",
		Constants.SERVICE_DESCRIPTION+"= A sample workflow",
		"process.label = Simple Workflow"
})
public class WorkflowDemo implements WorkflowProcess {

	@Override
	public void execute(WorkItem item, WorkflowSession session, MetaDataMap arg2) throws WorkflowException {
			Session jcrsession = session.adaptTo(Session.class);
			WorkflowData workflowData = item.getWorkflowData();
			String payloadPath = workflowData.getPayload().toString();
			try {
				Node node = jcrsession.getNode(payloadPath);
				if(node != null) {
					node = node.hasNode("jcr:content") ? node.getNode("jcr:content"):node;
					Property property = node.setProperty("name", "PK");
					jcrsession.save();
				}
			} catch (RepositoryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	

}
