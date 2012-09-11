package na.services.scheduler;

import na.utils.Setup;

/**
 * The Class EventScheduler. This class manages the client side of the notification service.
 * There are four independent threads:
 * 		Vigilante: Contacts the nutritionist web service and stores the notifications.
 * 		Juez: Evaluates the available notifications. Every valid notification is sent to Publicista
 * 		Publicista: Takes the validated notifications and displays them on the screen, until the user cliks on it.
 * 		ScreenSaver: Emulates a screensaver, displaying random advises when the user is idle for a while. (disabled)
 * 
 * Flow of information:
 * 
 *      Vigilante        |                Juez              | Publicista
 * ----------------------+----------------------------------+-------------------------------
 *                       |   .-------------------------.    |
 *                       |  /                          V    |
 * WebService data ---> datos ---> datos futuros     datos mostrables ---> datos ya mostrados
 *                       | ^              /                 |
 *                       |  \____________/                  |
 *                       |                                  |
 */
public class EventScheduler {
	static Thread vigilanteThread;
	static Thread screenThread;
	static Thread juezThread;
	static Thread publicistaThread;
	
	public void start() {
		// start threads
		Avisos avisosOriginales = new Avisos();
		Avisos avisosParaMostrar = new Avisos();
		AvisosChecker avisosYaMostrados = new AvisosChecker();
		
		Vigilante v = new Vigilante();
		v.setDatos(avisosOriginales);
		v.setDatosYaMostrados(avisosYaMostrados);
		v.setDatosMostrables(avisosParaMostrar);
		vigilanteThread = new Thread(v);
		vigilanteThread.start();

//		// activate screensaver if needed, otherwise disabled
//		if (Setup.isScreenSaverActive()) {
//			ScreenSaver s = new ScreenSaver();
//			s.setDatosMostrables(avisosParaMostrar);
//			screenThread = new Thread(s);
//			screenThread.start();
//		}
		
		Juez j = new Juez(v);
		j.setDatos(avisosOriginales);
		j.setDatosMostrables(avisosParaMostrar);
		juezThread = new Thread(j);
		juezThread.start();
		
		Publicista p = Publicista.getInstance();
		p.init(j);
		p.setDatosMostrables(avisosParaMostrar);
		p.setDatosYaMostrados(avisosYaMostrados);
		publicistaThread = new Thread(p);
		publicistaThread.start();
		
	}
	
	public static void stop() {
		if (EventScheduler.vigilanteThread!=null)
			EventScheduler.vigilanteThread.interrupt();
		if (EventScheduler.juezThread!=null)
			EventScheduler.juezThread.interrupt();
		if (EventScheduler.publicistaThread!=null)
			EventScheduler.publicistaThread.interrupt();
		if (EventScheduler.screenThread!=null)
			EventScheduler.screenThread.interrupt();
	}
}
