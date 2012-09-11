/**
 * Discussion.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.oasisUtils.socialCommunity.Friends;

public class Discussion  implements java.io.Serializable {
    private java.lang.String id;

    private java.lang.String name;

    private java.lang.String time_created;

    private java.lang.String author_id;

    private java.lang.String author_name;

    public Discussion() {
    }

    public Discussion(
           java.lang.String id,
           java.lang.String name,
           java.lang.String time_created,
           java.lang.String author_id,
           java.lang.String author_name) {
           this.id = id;
           this.name = name;
           this.time_created = time_created;
           this.author_id = author_id;
           this.author_name = author_name;
    }


    /**
     * Gets the id value for this Discussion.
     * 
     * @return id
     */
    public java.lang.String getId() {
        return id;
    }


    /**
     * Sets the id value for this Discussion.
     * 
     * @param id
     */
    public void setId(java.lang.String id) {
        this.id = id;
    }


    /**
     * Gets the name value for this Discussion.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this Discussion.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the time_created value for this Discussion.
     * 
     * @return time_created
     */
    public java.lang.String getTime_created() {
        return time_created;
    }


    /**
     * Sets the time_created value for this Discussion.
     * 
     * @param time_created
     */
    public void setTime_created(java.lang.String time_created) {
        this.time_created = time_created;
    }


    /**
     * Gets the author_id value for this Discussion.
     * 
     * @return author_id
     */
    public java.lang.String getAuthor_id() {
        return author_id;
    }


    /**
     * Sets the author_id value for this Discussion.
     * 
     * @param author_id
     */
    public void setAuthor_id(java.lang.String author_id) {
        this.author_id = author_id;
    }


    /**
     * Gets the author_name value for this Discussion.
     * 
     * @return author_name
     */
    public java.lang.String getAuthor_name() {
        return author_name;
    }


    /**
     * Sets the author_name value for this Discussion.
     * 
     * @param author_name
     */
    public void setAuthor_name(java.lang.String author_name) {
        this.author_name = author_name;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Discussion)) return false;
        Discussion other = (Discussion) obj;
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
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.time_created==null && other.getTime_created()==null) || 
             (this.time_created!=null &&
              this.time_created.equals(other.getTime_created()))) &&
            ((this.author_id==null && other.getAuthor_id()==null) || 
             (this.author_id!=null &&
              this.author_id.equals(other.getAuthor_id()))) &&
            ((this.author_name==null && other.getAuthor_name()==null) || 
             (this.author_name!=null &&
              this.author_name.equals(other.getAuthor_name())));
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
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getTime_created() != null) {
            _hashCode += getTime_created().hashCode();
        }
        if (getAuthor_id() != null) {
            _hashCode += getAuthor_id().hashCode();
        }
        if (getAuthor_name() != null) {
            _hashCode += getAuthor_name().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Discussion.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:Friends", "Discussion"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("time_created");
        elemField.setXmlName(new javax.xml.namespace.QName("", "time_created"));
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
