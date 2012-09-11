/**
 * NewEvent.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.oasisUtils.socialCommunity.Agenda;

public class NewEvent  implements java.io.Serializable {
    private java.lang.String title;

    private java.lang.String start_date;

    private java.lang.String end_date;

    private java.lang.String description;

    private java.lang.String event_tags;

    private java.lang.String start_time;

    private java.lang.String end_time;

    private java.lang.String venue;

    private java.lang.String fees;

    private java.lang.String contact;

    private java.lang.String organiser;

    private java.lang.String long_description;

    public NewEvent() {
    }

    public NewEvent(
           java.lang.String title,
           java.lang.String start_date,
           java.lang.String end_date,
           java.lang.String description,
           java.lang.String event_tags,
           java.lang.String start_time,
           java.lang.String end_time,
           java.lang.String venue,
           java.lang.String fees,
           java.lang.String contact,
           java.lang.String organiser,
           java.lang.String long_description) {
           this.title = title;
           this.start_date = start_date;
           this.end_date = end_date;
           this.description = description;
           this.event_tags = event_tags;
           this.start_time = start_time;
           this.end_time = end_time;
           this.venue = venue;
           this.fees = fees;
           this.contact = contact;
           this.organiser = organiser;
           this.long_description = long_description;
    }


    /**
     * Gets the title value for this NewEvent.
     * 
     * @return title
     */
    public java.lang.String getTitle() {
        return title;
    }


    /**
     * Sets the title value for this NewEvent.
     * 
     * @param title
     */
    public void setTitle(java.lang.String title) {
        this.title = title;
    }


    /**
     * Gets the start_date value for this NewEvent.
     * 
     * @return start_date
     */
    public java.lang.String getStart_date() {
        return start_date;
    }


    /**
     * Sets the start_date value for this NewEvent.
     * 
     * @param start_date
     */
    public void setStart_date(java.lang.String start_date) {
        this.start_date = start_date;
    }


    /**
     * Gets the end_date value for this NewEvent.
     * 
     * @return end_date
     */
    public java.lang.String getEnd_date() {
        return end_date;
    }


    /**
     * Sets the end_date value for this NewEvent.
     * 
     * @param end_date
     */
    public void setEnd_date(java.lang.String end_date) {
        this.end_date = end_date;
    }


    /**
     * Gets the description value for this NewEvent.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this NewEvent.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the event_tags value for this NewEvent.
     * 
     * @return event_tags
     */
    public java.lang.String getEvent_tags() {
        return event_tags;
    }


    /**
     * Sets the event_tags value for this NewEvent.
     * 
     * @param event_tags
     */
    public void setEvent_tags(java.lang.String event_tags) {
        this.event_tags = event_tags;
    }


    /**
     * Gets the start_time value for this NewEvent.
     * 
     * @return start_time
     */
    public java.lang.String getStart_time() {
        return start_time;
    }


    /**
     * Sets the start_time value for this NewEvent.
     * 
     * @param start_time
     */
    public void setStart_time(java.lang.String start_time) {
        this.start_time = start_time;
    }


    /**
     * Gets the end_time value for this NewEvent.
     * 
     * @return end_time
     */
    public java.lang.String getEnd_time() {
        return end_time;
    }


    /**
     * Sets the end_time value for this NewEvent.
     * 
     * @param end_time
     */
    public void setEnd_time(java.lang.String end_time) {
        this.end_time = end_time;
    }


    /**
     * Gets the venue value for this NewEvent.
     * 
     * @return venue
     */
    public java.lang.String getVenue() {
        return venue;
    }


    /**
     * Sets the venue value for this NewEvent.
     * 
     * @param venue
     */
    public void setVenue(java.lang.String venue) {
        this.venue = venue;
    }


    /**
     * Gets the fees value for this NewEvent.
     * 
     * @return fees
     */
    public java.lang.String getFees() {
        return fees;
    }


    /**
     * Sets the fees value for this NewEvent.
     * 
     * @param fees
     */
    public void setFees(java.lang.String fees) {
        this.fees = fees;
    }


    /**
     * Gets the contact value for this NewEvent.
     * 
     * @return contact
     */
    public java.lang.String getContact() {
        return contact;
    }


    /**
     * Sets the contact value for this NewEvent.
     * 
     * @param contact
     */
    public void setContact(java.lang.String contact) {
        this.contact = contact;
    }


    /**
     * Gets the organiser value for this NewEvent.
     * 
     * @return organiser
     */
    public java.lang.String getOrganiser() {
        return organiser;
    }


    /**
     * Sets the organiser value for this NewEvent.
     * 
     * @param organiser
     */
    public void setOrganiser(java.lang.String organiser) {
        this.organiser = organiser;
    }


    /**
     * Gets the long_description value for this NewEvent.
     * 
     * @return long_description
     */
    public java.lang.String getLong_description() {
        return long_description;
    }


    /**
     * Sets the long_description value for this NewEvent.
     * 
     * @param long_description
     */
    public void setLong_description(java.lang.String long_description) {
        this.long_description = long_description;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NewEvent)) return false;
        NewEvent other = (NewEvent) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.title==null && other.getTitle()==null) || 
             (this.title!=null &&
              this.title.equals(other.getTitle()))) &&
            ((this.start_date==null && other.getStart_date()==null) || 
             (this.start_date!=null &&
              this.start_date.equals(other.getStart_date()))) &&
            ((this.end_date==null && other.getEnd_date()==null) || 
             (this.end_date!=null &&
              this.end_date.equals(other.getEnd_date()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.event_tags==null && other.getEvent_tags()==null) || 
             (this.event_tags!=null &&
              this.event_tags.equals(other.getEvent_tags()))) &&
            ((this.start_time==null && other.getStart_time()==null) || 
             (this.start_time!=null &&
              this.start_time.equals(other.getStart_time()))) &&
            ((this.end_time==null && other.getEnd_time()==null) || 
             (this.end_time!=null &&
              this.end_time.equals(other.getEnd_time()))) &&
            ((this.venue==null && other.getVenue()==null) || 
             (this.venue!=null &&
              this.venue.equals(other.getVenue()))) &&
            ((this.fees==null && other.getFees()==null) || 
             (this.fees!=null &&
              this.fees.equals(other.getFees()))) &&
            ((this.contact==null && other.getContact()==null) || 
             (this.contact!=null &&
              this.contact.equals(other.getContact()))) &&
            ((this.organiser==null && other.getOrganiser()==null) || 
             (this.organiser!=null &&
              this.organiser.equals(other.getOrganiser()))) &&
            ((this.long_description==null && other.getLong_description()==null) || 
             (this.long_description!=null &&
              this.long_description.equals(other.getLong_description())));
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
        if (getTitle() != null) {
            _hashCode += getTitle().hashCode();
        }
        if (getStart_date() != null) {
            _hashCode += getStart_date().hashCode();
        }
        if (getEnd_date() != null) {
            _hashCode += getEnd_date().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getEvent_tags() != null) {
            _hashCode += getEvent_tags().hashCode();
        }
        if (getStart_time() != null) {
            _hashCode += getStart_time().hashCode();
        }
        if (getEnd_time() != null) {
            _hashCode += getEnd_time().hashCode();
        }
        if (getVenue() != null) {
            _hashCode += getVenue().hashCode();
        }
        if (getFees() != null) {
            _hashCode += getFees().hashCode();
        }
        if (getContact() != null) {
            _hashCode += getContact().hashCode();
        }
        if (getOrganiser() != null) {
            _hashCode += getOrganiser().hashCode();
        }
        if (getLong_description() != null) {
            _hashCode += getLong_description().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(NewEvent.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:Agenda", "NewEvent"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("title");
        elemField.setXmlName(new javax.xml.namespace.QName("", "title"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("start_date");
        elemField.setXmlName(new javax.xml.namespace.QName("", "start_date"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("end_date");
        elemField.setXmlName(new javax.xml.namespace.QName("", "end_date"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("", "description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("event_tags");
        elemField.setXmlName(new javax.xml.namespace.QName("", "event_tags"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("start_time");
        elemField.setXmlName(new javax.xml.namespace.QName("", "start_time"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("end_time");
        elemField.setXmlName(new javax.xml.namespace.QName("", "end_time"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("venue");
        elemField.setXmlName(new javax.xml.namespace.QName("", "venue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fees");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fees"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contact");
        elemField.setXmlName(new javax.xml.namespace.QName("", "contact"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("organiser");
        elemField.setXmlName(new javax.xml.namespace.QName("", "organiser"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("long_description");
        elemField.setXmlName(new javax.xml.namespace.QName("", "long_description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
