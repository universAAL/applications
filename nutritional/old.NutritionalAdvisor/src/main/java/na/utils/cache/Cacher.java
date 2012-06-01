package na.utils.cache;

import java.util.HashMap;
import java.util.Map;

import na.miniDao.FoodCategory;
import na.utils.Utils;

public class Cacher {
	
	private static Cacher cacher;
	
	private Map<Integer, FoodCategory> categories= new HashMap<Integer, FoodCategory>();
	
	public static Cacher get() {
		if (cacher==null)
			cacher = new Cacher();
		return cacher;
	}
	
	public FoodCategory getFoodCategory(int foodCateogoryID) {
		if (categories.containsKey(foodCateogoryID)) {
			Utils.println("Found cached foodCategory: "+foodCateogoryID);
			return categories.get(foodCateogoryID);
		} else {
			Utils.println("NOT Found cached foodCategory: "+foodCateogoryID + " downloading");
			
		}
		return null;
	}
}
