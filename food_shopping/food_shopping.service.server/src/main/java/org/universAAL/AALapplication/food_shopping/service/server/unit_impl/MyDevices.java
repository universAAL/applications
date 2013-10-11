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

package org.universAAL.AALapplication.food_shopping.service.server.unit_impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.ontology.Shopping.FoodItem;
import org.universAAL.ontology.Shopping.ShoppingList;
import org.universAAL.AALapplication.food_shopping.service.RFidProvider.CPublisher;
import org.universAAL.AALapplication.food_shopping.service.db.Derby.DerbyInterface;
import org.universAAL.AALapplication.food_shopping.service.db.manager.entitymanagers.UserManager;
import org.universAAL.AALapplication.food_shopping.service.db.utils.Column;
import org.universAAL.AALapplication.food_shopping.service.db.utils.ConnectionManager;
import org.universAAL.AALapplication.food_shopping.service.db.utils.ResultRow;
import org.universAAL.AALapplication.food_shopping.service.db.utils.Value;
import org.universAAL.AALapplication.food_shopping.service.db.utils.criteria.ArithmeticCriterion;
import org.universAAL.AALapplication.food_shopping.service.db.utils.criteria.Criterion;
import org.universAAL.AALapplication.food_shopping.service.db.utils.criteria.StringCriterion;
import org.universAAL.AALapplication.food_shopping.service.server.Activator;

/**
 * @author dimokas
 *
 */
public class MyDevices {
	private Hashtable shoppingLists = new Hashtable();
	private Hashtable shoppingListItems = new Hashtable();
	
	private class Device {
		String loc;
		boolean isOn;
		int temp;
		Hashtable foodItems = new Hashtable();
		Hashtable codeItems = new Hashtable();
		
		public Device(String loc, boolean isOn) {
			this.loc = loc;
			this.isOn = isOn;
		}
		public Device(String loc, boolean isOn, int temp) {
			this.loc = loc;
			this.isOn = isOn;
			this.temp = temp;
		}
		public Device(String loc, boolean isOn, int temp, DeviceFoodItem dfi) {
			this.loc = loc;
			this.isOn = isOn;
			this.temp = temp;
			this.foodItems.put(dfi.getName(), dfi);
		}
	}
	
	private Device[] myDeviceDB = new Device[] {
			new Device("loc1", false, 0)
	};
	
	private ArrayList listeners = new ArrayList();
	
	public MyDevices() {}
	
	public void addListener(DeviceStateListener l) {
		listeners.add(l);
	}
	
	public int[] getDeviceIDs() {
		int[] ids = new int[myDeviceDB.length];
		for (int i=0; i<myDeviceDB.length; i++)
			ids[i] = i;
		return ids;
	}
	
	public Hashtable[] getFoodItems() {
		Hashtable[] fooditems = new Hashtable[myDeviceDB.length];
		for (int i=0; i<myDeviceDB.length; i++)
			fooditems[i] = myDeviceDB[i].foodItems;
		return fooditems;
	}
	
	public String getDeviceLocation(int deviceID) {
		return myDeviceDB[deviceID].loc;
	}
	
	public boolean isOn(int deviceID) {
		return myDeviceDB[deviceID].isOn;
	}
	
	public int getDeviceTemperature(int deviceID) {
		return myDeviceDB[deviceID].temp;
	}

	public Hashtable getFoodItems(int deviceID){
		return myDeviceDB[deviceID].foodItems;
	}
	
	public void removeListener(DeviceStateListener l) {
		listeners.remove(l);
	}
	
	public void addFoodItem(int deviceID, FoodItem value) {
		if (myDeviceDB[deviceID].isOn) {
			String name = value.getName();
			double quantity = value.getQuantity();
			String size = value.getSize();
			String company = value.getCompany();
			int code = value.getCode();
			String itemID = value.getTagID();
			//DeviceFoodItem dfi = new DeviceFoodItem(name,quantity,size,company,code);
			DeviceFoodItem dfi = new DeviceFoodItem(itemID, name,quantity,size,company,code);
			if (myDeviceDB[deviceID].foodItems.containsKey(name)){
				//LogUtils.logInfo(Activator.logger, "MyDevices", "updateFoodItem", 
				//		new Object[] {"Food item " + name + " in ", "Device"+deviceID, " updated!"}, null);
				System.out.println("Server updated food item "+value.getName());
			}
			else{
				//LogUtils.logInfo(Activator.logger, "MyDevices", "addFoodItem", 
				//		new Object[] {"Food item " + name + " in ", "Device"+deviceID, " added!"}, null);
				System.out.println("Server added food item "+value.getName());
			}
			myDeviceDB[deviceID].foodItems.put(itemID, (DeviceFoodItem)dfi);
			//for (int i=0; i<myDeviceDB[deviceID].foodItems.size(); i++)
				//System.out.println("++++: "+myDeviceDB[deviceID].foodItems.get(name));
		}
	}
	
