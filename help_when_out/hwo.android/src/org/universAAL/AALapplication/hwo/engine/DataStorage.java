package org.universAAL.AALapplication.hwo.engine;

/* This class belonged to the servlet. We have copied it here to make use of its methods to manage POI, homeLocation etc. */

//TODO: Everytime one of this methods is used in the DataStorage class of the servlet, we have to make the same call here, via the context bus, to keep both 
// databases synchroniced (although HWO in the mobile device only needs data of POI and Home Location 

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Vector;

import org.universAAL.AALapplication.hwo.model.CoordinateSystem;
import org.universAAL.AALapplication.hwo.model.Point;

import android.os.Environment;
import android.util.Log;

public class DataStorage {

	private static final String TAG = "DataStorage";
	private static final String separator = "/";
	private static final String dataDir = Environment.getExternalStorageDirectory().getPath()+"/data/felix/configurations/etc/hwo.mobile/servlet";
	String safeAreaFileName, homePositionFileName, historyFileName,
			poiListFileName, historyIntervalFileName, mapKeyFileName,
			homeAreaFileName;

	private static DataStorage instance;
	private long historyInterval;
	private TreeMap addressLocations;

	// private static File confHome = new File(
	// new BundleConfigHome("hwo.mobile").getAbsolutePath());

	/**
	 * The data directory where configuration files are stored
	 * 
	 * @return The data directory with the trailing separator
	 */
	public String getDataDirectory() {
		return dataDir + separator;
	}

	private void setDataDirectory() {
		// TODO: WARNING, can issue a SecurityException if the process is not
		// allowed to access
		// the user home

		Log.d(TAG, "Ruta del directorio de almacenamiento es: " + dataDir);

		File dataDirFile = new File(dataDir);
		if (!dataDirFile.exists()) {
			// TODO: handle the security exception
			dataDirFile.mkdirs();
		}

	}

	public static DataStorage getInstance() {
		if (instance == null)
			instance = new DataStorage();
		return instance;

	}

	/**
	 * Private constructor, Singleton pattern
	 */
	private DataStorage() {

		setDataDirectory();
		safeAreaFileName = dataDir + separator + "safearea";
		homePositionFileName = dataDir + separator + "homePosition";
		historyFileName = dataDir + separator + "history";
		historyIntervalFileName = dataDir + separator + "historyInterval";
		poiListFileName = dataDir + separator + "poiList";
		mapKeyFileName = dataDir + separator + "mapKey";
		homeAreaFileName = dataDir + separator + "homearea";

		addressLocations = new TreeMap();
	}

	public void clearData() throws IOException {

		File[] filesToBeCleared = new File[5];

		filesToBeCleared[0] = new File(safeAreaFileName);
		filesToBeCleared[1] = new File(homePositionFileName);
		filesToBeCleared[2] = new File(poiListFileName);
		filesToBeCleared[3] = new File(mapKeyFileName);
		filesToBeCleared[4] = new File(homeAreaFileName);
		for (int i = 0; i < filesToBeCleared.length; ++i) {
			// overwrite the file opening a new FileOutputStream
			// and closing it
			if (filesToBeCleared[i].exists()) {
				Log.d(TAG, "Clearing file: " + filesToBeCleared[i].getName());
				FileOutputStream fos = new FileOutputStream(
						filesToBeCleared[i], false);

				fos.close();

			}
		}
		Log.i(TAG,
				"Cleared information on the data storage for Help when Outside");

	}

	/**
	 * Store the safe or home area on disk in the
	 * HOME_DIR/persona/helpwhenoutside/{safe,home}area
	 * 
	 * @param areaPoints
	 *            A list of points as a CSV list of latitude, longitude
	 * 
	 */
	public boolean setArea(String areaPoints, String which) {

		if (areaPoints == null || which == null)
			return false;
		if ("safeArea".equalsIgnoreCase(which))
			return writeData(areaPoints, safeAreaFileName, false);
		else
			return writeData(areaPoints, homeAreaFileName, false);
	}

