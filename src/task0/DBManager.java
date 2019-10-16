package task0;

import java.sql.*;

public class DBManager {

	private static String ip = "localhost";
	private static int port = 3307;
	private static String db_name = "valutazionistudenti";
	private static String db_user = "root";
	
	public Connection connection;
	private Statement statement;
	private ResultSet result;
	
	private static String selectionSubjectsString = "SELECT * FROM subjects";
	private static String selectionProfessorsString = "SELECT * FROM professors";
	private static String selectionSubjectsStringFiltered = "SELECT * FROM subjects WHERE subject_degreeCourse = ?";
	private static String selectionProfessorsStringFiltered = "SELECT * FROM professors JOIN ... WHERE ... ";
	
	private PreparedStatement selectProfessorsFiltered;
	private PreparedStatement selectSubjectsFiltered;
	
	
	// CONSTRUCTOR
	public DBManager(){
		try {
			connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + db_name, db_user, "");
			statement = connection.createStatement();
			
			selectProfessorsFiltered = connection.prepareStatement(selectionSubjectsStringFiltered);
			selectSubjectsFiltered = connection.prepareStatement(selectionProfessorsStringFiltered);
		} 
		catch (SQLException e) {e.printStackTrace();}
	}
	
	
	ResultSet getSubjects() {
		try {
			statement.execute(selectionSubjectsString);
			result = statement.getResultSet();
		} 
		catch (SQLException e) {e.printStackTrace();}
		return result;
	}
	
	
	void getProfessors() {
		try {
			statement.execute(selectionProfessorsString);
			result = statement.getResultSet();
		} 
		catch (SQLException e) {e.printStackTrace();}
	}
	
	
	void getSubjectsFiltered(String degreeCourse) {
		try {
			selectSubjectsFiltered.setString(1, degreeCourse);
			result = selectSubjectsFiltered.executeQuery();
		} 
		catch (SQLException e) {e.printStackTrace();}
		
	}
	
	
	void getProfessorsFiltered(String degreeCourse) {
		try {
			selectProfessorsFiltered.setString(1, degreeCourse);
			result = selectProfessorsFiltered.executeQuery();
		} 
		catch (SQLException e) {e.printStackTrace();}
	}
	
	
	void quit() {
		try {
			connection.close();
		} 
		catch (SQLException e) {e.printStackTrace();}
	}
	
}