	/* Derby Database version */
	
	public void addShoppingListToDB(String shoppingURI, String shoppingName, String shoppingDate, ArrayList shoppingItems) {
		DerbyInterface di = new DerbyInterface(); 
		try{
			di.init();
			di.addShoppingListToDB(shoppingURI, shoppingName, shoppingDate, shoppingItems);
		    LogUtils.logDebug(Activator.mc, MyDevices.class, "Food and Shopping", new Object[]{"Server added/updated shopping list: "+shoppingName},null);
		}
		catch(Exception e){ e.printStackTrace(); }
	}	
	
	private void populateShoppingLists() throws SQLException{
		DerbyInterface di = new DerbyInterface(); 
		try{
			di.init();
			Vector shoppingDetails = di.getShoppingLists();
			this.shoppingLists.clear();
			this.shoppingLists = new Hashtable();
			for (int i=0; i<shoppingDetails.size(); i++){
				ResultRow rr = (ResultRow)shoppingDetails.get(i);
				String name = rr.getStringValue(new Column("SHOPPINGLIST","NAME"));
				int shoppinglistID = rr.getIntValue(new Column("SHOPPINGLIST","SHOPPINGLIST_ID"));
				int state = rr.getIntValue(new Column("SHOPPINGLIST","STATE"));
				String creation = rr.getStringValue(new Column("SHOPPINGLIST","CREATION"));
				double price = rr.getDoubleValue(new Column("SHOPPINGLIST","PRICE"));
				int users_id = rr.getIntValue(new Column("SHOPPINGLIST","USERS_ID"));

				System.out.println("*******************************");
				System.out.println("name"+" , "+creation);
				System.out.println("*******************************");
				
				ShoppingList sl = new ShoppingList(ShoppingList.MY_URI, name, creation, shoppinglistID);
				this.shoppingLists.put(""+shoppinglistID, (ShoppingList)sl);
			}
		    LogUtils.logDebug(Activator.mc, MyDevices.class, "Food and Shopping", new Object[]{"Get Shopping Lists"},null);
		}
		catch(Exception e){ e.printStackTrace(); }
	}

	public Hashtable getDBShoppingListItems(String name){
		try{
			populateShoppingListItems(name);
		}
		catch(Exception e){ e.printStackTrace(); }
		
		return this.shoppingListItems;
	}

	private void populateShoppingListItems(String shoppingListName) throws Exception{
		DerbyInterface di = new DerbyInterface(); 
		di.init();
		Vector itemsDetails = di.getShoppingListItems(shoppingListName);
		this.shoppingListItems.clear();
		this.shoppingListItems = new Hashtable();
		for (int i=0; i<itemsDetails.size(); i++){
			ResultRow rr = (ResultRow)itemsDetails.get(i);
			int code = rr.getIntValue(new Column("CODE","CODE_ID"));
			String name = rr.getStringValue(new Column("CODE","NAME"));
			String company = rr.getStringValue(new Column("CODE","COMPANY"));
			String size = rr.getStringValue(new Column("CODE","SIZE"));

			FoodItem fi = new FoodItem(FoodItem.MY_URI,code, name,size,company);
			this.shoppingListItems.put(""+i, (FoodItem)fi);
		}
	}

	public boolean removeShoppingListFromDB(String shoppingListName) {
		boolean res = true;
		DerbyInterface di = new DerbyInterface(); 
		di.init();
		try{
			Vector shoppinglistIDs = di.getShoppingListByName(shoppingListName);
			// Remove values from include
			if (shoppinglistIDs.size()>0){ 
				ResultRow rr = (ResultRow)shoppinglistIDs.get(0);
				int shoppinglistID = rr.getIntValue(new Column("SHOPPINGLIST","SHOPPINGLIST_ID"));
				Vector includeIDs = di.getIncludeListBySID(shoppinglistID);
				for (int i=0; i<includeIDs.size(); i++){
					int includeID = ((Integer)includeIDs.get(i)).intValue();
					di.deleteInclude(includeID);
				}
				di.deleteShopping(shoppinglistID);
			}			
		}
		catch(Exception e){ res=false; e.printStackTrace(); }
		return res;
	}	
	
