/*****************************************************************************************
 * Copyright 2012 CERTH, http://www.certh.gr - Center for Research and Technology Hellas
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *****************************************************************************************/

package org.universAAL.AALapplication.safety_home.service.doorBellSoapClient;

import java.net.*;
import java.io.*;

import javax.xml.namespace.QName;

import org.universAAL.ri.wsdlToolkit.axis2Parser.Axis2ParserWrapper;
import org.universAAL.ri.wsdlToolkit.invocation.Axis2WebServiceInvoker;
import org.universAAL.ri.wsdlToolkit.invocation.InvocationResult;
import org.universAAL.ri.wsdlToolkit.ioApi.NativeObject;
import org.universAAL.ri.wsdlToolkit.ioApi.ParsedWSDLDefinition;
import org.universAAL.ri.wsdlToolkit.ioApi.WSOperation;
import org.universAAL.ri.wsdlToolkit.parser.WSDLParser;

/**
 * @author dimokas
 * 
 */

public class SOAPClient {

	public final static String SERVER_DOORBELL_SERVICE = "http://160.40.60.234:11223/0-FMS/consumerSwitchMonitor?wsdl";
	public final static String SOAP_ACTION = "";

	  public static boolean isDoorBellEnabled(){
		  try {
			  ParsedWSDLDefinition definition = new ParsedWSDLDefinition();
			  definition = WSDLParser.parseWSDLwithAxis(SERVER_DOORBELL_SERVICE, true,true);
			  InvocationResult invocationResult = null;
			  WSOperation operation = null;
			  if (definition != null) {
				  for (int i = 0; i < definition.getWsdlOperations().size(); i++) {
					  if (((WSOperation) definition.getWsdlOperations().get(i)).getOperationName().equals("isConsumerEnabled")) {
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
			  
			  System.out.println("IS DOOR BELL ENABLED:"+Boolean.parseBoolean(((NativeObject) operation.getHasOutput().getHasNativeOrComplexObjects().get(0)).getHasValue()));
			  return Boolean.parseBoolean(((NativeObject) operation.getHasOutput().getHasNativeOrComplexObjects().get(0)).getHasValue());		  
		  }
		  catch (Exception e) {
			  //System.err.println(e);
			  //e.printStackTrace();
			  return false;
		  }	  
	  }
  
	  public static void main(String[] args) {
		  isDoorBellEnabled();
	  }
}