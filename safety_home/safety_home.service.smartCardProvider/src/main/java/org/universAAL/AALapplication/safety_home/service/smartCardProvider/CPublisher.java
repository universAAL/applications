/*****************************************************************************************
 * Copyright 2012 CERTH, http://www.certh.gr - Center for Research and Technology Hellas
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *****************************************************************************************/

package org.universAAL.AALapplication.safety_home.service.smartCardProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import org.universAAL.AALapplication.db.Derby.DerbyInterface;
import org.universAAL.AALapplication.db.manager.entitymanagers.UserManager;
import org.universAAL.AALapplication.db.utils.Column;
import org.universAAL.AALapplication.db.utils.ConnectionManager;
import org.universAAL.AALapplication.db.utils.ResultRow;
import org.universAAL.AALapplication.db.utils.Value;
import org.universAAL.AALapplication.db.utils.criteria.ArithmeticCriterion;
import org.universAAL.AALapplication.db.utils.criteria.Criterion;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.DefaultContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.ontology.Safety.Door;
import org.universAAL.ontology.location.indoor.Room;
import org.universAAL.ontology.phThing.Device;

/**
 * @author dimokas
 * 
 */

public class CPublisher extends ContextPublisher{
	public static final String SAFETY_CARD_PROVIDER_NAMESPACE = "http://ontology.universaal.org/SafetySmartCardProvider.owl#";
	public static final String MY_URI = SAFETY_CARD_PROVIDER_NAMESPACE + "SmartCard";
	static final String DEVICE_URI_PREFIX = CPublisher.SAFETY_CARD_PROVIDER_NAMESPACE
			+ "controlledDevice";
	static final String LOCATION_URI_PREFIX = "urn:aal_space:test_environment#";
	
	private ContextPublisher cp;
	
	private Vector allusers;
	private Vector userDetails;
	private String user;
	
	protected CPublisher(ModuleContext context, ContextProvider providerInfo) {
		super(context, providerInfo);
	}
	
