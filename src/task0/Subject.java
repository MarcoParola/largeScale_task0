package task0;

import javafx.beans.property.*;

public class Subject {

	private final SimpleIntegerProperty id;
	private final SimpleIntegerProperty credits;
	
	
	// CONSTRUCTOR
	public Subject(int i, int c) {
		id = new SimpleIntegerProperty(i);
		credits = new SimpleIntegerProperty(c);
	}
}
