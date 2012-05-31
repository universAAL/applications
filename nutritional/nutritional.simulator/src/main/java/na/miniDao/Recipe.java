/**
 * Recipe.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.miniDao;

public class Recipe  implements java.io.Serializable {
    private int recipeID;

    private java.lang.String course;

    private java.lang.String dishCategory;

    private java.lang.String procedure;

    private java.lang.String picture;

    private boolean favouriteRecipe;

    private na.miniDao.RecipeIngredient[] recipeIngredients;

    private byte[] multimediaBytes;

    public Recipe() {
    }

    public Recipe(
           int recipeID,
           java.lang.String course,
           java.lang.String dishCategory,
           java.lang.String procedure,
           java.lang.String picture,
           boolean favouriteRecipe,
           na.miniDao.RecipeIngredient[] recipeIngredients,
           byte[] multimediaBytes) {
           this.recipeID = recipeID;
           this.course = course;
           this.dishCategory = dishCategory;
           this.procedure = procedure;
           this.picture = picture;
           this.favouriteRecipe = favouriteRecipe;
           this.recipeIngredients = recipeIngredients;
           this.multimediaBytes = multimediaBytes;
    }


    /**
     * Gets the recipeID value for this Recipe.
     * 
     * @return recipeID
     */
    public int getRecipeID() {
        return recipeID;
    }


    /**
     * Sets the recipeID value for this Recipe.
     * 
     * @param recipeID
     */
    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }


    /**
     * Gets the course value for this Recipe.
     * 
     * @return course
     */
    public java.lang.String getCourse() {
        return course;
    }


    /**
     * Sets the course value for this Recipe.
     * 
     * @param course
     */
    public void setCourse(java.lang.String course) {
        this.course = course;
    }


    /**
     * Gets the dishCategory value for this Recipe.
     * 
     * @return dishCategory
     */
    public java.lang.String getDishCategory() {
        return dishCategory;
    }


    /**
     * Sets the dishCategory value for this Recipe.
     * 
     * @param dishCategory
     */
    public void setDishCategory(java.lang.String dishCategory) {
        this.dishCategory = dishCategory;
    }


    /**
     * Gets the procedure value for this Recipe.
     * 
     * @return procedure
     */
    public java.lang.String getProcedure() {
        return procedure;
    }


    /**
     * Sets the procedure value for this Recipe.
     * 
     * @param procedure
     */
    public void setProcedure(java.lang.String procedure) {
        this.procedure = procedure;
    }


    /**
     * Gets the picture value for this Recipe.
     * 
     * @return picture
     */
    public java.lang.String getPicture() {
        return picture;
    }


    /**
     * Sets the picture value for this Recipe.
     * 
     * @param picture
     */
    public void setPicture(java.lang.String picture) {
        this.picture = picture;
    }


    /**
     * Gets the favouriteRecipe value for this Recipe.
     * 
     * @return favouriteRecipe
     */
    public boolean isFavouriteRecipe() {
        return favouriteRecipe;
    }


    /**
     * Sets the favouriteRecipe value for this Recipe.
     * 
     * @param favouriteRecipe
     */
    public void setFavouriteRecipe(boolean favouriteRecipe) {
        this.favouriteRecipe = favouriteRecipe;
    }


    /**
     * Gets the recipeIngredients value for this Recipe.
     * 
     * @return recipeIngredients
     */
    public na.miniDao.RecipeIngredient[] getRecipeIngredients() {
        return recipeIngredients;
    }


    /**
     * Sets the recipeIngredients value for this Recipe.
     * 
     * @param recipeIngredients
     */
    public void setRecipeIngredients(na.miniDao.RecipeIngredient[] recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public na.miniDao.RecipeIngredient getRecipeIngredients(int i) {
        return this.recipeIngredients[i];
    }

    public void setRecipeIngredients(int i, na.miniDao.RecipeIngredient _value) {
        this.recipeIngredients[i] = _value;
    }


    /**
     * Gets the multimediaBytes value for this Recipe.
     * 
     * @return multimediaBytes
     */
    public byte[] getMultimediaBytes() {
        return multimediaBytes;
    }


    /**
     * Sets the multimediaBytes value for this Recipe.
     * 
     * @param multimediaBytes
     */
    public void setMultimediaBytes(byte[] multimediaBytes) {
        this.multimediaBytes = multimediaBytes;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Recipe)) return false;
        Recipe other = (Recipe) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.recipeID == other.getRecipeID() &&
            ((this.course==null && other.getCourse()==null) || 
             (this.course!=null &&
              this.course.equals(other.getCourse()))) &&
            ((this.dishCategory==null && other.getDishCategory()==null) || 
             (this.dishCategory!=null &&
              this.dishCategory.equals(other.getDishCategory()))) &&
            ((this.procedure==null && other.getProcedure()==null) || 
             (this.procedure!=null &&
              this.procedure.equals(other.getProcedure()))) &&
            ((this.picture==null && other.getPicture()==null) || 
             (this.picture!=null &&
              this.picture.equals(other.getPicture()))) &&
            this.favouriteRecipe == other.isFavouriteRecipe() &&
            ((this.recipeIngredients==null && other.getRecipeIngredients()==null) || 
             (this.recipeIngredients!=null &&
              java.util.Arrays.equals(this.recipeIngredients, other.getRecipeIngredients()))) &&
            ((this.multimediaBytes==null && other.getMultimediaBytes()==null) || 
             (this.multimediaBytes!=null &&
              java.util.Arrays.equals(this.multimediaBytes, other.getMultimediaBytes())));
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
        _hashCode += getRecipeID();
        if (getCourse() != null) {
            _hashCode += getCourse().hashCode();
        }
        if (getDishCategory() != null) {
            _hashCode += getDishCategory().hashCode();
        }
        if (getProcedure() != null) {
            _hashCode += getProcedure().hashCode();
        }
        if (getPicture() != null) {
            _hashCode += getPicture().hashCode();
        }
        _hashCode += (isFavouriteRecipe() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getRecipeIngredients() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRecipeIngredients());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRecipeIngredients(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getMultimediaBytes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMultimediaBytes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMultimediaBytes(), i);
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
