/**
 * AgendaServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.oasisUtils.socialCommunity.Agenda;

public class AgendaServiceLocator extends org.apache.axis.client.Service implements na.oasisUtils.socialCommunity.Agenda.AgendaService {

    public AgendaServiceLocator() {
    }


    public AgendaServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AgendaServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for AgendaPort
    private java.lang.String AgendaPort_address = "http://www.innovalia.org/proyectos/oasis/soap/agenda.php";

    public java.lang.String getAgendaPortAddress() {
        return AgendaPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String AgendaPortWSDDServiceName = "AgendaPort";

    public java.lang.String getAgendaPortWSDDServiceName() {
        return AgendaPortWSDDServiceName;
    }

    public void setAgendaPortWSDDServiceName(java.lang.String name) {
        AgendaPortWSDDServiceName = name;
    }

    public na.oasisUtils.socialCommunity.Agenda.AgendaPort getAgendaPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(AgendaPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAgendaPort(endpoint);
    }

    public na.oasisUtils.socialCommunity.Agenda.AgendaPort getAgendaPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            na.oasisUtils.socialCommunity.Agenda.AgendaBindingStub _stub = new na.oasisUtils.socialCommunity.Agenda.AgendaBindingStub(portAddress, this);
            _stub.setPortName(getAgendaPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setAgendaPortEndpointAddress(java.lang.String address) {
        AgendaPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (na.oasisUtils.socialCommunity.Agenda.AgendaPort.class.isAssignableFrom(serviceEndpointInterface)) {
                na.oasisUtils.socialCommunity.Agenda.AgendaBindingStub _stub = new na.oasisUtils.socialCommunity.Agenda.AgendaBindingStub(new java.net.URL(AgendaPort_address), this);
                _stub.setPortName(getAgendaPortWSDDServiceName());
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
        if ("AgendaPort".equals(inputPortName)) {
            return getAgendaPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn:Agenda", "AgendaService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn:Agenda", "AgendaPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("AgendaPort".equals(portName)) {
            setAgendaPortEndpointAddress(address);
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
