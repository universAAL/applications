package org.universAAL.AALapplication.db.utils.statements;

import java.util.Enumeration;
import java.util.Hashtable;
import java.sql.Connection;

import java.sql.SQLException;

import org.universAAL.AALapplication.db.utils.Column;
import org.universAAL.AALapplication.db.utils.Value;

public class InsertStatement extends EntityStatement{
	
	public InsertStatement(String table, Connection con){
		super(table, con);
	}

	private StringBuffer constructColumns(Hashtable ht){
		StringBuffer ret = new StringBuffer("(");
		for (Enumeration e = ht.keys();e.hasMoreElements();){
	        Object col = e.nextElement();
        	ret.append(Column.nameOf(col));
			ret.append(",");
		}
		ret.deleteCharAt(ret.lastIndexOf(","));
		ret.append(") ");
		return ret;
	}

	public int execute(String query) throws SQLException {
		//System.out.println("Insert stmt = "+query.toString());
		return this.executeUpdate(query.toString());
	}

	public int execute(Hashtable ht) throws SQLException{
		StringBuffer query = new StringBuffer("insert into ");

		query.append(this.tableName);
		query.append(this.constructColumns(ht));
		query.append(" values (");

		for (Enumeration e = ht.elements();e.hasMoreElements();){
		    Value  val = ((Value)e.nextElement());
		    switch (val.getDataType()){
		    	case Value.STRINGTYPE :
		    	case Value.DATETYPE :
		    		query.append("'").append(val.toSQLString()).append("'");
		    		break;
		    	case Value.INTTYPE :
		    	case Value.LONGTYPE :
                case Value.DOUBLETYPE :
                case Value.BIGDECIMALTYPE :
                case Value.FLOATTYPE :
		    		query.append(val);
		    		break;
                case Value.BOOLEANTYPE:
                    query.append(val.toString());
                    break;
                case Value.NULLTYPE:
                    query.append("NULL");
		    	default:
		    		break;
		    }
		    query.append(",");
		}
		query.deleteCharAt(query.lastIndexOf(","));
		query.append(")");

		return this.execute(query.toString());
	}
}