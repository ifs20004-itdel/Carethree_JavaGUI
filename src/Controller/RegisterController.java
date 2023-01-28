package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Pattern;
import java.util.Random;

public class RegisterController {
	
	
	// Fungsi insert ke database
	public boolean insertData(String nama, String username, String password, String email, String phoneNumber, String gender){
		Random rand = new Random();
		int int_random = rand.nextInt(10000)+1000;
		String id = "U"+int_random;
		try {
			String query = "INSERT INTO users VALUES ('"+ id + "','" + nama + "','user','" + username + "','"+ password +"','"+ email + "','" + phoneNumber + "','" + gender +"')";
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/carethree_db", "root","");
			Statement st = con.createStatement(1004,1008);
			st.executeUpdate(query);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	// Fungsi-fungsi Validasi
	public boolean validateName(String name) {
		if(name.length()<5 || name.length()>20) {
			return false;
		}else {
			return true;
		}
	}
	
	public boolean validateUsername(String username) {
		if(username.length()<3 || username.length()>10) {
			return false;
		}else {
			return true;
		}
	}
	public boolean validatePassword(String password){
		if(password.length()>=8 && password.matches("[A-Za-z0-9]+")) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean validateConfirmation(String pass, String confirm) {
		if(pass.equals(confirm)) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean unique(String email) {
		boolean res = true;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/carethree_db", "root","");
			Statement st = con.createStatement(1004,1008);
			ResultSet rs = st.executeQuery("Select email from users");
			while(rs.next()) {
				String dbEmail = rs.getString("email");
				if(dbEmail.equals(email)) {
					res = false;
					break;
				}
			}
		}catch(Exception e){
	        e.printStackTrace();
	        e.getCause();
		}
		return res;
	}
	
	public boolean validateEmail(String email) {
		char pattern = '@';
		int count = 0;
		for (int i = 0; i < email.length(); i++) {
		    if (email.charAt(i) == pattern) {
		        count++;
		    }
		}
		if(email.endsWith("@gmail.com") && !(email.charAt(0)=='@') && count == 1 && unique(email)) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean validatePhoneNumber(String phoneNumber){
		if(phoneNumber.startsWith("+62") && phoneNumber.length()>=10) {
			String checkNumber = phoneNumber.substring(3,phoneNumber.length());
			boolean isMatch = Pattern.compile("^\\d+$").matcher(checkNumber).find();
			if(isMatch) {
				return true;	
			}else {
				return false;
			}
			
		}else {
			return false;
		}
	}
	
	public boolean validateGender(String gender) {
		if(gender.equals("")) {
			return false;
		}else{
			return true;
		}
	}
}
