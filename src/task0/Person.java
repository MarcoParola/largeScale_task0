package task0;

import javafx.beans.property.*;

public abstract class Person {
	
	private final SimpleIntegerProperty id;
	private final SimpleStringProperty name;
	private final SimpleStringProperty surname;
	private final SimpleIntegerProperty degree;
	
	
	// CONSTRUCTOR
	public Person (int i, String n, String s, int d) {
		id = new SimpleIntegerProperty(i);
		name = new SimpleStringProperty(n);
		surname = new SimpleStringProperty(s);
		degree = new SimpleIntegerProperty(d);
	}
	
	public int getId() {
		return id.intValue();
	}
	public void setId(int i) {
		id.set(i);
	}
	
	
	public String getName() {
		return name.getValue();
	}
	public void setName(String n) {
		name.set(n);
	}
	
	
	public String getSurname() {
		return surname.getValue();
	}
	public void setSurname(String s) {
		surname.set(s);
	}
	
	
	public int getDegree() {
		return degree.getValue();
	}
	public void setDegree(int d) {
		degree.set(d);
	}
}
