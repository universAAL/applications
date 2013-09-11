/**
 * Translation.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.ws;

public class Translation implements java.io.Serializable {
    private java.lang.String fieldType;

    private java.lang.String id;

    private java.lang.String[] values;

    private java.lang.String[] translations;

    private int codeLang;

    public Translation() {
    }

    public Translation(java.lang.String fieldType, java.lang.String id,
	    java.lang.String[] values, java.lang.String[] translations,
	    int codeLang) {
	this.fieldType = fieldType;
	this.id = id;
	this.values = values;
	this.translations = translations;
	this.codeLang = codeLang;
    }

    /**
     * Gets the fieldType value for this Translation.
     * 
     * @return fieldType
     */
    public java.lang.String getFieldType() {
	return fieldType;
    }

    /**
     * Sets the fieldType value for this Translation.
     * 
     * @param fieldType
     */
    public void setFieldType(java.lang.String fieldType) {
	this.fieldType = fieldType;
    }

    /**
     * Gets the id value for this Translation.
     * 
     * @return id
     */
    public java.lang.String getId() {
	return id;
    }

    /**
     * Sets the id value for this Translation.
     * 
     * @param id
     */
    public void setId(java.lang.String id) {
	this.id = id;
    }

    /**
     * Gets the values value for this Translation.
     * 
     * @return values
     */
    public java.lang.String[] getValues() {
	return values;
    }

    /**
     * Sets the values value for this Translation.
     * 
     * @param values
     */
    public void setValues(java.lang.String[] values) {
	this.values = values;
    }

    public java.lang.String getValues(int i) {
	return this.values[i];
    }

    public void setValues(int i, java.lang.String _value) {
	this.values[i] = _value;
    }

    /**
     * Gets the translations value for this Translation.
     * 
     * @return translations
     */
    public java.lang.String[] getTranslations() {
	return translations;
    }

    /**
     * Sets the translations value for this Translation.
     * 
     * @param translations
     */
    public void setTranslations(java.lang.String[] translations) {
	this.translations = translations;
    }

    public java.lang.String getTranslations(int i) {
	return this.translations[i];
    }

    public void setTranslations(int i, java.lang.String _value) {
	this.translations[i] = _value;
    }

    /**
     * Gets the codeLang value for this Translation.
     * 
     * @return codeLang
     */
    public int getCodeLang() {
	return codeLang;
    }

    /**
     * Sets the codeLang value for this Translation.
     * 
     * @param codeLang
     */
    public void setCodeLang(int codeLang) {
	this.codeLang = codeLang;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
	if (!(obj instanceof Translation))
	    return false;
	Translation other = (Translation) obj;
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
		&& ((this.fieldType == null && other.getFieldType() == null) || (this.fieldType != null && this.fieldType
			.equals(other.getFieldType())))
		&& ((this.id == null && other.getId() == null) || (this.id != null && this.id
			.equals(other.getId())))
		&& ((this.values == null && other.getValues() == null) || (this.values != null && java.util.Arrays
			.equals(this.values, other.getValues())))
		&& ((this.translations == null && other.getTranslations() == null) || (this.translations != null && java.util.Arrays
			.equals(this.translations, other.getTranslations())))
		&& this.codeLang == other.getCodeLang();
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
	if (getFieldType() != null) {
	    _hashCode += getFieldType().hashCode();
	}
	if (getId() != null) {
	    _hashCode += getId().hashCode();
	}
	if (getValues() != null) {
	    for (int i = 0; i < java.lang.reflect.Array.getLength(getValues()); i++) {
		java.lang.Object obj = java.lang.reflect.Array.get(getValues(),
			i);
		if (obj != null && !obj.getClass().isArray()) {
		    _hashCode += obj.hashCode();
		}
	    }
	}
	if (getTranslations() != null) {
	    for (int i = 0; i < java.lang.reflect.Array
		    .getLength(getTranslations()); i++) {
		java.lang.Object obj = java.lang.reflect.Array.get(
			getTranslations(), i);
		if (obj != null && !obj.getClass().isArray()) {
		    _hashCode += obj.hashCode();
		}
	    }
	}
	_hashCode += getCodeLang();
	__hashCodeCalc = false;
	return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
	    Translation.class, true);

    static {
	typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.na/",
		"translation"));
	org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("fieldType");
	elemField.setXmlName(new javax.xml.namespace.QName("", "fieldType"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "string"));
	elemField.setMinOccurs(0);
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("id");
	elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "string"));
	elemField.setMinOccurs(0);
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("values");
	elemField.setXmlName(new javax.xml.namespace.QName("", "values"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "string"));
	elemField.setMinOccurs(0);
	elemField.setNillable(false);
	elemField.setMaxOccursUnbounded(true);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("translations");
	elemField.setXmlName(new javax.xml.namespace.QName("", "translations"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "string"));
	elemField.setMinOccurs(0);
	elemField.setNillable(false);
	elemField.setMaxOccursUnbounded(true);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("codeLang");
	elemField.setXmlName(new javax.xml.namespace.QName("", "codeLang"));
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
