/**
 * Food.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.miniDao;

public class Food implements java.io.Serializable {
    private int ID;

    private java.lang.String name;

    private int categoryID;

    private int subCategoryID;

    private double kCal;

    private double carbohydrates;

    private double proteins;

    private double fat;

    private int author_id;

    public Food() {
    }

    public Food(int ID, java.lang.String name, int categoryID,
	    int subCategoryID, double kCal, double carbohydrates,
	    double proteins, double fat, int author_id) {
	this.ID = ID;
	this.name = name;
	this.categoryID = categoryID;
	this.subCategoryID = subCategoryID;
	this.kCal = kCal;
	this.carbohydrates = carbohydrates;
	this.proteins = proteins;
	this.fat = fat;
	this.author_id = author_id;
    }

    /**
     * Gets the ID value for this Food.
     * 
     * @return ID
     */
    public int getID() {
	return ID;
    }

    /**
     * Sets the ID value for this Food.
     * 
     * @param ID
     */
    public void setID(int ID) {
	this.ID = ID;
    }

    /**
     * Gets the name value for this Food.
     * 
     * @return name
     */
    public java.lang.String getName() {
	return name;
    }

    /**
     * Sets the name value for this Food.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
	this.name = name;
    }

    /**
     * Gets the categoryID value for this Food.
     * 
     * @return categoryID
     */
    public int getCategoryID() {
	return categoryID;
    }

    /**
     * Sets the categoryID value for this Food.
     * 
     * @param categoryID
     */
    public void setCategoryID(int categoryID) {
	this.categoryID = categoryID;
    }

    /**
     * Gets the subCategoryID value for this Food.
     * 
     * @return subCategoryID
     */
    public int getSubCategoryID() {
	return subCategoryID;
    }

    /**
     * Sets the subCategoryID value for this Food.
     * 
     * @param subCategoryID
     */
    public void setSubCategoryID(int subCategoryID) {
	this.subCategoryID = subCategoryID;
    }

    /**
     * Gets the kCal value for this Food.
     * 
     * @return kCal
     */
    public double getKCal() {
	return kCal;
    }

    /**
     * Sets the kCal value for this Food.
     * 
     * @param kCal
     */
    public void setKCal(double kCal) {
	this.kCal = kCal;
    }

    /**
     * Gets the carbohydrates value for this Food.
     * 
     * @return carbohydrates
     */
    public double getCarbohydrates() {
	return carbohydrates;
    }

    /**
     * Sets the carbohydrates value for this Food.
     * 
     * @param carbohydrates
     */
    public void setCarbohydrates(double carbohydrates) {
	this.carbohydrates = carbohydrates;
    }

    /**
     * Gets the proteins value for this Food.
     * 
     * @return proteins
     */
    public double getProteins() {
	return proteins;
    }

    /**
     * Sets the proteins value for this Food.
     * 
     * @param proteins
     */
    public void setProteins(double proteins) {
	this.proteins = proteins;
    }

    /**
     * Gets the fat value for this Food.
     * 
     * @return fat
     */
    public double getFat() {
	return fat;
    }

    /**
     * Sets the fat value for this Food.
     * 
     * @param fat
     */
    public void setFat(double fat) {
	this.fat = fat;
    }

    /**
     * Gets the author_id value for this Food.
     * 
     * @return author_id
     */
    public int getAuthor_id() {
	return author_id;
    }

    /**
     * Sets the author_id value for this Food.
     * 
     * @param author_id
     */
    public void setAuthor_id(int author_id) {
	this.author_id = author_id;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
	if (!(obj instanceof Food))
	    return false;
	Food other = (Food) obj;
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
		&& ((this.name == null && other.getName() == null) || (this.name != null && this.name
			.equals(other.getName())))
		&& this.categoryID == other.getCategoryID()
		&& this.subCategoryID == other.getSubCategoryID()
		&& this.kCal == other.getKCal()
		&& this.carbohydrates == other.getCarbohydrates()
		&& this.proteins == other.getProteins()
		&& this.fat == other.getFat()
		&& this.author_id == other.getAuthor_id();
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
	if (getName() != null) {
	    _hashCode += getName().hashCode();
	}
	_hashCode += getCategoryID();
	_hashCode += getSubCategoryID();
	_hashCode += new Double(getKCal()).hashCode();
	_hashCode += new Double(getCarbohydrates()).hashCode();
	_hashCode += new Double(getProteins()).hashCode();
	_hashCode += new Double(getFat()).hashCode();
	_hashCode += getAuthor_id();
	__hashCodeCalc = false;
	return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
	    Food.class, true);

    static {
	typeDesc.setXmlType(new javax.xml.namespace.QName("http://miniDao/",
		"food"));
	org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("ID");
	elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/",
		"ID"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "int"));
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("name");
	elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/",
		"name"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "string"));
	elemField.setMinOccurs(0);
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("categoryID");
	elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/",
		"categoryID"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "int"));
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("subCategoryID");
	elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/",
		"subCategoryID"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "int"));
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("KCal");
	elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/",
		"kCal"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "double"));
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("carbohydrates");
	elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/",
		"carbohydrates"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "double"));
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("proteins");
	elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/",
		"proteins"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "double"));
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("fat");
	elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/",
		"fat"));
	elemField.setXmlType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "double"));
	elemField.setNillable(false);
	typeDesc.addFieldDesc(elemField);
	elemField = new org.apache.axis.description.ElementDesc();
	elemField.setFieldName("author_id");
	elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/",
		"author_id"));
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
