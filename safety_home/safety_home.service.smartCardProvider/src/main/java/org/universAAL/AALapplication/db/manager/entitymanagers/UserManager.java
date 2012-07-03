package org.universAAL.AALapplication.db.manager.entitymanagers;

import org.universAAL.AALapplication.db.manager.entities.Entity;
import org.universAAL.AALapplication.db.utils.*;

import org.universAAL.AALapplication.db.utils.statements.*;
import org.universAAL.AALapplication.db.utils.criteria.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Hashtable;
import java.util.Vector;

public class UserManager extends EntityManager{

    public static final long ASSISTEDPERSON = 1;
    public static final long FORMALCAREGIVER = 2;
    public static final long INFORMALCAAREGIVER = 3;

    public static final long OTHER = 30;

	public UserManager(Connection con) {
		super(con);
	}

	public ResultRow getUser(long userID, Vector columns) throws SQLException{
		return getInfo("users", userID, columns);
	}

	public Vector getUsers(Vector columns, Vector criteria) throws SQLException {
		columns.add(new Column("users", "users_id"));

		Vector orderBy = new Vector();
		orderBy.add("role_id");

		return this.get("users", columns, criteria, orderBy);
	}

	public Vector getUser(long userID, Vector columns, Vector criteria) throws SQLException {
        Vector result = new Vector();

        if (columns.size() == 0)
            columns.add(new Column("users","*"));

        SelectStatement ss = new SelectStatement("users",con);

		if (criteria == null) criteria = new Vector();
		ArithmeticCriterion ac = new ArithmeticCriterion(new Column("users","users_id"), new Value(userID), Criterion.EQUAL);
		criteria.add(ac);

        ResultSet rs = ss.execute(columns, criteria);
        while (rs.next()){
            ResultRow rr = new ResultRow(rs);
            result.add(rr);
        }
        rs.close();
        
        return result;
	}

	public Vector getAllUsers(Vector columns, Vector criteria) throws SQLException {
        Vector result = new Vector();

        if (columns.size() == 0)
            columns.add(new Column("users","*"));

        if (criteria == null) criteria = new Vector();
        
        SelectStatement ss = new SelectStatement("users",con);

        ResultSet rs = ss.execute(columns, criteria);
        while (rs.next()){
            ResultRow rr = new ResultRow(rs);
            result.add(rr);
        }
        rs.close();
        
        return result;
	}

	public Vector getUserBySmartCard(String tagUid, Vector columns, Vector criteria) throws SQLException {
        Vector result = new Vector();

        if (columns.size() == 0){
            columns.add(new Column("users","*"));
            columns.add(new Column("role","name"));
            //columns.add(new Column("smartcard","shortdescription"));            
        }
        //System.out.println("************************************************");
        //System.out.println("tag uid="+tagUid);
        //System.out.println("************************************************");
        SelectStatement ss = new SelectStatement("users",con);
        ss.addJoinTable("users_to_role");
        ss.addJoinCondition(new Column("users","users_id"),new Column("users_to_role","users_id"));
        ss.addJoinTable("role");
        ss.addJoinCondition(new Column("role","role_id"),new Column("users_to_role","role_id"));
        ss.addJoinTable("device_to_users_to_smartcard");
        ss.addJoinCondition(new Column("users","users_id"),new Column("device_to_users_to_smartcard","users_id"));
        ss.addJoinTable("smartcard");
        ss.addJoinCondition(new Column("smartcard","smartcard_id"),new Column("device_to_users_to_smartcard","smartcard_id"));
        
        if (criteria == null) criteria = new Vector();
        StringCriterion ac = new StringCriterion(new Column("smartcard","shortdescription"), new Value(tagUid), Criterion.EQUAL);
        criteria.add(ac);

        ResultSet rs = ss.execute(columns, criteria);
        while (rs.next()){
            ResultRow rr = new ResultRow(rs);
            result.add(rr);
        }
        rs.close();
        
        return result;
	}

	public Vector getUserBySmartCard(long smartcard_id, Vector columns, Vector criteria) throws SQLException {
        Vector result = new Vector();

        if (columns.size() == 0){
            columns.add(new Column("users","*"));
            columns.add(new Column("role","name"));
            columns.add(new Column("smartcard","shortdescription"));            
        }
        
        SelectStatement ss = new SelectStatement("users",con);
        ss.addJoinTable("users_to_role");
        ss.addJoinCondition(new Column("users","users_id"),new Column("users_to_role","users_id"));
        ss.addJoinTable("role");
        ss.addJoinCondition(new Column("role","role_id"),new Column("users_to_role","role_id"));
        ss.addJoinTable("device_to_users_to_smartcard");
        ss.addJoinCondition(new Column("users","users_id"),new Column("device_to_users_to_smartcard","users_id"));
        ss.addJoinTable("smartcard");
        ss.addJoinCondition(new Column("smartcard","smartcard_id"),new Column("device_to_users_to_smartcard","smartcard_id"));
        
        if (criteria == null) criteria = new Vector();
        ArithmeticCriterion ac = new ArithmeticCriterion(new Column("smartcard","smartcard_id"), new Value(smartcard_id), Criterion.EQUAL);
        criteria.add(ac);

        ResultSet rs = ss.execute(columns, criteria);
        while (rs.next()){
            ResultRow rr = new ResultRow(rs);
            result.add(rr);
        }
        rs.close();
        
        return result;
	}

	
	public long addUser(Hashtable colValues) throws SQLException {
		return this.add("users", colValues);			
	}

