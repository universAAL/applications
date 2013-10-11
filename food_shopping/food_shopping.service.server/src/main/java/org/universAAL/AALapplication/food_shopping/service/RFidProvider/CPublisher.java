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
import org.universAAL.AALapplication.food_shopping.service.db.Derby.DerbyInterface;
import org.universAAL.AALapplication.food_shopping.service.db.manager.entitymanagers.UserManager;
import org.universAAL.AALapplication.food_shopping.service.db.utils.Value;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.DefaultContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.ontology.Shopping.FoodItem;
import org.universAAL.ontology.Shopping.FoodManagement;
import org.universAAL.ontology.Shopping.Refrigerator;
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

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.ArrayList;
import org.universAAL.AALapplication.food_shopping.service.db.utils.criteria.ArithmeticCriterion;
import org.universAAL.AALapplication.food_shopping.service.db.utils.criteria.Criterion;
import org.universAAL.AALapplication.food_shopping.service.db.utils.Column;
import org.universAAL.AALapplication.food_shopping.service.db.utils.ConnectionManager;
import org.universAAL.AALapplication.food_shopping.service.db.utils.ResultRow;

import com.metratec.lib.connection.CommConnectionException;
import com.metratec.lib.rfidreader.UHFReader;

import java.net.*;

/**
 * @author dimokas
 *
 */
public class CPublisher{
	public static final String RFID_PROVIDER_NAMESPACE = "http://ontology.universaal.org/RFidReaderProvider.owl#";
	public static final String MY_URI = RFID_PROVIDER_NAMESPACE + "RFidReader";
	static final String DEVICE_URI_PREFIX = CPublisher.RFID_PROVIDER_NAMESPACE + "controlledDevice";
	static final String LOCATION_URI_PREFIX = "urn:aal_space:myHome#";

	
	private Hashtable basket = new Hashtable();
	private Vector itemDetails;
	private String foodItemName;
	private ContextPublisher cp;
	
  	public CPublisher(ContextPublisher cp) {
		try{
			this.cp = cp;
			invoke();
		}
		catch (InterruptedException e){
			e.printStackTrace();
		}
	}

  	public void invoke() throws InterruptedException{
		getItemByRFidTag();
	}

  	//private void publishFoodItem(int deviceID, String name, double quantity, String company, String size, int code, String tag){
  	private void publishFoodItem(int deviceID, FoodItem[] lfi){
		if(deviceID==0){
			System.out.println("############### PUBLISHING EVENT ###############.");
			Refrigerator r = new Refrigerator();
			r.setFoodItems(lfi);
			ContextEvent ce = new ContextEvent(r, Refrigerator.PROP_HAS_FOODITEMS);
			cp.publish(ce);
			System.out.println("################################################");
		}
	}
  	
