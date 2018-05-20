import Objects.Item;
import Objects.OrdinaryTruck;
import Objects.RefrigeratedTruck;
import Objects.Truck;

import java.util.ArrayList;
import java.util.Collections;

public class Manifest {
	public ArrayList<Truck> getTruckList() {
		return truckList;
	}

	public void setTruckList(ArrayList<Truck> truckList) {
		this.truckList = truckList;
	}

	ArrayList<Truck> truckList;


	//TODO: extends truck types from Truck abstract class
//	public Manifest(ArrayList<Item> orderList){
//		ArrayList<Item> temConItemList = new ArrayList<>();
//		ArrayList<Item> dryItemList = new ArrayList<>();
//		ArrayList<Item> sortedtempCnItemList;
//		ArrayList<Item> sortedItemList;
//		int sumOfTempCnItem = 0;
////		int sumOfDryItem = 0;
////		int numOfRTruck = 0;
////		int numOfOTruck = 0;
////		int leftSpaceOfRTruck;
////		double costOfRTruck = 0;
////		double costOfOTruck = 0;
////		int capacityOfOTruck = 1000;
////		int capacityOfRTruck = 800;
//
//
//		for (Item item : orderList) {
//			if (item.getTemp() != null) {
//				temConItemList.add(item);
//			} else {
//				dryItemList.add(item);
//			}
//		}
//		sortedtempCnItemList = sortListFunction(temConItemList);
//		System.out.println(sortedtempCnItemList);
//
//		for (Item item : sortedtempCnItemList) {
//			sumOfTempCnItem += item.getReorderAmount();
//		}
//		System.out.println(sumOfTempCnItem);
//
//		//add dry food items to temp controlled Items
//		sortedtempCnItemList.addAll(dryItemList);
//		sortedItemList = sortedtempCnItemList;
//
////		numOfRTruck = (int) Math.ceil((double) sumOfTempCnItem / capacityOfRTruck);
////		System.out.println(numOfRTruck);
//
//		//generate cargo item list
//		ArrayList<Item> cargoItemList = cargoItemListGenerator(sortedItemList);
//
//		//generate cargo list
//		cargoListGenerator(cargoItemList);
//	}

	public static ArrayList<Truck> manifestGenerator(ArrayList<Item> orderList) {
		ArrayList<Item> temConItemList = new ArrayList<>();
		ArrayList<Item> dryItemList = new ArrayList<>();
		ArrayList<Item> sortedtempCnItemList;
		ArrayList<Item> sortedItemList = new ArrayList<>();
		int sumOfTempCnItem = 0;
		int sumOfDryItem = 0;
		int numOfRTruck = 0;
		int numOfOTruck = 0;
		int leftSpaceOfRTruck;
		double costOfRTruck = 0;
		double costOfOTruck = 0;
		int capacityOfOTruck = 1000;
		int capacityOfRTruck = 800;

//		RefrigeratedTruck refrigeratedTruck = new RefrigeratedTruck(0);
//		int capacityOfRTruck = refrigeratedTruck.getCapacity();
//		OrdinaryTruck ordinaryTruck = new OrdinaryTruck();
//		int capacityOfOTruck = ordinaryTruck.getCapacity();


		for (Item item : orderList) {
			if (item.getTemp() != null) {
				temConItemList.add(item);
			} else {
				dryItemList.add(item);
			}
		}
		sortedtempCnItemList = sortListFunction(temConItemList);
//		System.out.println(sortedtempCnItemList);

		for (Item item : sortedtempCnItemList) {
			sumOfTempCnItem += item.getReorderAmount();
		}
//		System.out.println(sumOfTempCnItem);

		//add dry food items to temp controlled Items
		sortedtempCnItemList.addAll(dryItemList);
		sortedItemList = sortedtempCnItemList;

		for (Item item : dryItemList) {
			sumOfDryItem += item.getReorderAmount();
		}
//		System.out.println(sumOfDryItem);

		numOfRTruck = (int) Math.ceil((double) sumOfTempCnItem / capacityOfRTruck);
//		System.out.println(numOfRTruck);
//		costOfRTruck = refrigeratedTruck.calculateCost();
//		Item item0 = sortedtempCnItemList.get(0);
//		System.out.println(item0);

		//generate cargo item list
		ArrayList<Item> cargoItemList = cargoItemListGenerator(sortedItemList);

		//generate cargo list

		return cargoListGenerator(cargoItemList);


//
//		leftSpaceOfRTruck = capacityOfRTruck * numOfRTruck - sumOfTempCnItem;
//
//		System.out.println(sumOfDryItem - leftSpaceOfRTruck);
//
//
//		numOfOTruck = (int)Math.ceil((double)(sumOfDryItem - leftSpaceOfRTruck) / capacityOfOTruck);
//		System.out.println(numOfOTruck);
	}

