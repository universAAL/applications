/*
	Copyright 2011-2012 TSB, http://www.tsbtecnologias.es
	TSB - Tecnologías para la Salud y el Bienestar
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.universAAL.AALfficiency;

import org.universAAL.AALfficiency.model.ChallengeModel;
import org.universAAL.AALfficiency.utils.Setup;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.ontology.aalfficiency.scores.*;

public class ScoresConsumer extends ContextSubscriber {

	 private static ContextEventPattern[] getContextSubscriptionParams() {
			ContextEventPattern cep = new ContextEventPattern();
			cep.addRestriction(MergedRestriction.getAllValuesRestriction(
				ContextEvent.PROP_RDF_SUBJECT, AalfficiencyScores.MY_URI));
			return new ContextEventPattern[] { cep };
		    }
	
	 protected ScoresConsumer(ModuleContext context,
				ContextEventPattern[] initialSubscriptions) {
			super(context, initialSubscriptions);
			// TODO Auto-generated constructor stub
		}
	 
	 protected ScoresConsumer(ModuleContext context) {
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
		
		System.out.println("ELECTRICITY SCORES \n  ----------------\n" + " sub="
				+ event.getSubjectURI() + "\n pred=" + event.getRDFPredicate()
				+ "\n obj=" + event.getRDFObject() + "\n tst="
				+ event.getTimestamp());
		if (event.getRDFObject() instanceof ElectricityScore){
			ElectricityScore e = (ElectricityScore) event.getRDFObject();
			s.setElectricitySaving(String.valueOf(e.getSaving()));
			s.setElectricityChallengeDescription(e.getChallenge().getDescription());
			s.setTodayElectricScore(String.valueOf(e.getTodayElectricityScore()));
			s.setElectricityChallengeGoal(e.getChallenge().getGoal());	
			
			ChallengeModel c =  s.getElectricityChallenge();
			if (c!=null){
				if (c.getDescription().compareTo(e.getChallenge().getDescription())==0){
					int total = s.getTotalElectricScore()+e.getTodayElectricityScore();
					s.setTotalElectricScore(String.valueOf(total));
				}
				else {
					int total = e.getTodayElectricityScore();
					s.setTotalElectricScore(String.valueOf(total));
				}
				
				
			}
		}
		
		else if (event.getRDFObject() instanceof ActivityScore){
			ActivityScore a = (ActivityScore) event.getRDFObject();
			s.setActivityChallengeDescription(a.getChallenge().getDescription());
			s.setActivityChallengeGoal(a.getChallenge().getGoal());
			s.setTodayActivityScore(String.valueOf(a.getTodayActivityScore()));
			s.setActivityKcal(String.valueOf(a.getKcal()));
			s.setActivitySteps(String.valueOf(a.getSteps()));
			ChallengeModel c =  s.getElectricityChallenge();
			if (c!=null){
				if (c.getDescription().compareTo(a.getChallenge().getDescription())==0){
					int total = s.getTotalActivityScore()+a.getTodayActivityScore();
					s.setTotalActivityScore(String.valueOf(total));
				}
				else {
					int total = a.getTodayActivityScore();
					s.setTotalActivityScore(String.valueOf(total));
				}
			}
		}
		
	}

}
