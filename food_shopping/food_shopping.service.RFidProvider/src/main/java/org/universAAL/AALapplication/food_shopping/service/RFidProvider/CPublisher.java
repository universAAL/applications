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
package org.universAAL.AALapplication.food_shopping.service.RFidProvider;

import org.osgi.framework.BundleContext;
import org.universAAL.AALapplication.food_shopping.service.db.manager.entitymanagers.UserManager;
import org.universAAL.AALapplication.food_shopping.service.db.utils.Value;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.DefaultContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.ontology.Shopping.FoodItem;
import org.universAAL.ontology.location.indoor.Room;
import org.universAAL.ontology.phThing.Device;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.*;
import javax.sql.*;
import java.util.Vector;
import java.util.ArrayList;
import org.universAAL.AALapplication.food_shopping.service.db.utils.criteria.ArithmeticCriterion;
import org.universAAL.AALapplication.food_shopping.service.db.utils.criteria.Criterion;
import org.universAAL.AALapplication.food_shopping.service.db.utils.Column;
import org.universAAL.AALapplication.food_shopping.service.db.utils.ConnectionManager;
import org.universAAL.AALapplication.food_shopping.service.db.utils.ResultRow;
import org.universAAL.AALapplication.food_shopping.service.db.utils.Value;

import com.metratec.lib.rfidreader.UHFReader;

import java.net.*;

/**
 * @author dimokas
 * 
 */

public class CPublisher extends ContextPublisher{
	public static final String RFID_PROVIDER_NAMESPACE = "http://ontology.universaal.org/RFidReaderProvider.owl#";
	public static final String MY_URI = RFID_PROVIDER_NAMESPACE + "RFidReader";
	static final String DEVICE_URI_PREFIX = CPublisher.RFID_PROVIDER_NAMESPACE + "controlledDevice";
	static final String LOCATION_URI_PREFIX = "urn:aal_space:myHome#";
	
	private ContextPublisher cp;
	
	private Vector allusers;
	private Vector userDetails;
	private String foodItemName;
	
	protected CPublisher(ModuleContext context, ContextProvider providerInfo) {
		super(context, providerInfo);
	}
	
	protected CPublisher(ModuleContext context) {
		super(context, getProviderInfo());
		try{
			ContextProvider info = new ContextProvider(RFID_PROVIDER_NAMESPACE + "RFidContextProvider");
			info.setType(ContextProviderType.controller);
			cp = new DefaultContextPublisher(context, info);
			invoke();
		}
		catch (InterruptedException e){
			e.printStackTrace();
		}
	}
	
	public static void invoke() throws InterruptedException{
		getItemByRFidTag();
	}

/*	
	public void invoke() throws InterruptedException{
		while (true){
			Thread.sleep(15*60000);
			getRandomUser();
			publishDoorBell(0);
		}
	}
*/
	private void publishFoodItem(int deviceID){
		Device device=null;
		if(deviceID==0){
			FoodItem door = new FoodItem(CPublisher.DEVICE_URI_PREFIX + deviceID);
			//device=(Device)door;
			//door.setDeviceLocation(new Room(CPublisher.LOCATION_URI_PREFIX + "front door"));
			//door.setDeviceRfid(this.user);
			door.setName(this.foodItemName);
			door.setQuantity(1);
			
			System.out.println("############### PUBLISHING EVENT ###############");
			cp.publish(new ContextEvent(door, FoodItem.MY_URI));
			System.out.println("################################################");
		}
	}

	public static int randint(int lb, int hb){
		int d=hb-lb+1;
		return ( lb+ (int)( Math.random()*d)) ;		
	}
	
	public static void getItemByRFidTag(){
		
        Connection con = null;
        UHFReader r = new UHFReader("160.40.60.210", 80, UHFReader.READER_MODE.ETSI); 
        
        try {
        	r.connect();
        	ArrayList al = (ArrayList)r.getInventory();
        	for (int i=0; i<al.size(); i++)
        		System.out.println(al.get(i));
     
        } catch (Exception e) {
            System.err.println("Couldn't get I/O for the connection to: 160.40.60.229");
            System.exit(1);
        }

        try{
        	//String fromServer;
			//con = ConnectionManager.getConnection();

		} 
		catch (Exception e){
			e.printStackTrace();
		}
		finally{ ConnectionManager.returnConnection(con); }
	}
/*
	public void getRandomUser(){
   		Connection con = null;
		try{
			con = ConnectionManager.getConnection();
			this.populateUserBySmartCard(con, randint(1,5));
			if (this.userDetails.size()>0){
				ResultRow rr = (ResultRow)this.userDetails.get(0);
				System.out.println(rr.getStringValue(new Column("users","firstname")) + "\t" + rr.getStringValue(new Column("users","lastname"))+"\t"+rr.getStringValue(new Column("role","name"))+"\t"+rr.getStringValue(new Column("smartcard","shortdescription")));
				this.foodItemName = rr.getStringValue(new Column("users","firstname")) + " " + rr.getStringValue(new Column("users","lastname"));
			}
			else
				this.foodItemName = "Unknown Person";
		} 
		catch (Exception e){
			e.printStackTrace();
		}
		finally{ ConnectionManager.returnConnection(con); }
	}
*/
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
		// TODO Auto-generated method stub
		//return new ContextProvider(MY_URI);
		return null;
	}

	public void communicationChannelBroken() {
		// TODO Auto-generated method stub
		
	}
	
	public boolean isWellFormed() {
		return true;
	}
	
    public String getClassURI() {
    	return MY_URI;
    }
}
