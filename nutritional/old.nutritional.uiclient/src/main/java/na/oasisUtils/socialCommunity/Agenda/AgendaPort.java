/**
 * AgendaPort.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.oasisUtils.socialCommunity.Agenda;

public interface AgendaPort extends java.rmi.Remote {
    public na.oasisUtils.socialCommunity.Agenda.GetMyEventsOutput getmyevents(na.oasisUtils.socialCommunity.Agenda.GetMyEventsInput parameters) throws java.rmi.RemoteException;
    public na.oasisUtils.socialCommunity.Agenda.StatusResponse addevent(na.oasisUtils.socialCommunity.Agenda.AddEventInput parameters) throws java.rmi.RemoteException;
    public na.oasisUtils.socialCommunity.Agenda.StatusResponse updateevent(na.oasisUtils.socialCommunity.Agenda.UpdateEventInput parameters) throws java.rmi.RemoteException;
    public na.oasisUtils.socialCommunity.Agenda.StatusResponse deleteevent(na.oasisUtils.socialCommunity.Agenda.DeleteEventInput parameters) throws java.rmi.RemoteException;
}
