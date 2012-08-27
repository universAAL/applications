package org.universAAL.AALapplication.helpwhenoutdoor;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Vector;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.universAAL.AALapplication.helpwhenoutdoor.common.AddressLocation;
import org.universAAL.AALapplication.helpwhenoutdoor.common.Agenda;
import org.universAAL.AALapplication.helpwhenoutdoor.common.AgendaEvent;
import org.universAAL.AALapplication.helpwhenoutdoor.common.DataStorage;
import org.universAAL.AALapplication.helpwhenoutdoor.stubs.POI;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.ontology.location.position.Point;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * HelpWhenOutdoorDataServices interfaces with the universAAL middleware in order
 * to get the data from GPS and Profiling modules
 * 
 * @author Mario Latronico
 * 
 */
public class HelpWhenOutdoorXMLDataService implements
		HelpWhenOutdoorDataService {
	private static final Logger log = LoggerFactory.getLogger(HelpWhenOutdoorXMLDataService.class);
	private Double latitude;
	private Double longitude;

	private boolean updatedLat; // true if the latitude is updated
	private boolean updatedLng; // true if the longitude is updated

	private DataStorage dataStorage;

	// Used to get the current appointment and the next one
	private Agenda agenda;

	/**
	 * 
	 * This method get the latitude
	 * 
	 * @return Double
	 * 		Return the latitude
	 */
	public synchronized Double getLatitude() {
		return latitude;
	}

	/**
	 * This method set the latitude. If it is the same is not updated
	 * 
	 * @param Double
	 * 		The latitude to set
	 */
	public synchronized void setLatitude(Double latitude) {
		// if it is the same don't update
		if (latitude.equals(this.latitude)) {
			updatedLat = false;
		} else {
			this.latitude = latitude;
			updatedLat = true;
			log.debug("Update latitude: " + latitude);
		}
	}

	/**
	 * This method get the longitude
	 * 
	 * @return Double
	 */
	public synchronized Double getLongitude() {
		return longitude;
	}
	

	/**
	 * This method set the longitude. If it is the same is not updated
	 * 
	 * @param Double
	 */
	public synchronized void setLongitude(Double longitude) {
		// if it is the same don't update
		if (longitude.equals(this.longitude)) {
			updatedLng = false;
		} else {
			this.longitude = longitude;
			updatedLng = true;
			log.debug("Update longitude: " + longitude);
		}
	}
	
	/**
	 * This method adds a new entry in the history
	 * 
	 * @param long
	 */
	public void setHistoryEntry(long time)
	{
		if (updatedLat && updatedLng)
		{
			AddressLocation al = new AddressLocation();
			al.latitude = latitude.doubleValue();
			al.longitude = longitude.doubleValue();
			al.time = time;
			dataStorage.setAddressLocation(al);
		}
			
	}
	
	/**
	 * Construct an XML data service, used to get data from the server in XML form
	 * 
	 * @param agenda The object used to query data from the Agenda service 
	 */
	public HelpWhenOutdoorXMLDataService(
			Agenda agenda) {

		dataStorage = DataStorage.getInstance();

		
		double[] homePosition = dataStorage.getHomePosition();
		if (homePosition != null)
		{
			setLatitude(new Double(homePosition[0]));
			setLongitude(new Double(homePosition[1]));
		}
		this.agenda = agenda;

	}

	/**
	 * Creates and initialize the XML document structure. This is the skeleton
	 * used to populate the XML with all information or only updated GPS
	 * coordinates
	 * 
	 * @return The root element of the document
	 */
	private Document createXMLDocumentStructure() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Creates the Document
		Document serviceDoc = builder.newDocument();
		/*
		 * Create the XML Tree
		 */
		Element root = serviceDoc.createElement("service");

		serviceDoc.appendChild(root);
		return serviceDoc;
	}

	/**
	 * Returns the Document as an XML string.
	 * 
	 * @param serviceDoc
	 *            XML Document structure
	 * @return A string representing the XML, null on exceptions
	 */
	public String write(Document serviceDoc) {
		try {
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			StringWriter sw = new StringWriter();
			StreamResult xmlResult = new StreamResult(sw);
			DOMSource source = new DOMSource(serviceDoc);
			transformer.transform(source, xmlResult);
			log.info("Writing XML document for Help When Outdoor");
			return sw.toString();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Return the GPS coordinate updated
	 * @return An XML document with only GPS position data, null if the position has not been updated
	 */
	public synchronized String getUpdatedPosData() {
		// don't send anything if the latitude AND longitude are false
		if (updatedLat == false && updatedLng == false)
			return "";
		Document serviceDoc = createXMLDocumentStructure();
		Element root = serviceDoc.getDocumentElement();	
		Element gpsElement = createGPSPosElement(serviceDoc);
		if (gpsElement != null)
			root.appendChild(gpsElement);
		// Set the dirty flag for position
		updatedLat = updatedLng = false;

		return write(serviceDoc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.universAAL.AALapplication.HelpWhenOutdoorDataService#getAllData()
	 */
	public String getAllData() {
		// Creates the basic structure
		Document serviceDoc = createXMLDocumentStructure();
		Element root = serviceDoc.getDocumentElement();

		// Add a <gpsPos> tag
		Element gpsElement = createGPSPosElement(serviceDoc); 
		Element safeAreaElement = createSafeAreaElement(serviceDoc);
		Element homePositionElement = createHomePositionElement(serviceDoc);
		Element poiList = createPOIListElement(serviceDoc);
		Element agendaElement = createAgendaElement(serviceDoc);
		Element homeAreaElement = createHomeAreaElement(serviceDoc);
		try {
			if (gpsElement != null)
				root.appendChild(gpsElement);
			else System.err.println("gspElement is null");
			if (poiList != null)
				root.appendChild(poiList);
			else System.err.println("poiList is null");
			if (agendaElement != null)
				root.appendChild(agendaElement);
			else System.err.println("agendaElement is null");
			if (safeAreaElement != null){
				root.appendChild(safeAreaElement);
				System.err.println("safe area element [Name] " + safeAreaElement.getNodeName() + " [Value] " + safeAreaElement.getNodeValue());
			}
			else System.err.println("safeAreaElement is null");
			if (homeAreaElement != null)
				root.appendChild(homeAreaElement);
			else System.err.println("homeAreaElement is null");
						if (homePositionElement != null)
				root.appendChild(homePositionElement);
			else System.err.println("homePositionElement is null");
			
		} catch (DOMException e) {
			// TODO: handle DOM errors
			e.printStackTrace();
		}
	System.err.println("\n\n\n\n serviceDoc "+serviceDoc.getNodeValue()+"\n\n\n\n");
		
		return write(serviceDoc);
	}
	
	/**
	 * This method creates a new point element at home area, getting the safe Area from the profiling and adding it to the document (XML file)
	 * 
	 * @param Document
	 * @return Element
	 */
	private Element createHomeAreaElement(Document serviceDoc) {
		Element homeAreaElement = serviceDoc.createElement("HomeArea");
		// Get the safe Area from the Profiling and add it to the XML
		Vector homeArea = dataStorage.getArea("homeArea");
		if (homeArea == null)
			return null;
		// Iterate over the safe area polygon

		for (Iterator i = homeArea.iterator(); i.hasNext();) {
			Point p = (Point) i.next();
			double lat = p.get2DCoordinates()[0];
			double lng = p.get2DCoordinates()[1];
			Element pointElement = serviceDoc.createElement("HomeAreaPoint");
			pointElement.setAttribute("latitude", new Double(lat).toString());
			pointElement.setAttribute("longitude", new Double(lng).toString());
			homeAreaElement.appendChild(pointElement);
			System.err.println("home are point " + pointElement.getNodeName() + " " + pointElement.getNodeValue());
		}

		return homeAreaElement;

	}

	/**
	 * Create the GPSPos element
	 * @param serviceDoc The document used to append the element
	 * @return The GPSPos XML element
	 */
	public Element createGPSPosElement(Document serviceDoc)
	{
		// Add the <gpsPos> tag
		if (latitude != null && longitude != null)
		{
		Element gpsElement = serviceDoc.createElement("gpsPos");
		gpsElement.setAttribute("latitude", latitude.toString());
		gpsElement.setAttribute("longitude", longitude.toString());
		return gpsElement;
		}
		return null;
	}
	
	/**
	 * This method creates a new home position element and return it
	 * @param Document
	 * @return Element Return a home position element
	 */
	public Element createHomePositionElement (Document serviceDoc)
	{
		Element homePositionElement = serviceDoc.createElement("HomePosition");
		double[] pos = getHomePosition();
		if (pos == null)
			return null;
		homePositionElement.setAttribute("latitude", new Double(pos[0]).toString());
		homePositionElement.setAttribute("longitude", new Double(pos[1]).toString());
		return homePositionElement;
	}
		
	/**
	 * This method returns a new agenda element
	 * 
	 * @param Document
	 * @return Element
	 */
	public Element createAgendaElement(Document serviceDoc) {
		// TODO: connect with the uAAL agenda service  
		//Agenda appointments
		AgendaEvent[] agendaEvent = agenda.getAgendaEvents();

		// TODO DUMMY CODE, change it
	/*	Properties config = new Properties();
		try {
			config.load(this.getClass().getClassLoader().getResourceAsStream(
					"config.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		XMLGregorianCalendar dateTime = TypeMapper.getCurrentDateTime();

//		if (agendaEvent[0] == null) {
//			agendaEvent[0] = new AgendaEvent();
//			String address1 = config.getProperty("agendaAddress1");
//			if (address1 == null)
//				agendaEvent[0].address = "Via Giovanni Treccani, 12";
//			else
//				agendaEvent[0].address = address1;
//
//			agendaEvent[0].beginTime = (XMLGregorianCalendar) dateTime.clone();
//			agendaEvent[0].beginTime.setHour(dateTime.getHour() - 1);
//			agendaEvent[0].beginTime.setMinute(30);
//
//			agendaEvent[0].endTime = (XMLGregorianCalendar) agendaEvent[0].beginTime
//					.clone();
//			agendaEvent[0].endTime.setHour(dateTime.getHour() + 1);
//			agendaEvent[0].endTime.setMinute(45);
//
//		}
//		if (agendaEvent[1] == null) {
//			agendaEvent[1] = new AgendaEvent();
//			String address2 = config.getProperty("agendaAddress2");
//			if (address2 == null)
//				agendaEvent[1].address = "Via Tibaldi, 234";
//			else
//				agendaEvent[1].address = address2;
//
//			agendaEvent[1].beginTime = (XMLGregorianCalendar) agendaEvent[0].endTime
//					.clone();
//			agendaEvent[1].beginTime
//					.setHour(agendaEvent[1].beginTime.getHour() + 1);
//			agendaEvent[1].beginTime.setMinute(25);
//
//			agendaEvent[1].endTime = (XMLGregorianCalendar) agendaEvent[1].beginTime
//					.clone();
//			agendaEvent[1].endTime
//					.setHour(agendaEvent[1].beginTime.getHour() + 1);
//			agendaEvent[1].endTime.setMinute(45);
//
//		}

		Element agendaElement = serviceDoc.createElement("Agenda");
		String eventAddress, eventBeginTime, eventEndTime;
		// if null or both values are null
		if (agendaEvent == null || agendaEvent.length == 0 || (agendaEvent[0] == null && agendaEvent[1] == null))
		{
			// create a "No events in agenda" value
			int currentMinutes = dateTime.getMinute(); 
			Element eventElement = serviceDoc.createElement("Event");
			eventElement.setAttribute("type", "Current");
			XMLGregorianCalendar begin = (XMLGregorianCalendar) dateTime.clone();
			// no events in the past 10 minutes
			int minutes = currentMinutes - 10;
			if (minutes < 0)
				minutes = 0;
			begin.setMinute(minutes);
			XMLGregorianCalendar end = (XMLGregorianCalendar) begin.clone();
			// for the next 10 minutes
			minutes = currentMinutes + 10;
			if (minutes >= 60)
				minutes = 59;
			end.setMinute(minutes);
			eventElement.setAttribute("address", "No events in agenda");
			eventElement.setAttribute("beginTime", begin.getHour() + ":" + begin.getMinute());
			eventElement.setAttribute("endTime", begin.getHour() + ":" + begin.getMinute());
			agendaElement.appendChild(eventElement);
		}
		
		for (int i = 0; i < agendaEvent.length; i++) {
			// the event can be null
			if (agendaEvent[i] != null) {
				eventAddress = agendaEvent[i].address;
				eventBeginTime = agendaEvent[i].beginTime.getHour() + ":"
						+ agendaEvent[i].beginTime.getMinute();
				eventEndTime = agendaEvent[i].endTime.getHour() + ":"
						+ agendaEvent[i].endTime.getMinute();
				Element eventElement = serviceDoc.createElement("Event");

				// The array contains the current element in position 0 and the
				// next
				// in position 1, so the current index is used to distinguish
				// them
				eventElement
						.setAttribute("type", (i == 0) ? "Current" : "Next");
				eventElement.setAttribute("address", eventAddress);
				eventElement.setAttribute("beginTime", eventBeginTime);
				eventElement.setAttribute("endTime", eventEndTime);
				agendaElement.appendChild(eventElement);
			}
		}
		return agendaElement;
	}

	/**
	 * This method create a safe area element
	 * 
	 * @param Document
	 * @return Element
	 */
	public Element createSafeAreaElement(Document serviceDoc) {

		Element safeAreaElement = serviceDoc.createElement("SafeArea");
		System.out.println("create safe area element ...voy a get area" );
		// Get the safe Area from the Profiling and add it to the XML
		Vector safeArea = dataStorage.getArea("safeArea");
		if (safeArea == null)
			return null;
		// Iterate over the safe area polygon

		for (Iterator i = safeArea.iterator(); i.hasNext();) {
			Point p = (Point) i.next();
			double lat = p.get2DCoordinates()[0];
			double lng = p.get2DCoordinates()[1];
			Element pointElement = serviceDoc.createElement("SafeAreaPoint");
			pointElement.setAttribute("latitude", new Double(lat).toString());
			pointElement.setAttribute("longitude", new Double(lng).toString());
			safeAreaElement.appendChild(pointElement);

		}

		return safeAreaElement;

	}

	/**
	 * Get the POI list, for example <PoiList> <Poi latitude="34.56"
	 * longitude="56.67" name="market" poiID="1234" /> <Poi latitude="35.67"
	 * longitude="78.12" name="church" poiID="4567" /> </PoiList>
	 * 
	 * @param serviceDoc
	 *            The main document where the list need to be attached
	 * @return an Element node with the XML for the POI list
	 */
	public Element createPOIListElement(Document serviceDoc) {
		Vector poi = dataStorage.getPOIList();
		if (poi == null)
			return null;
		Element poiList = null;
		if (poi.size() > 0) {
			poiList = serviceDoc.createElement("PoiList");

		}
		for (int i = 0; i < poi.size(); i++) {
			// store each poi as an XML element
			POI poiItem = (POI) poi.get(i);
			if (poiItem != null) {

				Element poiElement = serviceDoc.createElement("Poi");
				double[] poiCoord = poiItem.point.get2DCoordinates();
				poiElement.setAttribute("latitude", new Double(poiCoord[0])
						.toString());
				poiElement.setAttribute("longitude", new Double(poiCoord[1])
						.toString());
				poiElement.setAttribute("name", poiItem.name);
				//poiElement.setAttribute("pinID", poiItem.id);
				// add the element to the PoiList parent
				poiList.appendChild(poiElement);
			}
		}
		return poiList;
	}

	
	/** 
	 * This method get history data
	 * @param long
	 * @return String
	 * 
	 */
	public String getHistoryData(long fromTime) {
		
		Vector history = dataStorage.getHistory(fromTime);
		if (history== null || history.isEmpty())
			return null;
		
		Document serviceDoc = createXMLDocumentStructure();
		Element root = serviceDoc.getDocumentElement();

		// Add the <HistoryList> tag
		Element historyListElement = serviceDoc.createElement("HistoryList");
		Iterator i = history.iterator();
		while (i.hasNext())
		{
			AddressLocation al = (AddressLocation)i.next();
			Element historyElement = serviceDoc.createElement("History");
			historyElement.setAttribute("latitude",
					new Double(al.latitude).toString());
			historyElement.setAttribute("longitude", new Double(
					al.longitude).toString());
			historyElement.setAttribute("timestamp", new Long(
					al.time).toString());
			historyListElement.appendChild(historyElement);
		}
		root.appendChild(historyListElement);
		return write(serviceDoc);
	}

	/**
	 * This method set the area at right file
	 * 
	 * @param String It provides the safe area data
	 * @param String it provides the name of the file where to store the safe area data
	 */ 
	public void setArea(String safeAreaData, String which) throws NoSuchElementException,
			NumberFormatException {

		dataStorage.setArea(safeAreaData, which);
	}
	
	
	/**
	 * This method set the home position 
	 * @param String It provides the home position
	 */
	public void setHomePosition(String latlng) {
		
		dataStorage.setHomePosition(latlng);
		
	}
	
	/**
	 * This method returns the home position  
	 * @return double[]
	 */
	public double[] getHomePosition()
	{
		return dataStorage.getHomePosition();
	}
}
