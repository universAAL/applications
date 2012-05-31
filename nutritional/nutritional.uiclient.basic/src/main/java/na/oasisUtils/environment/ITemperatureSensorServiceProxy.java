package na.oasisUtils.environment;

public class ITemperatureSensorServiceProxy implements na.oasisUtils.environment.ITemperatureSensorService {
  private String _endpoint = null;
  private na.oasisUtils.environment.ITemperatureSensorService iTemperatureSensorService = null;
  
  public ITemperatureSensorServiceProxy() {
    _initITemperatureSensorServiceProxy();
  }
  
  public ITemperatureSensorServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initITemperatureSensorServiceProxy();
  }
  
  private void _initITemperatureSensorServiceProxy() {
    try {
      iTemperatureSensorService = (new na.oasisUtils.environment.TemperatureSensorServiceServiceLocator()).getTemperatureSensorServicePort();
      if (iTemperatureSensorService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iTemperatureSensorService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iTemperatureSensorService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iTemperatureSensorService != null)
      ((javax.xml.rpc.Stub)iTemperatureSensorService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public na.oasisUtils.environment.ITemperatureSensorService getITemperatureSensorService() {
    if (iTemperatureSensorService == null)
      _initITemperatureSensorServiceProxy();
    return iTemperatureSensorService;
  }
  
  public long getTemperatureAge(int index) throws java.rmi.RemoteException, na.oasisUtils.environment.DomoticException{
    if (iTemperatureSensorService == null)
      _initITemperatureSensorServiceProxy();
    return iTemperatureSensorService.getTemperatureAge(index);
  }
  
  public int getTemperatureSensorCount() throws java.rmi.RemoteException{
    if (iTemperatureSensorService == null)
      _initITemperatureSensorServiceProxy();
    return iTemperatureSensorService.getTemperatureSensorCount();
  }
  
  public double getTemperature(int index) throws java.rmi.RemoteException, na.oasisUtils.environment.DomoticException{
    if (iTemperatureSensorService == null)
      _initITemperatureSensorServiceProxy();
    return iTemperatureSensorService.getTemperature(index);
  }
  
  
}