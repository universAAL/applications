/**
 * NutritionistService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.ws.extra;

public interface NutritionistService extends java.rmi.Remote {
    // public java.lang.String activateMenu(java.lang.String token, int
    // patientID, int menuID) throws java.rmi.RemoteException,
    // org.itaca.oasis.NutriSecurityException;
    // public java.lang.String addNewAdviseToPatient(java.lang.String token, int
    // patientID, miniDao.Advise newAdvise) throws java.rmi.RemoteException,
    // org.itaca.oasis.NutriSecurityException;
    // public miniDao.menu.Day adjustDayMenuQuantities(java.lang.String token,
    // miniDao.menu.Day referenceMenu, miniDao.menu.Day userMenu) throws
    // java.rmi.RemoteException, org.itaca.oasis.NutritionalMenusException,
    // org.itaca.oasis.NutriSecurityException;
    // public miniDao.menu.NutritionMenu adjustMenuToGoalKCal(java.lang.String
    // token, int menuWeekID, double kCalPerDay) throws
    // java.rmi.RemoteException, org.itaca.oasis.NutriSecurityException;
    // public java.lang.String assignMenuWeekToPatient(java.lang.String token,
    // miniDao.menu.NutritionMenu currentMenu, int patientID) throws
    // java.rmi.RemoteException, org.itaca.oasis.NutriSecurityException;
    // public java.lang.String assignQuestionnaireToUser(java.lang.String token,
    // int questionnaireID, int patientID, java.util.Calendar endDate) throws
    // java.rmi.RemoteException, org.itaca.oasis.NutriSecurityException,
    // org.itaca.oasis.QuestionnaireException;
    // public miniDao.menu.MenuCalendar[]
    // deleteMenuFromMenuCalendar(java.lang.String token, int patientID, int
    // arg2) throws java.rmi.RemoteException,
    // org.itaca.oasis.NutriSecurityException;
    // public java.lang.String editAdvise(java.lang.String token, int patientID,
    // miniDao.Advise newAdvise) throws java.rmi.RemoteException,
    // org.itaca.oasis.NutriSecurityException;
    // public miniDao.questionnaire.Questionnaire[]
    // getAllQuestionnaires(java.lang.String token, int lang) throws
    // java.rmi.RemoteException, org.itaca.oasis.NutriSecurityException;
    // public miniDao.menu.Dish[] getAlternativeDishes(java.lang.String token,
    // int dishID, miniDao.profile.PatientIncompatibilities patientData) throws
    // java.rmi.RemoteException, org.itaca.oasis.NutriSecurityException;
    // public org.itaca.oasis.PatientProfile
    // getBasicPatientProfile(java.lang.String token, int patientID) throws
    // java.rmi.RemoteException;
    // public miniDao.menu.MenuSource[] getMenuSourceList(java.lang.String
    // token) throws java.rmi.RemoteException,
    // org.itaca.oasis.NutriSecurityException;
    // public miniDao.menu.NutritionMenu getMenuWeek(java.lang.String token, int
    // menuID) throws java.rmi.RemoteException,
    // org.itaca.oasis.NutriSecurityException;
    // public miniDao.menu.NutritionMenu
    // getMenuWeekFoodIncompatibilities(java.lang.String token,
    // miniDao.menu.NutritionMenu menuWeek,
    // miniDao.profile.PatientIncompatibilities patientData) throws
    // java.rmi.RemoteException, org.itaca.oasis.NutriSecurityException;
    // public org.itaca.oasis.Report getNutritionalReport(java.lang.String
    // token, int patientID) throws java.rmi.RemoteException;
    // public miniDao.questionnaire.Exercise
    // getPatientAnswersToQuestionnaire(java.lang.String token, int patientID,
    // int exerciseID) throws java.rmi.RemoteException,
    // org.itaca.oasis.NutriSecurityException;
    // public miniDao.questionnaire.Exercise[]
    // getPatientAssignedQuestionnaires(java.lang.String token, int patientID)
    // throws java.rmi.RemoteException, org.itaca.oasis.NutriSecurityException;
    // public miniDao.menu.MenuCalendar[]
    // getPatientMenuCalendar(java.lang.String token, int patientID) throws
    // java.rmi.RemoteException, org.itaca.oasis.NutriSecurityException;
    // public miniDao.profile.PatientPicture getPatientPicture(java.lang.String
    // token, int patientID) throws java.rmi.RemoteException;
    // public org.itaca.oasis.PatientProfile getPatientProfile(java.lang.String
    // token, int patientID) throws java.rmi.RemoteException;
    // public miniDao.Advise[] getPatientsAdvises(java.lang.String token, int
    // patientID) throws java.rmi.RemoteException,
    // org.itaca.oasis.NutriSecurityException;
    public na.miniDao.profile.PatientList[] getPatientsList(
	    java.lang.String token) throws java.rmi.RemoteException;

    // public byte[] getQuestionPicture(java.lang.String token, int questionID)
    // throws java.rmi.RemoteException, org.itaca.oasis.NutriSecurityException;
    // public miniDao.questionnaire.Questionnaire
    // getQuestionnaire(java.lang.String token, int questionnaireID) throws
    // java.rmi.RemoteException, org.itaca.oasis.NutriSecurityException;
    public User getToken(java.lang.String clientVersion,
	    java.lang.String username, java.lang.String password)
	    throws java.rmi.RemoteException, NutriSecurityException;

    // public java.lang.String login(java.lang.String username, java.lang.String
    // password) throws java.rmi.RemoteException,
    // org.itaca.oasis.NutriSecurityException;
    // public int register(java.lang.String firstName, java.lang.String
    // lastName, java.lang.String username, java.lang.String password,
    // java.lang.String email, java.lang.String city, int countryCode) throws
    // java.rmi.RemoteException;
    // public miniDao.profile.PatientList[]
    // searchPatientByNameLastname(java.lang.String token, java.lang.String
    // firstName, java.lang.String lastName) throws java.rmi.RemoteException;
    // public miniDao.menu.MenuCalendar[] setMenuCalendarOrder(java.lang.String
    // token, int patientID, int menuID, int direction) throws
    // java.rmi.RemoteException, org.itaca.oasis.NutriSecurityException;
    // public java.lang.String setMultipleProfileProperty(java.lang.String arg0,
    // int arg1, java.lang.String arg2, java.lang.String[] arg3) throws
    // java.rmi.RemoteException;
    // public java.lang.String setSingleProfileProperty(java.lang.String arg0,
    // int arg1, java.lang.String arg2, java.lang.String arg3) throws
    // java.rmi.RemoteException;
    // public java.lang.String storeMenuWeek(java.lang.String token,
    // miniDao.menu.NutritionMenu newMenu) throws java.rmi.RemoteException,
    // org.itaca.oasis.NutriSecurityException;
    // public java.lang.String updateMenuWeek(java.lang.String token,
    // miniDao.menu.NutritionMenu updatedMenu) throws java.rmi.RemoteException,
    // org.itaca.oasis.NutriSecurityException;
    public java.lang.String setSingleProfileProperty(String token, int type,
	    String propertyParentName, String propertyChildrenName,
	    int patientID, String[] values, boolean isMultiple)
	    throws java.rmi.RemoteException, na.ws.NutriSecurityException,
	    na.ws.TokenExpiredException;;

    public java.lang.String setProfileProperty(String token, int patientID,
	    String propertyName, String value) throws java.rmi.RemoteException,
	    na.ws.NutriSecurityException, na.ws.TokenExpiredException;

}
