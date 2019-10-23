package task0;

import java.util.Date;
import javafx.application.*;
import static javafx.application.Application.launch;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import static javafx.scene.control.TableView.*;
import javafx.scene.control.cell.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.*;

public class GraphicInterface extends Application {
    
    Label usernameLab, passwordLab;
    TextField username, password;
    TextArea info, comment;
    Button loginBtn, commentBtn, deleteBtn, updateBtn;;
    ChoiceBox choose;
    TableView table, comments;
    TableColumn idProfColumn, nameProfColumn, surnameProfColumn, idSubjectColumn, nameSubjectColumn, creditSubjectColumn;
    TableColumn textColumn, dateColumn;
    HBox loginBox, commentBox;
    VBox leftBox, rightBox;
    Group root;
    
    DBManager manager;
    ObservableList<Professor> professorsList;
    ObservableList<Subject> subjectsList;
    ObservableList<Comment> commentsList;
    
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
        deleteBtn = new Button("Delete");
        updateBtn = new Button("Update");
        
        loginBox = new HBox(10);
        loginBox.getChildren().addAll(usernameLab,username,passwordLab,password,loginBtn);
        
        //box on the left
        info = new TextArea("Informations about prof/subjects are visualized here");
        info.setWrapText(true);
        prepareComments();
        
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

                if((choose.getItems().get((Integer) newValue)) == "Subjects") {
                	setProfessorComments(-1);
                	setSubjectsList();
                }
                else if((choose.getItems().get((Integer) newValue)) == "Professors") {
                	setSubjectComments(-1);
                	setProfessorsList();
                }
                        
                
                info.setText("");
                
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
        commentBox.getChildren().addAll(comment,commentBtn, deleteBtn, updateBtn);
        
        root = new Group();
        root.getChildren().addAll(loginBox, leftBox, rightBox, commentBox);
        
        Scene scene = new Scene(root, 1110, 700);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        setPositions();
        setSizes();
        setCssStyle();
        
        table.setOnMouseClicked((MouseEvent ev) -> {
            String type = (String) choose.getValue();
            Object o = table.getSelectionModel().getSelectedItem();
            if(o != null){
                if("Professors".equals(type)){
                    Professor p = (Professor)o;
                    info.setText(p.getInfo());
                    setProfessorComments(p.getId());
                }else{
                    Subject s = (Subject)o;
                    info.setText(s.getInfo());
                    setSubjectComments(s.getId());
                }
            }
        });
        
        commentBtn.setOnAction((ActionEvent ev)->{
            if(student != null) {
	            if("Subjects".equals((String)choose.getValue())){
	                Subject s = (Subject) table.getSelectionModel().getSelectedItem();
	                SubjectComment c = new SubjectComment(0,comment.getText(),student.getId(),s.getId(),new Date());
	                manager.insertCommentSubject(c.getStudent(), c.getSubject(), c.getText());
	                this.setSubjectComments(s.getId());
	            }else{
	                Professor p = (Professor) table.getSelectionModel().getSelectedItem();
	                ProfessorComment c = new ProfessorComment(0,comment.getText(),student.getId(),p.getId(),new Date());
	                manager.insertCommentProf(c.getStudent(), c.getProfessor(), c.getText());
	                this.setProfessorComments(p.getId());
	            }
	            
	            this.comment.setText("");
	    
            }else 
            	System.out.println("You have to login!\n");
            
        });
        
        updateBtn.setOnAction((ActionEvent ev)->{
            if(student != null) {
	            if("Subjects".equals((String)choose.getValue())){
	                Subject s = (Subject) comments.getSelectionModel().getSelectedItem();
	                SubjectComment sc = (SubjectComment) comments.getSelectionModel().getSelectedItem();
	                
	                System.out.println(sc.getId());
	                
	                manager.updateCommentSubject(sc.getId(), sc.getText());
	                
	                this.setSubjectComments(s.getId());
	            }else{
	                Professor p = (Professor) table.getSelectionModel().getSelectedItem();
	                ProfessorComment c = (ProfessorComment) comments.getSelectionModel().getSelectedItem();
	                
	                System.out.println(c.getId());
	                
	                manager.updateCommentProf(c.getId(), c.getText());
	                
	                this.setProfessorComments(p.getId());
	            }
	    
            }else 
            	System.out.println("You have to login!\n");
            
        });
        
        deleteBtn.setOnAction((ActionEvent ev)->{
        	if(student != null) {
	            if("Subjects".equals((String)choose.getValue())){
	                Subject s = (Subject) comments.getSelectionModel().getSelectedItem();
	                SubjectComment sc = (SubjectComment) comments.getSelectionModel().getSelectedItem();
	                
	                System.out.println(sc.getId());
	                
	                manager.deleteCommentSubject(sc.getId());
	                
	                this.setSubjectComments(s.getId());
	            }else{
	                Professor p = (Professor) table.getSelectionModel().getSelectedItem();
	                ProfessorComment c = (ProfessorComment) comments.getSelectionModel().getSelectedItem();
	                
	                System.out.println(c.getId());
	                
	                manager.deleteCommentProf(c.getId());
	                
	                this.setProfessorComments(p.getId());
	            }
	    
            }else 
            	System.out.println("You have to login!\n");
            
        });
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
        choose.setMinSize(150,30);
        table.setMinHeight(480);
        rightBox.setMinSize(500, 550);
        comment.setMaxHeight(50);        
    }
        
    // STYLE
    private void setPositions(){
        loginBox.setLayoutX(10); loginBox.setLayoutY(10);
        leftBox.setLayoutX(20); leftBox.setLayoutY(70);
        rightBox.setLayoutX(leftBox.getWidth()+40); rightBox.setLayoutY(70);
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
    
    private void prepareComments(){
        commentsList = FXCollections.observableArrayList(); 
        
        comments = new TableView<>();
        comments.setEditable(true);
        
        textColumn = new TableColumn("Text");
        textColumn.setCellValueFactory(new PropertyValueFactory<Comment, String>("text"));
        dateColumn = new TableColumn("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<Comment, Date>("date"));
        
    	comments.getColumns().addAll(textColumn, dateColumn);
        comments.setItems(commentsList);
        comments.setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        
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
    	comments.setItems(commentsList);
    }
    
    private void setProfessorComments(int profID){
        commentsList.clear();
        commentsList.addAll(manager.getProfessorComments(profID));
    }
    
    private void setSubjectComments(int subID){
        commentsList.clear();
        commentsList.addAll(manager.getSubjectComments(subID));
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

