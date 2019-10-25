package task0;

import javafx.beans.property.*;

public abstract class Person {
	
	private final SimpleIntegerProperty id;
	private final SimpleIntegerProperty degree;
	
	
	// CONSTRUCTOR
	public Person (int i, int d) {
		
		id = new SimpleIntegerProperty(i);
		degree = new SimpleIntegerProperty(d);
	}
	
	public int getId() {
		return id.intValue();
	}
	public void setId(int i) {
		id.set(i);
	}
	
	
	public int getDegree() {
		return degree.getValue();
	}
	public void setDegree(int d) {
		degree.set(d);
	}
}
