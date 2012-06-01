/**
 * TemperatureSensorServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.oasisUtils.environment;

public class TemperatureSensorServiceServiceLocator extends org.apache.axis.client.Service implements na.oasisUtils.environment.TemperatureSensorServiceService {

    public TemperatureSensorServiceServiceLocator() {
    }


    public TemperatureSensorServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public TemperatureSensorServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for TemperatureSensorServicePort
    private java.lang.String TemperatureSensorServicePort_address = "http://160.40.60.234:11223/S300/temperatureSensor";

    public java.lang.String getTemperatureSensorServicePortAddress() {
        return TemperatureSensorServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String TemperatureSensorServicePortWSDDServiceName = "TemperatureSensorServicePort";

    public java.lang.String getTemperatureSensorServicePortWSDDServiceName() {
        return TemperatureSensorServicePortWSDDServiceName;
    }

    public void setTemperatureSensorServicePortWSDDServiceName(java.lang.String name) {
        TemperatureSensorServicePortWSDDServiceName = name;
    }

    public na.oasisUtils.environment.ITemperatureSensorService getTemperatureSensorServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(TemperatureSensorServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getTemperatureSensorServicePort(endpoint);
    }

    public na.oasisUtils.environment.ITemperatureSensorService getTemperatureSensorServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            na.oasisUtils.environment.TemperatureSensorServiceServiceSoapBindingStub _stub = new na.oasisUtils.environment.TemperatureSensorServiceServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getTemperatureSensorServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setTemperatureSensorServicePortEndpointAddress(java.lang.String address) {
        TemperatureSensorServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (na.oasisUtils.environment.ITemperatureSensorService.class.isAssignableFrom(serviceEndpointInterface)) {
                na.oasisUtils.environment.TemperatureSensorServiceServiceSoapBindingStub _stub = new na.oasisUtils.environment.TemperatureSensorServiceServiceSoapBindingStub(new java.net.URL(TemperatureSensorServicePort_address), this);
                _stub.setPortName(getTemperatureSensorServicePortWSDDServiceName());
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
        if ("TemperatureSensorServicePort".equals(inputPortName)) {
            return getTemperatureSensorServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.domologic.com/webservices", "TemperatureSensorServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.domologic.com/webservices", "TemperatureSensorServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("TemperatureSensorServicePort".equals(portName)) {
            setTemperatureSensorServicePortEndpointAddress(address);
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
