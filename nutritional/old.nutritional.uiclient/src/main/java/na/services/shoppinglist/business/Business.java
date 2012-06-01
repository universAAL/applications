package na.services.shoppinglist.business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import na.miniDao.FoodItem;
import na.oasisUtils.ami.AmiConnector;
import na.oasisUtils.socialCommunity.Agenda.Event;
import na.oasisUtils.socialCommunity.Agenda.GetMyEventsOutput;
import na.oasisUtils.socialCommunity.Friends.AuthToken;
import na.oasisUtils.socialCommunity.Friends.FriendsPortProxy;
import na.oasisUtils.socialCommunity.Friends.Group;
import na.oasisUtils.socialCommunity.Friends.GroupsResponse;
import na.oasisUtils.socialCommunity.Friends.StatusResponse;
import na.oasisUtils.trustedSecurityNetwork.TSFConnector;
import na.services.shoppinglist.VIsualShoppingList;
import na.utils.ExtraShoppingItems;
import na.utils.FoodInventory;
import na.utils.OASIS_ServiceUnavailable;
import na.utils.ServiceInterface;
import na.utils.Setup;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class Business {
	private Log log = LogFactory.getLog(Business.class);
	
	
	/**
	 * Gets the super shopping list map.
	 * SL = Original items + extraItems - foodInventoryItems
	 * 
	 * @param size the size
	 * @param startingDay the starting day
	 * @return the super shopping list map
	 * @throws OASIS_ServiceUnavailable 
	 */
	public VIsualShoppingList getSuperShoppingListMap(int size, int startingDay, String[] outsideEvents) throws OASIS_ServiceUnavailable {
		log.info("Shop: getSuperShoppingListMap size: "+size + " startingDay: "+startingDay);
//		1. original items, from webservice
		VIsualShoppingList vi;
		if (outsideEvents!=null && outsideEvents.length > 0) {
			log.info("Shop: events found: "+outsideEvents.length);
			vi = this.getSuperShoppingListFromWS(ServiceInterface.OP_GetSmartCustomShoppingList,size, startingDay, outsideEvents);
		} else {
			log.info("Shop: NO events found");
			vi = this.getSuperShoppingListFromWS(ServiceInterface.OP_GetCustomShoppingList,size, startingDay, null);
		}
		if (vi==null || vi.items == null)
			return vi;
//		for (FoodItem item : vi.items.values()) {
//			log.info("Item: "+item.getName());
//		}
		
//		2. checks if a food is already in Food Inventory
		// if found, sets source to INVENTORY
		String[] foodsInventory = FoodInventory.getFoodInventory();
		if (foodsInventory!= null) {
			log.info("Food inventory. Size: "+foodsInventory.length);
			for (String food : foodsInventory) {
				if (food.contains("@")) {
					int extraID = Integer.parseInt(food.split("@")[FoodInventory.ID_col]);
//					String extraName = food.split("@")[FoodInventory.NAME_col];
					FoodItem foundFood = vi.items.get(extraID); // search based on ID
					if (foundFood!=null) {
						foundFood.setSource(VIsualShoppingList.Source_INVENTORY);
						vi.items.put(foundFood.getFoodID(), foundFood);
					}
				}
			}
		} else {
			log.info("Food inventory empty");
		}
		
//		3. check add extra food items to list
		String[] foodsExtra = ExtraShoppingItems.getExtraFoodInventory();
		
		if (foodsExtra!=null) {
			for (String food : foodsExtra) {
				String[] elem = food.split("@");
				if (elem==null || elem.length!=3)
					break;
				int id_col = ExtraShoppingItems.ID_col;
				int name_col = ExtraShoppingItems.NAME_col;
				int category_col = ExtraShoppingItems.CATEGORY_col;
				
				String name = elem[name_col];
				int id = Integer.valueOf(elem[id_col]).intValue();
				String category = elem[category_col].toLowerCase();
				
				FoodItem oldFood = vi.items.get(id); // search based on ID
				if (oldFood!=null) { //found
					//nothing to do
				} else { //not found, add it as an extra
					FoodItem newFood = new FoodItem();
					newFood.setFoodID(id);
					newFood.setName(name);
					newFood.setSource(VIsualShoppingList.Source_EXTRA);
					newFood.setCategory(category);
					vi.items.put(id, newFood);
				}
			}
		}
		return vi;
	}
	
//	/**
//	 * Gets the super shopping list map.
//	 * SL = Original items + extraItems - foodInventoryItems
//	 * 
//	 * @param size the size
//	 * @param startingDay the starting day
//	 * @return the super shopping list map
//	 * @throws OASIS_ServiceUnavailable 
//	 */
//	public VIsualShoppingList getSuperShoppingListMapSmart(int size, String startingDay) throws OASIS_ServiceUnavailable {
//		log.info("getSuperShoppingListMapSmart size: "+size + " startingDay: "+startingDay);
////		1. original items, from webservice
//		VIsualShoppingList vi = this.getSuperShoppingListFromWS(ServiceInterface.OP_GetCustomShoppingList,size, startingDay, null);
//		if (vi==null || vi.items == null)
//			return vi;
////		for (FoodItem item : vi.items.values()) {
////			log.info("Item: "+item.getName());
////		}
//		
////		2. checks if a food is already in Food Inventory
//		// if found, sets source to INVENTORY
//		String[] foodsInventory = FoodInventory.getFoodInventory();
//		if (foodsInventory!= null) {
//			log.info("Food inventory. Size: "+foodsInventory.length);
//			for (String food : foodsInventory) {
//				if (food.contains("@")) {
//					int extraID = Integer.parseInt(food.split("@")[FoodInventory.ID_col]);
////					String extraName = food.split("@")[FoodInventory.NAME_col];
//					FoodItem foundFood = vi.items.get(extraID); // search based on ID
//					if (foundFood!=null) {
//						foundFood.setSource(VIsualShoppingList.Source_INVENTORY);
//						vi.items.put(foundFood.getFoodID(), foundFood);
//					}
//				}
//			}
//		} else {
//			log.info("Food inventory empty");
//		}
//		
////		3. check add extra food items to list
//		String[] foodsExtra = ExtraShoppingItems.getExtraFoodInventory();
//		
//		if (foodsExtra!=null) {
//			for (String food : foodsExtra) {
//				String[] elem = food.split("@");
//				if (elem==null || elem.length!=3)
//					break;
//				int id_col = ExtraShoppingItems.ID_col;
//				int name_col = ExtraShoppingItems.NAME_col;
//				int category_col = ExtraShoppingItems.CATEGORY_col;
//				
//				String name = elem[name_col];
//				int id = Integer.valueOf(elem[id_col]).intValue();
//				String category = elem[category_col].toLowerCase();
//				
//				FoodItem oldFood = vi.items.get(id); // search based on ID
//				if (oldFood!=null) { //found
//					//nothing to do
//				} else { //not found, add it as an extra
//					FoodItem newFood = new FoodItem();
//					newFood.setFoodID(id);
//					newFood.setName(name);
//					newFood.setSource(VIsualShoppingList.Source_EXTRA);
//					newFood.setCategory(category);
//					vi.items.put(id, newFood);
//				}
//			}
//		}
//		return vi;
//	}
	

	/**
	 * Gets the super shopping list from ws.
	 * @param outsideEvents 
	 * 
	 * @param operation: weekly shoppingList, single sl?
	 * @return the super shopping list, items sorted by name.
	 * @throws OASIS_ServiceUnavailable 
	 */
	private VIsualShoppingList getSuperShoppingListFromWS(String operation, int size, int startingDay, String[] outsideEvents) throws OASIS_ServiceUnavailable {
		AmiConnector ami = AmiConnector.getAMI();
		if (ami != null) {
			log.info("Shop: getSuperShoppingList");
			na.miniDao.ShoppingList shoppingList = null;
			if (outsideEvents!=null && outsideEvents.length>0) {
				Object[] input = {TSFConnector.getInstance().getToken(), String.valueOf(size), startingDay, outsideEvents};
				shoppingList = (na.miniDao.ShoppingList)ami.invokeOperation(ServiceInterface.DOMAIN_Nutrition, operation, input, false);
			} else {
				Object[] input = {TSFConnector.getInstance().getToken(), String.valueOf(size), startingDay};
				shoppingList = (na.miniDao.ShoppingList)ami.invokeOperation(ServiceInterface.DOMAIN_Nutrition, operation, input, false);
			}
			VIsualShoppingList vsl = new VIsualShoppingList();
			
			if (shoppingList != null) {
				log.info("ShoppingListBusiness, list found. Size: " + shoppingList.getItems().length);

				if (shoppingList.getItems().length==0) {
					log.info("ShoppingListBusiness, list is empty!");
					return null;
				} else { //Everything ok
					vsl.setUserID(shoppingList.getUserID());
					
					// Items are inserted and sorted by name
					for (na.miniDao.FoodItem item : shoppingList.getItems()) {
						item.setCategory(item.getCategory().toLowerCase());
						vsl.items.put(item.getFoodID(), item);
					}
					
					return vsl;
				}
			}
			else 
				log.info("getSuperShoppingList, no list found.");
		} else {
			log.info("AMI Bundle not available!");
		}
		return null;
	}
	
	
//	/**
//	 * Gets the smart super shopping list from ws.
//	 * 
//	 * @param operation: weekly shoppingList, single sl?
//	 * @return the super shopping list, items sorted by name.
//	 * @throws OASIS_ServiceUnavailable 
//	 */
//	private VIsualShoppingList getSmartSuperShoppingListFromWS(String operation, int size, String startingDay, String[] outsideEvents) throws OASIS_ServiceUnavailable {
//		AmiConnector ami = AmiConnector.getAMI();
//		if (ami != null) {
//			log.info("getSmartSuperShoppingListFromWS");
//			//TODO set right operation, outsideEvents
//			String[] input = {TSFConnector.getInstance().getToken(), String.valueOf(size), startingDay};
//			miniDao.ShoppingList shoppingList = (miniDao.ShoppingList)ami.invokeOperation(ServiceInterface.DOMAIN_Nutrition, operation, input);
//			VIsualShoppingList vsl = new VIsualShoppingList();
//			
//			if (shoppingList != null) {
//				log.info("ShoppingListBusiness, list found. Size: " + shoppingList.getItems().length);
//
//				if (shoppingList.getItems().length==0) {
//					log.info("ShoppingListBusiness, list is empty!");
//					return null;
//				} else { //Everything ok
//					vsl.setUserID(shoppingList.getUserID());
//					
//					// Items are inserted and sorted by name
//					for (miniDao.FoodItem item : shoppingList.getItems()) {
//						item.setCategory(item.getCategory().toLowerCase());
//						vsl.items.put(item.getFoodID(), item);
//					}
//					
//					return vsl;
//				}
//			}
//			else 
//				log.info("getSuperShoppingList, no list found.");
//		} else {
//			log.info("AMI Bundle not available!");
//		}
//		return null;
//	}
	
	public String[] getFoodCategories() throws OASIS_ServiceUnavailable {
		AmiConnector ami = AmiConnector.getAMI();
		if (ami != null) {
			String[] input = {TSFConnector.getInstance().getToken()};
			na.miniDao.FoodCategory[] foodCategories = (na.miniDao.FoodCategory[])ami.invokeOperation(ServiceInterface.DOMAIN_Nutrition, ServiceInterface.OP_GetFoodCategories, input, false);

			if (foodCategories != null) {
				log.info("ShoppingListBusiness, foodCategories found size: " + foodCategories.length);
				String[] result = new String[foodCategories.length];
				
				/**
				 * 0 => ID
				 * 1 => Description
				 * 2 => Picture
				 */
				int pos =0;
				for (na.miniDao.FoodCategory food : foodCategories) {
					String[] datos = new String[3];
					datos[0] = String.valueOf(food.getID());
					datos[1] = food.getDescription().substring(0, 1).toUpperCase() + food.getDescription().substring(1);
					datos[2] = "";
					result[pos++] = datos[0] + "@" + datos[1] + "@" + datos[2];
				}
				
				return result;
			}
			else 
				log.info("RecipeBusiness, no foodCategories found.");
		} else {
			log.info("AMI Bundle not available!");
		}
		return null;
	}
	
	public String[] getFoodsByCategory(int foodCategory) throws OASIS_ServiceUnavailable {
		AmiConnector ami = AmiConnector.getAMI();
		if (ami != null) {
			String[] input = {String.valueOf(foodCategory), TSFConnector.getInstance().getToken()};
			na.miniDao.FoodItem[] foods = (na.miniDao.FoodItem[])ami.invokeOperation(ServiceInterface.DOMAIN_Nutrition, ServiceInterface.OP_GetFoodsByCategory, input, false);

			if (foods != null) {
				log.info("ShoppingListBusiness, foods found size: " + foods.length);
				List<String> resultList = new ArrayList<String>();
				/**
				 * 0 => Description
				 * 1 => ID
				 * 2 => Picture
				 * 3 => Category
				 */
				for (na.miniDao.FoodItem food : foods) {
					String[] datos = new String[3];
					datos[1] = String.valueOf(food.getFoodID());
					if (food.getName()==null)
						continue;
					datos[0] = food.getName().substring(0, 1).toUpperCase() + food.getName().substring(1).toLowerCase();
					datos[2] = "";
					resultList.add(datos[0] + "@" + datos[1] + "@" + datos[2] + "@" + food.getCategory());
				}
				String[] result = resultList.toArray(new String[0]);
				Arrays.sort(result, String.CASE_INSENSITIVE_ORDER);
				return result;
			}
			else 
				log.info("ShoppingListBusiness, no foods found.");
		} else {
			log.info("AMI Bundle not available!");
		}
		return null;
	}

	public Event[] getMyEvents(Calendar today, int sizeSelection) throws OASIS_ServiceUnavailable {
		AmiConnector ami = AmiConnector.getAMI();
		if (ami != null) {
			log.info("Shop: size: "+sizeSelection);
			// 1. Login
			String[] input = {TSFConnector.getInstance().getSocialCommunitiesUsername(), TSFConnector.getInstance().getSocialCommunitiesPassword()};
			na.oasisUtils.socialCommunity.Agenda.AuthToken token = (na.oasisUtils.socialCommunity.Agenda.AuthToken) ami.invokeOperation(ServiceInterface.DOMAIN_SocialCommunity, ServiceInterface.OP_Social_AgendaLogin, input, Setup.use_WS_toConnectToSocialCommunities());
			if (token==null) {
				log.error("token is null");
				return null;
			}
			
			// 2. GetMyEvents from Date1 to Date2
			Calendar c1 = (Calendar)today.clone();
			c1.add(Calendar.DAY_OF_YEAR, -1);
			c1.set(Calendar.HOUR_OF_DAY, 21);
			c1.set(Calendar.MINUTE, 59);
			System.out.println("Time1 is: "+c1.getTime());
			String time = String.valueOf(c1.getTimeInMillis()/1000);
			Calendar c2 = (Calendar)today.clone();
			c2.set(Calendar.HOUR_OF_DAY, 21);
			c2.set(Calendar.MINUTE, 59);
			if (sizeSelection>1) {
				c2.add(Calendar.DAY_OF_YEAR, (sizeSelection-1));
			}
			System.out.println("Time2 is: "+c2.getTime());
			String time2 = String.valueOf(c2.getTimeInMillis()/1000);
//			Calendar c = Calendar.getInstance();
//			c.set(2011, Calendar.JANUARY, 1, 0, 0);
//			String time = String.valueOf(c.getTimeInMillis()/1000);
			System.out.println("Time1 is: "+ time);
//			Calendar c2 = Calendar.getInstance();
//			c2.set(2011, Calendar.DECEMBER, 28, 0, 0);
//			String time2 = String.valueOf(c2.getTimeInMillis()/1000);
			System.out.println("Time2 is: "+ time2);
			Object[] inputs = {token, time, time2};
			GetMyEventsOutput eventsOutput= (GetMyEventsOutput) ami.invokeOperation(ServiceInterface.DOMAIN_SocialCommunity, ServiceInterface.OP_Social_AgendaGetMyEvents, inputs, Setup.use_WS_toConnectToSocialCommunities());
			
			// 3. Inspect event tags and 
			if (eventsOutput!=null && eventsOutput.getEvents()!=null && eventsOutput.getEvents().length>0) {
				Event[] events = eventsOutput.getEvents();
				if (events!=null) {
					for (Event event : events) {
						String eventName = event.getTitle();
//						System.out.println("Event: "+eventName);
						String tags = event.getEvent_tags();
//						System.out.println("Event tags: "+tags);
//						System.out.println("");
					}
					return events;
				}
			} else {
				log.info("Shop: there are no events :(");
			}
		} else {
			log.error("Couldn't get AMI");
		}
		return null;
	}

//	/*
//	 * Compares Field 1 from both arrays
//	 */
//	class StringComparator implements Comparator
//	{
//	  public int compare(Object obj1, Object obj2)
//	  {
//	    int result = 0;
//	    int field = 1;
//	 
//	    String[] str1 = (String[]) obj1;
//	    String[] str2 = (String[]) obj2;
//	 
//	    /* Sort on first element of each array (last name) */
//	    result = str1[field].compareTo(str2[field]);
//	    return result;
//	  }
//	}

}