	public void getItemByRFidTag(){
		Vector newItems = new Vector();
        UHFReader r = new UHFReader("160.40.60.210", 10001, UHFReader.READER_MODE.ETSI); 
        try {
			while (true){
				r.connect();
				ArrayList al = (ArrayList)r.getInventory();
				for (int i=0; i<al.size(); i++){
					String readingTag = (String)al.get(i);
					System.out.println("Metratec reader identifies tag: "+readingTag);
					if (!basket.containsKey((String)al.get(i))){
						// Old implementation based on MySQL
						//populateFoodItem(con,(String)al.get(i));
						this.populateDerbyFoodItem((String)al.get(i));
						FoodItem fi = new FoodItem(CPublisher.DEVICE_URI_PREFIX + 0);
						for (int j=0; j<this.itemDetails.size(); j++){
							String name = ((ResultRow)this.itemDetails.get(j)).getStringValue(new Column("CODE", "NAME"));
	        				fi.setCode(((ResultRow)this.itemDetails.get(j)).getIntValue(new Column("CODE", "CODE_ID")));
	        				fi.setName(name);
	        				fi.setCompany(((ResultRow)this.itemDetails.get(j)).getStringValue(new Column("CODE", "COMPANY")));
	        				fi.setSize(((ResultRow)this.itemDetails.get(j)).getStringValue(new Column("CODE", "SIZE")));
	        				fi.setQuantity(((ResultRow)this.itemDetails.get(j)).getIntValue(new Column("ITEM", "QUANTITY")));
	        				fi.setExpDate(((ResultRow)this.itemDetails.get(j)).getStringValue(new Column("ITEM", "EXPIRATION")));
	        				fi.setInsDate(((ResultRow)this.itemDetails.get(j)).getStringValue(new Column("ITEM", "INSERTION")));
	        				fi.setTagID(((ResultRow)this.itemDetails.get(j)).getStringValue(new Column("RFIDTAG", "SHORTDESCRIPTION")));
						}
						basket.put((String)al.get(i), fi);
						// Old implementation based on MySQL
						//addToDB(con,readingTag);

						/* update db item's state = 1 */
						System.out.println("Add item: "+(String)readingTag );
						this.addToDerbyDB(readingTag);
	        		}
				}
				// Clean basket from missing items
				Enumeration en = (Enumeration)basket.keys();
				Vector tobeRemoved = new Vector();
				while (en.hasMoreElements()){
					String key = (String)en.nextElement();
					boolean rem = false;
					for (int i=0; i<al.size(); i++){
						String readingTag = (String)al.get(i);
						if (key.equals(readingTag))
							rem = true;
					}
					if (rem==false)
						tobeRemoved.add(key);
				}
				for (int i=0; i<tobeRemoved.size(); i++){
					System.out.println("Remove item: "+(String)tobeRemoved.get(i));
					basket.remove((String)tobeRemoved.get(i));
					// Old implementation based on MySQL
					//removeFromDB(con,(String)tobeRemoved.get(i));
					
					/* update db item's state = 0 */
					this.removeFromDerbyDB((String)tobeRemoved.get(i));

				}
				tobeRemoved.clear();
				FoodItem[] lfi;
				if (basket.size()>0){
					// publish basket entries (food item)
					Enumeration en2 = (Enumeration)basket.keys();
					lfi = new FoodItem[basket.size()];
					int l=0;
					while (en2.hasMoreElements()){
						String key = (String)en2.nextElement();
						//System.out.println("Food Item="+((FoodItem)basket.get(key)).getName());
						lfi[l] = new FoodItem(FoodItem.MY_URI, ((FoodItem)basket.get(key)).getName(), ((FoodItem)basket.get(key)).getQuantity(), ((FoodItem)basket.get(key)).getSize(), ((FoodItem)basket.get(key)).getCompany(), ((FoodItem)basket.get(key)).getCode(), ((FoodItem)basket.get(key)).getTagID());
						lfi[l].setInsDate(((FoodItem)basket.get(key)).getInsDate());
						lfi[l].setExpDate(((FoodItem)basket.get(key)).getExpDate());
						l++;
						//publishFoodItem(0,((FoodItem)basket.get(key)).getName(),((FoodItem)basket.get(key)).getQuantity(),((FoodItem)basket.get(key)).getCompany(),((FoodItem)basket.get(key)).getSize(),((FoodItem)basket.get(key)).getCode(),((FoodItem)basket.get(key)).getTagID());
						//break;
					}
					publishFoodItem(0,lfi);
				}
				else{
					lfi = new FoodItem[1];
					lfi[0] = new FoodItem(FoodItem.MY_URI, "item", 0.0, "size", "company", 0, "000000000000000000");
					publishFoodItem(0,lfi);
				}
				r.close();
				Thread.sleep(20*1000);
			}
        }
        catch (CommConnectionException cce) {
            System.err.println("1. Couldn't get I/O for the connection to: 160.40.60.210");
        	populateAndPublishFoodItems();
            //cce.printStackTrace();
            //System.exit(1);
        }
        catch (Exception e) {
            System.err.println("2. Couldn't get I/O for the connection to: 160.40.60.210");
            //e.printStackTrace();
            //System.exit(1);
        }
        
	}