	public static ArrayList<Truck> cargoListGenerator(ArrayList<Item> cargoItemList) {
		int numOfItemTemp = 0;
		int capacityOfOTruck = 1000;
		int capacityOfRTruck = 800;
		ArrayList<Item> itemListTemp = new ArrayList<>();
		ArrayList<RefrigeratedTruck> refrigeratedTruckList = new ArrayList<>();
		ArrayList<OrdinaryTruck> ordinaryTruckList = new ArrayList<>();

		for (int i = 0; i < cargoItemList.size(); i++) {
			numOfItemTemp += cargoItemList.get(i).getReorderAmount();
			itemListTemp.add(cargoItemList.get(i));

			if (numOfItemTemp == capacityOfRTruck) {
				ArrayList<Item> addItemList = new ArrayList<>();
				addItemList.addAll(itemListTemp);

				if (addItemList.get(0).getTemp() != null) {

					RefrigeratedTruck refrigeratedTruck = new RefrigeratedTruck();
					refrigeratedTruck.setTemp(Integer.valueOf(addItemList.get(0).getTemp()));
					refrigeratedTruck.setCost(Integer.valueOf(addItemList.get(0).getTemp()));
					refrigeratedTruck.setItemList(addItemList);
					refrigeratedTruckList.add(refrigeratedTruck);

					itemListTemp.clear();
					numOfItemTemp = 0;
				}
			} else if (numOfItemTemp == capacityOfOTruck || i == cargoItemList.size() -1) {
				ArrayList<Item> addItemList = new ArrayList<>();
				addItemList.addAll(itemListTemp);
				OrdinaryTruck ordinaryTruck = new OrdinaryTruck();
				ordinaryTruck.setQuantity(numOfItemTemp);
				ordinaryTruck.setCost(numOfItemTemp);
				ordinaryTruck.setItemList(addItemList);
				ordinaryTruckList.add(ordinaryTruck);

				itemListTemp.clear();
				numOfItemTemp = 0;
			}
		}

//		for (RefrigeratedTruck truck : refrigeratedTruckList) {
//			System.out.println(truck);
//		}
//
//		for (OrdinaryTruck truck : ordinaryTruckList) {
//			System.out.println(truck);
//		}
		ArrayList<Truck> truckList = new ArrayList<>();
		truckList.addAll(refrigeratedTruckList);
		truckList.addAll(ordinaryTruckList);
		return truckList;
	}


	public static ArrayList<Item> cargoItemListGenerator(ArrayList<Item> sortedtempCnItemList) {
		int numOfItemTemp = 0;
		ArrayList<Item> cargoItemList = new ArrayList<>();
		ArrayList<RefrigeratedTruck> RTruckList = new ArrayList<>();
		int capacityOfOTruck = 1000;
		int capacityOfRTruck = 800;

		int capacityOfTruck = capacityOfRTruck;
		for (int i = 0; i < sortedtempCnItemList.size(); i++) {
			numOfItemTemp += sortedtempCnItemList.get(i).getReorderAmount();

			if (numOfItemTemp > capacityOfTruck) {
				int amountNext = numOfItemTemp - capacityOfTruck;
				int amountThis = sortedtempCnItemList.get(i).getReorderAmount() - amountNext;
//				sortedtempCnItemList.get(i).setReorderAmount(amountThis);
				Item addItem = new Item();
				addItem.setName(sortedtempCnItemList.get(i).getName());
				addItem.setReorderAmount(amountThis);
				addItem.setQuantity(amountThis);
				addItem.setTemp(sortedtempCnItemList.get(i).getTemp());

				cargoItemList.add(addItem);
				sortedtempCnItemList.get(i).setReorderAmount(amountNext);

				if (sortedtempCnItemList.get(i).getTemp() == null) {
					capacityOfTruck = capacityOfOTruck;
				}

//				RefrigeratedTruck refrigeratedTruck = new RefrigeratedTruck();
//				refrigeratedTruck.setTemp(Integer.valueOf(eachCargoItemList.get(0).getTemp()));
//				refrigeratedTruck.setCost(Integer.valueOf(eachCargoItemList.get(0).getTemp()));
//
//				refrigeratedTruck.setItemList(eachCargoItemList);
//				RTruckList.add(refrigeratedTruck);
//				System.out.println(">" + refrigeratedTruck);
//
//				eachCargoItemList.clear();
//				System.out.println(">" + refrigeratedTruck);

				numOfItemTemp = 0;

				i--;
			} else if (numOfItemTemp == capacityOfTruck) {
//				RefrigeratedTruck refrigeratedTruck = new RefrigeratedTruck();
//				refrigeratedTruck.setTemp(Integer.valueOf(eachCargoItemList.get(0).getTemp()));
//				refrigeratedTruck.setCost(Integer.valueOf(eachCargoItemList.get(0).getTemp()));
//				refrigeratedTruck.setItemList(eachCargoItemList);
//				RTruckList.add(refrigeratedTruck);
//
//				System.out.println("=" + refrigeratedTruck);

//				eachCargoItemList.clear();

				numOfItemTemp = 0;

				if (sortedtempCnItemList.get(i + 1).getTemp() == null) {
					capacityOfTruck = capacityOfOTruck;
				}

			} else {
				cargoItemList.add(sortedtempCnItemList.get(i));
			}
		}
//
		for (Item item : cargoItemList) {
//			System.out.println(item.getName() + "=" + Integer.toString(item.getReorderAmount()));
		}
		return cargoItemList;
	}


	public static ArrayList<Item> sortListFunction(ArrayList<Item> list) {
		Collections.sort(list, (i1, i2) -> {
			if (Integer.valueOf(i1.getTemp()) > Integer.valueOf(i2.getTemp())) {
				return 1;
			}
			if (Integer.valueOf(i1.getTemp()) == Integer.valueOf(i2.getTemp())) {
				return 0;
			}
			return -1;
		});
		return list;
	}

}
