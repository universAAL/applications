/**
 * NutritionistServiceBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package na.ws.extra;

import java.rmi.RemoteException;

import na.miniDao.Advise;
import na.miniDao.AdviseTime;
import na.miniDao.AdviseTimeInfo;
import na.miniDao.Answer;
import na.miniDao.Dish;
import na.miniDao.Exercise;
import na.miniDao.Food;
import na.miniDao.Meal;
import na.miniDao.NutriCalendar;
import na.miniDao.OtherCondition;
import na.miniDao.Question;
import na.miniDao.Questionnaire;
import na.miniDao.profile.PatientIncompatibilities;
import na.miniDao.profile.PatientList;
import na.miniDao.profile.PatientPicture;
import na.utils.NP.Nutrition.Habits;
import na.utils.NP.Nutrition.NutriPreferences;
import na.utils.NP.Nutrition.Report;
import na.ws.NutriSecurityException;
import na.ws.TokenExpiredException;

import org.apache.axis.types.Day;

//import org.universAAL.ontology.nutrition.profile.NutritionalPreferences;

public class NutritionistServiceBindingStub extends org.apache.axis.client.Stub
	implements NutritionistService {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc[] _operations;

    static {
	_operations = new org.apache.axis.description.OperationDesc[4];
	_initOperationDesc1();
    }

    private static void _initOperationDesc1() {
	org.apache.axis.description.OperationDesc oper;
	org.apache.axis.description.ParameterDesc param;
	// //////////////////////////////////////////////////////////////////////////////////////
	oper = new org.apache.axis.description.OperationDesc();
	oper.setName("setSingleProfileProperty");
	param = new org.apache.axis.description.ParameterDesc(
		new javax.xml.namespace.QName("", "token"),
		org.apache.axis.description.ParameterDesc.IN,
		new javax.xml.namespace.QName(
			"http://www.w3.org/2001/XMLSchema", "string"),
		java.lang.String.class, false, false);
	param.setOmittable(true);
	param.setNillable(true);
	oper.addParameter(param);
	param = new org.apache.axis.description.ParameterDesc(
		new javax.xml.namespace.QName("", "type"),
		org.apache.axis.description.ParameterDesc.IN,
		new javax.xml.namespace.QName(
			"http://www.w3.org/2001/XMLSchema", "int"), int.class,
		false, false);
	oper.addParameter(param);
	param = new org.apache.axis.description.ParameterDesc(
		new javax.xml.namespace.QName("", "propertyParentName"),
		org.apache.axis.description.ParameterDesc.IN,
		new javax.xml.namespace.QName(
			"http://www.w3.org/2001/XMLSchema", "string"),
		java.lang.String.class, false, false);
	param.setOmittable(true);
	param.setNillable(true);
	oper.addParameter(param);
	param = new org.apache.axis.description.ParameterDesc(
		new javax.xml.namespace.QName("", "propertyChildrenName"),
		org.apache.axis.description.ParameterDesc.IN,
		new javax.xml.namespace.QName(
			"http://www.w3.org/2001/XMLSchema", "string"),
		java.lang.String.class, false, false);
	param.setOmittable(true);
	param.setNillable(true);
	oper.addParameter(param);
	param = new org.apache.axis.description.ParameterDesc(
		new javax.xml.namespace.QName("", "patientID"),
		org.apache.axis.description.ParameterDesc.IN,
		new javax.xml.namespace.QName(
			"http://www.w3.org/2001/XMLSchema", "int"), int.class,
		false, false);
	oper.addParameter(param);
	param = new org.apache.axis.description.ParameterDesc(
		new javax.xml.namespace.QName("", "values"),
		org.apache.axis.description.ParameterDesc.IN,
		new javax.xml.namespace.QName(
			"http://www.w3.org/2001/XMLSchema", "string"),
		java.lang.String.class, false, false);
	param.setOmittable(true);
	param.setNillable(true);
	oper.addParameter(param);
	oper.setReturnType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "string"));
	oper.setReturnClass(java.lang.String.class);
	oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
	oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
	oper.setUse(org.apache.axis.constants.Use.LITERAL);
	_operations[0] = oper;
	// /////////////////////////////////////////////////////////////////////
	oper = new org.apache.axis.description.OperationDesc();
	oper.setName("setProfileProperty");
	param = new org.apache.axis.description.ParameterDesc(
		new javax.xml.namespace.QName("", "token"),
		org.apache.axis.description.ParameterDesc.IN,
		new javax.xml.namespace.QName(
			"http://www.w3.org/2001/XMLSchema", "string"),
		java.lang.String.class, false, false);
	param.setOmittable(true);
	param.setNillable(true);
	oper.addParameter(param);
	param = new org.apache.axis.description.ParameterDesc(
		new javax.xml.namespace.QName("", "patientID"),
		org.apache.axis.description.ParameterDesc.IN,
		new javax.xml.namespace.QName(
			"http://www.w3.org/2001/XMLSchema", "int"), int.class,
		false, false);
	oper.addParameter(param);
	param = new org.apache.axis.description.ParameterDesc(
		new javax.xml.namespace.QName("", "propertyName"),
		org.apache.axis.description.ParameterDesc.IN,
		new javax.xml.namespace.QName(
			"http://www.w3.org/2001/XMLSchema", "string"),
		java.lang.String.class, false, false);
	param.setOmittable(true);
	param.setNillable(true);
	oper.addParameter(param);
	param = new org.apache.axis.description.ParameterDesc(
		new javax.xml.namespace.QName("", "value"),
		org.apache.axis.description.ParameterDesc.IN,
		new javax.xml.namespace.QName(
			"http://www.w3.org/2001/XMLSchema", "string"),
		java.lang.String[].class, false, false);
	param.setOmittable(true);
	param.setNillable(true);
	oper.addParameter(param);
	oper.setReturnType(new javax.xml.namespace.QName(
		"http://www.w3.org/2001/XMLSchema", "string"));
	oper.setReturnClass(java.lang.String.class);
	oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
	oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
	oper.setUse(org.apache.axis.constants.Use.LITERAL);
	_operations[1] = oper;
	// /////////////////////////////////////////////////////////////////////
	oper = new org.apache.axis.description.OperationDesc();
	oper.setName("getPatientsList");
	param = new org.apache.axis.description.ParameterDesc(
		new javax.xml.namespace.QName("", "token"),
		org.apache.axis.description.ParameterDesc.IN,
		new javax.xml.namespace.QName(
			"http://www.w3.org/2001/XMLSchema", "string"),
		java.lang.String.class, false, false);
	param.setOmittable(true);
	param.setNillable(true);
	oper.addParameter(param);
	oper.setReturnType(new javax.xml.namespace.QName(
		"http://profile.nutritionist/", "patientList"));
	oper.setReturnClass(PatientList.class);
	oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
	oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
	oper.setUse(org.apache.axis.constants.Use.LITERAL);
	_operations[2] = oper;
	// /////////////////////////////////////////////////////////////////////
	oper = new org.apache.axis.description.OperationDesc();
	oper.setName("getToken");
	param = new org.apache.axis.description.ParameterDesc(
		new javax.xml.namespace.QName("", "clientVersion"),
		org.apache.axis.description.ParameterDesc.IN,
		new javax.xml.namespace.QName(
			"http://www.w3.org/2001/XMLSchema", "string"),
		java.lang.String.class, false, false);
	param.setOmittable(true);
	param.setNillable(true);
	oper.addParameter(param);
	param = new org.apache.axis.description.ParameterDesc(
		new javax.xml.namespace.QName("", "username"),
		org.apache.axis.description.ParameterDesc.IN,
		new javax.xml.namespace.QName(
			"http://www.w3.org/2001/XMLSchema", "string"),
		java.lang.String.class, false, false);
	param.setOmittable(true);
	param.setNillable(true);
	oper.addParameter(param);
	param = new org.apache.axis.description.ParameterDesc(
		new javax.xml.namespace.QName("", "password"),
		org.apache.axis.description.ParameterDesc.IN,
		new javax.xml.namespace.QName(
			"http://www.w3.org/2001/XMLSchema", "string"),
		java.lang.String.class, false, false);
	param.setOmittable(true);
	param.setNillable(true);
	oper.addParameter(param);

	oper.setReturnType(new javax.xml.namespace.QName("http://ws.na/",
		"user"));
	oper.setReturnClass(User.class);
	oper.setReturnQName(new javax.xml.namespace.QName("", "return"));
	oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
	oper.setUse(org.apache.axis.constants.Use.LITERAL);
	oper.addFault(new org.apache.axis.description.FaultDesc(
		new javax.xml.namespace.QName("http://ws.na/",
			"NutriSecurityException"),
		"na.ws.extra.NutriSecurityException",
		new javax.xml.namespace.QName("http://ws.na/",
			"NutriSecurityException"), true));
	_operations[3] = oper;
    }

    public NutritionistServiceBindingStub() throws org.apache.axis.AxisFault {
	this(null);
    }

    public NutritionistServiceBindingStub(java.net.URL endpointURL,
	    javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
	this(service);
	super.cachedEndpoint = endpointURL;
    }

    public NutritionistServiceBindingStub(javax.xml.rpc.Service service)
	    throws org.apache.axis.AxisFault {
	if (service == null) {
	    super.service = new org.apache.axis.client.Service();
	} else {
	    super.service = service;
	}
	((org.apache.axis.client.Service) super.service)
		.setTypeMappingVersion("1.2");
	java.lang.Class cls;
	javax.xml.namespace.QName qName;
	javax.xml.namespace.QName qName2;
	java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
	java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
	java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
	java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
	java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
	java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
	java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
	java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
	java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
	java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
	qName = new javax.xml.namespace.QName("http://menu.miniDao/", "day");
	cachedSerQNames.add(qName);
	cls = Day.class;
	cachedSerClasses.add(cls);
	cachedSerFactories.add(beansf);
	cachedDeserFactories.add(beandf);

	qName = new javax.xml.namespace.QName("http://menu.miniDao/", "dish");
	cachedSerQNames.add(qName);
	cls = Dish.class;
	cachedSerClasses.add(cls);
	cachedSerFactories.add(beansf);
	cachedDeserFactories.add(beandf);

	qName = new javax.xml.namespace.QName("http://menu.miniDao/", "food");
	cachedSerQNames.add(qName);
	cls = Food.class;
	cachedSerClasses.add(cls);
	cachedSerFactories.add(beansf);
	cachedDeserFactories.add(beandf);

	// qName = new javax.xml.namespace.QName("http://menu.miniDao/",
	// "foodIncompatibility");
	// cachedSerQNames.add(qName);
	// cls = FoodIncompatibility.class;
	// cachedSerClasses.add(cls);
	// cachedSerFactories.add(beansf);
	// cachedDeserFactories.add(beandf);

	qName = new javax.xml.namespace.QName("http://menu.miniDao/", "meal");
	cachedSerQNames.add(qName);
	cls = Meal.class;
	cachedSerClasses.add(cls);
	cachedSerFactories.add(beansf);
	cachedDeserFactories.add(beandf);

	// qName = new javax.xml.namespace.QName("http://menu.miniDao/",
	// "menuCalendar");
	// cachedSerQNames.add(qName);
	// cls = MenuCalendar.class;
	// cachedSerClasses.add(cls);
	// cachedSerFactories.add(beansf);
	// cachedDeserFactories.add(beandf);

	// qName = new javax.xml.namespace.QName("http://menu.miniDao/",
	// "menuSource");
	// cachedSerQNames.add(qName);
	// cls = MenuSource.class;
	// cachedSerClasses.add(cls);
	// cachedSerFactories.add(beansf);
	// cachedDeserFactories.add(beandf);

	// qName = new javax.xml.namespace.QName("http://menu.miniDao/",
	// "nutritionMenu");
	// cachedSerQNames.add(qName);
	// cls = NutritionMenu.class;
	// cachedSerClasses.add(cls);
	// cachedSerFactories.add(beansf);
	// cachedDeserFactories.add(beandf);

	qName = new javax.xml.namespace.QName("http://miniDao/", "advise");
	cachedSerQNames.add(qName);
	cls = Advise.class;
	cachedSerClasses.add(cls);
	cachedSerFactories.add(beansf);
	cachedDeserFactories.add(beandf);

	qName = new javax.xml.namespace.QName("http://miniDao/", "adviseTime");
	cachedSerQNames.add(qName);
	cls = AdviseTime.class;
	cachedSerClasses.add(cls);
	cachedSerFactories.add(beansf);
	cachedDeserFactories.add(beandf);

	qName = new javax.xml.namespace.QName("http://miniDao/",
		"adviseTimeInfo");
	cachedSerQNames.add(qName);
	cls = AdviseTimeInfo.class;
	cachedSerClasses.add(cls);
	cachedSerFactories.add(beansf);
	cachedDeserFactories.add(beandf);

	qName = new javax.xml.namespace.QName("http://miniDao/",
		"nutriCalendar");
	cachedSerQNames.add(qName);
	cls = NutriCalendar.class;
	cachedSerClasses.add(cls);
	cachedSerFactories.add(beansf);
	cachedDeserFactories.add(beandf);

	qName = new javax.xml.namespace.QName("http://miniDao/",
		"otherCondition");
	cachedSerQNames.add(qName);
	cls = OtherCondition.class;
	cachedSerClasses.add(cls);
	cachedSerFactories.add(beansf);
	cachedDeserFactories.add(beandf);

	// qName = new javax.xml.namespace.QName("http://oasis.itaca.org/",
	// "addressInfo");
	// cachedSerQNames.add(qName);
	// cls = AddressInfo.class;
	// cachedSerClasses.add(cls);
	// cachedSerFactories.add(beansf);
	// cachedDeserFactories.add(beandf);

	// qName = new javax.xml.namespace.QName("http://oasis.itaca.org/",
	// "anthropometry");
	// cachedSerQNames.add(qName);
	// cls = Anthropometry.class;
	// cachedSerClasses.add(cls);
	// cachedSerFactories.add(beansf);
	// cachedDeserFactories.add(beandf);

	// qName = new javax.xml.namespace.QName("http://oasis.itaca.org/",
	// "bioparameters");
	// cachedSerQNames.add(qName);
	// cls = Bioparameters.class;
	// cachedSerClasses.add(cls);
	// cachedSerFactories.add(beansf);
	// cachedDeserFactories.add(beandf);

	// qName = new javax.xml.namespace.QName("http://oasis.itaca.org/",
	// "clinical");
	// cachedSerQNames.add(qName);
	// cls = Clinical.class;
	// cachedSerClasses.add(cls);
	// cachedSerFactories.add(beansf);
	// cachedDeserFactories.add(beandf);

	// qName = new javax.xml.namespace.QName("http://oasis.itaca.org/",
	// "dietetics");
	// cachedSerQNames.add(qName);
	// cls = Dietetics.class;
	// cachedSerClasses.add(cls);
	// cachedSerFactories.add(beansf);
	// cachedDeserFactories.add(beandf);

	// qName = new javax.xml.namespace.QName("http://oasis.itaca.org/",
	// "general");
	// cachedSerQNames.add(qName);
	// cls = General.class;
	// cachedSerClasses.add(cls);
	// cachedSerFactories.add(beansf);
	// cachedDeserFactories.add(beandf);

	qName = new javax.xml.namespace.QName("http://oasis.itaca.org/",
		"habits");
	cachedSerQNames.add(qName);
	cls = Habits.class;
	cachedSerClasses.add(cls);
	cachedSerFactories.add(beansf);
	cachedDeserFactories.add(beandf);

	qName = new javax.xml.namespace.QName("http://oasis.itaca.org/",
		"NutriSecurityException");
	cachedSerQNames.add(qName);
	cls = NutriSecurityException.class;
	cachedSerClasses.add(cls);
	cachedSerFactories.add(beansf);
	cachedDeserFactories.add(beandf);

	// qName = new javax.xml.namespace.QName("http://oasis.itaca.org/",
	// "NutritionalMenusException");
	// cachedSerQNames.add(qName);
	// cls = NutritionalMenusException.class;
	// cachedSerClasses.add(cls);
	// cachedSerFactories.add(beansf);
	// cachedDeserFactories.add(beandf);

	qName = new javax.xml.namespace.QName("http://oasis.itaca.org/",
		"nutritionalPreferences");
	cachedSerQNames.add(qName);
	cls = NutriPreferences.class;
	cachedSerClasses.add(cls);
	cachedSerFactories.add(beansf);
	cachedDeserFactories.add(beandf);

	// qName = new javax.xml.namespace.QName("http://oasis.itaca.org/",
	// "patientProfile");
	// cachedSerQNames.add(qName);
	// cls = PatientProfile.class;
	// cachedSerClasses.add(cls);
	// cachedSerFactories.add(beansf);
	// cachedDeserFactories.add(beandf);

	// qName = new javax.xml.namespace.QName("http://oasis.itaca.org/",
	// "physical");
	// cachedSerQNames.add(qName);
	// cls = Physical.class;
	// cachedSerClasses.add(cls);
	// cachedSerFactories.add(beansf);
	// cachedDeserFactories.add(beandf);

	// qName = new javax.xml.namespace.QName("http://oasis.itaca.org/",
	// "QuestionnaireException");
	// cachedSerQNames.add(qName);
	// cls = QuestionnaireException.class;
	// cachedSerClasses.add(cls);
	// cachedSerFactories.add(beansf);
	// cachedDeserFactories.add(beandf);

	// qName = new javax.xml.namespace.QName("http://oasis.itaca.org/",
	// "relevant");
	// cachedSerQNames.add(qName);
	// cls = Relevant.class;
	// cachedSerClasses.add(cls);
	// cachedSerFactories.add(beansf);
	// cachedDeserFactories.add(beandf);

	qName = new javax.xml.namespace.QName("http://oasis.itaca.org/",
		"report");
	cachedSerQNames.add(qName);
	cls = Report.class;
	cachedSerClasses.add(cls);
	cachedSerFactories.add(beansf);
	cachedDeserFactories.add(beandf);

	qName = new javax.xml.namespace.QName("http://oasis.itaca.org/", "user");
	cachedSerQNames.add(qName);
	cls = User.class;
	cachedSerClasses.add(cls);
	cachedSerFactories.add(beansf);
	cachedDeserFactories.add(beandf);

	qName = new javax.xml.namespace.QName("http://profile.miniDao/",
		"patientIncompatibilities");
	cachedSerQNames.add(qName);
	cls = PatientIncompatibilities.class;
	cachedSerClasses.add(cls);
	cachedSerFactories.add(beansf);
	cachedDeserFactories.add(beandf);

	qName = new javax.xml.namespace.QName("http://profile.miniDao/",
		"patientList");
	cachedSerQNames.add(qName);
	cls = PatientList.class;
	cachedSerClasses.add(cls);
	cachedSerFactories.add(beansf);
	cachedDeserFactories.add(beandf);

	qName = new javax.xml.namespace.QName("http://profile.miniDao/",
		"patientPicture");
	cachedSerQNames.add(qName);
	cls = PatientPicture.class;
	cachedSerClasses.add(cls);
	cachedSerFactories.add(beansf);
	cachedDeserFactories.add(beandf);

	qName = new javax.xml.namespace.QName("http://questionnaire.miniDao/",
		"answer");
	cachedSerQNames.add(qName);
	cls = Answer.class;
	cachedSerClasses.add(cls);
	cachedSerFactories.add(beansf);
	cachedDeserFactories.add(beandf);

	// qName = new
	// javax.xml.namespace.QName("http://questionnaire.miniDao/",
	// "answeredQuestion");
	// cachedSerQNames.add(qName);
	// cls = AnsweredQuestion.class;
	// cachedSerClasses.add(cls);
	// cachedSerFactories.add(beansf);
	// cachedDeserFactories.add(beandf);

	qName = new javax.xml.namespace.QName("http://questionnaire.miniDao/",
		"exercise");
	cachedSerQNames.add(qName);
	cls = Exercise.class;
	cachedSerClasses.add(cls);
	cachedSerFactories.add(beansf);
	cachedDeserFactories.add(beandf);

	qName = new javax.xml.namespace.QName("http://questionnaire.miniDao/",
		"question");
	cachedSerQNames.add(qName);
	cls = Question.class;
	cachedSerClasses.add(cls);
	cachedSerFactories.add(beansf);
	cachedDeserFactories.add(beandf);

	qName = new javax.xml.namespace.QName("http://questionnaire.miniDao/",
		"questionnaire");
	cachedSerQNames.add(qName);
	cls = Questionnaire.class;
	cachedSerClasses.add(cls);
	cachedSerFactories.add(beansf);
	cachedDeserFactories.add(beandf);

    }

    protected org.apache.axis.client.Call createCall()
	    throws java.rmi.RemoteException {
	try {
	    org.apache.axis.client.Call _call = super._createCall();
	    if (super.maintainSessionSet) {
		_call.setMaintainSession(super.maintainSession);
	    }
	    if (super.cachedUsername != null) {
		_call.setUsername(super.cachedUsername);
	    }
	    if (super.cachedPassword != null) {
		_call.setPassword(super.cachedPassword);
	    }
	    if (super.cachedEndpoint != null) {
		_call.setTargetEndpointAddress(super.cachedEndpoint);
	    }
	    if (super.cachedTimeout != null) {
		_call.setTimeout(super.cachedTimeout);
	    }
	    if (super.cachedPortName != null) {
		_call.setPortName(super.cachedPortName);
	    }
	    java.util.Enumeration keys = super.cachedProperties.keys();
	    while (keys.hasMoreElements()) {
		java.lang.String key = (java.lang.String) keys.nextElement();
		_call.setProperty(key, super.cachedProperties.get(key));
	    }
	    // All the type mapping information is registered
	    // when the first call is made.
	    // The type mapping information is actually registered in
	    // the TypeMappingRegistry of the service, which
	    // is the reason why registration is only needed for the first call.
	    synchronized (this) {
		if (firstCall()) {
		    // must set encoding style before registering serializers
		    _call.setEncodingStyle(null);
		    for (int i = 0; i < cachedSerFactories.size(); ++i) {
			java.lang.Class cls = (java.lang.Class) cachedSerClasses
				.get(i);
			javax.xml.namespace.QName qName = (javax.xml.namespace.QName) cachedSerQNames
				.get(i);
			java.lang.Object x = cachedSerFactories.get(i);
			if (x instanceof Class) {
			    java.lang.Class sf = (java.lang.Class) cachedSerFactories
				    .get(i);
			    java.lang.Class df = (java.lang.Class) cachedDeserFactories
				    .get(i);
			    _call.registerTypeMapping(cls, qName, sf, df, false);
			} else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
			    org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory) cachedSerFactories
				    .get(i);
			    org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory) cachedDeserFactories
				    .get(i);
			    _call.registerTypeMapping(cls, qName, sf, df, false);
			}
		    }
		}
	    }
	    return _call;
	} catch (java.lang.Throwable _t) {
	    throw new org.apache.axis.AxisFault(
		    "Failure trying to get the Call object", _t);
	}
    }

    public String setSingleProfileProperty(String token, int type,
	    String propertyParentName, String propertyChildrenName,
	    int patientID, String[] values, boolean isMultiple)
	    throws RemoteException, NutriSecurityException,
	    TokenExpiredException {
	if (super.cachedEndpoint == null) {
	    throw new org.apache.axis.NoEndPointException();
	}
	org.apache.axis.client.Call _call = createCall();
	_call.setOperation(_operations[0]);
	_call.setUseSOAPAction(true);
	_call.setSOAPActionURI("");// urn:setSingleProfileProperty
	_call.setEncodingStyle(null);
	_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR,
		Boolean.FALSE);
	_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS,
		Boolean.FALSE);
	_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
	_call.setOperationName(new javax.xml.namespace.QName("http://ws.na/",
		"setSingleProfileProperty"));

	setRequestHeaders(_call);
	setAttachments(_call);
	try {
	    java.lang.Object _resp = _call.invoke(new java.lang.Object[] {
		    token, type, propertyParentName, propertyChildrenName,
		    patientID, values, isMultiple });

	    if (_resp instanceof java.rmi.RemoteException) {
		throw (java.rmi.RemoteException) _resp;
	    } else {
		extractAttachments(_call);
		try {
		    return (java.lang.String) _resp;
		} catch (java.lang.Exception _exception) {
		    return (java.lang.String) org.apache.axis.utils.JavaUtils
			    .convert(_resp, java.lang.String.class);
		}
	    }
	} catch (org.apache.axis.AxisFault axisFaultException) {
	    throw axisFaultException;
	}
    }

    public java.lang.String setProfileProperty(String token, int patientID,
	    String propertyName, String value) throws RemoteException,
	    NutriSecurityException, TokenExpiredException {
	if (super.cachedEndpoint == null) {
	    throw new org.apache.axis.NoEndPointException();
	}
	org.apache.axis.client.Call _call = createCall();
	_call.setOperation(_operations[1]);
	_call.setUseSOAPAction(true);
	_call.setSOAPActionURI("");// urn:setProfileProperty
	_call.setEncodingStyle(null);
	_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR,
		Boolean.FALSE);
	_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS,
		Boolean.FALSE);
	_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
	_call.setOperationName(new javax.xml.namespace.QName("http://ws.na/",
		"setProfileProperty"));

	setRequestHeaders(_call);
	setAttachments(_call);
	try {
	    java.lang.Object _resp = _call.invoke(new java.lang.Object[] {
		    token, patientID, propertyName, value });

	    if (_resp instanceof java.rmi.RemoteException) {
		throw (java.rmi.RemoteException) _resp;
	    } else {
		extractAttachments(_call);
		try {
		    return (java.lang.String) _resp;
		} catch (java.lang.Exception _exception) {
		    return (java.lang.String) org.apache.axis.utils.JavaUtils
			    .convert(_resp, java.lang.String.class);
		}
	    }
	} catch (org.apache.axis.AxisFault axisFaultException) {
	    throw axisFaultException;
	}
    }

    public PatientList[] getPatientsList(String token) throws RemoteException {
	if (super.cachedEndpoint == null) {
	    throw new org.apache.axis.NoEndPointException();
	}
	org.apache.axis.client.Call _call = createCall();
	_call.setOperation(_operations[2]);
	_call.setUseSOAPAction(true);
	_call.setSOAPActionURI("");// urn:setProfileProperty
	_call.setEncodingStyle(null);
	_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR,
		Boolean.FALSE);
	_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS,
		Boolean.FALSE);
	_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
	_call.setOperationName(new javax.xml.namespace.QName("http://ws.na/",
		"setProfileProperty"));

	setRequestHeaders(_call);
	setAttachments(_call);
	try {
	    java.lang.Object _resp = _call
		    .invoke(new java.lang.Object[] { token });

	    if (_resp instanceof java.rmi.RemoteException) {
		throw (java.rmi.RemoteException) _resp;
	    } else {
		extractAttachments(_call);
		try {
		    return (PatientList[]) _resp;
		} catch (java.lang.Exception _exception) {
		    return (PatientList[]) org.apache.axis.utils.JavaUtils
			    .convert(_resp, PatientList[].class);
		}
	    }
	} catch (org.apache.axis.AxisFault axisFaultException) {
	    throw axisFaultException;
	}
    }

    public User getToken(String clientVersion, String username, String password)
	    throws RemoteException, na.ws.extra.NutriSecurityException {
	if (super.cachedEndpoint == null) {
	    throw new org.apache.axis.NoEndPointException();
	}
	org.apache.axis.client.Call _call = createCall();
	_call.setOperation(_operations[3]);
	_call.setUseSOAPAction(true);
	_call.setSOAPActionURI("");
	_call.setEncodingStyle(null);
	_call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR,
		Boolean.FALSE);
	_call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS,
		Boolean.FALSE);
	_call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
	_call.setOperationName(new javax.xml.namespace.QName("http://ws.na/",
		"getToken"));

	setRequestHeaders(_call);
	setAttachments(_call);
	try {
	    java.lang.Object _resp = _call.invoke(new java.lang.Object[] {
		    clientVersion, username, password });

	    if (_resp instanceof java.rmi.RemoteException) {
		throw (java.rmi.RemoteException) _resp;
	    } else {
		extractAttachments(_call);
		try {
		    return (User) _resp;
		} catch (java.lang.Exception _exception) {
		    return (User) org.apache.axis.utils.JavaUtils.convert(
			    _resp, User.class);
		}
	    }
	} catch (org.apache.axis.AxisFault axisFaultException) {
	    if (axisFaultException.detail != null) {
		if (axisFaultException.detail instanceof java.rmi.RemoteException) {
		    throw (java.rmi.RemoteException) axisFaultException.detail;
		}
		if (axisFaultException.detail instanceof NutriSecurityException) {
		    throw (NutriSecurityException) axisFaultException.detail;
		}
	    }
	    throw axisFaultException;
	}
    }

}
