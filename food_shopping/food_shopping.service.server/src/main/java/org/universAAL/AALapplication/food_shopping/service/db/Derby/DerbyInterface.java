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

package org.universAAL.AALapplication.food_shopping.service.db.Derby;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import org.universAAL.AALapplication.food_shopping.service.db.utils.Column;
import org.universAAL.AALapplication.food_shopping.service.db.utils.ResultRow;
import org.universAAL.AALapplication.food_shopping.service.server.Activator;
import org.universAAL.middleware.container.osgi.util.BundleConfigHome;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.ontology.Shopping.FoodItem;

/**
 * @author dimokas
 * 
 */
public class DerbyInterface {
	private static String DBNAME ="food_shopping";
	private static String DBURL ="food_shopping";

	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	public void init(){
  	  File confHome = new File(new BundleConfigHome("shopping").getAbsolutePath());
  	  DBURL = confHome.getAbsolutePath() + File.separator + DBNAME;
  	  DBURL = DBURL.replaceAll("\\\\", "/");
	}
	
	public boolean exist(){
		
		try {
			// load the Derby driver using the current class loader
		    Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			Connection connect = null;
			connect = DriverManager.getConnection("jdbc:derby:"+DBURL+";create=false;");
			statement = connect.createStatement();
		    statement.setQueryTimeout(30);  // set timeout to 30 sec.
		    
			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM food_shopping.device");
			
			LogUtils.logInfo(Activator.mc,	DerbyInterface.class,	"exist",
					new Object[] { "Check if database exists ..." }, null);

			if (resultSet.next()){
				LogUtils.logInfo(Activator.mc,	DerbyInterface.class,	"exist",
						new Object[] { "Database exists ..." }, null);
				return true;
			}
			else{
				LogUtils.logInfo(Activator.mc,	DerbyInterface.class,	"exist",
						new Object[] { "Database does not exist ..." }, null);
				return false;
			}
		}
		catch (Exception e) {
			LogUtils.logError(Activator.mc,	DerbyInterface.class,	"exist",
					new Object[] { "Database does not exist ..." }, null);
			return false;
		} finally {
			close();
		}
	}

