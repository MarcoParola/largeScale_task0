package task0;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Student extends Person{

	private final SimpleBooleanProperty admin;
	private final SimpleStringProperty username;
	// CONSTRUCTOR
	public Student(int i, String u, int d, boolean a) {
		super(i, d);
		admin = new SimpleBooleanProperty(a);
		username = new SimpleStringProperty(u);
		
	}
	public boolean getAdmin() {
		return admin.get();
	}
	public String getUsername() {
		return username.get();
	}
	
}
