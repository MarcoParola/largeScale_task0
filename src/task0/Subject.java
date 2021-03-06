package task0;

import javafx.beans.property.*;

public class Subject {

	private final SimpleIntegerProperty id;
	private final SimpleStringProperty name;
	private final SimpleIntegerProperty credits;
	private final SimpleStringProperty info;
	private final SimpleIntegerProperty degree;
	
	
	// CONSTRUCTOR
	public Subject(int i, String n, int c, String inf, int d) {
		id = new SimpleIntegerProperty(i);
		name = new SimpleStringProperty(n);
		credits = new SimpleIntegerProperty(c);
		info = new SimpleStringProperty(inf);
		degree = new SimpleIntegerProperty(d);
	}
	
	public int getId() {
		return id.getValue();
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
	
	
	public int getCredits() {
		return credits.getValue();
	}
	public void setCredits(int c) {
		credits.set(c);
	}
	
	
	public String getInfo() {
		return info.getValue();
	}
	public void setInfo(String i) {
		info.set(i);
	}
	
	
	public int getDegree() {
		return degree.getValue();
	}
	public void setDegree(int d) {
		degree.set(d);
	}
}
