package Viewer;


import Controller.AdminController;
import Controller.UserController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminViewer implements EventHandler<ActionEvent> {

	UserController userCon = new UserController();
	private TextField productId;
	private TextField brand;
	@SuppressWarnings("rawtypes")
	private ComboBox type;
	private Spinner<Integer> priceSpinner;
	private Spinner<Integer> stockSpinner;
	Button insertBtn;
	Button updateBtn;
	Button deleteBtn;
	MenuItem manageProduct;
	MenuItem logOut;
	private Stage primaryStage;
	BorderPane root = new BorderPane();
	Alert alert = new Alert(AlertType.NONE);
	
	public void start() {
		primaryStage = new Stage();
		// List of menu
		Menu menu = new Menu("Menu");
		manageProduct =  new MenuItem("Manage Product");
		manageProduct.setOnAction(this);
		menu.getItems().add(manageProduct);
		
		Menu account = new Menu("Account");
		logOut = new MenuItem("Logout");
		logOut.setOnAction(this);
		account.getItems().add(logOut);
		
		MenuBar list = new MenuBar(menu,account);
		VBox vbMenu = new VBox(list);
		root.setTop(vbMenu);
		
		Scene scene = new Scene(root, 750,750);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void showManageProduct() {
		Node topNode = new Label("Manage Product");

		Text productIdTxt = new Text("Product ID");
		productId = new TextField();
		productId.setPrefWidth(400);
		productId.setPromptText("type id here to remove/update");
		
		Text brandTxt = new Text("Brand");
		brand = new TextField();
		
		String types [] = {
				"Food","Drink","Sanitary"
		};
		
		type = new ComboBox<String>(FXCollections.observableArrayList(types));
		
		Text priceTxt = new Text("Price");
		priceSpinner = new Spinner<>(1000,100000,1);
		priceSpinner.setEditable(true);
		VBox priceBox = new VBox();
		priceBox.getChildren().addAll(priceTxt,priceSpinner);
		
		Text stockTxt = new Text("Stock");
		stockSpinner = new Spinner<>(1, 1000,1);
		stockSpinner.setEditable(true);
		VBox stockBox = new VBox();
		priceBox.getChildren().addAll(stockTxt,stockSpinner);
		
		HBox hbSpinner = new HBox();
		hbSpinner.getChildren().addAll(priceBox, stockBox);
		
		insertBtn =  new Button("Insert");
		insertBtn.setOnAction(this);
		updateBtn = new Button("Update");
		updateBtn.setOnAction(this);
		deleteBtn = new Button("Delete");
		deleteBtn.setOnAction(this);
		
		VBox buttons = new VBox();
		buttons.getChildren().addAll(insertBtn, updateBtn, deleteBtn);
		buttons.setSpacing(20);
		buttons.setPrefWidth(100);
		insertBtn.setMinWidth(buttons.getPrefWidth());
		updateBtn.setMinWidth(buttons.getPrefWidth());
		deleteBtn.setMinWidth(buttons.getPrefWidth());
		
		VBox form = new VBox();
		form.getChildren().addAll(productIdTxt,productId, brandTxt, brand,type,hbSpinner);
		form.setSpacing(5);
		
		HBox crud = new HBox();
		crud.getChildren().addAll(form,buttons);
		crud.setSpacing(20);
		
		BorderPane child= new BorderPane();
		child.setLeft(userCon.showProduct("admin"));
		child.setTop(topNode);
		child.setBottom(crud);
		child.setPadding(new Insets(0,0,30,30));
		root.setCenter(child);
	}
	
	public void notification() {
		alert.setAlertType(AlertType.WARNING);
		alert.setContentText("Ooops, there is somethint wrong!");
		alert.show();
	}

	@Override
	public void handle(ActionEvent event) {
		AdminController adminCon = new AdminController();
		String param1;
		String param2;
		String param3;
		int param4;
		int param5;
		
		if(event.getSource()==manageProduct) {
			showManageProduct();
		}else if(event.getSource()==logOut) {
			primaryStage.close();
			LoginView lv = new LoginView();
			lv.start();
		}
		
		if(event.getSource()==insertBtn) {
			param2 = this.brand.getText();
			try {
				param3 = (String)this.type.getValue();
			}catch(NullPointerException e) {
				param3 = "";	
			}
			
			param4 = this.priceSpinner.getValue();
			param5 = this.stockSpinner.getValue();
			if(!adminCon.insertData(param2, param3,param4,param5)) {
				notification();
			}else {
				showManageProduct();
			}
		}else if(event.getSource()== updateBtn) {
			param1 = this.productId.getText();
			param2 = this.brand.getText();
			param4 = this.priceSpinner.getValue();
			param5 = this.stockSpinner.getValue();
			if(!adminCon.updateData(param1, param2,param4,param5)) {
				notification();
			}else {
				showManageProduct();
			}
		}else if(event.getSource()==deleteBtn) {
			param1 = this.productId.getText();
			param2 = this.brand.getText();
			if(!adminCon.deleteData(param1, param2)) {
				notification();
			}else {
				showManageProduct();
			}
			
		}
	}
}
