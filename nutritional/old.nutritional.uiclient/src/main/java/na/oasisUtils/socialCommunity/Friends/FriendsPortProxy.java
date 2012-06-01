package na.oasisUtils.socialCommunity.Friends;

public class FriendsPortProxy implements na.oasisUtils.socialCommunity.Friends.FriendsPort {
  private String _endpoint = null;
  private na.oasisUtils.socialCommunity.Friends.FriendsPort friendsPort = null;
  
  public FriendsPortProxy() {
    _initFriendsPortProxy();
  }
  
  public FriendsPortProxy(String endpoint) {
    _endpoint = endpoint;
    _initFriendsPortProxy();
  }
  
  private void _initFriendsPortProxy() {
    try {
      friendsPort = (new na.oasisUtils.socialCommunity.Friends.FriendsServiceLocator()).getFriendsPort();
      if (friendsPort != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)friendsPort)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)friendsPort)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (friendsPort != null)
      ((javax.xml.rpc.Stub)friendsPort)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public na.oasisUtils.socialCommunity.Friends.FriendsPort getFriendsPort() {
    if (friendsPort == null)
      _initFriendsPortProxy();
    return friendsPort;
  }
  
  public na.oasisUtils.socialCommunity.Friends.GetFriendsResponse getfriends(na.oasisUtils.socialCommunity.Friends.AuthToken parameters) throws java.rmi.RemoteException{
    if (friendsPort == null)
      _initFriendsPortProxy();
    return friendsPort.getfriends(parameters);
  }
  
  public na.oasisUtils.socialCommunity.Friends.GetUserProfileResponse getuserprofile(na.oasisUtils.socialCommunity.Friends.GetUserProfileInput parameters) throws java.rmi.RemoteException{
    if (friendsPort == null)
      _initFriendsPortProxy();
    return friendsPort.getuserprofile(parameters);
  }
  
  public na.oasisUtils.socialCommunity.Friends.GroupsResponse getgroups(na.oasisUtils.socialCommunity.Friends.AuthToken parameters) throws java.rmi.RemoteException{
    if (friendsPort == null)
      _initFriendsPortProxy();
    return friendsPort.getgroups(parameters);
  }
  
  public na.oasisUtils.socialCommunity.Friends.GroupsResponse getmygroups(na.oasisUtils.socialCommunity.Friends.AuthToken parameters) throws java.rmi.RemoteException{
    if (friendsPort == null)
      _initFriendsPortProxy();
    return friendsPort.getmygroups(parameters);
  }
  
  public na.oasisUtils.socialCommunity.Friends.StatusResponse addgroup(na.oasisUtils.socialCommunity.Friends.AddGroupInput parameters) throws java.rmi.RemoteException{
    if (friendsPort == null)
      _initFriendsPortProxy();
    return friendsPort.addgroup(parameters);
  }
  
  public na.oasisUtils.socialCommunity.Friends.StatusResponse leavegroup(na.oasisUtils.socialCommunity.Friends.GroupInput parameters) throws java.rmi.RemoteException{
    if (friendsPort == null)
      _initFriendsPortProxy();
    return friendsPort.leavegroup(parameters);
  }
  
  public na.oasisUtils.socialCommunity.Friends.StatusResponse joingroup(na.oasisUtils.socialCommunity.Friends.GroupInput parameters) throws java.rmi.RemoteException{
    if (friendsPort == null)
      _initFriendsPortProxy();
    return friendsPort.joingroup(parameters);
  }
  
  public na.oasisUtils.socialCommunity.Friends.GetGroupTopicsResponse getgrouptopics(na.oasisUtils.socialCommunity.Friends.GroupInput parameters) throws java.rmi.RemoteException{
    if (friendsPort == null)
      _initFriendsPortProxy();
    return friendsPort.getgrouptopics(parameters);
  }
  
  public na.oasisUtils.socialCommunity.Friends.GetTopicResponse gettopic(na.oasisUtils.socialCommunity.Friends.GetTopicInput parameters) throws java.rmi.RemoteException{
    if (friendsPort == null)
      _initFriendsPortProxy();
    return friendsPort.gettopic(parameters);
  }
  
  public na.oasisUtils.socialCommunity.Friends.StatusResponse addtopic(na.oasisUtils.socialCommunity.Friends.AddTopicInput parameters) throws java.rmi.RemoteException{
    if (friendsPort == null)
      _initFriendsPortProxy();
    return friendsPort.addtopic(parameters);
  }
  
  public na.oasisUtils.socialCommunity.Friends.StatusResponse posttotopic(na.oasisUtils.socialCommunity.Friends.PostToTopicInput parameters) throws java.rmi.RemoteException{
    if (friendsPort == null)
      _initFriendsPortProxy();
    return friendsPort.posttotopic(parameters);
  }
  
  
}