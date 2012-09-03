package na.utils;

import java.util.HashMap;
import java.util.Map;

public class ButtonLab {
	private static ButtonLab instance;
	private Map<String, Object> map = new HashMap<String, Object>();

	private ButtonLab() {
		
	}
	
	public synchronized static ButtonLab getInstance() {
		if (instance==null)
			instance = new ButtonLab();
		return instance;
	}
	
	public synchronized Object getObject(String id) {
		if (this.map.containsKey(id))
			return this.map.get(id);
		return null;
	}
	
	public synchronized void addObject(Object o, String id) {
		this.map.put(id,o);
	}
	
	public synchronized int size() {
		return map.size();
	}
	
	public synchronized String getNames() {
		String[] keys = (String[]) map.keySet().toArray(new String[map.size()]);
		String res = "";
		for (String string : keys) {
			res += string+"; ";
		}
		return res;
	}
	
	// Main navigation bar
	public static String main_NutritionalMenus = "Main menu Nutritional Menus";
	public static String main_Recipes = "Main menu Recipes";
	public static String main_ShoppingList = "Main menu Shopping List";
	public static String main_MyNutritionalProfile = "Main menu My Nutritional Profile";
	public static String main_Help = "Main menu Help";
	public static String main_Exit = "Main menu Exit";
	
	// Nutritional Menus
	public static String menus_Today = "NutritionalMenus Today";
	public static String menus_Tomorrow = "NutritionalMenus Tomorrow";
	public static String menus_Weekly = "NutritionalMenus Weekly";
	public static String menus_Tips = "NutritionalMenus Tips";
	
	// Tips
	public static String tips_next = "NutritionTips next";
	public static String tips_prev = "NutritionTips previous";
	
	// Recipes
	public static String recipes_Today = "Recipes Today";
	public static String recipes_Tomorrow = "Recipes Tomorrow";
	public static String recipes_Favourites = "Recipes Favourites";
	public static String recipes_Read = "Recipes Read";
	
	// Questionnaires
	public static String profile_eating = "Profile Eating Preferences";
	public static String profile_pending_questionnaires = "Profile Pending Questionnaires";
	public static String profile_startQuestionnaire = "Questionnaires Start";
	
	// Shopping List
	public static String shopping_GoingShopping = "ShoppingList Going Shopping";
	public static String shopping_AddFood = "ShoppingList Add Food";
}
