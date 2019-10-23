package task0;

import java.util.Date;
import javafx.beans.property.*;

public class SubjectComment extends Comment{

    private final SimpleIntegerProperty subject;

    // CONSTRUCTOR
    public SubjectComment(int i, String t, int s, int sub, Date d) {

            super(i, t, s, d);
            subject = new SimpleIntegerProperty(sub);
    }
    
    public int getSubject(){ return subject.get(); }
    public void setSubject(int s){ subject.set(s); }
}
