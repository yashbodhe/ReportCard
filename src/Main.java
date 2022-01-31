import java.sql.*;
import java.util.*;
import java.lang.*;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
public class Main {
//***************************************************************
//	THE MAIN FUNCTION OF PROGRAM
//****************************************************************

	public static void main(String[] args) {
		try{
    		//step1 load the driver class
    		Class.forName("oracle.jdbc.driver.OracleDriver");
    		//step2 create the connection object
    		Connection con=DriverManager.getConnection(
    		"jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
    		//step3 create the statement object
    		Statement stmt=con.createStatement();
    		//step4 execute query
    		
    		mainMenu(stmt);
    		con.close();
    		}
    	catch(Exception e){ 
    		System.out.println(e);
    		}
	}
//***************************************************************
//	THE MENU FUNCTION OF PROGRAM ( SHOW ALL OPTIONS )
//****************************************************************
	
	public static void mainMenu(Statement stmt) throws SQLException{
	    Scanner sc = new Scanner(System.in);
	 
		System.out.println("ENTRY MENU");
		System.out.println("1.CREATE STUDENT RECORD");
		System.out.println("2.DISPLAY ALL STUDENTS RECORDS");
		System.out.println("3.SEARCH STUDENT RECORD ");
		System.out.println("4.MODIFY STUDENT RECORD");
		System.out.println("5.DELETE STUDENT RECORD");
		System.out.println("6.TO EXIT");
		System.out.println("Please Enter Your Choice (1-6) ");
		
		int ch = sc.nextInt();
		int rollNo;
		
		switch(ch)
		{
		case 1:	insertData(stmt); 
				break;
		case 2:	displayAllResult(stmt); 
				break;
		case 3:	System.out.println("Please Enter The roll number for Result");
		        rollNo = sc.nextInt();
				displayRollNoResult(stmt,rollNo); 
				break;
		case 4:	System.out.println("Please Enter The roll number for MODIFY");
		        rollNo = sc.nextInt();
				modifyData(stmt,rollNo); 
				break;
		case 5:	System.out.println("Please Enter The roll number which you want to DELETE");
		        rollNo = sc.nextInt();
			    deleteData(stmt,rollNo); 
			    break;
		default:	exit();
		}
		System.out.printf("%n%n%n");
	}
	
//***************************************************************
//	function to Delete Result of any specific Roll No
//****************************************************************
	
	public static void deleteData(Statement stmt,int rollNo) throws SQLException{
		System.out.printf("%n%n%n");
		ResultSet rs=stmt.executeQuery("delete from Student where id ="+rollNo);
		System.out.printf("Result of Roll No. %d is Successfully deleted%n%n",rollNo);
		System.out.printf("%n%n%n");
		mainMenu(stmt);
		
	}
	
//***************************************************************
//	function to Modify Data in Database
//****************************************************************
	
	public static void modifyData(Statement stmt, int rollNo) throws SQLException{
		System.out.printf("%n%n%n");
		Scanner sc = new Scanner(System.in);
		
		
		
		ResultSet rs=stmt.executeQuery("select * from Student where id ="+rollNo);
		System.out.printf("Result of Roll No. %d are%n%n",rollNo);
		
		System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n","Id","Name","Physics","Chemistry","Maths","Percentage","Grade");
		
		while(rs.next()) {
			System.out.printf("%-15d%-15s%-15d%-15d%-15d%-15d%-15s%n", rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4),rs.getInt(5),rs.getInt(6),rs.getString(7));
		}
		
		
		System.out.println();
		System.out.println("Enter the New Student Details");
		System.out.println();
		int id = rollNo;
		System.out.println("Enter the Name of Student");
		String name = sc.nextLine();
		System.out.println("Enter Physics Marks");
		int phy = sc.nextInt();
		System.out.println("Enter Chemistry Marks");
		int chem = sc.nextInt();
		System.out.println("Enter Maths Marks");
		int math = sc.nextInt();
		System.out.println("Enter Percentage of Student");
		int total = sc.nextInt();
		System.out.println("Enter Grade of Student");
		sc.nextLine();
		String grade = sc.nextLine();	
		String sql = "update Student Set NAME=?,PHYSICS=?,CHEMISTRY=?,MATHS=?,PERCENTAGE=?,GRADE=? where id=?";
		
		try{
    		//step1 load the driver class
    		Class.forName("oracle.jdbc.driver.OracleDriver");
    		//step2 create the connection object
    		Connection con=DriverManager.getConnection(
    		"jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
    		//step3 create the statement object
    		Statement myStmt=con.prepareStatement(sql);
    		//step4 execute query
    		
    		((PreparedStatement) myStmt).setString(1, name);
    		((PreparedStatement) myStmt).setInt(2, phy);
    		((PreparedStatement) myStmt).setInt(3, chem);
    		((PreparedStatement) myStmt).setInt(4, math);
    		((PreparedStatement) myStmt).setInt(5, total);
    		((PreparedStatement) myStmt).setString(6, grade);
    		((PreparedStatement) myStmt).setInt(7, id);
    		
    		int result = ((PreparedStatement) myStmt).executeUpdate();
    		if (result > 0) {
            	System.out.println("Successfully Modified");
            }
            else {
            	 System.out.println("Unsuccessful Modification ");
            }
    		con.close();
    		}
    	catch(Exception e){ 
    		System.out.println(e);
    		}
		System.out.printf("%n%n%n");
		mainMenu(stmt);
		
	}
	
//***************************************************************
//	function to Display Data of Specific Roll No
//****************************************************************
	
	public static void displayRollNoResult(Statement stmt,int rollNo) throws SQLException{
		System.out.printf("%n%n%n");
		ResultSet rs=stmt.executeQuery("select * from Student where id ="+rollNo);
		System.out.printf("Result of Roll No. %d are%n%n",rollNo);
		
		System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n","Id","Name","Physics","Chemistry","Maths","Percentage","Grade");
		
		while(rs.next()) {
			System.out.printf("%-15d%-15s%-15d%-15d%-15d%-15d%-15s%n", rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4),rs.getInt(5),rs.getInt(6),rs.getString(7));
		}
		System.out.printf("%n%n%n");
		mainMenu(stmt);
		
	}
	
//***************************************************************
//	function to Display All Result
//****************************************************************
	
