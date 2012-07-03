package org.universAAL.AALapplication.safety_home.service.windowSoapClient;
import java.net.*;
import java.io.*;


public class SOAPClient {

  public final static String DEFAULT_SERVER 
   = "http://160.40.60.234:11223/0-TFK/contactSensor";
  public final static String SOAP_ACTION 
   = "";

  public static boolean isWindowClosed(){
	  boolean isOpen = true;
	  String server = DEFAULT_SERVER;
	  try {
		  URL u = new URL(server);
	      URLConnection uc = u.openConnection();
	      HttpURLConnection connection = (HttpURLConnection) uc;
	      
	      connection.setDoOutput(true);
	      connection.setDoInput(true);
	      connection.setRequestMethod("POST");
	      connection.setRequestProperty("SOAPAction", SOAP_ACTION);
	      
	      OutputStream out = connection.getOutputStream();
	      Writer wout = new OutputStreamWriter(out);
	      
	      wout.write("<soapenv:Envelope xmlns:soapenv=");
	      wout.write("'http://schemas.xmlsoap.org/soap/envelope/' "); 
	      wout.write("xmlns:web=");
	      wout.write("'http://www.domologic.com/webservices'>\r\n");  
	      wout.write("<soapenv:Header/>\r\n");
	      wout.write("<soapenv:Body>\r\n");
	      wout.write("<web:isContactClosed>\r\n");
	      wout.write("<index>0</index>\r\n");
	      wout.write("</web:isContactClosed>\r\n");
	      wout.write("</soapenv:Body>\r\n");
	      wout.write("</soapenv:Envelope>\r\n");
	      wout.flush();
	      wout.close();
	      
	      BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	      String input;
		  String res = "";
	      while ((input = br.readLine())!=null){
	    	  if (input.indexOf("<return>")!=-1){
	    		  res = input.substring(input.indexOf("<return>")+8,input.indexOf("</return>"));
	    		  //System.out.println(res+"\n");
	    	  }
	    	  System.out.println(input);
	      }
	      if (res.equals("true"))
	    	  isOpen = true;
	      else
	    	  isOpen = false;

	      return isOpen;
	  }
	  catch (IOException e) {
		  //System.err.println(e);
		  return true;
	  }	  
  }
  
  public static void main(String[] args) {
	  isWindowClosed();
  }
}