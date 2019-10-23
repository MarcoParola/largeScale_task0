package task0;

import java.util.Date;
import javafx.beans.property.*;

public class ProfessorComment extends Comment{

    private final SimpleIntegerProperty professor;

    // CONSTRUCTOR
    public ProfessorComment(int i, String t, int s, int p, Date d) {
        super(i, t, s, d);
        professor = new SimpleIntegerProperty(p);
    }
    
    public int getProfessor(){ return professor.get(); }
    public void setProfessor(int p){ professor.set(p); }
}