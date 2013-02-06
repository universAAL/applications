package org.universAAL.AALapplication.safety_home.service.windowSoapClient;

import java.net.*;
import java.io.*;

import javax.xml.namespace.QName;

import org.universAAL.ri.wsdlToolkit.invocation.Axis2WebServiceInvoker;
import org.universAAL.ri.wsdlToolkit.invocation.InvocationResult;
import org.universAAL.ri.wsdlToolkit.ioApi.NativeObject;
import org.universAAL.ri.wsdlToolkit.ioApi.ParsedWSDLDefinition;
import org.universAAL.ri.wsdlToolkit.ioApi.WSOperation;
import org.universAAL.ri.wsdlToolkit.ioApi.WSOperationInput;
import org.universAAL.ri.wsdlToolkit.parser.WSDLParser;

public class SOAPClient {

  //public final static String DEFAULT_SERVER = "http://160.40.60.234:11223/0-TFK/contactSensor";
  public final static String DEFAULT_SERVER = "http://160.40.60.234:11223/0-TFK/contactSensor?WSDL";
  public final static String SOAP_ACTION = "";

  public static boolean isWindowClosed(){
	  boolean isOpen = true;
	  String server = DEFAULT_SERVER;
	  try {
		  ParsedWSDLDefinition definition = new ParsedWSDLDefinition();
		  definition = WSDLParser.parseWSDLwithAxis(DEFAULT_SERVER, true,true);
		  InvocationResult invocationResult = null;
		  WSOperation operation = null;
		  if (definition != null) {
			  // find which operation corresponds to the one with name "isContactClosed"
			  for (int i = 0; i < definition.getWsdlOperations().size(); i++) {
				  if (((WSOperation) definition.getWsdlOperations().get(i)).getOperationName().equals("isContactClosed")) {
					  {
						  operation = ((WSOperation) definition.getWsdlOperations().get(i));
						  break;
					  }
				  }
			  }
			  // fill in input values
			  (((NativeObject) operation.getHasInput().getHasNativeOrComplexObjects().get(0))).setHasValue("0");
			  invocationResult = Axis2WebServiceInvoker.invokeWebService(operation, definition);
		  }
		  if (((NativeObject) operation.getHasOutput().getHasNativeOrComplexObjects().get(0)).getHasValue().equals("true"))
				isOpen = true;
			else
				isOpen = false;

			return isOpen;
	  }
	  catch (Exception e) {
		  //System.err.println(e);
		  return true;
	  }	  
  }
  
  public static void main(String[] args) {
	  isWindowClosed();
  }
}