package Viewer;
	
import Controller.RegisterController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;


public class RegisterView implements EventHandler<ActionEvent> {
	private TextField name;
	private TextField username;
	private PasswordField password;
	private PasswordField confirmation;
	private TextField email;
	private TextField phoneNumber;
	private Button login;
	private Button register;
	private Stage primaryStage;
	private RadioButton check;
	private CheckBox terms;
	private ToggleGroup tg;
	Alert notification = new Alert(AlertType.NONE);
	
	public void start() {
		try {
			primaryStage = new Stage();
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,600,600);
			primaryStage.setTitle("Register");
			GridPane gp = new GridPane();
			
			Label lblName = new Label("Name");
			Label lblUsername = new Label("Username");
			Label lblPassword = new Label("Password");
			Label lblConfirmation = new Label("Confirm Password");
			Label lblEmail = new Label("Email");
			Label lblPhone = new Label("Phone Number");
			Label lblGender = new Label("Gender");
			
			name = new TextField();
			name.setPromptText("Name");
			name.setPrefWidth(500);
			
			username = new TextField();
			username.setPromptText("Username");
			
			password = new PasswordField();
			password.setPromptText("Password");
			
			confirmation = new PasswordField();
			confirmation.setPromptText("Confirm Password");
			
			email = new TextField();
			email.setPromptText("Email");
			
			phoneNumber = new TextField();
			phoneNumber.setPromptText("+62XXXXXXXXXX");
			
			// Gender
			tg = new ToggleGroup();
			RadioButton male = new RadioButton("Male");
			RadioButton female = new RadioButton("Female");
			male.setToggleGroup(tg);
			female.setToggleGroup(tg);
			
			tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
		        @Override
		        public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {
		            check= (RadioButton)t1.getToggleGroup().getSelectedToggle();
		            System.out.println("Selected Radio Button - "+check.getText());
		        }
		    });
			
			HBox genderBox = new HBox();
			genderBox.getChildren().addAll(male,female);
			genderBox.setSpacing(10);
			
			// Terms and condition
			terms = new CheckBox("I agree with Terms and Conditions");
			
			login = new Button("Login");
			register = new Button("Register");
			login.setOnAction(this);
			register.setOnAction(this);
			
			gp.add(lblName, 0, 0);
			gp.add(name, 0, 1);
			
			gp.add(lblUsername, 0, 4);
			gp.add(username, 0, 5);

			gp.add(lblPassword, 0, 8);
			gp.add(password, 0, 9);
			
			gp.add(lblConfirmation, 0, 12);
			gp.add(confirmation, 0, 13);
			
			gp.add(lblEmail, 0, 16);
			gp.add(email, 0, 17);

			gp.add(lblPhone, 0, 20);
			gp.add(phoneNumber, 0, 21);
			
			gp.add(lblGender, 0, 23);
			gp.add(genderBox, 0, 24);
			
			gp.add(terms, 0, 26);
			
			HBox buttons =new HBox();
			buttons.getChildren().addAll(register, login);
			buttons.setSpacing(5);
			buttons.setAlignment(Pos.BOTTOM_RIGHT);
			gp.add(buttons, 0, 28);
			
			gp.setVgap(5);
			gp.setPadding(new Insets(50,0,0,50));
			root.setCenter(gp);
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void warningAlert(String message) {
		notification.setAlertType(AlertType.WARNING);
		notification.setContentText(message);
		notification.show();
	}
	
	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		if(event.getSource()==login) {
			primaryStage.close();
			LoginView lv = new LoginView();
			lv.start();
		}else if(event.getSource()==register){
			RegisterController rc = new RegisterController();
			String name = this.name.getText();
			String username = this.username.getText();
			String password = this.password.getText();
			String confirmation = this.confirmation.getText();
			String email = this.email.getText();
			String phoneNumber = this.phoneNumber.getText();
			String gender;
			try {
				gender = check.getText();
			}catch(NullPointerException e) {
				gender = "";	
			}
			
			if(!rc.validateName(name)){
				warningAlert("Name must be between 5-20 characters!");
			}else if(!rc.validateUsername(username)) {
				warningAlert("Username must be between 3-10 characters!");
			}else if(!rc.validatePassword(password)) {
				warningAlert("Password must contain minimal 8 characters and alphanumeric!");
			}else if(!rc.validateConfirmation(password,confirmation)){
				warningAlert("Confirm Password must same as Password!");
			}else if(!rc.validateEmail(email)) {
				warningAlert("Email must end with ‘@gmail.com’, contains 1 ‘@’ and not in front, and unique!");
			}else if(!rc.validatePhoneNumber(phoneNumber)) {
				warningAlert("Phone Number must contain at least 10 characters, numeric, and starts with ‘+62’");
			}else if(!rc.validateGender(gender)){
				warningAlert("Gender must be choosed.");
			}else if(!terms.isSelected()) {
				warningAlert("CheckBox must be checked");
			}else {
				rc.insertData(name, username, password, email, phoneNumber, gender);
				primaryStage.close();
				LoginView lv = new LoginView();
				lv.start();
			}
		}
		
	}
	
}
