package task0;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Student extends Person{

    private final SimpleBooleanProperty admin;
    private final SimpleStringProperty username;
    private final SimpleObjectProperty<Degree> degree;
    // CONSTRUCTOR
    public Student(int i, String u, Degree d, boolean a) {
        super(i);
        admin = new SimpleBooleanProperty(a);
        username = new SimpleStringProperty(u);
        degree = new SimpleObjectProperty(d);		
    }
    public boolean getAdmin() {
            return admin.get();
    }
    public String getUsername() {
            return username.get();
    }

    public Degree getDegree() {
            return degree.getValue();
    }
    public void setDegree(Degree d) {
            degree.set(d);
    }
	
}
