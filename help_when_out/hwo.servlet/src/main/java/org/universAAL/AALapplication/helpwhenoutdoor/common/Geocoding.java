package org.universAAL.AALapplication.helpwhenoutdoor.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Geocoding {

	public static String HTTP_RESPONSE_OK ="200";
	public static String GEOCODING_SERVICE_URL = "http://maps.google.com/maps/geo?";
	public static int STREET_LEVEL_ACCURACY = 6;	


	/**
	 * Perform physical addresses geocoding. It sends an http request to
	 * Google maps service
	 * @param a physical address string 
	 * @return a double array with the address latitude in the first element
	 * and the address longitude in the second one. This value is null when
	 * it is not possible to perform the geocoding algorithm (i.e. when the
	 * address is not found or when the geocoding service is not available).
	 */
	public static double[] geoCoding(String address)
	{
		if (address==null || address=="")
			return null;
		String formattedAddress=address.replaceAll(" ", "+");


		String [] parsedCSV= sendHTTPRequest(formattedAddress,false);
		if (parsedCSV==null || parsedCSV.length!=4)
			return null;
		double [] result = new double [] {Double.parseDouble(parsedCSV[2]),
				Double.parseDouble(parsedCSV[3])};	
		return result;
	}
	
	/**
	 * Get a physical address from input position (using Google reverse geocoding
	 * service)
	 * @param position a double array with a latitude in the first element and 
	 * a longitude in the second one
	 * @return a string that represents the physical address associated to the
	 * given position. This value is null when it is not possible to perform 
	 * the geocoding algorithm (i.e. when the address is not found or when the 
	 * reverse geocoding service is not available).
	 */
	public static String reverseGeoCoding(double[]position)
	{
		String [] parsedCSV= reverseGeoCodingCompleteData(position);
		if (parsedCSV==null)
			return null;
		return parsedCSV[2];
	}
	
	private static String [] reverseGeoCodingCompleteData(double[]position)
	{
		if (position==null || position.length<2)
			return null;
		String formattedPosition=position[0] + ","+ position[1];
	
		String [] parsedCSV= sendHTTPRequest(formattedPosition, true);
		if (parsedCSV==null)
			return null;
		
		if (!parsedCSV[2].startsWith("\"") || !parsedCSV[2].endsWith("\""))
			return null;
		
		parsedCSV[2]=parsedCSV[2].replaceAll("\"", "");
		return parsedCSV;
	}
	
	
	public static String [] getStreetAndNumber(double [] position){
		String []parsedCSV = reverseGeoCodingCompleteData(position);
		if (parsedCSV==null)
			return null;

		String [] parsedAddress = parsedCSV[2].split(", ",3);
		if (parsedAddress==null || parsedAddress.length<2 ||
				parsedAddress[0].length()==0 || parsedAddress[1].length()==0)
			return null;

		int accuracy=getAccuracy(parsedCSV[1]);
		if (accuracy==STREET_LEVEL_ACCURACY)
			return new String [] {parsedAddress[0]};
		
		char numberChar;
		String street;
		int lastSpaceIndex=parsedAddress[0].lastIndexOf(" ");
		if (lastSpaceIndex!=-1 && lastSpaceIndex!=parsedAddress[0].length()-1)
		{
			numberChar = parsedAddress[0].charAt(lastSpaceIndex+1);
			if (numberChar>='0' && numberChar<='9')
			{
				street= parsedAddress[0].substring(0,lastSpaceIndex);
				String number=parsedAddress[0].substring(lastSpaceIndex+1);
				return new String []{street, number};
			}
		}
		numberChar= parsedAddress[1].charAt(0);
		
		if (numberChar>='0' && numberChar<='9')
		{
			street=parsedAddress[0];
			return new String []{street, parsedAddress[1]};
		}
		return new String [] {parsedAddress[0]};
	}

	private static String[]	sendHTTPRequest(String formattedRequest, boolean reverseGC)
	{
		//create the URL for the Google Maps service
		String serviceURL=GEOCODING_SERVICE_URL + "q=" + formattedRequest + 
		"&output=csv&sensor=false";
		if (true)
			return null;
		URL httpRequest;
		BufferedReader in;
		String csvData=null;
		try {
			httpRequest = new URL(serviceURL);
			in = new BufferedReader(new InputStreamReader(httpRequest.openStream()));
			if (in!=null)
				//the service response is in CSV format
				csvData = in.readLine();
			in.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		String [] parsedCSV;
		if (reverseGC)
		{
			//the service response contains 3 elements: httpResponseStatus, Accuracy, address
			parsedCSV = csvData.split(",",3);
			if (parsedCSV == null || parsedCSV.length!=3)
				return null;
		}
		else
		{
			//the service response contains 4 elements: httpResponseStatus, Accuracy, latitude
			// and longitude
			parsedCSV = csvData.split(",",4);
			if (parsedCSV == null || parsedCSV.length!=4)
				return null;
		}

		if (!parsedCSV[0].equals(HTTP_RESPONSE_OK))
			return null;
		
		int accuracy=getAccuracy(parsedCSV[1]);

		if (accuracy<STREET_LEVEL_ACCURACY)
			return null;

		return parsedCSV;

	}
	
	private static int getAccuracy (String sAccuracy){
		int accuracy = 0;

		try
		{
			accuracy=Integer.parseInt(sAccuracy);
		}
		catch(NumberFormatException e)
		{
			e.printStackTrace();
		}
		return accuracy;
	}
}
