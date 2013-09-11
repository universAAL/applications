package org.universAAL.AALapplication.db.utils;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

public class Value{
    
    public static final int NULLTYPE = 0;
    public static final int STRINGTYPE = 1;
    public static final int INTTYPE = 2;
    public static final int DATETYPE = 3;
    public static final int LONGTYPE = 4;
    public static final int DOUBLETYPE = 5;
    public static final int BOOLEANTYPE = 6;
    public static final int BIGDECIMALTYPE = 7;
    public static final int FLOATTYPE = 8;
    public static final int TIMETYPE = 9;
    public static final int BYTEATYPE = 10;
    
    private int dataType;
    private Object dataValue;
    
    public Value(byte[] dataValue){
    	this.dataType = Value.BYTEATYPE;
        this.dataValue = dataValue;    	
    }

    public Value(int dataValue){
    	this.dataType = Value.INTTYPE;
        this.dataValue = new Integer(dataValue);    	
    }
    
    public Value(long dataValue){
    	this.dataType = Value.LONGTYPE;
        this.dataValue = new Long(dataValue);    	
    }
    
    public Value(double dataValue){
    	this.dataType = Value.DOUBLETYPE;
        this.dataValue = new Double(dataValue);    	
    }
    
    public Value(boolean dataValue){
    	this.dataType = Value.BOOLEANTYPE;
        this.dataValue = new Boolean(dataValue);    	
    }
    
    public Value(float dataValue){
        this.dataType = Value.FLOATTYPE;
        this.dataValue = new Float(dataValue);      
    }

    public Value(Object dataValue){
        super();
        if (dataValue != null)
        {
	        if (dataValue instanceof String){
	            this.dataType = Value.STRINGTYPE;
                this.dataValue = (String)dataValue;
	        }
	        else if (dataValue instanceof Date){
	            this.dataType = Value.DATETYPE;
	            this.dataValue = (Date)dataValue;
	        }
	        else if (dataValue instanceof Integer){
	            this.dataType = Value.INTTYPE;
	            this.dataValue = (Integer)dataValue;
	        }
	        else if (dataValue instanceof Long){
	            this.dataType = Value.LONGTYPE;
	            this.dataValue = (Long)dataValue;
	        }
	        else if (dataValue instanceof Double)
            {
                this.dataType = Value.DOUBLETYPE;
                this.dataValue  = (Double)dataValue;
            }
	        else if (dataValue instanceof Boolean)
            {
                this.dataType = Value.BOOLEANTYPE;
                this.dataValue = (Boolean)dataValue;
            }
            else if (dataValue instanceof BigDecimal)
            {
                this.dataType = Value.BIGDECIMALTYPE;
                this.dataValue = (BigDecimal)dataValue;
            }
            else if (dataValue instanceof Float)
            {
                this.dataType = Value.FLOATTYPE;
                this.dataValue = (Float)dataValue;
            }
            else if (dataValue instanceof Time)
            {
                this.dataType = Value.TIMETYPE;
                this.dataValue = (Time)dataValue;
            }
            else if (dataValue instanceof byte[])
            {
                this.dataType = Value.BYTEATYPE;
                this.dataValue = dataValue;
            }
        }
        else{
        	this.dataType = NULLTYPE;
        	this.dataValue = null;
        }
    }

    public Value(int dataType, Object dataValue){
        super();
        this.dataType = dataType;
        this.dataValue = dataValue;
    }
  
    public int getDataType(){
        return this.dataType;
    }
    public void setDataType(int dataType){
        this.dataType = dataType;
    }
    
    public Object getDataValue(){
        return this.dataValue;
    }
    public void setDataValue(Object dataValue){
        this.dataValue = dataValue;
    }
    
    public boolean isStringType(){
        return this.dataType == Value.STRINGTYPE;
    }
    
    public boolean isIntType(){
        return this.dataType == Value.INTTYPE;
    }
    
    public boolean isDateType(){
        return this.dataType == Value.DATETYPE;
    }

    public String toString(){
    	return (this.dataValue == null) ? null : this.dataValue.toString();
    }
    
    public String toSQLString(){
        return this.toString().replaceAll("'","''");
    }
    
    public int intValue(){
    	return ((Integer)this.dataValue).intValue();    	
    }
    
    public long longValue(){
    	return ((Long)this.dataValue).longValue();    	
    }
    
    public double doubleValue(){
    	return ((Double)this.dataValue).doubleValue();    	
    }
    
    public boolean booleanValue(){
    	return ((Boolean)this.dataValue).booleanValue();    	
    }
    
    public double bigDecimalValue(){
        return ((BigDecimal)this.dataValue).doubleValue();        
    }
    
    public float floatValue(){
        return ((Float)this.dataValue).floatValue();        
    }
}