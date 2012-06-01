/**
 * Questionnaire.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.miniDao;

public class Questionnaire  implements java.io.Serializable {
    private int ID;

    private int userID;

    private java.lang.String type;

    private java.lang.String state;

    private java.lang.String description;

    private java.lang.String title;

    private na.miniDao.NutriDate creationDate;

    private na.miniDao.Exercise[] exercises;

    private na.miniDao.Question[] questions;

    public Questionnaire() {
    }

    public Questionnaire(
           int ID,
           int userID,
           java.lang.String type,
           java.lang.String state,
           java.lang.String description,
           java.lang.String title,
           na.miniDao.NutriDate creationDate,
           na.miniDao.Exercise[] exercises,
           na.miniDao.Question[] questions) {
           this.ID = ID;
           this.userID = userID;
           this.type = type;
           this.state = state;
           this.description = description;
           this.title = title;
           this.creationDate = creationDate;
           this.exercises = exercises;
           this.questions = questions;
    }


    /**
     * Gets the ID value for this Questionnaire.
     * 
     * @return ID
     */
    public int getID() {
        return ID;
    }


    /**
     * Sets the ID value for this Questionnaire.
     * 
     * @param ID
     */
    public void setID(int ID) {
        this.ID = ID;
    }


    /**
     * Gets the userID value for this Questionnaire.
     * 
     * @return userID
     */
    public int getUserID() {
        return userID;
    }


    /**
     * Sets the userID value for this Questionnaire.
     * 
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }


    /**
     * Gets the type value for this Questionnaire.
     * 
     * @return type
     */
    public java.lang.String getType() {
        return type;
    }


    /**
     * Sets the type value for this Questionnaire.
     * 
     * @param type
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }


    /**
     * Gets the state value for this Questionnaire.
     * 
     * @return state
     */
    public java.lang.String getState() {
        return state;
    }


    /**
     * Sets the state value for this Questionnaire.
     * 
     * @param state
     */
    public void setState(java.lang.String state) {
        this.state = state;
    }


    /**
     * Gets the description value for this Questionnaire.
     * 
     * @return description
     */
    public java.lang.String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this Questionnaire.
     * 
     * @param description
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }


    /**
     * Gets the title value for this Questionnaire.
     * 
     * @return title
     */
    public java.lang.String getTitle() {
        return title;
    }


    /**
     * Sets the title value for this Questionnaire.
     * 
     * @param title
     */
    public void setTitle(java.lang.String title) {
        this.title = title;
    }


    /**
     * Gets the creationDate value for this Questionnaire.
     * 
     * @return creationDate
     */
    public na.miniDao.NutriDate getCreationDate() {
        return creationDate;
    }


    /**
     * Sets the creationDate value for this Questionnaire.
     * 
     * @param creationDate
     */
    public void setCreationDate(na.miniDao.NutriDate creationDate) {
        this.creationDate = creationDate;
    }


    /**
     * Gets the exercises value for this Questionnaire.
     * 
     * @return exercises
     */
    public na.miniDao.Exercise[] getExercises() {
        return exercises;
    }


    /**
     * Sets the exercises value for this Questionnaire.
     * 
     * @param exercises
     */
    public void setExercises(na.miniDao.Exercise[] exercises) {
        this.exercises = exercises;
    }

    public na.miniDao.Exercise getExercises(int i) {
        return this.exercises[i];
    }

    public void setExercises(int i, na.miniDao.Exercise _value) {
        this.exercises[i] = _value;
    }


    /**
     * Gets the questions value for this Questionnaire.
     * 
     * @return questions
     */
    public na.miniDao.Question[] getQuestions() {
        return questions;
    }


    /**
     * Sets the questions value for this Questionnaire.
     * 
     * @param questions
     */
    public void setQuestions(na.miniDao.Question[] questions) {
        this.questions = questions;
    }

    public na.miniDao.Question getQuestions(int i) {
        return this.questions[i];
    }

    public void setQuestions(int i, na.miniDao.Question _value) {
        this.questions[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Questionnaire)) return false;
        Questionnaire other = (Questionnaire) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.ID == other.getID() &&
            this.userID == other.getUserID() &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            ((this.state==null && other.getState()==null) || 
             (this.state!=null &&
              this.state.equals(other.getState()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.title==null && other.getTitle()==null) || 
             (this.title!=null &&
              this.title.equals(other.getTitle()))) &&
            ((this.creationDate==null && other.getCreationDate()==null) || 
             (this.creationDate!=null &&
              this.creationDate.equals(other.getCreationDate()))) &&
            ((this.exercises==null && other.getExercises()==null) || 
             (this.exercises!=null &&
              java.util.Arrays.equals(this.exercises, other.getExercises()))) &&
            ((this.questions==null && other.getQuestions()==null) || 
             (this.questions!=null &&
              java.util.Arrays.equals(this.questions, other.getQuestions())));
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
        _hashCode += getUserID();
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        if (getState() != null) {
            _hashCode += getState().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getTitle() != null) {
            _hashCode += getTitle().hashCode();
        }
        if (getCreationDate() != null) {
            _hashCode += getCreationDate().hashCode();
        }
        if (getExercises() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getExercises());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getExercises(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getQuestions() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getQuestions());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getQuestions(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Questionnaire.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://miniDao/", "questionnaire"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/", "ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/", "userID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/", "type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("state");
        elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/", "state"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/", "description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("title");
        elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/", "title"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creationDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/", "creationDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://miniDao/", "nutriDate"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("exercises");
        elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/", "exercises"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://miniDao/", "exercise"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("questions");
        elemField.setXmlName(new javax.xml.namespace.QName("http://miniDao/", "questions"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://miniDao/", "question"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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
