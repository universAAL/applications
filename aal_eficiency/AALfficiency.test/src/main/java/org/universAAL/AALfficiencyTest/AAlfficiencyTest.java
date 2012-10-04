package org.universAAL.AALfficiencyTest;

import java.util.List;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.ontology.aalfficiency.scores.*;

public class AAlfficiencyTest {
	
	private static final String NAMESPACE = "http://tsbtecnologias.es/AALfficiencyTest.owl#";
	
	private static final String OUTPUT_ACTIVITY_SCORE = NAMESPACE+"outputActivityScore";
	private static final String OUTPUT_ELECTRICITY_SCORE = NAMESPACE+"outputElectricityScore";
	private static final String OUTPUT_CHALLENGE = NAMESPACE+"outputChallenge";
	private static final String OUTPUT_ADVICES = NAMESPACE+"outputAdvices";
	private static final String OUTPUT_ADVICE = "http://www.tsbtecnologias.es/AALfficiency.owl#AdviceInfo";
	
	
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
				ServiceRequest getAdvices = new ServiceRequest(new AalfficiencyScores(), null);

				// In this case, we do not intend to change anything but only retrieve
				// some info
				getAdvices.addRequiredOutput(OUTPUT_ADVICES,new String[] {AalfficiencyScores.PROP_AALFFICIENCY_ADVICES});

				return getAdvices;
		}
		 
		 
		 public static void testCall1(ModuleContext c) {
				System.out.println("Llamando getScore...");
				ServiceCaller caller = new DefaultServiceCaller(c);
				//ServiceRequest addReq = new ServiceRequest(new Aalfficiency(null),null);
				// Make a call for the lamps and get the request
				ServiceResponse sr = caller.call(getActivityScoreRequest());

				if (sr.getCallStatus() == CallStatus.succeeded) {
				    try {
				    	
				    List scoreList =  sr.getOutput(OUTPUT_ACTIVITY_SCORE, true);
					
					if (scoreList == null || scoreList.size()==0) {
			    		LogUtils.logInfo(Activator.mc, AAlfficiencyTest.class,
			    				"getScore",
			    	new Object[] { "there is no score" }, null);
					}
					else{
						ActivityScore[] scores = (ActivityScore[]) scoreList
								.toArray(new ActivityScore[scoreList.size()]);
						for (int i=0;i<scores.length;i++) {
							System.out.print("ActivityScore "+scores[i]+"\n");
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
			
			 public static ServiceRequest getActivityScoreRequest() {
					// Again we want to create a ServiceRequest regarding LightSources
					ServiceRequest getActivityScoreAdvices = new ServiceRequest(new AalfficiencyScores(), null);

					// In this case, we do not intend to change anything but only retrieve
					// some info
					getActivityScoreAdvices.addRequiredOutput(OUTPUT_CHALLENGE,new String[] {Challenge.MY_URI});

					return getActivityScoreAdvices;
			}
		 
		
			 public static void testCall3(ModuleContext c) {
					System.out.println("Llamando getScore...");
					ServiceCaller caller = new DefaultServiceCaller(c);
					//ServiceRequest addReq = new ServiceRequest(new Aalfficiency(null),null);
					// Make a call for the lamps and get the request
					ServiceResponse sr = caller.call(getElectricityScoreRequest());

					if (sr.getCallStatus() == CallStatus.succeeded) {
					    try {
					    	
					    List scoreList =  sr.getOutput(OUTPUT_ELECTRICITY_SCORE, true);
						
						if (scoreList == null || scoreList.size()==0) {
				    		LogUtils.logInfo(Activator.mc, AAlfficiencyTest.class,
				    				"getScore",
				    	new Object[] { "there is no score" }, null);
						}
						else{
							ElectricityScore[] scores = (ElectricityScore[]) scoreList
									.toArray(new ElectricityScore[scoreList.size()]);
							for (int i=0;i<scores.length;i++) {
								System.out.print("ElectricityScore "+scores[i]+"\n");
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
				
				 public static ServiceRequest getElectricityScoreRequest() {
						// Again we want to create a ServiceRequest regarding LightSources
						ServiceRequest getScoreAdvices = new ServiceRequest(new AalfficiencyScores(), null);

						// In this case, we do not intend to change anything but only retrieve
						// some info
						getScoreAdvices.addRequiredOutput(OUTPUT_ELECTRICITY_SCORE,new String[] {AalfficiencyScores.PROP_ELECTRICITY_SCORE});

						return getScoreAdvices;
				}
			 
				 	public static void testCall4(ModuleContext c) {
						System.out.println("Llamando getScore...");
						ServiceCaller caller = new DefaultServiceCaller(c);
						//ServiceRequest addReq = new ServiceRequest(new Aalfficiency(null),null);
						// Make a call for the lamps and get the request
						ServiceResponse sr = caller.call(getAdviceInfoRequest());

						if (sr.getCallStatus() == CallStatus.succeeded) {
						    try {
						    	
						    List scoreList =  sr.getOutput(OUTPUT_ADVICE, true);
							
							if (scoreList == null || scoreList.size()==0) {
					    		LogUtils.logInfo(Activator.mc, AAlfficiencyTest.class,
					    				"getAdvice",
					    	new Object[] { "there is no score" }, null);
							}
							else{
								Advice[] scores = (Advice[]) scoreList
										.toArray(new Advice[scoreList.size()]);
								for (int i=0;i<scores.length;i++) {
									System.out.print("Advice "+scores[i]+"\n");
								}
							}
						    } catch (Exception e) {
							LogUtils.logError(Activator.mc, AAlfficiencyTest.class,
								"getChallenge", new Object[] { "got exception",
									e.getMessage() }, e);
						    }
						} else {
						    LogUtils.logWarn(Activator.mc, AAlfficiencyTest.class,
							    "getAdvice",
							    new Object[] { "callstatus is not succeeded" }, null);
						}

					}
					
					 public static ServiceRequest getAdviceInfoRequest() {
							// Again we want to create a ServiceRequest regarding LightSources
							ServiceRequest getAdviceINfo = new ServiceRequest(new AalfficiencyScores(), null);

							getAdviceINfo.addValueFilter(new String[]{AalfficiencyAdvices.PROP_ADVICE}, 
									new Advice("http://www.tsbtecnologias.es/AALfficiency.owl#advices0"));
							
							// In this case, we do not intend to change anything but only retrieve
							// some info
							getAdviceINfo.addRequiredOutput(OUTPUT_ADVICE,new String[]{AalfficiencyAdvices.PROP_ADVICE});

							return getAdviceINfo;
					} 
				 
			 
		 
}
