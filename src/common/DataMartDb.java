package common;

import java.sql.*;

public class DataMartDb {

			private static DataMartDb sInstance = null;
			private static Connection con=null;
			private static Statement stmt=null;
			private static PreparedStatement pstmt=null;
			
			public static DataMartDb instance() {

				if (sInstance == null) {
					sInstance = new DataMartDb();
				}
				return sInstance;
			}
			
			public Connection getConnection()
			{
				try
				{
					Util.printInfo("connecting to Data Mart Data Base :: ");
					
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
					con = DriverManager.getConnection("jdbc:sqlserver://USPETDDDBSINP01\\INPSDMD1; user=Informatica; password=Inf0rmatica1");           
				}
				catch (Exception ex)
				{
					System.out.println(ex+"--Connection ERROR--");
				}
				return con;
			}
			public static void openConnection() throws ClassNotFoundException, SQLException
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


public String ExecuteQuery(String SerialNumber)
{
	String authenticate_query=SerialNumber;
	String flag="false"; 
	ResultSet rs=null;
	try
	{
		openConnection();
		pstmt=con.prepareStatement("select * from CONTRACT_DETAILS where SERIAL_NUM='"+SerialNumber+"'");
		rs=pstmt.executeQuery();
		while(rs.next())
		{
			flag=rs.getString(3);
		}
		closeConnection();
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
	return (flag);
}

}


