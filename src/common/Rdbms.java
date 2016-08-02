package common;

import java.sql.*;
import java.util.Properties;

/**
 * 
 * @author Brijesh Chavda
 */
public class Rdbms {
	Connection conexion1;
	Connection conexion2;
	
	private boolean isactive;
	private static Rdbms rdbms;

	private Rdbms(String propertyfile) {
		try {
			//InputStream fis = ClassLoader.getSystemResourceAsStream(propertyfile);
			//Properties prop = new Properties();
			//prop.load(fis);
			Properties prop = Util.loadPropertiesFile(propertyfile);
			
			String host1 = prop.getProperty("HOST1");
			String db1 = prop.getProperty("DATABASE1");
			String user1 = prop.getProperty("USER1");
			String password1 = prop.getProperty("PASSWORD1");
			String host2 = prop.getProperty("HOST2");
			String db2 = prop.getProperty("DATABASE2");
			String user2 = prop.getProperty("USER2");
			String password2 = prop.getProperty("PASSWORD2");
			String url = prop.getProperty("DRIVER");
			if ((host1.length() == 0) || (db1.length() == 0)
					|| (user1.length() == 0) || (password1.length() == 0) || (url.length() == 0)) {
				conexion1 = null;
				System.out
						.println("Please check your database property files, it has some invalid parameters.");
				isactive = false;
			} else {
				Class.forName(url).newInstance();
				String connectionurl1 = "jdbc:sqlserver://" + host1 + ";databaseName=" + db1; // a JDBC url
				conexion1 = DriverManager.getConnection(connectionurl1, user1, password1);
				System.out.println("ODS Database is connected successfully.");
				isactive = true;
			}
			if ((host2.length() == 0) || (db2.length() == 0)
					|| (user2.length() == 0) || (password2.length() == 0) || (url.length() == 0)) {
				conexion2 = null;
				System.out
						.println("Please check your database property files, it has some invalid parameters.");
				isactive = false;
			} else {
				Class.forName(url).newInstance();
				String connectionurl2 = "jdbc:sqlserver://" + host2 + ";databaseName=" + db2; // a JDBC url
				conexion2 = DriverManager.getConnection(connectionurl2, user2, password2);
				System.out.println("GDW Database is connected successfully.");
				isactive = true;
			}
		} catch (ClassNotFoundException cex) {
			System.out.println("Library is not loaded correctly.. "
					+ cex.getMessage());
			conexion1 = null;
			conexion2 = null;
			isactive = false;
		//} catch (java.net.SocketException sock) {
		//	System.out.println("Check your HOST URL. " + sock.getMessage());
		//	conexion1 = null;
		//	conexion2 = null;
		//	isactive = false;
		} catch (Exception err) {
			System.out.println("Error occured during database connection. "
					+ err.getMessage());
			conexion1 = null;
			conexion2 = null;
			isactive = false;
		}
	}
	
