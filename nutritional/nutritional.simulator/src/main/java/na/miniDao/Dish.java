/**
 * Dish.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.miniDao;

public class Dish  implements java.io.Serializable {
    private int usersMenuDishesID;

    private java.lang.String description;

    private int recipeID;

    private java.lang.String image;

    private byte[] imageBytes;

    private int dishCategory;

    private boolean hasProcedure;

    private boolean containsRecipe;

    public Dish() {
    }

    public Dish(
           int usersMenuDishesID,
           java.lang.String description,
           int recipeID,
           java.lang.String image,
           byte[] imageBytes,
           int dishCategory,
           boolean hasProcedure,
           boolean containsRecipe) {
           this.usersMenuDishesID = usersMenuDishesID;
           this.description = description;
           this.recipeID = recipeID;
           this.image = image;
           this.imageBytes = imageBytes;
           this.dishCategory = dishCategory;
           this.hasProcedure = hasProcedure;
           this.containsRecipe = containsRecipe;
    }


    /**
     * Gets the usersMenuDishesID value for this Dish.
     * 
     * @return usersMenuDishesID
     */
    public int getUsersMenuDishesID() {
        return usersMenuDishesID;
    }


    /**
     * Sets the usersMenuDishesID value for this Dish.
     * 
     * @param usersMenuDishesID
     */
    public void setUsersMenuDishesID(int usersMenuDishesID) {
        this.usersMenuDishesID = usersMenuDishesID;
    }


    /**
     * Gets the description value for this Dish.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this Dish.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the recipeID value for this Dish.
     * 
     * @return recipeID
     */
    public int getRecipeID() {
        return recipeID;
    }


    /**
     * Sets the recipeID value for this Dish.
     * 
     * @param recipeID
     */
    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }


    /**
     * Gets the image value for this Dish.
     * 
     * @return image
     */
    public java.lang.String getImage() {
        return image;
    }


    /**
     * Sets the image value for this Dish.
     * 
     * @param image
     */
    public void setImage(java.lang.String image) {
        this.image = image;
    }


    /**
     * Gets the imageBytes value for this Dish.
     * 
     * @return imageBytes
     */
    public byte[] getImageBytes() {
        return imageBytes;
    }


    /**
     * Sets the imageBytes value for this Dish.
     * 
     * @param imageBytes
     */
    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }


    /**
     * Gets the dishCategory value for this Dish.
     * 
     * @return dishCategory
     */
    public int getDishCategory() {
        return dishCategory;
    }


    /**
     * Sets the dishCategory value for this Dish.
     * 
     * @param dishCategory
     */
    public void setDishCategory(int dishCategory) {
        this.dishCategory = dishCategory;
    }


    /**
     * Gets the hasProcedure value for this Dish.
     * 
     * @return hasProcedure
     */
    public boolean isHasProcedure() {
        return hasProcedure;
    }


    /**
     * Sets the hasProcedure value for this Dish.
     * 
     * @param hasProcedure
     */
    public void setHasProcedure(boolean hasProcedure) {
        this.hasProcedure = hasProcedure;
    }


    /**
     * Gets the containsRecipe value for this Dish.
     * 
     * @return containsRecipe
     */
    public boolean isContainsRecipe() {
        return containsRecipe;
    }


    /**
     * Sets the containsRecipe value for this Dish.
     * 
     * @param containsRecipe
     */
    public void setContainsRecipe(boolean containsRecipe) {
        this.containsRecipe = containsRecipe;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Dish)) return false;
        Dish other = (Dish) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.usersMenuDishesID == other.getUsersMenuDishesID() &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            this.recipeID == other.getRecipeID() &&
            ((this.image==null && other.getImage()==null) || 
             (this.image!=null &&
              this.image.equals(other.getImage()))) &&
            ((this.imageBytes==null && other.getImageBytes()==null) || 
             (this.imageBytes!=null &&
              java.util.Arrays.equals(this.imageBytes, other.getImageBytes()))) &&
            this.dishCategory == other.getDishCategory() &&
            this.hasProcedure == other.isHasProcedure() &&
            this.containsRecipe == other.isContainsRecipe();
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
        _hashCode += getUsersMenuDishesID();
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        _hashCode += getRecipeID();
        if (getImage() != null) {
            _hashCode += getImage().hashCode();
        }
        if (getImageBytes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getImageBytes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getImageBytes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += getDishCategory();
        _hashCode += (isHasProcedure() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isContainsRecipe() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }


}
