package org.universAAL.EnergyReader.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.universAAL.EnergyReader.model.ChallengeModel;
import org.universAAL.EnergyReader.model.ReadEnergyModel;
import org.universAAL.EnergyReader.utils.Setup;

public class EnergyReaderDBInterface {

	private Connection connect = null;
	private Statement statement = null;
	private Statement statement2 = null;
	private Statement statement3 = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private ResultSet resultSet2 = null;
	private ResultSet resultSet3 = null;
	
	private Setup s = new Setup();
	
	public void insertMeasurement(ReadEnergyModel[] consumptions) throws Exception{
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			String user = s.getDBUser();
			String pwd = s.getDBPwd();
			connect = DriverManager
					.getConnection("jdbc:mysql://localhost/aalfficiency?user="+user+"&password="+pwd);

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			for (ReadEnergyModel rem : consumptions){
				resultSet = statement
						.executeQuery("select * from aalfficiency.device where Name='"+rem.getDevice().getName()+"'");
				if (resultSet.next()){
				int deviceId = resultSet.getInt("id");
				preparedStatement = connect
						.prepareStatement("insert into  aalfficiency.dailymeasurements values (default, ?, ?, ?)");
				// Parameters start with 1
				preparedStatement.setInt(1,deviceId);
				preparedStatement.setInt(2, rem.getMeasure().getMeasurement());
				Date date = new Date();
				preparedStatement.setTimestamp(3, new java.sql.Timestamp(date.getTime()));
				preparedStatement.executeUpdate();
				}
			}
		}
		catch (Exception e) {
			throw e;
		} finally {
			close();
		}
		
	}
	
	public void insertTotalMeasurement(String device, int measurement)throws Exception{
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			String user = s.getDBUser();
			String pwd = s.getDBPwd();
			connect = DriverManager
					.getConnection("jdbc:mysql://localhost/aalfficiency?user="+user+"&password="+pwd);

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			
				resultSet = statement
						.executeQuery("select * from aalfficiency.device where Name='"+device+"'");
				if (resultSet.next()){
				int deviceId = resultSet.getInt("id");
				preparedStatement = connect
						.prepareStatement("insert into  aalfficiency.historicmeasurements values (default, ?, ?, ?)");
				// Parameters start with 1
				preparedStatement.setInt(1,deviceId);
				preparedStatement.setInt(2, measurement);
				Date date = new Date();
				preparedStatement.setDate(3, new java.sql.Date(date.getTime()));
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
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			String user = s.getDBUser();
			String pwd = s.getDBPwd();
			connect = DriverManager
					.getConnection("jdbc:mysql://localhost/aalfficiency?user="+user+"&password="+pwd);

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			resultSet = statement
					.executeQuery("select * from aalfficiency.device where Name='"+device+"'");
			if (resultSet.next()){
				int deviceId = resultSet.getInt("id");
				resultSet2 = statement.executeQuery("select * from aalfficiency.dailymeasurements where Device='"+deviceId+"'");
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
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			String user = s.getDBUser();
			String pwd = s.getDBPwd();
			connect = DriverManager
					.getConnection("jdbc:mysql://localhost/aalfficiency?user="+user+"&password="+pwd);

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			statement.executeUpdate("delete from aalfficiency.dailymeasurements");
		}
		catch (Exception e) {
			throw e;
		} finally {
			close();
		}
		
	}
	
	public List<ChallengeModel> getActiveChallenges() throws Exception{
		List<ChallengeModel> challenges = new ArrayList();
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			String user = s.getDBUser();
			String pwd = s.getDBPwd();
			connect = DriverManager
					.getConnection("jdbc:mysql://localhost/aalfficiency?user="+user+"&password="+pwd);
			
			int days = s.getElectricityChallengeDays();
			
			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			statement2 = connect.createStatement();
			statement3 = connect.createStatement();
				resultSet = statement
						.executeQuery("select * from aalfficiency.challenges where IsActive = 1 AND Type='Electricity'");
				while(resultSet.next()){
					ChallengeModel c = setChallengeValues(resultSet);
					
					Date d = new Date();
					java.sql.Date date = new java.sql.Date(d.getTime());
			
					resultSet2 = statement2.executeQuery("SELECT DATEDIFF('"+date+"','"+c.getStartdate()+"')");
					if (resultSet2.next()){
						int dif = resultSet2.getInt(1);
						//If it's been less than week since the challenge started, its the current one
						if (dif<days){
							challenges.add(c);
						}
						//If it's been a week since the challenge started, change active challenge for the next one
						else{
							//Calculate how many challenges there are to know which one is the next one
							resultSet2 = statement2.executeQuery("SELECT COUNT(*) FROM aalfficiency.challenges WHERE Type='"+c.getType()+"'");
							if (resultSet2.next()){
								int num = resultSet2.getInt(1);
								//if current challenge has a lower order than number of challenges, the new one is the next
								if (c.getOrder()<num){
									statement3.executeUpdate("UPDATE `aalfficiency`.`challenges` SET `IsActive`='0' WHERE `id`='"+c.getId()+"'");
									String sta = "UPDATE `aalfficiency`.`challenges` SET IsActive=1, StartDate='"+date+"' WHERE Type='Electricity' AND Orden="+(c.getOrder()+1);
									statement3.executeUpdate(sta);
									resultSet3 = statement3
											.executeQuery("select * from aalfficiency.challenges where IsActive = 1 AND Type='Electricity'");
									if (resultSet3.next()){
										ChallengeModel nuevo = setChallengeValues(resultSet3);
										challenges.add(nuevo);
									}
								}
								//else, the new one is the first one
								else{
									statement3.executeUpdate("UPDATE `aalfficiency`.`challenges` SET `IsActive`='0' WHERE `id`='"+c.getId()+"'");
									statement3.executeUpdate("UPDATE `aalfficiency`.`challenges` SET `IsActive`='1', 'StartDate'='"+date+"' WHERE Type='Electricity' AND Orden='1'");
									resultSet3 = statement3
											.executeQuery("select * from aalfficiency.challenges where IsActive = 1 AND Type='Electricity'");
									if (resultSet3.next()){
										ChallengeModel nuevo = setChallengeValues(resultSet3);
										challenges.add(nuevo);
									}
									
								}
							}
						}
					}
					
				}
				
				return challenges;
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
			c.setId(r.getInt("id"));
		c.setActive(true);
		c.setChallenge(r.getString("Challenge"));
		c.setCurrentScore(r.getInt("CurrentScore"));
		c.setDescription(r.getString("Description"));
		c.setGoal(String.valueOf(r.getInt("Goal")));
		c.setOriginalMeasurement(r.getInt("OriginalMeasurement"));
		c.setStartdate(r.getDate("StartDate"));
		c.setTotalScore(r.getInt("TotalScore"));
		c.setType(r.getString("Type"));
		c.setOrder(r.getInt("Orden"));
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
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			String user = s.getDBUser();
			String pwd = s.getDBPwd();
			connect = DriverManager
					.getConnection("jdbc:mysql://localhost/aalfficiency?user="+user+"&password="+pwd);

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			statement2 = connect.createStatement();
				resultSet = statement
						.executeQuery("select * from aalfficiency.challengedevices where Challenge = "+challenge);
				while(resultSet.next()){
					int device = resultSet.getInt("Dev");
					Date date = new Date();
					String sql = "select * from aalfficiency.historicmeasurements where Device = "+device+" AND Date='"+new java.sql.Date(date.getTime())+"'";
					resultSet2 = statement2
							.executeQuery(sql);
					if (resultSet2.next()){
						total+=resultSet2.getInt("Measurement");
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
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			String user = s.getDBUser();
			String pwd = s.getDBPwd();
			connect = DriverManager
					.getConnection("jdbc:mysql://localhost/aalfficiency?user="+user+"&password="+pwd);

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			statement.executeUpdate("UPDATE `aalfficiency`.`challenges` SET `CurrentScore`='"+c.getCurrentScore()+"' WHERE `id`='"+c.getId()+"'");
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
