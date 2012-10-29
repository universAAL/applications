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
package org.universAAL.FitbitPublisher.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSetMetaData;
import java.util.Date;

import org.universAAL.FitbitPublisher.model.*;
import org.universAAL.FitbitPublisher.utils.Setup;
import org.universAAL.ontology.aalfficiency.scores.Challenge;

public class FitbitDBInterface {
	private Setup s = new Setup();
	private static final String DBNAME ="FitbitPublisher";

	private Connection connect = null;
	private Statement statement = null;
	private Statement statement2 = null;
	private Statement statement3 = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private ResultSet resultSet2 = null;
	private ResultSet resultSet3 = null;
	

	
	public void createDB() throws Exception{
		// load the h2-JDBC driver using the current class loader
	    Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
		try {
			Connection connect = null;
			connect = DriverManager.getConnection("jdbc:derby:"+DBNAME+";create=true;");
			statement = connect.createStatement();
		    statement.setQueryTimeout(30);  // set timeout to 30 sec.
		   
		  
    		String createTableChallenge = "CREATE TABLE  "+DBNAME+".challenge (id integer PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), description varchar(255),goal varchar(20),"+
    				"startdate date, isActive boolean,totalscore integer,currentscore integer, orden integer)";
    		
    		//create tables   
		    statement.executeUpdate(createTableChallenge);
		    
		    
		    
		    Date date = new Date();
		    String  d = String.valueOf(new java.sql.Date(date.getTime()));
		    //insert challenges
		    String sql = "insert into "+DBNAME+".challenge (description ,goal, startdate, isActive,totalscore ,currentscore, orden) values ('Walk 10000 steps this week', '10000 steps','"+d+"', true, 150,0,1)";
		    statement.executeUpdate(sql);
		    statement.executeUpdate("insert into "+DBNAME+".challenge (description ,goal, isActive,totalscore ,currentscore, orden) values ('Burn 4200 kcal this week', '4200 kcal', true, 120,0,2)");
		    
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
			// Setup the connection with the DB
			String user = s.getDBUser();
			String pwd = s.getDBPwd();
			connect = DriverManager.
					getConnection("jdbc:derby:"+DBNAME);
			
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
							resultSet2 = statement2.executeQuery("SELECT COUNT(*) FROM challenge");
							if (resultSet2.next()){
								int num = resultSet2.getInt(1);
								//if current challenge has a lower order than number of challenges, the new one is the next
								if (c.getOrder()<num){
									statement3.executeUpdate("UPDATE "+DBNAME+".challenge SET `IsActive`=false WHERE `id`='"+c.getId()+"'");
									String sta = "UPDATE challenge SET IsActive=true, StartDate='"+String.valueOf(new java.sql.Date(d.getTime()))+"' WHERE Orden="+(c.getOrder()+1);
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
		Date d =r.getDate(4); 
		c.setStartdate(d);
		c.setTotalScore(r.getInt(6));
		c.setOrder(r.getInt(8));
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
			// Setup the connection with the DB
			String user = s.getDBUser();
			String pwd = s.getDBPwd();
			connect = DriverManager.
					getConnection("jdbc:derby:"+DBNAME);

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
			// Setup the connection with the DB
			String user = s.getDBUser();
			String pwd = s.getDBPwd();
			connect = DriverManager.getConnection("jdbc:derby:"+DBNAME);

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