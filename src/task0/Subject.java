package task0;

import java.util.ArrayList;

public class Subject {

	int id;
	int credits;
	ArrayList <SubjectComment> comments;
	
	// CONSTRUCTOR
	public Subject(int i, int c) {
		id = i;
		credits = c;
		comments = new ArrayList <SubjectComment>();
	}
}
