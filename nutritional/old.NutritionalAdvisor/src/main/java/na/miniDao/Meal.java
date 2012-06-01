/**
 * Meal.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.miniDao;

public class Meal  implements java.io.Serializable {
    private int usersMenuMealsID;

    private java.lang.String category;

    private na.miniDao.Dish[] dishes;

    public Meal() {
    }

    public Meal(
           int usersMenuMealsID,
           java.lang.String category,
           na.miniDao.Dish[] dishes) {
           this.usersMenuMealsID = usersMenuMealsID;
           this.category = category;
           this.dishes = dishes;
    }


    /**
     * Gets the usersMenuMealsID value for this Meal.
     * 
     * @return usersMenuMealsID
     */
    public int getUsersMenuMealsID() {
        return usersMenuMealsID;
    }


    /**
     * Sets the usersMenuMealsID value for this Meal.
     * 
     * @param usersMenuMealsID
     */
    public void setUsersMenuMealsID(int usersMenuMealsID) {
        this.usersMenuMealsID = usersMenuMealsID;
    }


    /**
     * Gets the category value for this Meal.
     * 
     * @return category
     */
    public java.lang.String getCategory() {
        return category;
    }


    /**
     * Sets the category value for this Meal.
     * 
     * @param category
     */
    public void setCategory(java.lang.String category) {
        this.category = category;
    }


    /**
     * Gets the dishes value for this Meal.
     * 
     * @return dishes
     */
    public na.miniDao.Dish[] getDishes() {
        return dishes;
    }


    /**
     * Sets the dishes value for this Meal.
     * 
     * @param dishes
     */
    public void setDishes(na.miniDao.Dish[] dishes) {
        this.dishes = dishes;
    }

    public na.miniDao.Dish getDishes(int i) {
        return this.dishes[i];
    }

    public void setDishes(int i, na.miniDao.Dish _value) {
        this.dishes[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Meal)) return false;
        Meal other = (Meal) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.usersMenuMealsID == other.getUsersMenuMealsID() &&
            ((this.category==null && other.getCategory()==null) || 
             (this.category!=null &&
              this.category.equals(other.getCategory()))) &&
            ((this.dishes==null && other.getDishes()==null) || 
             (this.dishes!=null &&
              java.util.Arrays.equals(this.dishes, other.getDishes())));
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
        _hashCode += getUsersMenuMealsID();
        if (getCategory() != null) {
            _hashCode += getCategory().hashCode();
        }
        if (getDishes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDishes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDishes(), i);
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
        new org.apache.axis.description.TypeDesc(Meal.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://miniDao/", "meal"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usersMenuMealsID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/", "usersMenuMealsID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("category");
        elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/", "category"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dishes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/", "dishes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://miniDao/", "dish"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
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
