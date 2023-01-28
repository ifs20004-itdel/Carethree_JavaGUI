package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import Model.Cart;
import Model.Product;
import Viewer.UserViewer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class UserController {
	
	Alert notif = new Alert(AlertType.NONE);
	
	// Insert data into database
	public void insertOrUpdateData(String userId, String productId, int qty) {
		int validate = validateProduct(productId);
		if(validate == -1) {
			notif.setAlertType(AlertType.WARNING);
			notif.setContentText("ProductID must exist in database!");
			notif.show();
		}else{
			if(qty>validate) {
				notif.setAlertType(AlertType.WARNING);
				notif.setContentText("Item is bigger than available quantity!");
				notif.show();
			}
		}
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/carethree_db", "root","");
			Statement st = con.createStatement(1004,1008);
			ResultSet rs = st.executeQuery("Select * from carts where userID = '"+userId+"' and productId = '"+productId+"'");
			if(rs.next()) {
				updateData(userId, productId, qty);
			}else {
				insertData(userId, productId, qty);
			}
		}catch(Exception e){
	        e.printStackTrace();
		}
	}
	// Insert data into database
		public void deleteOrUpdateData(String userId, String productId, int qty) {
			int validate = validateCart(productId);
			if(validate == -1) {
				notif.setAlertType(AlertType.WARNING);
				notif.setContentText("ProductID must exist in database!");
				notif.show();
			}else{
				if(qty>validate) {
					notif.setAlertType(AlertType.WARNING);
					notif.setContentText("Qty is bigger than available stock!");
					notif.show();
				}else {
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost/carethree_db", "root","");
						Statement st = con.createStatement(1004,1008);
						ResultSet rs = st.executeQuery("Select * from carts where userID = '"+userId+"' and productId = '"+productId+"'");
						if(rs.next()) {
							if(qty < validate) {
								updateData(userId, productId, qty);
							}else if(qty == validate) {
								deleteData(userId, productId);
							}
						}
					}catch(Exception e){
				        e.printStackTrace();
					}
				}
			}

		}
	
	// Update Data
	public void updateData(String userId, String productId, int qty) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/carethree_db", "root","");
			Statement st = con.createStatement(1004,1008);
			st.executeUpdate("UPDATE carts SET qty = "+qty+" where userID = '"+ userId +"'and productID = '"+productId+"'");
		}catch(Exception e){
	        e.printStackTrace();
		}
	}
	
	// InsertData
	public void insertData(String userId, String productId, int qty) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/carethree_db", "root","");
			Statement st = con.createStatement(1004,1008);
			st.executeUpdate("Insert INTO carts VALUES( '"+ userId +"','"+productId+"'," + qty +")");
		}catch(Exception e){
	        e.printStackTrace();
		}
	}
	
	//Delete Data
	public void deleteData(String userId, String productId) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/carethree_db", "root","");
			Statement st = con.createStatement(1004,1008);
			st.executeUpdate("DELETE FROM carts WHERE userID = '"+userId+"' and productID = '"+productId+"'");
		}catch(Exception e){
	        e.printStackTrace();
		}
	}
	
	// Validate productId and qty
	public int validateProduct(String productId) {
		int res = -1;
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost/carethree_db", "root","");
				Statement st = con.createStatement(1004,1008);
				ResultSet rs = st.executeQuery("Select * from products");
				while(rs.next()) {
					if(productId.equals(rs.getString("id"))) {
						res = rs.getInt("stock") ;
					};
				}
			}catch(Exception e){
		        e.printStackTrace();
			}
			return res;
	}
	
	// Validate qty
	public int validateCart(String productId) {
		int res = -1;
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost/carethree_db", "root","");
				Statement st = con.createStatement(1004,1008);
				ResultSet rs = st.executeQuery("Select * from carts");
				while(rs.next()) {
					if(productId.equals(rs.getString("productID"))) {
						res = rs.getInt("qty") ;
					};
				}
			}catch(Exception e){
		        e.printStackTrace();
			}
			return res;
	}
	
	// Get all the products
	public ObservableList<Product> getProduct(){
		ObservableList<Product> products = FXCollections.observableArrayList();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/carethree_db", "root","");
			Statement st = con.createStatement(1004,1008);
			ResultSet rs = st.executeQuery("Select * From Products");
			Product product;
			while(rs.next()) {
				product = new Product(rs.getString("id"),rs.getString("type"), rs.getString("brand"),rs.getInt("price"), rs.getInt("stock"));
				products.add(product);
			}
		}catch(Exception e){
	        e.printStackTrace();
	        e.getCause();
		}
		
		return products;
	}
	
	// Get all the cart
	public ObservableList<Cart> getCart(String userId){
		ObservableList<Cart> carts = FXCollections.observableArrayList();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/carethree_db", "root","");
			Statement st = con.createStatement(1004,1008);
			ResultSet rs = st.executeQuery("SELECT * FROM `products` join carts where products.id = carts.productID and carts.userID = '"+userId+"'");
			Cart cart;
			while(rs.next()) {
				cart = new Cart(rs.getString("productId"),rs.getString("brand"), rs.getInt("qty"),rs.getInt("price"), rs.getInt("qty")*rs.getInt("price"));
				carts.add(cart);
			}
		}catch(Exception e){
	        e.printStackTrace();
	        e.getCause();
		}
		
		return carts;
	}
	
	
	// Show Table View
	TableView<Product> tableProduct;
	TableView<Cart> tableCart;
	TableColumn<Product, String> colIdProduct;
	TableColumn<Product, String> colTypeProduct;
	TableColumn<Product, String> colBrandProduct;
	TableColumn<Product, String> colPriceProduct;
	TableColumn<Product, String> colStockProduct;
	
	@SuppressWarnings("unchecked")
	public VBox showProduct(String role) {
		// Kolom nama
		colIdProduct = new TableColumn<>("ID");
		colIdProduct.setMinWidth(50);
		colIdProduct.setCellValueFactory(new PropertyValueFactory<>("id"));
		
		// Kolom tipe
		colTypeProduct = new TableColumn<>("Type");
		colTypeProduct.setMinWidth(100);
		colTypeProduct.setCellValueFactory(new PropertyValueFactory<>("type"));
		
		// Kolom brand
		colBrandProduct = new TableColumn<>("Brand");
		colBrandProduct.setMinWidth(100);
		colBrandProduct.setCellValueFactory(new PropertyValueFactory<>("brand"));
		
		// Kolom harga
		colPriceProduct = new TableColumn<>("Price");
		colPriceProduct.setMinWidth(100);
		colPriceProduct.setCellValueFactory(new PropertyValueFactory<>("price"));
		
		// Kolom stok
		colStockProduct = new TableColumn<>("Stock");
		colStockProduct.setMinWidth(100);
		colStockProduct.setCellValueFactory(new PropertyValueFactory<>("stock"));

		
		tableProduct = new TableView<>();
		tableProduct.setItems(getProduct());
		tableProduct.getColumns().addAll(colIdProduct, colTypeProduct, colBrandProduct, colPriceProduct, colStockProduct);
		
		if(role.equals("user")) {
			tableProduct.setOnMouseClicked((MouseEvent event)->{
				Product product = tableProduct.getSelectionModel().getSelectedItem();
				if(colIdProduct.getCellData(product) !=null) {
				   UserViewer.productId.setText(product.getId());
				}
			});
		}

		VBox vb = new VBox();
		vb.getChildren().addAll(tableProduct);
		return vb;	
	}
	
	TableColumn<Cart, String> colIdCart;
	TableColumn<Cart, String> colBrandCart;
	TableColumn<Cart, Integer> qty;
	TableColumn<Cart, Integer> colPriceCart;
	TableColumn<Cart, Integer> total;
	
	@SuppressWarnings("unchecked")
	public VBox showCart(String userId) {
		// Kolom product
		colIdCart = new TableColumn<>("Product ID");
		colIdCart.setMinWidth(50);
		colIdCart.setCellValueFactory(new PropertyValueFactory<>("productId"));
		
		// Kolom brand
		colBrandCart = new TableColumn<>("Brand");
		colBrandCart.setMinWidth(100);
		colBrandCart.setCellValueFactory(new PropertyValueFactory<>("brand"));
		
		// Kolom qty
		qty= new TableColumn<>("Qty");
		qty.setMinWidth(100);
		qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
			
		// Kolom harga
		colPriceCart = new TableColumn<>("Price");
		colPriceCart.setMinWidth(100);
		colPriceCart.setCellValueFactory(new PropertyValueFactory<>("price"));
		
		// Kolom total
		total = new TableColumn<>("Total");
		total.setMinWidth(100);
		total.setCellValueFactory(new PropertyValueFactory<>("total"));
		
		tableCart = new TableView<>();
		tableCart.setItems(getCart(userId));
		tableCart.getColumns().addAll(colIdCart, colBrandCart, qty, colPriceCart, total);
		
		tableCart.setOnMouseClicked((MouseEvent event)->{
			Cart cart = tableCart.getSelectionModel().getSelectedItem();
				if(colIdCart.getCellData(cart) !=null) {
				   UserViewer.productId.setText(cart.getProductId());
				}
			});
		VBox vb = new VBox();
		vb.getChildren().addAll(tableCart);
		return vb;	
	}

	// Sum of total price
	public int totalPrice(String userId) {
		int total=0;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/carethree_db", "root","");
			Statement st = con.createStatement(1004,1008);
			ResultSet rs = st.executeQuery("SELECT SUM(qty*price) as total_price FROM `products` join carts where products.id = carts.productID and carts.userID = '"+userId+"'");
			if(rs.next()) {
				total += rs.getInt("total_price");
			}
		}catch(Exception e){
	        e.printStackTrace();
		}
		return total;
	}
	
	// Clear the cart
	public void deleteCart(String userId) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/carethree_db", "root","");
			Statement st = con.createStatement(1004,1008);
			st.executeUpdate("DELETE FROM carts WHERE userID = '"+userId+"'");
		}catch(Exception e){
	        e.printStackTrace();
		}
	}
	
	// Update the stock from products
	public void updateStock(String userId) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/carethree_db", "root","");
			Statement st = con.createStatement(1004,1008);
			st.executeUpdate("UPDATE products JOIN carts SET stock = products.stock-carts.qty WHERE products.id = carts.productID and carts.userID = '"+userId+"'");
		}catch(Exception e){
	        e.printStackTrace();
		}

	}
}