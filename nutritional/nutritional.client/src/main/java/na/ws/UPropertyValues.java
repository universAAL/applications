/**
 * UPropertyValues.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.ws;

public class UPropertyValues implements java.io.Serializable {
    private int ID;

    private java.lang.String value;

    private boolean custom;

    private int type;

    private int language;

    private int order;

    public UPropertyValues() {
    }

    public UPropertyValues(int ID, java.lang.String value, boolean custom,
	    int type, int language, int order) {
	this.ID = ID;
	this.value = value;
	this.custom = custom;
	this.type = type;
	this.language = language;
	this.order = order;
    }

    public int getID() {
	return ID;
    }

    public void setID(int ID) {
	this.ID = ID;
    }

    /**
     * Gets the value value for this UPropertyValues.
     * 
     * @return value
     */
    public java.lang.String getValue() {
	return value;
    }

    /**
     * Sets the value value for this UPropertyValues.
     * 
     * @param value
     */
    public void setValue(java.lang.String value) {
	this.value = value;
    }

    /**
     * Gets the custom value for this UPropertyValues.
     * 
     * @return custom
     */
    public boolean isCustom() {
	return custom;
    }

    /**
     * Sets the custom value for this UPropertyValues.
     * 
     * @param custom
     */
    public void setCustom(boolean custom) {
	this.custom = custom;
    }

    /**
     * Gets the type value for this UPropertyValues.
     * 
     * @return type
     */
    public int getType() {
	return type;
    }

    /**
     * Sets the type value for this UPropertyValues.
     * 
     * @param type
     */
    public void setType(int type) {
	this.type = type;
    }

    /**
     * Gets the language value for this UPropertyValues.
     * 
     * @return language
     */
    public int getLanguage() {
	return language;
    }

    /**
     * Sets the language value for this UPropertyValues.
     * 
     * @param language
     */
    public void setLanguage(int language) {
	this.language = language;
    }

    /**
     * Gets the order value for this UPropertyValues.
     * 
     * @return order
     */
    public int getOrder() {
	return order;
    }

    /**
     * Sets the order value for this UPropertyValues.
     * 
     * @param order
     */
    public void setOrder(int order) {
	this.order = order;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
	if (!(obj instanceof UPropertyValues))
	    return false;
	UPropertyValues other = (UPropertyValues) obj;
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
		&& ((this.value == null && other.getValue() == null) || (this.value != null && this.value
			.equals(other.getValue())))
		&& this.custom == other.isCustom()
		&& this.type == other.getType()
		&& this.language == other.getLanguage()
		&& this.ID == other.getID() && this.order == other.getOrder();
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
	if (getValue() != null) {
	    _hashCode += getValue().hashCode();
	}
	_hashCode += (isCustom() ? Boolean.TRUE : Boolean.FALSE).hashCode();
	_hashCode += getType();
	_hashCode += getLanguage();
	_hashCode += getOrder();
	_hashCode += getID();
	__hashCodeCalc = false;
	return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
	    UPropertyValues.class, true);

    static {
	typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.na/",
		"uPropertyValues"));
	org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("ID");
	elemField.setXmlName(new javax.xml.namespace.QName("", "ID"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "int"));
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("value");
	elemField.setXmlName(new javax.xml.namespace.QName("", "value"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "string"));
	elemField.setMinOccurs(0);
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("custom");
	elemField.setXmlName(new javax.xml.namespace.QName("", "custom"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "boolean"));
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("type");
	elemField.setXmlName(new javax.xml.namespace.QName("", "type"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "int"));
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("language");
	elemField.setXmlName(new javax.xml.namespace.QName("", "language"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "int"));
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("order");
	elemField.setXmlName(new javax.xml.namespace.QName("", "order"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "int"));
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
