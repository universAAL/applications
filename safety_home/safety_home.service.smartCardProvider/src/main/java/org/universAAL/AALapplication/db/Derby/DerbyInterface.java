package org.universAAL.AALapplication.db.Derby;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSetMetaData;
import java.util.Date;
import java.util.Vector;

import org.universAAL.AALapplication.db.utils.Column;
import org.universAAL.AALapplication.db.utils.ResultRow;
import org.universAAL.AALapplication.db.utils.Value;
import org.universAAL.AALapplication.db.utils.criteria.Criterion;
import org.universAAL.AALapplication.db.utils.criteria.StringCriterion;
import org.universAAL.AALapplication.db.utils.statements.SelectStatement;
import org.universAAL.AALapplication.safety_home.service.smartCardProvider.Activator;
import org.universAAL.middleware.container.osgi.util.BundleConfigHome;
import org.universAAL.middleware.container.utils.LogUtils;

public class DerbyInterface {
	private static String DBNAME ="safety_home";
	private static String DBURL ="safety_home";

	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	public void init(){
  	  File confHome = new File(new BundleConfigHome("safety").getAbsolutePath());
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
			resultSet = statement.executeQuery("SELECT * FROM safety_home.smartcard");
			
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
			LogUtils.logWarn(Activator.mc,	DerbyInterface.class,	"exist",
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
		    String createTableUsers = "CREATE TABLE  "+DBNAME+".users ( " +
		    		"users_id integer PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
		    		"firstname varchar(50), " +
		    		"lastname varchar(50), " +
		    		"age integer, " +
		    		"job varchar(100), " +
		    		"email varchar(70), " +
		    		"username varchar(20), " +
		    		"password varchar(10))";
		    String createTableSmartcard = "CREATE TABLE  "+DBNAME+".smartcard ( " +
		    		"smartcard_id integer PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
		    		"shortdescription varchar(20), " +
		    		"description varchar(150))";
		    String createTableRole = "CREATE TABLE  "+DBNAME+".role ( " +
		    		"role_id integer PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
		    		"name varchar(20), " +
		    		"description varchar(100))";
    		String createTableDevice_to_users_to_smartcard = "CREATE TABLE  "+DBNAME+".device_to_users_to_smartcard ( " +
    				"device_to_users_to_smartcard_id integer PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
    				"device_id integer, " +
    				"users_id integer, " +
    				"smartcard_id integer, " +
    				"CONSTRAINT device_fk FOREIGN KEY (device_id) REFERENCES "+DBNAME+".device(device_id)," +
    				"CONSTRAINT users_fk FOREIGN KEY (users_id) REFERENCES "+DBNAME+".users(users_id)," +
    				"CONSTRAINT smartcard_fk FOREIGN KEY(smartcard_id)  REFERENCES "+DBNAME+".smartcard(smartcard_id))";
    		String createTableUsers_to_role = "CREATE TABLE  "+DBNAME+".users_to_role ( " +
    				"users_to_role_id integer PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
    				"users_id integer, " +
    				"role_id integer, " +
    				"CONSTRAINT users2_fk FOREIGN KEY (users_id) REFERENCES "+DBNAME+".users(users_id)," +
    				"CONSTRAINT role_fk FOREIGN KEY(role_id)  REFERENCES "+DBNAME+".role(role_id))";

    		//create tables   
		    statement.executeUpdate(createTableDevice);
		    statement.executeUpdate(createTableUsers);
		    statement.executeUpdate(createTableSmartcard);
		    statement.executeUpdate(createTableRole);
		    statement.executeUpdate(createTableDevice_to_users_to_smartcard);
		    statement.executeUpdate(createTableUsers_to_role);
		    
		    //insert device
		    statement.executeUpdate("insert into "+DBNAME+".device (name, location, manufacturer) values ('Omnikey 5131 CL','Front Door','HID Global')");
		    //insert users
		    statement.executeUpdate("insert into "+DBNAME+".users (firstname, lastname, age, job, email, username, password) values ('Kostas','Kalogirou',33,'senior software engineer','kalogir@certh.gr','kalogir','kalogir')");
		    statement.executeUpdate("insert into "+DBNAME+".users (firstname, lastname, age, job, email, username, password) values ('Paulos','Spanidis',31,'senior software engineer','spanidis@certh.gr','spanidis','spanidis')");
		    statement.executeUpdate("insert into "+DBNAME+".users (firstname, lastname, age, job, email, username, password) values ('Nikos','Dimokas',33,'junior software engineer','dimokas@certh.gr','dimokas','dimokas')");
		    statement.executeUpdate("insert into "+DBNAME+".users (firstname, lastname, age, job, email, username, password) values ('Taxiarchis','Tsaprounis',31,'junior software engineer','tsaprounis@certh.gr','tax','tax')");
		    statement.executeUpdate("insert into "+DBNAME+".users (firstname, lastname, age, job, email, username, password) values ('Olga','Gkaitatzi',27,'junior software engineer','ogkaitatzi@certh.gr','olga','olga')");
		    //insert smartcard
		    statement.executeUpdate("insert into "+DBNAME+".smartcard (shortdescription, description) values ('E007000012D70E90',' ')");
		    statement.executeUpdate("insert into "+DBNAME+".smartcard (shortdescription, description) values ('E007000012D70E94',' ')");
		    statement.executeUpdate("insert into "+DBNAME+".smartcard (shortdescription, description) values ('E007000012D70E93',' ')");
		    statement.executeUpdate("insert into "+DBNAME+".smartcard (shortdescription, description) values ('E007000012D70E92',' ')");
		    statement.executeUpdate("insert into "+DBNAME+".smartcard (shortdescription, description) values ('E007000012D70E91',' ')");
		    //insert role
		    statement.executeUpdate("insert into "+DBNAME+".role (name, description) values ('Assisted Person',' ')");
		    statement.executeUpdate("insert into "+DBNAME+".role (name, description) values ('Formal Caregiver',' ')");
		    statement.executeUpdate("insert into "+DBNAME+".role (name, description) values ('Informal Caregiver',' ')");
		    //insert device_to_users_to_smartcard
		    statement.executeUpdate("insert into "+DBNAME+".device_to_users_to_smartcard (device_id, users_id, smartcard_id) values (1,1,1)");
		    statement.executeUpdate("insert into "+DBNAME+".device_to_users_to_smartcard (device_id, users_id, smartcard_id) values (1,2,2)");
		    statement.executeUpdate("insert into "+DBNAME+".device_to_users_to_smartcard (device_id, users_id, smartcard_id) values (1,5,3)");
		    statement.executeUpdate("insert into "+DBNAME+".device_to_users_to_smartcard (device_id, users_id, smartcard_id) values (1,3,4)");
		    statement.executeUpdate("insert into "+DBNAME+".device_to_users_to_smartcard (device_id, users_id, smartcard_id) values (1,4,5)");
		    //insert users_to_role
		    statement.executeUpdate("insert into "+DBNAME+".users_to_role (users_id, role_id) values (5,1)");
		    statement.executeUpdate("insert into "+DBNAME+".users_to_role (users_id, role_id) values (1,3)");
		    statement.executeUpdate("insert into "+DBNAME+".users_to_role (users_id, role_id) values (2,2)");
		}
		catch (Exception e) {
			throw e;
		} finally {
			close();
		}
		
	}
	
	public Vector getUserBySmartCardUsingDerby(String tagUid) throws Exception {
        Vector result = new Vector();
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			connect = DriverManager.getConnection("jdbc:derby:"+DBURL);

	        System.out.println("************************************************");
	        System.out.println("tag uid="+tagUid);
	        System.out.println("************************************************");
			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			resultSet = statement.executeQuery("select safety_home.users.*, safety_home.role.name " +
					"from safety_home.users, safety_home.users_to_role, safety_home.role, safety_home.device_to_users_to_smartcard, safety_home.smartcard " +
					"where safety_home.users.users_id=safety_home.users_to_role.users_id and safety_home.role.role_id=safety_home.users_to_role.role_id and safety_home.users.users_id=safety_home.device_to_users_to_smartcard.users_id and " +
					"safety_home.smartcard.smartcard_id=safety_home.device_to_users_to_smartcard.smartcard_id and safety_home.smartcard.shortdescription='"+tagUid+"' and safety_home.device_to_users_to_smartcard.device_id=1");

	        while (resultSet.next()){
	            ResultRow rr = new ResultRow(resultSet);
	            result.add(rr);
	        }
	        //resultSet.close();
		}
		catch (Exception e) {
			throw e;
		} finally {
			//close();
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