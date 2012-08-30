package org.universAAL.AALfficiency;

import org.universAAL.AALfficiency.utils.Setup;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextBus;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.ontology.aalfficiency.scores.*;

public class ElectricityDataConsumer extends ContextSubscriber {
	
	
	private static final String NAMESPACE ="http://www.tsbtecnologias.es/ElectricityDataConsumer";
	
	private static final String OUTPUT_DATA = NAMESPACE+"#ElectricityData";
	
	 private static ContextEventPattern[] getContextSubscriptionParams() {
			// I am interested in all events with a ElectricityData as subject
			ContextEventPattern cep = new ContextEventPattern();
			cep.addRestriction(MergedRestriction.getAllValuesRestriction(
				ContextEvent.PROP_RDF_SUBJECT, AalfficiencyScores.PROP_HAS_ELECTRICITY_SCORE));
			cep.addRestriction(MergedRestriction.getAllValuesRestriction(
				ContextEvent.PROP_RDF_SUBJECT, Challenge.PROP_HAS_DESCRIPTION));
			cep.addRestriction(MergedRestriction.getAllValuesRestriction(
					ContextEvent.PROP_RDF_SUBJECT, Challenge.PROP_HAS_GOAL));
			return new ContextEventPattern[] { cep };
		    }
	
	 protected ElectricityDataConsumer(ModuleContext context,
				ContextEventPattern[] initialSubscriptions) {
			super(context, initialSubscriptions);
			// TODO Auto-generated constructor stub
		}
	 
	 protected ElectricityDataConsumer(ModuleContext context) {
			super(context, getContextSubscriptionParams());
			// TODO Auto-generated constructor stub
		    }
	
	@Override
	public void communicationChannelBroken() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleContextEvent(ContextEvent event) {
		Setup s = new Setup();
		// TODO Auto-generated method stub
		//ElectricityData e = (ElectricityData)event.getRDFSubject();
		if (event.getRDFPredicate().compareTo("http://ontology.universAAL.org/Aalfficiency.owl#ElectricityTodayScore")==0){
			s.setTodayElectricScore(String.valueOf(event.getRDFObject()));
			System.out.print("****************************************************");}
		else if (event.getRDFPredicate().compareTo("http://ontology.universAAL.org/Aalfficiency.owl#ElectricitySaving")==0){
				s.setElectricitySaving(String.valueOf(event.getRDFObject()));
				System.out.print("****************************************************");}
		else if (event.getRDFPredicate().compareTo("http://ontology.universAAL.org/Aalfficiency.owl#ChallengeDescription")==0
				&& event.getSubjectURI().compareTo("http://tsbtecnologias.es/ReadEnergy#ElectricityDataChallenge")==0){
			s.setElectricityChallengeDescription((String)event.getRDFObject());
			System.out.print("****************************************************");}
		else if (event.getRDFPredicate().compareTo("http://ontology.universAAL.org/Aalfficiency.owl#ChallengeGoal")==0
				&& event.getSubjectURI().compareTo("http://tsbtecnologias.es/ReadEnergy#ElectricityDataChallenge")==0){
			s.setElectricityChallengeGoal((String)event.getRDFObject());
			System.out.print("****************************************************");}
		
		System.out.println("----------------\n" + " sub="
			+ event.getSubjectURI() + "\n pred=" + event.getRDFPredicate()
			+ "\n obj=" + event.getRDFObject() + "\n tst="
			+ event.getTimestamp());
		
	}

}
