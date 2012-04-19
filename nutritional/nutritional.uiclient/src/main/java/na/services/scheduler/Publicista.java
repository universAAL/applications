package na.services.scheduler;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import na.oasisUtils.ami.AmiConnector;
import na.oasisUtils.trustedSecurityNetwork.TSFConnector;
import na.utils.AdviseRepository;
import na.utils.MiniCalendar;
import na.utils.OASIS_ServiceUnavailable;
import na.utils.ServiceInterface;
import na.utils.Utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


class Publicista implements Runnable{
	private static Log log = LogFactory.getLog(Publicista.class);
	private static final int DELAY = 500;
	private static Publicista instance  = null;
	private static boolean showingMessage = false;
	
	private Avisos datosMostrables;
	private AvisosChecker datosYaMostrados;
	
	private Juez juez;
	
	private Publicista() {
		
	}
	
	protected static synchronized Publicista getInstance() {
		if (Publicista.instance == null)
			Publicista.instance = new Publicista();
		return Publicista.instance;		
	}
		
	protected void init(Juez j) {
		this.juez = j;
//		this.avisosMostrados = a;
	}
	
	public void run() {
		try {
			log.info("P: Running thread: "+Publicista.class.getCanonicalName());
			
			while (true) {
				synchronized (this.juez) {
					while (this.datosMostrables.isEmpty()) {
						log.info("P: no data, waiting for more");
						juez.wait();
					}
				}
				
				while (! this.datosMostrables.isEmpty() ) {
					ExtraAdvise item = (ExtraAdvise) this.datosMostrables.getFirst();
	//				log.info("P: showing item "+ item.advise.getTitle());
					//actualizar item!
					if (item.advise.getPicture()!=null && item.advise.getPicture().length()>0) {
						Publicista.showMessageWithPicture(item);
					} else {
						Publicista.showMessage(item);
					}
					ExtraAdvise iteng = (ExtraAdvise) this.datosMostrables.removeFirst();
					Calendar cal = Utils.Dates.getMyCalendar();
					item.setLastTimeShown(cal);
					item.setSentToPublicist(true);
					this.datosYaMostrados.addItem(item);
					MiniCalendar showTime = Utils.Dates.getCurrentAdviceTime();
					AdviseRepository.addAdvise(item.advise.getID(), showTime);
					this.wait_miliseconds(DELAY);
				}
	//			log.info("P: stack empty");
			}
		} catch (InterruptedException ex) {
			this.datosMostrables = null;
			this.datosYaMostrados = null;
			log.info("P: Publicista ends forever");
			Thread.currentThread().interrupt();
		}
	}

	
	
	protected static void showScreenSaver(String item) {
		if (Publicista.isShowingMessage()) {
//			log.info("There is a message already being shown");
		} else { 
			String[] valores = Publicista.getRandomNutritionalTip();
			Publicista.showPopUpTip("Nutritional Tip", valores[0], valores[1], JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	private static void showPopUpTip(String title, String message, String fileName, int type) {
		ImageIcon popImage = new ImageIcon(na.utils.ServiceInterface.PATH_TIPS_IMAGES + "/" + fileName);
		PopUp popi = new PopUp();
		popi.setImage(popImage);
		popi.setText(message);
		
		final JComponent[] inputs = new JComponent[] {
		                popi
		};
		JOptionPane.showMessageDialog(null, inputs, title, JOptionPane.PLAIN_MESSAGE);
	}
	
	private synchronized static void showMessage(ExtraAdvise item) {
		Publicista.setShowingMessage(true); 
		JOptionPane.showMessageDialog(null, "Advise: "+item.advise.getMessage(),item.advise.getTitle(), JOptionPane.PLAIN_MESSAGE);
		Publicista.setShowingMessage(false);
	}
	
	private static void showMessageWithPicture(ExtraAdvise item) {
		Publicista.setShowingMessage(true);
		
		/*
		 * Try to get image
		 */
		NicePopUp popi = new NicePopUp();
		try {
			ImageIcon popImage = new ImageIcon (Publicista.getAdvisePircture(item.advise.getID()));
			popi.setImage(popImage);
		} catch (Exception e) {
			log.error("error :(");
			e.printStackTrace();
		}
		popi.setTitle(item.advise.getTitle());
		popi.setText(item.advise.getMessage());
		
		popi.pack();
		popi.setLocationRelativeTo(null);
		popi.setVisible(true);

		Publicista.setShowingMessage(false);
	}
	
	
	private static String[] getRandomNutritionalTip() {
		Properties properties = new Properties();
		try {
		    properties.load(new FileInputStream(na.utils.ServiceInterface.PATH_TIPS+"/tips_en.properties"));
		    int size = properties.size();
		    Random rand = new Random();
		    int i = rand.nextInt(size);
		    i++;
//		    log.info("Publicista: getting tip "+i);
		    String cadena = (String) properties.get("tip"+i);
		    return cadena.split("@;");
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void wait_miliseconds(int miliseconds) throws InterruptedException {
		Thread.sleep(miliseconds);
	}
	
	private static byte[] getAdvisePircture(int adviseID) throws OASIS_ServiceUnavailable {
		String[] input = {TSFConnector.getInstance().getToken(), ""+adviseID};
		AmiConnector ami = AmiConnector.getAMI();
		byte[] data = (byte[])ami.invokeOperation(ServiceInterface.DOMAIN_Nutrition, ServiceInterface.OP_GetAdvisePicture, input, false);
		return data;
	}

	/////
	//// GETTERS & SETTERS
	///
	//
	public Avisos getDatosMostrables() {
		return datosMostrables;
	}

	public void setDatosMostrables(Avisos datosMostrables) {
		this.datosMostrables = datosMostrables;
	}

	private static void setShowingMessage(boolean showingMessage) {
		Publicista.showingMessage = showingMessage;
	}

	private static boolean isShowingMessage() {
		return showingMessage;
	}

	public void setDatosYaMostrados(AvisosChecker avisosYaMostrados) {
		this.datosYaMostrados = avisosYaMostrados;
	}
}
