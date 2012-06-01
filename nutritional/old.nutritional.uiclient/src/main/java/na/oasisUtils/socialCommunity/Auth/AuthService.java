/**
 * AuthService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.oasisUtils.socialCommunity.Auth;

public interface AuthService extends javax.xml.rpc.Service {
    public java.lang.String getAuthPortAddress();

    public na.oasisUtils.socialCommunity.Auth.AuthPort getAuthPort() throws javax.xml.rpc.ServiceException;

    public na.oasisUtils.socialCommunity.Auth.AuthPort getAuthPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
