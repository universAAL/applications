/**
 * Question.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.miniDao;

public class Question implements java.io.Serializable {
    private int ID;

    private int order;

    private java.lang.String multimediaType;

    private java.lang.String multimedia;

    private byte[] multimediaBytes;

    private java.lang.String questionText;

    private java.lang.String questionExplanation;

    private na.miniDao.Answer[] answers;

    private boolean last;

    private boolean first;

    private int userQuestionnaireID;

    private java.lang.String type;

    public Question() {
    }

    public Question(int ID, int order, java.lang.String multimediaType,
	    java.lang.String multimedia, byte[] multimediaBytes,
	    java.lang.String questionText,
	    java.lang.String questionExplanation, na.miniDao.Answer[] answers,
	    boolean last, boolean first, int userQuestionnaireID,
	    java.lang.String type) {
	this.ID = ID;
	this.order = order;
	this.multimediaType = multimediaType;
	this.multimedia = multimedia;
	this.multimediaBytes = multimediaBytes;
	this.questionText = questionText;
	this.questionExplanation = questionExplanation;
	this.answers = answers;
	this.last = last;
	this.first = first;
	this.userQuestionnaireID = userQuestionnaireID;
	this.type = type;
    }

    /**
     * Gets the ID value for this Question.
     * 
     * @return ID
     */
    public int getID() {
	return ID;
    }

    /**
     * Sets the ID value for this Question.
     * 
     * @param ID
     */
    public void setID(int ID) {
	this.ID = ID;
    }

    /**
     * Gets the order value for this Question.
     * 
     * @return order
     */
    public int getOrder() {
	return order;
    }

    /**
     * Sets the order value for this Question.
     * 
     * @param order
     */
    public void setOrder(int order) {
	this.order = order;
    }

    /**
     * Gets the multimediaType value for this Question.
     * 
     * @return multimediaType
     */
    public java.lang.String getMultimediaType() {
	return multimediaType;
    }

    /**
     * Sets the multimediaType value for this Question.
     * 
     * @param multimediaType
     */
    public void setMultimediaType(java.lang.String multimediaType) {
	this.multimediaType = multimediaType;
    }

    /**
     * Gets the multimedia value for this Question.
     * 
     * @return multimedia
     */
    public java.lang.String getMultimedia() {
	return multimedia;
    }

    /**
     * Sets the multimedia value for this Question.
     * 
     * @param multimedia
     */
    public void setMultimedia(java.lang.String multimedia) {
	this.multimedia = multimedia;
    }

    /**
     * Gets the multimediaBytes value for this Question.
     * 
     * @return multimediaBytes
     */
    public byte[] getMultimediaBytes() {
	return multimediaBytes;
    }

    /**
     * Sets the multimediaBytes value for this Question.
     * 
     * @param multimediaBytes
     */
    public void setMultimediaBytes(byte[] multimediaBytes) {
	this.multimediaBytes = multimediaBytes;
    }

    /**
     * Gets the questionText value for this Question.
     * 
     * @return questionText
     */
    public java.lang.String getQuestionText() {
	return questionText;
    }

    /**
     * Sets the questionText value for this Question.
     * 
     * @param questionText
     */
    public void setQuestionText(java.lang.String questionText) {
	this.questionText = questionText;
    }

    /**
     * Gets the questionExplanation value for this Question.
     * 
     * @return questionExplanation
     */
    public java.lang.String getQuestionExplanation() {
	return questionExplanation;
    }

    /**
     * Sets the questionExplanation value for this Question.
     * 
     * @param questionExplanation
     */
    public void setQuestionExplanation(java.lang.String questionExplanation) {
	this.questionExplanation = questionExplanation;
    }

    /**
     * Gets the answers value for this Question.
     * 
     * @return answers
     */
    public na.miniDao.Answer[] getAnswers() {
	return answers;
    }

    /**
     * Sets the answers value for this Question.
     * 
     * @param answers
     */
    public void setAnswers(na.miniDao.Answer[] answers) {
	this.answers = answers;
    }

    public na.miniDao.Answer getAnswers(int i) {
	return this.answers[i];
    }

    public void setAnswers(int i, na.miniDao.Answer _value) {
	this.answers[i] = _value;
    }

    /**
     * Gets the last value for this Question.
     * 
     * @return last
     */
    public boolean isLast() {
	return last;
    }

    /**
     * Sets the last value for this Question.
     * 
     * @param last
     */
    public void setLast(boolean last) {
	this.last = last;
    }

    /**
     * Gets the first value for this Question.
     * 
     * @return first
     */
    public boolean isFirst() {
	return first;
    }

    /**
     * Sets the first value for this Question.
     * 
     * @param first
     */
    public void setFirst(boolean first) {
	this.first = first;
    }

    /**
     * Gets the userQuestionnaireID value for this Question.
     * 
     * @return userQuestionnaireID
     */
    public int getUserQuestionnaireID() {
	return userQuestionnaireID;
    }

    /**
     * Sets the userQuestionnaireID value for this Question.
     * 
     * @param userQuestionnaireID
     */
    public void setUserQuestionnaireID(int userQuestionnaireID) {
	this.userQuestionnaireID = userQuestionnaireID;
    }

    /**
     * Gets the type value for this Question.
     * 
     * @return type
     */
    public java.lang.String getType() {
	return type;
    }

    /**
     * Sets the type value for this Question.
     * 
     * @param type
     */
    public void setType(java.lang.String type) {
	this.type = type;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
	if (!(obj instanceof Question))
	    return false;
	Question other = (Question) obj;
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
		&& ((this.multimediaType == null && other.getMultimediaType() == null) || (this.multimediaType != null && this.multimediaType
			.equals(other.getMultimediaType())))
		&& ((this.multimedia == null && other.getMultimedia() == null) || (this.multimedia != null && this.multimedia
			.equals(other.getMultimedia())))
		&& ((this.multimediaBytes == null && other.getMultimediaBytes() == null) || (this.multimediaBytes != null && java.util.Arrays
			.equals(this.multimediaBytes,
				other.getMultimediaBytes())))
		&& ((this.questionText == null && other.getQuestionText() == null) || (this.questionText != null && this.questionText
			.equals(other.getQuestionText())))
		&& ((this.questionExplanation == null && other
			.getQuestionExplanation() == null) || (this.questionExplanation != null && this.questionExplanation
			.equals(other.getQuestionExplanation())))
		&& ((this.answers == null && other.getAnswers() == null) || (this.answers != null && java.util.Arrays
			.equals(this.answers, other.getAnswers())))
		&& this.last == other.isLast()
		&& this.first == other.isFirst()
		&& this.userQuestionnaireID == other.getUserQuestionnaireID()
		&& ((this.type == null && other.getType() == null) || (this.type != null && this.type
			.equals(other.getType())));
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
	if (getMultimediaType() != null) {
	    _hashCode += getMultimediaType().hashCode();
	}
	if (getMultimedia() != null) {
	    _hashCode += getMultimedia().hashCode();
	}
	if (getMultimediaBytes() != null) {
	    for (int i = 0; i < java.lang.reflect.Array
		    .getLength(getMultimediaBytes()); i++) {
		java.lang.Object obj = java.lang.reflect.Array.get(
			getMultimediaBytes(), i);
		if (obj != null && !obj.getClass().isArray()) {
		    _hashCode += obj.hashCode();
		}
	    }
	}
	if (getQuestionText() != null) {
	    _hashCode += getQuestionText().hashCode();
	}
	if (getQuestionExplanation() != null) {
	    _hashCode += getQuestionExplanation().hashCode();
	}
	if (getAnswers() != null) {
	    for (int i = 0; i < java.lang.reflect.Array.getLength(getAnswers()); i++) {
		java.lang.Object obj = java.lang.reflect.Array.get(
			getAnswers(), i);
		if (obj != null && !obj.getClass().isArray()) {
		    _hashCode += obj.hashCode();
		}
	    }
	}
	_hashCode += (isLast() ? Boolean.TRUE : Boolean.FALSE).hashCode();
	_hashCode += (isFirst() ? Boolean.TRUE : Boolean.FALSE).hashCode();
	_hashCode += getUserQuestionnaireID();
	if (getType() != null) {
	    _hashCode += getType().hashCode();
	}
	__hashCodeCalc = false;
	return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
	    Question.class, true);

    static {
	typeDesc.setXmlType(new javax.xml.namespace.QName("http://miniDao/",
		"question"));
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
	elemField.setFieldName("multimediaType");
	elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/",
		"multimediaType"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "string"));
	elemField.setMinOccurs(0);
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("multimedia");
	elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/",
		"multimedia"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "string"));
	elemField.setMinOccurs(0);
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("multimediaBytes");
	elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/",
		"multimediaBytes"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "base64Binary"));
	elemField.setMinOccurs(0);
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("questionText");
	elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/",
		"questionText"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "string"));
	elemField.setMinOccurs(0);
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("questionExplanation");
	elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/",
		"questionExplanation"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "string"));
	elemField.setMinOccurs(0);
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("answers");
	elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/",
		"answers"));
	elemField.setXmlType(new javax.xml.namespace.QName("http://miniDao/",
		"answer"));
	elemField.setMinOccurs(0);
	elemField.setNillable(false);
	elemField.setMaxOccursUnbounded(true);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("last");
	elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/",
		"last"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "boolean"));
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("first");
	elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/",
		"first"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "boolean"));
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("userQuestionnaireID");
	elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/",
		"userQuestionnaireID"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "int"));
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("type");
	elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/",
		"type"));
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
