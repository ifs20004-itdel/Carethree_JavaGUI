package Viewer;

import Controller.UserController;
import Model.Product;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UserViewer implements EventHandler<ActionEvent>{

	UserController uc = new UserController();
	Alert notification = new Alert(AlertType.NONE);
	private String userId;
	private Stage primaryStage;
	private BorderPane root = new BorderPane();
	MenuItem productList;
	MenuItem cart;
	MenuItem logOut;
	TableView<Product> tableProduct =  new TableView<>();
	public static TextField productId;
	Spinner<Integer> qtySpinner;
	Button add;
	Button remove;
	Button checkOut;
	
	public String getUserID() {
		return this.userId;
	}
	
	public void start(String userId) {
		this.userId = userId;
		primaryStage = new Stage();
		
		// List of menu
		Menu menu = new Menu("Menu");
		productList = new MenuItem("Product List");
		productList.setOnAction(this);
		cart = new MenuItem("Cart");
		cart.setOnAction(this);
		menu.getItems().add(productList);
		menu.getItems().add(cart);
		
		// LogOut
		Menu account = new Menu("Account");
		logOut = new MenuItem("Logout");
		logOut.setOnAction(this);
		account.getItems().add(logOut);
		
		MenuBar list = new MenuBar(menu,account);
		root.setTop(list);
		
		Scene scene = new Scene(root, 600,600);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	

	public void showUserProduct() {
		Node topNode = new Label("Product List");

		Text productIdTxt = new Text("Product ID");
		productId = new TextField();
		productId.setMaxWidth(400);
		
		Text qtyTxt = new Text("Qty");
		qtySpinner = new Spinner<>(1, 1000,1);
		
		add =  new Button("Add to Cart");
		add.setOnAction(this);
		
		VBox vb = new VBox();
		vb.getChildren().addAll(productIdTxt,productId, qtyTxt, qtySpinner,add);
		
		BorderPane child= new BorderPane();
		child.setLeft(uc.showProduct("user"));
		child.setTop(topNode);
		child.setBottom(vb);
		child.setPadding(new Insets(0,0,30,30));
		root.setCenter(child);
	}
	
	public void showCartProduct() {
		Node topNode = new Label("Cart");
		
		Text totalPriceTxt = new Text("Total price: "+ uc.totalPrice(getUserID()));

		Text productIdTxt = new Text("Cart");
		productId = new TextField();
		productId.setMaxWidth(400);
		
		Text qtyTxt = new Text("Qty");
		qtySpinner = new Spinner<>(1, 1000,1);
		
		remove =  new Button("Remove from Cart");
		remove.setOnAction(this);
		
		checkOut = new Button("Checkout");
		checkOut.setOnAction(this);
		
		VBox vb = new VBox();
		vb.getChildren().addAll(totalPriceTxt,productIdTxt,productId, qtyTxt, qtySpinner,remove, checkOut);
		
		BorderPane child= new BorderPane();
		
		child.setLeft(uc.showCart(userId));
		child.setTop(topNode);
		child.setBottom(vb);
		child.setPadding(new Insets(0,0,30,30));
		root.setCenter(child);
	}

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		if(event.getSource()== productList) {
			showUserProduct();
		}else if(event.getSource()==cart){
			showCartProduct();
		} else if(event.getSource()==logOut) {
			primaryStage.close();
			LoginView lv = new LoginView();
			lv.start();
		}
		if(event.getSource()==add) {
			int inQty = qtySpinner.getValue();
			String inProductId = productId.getText();
			String userId = getUserID();
			if(inProductId.equals("")) {
				notification.setAlertType(AlertType.WARNING);
				notification.setContentText("Product Id's must be filled");
				notification.show();
			}else {
				uc.insertOrUpdateData(userId,inProductId, inQty);
				productId.setText("");
			}
		}else if(event.getSource()==remove) {
			int inQty = qtySpinner.getValue();
			String inProductId = productId.getText();
			String userId = getUserID();
			if(inProductId.equals("")) {
				notification.setAlertType(AlertType.WARNING);
				notification.setContentText("Product Id's must be filled");
				notification.show();
			}else {
				uc.deleteOrUpdateData(userId,inProductId, inQty);
				showCartProduct();
			}
		}else if(event.getSource()==checkOut) {
			uc.updateStock(getUserID());
			uc.deleteCart(getUserID());
			notification.setAlertType(AlertType.INFORMATION);
			notification.setContentText("Checkout success! Stock has been update.");
			notification.show();
			showUserProduct();
		}
	}
}
