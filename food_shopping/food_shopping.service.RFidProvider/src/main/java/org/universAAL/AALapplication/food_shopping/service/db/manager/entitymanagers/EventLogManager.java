package org.universAAL.AALapplication.food_shopping.service.db.manager.entitymanagers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Vector;

import org.universAAL.AALapplication.food_shopping.service.db.utils.Column;
import org.universAAL.AALapplication.food_shopping.service.db.utils.ResultRow;
import org.universAAL.AALapplication.food_shopping.service.db.utils.Value;
import org.universAAL.AALapplication.food_shopping.service.db.utils.criteria.ArithmeticCriterion;
import org.universAAL.AALapplication.food_shopping.service.db.utils.criteria.Criterion;

public class EventLogManager extends EntityManager{

    public static final long LOGINEVENT = 1;
    
    public EventLogManager(Connection con){
        super(con);
    }
    

    public Vector getEvents(Vector columns, Vector criteria) throws SQLException{
        return this.get("eventlog",columns,criteria);
    }
    
    public long setUserLoginInfo(long userID, Hashtable colValues) throws SQLException{
        colValues.put("users_id",new Value(userID));
        return this.add("eventlog",colValues);
    }
    
    public ResultRow getUserLastLoginInfo(long userID) throws SQLException{
        ResultRow result = null;
        
        Statement s = con.createStatement();
        String query = "select max(eventlog_time) as lastlogintime, eventlog_date as lastlogindate " +
                        "from eventlog " +
                        "where users_id =  " + userID +
                        "and eventlog_date in"+
                        "(select max(eventlog_date) from eventlog) " + 
                        "group by eventlog_date";
                       
        
        ResultSet rs = s.executeQuery(query);
        if (rs.next())
            result = new ResultRow(rs);
        
        rs.close();
        
        return result;
        
    }
    
    public Vector getEventsOfType(long eventTypeID, Vector columns) throws SQLException{
        Value val = new Value(eventTypeID);
        ArithmeticCriterion ac = new ArithmeticCriterion(new Column("eventlog","eventtype_id"), val, Criterion.EQUAL);
        Vector criteria = new Vector();
        criteria.add(ac);
        return getEvents(columns, criteria);
    }
    
    public long addEventLog(Hashtable colValues) throws SQLException{
        return this.add("eventlog",colValues);        
    }
    
    public void removeEventLog(long eventLogID) throws SQLException{
        this.delete("eventlog",eventLogID);
    }

    /*event type management*/
    public Vector getEventTypes(Vector columns, Vector criteria) throws SQLException{
        return this.get("eventtype",columns,criteria);
    }
    
    public long addEventType(Hashtable colValues) throws SQLException{
        return this.add("eventtype",colValues);
    }
    
    public void removeEventType(long eventTypeID) throws SQLException{
        this.delete("eventtype",eventTypeID);
    }
    /*end of event type management*/
}
