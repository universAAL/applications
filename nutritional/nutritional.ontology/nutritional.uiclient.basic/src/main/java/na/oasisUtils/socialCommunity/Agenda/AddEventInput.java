/**
 * AddEventInput.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.oasisUtils.socialCommunity.Agenda;

public class AddEventInput  implements java.io.Serializable {
    private na.oasisUtils.socialCommunity.Agenda.AuthToken auth_token;

    private na.oasisUtils.socialCommunity.Agenda.NewEvent event;

    public AddEventInput() {
    }

    public AddEventInput(
           na.oasisUtils.socialCommunity.Agenda.AuthToken auth_token,
           na.oasisUtils.socialCommunity.Agenda.NewEvent event) {
           this.auth_token = auth_token;
           this.event = event;
    }


    /**
     * Gets the auth_token value for this AddEventInput.
     * 
     * @return auth_token
     */
    public na.oasisUtils.socialCommunity.Agenda.AuthToken getAuth_token() {
        return auth_token;
    }


    /**
     * Sets the auth_token value for this AddEventInput.
     * 
     * @param auth_token
     */
    public void setAuth_token(na.oasisUtils.socialCommunity.Agenda.AuthToken auth_token) {
        this.auth_token = auth_token;
    }


    /**
     * Gets the event value for this AddEventInput.
     * 
     * @return event
     */
    public na.oasisUtils.socialCommunity.Agenda.NewEvent getEvent() {
        return event;
    }


    /**
     * Sets the event value for this AddEventInput.
     * 
     * @param event
     */
    public void setEvent(na.oasisUtils.socialCommunity.Agenda.NewEvent event) {
        this.event = event;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AddEventInput)) return false;
        AddEventInput other = (AddEventInput) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.auth_token==null && other.getAuth_token()==null) || 
             (this.auth_token!=null &&
              this.auth_token.equals(other.getAuth_token()))) &&
            ((this.event==null && other.getEvent()==null) || 
             (this.event!=null &&
              this.event.equals(other.getEvent())));
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
        if (getAuth_token() != null) {
            _hashCode += getAuth_token().hashCode();
        }
        if (getEvent() != null) {
            _hashCode += getEvent().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AddEventInput.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:Agenda", "AddEventInput"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("auth_token");
        elemField.setXmlName(new javax.xml.namespace.QName("", "auth_token"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:Agenda", "AuthToken"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("event");
        elemField.setXmlName(new javax.xml.namespace.QName("", "event"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:Agenda", "NewEvent"));
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
