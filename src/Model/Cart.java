package Model;

public class Cart {
	private String productId;
	private String brand;
	private int qty;
	private int price;
	private int total;
	
	public Cart(String userProductId,String brand, int qty, int price, int total){
		this.productId = userProductId;
		this.brand = brand;
		this.qty = qty;
		this.price = price;
		this.total = total;
	}
	
	// Setter
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public void setTotal(int total) {
		this.total = total;
	}

	// Getter
	public String getProductId() {
		return this.productId;
	}
	public String getBrand() {
		return this.brand;
	}
	public int getQty() {
		return this.qty;
	}
	public int getPrice() {
		return this.price;
	}
	public int getTotal() {
		return this.total;
	}
	
}
