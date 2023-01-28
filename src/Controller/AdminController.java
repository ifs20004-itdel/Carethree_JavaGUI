package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;


public class AdminController {

	public void connect(String query) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/carethree_db", "root","");
			Statement st = con.createStatement(1004,1008);
			st.executeUpdate(query);
		}catch(Exception e){
	        e.printStackTrace();
	        e.getCause();
		}
	}
	public boolean insertData(String brand, String type, int price, int stock) {
		Random rand = new Random();
		int int_random = rand.nextInt(10000)+1000;
		if(validateBrand(brand) && validateType(type) && validateStock(stock)) {
			String id = ""+ Character.toUpperCase(type.charAt(0))  + int_random;
			
			connect("INSERT INTO products VALUES ('"+id+"', '"+type+"' , '"+brand+"', "+price+" , "+stock+" )");
			return true;
		}else {
			return false;
		}
	}

	public boolean deleteData(String param1, String param2 ) {
		// TODO Auto-generated method stub
		if(validateData(param1, param2).length()>0) {
			connect("DELETE FROM products WHERE id = '"+ validateData(param1,param2)+"'");
			return true;
		}else {
			return false;
		}
	}

	public boolean updateData(String param1, String param2, int price, int stock) {
		// TODO Auto-generated method stub
		if(validateData(param1, param2).length()>0 && validateStock(stock)) {
			connect("UPDATE products SET stock = "+stock+", price = "+price+" where id = '"+validateData(param1,param2)+"'");
			return true;
		}else {
			return false;
		}
	}
	
	public String validateData(String param1, String param2) {
		String validate = "";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/carethree_db", "root","");
			Statement st = con.createStatement(1004,1008);
			ResultSet rs = st.executeQuery("Select * from products");
			while(rs.next()) {
				String idProduct = rs.getString("id");
				if(param1.equals(idProduct)){
					validate += param1;
					return validate;
				}else if(param2.equals(idProduct)) {
					validate +=param2;
					return validate;
				}
			}
		}catch(Exception e){
	        e.printStackTrace();
	        e.getCause();
		}
		return validate;
	}
	
	public boolean validateBrand(String brand ) {
		if(brand.length()<5 || brand.length()>20) {
			return false;
		}else {
			return true;
		}
	}
	public boolean validateType(String type) {
		if(type==null) {
			return false;
		}
		if(type.length()<=1) {
			return false;
		}else {
			return true;
		}
	}
	
	public boolean validateStock(int stock) {
		if(stock < 30 || stock>1000) {
			return false;
		}else {
			return true;
		}
	}
}