	public void createDB() throws Exception{
		// load the Derby driver using the current class loader
	    Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
		try {
			Connection connect = null;
			connect = DriverManager.getConnection("jdbc:derby:"+DBURL+";create=true;");

			//connect = DriverManager.getConnection("jdbc:derby:c:/universAAL/workspaces/uAAL130/rundir/confadmin/safety/safety_home;create=true;");
			statement = connect.createStatement();
		    statement.setQueryTimeout(30);  // set timeout to 30 sec.
		    
		    String createTableDevice = "CREATE TABLE  "+DBNAME+".device ( " +
		    		"device_id integer PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
		    		"name varchar(50), " +
		    		"location varchar(50), " +
		    		"manufacturer varchar(10))";
		    String createTableCode = "CREATE TABLE  "+DBNAME+".code ( " +
		    		"code_id integer PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
		    		"name varchar(50), " +
		    		"size varchar(50), " +
		    		"company varchar(10))";
		    String createTableRfidtag = "CREATE TABLE  "+DBNAME+".rfidtag ( " +
		    		"rfidtag_id integer PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
		    		"shortdescription varchar(50), " +
		    		"description varchar(150))";
		    String createTableUsers = "CREATE TABLE  "+DBNAME+".users ( " +
		    		"users_id integer PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
		    		"firstname varchar(50), " +
		    		"lastname varchar(50), " +
		    		"age integer, " +
		    		"job varchar(100), " +
		    		"email varchar(70), " +
		    		"username varchar(20), " +
		    		"password varchar(10))";
    		String createTableShoppinglist = "CREATE TABLE  "+DBNAME+".shoppinglist ( " +
    				"shoppinglist_id integer PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
		    		"name varchar(50), " +
		    		"state integer, " +
		    		"creation DATE, " +
		    		"price float, " +
    				"users_id integer, " +
    				"CONSTRAINT users_fk FOREIGN KEY (users_id) REFERENCES "+DBNAME+".users(users_id))";
    		String createTableItem = "CREATE TABLE  "+DBNAME+".item ( " +
    				"item_id integer PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
		    		"insertion DATE, " +
		    		"expiration DATE, " +
    				"quantity integer, " +
    				"state integer, " +
    				"code_id integer, " +
    				"device_id integer, " +
    				"rfidtag_id integer, " +
    				"CONSTRAINT code_fk FOREIGN KEY (code_id) REFERENCES "+DBNAME+".code(code_id)," +
    				"CONSTRAINT device_fk FOREIGN KEY (device_id) REFERENCES "+DBNAME+".device(device_id)," +
    				"CONSTRAINT rfidtag_fk FOREIGN KEY(rfidtag_id)  REFERENCES "+DBNAME+".rfidtag(rfidtag_id))";
    		String createTableInclude = "CREATE TABLE  "+DBNAME+".include ( " +
    				"include_id integer PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
		    		"price float, " +
    				"code_id integer, " +
    				"shoppinglist_id integer, " +
    				"CONSTRAINT code2_fk FOREIGN KEY (code_id) REFERENCES "+DBNAME+".code(code_id)," +
    				"CONSTRAINT shoppinglist_fk FOREIGN KEY(shoppinglist_id)  REFERENCES "+DBNAME+".shoppinglist(shoppinglist_id))";

    		//create tables   
		    statement.executeUpdate(createTableDevice);
		    statement.executeUpdate(createTableCode);
		    statement.executeUpdate(createTableRfidtag);
		    statement.executeUpdate(createTableUsers);
		    statement.executeUpdate(createTableShoppinglist);
		    statement.executeUpdate(createTableItem);
		    statement.executeUpdate(createTableInclude);
		    
		    //insert device
		    statement.executeUpdate("insert into "+DBNAME+".device (name, location, manufacturer) values ('PulsarMX','Kitchen','metraTec')");
		    //insert code
		    statement.executeUpdate("insert into "+DBNAME+".code (name, size, company) values ('Fresh Milk','1lt','MEVGAL')");
		    statement.executeUpdate("insert into "+DBNAME+".code (name, size, company) values ('Fresh Milk','1lt','DELTA')");
		    statement.executeUpdate("insert into "+DBNAME+".code (name, size, company) values ('Fresh Milk','1lt','FAGE')");
		    statement.executeUpdate("insert into "+DBNAME+".code (name, size, company) values ('Fresh Milk','1lt','AGNO')");
		    statement.executeUpdate("insert into "+DBNAME+".code (name, size, company) values ('Fresh Milk','0.5lt','MEVGAL')");
		    statement.executeUpdate("insert into "+DBNAME+".code (name, size, company) values ('Fresh Milk','0.5lt','DELTA')");
		    statement.executeUpdate("insert into "+DBNAME+".code (name, size, company) values ('Fresh Milk','0.5lt','FAGE')");
		    statement.executeUpdate("insert into "+DBNAME+".code (name, size, company) values ('Fresh Milk','0.5lt','AGNO')");
		    statement.executeUpdate("insert into "+DBNAME+".code (name, size, company) values ('Water','1lt','BIKOS')");
		    statement.executeUpdate("insert into "+DBNAME+".code (name, size, company) values ('Water','1lt','AYRA')");
		    statement.executeUpdate("insert into "+DBNAME+".code (name, size, company) values ('Water','1lt','IOLI')");
		    statement.executeUpdate("insert into "+DBNAME+".code (name, size, company) values ('Water','1lt','KORPI')");
		    statement.executeUpdate("insert into "+DBNAME+".code (name, size, company) values ('Water','0.5lt','BIKOS')");
		    statement.executeUpdate("insert into "+DBNAME+".code (name, size, company) values ('Water','0.5lt','AYRA')");
		    statement.executeUpdate("insert into "+DBNAME+".code (name, size, company) values ('Water','0.5lt','IOLI')");
		    statement.executeUpdate("insert into "+DBNAME+".code (name, size, company) values ('Water','0.5lt','KORPI')");
		    statement.executeUpdate("insert into "+DBNAME+".code (name, size, company) values ('Egg','6 pieces','Golden Egg')");
		    statement.executeUpdate("insert into "+DBNAME+".code (name, size, company) values ('Egg','12 pieces','Golden Egg')");
		    //insert rfidtag
		    statement.executeUpdate("insert into "+DBNAME+".rfidtag (shortdescription, description) values ('E2003411B802011665038065',' ')");
		    statement.executeUpdate("insert into "+DBNAME+".rfidtag (shortdescription, description) values ('E2003411B802011665038137',' ')");
		    //insert users
		    statement.executeUpdate("insert into "+DBNAME+".users (firstname, lastname, age, job, email, username, password) values ('Kostas','Kalogirou',33,'senior software engineer','kalogir@certh.gr','kalogir','kalogir')");
		    statement.executeUpdate("insert into "+DBNAME+".users (firstname, lastname, age, job, email, username, password) values ('Paulos','Spanidis',31,'senior software engineer','spanidis@certh.gr','spanidis','spanidis')");
		    statement.executeUpdate("insert into "+DBNAME+".users (firstname, lastname, age, job, email, username, password) values ('Nikos','Dimokas',33,'junior software engineer','dimokas@certh.gr','dimokas','dimokas')");
		    statement.executeUpdate("insert into "+DBNAME+".users (firstname, lastname, age, job, email, username, password) values ('Taxiarchis','Tsaprounis',31,'junior software engineer','tsaprounis@certh.gr','tax','tax')");
		    statement.executeUpdate("insert into "+DBNAME+".users (firstname, lastname, age, job, email, username, password) values ('Olga','Gkaitatzi',27,'junior software engineer','ogkaitatzi@certh.gr','olga','olga')");
		    //insert shoppinglist
		    statement.executeUpdate("insert into "+DBNAME+".shoppinglist (name, state, creation, price, users_id) values ('first shopping list',1,'2012-03-27',100.00,1)");
		    statement.executeUpdate("insert into "+DBNAME+".shoppinglist (name, state, creation, price, users_id) values ('second shopping list',0,'2012-05-30',0.00,1)");
		    //insert item
		    statement.executeUpdate("insert into "+DBNAME+".item (insertion, expiration, quantity, code_id, device_id, rfidtag_id, state) values ('2012-03-26','2012-04-29',1,1,1,2,1)");
		    statement.executeUpdate("insert into "+DBNAME+".item (insertion, expiration, quantity, code_id, device_id, rfidtag_id, state) values ('2012-03-26','2012-05-11',1,13,1,1,1)");
		    //insert include
		    statement.executeUpdate("insert into "+DBNAME+".include (price, code_id, shoppinglist_id) values (0.95,1,1)");
		    statement.executeUpdate("insert into "+DBNAME+".include (price, code_id, shoppinglist_id) values (0.95,1,1)");
		    statement.executeUpdate("insert into "+DBNAME+".include (price, code_id, shoppinglist_id) values (0.50,13,1)");
		    statement.executeUpdate("insert into "+DBNAME+".include (price, code_id, shoppinglist_id) values (0.50,13,1)");
		    statement.executeUpdate("insert into "+DBNAME+".include (price, code_id, shoppinglist_id) values (0.50,13,1)");
		    statement.executeUpdate("insert into "+DBNAME+".include (price, code_id, shoppinglist_id) values (0.50,13,1)");
		    statement.executeUpdate("insert into "+DBNAME+".include (price, code_id, shoppinglist_id) values (0.00,2,2)");
		    statement.executeUpdate("insert into "+DBNAME+".include (price, code_id, shoppinglist_id) values (0.00,9,2)");
		    statement.executeUpdate("insert into "+DBNAME+".include (price, code_id, shoppinglist_id) values (0.00,9,2)");
		    statement.executeUpdate("insert into "+DBNAME+".include (price, code_id, shoppinglist_id) values (0.00,9,2)");
		    statement.executeUpdate("insert into "+DBNAME+".include (price, code_id, shoppinglist_id) values (0.00,10,2)");
		    statement.executeUpdate("insert into "+DBNAME+".include (price, code_id, shoppinglist_id) values (0.00,10,2)");
		    statement.executeUpdate("insert into "+DBNAME+".include (price, code_id, shoppinglist_id) values (0.00,18,2)");
		    statement.executeUpdate("insert into "+DBNAME+".include (price, code_id, shoppinglist_id) values (0.00,1,2)");
		}
		catch (Exception e) {
			throw e;
		} finally {
			close();
		}
		
	}
	
