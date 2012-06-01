/**
 * Topic.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.oasisUtils.socialCommunity.Friends;

public class Topic  implements java.io.Serializable {
    private java.lang.String id;

    private java.lang.String title;

    private java.lang.String time_updated;

    private java.lang.String author_id;

    private java.lang.String author_name;

    private java.lang.String tags;

    private java.lang.String status;

    public Topic() {
    }

    public Topic(
           java.lang.String id,
           java.lang.String title,
           java.lang.String time_updated,
           java.lang.String author_id,
           java.lang.String author_name,
           java.lang.String tags,
           java.lang.String status) {
           this.id = id;
           this.title = title;
           this.time_updated = time_updated;
           this.author_id = author_id;
           this.author_name = author_name;
           this.tags = tags;
           this.status = status;
    }


    /**
     * Gets the id value for this Topic.
     * 
     * @return id
     */
    public java.lang.String getId() {
        return id;
    }


    /**
     * Sets the id value for this Topic.
     * 
     * @param id
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }


    /**
     * Gets the title value for this Topic.
     * 
     * @return title
     */
    public java.lang.String getTitle() {
        return title;
    }


    /**
     * Sets the title value for this Topic.
     * 
     * @param title
     */
    public void setTitle(java.lang.String title) {
        this.title = title;
    }


    /**
     * Gets the time_updated value for this Topic.
     * 
     * @return time_updated
     */
    public java.lang.String getTime_updated() {
        return time_updated;
    }


    /**
     * Sets the time_updated value for this Topic.
     * 
     * @param time_updated
     */
    public void setTime_updated(java.lang.String time_updated) {
        this.time_updated = time_updated;
    }


    /**
     * Gets the author_id value for this Topic.
     * 
     * @return author_id
     */
    public java.lang.String getAuthor_id() {
        return author_id;
    }


    /**
     * Sets the author_id value for this Topic.
     * 
     * @param author_id
     */
    public void setAuthor_id(java.lang.String author_id) {
        this.author_id = author_id;
    }


    /**
     * Gets the author_name value for this Topic.
     * 
     * @return author_name
     */
    public java.lang.String getAuthor_name() {
        return author_name;
    }


    /**
     * Sets the author_name value for this Topic.
     * 
     * @param author_name
     */
    public void setAuthor_name(java.lang.String author_name) {
        this.author_name = author_name;
    }


    /**
     * Gets the tags value for this Topic.
     * 
     * @return tags
     */
    public java.lang.String getTags() {
        return tags;
    }


    /**
     * Sets the tags value for this Topic.
     * 
     * @param tags
     */
    public void setTags(java.lang.String tags) {
        this.tags = tags;
    }


    /**
     * Gets the status value for this Topic.
     * 
     * @return status
     */
    public java.lang.String getStatus() {
        return status;
    }


    /**
     * Sets the status value for this Topic.
     * 
     * @param status
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Topic)) return false;
        Topic other = (Topic) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.title==null && other.getTitle()==null) || 
             (this.title!=null &&
              this.title.equals(other.getTitle()))) &&
            ((this.time_updated==null && other.getTime_updated()==null) || 
             (this.time_updated!=null &&
              this.time_updated.equals(other.getTime_updated()))) &&
            ((this.author_id==null && other.getAuthor_id()==null) || 
             (this.author_id!=null &&
              this.author_id.equals(other.getAuthor_id()))) &&
            ((this.author_name==null && other.getAuthor_name()==null) || 
             (this.author_name!=null &&
              this.author_name.equals(other.getAuthor_name()))) &&
            ((this.tags==null && other.getTags()==null) || 
             (this.tags!=null &&
              this.tags.equals(other.getTags()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus())));
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
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getTitle() != null) {
            _hashCode += getTitle().hashCode();
        }
        if (getTime_updated() != null) {
            _hashCode += getTime_updated().hashCode();
        }
        if (getAuthor_id() != null) {
            _hashCode += getAuthor_id().hashCode();
        }
        if (getAuthor_name() != null) {
            _hashCode += getAuthor_name().hashCode();
        }
        if (getTags() != null) {
            _hashCode += getTags().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Topic.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:Friends", "Topic"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("title");
        elemField.setXmlName(new javax.xml.namespace.QName("", "title"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("time_updated");
        elemField.setXmlName(new javax.xml.namespace.QName("", "time_updated"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("author_id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "author_id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("author_name");
        elemField.setXmlName(new javax.xml.namespace.QName("", "author_name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tags");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tags"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("", "status"));
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
