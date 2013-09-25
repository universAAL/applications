package org.universAAL.AALApplication.health.motivation.scheduleRules;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

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
import org.junit.Before;
import org.junit.Test;
import org.universAAL.AALApplication.health.motivation.MotivationServiceRequirementsIface;
import org.universAAL.AALApplication.health.motivation.SendMotivationMessageIface;
import org.universAAL.AALApplication.health.motivation.motivatonalMessageManagement.MessageManager;
import org.universAAL.AALApplication.health.motivation.motivatonalMessageManagement.MessageVariables;
import org.universAAL.AALApplication.health.motivation.testSupportClasses.TreatmentTypeClassification;
import org.universAAL.AALApplication.health.motivation.treatment.TestIface;
import org.universAAL.ontology.ICD10CirculatorySystemDiseases.owl.HeartFailure;
import org.universAAL.ontology.health.owl.Treatment;
import org.universAAL.ontology.health.owl.TreatmentPlanning;
import org.universAAL.ontology.health.owl.Walking;
import org.universAAL.ontology.health.owl.WeightMeasurementTreatment;
import org.universAAL.ontology.health.owl.WeightRequirement;
import org.universAAL.ontology.profile.User;

public class TestTreatmentTypeClassification extends TestIface{
	
	User ap;
	
	private KnowledgeBase kbase;
	private static StatefulKnowledgeSession ksession;
	
	private SendMotivationMessageIface iTestTreatment = this;
	private MotivationServiceRequirementsIface iTestStatusForMMVariables = this;
	
	Treatment walking; 
	
@Before
	
	public void setUp() throws Exception{
		registerClassesNeeded();
		ap = this.getAssistedPerson(); 		
		//load up the knowledge base
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("rules/ScheduleRules.drl"), ResourceType.DRL);
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
	
		//load the facts
		 
			walking = generateTreatment4Test();
		//insert the facts in drools working memory
			ksession.insert(walking);
			
		//fire the rules
			ksession.fireAllRules();
	}

	public Treatment generateTreatment4Test() throws Exception{
		
		GregorianCalendar startDate = new GregorianCalendar();
		GregorianCalendar endDate = new GregorianCalendar();
		
		startDate.add(Calendar.DAY_OF_YEAR, -10); //comenz� hace 10 d�as
		endDate.add(Calendar.DAY_OF_YEAR, -10); // termin� (la primera sesi�n) hace 10 d�as
		
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
	
	/*
	@Test
	public void testTTCCreated(){
		Assert.assertEquals(true, TreatmentTypeClassification.hasTTC(walking));
	}
	*/
	@Test
	public void testNoMeasurementType(){
		TreatmentTypeClassification ttc = new TreatmentTypeClassification(walking);
		Assert.assertEquals(true, TreatmentTypeClassification.hasTTC(walking));
	}
	
	
}