	public static void displayAllResult(Statement stmt) throws SQLException{
		System.out.printf("%n%n%n");
		ResultSet rs=stmt.executeQuery("select * from Student order by id");
		System.out.printf("Result of All Students%n%n");
		System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n","Id","Name","Physics","Chemistry","Maths","Percentage","Grade");
		
		while(rs.next()) {
			System.out.printf("%-15d%-15s%-15d%-15d%-15d%-15d%-15s%n", rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4),rs.getInt(5),rs.getInt(6),rs.getString(7));
		}
		System.out.printf("%n%n%n");
		mainMenu(stmt);
		
	}
	
//***************************************************************
//	function to Insert Data into Database
//****************************************************************
	
	public static void insertData(Statement stmt) throws SQLException{
		System.out.printf("%n%n%n");
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Student Result");
		System.out.println("Enter the Student Id");
		int id = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter the Name of Student");
		String name = sc.nextLine();
		System.out.println("Enter Physics Marks");
		int phy = sc.nextInt();
		System.out.println("Enter Chemistry Marks");
		int chem = sc.nextInt();
		System.out.println("Enter Maths Marks");
		int math = sc.nextInt();
		System.out.println("Enter Percentage of Student");
		int total = sc.nextInt();
		System.out.println("Enter Grade of Student");
		sc.nextLine();
		String grade = sc.nextLine();
		
		String sql = "insert into Student values(?,?,?,?,?,?,?)";
		
		try{
    		//step1 load the driver class
    		Class.forName("oracle.jdbc.driver.OracleDriver");
    		//step2 create the connection object
    		Connection con=DriverManager.getConnection(
    		"jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
    		//step3 create the statement object
    		Statement myStmt=con.prepareStatement(sql);
    		//step4 execute query
    		((PreparedStatement) myStmt).setInt(1, id);
    		((PreparedStatement) myStmt).setString(2, name);
    		((PreparedStatement) myStmt).setInt(3, phy);
    		((PreparedStatement) myStmt).setInt(4, chem);
    		((PreparedStatement) myStmt).setInt(5, math);
    		((PreparedStatement) myStmt).setInt(6, total);
    		((PreparedStatement) myStmt).setString(7, grade);
    		
    		int result = ((PreparedStatement) myStmt).executeUpdate();
    		if (result > 0) {
            	System.out.println("Successfully inserted");
            }
            else {
            	 System.out.println("Unsucessful insertion ");
            }
    		con.close();
    		}
    	catch(Exception e){ 
    		System.out.println(e);
    		}
		System.out.printf("%n%n%n");
		mainMenu(stmt);
        
	}
	public static void exit(){
		System.out.printf("%n%n%n%n%n%n%n%n%n");
		System.out.println("Thanks for your Time");
		clearScreen();
	}
	
//***************************************************************
//	function to clear the Console
//****************************************************************
	
	public static void clearScreen() {  
		Robot robbie = null;
		try {
			robbie = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		robbie.keyPress(KeyEvent.VK_SHIFT);
        robbie.keyPress(KeyEvent.VK_F10);
        robbie.keyRelease(KeyEvent.VK_SHIFT);
        robbie.keyRelease(KeyEvent.VK_F10);
        robbie.keyPress(KeyEvent.VK_R);
        robbie.keyRelease(KeyEvent.VK_R);
        
        System.out.println("Hello");
	}  

}
