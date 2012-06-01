package na.services;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.SystemColor;

import javax.swing.JFrame;

import na.utils.ServiceInterface;
import na.widgets.panel.AdaptivePanel;
import na.widgets.panel.InteractionSettingsBar;

@SuppressWarnings("serial")
public class AppFrame extends JFrame {

	public static final int DEFAULT_AGE = 50;
	AdaptivePanel contentPane;
	AdaptivePanel serviceCanvas;
//	private Log log = LogFactory.getLog(AppFrame.class);
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppFrame frame = new AppFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AppFrame() {
		this.setTitle("Nutritional Advisor");
		getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 50, 1024, 768);
        /*Update specific components so as to impose specific rules (both methods take as argument
         the  main container/placeholder the holds all the widgets placed into the UI. This concept
         is similar to the HTML <body> element) */
        
        contentPane = new AdaptivePanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{504, 0};
        gridBagLayout.rowHeights = new int[]{25, 730, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 1.0E-4};
        gridBagLayout.rowWeights = new double[]{0.0, 1.0, 1.0E-4};
        contentPane.setLayout(gridBagLayout);
        {
        	getContentPane().add(contentPane);
        }
        {
    	    	{
    	    		InteractionSettingsBar interactionSettingsBar = new InteractionSettingsBar();
    	    		GridBagConstraints gbc = new GridBagConstraints();
    	    		gbc.fill = GridBagConstraints.BOTH;
    	    		gbc.insets = new Insets(0, 0, 5, 0);
    	    		gbc.gridx = 0;
    	    		gbc.gridy = 0;
    	    		contentPane.add(interactionSettingsBar, gbc);
    	    		interactionSettingsBar.setBackground(SystemColor.inactiveCaption);
    	    	}
            	{
            		serviceCanvas = new ServiceCanvas();
            		serviceCanvas.setLayout(new GridLayout(1, 0, 0, 0));
            		GridBagConstraints gbc = new GridBagConstraints();
            		gbc.fill = GridBagConstraints.BOTH;
            		gbc.gridx = 0;
            		gbc.gridy = 1;
            		contentPane.add(serviceCanvas, gbc);
            	}
        contentPane.updateUI();
        
        // load Nutritional Advisor by default
        this.loadNutritionalAdvisorGUI();
        }
	}
	
	public void loadNutritionalAdvisorGUI() {
		ServiceInterface nutritional = new ServiceLauncher(null);
		nutritional.showApplicationNutritional(serviceCanvas, this);
    }
}
