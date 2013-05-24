package org.universAAL.AALapplication.helpwhenoutdoor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.universAAL.AALapplication.helpwhenoutdoor.common.DataStorage;

public class MapEditor extends org.universAAL.ri.servicegateway.GatewayPort {

	private static final long serialVersionUID = -6405348413699069198L;
	Properties config;

	private HelpWhenOutdoorDataService dataService;
	
	private DataStorage dataStorage;

	public MapEditor(Properties config, HelpWhenOutdoorDataService dataService) {
		this.config = config;

		this.dataService = dataService;
		dataStorage = DataStorage.getInstance(); 
	
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
		System.err.println("DO GET ");
		/*if (handleAuthorization(req, resp) == false)
			return;
*/
		PrintWriter out = resp.getWriter();
		resp.setHeader("Cache-Control", "no-store, no-cache");
		/*
		 * The serviceData parameter in the JavaScript file specifies how many
		 * data to send in response to XMLHttpRequest (possibily wrapped by
		 * Prototype or similar)
		 * 
		 * - serviceData=all -> send all data which describe the service -
		 * serviceData=updatePos -> send only GPS position -
		 * serviceData=getHistory -> send the location history
		 */
		String dataParameter = req.getParameter("serviceData");
		
		if (dataParameter != null && dataParameter.equals("all")) {
			System.err.println("dataParameter is all...go to getAllData" );
			resp.setContentType("text/xml");
			String xmlString = dataService.getAllData();
			System.err.println("xmlString......................................... datos que devuelve \n" + xmlString);
			out.println(xmlString);
		} 
		else if (dataParameter != null && dataParameter.contains("updatePos")) {
			System.err.println("dataParameter is updatePos...go to getUpdatedPosData" );
			resp.setContentType("text/xml");
			String xmlString = dataService.getUpdatedPosData();
			out.println(xmlString);
		} else if (dataParameter != null
				&& dataParameter.contains("getHistory")) {
			System.err.println("dataParameter is getHistory...go to getHistoryData" );
			String fromTimeString = req.getParameter("fromTime");
			// default to one hour in milliseconds 3600 * 1000;
			long fromTime = 3600000;
			if (!(fromTimeString == null || fromTimeString.equals("")))
				fromTime = Long.parseLong(fromTimeString);

			resp.setContentType("text/xml");
			String xmlString = dataService.getHistoryData(fromTime);
			out.print(xmlString);
		}
		// load the whereis.html file, including the JavaScript resources
		else {
			System.err.println("dataParameter is mapEditor..." );
			InputStream htmlIS = this.getClass().getClassLoader()
					.getResourceAsStream("helpwhenoutdoor/mapeditor.html");

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					htmlIS));
			StringBuilder html = new StringBuilder();
			String line;
			// Map API Key for the server
			String mapKey = DataStorage.getInstance().getMapKey();
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

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Do POST !!!");
	}

	public void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.err.println("DO PUT ");
	
		BufferedReader br = new BufferedReader(new InputStreamReader(request
				.getInputStream()));
		String data = br.readLine();
		StringTokenizer st = new StringTokenizer(data, ":");
		String operation = "", operationData = "";
		while (st.hasMoreTokens()) {
			operation = st.nextToken();
			// CLEAR command has no more tokens
			if (st.hasMoreTokens())
				operationData = st.nextToken();
		}
		String remoteHost = request.getRemoteHost();
		String remoteUser = request.getRemoteUser();
		System.err.println("PUT operation data: " + operationData + " from " + remoteUser + " on " + remoteHost);
		
		try {
			if ("deletePOI".equals(operation))
				dataStorage.deletePOI(operationData);
			if ("addPOI".equals(operation))
				dataStorage.setPOI(operationData);

			Activator.paseador.changePosEvent(); // Just for testing. This will simulate the movement of the user.
			    
			if ("clearMap".equals(operation))
				dataStorage.clearData();
			if ("safeArea".equals(operation))
				dataService.setArea(operationData, "safeArea");
			if ("homeArea".equals(operation))
				dataService.setArea(operationData, "homeArea");		
			if ("homePosition".equals(operation))
				dataService.setHomePosition(operationData);
			if ("historyInterval".equals(operation))
				dataStorage.setHistoryInterval(operationData);
			if ("mapKey".equals(operation))
				dataStorage.setMapKey(operationData);

		} catch (NumberFormatException nfe) {
			// TODO: return an alert with the exception
		} catch (NoSuchElementException nse) {
			// TODO: return an alert with the exception
		}

		System.out.println(data);
	}

	public Properties getProperties() {

		return new Properties();
	}

	public String url() {

		return "/mapeditor";
	}

	public String dataDir() {

		return "/helpwhenoutdoor";
	}

}
