/**
 * AdviseTime.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.miniDao;

public class AdviseTime  implements java.io.Serializable {
    private int mealType;

    private boolean usePatientTime;

    private java.lang.String time;

    public AdviseTime() {
    }

    public AdviseTime(
           int mealType,
           boolean usePatientTime,
           java.lang.String time) {
           this.mealType = mealType;
           this.usePatientTime = usePatientTime;
           this.time = time;
    }


    /**
     * Gets the mealType value for this AdviseTime.
     * 
     * @return mealType
     */
    public int getMealType() {
        return mealType;
    }


    /**
     * Sets the mealType value for this AdviseTime.
     * 
     * @param mealType
     */
    public void setMealType(int mealType) {
        this.mealType = mealType;
    }


    /**
     * Gets the usePatientTime value for this AdviseTime.
     * 
     * @return usePatientTime
     */
    public boolean isUsePatientTime() {
        return usePatientTime;
    }


    /**
     * Sets the usePatientTime value for this AdviseTime.
     * 
     * @param usePatientTime
     */
    public void setUsePatientTime(boolean usePatientTime) {
        this.usePatientTime = usePatientTime;
    }


    /**
     * Gets the time value for this AdviseTime.
     * 
     * @return time
     */
    public java.lang.String getTime() {
        return time;
    }


    /**
     * Sets the time value for this AdviseTime.
     * 
     * @param time
     */
    public void setTime(java.lang.String time) {
        this.time = time;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AdviseTime)) return false;
        AdviseTime other = (AdviseTime) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.mealType == other.getMealType() &&
            this.usePatientTime == other.isUsePatientTime() &&
            ((this.time==null && other.getTime()==null) || 
             (this.time!=null &&
              this.time.equals(other.getTime())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += getMealType();
        _hashCode += (isUsePatientTime() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getTime() != null) {
            _hashCode += getTime().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AdviseTime.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://miniDao/", "adviseTime"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mealType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/", "mealType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usePatientTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/", "usePatientTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("time");
        elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/", "time"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
