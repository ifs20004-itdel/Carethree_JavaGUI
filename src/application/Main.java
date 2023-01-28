package application;
	
import Viewer.LoginView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	public Stage stage;
	public Scene scene;
	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		LoginView parent = new LoginView();
		parent.start();
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}