	public Hashtable getDBCodeItems(){
		try{
			populateCodes();
		}
		catch(Exception e){}
		
		return myDeviceDB[0].codeItems;
	}

	private void populateCodes() throws Exception{
		int deviceID = 0; 
		DerbyInterface di = new DerbyInterface(); 
		di.init();
		Vector codeDetails = di.getCodes();
		myDeviceDB[deviceID].codeItems.clear();
		myDeviceDB[deviceID].codeItems = new Hashtable();
		for (int i=0; i<codeDetails.size(); i++){
			 int code = ((ResultRow)codeDetails.get(i)).getIntValue(new Column("CODE", "CODE_ID"));
			 String name = ((ResultRow)codeDetails.get(i)).getStringValue(new Column("CODE", "NAME"));
			 String company = ((ResultRow)codeDetails.get(i)).getStringValue(new Column("CODE", "COMPANY"));
			 String size = ((ResultRow)codeDetails.get(i)).getStringValue(new Column("CODE", "SIZE"));
			 FoodItem fi = new FoodItem(FoodItem.MY_URI,code, name,size,company);
			 myDeviceDB[deviceID].codeItems.put(""+code, (FoodItem)fi);
		 }
	}

	/* End of Derby Database version */
	
/*	MySQL version
	public void addShoppingListToDB(String shoppingURI, String shoppingName, String shoppingDate, ArrayList shoppingItems) {
		Connection con = null;
		try{
			con = ConnectionManager.getConnection();
			UserManager um = new UserManager(con);

			// If shoppinglist exists then update data only
			Vector criteria = new Vector();
			StringCriterion ac = new StringCriterion(new Column("food_shopping.shoppinglist","name"), new Value(shoppingName), Criterion.EQUAL);
			criteria.add(ac);
			Vector shoppingLists = um.getShoppingListByName(new Vector(), criteria);
			int found=0;
			for (int i=0; i<shoppingLists.size(); i++){
				ResultRow rr = (ResultRow)shoppingLists.get(i);
				String shoppingListName = rr.getStringValue(new Column("shoppinglist","name"));
	            System.out.println("shoppingListName="+shoppingListName);
				if (shoppingName.equals(shoppingListName))
					found = rr.getIntValue(new Column("shoppinglist","shoppinglist_id"));
			}
			
			// Insert values to shopping list
			Hashtable shoppingValues = new Hashtable();
			shoppingValues.put("name",new Value(shoppingName));
			shoppingValues.put("state",new Value(0));
			shoppingValues.put("creation",new Value(shoppingDate));
			shoppingValues.put("price",new Value(0.0));
			shoppingValues.put("users_id",new Value(1));
			long shoppingID = -1;
			if (found==0)
				shoppingID = um.addShopping(shoppingValues);
			else{
				shoppingID=found;
				um.modifyShopping(shoppingID, shoppingValues);
				
				// Delete old include entries in order to add the new entries later
				Vector includeIDs = um.getIncludeListBySID(shoppingID, new Vector(), null);
				for (int j=0; j<includeIDs.size(); j++){
					ResultRow rr = (ResultRow)includeIDs.get(j);
					long includeID = rr.getIntValue(new Column("include","include_id"));
					um.deleteInclude(includeID);
				}
			}
			shoppingValues.clear();

			// Insert values to include table
			Hashtable includeValues = new Hashtable();
			for (int i=0; i<shoppingItems.size(); i++){
				FoodItem fi = (FoodItem)shoppingItems.get(i);
				String tmp = ""+fi.getName();
				StringTokenizer st = new StringTokenizer(tmp,",");
				String codeID = "-1";
				String name = null;
				String size = null;
				String company = null;
				includeValues = new Hashtable();
				while (st.hasMoreTokens()){
					codeID = st.nextToken();
					name = st.nextToken();
					size = st.nextToken();
					company = st.nextToken();
					//System.out.println(">>>"+codeID+","+name+","+size+","+company);
					includeValues.put("code_id",new Value(Integer.parseInt(codeID)));
					includeValues.put("shoppinglist_id",new Value(shoppingID));
					includeValues.put("price",new Value(0.0));
				}
				long includeID = um.addInclude(includeValues);
				includeValues.clear();
			}
			
			System.out.println("Server added shopping list: "+shoppingName);
		}
		catch(Exception e){ e.printStackTrace(); }
		finally{ ConnectionManager.returnConnection(con); }
	}	
*/

/*  MySQL version
	public boolean removeShoppingListFromDB(String shoppingName) {
		boolean res = true;
		Connection con = null;
		try{
			con = ConnectionManager.getConnection();
			UserManager um = new UserManager(con);

			// Remove values from include
			Vector criteria = new Vector();
			StringCriterion ac = new StringCriterion(new Column("shoppinglist","name"), new Value(shoppingName), Criterion.EQUAL);
			criteria.add(ac);
			Vector shoppinglistIDs = um.getShoppingListByName(new Vector(), criteria);
			//System.out.println("itemsDetails= "+itemDetails);
			if (shoppinglistIDs.size()>0){ 
				ResultRow rr = (ResultRow)shoppinglistIDs.get(0);
				int shoppinglistID = rr.getIntValue(new Column("shoppinglist","shoppinglist_id"));
				//System.out.println("Shopping ID="+shoppinglistID);
				Vector includeIDs = um.getIncludeListBySID(shoppinglistID, new Vector(), null);
				for (int i=0; i<includeIDs.size(); i++){
					ResultRow rr2 = (ResultRow)includeIDs.get(i);
					int includeID = rr2.getIntValue(new Column("include","include_id"));
					//System.out.println("Include ID="+includeID);
					um.deleteInclude(includeID);
				}
				um.deleteShopping(shoppinglistID);
			}			
			System.out.println("Server removed shopping list: "+shoppingName);
		}
		catch(Exception e){ res=false; e.printStackTrace(); }
		finally{ ConnectionManager.returnConnection(con); }
		return res;
	}	
*/
	public void addFoodItemToDB(int deviceID, FoodItem value) {
		Connection con = null;
		try{
			con = ConnectionManager.getConnection();
			populateFoodItems(con, deviceID);

			if (myDeviceDB[deviceID].isOn) {
				String name = value.getName();
				String size = value.getSize();
				String company = value.getCompany();
				double quantity = value.getQuantity();
				String insDate = value.getInsDate();
				String expDate = value.getExpDate();
				String itemID = value.getTagID();
	
				UserManager um = new UserManager(con);
				// Check if food item is already in database
				Vector criteria = new Vector();
				ArithmeticCriterion ac = new ArithmeticCriterion(new Column("item","device_id"), new Value(1), Criterion.EQUAL);
				criteria.add(ac);
				Vector itemDetails = um.getItemByTag(itemID, new Vector(), criteria);
				if (itemDetails.size()==0){ // Adding a new item
					// Insert values to code
					Hashtable codeValues = new Hashtable();
					codeValues.put("name",new Value(name));
					codeValues.put("size",new Value(size));
					codeValues.put("company",new Value(company));
					long codeID = um.addCode(codeValues);
					codeValues.clear();
					// Insert values to rfidtag
					Hashtable rfidtagValues = new Hashtable();
					rfidtagValues.put("shortdescription",new Value(itemID));
					long rfidtagID = um.addRfidTag(rfidtagValues);
					rfidtagValues.clear();
					// Insert values to item
					Hashtable itemValues = new Hashtable();
					itemValues.put("insertion",new Value(insDate));
					itemValues.put("expiration",new Value(expDate));
					itemValues.put("quantity",new Value(quantity));
					itemValues.put("code_id",new Value(codeID));
					itemValues.put("rfidtag_id",new Value(rfidtagID));
					itemValues.put("device_id",new Value(1));				
					itemValues.put("state",new Value(1));				
					um.addItem(itemValues);
					itemValues.clear();

					//LogUtils.logInfo(Activator.logger, "MyDevices", "addFoodItem", 
					//		new Object[] {"Food item " + name + " in ", "Device"+deviceID, " added!"}, null);
					System.out.println("Server added food item "+value.getName());
				}
				else{
					ResultRow rr = (ResultRow)itemDetails.get(0);
					long item_id = rr.getIntValue(new Column("item","item_id"));
					long rfidtagID = rr.getIntValue(new Column("rfidtag","rfidtag_id"));
					long codeID = rr.getIntValue(new Column("code","code_id"));
					
					Hashtable codeValues = new Hashtable();
					codeValues.put("name",new Value(name));
					codeValues.put("size",new Value(size));
					codeValues.put("company",new Value(company));
					um.modifyCode(codeID, codeValues);
					codeValues.clear();
					// Insert values to rfidtag
					Hashtable rfidtagValues = new Hashtable();
					rfidtagValues.put("shortdescription",new Value(itemID));
					um.modifyRfidTag(rfidtagID,rfidtagValues);
					rfidtagValues.clear();
					// Insert values to item
					Hashtable itemValues = new Hashtable();
					itemValues.put("insertion",new Value(insDate));
					itemValues.put("expiration",new Value(expDate));
					itemValues.put("quantity",new Value(quantity));
					itemValues.put("code_id",new Value(codeID));
					itemValues.put("rfidtag_id",new Value(rfidtagID));
					itemValues.put("device_id",new Value(1));				
					um.modifyItem(item_id,itemValues);
					itemValues.clear();
					
					//LogUtils.logInfo(Activator.logger, "MyDevices", "updateFoodItem", 
					//		new Object[] {"Food item " + name + " in ", "Device"+deviceID, " updated!"}, null);
					System.out.println("Server updated food item "+value.getName());
				}

			}
		}
		catch(Exception e){ e.printStackTrace(); }
		finally{ ConnectionManager.returnConnection(con); }
	}	

