/**
 * Advise.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.miniDao;

public class Advise  implements java.io.Serializable {
    private int ID;

    private java.lang.String title;

    private java.lang.String message;

    private java.lang.String picture;

    private int status;

    private na.miniDao.NutriCalendar startDate;

    private na.miniDao.NutriCalendar endDate;

    private int frequency;

    private int startWeek;

    private int[] activeOnDays;

    private na.miniDao.AdviseTimeInfo timeInfo;

    private na.miniDao.OtherCondition otherConditions;

    public Advise() {
    }

    public Advise(
           int ID,
           java.lang.String title,
           java.lang.String message,
           java.lang.String picture,
           int status,
           na.miniDao.NutriCalendar startDate,
           na.miniDao.NutriCalendar endDate,
           int frequency,
           int startWeek,
           int[] activeOnDays,
           na.miniDao.AdviseTimeInfo timeInfo,
           na.miniDao.OtherCondition otherConditions) {
           this.ID = ID;
           this.title = title;
           this.message = message;
           this.picture = picture;
           this.status = status;
           this.startDate = startDate;
           this.endDate = endDate;
           this.frequency = frequency;
           this.startWeek = startWeek;
           this.activeOnDays = activeOnDays;
           this.timeInfo = timeInfo;
           this.otherConditions = otherConditions;
    }


    /**
     * Gets the ID value for this Advise.
     * 
     * @return ID
     */
    public int getID() {
        return ID;
    }


    /**
     * Sets the ID value for this Advise.
     * 
     * @param ID
     */
    public void setID(int ID) {
        this.ID = ID;
    }


    /**
     * Gets the title value for this Advise.
     * 
     * @return title
     */
    public java.lang.String getTitle() {
        return title;
    }


    /**
     * Sets the title value for this Advise.
     * 
     * @param title
     */
    public void setTitle(java.lang.String title) {
        this.title = title;
    }


    /**
     * Gets the message value for this Advise.
     * 
     * @return message
     */
    public java.lang.String getMessage() {
        return message;
    }


    /**
     * Sets the message value for this Advise.
     * 
     * @param message
     */
    public void setMessage(java.lang.String message) {
        this.message = message;
    }


    /**
     * Gets the picture value for this Advise.
     * 
     * @return picture
     */
    public java.lang.String getPicture() {
        return picture;
    }


    /**
     * Sets the picture value for this Advise.
     * 
     * @param picture
     */
    public void setPicture(java.lang.String picture) {
        this.picture = picture;
    }


    /**
     * Gets the status value for this Advise.
     * 
     * @return status
     */
    public int getStatus() {
        return status;
    }


    /**
     * Sets the status value for this Advise.
     * 
     * @param status
     */
    public void setStatus(int status) {
        this.status = status;
    }


    /**
     * Gets the startDate value for this Advise.
     * 
     * @return startDate
     */
    public na.miniDao.NutriCalendar getStartDate() {
        return startDate;
    }


    /**
     * Sets the startDate value for this Advise.
     * 
     * @param startDate
     */
    public void setStartDate(na.miniDao.NutriCalendar startDate) {
        this.startDate = startDate;
    }


    /**
     * Gets the endDate value for this Advise.
     * 
     * @return endDate
     */
    public na.miniDao.NutriCalendar getEndDate() {
        return endDate;
    }


    /**
     * Sets the endDate value for this Advise.
     * 
     * @param endDate
     */
    public void setEndDate(na.miniDao.NutriCalendar endDate) {
        this.endDate = endDate;
    }


    /**
     * Gets the frequency value for this Advise.
     * 
     * @return frequency
     */
    public int getFrequency() {
        return frequency;
    }


    /**
     * Sets the frequency value for this Advise.
     * 
     * @param frequency
     */
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }


    /**
     * Gets the startWeek value for this Advise.
     * 
     * @return startWeek
     */
    public int getStartWeek() {
        return startWeek;
    }


    /**
     * Sets the startWeek value for this Advise.
     * 
     * @param startWeek
     */
    public void setStartWeek(int startWeek) {
        this.startWeek = startWeek;
    }


    /**
     * Gets the activeOnDays value for this Advise.
     * 
     * @return activeOnDays
     */
    public int[] getActiveOnDays() {
        return activeOnDays;
    }


    /**
     * Sets the activeOnDays value for this Advise.
     * 
     * @param activeOnDays
     */
    public void setActiveOnDays(int[] activeOnDays) {
        this.activeOnDays = activeOnDays;
    }

    public int getActiveOnDays(int i) {
        return this.activeOnDays[i];
    }

    public void setActiveOnDays(int i, int _value) {
        this.activeOnDays[i] = _value;
    }


    /**
     * Gets the timeInfo value for this Advise.
     * 
     * @return timeInfo
     */
    public na.miniDao.AdviseTimeInfo getTimeInfo() {
        return timeInfo;
    }


    /**
     * Sets the timeInfo value for this Advise.
     * 
     * @param timeInfo
     */
    public void setTimeInfo(na.miniDao.AdviseTimeInfo timeInfo) {
        this.timeInfo = timeInfo;
    }


    /**
     * Gets the otherConditions value for this Advise.
     * 
     * @return otherConditions
     */
    public na.miniDao.OtherCondition getOtherConditions() {
        return otherConditions;
    }


    /**
     * Sets the otherConditions value for this Advise.
     * 
     * @param otherConditions
     */
    public void setOtherConditions(na.miniDao.OtherCondition otherConditions) {
        this.otherConditions = otherConditions;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Advise)) return false;
        Advise other = (Advise) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.ID == other.getID() &&
            ((this.title==null && other.getTitle()==null) || 
             (this.title!=null &&
              this.title.equals(other.getTitle()))) &&
            ((this.message==null && other.getMessage()==null) || 
             (this.message!=null &&
              this.message.equals(other.getMessage()))) &&
            ((this.picture==null && other.getPicture()==null) || 
             (this.picture!=null &&
              this.picture.equals(other.getPicture()))) &&
            this.status == other.getStatus() &&
            ((this.startDate==null && other.getStartDate()==null) || 
             (this.startDate!=null &&
              this.startDate.equals(other.getStartDate()))) &&
            ((this.endDate==null && other.getEndDate()==null) || 
             (this.endDate!=null &&
              this.endDate.equals(other.getEndDate()))) &&
            this.frequency == other.getFrequency() &&
            this.startWeek == other.getStartWeek() &&
            ((this.activeOnDays==null && other.getActiveOnDays()==null) || 
             (this.activeOnDays!=null &&
              java.util.Arrays.equals(this.activeOnDays, other.getActiveOnDays()))) &&
            ((this.timeInfo==null && other.getTimeInfo()==null) || 
             (this.timeInfo!=null &&
              this.timeInfo.equals(other.getTimeInfo()))) &&
            ((this.otherConditions==null && other.getOtherConditions()==null) || 
             (this.otherConditions!=null &&
              this.otherConditions.equals(other.getOtherConditions())));
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
        if (getTitle() != null) {
            _hashCode += getTitle().hashCode();
        }
        if (getMessage() != null) {
            _hashCode += getMessage().hashCode();
        }
        if (getPicture() != null) {
            _hashCode += getPicture().hashCode();
        }
        _hashCode += getStatus();
        if (getStartDate() != null) {
            _hashCode += getStartDate().hashCode();
        }
        if (getEndDate() != null) {
            _hashCode += getEndDate().hashCode();
        }
        _hashCode += getFrequency();
        _hashCode += getStartWeek();
        if (getActiveOnDays() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getActiveOnDays());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getActiveOnDays(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getTimeInfo() != null) {
            _hashCode += getTimeInfo().hashCode();
        }
        if (getOtherConditions() != null) {
            _hashCode += getOtherConditions().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    }