	public Vector getItemByTagUsingDerby(String tagUid) throws Exception {
        Vector result = new Vector();
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			connect = DriverManager.getConnection("jdbc:derby:"+DBURL);

	        statement = connect.createStatement();
			resultSet = statement.executeQuery("select food_shopping.item.*, food_shopping.code.*, food_shopping.rfidtag.* " +
					"from food_shopping.item, food_shopping.code, food_shopping.rfidtag " +
					"where food_shopping.item.code_id=food_shopping.code.code_id and food_shopping.item.rfidtag_id=food_shopping.rfidtag.rfidtag_id and " +
					"food_shopping.rfidtag.shortdescription='"+tagUid+"' and food_shopping.item.device_id=1");

	        while (resultSet.next()){
	            ResultRow rr = new ResultRow(resultSet);
	            result.add(rr);
	        }
		}
		catch (Exception e) {
			throw e;
		} finally {
			//close();
		}
        
        return result;
	}

	public int modifyItemState(int itemID, int newstate) throws Exception {
		int res=0;
		Vector result = new Vector();
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			connect = DriverManager.getConnection("jdbc:derby:"+DBURL);

			statement = connect.createStatement();
			res = statement.executeUpdate("update food_shopping.item " +
					"set state=" + newstate + 
					" where food_shopping.item.item_id=" + itemID);

		}
		catch (Exception e) {
			throw e;
		} finally {
			//close();
		}
        
        return res;
	}

	public int addShopping(String name, int state, String creation, double price, int users_id) throws Exception{
		int res=0;
		try {
		    Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			Connection connect = null;
			connect = DriverManager.getConnection("jdbc:derby:"+DBURL+";create=true;");

			//connect = DriverManager.getConnection("jdbc:derby:c:/universAAL/workspaces/uAAL130/rundir/confadmin/safety/safety_home;create=true;");
			statement = connect.createStatement();
			System.out.println(">>> creation="+creation);
		    int result = statement.executeUpdate("insert into "+DBNAME+".shoppinglist (name, state, creation, price, users_id) values ('"+name+"',"+state+",'"+creation+"',"+price+","+users_id+")");
		    statement.close();
		    
	        Statement statement2 = connect.createStatement();
			resultSet = statement2.executeQuery("select food_shopping.shoppinglist.* " +
					"from food_shopping.shoppinglist " +
					"where food_shopping.shoppinglist.name='"+name+"'");

			while (resultSet.next()){
	            String shoppingListName = resultSet.getString("name");
	            System.out.println("shoppingListName="+shoppingListName);
				if (name.equals(shoppingListName))
					res = resultSet.getInt("shoppinglist_id");
	        }
			statement2.close();
			resultSet.close();
		}
		catch (Exception e) {
			throw e;
		}
		
		return res;
	}
	
	public int modifyShopping(int shoppinglist_id, String name, int state, String creation, double price, int users_id) throws Exception{
		int res=0;
		try {
		    Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			Connection connect = null;
			connect = DriverManager.getConnection("jdbc:derby:"+DBURL+";create=true;");
			statement = connect.createStatement();
		    res = statement.executeUpdate("UPDATE food_shopping.shoppinglist SET name='"+name+"', state="+state+", creation='"+creation+"', price="+price+", users_id="+users_id+" WHERE shoppinglist_id="+shoppinglist_id);
		    statement.close();
		}
		catch (Exception e) {
			throw e;
		}
		
		return res;
	}
	
	public Vector getIncludeListBySID(int shoppingID) throws Exception {
        Vector result = new Vector();
        
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
        Connection connect = null;
        connect = DriverManager.getConnection("jdbc:derby:"+DBURL+";create=true;");

        statement = connect.createStatement();
		resultSet = statement.executeQuery("select food_shopping.include.* " +
				"from food_shopping.include " +
				"where food_shopping.include.shoppinglist_id="+shoppingID);
		
        while (resultSet.next()){
            int shoppingListID = resultSet.getInt("shoppinglist_id");
            int includeID = resultSet.getInt("include_id");
            result.add(new Integer(includeID));
        }
        
        return result;
	}
	
	public int deleteInclude(int includeID) throws Exception{
		int res=0;
	    Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
	    Connection connect = null;
	    connect = DriverManager.getConnection("jdbc:derby:"+DBURL+";create=true;");
	    //System.out.println("############ DELETE FROM INCLUDE TABLE ############");
	    statement = connect.createStatement();
	    res = statement.executeUpdate("DELETE FROM food_shopping.include WHERE food_shopping.include.include_id="+includeID);
	    //System.out.println("res="+res);
	    statement.close();
		
		return res;
	}
	
	public int addInclude(float price, int codeID, int shoppingID) throws Exception{
		int res=0;
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
		Connection connect = null;
		connect = DriverManager.getConnection("jdbc:derby:"+DBURL+";create=true;");

		statement = connect.createStatement();
		int result = statement.executeUpdate("insert into "+DBNAME+".include (price, code_id, shoppinglist_id) values ("+price+","+codeID+","+shoppingID+")");
		statement.close();
		    
		resultSet.close();
		return res;
	}

	public void printItems(int shoppingID) throws Exception {
        Vector result = new Vector();
        
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
        Connection connect = null;
        connect = DriverManager.getConnection("jdbc:derby:"+DBURL+";create=true;");

        statement = connect.createStatement();
		resultSet = statement.executeQuery("select food_shopping.include.*, food_shopping.code.* " +
				"from food_shopping.include, food_shopping.code " +
				"where food_shopping.include.code_id=food_shopping.code.code_id and food_shopping.include.shoppinglist_id="+shoppingID);
		int i=1;
        while (resultSet.next()){
            int codeID = resultSet.getInt("code_id");
            String codeName = resultSet.getString("name");
            String size = resultSet.getString("size");
            String company = resultSet.getString("company");
    	    LogUtils.logDebug(Activator.mc, DerbyInterface.class, "Food and Shopping", new Object[]{
    	    	"item"+i+": "+codeName+","+size+","+company+","+codeID},null);
            i++;
        }
	}

	public void addShoppingListToDB(String shoppingURI, String shoppingName, String shoppingDate, ArrayList shoppingItems) {
		Connection connect = null;
		try{
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			connect = DriverManager.getConnection("jdbc:derby:"+DBURL);
			// If shoppinglist exists then update data only
	        statement = connect.createStatement();
			resultSet = statement.executeQuery("select food_shopping.shoppinglist.* " +
					"from food_shopping.shoppinglist " +
					"where food_shopping.shoppinglist.name='"+shoppingName+"'");
			int found=0;
	        while (resultSet.next()){
	            String shoppingListName = resultSet.getString("name");
				if (shoppingName.equals(shoppingListName))
					found = resultSet.getInt("shoppinglist_id");
	        }

			// Insert values to shopping list
			int shoppingID = -1;
			if (found==0)
				shoppingID = this.addShopping(shoppingName, 0, shoppingDate, 0, 1);
			else{
				shoppingID=found;
				System.out.println("shoppingID==="+shoppingID);
				this.modifyShopping(shoppingID, shoppingName, 0, shoppingDate, 0, 1);
				// Delete old include entries in order to add the new entries later
				Vector includeIDs = this.getIncludeListBySID(shoppingID);
				for (int j=0; j<includeIDs.size(); j++){
					int includeID = ((Integer)includeIDs.get(j)).intValue();
					this.deleteInclude(includeID);
				}
			}

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
				while (st.hasMoreTokens()){
					codeID = st.nextToken();
					name = st.nextToken();
					size = st.nextToken();
					company = st.nextToken();
					System.out.println(">>>"+codeID+","+name+","+size+","+company);
				}
				int includeID = this.addInclude(0,Integer.parseInt(codeID),shoppingID);
			}
    	    LogUtils.logDebug(Activator.mc, DerbyInterface.class, "Food and Shopping", new Object[]{
    	    	"Shopping List "+shoppingName+" with ID "+shoppingID+" contains the following items:"},null);
			this.printItems(shoppingID);
		}
		catch(Exception e){ e.printStackTrace(); }
	}	

	public Vector getShoppingLists() throws Exception {
        Vector result = new Vector();
        
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
        Connection connect = null;
        connect = DriverManager.getConnection("jdbc:derby:"+DBURL+";create=true;");

        statement = connect.createStatement();
		resultSet = statement.executeQuery("select food_shopping.shoppinglist.* " +
				"from food_shopping.shoppinglist");
		
        while (resultSet.next()){
            ResultRow rr = new ResultRow(resultSet);
            result.add(rr);
        }
        
        return result;
	}

	public Vector getShoppingListByName(String shoppingListName) throws Exception {
        Vector result = new Vector();
        
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
        Connection connect = null;
        connect = DriverManager.getConnection("jdbc:derby:"+DBURL+";create=true;");

        statement = connect.createStatement();
		resultSet = statement.executeQuery("select food_shopping.shoppinglist.* " +
				"from food_shopping.shoppinglist where food_shopping.shoppinglist.name='"+shoppingListName+"'");
		
        while (resultSet.next()){
            ResultRow rr = new ResultRow(resultSet);
            result.add(rr);
        }
        
        return result;
	}

	public Vector getShoppingListItems(String name) throws Exception {
        Vector result = new Vector();

        Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
        Connection connect = null;
        connect = DriverManager.getConnection("jdbc:derby:"+DBURL+";create=true;");
        statement = connect.createStatement();
        resultSet = statement.executeQuery("select food_shopping.shoppinglist.*, food_shopping.code.*, food_shopping.include.* " +
					"from food_shopping.shoppinglist, food_shopping.code, food_shopping.include "+
					"where food_shopping.shoppinglist.shoppinglist_id=food_shopping.include.shoppinglist_id and food_shopping.include.code_id=food_shopping.code.code_id and food_shopping.shoppinglist.name='"+name+"' "+
					"order by food_shopping.code.code_id DESC");
			
        while (resultSet.next()){
        	ResultRow rr = new ResultRow(resultSet);
        	System.out.println("code name="+rr.getStringValue(new Column("CODE","NAME")));
        	result.add(rr);
        }

        return result;
	}

	public int deleteShopping(int shoppinglistID) throws Exception{
		int res=0;
	    Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
	    Connection connect = null;
	    connect = DriverManager.getConnection("jdbc:derby:"+DBURL+";create=true;");
	    //System.out.println("############ DELETE FROM SHOPPING LIST TABLE ############");
	    statement = connect.createStatement();
	    res = statement.executeUpdate("DELETE FROM food_shopping.shoppinglist WHERE food_shopping.shoppinglist.shoppinglist_id="+shoppinglistID);
	    //System.out.println("res="+res);
	    statement.close();
		
		return res;
	}

	public Vector getCodes() throws Exception {
        Vector result = new Vector();

        Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
        Connection connect = null;
        connect = DriverManager.getConnection("jdbc:derby:"+DBURL+";create=true;");
        statement = connect.createStatement();
        resultSet = statement.executeQuery("select food_shopping.code.* " +
					"from food_shopping.code");
			
        System.out.println("#### NUTRITION PRODUCTS ####");
        while (resultSet.next()){
        	ResultRow rr = new ResultRow(resultSet);
        	result.add(rr);
        }

        return result;
	}

	public Vector getItemByStateUsingDerby(int state) throws Exception {
        Vector result = new Vector();

        Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
        Connection connect = null;
        connect = DriverManager.getConnection("jdbc:derby:"+DBURL+";create=true;");
        statement = connect.createStatement();
        resultSet = statement.executeQuery("select food_shopping.item.*, food_shopping.code.*, food_shopping.rfidtag.* " +
					"from food_shopping.item, food_shopping.code, food_shopping.rfidtag "+
					"where food_shopping.item.code_id=food_shopping.code.code_id and food_shopping.item.rfidtag_id=food_shopping.rfidtag.rfidtag_id "+
					"and food_shopping.item.state=1 " +
					"order by food_shopping.code.code_id DESC");
			
        while (resultSet.next()){
        	ResultRow rr = new ResultRow(resultSet);
        	result.add(rr);
        }

        return result;
	}

	private void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}
}