/**
 * ITemperatureSensorService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.oasisUtils.environment;

public interface ITemperatureSensorService extends java.rmi.Remote {
    public long getTemperatureAge(int index) throws java.rmi.RemoteException, na.oasisUtils.environment.DomoticException;
    public int getTemperatureSensorCount() throws java.rmi.RemoteException;
    public double getTemperature(int index) throws java.rmi.RemoteException, na.oasisUtils.environment.DomoticException;
}
