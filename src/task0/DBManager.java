package task0;

import java.sql.*;
import java.util.*;

public class DBManager {

    private static String ip = "localhost";
    private static int port = 3306;
    private static String db_name = "studentsevaluations";
    private static String db_user = "root";

    public Connection connection;
    private Statement statement;
    private ResultSet result;

    private static String selectionSubjectsString = "SELECT * FROM subjects";
    private static String selectionProfessorsString =   "SELECT p.*, a.degree\n" +
                                                        "FROM professors p JOIN (\n" +
                                                        "    SELECT t.professorId, s.degree\n" +
                                                        "    FROM subjects s INNER JOIN teaching t \n" +
                                                        "    ON t.subjectid = s.id \n" +
                                                        ") AS a \n" +
                                                        "ON p.Id = a.professorId";
    private static String selectionUserString =  "SELECT * FROM `users` WHERE Username = ? AND Password = ?;";
    private static String insertProfCommentString = "INSERT INTO `prof_comments` (`userId`, `professorId`, `text`, `date`) "
                                                    + "VALUES (?, ?, ?, current_timestamp());";
    private static String insertSubjectCommentString =  "INSERT INTO `subject_comments` (`id`, `userId`, `subjectId`, `text`, `date`) "
                                                    + "VALUES (NULL, ?, ?, ?, current_timestamp());";
    private static String selectionProfCommentString = "SELECT * FROM `prof_comments` ;";
    private static String selectionSubjectCommentString = "SELECT * FROM `subject_comments`;";
    private static String selectionDegreeProgrammesString= "SELECT name FROM `degree_programmes`;";
    private static String selectionDegreeIdString="SELECT id FROM degree_programmes dp WHERE dp.name = ? ;";
    private static String selectionDegreeNameString="SELECT name FROM degree_programmes dp WHERE dp.Id = ? ;";
    
    private PreparedStatement selectionUserStatement;
    private PreparedStatement insertProfCommentStatement;
    private PreparedStatement insertSubjectCommentStatement;
    private PreparedStatement selectionDegreeIdStatement;
    private PreparedStatement selectionDegreeNameStatement;

    /*
     
     
    ----- SUBJECT -----
    UPDATE `subject_comments` SET `text` = ? WHERE `subject_comments`.`id` = ?;
    DELETE FROM `subject_comments` WHERE `subject_comments`.`id` = ?;
    
    
    ----- PROF -----
    UPDATE `prof_comments` SET `text` = ? WHERE `prof_comments`.`id` = ?;
    DELETE FROM `prof_comments` WHERE `prof_comments`.`id` = ?;
    
    
    */
    // CONSTRUCTOR
    public DBManager(){
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + db_name, db_user, "");
            statement = connection.createStatement();

