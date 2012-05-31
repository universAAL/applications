/**
 * ShoppingList.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.miniDao;

public class ShoppingList  implements java.io.Serializable {
    private int userID;

    private na.miniDao.FoodItem[] items;

    public ShoppingList() {
    }

    public ShoppingList(
           int userID,
           na.miniDao.FoodItem[] items) {
           this.userID = userID;
           this.items = items;
    }


    /**
     * Gets the userID value for this ShoppingList.
     * 
     * @return userID
     */
    public int getUserID() {
        return userID;
    }


    /**
     * Sets the userID value for this ShoppingList.
     * 
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }


    /**
     * Gets the items value for this ShoppingList.
     * 
     * @return items
     */
    public na.miniDao.FoodItem[] getItems() {
        return items;
    }


    /**
     * Sets the items value for this ShoppingList.
     * 
     * @param items
     */
    public void setItems(na.miniDao.FoodItem[] items) {
        this.items = items;
    }

    public na.miniDao.FoodItem getItems(int i) {
        return this.items[i];
    }

    public void setItems(int i, na.miniDao.FoodItem _value) {
        this.items[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ShoppingList)) return false;
        ShoppingList other = (ShoppingList) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.userID == other.getUserID() &&
            ((this.items==null && other.getItems()==null) || 
             (this.items!=null &&
              java.util.Arrays.equals(this.items, other.getItems())));
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
        _hashCode += getUserID();
        if (getItems() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getItems());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getItems(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }


}
