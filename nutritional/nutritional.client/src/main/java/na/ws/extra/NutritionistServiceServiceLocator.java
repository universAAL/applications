/**
 * NutritionistServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.ws.extra;

import na.utils.Setup;

public class NutritionistServiceServiceLocator extends
	org.apache.axis.client.Service implements NutritionistServiceService {

    public NutritionistServiceServiceLocator() {
    }

    public NutritionistServiceServiceLocator(
	    org.apache.axis.EngineConfiguration config) {
	super(config);
    }

    public NutritionistServiceServiceLocator(java.lang.String wsdlLoc,
	    javax.xml.namespace.QName sName)
	    throws javax.xml.rpc.ServiceException {
	super(wsdlLoc, sName);
    }

    // Use to get a proxy class for NutritionistServicePort
    private java.lang.String NutritionistServicePort_address = "http://158.42.166.200:8080/NutriAdvisor_uAAL/NutritionistService";

    public java.lang.String getNutritionistServicePortAddress() {
	return NutritionistServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String NutritionistServicePortWSDDServiceName = "NutritionistServicePort";

    public java.lang.String getNutritionistServicePortWSDDServiceName() {
	return NutritionistServicePortWSDDServiceName;
    }

    public void setNutritionistServicePortWSDDServiceName(java.lang.String name) {
	NutritionistServicePortWSDDServiceName = name;
    }

    public NutritionistService getNutritionistServicePort()
	    throws javax.xml.rpc.ServiceException {
	java.net.URL endpoint;
	try {
	    endpoint = new java.net.URL(NutritionistServicePort_address);
	} catch (java.net.MalformedURLException e) {
	    throw new javax.xml.rpc.ServiceException(e);
	}
	return getNutritionistServicePort(endpoint);
    }

    public NutritionistService getNutritionistServicePort(
	    java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
	try {
	    NutritionistServiceBindingStub _stub = new NutritionistServiceBindingStub(
		    portAddress, this);
	    _stub.setPortName(getNutritionistServicePortWSDDServiceName());
	    return _stub;
	} catch (org.apache.axis.AxisFault e) {
	    return null;
	}
    }

    public void setNutritionistServicePortEndpointAddress(
	    java.lang.String address) {
	NutritionistServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation. If this service has
     * no port for the given interface, then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface)
	    throws javax.xml.rpc.ServiceException {
	try {
	    if (NutritionistService.class
		    .isAssignableFrom(serviceEndpointInterface)) {
		NutritionistServiceBindingStub _stub = new NutritionistServiceBindingStub(
			new java.net.URL(NutritionistServicePort_address), this);
		_stub.setPortName(getNutritionistServicePortWSDDServiceName());
		return _stub;
	    }
	} catch (java.lang.Throwable t) {
	    throw new javax.xml.rpc.ServiceException(t);
	}
	throw new javax.xml.rpc.ServiceException(
		"There is no stub implementation for the interface:  "
			+ (serviceEndpointInterface == null ? "null"
				: serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation. If this service has
     * no port for the given interface, then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName,
	    Class serviceEndpointInterface)
	    throws javax.xml.rpc.ServiceException {
	if (portName == null) {
	    return getPort(serviceEndpointInterface);
	}
	java.lang.String inputPortName = portName.getLocalPart();
	if ("NutritionistServicePort".equals(inputPortName)) {
	    return getNutritionistServicePort();
	} else {
	    java.rmi.Remote _stub = getPort(serviceEndpointInterface);
	    ((org.apache.axis.client.Stub) _stub).setPortName(portName);
	    return _stub;
	}
    }

    public javax.xml.namespace.QName getServiceName() {
	return new javax.xml.namespace.QName("http://ws.na/",
		"NutritionistServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
	if (ports == null) {
	    ports = new java.util.HashSet();
	    ports.add(new javax.xml.namespace.QName("http://ws.na/",
		    "NutritionistServicePort"));
	}
	return ports.iterator();
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(java.lang.String portName,
	    java.lang.String address) throws javax.xml.rpc.ServiceException {

	if ("NutritionistServicePort".equals(portName)) {
	    setNutritionistServicePortEndpointAddress(address);
	} else { // Unknown Port Name
	    throw new javax.xml.rpc.ServiceException(
		    " Cannot set Endpoint Address for Unknown Port" + portName);
	}
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(javax.xml.namespace.QName portName,
	    java.lang.String address) throws javax.xml.rpc.ServiceException {
	setEndpointAddress(portName.getLocalPart(), address);
    }

}
