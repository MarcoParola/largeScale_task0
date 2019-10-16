package task0;

import java.util.Date;

public class ProfessorComment extends Comment{

	int professor;
	
	// CONSTRUCTOR
	public ProfessorComment(int i, String t, int s, int p, Date d) {
		
		super(i, t, s, d);
		professor = p;
	}

}
