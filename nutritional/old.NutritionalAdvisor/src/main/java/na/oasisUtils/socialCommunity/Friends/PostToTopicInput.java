/**
 * PostToTopicInput.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.oasisUtils.socialCommunity.Friends;

public class PostToTopicInput  implements java.io.Serializable {
    private na.oasisUtils.socialCommunity.Friends.AuthToken token;

    private java.lang.String topic_id;

    private java.lang.String text;

    public PostToTopicInput() {
    }

    public PostToTopicInput(
           na.oasisUtils.socialCommunity.Friends.AuthToken token,
           java.lang.String topic_id,
           java.lang.String text) {
           this.token = token;
           this.topic_id = topic_id;
           this.text = text;
    }


    /**
     * Gets the token value for this PostToTopicInput.
     * 
     * @return token
     */
    public na.oasisUtils.socialCommunity.Friends.AuthToken getToken() {
        return token;
    }


    /**
     * Sets the token value for this PostToTopicInput.
     * 
     * @param token
     */
    public void setToken(na.oasisUtils.socialCommunity.Friends.AuthToken token) {
        this.token = token;
    }


    /**
     * Gets the topic_id value for this PostToTopicInput.
     * 
     * @return topic_id
     */
    public java.lang.String getTopic_id() {
        return topic_id;
    }


    /**
     * Sets the topic_id value for this PostToTopicInput.
     * 
     * @param topic_id
     */
    public void setTopic_id(java.lang.String topic_id) {
        this.topic_id = topic_id;
    }


    /**
     * Gets the text value for this PostToTopicInput.
     * 
     * @return text
     */
    public java.lang.String getText() {
        return text;
    }


    /**
     * Sets the text value for this PostToTopicInput.
     * 
     * @param text
     */
    public void setText(java.lang.String text) {
        this.text = text;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PostToTopicInput)) return false;
        PostToTopicInput other = (PostToTopicInput) obj;
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
            ((this.topic_id==null && other.getTopic_id()==null) || 
             (this.topic_id!=null &&
              this.topic_id.equals(other.getTopic_id()))) &&
            ((this.text==null && other.getText()==null) || 
             (this.text!=null &&
              this.text.equals(other.getText())));
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
        if (getTopic_id() != null) {
            _hashCode += getTopic_id().hashCode();
        }
        if (getText() != null) {
            _hashCode += getText().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PostToTopicInput.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:Friends", "PostToTopicInput"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("token");
        elemField.setXmlName(new javax.xml.namespace.QName("", "token"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:Friends", "AuthToken"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topic_id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "topic_id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("text");
        elemField.setXmlName(new javax.xml.namespace.QName("", "text"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
