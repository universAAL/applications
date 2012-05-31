package na.oasisUtils.socialCommunity.Agenda;

public class AgendaPortProxy implements na.oasisUtils.socialCommunity.Agenda.AgendaPort {
  private String _endpoint = null;
  private na.oasisUtils.socialCommunity.Agenda.AgendaPort agendaPort = null;
  
  public AgendaPortProxy() {
    _initAgendaPortProxy();
  }
  
  public AgendaPortProxy(String endpoint) {
    _endpoint = endpoint;
    _initAgendaPortProxy();
  }
  
  private void _initAgendaPortProxy() {
    try {
      agendaPort = (new na.oasisUtils.socialCommunity.Agenda.AgendaServiceLocator()).getAgendaPort();
      if (agendaPort != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)agendaPort)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)agendaPort)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (agendaPort != null)
      ((javax.xml.rpc.Stub)agendaPort)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public na.oasisUtils.socialCommunity.Agenda.AgendaPort getAgendaPort() {
    if (agendaPort == null)
      _initAgendaPortProxy();
    return agendaPort;
  }
  
  public na.oasisUtils.socialCommunity.Agenda.GetMyEventsOutput getmyevents(na.oasisUtils.socialCommunity.Agenda.GetMyEventsInput parameters) throws java.rmi.RemoteException{
    if (agendaPort == null)
      _initAgendaPortProxy();
    return agendaPort.getmyevents(parameters);
  }
  
  public na.oasisUtils.socialCommunity.Agenda.StatusResponse addevent(na.oasisUtils.socialCommunity.Agenda.AddEventInput parameters) throws java.rmi.RemoteException{
    if (agendaPort == null)
      _initAgendaPortProxy();
    return agendaPort.addevent(parameters);
  }
  
  public na.oasisUtils.socialCommunity.Agenda.StatusResponse updateevent(na.oasisUtils.socialCommunity.Agenda.UpdateEventInput parameters) throws java.rmi.RemoteException{
    if (agendaPort == null)
      _initAgendaPortProxy();
    return agendaPort.updateevent(parameters);
  }
  
  public na.oasisUtils.socialCommunity.Agenda.StatusResponse deleteevent(na.oasisUtils.socialCommunity.Agenda.DeleteEventInput parameters) throws java.rmi.RemoteException{
    if (agendaPort == null)
      _initAgendaPortProxy();
    return agendaPort.deleteevent(parameters);
  }
  
  
}