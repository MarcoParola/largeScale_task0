package task0;

import java.sql.*;
import java.util.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Interfaccia extends Application {
    Label usernameLab, passwordLab;
    TextField username, password;
    TextArea info, comment;
    Button loginBtn, commentBtn;
    ChoiceBox choose;
    TableView table, comments;
    HBox loginBox, commentBox;
    VBox leftBox, rightBox;
    Group root;
    
    DBManager manager;
    
    
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
        table = new TableView<>();
        
        
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

    private void setCssStyle(){
        root.setStyle("-fx-color:orange; -fx-font-size: 14; -fx-font-family: Arial");
    }
    
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
    
    private void setPositions(){
        loginBox.setLayoutX(10);
        leftBox.setLayoutX(20); leftBox.setLayoutY(60);
        rightBox.setLayoutX(leftBox.getWidth()); rightBox.setLayoutY(60);
        commentBox.setLayoutX(20); commentBox.setLayoutY(630);        
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}


/*
public class Application {

	static DBManager manager;
	private Student loggedStudent;
	
	ArrayList <Professor> professors;
	ArrayList <Subject> subjects;
	
	public static void main(String [] args) {
		
		manager = new DBManager();
		
		List <Professor> l = manager.getProfessors();
		
		for(int i=0; i< l.size(); i++)
			System.out.println(l.get(i).getName());
		
		
		manager.quit();
	}
	
	
	// chiamare questa funzione quando un utente prova a loggarsi
	private void login(String name, String pwd) {
		
		// TODO
		manager.checkUser(name, pwd);
	}
}

*/
