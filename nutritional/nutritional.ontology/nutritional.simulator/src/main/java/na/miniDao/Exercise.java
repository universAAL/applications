/**
 * Exercise.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.miniDao;

public class Exercise  implements java.io.Serializable {
    private int ID;

    private na.miniDao.NutriDate startDate;

    private na.miniDao.NutriDate endDate;

    private na.miniDao.NutriDate assignedDate;

    private java.lang.String finalScore;

    private na.miniDao.Question firstQuestion;

    private java.lang.String state;

    private na.miniDao.Question lastQuestion;

    private na.miniDao.Questionnaire questionnaire;

    private int userID;

    public Exercise() {
    }

    public Exercise(
           int ID,
           na.miniDao.NutriDate startDate,
           na.miniDao.NutriDate endDate,
           na.miniDao.NutriDate assignedDate,
           java.lang.String finalScore,
           na.miniDao.Question firstQuestion,
           java.lang.String state,
           na.miniDao.Question lastQuestion,
           na.miniDao.Questionnaire questionnaire,
           int userID) {
           this.ID = ID;
           this.startDate = startDate;
           this.endDate = endDate;
           this.assignedDate = assignedDate;
           this.finalScore = finalScore;
           this.firstQuestion = firstQuestion;
           this.state = state;
           this.lastQuestion = lastQuestion;
           this.questionnaire = questionnaire;
           this.userID = userID;
    }


    /**
     * Gets the ID value for this Exercise.
     * 
     * @return ID
     */
    public int getID() {
        return ID;
    }


    /**
     * Sets the ID value for this Exercise.
     * 
     * @param ID
     */
    public void setID(int ID) {
        this.ID = ID;
    }


    /**
     * Gets the startDate value for this Exercise.
     * 
     * @return startDate
     */
    public na.miniDao.NutriDate getStartDate() {
        return startDate;
    }


    /**
     * Sets the startDate value for this Exercise.
     * 
     * @param startDate
     */
    public void setStartDate(na.miniDao.NutriDate startDate) {
        this.startDate = startDate;
    }


    /**
     * Gets the endDate value for this Exercise.
     * 
     * @return endDate
     */
    public na.miniDao.NutriDate getEndDate() {
        return endDate;
    }


    /**
     * Sets the endDate value for this Exercise.
     * 
     * @param endDate
     */
    public void setEndDate(na.miniDao.NutriDate endDate) {
        this.endDate = endDate;
    }


    /**
     * Gets the assignedDate value for this Exercise.
     * 
     * @return assignedDate
     */
    public na.miniDao.NutriDate getAssignedDate() {
        return assignedDate;
    }


    /**
     * Sets the assignedDate value for this Exercise.
     * 
     * @param assignedDate
     */
    public void setAssignedDate(na.miniDao.NutriDate assignedDate) {
        this.assignedDate = assignedDate;
    }


    /**
     * Gets the finalScore value for this Exercise.
     * 
     * @return finalScore
     */
    public java.lang.String getFinalScore() {
        return finalScore;
    }


    /**
     * Sets the finalScore value for this Exercise.
     * 
     * @param finalScore
     */
    public void setFinalScore(java.lang.String finalScore) {
        this.finalScore = finalScore;
    }


    /**
     * Gets the firstQuestion value for this Exercise.
     * 
     * @return firstQuestion
     */
    public na.miniDao.Question getFirstQuestion() {
        return firstQuestion;
    }


    /**
     * Sets the firstQuestion value for this Exercise.
     * 
     * @param firstQuestion
     */
    public void setFirstQuestion(na.miniDao.Question firstQuestion) {
        this.firstQuestion = firstQuestion;
    }


    /**
     * Gets the state value for this Exercise.
     * 
     * @return state
     */
    public java.lang.String getState() {
        return state;
    }


    /**
     * Sets the state value for this Exercise.
     * 
     * @param state
     */
    public void setState(java.lang.String state) {
        this.state = state;
    }


    /**
     * Gets the lastQuestion value for this Exercise.
     * 
     * @return lastQuestion
     */
    public na.miniDao.Question getLastQuestion() {
        return lastQuestion;
    }


    /**
     * Sets the lastQuestion value for this Exercise.
     * 
     * @param lastQuestion
     */
    public void setLastQuestion(na.miniDao.Question lastQuestion) {
        this.lastQuestion = lastQuestion;
    }


    /**
     * Gets the questionnaire value for this Exercise.
     * 
     * @return questionnaire
     */
    public na.miniDao.Questionnaire getQuestionnaire() {
        return questionnaire;
    }


    /**
     * Sets the questionnaire value for this Exercise.
     * 
     * @param questionnaire
     */
    public void setQuestionnaire(na.miniDao.Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }


    /**
     * Gets the userID value for this Exercise.
     * 
     * @return userID
     */
    public int getUserID() {
        return userID;
    }


    /**
     * Sets the userID value for this Exercise.
     * 
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Exercise)) return false;
        Exercise other = (Exercise) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.ID == other.getID() &&
            ((this.startDate==null && other.getStartDate()==null) || 
             (this.startDate!=null &&
              this.startDate.equals(other.getStartDate()))) &&
            ((this.endDate==null && other.getEndDate()==null) || 
             (this.endDate!=null &&
              this.endDate.equals(other.getEndDate()))) &&
            ((this.assignedDate==null && other.getAssignedDate()==null) || 
             (this.assignedDate!=null &&
              this.assignedDate.equals(other.getAssignedDate()))) &&
            ((this.finalScore==null && other.getFinalScore()==null) || 
             (this.finalScore!=null &&
              this.finalScore.equals(other.getFinalScore()))) &&
            ((this.firstQuestion==null && other.getFirstQuestion()==null) || 
             (this.firstQuestion!=null &&
              this.firstQuestion.equals(other.getFirstQuestion()))) &&
            ((this.state==null && other.getState()==null) || 
             (this.state!=null &&
              this.state.equals(other.getState()))) &&
            ((this.lastQuestion==null && other.getLastQuestion()==null) || 
             (this.lastQuestion!=null &&
              this.lastQuestion.equals(other.getLastQuestion()))) &&
            ((this.questionnaire==null && other.getQuestionnaire()==null) || 
             (this.questionnaire!=null &&
              this.questionnaire.equals(other.getQuestionnaire()))) &&
            this.userID == other.getUserID();
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
        if (getStartDate() != null) {
            _hashCode += getStartDate().hashCode();
        }
        if (getEndDate() != null) {
            _hashCode += getEndDate().hashCode();
        }
        if (getAssignedDate() != null) {
            _hashCode += getAssignedDate().hashCode();
        }
        if (getFinalScore() != null) {
            _hashCode += getFinalScore().hashCode();
        }
        if (getFirstQuestion() != null) {
            _hashCode += getFirstQuestion().hashCode();
        }
        if (getState() != null) {
            _hashCode += getState().hashCode();
        }
        if (getLastQuestion() != null) {
            _hashCode += getLastQuestion().hashCode();
        }
        if (getQuestionnaire() != null) {
            _hashCode += getQuestionnaire().hashCode();
        }
        _hashCode += getUserID();
        __hashCodeCalc = false;
        return _hashCode;
    }

}
