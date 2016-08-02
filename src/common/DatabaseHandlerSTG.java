package common;

import java.sql.*;

public class DatabaseHandlerSTG {

			private static DatabaseHandlerSTG sInstance = null;
			private static Connection con=null;
			private static Statement stmt=null;
			private static PreparedStatement pstmt=null;
			
			public static DatabaseHandlerSTG instance() {

				if (sInstance == null) {
					sInstance = new DatabaseHandlerSTG();
				}
				return sInstance;
			}
			
			public Connection getSiebleDBConnection1()
			{
				try
				{
					Util.printInfo("connecting to CEP Stage Data Base :: ");
					Class.forName ("com.mysql.jdbc.Driver");
					con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:13307/cepstgdb", "cepstg", "cepstg1");
					Util.printInfo("Connected to CEP Stage DataBase :: ");
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
					Class.forName ("com.mysql.jdbc.Driver");
					con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:13307/cepstgdb", "cepstg", "cepstg1");
				}
				catch (Exception ex)
				{
					System.out.println(ex+"--Connection ERROR--");
				}
			}
			
			public Connection getSiebleDBConnection()
			{
				try
				{
					Util.printInfo("connecting to Siebel Data Base :: ");
					
					Class.forName("oracle.jdbc.OracleDriver");
					con = DriverManager.getConnection("jdbc:oracle:thin:@//ussclsesbdbo001:1521/SBLTSTG1_EAI.autodesk.com", "dataqa","Dataqa#1298");           
					Util.printInfo("Connected to Siebel Data base :: ");
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
					con = DriverManager.getConnection("jdbc:oracle:thin:@//ussclsesbdbo001:1521/SBLTSTG1_EAI.autodesk.com", "dataqa","Dataqa#1298"); 
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


public ResultSet ExecuteSiebelDBQuery1(String Query)
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


