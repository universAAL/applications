package org.universAAL.AALapplication.food_shopping.service.db.utils.statements;

import org.universAAL.AALapplication.food_shopping.service.db.utils.criteria.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Vector;

public class DeleteStatement extends EntityStatement{

	public DeleteStatement(String tableName, Connection con) {
		super(tableName, con);
    }

	public int execute(String query) throws SQLException {
		return this.executeUpdate(query.toString());
	}

	public int execute(long id) throws SQLException {

		StringBuffer query = new StringBuffer("delete from ");
		query.append(this.tableName);
		query.append(" where ");
		query.append(this.tableID + " = " + id);

		return this.execute(query.toString());
	}
	
	public int execute(Vector criteria) throws SQLException {

		StringBuffer query = new StringBuffer("delete from ");
		query.append(this.tableName);
		query.append(this.constructCriteria(criteria));

		return this.execute(query.toString());
	}
	
	private StringBuffer constructCriteria(Vector criteria){
		StringBuffer ret = new StringBuffer("");
		if (criteria != null && criteria.size() > 0){
			ret.append(" where ");
			for (Enumeration e = criteria.elements(); e.hasMoreElements();){
				Criterion c = (Criterion) e.nextElement();
				ret.append(c.expand());
				ret.append(" and ");
			}
			// remove last and
			try{
				ret.delete(ret.lastIndexOf("and"), ret.length());
			}catch (StringIndexOutOfBoundsException se){		
			}
	    }
	    return ret;
	}
}
