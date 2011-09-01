/**
 * GetFriendsResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.oasisUtils.socialCommunity.Friends;

public class GetFriendsResponse  implements java.io.Serializable {
    private java.lang.String status;

    private na.oasisUtils.socialCommunity.Friends.UserProfile[] friends;

    public GetFriendsResponse() {
    }

    public GetFriendsResponse(
           java.lang.String status,
           na.oasisUtils.socialCommunity.Friends.UserProfile[] friends) {
           this.status = status;
           this.friends = friends;
    }


    /**
     * Gets the status value for this GetFriendsResponse.
     * 
     * @return status
     */
    public java.lang.String getStatus() {
        return status;
    }


    /**
     * Sets the status value for this GetFriendsResponse.
     * 
     * @param status
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
    }


    /**
     * Gets the friends value for this GetFriendsResponse.
     * 
     * @return friends
     */
    public na.oasisUtils.socialCommunity.Friends.UserProfile[] getFriends() {
        return friends;
    }


    /**
     * Sets the friends value for this GetFriendsResponse.
     * 
     * @param friends
     */
    public void setFriends(na.oasisUtils.socialCommunity.Friends.UserProfile[] friends) {
        this.friends = friends;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetFriendsResponse)) return false;
        GetFriendsResponse other = (GetFriendsResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.friends==null && other.getFriends()==null) || 
             (this.friends!=null &&
              java.util.Arrays.equals(this.friends, other.getFriends())));
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
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getFriends() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFriends());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFriends(), i);
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
        new org.apache.axis.description.TypeDesc(GetFriendsResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:Friends", "GetFriendsResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("", "status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("friends");
        elemField.setXmlName(new javax.xml.namespace.QName("", "friends"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:Friends", "UserProfile"));
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
