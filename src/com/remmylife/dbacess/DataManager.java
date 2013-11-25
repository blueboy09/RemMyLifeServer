package com.remmylife.dbacess;


import java.io.*;
import java.sql.*;


public class DataManager {
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	private ResultSetMetaData metaData;
	private int numberOfRows;

	private String driver = "com.mysql.jdbc.Driver"; 
	private String url = "jdbc:mysql://localhost:3306/remembermylife";
	private String user = "yfjin";
	private String password = "1234";
	
	   // keep track of database connection status
	   private boolean connectedToDatabase = false;
	   
	   // initialize resultSet and obtain its meta data object;
	   // determine number of rows

	
	public DataManager(String driver, String url, String user, String password) {
		super();
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.password = password;
	}
	public DataManager() {
		super();
		try {
			Configuration rc = new Configuration(".\\database.properties");
			driver = rc.getValue("driver");
			url = rc.getValue("url");
			user = rc.getValue("user");
			password = rc.getValue("password");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void connectToDatabase() throws SQLException, ClassNotFoundException{
	      // load database driver class
	      Class.forName( driver );
	      
	      // connect to database
	      connection = DriverManager.getConnection( url,user,password );

	      // create Statement to query database
	      statement = connection.createStatement( 
	         ResultSet.TYPE_SCROLL_INSENSITIVE,
	         ResultSet.CONCUR_READ_ONLY );

	      // update database connection status
	      connectedToDatabase = true;
	   }
	   
	   // set new database query string
	   public void setQuery( String query ) 
	      throws SQLException, IllegalStateException 
	   {
	      // ensure database connection is available
	      if ( !connectedToDatabase ) 
	         throw new IllegalStateException( "Not Connected to Database" );

	      // specify query and execute it
	      // System.out.println(query);
	      resultSet = statement.executeQuery( query );

	      // obtain meta data for ResultSet
	      metaData = resultSet.getMetaData();

	      // determine number of rows in ResultSet
	      resultSet.last();                   // move to last row
	      
	      numberOfRows = resultSet.getRow();  // get row number  
	   }
	   
	   // set new database update string
	   public void setUpdate( String query ) 
	      throws SQLException, IllegalStateException 
	   {
	      // ensure database connection is available
	      if ( !connectedToDatabase ) 
	         throw new IllegalStateException( "Not Connected to Database" );
	      // specify query and execute it
	      statement.executeUpdate( query );
 
	   }
	   
	   // set new database update string
	   public void setExec( String query ) 
	      throws SQLException, IllegalStateException 
	   {
	      // ensure database connection is available
	      if ( !connectedToDatabase ) 
	         throw new IllegalStateException( "Not Connected to Database" );

	      // specify query and execute it
	      statement.execute( query );
 
	   }
	   
	   public void execSqlFile(String filename){
		   try {
			this.connectToDatabase();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		   String content=readSQL(filename);
		   String[] commands =content.split(";");
		   for(String s:commands){
			   try {
				setExec(s);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		   }
		   
		   this.disconnectFromDatabase();
	   }
	   
	   public void disconnectFromDatabase()
	   {
	      // close Statement and Connection
	      try {
	         statement.close();
	         connection.close();
	      }
	      
	      // catch SQLExceptions and print error message
	      catch ( SQLException sqlException ) {
	         sqlException.printStackTrace();
	      }

	      // update database connection status
	      finally { 
	         connectedToDatabase = false; 
	      }
	   }
	   
	   public Object getValueAt( int row, int column ) 
			      throws IllegalStateException
			   {
			      // ensure database connection is available
			      if ( !connectedToDatabase ) 
			         throw new IllegalStateException( "Not Connected to Database" );

			      // obtain a value at specified ResultSet row and column
			      try {
			         resultSet.absolute( row + 1 );
			         
			         return resultSet.getObject( column + 1 );
			      }
			      
			      // catch SQLExceptions and print error message
			      catch ( SQLException sqlException ) {
			         sqlException.printStackTrace();
			      }
			      
			      // if problems, return empty string object
			      return "";
			   }
	   
	   public int getRowCount() throws IllegalStateException
	   {      
	      // ensure database connection is available
	      if ( !connectedToDatabase ) 
	         throw new IllegalStateException( "Not Connected to Database" );
	 
	      return numberOfRows;
	   }
	   
	   // get number of columns in ResultSet
	   public int getColumnCount() throws IllegalStateException
	   {   
	      // ensure database connection is available
	      if ( !connectedToDatabase ) 
	         throw new IllegalStateException( "Not Connected to Database" );

	      // determine number of columns
	      try {
	         return metaData.getColumnCount(); 
	      }
	      
	      // catch SQLExceptions and print error message
	      catch ( SQLException sqlException ) {
	         sqlException.printStackTrace();
	      }
	      
	      // if problems occur above, return 0 for number of columns
	      return 0;
	   }
	   
	   // get name of a particular column in ResultSet
	   public String getColumnName( int column ) throws IllegalStateException
	   {    
	      // ensure database connection is available
	      if ( !connectedToDatabase ) 
	         throw new IllegalStateException( "Not Connected to Database" );

	      // determine column name
	      try {
	         return metaData.getColumnName( column + 1 );  
	      }
	      
	      // catch SQLExceptions and print error message
	      catch ( SQLException sqlException ) {
	         sqlException.printStackTrace();
	      }
	      
	      // if problems, return empty string for column name
	      return "";
	   }
	   
	   // get class that represents column type
	   public Class getColumnClass( int column ) throws IllegalStateException
	   {
	      // ensure database connection is available
	      if ( !connectedToDatabase ) 
	         throw new IllegalStateException( "Not Connected to Database" );

	      // determine Java class of column
	      try {
	         String className = metaData.getColumnClassName( column + 1 );
	         
	         // return Class object that represents className
	         return Class.forName( className );
	      }
	      
	      // catch SQLExceptions and ClassNotFoundExceptions
	      catch ( Exception exception ) {
	         exception.printStackTrace();
	      }
	      
	      // if problems occur above, assume type Object 
	      return Object.class;
	   }

		public String readSQL(String fileName) {
			File file = new File(fileName);
			BufferedReader bf;
			try {
				bf = new BufferedReader(new FileReader(file));
				
				String content = "";
				StringBuilder sb = new StringBuilder();
				  
				while(content != null){
				   try {
					content = bf.readLine();
					   if(content == null){
						    break;
						   }
						   
					sb.append(content.trim());
				   
				}catch (IOException e) {
					e.printStackTrace();
				}
				}
				bf.close();					
				return sb.toString();
				
			}catch(IOException e){
				e.printStackTrace();
				return null;
			}
			  
		}
		
		
		public String avoidAqlInjection(String query){
			return query.replace('"','_').replace('\'', '_').replace('`', '_');
		}
		
		public String getDriver() {
			return driver;
		}
		public void setDriver(String driver) {
			this.driver = driver;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getUser() {
			return user;
		}
		public void setUser(String user) {
			this.user = user;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}

}
