package org.universAAL.AALapplication.food_shopping.service.db.utils;

public class Column{
    protected String tableName = null;
    protected String colName = null;
    protected int dataType;
    boolean pKey = false;
    boolean fKey = false;
    protected String refTable = null;
    protected int size = -1;

    public Column(String tableName, String colName){
        super();
        this.tableName = tableName;
        this.colName = colName;
    }
    
    public Column(String tableName){
        super();
        this.tableName = tableName;
        this.colName = tableName + "_id";
    }
    
    public Column(String tableName, String colName, int dataType){
        super();
        this.tableName = tableName;
        this.colName = colName;
        this.dataType = dataType;
    }
    
    public String getTableName()
    {
        return this.tableName;
    }
    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }
    public String getColName()
    {
        return this.colName;
    }
    public void setColName(String colName)
    {
        this.colName = colName;
    }
    public int getDataType()
    {
        return this.dataType;
    }
    public void setDataType(int dataType)
    {
        this.dataType = dataType;
    }
    public int getSize()
    {
        return this.size;
    }
    public void setSize(int size)
    {
        this.size = size;
    }
    public void setAsPKey()
    {
        pKey = true;
    }
    public boolean isPKey()
    {
        return this.pKey;
    }
    
    public void setAsFKey(String refTable)
    {
        fKey = true;
        this.refTable = refTable;
    }
    
    public String isFKey()
    {
        return this.refTable;
    }
    
    public String qualifyColName(){
    	if (this.tableName.equals("func"))
            return this.colName;
        else
        	return this.tableName + "." + this.colName;
    }
    
    public String toString(){
        return this.qualifyColName();
    }

    public static String qualifyNameOf(Object obj){
    	String ret = null;
        if (obj instanceof Column){
			Column col = (Column) obj;
			ret = col.qualifyColName();
        }
        else if (obj instanceof String){
        	ret = (String) obj;				
        }
        return ret;
    }

    public static String nameOf(Object obj){
    	String ret = null;
        if (obj instanceof Column){
			Column col = (Column) obj;
			ret = col.getColName();
        }
        else if (obj instanceof String){
        	ret = (String) obj;				
        }
        return ret;
    }
    
    public int hashCode(){
        String hashString  = this.toString();
        return hashString.hashCode();
    }
    
    public boolean equals(Object obj){
        Column col = (Column)obj;
        /*int hash1 = this.hashCode();
        int hash2 = col.hashCode();
        if (hash1 == hash2)
            return true;*/
        if (this.tableName.equals(col.tableName) && this.colName.equals(col.colName))
            return true;
        
        return false;
    }
}
