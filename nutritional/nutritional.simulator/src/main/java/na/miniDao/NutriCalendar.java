/**
 * NutriCalendar.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.miniDao;

public class NutriCalendar  implements java.io.Serializable {
    private java.lang.String timeZone;

    private long timeInMillis;

    public NutriCalendar() {
    }

    public NutriCalendar(
           java.lang.String timeZone,
           long timeInMillis) {
           this.timeZone = timeZone;
           this.timeInMillis = timeInMillis;
    }


    /**
     * Gets the timeZone value for this NutriCalendar.
     * 
     * @return timeZone
     */
    public java.lang.String getTimeZone() {
        return timeZone;
    }


    /**
     * Sets the timeZone value for this NutriCalendar.
     * 
     * @param timeZone
     */
    public void setTimeZone(java.lang.String timeZone) {
        this.timeZone = timeZone;
    }


    /**
     * Gets the timeInMillis value for this NutriCalendar.
     * 
     * @return timeInMillis
     */
    public long getTimeInMillis() {
        return timeInMillis;
    }


    /**
     * Sets the timeInMillis value for this NutriCalendar.
     * 
     * @param timeInMillis
     */
    public void setTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NutriCalendar)) return false;
        NutriCalendar other = (NutriCalendar) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.timeZone==null && other.getTimeZone()==null) || 
             (this.timeZone!=null &&
              this.timeZone.equals(other.getTimeZone()))) &&
            this.timeInMillis == other.getTimeInMillis();
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
        if (getTimeZone() != null) {
            _hashCode += getTimeZone().hashCode();
        }
        _hashCode += new Long(getTimeInMillis()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

}
