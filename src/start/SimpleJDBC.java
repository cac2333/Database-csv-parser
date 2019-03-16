package project3;

import java.sql.*;
//util.*
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class SimpleJDBC {
	static Statement statement;

	private static int sqlCode=0; //Variable to hold SQLCODE
	private static String sqlState="00000"; //var to hold SQLSTATE

	public static void main(String[] args) throws SQLException, FileNotFoundException{
		//Unique table name. 
		//Either the user supplies a unique identifier as a command line arg, 
		//or the program makes one up

		String tableName="";
		Scanner sc=new Scanner(new File("data\\account.txt"));
		sc.useDelimiter("...");
		String usernamestring=sc.nextLine();
		String passwordstring=sc.nextLine();
		sc.close();
		String data;
		
		if(args.length>0){
			tableName+=args[0];
		}else{
			tableName+="example.tbl";
		}

		try {
			DriverManager.registerDriver ( new org.postgresql.Driver() ) ;
		} catch (Exception cnfe){
			System.out.println("Class not found");
		}

		// This is the url you must use for Postgresql.
		//Note: This url may not valid now !
		
		String url = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";
		Connection con = DriverManager.getConnection (url,usernamestring, passwordstring) ;
		Statement statement = con.createStatement ( ) ;
		
		
		//Insert Into Visitor		
		Parser visitor=new Parser("visitor.csv", new int[]{1, 1, 1});
		data=visitor.getLine();
		while(data!=null){
			insert(statement, "Visitor", data);
			data=visitor.getLine();
		}
		
		//Insert Into Collection
		Parser collection=new Parser("collection.csv", new int[]{0, 1, 1});
		data=collection.getLine();
		while(data!=null){
			insert(statement, "Collections", data);
			data=collection.getLine();
		}
		
		//Insert Into Staff
		Parser staff=new Parser("staff.csv", new int[]{0, 1});
		data=staff.getLine();
		while(data!=null){
			insert(statement, "Staff", data);
			data=staff.getLine();
		}

		//Insert Into Book
		Parser book=new Parser("booknumber.csv", new int[]{0,1,1});
		data=book.getLine();
		while(data!=null){
			insert(statement, "Book", data);
			data=book.getLine();
		}
		
		//Insert Into reservation
		Parser reservation =new Parser("reservation.csv", new int[]{0,0,0,1,1});
		data=reservation.getLine();
		while(data!=null){
			//insert(statement, "Reservation", data);
			data=reservation.getLine();
			System.out.println(data);
		}
		
		
		
		statement.close();
		con.close();
	}

	
	
	private static void insert(Statement statement, String tableName, String val)throws SQLException{
		
		String insertSQL;
		try{

				insertSQL = "INSERT INTO "+tableName+" VALUES("+val+");";
				System.out.println(insertSQL);
				statement.executeUpdate(insertSQL);
				System.out.println("DONE");
				
		}catch(SQLException e){
			sqlCode = e.getErrorCode(); // Get SQLCODE
			sqlState = e.getSQLState(); // Get SQLSTATE
			System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
		}
	}
	
	
	//Q2 part 2 (modification) insert a new activity
	public static String insertActivity(String activityName, String activityTime, String inputPrice) { 
		//parse price
		double newPrice=0.0;
		try {
			newPrice = Double.parseDouble(inputPrice);
		}catch(InputMismatchException e) {
			return "Please type in a valid price";
		}
		
		//insert an activity
		try {
			insert(statement, "Activity", "INSERT INTO activity VALUES('"+activityName+"','"+activityTime+"',"+newPrice+")");
			
		}catch(SQLException e) {
			//stop inserting if an error occurs
			
			sqlCode = e.getErrorCode(); // Get SQLCODE
			sqlState = e.getSQLState(); // Get SQLSTATE
			
			if(sqlCode==22007) {
				return "Please type in a valid date";
			}
			
			return ("Code: " + sqlCode + "  sqlState: " + sqlState);
		}
		
		return "Successfully inserted your activity!";
	}
	
	
	//Q2 part 5 (modification) modify a price
	public static String modifyPrice(String activityName, String activityTime, String inputPrice) {
		//parse price
		double newPrice=0.0;
		try {
			newPrice = Double.parseDouble(inputPrice);
		}catch(InputMismatchException e) {
			return "Please type in a valid new price";
		}
		
		ResultSet rset = null;
		String tempQuery = "SELECT * FROM activity WHERE activityName='"+activityName
				+"' AND activityTime='"+activityTime+"'";
		try {
			rset = statement.executeQuery(tempQuery);
			
		}catch(SQLException e){
			sqlCode = e.getErrorCode(); // Get SQLCODE
			sqlState = e.getSQLState(); // Get SQLSTATE
			return ("Code: " + sqlCode + "  sqlState: " + sqlState);
		}
		
		try {
			if(rset.next()) {
				//activity record exists
				//so update the price
				tempQuery="UPDATE activity SET activity.price ="+newPrice
						+" WHERE activity.activityName ='"+activityName+"' AND activity.activityTime='"
						+activityTime+"'";
				statement.executeQuery(tempQuery);
			}else {
				//activity not found
				return "The activity you entered does not exist. Please re-enter a valid activity.";
				
			}
		}catch(SQLException e) {
			sqlCode = e.getErrorCode(); // Get SQLCODE
			sqlState = e.getSQLState(); // Get SQLSTATE
			System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
		}		
		
		return "Successfully modified the price of the activity you've specified";
	}
	
	
	
	
	
	
	
}



