package na.services.scheduler;


import na.services.scheduler.evaluation.ConditionEvaluator;
import na.services.scheduler.evaluation.TimeEvaluator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * The Class Juez. Loops through the available notifications. Check the rules:
 * check the time condition
 * if ok-> check Conditions
 * if ok-> send to publisher by writing in datosMostrables
 * 
 * It waits DELAY_in_SECONDS between notifications to avoid overwhelming the user
 */
public class Juez implements  Runnable {
	private Log log = LogFactory.getLog(Juez.class);
	private TimeEvaluator timeEvaluator;
	private ConditionEvaluator conditionEvaluator;
	
	private Vigilante vigilante;
	private Avisos datos;
	private Avisos datosFuturos;
	private Avisos datosMostrables;

	private static final int DELAY_in_SECONDS = 60; //every X seconds, analyzes existing advices from datos
	
	public static final int ACTION_PUBLISH = 1;
	public static final int ACTION_DESTROY = 2;
	public static final int ACTION_IGNORE = 3;
	public static final int ERROR = -1;
	
	protected Juez(Vigilante v) {
		this.vigilante = v;
		this.datosFuturos = new Avisos();
		this.timeEvaluator = new TimeEvaluator();
		this.conditionEvaluator = new ConditionEvaluator();
	}
	
	
	/*
	 * Reads the output of Vigilante. Process every item
	 */
	public void run() {
		try {
			while (true) {
				// log.info("Profile: startListeningToFatigueChanges...");
				// GenericProfileProperty gpp = new GenericProfileProperty();
				// //
				// gpp.setAbsoluteName(NP.External.ActivityCoach.FATIGUE_LEVEL);
				// gpp.setAbsoluteName("/apps/health/HealthCond_Healthy");
				// ProfileConnector.getInstance().frontEnd.addAMIPropertyChangeListener(gpp,
				// this);
				// log.info("Profile: startListeningToFatigueChanges: SET");

				// wait for incoming Notifications
				synchronized (this.vigilante) {
					while (this.datos.isEmpty()) {
						// log.info("J: no data, waiting for more");
						vigilante.wait();
					}
				}

				// loop advises, one by one
				// check the time condition
				// if ok-> check Conditions
				// if ok-> send to publisher
				while (!this.datos.isEmpty()) {
					ExtraAdvise item = (ExtraAdvise) this.datos.removeFirst();
					if (item.advise.getStatus() != 2) {
//						log.info("Advise not active: "+ item.advise.getTitle());
						continue;
					} else {
//						log.info("Advises status for :"+ item.advise.getTitle() + "> "+ item.advise.getStatus());
					}
					// review content RULES
					 log.info("J: >>>>>> ANALIZANDO:  "+item.advise.getTitle());
					int stateTime = this.timeEvaluator.checkTempRules(item);
					if (stateTime == Juez.ACTION_PUBLISH) {
						int stateCond = this.conditionEvaluator.checkConditions(item);
						switch (stateCond) {
						case ConditionEvaluator.NUTRITION_CONDITION_Valid:
							// log.info("Condicion Valida");
							synchronized (this) {
								log.info("J: >>>>>> Mostrando advise:  "+ item.advise.getTitle());
								item.setSentToPublicist(true);
								this.datosMostrables.addItem(item);
								notifyAll();
							}
							break;

						case ConditionEvaluator.NUTRITION_CONDITION_InValid:
							// log.info("J: Condicion NO Valida");
							break;
						default:
							log.error("J: Error invalid condition!");
							break;
						}

					} else {
						// log.info("J: Event din't pass the temp rules. State: "+
						// stateTime);
						if (stateTime == Juez.ACTION_DESTROY) {
							// log.info("J: DESTROY "+item.advise.getTitle());
						} else if (stateTime == Juez.ACTION_IGNORE) {
							// log.info("J: IGNORE");
							// this.datos.setLast(item);
							this.datosFuturos.setLast(item); // reprocesar en el futuro
						}
					}
				}
				// log.info("J: END OF ITERATION");

				// wait to process next set of advises
				log.info("J: Waiting " + DELAY_in_SECONDS+ " seconds for next iteration.");
				this.wait_seconds(DELAY_in_SECONDS);

				// move data from future to actual
				while (!this.datosFuturos.isEmpty()) {
					this.datos.addItem(this.datosFuturos.removeFirst());
				}
			}
		} catch (InterruptedException ex) {
			this.conditionEvaluator = null;
			this.datos = null;
			this.datosFuturos = null;
			this.datosMostrables = null;
			this.timeEvaluator = null;
			log.info("J: Juez ends forever");
			Thread.currentThread().interrupt();
		}
	}	
	

	/////////////////////////////////////////////////////
	////////////////////////////////////////////////////
	///////////////////////////////////////////////////
	
	
	private void wait_seconds(int seconds) throws InterruptedException {
		Thread.sleep(seconds*1000);
	}
	
	/////
	//// GETTERS & SETTERS
	///
	//
	public void setDatos(Avisos datos) {
		this.datos = datos;
	}

	public Avisos getDatos() {
		return datos;
	}

	public void setDatosMostrables(Avisos datosMostrables) {
		this.datosMostrables = datosMostrables;
	}

	public Avisos getDatosMostrables() {
		return datosMostrables;
	}


////	@Override
//	public void amiPropertyChange(AmiPropertyChangeEvent p) {
//		log.info("Profile: property changed!");
//		String newValue = p.getNewValue();
//		if (newValue!=null) {
//			log.info("Profile: new value for fatigue level: "+newValue);
//			ExtraAdvise e = new ExtraAdvise();
//			e.advise.setTitle("HIGH FATIGUE LEVEL!");
//			e.advise.setMessage("You are fatigued!");
//			e.setSentToPublicist(true);
//			this.datosMostrables.addItem(e);
//		} else {
//			log.info("Profile: propertyChange is null");
//		}
//	}


	
}
