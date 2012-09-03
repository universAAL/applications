/**
 * AddTopicInput.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.oasisUtils.socialCommunity.Friends;

public class AddTopicInput  implements java.io.Serializable {
    private na.oasisUtils.socialCommunity.Friends.AuthToken token;

    private na.oasisUtils.socialCommunity.Friends.NewTopic topic;

    public AddTopicInput() {
    }

    public AddTopicInput(
           na.oasisUtils.socialCommunity.Friends.AuthToken token,
           na.oasisUtils.socialCommunity.Friends.NewTopic topic) {
           this.token = token;
           this.topic = topic;
    }


    /**
     * Gets the token value for this AddTopicInput.
     * 
     * @return token
     */
    public na.oasisUtils.socialCommunity.Friends.AuthToken getToken() {
        return token;
    }


    /**
     * Sets the token value for this AddTopicInput.
     * 
     * @param token
     */
    public void setToken(na.oasisUtils.socialCommunity.Friends.AuthToken token) {
        this.token = token;
    }


    /**
     * Gets the topic value for this AddTopicInput.
     * 
     * @return topic
     */
    public na.oasisUtils.socialCommunity.Friends.NewTopic getTopic() {
        return topic;
    }


    /**
     * Sets the topic value for this AddTopicInput.
     * 
     * @param topic
     */
    public void setTopic(na.oasisUtils.socialCommunity.Friends.NewTopic topic) {
        this.topic = topic;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AddTopicInput)) return false;
        AddTopicInput other = (AddTopicInput) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.token==null && other.getToken()==null) || 
             (this.token!=null &&
              this.token.equals(other.getToken()))) &&
            ((this.topic==null && other.getTopic()==null) || 
             (this.topic!=null &&
              this.topic.equals(other.getTopic())));
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
        if (getToken() != null) {
            _hashCode += getToken().hashCode();
        }
        if (getTopic() != null) {
            _hashCode += getTopic().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AddTopicInput.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:Friends", "AddTopicInput"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("token");
        elemField.setXmlName(new javax.xml.namespace.QName("", "token"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:Friends", "AuthToken"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topic");
        elemField.setXmlName(new javax.xml.namespace.QName("", "topic"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:Friends", "NewTopic"));
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
