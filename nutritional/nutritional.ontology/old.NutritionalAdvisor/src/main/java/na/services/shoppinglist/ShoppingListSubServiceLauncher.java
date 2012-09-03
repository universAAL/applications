package na.services.shoppinglist;

import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;

import na.oasisUtils.socialCommunity.Agenda.Event;
import na.oasisUtils.socialCommunity.Agenda.GetMyEventsOutput;
import na.services.shoppinglist.business.Business;
import na.services.shoppinglist.business.ShoppingData;
import na.services.shoppinglist.ui.SubServiceFrame;
import na.services.shoppinglist.ui.actionsPanel.ActionsWindow;
import na.services.shoppinglist.ui.addFoodItem.AddFoodWindow;
import na.services.shoppinglist.ui.goingShop.ShoppingWindow;
import na.utils.OASIS_ServiceUnavailable;
import na.utils.ServiceInterface;
import na.utils.Utils;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ShoppingListSubServiceLauncher {
	private Log log = LogFactory.getLog(ShoppingListSubServiceLauncher.class);
	public na.widgets.panel.AdaptivePanel canvas;
	private Business business = new Business();
	private SubServiceFrame subServiceFrame;
	
	/*
	 * Draws the first window for Nutritional Menus 
	 **/

	public void showSubService() {
		log.info("ShoppingList main window");
		
		this.subServiceFrame = new SubServiceFrame(this);
		this.canvas.add(this.subServiceFrame);

		this.subServiceFrame.computeSelectedAndGeneratedDays();
		// Store new data
		ShoppingData.storeSelectionChanges(this.subServiceFrame.sizeSelection, Utils.Dates.getTodayDate());
		this.drawGoingShoppingBasicItems();
		this.loadGoingShoppingActionPanel();
		this.loadAndShowShoppingList();
		
//		this.loadAddFoodItem(); // testing purposes
	}
	
	// MAIN BUTTONS LOAD AND ADD_fOOD
	
	public void loadAddFoodItem() {
		this.subServiceFrame.showTemporaryError("");
		this.emptyBoxLeft();
		this.drawAddFoodItem();
	}
	
	/**
	 * Loads and shows the going shopping action items (print, send to email, selected days...)
	 */
	public void loadGoingShoppingActionPanel() {
		this.subServiceFrame.showTemporaryError("");
		this.emptyBoxLeft();
		this.drawGoingShoppingActionPanel();
	}
	
	//////////////////////////////////////////////////
	
	public void reLoad() {
//		this.subServiceFrame.computeSelectedAndGeneratedDays();
		// Store new data
//		ShoppingData.storeSelectionChanges(this.subServiceFrame.sizeSelection, Utils.Dates.getTodayDate());
//		this.drawGoingShoppingBasicItems();
//		this.loadGoingShoppingActionPanel();
		this.loadAndShowShoppingList();
	}
	
	public void reLoadSmart(String[] outsideEvents) {
		this.loadSmartShoppingList(outsideEvents);
	}

	/**
	 * Draw the basic going shopping items. With no data.
	 */
	private void drawGoingShoppingBasicItems() {
		ShoppingWindow shop = new ShoppingWindow();
		shop.getReady(this.subServiceFrame);
		this.subServiceFrame.content.add(shop);
		this.redraw();
	}
	
	private void drawGoingShoppingActionPanel() {
		ActionsWindow actionsPanel = new ActionsWindow();
		actionsPanel.setBorder(BorderFactory.createEmptyBorder());
		actionsPanel.getReady(this.subServiceFrame);
		this.subServiceFrame.leftPanel.remove(this.subServiceFrame.leftSide);
		this.subServiceFrame.leftPanel.setLeftContent(actionsPanel);
		this.subServiceFrame.leftSide = actionsPanel;
		this.subServiceFrame.leftPanel.setBorder(BorderFactory.createEmptyBorder());
		this.redraw();
	}
	
	private void drawAddFoodItem(){
		try {
			AddFoodWindow addFoodPanel = new AddFoodWindow();
			addFoodPanel.setBorder(BorderFactory.createEmptyBorder());
			String[] foodCategories =business.getFoodCategories();
			addFoodPanel.getReady(this.subServiceFrame, foodCategories);
			this.subServiceFrame.leftPanel.remove(this.subServiceFrame.leftSide);
			this.subServiceFrame.leftPanel.setLeftContent(addFoodPanel);
			this.subServiceFrame.leftSide = addFoodPanel;
			this.redraw();
		} catch (OASIS_ServiceUnavailable e) {
			e.printStackTrace();
			Utils.Errors.showError(this.subServiceFrame.content, e.getMessage());
		}
	}


	private void emptyBoxLeft() {
		this.subServiceFrame.emptyBoxLeft();
	}

	
	/**
	 * Loads the shopping list (remote) and shows it.
	 */
	private void loadAndShowShoppingList() {
		try {
			// get shoppingList from web service
			this.subServiceFrame.superSL = business.getSuperShoppingListMap(this.subServiceFrame.sizeSelection, this.subServiceFrame.today.get(Calendar.DAY_OF_WEEK), null);
			
			// draw shoppingList
			if (this.subServiceFrame.superSL != null)
				this.subServiceFrame.drawShoppingList();
			else 
				this.subServiceFrame.showError("Couldn't get shopping list.", null);
		} catch (OASIS_ServiceUnavailable e) {
			e.printStackTrace();
			Utils.Errors.showError(this.subServiceFrame.content, e.getMessage());
		}
	}
	
	public void loadReducedShoppingList() {
		try {
			// get shoppingList from web service
			this.subServiceFrame.today.add(Calendar.DAY_OF_MONTH, this.subServiceFrame.sizeAlreadyGenerated);
			int size = this.subServiceFrame.getReducedSLSize();
			this.subServiceFrame.superSL = business.getSuperShoppingListMap(size, this.subServiceFrame.today.get(Calendar.DAY_OF_WEEK), null);
			
			// draw shoppingList
			if (this.subServiceFrame.superSL != null) {
				log.info("TODO: mostrar cosas!");
	//			this.subServiceFrame.drawShoppingList(this.box_right);
			}
			else 
				this.subServiceFrame.showError("Couldn't get shopping list.", null);
		} catch (OASIS_ServiceUnavailable e) {
			e.printStackTrace();
			Utils.Errors.showError(this.subServiceFrame.content, e.getMessage());
		}
	}
	
	public void loadSmartShoppingList(String[] outsideEvents) {
		try {
			// get shoppingList from web service
//?¿?//			this.subServiceFrame.today.add(Calendar.DAY_OF_MONTH, this.subServiceFrame.sizeAlreadyGenerated);
			int size = this.subServiceFrame.getReducedSLSize();
			if (size==0) {
				log.error("shop: size is ZERO!");
			}
			this.subServiceFrame.superSL = business.getSuperShoppingListMap(size, this.subServiceFrame.today.get(Calendar.DAY_OF_WEEK), outsideEvents);
			
//			// draw shoppingList
//			if (this.subServiceFrame.superSL != null) {
//				log.info("TODO: mostrar cosas!");
//	//			this.subServiceFrame.drawShoppingList(this.box_right);
//			} else { 
//				this.subServiceFrame.showError("Couldn't get shopping list.", null);
//			}
		} catch (OASIS_ServiceUnavailable e) {
			e.printStackTrace();
			Utils.Errors.showError(this.subServiceFrame.content, e.getMessage());
		}
	}
	
	
	public String[] getFoodsByCategory(int foodCategory) throws OASIS_ServiceUnavailable {
//		this.window.removeTempError(box_right);
		String[] foods = business.getFoodsByCategory(foodCategory);
		return foods;
	}

	private void redraw() {
		this.canvas.validate();
		this.canvas.repaint();
	}

	public String[] getMyOutsideEvents(Calendar today, int sizeSelection) throws OASIS_ServiceUnavailable {
		// loop events, search for tags out lunch, dinner
		Event[] allEvents = business.getMyEvents(today, sizeSelection);
		ArrayList<String> interesingEvents = new ArrayList<String>();
		if (allEvents!=null) {
			for (Event event : allEvents) {
				String eventName = event.getTitle();
				log.info("Shop: Event: "+eventName);
				String tags = event.getEvent_tags();
				log.info("Shop: Event tags: "+tags);
				String startDate = event.getStart_date();
				log.info("Shop: Event time: " + startDate);
				log.info("");
				
				if (tags!=null && tags.length()>0) {
					int res = tags.indexOf(ServiceInterface.TAG_LUNCH_OUTSIDE);
					if (res!=-1) {
//						Calendar my = Calendar.getInstance();						
//						log.info("Shop: currentTime in milli: "+my.getTimeInMillis());
						//obtener fecha -> dia
						Calendar c = Calendar.getInstance();
						long millis = Long.parseLong(startDate) * 1000;
						c.setTimeInMillis(millis);
//						log.info("Shop: Time is: "+ millis + " -> " + c.getTime());
						log.info("Shop: Found interesting Event: "+ eventName + " at "+ c.getTime());
						int day_of_week = c.get(Calendar.DAY_OF_WEEK);
						log.info("Shop: event day of week: "+day_of_week);
						String set = ""+day_of_week+"@3"; // lunch
						interesingEvents.add(set);
					}
				}
			}
		}
		return (String[]) interesingEvents.toArray(new String[interesingEvents.size()]);
	}
}
