/**
 * Answer.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.miniDao;

public class Answer implements java.io.Serializable {
    private int ID;

    private int order;

    private java.lang.String score;

    private java.lang.String answerText;

    private int type;

    private java.lang.String minValue;

    private java.lang.String maxValue;

    private java.lang.String userValue;

    private java.lang.String defaultValue;

    public Answer() {
    }

    public Answer(int ID, int order, java.lang.String score,
	    java.lang.String answerText, int type, java.lang.String minValue,
	    java.lang.String maxValue, java.lang.String userValue,
	    java.lang.String defaultValue) {
	this.ID = ID;
	this.order = order;
	this.score = score;
	this.answerText = answerText;
	this.type = type;
	this.minValue = minValue;
	this.maxValue = maxValue;
	this.userValue = userValue;
	this.defaultValue = defaultValue;
    }

    /**
     * Gets the ID value for this Answer.
     * 
     * @return ID
     */
    public int getID() {
	return ID;
    }

    /**
     * Sets the ID value for this Answer.
     * 
     * @param ID
     */
    public void setID(int ID) {
	this.ID = ID;
    }

    /**
     * Gets the order value for this Answer.
     * 
     * @return order
     */
    public int getOrder() {
	return order;
    }

    /**
     * Sets the order value for this Answer.
     * 
     * @param order
     */
    public void setOrder(int order) {
	this.order = order;
    }

    /**
     * Gets the score value for this Answer.
     * 
     * @return score
     */
    public java.lang.String getScore() {
	return score;
    }

    /**
     * Sets the score value for this Answer.
     * 
     * @param score
     */
    public void setScore(java.lang.String score) {
	this.score = score;
    }

    /**
     * Gets the answerText value for this Answer.
     * 
     * @return answerText
     */
    public java.lang.String getAnswerText() {
	return answerText;
    }

    /**
     * Sets the answerText value for this Answer.
     * 
     * @param answerText
     */
    public void setAnswerText(java.lang.String answerText) {
	this.answerText = answerText;
    }

    /**
     * Gets the type value for this Answer.
     * 
     * @return type
     */
    public int getType() {
	return type;
    }

    /**
     * Sets the type value for this Answer.
     * 
     * @param type
     */
    public void setType(int type) {
	this.type = type;
    }

    /**
     * Gets the minValue value for this Answer.
     * 
     * @return minValue
     */
    public java.lang.String getMinValue() {
	return minValue;
    }

    /**
     * Sets the minValue value for this Answer.
     * 
     * @param minValue
     */
    public void setMinValue(java.lang.String minValue) {
	this.minValue = minValue;
    }

    /**
     * Gets the maxValue value for this Answer.
     * 
     * @return maxValue
     */
    public java.lang.String getMaxValue() {
	return maxValue;
    }

    /**
     * Sets the maxValue value for this Answer.
     * 
     * @param maxValue
     */
    public void setMaxValue(java.lang.String maxValue) {
	this.maxValue = maxValue;
    }

    /**
     * Gets the userValue value for this Answer.
     * 
     * @return userValue
     */
    public java.lang.String getUserValue() {
	return userValue;
    }

    /**
     * Sets the userValue value for this Answer.
     * 
     * @param userValue
     */
    public void setUserValue(java.lang.String userValue) {
	this.userValue = userValue;
    }

    /**
     * Gets the defaultValue value for this Answer.
     * 
     * @return defaultValue
     */
    public java.lang.String getDefaultValue() {
	return defaultValue;
    }

    /**
     * Sets the defaultValue value for this Answer.
     * 
     * @param defaultValue
     */
    public void setDefaultValue(java.lang.String defaultValue) {
	this.defaultValue = defaultValue;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
	if (!(obj instanceof Answer))
	    return false;
	Answer other = (Answer) obj;
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
		&& this.order == other.getOrder()
		&& ((this.score == null && other.getScore() == null) || (this.score != null && this.score
			.equals(other.getScore())))
		&& ((this.answerText == null && other.getAnswerText() == null) || (this.answerText != null && this.answerText
			.equals(other.getAnswerText())))
		&& this.type == other.getType()
		&& ((this.minValue == null && other.getMinValue() == null) || (this.minValue != null && this.minValue
			.equals(other.getMinValue())))
		&& ((this.maxValue == null && other.getMaxValue() == null) || (this.maxValue != null && this.maxValue
			.equals(other.getMaxValue())))
		&& ((this.userValue == null && other.getUserValue() == null) || (this.userValue != null && this.userValue
			.equals(other.getUserValue())))
		&& ((this.defaultValue == null && other.getDefaultValue() == null) || (this.defaultValue != null && this.defaultValue
			.equals(other.getDefaultValue())));
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
	_hashCode += getOrder();
	if (getScore() != null) {
	    _hashCode += getScore().hashCode();
	}
	if (getAnswerText() != null) {
	    _hashCode += getAnswerText().hashCode();
	}
	_hashCode += getType();
	if (getMinValue() != null) {
	    _hashCode += getMinValue().hashCode();
	}
	if (getMaxValue() != null) {
	    _hashCode += getMaxValue().hashCode();
	}
	if (getUserValue() != null) {
	    _hashCode += getUserValue().hashCode();
	}
	if (getDefaultValue() != null) {
	    _hashCode += getDefaultValue().hashCode();
	}
	__hashCodeCalc = false;
	return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
	    Answer.class, true);

    static {
	typeDesc.setXmlType(new javax.xml.namespace.QName("http://miniDao/",
		"answer"));
	org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("ID");
	elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/",
		"ID"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "int"));
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("order");
	elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/",
		"order"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "int"));
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("score");
	elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/",
		"score"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "string"));
	elemField.setMinOccurs(0);
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("answerText");
	elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/",
		"answerText"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "string"));
	elemField.setMinOccurs(0);
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("type");
	elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/",
		"type"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "int"));
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("minValue");
	elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/",
		"minValue"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "string"));
	elemField.setMinOccurs(0);
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("maxValue");
	elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/",
		"maxValue"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "string"));
	elemField.setMinOccurs(0);
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("userValue");
	elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/",
		"userValue"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "string"));
	elemField.setMinOccurs(0);
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("defaultValue");
	elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/",
		"defaultValue"));
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
