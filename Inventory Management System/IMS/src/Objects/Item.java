package Objects;

import java.util.Comparator;

public class Item {
	private String name;
	private int cost;
	private int price;
	private int reorderPoint;
	private int reorderAmount;
	private String temp;
	private int quantity;

//	public Item(String name, int cost, int price, int reorderPoint,int reorderAmount, String temp, int quantity) {
//		this.name = name;
//		this.cost = cost;
//		this.price= price;
//		this.reorderAmount = reorderAmount;
//		this.reorderPoint = reorderPoint;
//		this.temp = temp;
//		this.quantity = quantity;
//	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getReorderPoint() {
		return reorderPoint;
	}

	public void setReorderPoint(int reorderPoint) {
		this.reorderPoint = reorderPoint;
	}

	public int getReorderAmount() {
		return reorderAmount;
	}

	public void setReorderAmount(int reorderAmount) {
		this.reorderAmount = reorderAmount;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	@Override
	public String toString() {
		return name + " " + quantity + " "  + cost + " " + price + " " + reorderPoint + " " + reorderAmount + " " + temp;
	}

//	@Override
//	public int compare(Item i1, Item i2) {
//		if(Integer.valueOf(i1.getTemp())  > Integer.valueOf(i2.getTemp())){
//			return 1;
//		}else{
//			return -1;
//		}
//	}
}
