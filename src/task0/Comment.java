package task0;

import java.util.Date;

public abstract class Comment {

	int id;
	String text;
	int student;
	Date date;
	
	public Comment(int i, String t, int s, Date d) {
		id = i;
		text = t;
		student = s;
		date = d;
	}
}