	public void removeFoodItemFromDB(int deviceID, String key) {
		Connection con = null;
		try{
			con = ConnectionManager.getConnection();
			populateFoodItems(con, deviceID);

			if (myDeviceDB[deviceID].isOn) {
				UserManager um = new UserManager(con);
				Vector criteria = new Vector();
				ArithmeticCriterion ac = new ArithmeticCriterion(new Column("item","device_id"), new Value(1), Criterion.EQUAL);
				criteria.add(ac);
				Vector itemDetails = um.getItemByTag(key, new Vector(), criteria);
				//System.out.println("itemsDetails= "+itemDetails);
				if (itemDetails.size()>0){ 
					ResultRow rr = (ResultRow)itemDetails.get(0);
					int itemID = rr.getIntValue(new Column("item","item_id"));
					int rfID = rr.getIntValue(new Column("rfidtag","rfidtag_id"));
					//System.out.println("Item="+itemID);
					//System.out.println("RFid="+rfID);
					um.deleteItem(itemID);
					um.deleteRfidTag(rfID);
				
					if (myDeviceDB[deviceID].foodItems.containsKey(key)){
						myDeviceDB[deviceID].foodItems.remove(key);
						//LogUtils.logInfo(Activator.logger, "MyDevices", "removeFoodItem", 
						//		new Object[] {"Food item " + key + " in ", "Device"+deviceID, " removed!"}, null);
						System.out.println("Server removed food item "+ key);
					}
				}
				else{
					//LogUtils.logInfo(Activator.logger, "MyDevices", "removeFoodItem", 
					//	new Object[] {"Food item " + key + " in ", "Device"+deviceID, " failed to be removed!"}, null);
					System.out.println("Server failed to remove food item "+ key);
				}
			}
		}
		catch(Exception e){ e.printStackTrace(); }
		finally{ ConnectionManager.returnConnection(con); }		
	}
	
