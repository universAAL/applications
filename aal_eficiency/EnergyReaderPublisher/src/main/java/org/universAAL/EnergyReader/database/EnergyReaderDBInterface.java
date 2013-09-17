/*
	Copyright 2011-2012 TSB, http://www.tsbtecnologias.es
	TSB - Tecnologías para la Salud y el Bienestar
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.universAAL.EnergyReader.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.universAAL.EnergyReader.model.ChallengeModel;
import org.universAAL.EnergyReader.utils.Setup;
import org.universAAL.middleware.container.osgi.util.BundleConfigHome;

public class EnergyReaderDBInterface {
	private Setup s = new Setup();
	private static final String DBNAME ="EnergyReader";

	private Connection connect = null;
	private Statement statement = null;
	private Statement statement2 = null;
	private Statement statement3 = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private ResultSet resultSet2 = null;
	private ResultSet resultSet3 = null;
	
	public void createDB() throws Exception{
		// load the derby-JDBC driver using the current class loader
	    //Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
		new org.apache.derby.jdbc.EmbeddedDriver();
	    String configFolderPath = System.getProperty(
                BundleConfigHome.uAAL_CONF_ROOT_DIR, System
                        .getProperty("user.dir"));
	    if(configFolderPath.substring(configFolderPath.length() - 1)!="/"){
			configFolderPath+="/";
		}
        System.setProperty("derby.system.home", configFolderPath);
		try {
			Connection connect = null;
			String dbURL = "jdbc:derby:" + configFolderPath
                    + "/EnergyReaderPublisher/EnergyReaderDB;create=true";
			connect = DriverManager.getConnection(dbURL);
			statement = connect.createStatement();
		    statement.setQueryTimeout(30);  // set timeout to 30 sec.
		    
		  
		    String createTableDeviceType = "CREATE TABLE  "+DBNAME+".devicetype ( id integer PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), Brand  varchar(25),Type   varchar(25))";
    		String createTableDevice = "CREATE TABLE  "+DBNAME+".device ( id integer PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),name varchar(25), type integer, location  varchar(50),"+
    				"CONSTRAINT type_fk FOREIGN KEY (type) REFERENCES "+DBNAME+".devicetype(id))";
    		String createTableDaily = "CREATE TABLE  "+DBNAME+".dailymeasurements ( id integer PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), device integer, measurement  integer,"+
    				"CONSTRAINT device_fk FOREIGN KEY (device)  REFERENCES "+DBNAME+".device(id))";
    		String createTableHistoric ="CREATE TABLE  "+DBNAME+".historicmeasurements (id integer PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), device integer, measurement  integer, fecha DATE,"+
    				"CONSTRAINT device_his_fk FOREIGN KEY (device) REFERENCES "+DBNAME+".device(id))";
    		String createTableChallenge = "CREATE TABLE  "+DBNAME+".challenge (id integer PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), description varchar(255),goal varchar(20),"+
    				"startdate date, isActive boolean,totalscore integer,currentscore integer, originalmeassurement integer, orden integer)";
    		String createTableChallengeDevices ="CREATE TABLE  "+DBNAME+".challengedevice (id integer PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), challenge integer, device integer,"+
    				"CONSTRAINT challenge_fk FOREIGN KEY (device) REFERENCES "+DBNAME+".device(id), CONSTRAINT device_ch_fk FOREIGN KEY (challenge) REFERENCES "+DBNAME+".challenge(id))";
            
    		//create tables   
		    statement.executeUpdate(createTableDeviceType);
		    statement.executeUpdate(createTableDevice);
		    statement.executeUpdate(createTableDaily);
		    statement.executeUpdate(createTableHistoric);
		    statement.executeUpdate(createTableChallenge);
		    statement.executeUpdate(createTableChallengeDevices);
		    
		    //insert device types
		    statement.executeUpdate("insert into "+DBNAME+".devicetype (brand, type) values ('AeonLabs','Clamp')");
		    statement.executeUpdate("insert into "+DBNAME+".devicetype (brand, type) values ('Everspring','Plug')");
		    
		    //insert devices
		    statement.executeUpdate("insert into "+DBNAME+".device (name, type, location) values ('VeraLite',2, 'Armario')");
		    statement.executeUpdate("insert into "+DBNAME+".device (name, type, location) values ('Pantalla Serdula',2, 'Mesa')");
		    statement.executeUpdate("insert into "+DBNAME+".device (name, type, location) values ('Miguel Angel',2, 'Mesa')");
		    statement.executeUpdate("insert into "+DBNAME+".device (name, type, location) values ('Enchufe Armario',2, 'Armario')");
		    
		    Date date = new Date();
		    String  d = String.valueOf(new java.sql.Date(date.getTime()));
		    //insert challenges
		    String sql = "insert into "+DBNAME+".challenge (description ,goal, startdate, isActive,totalscore ,currentscore, originalmeassurement, orden) values ('Save 10% in lighting', '10 %','"+d+"', true, 120,0,352,1)";
		    statement.executeUpdate(sql);
		    statement.executeUpdate("insert into "+DBNAME+".challenge (description ,goal, isActive,totalscore ,currentscore, originalmeassurement, orden) values ('Save 25% in big consumptions', '25 %', true, 120,0,352,2)");
		    
		    //insert challenge devices
		    statement.executeUpdate("insert into "+DBNAME+".challengedevice (challenge, device) values (1,1)");
		    statement.executeUpdate("insert into "+DBNAME+".challengedevice (challenge, device)values (1,2)");
		    statement.executeUpdate("insert into "+DBNAME+".challengedevice (challenge, device)values (1,3)");
		    statement.executeUpdate("insert into "+DBNAME+".challengedevice (challenge, device)values (1,4)");
		    statement.executeUpdate("insert into "+DBNAME+".challengedevice (challenge, device)values (2,1)");
		    statement.executeUpdate("insert into "+DBNAME+".challengedevice (challenge, device)values (2,2)");
		    statement.executeUpdate("insert into "+DBNAME+".challengedevice (challenge, device)values (2,3)");
		    statement.executeUpdate("insert into "+DBNAME+".challengedevice (challenge, device)values (2,4)");
		    
		    resultSet =  statement.executeQuery("Select * from "+DBNAME+".challenge");
		    
		    while (resultSet.next()){
		    	System.out.print("CHALLENGE\n");
		    	System.out.print("Description: "+resultSet.getString(2)+"\n");
		    	System.out.print("Goal: "+resultSet.getString(3)+"\n");
		    	Date ds = resultSet.getDate(4);
		    	if (ds!=null)
		    		System.out.print("StartDate: "+ds.getDate()+"\n");
		    	System.out.print("TotalScore: "+resultSet.getString(6)+"\n");
		    	System.out.print("CurrentScore: "+resultSet.getString(7)+"\n");
		    	System.out.print("Original: "+resultSet.getString(8)+"\n");
		    	System.out.print("Order: "+resultSet.getString(9)+"\n");
		    }
		    
		    
		  //  }
		    
		}
		catch (Exception e) {
			throw e;
		} finally {
			close();
		}
		
	}
	
	public void insertDailyMeasurement(String device, int measurement)throws Exception{
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			 String configFolderPath = System.getProperty(
		                BundleConfigHome.uAAL_CONF_ROOT_DIR, System
		                        .getProperty("user.dir"));
			 if(configFolderPath.substring(configFolderPath.length() - 1)!="/"){
					configFolderPath+="/";
				}
		        System.setProperty("derby.system.home", configFolderPath);
		        String dbURL = "jdbc:derby:" + configFolderPath
	                    + "/EnergyReaderPublisher/EnergyReaderDB;create=true";
				connect = DriverManager.getConnection(dbURL);

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			
				resultSet = statement
						.executeQuery("select * from "+DBNAME+".device where Name='"+device+"'");
				if (resultSet.next()){
				int deviceId = resultSet.getInt("id");
				preparedStatement = connect
						.prepareStatement("insert into  "+DBNAME+".dailymeasurements (device, measurement) values ("+deviceId+","+measurement+")");
				preparedStatement.executeUpdate();
				}
		}
		catch (Exception e) {
			throw e;
		} finally {
			close();
		}
		
		
	}
	
	public void insertHistoricMeasurement(String device, int measurement)throws Exception{
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			String configFolderPath = System.getProperty(
	                BundleConfigHome.uAAL_CONF_ROOT_DIR, System
	                        .getProperty("user.dir"));
			if(configFolderPath.substring(configFolderPath.length() - 1)!="/"){
				configFolderPath+="/";
			}
	        System.setProperty("derby.system.home", configFolderPath);
			// Setup the connection with the DB
	        String dbURL = "jdbc:derby:" + configFolderPath
                    + "/EnergyReaderPublisher/EnergyReaderDB;create=true";
			connect = DriverManager.getConnection(dbURL);

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			
				resultSet = statement
						.executeQuery("select * from "+DBNAME+".device where Name='"+device+"'");
				if (resultSet.next()){
				int deviceId = resultSet.getInt("id");
				Date date = new Date();
				preparedStatement = connect
						.prepareStatement("insert into  "+DBNAME+".historicmeasurements (device, measurement, fecha) values ("+deviceId+","+measurement+",'"+String.valueOf(new java.sql.Date(date.getTime()))+"')");
				preparedStatement.executeUpdate();
				}
		}
		catch (Exception e) {
			throw e;
		} finally {
			close();
		}
		
		
	}
	
	
	public int totalMeasurements(String device) throws Exception{
		int total=0;
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			String configFolderPath = System.getProperty(
	                BundleConfigHome.uAAL_CONF_ROOT_DIR, System
	                        .getProperty("user.dir"));
			if(configFolderPath.substring(configFolderPath.length() - 1)!="/"){
				configFolderPath+="/";
			}
	        System.setProperty("derby.system.home", configFolderPath);
			// Setup the connection with the DB
	        String dbURL = "jdbc:derby:" + configFolderPath
                    + "/EnergyReaderPublisher/EnergyReaderDB;create=true";
			connect = DriverManager.getConnection(dbURL);

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			resultSet = statement
					.executeQuery("select * from "+DBNAME+".device where Name='"+device+"'");
			if (resultSet.next()){
				int deviceId = resultSet.getInt("id");
				resultSet2 = statement.executeQuery("select * from "+DBNAME+".dailymeasurements where Device='"+deviceId+"'");
				while (resultSet2.next()){
					total+=resultSet2.getInt("Measurement");
				}
				return total;
			}
			
		}
		catch (Exception e) {
			throw e;
		} finally {
			close();
			return total;
		}	
	}
	
	public void deleteMeasurements() throws Exception{
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			String configFolderPath = System.getProperty(
	                BundleConfigHome.uAAL_CONF_ROOT_DIR, System
	                        .getProperty("user.dir"));
			if(configFolderPath.substring(configFolderPath.length() - 1)!="/"){
				configFolderPath+="/";
			}
	        System.setProperty("derby.system.home", configFolderPath);
	        
	        String dbURL = "jdbc:derby:" + configFolderPath
                    + "/EnergyReaderPublisher/EnergyReaderDB;create=true";
			connect = DriverManager.getConnection(dbURL);

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			statement.executeUpdate("delete from "+DBNAME+".dailymeasurements");
		}
		catch (Exception e) {
			throw e;
		} finally {
			close();
		}
		
	}
	
	public ChallengeModel getActiveChallenges() throws Exception{
		ChallengeModel challenge = new ChallengeModel();
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			String configFolderPath = System.getProperty(
	                BundleConfigHome.uAAL_CONF_ROOT_DIR, System
	                        .getProperty("user.dir"));
			if(configFolderPath.substring(configFolderPath.length() - 1)!="/"){
				configFolderPath+="/";
			}
	        System.setProperty("derby.system.home", configFolderPath);
	        
	        String dbURL = "jdbc:derby:" + configFolderPath
                    + "/EnergyReaderPublisher/EnergyReaderDB;create=true";
			connect = DriverManager.getConnection(dbURL);
			
			int days = s.getElectricityChallengeDays();
			
			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			statement2 = connect.createStatement();
			statement3 = connect.createStatement();
			resultSet = statement
						.executeQuery("select * from "+DBNAME+".challenge where IsActive = true");
				while(resultSet.next()){
					ChallengeModel c = setChallengeValues(resultSet);
					
					Date d = new Date();
					int dif = (int) ((d.getTime()-c.getStartdate().getTime())/(24 * 60 * 60 * 1000));
						//If it's been less than week since the challenge started, its the current one
						if (dif<days){
							return c;
						}
						//If it's been a week since the challenge started, change active challenge for the next one
						else{
							//Calculate how many challenges there are to know which one is the next one
							resultSet2 = statement2.executeQuery("SELECT COUNT(*) FROM "+DBNAME+".challenge");
							if (resultSet2.next()){
								int num = resultSet2.getInt(1);
								//if current challenge has a lower order than number of challenges, the new one is the next
								if (c.getOrder()<num){
									statement3.executeUpdate("UPDATE "+DBNAME+".challenge SET IsActive=false WHERE id="+c.getId()+"");
									String sta = "UPDATE "+DBNAME+".challenge SET IsActive=true, StartDate='"+String.valueOf(new java.sql.Date(d.getTime()))+"' WHERE Orden="+(c.getOrder()+1);
									statement3.executeUpdate(sta);
									resultSet3 = statement3
											.executeQuery("select * from "+DBNAME+".challenge where IsActive = true");
									if (resultSet3.next()){
										ChallengeModel nuevo = setChallengeValues(resultSet3);
										return nuevo;
									}
								}
								//else, the new one is the first one
								else{
									statement3.executeUpdate("UPDATE "+DBNAME+".challenge SET `IsActive`=false WHERE `id`='"+c.getId()+"'");
									statement3.executeUpdate("UPDATE "+DBNAME+".challenge SET `IsActive`=true, 'StartDate'='"+String.valueOf(new java.sql.Date(d.getTime()))+"' WHERE Orden='1'");
									resultSet3 = statement3
											.executeQuery("select * from "+DBNAME+".challenge where IsActive = 1");
									if (resultSet3.next()){
										ChallengeModel nuevo = setChallengeValues(resultSet3);
										return nuevo;
									}
									
								}
							}
						}			
				}
				
				return challenge;
		}
		catch (Exception e) {
			throw e;
		} finally {
			close();
		}
		
	}
	
	public ChallengeModel setChallengeValues(ResultSet r){
		ChallengeModel c = new ChallengeModel();
		try {
		c.setId(r.getInt(1));
		c.setActive(true);
		c.setCurrentScore(r.getInt(7));
		c.setDescription(r.getString(2));
		c.setGoal(String.valueOf(r.getString(3)));
		c.setOriginalMeasurement(r.getInt(8));
		Date d =r.getDate(4); 
		c.setStartdate(d);
		c.setTotalScore(r.getInt(6));
		c.setOrder(r.getInt(9));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}
	
	public int getChallengeConsumption(int challenge) throws Exception{
		int total=0; 
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			String configFolderPath = System.getProperty(
	                BundleConfigHome.uAAL_CONF_ROOT_DIR, System
	                        .getProperty("user.dir"));
			if(configFolderPath.substring(configFolderPath.length() - 1)!="/"){
				configFolderPath+="/";
			}
	        System.setProperty("derby.system.home", configFolderPath);
	        String dbURL = "jdbc:derby:" + configFolderPath
                    + "/EnergyReaderPublisher/EnergyReaderDB;create=true";
			connect = DriverManager.getConnection(dbURL);

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			statement2 = connect.createStatement();
				resultSet = statement
						.executeQuery("select * from "+DBNAME+".challengedevice where challenge = "+challenge);
				while(resultSet.next()){
					int device = resultSet.getInt(3);
					Date date = new Date();
					String sql = "select * from "+DBNAME+".historicmeasurements where device = "+device+" AND fecha='"+String.valueOf(new java.sql.Date(date.getTime()))+"'";
					resultSet2 = statement2
							.executeQuery(sql);
					if (resultSet2.next()){
						total+=resultSet2.getInt(3);
					}
				}
				
				return total;
		}
		catch (Exception e) {
			throw e;
		}
		finally{
			close();
		}
		
	}
	
	public void modifyCurrentScore(ChallengeModel c) throws Exception{
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			String configFolderPath = System.getProperty(
	                BundleConfigHome.uAAL_CONF_ROOT_DIR, System
	                        .getProperty("user.dir"));
			if(configFolderPath.substring(configFolderPath.length() - 1)!="/"){
				configFolderPath+="/";
			}
	        System.setProperty("derby.system.home", configFolderPath);
	        String dbURL = "jdbc:derby:" + configFolderPath
                    + "/EnergyReaderPublisher/EnergyReaderDB;create=true";
			connect = DriverManager.getConnection(dbURL);

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			statement.executeUpdate("UPDATE "+DBNAME+".challenge SET CurrentScore="+c.getCurrentScore()+" WHERE id="+c.getId()+"");
		}
		catch (Exception e) {
			throw e;
		}
		finally{
			close();
		}
		
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