package task0;

import javafx.beans.property.*;

public class Professor extends Person{

	private final SimpleStringProperty info;
	
	
	// CONSTRUCTOR
	public Professor(int i, String n, String s, String inf, int d) {
		super(i, n, s, d);
		
		info = new SimpleStringProperty(inf);
	}
	
	
	String getInfo() {
		return info.getValue();
	}
	void setInfo(String i) {
		info.set(i);
	}

}