	public void removeFoodItem(int deviceID, String key) {
		if (myDeviceDB[deviceID].isOn) {
			if (myDeviceDB[deviceID].foodItems.containsKey(key)){
				myDeviceDB[deviceID].foodItems.remove(key);
				//LogUtils.logInfo(Activator.logger, "MyDevices", "removeFoodItem", 
				//		new Object[] {"Food item " + key + " in ", "Device"+deviceID, " removed!"}, null);
				System.out.println("Server removed food item "+ key);
			}
			else{
				//LogUtils.logInfo(Activator.logger, "MyDevices", "removeFoodItem", 
				//		new Object[] {"Food item " + key + " in ", "Device"+deviceID, " failed to be removed!"}, null);
				System.out.println("Server failed to remove food item "+ key);
			}
		}
	}

	public void removeFoodItem(int deviceID, FoodItem value) {
		if (myDeviceDB[deviceID].isOn) {
			System.out.println("Server is going to remove food item "+value.getName());
			String name = value.getName();
			myDeviceDB[deviceID].foodItems.remove(name);
			//LogUtils.logInfo(Activator.logger, "MyDevices", "removeFoodItem", 
			//		new Object[] {"Food item " + name + " in ", "Device"+deviceID, " removed!"}, null);
			System.out.println("Server removed food item "+value.getName());
		}
	}