	private void populateAndPublishFoodItems(){
		try{
			DerbyInterface di = new DerbyInterface(); 
			di.init();
			this.itemDetails = di.getItemByStateUsingDerby(1);
			System.out.println("itemDetails="+this.itemDetails);
			
			for (int j=0; j<this.itemDetails.size(); j++){
				FoodItem fi = new FoodItem(CPublisher.DEVICE_URI_PREFIX + 0);
				String name = ((ResultRow)this.itemDetails.get(j)).getStringValue(new Column("CODE", "NAME"));
				fi.setCode(((ResultRow)this.itemDetails.get(j)).getIntValue(new Column("CODE", "CODE_ID")));
				fi.setName(name);
				fi.setCompany(((ResultRow)this.itemDetails.get(j)).getStringValue(new Column("CODE", "COMPANY")));
				fi.setSize(((ResultRow)this.itemDetails.get(j)).getStringValue(new Column("CODE", "SIZE")));
				fi.setQuantity(((ResultRow)this.itemDetails.get(j)).getIntValue(new Column("ITEM", "QUANTITY")));
				fi.setExpDate(((ResultRow)this.itemDetails.get(j)).getStringValue(new Column("ITEM", "EXPIRATION")));
				fi.setInsDate(((ResultRow)this.itemDetails.get(j)).getStringValue(new Column("ITEM", "INSERTION")));
				fi.setTagID(((ResultRow)this.itemDetails.get(j)).getStringValue(new Column("RFIDTAG", "SHORTDESCRIPTION")));

				basket.put(fi.getTagID(), fi);
			}
	
			FoodItem[] lfi;		
			if (basket.size()>0){
				// publish basket entries (food item)
				Enumeration en2 = (Enumeration)basket.keys();
				lfi = new FoodItem[basket.size()];
				int l=0;
				while (en2.hasMoreElements()){
					String key = (String)en2.nextElement();
					lfi[l] = new FoodItem(FoodItem.MY_URI, ((FoodItem)basket.get(key)).getName(), ((FoodItem)basket.get(key)).getQuantity(), ((FoodItem)basket.get(key)).getSize(), ((FoodItem)basket.get(key)).getCompany(), ((FoodItem)basket.get(key)).getCode(), ((FoodItem)basket.get(key)).getTagID());
					lfi[l].setInsDate(((FoodItem)basket.get(key)).getInsDate());
					lfi[l].setExpDate(((FoodItem)basket.get(key)).getExpDate());
					l++;
				}
				publishFoodItem(0,lfi);
			}
		}
		catch(Exception e){ e.printStackTrace(); }
	}
	
	/* Functions based on Derby */
	
	private void populateDerbyFoodItem(String rfidtag) throws Exception{
		DerbyInterface di = new DerbyInterface(); 
		di.init();
		this.itemDetails = di.getItemByTagUsingDerby(rfidtag);
		System.out.println("itemDetails="+this.itemDetails);
	}

	private void addToDerbyDB(String rfidtag) throws Exception{
		DerbyInterface di = new DerbyInterface(); 
		di.init();
		this.itemDetails = di.getItemByTagUsingDerby(rfidtag);
		ResultRow rr = (ResultRow)this.itemDetails.get(0);
		int itemID = rr.getIntValue(new Column("ITEM","ITEM_ID"));
		di.modifyItemState(itemID, 1);
	}

	private void removeFromDerbyDB(String rfidtag) throws Exception{
		DerbyInterface di = new DerbyInterface(); 
		di.init();
		this.itemDetails = di.getItemByTagUsingDerby(rfidtag);
		ResultRow rr = (ResultRow)this.itemDetails.get(0);
		int itemID = rr.getIntValue(new Column("ITEM","ITEM_ID"));
		di.modifyItemState(itemID, 0);
	}

	
	/* Functions based on MySQL */
	
	private void populateFoodItem(Connection con, String rfidtag) throws SQLException{
		 UserManager um = new UserManager(con);
		 Vector criteria = new Vector();
		 ArithmeticCriterion ac = new ArithmeticCriterion(new Column("item","device_id"), new Value(1), Criterion.EQUAL);
		 criteria.add(ac);
		 this.itemDetails = um.getItemByTag(rfidtag, new Vector(), criteria);
	}

	private void removeFromDB(Connection con, String rfidtag) throws SQLException{
		 UserManager um = new UserManager(con);
		 Vector criteria = new Vector();
		 ArithmeticCriterion ac = new ArithmeticCriterion(new Column("item","device_id"), new Value(1), Criterion.EQUAL);
		 criteria.add(ac);
		 this.itemDetails = um.getItemByTag(rfidtag, new Vector(), criteria);
		 ResultRow rr = (ResultRow)this.itemDetails.get(0);
		 int itemID = rr.getIntValue(new Column("item","item_id"));
		 Hashtable colValues = new Hashtable();
		 colValues.put("state",new Value(0));
		 um.modifyItem(itemID, colValues);
	}

	private void addToDB(Connection con, String rfidtag) throws SQLException{
		 UserManager um = new UserManager(con);
		 Vector criteria = new Vector();
		 ArithmeticCriterion ac = new ArithmeticCriterion(new Column("item","device_id"), new Value(1), Criterion.EQUAL);
		 criteria.add(ac);
		 this.itemDetails = um.getItemByTag(rfidtag, new Vector(), criteria);
		 ResultRow rr = (ResultRow)this.itemDetails.get(0);
		 int itemID = rr.getIntValue(new Column("item","item_id"));
		 Hashtable colValues = new Hashtable();
		 colValues.put("state",new Value(1));
		 um.modifyItem(itemID, colValues);
	}

	
	private static ContextProvider getProviderInfo() {
		return null;
	}

	public void communicationChannelBroken() {
	}
	
    public String getClassURI() {
    	return MY_URI;
    }
}
