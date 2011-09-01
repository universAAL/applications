/**
 * FriendsPort.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.oasisUtils.socialCommunity.Friends;

public interface FriendsPort extends java.rmi.Remote {
    public na.oasisUtils.socialCommunity.Friends.GetFriendsResponse getfriends(na.oasisUtils.socialCommunity.Friends.AuthToken parameters) throws java.rmi.RemoteException;
    public na.oasisUtils.socialCommunity.Friends.GetUserProfileResponse getuserprofile(na.oasisUtils.socialCommunity.Friends.GetUserProfileInput parameters) throws java.rmi.RemoteException;
    public na.oasisUtils.socialCommunity.Friends.GroupsResponse getgroups(na.oasisUtils.socialCommunity.Friends.AuthToken parameters) throws java.rmi.RemoteException;
    public na.oasisUtils.socialCommunity.Friends.GroupsResponse getmygroups(na.oasisUtils.socialCommunity.Friends.AuthToken parameters) throws java.rmi.RemoteException;
    public na.oasisUtils.socialCommunity.Friends.StatusResponse addgroup(na.oasisUtils.socialCommunity.Friends.AddGroupInput parameters) throws java.rmi.RemoteException;
    public na.oasisUtils.socialCommunity.Friends.StatusResponse leavegroup(na.oasisUtils.socialCommunity.Friends.GroupInput parameters) throws java.rmi.RemoteException;
    public na.oasisUtils.socialCommunity.Friends.StatusResponse joingroup(na.oasisUtils.socialCommunity.Friends.GroupInput parameters) throws java.rmi.RemoteException;
    public na.oasisUtils.socialCommunity.Friends.GetGroupTopicsResponse getgrouptopics(na.oasisUtils.socialCommunity.Friends.GroupInput parameters) throws java.rmi.RemoteException;
    public na.oasisUtils.socialCommunity.Friends.GetTopicResponse gettopic(na.oasisUtils.socialCommunity.Friends.GetTopicInput parameters) throws java.rmi.RemoteException;
    public na.oasisUtils.socialCommunity.Friends.StatusResponse addtopic(na.oasisUtils.socialCommunity.Friends.AddTopicInput parameters) throws java.rmi.RemoteException;
    public na.oasisUtils.socialCommunity.Friends.StatusResponse posttotopic(na.oasisUtils.socialCommunity.Friends.PostToTopicInput parameters) throws java.rmi.RemoteException;
}
