package org.universAAL.AALapplication.food_shopping.service.db.utils.statements;

import org.universAAL.AALapplication.food_shopping.service.db.utils.Column;
import org.universAAL.AALapplication.food_shopping.service.db.utils.Value;
import org.universAAL.AALapplication.food_shopping.service.db.utils.criteria.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Vector;

public class SelectStatement extends EntityStatement {
	protected Vector joinTables = null;
	protected Vector joinColumns1 = null;
	protected Vector joinColumns2 = null;
	protected Vector orderByColumns = null;
	protected Vector groupByColumns = null;

	public SelectStatement(Connection con) {
		super(con);
	}

	public SelectStatement(String tableName, Connection con) {
		super(tableName, con);
	}

	public ResultSet execute(String query) throws SQLException {
		return this.executeQuery(query.toString());
	}

	public ResultSet execute(Vector columns, Vector criteria) throws SQLException {
		StringBuffer query = new StringBuffer("select ");
	    query.append(this.constructColumns(columns));
	    query.append(this.constructSelectTables());
		query.append(this.constructCriteria(criteria));
        query.append(this.constructGroupBy(this.groupByColumns));
        query.append(this.constructOrderBy());
//System.out.println("******************************************");
//System.out.println("Query="+query);
//System.out.println("******************************************");
		return this.execute(query.toString());
	}

	public ResultSet execute(Vector columns) throws SQLException {
		return this.execute(columns, null);
	}

	public ResultSet execute(Vector columns, long id) throws SQLException {
		StringBuffer query = new StringBuffer("select ");
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

	public ResultSet execute(Vector columns, Vector criteria, Vector groupByCol) throws SQLException {
		StringBuffer query = new StringBuffer("select ");
	    query.append(this.constructColumns(columns));
	    query.append(this.constructSelectTables());
		query.append(this.constructCriteria(criteria));
		query.append(this.constructGroupBy(groupByCol));
		query.append(this.constructOrderBy());
		return this.execute(query.toString());
	}

	public void addJoinTable(String tableName){
		if (this.joinTables == null)
			this.joinTables = new Vector();
		this.joinTables.add(tableName);
	}

	public void addJoinCondition(Column column1, Column column2){
		if (this.joinColumns1 == null)
			this.joinColumns1 = new Vector();

		this.joinColumns1.add(column1);

		if (this.joinColumns2 == null)
			this.joinColumns2 = new Vector();

		this.joinColumns2.add(column2);
	}

	public void addOrderByColumn(String columnName, String order){
		if (this.orderByColumns == null)
			this.orderByColumns = new Vector();
		this.orderByColumns.add(columnName + " " + order);
	}
	
	public void addOrderByColumns(Vector orderByColumns){
		this.orderByColumns = orderByColumns;
	}
    
	public void addGroupByColumn(String columnName){
		if (this.groupByColumns == null)
			this.groupByColumns = new Vector();
		this.groupByColumns.add(columnName);
    }

    public void addGroupByColumns(Vector columns){
        this.groupByColumns = columns;
    }

    protected StringBuffer constructColumns(Vector columns) {
		StringBuffer ret = new StringBuffer();
		if (columns.size()==0){
			ret.append("*");
		}
		else{
			for (Enumeration e = columns.elements(); e.hasMoreElements();) {
				Object col = e.nextElement();
				ret.append(Column.qualifyNameOf(col));
				ret.append(",");
			}
			try{
				ret.deleteCharAt(ret.lastIndexOf(","));
			}
			catch(StringIndexOutOfBoundsException se){}
		}
		return ret;
	}

	protected StringBuffer constructSelectTables(){
		StringBuffer ret = new StringBuffer(" from ");
		ret.append(this.tableName);

		if (this.joinTables != null && this.joinTables.size() > 0){
			for (Enumeration e = this.joinTables.elements(); e.hasMoreElements();){
				ret.append(" , ");
				String jTable = (String) e.nextElement();
				ret.append(jTable);
			}
		}
		return ret;
	}

	protected StringBuffer constructJoinCriteria(){
		StringBuffer ret = new StringBuffer("");
	    if (this.hasJoinColumns()){
	    	Enumeration e2 = this.joinColumns2.elements();
	    	for(Enumeration e = this.joinColumns1.elements(); e.hasMoreElements();){
	    		Column col1 = (Column)e.nextElement();
	    		Column col2 = (Column)e2.nextElement();
	    		ret.append(col1 + "=" + col2);
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
	
	protected StringBuffer constructCriteria(Vector criteria){
		StringBuffer ret = new StringBuffer("");
		if (this.hasJoinColumns()){
			ret.append(" where ");
			ret.append(this.constructJoinCriteria());
		}
		if (criteria != null && criteria.size() > 0){
			//if (this.hasJoinColumns() == false)
			//or
			if (ret.length() == 0)
				ret.append(" where ");
			else
				ret.append(" and ");

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

	public static String constructSimpleCriteria(Vector criteria){
		StringBuffer ret = new StringBuffer(" and ");
		if (criteria != null && criteria.size() > 0){
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
		    return ret.toString();
		} else
			return "";
	}

	protected StringBuffer constructOrderBy(){
		StringBuffer ret = new StringBuffer("");
	    if (this.orderByColumns != null && this.orderByColumns.size() > 0){
	    	ret.append(" order by ");
			for (Enumeration e = this.orderByColumns.elements(); e.hasMoreElements();){
				Object col = e.nextElement();
				ret.append(Column.qualifyNameOf(col));
				ret.append(",");
			}
			try{
				ret.deleteCharAt(ret.lastIndexOf(","));
			}catch (StringIndexOutOfBoundsException se){
			}
	    }
	    return ret;
	}

	protected StringBuffer constructGroupBy(Vector columns){
        StringBuffer ret = new StringBuffer("");
        if (columns != null && columns.size() > 0){
            ret.append(" group by ");
            for (Enumeration e = columns.elements(); e.hasMoreElements();) {
                Object col = e.nextElement();
                ret.append(Column.qualifyNameOf(col));
                ret.append(",");
            }
            try{
                ret.deleteCharAt(ret.lastIndexOf(","));
            }
            catch (StringIndexOutOfBoundsException se){}            
        }
        return ret;
    }
	
	public boolean hasJoinColumns(){
		if (this.joinColumns1 != null && this.joinColumns1.size() > 0)
			return true;
		else
			return false;
	}
}