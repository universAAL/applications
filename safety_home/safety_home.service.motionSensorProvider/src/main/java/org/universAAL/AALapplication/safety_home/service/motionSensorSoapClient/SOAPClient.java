package org.universAAL.AALapplication.safety_home.service.motionSensorSoapClient;

import java.net.*;
import java.io.*;

import javax.xml.namespace.QName;

import org.universAAL.ri.wsdlToolkit.invocation.Axis2WebServiceInvoker;
import org.universAAL.ri.wsdlToolkit.invocation.InvocationResult;
import org.universAAL.ri.wsdlToolkit.ioApi.NativeObject;
import org.universAAL.ri.wsdlToolkit.ioApi.ParsedWSDLDefinition;
import org.universAAL.ri.wsdlToolkit.ioApi.WSOperation;
import org.universAAL.ri.wsdlToolkit.parser.WSDLParser;

public class SOAPClient {

  //public final static String DEFAULT_SERVER = "http://160.40.60.234:11223/0-RBM/motionSensor";
	public final static String DEFAULT_SERVER = "http://160.40.60.234:11223/0-RBM/motionSensor?WSDL";
	public final static String SOAP_ACTION = "";

  public static double getMotionDetection(){
	  double temp = 0;
	  String server = DEFAULT_SERVER;
	  try {
		  	//usage of Internet Gateway module
			ParsedWSDLDefinition definition = new ParsedWSDLDefinition();
			definition = WSDLParser.parseWSDLwithAxis(DEFAULT_SERVER, true,true);
			InvocationResult invocationResult = null;
			WSOperation operation = null;
			if (definition != null) {
				// find which operation corresponds to the one with name "getMotionAge"
				for (int i = 0; i < definition.getWsdlOperations().size(); i++) {
					if (((WSOperation) definition.getWsdlOperations().get(i)).getOperationName().equals("getMotionAge")) {
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
			return Float.parseFloat(((NativeObject) operation.getHasOutput().getHasNativeOrComplexObjects().get(0)).getHasValue());
	  }
	  catch (Exception e) {
		  //System.err.println(e);
		  return 0.0;
	  }	  
  }
  
  public static void main(String[] args) {
	  getMotionDetection();
  }
}