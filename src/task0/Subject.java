package task0;

import javafx.beans.property.*;

public class Subject {

	private final SimpleIntegerProperty id;
	private final SimpleIntegerProperty credits;
	private final SimpleStringProperty info;
	
	
	// CONSTRUCTOR
	public Subject(int i, int c, String inf) {
		id = new SimpleIntegerProperty(i);
		credits = new SimpleIntegerProperty(c);
		info = new SimpleStringProperty(inf);
	}
	
	int getId() {
		return id.intValue();
	}
	
	int getCredits() {
		return credits.intValue();
	}
	
	String getInfo() {
		return String.valueOf(info);
	}
}
