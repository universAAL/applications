package na.services;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.UIManager;
import javax.swing.WindowConstants;

import na.utils.Utils;

public class NutriUILauncher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new NutriUILauncher().launch();
	}
	
	public void launch() {
		Utils.println("launching UI");
		try {
		      UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
		      e.printStackTrace();
		}

		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	AppFrame frame = new AppFrame();

            	WindowListener listener = new WindowAdapter() {
        		    // This method is called when the user clicks the close button
        		    public void windowClosing(WindowEvent evt) {
        		    	// Frame frame = (Frame)evt.getSource();
        		        Utils.println("Window closing!");
        		    }
        		};
        		
        		frame.setExtendedState(frame.getExtendedState() | Frame.MAXIMIZED_BOTH);
        		// intercept close window action
        		frame.addWindowListener(listener);
        		// avoid shutting down the JVM when closing the window 
        		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        		frame.setAlwaysOnTop(true);
        		frame.setLocationByPlatform(true);
        		// show window
            	frame.setVisible(true);
            }
        });
		
		Utils.println("ending UI");
	}

}
