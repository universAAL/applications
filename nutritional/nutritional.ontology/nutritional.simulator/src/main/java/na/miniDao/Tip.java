/**
 * Tip.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.miniDao;

public class Tip  implements java.io.Serializable {
    private int ID;

    private java.lang.String description;

    private java.lang.String picture;

    private java.lang.String multimediaType;

    private java.lang.String multimedia;

    private int codeLang;

    public Tip() {
    }

    public Tip(
           int ID,
           java.lang.String description,
           java.lang.String picture,
           java.lang.String multimediaType,
           java.lang.String multimedia,
           int codeLang) {
           this.ID = ID;
           this.description = description;
           this.picture = picture;
           this.multimediaType = multimediaType;
           this.multimedia = multimedia;
           this.codeLang = codeLang;
    }


    /**
     * Gets the ID value for this Tip.
     * 
     * @return ID
     */
    public int getID() {
        return ID;
    }


    /**
     * Sets the ID value for this Tip.
     * 
     * @param ID
     */
    public void setID(int ID) {
        this.ID = ID;
    }


    /**
     * Gets the description value for this Tip.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this Tip.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the picture value for this Tip.
     * 
     * @return picture
     */
    public java.lang.String getPicture() {
        return picture;
    }


    /**
     * Sets the picture value for this Tip.
     * 
     * @param picture
     */
    public void setPicture(java.lang.String picture) {
        this.picture = picture;
    }


    /**
     * Gets the multimediaType value for this Tip.
     * 
     * @return multimediaType
     */
    public java.lang.String getMultimediaType() {
        return multimediaType;
    }


    /**
     * Sets the multimediaType value for this Tip.
     * 
     * @param multimediaType
     */
    public void setMultimediaType(java.lang.String multimediaType) {
        this.multimediaType = multimediaType;
    }


    /**
     * Gets the multimedia value for this Tip.
     * 
     * @return multimedia
     */
    public java.lang.String getMultimedia() {
        return multimedia;
    }


    /**
     * Sets the multimedia value for this Tip.
     * 
     * @param multimedia
     */
    public void setMultimedia(java.lang.String multimedia) {
        this.multimedia = multimedia;
    }


    /**
     * Gets the codeLang value for this Tip.
     * 
     * @return codeLang
     */
    public int getCodeLang() {
        return codeLang;
    }


    /**
     * Sets the codeLang value for this Tip.
     * 
     * @param codeLang
     */
    public void setCodeLang(int codeLang) {
        this.codeLang = codeLang;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Tip)) return false;
        Tip other = (Tip) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.ID == other.getID() &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.picture==null && other.getPicture()==null) || 
             (this.picture!=null &&
              this.picture.equals(other.getPicture()))) &&
            ((this.multimediaType==null && other.getMultimediaType()==null) || 
             (this.multimediaType!=null &&
              this.multimediaType.equals(other.getMultimediaType()))) &&
            ((this.multimedia==null && other.getMultimedia()==null) || 
             (this.multimedia!=null &&
              this.multimedia.equals(other.getMultimedia()))) &&
            this.codeLang == other.getCodeLang();
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
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getPicture() != null) {
            _hashCode += getPicture().hashCode();
        }
        if (getMultimediaType() != null) {
            _hashCode += getMultimediaType().hashCode();
        }
        if (getMultimedia() != null) {
            _hashCode += getMultimedia().hashCode();
        }
        _hashCode += getCodeLang();
        __hashCodeCalc = false;
        return _hashCode;
    }


}
