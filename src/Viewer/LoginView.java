package Viewer;
	

import Controller.LoginController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class LoginView  implements EventHandler<ActionEvent>{
	private Button login;
	private Button register;
	private TextField email;
	private PasswordField password;
	private Stage primaryStage;
	Alert notification = new Alert(AlertType.NONE);
	
	public void start() {
		try {
			primaryStage = new Stage();
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,300,320);
			primaryStage.setTitle("Login");
			GridPane gp = new GridPane();
			Label lblEmail = new Label("Email");
			Label lblPassword = new Label("Password");
			email = new TextField();
			password = new PasswordField();
			
			
			gp.add(lblEmail, 1, 2);
			gp.add(email, 1, 3);

			gp.add(lblPassword, 1, 5);
			gp.add(password, 1, 6);
			
			login = new Button("Login");
			register = new Button("Register");
			login.setOnAction(this);
			register.setOnAction(this);
			
			HBox buttons = new HBox();
			buttons.getChildren().addAll(login, register);
			buttons.setSpacing(5);
			gp.add(buttons, 1, 9);
			
			gp.setVgap(2);
			gp.setPadding(new Insets(75,0,0,70));
			root.setCenter(gp);
			primaryStage.setScene(scene);
			primaryStage.showAndWait();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handle(ActionEvent event) {
		if(event.getSource()==login) {
			LoginController lc = new LoginController();
			String valEmail = email.getText();
			String valPassword = password.getText();
			notification.setAlertType(AlertType.WARNING);
			
			if(valEmail.equals("")) {
				notification.setContentText("Email doesn't exist!");
				notification.show();
			}else if(valPassword.equals("")){
				notification.setContentText("Password doesn't exist!");
				notification.show();
			}else if(lc.validate(valEmail, valPassword)) {
				primaryStage.close();
				if(lc.getRole().equals("admin")) {
					AdminViewer av = new AdminViewer();
					av.start();
				}else {
					UserViewer uv = new UserViewer();
					uv.start(lc.getIdUser());
				}
			}else if(!lc.validate(valEmail, valPassword)){
				notification.setContentText("Username or Password doesn't match!");
				notification.show();
			}else {
				notification.setAlertType(AlertType.ERROR);
				notification.setContentText("Ooops, there was an error!");
				notification.show();
			}
		}else if(event.getSource()==register) {
			primaryStage.close();
			RegisterView rv = new RegisterView();
			rv.start();
		}
		
	}
}
