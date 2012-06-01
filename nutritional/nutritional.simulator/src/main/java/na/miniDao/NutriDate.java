/**
 * NutriDate.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.miniDao;

public class NutriDate  implements java.io.Serializable {
    private long date;

    public NutriDate() {
    }

    public NutriDate(
           long date) {
           this.date = date;
    }


    /**
     * Gets the date value for this NutriDate.
     * 
     * @return date
     */
    public long getDate() {
        return date;
    }


    /**
     * Sets the date value for this NutriDate.
     * 
     * @param date
     */
    public void setDate(long date) {
        this.date = date;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NutriDate)) return false;
        NutriDate other = (NutriDate) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.date == other.getDate();
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
        _hashCode += new Long(getDate()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }


}
