package task0;

import java.util.Date;

public class SubjectComment extends Comment{

	int subject;
	
	
	// CONSTRUCTOR
	public SubjectComment(int i, String t, int s, int sub, Date d) {
		
		super(i, t, s, d);
		subject = sub;
	}

}
