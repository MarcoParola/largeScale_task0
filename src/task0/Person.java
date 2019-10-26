package task0;

import javafx.beans.property.*;

public abstract class Person {
	
    private final SimpleIntegerProperty id;

    // CONSTRUCTOR
    public Person (int i) {
        id = new SimpleIntegerProperty(i);
    }

    public int getId() {
        return id.intValue();
    }
    public void setId(int i) {
        id.set(i);
    }
}
