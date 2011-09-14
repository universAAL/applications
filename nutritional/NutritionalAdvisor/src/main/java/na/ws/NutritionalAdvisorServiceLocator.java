/**
 * NutritionalAdvisorServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.ws;

public class NutritionalAdvisorServiceLocator extends org.apache.axis.client.Service implements na.ws.NutritionalAdvisorService {

    public NutritionalAdvisorServiceLocator() {
    }


    public NutritionalAdvisorServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public NutritionalAdvisorServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for NutritionalAdvisorPort
    private java.lang.String NutritionalAdvisorPort_address = "http://localhost:8080/NutriAdvisor_uAAL/NutritionalAdvisor";

    public java.lang.String getNutritionalAdvisorPortAddress() {
        return NutritionalAdvisorPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String NutritionalAdvisorPortWSDDServiceName = "NutritionalAdvisorPort";

    public java.lang.String getNutritionalAdvisorPortWSDDServiceName() {
        return NutritionalAdvisorPortWSDDServiceName;
    }

    public void setNutritionalAdvisorPortWSDDServiceName(java.lang.String name) {
        NutritionalAdvisorPortWSDDServiceName = name;
    }

    public na.ws.NutritionalAdvisor getNutritionalAdvisorPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(NutritionalAdvisorPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getNutritionalAdvisorPort(endpoint);
    }

    public na.ws.NutritionalAdvisor getNutritionalAdvisorPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            na.ws.NutritionalAdvisorBindingStub _stub = new na.ws.NutritionalAdvisorBindingStub(portAddress, this);
            _stub.setPortName(getNutritionalAdvisorPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setNutritionalAdvisorPortEndpointAddress(java.lang.String address) {
        NutritionalAdvisorPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (na.ws.NutritionalAdvisor.class.isAssignableFrom(serviceEndpointInterface)) {
                na.ws.NutritionalAdvisorBindingStub _stub = new na.ws.NutritionalAdvisorBindingStub(new java.net.URL(NutritionalAdvisorPort_address), this);
                _stub.setPortName(getNutritionalAdvisorPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("NutritionalAdvisorPort".equals(inputPortName)) {
            return getNutritionalAdvisorPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ws.na/", "NutritionalAdvisorService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ws.na/", "NutritionalAdvisorPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("NutritionalAdvisorPort".equals(portName)) {
            setNutritionalAdvisorPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
