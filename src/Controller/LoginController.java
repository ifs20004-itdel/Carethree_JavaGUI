package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class LoginController {
	private String role;
	private String idUser;
	
	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}
	public String getIdUser() {
		return this.idUser;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	public String getRole() {
		return this.role;
	}
	public boolean validate(String email, String password){
		boolean res = false;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/carethree_db", "root","");
			Statement st = con.createStatement(1004,1008);
			ResultSet rs = st.executeQuery("Select * from users");
			while(rs.next()) {
				String dbEmail = rs.getString("email");
				String dbPassword = rs.getString("password");
				setRole(rs.getString("role"));
				setIdUser(rs.getString("id"));
				if(dbEmail.equals(email) && dbPassword.equals(password)) {
					res = true;
					break;
				}
			}
		}catch(Exception e){
	        e.printStackTrace();
	        e.getCause();
		}
		return res;
	}
}
