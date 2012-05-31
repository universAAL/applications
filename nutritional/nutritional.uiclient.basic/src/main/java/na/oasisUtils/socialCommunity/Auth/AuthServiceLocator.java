/**
 * AuthServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.oasisUtils.socialCommunity.Auth;

public class AuthServiceLocator extends org.apache.axis.client.Service implements na.oasisUtils.socialCommunity.Auth.AuthService {

    public AuthServiceLocator() {
    }


    public AuthServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AuthServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for AuthPort
    private java.lang.String AuthPort_address = "http://www.innovalia.org/proyectos/oasis/soap/auth.php";

    public java.lang.String getAuthPortAddress() {
        return AuthPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String AuthPortWSDDServiceName = "AuthPort";

    public java.lang.String getAuthPortWSDDServiceName() {
        return AuthPortWSDDServiceName;
    }

    public void setAuthPortWSDDServiceName(java.lang.String name) {
        AuthPortWSDDServiceName = name;
    }

    public na.oasisUtils.socialCommunity.Auth.AuthPort getAuthPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(AuthPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAuthPort(endpoint);
    }

    public na.oasisUtils.socialCommunity.Auth.AuthPort getAuthPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            na.oasisUtils.socialCommunity.Auth.AuthBindingStub _stub = new na.oasisUtils.socialCommunity.Auth.AuthBindingStub(portAddress, this);
            _stub.setPortName(getAuthPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setAuthPortEndpointAddress(java.lang.String address) {
        AuthPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (na.oasisUtils.socialCommunity.Auth.AuthPort.class.isAssignableFrom(serviceEndpointInterface)) {
                na.oasisUtils.socialCommunity.Auth.AuthBindingStub _stub = new na.oasisUtils.socialCommunity.Auth.AuthBindingStub(new java.net.URL(AuthPort_address), this);
                _stub.setPortName(getAuthPortWSDDServiceName());
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
        if ("AuthPort".equals(inputPortName)) {
            return getAuthPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn:Auth", "AuthService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn:Auth", "AuthPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("AuthPort".equals(portName)) {
            setAuthPortEndpointAddress(address);
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
