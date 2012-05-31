package na.utils.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;

import na.utils.ServiceInterface;
import na.utils.Utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SuperCache {
	
	
	protected final int		EXPIRATION_TYPE_SAME_WEEK		= 1;
	protected final int		EXPIRATION_TYPE_HOUR_BASED		= 2;
	
	private final long 		HOUR = 3600000;
	protected final long		EXPIRE_TODAY_MENU 				= 1 * HOUR;
	protected final long		EXPIRE_TOMORROW_MENU 			= EXPIRE_TODAY_MENU;
	protected final long		EXPIRE_FAVOURITE_RECIPES	 	= EXPIRE_TODAY_MENU;
	protected final long		EXPIRE_SHOPPING_LIST		 	= EXPIRE_TODAY_MENU;
	protected final long		EXPIRE_FOOD_CATEGORIES		 	= EXPIRE_TODAY_MENU;
	protected final long		EXPIRE_FOOD_SUBCATEGORIES	 	= EXPIRE_TODAY_MENU;
	protected final long		EXPIRE_ADVICE_IMAGES		 	= 6 * HOUR;
	protected final long		EXPIRE_WEEK_MENU			 	= 8 * HOUR;
	protected final long		EXPIRE_SINGLE_RECIPE		 	= EXPIRE_TODAY_MENU;
	protected final long		EXPIRE_MY_TIPS				 	= EXPIRE_TODAY_MENU;
	
	public final String 	DATA_TODAY_MENU 				= ServiceInterface.CACHE_FOLDER + "todayMenu.data"; 
	public final String 	DATA_TOMORROW_MENU 				= ServiceInterface.CACHE_FOLDER + "tomorrowMenu.data"; 
	protected final String 	DATA_FAVOURITE_RECIPES			= ServiceInterface.CACHE_FOLDER + "favouriteRecipes.data";
	protected final String 	DATA_CUSTOM_SHOPPING_LIST		= ServiceInterface.CACHE_FOLDER + "shoppingList";
	protected final String 	DATA_FOOD_CATEGORIES			= ServiceInterface.CACHE_FOLDER + "foodCategories.data";
	protected final String 	DATA_FOOD_SUBCATEGORIES			= ServiceInterface.CACHE_FOLDER + "foodSubCategories";
	public final String 	DATA_WEEK_MENU					= ServiceInterface.CACHE_FOLDER + "weekMenu.data";
	protected final String 	DATA_SINGLE_RECIPE				= ServiceInterface.CACHE_FOLDER + "recipe";
	protected final String 	DATA_ADVICE_Pictures			= ServiceInterface.CACHE_FOLDER + "advice_pic";
	protected final String 	DATA_MY_TIPS					= ServiceInterface.CACHE_FOLDER + "myTips.data";
	protected final String 	DATA_PENDING_QUESTIONNAIRES		= ServiceInterface.CACHE_FOLDER + "pendingQuestionnaires.data";


	protected Object getCachedObject(String source, long expiration, int expirationType) {
		try {
			// Read from disk using FileInputStream
			FileInputStream f_in = new FileInputStream(source);
			// Read object using ObjectInputStream
			ObjectInputStream obj_in = new ObjectInputStream (f_in);
			// Read an object
			Object obj = obj_in.readObject();
			if (obj instanceof PersistentObject) {
				f_in.close(); //Cose file!
				PersistentObject data = (PersistentObject) obj;
				switch (expirationType) {
				case EXPIRATION_TYPE_SAME_WEEK:
					if (this.isNotSameWeek(data.time)) {
						return null;
					} else {
						System.out.println("AMI: Returning serialized data: "+source);
						return data.object;
					}

				default:
					if (this.hasExpired(data.time, expiration)) {
						return null;
					} else {
						System.out.println("AMI: Returning serialized data: "+source);
						return data.object;
					}
				}
			}
		} catch (FileNotFoundException e1) {
//			e1.printStackTrace();
			System.out.println("no cached file for: "+source);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		return null;
	}
	

	protected Object getCachedObject(String source, long expiration) {
		return this.getCachedObject(source, expiration, EXPIRATION_TYPE_HOUR_BASED);
	}
	
	
	private boolean hasExpired(long originalTime, long expireRange) {
		try {
			Calendar rightNow = Calendar.getInstance();
			if ((rightNow.getTimeInMillis() - originalTime) > expireRange) {
				return true;
			} else {
				return false;	
			}
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}
	
	private boolean isNotSameWeek(long time) {
		try {
			Calendar rightNow = Calendar.getInstance();
			Calendar cachedTime = Calendar.getInstance();
			cachedTime.setTimeInMillis(time);
			if (rightNow.get(Calendar.WEEK_OF_YEAR) == cachedTime.get(Calendar.WEEK_OF_YEAR)) {
				return false;
			} else {
				return true;	
			}
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}
	

	public void storeObject(Object menu, String file) {
		if (menu!=null) {
			try {
				// Write to disk with FileOutputStream
				FileOutputStream f_out = new FileOutputStream(file);
				// Write object with ObjectOutputStream
				ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
				// Write object out to disk
				PersistentObject p = new PersistentObject();
				p.object = menu;
				p.time = Utils.Dates.getTodayCalendar().getTimeInMillis();
				obj_out.writeObject(p);
				f_out.close(); // close file!
				System.out.println("stored in cache: "+file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void deleteFile(String fileName) {
	    // A File object to represent the filename
	    File f = new File(fileName);

	    // Make sure the file or directory exists and isn't write protected
	    if (!f.exists()) {
//	      throw new IllegalArgumentException("Delete: no such file or directory: " + fileName);
	    	System.out.println("Delete: file does not exist: "+ fileName);
	    	return;
	    }

	    if (!f.canWrite()) {
//	      throw new IllegalArgumentException("Delete: write protected: "+ fileName);
	    	System.out.println("Delete: write protected: "+ fileName);
	    	return;
	    }

//	    // If it is a directory, make sure it is empty
//	    if (f.isDirectory()) {
//	      String[] files = f.list();
//	      if (files.length > 0)
//	        throw new IllegalArgumentException("Delete: directory not empty: " + fileName);
//	    }

	    // Attempt to delete it
	    boolean success = f.delete();

	    if (!success) {
//	      throw new IllegalArgumentException("Delete: deletion failed");
	    	System.out.println("Delete: deletion failed: "+fileName);
	    }
	}


	public void emptyCache() {
		String dirName = ServiceInterface.CACHE_FOLDER;
		File dir = new File(dirName);
	    if (!dir.exists()) {
	      System.out.println(dirName + " does not exist");
	      return;
	    }

	    String[] info = dir.list();
	    for (int i = 0; i < info.length; i++) {
	      File n = new File(dirName + File.separator + info[i]);
	      if (!n.isFile()) // skip ., .., other directories too
	        continue;
	      System.out.println("removing " + n.getPath());
	      if (!n.delete())
	        System.out.println("Couldn't remove " + n.getPath());
	    }
	}
	
}
