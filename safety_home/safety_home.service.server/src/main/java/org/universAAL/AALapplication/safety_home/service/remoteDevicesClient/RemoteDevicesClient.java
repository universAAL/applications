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

package org.universAAL.AALapplication.safety_home.service.remoteDevicesClient;

import java.net.*;
import java.io.*;

import javax.xml.namespace.QName;

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

public class RemoteDevicesClient {

  public final static String SERVER_LAMP_SERVICE = "http://160.40.60.234:11223/0-ST/switchable?wsdl";
  public final static String SERVER_HEATING_SERVICE = "http://160.40.60.234:11223/2-ST/switchable?wsdl";
  public final static String SOAP_ACTION = "";

  /************************
   *   LAMP Management    *
   ************************/
  
  public static void turnOnLamp(){
	  try {
		  ParsedWSDLDefinition definition = new ParsedWSDLDefinition();
		  definition = WSDLParser.parseWSDLwithAxis(SERVER_LAMP_SERVICE, true,true);
		  InvocationResult invocationResult = null;
		  WSOperation operation = null;
		  if (definition != null) {
			  for (int i = 0; i < definition.getWsdlOperations().size(); i++) {
				  if (((WSOperation) definition.getWsdlOperations().get(i)).getOperationName().equals("setEnabled")) {
					  {
						  operation = ((WSOperation) definition.getWsdlOperations().get(i));
						  break;
					  }
				  }
			  }
			  // fill in input values
			  (((NativeObject) operation.getHasInput().getHasNativeOrComplexObjects().get(0))).setHasValue("0");
			  (((NativeObject) operation.getHasInput().getHasNativeOrComplexObjects().get(1))).setHasValue("1");
			  invocationResult = Axis2WebServiceInvoker.invokeWebService(operation, definition);
		  }
	  }
	  catch (Exception e) {
		  //System.err.println(e);
	  }	  
  }

  public static void turnOffLamp(){
	  try {
		  ParsedWSDLDefinition definition = new ParsedWSDLDefinition();
		  definition = WSDLParser.parseWSDLwithAxis(SERVER_LAMP_SERVICE, true,true);
		  InvocationResult invocationResult = null;
		  WSOperation operation = null;
		  if (definition != null) {
			  for (int i = 0; i < definition.getWsdlOperations().size(); i++) {
				  if (((WSOperation) definition.getWsdlOperations().get(i)).getOperationName().equals("setEnabled")) {
					  {
						  operation = ((WSOperation) definition.getWsdlOperations().get(i));
						  break;
					  }
				  }
			  }
			  // fill in input values
			  (((NativeObject) operation.getHasInput().getHasNativeOrComplexObjects().get(0))).setHasValue("0");
			  (((NativeObject) operation.getHasInput().getHasNativeOrComplexObjects().get(1))).setHasValue("0");
			  invocationResult = Axis2WebServiceInvoker.invokeWebService(operation, definition);
		  }
	  }
	  catch (Exception e) {
		  //System.err.println(e);
	  }	  
  }

  public static boolean isLampEnabled(){
	  try {
		  ParsedWSDLDefinition definition = new ParsedWSDLDefinition();
		  definition = WSDLParser.parseWSDLwithAxis(SERVER_LAMP_SERVICE, true,true);
		  InvocationResult invocationResult = null;
		  WSOperation operation = null;
		  if (definition != null) {
			  for (int i = 0; i < definition.getWsdlOperations().size(); i++) {
				  if (((WSOperation) definition.getWsdlOperations().get(i)).getOperationName().equals("isEnabled")) {
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
		  
		  System.out.println("ISENABLED:"+Boolean.parseBoolean(((NativeObject) operation.getHasOutput().getHasNativeOrComplexObjects().get(0)).getHasValue()));
		  return Boolean.parseBoolean(((NativeObject) operation.getHasOutput().getHasNativeOrComplexObjects().get(0)).getHasValue());		  
	  }
	  catch (Exception e) {
		  System.err.println(e);
		  return false;
	  }	  
  }
  
  /*****************************
   *   Fan Heater Management   *
   *****************************/

  public static void turnOnHeating(){
	  try {
		  ParsedWSDLDefinition definition = new ParsedWSDLDefinition();
		  definition = WSDLParser.parseWSDLwithAxis(SERVER_HEATING_SERVICE, true,true);
		  InvocationResult invocationResult = null;
		  WSOperation operation = null;
		  if (definition != null) {
			  for (int i = 0; i < definition.getWsdlOperations().size(); i++) {
				  if (((WSOperation) definition.getWsdlOperations().get(i)).getOperationName().equals("setEnabled")) {
					  {
						  operation = ((WSOperation) definition.getWsdlOperations().get(i));
						  break;
					  }
				  }
			  }
			  // fill in input values
			  (((NativeObject) operation.getHasInput().getHasNativeOrComplexObjects().get(0))).setHasValue("0");
			  (((NativeObject) operation.getHasInput().getHasNativeOrComplexObjects().get(1))).setHasValue("1");
			  invocationResult = Axis2WebServiceInvoker.invokeWebService(operation, definition);
		  }
	  }
	  catch (Exception e) {
		  //System.err.println(e);
	  }	  
  }
  
  public static void turnOffHeating(){
	  try {
		  ParsedWSDLDefinition definition = new ParsedWSDLDefinition();
		  definition = WSDLParser.parseWSDLwithAxis(SERVER_HEATING_SERVICE, true,true);
		  InvocationResult invocationResult = null;
		  WSOperation operation = null;
		  if (definition != null) {
			  for (int i = 0; i < definition.getWsdlOperations().size(); i++) {
				  if (((WSOperation) definition.getWsdlOperations().get(i)).getOperationName().equals("setEnabled")) {
					  {
						  operation = ((WSOperation) definition.getWsdlOperations().get(i));
						  break;
					  }
				  }
			  }
			  // fill in input values
			  (((NativeObject) operation.getHasInput().getHasNativeOrComplexObjects().get(0))).setHasValue("0");
			  (((NativeObject) operation.getHasInput().getHasNativeOrComplexObjects().get(1))).setHasValue("0");
			  invocationResult = Axis2WebServiceInvoker.invokeWebService(operation, definition);
		  }
	  }
	  catch (Exception e) {
		  //System.err.println(e);
	  }	  
  }

  public static boolean isHeatingEnabled(){
	  try {
		  ParsedWSDLDefinition definition = new ParsedWSDLDefinition();
		  definition = WSDLParser.parseWSDLwithAxis(SERVER_HEATING_SERVICE, true,true);
		  InvocationResult invocationResult = null;
		  WSOperation operation = null;
		  if (definition != null) {
			  for (int i = 0; i < definition.getWsdlOperations().size(); i++) {
				  if (((WSOperation) definition.getWsdlOperations().get(i)).getOperationName().equals("isEnabled")) {
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
		  
		  System.out.println("ISENABLED:"+Boolean.parseBoolean(((NativeObject) operation.getHasOutput().getHasNativeOrComplexObjects().get(0)).getHasValue()));
		  return Boolean.parseBoolean(((NativeObject) operation.getHasOutput().getHasNativeOrComplexObjects().get(0)).getHasValue());		  
	  }
	  catch (Exception e) {
		  System.err.println(e);
		  return false;
	  }	  
  }
  
  public static void main(String[] args) {
	  // Lamp service operations
	  turnOnLamp();
	  isLampEnabled();
	  turnOffLamp();
	  isLampEnabled();
	  
	  // Heating service operations
	  turnOnHeating();
	  isHeatingEnabled();
	  turnOffHeating();
	  isHeatingEnabled();
	  
	  
  }
}