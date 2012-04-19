package na.services.nutritionalMenus.business;

/**
 * Métodos de alto nivel de la capa de negocio correspondiente a:
 * Nutritional Menus (Nutritional Advisor)
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import na.oasisUtils.ami.AmiConnector;
import na.oasisUtils.ami.NutritionalCache;
import na.oasisUtils.trustedSecurityNetwork.TSFConnector;
import na.ws.NutriSecurityException;
import na.utils.OASIS_ServiceUnavailable;
import na.utils.PersistedImage;
import na.utils.ServiceInterface;
import na.utils.Utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class NewBusiness {
	private Log log = LogFactory.getLog(NewBusiness.class);
	/**
	 * Methods related to Nutritional Advisor -> Nutritional Menus	
	 * @throws OASIS_ServiceUnavailable 
	 */

	/*
	 * Gets today's menu's for current user
	 */
	public na.miniDao.DayMenu getTodayMenu() throws NutriSecurityException, OASIS_ServiceUnavailable{
		return this.getDayMenu(ServiceInterface.OP_GetTodayMenu);
	}
	
	/*
	 * Gets tomorrow's menu's for current user
	 */
	public na.miniDao.DayMenu getTomorrowMenu() throws OASIS_ServiceUnavailable {
		return this.getDayMenu(ServiceInterface.OP_GetTomorrowMenu);
	}
	
	/*
	 * Gets today's menu's for current user
	 * Connect with AMI, 
	 * day ["getTodayMenu", "getTomorrowMenu"]
	 */
	private na.miniDao.DayMenu getDayMenu(String operation) throws OASIS_ServiceUnavailable {
		AmiConnector ami = AmiConnector.getAMI();
		if (ami != null) {
			String[] input = {TSFConnector.getInstance().getToken()};
			na.miniDao.DayMenu menus = (na.miniDao.DayMenu)ami.invokeOperation(ServiceInterface.DOMAIN_Nutrition, operation, input, false);

			if (menus != null) {
				log.info("Business, UsersMenus found ID: " + menus.getUsersMenusID());

				if (menus.getMeals()!=null) {
					if (menus.getMeals().length==0) {
						log.info("Business, no meals found!");
						return null;
					} else { //Everything ok
						return  menus;
					}
				} else {
					log.info("Business, no meals found.");
				}
			} else { 
				log.info("Business, no menus found.");
			}
		}
		return null;
	}
	
	public na.miniDao.Tip[] getTips() throws OASIS_ServiceUnavailable {
		AmiConnector ami = AmiConnector.getAMI();
		if (ami != null) {
			String[] input = {TSFConnector.getInstance().getToken()};
			na.miniDao.Tip[] tips = (na.miniDao.Tip[])ami.invokeOperation(ServiceInterface.DOMAIN_Nutrition, ServiceInterface.OP_GetTips, input, false);

			if (tips != null) {
				log.info("Business, Tips found");

				if (tips.length==0) {
					log.info("Business, no tips!");
					return null;
				} else { //Everything ok
					return tips;
				}
			}
			else 
				log.info("Business, no menus found.");
		}
		return null;
	}
	
	public na.miniDao.DayMenu[] getThisWeekMenus() throws OASIS_ServiceUnavailable {
		return this.getThisWeekMenus(ServiceInterface.OP_GetThisWeekMenu);
	}
	
	private na.miniDao.DayMenu[] getThisWeekMenus(String operation) throws OASIS_ServiceUnavailable {
		AmiConnector ami = AmiConnector.getAMI();
		if (ami != null) {
			String[] input = {TSFConnector.getInstance().getToken()};
			na.miniDao.DayMenu[] menus = (na.miniDao.DayMenu[])ami.invokeOperation(ServiceInterface.DOMAIN_Nutrition, operation, input, false);

			if (menus != null) {
				log.info("Business, UsersMenus found");

				if (menus.length==0) {
					log.info("Business, no menus!");
					return null;
				} else { //Everything ok
					return menus;
				}
			}
			else 
				log.info("Business, no menus found.");
		}
		return null;
	}
	
	/*
	 * Gets tomorrow's menu's for current user
	 */
	public boolean changeMeal(int day, int mealCategory) throws OASIS_ServiceUnavailable {
		log.info("Business: changeMeal!");
		AmiConnector ami = AmiConnector.getAMI();
		if (ami != null) {
			String[] input = {TSFConnector.getInstance().getToken(),
							String.valueOf(day), 
							String.valueOf(mealCategory)};
			Boolean result = (Boolean)ami.invokeOperation(ServiceInterface.DOMAIN_Nutrition, ServiceInterface.OP_ChangeMeal, input, false);
			if (result!= null && result==true) {
				log.info("resetting today, tomorrow and weekly menu cache!");
				NutritionalCache myCache = new NutritionalCache();
				myCache.deleteFile(myCache.DATA_TODAY_MENU);
				myCache.deleteFile(myCache.DATA_TOMORROW_MENU);
				myCache.deleteFile(myCache.DATA_WEEK_MENU);
			}
			return result;
		}
		return false;
	}

	public byte[] getDishPicture(int recipeID) throws OASIS_ServiceUnavailable {
		AmiConnector ami = AmiConnector.getAMI();
		if (ami != null) {
			String[] input = {TSFConnector.getInstance().getToken(), ""+recipeID};
			byte[] picBytes= (byte[])ami.invokeOperation(ServiceInterface.DOMAIN_Nutrition, ServiceInterface.OP_GetDishPicture, input, false);

			if (picBytes != null) {
				log.info("Business, bytes found");
				return picBytes;
			}
			else 
				log.info("Business, no bytes found.");
		}
		return null;
	}
	
	public byte[] getTipPicture(int tipID) throws OASIS_ServiceUnavailable {
		// busca imagen local, si no la encuentra, las descarga y la guarda
		String recipeFileName = "tip_img_"+tipID+".data";
		String fullPath = ServiceInterface.PATH_TIP_PICTURES + recipeFileName;
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
	//		e1.printStackTrace();
			log.info("File Not found: "+recipeFileName);
		} catch (IOException e1) {
	//		e1.printStackTrace();
			log.info("IOException: "+e1.getLocalizedMessage());
		} catch (ClassNotFoundException e1) {
	//		e1.printStackTrace();
			log.info("Class not found: "+e1.getLocalizedMessage());
		}
		
		byte[] downloadedData = this.getTipPictureOnline(tipID);
		if (downloadedData!=null) {
			try {
				PersistedImage myImage = new PersistedImage();
				myImage.data = downloadedData;
				myImage.id = tipID;
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

	private byte[] getTipPictureOnline(int tipID) throws OASIS_ServiceUnavailable {
		AmiConnector ami = AmiConnector.getAMI();
		if (ami != null) {
			String[] input = {TSFConnector.getInstance().getToken(), ""+tipID};
			byte[] picBytes= (byte[])ami.invokeOperation(ServiceInterface.DOMAIN_Nutrition, ServiceInterface.OP_GetTipPicture, input, false);

			if (picBytes != null) {
				log.info("Business, bytes found");
				return picBytes;
			}
			else 
				log.info("Business, no bytes found.");
		}
		return null;
	}
	
//	/*
//	 * Returns the index of the meal
//	 */
//	private int getMenuMealIndex(UsersMenuMeals[] meals, int mealCategory) {
//		for (int i=0; i<meals.length; i++) {
//			if (meals[i].getMealCategories().getID() == mealCategory)
//				return i;
//		}
//		return -1; //not found
//	}

}
