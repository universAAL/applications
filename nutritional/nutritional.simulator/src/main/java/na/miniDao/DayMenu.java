/**
 * DayMenu.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.miniDao;

public class DayMenu  implements java.io.Serializable {
    private int actualDay;

    private int usersMenusID;

    private na.miniDao.Meal[] meals;

    private int breakfastIndex;

    private int lunchIndex;

    private int dinnerIndex;

    private int midmorningSnackIndex;

    private int afternoonSnackIndex;

    private int afterdinnerSnackIndex;

    public DayMenu() {
    }

    public DayMenu(
           int actualDay,
           int usersMenusID,
           na.miniDao.Meal[] meals,
           int breakfastIndex,
           int lunchIndex,
           int dinnerIndex,
           int midmorningSnackIndex,
           int afternoonSnackIndex,
           int afterdinnerSnackIndex) {
           this.actualDay = actualDay;
           this.usersMenusID = usersMenusID;
           this.meals = meals;
           this.breakfastIndex = breakfastIndex;
           this.lunchIndex = lunchIndex;
           this.dinnerIndex = dinnerIndex;
           this.midmorningSnackIndex = midmorningSnackIndex;
           this.afternoonSnackIndex = afternoonSnackIndex;
           this.afterdinnerSnackIndex = afterdinnerSnackIndex;
    }


    /**
     * Gets the actualDay value for this DayMenu.
     * 
     * @return actualDay
     */
    public int getActualDay() {
        return actualDay;
    }


    /**
     * Sets the actualDay value for this DayMenu.
     * 
     * @param actualDay
     */
    public void setActualDay(int actualDay) {
        this.actualDay = actualDay;
    }


    /**
     * Gets the usersMenusID value for this DayMenu.
     * 
     * @return usersMenusID
     */
    public int getUsersMenusID() {
        return usersMenusID;
    }


    /**
     * Sets the usersMenusID value for this DayMenu.
     * 
     * @param usersMenusID
     */
    public void setUsersMenusID(int usersMenusID) {
        this.usersMenusID = usersMenusID;
    }


    /**
     * Gets the meals value for this DayMenu.
     * 
     * @return meals
     */
    public na.miniDao.Meal[] getMeals() {
        return meals;
    }


    /**
     * Sets the meals value for this DayMenu.
     * 
     * @param meals
     */
    public void setMeals(na.miniDao.Meal[] meals) {
        this.meals = meals;
    }

    public na.miniDao.Meal getMeals(int i) {
        return this.meals[i];
    }

    public void setMeals(int i, na.miniDao.Meal _value) {
        this.meals[i] = _value;
    }


    /**
     * Gets the breakfastIndex value for this DayMenu.
     * 
     * @return breakfastIndex
     */
    public int getBreakfastIndex() {
        return breakfastIndex;
    }


    /**
     * Sets the breakfastIndex value for this DayMenu.
     * 
     * @param breakfastIndex
     */
    public void setBreakfastIndex(int breakfastIndex) {
        this.breakfastIndex = breakfastIndex;
    }


    /**
     * Gets the lunchIndex value for this DayMenu.
     * 
     * @return lunchIndex
     */
    public int getLunchIndex() {
        return lunchIndex;
    }


    /**
     * Sets the lunchIndex value for this DayMenu.
     * 
     * @param lunchIndex
     */
    public void setLunchIndex(int lunchIndex) {
        this.lunchIndex = lunchIndex;
    }


    /**
     * Gets the dinnerIndex value for this DayMenu.
     * 
     * @return dinnerIndex
     */
    public int getDinnerIndex() {
        return dinnerIndex;
    }


    /**
     * Sets the dinnerIndex value for this DayMenu.
     * 
     * @param dinnerIndex
     */
    public void setDinnerIndex(int dinnerIndex) {
        this.dinnerIndex = dinnerIndex;
    }


    /**
     * Gets the midmorningSnackIndex value for this DayMenu.
     * 
     * @return midmorningSnackIndex
     */
    public int getMidmorningSnackIndex() {
        return midmorningSnackIndex;
    }


    /**
     * Sets the midmorningSnackIndex value for this DayMenu.
     * 
     * @param midmorningSnackIndex
     */
    public void setMidmorningSnackIndex(int midmorningSnackIndex) {
        this.midmorningSnackIndex = midmorningSnackIndex;
    }


    /**
     * Gets the afternoonSnackIndex value for this DayMenu.
     * 
     * @return afternoonSnackIndex
     */
    public int getAfternoonSnackIndex() {
        return afternoonSnackIndex;
    }


    /**
     * Sets the afternoonSnackIndex value for this DayMenu.
     * 
     * @param afternoonSnackIndex
     */
    public void setAfternoonSnackIndex(int afternoonSnackIndex) {
        this.afternoonSnackIndex = afternoonSnackIndex;
    }


    /**
     * Gets the afterdinnerSnackIndex value for this DayMenu.
     * 
     * @return afterdinnerSnackIndex
     */
    public int getAfterdinnerSnackIndex() {
        return afterdinnerSnackIndex;
    }


    /**
     * Sets the afterdinnerSnackIndex value for this DayMenu.
     * 
     * @param afterdinnerSnackIndex
     */
    public void setAfterdinnerSnackIndex(int afterdinnerSnackIndex) {
        this.afterdinnerSnackIndex = afterdinnerSnackIndex;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DayMenu)) return false;
        DayMenu other = (DayMenu) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.actualDay == other.getActualDay() &&
            this.usersMenusID == other.getUsersMenusID() &&
            ((this.meals==null && other.getMeals()==null) || 
             (this.meals!=null &&
              java.util.Arrays.equals(this.meals, other.getMeals()))) &&
            this.breakfastIndex == other.getBreakfastIndex() &&
            this.lunchIndex == other.getLunchIndex() &&
            this.dinnerIndex == other.getDinnerIndex() &&
            this.midmorningSnackIndex == other.getMidmorningSnackIndex() &&
            this.afternoonSnackIndex == other.getAfternoonSnackIndex() &&
            this.afterdinnerSnackIndex == other.getAfterdinnerSnackIndex();
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
        _hashCode += getActualDay();
        _hashCode += getUsersMenusID();
        if (getMeals() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMeals());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMeals(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += getBreakfastIndex();
        _hashCode += getLunchIndex();
        _hashCode += getDinnerIndex();
        _hashCode += getMidmorningSnackIndex();
        _hashCode += getAfternoonSnackIndex();
        _hashCode += getAfterdinnerSnackIndex();
        __hashCodeCalc = false;
        return _hashCode;
    }


}
