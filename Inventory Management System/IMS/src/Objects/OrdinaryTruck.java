package Objects;

import java.util.ArrayList;

public class OrdinaryTruck extends Truck {


	private int quantity;

	private double cost;
	private static final int capacity = 1000;

	@Override
	public ArrayList<Item> getItemList() {
		return itemList;
	}

	private ArrayList<Item> itemList;

//	public RefrigeratedTruck(String temp, ArrayList<Item> itemlist) {
//		this.temp = Integer.valueOf(temp);
//		this.itemlist = itemlist;
//		this.cost = 900 + 200 * Math.pow(0.7, (Integer.valueOf(temp)/5));
//	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getCapacity() {
		return capacity;
	}


	@Override
	public double getCost() {
		return cost;
	}


	public void setCost(int quantity) {
		this.cost = 750 + 0.25 * quantity;
	}

//	public ArrayList<Item> getItemList() {
//		return itemList;
//	}
	@Override
	public void setItemList(ArrayList<Item> itemList) {
		this.itemList = itemList;
	}

	@Override
	public String toString() {
		String items = ",";
		for (Item item :itemList) {
			items += item.getName() + "=" + Integer.toString(item.getReorderAmount()) + " ";
		}
		return Integer.toString(quantity) + ' ' + Double.toString(cost) + itemList;
	}
}
