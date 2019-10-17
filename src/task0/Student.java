package task0;

import javafx.beans.property.*;

public class Student extends Person{

	private final SimpleStringProperty password;
	
	
	// CONSTRUCTOR
	public Student(int i, String n, String s, String p) {
		super(i, n, s);
		password = new SimpleStringProperty(p);
		
	}
	
	String getPassword() {
		return String.valueOf(password);
	}

	
}