	/**
	 * Return a Vector of Point representing an area polygon
	 * 
	 * @parem which A String representing which area must be retrieved. It can
	 *        be "safeArea" or "homeArea"
	 * @return
	 */
	public Vector getArea(String which) {

		String fileName;
		if (which == null || which.equals(""))
			return null;
		if (which.equals("safeArea"))
			fileName = safeAreaFileName;
		else
			fileName = homeAreaFileName;
		Vector areaPoint = null;

		FileInputStream fos;
		try {
			fos = new FileInputStream(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(fos));
			String line = br.readLine();
			// if the file is empty returns null
			if (line == null || "".equals(line))
				return null;
			StringTokenizer st = new StringTokenizer(line, ",");
			areaPoint = new Vector();

			while (st.hasMoreTokens()) {
				double lat = Double.parseDouble(st.nextToken());
				double lng = Double.parseDouble(st.nextToken());
				areaPoint.add(new Point(lat, lng, 0.0, CoordinateSystem.WGS84));

			}

		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		return areaPoint;
	}

	public boolean setHomePosition(String latlng) {

		if (latlng == null)
			return false;

		return writeData(latlng, homePositionFileName, false);

	}

	/**
	 * Get the position of user's house
	 * 
	 * @return An array with two values containing the latitude and the
	 *         longitude in the first two positions
	 */
	public double[] getHomePosition() {
		FileInputStream fis;
		double lat = 0.0, lng = 0.0;
		try {
			fis = new FileInputStream(homePositionFileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String line = br.readLine();
			if (line == null || "".equals(line))
				return null;
			StringTokenizer st = new StringTokenizer(line, " ");

			while (st.hasMoreTokens()) {
				lat = Double.parseDouble(st.nextToken());
				lng = Double.parseDouble(st.nextToken());
			}
		}

		catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		}

		return new double[] { lat, lng };
	}

	public boolean setHistoryInterval(String historyInterval) {

		return writeData(historyInterval, historyIntervalFileName, false);

	}

	public long getHistoryInterval() {

		FileInputStream fis;
		long intervalInSeconds = -1;
		try {
			fis = new FileInputStream(historyIntervalFileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String line = br.readLine();
			if (line == null || "".equals(line))
				return -1;
			// this line may throw a NumberFormatException
			intervalInSeconds = Long.parseLong(line);
		} catch (FileNotFoundException e) {
			return -1;
		} catch (IOException e) {
			return -1;
		} catch (NumberFormatException e) {
			return -1;
		}

		return intervalInSeconds * 1000;

	}

	public Vector getPOIList() {

		Vector poiList = new Vector();
		FileInputStream fis;
		try {
			fis = new FileInputStream(poiListFileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String line = "";

			while ((line = br.readLine()) != null) {

				StringTokenizer st = new StringTokenizer(line, ",");

				while (st.hasMoreTokens()) {
					double lat = Double.parseDouble(st.nextToken());
					double lng = Double.parseDouble(st.nextToken());
					String poiName;
					// Should not happen, if the user has not assigned a name
					// to this POI
					if (st.hasMoreTokens())
						poiName = st.nextToken();
					else
						poiName = "No Name";
					POI_UAAL poi = new POI_UAAL(poiName, new Point(lat, lng,
							0.0, CoordinateSystem.WGS84));
					poiList.add(poi);
				}
			}
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		} catch (NumberFormatException e) {
			// if the poi line has incorrect values for lat, lng
			// return null
			return null;
		}

		return poiList;

	}

	protected boolean writeData(String data, String fileName,
			boolean appendOnFile) {
		FileOutputStream fos;
		File file = new File(fileName);
		try {
			if (!file.exists())
				file.createNewFile();

			fos = new FileOutputStream(file, appendOnFile);
			if (appendOnFile)
				data = data + System.getProperty("line.separator");
			fos.write(data.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean setPOI(String poiData) {
		return writeData(poiData, poiListFileName, true);
	}

	/**
	 * Delete a POI from the list of POIs
	 * 
	 * @param poiData
	 *            A string representing the POI in the form
	 *            "latitude,longitude,name"
	 * @return True if successful, false otherwise
	 */
	public boolean deletePOI(String poiData) {
		// parse the string
		if (poiData == null)
			return false;
		StringTokenizer st = new StringTokenizer(poiData, ",");
		String name;
		double lat, lng;
		if (st.hasMoreTokens()) {
			try {
				lat = Double.parseDouble(st.nextToken());
				lng = Double.parseDouble(st.nextToken());
				name = st.nextToken();
			} catch (NoSuchElementException e) {
				return false;
			} catch (NumberFormatException e) {
				return false;
			}
		} else
			return false;

		// at this point lat, lng, name are set and represent the POI
		// so a valid POI object can be created

		POI_UAAL deletedPOI = new POI_UAAL(name, new Point(lat, lng, 0.0,
				CoordinateSystem.WGS84));

		Vector poiList = getPOIList();
		if (poiList == null)
			return false;
		Iterator i = poiList.iterator();
		// overwrite the file contents
		File poiFile = new File(poiListFileName);
		// reset the file
		try {
			FileOutputStream fos = new FileOutputStream(poiFile);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		boolean retValue = true;
		// iterate over the poi list to search if the deleted one
		// is in the list
		while (i.hasNext()) {
			POI_UAAL poi = (POI_UAAL) i.next();
			// rewrite all the poi except the deleted one
			if (!poi.equals(deletedPOI)) {
				// write the other to the file
				double[] coord = poi.point.get2DCoordinates();
				String data = coord[0] + "," + coord[1] + "," + poi.name;
				retValue = retValue & writeData(data, poiListFileName, true);
			}
		}
		return retValue;
	}

	public String getMapKey() {

		FileInputStream fis;
		String mapKey = null;
		try {
			fis = new FileInputStream(mapKeyFileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			mapKey = br.readLine();
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		}

		return mapKey;
	}

	public boolean setMapKey(String mapKey) {

		return writeData(mapKey, mapKeyFileName, false);

	}
}
