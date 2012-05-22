package org.universAAL.AALApplication.health.motivation.treatment;


import java.util.ArrayList;

import junit.framework.Assert;
import junit.framework.TestCase;

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
import org.universAAL.AALApplication.health.motivation.testSupportClasses.TreatmentDetection;
import org.universaal.ontology.health.owl.Diet;
import org.universaal.ontology.health.owl.Treatment;

/**
 * This test class checks if treatments are well detected, based on 
 * the property name. If the treatment has an empty name then it should be
 * not detected. If not, the rule "Treatment detection" must be fired.
 * @author mdelafuente
 *
 */
public class TestTreatmentDetection extends TestCase{

	private KnowledgeBase kbase;
	private static StatefulKnowledgeSession ksession;
	private static Diet treatment1;
	private static Diet treatment2;
	private static Diet treatment3;


	/**
	 * Method for setting up the stateful session
	 * @throws Exception 
	 */
	@Before
	public void setUp() throws Exception{
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
	}

	@After
	public void tearDown()
	{
		ksession.dispose();
	}

	/**
	 * This method checks that the valid treatments (t1 and t2) have been well detected.
	 * Invalid treatments (t3) should have not been detected 
	 */
	@Test
	public void testTreatmentDetection(){

		//load the facts
		treatment1 = new Diet ("Dieta baja en grasa", "descripci�n"); //valid treatment
		treatment2 = new Diet ("Dieta baja en sal", "descripci�n");//valid treatment
		treatment3 = new Diet ("", "descripci�n");// invalid treatment		

		//insert the facts in drools working memory
		ksession.insert(treatment1);
		ksession.insert(treatment2);
		ksession.insert(treatment3);

		//fire the rules
		ksession.fireAllRules();

		// we check the results
		ArrayList <Treatment> treatmentsDetected  = TreatmentDetection.getDetectedTreatments();

		Assert.assertEquals(true,treatmentsDetected.contains(treatment1));
		Assert.assertEquals(true,treatmentsDetected.contains(treatment2));
		Assert.assertEquals(false,treatmentsDetected.contains(treatment3));

		System.out.println("Tratamientos detectados correctamente");
	}
}


