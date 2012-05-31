package na.utils.ws;

import java.rmi.RemoteException;

import na.oasisUtils.socialCommunity.Agenda.AgendaPortProxy;
import na.oasisUtils.socialCommunity.Agenda.GetMyEventsInput;
import na.oasisUtils.socialCommunity.Agenda.GetMyEventsOutput;
import na.oasisUtils.socialCommunity.Friends.AddTopicInput;
import na.oasisUtils.socialCommunity.Friends.FriendsPortProxy;
import na.oasisUtils.socialCommunity.Friends.Group;
import na.oasisUtils.socialCommunity.Friends.GroupsResponse;
import na.oasisUtils.socialCommunity.Friends.NewTopic;
import na.oasisUtils.socialCommunity.Friends.StatusResponse;
import na.utils.ServiceInterface;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class SocialCommunityWSConnector {
	private Log log = LogFactory.getLog(SocialCommunityWSConnector.class);
	FriendsPortProxy friends = new FriendsPortProxy();
	na.oasisUtils.socialCommunity.Auth.AuthPortProxy auth = new na.oasisUtils.socialCommunity.Auth.AuthPortProxy();
	
	public Object invokeOperation(String operationName, Object[] input) {
		try {
		if (operationName.compareTo(ServiceInterface.OP_Social_FriendsLogin)==0) {
			String username = (String)input[0];
			String password = (String)input[1];
			return this.friends_login(username, password);
		} else if (operationName.compareTo(ServiceInterface.OP_Social_GetMyGroupsResponse)==0) {
			na.oasisUtils.socialCommunity.Friends.AuthToken token = (na.oasisUtils.socialCommunity.Friends.AuthToken) input[0];
			return this.getMyGroups(token);
		} else if (operationName.compareTo(ServiceInterface.OP_Social_AddTopic)==0) {
			Group group = (Group) input[0];
			na.oasisUtils.socialCommunity.Friends.AuthToken token = (na.oasisUtils.socialCommunity.Friends.AuthToken) input[1];
			FriendsPortProxy friends = (FriendsPortProxy) input[2];
			String course = (String) input[3];
			String procedure = (String) input[4];
			return this.addTopic(token, group, course, procedure, friends);
		} if (operationName.compareTo(ServiceInterface.OP_Social_AgendaLogin)==0) {
				String username = (String)input[0];
				String password = (String)input[1];
				return this.agenda_login(username, password);
		} else if (operationName.compareTo(ServiceInterface.OP_Social_AgendaGetMyEvents)==0) {
			na.oasisUtils.socialCommunity.Agenda.AuthToken token = (na.oasisUtils.socialCommunity.Agenda.AuthToken) input[0];
			return this.getMyEvents(token, (String)input[1], (String)input[2]);
		} else {
			log.fatal("Unknown operation: " + operationName);
		}
		} catch (RemoteException e) {
			log.error("Error: remoteException");
			e.printStackTrace();
		}
		
		return null;
	}
	
	private Object friends_login(String username, String password) throws RemoteException {
		na.oasisUtils.socialCommunity.Auth.AuthenticationInput input = new na.oasisUtils.socialCommunity.Auth.AuthenticationInput(username, password);
		na.oasisUtils.socialCommunity.Auth.AuthenticationResponse response = auth.gettoken(input);
		na.oasisUtils.socialCommunity.Friends.AuthToken token = new na.oasisUtils.socialCommunity.Friends.AuthToken(response.getToken());
		return token;
	}

	private Object agenda_login(String username, String password) throws RemoteException {
		na.oasisUtils.socialCommunity.Auth.AuthenticationInput input = new na.oasisUtils.socialCommunity.Auth.AuthenticationInput(username, password);
		na.oasisUtils.socialCommunity.Auth.AuthenticationResponse response = auth.gettoken(input);
		na.oasisUtils.socialCommunity.Agenda.AuthToken token = new na.oasisUtils.socialCommunity.Agenda.AuthToken(response.getToken());
		return token;
	}
	
	private Object getMyGroups(na.oasisUtils.socialCommunity.Friends.AuthToken token) throws RemoteException {
		FriendsPortProxy friends = new FriendsPortProxy();
		GroupsResponse groupResponse = friends.getmygroups(token);
		return groupResponse;
	}
	
	private Object addTopic(na.oasisUtils.socialCommunity.Friends.AuthToken token, Group group, String course, String procedure, FriendsPortProxy friends) throws RemoteException {
		NewTopic topic = new NewTopic(group.getId(), course, procedure, "recipes");
		AddTopicInput topicInput = new AddTopicInput(token, topic);
		StatusResponse resp = friends.addtopic(topicInput);
		return resp;
	}
	
	private Object getMyEvents(na.oasisUtils.socialCommunity.Agenda.AuthToken token, String start_date, String end_date) throws RemoteException {
		AgendaPortProxy agenda = new AgendaPortProxy();
		GetMyEventsInput eventInput = new GetMyEventsInput( token, start_date, end_date);
		GetMyEventsOutput output = agenda.getmyevents(eventInput);
		return output;
	}
}
