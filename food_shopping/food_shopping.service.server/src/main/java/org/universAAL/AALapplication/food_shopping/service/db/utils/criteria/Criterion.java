package org.universAAL.AALapplication.food_shopping.service.db.utils.criteria;

import org.universAAL.AALapplication.food_shopping.service.db.utils.*;

import java.util.Vector;

public abstract class Criterion{
    
    public static final int EQUAL = 1;
    public static final int NOTEQUAL = 2;
    public static final int SIMILAR = 3;
    public static final int LESSTHAN = 4;
    public static final int LESSOREQUALTHAN = 5;
    public static final int GREATERTHAN = 6;
    public static final int GREATEROREQUALTHAN = 7;
    public static final int ISNOTNULL = 8;
    public static final int ISNULL = 9;
    public static final int IN = 10;
    public static final int NOTIN = 11;
    
    //string, arithemetic, date comparison
    protected int dataType;
    
    //equality, similarity, less than, greater than 
    protected int comparisonType;
    
    protected Column column;
    protected Value value;
    protected Vector values; //for 'in' and 'not in' statement

    public Criterion(Column col, Value val, int comparisonType){
        super();
        this.comparisonType = comparisonType;
        this.value = val;
        this.column = col;
    }
    
    public Criterion(Column col, Vector vals, int comparisonType){
        super();
        this.comparisonType = comparisonType;
        this.values = vals;
        this.column = col;
    }

    public int getDataType()
    {
        return dataType;
    }
        
    public int getComparisonType()
    {
        return comparisonType;
    }
    
    public Value getValue()
    {
        return value;
    }
    
    public String getColumnName()
    {
        return column.toString();
    }
    
    public abstract String expand();
    public abstract String expandPrepared();
}