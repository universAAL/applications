package org.universAAL.AALApplication.health.motivation.treatment;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import junit.framework.Assert;

import org.drools.FactHandle;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.universAAL.AALApplication.health.motivation.MotivationServiceRequirementsIface;
import org.universAAL.AALApplication.health.motivation.SendMotivationMessageIface;
import org.universAAL.AALApplication.health.motivation.motivatonalMessageManagement.MessageManager;
import org.universAAL.AALApplication.health.motivation.motivatonalMessageManagement.MessageVariables;
import org.universAAL.AALApplication.health.motivation.schedulingTools.SchedulingTools;
import org.universAAL.AALApplication.health.motivation.testSupportClasses.DetectedTreatments;
import org.universAAL.AALApplication.health.motivation.testSupportClasses.PerformedSessionsSupport;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.ontology.ICD10CirculatorySystemDiseases.owl.HeartFailure;
import org.universAAL.ontology.health.owl.Diet;
import org.universAAL.ontology.health.owl.MeasuredPhysicalActivity;
import org.universAAL.ontology.health.owl.MotivationalStatusType;
import org.universAAL.ontology.health.owl.StatusType;
import org.universAAL.ontology.health.owl.Treatment;
import org.universAAL.ontology.health.owl.TreatmentPlanning;
import org.universAAL.ontology.health.owl.Walking;
import org.universAAL.ontology.health.owl.WeightMeasurementTreatment;
import org.universAAL.ontology.health.owl.WeightRequirement;
import org.universAAL.ontology.profile.User;
import org.universaal.ontology.owl.MotivationalMessage;
import org.universaal.ontology.owl.MotivationalMessageClassification;
import org.universaal.ontology.owl.MotivationalMessageSubclassification;
import org.universaal.ontology.owl.MotivationalQuestionnaire;
import org.universaal.ontology.owl.Questionnaire4TreatmentStrategy;
import org.universAAL.ontology.questionnaire.*;

public class TestMotivationalStatusChange extends TestIface{

	private KnowledgeBase kbase;
	private static StatefulKnowledgeSession ksession;
	
	private SendMotivationMessageIface iTestTreatment = this;
	private MotivationServiceRequirementsIface iTestStatusForMMVariables = this;
	
	public FactHandle wmt1ChangingStatus;
	
	public WeightMeasurementTreatment wmt1;
	public WeightMeasurementTreatment wmt2;
	
	public User ap; 
	
	// Agreement questionnaire sent to the assisted person
	private String qWording = "$partOfDay $userName! A new treatment, named $treatmentName has been asigned to you. This treatment consists of $treatmentDescription. Do you plan to follow it?";
			
	private ChoiceLabel choice1;
	private ChoiceLabel choice2;
	private ChoiceLabel[] booleanChoices;

	private SingleChoiceQuestion agreementQuestion;
	
	private Questionnaire questionnaire;
	private MotivationalQuestionnaire firstQuestionnaire;
	
	// Ponemos a punto la working memory
	
	@Before
	public void setUp() throws Exception{
		
		registerClassesNeeded();
		
		TestIface.resetMessagesSent();
		ap = this.getAssistedPerson(); 		
		//load up the knowledge base
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		InputStream is = getClass().getResourceAsStream("/rules/TreatmentRules.drl");
		kbuilder.add(ResourceFactory.newInputStreamResource(is), ResourceType.DRL);
		KnowledgeBuilderErrors errors = kbuilder.getErrors();
		if (errors.size() > 0) {
			for (KnowledgeBuilderError error: errors) {
				System.err.println(error);
			}
			throw new IllegalArgumentException("Could not parse knowledge.");
		}
		
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
		ksession = kbase.newStatefulKnowledgeSession();
		
		MessageManager.setLanguage(Locale.ENGLISH);
		MessageManager.setMMSenderIface(iTestTreatment); //para las pruebas, usamos la interfaz de pruebas
		MessageManager.buildMapStructure();
		MessageVariables.setMotivationServiceRequirementsIface(iTestStatusForMMVariables);
		MessageManager.buildInitialMapOfVariables();

		
		// Agreement questionnaire sent to the assisted person

		choice1 = new ChoiceLabel(Boolean.TRUE, "Yes");
		choice2 = new ChoiceLabel(Boolean.FALSE, "No");
		booleanChoices = new ChoiceLabel[2];
		booleanChoices[0]=choice1;
		booleanChoices[1]=choice2;
		agreementQuestion = new SingleChoiceQuestion(qWording,TypeMapper.getDatatypeURI(Boolean.class),booleanChoices);

		questionnaire = new Questionnaire ("First inquiry", 
					"This message will be displayed whenever a new treatment is detected, to ask the user" + 
					" about his/her predisposition to follow the treatment.", agreementQuestion); 

		 firstQuestionnaire = new MotivationalQuestionnaire(HeartFailure.MY_URI, Treatment.MY_URI, MotivationalStatusType.precontemplation, MotivationalMessageClassification.inquiry, MotivationalMessageSubclassification.treatment_agreement,  questionnaire);	
		//load the facts
		 
		WeightRequirement wr = new WeightRequirement(85, 60);
		wmt1 = new WeightMeasurementTreatment("Weight measurement 1", "Weight measurement to control weight", HeartFailure.MY_URI, wr);
		wmt2 = new WeightMeasurementTreatment("Weight measurement 2", "Weight measurement to control weight", HeartFailure.MY_URI, wr);
		
		// load global variables
		ksession.setGlobal("agreementQuestion",agreementQuestion);
		ksession.setGlobal("firstQuestionnaire",firstQuestionnaire);
		
		//insert the facts in drools working memory
		
		wmt1ChangingStatus = (FactHandle) ksession.insert(wmt1);
		ksession.insert(wmt2);
		
		//fire the rules
		ksession.fireAllRules();
	}
	
