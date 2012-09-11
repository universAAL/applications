package org.universAAL.AALapplication.db.utils;

import org.universAAL.AALapplication.db.manager.entities.*;

import java.sql.ResultSet;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

//import org.postgresql.PGResultSetMetaData;

public class ResultRow{
    private Hashtable colValues;
    
    public ResultRow(){
    	colValues = new Hashtable();
    }
    
    public ResultRow(ResultSet rs) throws SQLException{
    	colValues = new Hashtable();
        
        ResultSetMetaData rsmd = rs.getMetaData();
        //PGResultSetMetaData prsmd = (PGResultSetMetaData)rsmd; 
        	
		int numberOfColumns = rsmd.getColumnCount();
		
		for (int i = 1; i <= numberOfColumns; i++) {
			String columnName = (String) rsmd.getColumnName(i);
			//String tableName  = (String)prsmd.getBaseTableName(i);
			String tableName  = (String)rsmd.getTableName(i);
			Value val = new Value((Object) rs.getObject(i));
			colValues.put(new Column(tableName,columnName), val);
		}
    }
    
    public ResultRow(Entity entity){
    	colValues = new Hashtable();
        
        Hashtable properties = entity.getProperties();
        for (Enumeration keys = properties.keys(); keys.hasMoreElements(); )
        {
        	String key = (String)keys.nextElement();
            Value val = (Value) properties.get(key);
            colValues.put(new Column(entity.getTableName(), key), val);
        }
    }

    public Hashtable getColValues()
    {
        return colValues;
    }
    
    public void putValue(Column col, Value colValue)
    {
        colValues.put(col, colValue);
    }
    
    public long getLongValue(Column col)
    {
        long result = 0;
        Value val = getValue(col);
        if (val != null && val.getDataType()!= Value.NULLTYPE)
            result = val.longValue();
        return result;
    }
    
    public String getStringValue(Column col)
    {
        String result = null;
        Value val = getValue(col);
        if (val != null && val.getDataType() != Value.NULLTYPE)
        	result = val.toString();
        return result;
    }
    
    public Date getDateValue(Column col)
    {
        Date result = null;
        Value val = getValue(col);
        if (val != null && val.getDataType() != Value.NULLTYPE)
            result = (Date)val.getDataValue();
        return result;
    }
    
    public String getDateStrValue(Column col)
    {
        String datestr = "";
        Date date = null;
        Value val = getValue(col);
        if (val != null && val.getDataType() != Value.NULLTYPE)
        {
            date = (Date)val.getDataValue();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            datestr = sdf.format(date);
        }
        return datestr;
    }
    
    public Time getTimeValue(Column col)
    {
        Time result = null;
        Value val = getValue(col);
        if (val != null && val.getDataType() != Value.NULLTYPE)
            result = (Time)val.getDataValue();
        return result;
    }
    
    public int getIntValue(Column col)
    {
        int result = 0;
        Value val = getValue(col);
        if (val != null && val.getDataType()!= Value.NULLTYPE)
            result = val.intValue();
        return result;
    }
    
    public double getDoubleValue(Column col)
    {
        double result = 0;
        Value val = getValue(col);
        if (val != null && val.getDataType()!= Value.NULLTYPE)
            result = val.doubleValue();
        return result;
    }
    
    public double getBigDecimalValue(Column col)
    {
        double result = 0;
        Value val = getValue(col);
        if (val != null && val.getDataType()!= Value.NULLTYPE)
            result = val.bigDecimalValue();
        return result;
    }
    
    public boolean getBooleanValue(Column col)
    {
        boolean result = false;
        Value val = getValue(col);
        if (val != null && val.getDataType()!= Value.NULLTYPE)
            result = val.booleanValue();
        return result;
    }
    
    public Value getValue(Column col)
    {
        return (Value)colValues.get(col);
    }
    
    public int getColumnCount()
    {
        return colValues.size();
    }
    
    public ResultRow add(ResultRow rr)
    {
        Hashtable ht = rr.getColValues();
        if (ht != null)
            colValues.putAll(ht);
        
        return this;
    }
    
    public String toString(){
    	return this.colValues.toString();
    }
}