	public Rdbms(Properties dbPropertyFile,String jobName,String testStatus,String errorDesc,String environment) throws Exception {
		
		
		Connection conn;
		Statement stmt=null;
		try{
		String host = dbPropertyFile.getProperty("HOST");
		String port	=dbPropertyFile.getProperty("PORT");
		String db = dbPropertyFile.getProperty("DATABASE");
		String user = dbPropertyFile.getProperty("USER");
		String password = dbPropertyFile.getProperty("PASSWORD");
		String url = dbPropertyFile.getProperty("DRIVER");
		
		if ((host.length() == 0)  || (port.length() == 0)|| (db.length() == 0)
				|| (user.length() == 0) || (password.length() == 0) || (url.length() == 0)) {
			conn = null;
			System.out.println("Please check your database property files, it has some invalid parameters.");
			isactive = false;
		} else {
			Class.forName(url).newInstance();
			String connectionurl = "jdbc:mysql://"+host+":"+port+"/"+db; // a JDBC url
			conn = DriverManager.getConnection(connectionurl, user, password);
			System.out.println("ODS Database is connected successfully.");
			isactive = true;
			conn.setAutoCommit(false);

			try{
				String [] queries;
				
				int TC_ID=0;
				int TE_ID=0;
				int ER_ID=0;
				String errorDescription=null;
				
				if(errorDesc!=null)
				errorDescription = errorDesc.replace("'", "");   
				
				stmt = conn.createStatement();
				String getTestCaseID ="Select TC_ID from `test_master` where TEST_SCRIPT='"+jobName+"'";
				
				ResultSet rs=stmt.executeQuery(getTestCaseID); 
				
				while (rs.next()){
				TC_ID=rs.getInt("TC_ID");
				}
				System.out.println("Fetched Test Case ID  from test_master table:"+TC_ID);    

				if(testStatus.equalsIgnoreCase("FAILED")) {
				
				queries = new String[] {
						
							"INSERT INTO test_execution (ENVIRONMENT,TE_STATUS, TC_ID) " +
							"SELECT '"+environment+"', '"+testStatus+"', '"+TC_ID+"'",
							
							"INSERT INTO test_errors (ERROR_DETAILS, TC_ID, TE_ID) " +
							"SELECT '"+errorDescription+"', '"+TC_ID+"', Max(TE_ID) from test_execution",
																
							"INSERT INTO test_results (TR_STATUS, TC_ID, ER_ID, TE_ID) " +
							"SELECT '"+testStatus+"', '"+TC_ID+"', Max(er.ER_ID), Max(te.TE_ID)  from `test_errors` er CROSS JOIN `test_execution` te"
							};
				
				}else{
					
					queries = new String[] {
							
							"INSERT INTO test_execution (ENVIRONMENT,TE_STATUS, TC_ID) " +
							"SELECT '"+environment+"', '"+testStatus+"', '"+TC_ID+"'",
									
							"INSERT INTO test_results (ER_ID,TR_STATUS, TC_ID, TE_ID) " +
							"SELECT '0', '"+testStatus+"', '"+TC_ID+"', Max(TE_ID) from `test_execution`"
						};
				}
				
				for (String query : queries){ 
					
					stmt.addBatch(query); 
					System.out.println("Queries to be inserted:" +query);
				}
					stmt.executeBatch(); 
					conn.commit();

					stmt.close(); 
					conn.close(); 
					System.out.println("Tables inserted successfully");
			          
				  }
			  catch (SQLException s){
				  System.out.println("SQL statement is not executed hence rolling back the transaction!");
				  conn.rollback();

			  }

			}
		}
		catch (ClassNotFoundException cex) {
			System.out.println("Library is not loaded correctly.. "
					+ cex.getMessage());
			
			conn = null;
			isactive = false;

		}
	}
public Rdbms(Properties dbPropertyFile,String jobName,String testStatus,String errorDesc,String environment,String iteration) throws Exception {
		
		
		Connection conn;
		Statement stmt=null;
		try{
		String host = dbPropertyFile.getProperty("HOST");
		String port	=dbPropertyFile.getProperty("PORT");
		String db = dbPropertyFile.getProperty("DATABASE");
		String user = dbPropertyFile.getProperty("USER");
		String password = dbPropertyFile.getProperty("PASSWORD");
		String url = dbPropertyFile.getProperty("DRIVER");
		
		if ((host.length() == 0)  || (port.length() == 0)|| (db.length() == 0)
				|| (user.length() == 0) || (password.length() == 0) || (url.length() == 0)) {
			conn = null;
			System.out.println("Please check your database property files, it has some invalid parameters.");
			isactive = false;
		} else {
			Class.forName(url).newInstance();
			String connectionurl = "jdbc:mysql://"+host+":"+port+"/"+db; // a JDBC url
			conn = DriverManager.getConnection(connectionurl, user, password);
			System.out.println("ODS Database is connected successfully.");
			isactive = true;
			conn.setAutoCommit(false);

			try{
				String [] queries;
				
				int TC_ID=0;
				int TE_ID=0;
				int ER_ID=0;
				int iterationNo=Integer.parseInt(iteration);
				String errorDescription=null;
				
				if(errorDesc!=null)
				errorDescription = errorDesc.replace("'", "");   
				
				stmt = conn.createStatement();
				for(int i=1;i<=iterationNo;i++){
				String getTestCaseID ="Select TC_ID from `test_master` where TEST_SCRIPT='"+jobName+"'"  + "and ITERATION_NO ='"+i+"'" ;
				
				ResultSet rs=stmt.executeQuery(getTestCaseID); 
				
				while (rs.next()){
				TC_ID=rs.getInt("TC_ID");
				}
				System.out.println("Fetched Test Case ID  from test_master table:"+TC_ID);    

				if(testStatus.equalsIgnoreCase("FAILED")) {
				
				queries = new String[] {
						
							"INSERT INTO test_execution (ENVIRONMENT,TE_STATUS, TC_ID) " +
							"SELECT '"+environment+"', '"+testStatus+"', '"+TC_ID+"'",
							
							"INSERT INTO test_errors (ERROR_DETAILS, TC_ID, TE_ID) " +
							"SELECT '"+errorDescription+"', '"+TC_ID+"', Max(TE_ID) from test_execution",
																
							"INSERT INTO test_results (TR_STATUS, TC_ID, ER_ID, TE_ID) " +
							"SELECT '"+testStatus+"', '"+TC_ID+"', Max(er.ER_ID), Max(te.TE_ID)  from `test_errors` er CROSS JOIN `test_execution` te"
							};
				
				}else{
					
					queries = new String[] {
							
							"INSERT INTO test_execution (ENVIRONMENT,TE_STATUS, TC_ID) " +
							"SELECT '"+environment+"', '"+testStatus+"', '"+TC_ID+"'",
									
							"INSERT INTO test_results (ER_ID,TR_STATUS, TC_ID, TE_ID) " +
							"SELECT '0', '"+testStatus+"', '"+TC_ID+"', Max(TE_ID) from `test_execution`"
						};
				}
				
				for (String query : queries){ 
					
					stmt.addBatch(query); 
					System.out.println("Queries to be inserted:" +query);
				}
					stmt.executeBatch(); 
					conn.commit();

					System.out.println("Tables inserted successfully");
			          
				  }
				stmt.close(); 
				conn.close(); 
			}
			
			  catch (SQLException s){
				  System.out.println("SQL statement is not executed hence rolling back the transaction!");
				  conn.rollback();

			  }

			}
		}
		catch (ClassNotFoundException cex) {
			System.out.println("Library is not loaded correctly.. "
					+ cex.getMessage());
			
			conn = null;
			isactive = false;

		}
	}


