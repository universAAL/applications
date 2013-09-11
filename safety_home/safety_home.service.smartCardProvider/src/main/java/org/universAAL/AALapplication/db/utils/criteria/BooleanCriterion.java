package org.universAAL.AALapplication.db.utils.criteria;

import org.universAAL.AALapplication.db.utils.*;

public class BooleanCriterion extends Criterion{

    public BooleanCriterion(Column col, Value val, int comparisonType){
        super(col, val, comparisonType);
    }

    public String expand(){
        if (value == null || this.value.getDataType() == Value.NULLTYPE)
            return "";
        
        StringBuffer sb = new StringBuffer(this.column.toString());
        switch (comparisonType)
        {
            case Criterion.EQUAL:
                sb.append(" = ").append(this.value);
                break;
            case Criterion.NOTEQUAL:
                sb.append(" != ").append(this.value);
                break;
            default:
                break;
        }
        return sb.toString();
    }

    public String expandPrepared(){
        return null;
    }
}