package na.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// deprecated
public class FavoriteRecipes {
	
	private static Log log = LogFactory.getLog(FavoriteRecipes.class);
	public static final String filename = "C:\\OASIS\\user_favorite_recipes.properties";

	
	
	public static boolean addRecipe(int recipeID) {
		// load file
		Properties properties = new Properties();
		try {
		    properties.load(new FileInputStream(filename));
		    String favorites = properties.getProperty("favoriteRecipes");
		    if (favorites==null) {
//		    	log.info("No hay favoritos");
		    	properties.setProperty("favoriteRecipes", recipeID+";");
			    try {
			        properties.store(new FileOutputStream(filename), null);
			        log.info("Success! File: " + filename + " edited.");
			        return true;
			    } catch (IOException e) {
			    	log.info("Error, couldn't write file: "+e.toString());
			    	return false;
			    }
		    } else {
//		    	log.info("SI hay favoritos");
		    	String[] recipes = favorites.split(";");
		    	for (String recipe : recipes) {
					if (recipe.compareTo(String.valueOf(recipeID))==0) {
						log.info("The recipe is already stored");
						return true;
					} 
				}
		    	log.info("Storing new recipe...");
				properties.setProperty("favoriteRecipes", favorites + recipeID+";");
			    try {
			        properties.store(new FileOutputStream(filename), null);
			        log.info("Success! File: " + filename + " has one more recipe.");
			        return true;
			    } catch (IOException e) {
			    	log.info("Error, couldn't write file: "+e.toString());
			    	return false;
			    }
		    }
		    
		} catch (IOException e) {
			log.info("Error, couldn't read file: "+e.toString());
		}
		
		return true;
	}
	
	public static boolean removeRecipe(int recipeID) {
		// load file
		Properties properties = new Properties();
		try {
		    properties.load(new FileInputStream(filename));
		    String favorites = properties.getProperty("favoriteRecipes");
		    if (favorites==null) {
		    	return false;
		    } else {
//		    	log.info("SI hay favoritos");
		    	String search = ""+recipeID+";";
				
				Pattern pattern = Pattern.compile(search);
				Matcher matcher = pattern.matcher(favorites);
		        String output = matcher.replaceAll("");
		        properties.setProperty("favoriteRecipes", output);
			    try {
			        properties.store(new FileOutputStream(filename), null);
			        log.info("Success! File: " + filename + " has one recipe less.");
			        return true;
			    } catch (IOException e) {
			    	log.info("Error, couldn't write file: "+e.toString());
			    	return false;
			    }
		    }
		    
		} catch (IOException e) {
			log.info("Error, couldn't read file: "+e.toString());
		}
		
		return false;
	}
	
	static public String[] getMyFavoriteRecipes() {
		// load file
		Properties properties = new Properties();
		try {
		    properties.load(new FileInputStream(filename));
		    String favorites = properties.getProperty("favoriteRecipes");
		    if (favorites==null) {
		    	return null;
		    } else {
		    	return favorites.split(";");
		    }
		} catch (IOException e) {
			log.info("Error, couldn't read file: "+e.toString());
		}
		
		return null;
	}
	
	static public boolean isFavorite(int recipeID) {
		Properties properties = new Properties();
		try {
		    properties.load(new FileInputStream(filename));
		    String favorites = properties.getProperty("favoriteRecipes");
		    if (favorites==null) {
		    	log.info("There are no favorites");
		    	return false;
		    } else {
		    	String[] recipes = favorites.split(";");
		    	for (String recipe : recipes) {
					if (recipe.compareTo(String.valueOf(recipeID))==0) {
						return true;
					} 
				}
		    }
		} catch (IOException e) {
			log.info("Error, couldn't read file: "+e.toString());
		}
		return false;
	}
	

//	// Write properties file.
//	static private boolean CreateFile() {
//		Properties properties = new Properties();
//		try {
//		    properties.store(new FileOutputStream(filename), null);
//		    log.info("File: "+ filename + " written succesfuly");
//		    return true;
//		} catch (IOException e) {
//			log.info("Error, couldn't write file: "+e.toString());
//			return false;
//		}
//	}
}
