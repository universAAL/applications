package org.universAAL.AALfficiencyTest;

import java.util.List;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.ontology.aalfficiency.Aalfficiency;
import org.universAAL.ontology.aalfficiency.AalfficiencyAdvices;
import org.universAAL.ontology.aalfficiency.AalfficiencyChallenges;
import org.universAAL.ontology.aalfficiency.AalfficiencyScore;

public class AAlfficiencyTest {
	
	private static final String NAMESPACE = "http://tsbtecnologias.es/AALfficiencyTest.owl#";
	
	private static final String OUTPUT_SCORE = NAMESPACE+"outputScore";
	private static final String OUTPUT_CHALLENGES = NAMESPACE+"outputChallenges";
	private static final String OUTPUT_ADVICES = NAMESPACE+"outputAdvices";
	
	
	 public static void testCall2(ModuleContext c) {
			System.out.println("Llamando getAdvices...");
			ServiceCaller caller = new DefaultServiceCaller(c);
			//ServiceRequest addReq = new ServiceRequest(new Aalfficiency(null),null);
			// Make a call for the lamps and get the request
			ServiceResponse sr = caller.call(getAdvicesRequest());

			if (sr.getCallStatus() == CallStatus.succeeded) {
			    try {
			    	
			    List scoreList =  sr. getOutput(OUTPUT_ADVICES, true);
				
				if (scoreList == null || scoreList.size()==0) {
		    		LogUtils.logInfo(Activator.mc, AAlfficiencyTest.class,
		    				"getAdvices",
		    	new Object[] { "there are no score" }, null);
				}
				else{
					AalfficiencyAdvices[] scores = (AalfficiencyAdvices[]) scoreList
							.toArray(new AalfficiencyAdvices[scoreList.size()]);
					for (int i=0;i<scores.length;i++) {
						System.out.print("Score "+scores[i]);
					}
				}
			    } catch (Exception e) {
				LogUtils.logError(Activator.mc, AAlfficiencyTest.class,
					"getAdvices", new Object[] { "got exception",
						e.getMessage() }, e);
			    }
			} else {
			    LogUtils.logWarn(Activator.mc, AAlfficiencyTest.class,
				    "getAdvices",
				    new Object[] { "callstatus is not succeeded" }, null);
			}

		}
		
		 public static ServiceRequest getAdvicesRequest() {
				// Again we want to create a ServiceRequest regarding LightSources
				ServiceRequest getAdvices = new ServiceRequest(new Aalfficiency(), null);

				// In this case, we do not intend to change anything but only retrieve
				// some info
				getAdvices.addRequiredOutput(OUTPUT_ADVICES,new String[] {Aalfficiency.PROP_HAS_ADVICES});

				return getAdvices;
		}
		
		 public static void testCall3(ModuleContext c) {
				System.out.println("Llamando...");
				ServiceCaller caller = new DefaultServiceCaller(c);
				ServiceRequest addReq = new ServiceRequest(new Aalfficiency(null),
						null);
				// Make a call for the lamps and get the request
				ServiceResponse sr = caller.call(getChallengesRequest());

				if (sr.getCallStatus() == CallStatus.succeeded) {
				    try {
				    	
				    List scoreList =  sr. getOutput(OUTPUT_CHALLENGES, true);
					
					if (scoreList == null || scoreList.size()==0) {
			    		LogUtils.logInfo(Activator.mc, AAlfficiencyTest.class,
			    				"getChallenges",
			    	new Object[] { "there are no score" }, null);
					}
					else{
						AalfficiencyChallenges[] scores = (AalfficiencyChallenges[]) scoreList
								.toArray(new AalfficiencyChallenges[scoreList.size()]);
						for (int i=0;i<scores.length;i++) {
							System.out.print("Score "+scores[i]);
						}
					}
				    } catch (Exception e) {
					LogUtils.logError(Activator.mc, AAlfficiencyTest.class,
						"getAdvices", new Object[] { "got exception",
							e.getMessage() }, e);
				    }
				} else {
				    LogUtils.logWarn(Activator.mc, AAlfficiencyTest.class,
					    "getAdvices",
					    new Object[] { "callstatus is not succeeded" }, null);
				}

			}
			
			 public static ServiceRequest getChallengesRequest() {
					// Again we want to create a ServiceRequest regarding LightSources
					ServiceRequest getScore = new ServiceRequest(new Aalfficiency(), null);

					// In this case, we do not intend to change anything but only retrieve
					// some info
					getScore.addRequiredOutput(
					// this is OUR unique ID with which we can later retrieve the returned
						// value
							OUTPUT_CHALLENGES,
						// Specify the meaning of the required output
						// by pointing to the property in whose value you are interested
						// Because we haven't specified any filter before, this should
						// result
						// in returning all values associated with the specified
						// property
						new String[] {Aalfficiency.PROP_HAS_CHALLENGES});

					return getScore;
			}	 
	
}
