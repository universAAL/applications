package org.universAAL.AALapplication.food_shopping.service.db.manager.entitymanagers;

import org.universAAL.AALapplication.food_shopping.service.db.manager.entities.Entity;
import org.universAAL.AALapplication.food_shopping.service.db.utils.*;

import org.universAAL.AALapplication.food_shopping.service.db.utils.statements.*;
import org.universAAL.AALapplication.food_shopping.service.db.utils.criteria.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Vector;

public class GeneralParameterManager extends EntityManager{
	public GeneralParameterManager(Connection con) {
		super(con);
	}

	public GeneralParameterManager(Connection con, long depID) {
		super(con);
	}

	public Vector getParameterValues(String tableName, Vector columns, Vector criteria) throws SQLException{
		return this.get(tableName, columns, criteria);
    }

    public long addParameterValue(String tableName,Hashtable colValues) throws SQLException {
        return this.add(tableName, colValues);
    }  
    
    public void modifyParameterValue(String tableName,long parameterID, Hashtable colValues) throws SQLException {
        this.modify(tableName, parameterID, colValues);
    }

    public void deleteParameterValue(String tableName,long parameterID) throws SQLException {
        this.delete(tableName, parameterID);
    }
}