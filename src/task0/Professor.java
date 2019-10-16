package task0;

import java.util.ArrayList;

public class Professor extends Person{

	ArrayList <ProfessorComment> comments;
	
	
	// CONSTRUCTOR
	public Professor(int i, String n, String s) {
		super(i, n, s);
		
		comments = new ArrayList <ProfessorComment>();
		
	}

}
