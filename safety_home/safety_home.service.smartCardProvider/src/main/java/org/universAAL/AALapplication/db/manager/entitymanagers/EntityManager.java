package org.universAAL.AALapplication.db.manager.entitymanagers;

import org.universAAL.AALapplication.db.manager.entities.Entity;
import org.universAAL.AALapplication.db.utils.*;

import org.universAAL.AALapplication.db.utils.statements.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;

public class EntityManager {
	Connection con;
	
	public EntityManager(Connection con) {
		this.con = con;
	}

	public boolean has(String tableName, Vector criteria) throws SQLException {
		boolean result = false;

		Vector columns = new Vector();
		columns.add(new Column(tableName, tableName+"_id"));
		
		SelectStatement ss = new SelectStatement(tableName, this.con);

		ResultSet rs = ss.execute(columns, criteria);
		if (rs.next())
			result = true;

		rs.close();

		return result;
	}

	public Vector get(String tableName, Vector columns, Vector criteria) throws SQLException {
		Vector result = new Vector();
		if (columns.size() != 0)
			columns.add(new Column(tableName, tableName+"_id"));
		
		SelectStatement ss = new SelectStatement(tableName, this.con);
        ss.addOrderByColumn(tableName+"."+tableName+"_id","asc");
        
		ResultSet rs = ss.execute(columns, criteria);
		while (rs.next()) {
			ResultRow rr = new ResultRow(rs);
			result.add(rr);
		}
		rs.close();

		return result;
	}
	
	public Vector get(String tableName, Vector columns, Vector criteria, Vector orderByCols) throws SQLException {
		Vector result = new Vector();
		if (columns.size() != 0)
			columns.add(new Column(tableName, tableName+"_id"));
		
		SelectStatement ss = new SelectStatement(tableName, this.con);
		
		ss.addOrderByColumns(orderByCols);
		
		ResultSet rs = ss.execute(columns, criteria);
		while (rs.next()) {
			ResultRow rr = new ResultRow(rs);
			result.add(rr);
		}
		rs.close();

		return result;
	}

    public ResultRow getInfo(String tableName, long tableID, Vector columns) throws SQLException{
        Entity entity = new Entity(tableName, this.con, tableID, columns);
        return new ResultRow(entity);
    }
    
	public long add(String tableName, Hashtable colValues) throws SQLException {
		Entity entity = new Entity(tableName, this.con, colValues);
		entity.add();
		return entity.getID();
	}

	protected void delete(String tableName, long ID) throws SQLException {
		Entity.delete(tableName, this.con, ID);
	}

	protected void modify(String tableName, long ID, Hashtable colValues) throws SQLException {
		Entity entity = new Entity(tableName, this.con, ID);
		entity.setProperties(colValues);
		entity.update();
	}
}
