package rFQModules;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import baseclassFiles.BaseClass;


public class DatabaseIntegrations extends BaseClass {
	
	static String value;
	
	private static Connection connection=null;
	//public static String classForm="net.sourceforge.jtds.jdbc.Driver"; 
	public static String classForm="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	public static String dbConnectionUrlStg = "jdbc:sqlserver://204.232.165.183;DatabaseName=PanelcubeLive_Stg";
    public static String dbConnectionUrlProd= "jdbc:sqlserver://204.232.165.181;DatabaseName=PanelcubeLive"; 
	public static String dbUserName="sunil.k"; 
	public static String dbPassword="Us3r@1234"; 
	
	public static String dbProdUserName="rajesh_mehta"; 
	public static String dbProdPassword="FLk6U0wGPbTS";
	
	/**
	 * This method is used here for
	 * creating connection with database
	 * @throws SQLException
	 */
	public static void createDatabaseConnection() throws SQLException {
		
		try {
			if(BaseClass.currentURL.contains("stg")) {
				Class.forName(classForm);
				connection=DriverManager.getConnection(dbConnectionUrlStg, dbUserName, dbPassword);
				if(!connection.isClosed())
					System.out.println("Successfully connected to staging SQL server");
				
			}
			else {	
				Class.forName(DatabaseIntegrations.classForm);
				connection =DriverManager.getConnection(dbConnectionUrlProd, dbProdUserName, dbProdPassword);
				
				if(!connection.isClosed())
					System.out.println("Successfully connected to Prod SQL server");
				}
		
		} catch (ClassNotFoundException e) {
			System.out.println("Database is not connected"+e.getMessage());
		}
		
	}
	
	/**
	 * This method is used here for
	 * executing query and getting data from DB
	 * @throws SQLException
	 */
	public static void executeQuery(String query, Integer columnNo) throws SQLException {
		
		createDatabaseConnection();
		BaseClass.sleep(2000);
		Statement statement = connection.createStatement();
		BaseClass.sleep(1000);
		ResultSet resultSet = statement.executeQuery(query);
		
		while (resultSet.next()) { 
			value=resultSet.getString(columnNo);
		}
		
		if (resultSet != null) {
			 try {
			 resultSet.close();} 
			 catch (Exception e) {}
			}
			 
			 if (statement != null) {
			 try {
				 statement.close();
			 } catch (Exception e) {
			 }
			 }
			 
			 if (connection != null) {
			 try {
				 connection.close();
			 } catch (Exception e) {
			 }
			 }
			 
	}
	
	/**
	 * This method is used for getting Survey Points in DB
	 * @return
	 */
	public static String getColumnValue() {
		return value;
		
	}
	
}
