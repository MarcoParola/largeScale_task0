package task0;

import java.sql.*;
import java.util.ArrayList;

public class Application {

	static DBManager manager;
	private Student loggedStudent;
	
	ArrayList <Professor> professors;
	ArrayList <Subject> subjects;
	
	public static void main(String [] args) {
		
		manager = new DBManager();
		
		ResultSet r = manager.getSubjects();
		
		try {
			while(r.next()) {
				System.out.print(r.getInt("Id") + ": ");
				System.out.println(r.getString("name"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		manager.quit();
	}
	
	
	private void login() {
		
	}
}
