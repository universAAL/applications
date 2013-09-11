/**
 * PatientPicture.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.miniDao.profile;

public class PatientPicture implements java.io.Serializable {
    private int patientID;

    private byte[] imageBytes;

    public PatientPicture() {
    }

    public PatientPicture(int patientID, byte[] imageBytes) {
	this.patientID = patientID;
	this.imageBytes = imageBytes;
    }

    /**
     * Gets the patientID value for this PatientPicture.
     * 
     * @return patientID
     */
    public int getPatientID() {
	return patientID;
    }

    /**
     * Sets the patientID value for this PatientPicture.
     * 
     * @param patientID
     */
    public void setPatientID(int patientID) {
	this.patientID = patientID;
    }

    /**
     * Gets the imageBytes value for this PatientPicture.
     * 
     * @return imageBytes
     */
    public byte[] getImageBytes() {
	return imageBytes;
    }

    /**
     * Sets the imageBytes value for this PatientPicture.
     * 
     * @param imageBytes
     */
    public void setImageBytes(byte[] imageBytes) {
	this.imageBytes = imageBytes;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
	if (!(obj instanceof PatientPicture))
	    return false;
	PatientPicture other = (PatientPicture) obj;
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
		&& this.patientID == other.getPatientID()
		&& ((this.imageBytes == null && other.getImageBytes() == null) || (this.imageBytes != null && java.util.Arrays
			.equals(this.imageBytes, other.getImageBytes())));
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
	_hashCode += getPatientID();
	if (getImageBytes() != null) {
	    for (int i = 0; i < java.lang.reflect.Array
		    .getLength(getImageBytes()); i++) {
		java.lang.Object obj = java.lang.reflect.Array.get(
			getImageBytes(), i);
		if (obj != null && !obj.getClass().isArray()) {
		    _hashCode += obj.hashCode();
		}
	    }
	}
	__hashCodeCalc = false;
	return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
	    PatientPicture.class, true);

    static {
	typeDesc.setXmlType(new javax.xml.namespace.QName(
		"http://profile.miniDao/", "patientPicture"));
	org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("patientID");
	elemField.setXmlName(new javax.xml.namespace.QName(
		"http://profile.miniDao/", "patientID"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "int"));
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("imageBytes");
	elemField.setXmlName(new javax.xml.namespace.QName(
		"http://profile.miniDao/", "imageBytes"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "base64Binary"));
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
