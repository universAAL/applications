/**
 * PatientIncompatibilities.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.miniDao.profile;

public class PatientIncompatibilities implements java.io.Serializable {
    private java.lang.String username;

    private int patientID;

    private int[] food_dislikes;

    private int[] food_allergies;

    private int[] food_intollerances;

    public PatientIncompatibilities() {
    }

    public PatientIncompatibilities(java.lang.String username, int patientID,
	    int[] food_dislikes, int[] food_allergies, int[] food_intollerances) {
	this.username = username;
	this.patientID = patientID;
	this.food_dislikes = food_dislikes;
	this.food_allergies = food_allergies;
	this.food_intollerances = food_intollerances;
    }

    /**
     * Gets the username value for this PatientIncompatibilities.
     * 
     * @return username
     */
    public java.lang.String getUsername() {
	return username;
    }

    /**
     * Sets the username value for this PatientIncompatibilities.
     * 
     * @param username
     */
    public void setUsername(java.lang.String username) {
	this.username = username;
    }

    /**
     * Gets the patientID value for this PatientIncompatibilities.
     * 
     * @return patientID
     */
    public int getPatientID() {
	return patientID;
    }

    /**
     * Sets the patientID value for this PatientIncompatibilities.
     * 
     * @param patientID
     */
    public void setPatientID(int patientID) {
	this.patientID = patientID;
    }

    /**
     * Gets the food_dislikes value for this PatientIncompatibilities.
     * 
     * @return food_dislikes
     */
    public int[] getFood_dislikes() {
	return food_dislikes;
    }

    /**
     * Sets the food_dislikes value for this PatientIncompatibilities.
     * 
     * @param food_dislikes
     */
    public void setFood_dislikes(int[] food_dislikes) {
	this.food_dislikes = food_dislikes;
    }

    public int getFood_dislikes(int i) {
	return this.food_dislikes[i];
    }

    public void setFood_dislikes(int i, int _value) {
	this.food_dislikes[i] = _value;
    }

    /**
     * Gets the food_allergies value for this PatientIncompatibilities.
     * 
     * @return food_allergies
     */
    public int[] getFood_allergies() {
	return food_allergies;
    }

    /**
     * Sets the food_allergies value for this PatientIncompatibilities.
     * 
     * @param food_allergies
     */
    public void setFood_allergies(int[] food_allergies) {
	this.food_allergies = food_allergies;
    }

    public int getFood_allergies(int i) {
	return this.food_allergies[i];
    }

    public void setFood_allergies(int i, int _value) {
	this.food_allergies[i] = _value;
    }

    /**
     * Gets the food_intollerances value for this PatientIncompatibilities.
     * 
     * @return food_intollerances
     */
    public int[] getFood_intollerances() {
	return food_intollerances;
    }

    /**
     * Sets the food_intollerances value for this PatientIncompatibilities.
     * 
     * @param food_intollerances
     */
    public void setFood_intollerances(int[] food_intollerances) {
	this.food_intollerances = food_intollerances;
    }

    public int getFood_intollerances(int i) {
	return this.food_intollerances[i];
    }

    public void setFood_intollerances(int i, int _value) {
	this.food_intollerances[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
	if (!(obj instanceof PatientIncompatibilities))
	    return false;
	PatientIncompatibilities other = (PatientIncompatibilities) obj;
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
		&& ((this.username == null && other.getUsername() == null) || (this.username != null && this.username
			.equals(other.getUsername())))
		&& this.patientID == other.getPatientID()
		&& ((this.food_dislikes == null && other.getFood_dislikes() == null) || (this.food_dislikes != null && java.util.Arrays
			.equals(this.food_dislikes, other.getFood_dislikes())))
		&& ((this.food_allergies == null && other.getFood_allergies() == null) || (this.food_allergies != null && java.util.Arrays
			.equals(this.food_allergies, other.getFood_allergies())))
		&& ((this.food_intollerances == null && other
			.getFood_intollerances() == null) || (this.food_intollerances != null && java.util.Arrays
			.equals(this.food_intollerances,
				other.getFood_intollerances())));
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
	if (getUsername() != null) {
	    _hashCode += getUsername().hashCode();
	}
	_hashCode += getPatientID();
	if (getFood_dislikes() != null) {
	    for (int i = 0; i < java.lang.reflect.Array
		    .getLength(getFood_dislikes()); i++) {
		java.lang.Object obj = java.lang.reflect.Array.get(
			getFood_dislikes(), i);
		if (obj != null && !obj.getClass().isArray()) {
		    _hashCode += obj.hashCode();
		}
	    }
	}
	if (getFood_allergies() != null) {
	    for (int i = 0; i < java.lang.reflect.Array
		    .getLength(getFood_allergies()); i++) {
		java.lang.Object obj = java.lang.reflect.Array.get(
			getFood_allergies(), i);
		if (obj != null && !obj.getClass().isArray()) {
		    _hashCode += obj.hashCode();
		}
	    }
	}
	if (getFood_intollerances() != null) {
	    for (int i = 0; i < java.lang.reflect.Array
		    .getLength(getFood_intollerances()); i++) {
		java.lang.Object obj = java.lang.reflect.Array.get(
			getFood_intollerances(), i);
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
	    PatientIncompatibilities.class, true);

    static {
	typeDesc.setXmlType(new javax.xml.namespace.QName(
		"http://profile.miniDao/", "patientIncompatibilities"));
	org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("username");
	elemField.setXmlName(new javax.xml.namespace.QName(
		"http://profile.miniDao/", "username"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "string"));
	elemField.setMinOccurs(0);
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("patientID");
	elemField.setXmlName(new javax.xml.namespace.QName(
		"http://profile.miniDao/", "patientID"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "int"));
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("food_dislikes");
	elemField.setXmlName(new javax.xml.namespace.QName(
		"http://profile.miniDao/", "food_dislikes"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "int"));
	elemField.setMinOccurs(0);
	elemField.setNillable(false);
	elemField.setMaxOccursUnbounded(true);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("food_allergies");
	elemField.setXmlName(new javax.xml.namespace.QName(
		"http://profile.miniDao/", "food_allergies"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "int"));
	elemField.setMinOccurs(0);
	elemField.setNillable(false);
	elemField.setMaxOccursUnbounded(true);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("food_intollerances");
	elemField.setXmlName(new javax.xml.namespace.QName(
		"http://profile.miniDao/", "food_intollerances"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "int"));
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
