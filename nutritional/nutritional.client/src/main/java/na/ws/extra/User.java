/**
 * User.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.ws.extra;

public class User implements java.io.Serializable {
    private int codeLang;

    private java.lang.String expired;

    private int ID;

    private java.lang.String token;

    public User() {
    }

    public User(int codeLang, java.lang.String expired, int ID,
	    java.lang.String token) {
	this.codeLang = codeLang;
	this.expired = expired;
	this.ID = ID;
	this.token = token;
    }

    /**
     * Gets the codeLang value for this User.
     * 
     * @return codeLang
     */
    public int getCodeLang() {
	return codeLang;
    }

    /**
     * Sets the codeLang value for this User.
     * 
     * @param codeLang
     */
    public void setCodeLang(int codeLang) {
	this.codeLang = codeLang;
    }

    public java.lang.String getExpired() {
	return expired;
    }

    public void setExpired(java.lang.String expired) {
	this.expired = expired;
    }

    /**
     * Gets the ID value for this User.
     * 
     * @return ID
     */
    public int getID() {
	return ID;
    }

    /**
     * Sets the ID value for this User.
     * 
     * @param ID
     */
    public void setID(int ID) {
	this.ID = ID;
    }

    /**
     * Gets the token value for this User.
     * 
     * @return token
     */
    public java.lang.String getToken() {
	return token;
    }

    /**
     * Sets the token value for this User.
     * 
     * @param token
     */
    public void setToken(java.lang.String token) {
	this.token = token;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
	if (!(obj instanceof User))
	    return false;
	User other = (User) obj;
	if (obj == null)
	    return false;
	if (this == obj)
	    return true;
	if (__equalsCalc != null) {
	    return (__equalsCalc == obj);
	}
	__equalsCalc = obj;
	boolean _equals;
	_equals = true
		&& this.codeLang == other.getCodeLang()
		&& this.expired == other.getExpired()
		&& this.ID == other.getID()
		&& ((this.token == null && other.getToken() == null) || (this.token != null && this.token
			.equals(other.getToken())));
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
	_hashCode += getCodeLang();
	if (getExpired() != null) {
	    _hashCode += getExpired().hashCode();
	}
	_hashCode += getID();
	if (getToken() != null) {
	    _hashCode += getToken().hashCode();
	}
	__hashCodeCalc = false;
	return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
	    User.class, true);

    static {
	typeDesc.setXmlType(new javax.xml.namespace.QName("http:/ws.na/",
		"user"));
	org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("codeLang");
	elemField.setXmlName(new javax.xml.namespace.QName("", "codeLang"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "int"));
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("expired");
	elemField.setXmlName(new javax.xml.namespace.QName("", "expired"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "string"));
	elemField.setMinOccurs(0);
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("ID");
	elemField.setXmlName(new javax.xml.namespace.QName("", "ID"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "int"));
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("token");
	elemField.setXmlName(new javax.xml.namespace.QName("", "token"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "string"));
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
	    java.lang.String mechType, java.lang.Class _javaType,
	    javax.xml.namespace.QName _xmlType) {
	return new org.apache.axis.encoding.ser.BeanSerializer(_javaType,
		_xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
	    java.lang.String mechType, java.lang.Class _javaType,
	    javax.xml.namespace.QName _xmlType) {
	return new org.apache.axis.encoding.ser.BeanDeserializer(_javaType,
		_xmlType, typeDesc);
    }

}
