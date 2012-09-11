package org.universAAL.AALapplication.food_shopping.service.db.manager.entities;

import org.universAAL.AALapplication.food_shopping.service.db.utils.*;
import org.universAAL.AALapplication.food_shopping.service.db.utils.statements.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.util.Hashtable;
import java.util.Vector;

public class Entity {
	Connection connection;
	String tableName;
	long id;
	Hashtable properties;
	Hashtable modifiedProperties = null;

	public Entity(String tableName) {
		this.connection = null;
		this.id = -1;
		this.properties = new Hashtable();
		this.tableName = tableName;
	}

	public Entity(String tableName, Connection con) {
		this.connection = con;
		this.id = -1;
		this.properties = new Hashtable();
		this.tableName = tableName;
	}
	
	public Entity(String tableName, Connection con, long id) throws SQLException {
		this.connection = con;
		this.id = id;
		this.properties = new Hashtable();
		this.tableName = tableName;

		Vector columns = new Vector();
		columns.add("*");

		SelectStatement ss = new SelectStatement(this.tableName, con);
		ResultSet rs = ss.execute(columns, id);
		fillProperties(rs);
		rs.close();
	}
	
	public Entity(String tableName, Connection con, Hashtable ht) {
		this.connection = con;
		this.id = -1;
		this.properties = ht;
		this.tableName = tableName;
	}

	public Entity(String tableName, Connection con, long id, Vector columns) throws SQLException {
		this.connection = con;
		this.id = id;
		this.properties = new Hashtable();
		this.tableName = tableName;
		
		SelectStatement ss = new SelectStatement(this.tableName, con);
		ResultSet rs = ss.execute(columns, id);
		fillProperties(rs);
		rs.close();
	}

	public Entity(String tableName, Connection con, ResultSet rs) throws SQLException {
		this.connection = con;
		this.properties = new Hashtable();
		this.tableName = tableName;
		fillProperties(rs);
	}

	public Entity(String tableName, Connection con, ResultSet rs, Vector columns) throws SQLException {
		this.connection = con;
		this.properties = new Hashtable();
		this.tableName = tableName;
		fillProperties(rs, columns);
	}
    
	public void setProperty(String key, Object value) 
	{
	    //do not allow client to set id 
	    if (key.equals(this.tableName+"_id"))
	        return;
	    
		this.properties.put(key, value);
		
		if (this.modifiedProperties  == null)
			this.modifiedProperties = new Hashtable();
		
		this.modifiedProperties.put(key,value);
	}

	public Object getProperty(String key) {
		return this.properties.get(key);
	}

	public void setProperties(Hashtable ht) {
		
	    if (ht != null && ht.size() > 0 )
	    {
	        if (ht.get(this.tableName +"_id") != null)
	            ht.remove(this.tableName+"_id");

		    this.properties.putAll(ht);
		    
		    if (this.modifiedProperties  == null)
			    this.modifiedProperties = new Hashtable();
		    
		    this.modifiedProperties.putAll(ht);
	    }
	}
	
	public Hashtable getProperties() {
		return this.properties;
	}
	
	private void setID(long id) {
	    String strID  = tableName  + "_id";
	    this.properties.put(strID,new Value(id));
		this.id = id;
	}
	
	public long getID() {
		return this.id;
	}

	private void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public String getTableName() {
		return this.tableName;
	}

	public void setConnection(Connection con) {
		this.connection = con;
	}
	
	public long newID() throws SQLException{
		
		Vector col = new Vector();
		col.addElement("max("+this.tableName+"_id) AS maxVal");
		SelectStatement ss = new SelectStatement(this.tableName, this.connection);
		ResultSet rs = ss.execute(col);
		rs.next();
		return rs.getLong("maxVal") + 1;
	}
	
	private void fillProperties(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int numberOfColumns = rsmd.getColumnCount();
		if (rs.next())
        {
    		for (int i = 1; i <= numberOfColumns; i++) {
    			String columnName = (String) rsmd.getColumnName(i);
    			this.properties.put(columnName, new Value((Object) rs.getObject(columnName)));
    		}
        }
	}

	private void fillProperties(ResultSet rs, Vector columns) throws SQLException {
		if (rs.next())
        {
    		for (int i = 0; i < columns.size(); i++) {
    		    Object obj = columns.elementAt(i);
    		    String columnName = Column.nameOf(obj);
    			this.properties.put(columnName, new Value((Object) rs.getObject(columnName)));
    		}
        }
	}
	
	/* database related functions */
	public void add() throws SQLException {
	
	    long newId = newID();
        setID(newId);
		InsertStatement is = new InsertStatement(this.tableName, this.connection);
		is.execute(this.properties);
	}
	
	
	public int delete() throws SQLException {
	    int result = 0;
		if (this.id != -1) {
			DeleteStatement ds = new DeleteStatement(this.tableName, this.connection);
			result = ds.execute(this.id);
		}
		return result;
	}
	
	public static int delete(String tableName, Connection con, long id) throws SQLException {
		DeleteStatement ds = new DeleteStatement(tableName, con);
		return ds.execute(id);
	}

	public int update() throws SQLException {
	    int result = 0;
		if (this.id != -1 && this.modifiedProperties != null) {
			UpdateStatement us = new UpdateStatement(this.tableName, this.connection);
			result = us.execute(this.modifiedProperties, this.id);
			this.modifiedProperties.clear();
		}
		return result;
	}
	
}