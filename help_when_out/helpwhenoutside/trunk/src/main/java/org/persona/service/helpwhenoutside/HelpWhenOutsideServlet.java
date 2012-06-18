package org.persona.service.helpwhenoutside;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.persona.platform.servicegateway.GatewayPort;
import org.persona.service.helpwhenoutside.common.DataStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.ontology.location.position.CoordinateSystem;
import org.universAAL.ontology.location.position.Point;
import org.universAAL.ontology.profile.AssistedPerson;

/**
 * This is the servlet used for serving information to the web browser pointing
 * to the Help When Outside service URL It retrieves the information using
 * {@link HelpWhenOutsideXMLDataService}
 * 
 * @author Mario Latronico
 */
public class HelpWhenOutsideServlet extends org.universAAL.ri.servicegateway.GatewayPort { //Gateway port = módulo de UniversAAL

	/**
	 * Auto generated Serial Version ID (by Eclipse)
	 */
	private static final long serialVersionUID = -8706580244247319429L;

	private static final Logger log = LoggerFactory
			.getLogger(HelpWhenOutsideServlet.class);

	private boolean debug;
	
	private String myUrl;
	
	private ContextPublisher cp;

	/**
	 * This component retrieve data from the middleware and returns it as XML
	 * strings
	 */
	private HelpWhenOutsideDataService dataService;
	private int pktNumber;
	// The Context Publihser sends a Context Event with the outside
	// location
	//private OutdoorLocationContextPublisher outdoorPublisher;


	
	//public HelpWhenOutsideServlet(HelpWhenOutsideDataService dataService,
		//	Properties config, OutdoorLocationContextPublisher outdoorPublisher) {
	public HelpWhenOutsideServlet(HelpWhenOutsideDataService dataService,
			Properties config) {
		ContextProvider cpinfo = new ContextProvider("Servlet"
				+ "TestMassContextProvider");
			cpinfo.setType(ContextProviderType.gauge);
			cpinfo.setProvidedEvents(new ContextEventPattern[] { new ContextEventPattern() });
			
		myUrl = "/whereis";
		pktNumber = 0;
		this.dataService = dataService;
		

		if ("true".equals(config.getProperty("debug")))
			debug = true;
		else
			debug = false;

		//this.outdoorPublisher = outdoorPublisher;
	}

	public String url() {
		return myUrl;
	}

	
	

	/*
	 * Answer to the request with an XML with all data or only updated position
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		/*if (handleAuthorization(req, resp) == false)
			return;
		*/
		PrintWriter out = resp.getWriter();
		resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");

		/*
		 * The serviceData parameter in the JavaScript file specifies how many
		 * data to send in response to XMLHttpRequest (possibily wrapped by
		 * Prototype or similar)
		 * 
		 * serviceData=all -> send all data which describe the service -
		 * serviceData=updateGPSPos -> send only GPS position -
		 * serviceData=getHistory -> send the location history
		 */
		String dataParameter = req.getParameter("serviceData");
		if (dataParameter != null && dataParameter.equals("all")) {
			resp.setContentType("text/xml");
			String xmlString = dataService.getAllData();
			out.println(xmlString);

		}

		else if (dataParameter != null && dataParameter.contains("updatePos")) {
			resp.setContentType("text/xml");
			String xmlString = dataService.getUpdatedPosData();
			out.println(xmlString);
		} else if (dataParameter != null
				&& dataParameter.contains("getHistory")) {

			String fromTimeString = req.getParameter("fromTime");
			// default to half an hour in milliseconds 1800 * 1000;
			long fromTime = 1800000;
			if (!(fromTimeString == null || fromTimeString.equals("")))
				fromTime = Long.parseLong(fromTimeString);

			resp.setContentType("text/xml");
			String xmlString = dataService.getHistoryData(fromTime);
			out.print(xmlString);
		}
		// load the whereis.html file, including the JavaScript resources
		else {

			InputStream whereisIs = this.getClass().getClassLoader()
					.getResourceAsStream("helpwhenoutside/whereis.html");

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					whereisIs));
			StringBuilder html = new StringBuilder();
			String line;
			// Map API Key for the server
			String mapKey = DataStorage.getInstance().getMapKey();
			log.info("Google Map API Key: " + mapKey);
			while ((line = reader.readLine()) != null) {
				// Fill the Google API key
				// Check for map api URL
				if (line.contains("http://maps.google.com/maps")) {
					// Add the API key
					if (mapKey != null)
						line = line.replace("key=", "key=" + mapKey);
				}
				html.append(line);
				html.append(System.getProperty("line.separator"));
			}
			
			resp.setContentType("text/html");
			out.println(html.toString());

		} // main if else
	} // doGet

	private void dump(String gpsLine) {
		try {
			gpsLine += System.getProperty("line.separator");
			Calendar cal = Calendar.getInstance();
			String whenDump = cal.get(Calendar.YEAR) + "_"
					+ (cal.get(Calendar.MONTH) + 1) + "_"
					+ cal.get(Calendar.DAY_OF_MONTH);
			String fileName = System.getProperty("java.io.tmpdir")
					+ "/dumpGPS_" + whenDump + ".txt";
			FileOutputStream file = new FileOutputStream(fileName, true);
			file.write(gpsLine.getBytes());
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AssistedPerson ap = new AssistedPerson("Servlet AP");
		String latitude = request.getParameter("latitude");
		String longitude = request.getParameter("longitude");
		String courseMadeGood=request.getParameter("courseMadeGood");
		String stop = request.getParameter("stop");
		// if there is a stop parameter inform the others peer
		// using dummy values
		if ("true".equals(stop)) {
			// send two dummy values which indicate an indoor location
			ap.setLocation(new Point(200.0,200.0,CoordinateSystem.WGS84));
			cp.publish(new ContextEvent(ap,ap.PROP_PHYSICAL_LOCATION));
			//outdoorPublisher.publishLocation(new Double(200), new Double(200));
			log.debug("Stop from PDA");
			return;
		}
		Double latitudeNum = null;
		Double longitudeNum = null;
		Double courseMadeGoodNum=null;
		// if latitude or longitude are not Double, throw an exception
		// with the code BAD_REQUEST = 400
		try {
			latitudeNum = new Double(latitude);
			longitudeNum = new Double(longitude);
			if (courseMadeGood != null)
				courseMadeGoodNum = new Double(courseMadeGood);
		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			throw new ServletException();
		}
		log.debug("Received Lat: " + latitude + ":  Long: " + longitude);

		dataService.setLatitude(latitudeNum);
		dataService.setLongitude(longitudeNum);

		dataService.setHistoryEntry(new Date().getTime());

		//outdoorPublisher.publishLocation(latitudeNum, longitudeNum, courseMadeGoodNum);
		ap.setLocation(new Point(latitudeNum,longitudeNum,CoordinateSystem.WGS84));
		cp.publish(new ContextEvent(ap,ap.PROP_PHYSICAL_LOCATION));
		if (debug) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			String dateString = dateFormat.format(date);
			String dumpString = dateString + "  Pkt # " + pktNumber + " Lat: "
					+ latitudeNum + " Lng: " + longitudeNum;
			System.out.println(dumpString);
			dump(dumpString);
		}
		pktNumber++;
	}
	
	public String dataDir() {

		return "/helpwhenoutside";
	}
}
