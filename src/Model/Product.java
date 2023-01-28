package Model;

public class Product {
	private String id;
	private String brand;
	private String type;
	private int price;
	private int stock;
	
	// Constructor
	public Product(String id, String brand, String type, int price, int stock) {
		this.id = id;
		this.brand = brand;
		this.type = type;
		this.price = price;
		this.stock = stock;
	}
	
	// Setter
	public void setId(String id) {
		this.id = id;
	}
	public void setBrand(String brand){
		this.brand = brand;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	// Getter
	public String getId() {
		return this.id;
	}
	public String getBrand() {
		return this.brand;
	}
	public String getType() {
		return this.type;
	}
	public int getPrice() {
		return this.price;
	}
	public int getStock() {
		return this.stock;
	}
}