	@After
	public void tearDown()
	{
		TestIface.resetMessagesSent();
	}
	
/*	
public Treatment generateTreatment4Test() throws Exception{
		
		GregorianCalendar startDate = new GregorianCalendar();
		GregorianCalendar endDate = new GregorianCalendar();
		
		startDate.add(Calendar.DAY_OF_YEAR, -10); 
		endDate.add(Calendar.DAY_OF_YEAR, -10);
		
		XMLGregorianCalendar firstEventStartDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(startDate);
		XMLGregorianCalendar firstEventEndDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(endDate);
		
		firstEventStartDate.setTime(20, 0, 0);
		firstEventEndDate.setTime(20, 30, 0);
		
		String recurrence = "FREQ=WEEKLY;BYDAY=MO,WE,FR;UNTIL=20121231T235959"; // las sesiones ser�n los lunes, mi�rcoles y viernes hasta fin de a�o, en total, 4 meses de tratamiento.
		String description = "These treatment sessions consists of walking 30 minutes at a moderate step";
		
		TreatmentPlanning tp = new TreatmentPlanning(firstEventStartDate, firstEventEndDate, recurrence, description);
		Walking wt = new Walking("Walking", "walking three times a week for four months", tp, HeartFailure.MY_URI);
		
		return wt;
	}
	


	@Test
	// El primer tratamiento va a ser aceptado.
	public void testChangeToContemplationFromPrecontemplation(){
		
		Questionnaire4TreatmentStrategy newQuestionnaire = new Questionnaire4TreatmentStrategy(wmt1, questionnaire.getName(), questionnaire.getDescription(), questionnaire.getQuestions());
		Answer aWMT1 = new Answer(Boolean.TRUE, agreementQuestion);
		AnsweredQuestionnaire aq = new AnsweredQuestionnaire(newQuestionnaire, aWMT1, ap);
		
		ksession.insert(aq);
		ksession.fireAllRules();
		
		String contentExpected = "Good morning Andrea! Peter has agreeded to follow the treatment Weight measurement 1."+
		" You'll be notified whether the first session will be performed or not by Peter.";
		
		Assert.assertEquals(true, wmt1.getMotivationalStatus()== MotivationalStatusType.contemplation);
		Assert.assertEquals(true, TestIface.sentToCaregiverContainsPlainMessage(contentExpected));
		
		System.out.println("User agreed to follow treatment: " + wmt1.getName() + ", so motivational status is now contemplation");
	}
	

	@Test
	// El segundo tratamiento va a ser rechazado.
	public void testPrecontemplationPhaseFromPrecontemplation(){
		
		Questionnaire4TreatmentStrategy newQuestionnaire = new Questionnaire4TreatmentStrategy(wmt2, questionnaire.getName(), questionnaire.getDescription(), questionnaire.getQuestions());
		Answer aWMT2 = new Answer(Boolean.FALSE, agreementQuestion);
		AnsweredQuestionnaire aqW2 = new AnsweredQuestionnaire(newQuestionnaire, aWMT2, ap);
		
		String contentExpected = "Good morning Andrea. Peter has disagreeded to follow the treatment Weight measurement 2." +
		" The system will try to find out the reason why Peter refuses to perform the treatment and will try to convince him to change this attitude." + 
		" If any updates they will be sent to you.";
		
		ksession.insert(aqW2);
		ksession.fireAllRules();
		Assert.assertEquals(true, TestIface.sentToCaregiverContainsPlainMessage(contentExpected));
		Assert.assertEquals(true, wmt2.getMotivationalStatus()== MotivationalStatusType.precontemplation);
		
		System.out.println("User disagreed to follow treatment: " + wmt2.getName() + ", so motivational status remains in precontemplation");
	}
	
	
	@Test
	public void testChangeToActionPhaseFromContemplation() throws Exception{
		
		wmt1.setCompletenessUnit(2); // the completeness unit is set
		wmt1.setCompleteness(2); // the treatment completeness increases the completeness unit, which means
								// the user has begun the treatment, performing the first session.
		
		String contentExpected = "Good morning Andrea! Peter has begun his treatment: Weight measurement 1." + 
		" The platform will notify you with new updates, whenever they happen. Regards.";
		
		ksession.update(wmt1ChangingStatus, wmt1);
		ksession.fireAllRules();
		
		Assert.assertEquals(true, wmt1.getMotivationalStatus()== MotivationalStatusType.action);
		Assert.assertEquals(true, TestIface.sentToCaregiverContainsPlainMessage(contentExpected));
		
	}
	
	@Test
	public void testChangeToMaintenancePhaseFromAction(){
		
		wmt1.setMotivationalStatus(MotivationalStatusType.action);
		wmt1.setCompleteness(86);

		ksession.update(wmt1ChangingStatus, wmt1);
		ksession.fireAllRules();
		
		String contentExpected = "Good morning Andrea! Peter has performed the 85% of the treament Weight measurement 1.";

		Assert.assertEquals(true, wmt1.getMotivationalStatus()== MotivationalStatusType.maintenance);
		Assert.assertEquals(true, TestIface.sentToCaregiverContainsPlainMessage(contentExpected));
	}
	
	// Probar los metodos al contrario: en sentido descendente:
	
	*/
	@Test
	public void testChangeToPrecontemplationFromContemplation(){
		
		wmt1.setMotivationalStatus(MotivationalStatusType.contemplation);
		
		PerformedSessionsSupport pss = new PerformedSessionsSupport(wmt1);
		pss.setNumberOfConsecutiveNonPerformedSessionsCont(3);
		Treatment treatmentTest = pss.getAssociatedTreatment();
		//PerformedSessionsSupport pss4wmt1 = new PerformedSessionsSupport(wmt1, 3, 0, 0);
		
		ksession.update(wmt1ChangingStatus, wmt1);
		ksession.fireAllRules();
		
		boolean treatmentInMap = PerformedSessionsSupport.mapOfPSSupport.containsKey(wmt1);
		int setter = PerformedSessionsSupport.mapOfPSSupport.get(wmt1).getNumberOfConsecutiveNonPerformedSessionsCont();
		boolean abandonement = PerformedSessionsSupport.mapOfPSSupport.get(wmt1).getNumberOfConsecutiveNonPerformedSessionsCont()== PerformedSessionsSupport.numberOfNonPerformanceAllowed;
		String contentExpected = "Good morning Andrea. Peter hasn't performed, in tree attempts, the first session of his treament, Weight measurement 1." + 
		" The platform will try to find out the reason, but may be you prefer to contact him before it to set out the cause of the giving up by yourself. Regards.";
 
		Assert.assertEquals(true, TestIface.sentToCaregiverContainsPlainMessage(contentExpected));
		Assert.assertEquals(true, wmt1.getMotivationalStatus()== MotivationalStatusType.precontemplation);
		
	}
	
	
	@Test
	public void testChangeToContemplationFromAction(){
		
		wmt1.setMotivationalStatus(MotivationalStatusType.action);
		/*
		PerformedSessionsSupport pss = new PerformedSessionsSupport(wmt1);
		pss.setNumberOfConsecutiveNonPerformedSessionsAct(3);
		pss.setNumberOfConsecutiveNonPerformedSessionsCont(0);
		pss.setNumberOfConsecutiveNonPerformedSessionsMan(0);
		*/
		PerformedSessionsSupport pss4wmt1 = new PerformedSessionsSupport(wmt1, 0, 3, 0);
		ksession.update(wmt1ChangingStatus, wmt1);
		ksession.fireAllRules();
		
		String contentExpected = "Good morning Andrea. Peter hasn't performed 3 consecutive sessions of his treament, Weight measurement 1." + 
		" The platform will try to find out the reason, but may be you prefer to contact him before it to set out the cause of the giving up by yourself. Regards.";

		Assert.assertEquals(true, wmt1.getMotivationalStatus()== MotivationalStatusType.contemplation);
		Assert.assertEquals(true, TestIface.sentToCaregiverContainsPlainMessage(contentExpected));
	}
	
	
	@Test
	public void testChangeToActionFromMaintenance(){
		
		wmt1.setMotivationalStatus(MotivationalStatusType.maintenance);
		PerformedSessionsSupport pss4wmt1 = new PerformedSessionsSupport(wmt1, 0, 0, 5);
		
		/*
		PerformedSessionsSupport pss = new PerformedSessionsSupport(wmt1);
		pss.setNumberOfConsecutiveNonPerformedSessionsAct(0);
		pss.setNumberOfConsecutiveNonPerformedSessionsCont(0);
		pss.setNumberOfConsecutiveNonPerformedSessionsMan(5);
		*/
		
		ksession.update(wmt1ChangingStatus, wmt1);
		ksession.fireAllRules();
		
		String contentExpected = "Good morning Andrea. Peter hasn't performed 5 consecutive sessions of his treament, Weight measurement 1." + 
		" The platform will try to encourage him to perform the following ones, but may be you prefer to contact him to find out the reason of the giving up. Regards.";

		
		Assert.assertEquals(true, wmt1.getMotivationalStatus()== MotivationalStatusType.action);
		Assert.assertEquals(true, TestIface.sentToCaregiverContainsPlainMessage(contentExpected));
	}
	
	
	@Test
	public void testTreatmentActivation() throws Exception{
		
		GregorianCalendar startDate = new GregorianCalendar();
		GregorianCalendar endDate = new GregorianCalendar();
		
		startDate.add(Calendar.DAY_OF_YEAR, 0); 
		endDate.add(Calendar.DAY_OF_YEAR, 0);
		
		XMLGregorianCalendar firstEventStartDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(startDate);
		XMLGregorianCalendar firstEventEndDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(endDate);
		
		firstEventStartDate.setTime(20, 0, 0);
		firstEventEndDate.setTime(20, 30, 0);
		
		String recurrence = "FREQ=WEEKLY;BYDAY=MO,WE,FR;UNTIL=20121231T235959"; // las sesiones ser�n los lunes, mi�rcoles y viernes hasta fin de a�o, en total, 4 meses de tratamiento.
		String description = "These treatment sessions consists of walking 30 minutes at a moderate step";
		
		TreatmentPlanning tp = new TreatmentPlanning(firstEventStartDate, firstEventEndDate, recurrence, description);
		Walking wt = new Walking("Walking", "walking three times a week for four months", tp, HeartFailure.MY_URI);
		
		wt.setStatus(StatusType.planned);
		
		Assert.assertTrue(wt.getStatus() == StatusType.planned); 
		
		ksession.insert(wt);
		ksession.fireAllRules();
		
		Assert.assertTrue(wt.getStatus() == StatusType.actived);
		
	
	}
	
	
	@Test
	public void testTreatmentCancelation(){
		wmt1.setStatus(StatusType.cancelled);
		
		ksession.update(wmt1ChangingStatus, wmt1);
		ksession.fireAllRules();
	
		String contentExpected = "Peter, your caregiver, Andrea, has cancelled your treatment Weight measurement 1."+
		" You can get in touch with her to know the reasons or for further information. Regards.";
		
		Assert.assertEquals(true, TestIface.sentToAPContainsPlainMessage(contentExpected));
		System.out.println("Tratamiento dentro de los detected treatments: " + DetectedTreatments.containsTreatment(wmt1));
		Assert.assertFalse(DetectedTreatments.containsTreatment(wmt1));
	}
	   
		/*
	
	
	
	@Test
	public void testFinishedTreatment() throws Exception{
		Treatment t = generateTreatment4Test();
		t.setCompleteness(100);
		Assert.assertEquals(true, t.getStatus()==StatusType.finished);
	}
	
	
	
	@Test
	public void testTreatmentTimeExtension(){
		
	}
	
	*/
}