	protected CPublisher(ModuleContext context) {
		super(context, getProviderInfo());
		try{
			cp = new DefaultContextPublisher(context, getProviderInfo());
			invoke();
		}
		catch (InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public CPublisher(ModuleContext context, ContextProvider providerInfo, ContextPublisher cp) {
		super(context, providerInfo);
		try{
			this.cp = cp;
			invoke();
		}
		catch (InterruptedException e){
			//e.printStackTrace();
		}
	}

	public void invoke() throws InterruptedException{
		getUserByRFidTag();
	}

/*	
	// to be deleted after test
	public void invoke() throws InterruptedException{
		Thread.sleep(50000);
		publishDoorBell(0);
		//Thread.sleep(150000);
		//publishCaregiver(0);
	}
*/
	private void publishDoorBell(int deviceID){
		Device device=null;
		//this.user = "Unknown Person"; // to be deleted after test
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
/*
	// delete after test
	private void publishCaregiver(int deviceID){
		Device device=null;
		this.user = "Formal Caregiver: " + "Paulos Spanidis";
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
*/
	public static int randint(int lb, int hb){
		int d=hb-lb+1;
		return ( lb+ (int)( Math.random()*d)) ;		
	}
	
	public void getUserByRFidTag(){
		
        Socket kkSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        Connection con = null;
        int error = 0;
        
        try {
            kkSocket = new Socket("160.40.60.229", 4444);
            out = new PrintWriter(kkSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
        } catch (UnknownHostException e) {
        	error = 1;
            //System.err.println("Don't know about host: 160.40.60.229");
            //System.exit(1);
        } catch (IOException e) {
        	error = 1;
            //System.err.println("Couldn't get I/O for the connection to: 160.40.60.229");
            //System.exit(1);
        }

        /* TESTING DERBY DATABASE
        try{
	        this.populateUserBySmartCardUsingDerby("E007000012D70E90");
			if (this.userDetails.size()>0){
				ResultRow rr = (ResultRow)this.userDetails.get(0);
				System.out.println(rr.getStringValue(new Column("USERS","FIRSTNAME")) + "\t" + rr.getStringValue(new Column("USERS","LASTNAME"))+"\t"+rr.getStringValue(new Column("ROLE","NAME"))+"\t"+rr.getStringValue(new Column("SMARTCARD","SHORTDESCRIPTION")));
				this.user = rr.getStringValue(new Column("ROLE","NAME")) + ": " + rr.getStringValue(new Column("USERS","FIRSTNAME")) + " " + rr.getStringValue(new Column("USERS","LASTNAME"));
			}
			else
				this.user = "Unknown Person";
			publishDoorBell(0);
        }
        catch(Exception e){ e.printStackTrace(); }
        */
        
        if (error==0){
	        try{
	        	String fromServer;
				//con = ConnectionManager.getConnection();
	        	System.out.println("Starting Client ...");
	        	while ((fromServer = in.readLine()) != null) {
	        		System.out.println("Server: " + fromServer);
	        		if (fromServer.indexOf("Tag ID:")!=-1){
	        			// Old function call based on MySQL
	        			//this.populateUserBySmartCard(con, fromServer.substring(fromServer.indexOf(":")+2,fromServer.length()));
	        			this.populateUserBySmartCardUsingDerby(fromServer.substring(fromServer.indexOf(":")+2,fromServer.length()));
	        			if (this.userDetails.size()>0){
	        				ResultRow rr = (ResultRow)this.userDetails.get(0);
	        				System.out.println(rr.getStringValue(new Column("USERS","FIRSTNAME")) + "\t" + rr.getStringValue(new Column("USERS","LASTNAME"))+"\t"+rr.getStringValue(new Column("ROLE","NAME"))+"\t"+rr.getStringValue(new Column("SMARTCARD","SHORTDESCRIPTION")));
	        				this.user = rr.getStringValue(new Column("ROLE","NAME")) + ": " + rr.getStringValue(new Column("USERS","FIRSTNAME")) + " " + rr.getStringValue(new Column("USERS","LASTNAME"));
	        			}
	        			else
	        				this.user = "Unknown Person";
	        			publishDoorBell(0);
	        		}
	        		if (fromServer.equals("Bye"))
	        			break;
	        	}
	
	        	out.close();
	        	in.close();
	        	kkSocket.close();
			} 
			catch (Exception e){
				e.printStackTrace();
			}
			//finally{ ConnectionManager.returnConnection(con); }
        }
	}

/*	
	public void getUserByRFidTag(){
		
        Socket kkSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        Connection con = null;
        int error = 0;
        
        try {
            kkSocket = new Socket("160.40.60.229", 4444);
            out = new PrintWriter(kkSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
        } catch (UnknownHostException e) {
        	error = 1;
        } catch (IOException e) {
        	error = 1;
        }

        if (error==0){
	        try{
	        	String fromServer;
				con = ConnectionManager.getConnection();
	        	System.out.println("Starting Client ...");
	        	while ((fromServer = in.readLine()) != null) {
	        		System.out.println("Server: " + fromServer);
	        		if (fromServer.indexOf("Tag ID:")!=-1){
	        			this.populateUserBySmartCard(con, fromServer.substring(fromServer.indexOf(":")+2,fromServer.length()));
	        			if (this.userDetails.size()>0){
	        				ResultRow rr = (ResultRow)this.userDetails.get(0);
	        				System.out.println(rr.getStringValue(new Column("users","firstname")) + "\t" + rr.getStringValue(new Column("users","lastname"))+"\t"+rr.getStringValue(new Column("role","name"))+"\t"+rr.getStringValue(new Column("smartcard","shortdescription")));
	        				this.user = rr.getStringValue(new Column("role","name")) + ": " + rr.getStringValue(new Column("users","firstname")) + " " + rr.getStringValue(new Column("users","lastname"));
	        			}
	        			else
	        				this.user = "Unknown Person";
	        			publishDoorBell(0);
	        		}
	        		if (fromServer.equals("Bye"))
	        			break;
	        	}
	
	        	out.close();
	        	in.close();
	        	kkSocket.close();
			} 
			catch (Exception e){
				e.printStackTrace();
			}
			finally{ ConnectionManager.returnConnection(con); }
        }
	}
*/
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

	private void populateUserBySmartCardUsingDerby(String tagUid) throws Exception{
		 DerbyInterface di = new DerbyInterface();
		 di.init();
		 this.userDetails = di.getUserBySmartCardUsingDerby(tagUid);
	}

	private void populateUserBySmartCard(Connection con, int smartcard_id) throws SQLException{
		 UserManager um = new UserManager(con);
		 Vector criteria = new Vector();
		 ArithmeticCriterion ac = new ArithmeticCriterion(new Column("device_to_users_to_smartcard","device_id"), new Value(1), Criterion.EQUAL);
		 criteria.add(ac);
		 this.userDetails = um.getUserBySmartCard(smartcard_id, new Vector(), criteria);
	}

	private void populateUserBySmartCard(Connection con, String tagUid) throws SQLException{
		 UserManager um = new UserManager(con);
		 Vector criteria = new Vector();
		 ArithmeticCriterion ac = new ArithmeticCriterion(new Column("device_to_users_to_smartcard","device_id"), new Value(1), Criterion.EQUAL);
		 criteria.add(ac);
		 this.userDetails = um.getUserBySmartCard(tagUid, new Vector(), criteria);
	}

	private static ContextProvider getProviderInfo() {
		ContextEventPattern cep1 = new ContextEventPattern();
		cep1.addRestriction(MergedRestriction.getAllValuesRestriction(ContextEvent.PROP_RDF_SUBJECT, Door.MY_URI));
		ContextProvider info = new ContextProvider(SAFETY_CARD_PROVIDER_NAMESPACE + "SmartCardContextProvider");
		info.setType(ContextProviderType.controller);
		info.setProvidedEvents(new ContextEventPattern[]{cep1});

		return info;
	}

	public void communicationChannelBroken() {
		// TODO Auto-generated method stub
		
	}
}
