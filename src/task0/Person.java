package task0;

import javafx.beans.property.*;

public abstract class Person {
	
	private final SimpleIntegerProperty id;
	private final SimpleStringProperty name;
	private final SimpleStringProperty surname;
	
	
	// CONSTRUCTOR
	public Person (int i, String n, String s) {
		id = new SimpleIntegerProperty(i);
		name = new SimpleStringProperty(n);
		surname = new SimpleStringProperty(s);
	}
	
	int getId() {
		return id.intValue();
	}
	
	String getName() {
		return String.valueOf(name);
	}
	
	String getSurname() {
		return String.valueOf(surname);
	}
}
