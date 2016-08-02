package common;

import java.sql.*;

public class DatabaseHandler {

			private static DatabaseHandler sInstance = null;
			private static Connection con=null;
			private static Statement stmt=null;
			private static PreparedStatement pstmt=null;
			
			public static DatabaseHandler instance() {

				if (sInstance == null) {
					sInstance = new DatabaseHandler();
				}
				return sInstance;
			}
			
			public Connection getDataMartDbConnectionDEV()
			{
				try
				{
					Util.printInfo("connecting to Data Mart Data Base :: ");
					
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
					con = DriverManager.getConnection("jdbc:sqlserver://USPETDDDBSINP01\\INPSDMD1; user=Informatica; password=Inf0rmatica1");
					Util.printInfo("Connected to DM DataBase :: ");
				}
				catch (Exception ex)
				{
					System.out.println(ex+"--Connection ERROR--");
				}
				return con;
			}
			public static void openDMartConnection() throws ClassNotFoundException, SQLException
			{
				try
				{
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
					con = DriverManager.getConnection("jdbc:sqlserver://USPETDDDBSINP01\\INPSDMD1; user=Informatica; password=Inf0rmatica1");
				}
				catch (Exception ex)
				{
					System.out.println(ex+"--Connection ERROR--");
				}
			}
			
			public Connection getSiebleDbConnectionDEV()
			{
				try
				{
					Util.printInfo("connecting to Siebel Data Base :: ");
					
					Class.forName("oracle.jdbc.OracleDriver");
					con = DriverManager.getConnection("jdbc:oracle:thin:@//uspetddgpdbo001:1521/SBLTDEV1.autodesk.com", "rdon","trdon");           
					Util.printInfo("Connected to Sieble Data base :: ");
				}
				catch (Exception ex)
				{
					System.out.println(ex+"--Connection ERROR--");
				}
				return con;
			}
			public static void openSiebleConnection() throws ClassNotFoundException, SQLException
			{
				try
				{
					Class.forName("oracle.jdbc.OracleDriver");
					con = DriverManager.getConnection("jdbc:oracle:thin:@//uspetddgpdbo001:1521/SBLTDEV1.autodesk.com", "rdon","trdon"); 
				}
				catch (Exception ex)
				{
					System.out.println(ex+"--Connection ERROR--");
				}
			}

public static void closeConnection() throws SQLException
{
	con.close();
}
public static Statement createStatement() throws SQLException
{
	stmt=con.createStatement();
	return stmt;
}
public static void closeStatement() throws SQLException
{
	if(stmt==null)
		stmt.close();
}


public ResultSet ExecuteDMartQuery(String Query)
{
	String authenticate_query=Query;
	String flag="false"; 
	ResultSet rs=null;
	try
	{
		openDMartConnection();
		pstmt=con.prepareStatement(Query);
		rs=pstmt.executeQuery();
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
	return (rs);
}

public ResultSet ExecuteSiebelDBQuery(String Query)
{
	String authenticate_query=Query;
	String flag="false"; 
	ResultSet rs=null;
	try
	{
		openSiebleConnection();
		pstmt=con.prepareStatement(Query);
		rs=pstmt.executeQuery();
//		closeConnection();
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
	return (rs);
}

}


