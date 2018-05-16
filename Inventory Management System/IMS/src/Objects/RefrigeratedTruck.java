package Objects;

import java.util.ArrayList;

public class RefrigeratedTruck extends Truck {
	private int temp;

	private double cost;
	private static final int capacity = 800;
	private ArrayList<Item> itemList;

//	public RefrigeratedTruck(String temp, ArrayList<Item> itemlist) {
//		this.temp = Integer.valueOf(temp);
//		this.itemlist = itemlist;
//		this.cost = 900 + 200 * Math.pow(0.7, (Integer.valueOf(temp)/5));
//	}


	public int getCapacity() {
		return capacity;
	}

	public int getTemp() {
		return temp;
	}

	public void setTemp(int temp) {
		this.temp = temp;
	}

	@Override
	public double getCost() {
		return cost;
	}


	public void setCost(int temp) {
		this.cost = 900 + 200 * Math.pow(0.7, (Integer.valueOf(temp)/5));
	}

//	public ArrayList<Item> getItemList() {
//		return itemList;
//	}

	public void setItemList(ArrayList<Item> itemList) {
		this.itemList = itemList;
	}

	@Override
	public String toString() {
		String items = ",";
		for (Item item :itemList) {
			items += item.getName() + "=" + Integer.toString(item.getReorderAmount()) + " ";
		}
		return Integer.toString(temp) + ' ' + Double.toString(cost) + itemList;
	}
}
