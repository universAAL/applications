package org.persona.service.helpwhenoutside.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.persona.service.helpwhenoutside.SMSGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SMSGatewayImpl extends org.universAAL.ri.servicegateway.GatewayPort implements SMSGateway {
	
	private String host;
	private static final Logger log = LoggerFactory.getLogger(SMSGatewayImpl.class);
	private static final long serialVersionUID = 1L;
	/**
	 * Creates an SMSGateway object. If the host is null or empty, it defaults to 10.128.208.17:8080/MAM 
	 * @param host The host where the sms server is running
	 */
	public SMSGatewayImpl(String host)
	{
		if (host == null || host.equals(""))
			this.host = "10.128.208.17:8080/MAM";
		else
			this.host = host;
	
	}
	/**
	 * Request a sms from the server. The Request must supply two parameters:
	 * - msisdn = the phone number with the country code (eg 39 for Italy)
	 * - message = the message to be delivered
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException 
	{
		PrintWriter out = resp.getWriter();
		resp.setHeader("Cache-Control", "no-cache");
		System.out.println("INFO: Char Encoding: " + req.getCharacterEncoding());
		
		String number = req.getParameter("msisdn");
		String message = req.getParameter("message");
		
		if (number.equals("") || message.equals(""))
			out.println("false");
		else
		{
			System.out.println("INFO: MSISDN: " + number);
			System.out.println("INFO: Message: " + message);
			boolean rv = sendSMS(number, message);
			out.println(rv);
		}
	}
	public boolean sendSMS(String number, String message) throws IllegalArgumentException{
		
		// the telephone number must have at least nine digits
		if (number == null || ! number.matches("\\d{9,}"))
			throw new IllegalArgumentException("Number is not correct");
		
		HttpURLConnection httpConnection = null;
		
		try {
			// Connect to the MAM gateway
			URL url = new URL(
							//"http://10.128.208.17:8080/MAM/send?msisdn="
							"http://" + host + "/send?msisdn=" 
							+ number + "&message=" + URLEncoder.encode(message,"UTF-8")
							);
			
			httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			httpConnection.setRequestMethod("GET");
			httpConnection.connect();
			if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				// read the html file from the server
				BufferedReader rd = new BufferedReader(new InputStreamReader(
						httpConnection.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = rd.readLine()) != null) {
					sb.append(line + '\n');
				}
				
				
				
				// Search for "Result: true" string, if found the SMS has been 
				// successfully delivered, else return false
				if (sb.toString().indexOf("Result: true") != -1)
				{
					log.info("Sent sms to " + number);
					return true;
				}
				else 
					return false;

			} else
				return false;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// close the connection and set all objects to null
			if (httpConnection != null)
				httpConnection.disconnect();
		}
		return false;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	public Properties getProperties() {
		return new Properties();
	}
	public String url() {

		return "/send";
	}
	public String dataDir() {
		return null;
	}

}