	public void deleteUser(long userID) throws SQLException {
		this.delete("users", userID);
	}

	public void modifyUser(long userID, Hashtable colValues) throws SQLException {
		this.modify("users", userID, colValues);
	}

	public ResultRow getGeneralInfo(long userID, Vector columns) throws SQLException {
		return this.getInfo("users", userID, columns);
	}
	
	//RoleManager
	public Vector getRoles(Vector columns, Vector criteria) throws SQLException {
		columns.add(new Column("role", "role_id"));

		return this.get("role", columns, criteria);
	}

	public long addRole(Hashtable colValues) throws SQLException {
        return this.add("role", colValues);
	}

	public void deleteRole(long roleID) throws SQLException {
		this.delete("role", roleID);
	}

	public void modifyRole(long roleID, Hashtable colValues) throws SQLException {
        this.modify("role", roleID, colValues);
	}
	//End of RoleManager

	public long authenticateUser(String userName, String passwd, long cityID) throws SQLException {
	    long result = 0;
		Vector columns = new Vector();
		Vector criteria = new Vector();
		columns.add(new Column("users", "username"));
		columns.add(new Column("users", "passwd"));
        columns.add(new Column("users", "users_id"));
		Value uname = new Value(userName);
		StringCriterion sc1 = new StringCriterion(new Column("users", "username"), uname, Criterion.EQUAL);
		Value pswd = new Value(passwd);
		StringCriterion sc2 = new StringCriterion(new Column("users", "passwd"), pswd, Criterion.EQUAL);

		criteria.add(sc1);
		criteria.add(sc2);

		SelectStatement ss = new SelectStatement("users", this.con);
		ResultSet rs = ss.execute(columns, criteria);
		if (rs.next()) 
			result = rs.getLong("users_id");
        
        rs.close();
        
        return result;
	}

	public void setUserRole(long userID, long roleID) throws SQLException {
		Hashtable colValues = new Hashtable();
        colValues.put("role_id", new Value(roleID));
        this.modify("users", userID, colValues);
	}

	public long getUserRole(long userID) throws SQLException {
		Column col = new Column("users", "role_id");
		Vector columns = new Vector();
		columns.add(col);
		ResultRow rr = this.getInfo("users", userID, columns);
		return rr.getLongValue(col);
	}

	public boolean isUserInRole(long userID, long roleID) throws SQLException {
        boolean result = false;

        long role_id = this.getUserRole(userID);

		if (role_id == roleID)
			result = true;

		return result;
	}
    
    public boolean isUserInRole(String userName, String roleName) throws SQLException {
        boolean result = false;
        Vector columns = new Vector();
        columns.add("role_id");

        SelectStatement ss = new SelectStatement("users", con);
        ss.addJoinTable("role");
        ss.addJoinCondition(new Column("users","role_id"),new Column("role","role_id"));
        
        Vector criteria = new Vector();
        StringCriterion sc = new StringCriterion(new Column("users","username"),new Value(userName),Criterion.EQUAL);
        criteria.add(sc);
        sc = new StringCriterion(new Column("role","name"),new Value(roleName),Criterion.EQUAL);
        
        Vector cols = new Vector();
        cols.add(new Column("users","users_id"));
        cols.add(new Column("role","role_id"));
        ResultSet rs = ss.execute(cols,criteria);
        if (rs.next())
            result = true;
        
        rs.close();
        
        return result;
    }

    public long addUsers_to_role(Hashtable colValues) throws SQLException {
		return this.add("users_to_role", colValues);
	}    

	public void deleteUsers_to_role(long userstoroleID) throws SQLException{
		this.delete("users_to_role", userstoroleID);
	}

    public void modifyUsers_to_role(long userstoroleID, Hashtable colValues) throws SQLException{
        this.modify("users_to_role", userstoroleID, colValues); 
    }

    public Vector getUserRoles(long userID, Vector cols, Vector criteria) throws SQLException{
        Vector result = new Vector();
        
        if (cols.size() == 0)
            cols.add(new Column("users_to_role","role_id"));
        
        if (criteria == null) criteria = new Vector();
        
        SelectStatement ss = new SelectStatement("users_to_role",con);
        
        ArithmeticCriterion ac = new ArithmeticCriterion(new Column("users_to_role","users_id"), new Value(userID), Criterion.EQUAL);
        criteria.add(ac);
        
        ResultSet rs = ss.execute(cols, criteria);
        while (rs.next()){
        	long roleID = rs.getLong("role_id");
            result.add(new Long(roleID));
        }
        rs.close();
        
        return result;
    }

    public boolean isUserInRole(long userID, String roleName) throws SQLException {
    		
   		SelectStatement ss = new SelectStatement("users", this.con);
   		ss.addJoinTable("users_to_role");
   		ss.addJoinCondition(new Column("users","users_id"),new Column("users_to_role","users_id"));
   		ss.addJoinTable("role");
   		ss.addJoinCondition(new Column("role","role_id"),new Column("users_to_role","role_id"));
    	
   		Vector criteria = new Vector();
        ArithmeticCriterion ac = new ArithmeticCriterion(new Column("users","users_id"), new Value(userID), Criterion.EQUAL);
        criteria.add(ac);

   		Vector columns = new Vector();
   		columns.add(new Column("role", "name"));
   		ResultSet rs = ss.execute(columns, criteria);
        while (rs.next()){
        	if (roleName.equals((String) rs.getString("name"))){
        		rs.close();
        		return true;
        	}
        }
   		rs.close();
   		return false;
   }
}