/**
 * RecipeIngredient.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.miniDao;

public class RecipeIngredient  implements java.io.Serializable {
    private int foodID;

    private int recipeIngredientsID;

    private int measUnitsID;

    private int foodCategoryID;

    private int foodSubCategoryID;

    private java.lang.String picture;

    private java.lang.String foodSubCategory;

    private java.lang.String foodCategory;

    private java.lang.String description;

    private java.lang.String measureDescription;

    private double quantity;

    public RecipeIngredient() {
    }

    public RecipeIngredient(
           int foodID,
           int recipeIngredientsID,
           int measUnitsID,
           int foodCategoryID,
           int foodSubCategoryID,
           java.lang.String picture,
           java.lang.String foodSubCategory,
           java.lang.String foodCategory,
           java.lang.String description,
           java.lang.String measureDescription,
           double quantity) {
           this.foodID = foodID;
           this.recipeIngredientsID = recipeIngredientsID;
           this.measUnitsID = measUnitsID;
           this.foodCategoryID = foodCategoryID;
           this.foodSubCategoryID = foodSubCategoryID;
           this.picture = picture;
           this.foodSubCategory = foodSubCategory;
           this.foodCategory = foodCategory;
           this.description = description;
           this.measureDescription = measureDescription;
           this.quantity = quantity;
    }


    /**
     * Gets the foodID value for this RecipeIngredient.
     * 
     * @return foodID
     */
    public int getFoodID() {
        return foodID;
    }


    /**
     * Sets the foodID value for this RecipeIngredient.
     * 
     * @param foodID
     */
    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }


    /**
     * Gets the recipeIngredientsID value for this RecipeIngredient.
     * 
     * @return recipeIngredientsID
     */
    public int getRecipeIngredientsID() {
        return recipeIngredientsID;
    }


    /**
     * Sets the recipeIngredientsID value for this RecipeIngredient.
     * 
     * @param recipeIngredientsID
     */
    public void setRecipeIngredientsID(int recipeIngredientsID) {
        this.recipeIngredientsID = recipeIngredientsID;
    }


    /**
     * Gets the measUnitsID value for this RecipeIngredient.
     * 
     * @return measUnitsID
     */
    public int getMeasUnitsID() {
        return measUnitsID;
    }


    /**
     * Sets the measUnitsID value for this RecipeIngredient.
     * 
     * @param measUnitsID
     */
    public void setMeasUnitsID(int measUnitsID) {
        this.measUnitsID = measUnitsID;
    }


    /**
     * Gets the foodCategoryID value for this RecipeIngredient.
     * 
     * @return foodCategoryID
     */
    public int getFoodCategoryID() {
        return foodCategoryID;
    }


    /**
     * Sets the foodCategoryID value for this RecipeIngredient.
     * 
     * @param foodCategoryID
     */
    public void setFoodCategoryID(int foodCategoryID) {
        this.foodCategoryID = foodCategoryID;
    }


    /**
     * Gets the foodSubCategoryID value for this RecipeIngredient.
     * 
     * @return foodSubCategoryID
     */
    public int getFoodSubCategoryID() {
        return foodSubCategoryID;
    }


    /**
     * Sets the foodSubCategoryID value for this RecipeIngredient.
     * 
     * @param foodSubCategoryID
     */
    public void setFoodSubCategoryID(int foodSubCategoryID) {
        this.foodSubCategoryID = foodSubCategoryID;
    }


    /**
     * Gets the picture value for this RecipeIngredient.
     * 
     * @return picture
     */
    public java.lang.String getPicture() {
        return picture;
    }


    /**
     * Sets the picture value for this RecipeIngredient.
     * 
     * @param picture
     */
    public void setPicture(java.lang.String picture) {
        this.picture = picture;
    }


    /**
     * Gets the foodSubCategory value for this RecipeIngredient.
     * 
     * @return foodSubCategory
     */
    public java.lang.String getFoodSubCategory() {
        return foodSubCategory;
    }


    /**
     * Sets the foodSubCategory value for this RecipeIngredient.
     * 
     * @param foodSubCategory
     */
    public void setFoodSubCategory(java.lang.String foodSubCategory) {
        this.foodSubCategory = foodSubCategory;
    }


    /**
     * Gets the foodCategory value for this RecipeIngredient.
     * 
     * @return foodCategory
     */
    public java.lang.String getFoodCategory() {
        return foodCategory;
    }


    /**
     * Sets the foodCategory value for this RecipeIngredient.
     * 
     * @param foodCategory
     */
    public void setFoodCategory(java.lang.String foodCategory) {
        this.foodCategory = foodCategory;
    }


    /**
     * Gets the description value for this RecipeIngredient.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this RecipeIngredient.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the measureDescription value for this RecipeIngredient.
     * 
     * @return measureDescription
     */
    public java.lang.String getMeasureDescription() {
        return measureDescription;
    }


    /**
     * Sets the measureDescription value for this RecipeIngredient.
     * 
     * @param measureDescription
     */
    public void setMeasureDescription(java.lang.String measureDescription) {
        this.measureDescription = measureDescription;
    }


    /**
     * Gets the quantity value for this RecipeIngredient.
     * 
     * @return quantity
     */
    public double getQuantity() {
        return quantity;
    }


    /**
     * Sets the quantity value for this RecipeIngredient.
     * 
     * @param quantity
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RecipeIngredient)) return false;
        RecipeIngredient other = (RecipeIngredient) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.foodID == other.getFoodID() &&
            this.recipeIngredientsID == other.getRecipeIngredientsID() &&
            this.measUnitsID == other.getMeasUnitsID() &&
            this.foodCategoryID == other.getFoodCategoryID() &&
            this.foodSubCategoryID == other.getFoodSubCategoryID() &&
            ((this.picture==null && other.getPicture()==null) || 
             (this.picture!=null &&
              this.picture.equals(other.getPicture()))) &&
            ((this.foodSubCategory==null && other.getFoodSubCategory()==null) || 
             (this.foodSubCategory!=null &&
              this.foodSubCategory.equals(other.getFoodSubCategory()))) &&
            ((this.foodCategory==null && other.getFoodCategory()==null) || 
             (this.foodCategory!=null &&
              this.foodCategory.equals(other.getFoodCategory()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.measureDescription==null && other.getMeasureDescription()==null) || 
             (this.measureDescription!=null &&
              this.measureDescription.equals(other.getMeasureDescription()))) &&
            this.quantity == other.getQuantity();
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
        _hashCode += getRecipeIngredientsID();
        _hashCode += getMeasUnitsID();
        _hashCode += getFoodCategoryID();
        _hashCode += getFoodSubCategoryID();
        if (getPicture() != null) {
            _hashCode += getPicture().hashCode();
        }
        if (getFoodSubCategory() != null) {
            _hashCode += getFoodSubCategory().hashCode();
        }
        if (getFoodCategory() != null) {
            _hashCode += getFoodCategory().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getMeasureDescription() != null) {
            _hashCode += getMeasureDescription().hashCode();
        }
        _hashCode += new Double(getQuantity()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }


}
