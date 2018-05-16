package Objects;

import java.util.ArrayList;

public class Store {
	public double getCapital() {
		return capital;
	}

	public void setCapital(double capital) {
		this.capital = capital;
	}

	public ArrayList<Item> getInventory() {
		return Inventory;
	}

	public void setInventory(ArrayList<Item> inventory) {
		Inventory = inventory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private double capital;
	private ArrayList<Item> Inventory;
	private String name;

	public Store() {

	}
}
