package org.universAAL.AALApplication.health.motivation.useCases;

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
import org.universAAL.AALApplication.health.motivation.SendMotivationMessageIface;
import org.universAAL.AALApplication.health.motivation.motivatonalMessageManagement.MessageManager;
import org.universAAL.AALApplication.health.motivation.schedulingTools.TreatmentPlanningToiCal;
import org.universAAL.AALApplication.health.motivation.treatment.TestIface;
import org.universaal.ontology.ICD10CirculatorySystemDiseases.owl.HeartFailure;
import org.universaal.ontology.health.owl.Treatment;
import org.universaal.ontology.health.owl.TreatmentPlanning;
import org.universaal.ontology.health.owl.Walking;
import org.universaal.ontology.health.owl.WeightMeasurementTreatment;
import org.universaal.ontology.health.owl.WeightRequirement;

public class TestWalkingTreatment extends TestIface{
	
	private KnowledgeBase kbase;
	private static StatefulKnowledgeSession ksession;
	private SendMotivationMessageIface iTestTreatment = this;
	
	private Walking walkTreatment;
	private FactHandle wtAlongTime; // vamos a modificar el tiempo en los distintos métodos de prueba.
	
	private Walking createWalkingTreatment(){
		
		try{
			GregorianCalendar startDate = new GregorianCalendar();
			GregorianCalendar endDate = new GregorianCalendar();
			
			XMLGregorianCalendar firstEventStartDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(startDate);
			XMLGregorianCalendar firstEventEndDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(endDate);
			
			firstEventStartDate.setYear(2012);
			firstEventStartDate.setMonth(9);
			firstEventStartDate.setDay(1);
			firstEventStartDate.setTime(20, 0, 0);
			
			firstEventEndDate.setYear(2012);
			firstEventEndDate.setMonth(9);
			firstEventEndDate.setDay(1);
			firstEventEndDate.setTime(20, 30, 0);
			
			String recurrence = "FREQ=WEEKLY;BYDAY=MO,WE,FR;UNTIL=20121231T235959"; // las sesiones serán los lunes, miércoles y viernes hasta fin de año, en total, 4 meses de tratamiento.
			String description = "These treatment sessions consists of walking 30 minutes at a moderate step";
			
			TreatmentPlanning tp = new TreatmentPlanning(firstEventStartDate, firstEventEndDate, recurrence, description);
			Walking wt = new Walking("Walking", "walking three times a week for four months", tp, HeartFailure.MY_URI);
			
			return wt;
		}catch(Exception e){
			System.out.println(e);
			return null;
		}
	}
	
// Ponemos a punto la working memory
	
	@Before
	public void setUp() throws Exception{
		
		registerClassesNeeded();
				
		//load up the knowledge base
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("rules/TreatmentRules.drl"), ResourceType.DRL);
		//kbuilder.add(ResourceFactory.newClassPathResource("TreatmentRules.drl"), ResourceType.DRL);
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
		
		//load the facts
		walkTreatment = createWalkingTreatment();
		
		// load global variables
		//ksession.setGlobal("",);
		
		//insert the facts in drools working memory
		wtAlongTime = (FactHandle) ksession.insert(walkTreatment);
		
		//fire the rules
		//ksession.fireAllRules();
		
	}

	
	
}
