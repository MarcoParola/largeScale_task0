package task0;

import java.util.Date;
import javafx.beans.property.*;

public abstract class Comment {

    private final SimpleIntegerProperty id;
    private final SimpleStringProperty text;
    private final SimpleIntegerProperty student;
    private final SimpleStringProperty date;

    public Comment(int i, String t, int s, Date d) {
        id = new SimpleIntegerProperty(i);
        text = new SimpleStringProperty(t);
        student = new SimpleIntegerProperty(s);
        date = new SimpleStringProperty(d.toString());
    }
    
    public int getId(){
        return id.get();
    }
    
    public String getText(){
        return text.get();
    }
    
    public int getStudent(){
        return student.get();
    }
    
    public String getDate(){
        return date.get();
    }
    
    public void setId(int i){ id.set(i); }
    public void setText(String t){ text.set(t); }
    public void setStudent(int s){ student.set(s); }
    public void setDate(Date d){ date.set(d.toString()); }
}