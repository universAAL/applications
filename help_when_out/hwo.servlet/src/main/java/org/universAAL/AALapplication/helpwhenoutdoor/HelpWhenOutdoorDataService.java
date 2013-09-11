package org.universAAL.AALapplication.helpwhenoutdoor;

import java.util.NoSuchElementException;


public interface HelpWhenOutdoorDataService {

	/**
	 * Set the latitude. 
	 * @param latitude
	 */
	public void setLatitude(Double latitude);
	/**
	 * Set the longitude
	 * @param longitude
	 */
	public void setLongitude(Double longitude);
	
	
	/**
	 * Retrieves the history from the middleware
	 * @param fromTime The moment from which the data are retrieved, expressed in milliseconds
	 * @return A string representing the previous locations
	 * or an empty String if there aren't any of them.  
	 */
	
	public abstract String getHistoryData(long fromTime);

	/**
	 * Publish the location to the middleware as a ContextEvent
	 * @param latitude The latitude 
	 * @param longitude The longitude
	 */
	//public abstract void publishLocation(Double latitude, Double longitude);
	
	/**
	 * Retrieves only the updated position.
	 * @return A string representing only the GPS coordinate updated,
	 * or an empty String if the position is not updated  
	 */
	public abstract String getUpdatedPosData();

	/**
	 * Retrieves data from the middleware and creates a representation of these data.
	 * @return A String representing the data for Help when outdoor service
	 */
	public abstract String getAllData();
	/**
	 * Set a particular area and persist it in the storage (a DB, an XML file and so on)
	 * @param areaData The string representing the point of the area polygon. 
	 * Each point is represented by a pair latitude, longitude. For example, a triangle is :
	 * 37.41, -122.01, 37.40, -121.90,37.39, -122.00,37.40, -122.02
	 * @param which Which kind of area must be set. It can be "safeArea" or "homeArea"
	 * @throws NumberFormatException if a latitude or longitude value is not in the correct range 
	 * @throws  NoSuchElementException if the data to parse don't contain enough points to form a polygon
	 */
	public abstract void setArea(String areaData, String which) 
		throws NumberFormatException, NoSuchElementException;
	/**
	 * Set the position of user's house
	 * @param latlng The latitude and longitude coordinates of the house, separated by a space. For example
	 * "37.41 56.78"
	 */
	public abstract void setHomePosition(String latlng);
	/**
	 * Set the address location in the history list if the user has changed its position.
	 * The location is from the latitude, longitude pair previously stored
	 * 
	 * @param time The time in milliseconds when the user visited the last location.
	 */
	public abstract void setHistoryEntry(long time);
}