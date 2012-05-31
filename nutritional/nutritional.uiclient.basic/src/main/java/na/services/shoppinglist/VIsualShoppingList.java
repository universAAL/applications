package na.services.shoppinglist;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import na.miniDao.FoodItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class VIsualShoppingList {
	private Log log = LogFactory.getLog(VIsualShoppingList.class);
	public TreeMap<Integer, FoodItem> items;
    private int userID;
    
    static public final String Source_INVENTORY = "inventory";
    static public final String Source_EXTRA = "extra";
    static public final String Source_WEB = "web";
    static public final String Source_REJECTED_EXTRA = "rejectedExtra";
    
    
    //////////////////////////////////////////////////////////////////////    
	@SuppressWarnings("unchecked")
	public VIsualShoppingList() {
		super();
		this.items = new TreeMap<Integer, FoodItem>(new FoodItemComparator());//;new TreeMap<Integer, FoodItem>();
		this.userID = -1;
	}
	
	/////////////////////////////////////////////////////////////////////
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getUserID() {
		return userID;
	}
    
    /*
     * Operations:
     * 
     * setAlreadyBoughtIt(int foodID, int type)
     * 
     * unSetAlreadyBoughtIt(int foodID, int type)
     * 
     * getOrderedArray
     * 
     * getItemsInOrder(int position, int size)
     * 
     * addFoodItem(int id)
     * 
     */
	
	/**
     * Gets some items from the Shopping List
     * 
     * @param startingPosition the starting position
     * @param selectionSize the selection size
     * @return the some items
     */
    public FoodItem[] getSomeItems(int startingPosition, int selectionSize) {
    	Map<String, FoodItem> sortedSL = VIsualShoppingList.sortByName(this.items);
		FoodItem[] allItems = (FoodItem[]) sortedSL.values().toArray(new FoodItem[sortedSL.size()]);
		FoodItem[] selection = new FoodItem[selectionSize];
		int totalSize = sortedSL.size();
		log.info("Starting posisiton = "+startingPosition + " selectionSize = "+selectionSize + " totalSize= "+totalSize);
		if ((startingPosition+selectionSize)>totalSize)
			selectionSize = totalSize - startingPosition;
		System.arraycopy(allItems, startingPosition, selection, 0, selectionSize);
		
		return selection;
	}

	/*
	 * Compares IDs from both FoodItems
	 */
	@SuppressWarnings("unchecked")
	private class FoodItemComparator implements Comparator
	{
	  public int compare(Object obj1, Object obj2) {
	    Integer id_1 = (Integer) obj1;
	    Integer id_2 = (Integer) obj2;
	    return id_1.compareTo(id_2);
	    
//	    int result = 0;
//	 
//	    String name1 = (String) obj1;
//	    String name2 = (String) obj2;
//	 
//	    /* Sort alfabetically by name */
//	    if (name1 == null) {
//	    	log.info("item obj1: "+obj1 + " is null");
//	    	return -1;
//	    } else if (name2 == null) {
//	    	log.info("item obj2: "+obj2 + " is null");
//	    	return 1;
//	    }
//	    result = name1.compareTo(name2);
//	    return result;
	  }
	}
	
	/**
	 * Sorts shoppingList items alphabetically, according to their category
	 * 
	 * @param map the map
	 * @return the ordered map
	 */
	@SuppressWarnings("unchecked")
	public static Map sortByCategoryThenName(Map map) {
	     List list = new LinkedList(map.entrySet());
	     Collections.sort(list, new Comparator() {
	          public int compare(Object o1, Object o2) {
	        	  String cat1 = ((FoodItem)((Map.Entry)o1).getValue()).getCategory();
	        	  String cat2 = ((FoodItem)((Map.Entry)o2).getValue()).getCategory();
	        	  String name1 = ((FoodItem)((Map.Entry)o1).getValue()).getName();
	        	  String name2 = ((FoodItem)((Map.Entry)o2).getValue()).getName();
	        	  
	        	  int catCompare = cat1.compareTo(cat2);
	        	  // si son de la misma categoria, comparar nombres
	        	  if (catCompare==0)
	        		  return name1.compareTo(name2);
	        	  else
	        		  return catCompare;
	          }
	     });
	// logger.info(list);
	Map result = new LinkedHashMap();
	for (Iterator it = list.iterator(); it.hasNext();) {
	     Map.Entry entry = (Map.Entry)it.next();
	     result.put(entry.getKey(), entry.getValue());
	     }
	return result;
	}
	
	/**
	 * Sorts shoppingList items by name
	 * 
	 * @param map the map
	 * @return the ordered map
	 */
	@SuppressWarnings("unchecked")
	public static Map sortByName(Map map) {
	     List list = new LinkedList(map.entrySet());
	     Collections.sort(list, new Comparator() {
	          public int compare(Object o1, Object o2) {
	        	  return ((FoodItem)((Map.Entry)o1).getValue()).getName().compareToIgnoreCase(((FoodItem)((Map.Entry)o2).getValue()).getName());
	          }
	     });
	// logger.info(list);
	Map result = new LinkedHashMap();
	for (Iterator it = list.iterator(); it.hasNext();) {
	     Map.Entry entry = (Map.Entry)it.next();
	     result.put(entry.getKey(), entry.getValue());
	     }
	return result;
	}
}
