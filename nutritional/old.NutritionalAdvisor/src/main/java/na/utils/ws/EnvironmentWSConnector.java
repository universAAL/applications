package na.utils.ws;

import java.rmi.RemoteException;

import na.oasisUtils.environment.DomoticException;
import na.oasisUtils.environment.ITemperatureSensorServiceProxy;
import na.oasisUtils.socialCommunity.Agenda.AgendaPort;
import na.oasisUtils.socialCommunity.Agenda.AgendaPortProxy;
import na.oasisUtils.socialCommunity.Agenda.GetMyEventsInput;
import na.oasisUtils.socialCommunity.Agenda.GetMyEventsOutput;
import na.oasisUtils.socialCommunity.Auth.AuthPortProxy;
import na.oasisUtils.socialCommunity.Auth.AuthenticationInput;
import na.oasisUtils.socialCommunity.Auth.AuthenticationResponse;
import na.oasisUtils.socialCommunity.Friends.AddTopicInput;
import na.oasisUtils.socialCommunity.Friends.FriendsPortProxy;
import na.oasisUtils.socialCommunity.Friends.Group;
import na.oasisUtils.socialCommunity.Friends.GroupsResponse;
import na.oasisUtils.socialCommunity.Friends.NewTopic;
import na.oasisUtils.socialCommunity.Friends.StatusResponse;
import na.utils.ServiceInterface;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class EnvironmentWSConnector {
	private Log log = LogFactory.getLog(EnvironmentWSConnector.class);
	private ITemperatureSensorServiceProxy temp = new ITemperatureSensorServiceProxy();
	
	public Object invokeOperation(String operationName, Object[] input) {
		try {
			if (operationName.compareTo(ServiceInterface.OP_GetTemperature)==0) {
				int val = Integer.parseInt((String) input[0]);
				return this.getTemperature(val);
			} else {
				log.fatal("Unknown operation: " + operationName);
			}
		} catch (RemoteException e) {
			log.error("Error: remoteException");
			e.printStackTrace();
		}
		
		return null;
	}

	private Object getTemperature(int val) throws DomoticException, RemoteException {
		return ""+this.temp.getTemperature(val);
	}
	
	
}
