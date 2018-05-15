import Objects.Item;
import com.sun.corba.se.impl.ior.iiop.IIOPProfileTemplateImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class IMSmain {
	public static void main(String[] args) {
		double capital = 100000.00;
		ArrayList<Item> initializedInventoryList = new ArrayList<>();
		ArrayList<Item> orderList = new ArrayList<>();


		viewStoreCapital(capital);

		initializedInventoryList = initializeItem();

		orderList = stockOrderGenerator(initializedInventoryList);

		System.out.println(orderList);

		Manifest.manifestGenerator(orderList);

	}

	//method for showing the store capital
	public static void viewStoreCapital(double capital){

		NumberFormat numberFormat = NumberFormat.getNumberInstance();

		numberFormat.setMaximumFractionDigits(2);

		numberFormat.setMinimumFractionDigits(2);

		String numberString = numberFormat.format(capital);

		System.out.println("$" + numberString);
	}

	//method for initializing items by loading item_properties.csv
	public static ArrayList<Item> initializeItem() {
		String csvFile = "IMS/in/item_properties.csv";
		String line = "";
		String cvsSplitBy = ",";
		ArrayList<Item> inventoryList = new ArrayList<>();
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile))) {

			while ((line = bufferedReader.readLine()) != null) {

				// use comma as separator
				String[] data;
				data = line.split(cvsSplitBy);
				Item item = new Item();
//				Item item = new Item(data[0], Integer.valueOf(data[1]), Integer.valueOf(data[2]), Integer.valueOf(data[3]), Integer.valueOf(data[5]));
				item.setName(data[0]);
				item.setCost(Integer.valueOf(data[1]));
				item.setPrice(Integer.valueOf(data[2]));
				item.setReorderPoint(Integer.valueOf(data[3]));
				item.setReorderAmount(Integer.valueOf(data[4]));
				item.setQuantity(0);
				if (data.length == 6) {
					item.setTemp(data[5]);
				}
				inventoryList.add(item);
//				System.out.println(item.getName() + ',' + item.getCost() + ',' + item.getPrice() + ',' + item.getReorderPoint() + ',' + item.getReorderAmount() + ',' + item.getTemp());
			}
			System.out.println(inventoryList);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inventoryList;
	}

	//method for generating a stock order based on current inventory
	public static ArrayList<Item> stockOrderGenerator(ArrayList<Item> currentInventory) {
		ArrayList<Item> orderList = new ArrayList<>();
		for (Item item : currentInventory) {
			if (item.getQuantity() <= item.getReorderPoint()){
				orderList.add(item);
			}
		}
		return orderList;
	}


//	String line;
//		int tmp=0;
//		try {
//
//			BufferedReader in = new BufferedReader(new FileReader(fileName));
//			line = in.readLine();
//			String[] data = line.trim().split(",");
//			tmp = Integer.valueOf(data[0]);
//			in.close();
//			return tmp;
//		}catch(IOException iox) {
//			System.out.println("Failed reading:"+fileName);
//			return tmp;
//		}
//		String filePath = "F:\\data.csv";
//		readCsvFile(filePath);
//	}
}