            selectionUserStatement = connection.prepareStatement(selectionUserString);
            insertProfCommentStatement = connection.prepareStatement(insertProfCommentString);
            insertSubjectCommentStatement = connection.prepareStatement(insertSubjectCommentString);
            selectionDegreeIdStatement = connection.prepareStatement(selectionDegreeIdString);
            selectionDegreeNameStatement = connection.prepareStatement(selectionDegreeNameString);
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
                list.add(new Subject(result.getInt("Id"), result.getString("name"), 
                    result.getInt("credits"),result.getString("info"), result.getInt("degree")));
        } 
        catch (SQLException e) {e.printStackTrace();}
        return list;
    }
    
    

    // QUERY per il ritorno di tutti i professori
    List<Professor> getProfessors() {

        List<Professor> list = new ArrayList<>();
        try {
            statement.execute(selectionProfessorsString);
            result = statement.getResultSet();

            while(result.next()) {
                list.add(new Professor(result.getInt("id"), result.getString("name"),
                    result.getString("surname"), result.getString("info"), result.getInt("degree")));
            	
            }
        } 
        catch (SQLException e) {e.printStackTrace();}
        return list;
    }
    
    
    
    // QUERY to retrieve comments about subject
    List<Comment> getSubjectComments(int subjectID){
        List<Comment> list = new ArrayList<>();
        try {
            statement.execute(selectionSubjectCommentString);
            result = statement.getResultSet();

            while(result.next()) {
            	if(result.getInt("subjectId") == subjectID)
                list.add(new SubjectComment(result.getInt("id"), result.getString("text"),
                    result.getInt("userId"),result.getInt("subjectId"),result.getDate("date")));
            }
        } 
        catch (SQLException e) {e.printStackTrace();}
        return list;   
    }
    
    //QUERY to retrieve degree courses.
    List<String> getDegreeCourses(){
        List<String> list = new ArrayList<>();
        try {
        	
            statement.execute(selectionDegreeProgrammesString);
            result = statement.getResultSet();

            while(result.next()) 
                list.add(result.getString("name"));
        } 
        catch (SQLException e) {e.printStackTrace();}
        return list;   
    }
    
    //QUERY to retrieve degreeId.
    int getDegreeId(String s){
    	int degreeId = -1;
        try {
        	selectionDegreeIdStatement.setString(1, s);
        	result = selectionDegreeIdStatement.executeQuery();
        	 if(!result.first()) {
                 return -1;
             }
            degreeId = result.getInt("Id");
        } 
        catch (SQLException e) {e.printStackTrace();}
        return degreeId;
    }
    
    String getDegreeName(int id) {
    	String name;
    	try {
        	selectionDegreeNameStatement.setInt(1, id);
        	result = selectionDegreeNameStatement.executeQuery();
        	if(!result.first()) {
        		return null;
        	} else {
	            name = result.getString("name");
	            return name;
        	}
        } 
        catch (SQLException e) {e.printStackTrace();}
    	return null;
    }
    
    // QUERY to retrieve comments about prof
    List<ProfessorComment> getProfessorComments(int profID){
        List<ProfessorComment> list = new ArrayList<>();
        try {
            statement.execute(selectionProfCommentString);
            result = statement.getResultSet();

            while(result.next()) {
            	if(result.getInt("professorID") == profID) {
            		            
	                list.add(new ProfessorComment(result.getInt("id"), result.getString("text"),
	                    result.getInt("userId"), result.getInt("professorId"), 
	                    result.getDate("date")));
            	}
            }
        } 
        catch (SQLException e) {e.printStackTrace();}
        return list;   
    }
    


    // QUERY for login
    Student checkUser(String name, String pwd) {
        try {
            selectionUserStatement.setString(1, name);
            selectionUserStatement.setString(2, pwd);
            result = selectionUserStatement.executeQuery();

            if(!result.first()) {
                return null;
            }else{
                return new Student(result.getInt("Id"), result.getString("Username"), result.getInt("degree"), result.getBoolean("admin"));
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
    
    
    
 // QUERY per l'aggiornamento di un commento del prof
    void updateCommentProf(int i, String t, int s) {
        try {
        	PreparedStatement ps = connection.prepareStatement("UPDATE `prof_comments` SET `text` = ? WHERE `prof_comments`.`id` = ?"
        															+ " AND `prof_comments`.`userId` = ?;");
            ps.setString(1, t);
            ps.setInt(2, i);
            ps.setInt(3, s);
            
            System.out.println(ps.executeUpdate());            
            
        } 
        catch (SQLException e) {e.printStackTrace();}
    }

    
    
    
    
    // QUERY per l'aggiornamento di un commento della materia
    void updateCommentSubject(int i, String t, int s) {
    	
        try {
        	PreparedStatement ps = connection.prepareStatement("UPDATE `subject_comments` SET `text` = ? WHERE `subject_comments`.`id` = ? "
        																	+ "AND `subject_comments`.`userId` = ?;");
            ps.setString(1, t);
            ps.setInt(2, i);
            ps.setInt(3, s);
            System.out.println(i + " " + t + " " + s);
            ps.executeUpdate();
        } 
        catch (SQLException e) {e.printStackTrace();}
    }
    
    
    
    // QUERY per l'eliminazione di un commento del prof
       void deleteCommentProf(int i, int s, boolean a) {
           try {
        	   PreparedStatement ps;
        	   if(!a) {
	    		   ps = connection.prepareStatement("DELETE FROM `prof_comments` " + 
	    				   													"WHERE `prof_comments`.`id` = ? " + 
	    				   														"AND `prof_comments`.`userId` = ?;");
	    		   
	    		   ps.setInt(1, i);
	               ps.setInt(2, s);
	               
    		   } else {
    			   ps = connection.prepareStatement("DELETE FROM `prof_comments` WHERE `prof_comments`.`id` = ?;");
    			   ps.setInt(1, i);
    		   }
        	   
        	   System.out.println(ps.executeUpdate());  
        	   ps.executeUpdate();
               
           } 
           catch (SQLException e) {e.printStackTrace();}
       }

       
       
       
       
       // QUERY per l'eliminazione di un commento della materia
       void deleteCommentSubject(int i, int s, boolean a) {
       	
    	   try {
    		   PreparedStatement ps;
    		   if(!a) {
	    		   ps = connection.prepareStatement("DELETE FROM `subject_comments` " + 
	    				   													"WHERE `subject_comments`.`id` = ? " + 
	    				   														"AND `subject_comments`.`userId` = ?;");
	    		   ps.setInt(1, i);
	               ps.setInt(2, s);
	               
    		   } else {
    			   ps = connection.prepareStatement("DELETE FROM `subject_comments` WHERE `subject_comments`.`id` = ?;");
    			   ps.setInt(1, i);  
    		   }
    		   System.out.println(ps.executeUpdate());
    		   
			   
              
           } 
           catch (SQLException e) {e.printStackTrace();}
       }
       
       void insertProfessor(String n, String s, String i) {
    	   try {
	    	   	PreparedStatement ps = connection.prepareStatement("INSERT INTO professors (name, surname, info) VALUES (?, ?, ?); ");
				ps.setString(1, n);
				ps.setString(2, s);
				ps.setString(3, i);
				
				System.out.println("rows affected: "+ ps.executeUpdate());
    	   }
    	   catch (SQLException e) {e.printStackTrace();}
       }
       
       void insertSubject(String n, int c, String i, int id) {
    	   try {
	    	   	PreparedStatement ps = connection.prepareStatement("INSERT INTO subjects (name, credits, degree, info) VALUES (?, ?, ?, ?); ");
				ps.setString(1, n);
				ps.setInt(2, c);
				ps.setInt(3, id);
				ps.setString(4, i);
				
				System.out.println("rows affected: "+ ps.executeUpdate());
    	   }
    	   catch (SQLException e) {e.printStackTrace();}
       }

    // stop connection
    void quit() {
        try{
            connection.close();
        } 
        catch (SQLException e) {e.printStackTrace();}
    }
}

