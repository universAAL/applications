/**
 * PatientList.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.miniDao.profile;

public class PatientList implements java.io.Serializable {
    private int ID;

    private java.lang.String firstName;

    private java.lang.String lastName;

    public PatientList() {
    }

    public PatientList(int ID, java.lang.String firstName,
	    java.lang.String lastName) {
	this.ID = ID;
	this.firstName = firstName;
	this.lastName = lastName;
    }

    /**
     * Gets the ID value for this PatientList.
     * 
     * @return ID
     */
    public int getID() {
	return ID;
    }

    /**
     * Sets the ID value for this PatientList.
     * 
     * @param ID
     */
    public void setID(int ID) {
	this.ID = ID;
    }

    /**
     * Gets the firstName value for this PatientList.
     * 
     * @return firstName
     */
    public java.lang.String getFirstName() {
	return firstName;
    }

    /**
     * Sets the firstName value for this PatientList.
     * 
     * @param firstName
     */
    public void setFirstName(java.lang.String firstName) {
	this.firstName = firstName;
    }

    /**
     * Gets the lastName value for this PatientList.
     * 
     * @return lastName
     */
    public java.lang.String getLastName() {
	return lastName;
    }

    /**
     * Sets the lastName value for this PatientList.
     * 
     * @param lastName
     */
    public void setLastName(java.lang.String lastName) {
	this.lastName = lastName;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
	if (!(obj instanceof PatientList))
	    return false;
	PatientList other = (PatientList) obj;
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
		&& ((this.firstName == null && other.getFirstName() == null) || (this.firstName != null && this.firstName
			.equals(other.getFirstName())))
		&& ((this.lastName == null && other.getLastName() == null) || (this.lastName != null && this.lastName
			.equals(other.getLastName())));
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
	if (getFirstName() != null) {
	    _hashCode += getFirstName().hashCode();
	}
	if (getLastName() != null) {
	    _hashCode += getLastName().hashCode();
	}
	__hashCodeCalc = false;
	return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
	    PatientList.class, true);

    static {
	typeDesc.setXmlType(new javax.xml.namespace.QName(
		"http://profile.miniDao/", "patientList"));
	org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("ID");
	elemField.setXmlName(new javax.xml.namespace.QName(
		"http://profile.miniDao/", "ID"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "int"));
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("firstName");
	elemField.setXmlName(new javax.xml.namespace.QName(
		"http://profile.miniDao/", "FirstName"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "string"));
	elemField.setMinOccurs(0);
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("lastName");
	elemField.setXmlName(new javax.xml.namespace.QName(
		"http://profile.miniDao/", "LastName"));
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
