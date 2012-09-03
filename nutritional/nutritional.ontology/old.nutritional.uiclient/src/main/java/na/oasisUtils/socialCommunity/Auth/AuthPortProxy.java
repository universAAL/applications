package na.oasisUtils.socialCommunity.Auth;

public class AuthPortProxy implements na.oasisUtils.socialCommunity.Auth.AuthPort {
  private String _endpoint = null;
  private na.oasisUtils.socialCommunity.Auth.AuthPort authPort = null;
  
  public AuthPortProxy() {
    _initAuthPortProxy();
  }
  
  public AuthPortProxy(String endpoint) {
    _endpoint = endpoint;
    _initAuthPortProxy();
  }
  
  private void _initAuthPortProxy() {
    try {
      authPort = (new na.oasisUtils.socialCommunity.Auth.AuthServiceLocator()).getAuthPort();
      if (authPort != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)authPort)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)authPort)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (authPort != null)
      ((javax.xml.rpc.Stub)authPort)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public na.oasisUtils.socialCommunity.Auth.AuthPort getAuthPort() {
    if (authPort == null)
      _initAuthPortProxy();
    return authPort;
  }
  
  public na.oasisUtils.socialCommunity.Auth.AuthenticationResponse gettoken(na.oasisUtils.socialCommunity.Auth.AuthenticationInput parameters) throws java.rmi.RemoteException{
    if (authPort == null)
      _initAuthPortProxy();
    return authPort.gettoken(parameters);
  }
  
  
}