package org.universAAL.AALapplication.db.utils.criteria;

import org.universAAL.AALapplication.db.utils.*;

import java.util.Vector;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class DateCriterion extends Criterion{

    public DateCriterion(Column col, Value val, int comparisonType){
        super(col, val, comparisonType);
    }

    public DateCriterion(Column col, Vector vals, int comparisonType){
        super(col, vals, comparisonType);
    }
    
    public String expand(){
        
    	if ( (this.value == null || this.value.getDataType() == Value.NULLTYPE)
        		&& (this.values == null || this.values.size() == 0) )
        	return "";
        
        StringBuffer sb = new StringBuffer(this.column.toString());
        String date = getDateString(this.value);
        switch (comparisonType){
        	case Criterion.EQUAL:
                sb.append(" = '").append(date).append("'");
                break;
            case Criterion.NOTEQUAL:
                sb.append(" != ").append(date);
                break;
        	case Criterion.LESSTHAN:
                sb.append(" < '").append(date).append("'");
                break;
            case Criterion.LESSOREQUALTHAN:
                sb.append(" <= '").append(date).append("'");
                break;
        	case Criterion.GREATERTHAN:
                sb.append(" > '").append(date).append("'");
                break;
            case Criterion.GREATEROREQUALTHAN:
                sb.append(" >= '").append(date).append("'");
                break;
            case Criterion.ISNULL:
                sb.append(" is null ");
                break;
            case Criterion.ISNOTNULL:
                sb.append(" is not null ");
                break;
            case Criterion.IN:
                if (this.values != null && this.values.size() > 0)
                {
                    sb.append(" in (");
                    for (int i = 0 ; i < this.values.size() ; i++)
                    {
                        Value val = (Value) this.values.get(i);
                        sb.append("'").append(getDateString(val)).append("'");
                        sb.append(",");
                    }
                    try{
                        sb.deleteCharAt(sb.lastIndexOf(","));
                    }catch (StringIndexOutOfBoundsException se){
                    }
                    sb.append(" ) ");
                }
                break;
            case Criterion.NOTIN:
                if (this.values != null && this.values.size() > 0)
                {
                    sb.append(" not in (");
                    for (int i = 0 ; i < this.values.size() ; i++)
                    {
                        Value val = (Value) this.values.get(i);
                        sb.append("'").append(getDateString(val)).append("'");
                        sb.append(",");
                    }
                    try{
                        sb.deleteCharAt(sb.lastIndexOf(","));
                    }catch (StringIndexOutOfBoundsException se){
                    }
                    sb.append(" ) ");
                }
                break;
        	default:
                break;
        }
        return sb.toString();
    }
    
    public String expandPrepared(){
        StringBuffer sb = new StringBuffer(column.toString());
        switch (comparisonType){
        	case Criterion.EQUAL:
                sb.append(" = ? ");
                break;
        	case Criterion.LESSTHAN:
                sb.append(" < ? ");
                break;
        	case Criterion.GREATERTHAN:
                sb.append(" > ? ");
                break;
        	default:
                break;
        }
        return sb.toString();
    }
    
    private String getDateString(Value value){
    	Date date = (Date)value.getDataValue();
    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    	return sdf.format(date);
    }
}