	public void turnOff(int deviceID) {
		if (myDeviceDB[deviceID].isOn) {
			myDeviceDB[deviceID].isOn = false;
			myDeviceDB[deviceID].temp = 0;
			//LogUtils.logInfo(Activator.logger, "MyDevices", "turnOff", 
			//		new Object[] {"Device in ", myDeviceDB[deviceID].loc, " turned off!"}, null);
			for (Iterator i=listeners.iterator(); i.hasNext();)
				((DeviceStateListener) i.next()).deviceStateChanged(deviceID, myDeviceDB[deviceID].loc, false);
		}
	}
	
	public void turnOn(int deviceID) {
		//System.out.println("2. MyDevices");
		if (!myDeviceDB[deviceID].isOn) {
			myDeviceDB[deviceID].isOn = true;
			//LogUtils.logInfo(Activator.logger, "MyDevices", "turnOn", 
			//		new Object[] {"Lamp in ", myDeviceDB[deviceID].loc, " turned on!"}, null);
			for (Iterator i=listeners.iterator(); i.hasNext();)
				((DeviceStateListener) i.next()).deviceStateChanged(deviceID, myDeviceDB[deviceID].loc, true);			
		}
	}

	public void changeTemperature(int deviceID, int inputTemp) {
		System.out.println("Server is going to change temperature ...");
		if (myDeviceDB[deviceID].isOn) {
			if (inputTemp>-18 && inputTemp<20){
				myDeviceDB[deviceID].temp = inputTemp;
				//LogUtils.logInfo(Activator.logger, "MyDevices", "changeTemperature", 
				//		new Object[] {"Refrigerator Temperature", myDeviceDB[deviceID].temp, " !"}, null);

			}
		}
	}

