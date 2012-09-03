/**
 * FoodItem.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.miniDao;

public class FoodItem  implements java.io.Serializable {
    private int foodID;

    private int shoppingListID;

    private java.lang.String name;

    private java.lang.String category;

    private java.lang.String amount;

    private java.lang.String units;

    private java.lang.String source;

    private int foodSubCategoryID;

    private int foodCategoryID;

    public FoodItem() {
    }

    public FoodItem(
           int foodID,
           int shoppingListID,
           java.lang.String name,
           java.lang.String category,
           java.lang.String amount,
           java.lang.String units,
           java.lang.String source,
           int foodSubCategoryID,
           int foodCategoryID) {
           this.foodID = foodID;
           this.shoppingListID = shoppingListID;
           this.name = name;
           this.category = category;
           this.amount = amount;
           this.units = units;
           this.source = source;
           this.foodSubCategoryID = foodSubCategoryID;
           this.foodCategoryID = foodCategoryID;
    }


    /**
     * Gets the foodID value for this FoodItem.
     * 
     * @return foodID
     */
    public int getFoodID() {
        return foodID;
    }


    /**
     * Sets the foodID value for this FoodItem.
     * 
     * @param foodID
     */
    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }


    /**
     * Gets the shoppingListID value for this FoodItem.
     * 
     * @return shoppingListID
     */
    public int getShoppingListID() {
        return shoppingListID;
    }


    /**
     * Sets the shoppingListID value for this FoodItem.
     * 
     * @param shoppingListID
     */
    public void setShoppingListID(int shoppingListID) {
        this.shoppingListID = shoppingListID;
    }


    /**
     * Gets the name value for this FoodItem.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this FoodItem.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the category value for this FoodItem.
     * 
     * @return category
     */
    public java.lang.String getCategory() {
        return category;
    }


    /**
     * Sets the category value for this FoodItem.
     * 
     * @param category
     */
    public void setCategory(java.lang.String category) {
        this.category = category;
    }


    /**
     * Gets the amount value for this FoodItem.
     * 
     * @return amount
     */
    public java.lang.String getAmount() {
        return amount;
    }


    /**
     * Sets the amount value for this FoodItem.
     * 
     * @param amount
     */
    public void setAmount(java.lang.String amount) {
        this.amount = amount;
    }


    /**
     * Gets the units value for this FoodItem.
     * 
     * @return units
     */
    public java.lang.String getUnits() {
        return units;
    }


    /**
     * Sets the units value for this FoodItem.
     * 
     * @param units
     */
    public void setUnits(java.lang.String units) {
        this.units = units;
    }


    /**
     * Gets the source value for this FoodItem.
     * 
     * @return source
     */
    public java.lang.String getSource() {
        return source;
    }


    /**
     * Sets the source value for this FoodItem.
     * 
     * @param source
     */
    public void setSource(java.lang.String source) {
        this.source = source;
    }


    /**
     * Gets the foodSubCategoryID value for this FoodItem.
     * 
     * @return foodSubCategoryID
     */
    public int getFoodSubCategoryID() {
        return foodSubCategoryID;
    }


    /**
     * Sets the foodSubCategoryID value for this FoodItem.
     * 
     * @param foodSubCategoryID
     */
    public void setFoodSubCategoryID(int foodSubCategoryID) {
        this.foodSubCategoryID = foodSubCategoryID;
    }


    /**
     * Gets the foodCategoryID value for this FoodItem.
     * 
     * @return foodCategoryID
     */
    public int getFoodCategoryID() {
        return foodCategoryID;
    }


    /**
     * Sets the foodCategoryID value for this FoodItem.
     * 
     * @param foodCategoryID
     */
    public void setFoodCategoryID(int foodCategoryID) {
        this.foodCategoryID = foodCategoryID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FoodItem)) return false;
        FoodItem other = (FoodItem) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.foodID == other.getFoodID() &&
            this.shoppingListID == other.getShoppingListID() &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.category==null && other.getCategory()==null) || 
             (this.category!=null &&
              this.category.equals(other.getCategory()))) &&
            ((this.amount==null && other.getAmount()==null) || 
             (this.amount!=null &&
              this.amount.equals(other.getAmount()))) &&
            ((this.units==null && other.getUnits()==null) || 
             (this.units!=null &&
              this.units.equals(other.getUnits()))) &&
            ((this.source==null && other.getSource()==null) || 
             (this.source!=null &&
              this.source.equals(other.getSource()))) &&
            this.foodSubCategoryID == other.getFoodSubCategoryID() &&
            this.foodCategoryID == other.getFoodCategoryID();
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
        _hashCode += getFoodID();
        _hashCode += getShoppingListID();
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getCategory() != null) {
            _hashCode += getCategory().hashCode();
        }
        if (getAmount() != null) {
            _hashCode += getAmount().hashCode();
        }
        if (getUnits() != null) {
            _hashCode += getUnits().hashCode();
        }
        if (getSource() != null) {
            _hashCode += getSource().hashCode();
        }
        _hashCode += getFoodSubCategoryID();
        _hashCode += getFoodCategoryID();
        __hashCodeCalc = false;
        return _hashCode;
    }


}
