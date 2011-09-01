package na.services.scheduler;


import na.miniDao.Advise;
import na.oasisUtils.ami.AmiConnector;
import na.oasisUtils.trustedSecurityNetwork.TSFConnector;
import na.utils.OASIS_ServiceUnavailable;
import na.utils.ServiceInterface;
import na.utils.Setup;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * The Class Vigilante. Asks the Nutritionist Web Service for notifications. The notifications are added to 
 * Avisos datos. It then waits UPDATE_RATE to ask again.
 */
public class Vigilante implements Runnable{
	private static Log log = LogFactory.getLog(Vigilante.class);	
	private Avisos datos;
	private AvisosChecker datosYaMostrados;
	private Avisos datosMostrables;
	
	private static final int INITAL_WAIT_in_SECONDS = 5;
	
	/* Contacts the web server and asks for Notifications.
	 * If found, stores them
	 * 
	 * WebServer --> datos --> datosMostrables --> datosYaMostrados
	 */
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			try {
				// log.info("V: Running thread: "+Vigilante.class.getCanonicalName());
				// Wait for a few seconds before showing any notification
				this.wait_seconds(INITAL_WAIT_in_SECONDS);

				while (true) {
					try {
						Advise[] advises = this.getAdvisesFromWebServer();
						if (advises != null) {
							// log.info("V: found some advises, size: "+advises.length);
							synchronized (this) {
								for (Advise notification : advises) {
									if (notification == null) {
										log.error("V: Error! notification is null");
										continue;
									}
									ExtraAdvise superAdvise = new ExtraAdvise();
									superAdvise.advise = notification;
									// check if it is in the Publicista queue
									if (this.datosMostrables.contains(superAdvise)) {
										// log.info("V: Advise ya se encuentra en la cola de publicacion: "+superAdvise.advise.getTitle());
									} else {
										if (this.datosYaMostrados.containsKey(superAdvise.advise.getID())) {
											 log.info("V: El ADVISE YA FUE MOSTRADO:"+notification.getTitle());
											 log.info("V: show again?");
											 // comprobar si se muestra varias
											// veces al dia:
											 ExtraAdvise exad = (ExtraAdvise) this.datosYaMostrados.get(superAdvise.advise.getID());
											 if (exad.advise.getTimeInfo()!=null) {
												 if (exad.advise.getTimeInfo().getTimes() != null && exad.advise.getTimeInfo().getTimes().length > 1 ) {
													 log.info("V: EL ADVISE SE MUESTRA VARIAS VECES AL DIA!");
													 datos.addItem(superAdvise);
													 log.info("V: Adding item: " + notification.getTitle());
												 }
											 }
										} else { //no ha sido mostrado, tener en cuenta
											datos.addItem(superAdvise);
											// log.info("V: Adding item: "+
											// notification.getTitle());
										}
									}
								}
								notifyAll(); //procesados todos, avisar al resto para que continuen usando los datos
							}
						} else {
							 log.info("V: No notifications found");
						}
					} catch (OASIS_ServiceUnavailable e) {
						e.printStackTrace();
						log.error("V: Could not download advises from server, service unavailable");
					}
					log.info("V: Waiting : " + Setup.getAdvisesDelayFromServer() + " seconds for next iteration");
					this.wait_seconds(Setup.getAdvisesDelayFromServer());
					// log.info("V: END OF ITERATION");
				}
			} catch (InterruptedException ex) {
				this.datos = null;
				this.datosMostrables = null;
				this.datosYaMostrados = null;
				log.info("V: Vigilante ends forever");
				Thread.currentThread().interrupt();
			}
		}
	}
	
	
	private Advise[] getAdvisesFromWebServer() throws OASIS_ServiceUnavailable {
		String[] input = {TSFConnector.getInstance().getToken()};
		AmiConnector ami = AmiConnector.getAMI();
		Advise[] advises = (Advise[])ami.invokeOperation(ServiceInterface.DOMAIN_Nutrition, ServiceInterface.OP_GetMyAdvises, input, false);
		return advises;
	}
	
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

	public void setDatosYaMostrados(AvisosChecker avisosYaMostrados) {
		this.datosYaMostrados = avisosYaMostrados;
	}


	public void setDatosMostrables(Avisos avisosFiltrados) {
		this.datosMostrables = avisosFiltrados;
	}

}
