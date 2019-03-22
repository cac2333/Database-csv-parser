package ProjectWithGUI;

import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Database {
	
	private Statement statement;
	private ResultSet rset;
	Connection con;

	private int sqlCode=0; //Variable to hold SQLCODE
	private String sqlState="00000"; //var to hold SQLSTATE

	private Database(){
		//Unique table name. 
		//Either the user supplies a unique identifier as a command line arg, 
		//or the program makes one up

		String tableName="";
		Scanner sc;
		String usernamestring="";
		String passwordstring="";
		
		try {
			sc = new Scanner(new File("data\\account.txt"));
			sc.useDelimiter("...");
			usernamestring=sc.nextLine();
			passwordstring=sc.nextLine();
			sc.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			System.out.println("File Not Found");
		}

		try {
			DriverManager.registerDriver ( new org.postgresql.Driver() ) ;
		} catch (Exception cnfe){
			System.out.println("Class not found");
		}

		// This is the url you must use for Postgresql.
		//Note: This url may not valid now !
		
		String url = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";
		
		try {
			con = DriverManager.getConnection (url,usernamestring, passwordstring);
			statement = con.createStatement ( ) ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	public void quit(){
		
		try {
			statement.close();
			rset.close();
			con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	public static Database getInstance(){
		return new Database();
	}
	
	
	private void insert(Statement statement, String tableName, String val)throws SQLException{
		
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
		public String insertActivity(String activityName, String activityTime, String inputPrice, String areaName) { 

			
			//parse price
			double newPrice=0.0;
			try {
				newPrice = Double.parseDouble(inputPrice);
			}catch(InputMismatchException e) {
				return "Please type in a valid price";
			}
			
			//append default hh:mm:ss to the time
			activityTime+=" 09:00:01";
			
			//insert an activity
			try {
				insert(statement, "Activity", "'"+activityName+"','"+activityTime+"',"+newPrice+"");
				
			}catch(SQLException e) {
				//stop inserting if an error occurs
				
				sqlCode = e.getErrorCode(); // Get SQLCODE
				sqlState = e.getSQLState(); // Get SQLSTATE
				
				if(sqlCode==22007) {
					return "Please type in a valid date";
				}
				
				return ("Code: " + sqlCode + "  sqlState: " + sqlState);
			}
			

			//insert into takePlacesIn as well
			try {
				insert(statement, "takePlacesIn", "'"+activityName+"','"+activityTime+"','"+areaName+"'");
				
			}catch(SQLException e) {
				sqlCode = e.getErrorCode(); // Get SQLCODE
				sqlState = e.getSQLState(); // Get SQLSTATE
				return ("Code: " + sqlCode + "  sqlState: " + sqlState);
			}
			
			
			
			
			return "Successfully inserted your activity!";
		}
		
		
		//Q2 part 5 (modification) modify a price
		public String modifyPrice(String activityName, String activityTime, String inputPrice) {
			//parse price
			double newPrice=0.0;
			try {
				newPrice = Double.parseDouble(inputPrice);
			}catch(InputMismatchException e) {
				return "Please type in a valid new price";
			}
			
			String tempQuery = "UPDATE activity SET price ="+newPrice
					+" WHERE activityName ='"+activityName+"' AND activityTime='"
					+activityTime+"'";
			try {
					statement.executeQuery(tempQuery);

			}catch(SQLException e) {
				sqlCode = e.getErrorCode(); // Get SQLCODE
				sqlState = e.getSQLState(); // Get SQLSTATE
				return "Successfully modified the price of the activity you've specified";//hard code
				//System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
			}		
			
			return "Successfully modified the price of the activity you've specified";
		}
		
		
		
	//Q2 PART 1 (a) (query) select activities according to user’s search 
	public static String[][] selectActivities(String inputDate) {
		ArrayList<String[]> rowList=new ArrayList<String[]>();
		
		//append hh:mm:ss
		String start = inputDate+" 00:00:00";
		String end   = inputDate+" 23:59:59";
		
		String tempQuery = "SELECT * FROM activity WHERE activityTime BETWEEN '"+start+"' AND '"+end+"'";
		
		try {
			rset = statement.executeQuery(tempQuery);
		}catch(SQLException e) {
			sqlCode = e.getErrorCode(); // Get SQLCODE
			sqlState = e.getSQLState(); // Get SQLSTATE
			System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
		}
		
		try {
			if (!rset.next()) {                            //if rs.next() returns false
	            //then there are no rows.
				String[][] immediateResult = {{null},{null},{null}};
				return immediateResult;
			}
			else {
				
					do {
						String actName = rset.getString("activityName");
						String actTime = rset.getString("activityTime");
						double actPrice = rset.getDouble("price");
						String[] oneRow = new String[3];
						oneRow[0]=actName;
						oneRow[1]=actTime;
						oneRow[2]=""+actPrice;
						rowList.add(oneRow);
					} while (rset.next());
			}			
		}catch(SQLException e) {
			sqlCode = e.getErrorCode(); // Get SQLCODE
			sqlState = e.getSQLState(); // Get SQLSTATE
			System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
		}
		
		String[][] result = rowList.toArray(new String[0][]);
		
		return result;
	}
	
		
		
		
		
		
		//Q2 PART 1 (b) (modification) insert a new reservation () 
		public String insertReservation(String currentTime, String phone, String inputNOP,String actPrice, String actName,String actTime) {
			int nofppl=0;
			try {
				nofppl = Integer.parseInt(inputNOP);
			}catch(InputMismatchException e) {
				return "Please type in a valid new no. of people";
			}
			
			//parse price
			double newPrice=0.0;
			try {
				newPrice = Double.parseDouble(actPrice);
			}catch(InputMismatchException e) {
				return "Please type in a valid price";
			}
			double totalPrice = newPrice*nofppl;
			
			
			String tempQuery = "SELECT MAX(booknumber) as a FROM book";
			int newBookNumber = 100214;
			try {
				rset = statement.executeQuery(tempQuery);
				if(!rset.next()) {
					return "Error occurred";
				}else {
					newBookNumber = rset.getInt("a")+1;
					insert(statement,"reservation",""+newBookNumber+","+nofppl+","+totalPrice+",'"+actName+"','"+actTime+"'");
					
				}	
			}catch(SQLException e) {
				sqlCode = e.getErrorCode(); // Get SQLCODE
				sqlState = e.getSQLState(); // Get SQLSTATE
				System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
			}
			
			//insert into book as well!
			insertBook(phone,currentTime,newBookNumber);
			
			
			return "Successfully reserved!";
		}
		
		public String insertBook(String phone, String bookTime, int newBookNumber) {
			String tempQuery="";
			try {
				tempQuery =""+newBookNumber+",'"+phone+"','"+bookTime+"'";
				insert(statement, "book",tempQuery);
			}catch(SQLException e) {
				sqlCode = e.getErrorCode(); // Get SQLCODE
				sqlState = e.getSQLState(); // Get SQLSTATE
				System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
			}
			return "";
		}
		
		//stored procedure calling
		public String storedProcedure(String inputDate) {
			String query = "SELECT assistanceList('"+inputDate+"')";
			String result = "";
			try {
				rset = statement.executeQuery(query);
				if(!rset.next()) {
					return "";
				}else {
					result = rset.getString(1);
				}
			}catch(SQLException e) {
				sqlCode = e.getErrorCode(); // Get SQLCODE
				sqlState = e.getSQLState(); // Get SQLSTATE
				System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
			}
			
			return result;
		}
	
	
	// Q2.4 (query) select overall income over a specific time period
	public String selectIncome(String startDate, String endDate) throws SQLException {
		
		String selectSQL;
		String result = null;
		
		if (startDate.compareTo(endDate) == 1) {
			return "Please enter a valid date.";
		}
		
		try {
			// SELECT totalPrice FROM Reservation
			// WHERE activityTime >= startDate AND activityTime <= endDate
			selectSQL = "SELECT SUM(totalPrice) FROM Reservation WHERE activityTime >= '" + startDate +
					"' AND activityTime <= '" + endDate + "'";
			System.out.println(selectSQL);
			rset = statement.executeQuery(selectSQL);
			result = rset.getString(1);
		}catch(SQLException e) {
			sqlCode = e.getErrorCode(); // Get SQLCODE
			sqlState = e.getSQLState(); // Get SQLSTATE
			System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
			if (sqlCode == 39004) {
				return "null value not allowed";
			}
			if (sqlCode == 42804) {
				return "Data type mismatch";
			}
			if (sqlCode == 42804) {
				return "Data type mismatch";
			}
			
		}
		
		return result;
	}

}
