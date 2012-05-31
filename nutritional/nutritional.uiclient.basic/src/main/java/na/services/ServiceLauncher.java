package na.services;

import javax.swing.JFrame;

import na.oasisUtils.profile.ProfileConnector;
import na.services.scheduler.EventScheduler;
import na.utils.ServiceInterface;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleContext;


public class ServiceLauncher implements ServiceInterface {
	private Log log = LogFactory.getLog(ServiceLauncher.class);
	private na.widgets.panel.AdaptivePanel canvas;
	private ServiceFrame content;
	private BundleContext context;
	
	
	public ServiceLauncher(BundleContext context) {
		this.context = context;
	}

	/**
	 * Draw panel widget on parent
	 */
	public void showApplicationNutritional(na.widgets.panel.AdaptivePanel parent, JFrame frame) {
		log.info("Showing Nutritional Advisor app upv style");
		this.canvas = parent;
		content = new ServiceFrame(this.context, frame);
		// draw on the entire parent canvas
		this.canvas.add(content);
	}

//	@Override
//	public Object exit(ApplicationContext arg0) throws Exception {
//		log.info("EXIT nutritional advisor");
//		ProfileConnector.closeProfile();
//		AmiConnector.closeFrontend();
//		this.content = null;
//		this.canvas = null;
//		EventScheduler.stop();
//		log.info("Nutritional advisor exited");
//		return null;
//	}

//	@Override
//	public Component getView() throws IllegalStateException {
//		log.info("GETTING VIEW...");
//		return content;
//	}

////	@Override
//	public void launch(ApplicationContext arg0) throws Exception {
//		log.info("LAUNCH");
//		content = new ServiceFrame(this.context);
//	}
//
////	@Override
//	public void launch(ApplicationContext arg0, Object arg1, int arg2)
//			throws Exception {
//		log.info("LAUNCH PARAMS");
//		content = new ServiceFrame(this.context);
//	}
//
////	@Override
//	public Image getIcon() {
//		log.info("GET ICON");
//		URL picbfURL = this.getClass().getResource("/na/utils/boiler_pan_32_chachi.png");
//		ImageIcon i = new ImageIcon(picbfURL);
//		return i.getImage();
//	}
//
////	@Override
//	public String getTitle() {
//		log.info("GET TITLE");
//		return "Nutritional Advisor";
//	}
//	
}
