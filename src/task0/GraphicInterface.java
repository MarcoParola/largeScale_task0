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
    
    Label usernameLab, passwordLab, nameLab, surnameAndCreditsLab, infoLab, studentLab, profIdLab;
    TextField username, password, name, surnameAndCredits, profId;
    TextArea info, comment, addInfo;
    Button loginBtn, logoutBtn, commentBtn, deleteBtn, updateBtn, addBtn, editBtn, deleteBtnProfSubj;
    ChoiceBox<String> choosePS, chooseDegree; //PS -> ProfessorSubject
    HBox loginBox, logoutBox, commentBox, adminButtonsBox, FieldsAdminBox, chooseBox;
    VBox leftBox, rightBox, nameSurnameBox, InfoVBox, profIdBox;
    Group root;
    
    DBManager manager;
    ObservableList<String> degreeList;
    
    Student student;
    
    CommentTable comments;
    ProfSubjectTable table;
    
    @Override
    public void start(Stage primaryStage) {
    	
    	manager = new DBManager();
    	table = new ProfSubjectTable(this);
    	comments = new CommentTable(this);
    	 
        //login box on the top
    	usernameLab = new Label("Username:");
        username = new TextField();
        passwordLab = new Label("Password:");
        password = new TextField();
        loginBtn = new Button("Login");
        
        loginBox = new HBox(10);
        loginBox.getChildren().addAll(usernameLab,username,passwordLab,password,loginBtn);
       
        //logout box on the top
        studentLab = new Label("");
        logoutBtn = new Button("Logout");
        //user not logged, hide logoutbox
        
        logoutBox = new HBox(10);
        logoutBox.getChildren().addAll(studentLab, logoutBtn);
        logoutBox.setVisible(false);
        
        //box on the left
        info = new TextArea("Informations about Professors are visualized here");
        info.setWrapText(true);
        info.setEditable(false);
        
        leftBox = new VBox(10);
        leftBox.getChildren().addAll(info,comments);
          
        //info Vbox creation for the admin
        infoLab = new Label("Professor Informations:");
        addInfo = new TextArea("");
        addInfo.setWrapText(true);
        
        InfoVBox = new VBox(10);
        InfoVBox.getChildren().addAll(infoLab,addInfo);
        
        //name and surname/credits about professor/subject VBox for the admin
        nameLab = new Label("Name:");
        name = new TextField();
        surnameAndCreditsLab = new Label("Surame:");
        surnameAndCredits = new TextField();       
        
        nameSurnameBox = new VBox(10);
        nameSurnameBox.getChildren().addAll(nameLab,name, surnameAndCreditsLab, surnameAndCredits ); 
        
        //id prof vbox for admin
        profIdLab = new Label("Professor Id:");
        profId = new TextField();
        
        profIdBox = new VBox(10);
        profIdBox.getChildren().addAll(profIdLab, profId);
        
        //Hbox with the 3 vbox created before and the add button.
        addBtn = new Button("Add");
        FieldsAdminBox = new HBox(10);
        FieldsAdminBox.getChildren().addAll(nameSurnameBox, InfoVBox, addBtn);
        FieldsAdminBox.setVisible(false);
        
        //box on the right
        choosePS = new ChoiceBox<>(); //Prof/subj choice box
        choosePS.getItems().add("Professors");
        choosePS.getItems().add("Subjects");
        choosePS.getSelectionModel().selectFirst();
        
        chooseDegree = new ChoiceBox<>(); //degree choice box
        degreeList = FXCollections.observableArrayList();
        degreeList.addAll(manager.getDegreeCourses());
        chooseDegree.setItems(degreeList);
        chooseDegree.getItems().add(0, "All");
        chooseDegree.getSelectionModel().selectFirst();
        
        table.setProfessorsList(manager.getProfessors(-1));
       
        // add event to choiceProfSubject changing selection value
        choosePS.getSelectionModel().selectedIndexProperty().addListener((ChangeListener<? super Number>) new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                if((choosePS.getItems().get((Integer) newValue)) == "Subjects") {
                	comments.setProfessorComments(-1);
                	table.setSubjectsList(manager.getSubjects());
                }
                else if((choosePS.getItems().get((Integer) newValue)) == "Professors") {
                	comments.setSubjectComments(-1);
                	table.setProfessorsList(manager.getProfessors(-1));
                }
                info.setText("Informations about " + choosePS.getItems().get((Integer) newValue) +" are visualized here");  
            }
        });
       
        // add event to choiceDegree changing selection value 
        chooseDegree.getSelectionModel().selectedIndexProperty().addListener((ChangeListener<? super Number>) new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {      	
            	  
            	if((chooseDegree.getItems().get((Integer) newValue)) == "All"){
            		if("Subjects".equals((String)choosePS.getValue())){
                		table.setSubjectsList(manager.getSubjects());
                	} else {
                		table.setProfessorsList(manager.getProfessors(-1));
                	}  
            	} else {
            		int degreeId = manager.getDegreeId((String)(chooseDegree.getItems().get((Integer) newValue)));
            		table.updateSubjectProfListLogin(degreeId);
            		if("Subjects".equals((String)choosePS.getValue())){
                		table.setSubjectsList(manager.getSubjects());
                	} else {
                		table.setProfessorsList(manager.getProfessors(degreeId));
                	}  
            	}
            }
        });     
        
        //admin buttons
    	editBtn = new Button("Edit");
    	deleteBtnProfSubj = new Button("Delete");
    	
    	adminButtonsBox = new HBox(10);
        adminButtonsBox.getChildren().addAll(editBtn, deleteBtnProfSubj);
        adminButtonsBox.setVisible(false);
        
        loginBtn.setOnAction((ActionEvent e) -> loginAction());       
        logoutBtn.setOnAction((ActionEvent e) -> logoutAction());
        
        table.lookup(".scroll-bar:vertical"); 
        
        chooseBox = new HBox(10);
        chooseBox.getChildren().addAll(choosePS, chooseDegree);
        
        rightBox = new VBox(10);
        rightBox.getChildren().addAll(chooseBox,table);
        
        //box on the bottom
        comment = new TextArea("Leave a comment here...");
        commentBtn = new Button("Comment");
        deleteBtn = new Button("Delete");
        updateBtn = new Button("Update");
        
        commentBox = new HBox(10);
        commentBox.getChildren().addAll(comment,commentBtn, deleteBtn, updateBtn);
        
        root = new Group();
        root.getChildren().addAll(loginBox, logoutBox, leftBox, rightBox, adminButtonsBox, commentBox, FieldsAdminBox);
        
        Scene scene = new Scene(root, 1210, 700);
        primaryStage.setTitle("Students Evaluations");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        setPositions();
        setSizes();
        setCssStyle();
        
        //choice box selection 		
        table.setOnMouseClicked((MouseEvent ev) -> {
            String type = (String) choosePS.getValue();
            Object o = table.getSelectionModel().getSelectedItem();
            if(o != null){
                if("Professors".equals(type)){
                    Professor p = (Professor)o;
                    info.setText(p.getInfo());
                    comments.setProfessorComments(p.getId());
                    
                }else{
                    Subject s = (Subject)o;
                    info.setText(s.getInfo());
                    comments.setSubjectComments(s.getId());
                    
                }
            }
        });
        
        //add comment 
        commentBtn.setOnAction((ActionEvent ev)->{
            if(student != null) {
	            if("Subjects".equals((String)choosePS.getValue())){
	                Subject s = (Subject) table.getSelectionModel().getSelectedItem();
	                SubjectComment c = new SubjectComment(0, comment.getText(),student.getId(),s.getId(),new Date());
	                manager.insertCommentSubject(c.getStudent(), c.getSubject(), c.getText());
	                comments.setSubjectComments(s.getId());
	            }else{
	                Professor p = (Professor) table.getSelectionModel().getSelectedItem();
	                ProfessorComment c = new ProfessorComment(0,comment.getText(),student.getId(),p.getId(),new Date());
	                manager.insertCommentProf(c.getStudent(), c.getProfessor(), c.getText());
	                comments.setProfessorComments(p.getId());
	            }
	            this.comment.setText("");
	    
            }else 
            	System.out.println("You have to login!\n");
        });
        
        //update comment
         updateBtn.setOnAction((ActionEvent ev)->{
            if(student != null) {
	            if("Subjects".equals((String)choosePS.getValue())){
	                Subject s = (Subject) table.getSelectionModel().getSelectedItem();
	                SubjectComment sc = (SubjectComment) comments.getSelectionModel().getSelectedItem();
	            
	                manager.updateCommentSubject(sc.getId(), comment.getText(), student.getId());
	                
	                comments.setSubjectComments(s.getId());
	            }else{
	                Professor p = (Professor) table.getSelectionModel().getSelectedItem();
	                ProfessorComment c = (ProfessorComment) comments.getSelectionModel().getSelectedItem();
	                
	                manager.updateCommentProf(c.getId(), comment.getText(), student.getId());
	                
	                comments.setProfessorComments(p.getId());
	            }
            }else 
            	System.out.println("You have to login!\n");
        });
        
        //delete comment
        deleteBtn.setOnAction((ActionEvent ev)->{
        	if(student != null) {
	            if("Subjects".equals((String)choosePS.getValue())){
	                Subject s = (Subject) table.getSelectionModel().getSelectedItem();
	                SubjectComment sc = (SubjectComment) comments.getSelectionModel().getSelectedItem();
	                manager.deleteCommentSubject(sc.getId(),student.getId(), student.getAdmin());
	                comments.setSubjectComments(s.getId());
	            }else{
	                Professor p = (Professor) table.getSelectionModel().getSelectedItem();
	                ProfessorComment c = (ProfessorComment) comments.getSelectionModel().getSelectedItem();
	                
	                manager.deleteCommentProf(c.getId(), student.getId(), student.getAdmin());
	                comments.setProfessorComments(p.getId());
	            }
            }else 
            	System.out.println("You have to login!\n");
        });
        
        //Add professor/course button for admin
        addBtn.setOnAction((ActionEvent ev)->{
            
            if("Subjects".equals((String)choosePS.getValue())){
            	if(!"All".equals((String)chooseDegree.getValue())) {
	            	int degreeId = manager.getDegreeId(chooseDegree.getValue());
	            	Subject s = new Subject(0, name.getText(), Integer.parseInt(surnameAndCredits.getText()),addInfo.getText(), degreeId );
	                manager.insertSubject(s.getName(), s.getCredits(), s.getInfo(), s.getDegree(), Integer.parseInt(profId.getText()));
	                name.setText("");
	                surnameAndCredits.setText("");
	                addInfo.setText("");	
	                //TODO Update subject list after insert.
            	} else {
            		System.out.println("Select a Degree program");
            	}
               
                
            }else{
            	Professor p = new Professor(0, name.getText(),surnameAndCredits.getText(),addInfo.getText(), -1);
                manager.insertProfessor(p.getName(), p.getSurname(), p.getInfo());
                name.setText("");
                surnameAndCredits.setText("");
                addInfo.setText("");   
            }   
        });
    }

   
    // STYLE
    private void setCssStyle(){
        root.setStyle("-fx-color:orange; -fx-font-size: 14; -fx-font-family: Arial");
        studentLab.setStyle("-fx-font-size: 20");
    }
    
    // STYLE
	private void setSizes(){
		logoutBtn.setMinWidth(80);//logout button
		loginBtn.setMinWidth(80);//login button
		comment.setMaxHeight(50); 
		
		//right box
		choosePS.setMinSize(150,30);
		chooseDegree.setMinSize(350,30); chooseDegree.setMaxWidth(350);
        table.setMinHeight(500);
        rightBox.setMinWidth(500);
        
        //Left Box
        leftBox.setMaxSize(540, 530);
        comments.setMaxSize(540, 430); comments.setMinHeight(430);	//tabella dei commenti
        info.setMaxSize(540,100); info.setMinSize(540, 100);		 
        
        //admin fields
        FieldsAdminBox.setMinSize(540, 100); FieldsAdminBox.setMaxWidth(640);
        addInfo.setMaxSize(285, 90);
        
        //admin buttons
        addBtn.setMinSize(60, 120);
		editBtn.setMinWidth(80);
		deleteBtnProfSubj.setMinWidth(80);
    }
        
    // STYLE
    private void setPositions(){
        loginBox.setLayoutX(10); loginBox.setLayoutY(10);
        logoutBox.setLayoutX(450); logoutBox.setLayoutY(10);
        studentLab.setLayoutY(20);
        leftBox.setLayoutX(20); leftBox.setLayoutY(70);
        FieldsAdminBox.setLayoutX(20); FieldsAdminBox.setLayoutY(515);
        rightBox.setLayoutX(leftBox.getWidth()+100); rightBox.setLayoutY(70);
        commentBox.setLayoutX(20); commentBox.setLayoutY(640);  
        adminButtonsBox.setLayoutX(900); adminButtonsBox.setLayoutY(530);
    }
    
    //STYLE updates tables size based on the actual user (admin or not)
  	private void updateSizes(){
  		if( student != null && student.getAdmin()) {
  			table.setMinHeight(400);
  			comments.setMaxSize(540, 330); comments.setMinHeight(330);
  		} else {
  			table.setMinHeight(500);
  			comments.setMaxSize(540, 430); comments.setMinHeight(430);	
  		}
  	}
  	
    
    // function for click on loginButton
    private void loginAction() {
    	student = manager.checkUser(username.getText(), password.getText());
    	
    	if(student != null) {
    		loginBox.setVisible(false);
    		logoutBox.setVisible(true);
    		studentLab.setText("User: " + student.getUsername());
            table.updateSubjectProfListLogin(student.getDegree());    
            
            //update the degree course choice box based on the student degree course.
            chooseDegree.setValue(manager.getDegreeName(student.getDegree()));
            
            //show/hide admin buttons 
            adminButtonsBox.setVisible(student.getAdmin());
            
            //updates STYLE sizes based on the logged user (admin or not)
            updateSizes();
    	}
    }
    
    private void logoutAction(){
    	chooseDegree.setValue("All");
    	logoutBox.setVisible(false);
    	loginBox.setVisible(true);
    	adminButtonsBox.setVisible(false); 
    	FieldsAdminBox.setVisible(false);
    	student = null;
    	updateSizes();
    	if("Subjects".equals((String)choosePS.getValue())){
    		table.setSubjectsList(manager.getSubjects());
    	} else {
    		table.setProfessorsList(manager.getProfessors(-1));
    	}
    }
    
    // MAIN
    public static void main(String[] args) {
        launch(args);
    }   
}