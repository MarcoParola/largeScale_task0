package task0;

import java.sql.*;
import java.util.*;
import javafx.application.*;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.*;


public class Interfaccia extends Application {
	
    Label usernameLab, passwordLab;
    TextField username, password;
    TextArea info, comment;
    Button loginBtn, commentBtn;
    ChoiceBox choose;
    TableView table, comments;
    TableColumn idProfColumn, nameProfColumn, surnameProfColumn, idSubjectColumn, nameSubjectColumn, creditSubjectColumn;
    HBox loginBox, commentBox;
    VBox leftBox, rightBox;
    Group root;
    
    DBManager manager;
    ObservableList<Professor> professorsList;
    ObservableList<Subject> subjectsList;
    
    Student student;
    
    
    
    @Override
    public void start(Stage primaryStage) {
    	
    	manager = new DBManager();
    	
        //box on the top
        usernameLab = new Label("Username:");
        username = new TextField();
        passwordLab = new Label("Password:");
        password = new TextField();
        loginBtn = new Button("Login");
        
        loginBox = new HBox(10);
        loginBox.getChildren().addAll(usernameLab,username,passwordLab,password,loginBtn);
        
        //box on the left
        info = new TextArea("Informations about prof/subjects are visualized here");
        comments = new TableView<>();
        leftBox = new VBox(20);
        leftBox.getChildren().addAll(info,comments);
        
        //box on the right
        choose = new ChoiceBox();
        choose.getItems().add("Professors");
        choose.getItems().add("Subjects");
        choose.getSelectionModel().selectFirst();
        
        
        // add event to choiceBox changing selection value
        choose.getSelectionModel().selectedIndexProperty().addListener((ChangeListener<? super Number>) new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				
					if((choose.getItems().get((Integer) newValue)) == "Subjects")
						setSubjectsList();
					else if((choose.getItems().get((Integer) newValue)) == "Professors")
						setProfessorsList();
					
					
				System.out.println(choose.getSelectionModel().getSelectedIndex());
			}
          });
        
       
        
        prepareTable();
        
        loginBtn.setOnAction((ActionEvent e) -> loginAction());
        
        
      
        table.lookup(".scroll-bar:vertical"); 
        
        rightBox = new VBox(10);
        rightBox.getChildren().addAll(choose,table);
        
        //box on the bottom
        comment = new TextArea("Leave a comment here...");
        commentBtn = new Button("Comment");
        
        commentBox = new HBox(10);
        commentBox.getChildren().addAll(comment,commentBtn);
        
        root = new Group();
        root.getChildren().addAll(loginBox, leftBox, rightBox, commentBox);
        
        Scene scene = new Scene(root, 800, 700);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        setPositions();
        setSizes();
        setCssStyle();
    }

    
    
    // STYLE
    private void setCssStyle(){
        root.setStyle("-fx-color:orange; -fx-font-size: 14; -fx-font-family: Arial");
    }
    
    
    
    // STYLE
    private void setSizes(){
        loginBtn.setMinWidth(80);
        info.setMaxSize(540,100);
        comments.setMaxSize(540, 450);
        leftBox.setMaxSize(420, 560);
        choose.setMinSize(150,30);
        table.setMinHeight(480);
        rightBox.setMinSize(300, 550);
        comment.setMaxHeight(50);        
    }
    
    
    
    // STYLE
    private void setPositions(){
        loginBox.setLayoutX(10);
        leftBox.setLayoutX(20); leftBox.setLayoutY(60);
        rightBox.setLayoutX(leftBox.getWidth()); rightBox.setLayoutY(60);
        commentBox.setLayoutX(20); commentBox.setLayoutY(630);        
    }
    
    
    
    // prepare table for professors and subjects
    private void prepareTable() {
    	
    	professorsList = FXCollections.observableArrayList(); 
        professorsList.addAll(manager.getProfessors());        
        
        subjectsList = FXCollections.observableArrayList(); 
        subjectsList.addAll(manager.getSubjects());
        
        table = new TableView<>();
        table.setEditable(true);
        
        idProfColumn = new TableColumn("ID");
        idProfColumn.setCellValueFactory(new PropertyValueFactory<Professor, String>("id"));
        
        nameProfColumn = new TableColumn("NAME");
        nameProfColumn.setCellValueFactory(new PropertyValueFactory<Professor, String>("name"));
        
        surnameProfColumn = new TableColumn("SURNAME");
        surnameProfColumn.setCellValueFactory(new PropertyValueFactory<Professor, String>("surname"));
                
        idSubjectColumn = new TableColumn("ID");
        idSubjectColumn.setCellValueFactory(new PropertyValueFactory<Subject, String>("id"));
        
        nameSubjectColumn = new TableColumn("NAME");
        nameSubjectColumn.setCellValueFactory(new PropertyValueFactory<Subject, String>("name"));
        
        creditSubjectColumn = new TableColumn("CREDITS");
        creditSubjectColumn.setCellValueFactory(new PropertyValueFactory<Subject, String>("credits"));
        
        setProfessorsList();
       
    }
    
    
    // update table: set professors information
    private void setProfessorsList() {
		table.getColumns().clear();
    	table.setItems(professorsList);
    	table.getColumns().addAll(idProfColumn, nameProfColumn, surnameProfColumn);
    }
    
    
    
    // update table: set subjects information
    private void setSubjectsList() {
    	table.getColumns().clear();
    	table.setItems(subjectsList);
    	table.getColumns().addAll(idSubjectColumn, nameSubjectColumn, creditSubjectColumn);
    }
    
    
    
    // function for click on loginButton
    private void loginAction() {
    	student = manager.checkUser(username.getText(), password.getText());
    	
    	if(student != null) {
            
            int i = 0;
            while(i < subjectsList.size()) {
            	if(subjectsList.get(i).getDegree() == student.getDegree())
            		i++;
            	else
            		subjectsList.remove(i);
            	
            }
            
            i = 0;
            while(i < professorsList.size()) {
            	if(professorsList.get(i).getDegree() == student.getDegree())
            		i++;
            	else
            		professorsList.remove(i);
            }
                          
            if(choose.getSelectionModel().getSelectedIndex() == 1)
				setSubjectsList();
			else if(choose.getSelectionModel().getSelectedIndex() == 0)
				setProfessorsList();
    	}
    }
    
    
    
    // MAIN
    public static void main(String[] args) {
        launch(args);
    }   
}

