/**
 * FoodCategory.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.miniDao.full;

public class FoodCategory  implements java.io.Serializable {
    private int ID;

    private java.lang.String name;

    private na.miniDao.full.FoodSubcategory[] subCategories;

    public FoodCategory() {
    }

    public FoodCategory(
           int ID,
           java.lang.String name,
           na.miniDao.full.FoodSubcategory[] subCategories) {
           this.ID = ID;
           this.name = name;
           this.subCategories = subCategories;
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
     * Gets the name value for this FoodCategory.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this FoodCategory.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the subCategories value for this FoodCategory.
     * 
     * @return subCategories
     */
    public na.miniDao.full.FoodSubcategory[] getSubCategories() {
        return subCategories;
    }


    /**
     * Sets the subCategories value for this FoodCategory.
     * 
     * @param subCategories
     */
    public void setSubCategories(na.miniDao.full.FoodSubcategory[] subCategories) {
        this.subCategories = subCategories;
    }

    public na.miniDao.full.FoodSubcategory getSubCategories(int i) {
        return this.subCategories[i];
    }

    public void setSubCategories(int i, na.miniDao.full.FoodSubcategory _value) {
        this.subCategories[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FoodCategory)) return false;
        FoodCategory other = (FoodCategory) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.ID == other.getID() &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.subCategories==null && other.getSubCategories()==null) || 
             (this.subCategories!=null &&
              java.util.Arrays.equals(this.subCategories, other.getSubCategories())));
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
        if (getSubCategories() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSubCategories());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSubCategories(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FoodCategory.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://full.miniDao/", "foodCategory"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://full.miniDao/", "ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://full.miniDao/", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subCategories");
        elemField.setXmlName(new javax.xml.namespace.QName("http://full.miniDao/", "subCategories"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://full.miniDao/", "foodSubcategory"));
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
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
