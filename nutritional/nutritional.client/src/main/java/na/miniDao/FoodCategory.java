/**
 * FoodCategory.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.miniDao;

public class FoodCategory implements java.io.Serializable {
    private int ID;

    private java.lang.String description;

    public FoodCategory() {
    }

    public FoodCategory(int ID, java.lang.String description) {
	this.ID = ID;
	this.description = description;
    }

    /**
     * Gets the ID value for this FoodCategory.
     * 
     * @return ID
     */
    public int getID() {
	return ID;
    }

    /**
     * Sets the ID value for this FoodCategory.
     * 
     * @param ID
     */
    public void setID(int ID) {
	this.ID = ID;
    }

    /**
     * Gets the description value for this FoodCategory.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
	return description;
    }

    /**
     * Sets the description value for this FoodCategory.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
	this.description = description;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
	if (!(obj instanceof FoodCategory))
	    return false;
	FoodCategory other = (FoodCategory) obj;
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
		&& this.ID == other.getID()
		&& ((this.description == null && other.getDescription() == null) || (this.description != null && this.description
			.equals(other.getDescription())));
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
	_hashCode += getID();
	if (getDescription() != null) {
	    _hashCode += getDescription().hashCode();
	}
	__hashCodeCalc = false;
	return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
	    FoodCategory.class, true);

    static {
	typeDesc.setXmlType(new javax.xml.namespace.QName("http://miniDao/",
		"foodCategory"));
	org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("ID");
	elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/",
		"ID"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "int"));
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("description");
	elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/",
		"description"));
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
