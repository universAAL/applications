package na.services.recipes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import na.oasisUtils.profile.ProfileConnector;
import na.services.recipes.business.Business;
import na.services.recipes.ui.SubServiceFrame;
import na.services.recipes.ui.favourites.FavouritePanel;
import na.services.recipes.ui.single.SingleRecipeWindow;
import na.services.recipes.ui.today.DayRecipesWindow;
import na.utils.OASIS_ServiceUnavailable;
import na.utils.PersistedImage;
import na.utils.ServiceInterface;
import na.utils.Utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RecipesSubServiceLauncher {

	private Log log = LogFactory.getLog(RecipesSubServiceLauncher.class);
	public na.widgets.panel.AdaptivePanel canvas;
	private SubServiceFrame subServiceFrame;
	private Business business = new Business();

	public void showSubService() {
		log.info("Recipes main window");

		this.subServiceFrame = new SubServiceFrame(this);
		this.canvas.add(this.subServiceFrame);
		// by default show Today recipes
		this.showTodayRecipes();
		
		this.redraw();
	}
	
	public void showSubServiceWithRecipe(int recipeID) {
		log.info("Show Recipes directly");

		this.subServiceFrame = new SubServiceFrame(this);
		this.canvas.add(this.subServiceFrame);
		this.showRecipe(recipeID);
		
		this.redraw();
	
	}

	public void showTodayRecipes() {
		// clean and create box
		this.emptyBox();
		try {
			// get today's menu
			na.miniDao.DayMenu todayRecipeDescriptions = business.getTodayRecipes();
			// draw today's menu
			if (todayRecipeDescriptions != null) {
				DayRecipesWindow tw = new DayRecipesWindow();
				tw.setLauncher(this);
				tw.setDayMenu(todayRecipeDescriptions);
				tw.drawContent();
				this.subServiceFrame.content.add(tw);
				this.redraw();
			}
			else {
				Utils.Errors.showError(this.subServiceFrame.content, "There was an error loading today's recipes.");
			}
		} catch (OASIS_ServiceUnavailable e) {
			e.printStackTrace();
			Utils.Errors.showError(this.subServiceFrame.content, e.getMessage());
		}
	}
	
	public void showTomorrowRecipes() {
		// clean and create box
		this.emptyBox();
		try {
			// get tomorrow's menu
			na.miniDao.DayMenu tomorrowRecipeDescriptions = business.getTomorrowRecipes();
			// draw tomorrow's menu
			if (tomorrowRecipeDescriptions != null) {
				DayRecipesWindow tw = new DayRecipesWindow();
				tw.setLauncher(this);
				tw.setDayMenu(tomorrowRecipeDescriptions);
				tw.drawContent();
				this.subServiceFrame.content.add(tw);
				this.redraw();
			}
			else {
				Utils.Errors.showError(this.subServiceFrame.content, "There was an error loading tomorrow's recipes.");
//				this.showError("Couldn't find today's menu", this.canvas);
			}
		} catch (OASIS_ServiceUnavailable e) {
			e.printStackTrace();
			Utils.Errors.showError(this.subServiceFrame.content, e.getMessage());
		}
	}

	public void showMyFavoriteRecipes() {
		// clean and create box
		this.emptyBox();
		try {
			// get today's menu
			na.miniDao.Recipe[] recipes  = business.getMyFavoriteRecipes();
			// draw today's menu
			if (recipes != null) {
//			FavouriteRecipesWindow tw = new FavouriteRecipesWindow();
//			tw.setLauncher(this);
//			tw.setRecipes(recipes);
//			tw.drawContent();
				FavouritePanel tw = new FavouritePanel();
				tw.getReady(subServiceFrame);
				subServiceFrame.recipes = recipes;
				subServiceFrame.drawMyFavourites();
				this.subServiceFrame.content.add(tw);
				this.redraw();
			}
			else {
				Utils.Errors.showError(this.subServiceFrame.content, "There was an error loading favorite recipes.");
//				this.showError("Couldn't find today's menu", this.canvas);
			}
//			else 
//				this.showError("Couldn't find favourite recipes", this.canvas);
		} catch (OASIS_ServiceUnavailable e) {
			e.printStackTrace();
			Utils.Errors.showError(this.subServiceFrame.content, e.getMessage());
		}
	}
	

	public void showRecipe(int recipeID) {
		log.info("SubServiceLauncher(Recipes) showing: " + recipeID);
		// clean and create box
		this.emptyBox();
		try {
			// get today's recipe
			na.miniDao.Recipe recipe = business.getRecipe(recipeID);
			// draw today's recipe
			if (recipe != null) {
				SingleRecipeWindow tw = new SingleRecipeWindow();
				tw.setLauncher(this);
				tw.setRecipe(recipe);
				tw.drawContent();
				this.subServiceFrame.content.add(tw);
				this.redraw();
			}
			else {
				Utils.Errors.showError(this.subServiceFrame.content, "There was an error loading the recipe.");
//				this.showError("Couldn't find today's menu", this.canvas);
			}
//			else 
//				this.showError("Couldn't find recipe: " + recipeID, this.canvas);
		} catch (OASIS_ServiceUnavailable e) {
			e.printStackTrace();
			Utils.Errors.showError(this.subServiceFrame.content, e.getMessage());
		}
	}
	
	public boolean addRecipeToFavorites(int recipeID, String recipeName) throws OASIS_ServiceUnavailable {
		if (business.addRecipeToFavoritesWebServer(recipeID)== true) {
			return true;
//			log.info("Todo correcto, ahora guardar receta en profile");
//			try {
//				if (ProfileConnector.getInstance().shareRecipeOnProfile(recipeID, recipeName) == true) {
//					log.info("recipe stored in profile as favourite");
//					return true;
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		}
		return false;
	}
	
	public boolean removeRecipeFromFavorites(int recipeID) throws OASIS_ServiceUnavailable {
		if (business.removeRecipeFromFavoritesWebServer(recipeID)== true) {
			return true;
//			log.info("Todo correcto, ahora eliminar receta del profile");
//			try {
//				if (ProfileConnector.getInstance().unshareRecipeOnProfile(recipeID) == true) {
//					log.info("recipe unstored in profile as favourite");
//					return true;
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		}
		return false;
	}
	
	private void emptyBox() {
		this.subServiceFrame.content.removeAll();
		this.subServiceFrame.content.validate();
		this.subServiceFrame.content.repaint();
	}
	
	private void redraw() {
		this.canvas.validate();
		this.canvas.repaint();
	}
	
	public boolean shareRecipe(int recipeID, String course, String procedure) throws OASIS_ServiceUnavailable {
		return this.business.shareRecipeToSocialCommunity(recipeID, course, procedure);
	}

	public byte[] getDishPicture(int recipeID) throws OASIS_ServiceUnavailable {
//		return this.business.getDishPicture(recipeID);
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
