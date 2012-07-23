package org.universaal.ontology.owl;

import org.universAAL.middleware.owl.ManagedIndividual;

public class MotivationalMessageSubclassification extends ManagedIndividual {
	
	public static final String MY_URI = MessageOntology.NAMESPACE
    + "MotivationalMessageSubclassification";

  // Subclassification for educational messages	
  public static final int ACADEMIC = 0;
  public static final int ADVICE = 1;
  public static final int BENEFIT = 2;
  public static final int CURIOSITY = 3;
  public static final int EXPLANATION = 4;
  
  //Subclasification for reward messages
  public static final int SESSION_RECOGNITION = 5;
  public static final int TREATMENT_PERFORMANCE_RECOGNITION = 6;
  public static final int AGREEMENT = 7;
  
  //Subclasification for test messages
  public static final int ACADEMIC_KNOWLEDGE = 8;
  public static final int PERSONAL_KNOWLEDGE = 9;
  public static final int SOLUTION = 10;

  // Subclasification for reminder messages
  public static final int SESSION_PERFORMANCE_REMINDER = 11;
  public static final int SESSION_PERFORMANCE_ALERT = 12;
  public static final int TREATMENT_NEEDS = 13;
  public static final int AGREEMENT_PENDANT = 14;
  
  //Subclassification for notification messages
  public static final int UNUSUAL_MEASUREMENT = 15;
  public static final int ABANDONMENT = 16;
  public static final int TREATMENT_MANAGEMENT = 17;
  public static final int TREATMENT_PERFORMANCE_AGREEMENT = 18;
 
  
  //Subclasification for personalized_feedback messgaes
  public static final int SESSION = 19;
  public static final int EVOLUTION = 20;
  
  //Subclasification for inquiry messages
  public static final int SESSION_INTEREST = 21;
  public static final int TREATMENT_INTEREST = 22;
  public static final int BEGINER_INTEREST = 23;
  public static final int FAMILIAR_INTEREST = 24;
  public static final int NON_PERFORMANCE_CAUSE = 25;
  public static final int SESSION_AGREEMENT = 26;
  public static final int TREATMENT_AGREEMENT = 27;
  public static final int AGREEMENT_PROPOSAL = 28;
  public static final int SESSION_PERFORMANCE = 29;
 
  
  //Subclasification for emotion_messages
  public static final int VS_FEAR = 30;
  public static final int VS_DESPAIR = 31;
  public static final int VS_FRUSTRATION = 32;
  public static final int VS_LACK_OF_HABILITY = 33;
  
  
  public static final int TREATMENT_PERFORMANCE_DISAGREEMENT = 34; //notification messages
  public static final int TREATMENT_STATUS_CANCELLED = 35;
  public static final int TREATMENT_STATUS_FINISHED = 36;
  public static final int TREATMENT_STATUS_PROLONGED = 37;
  
  private static final String[] names = {
    "academic","advice","benefit","curiosity", "explanation", 
    "session_recognition", "treatment_performance_recognition", "agreement",
    "academic_knowledge", "personal_knowledge", "solution", 
    "session_performance_reminder", "session_performance_alert", "treatment_needs", "agreement_pendant",
    "unusual_measurement", "abandonment", "treatment_management", "treatment_performance_agreement", 
    "session", "evolution",
    "session_interest", "treatment_interest", "beginer_interest", "familiar_interest", "non_performance_cause", "session_agreement", "treatment_agreement", "agreement_proposal", "session_performance",
    "vs_fear", "vs_despair", "vs_frustration", "vs_lack_of_hability", 
    "treatment_performance_disagreement", "treatment_status_cancelled", "treatment_status_finished", "treatment_status_cancelled"};

  public static final MotivationalMessageSubclassification academic = new MotivationalMessageSubclassification(ACADEMIC);
  public static final MotivationalMessageSubclassification advice = new MotivationalMessageSubclassification(ADVICE);
  public static final MotivationalMessageSubclassification benefit = new MotivationalMessageSubclassification(BENEFIT);
  public static final MotivationalMessageSubclassification curiosity = new MotivationalMessageSubclassification(CURIOSITY);
  public static final MotivationalMessageSubclassification explanation = new MotivationalMessageSubclassification(EXPLANATION);
  
