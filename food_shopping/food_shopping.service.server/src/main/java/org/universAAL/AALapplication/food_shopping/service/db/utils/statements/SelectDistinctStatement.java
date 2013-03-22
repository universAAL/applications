package org.universAAL.AALapplication.food_shopping.service.db.utils.statements;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class SelectDistinctStatement extends SelectStatement {
	public SelectDistinctStatement(String tableName, Connection con) {
		super(tableName, con);
	}

	public ResultSet execute(Vector columns) throws SQLException {
		return this.execute(columns, null);
	}

	public ResultSet execute(Vector columns, long id) throws SQLException {
		StringBuffer query = new StringBuffer("select distinct ");
	    query.append(this.constructColumns(columns));
	    query.append(this.constructSelectTables());
		
	    query.append(" where ");
	    if (this.hasJoinColumns()){
	    	query.append(this.constructJoinCriteria());
	    	query.append(" and ");
	    }
	    query.append(this.tableName).append(".").append(this.tableID).append("=").append(id);

		query.append(this.constructOrderBy());

		return this.execute(query.toString());
	}
	
	public ResultSet execute(Vector columns, Vector criteria) throws SQLException {
		StringBuffer query = new StringBuffer("select distinct ");
	    query.append(this.constructColumns(columns));
	    query.append(this.constructSelectTables());
		query.append(this.constructCriteria(criteria));
		query.append(this.constructOrderBy());
        query.append(this.constructGroupBy(this.groupByColumns));
//System.out.println("Query="+query);
		return this.execute(query.toString());
	}

	public ResultSet execute(Vector columns, Vector criteria, Vector groupByCol) throws SQLException {
		StringBuffer query = new StringBuffer("select distinct ");
	    query.append(this.constructColumns(columns));
	    query.append(this.constructSelectTables());
		query.append(this.constructCriteria(criteria));
		query.append(this.constructGroupBy(groupByCol));
		query.append(this.constructOrderBy());
		return this.execute(query.toString());
	}
}
