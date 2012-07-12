package org.universAAL.AALapplication.food_shopping.service.RFidProvider;

/* More on how to use this class at: 
 * http://forge.universaal.org/wiki/support:Developer_Handbook_6#Publishing_context_events */

import org.osgi.framework.BundleContext;
import org.universAAL.AALapplication.food_shopping.service.db.manager.entitymanagers.UserManager;
import org.universAAL.AALapplication.food_shopping.service.db.utils.Value;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.DefaultContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.ontology.location.indoor.Room;
import org.universAAL.ontology.phThing.Device;
import org.universAAL.AALapplication.ont.foodDevices.FoodItem;
import org.universAAL.AALapplication.ont.foodDevices.Refrigerator;

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

import com.metratec.lib.rfidreader.UHFReader;

import java.net.*;


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

  	private void publishFoodItem(int deviceID, String name, double quantity, String company, String size, int code, String tag){
		if(deviceID==0){
			//FoodItem fi = new FoodItem(CPublisher.DEVICE_URI_PREFIX + deviceID, name, quantity, size, company, code, tag);
			FoodItem fi = new FoodItem(FoodItem.MY_URI, name, quantity, size, company, code, tag);
			//DeviceFoodItem fi = new DeviceFoodItem(tag, name, quantity, size, company, code);
			System.out.println("############### PUBLISHING EVENT ###############.");
			cp.publish(new ContextEvent(fi, FoodItem.PROP_TAGID));
			System.out.println("################################################");
		}
	}
  	
	public void getItemByRFidTag(){
		Vector newItems = new Vector();
		Connection con = null;
        UHFReader r = new UHFReader("160.40.60.210", 10001, UHFReader.READER_MODE.ETSI); 
        try {
			con = ConnectionManager.getConnection();
			r.connect();
			while (true){
				ArrayList al = (ArrayList)r.getInventory();
				for (int i=0; i<al.size(); i++){
					String readingTag = (String)al.get(i);
					System.out.println("Metratec reader identifies tag: "+readingTag);
					if (!basket.containsKey((String)al.get(i))){
						populateFoodItem(con,(String)al.get(i));
						FoodItem fi = new FoodItem(CPublisher.DEVICE_URI_PREFIX + 0);
						for (int j=0; j<this.itemDetails.size(); j++){
							String name = ((ResultRow)this.itemDetails.get(j)).getStringValue(new Column("code", "name"));
	        				fi.setCode(((ResultRow)this.itemDetails.get(j)).getIntValue(new Column("code", "code_id")));
	        				fi.setName(name);
	        				fi.setCompany(((ResultRow)this.itemDetails.get(j)).getStringValue(new Column("code", "company")));
	        				fi.setSize(((ResultRow)this.itemDetails.get(j)).getStringValue(new Column("code", "size")));
	        				fi.setQuantity(((ResultRow)this.itemDetails.get(j)).getDoubleValue(new Column("item", "quantity")));
	        				fi.setTagID(((ResultRow)this.itemDetails.get(j)).getStringValue(new Column("rfidtag", "shortdescription")));
	    					//System.out.println(fi.getName()+", "+fi.getQuantity()+", "+fi.getCompany()+", "+fi.getSize()+", "+fi.getCode()+", "+fi.getTagID());
						}
						basket.put((String)al.get(i), fi);
						// update db item's state = 1
						addToDB(con,readingTag);
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
					// update db item's state = 0
					removeFromDB(con,(String)tobeRemoved.get(i));
				}
				tobeRemoved.clear();
				
				if (basket.size()>0){
					// publish basket entries (food item)
					Enumeration en2 = (Enumeration)basket.keys();
					while (en2.hasMoreElements()){
						String key = (String)en2.nextElement();
						publishFoodItem(0,((FoodItem)basket.get(key)).getName(),((FoodItem)basket.get(key)).getQuantity(),((FoodItem)basket.get(key)).getCompany(),((FoodItem)basket.get(key)).getSize(),((FoodItem)basket.get(key)).getCode(),((FoodItem)basket.get(key)).getTagID());
						break;
					}				
				}
				else
					publishFoodItem(0,"item",0.0,"company","size",0,"000000000000000000");
				Thread.sleep(10*1000);
			}
        } catch (Exception e) {
            //System.err.println("Couldn't get I/O for the connection to: 160.40.60.210");
            //e.printStackTrace();
            //System.exit(1);
        }
        finally{ ConnectionManager.returnConnection(con); }
        
        
        try{
        	//String fromServer;

		} 
		catch (Exception e){
			e.printStackTrace();
		}
		
	}

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
