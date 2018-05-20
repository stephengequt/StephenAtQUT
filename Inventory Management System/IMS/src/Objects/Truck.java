package Objects;

import java.util.ArrayList;

public abstract class Truck {
	//	public abstract double setCost();
//	abstract int getCapacity();
	private double cost;
//	abstract double getCost();

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getCost() {
		return cost;
	}


	private ArrayList<Item> itemList;

	public ArrayList<Item> getItemList() {
		return itemList;
	}

	public void setItemList(ArrayList<Item> itemList) {
		this.itemList = itemList;
	}
}