	public boolean CheckODSconnection() {
		if (isactive) {
			try {
				String query = "SELECT COUNT(*) FROM DBO.T_CASE";
				Statement stmt = conexion1.createStatement();
				//PreparedStatement pstmt = conexion1.prepareStatement(query);
				isactive = stmt.execute(query);
				System.out.println("ODS Database is connected " + isactive);
				return isactive;
			} catch (SQLException ex) {
				System.out.println(Rdbms.class.getName() + ex.getMessage());
				isactive = false;
				return isactive;
			}
		} else {
			isactive = false;
			 System.out.println("ODS Database is not connected " + isactive);
			return isactive;
		}
	}
	
	public boolean CheckGDWconnection() {
		if (isactive) {
			try {
				String query = "SELECT COUNT (*) FROM [GDW].[dbo].[T_DIM_OPPORTUNITY_LINE_ITEM]";
				Statement stmt = conexion2.createStatement();
				//PreparedStatement pstmt = conexion1.prepareStatement(query);
				isactive = stmt.execute(query);
				System.out.println("GDW Database is connected " + isactive);
				return isactive;
			} catch (SQLException ex) {
				System.out.println(Rdbms.class.getName() + ex.getMessage());
				isactive = false;
				return isactive;
			}
		} else {
			isactive = false;
			 System.out.println("GDW Database is not connected " + isactive);
			return isactive;
		}
	}
	
	public int getrecordsetmultiple(String query) {		
		int count=-1;
		int colcount=0;
		try {
			Statement stmt = conexion1.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			ResultSetMetaData mtdt = rs.getMetaData();
			while (rs.next()) {
				colcount += 1;
//				count = rs.getInt(1);
				Object value;
				for (int i=1; i < colcount; i++)
				{
					value = rs.getObject(i);
					System.out.println("Query Result = " + value.toString());
				}
				
				System.out.println("Columncount = " + mtdt.getColumnCount());
				} //end while
			System.out.println("Database connection is connected "
					+ isactive);
			} catch (SQLException ex) {
			System.out.println(Rdbms.class.getName() + ex.getMessage());			
			}
			return count;
	}

	public String getdbvalue(String query, String dbname) {	
		String count = "";
		try {
			if(dbname.equalsIgnoreCase("ODS")) {
				Statement stmt = conexion1.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				while (rs.next()) {				
					count = rs.getString(1);
				} //end while
			}
			else if(dbname.equalsIgnoreCase("GDW")) {
				Statement stmt = conexion2.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				while (rs.next()) {				
					count = rs.getString(1);
				} //end while
			}
			//System.out.println("Database connection is connected "+ isactive);
			} catch (SQLException ex) {
			System.out.println(Rdbms.class.getName() + ex.getMessage());			
			}			
			return count;
	}
	
	public void comparedbvalues(String query, String expvalue, String dbname) {	
		String count = "";
		try {
			if(dbname.equalsIgnoreCase("ODS")) {
				Statement stmt = conexion1.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				while (rs.next()) {				
					count = rs.getString(1);
				} //end while
			}
			else if(dbname.equalsIgnoreCase("GDW")) {
				Statement stmt = conexion2.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				while (rs.next()) {				
					count = rs.getString(1);
				} //end while
			}			
			//System.out.println("Database connection is connected "+ isactive);
			} 
			catch (SQLException ex) {
			System.out.println(Rdbms.class.getName() + ex.getMessage());			
			}
			if(count.equalsIgnoreCase(expvalue)) {
				System.out.println("Actual Value: " + count );
				System.out.println("PASS\n");
			}
			else{
				System.out.println("Actual Value: " + count );
				System.out.println("Fail\n");
			}		
	}
	
	public Connection getConnection() {
		if (isactive)
			return conexion1;
		else
			return null;
	}

	public static Rdbms getInstance(String properyfile) {
		if (rdbms == null) {
			rdbms = new Rdbms(properyfile);
			return rdbms;
		} else {
			System.out.println("Singleton is already present");
			return rdbms;
		}
	}
	
	

	
	}