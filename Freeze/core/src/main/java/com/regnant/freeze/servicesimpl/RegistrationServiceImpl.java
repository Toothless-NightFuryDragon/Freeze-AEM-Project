package com.regnant.freeze.servicesimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.commons.datasource.poolservice.DataSourceNotFoundException;
import com.day.commons.datasource.poolservice.DataSourcePool;
import com.regnant.freeze.db.ConnectionHelper;
import com.regnant.freeze.services.RegistrationService;

@Component(service = RegistrationService.class, immediate = true)

public class RegistrationServiceImpl implements RegistrationService {

	protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Reference
	private DataSourcePool source;

	// Returns a connection using the configured DataSourcePool
	private Connection getConnection() {
		DataSource dataSource = null;
		Connection con = null;
		System.out.println("source" + source);
		// Inject the DataSourcePool right here!
		try {
			dataSource = (DataSource) source.getDataSource("customer");
			LOGGER.debug("datasource {}" , dataSource);
			con = dataSource.getConnection();
			System.out.println("con" + con);
			return con;
		} catch (DataSourceNotFoundException e) {
			LOGGER.error("RegistrationService getConnection method {}", e.getMessage());
		} catch (SQLException e) {
			LOGGER.error("RegistrationService getConnection method {}", e.getMessage());
		}

		return null;
	}

	@Override
	public int injectCustData(String name) {
		Connection c = null;

		try {
			System.out.println("regimpl 1 :::::" + name);

			// Create a Connection object
			// c = ConnectionHelper.getConnection();
			// System.out.println(c.toString());

			 c = getConnection();
			// Use prepared statements to protected against SQL injection attacks
			System.out.println("above ps" + c.toString());
			PreparedStatement ps = null;

			System.out.println("regimpl 2:::::" + name);

			String insert = "INSERT INTO \"Explore\".\"Demo\" VALUES(?);";

			ps = c.prepareStatement(insert);
			ps.setString(1, name);
			ps.execute();
			return 0;
		} catch (Exception e) {
			System.out.println("Exception 2" + e);
		}
	       finally {
	    	   try
		         {
	    	   if(null != c && !c.isClosed())
	        
	           c.close();
	         }
	         
	           catch (SQLException e) {
	             e.printStackTrace();
	           }
	   }
		return 0;
	}

	@Override
	public String getCustData(String filter) {
//		Connection c = null;
//	       int rowCount= 0; 
//	       customer cust = null; 
//	       String query = "";
//	       List<Customer> custList = new ArrayList<Customer>();
//	       try {
//	                                   
//	             // Create a Connection object
//	             c =  getConnection();
//	          
//	              ResultSet rs = null;
//	              Statement s = c.createStatement();
//	              Statement scount = c.createStatement();
//	                
//	              //Use prepared statements to protected against SQL injection attacks
//	              PreparedStatement pstmt = null;
//	              PreparedStatement ps = null; 
//	                          
//	              //Set the query and use a preparedStatement
//	              if (filter.equals("All Customers"))
//	                  query = "Select custId,custFirst,custLast,custAddress,custDesc FROM Customer";
//	              else if(filter.equals("Past Customer"))
//	                  query = "Select custId,custFirst,custLast,custAddress,custDesc FROM Customer where custDesc = 'Past Customer'; ";
//	              else if(filter.equals("Active Customer"))
//	                  query = "Select custId,custFirst,custLast,custAddress,custDesc FROM Customer where custDesc = 'Active Customer'; ";
//	               
//	              pstmt = c.prepareStatement(query); 
//	              rs = pstmt.executeQuery();
//	                
//	              while (rs.next()) 
//	              {
//	                  //for each pass - create a Customer object
//	                  cust = new Customer(); 
//	                   
//	                  //Populate customer members with data from MySQL
//	                  int custId = rs.getInt(1);
//	                  String id = Integer.toString(custId);
//	                  cust.setCustId(id); 
//	                   
//	                  cust.setCustFirst(rs.getString(2));
//	                  cust.setCustLast(rs.getString(3));
//	                  cust.setCustAddress(rs.getString(4));
//	                  cust.setCustDescription(rs.getString(5));
//	                   
//	                //Push Customer to the list
//	                custList.add(cust);
//	                    
//	              }             
//	              //return xml that contains all customer taken from MySQL
//	              return convertToString(toXml(custList));               
//	               
//	       }
//	   catch(Exception e)
//	       {
//	        e.printStackTrace();
//	       }
		return null;
	}

}