  public static final MotivationalMessageSubclassification session_recognition = new MotivationalMessageSubclassification(SESSION_RECOGNITION);
  public static final MotivationalMessageSubclassification treatment_performance_recognition = new MotivationalMessageSubclassification(TREATMENT_PERFORMANCE_RECOGNITION);
  public static final MotivationalMessageSubclassification agreement = new MotivationalMessageSubclassification(AGREEMENT);
  
  
  public static final MotivationalMessageSubclassification academic_knowledge = new MotivationalMessageSubclassification(ACADEMIC_KNOWLEDGE);
  public static final MotivationalMessageSubclassification personal_knowledge = new MotivationalMessageSubclassification(PERSONAL_KNOWLEDGE);
  public static final MotivationalMessageSubclassification solution = new MotivationalMessageSubclassification(SOLUTION);
  
  public static final MotivationalMessageSubclassification session_performance_reminder = new MotivationalMessageSubclassification(SESSION_PERFORMANCE_REMINDER);
  public static final MotivationalMessageSubclassification session_performance_alert = new MotivationalMessageSubclassification(SESSION_PERFORMANCE_ALERT);
  public static final MotivationalMessageSubclassification treatment_needs = new MotivationalMessageSubclassification(TREATMENT_NEEDS);
  public static final MotivationalMessageSubclassification agreement_pendant = new MotivationalMessageSubclassification(AGREEMENT_PENDANT);
  
  public static final MotivationalMessageSubclassification unusual_measurement = new MotivationalMessageSubclassification(UNUSUAL_MEASUREMENT);
  public static final MotivationalMessageSubclassification abandonment = new MotivationalMessageSubclassification(ABANDONMENT);
  public static final MotivationalMessageSubclassification treatment_management = new MotivationalMessageSubclassification(TREATMENT_MANAGEMENT);
  public static final MotivationalMessageSubclassification treatment_performance_agreement = new MotivationalMessageSubclassification(TREATMENT_PERFORMANCE_AGREEMENT);
  public static final MotivationalMessageSubclassification treatment_performance_disagreement = new MotivationalMessageSubclassification(TREATMENT_PERFORMANCE_DISAGREEMENT);
  public static final MotivationalMessageSubclassification treatment_status_cancelled = new MotivationalMessageSubclassification( TREATMENT_STATUS_CANCELLED);
  public static final MotivationalMessageSubclassification treatment_status_finished = new MotivationalMessageSubclassification( TREATMENT_STATUS_FINISHED);
  public static final MotivationalMessageSubclassification treatment_status_prolonged = new MotivationalMessageSubclassification( TREATMENT_STATUS_PROLONGED);
  
  
  public static final MotivationalMessageSubclassification session = new MotivationalMessageSubclassification(SESSION);
  public static final MotivationalMessageSubclassification evolution = new MotivationalMessageSubclassification(EVOLUTION);
  
  public static final MotivationalMessageSubclassification session_interest = new MotivationalMessageSubclassification(SESSION_INTEREST);
  public static final MotivationalMessageSubclassification treatment_interest = new MotivationalMessageSubclassification(TREATMENT_INTEREST);
  public static final MotivationalMessageSubclassification beginer_interest = new MotivationalMessageSubclassification(BEGINER_INTEREST);
  public static final MotivationalMessageSubclassification familiar_interest = new MotivationalMessageSubclassification(FAMILIAR_INTEREST);
  public static final MotivationalMessageSubclassification non_performance_cause = new MotivationalMessageSubclassification(NON_PERFORMANCE_CAUSE);
  public static final MotivationalMessageSubclassification session_agreement = new MotivationalMessageSubclassification(SESSION_AGREEMENT);
  public static final MotivationalMessageSubclassification treatment_agreement = new MotivationalMessageSubclassification(TREATMENT_AGREEMENT);
  public static final MotivationalMessageSubclassification agreement_proposal = new MotivationalMessageSubclassification(AGREEMENT_PROPOSAL);
  public static final MotivationalMessageSubclassification session_performance = new MotivationalMessageSubclassification(SESSION_PERFORMANCE);
  
