package task0;

import javafx.beans.property.*;

public class Professor extends Person{

	private final SimpleStringProperty info;
	
	
	// CONSTRUCTOR
	public Professor(int i, String n, String s, String inf) {
		super(i, n, s);
		
		info = new SimpleStringProperty(inf);
	}
	
	
	String getInfo() {
		return String.valueOf(info);
	}

}
