package task0;

import static javafx.scene.control.TableView.*;
import java.util.*;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;

public class CommentTable extends TableView<Comment>{

    ObservableList<Comment> commentsList;
    TableColumn textColumn, dateColumn;
    
    GraphicInterface graphic;
    
    CommentTable (GraphicInterface g){
    	graphic = g;
    	
    	commentsList = FXCollections.observableArrayList(); 
        
        
        textColumn = new TableColumn("Text");
        textColumn.setCellValueFactory(new PropertyValueFactory<Comment, String>("text"));
        dateColumn = new TableColumn("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<Comment, Date>("date"));
        
    	this.getColumns().addAll(textColumn, dateColumn);
        this.setItems(commentsList);
        this.setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        this.setEditable(true);
    }
    

    void setProfessorComments(int profID){
        commentsList.clear();
        commentsList.addAll(graphic.manager.getProfessorComments(profID));
    }
    
    void setSubjectComments(int subID){
        commentsList.clear();
        commentsList.addAll(graphic.manager.getSubjectComments(subID));
    }
}