	private void populateFoodItems(Connection con, int deviceID) throws SQLException{
		 UserManager um = new UserManager(con);
		 Vector criteria = new Vector();
		 ArithmeticCriterion ac = new ArithmeticCriterion(new Column("item","device_id"), new Value(1), Criterion.EQUAL);
		 criteria.add(ac);
		 Vector itemDetails = um.getItems(new Vector(), criteria);
		 myDeviceDB[deviceID].foodItems.clear();
		 myDeviceDB[deviceID].foodItems = new Hashtable();
		 for (int i=0; i<itemDetails.size(); i++){
			 FoodItem fi = new FoodItem();

			 String name = ((ResultRow)itemDetails.get(i)).getStringValue(new Column("code", "name"));
			 int code = ((ResultRow)itemDetails.get(i)).getIntValue(new Column("code", "code_id"));
			 String company = ((ResultRow)itemDetails.get(i)).getStringValue(new Column("code", "company"));
			 String size = ((ResultRow)itemDetails.get(i)).getStringValue(new Column("code", "size"));
			 double quantity = ((ResultRow)itemDetails.get(i)).getDoubleValue(new Column("item", "quantity"));
			 String itemID = ((ResultRow)itemDetails.get(i)).getStringValue(new Column("rfidtag", "shortdescription"));
			 String insdate = ((ResultRow)itemDetails.get(i)).getStringValue(new Column("item", "insertion"));
			 String expdate = ((ResultRow)itemDetails.get(i)).getStringValue(new Column("item", "expiration"));
			 
			 DeviceFoodItem dfi = new DeviceFoodItem(itemID, name,quantity,size,company,insdate,expdate);
			 myDeviceDB[deviceID].foodItems.put(itemID, (DeviceFoodItem)dfi);
		 }
	}

/*  MySQL version
	private void populateCodes(Connection con) throws SQLException{
		int deviceID = 0; 
		UserManager um = new UserManager(con);
		 Vector codeDetails = um.getCodes(new Vector(), null);
		 myDeviceDB[deviceID].codeItems.clear();
		 myDeviceDB[deviceID].codeItems = new Hashtable();
		 for (int i=0; i<codeDetails.size(); i++){
			 int code = ((ResultRow)codeDetails.get(i)).getIntValue(new Column("code", "code_id"));
			 String name = ((ResultRow)codeDetails.get(i)).getStringValue(new Column("code", "name"));
			 String company = ((ResultRow)codeDetails.get(i)).getStringValue(new Column("code", "company"));
			 String size = ((ResultRow)codeDetails.get(i)).getStringValue(new Column("code", "size"));
			 FoodItem fi = new FoodItem(FoodItem.MY_URI,code, name,size,company);
			 myDeviceDB[deviceID].codeItems.put(""+code, (FoodItem)fi);
		 }
	}

	private void populateShoppingListItems(Connection con, String shoppingName) throws SQLException{
		int deviceID = 0; 
		UserManager um = new UserManager(con);
		Vector criteria = new Vector();
		StringCriterion ac = new StringCriterion(new Column("shoppinglist","name"), new Value(shoppingName), Criterion.EQUAL);
		criteria.add(ac);
		
		Vector itemsDetails = um.getShoppingItems(new Vector(), criteria);
		 this.shoppingListItems.clear();
		 this.shoppingListItems = new Hashtable();
		 for (int i=0; i<itemsDetails.size(); i++){
			 int code = ((ResultRow)itemsDetails.get(i)).getIntValue(new Column("code", "code_id"));
			 String name = ((ResultRow)itemsDetails.get(i)).getStringValue(new Column("code", "name"));
			 String company = ((ResultRow)itemsDetails.get(i)).getStringValue(new Column("code", "company"));
			 String size = ((ResultRow)itemsDetails.get(i)).getStringValue(new Column("code", "size"));
			 FoodItem fi = new FoodItem(FoodItem.MY_URI,code, name,size,company);
			 this.shoppingListItems.put(""+i, (FoodItem)fi);
		 }
	}
*/
/*  MySQL
	private void populateShoppingLists(Connection con) throws SQLException{
		UserManager um = new UserManager(con);
		Vector shoppingDetails = um.getShoppingLists(new Vector(), null);
		this.shoppingLists.clear();
		this.shoppingLists = new Hashtable();
		for (int i=0; i<shoppingDetails.size(); i++){
			 int shoppinglistID = ((ResultRow)shoppingDetails.get(i)).getIntValue(new Column("shoppinglist", "shoppinglist_id"));
			 String name = ((ResultRow)shoppingDetails.get(i)).getStringValue(new Column("shoppinglist", "name"));
			 int state = ((ResultRow)shoppingDetails.get(i)).getIntValue(new Column("shoppinglist", "state"));
			 String creation = ((ResultRow)shoppingDetails.get(i)).getStringValue(new Column("shoppinglist", "creation"));
			 double price = ((ResultRow)shoppingDetails.get(i)).getDoubleValue(new Column("shoppinglist", "price"));
			 int users_id = ((ResultRow)shoppingDetails.get(i)).getIntValue(new Column("shoppinglist", "users_id"));

			 ShoppingList sl = new ShoppingList(ShoppingList.MY_URI, name, creation, shoppinglistID);
			 this.shoppingLists.put(""+shoppinglistID, (ShoppingList)sl);
		}
	}

	public Hashtable getDBCodeItems(){
		Connection con = null;
		try{
			con = ConnectionManager.getConnection();
			populateCodes(con);
		}
		catch(Exception e){}
		finally{ ConnectionManager.returnConnection(con); }		
		
		return myDeviceDB[0].codeItems;
	}
*/
/*  MySQL version
	public Hashtable getDBShoppingListItems(String name){
		Connection con = null;
		try{
			con = ConnectionManager.getConnection();
			populateShoppingListItems(con, name);
		}
		catch(Exception e){}
		finally{ ConnectionManager.returnConnection(con); }		
		
		return this.shoppingListItems;
	}
*/
	public Hashtable getDBShoppingLists(){
		//Connection con = null;
		try{
			//con = ConnectionManager.getConnection();
			//populateShoppingLists(con);
			populateShoppingLists();
		}
		catch(Exception e){}
		//finally{ ConnectionManager.returnConnection(con); }		
		
		return this.shoppingLists;
	}


	public Hashtable getDBFoodItems(int deviceID){
		Connection con = null;
		try{
			con = ConnectionManager.getConnection();
			populateFoodItems(con, deviceID);
		}
		catch(Exception e){}
		finally{ ConnectionManager.returnConnection(con); }	
		
		return myDeviceDB[deviceID].foodItems;
	}
}
