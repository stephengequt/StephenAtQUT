import Objects.Item;
import Objects.OrdinaryTruck;
import Objects.RefrigeratedTruck;

import java.util.ArrayList;
import java.util.Collections;

public class Manifest {

	public static void manifestGenerator(ArrayList<Item> orderList) {
		ArrayList<Item> temConItemList = new ArrayList<>();
		ArrayList<Item> dryItemList = new ArrayList<>();
		ArrayList<Item> sortedtempCnItemList = new ArrayList<>();
		int sumOfTempCnItem = 0;
		int sumOfDryItem = 0;
		int numOfRTruck = 0;
		int numOfOTruck = 0;
		int leftSpaceOfRTruck;
		double costOfRTruck = 0;
		double costOfOTruck = 0;

//		RefrigeratedTruck refrigeratedTruck = new RefrigeratedTruck(0);
//		int capacityOfRTruck = refrigeratedTruck.getCapacity();
		int capacityOfRTruck = 800;
//		OrdinaryTruck ordinaryTruck = new OrdinaryTruck();
//		int capacityOfOTruck = ordinaryTruck.getCapacity();
		int capacityOfOTruck = 900;

		for (Item item : orderList) {
			if (item.getTemp() != null) {
				temConItemList.add(item);
			} else {
				dryItemList.add(item);
			}
		}
		sortedtempCnItemList = sortListFunction(temConItemList);
		System.out.println(sortedtempCnItemList);

		for (Item item : sortedtempCnItemList) {
			sumOfTempCnItem += item.getReorderAmount();
		}
		System.out.println(sumOfTempCnItem);

		for (Item item : dryItemList) {
			sumOfDryItem += item.getReorderAmount();
		}
		System.out.println(sumOfDryItem);

		numOfRTruck = (int)Math.ceil((double)sumOfTempCnItem / capacityOfRTruck);
		System.out.println(numOfRTruck);
//		costOfRTruck = refrigeratedTruck.calculateCost();
//		Item item0 = sortedtempCnItemList.get(0);
//		System.out.println(item0);

//		int numOfItemTemp = 0;
//		ArrayList<Item> eachCargoItemList = new ArrayList<>();
//		ArrayList<RefrigeratedTruck> RTruckList = new ArrayList<>();
//		for (int i = 0; i < sortedtempCnItemList.size(); i++){
//			numOfItemTemp += sortedtempCnItemList.get(i).getReorderAmount();
//
//			if (numOfItemTemp > capacityOfRTruck) {
//				int amountNext = numOfItemTemp - capacityOfRTruck;
//				int amountThis = sortedtempCnItemList.get(i).getReorderAmount()-amountNext;
//				sortedtempCnItemList.get(i).setReorderAmount(amountThis);
//				eachCargoItemList.add(sortedtempCnItemList.get(i));
//
//
//				RefrigeratedTruck refrigeratedTruck = new RefrigeratedTruck();
//				refrigeratedTruck.setTemp(Integer.valueOf(eachCargoItemList.get(0).getTemp()));
//				refrigeratedTruck.setCost(Integer.valueOf(eachCargoItemList.get(0).getTemp()));
//				refrigeratedTruck.setItemList(eachCargoItemList);
//				RTruckList.add(refrigeratedTruck);
//				System.out.println(">" + refrigeratedTruck);
//
////				eachCargoItemList.clear();
//				System.out.println(">" + refrigeratedTruck);
//
//				numOfItemTemp = 0;
//
//				sortedtempCnItemList.get(i).setReorderAmount(amountNext);
//				i--;
//			} else if (numOfItemTemp == capacityOfRTruck){
//				RefrigeratedTruck refrigeratedTruck = new RefrigeratedTruck();
//				refrigeratedTruck.setTemp(Integer.valueOf(eachCargoItemList.get(0).getTemp()));
//				refrigeratedTruck.setCost(Integer.valueOf(eachCargoItemList.get(0).getTemp()));
//				refrigeratedTruck.setItemList(eachCargoItemList);
//				RTruckList.add(refrigeratedTruck);
//
//				System.out.println("=" + refrigeratedTruck);
//
//				eachCargoItemList.clear();
//
//				numOfItemTemp = 0;
//
//			} else {
//				if (i == sortedtempCnItemList.size() - 1){
////					int amountNext = numOfItemTemp - capacityOfRTruck;
////					int amountThis = sortedtempCnItemList.get(i).getReorderAmount()-amountNext;
////					sortedtempCnItemList.get(i).setReorderAmount(amountThis);
////					eachCargoItemList.add(sortedtempCnItemList.get(i));
//
//					RefrigeratedTruck refrigeratedTruck = new RefrigeratedTruck();
//					refrigeratedTruck.setTemp(Integer.valueOf(eachCargoItemList.get(0).getTemp()));
//					refrigeratedTruck.setCost(Integer.valueOf(eachCargoItemList.get(0).getTemp()));
//					refrigeratedTruck.setItemList(eachCargoItemList);
//					RTruckList.add(refrigeratedTruck);
//					System.out.println("last" + refrigeratedTruck);
//
//				} else {
//					eachCargoItemList.add(sortedtempCnItemList.get(i));
//				}
//			}
//		}

		int numOfItemTemp = 0;
		ArrayList<Item> cargoItemList = new ArrayList<>();
		ArrayList<RefrigeratedTruck> RTruckList = new ArrayList<>();


		for (int i = 0; i < sortedtempCnItemList.size(); i++){
			numOfItemTemp += sortedtempCnItemList.get(i).getReorderAmount();

			if (numOfItemTemp > capacityOfRTruck) {
				int amountNext = numOfItemTemp - capacityOfRTruck;
				int amountThis = sortedtempCnItemList.get(i).getReorderAmount()-amountNext;
//				sortedtempCnItemList.get(i).setReorderAmount(amountThis);
				Item addItem = new Item();
				addItem.setName(sortedtempCnItemList.get(i).getName());
				addItem.setReorderAmount(amountThis);
				cargoItemList.add(addItem);
				sortedtempCnItemList.get(i).setReorderAmount(amountNext);


//				RefrigeratedTruck refrigeratedTruck = new RefrigeratedTruck();
//				refrigeratedTruck.setTemp(Integer.valueOf(eachCargoItemList.get(0).getTemp()));
//				refrigeratedTruck.setCost(Integer.valueOf(eachCargoItemList.get(0).getTemp()));
//				refrigeratedTruck.setItemList(eachCargoItemList);
//				RTruckList.add(refrigeratedTruck);
//				System.out.println(">" + refrigeratedTruck);

//				eachCargoItemList.clear();
//				System.out.println(">" + refrigeratedTruck);

				numOfItemTemp = 0;

				i--;
			} else if (numOfItemTemp == capacityOfRTruck){
//				RefrigeratedTruck refrigeratedTruck = new RefrigeratedTruck();
//				refrigeratedTruck.setTemp(Integer.valueOf(eachCargoItemList.get(0).getTemp()));
//				refrigeratedTruck.setCost(Integer.valueOf(eachCargoItemList.get(0).getTemp()));
//				refrigeratedTruck.setItemList(eachCargoItemList);
//				RTruckList.add(refrigeratedTruck);
//
//				System.out.println("=" + refrigeratedTruck);

//				eachCargoItemList.clear();

				numOfItemTemp = 0;

			} else {
				cargoItemList.add(sortedtempCnItemList.get(i));
			}
		}
//
		for (Item item : cargoItemList) {
			System.out.println(item.getName() + "=" + Integer.toString(item.getReorderAmount()));

		}




//
//		leftSpaceOfRTruck = capacityOfRTruck * numOfRTruck - sumOfTempCnItem;
//
//		System.out.println(sumOfDryItem - leftSpaceOfRTruck);
//
//
//		numOfOTruck = (int)Math.ceil((double)(sumOfDryItem - leftSpaceOfRTruck) / capacityOfOTruck);
//		System.out.println(numOfOTruck);
	}


	public static ArrayList<Item> sortListFunction(ArrayList<Item> list) {
		Collections.sort(list, (i1, i2) -> {
			if(Integer.valueOf(i1.getTemp()) > Integer.valueOf(i2.getTemp())){
				return 1;
			}
			if(Integer.valueOf(i1.getTemp()) == Integer.valueOf(i2.getTemp())){
				return 0;
			}
			return -1;
		});
		return list;
	}

}
