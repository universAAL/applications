package na.services.nutritionalMenus.ui.today;

import java.util.Comparator;

import na.utils.ServiceInterface.DishCategories;


/*
 * Compares dish type
 */
@SuppressWarnings("unchecked")
public class DishesComparator implements Comparator
{
  public int compare(Object obj1, Object obj2)
  {
    na.miniDao.Dish dish1 = (na.miniDao.Dish) obj1;
    na.miniDao.Dish dish2 = (na.miniDao.Dish) obj2;
    
    int first = -1;
    int second = 1;
 
    // MainCourse
    if (dish1.getDishCategory() == DishCategories.MainCourse) {
    	return first;
    }
    if (dish2.getDishCategory() == DishCategories.MainCourse) {
    	return second;
    }
    
    // FirstCourse
    if (dish1.getDishCategory() == DishCategories.FirstCourse) {
    	return first;
    }
    if (dish2.getDishCategory() == DishCategories.FirstCourse) {
    	return second;
    }
    
    // Starter
    if (dish1.getDishCategory() == DishCategories.Starter) {
    	return first;
    }
    if (dish2.getDishCategory() == DishCategories.Starter) {
    	return second;
    }
    
    // Side Dish
    if (dish1.getDishCategory() == DishCategories.SideDish) {
    	return first;
    }
    if (dish2.getDishCategory() == DishCategories.SideDish) {
    	return second;
    }
    
    // Dessert
    if (dish1.getDishCategory() == DishCategories.Dessert) {
    	return first;
    }
    if (dish2.getDishCategory() == DishCategories.Dessert) {
    	return second;
    }
    
    // Breakfast
    if (dish1.getDishCategory() == DishCategories.Breakfast) {
    	return first;
    }
    if (dish2.getDishCategory() == DishCategories.Breakfast) {
    	return second;
    }
    
    // Snack
    if (dish1.getDishCategory() == DishCategories.Snack) {
    	return first;
    }
    if (dish2.getDishCategory() == DishCategories.Snack) {
    	return second;
    }
    
    // Drinki
    if (dish1.getDishCategory() == DishCategories.Drink) {
    	return first;
    }
    if (dish2.getDishCategory() == DishCategories.Drink) {
    	return second;
    }
    
    return 0;
  }
}