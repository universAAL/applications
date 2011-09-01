package na.services.nutritionalMenus;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import na.ws.NutriSecurityException;
import na.services.ServiceFrame;
import na.services.nutritionalMenus.business.NewBusiness;
import na.services.nutritionalMenus.ui.*;
import na.services.nutritionalMenus.ui.tips.GlobalPanel;
import na.services.nutritionalMenus.ui.today.NouTodayWindow;
import na.services.nutritionalMenus.ui.today.OldTodayWindow;
import na.services.nutritionalMenus.ui.week.WeeklyWindow;
import na.utils.OASIS_ServiceUnavailable;
import na.utils.PersistedImage;
import na.utils.ServiceInterface;
import na.utils.Utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class MenusSubServiceLauncher {
	private Log log = LogFactory.getLog(MenusSubServiceLauncher.class);
	public na.widgets.panel.AdaptivePanel canvas;
	private SubServiceFrame subServiceFrame;
	public NewBusiness business = new NewBusiness();
	private ServiceFrame serviceFrame;
	
	/*
	 * Draws the first window for Nutritional Menus 
	 **/

	public MenusSubServiceLauncher(ServiceFrame serviceFrame) {
		this.serviceFrame = serviceFrame;
	}


	public void showSubService() {
		log.info("Nutritional Menus main window");

		/**
		 * 
		 * Nutritional Menus
		 * Loads today's menu: breakfast, lunch and dinner
		 * Allows the user to change the current meal and see the recipe
		 *
		 */
		this.subServiceFrame = new SubServiceFrame(this);
		this.canvas.add(this.subServiceFrame);
		// by default show Today menus
		this.showTodayMenus();
//		this.showTomorrowMenus();
//		this.showNutritionTips();
//		this.showThisWeekMenus();
		
	}

	
	public void showTodayMenus() {
		// clean and create box
		this.emptyBox();
		na.miniDao.DayMenu todayMenuDescription;
		// get today's menu
		try {
			todayMenuDescription = business.getTodayMenu();
			// draw today's menu
			if (todayMenuDescription != null) {
//				TodayWindow tw = new TodayWindow();
				NouTodayWindow tw = new NouTodayWindow();
				tw.setLauncher(this);
				tw.setDayMenu(todayMenuDescription);
				tw.drawContent(true);
				this.subServiceFrame.content.add(tw);
				serviceFrame.redraw();
			}
			else {
				Utils.Errors.showError(this.subServiceFrame.content, "There was an error loading today's menu.");
			}
		} catch (NutriSecurityException e) {
			e.printStackTrace();
			Utils.showConnectionErrorPopup(e.getMessage());
		} catch (OASIS_ServiceUnavailable e) {
			e.printStackTrace();
			Utils.Errors.showError(this.subServiceFrame.content, e.getMessage());
		}
	}
	
	public void showTomorrowMenus() {
		// clean and create box
		this.emptyBox();
//		// load Business layer
		na.miniDao.DayMenu tomorrowMenuDescription;
		// get today's menu
		try {
			tomorrowMenuDescription = business.getTomorrowMenu();
			// draw today's menu
			if (tomorrowMenuDescription != null) {
//				TodayWindow tw = new TodayWindow();
				NouTodayWindow tw = new NouTodayWindow();
				tw.setLauncher(this);
				tw.setDayMenu(tomorrowMenuDescription);
				tw.drawContent(false);
				this.subServiceFrame.content.add(tw);
				serviceFrame.redraw();
			}
			else
				Utils.Errors.showError(this.subServiceFrame.content, "There was an error loading tomorrow's menu.");
		} catch (OASIS_ServiceUnavailable e) {
			e.printStackTrace();
			Utils.Errors.showError(this.subServiceFrame.content, e.getMessage());
		}
	}
	
	public void showThisWeekMenus() {
		// clean and create box
		this.emptyBox();
		try {
			na.miniDao.DayMenu[] thisWeekMenuDescriptions = business.getThisWeekMenus();
			// draw today's menu
			if (thisWeekMenuDescriptions != null) {
				WeeklyWindow newWeek = new WeeklyWindow();
				newWeek.getReady(thisWeekMenuDescriptions);
				this.subServiceFrame.content.add(newWeek);
				serviceFrame.redraw();
			}
			else 
				Utils.Errors.showError(this.subServiceFrame.content, "There was an error loading the week menu.");
		} catch (OASIS_ServiceUnavailable e) {
			e.printStackTrace();
			Utils.Errors.showError(this.subServiceFrame.content, e.getMessage());
		}
	}
	
	public void showNutritionTips() {
		// clean and create box
		this.emptyBox();
		
//		// load Business layer
//		na.services.nutritionalMenus.business.NewBusiness business = new NewBusiness();
		na.miniDao.Tip[] tipsDescriptions;
		// get user's tips
		try {
			tipsDescriptions = business.getTips();
			
			// draw today's menu
			if (tipsDescriptions != null) {
				GlobalPanel g = new GlobalPanel();
				g.setData(this, tipsDescriptions, 0);
				this.subServiceFrame.content.add(g);
				serviceFrame.redraw();
			}else 
//				this.showError("Couldn't find nutrition tips",this.canvas);
				Utils.Errors.showError(this.subServiceFrame.content, "There was an error loading tips.");
		} catch (OASIS_ServiceUnavailable e) {
			e.printStackTrace();
			Utils.Errors.showError(this.subServiceFrame.content, e.getMessage());
		} 
	}
	
	public void changeMeal(int day, int mealCategory) {
		log.info("SubServiceLauncher: change mealCategory: "+mealCategory);
		
		// draw today's menu
		try {
			if (business.changeMeal(day, mealCategory) == true)
				log.info("Change meal succesful");
			else 
				log.error("Couldn't change meal :(");
		} catch (OASIS_ServiceUnavailable e) {
			e.printStackTrace();
			Utils.Errors.showError(this.subServiceFrame.content, e.getMessage());
		}
	}
	
	public void showRecipe(int recipeID) {
		log.info("Show recipe...");
		this.serviceFrame.showSingleRecipe(recipeID);
//		na.services.Activator.nutritional.showRecipe(null, recipeID);
		
//		ServiceReference s = Activator.bundleContext.getServiceReference(ServiceInterface.SERVICE_NUTRITIONAL_ADVISOR);
//		if (s!=null) {
//			ServiceInterface nutritional = (ServiceInterface) Activator.bundleContext.getService(s);
//			log.info("Show recipe...");
//			nutritional.showRecipe(null, recipeID);
//		} else {
//			log.info(" Nutritional Advisor bundle NOT available!");
//			// Show error to user :(
//			JLabel error = new JLabel("Couldn't load Nutritional Advisor bundle");
//			log.info("Error cargando: " + ServiceInterface.SERVICE_NUTRITIONAL_ADVISOR);
//			error.setBounds(15, 15, 300, 50);
//			this.canvas.add(error);
//		}
	}
	
	private void emptyBox() {
		this.subServiceFrame.content.removeAll();
		this.subServiceFrame.content.validate();
		this.subServiceFrame.content.repaint();
	}
	
	

	public byte[] getDishPicture(int recipeID) throws OASIS_ServiceUnavailable {
		// busca imagen local, si no la encuentra, las descarga y la guarda
		String recipeFileName = "recipe_img_"+recipeID+".data";
		String fullPath = ServiceInterface.PATH_DISH_PICTURES + recipeFileName;
		try {
			// Read from disk using FileInputStream
			FileInputStream f_in = new FileInputStream(fullPath);
			// Read object using ObjectInputStream
			ObjectInputStream obj_in = new ObjectInputStream (f_in);
			// Read an object
			Object obj = obj_in.readObject();
			if (obj instanceof PersistedImage) {
				PersistedImage persistentData = (PersistedImage) obj;
				log.info("Returning serialized image: "+recipeFileName);
				return persistentData.data;
			}
		} catch (FileNotFoundException e1) {
//			e1.printStackTrace();
			log.info("File Not found: "+recipeFileName);
		} catch (IOException e1) {
//			e1.printStackTrace();
			log.info("IOException: "+e1.getLocalizedMessage());
		} catch (ClassNotFoundException e1) {
//			e1.printStackTrace();
			log.info("Class not found: "+e1.getLocalizedMessage());
		}
		
		byte[] downloadedData = this.business.getDishPicture(recipeID);
		if (downloadedData!=null) {
			try {
				PersistedImage myImage = new PersistedImage();
				myImage.data = downloadedData;
				myImage.id = recipeID;
				myImage.time = Utils.Dates.getTodayCalendar().getTimeInMillis();
				// Write to disk with FileOutputStream
				FileOutputStream f_out = new FileOutputStream(fullPath);
				// Write object with ObjectOutputStream
				ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
				// Write object out to disk
				obj_out.writeObject(myImage);
				log.info("File "+recipeFileName+" written.");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return downloadedData;
	}
	
	
}
