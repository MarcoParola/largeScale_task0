package task0;

import javafx.beans.property.*;

public class Professor extends Person{

	private final SimpleStringProperty info;
	private final SimpleStringProperty name;
	private final SimpleStringProperty surname;
	
	
	// CONSTRUCTOR
	public Professor(int i, String n, String s, String inf, int d) {
		super(i, d);
		
		name = new SimpleStringProperty(n);
		surname = new SimpleStringProperty(s);
		info = new SimpleStringProperty(inf);
	}
	
	public String getName() {
		return name.get();
	}
	public void setName(String n) {
		name.set(n);
	}
	
	
	public String getSurname() {
		return surname.get();
	}
	public void setSurname(String s) {
		surname.set(s);
	}
	
	
	String getInfo() {
		return info.get();
	}
	void setInfo(String i) {
		info.set(i);
	}

}