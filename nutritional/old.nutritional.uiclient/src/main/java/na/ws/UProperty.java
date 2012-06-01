/**
 * UProperty.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.ws;

public class UProperty  implements java.io.Serializable {
    private java.lang.String code;

    private na.ws.UPropertyValues default_Value;

    private na.ws.UPropertyValues[] values;

    public UProperty() {
    }

    public UProperty(
           java.lang.String code,
           na.ws.UPropertyValues default_Value,
           na.ws.UPropertyValues[] values) {
           this.code = code;
           this.default_Value = default_Value;
           this.values = values;
    }


    /**
     * Gets the code value for this UProperty.
     * 
     * @return code
     */
    public java.lang.String getCode() {
        return code;
    }


    /**
     * Sets the code value for this UProperty.
     * 
     * @param code
     */
    public void setCode(java.lang.String code) {
        this.code = code;
    }


    /**
     * Gets the default_Value value for this UProperty.
     * 
     * @return default_Value
     */
    public na.ws.UPropertyValues getDefault_Value() {
        return default_Value;
    }


    /**
     * Sets the default_Value value for this UProperty.
     * 
     * @param default_Value
     */
    public void setDefault_Value(na.ws.UPropertyValues default_Value) {
        this.default_Value = default_Value;
    }


    /**
     * Gets the values value for this UProperty.
     * 
     * @return values
     */
    public na.ws.UPropertyValues[] getValues() {
        return values;
    }


    /**
     * Sets the values value for this UProperty.
     * 
     * @param values
     */
    public void setValues(na.ws.UPropertyValues[] values) {
        this.values = values;
    }

    public na.ws.UPropertyValues getValues(int i) {
        return this.values[i];
    }

    public void setValues(int i, na.ws.UPropertyValues _value) {
        this.values[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UProperty)) return false;
        UProperty other = (UProperty) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.code==null && other.getCode()==null) || 
             (this.code!=null &&
              this.code.equals(other.getCode()))) &&
            ((this.default_Value==null && other.getDefault_Value()==null) || 
             (this.default_Value!=null &&
              this.default_Value.equals(other.getDefault_Value()))) &&
            ((this.values==null && other.getValues()==null) || 
             (this.values!=null &&
              java.util.Arrays.equals(this.values, other.getValues())));
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
        if (getCode() != null) {
            _hashCode += getCode().hashCode();
        }
        if (getDefault_Value() != null) {
            _hashCode += getDefault_Value().hashCode();
        }
        if (getValues() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getValues());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getValues(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UProperty.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.na/", "uProperty"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("code");
        elemField.setXmlName(new javax.xml.namespace.QName("", "code"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("default_Value");
        elemField.setXmlName(new javax.xml.namespace.QName("", "default_Value"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.na/", "uPropertyValues"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("values");
        elemField.setXmlName(new javax.xml.namespace.QName("", "values"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.na/", "uPropertyValues"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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
