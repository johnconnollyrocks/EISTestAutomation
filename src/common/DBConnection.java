package common;

import java.sql.*;

public class DBConnection {

			private static DBConnection sInstance = null;
			private static Connection con=null;
			private static Statement stmt=null;
			private static PreparedStatement pstmt=null;
			
			public static DBConnection instance() {

				if (sInstance == null) {
					sInstance = new DBConnection();
				}
				return sInstance;
			}
			
			public Connection getDatabaseConnectionSTG()
			{
				try
				{
					Util.printInfo("connecting to Automation Data Base :: ");
					
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
					String connectionurl = "jdbc:mysql://USPETQDTSTAUT01:3306/automation"; // a JDBC url
					con = DriverManager.getConnection(connectionurl, "root", "505tulsa");
//					con = DriverManager.getConnection("jdbc:sqlserver://USPETQDTSTAUT01\\automation:3306; user=automation; password=505tulsa");
//					con = DriverManager.getConnection("jdbc:mysql://USPETQDTSTAUT01:3306/automation, user=root, password=505tulsa");
					Util.printInfo("Connected to Automation Data Base :: ");
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
					con = DriverManager.getConnection("jdbc:sqlserver://USPETQDTSTAUT01\\automation; user=automation; password=505tulsa");
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


public ResultSet ExecuteQuery(String Query)
{
	String authenticate_query=Query;
	String flag="false"; 
	ResultSet rs=null;
	Statement stmt=null;
	try
	{
		stmt = con.createStatement();
//		openDMartConnection();
//		pstmt=con.prepareStatement(Query);
		stmt.execute(Query);
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
	return (rs);
}

}


