package org.universAAL.AALapplication.safety_home.service.uiclient.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSetMetaData;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import org.universAAL.AALapplication.safety_home.service.uiclient.osgi.Activator;
import org.universAAL.middleware.container.osgi.util.BundleConfigHome;
import org.universAAL.middleware.container.utils.LogUtils;

public class DerbyInterface {
	private static String DBNAME ="safety_home_client";
	private static String DBURL ="safety_home_client";

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
			resultSet = statement.executeQuery("SELECT * FROM safety_home_client.users");
			
			if (resultSet.next()){
				LogUtils.logInfo(Activator.getModuleContext(),	DerbyInterface.class,	"exist",
						new Object[] { "Database exists ..." }, null);
				return true;
			}
			else{
				LogUtils.logInfo(Activator.getModuleContext(),	DerbyInterface.class,	"exist",
						new Object[] { "Database does not exist ..." }, null);
				return false;
			}
		}
		catch (Exception e) {
			LogUtils.logWarn(Activator.getModuleContext(),	DerbyInterface.class,	"exist",
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
		    
		    String createTableUsers = "CREATE TABLE  "+DBNAME+".users ( " +
		    		"users_id integer PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
		    		"firstname varchar(50) NOT NULL, " +
		    		"lastname varchar(50) NOT NULL, " +
		    		"age integer, " +
		    		"job varchar(100) , " +
		    		"email varchar(70), " +
		    		"username varchar(20) NOT NULL, " +
		    		"password varchar(10) NOT NULL)";
		    String createTableRole = "CREATE TABLE  "+DBNAME+".role ( " +
		    		"role_id integer PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
		    		"name varchar(20) NOT NULL, " +
		    		"description varchar(100))";
    		String createTableUsers_to_role = "CREATE TABLE  "+DBNAME+".users_to_role ( " +
    				"users_to_role_id integer PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
    				"users_id integer, " +
    				"role_id integer, " +
    				"CONSTRAINT users2_fk FOREIGN KEY (users_id) REFERENCES "+DBNAME+".users(users_id)," +
    				"CONSTRAINT role_fk FOREIGN KEY(role_id)  REFERENCES "+DBNAME+".role(role_id))";
    		String createTableNotification = "CREATE TABLE  "+DBNAME+".notification ( " +
    				"notification_id integer PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
    				"message varchar(100) NOT NULL, " +
    				"creation DATE, " +
    				"creationtime TIME, " +
    				"state integer, " +
    				"category integer, " +
    				"users_id integer, " +
    				"CONSTRAINT usernotification_fk FOREIGN KEY (users_id) REFERENCES "+DBNAME+".users(users_id))";

    		
    		//create tables   
		    statement.executeUpdate(createTableUsers);
		    statement.executeUpdate(createTableRole);
		    statement.executeUpdate(createTableUsers_to_role);
		    statement.executeUpdate(createTableNotification);
		    
		    //insert users
		    statement.executeUpdate("insert into "+DBNAME+".users (firstname, lastname, age, job, email, username, password) values ('Kostas','Kalogirou',33,'senior software engineer','kalogir@certh.gr','kalogir','kalogir')");
		    statement.executeUpdate("insert into "+DBNAME+".users (firstname, lastname, age, job, email, username, password) values ('Paulos','Spanidis',31,'senior software engineer','spanidis@certh.gr','spanidis','spanidis')");
		    statement.executeUpdate("insert into "+DBNAME+".users (firstname, lastname, age, job, email, username, password) values ('Nikos','Dimokas',33,'junior software engineer','dimokas@certh.gr','dimokas','dimokas')");
		    statement.executeUpdate("insert into "+DBNAME+".users (firstname, lastname, age, job, email, username, password) values ('Taxiarchis','Tsaprounis',31,'junior software engineer','tsaprounis@certh.gr','tax','tax')");
		    statement.executeUpdate("insert into "+DBNAME+".users (firstname, lastname, age, job, email, username, password) values ('Olga','Gkaitatzi',27,'junior software engineer','ogkaitatzi@certh.gr','olga','olga')");
		    //insert role
		    statement.executeUpdate("insert into "+DBNAME+".role (name, description) values ('Assisted Person',' ')");
		    statement.executeUpdate("insert into "+DBNAME+".role (name, description) values ('Formal Caregiver',' ')");
		    statement.executeUpdate("insert into "+DBNAME+".role (name, description) values ('Informal Caregiver',' ')");
		    //insert users_to_role
		    statement.executeUpdate("insert into "+DBNAME+".users_to_role (users_id, role_id) values (5,1)");
		    statement.executeUpdate("insert into "+DBNAME+".users_to_role (users_id, role_id) values (1,3)");
		    statement.executeUpdate("insert into "+DBNAME+".users_to_role (users_id, role_id) values (2,2)");
		    //insert notification
		    statement.executeUpdate("insert into "+DBNAME+".notification (message, creation, creationtime, state, category, users_id) values ('Window is open','2013-09-26', '13:03:28', 0, 1, 5)");
		    
		    
		}
		catch (Exception e) {
			throw e;
		} finally {
			close();
		}
		
	}

	public int addNotification(String message, int state, int category) throws Exception{
		int res=0;
		try {
			Calendar javaCalendar = null;
			String currentDate = "";
			String currentTime = "";
			javaCalendar = Calendar.getInstance();
			
			currentDate = javaCalendar.get(Calendar.YEAR) + "-" + (javaCalendar.get(Calendar.MONTH) + 1) + "-" + javaCalendar.get(Calendar.DATE);
			currentTime = javaCalendar.get(Calendar.HOUR_OF_DAY) + ":" + (javaCalendar.get(Calendar.MINUTE)) + ":" + javaCalendar.get(Calendar.SECOND);
			
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			Connection connect = null;
			connect = DriverManager.getConnection("jdbc:derby:"+DBURL+";create=true;");
			
			statement = connect.createStatement();
		    res = statement.executeUpdate("insert into "+DBNAME+".notification (message, creation, creationtime, state, category, users_id) values ('"+message+"','"+currentDate+"','"+currentTime+"',"+0+","+category+","+5+")");
		    statement.close();
		    
	        statement = connect.createStatement();
			resultSet = statement.executeQuery("select safety_home_client.notification.notification_id " +
					"from safety_home_client.notification");

			while (resultSet.next()){
				res = resultSet.getInt("notification_id");
				System.out.println("notificationID="+res);
	        }
			statement.close();
			resultSet.close();
		}
		catch (Exception e) {
			throw e;
		}
		
		return res;
	}

	public int modifyNotificationState(int notificationID) throws Exception {
		int res=0;
		Vector result = new Vector();
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			connect = DriverManager.getConnection("jdbc:derby:"+DBURL);
			System.out.println("NotificationID="+notificationID);
			statement = connect.createStatement();
			res = statement.executeUpdate("update safety_home_client.notification " +
					"set state=" + 1 + 
					" where safety_home_client.notification.notification_id=" + notificationID);

		}
		catch (Exception e) {
			throw e;
		} finally {
			//close();
		}
        
        return res;
	}

	public int modifyAllNotificationState() throws Exception {
		int res=0;
		Vector result = new Vector();
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			connect = DriverManager.getConnection("jdbc:derby:"+DBURL);
			statement = connect.createStatement();
			res = statement.executeUpdate("update safety_home_client.notification " +
					"set state=" + 1);

		}
		catch (Exception e) {
			throw e;
		} finally {
			//close();
		}
        
        return res;
	}

	public Hashtable getUnReadNotifications() throws Exception {
        Hashtable result = new Hashtable();
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			connect = DriverManager.getConnection("jdbc:derby:"+DBURL);

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			resultSet = statement.executeQuery("select safety_home_client.notification.* " +
					"from safety_home_client.notification " +
					"where safety_home_client.notification.state=0");

	        while (resultSet.next()){
				int notificationID = resultSet.getInt("notification_id");
				String message = resultSet.getString("message");
				String creation = resultSet.getString("creation");
				String creationTime = resultSet.getString("creationtime");
				System.out.println(message + "\t" + creation);
				Vector tmp = new Vector();
				tmp.add(message);
				tmp.add(creation);
				tmp.add(creationTime);
				
				result.put(new Integer(notificationID), tmp);
	        }
	        //resultSet.close();
		}
		catch (Exception e) {
			throw e;
		} finally {
			close();
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