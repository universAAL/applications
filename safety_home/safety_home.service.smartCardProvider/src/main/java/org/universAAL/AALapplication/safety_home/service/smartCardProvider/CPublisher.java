package org.universAAL.AALapplication.safety_home.service.smartCardProvider;

/* More on how to use this class at: 
 * http://forge.universaal.org/wiki/support:Developer_Handbook_6#Publishing_context_events */

import org.osgi.framework.BundleContext;
import org.universAAL.AALapplication.db.manager.entitymanagers.UserManager;
import org.universAAL.AALapplication.db.utils.Value;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.DefaultContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.ontology.location.indoor.Room;
import org.universAAL.ontology.phThing.Device;
import org.universAAL.ontology.safetyDevices.Door;

import java.sql.*;
import javax.sql.*;
import java.util.Vector;
import org.universAAL.AALapplication.db.utils.criteria.ArithmeticCriterion;
import org.universAAL.AALapplication.db.utils.criteria.Criterion;
import org.universAAL.AALapplication.db.utils.Column;
import org.universAAL.AALapplication.db.utils.ConnectionManager;
import org.universAAL.AALapplication.db.utils.ResultRow;
import org.universAAL.AALapplication.db.utils.Value;


public class CPublisher extends ContextPublisher{
	public static final String SAFETY_CARD_PROVIDER_NAMESPACE = "http://ontology.universaal.org/SafetySmartCardProvider.owl#";
	public static final String MY_URI = SAFETY_CARD_PROVIDER_NAMESPACE + "SmartCard";
	static final String DEVICE_URI_PREFIX = CPublisher.SAFETY_CARD_PROVIDER_NAMESPACE
			+ "controlledDevice";
	static final String LOCATION_URI_PREFIX = "urn:aal_space:myHome#";
	
	private ContextPublisher cp;
	
	private Vector allusers;
	private Vector userDetails;
	private String user;
	
	protected CPublisher(BundleContext context, ContextProvider providerInfo) {
		super(context, providerInfo);
	}
	
	protected CPublisher(BundleContext context) {
		super(context, getProviderInfo());
		try{
			ContextProvider info = new ContextProvider(SAFETY_CARD_PROVIDER_NAMESPACE + "SmartCardContextProvider");
			info.setType(ContextProviderType.controller);
			cp = new DefaultContextPublisher(context, info);
			invoke();
		}
		catch (InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public void invoke() throws InterruptedException{
		//getUsers();
		while (true){
			Thread.sleep(15*60000);
			getRandomUser();
			publishDoorBell(0);
		}
	}
	
	private void publishDoorBell(int deviceID){
		Device device=null;
		if(deviceID==0){
			Door door = new Door(CPublisher.DEVICE_URI_PREFIX + deviceID);
			device=(Device)door;
			door.setDeviceLocation(new Room(CPublisher.LOCATION_URI_PREFIX + "front door"));
			door.setDeviceRfid(this.user);
			
			System.out.println("############### PUBLISHING EVENT ###############");
			cp.publish(new ContextEvent(door, Door.PROP_DEVICE_RFID));
			System.out.println("################################################");
		}
	}

	public static int randint(int lb, int hb){
		int d=hb-lb+1;
		return ( lb+ (int)( Math.random()*d)) ;		
	}
	
	public void getRandomUser(){
   		Connection con = null;
		try{
			con = ConnectionManager.getConnection();
			this.populateUserBySmartCard(con, randint(1,5));
			if (this.userDetails.size()>0){
				ResultRow rr = (ResultRow)this.userDetails.get(0);
				System.out.println(rr.getStringValue(new Column("users","firstname")) + "\t" + rr.getStringValue(new Column("users","lastname"))+"\t"+rr.getStringValue(new Column("role","name"))+"\t"+rr.getStringValue(new Column("smartcard","shortdescription")));
				this.user = rr.getStringValue(new Column("users","firstname")) + " " + rr.getStringValue(new Column("users","lastname"));
			}
			else
				this.user = "Unknown Person";
		} 
		catch (Exception e){
			e.printStackTrace();
		}
		finally{ ConnectionManager.returnConnection(con); }
	}

	public void getUsers(){
   		Connection con = null;
		try{
			con = ConnectionManager.getConnection();
			this.populateAllUsers(con);
			for (int i=0; i<this.allusers.size(); i++){
				ResultRow rr = (ResultRow)this.allusers.get(i);
				System.out.println(rr.getStringValue(new Column("users","firstname"))+"\t"+rr.getStringValue(new Column("users","lastname"))+"\t"+rr.getStringValue(new Column("users","email")));				
			}
		} 
		catch (Exception e){
			e.printStackTrace();
		}
		finally{ ConnectionManager.returnConnection(con); }
	}
	
	private void populateAllUsers(Connection con) throws SQLException{
		 UserManager um = new UserManager(con);
		 this.allusers = um.getAllUsers(new Vector(),null);
	}

	private void populateUserBySmartCard(Connection con, int smartcard_id) throws SQLException{
		 UserManager um = new UserManager(con);
		 Vector criteria = new Vector();
		 ArithmeticCriterion ac = new ArithmeticCriterion(new Column("device_to_users_to_smartcard","device_id"), new Value(1), Criterion.EQUAL);
		 criteria.add(ac);
		 this.userDetails = um.getUserBySmartCard(smartcard_id, new Vector(), criteria);
	}

	private static ContextProvider getProviderInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	public void communicationChannelBroken() {
		// TODO Auto-generated method stub
		
	}
}
