package task0;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Application {

	static DBManager manager;
	private Student loggedStudent;
	
	ArrayList <Professor> professors;
	ArrayList <Subject> subjects;
	
	public static void main(String [] args) {
		
		manager = new DBManager();
		
		List <Professor> l = manager.getProfessors();
		
		for(int i=0; i< l.size(); i++)
			System.out.println(l.get(i).getName());
		
		/*ResultSet r = manager.getSubjects();
		
		try {
			while(r.next()) {
				System.out.print(r.getInt("Id") + ": ");
				System.out.println(r.getString("name"));
			}
		} 
		catch (SQLException e) {e.printStackTrace();}
		*/
		manager.quit();
	}
	
	
	// chiamare questa funzione quando un utente prova a loggarsi
	private void login(String name, String pwd) {
		
		// TODO
		manager.checkUser(name, pwd);
	}
}
