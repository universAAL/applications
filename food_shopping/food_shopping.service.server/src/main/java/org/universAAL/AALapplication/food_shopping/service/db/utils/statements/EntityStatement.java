package org.universAAL.AALapplication.food_shopping.service.db.utils.statements;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EntityStatement {
	protected String tableName;
	protected String tableID;
	protected Connection con;

	public EntityStatement(Connection con){
		super();
		this.tableName = null;
		this.tableID = null;
		this.con = con;
	}

	public EntityStatement(String tName, Connection con){
		super();
		this.tableName = tName;
		this.tableID = tName + "_id";
		this.con = con;
	}

	public String getTableName(){
		return this.tableName;
	}

	public void setTableName(String tableName){
		this.tableName = tableName;
	}

	public ResultSet executeQuery(String query) throws SQLException {
		//Statement st = this.con.createStatement();
		Statement st = this.con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		return st.executeQuery(query);	
	}

	public int executeUpdate(String query) throws SQLException {
		Statement st = this.con.createStatement();
		return st.executeUpdate(query);
	}
}