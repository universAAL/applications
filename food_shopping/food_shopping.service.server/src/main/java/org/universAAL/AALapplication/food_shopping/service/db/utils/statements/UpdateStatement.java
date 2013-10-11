package org.universAAL.AALapplication.food_shopping.service.db.utils.statements;

import org.universAAL.AALapplication.food_shopping.service.db.utils.Column;
import org.universAAL.AALapplication.food_shopping.service.db.utils.Value;
import java.sql.Connection;
import java.sql.SQLException;

import java.util.Enumeration;
import java.util.Vector;
import java.util.Hashtable;

public class UpdateStatement extends EntityStatement {

	public UpdateStatement(String tableName, Connection con) {
		super(tableName, con);
	}

	public int execute(String query) throws SQLException {
		return this.executeUpdate(query.toString());
	}
	
	public int execute(Hashtable ht, long id) throws SQLException {
		Vector cols = new Vector();	
		for (Enumeration e = ht.keys();e.hasMoreElements();)		   
			cols.add(e.nextElement());

		Vector vals = new Vector();		
		for (Enumeration e = ht.elements();e.hasMoreElements();)	  
			vals.add((Value)e.nextElement());

		return this.execute(cols, vals, id);
	}

	public int execute(Vector columns, Vector values, long id) throws SQLException {
		StringBuffer query = new StringBuffer("update ");
		query.append(this.tableName);
		query.append(" set ");

		Enumeration c, v;
		c = columns.elements();
		v = values.elements();
		for (; c.hasMoreElements();) {
			// get column
	        Object col = c.nextElement();
	        query.append(Column.nameOf(col));
	        query.append(" = ");
			// get value
			Value val = ((Value) v.nextElement());
			switch (val.getDataType()) 
            {
    			case Value.STRINGTYPE:
    			case Value.DATETYPE :
    				query.append("'").append(val.toSQLString()).append("'");
    				break;
    			case Value.INTTYPE:
    			case Value.LONGTYPE:
                case Value.DOUBLETYPE:    
                case Value.BIGDECIMALTYPE :
                case Value.FLOATTYPE :
                case Value.BOOLEANTYPE:
    				query.append(val);
    				break;
                case Value.NULLTYPE:
                    query.append("NULL");
    			default:
    				break;
			}
			query.append(",");
		}
		query.deleteCharAt(query.lastIndexOf(","));

		query.append(" where ");
		query.append(this.tableID + " = " + id);
//System.out.println("Update = "+query.toString());
		return this.execute(query.toString());
	}
}