  public static final MotivationalMessageSubclassification vs_fear = new MotivationalMessageSubclassification(VS_FEAR);
  public static final MotivationalMessageSubclassification vs_despair = new MotivationalMessageSubclassification(VS_DESPAIR);
  public static final MotivationalMessageSubclassification vs_frustration = new MotivationalMessageSubclassification(VS_FRUSTRATION);
  public static final MotivationalMessageSubclassification vs_lack_of_hability = new MotivationalMessageSubclassification(VS_LACK_OF_HABILITY);
  

  private int order;

  private MotivationalMessageSubclassification(int order) {
    super(MessageOntology.NAMESPACE + names[order]);
    this.order = order;
  }

  public int getPropSerializationType(String propURI) {
    return PROP_SERIALIZATION_OPTIONAL;
  }

  public boolean isWellFormed() {
    return true;
  }

  public String name() {
    return names[order];
  }

  public int ord() {
    return order;
  }

  public String getClassURI() {
    return MY_URI;
  }
  public static MotivationalMessageSubclassification getMotivationalMessageSubclassificationByOrder(int order) {
    
	  switch (order) {
    
      case ACADEMIC:
        return academic;
      case ADVICE:
        return advice;
      case BENEFIT:
        return benefit;
      case CURIOSITY:
        return curiosity;
      case EXPLANATION:
          return explanation;
      
      case SESSION_RECOGNITION:
          return session_recognition;
      case TREATMENT_PERFORMANCE_RECOGNITION:
          return treatment_performance_recognition;
      case AGREEMENT:
          return agreement;
          
      case ACADEMIC_KNOWLEDGE:
    	  return academic_knowledge;
     case PERSONAL_KNOWLEDGE:
          return personal_knowledge;
      case SOLUTION:
          return solution;
          
          
      case SESSION_PERFORMANCE_REMINDER:
          return session_performance_reminder;
      case SESSION_PERFORMANCE_ALERT:
          return session_performance_alert;
      case TREATMENT_NEEDS:
          return treatment_needs;
      case AGREEMENT_PENDANT:
          return agreement_pendant;
        
      case UNUSUAL_MEASUREMENT:
          return unusual_measurement;
      case ABANDONMENT:
          return abandonment;
      case TREATMENT_MANAGEMENT:
          return treatment_management;
      case TREATMENT_PERFORMANCE_AGREEMENT:
          return treatment_performance_agreement;
      case TREATMENT_PERFORMANCE_DISAGREEMENT:
    	  return treatment_performance_disagreement;
      case TREATMENT_STATUS_CANCELLED:
    	  return treatment_status_cancelled;
      case TREATMENT_STATUS_FINISHED:
    	  return treatment_status_finished;
      case TREATMENT_STATUS_PROLONGED:
    	  return treatment_status_prolonged;
     
      case SESSION:
          return session_performance_reminder;
      case EVOLUTION:
          return evolution;
     
      case SESSION_INTEREST:
          return session_interest;
      case TREATMENT_INTEREST:
          return treatment_interest;
      case BEGINER_INTEREST:
          return beginer_interest;
      case FAMILIAR_INTEREST:
          return familiar_interest;
      case NON_PERFORMANCE_CAUSE:
          return non_performance_cause;
      case SESSION_AGREEMENT:
          return session_agreement;
      case TREATMENT_AGREEMENT:
          return treatment_agreement;
      case AGREEMENT_PROPOSAL:
          return agreement_proposal;
      case SESSION_PERFORMANCE:
          return session_performance;
          
      case VS_FEAR:
          return vs_fear;
      case VS_DESPAIR:
          return vs_despair;
      case VS_FRUSTRATION:
          return vs_frustration;
      case VS_LACK_OF_HABILITY:
          return vs_lack_of_hability;
          
          
    default:
      return null;    }
  }

  public static final MotivationalMessageSubclassification valueOf(String name) {
	if (name == null)
	    return null;

	if (name.startsWith(MessageOntology.NAMESPACE))
	    name = name.substring(MessageOntology.NAMESPACE.length());

	for (int i = ACADEMIC; i <= TREATMENT_STATUS_PROLONGED; i++)
	    if (names[i].equals(name))
		return getMotivationalMessageSubclassificationByOrder(i);

	return null;
  }

}
