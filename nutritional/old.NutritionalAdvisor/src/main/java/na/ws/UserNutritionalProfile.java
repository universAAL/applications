/**
 * UserNutritionalProfile.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.ws;

public class UserNutritionalProfile  implements java.io.Serializable {
    private java.lang.String statusResponse;

    private int version;

    private double timeinmillis;

    private int userID;

    private na.ws.UProperty[] properties;

    public UserNutritionalProfile() {
    }

    public UserNutritionalProfile(
           java.lang.String statusResponse,
           int version,
           double timeinmillis,
           int userID,
           na.ws.UProperty[] properties) {
           this.statusResponse = statusResponse;
           this.version = version;
           this.timeinmillis = timeinmillis;
           this.userID = userID;
           this.properties = properties;
    }


    /**
     * Gets the statusResponse value for this UserNutritionalProfile.
     * 
     * @return statusResponse
     */
    public java.lang.String getStatusResponse() {
        return statusResponse;
    }


    /**
     * Sets the statusResponse value for this UserNutritionalProfile.
     * 
     * @param statusResponse
     */
    public void setStatusResponse(java.lang.String statusResponse) {
        this.statusResponse = statusResponse;
    }


    /**
     * Gets the version value for this UserNutritionalProfile.
     * 
     * @return version
     */
    public int getVersion() {
        return version;
    }


    /**
     * Sets the version value for this UserNutritionalProfile.
     * 
     * @param version
     */
    public void setVersion(int version) {
        this.version = version;
    }


    /**
     * Gets the timeinmillis value for this UserNutritionalProfile.
     * 
     * @return timeinmillis
     */
    public double getTimeinmillis() {
        return timeinmillis;
    }


    /**
     * Sets the timeinmillis value for this UserNutritionalProfile.
     * 
     * @param timeinmillis
     */
    public void setTimeinmillis(double timeinmillis) {
        this.timeinmillis = timeinmillis;
    }


    /**
     * Gets the userID value for this UserNutritionalProfile.
     * 
     * @return userID
     */
    public int getUserID() {
        return userID;
    }


    /**
     * Sets the userID value for this UserNutritionalProfile.
     * 
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }


    /**
     * Gets the properties value for this UserNutritionalProfile.
     * 
     * @return properties
     */
    public na.ws.UProperty[] getProperties() {
        return properties;
    }


    /**
     * Sets the properties value for this UserNutritionalProfile.
     * 
     * @param properties
     */
    public void setProperties(na.ws.UProperty[] properties) {
        this.properties = properties;
    }

    public na.ws.UProperty getProperties(int i) {
        return this.properties[i];
    }

    public void setProperties(int i, na.ws.UProperty _value) {
        this.properties[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UserNutritionalProfile)) return false;
        UserNutritionalProfile other = (UserNutritionalProfile) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.statusResponse==null && other.getStatusResponse()==null) || 
             (this.statusResponse!=null &&
              this.statusResponse.equals(other.getStatusResponse()))) &&
            this.version == other.getVersion() &&
            this.timeinmillis == other.getTimeinmillis() &&
            this.userID == other.getUserID() &&
            ((this.properties==null && other.getProperties()==null) || 
             (this.properties!=null &&
              java.util.Arrays.equals(this.properties, other.getProperties())));
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
        if (getStatusResponse() != null) {
            _hashCode += getStatusResponse().hashCode();
        }
        _hashCode += getVersion();
        _hashCode += new Double(getTimeinmillis()).hashCode();
        _hashCode += getUserID();
        if (getProperties() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProperties());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProperties(), i);
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
        new org.apache.axis.description.TypeDesc(UserNutritionalProfile.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.na/", "userNutritionalProfile"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statusResponse");
        elemField.setXmlName(new javax.xml.namespace.QName("", "statusResponse"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("version");
        elemField.setXmlName(new javax.xml.namespace.QName("", "version"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("timeinmillis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "timeinmillis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("properties");
        elemField.setXmlName(new javax.xml.namespace.QName("", "properties"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.na/", "uProperty"));
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
