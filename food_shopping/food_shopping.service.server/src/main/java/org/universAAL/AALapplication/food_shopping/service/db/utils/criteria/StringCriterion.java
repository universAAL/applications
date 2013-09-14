package org.universAAL.AALapplication.food_shopping.service.db.utils.criteria;

import java.util.Vector;

import org.universAAL.AALapplication.food_shopping.service.db.utils.Column;
import org.universAAL.AALapplication.food_shopping.service.db.utils.Value;

public class StringCriterion extends Criterion{

    public StringCriterion(Column col, Value val, int comparisonType){
        super(col, val, comparisonType);
    }

    public StringCriterion(Column col, Vector vals, int comparisonType){
        super(col, vals, comparisonType);
    }

    public String expand(){
    	if ( (this.value == null || this.value.getDataType() == Value.NULLTYPE)
        		&& (this.values == null || this.values.size() == 0) )
        	return "";
        
        StringBuffer sb = new StringBuffer(this.column.toString());
        switch (comparisonType)
        {
			case Criterion.EQUAL:
				sb.append(" = '").append(this.value.toSQLString()).append("'");
				break;       
            case Criterion.NOTEQUAL:
                sb.append(" != ").append(this.value.toSQLString()).append("'");
                break;
            case Criterion.LESSTHAN:
                sb.append(" < '").append(this.value.toSQLString()).append("'");
                break;
            case Criterion.LESSOREQUALTHAN:
                sb.append(" <= '").append(this.value.toSQLString()).append("'");
                break;
            case Criterion.GREATERTHAN:
                sb.append(" > '").append(this.value.toSQLString()).append("'");
                break;
            case Criterion.GREATEROREQUALTHAN:
                sb.append(" >= '").append(this.value.toSQLString()).append("'");
                break;
			case Criterion.SIMILAR:
				sb.append(" like '%").append(this.value.toSQLString()).append("%'");
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
                        sb.append("'").append(val.toSQLString()).append("'");
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
                        sb.append("'").append(val.toSQLString()).append("'");
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
        sb.append(" like ? ");
        return sb.toString();
    }
}
