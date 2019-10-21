package task0;

import java.sql.*;
import java.util.*;

public class DBManager {

	private static String ip = "localhost";
	private static int port = 3307;
	private static String db_name = "studentsevaluations";
	private static String db_user = "root";
	
	public Connection connection;
	private Statement statement;
	private ResultSet result;
	
	private static String selectionSubjectsString = "SELECT * FROM subjects";
	
	private static String selectionProfessorsString = "SELECT DISTINCT p.*, s.degree\n" + 
													"FROM subjects s INNER JOIN teaching t ON t.subjectId = s.Id "
																	+ "INNER JOIN professors p ON p.Id = t.professorId;";
	
	private static String selectionUserString =  "SELECT * FROM `users` WHERE Username = ? AND Password = ?;";
	
	private static String insertProfCommentString = "INSERT INTO `prof_comments` (`id`, `userId`, `professorId`, `text`, `date`) "
													+ "VALUES (NULL, ?, ?, ?, current_timestamp());";
	
	private static String insertSubjectCommentString =  "INSERT INTO `subject_comments` (`id`, `userId`, `subjectId`, `text`, `date`) "
														+ "VALUES (NULL, ?, ?, ?, current_timestamp());";
	
	private static String selectionProfCommentString = "SELECT * FROM `prof_comments` ";
	
	private static String selectionSubjectCommentString = "SELECT * FROM `subject_comments";
	
	private PreparedStatement selectionUserStatement;
	private PreparedStatement insertProfCommentStatement;
	private PreparedStatement insertSubjectCommentStatement;
	
	
	// CONSTRUCTOR
	public DBManager(){
		try {
			connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + db_name, db_user, "");
			statement = connection.createStatement();
			
			selectionUserStatement = connection.prepareStatement(selectionUserString);
			insertProfCommentStatement = connection.prepareStatement(insertProfCommentString);
			insertSubjectCommentStatement = connection.prepareStatement(insertSubjectCommentString);
		} 
		catch (SQLException e) {e.printStackTrace();}
	}
	
	
	
	// QUERY per il ritorno di tutte le materie
	List<Subject> getSubjects() {
		
		List <Subject> list = new ArrayList<Subject>();
		
		try {
			statement.execute(selectionSubjectsString);
			result = statement.getResultSet();
			
			while(result.next())
				list.add(new Subject(result.getInt("Id"), result.getString("name"), result.getInt("credits"),
						 			 result.getString("info"), result.getInt("degree")));
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return list;
	}
	
	
	
	// QUERY per il ritorno di tutti i professori
	List<Professor> getProfessors() {
		
		List<Professor> list = new ArrayList<Professor>();
		
		try {
			statement.execute(selectionProfessorsString);
			result = statement.getResultSet();
			
			while(result.next()) {
				
				
				list.add(new Professor(result.getInt("Id"), result.getString("name").toString(),
										result.getString("surname"), result.getString("info"), result.getInt("degree")));
			}
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		return list;
	}
	
	/*
	
	// TODO
	void getSubjectsFiltered(String degreeCourse) {
		try {
			selectSubjectsFiltered.setString(1, degreeCourse);
			result = selectSubjectsFiltered.executeQuery();
		} 
		catch (SQLException e) {e.printStackTrace();}
		
	}
	
	
	
	// TODO
	void getProfessorsFiltered(String degreeCourse) {
		try {
			selectProfessorsFiltered.setString(1, degreeCourse);
			result = selectProfessorsFiltered.executeQuery();
		} 
		catch (SQLException e) {e.printStackTrace();}
	}
	*/
	
	
	// QUERY for login
	Student checkUser(String name, String pwd) {
		try {
			selectionUserStatement.setString(1, name);
			selectionUserStatement.setString(2, pwd);
			result = selectionUserStatement.executeQuery();
			
			if(!result.first()) {
				return null;
			}
			else {
				return new Student(result.getInt("Id"), result.getString("Username").toString(), null, result.getInt("degree"));
			}
			
		} 
		catch (SQLException e) {e.printStackTrace();}
		return null;
	}
	
	
	
	// QUERY per l'inserimento di un commento del prof
	void insertCommentProf(int u, int p, String t) {
		try {
			insertProfCommentStatement.setInt(1, u);
			insertProfCommentStatement.setInt(2, p);
			insertProfCommentStatement.setString(3, t);
			
			// TODO controlla il tipo di ritorno
			int i = insertProfCommentStatement.executeUpdate();
		} 
		catch (SQLException e) {e.printStackTrace();}
	}
	
	
	
	// QUERY per l'inserimento di un commento della materia
	void insertCommentSubject(int u, int s, String t) {
		try {
			insertSubjectCommentStatement.setInt(1, u);
			insertSubjectCommentStatement.setInt(2, s);
			insertSubjectCommentStatement.setString(3, t);
			
			// TODO controlla il tipo di ritorno
			int i = insertSubjectCommentStatement.executeUpdate();
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
