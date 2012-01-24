package org.universAAL.samples.testlocation;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.UIManager;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.DefaultContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.middleware.util.Constants;
import org.universAAL.ontology.location.indoor.Room;
import org.universAAL.ontology.phThing.PhysicalThing;
import org.universAAL.ontology.profile.User;

public class GUIPanel extends javax.swing.JFrame{
	
	private static final long serialVersionUID = 1L;
	// Variables declaration - do not modify
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton locXButton;
    private javax.swing.JPanel panel1;
    private javax.swing.JTextField locURI;
    private javax.swing.JTextField usrURI;
    // End of variables declaration
	private DefaultContextPublisher cp;
	// Pannel
	static private GUIPanel panel;
	
	public GUIPanel(ModuleContext context) {
		// Start the context publisher
		ContextProvider cpinfo = new ContextProvider("http://org.universAAL.ontology.location#IndoorLocationServer");
		cpinfo.setType(ContextProviderType.gauge);
		cp = new DefaultContextPublisher(context, cpinfo);
    	try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			System.err.println("Windows Look&Feel Not Found !");
		}
		initComponents();
	}

	private void initComponents() {
		tabbedPane = new javax.swing.JTabbedPane();
        panel1 = new javax.swing.JPanel();
        locXButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        locURI = new javax.swing.JTextField();
        usrURI = new javax.swing.JTextField();

        //WINDOW
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.setBounds(800, 660, 420, 330);
        this.setMaximumSize(new Dimension(420, 330));
        this.setMinimumSize(new Dimension(420, 330));
        this.setPreferredSize(new Dimension(420, 330));
        setResizable(true);
        getContentPane().setLayout(null);

        //TAB 1
        panel1.setLayout(null);
        locURI.setText("URI name of location");
        usrURI.setText("URI name of user");
        panel1.add(locURI);
        panel1.add(usrURI);
        locURI.setBounds(20, 100, 210, 23);
        usrURI.setBounds(20, 50, 210, 23);

        locXButton.setText("Publish");
        locXButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButton1ActionPerformed(evt);
            }
        });
        
        panel1.add(locXButton);
        locXButton.setBounds(20, 175, 75, 29);
        
        tabbedPane.addTab("Location", panel1);
         
        //MAIN
        getContentPane().add(tabbedPane);
        tabbedPane.setBounds(10, 40, 400, 260);
        jLabel1.setFont(new java.awt.Font("Verdana", 1, 14));
        jLabel1.setText("Location publisher utility");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(10, 10, 360, 18);

        pack();
	}
	
	private void sendButton1ActionPerformed(ActionEvent evt) {
		publishLocation(this.usrURI.getText(),this.locURI.getText());
	}
	
	private void publishLocation(String userURIsuffix, String locURIsuffix){
		if(userURIsuffix!=null){
			User user=new User(Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX+userURIsuffix);
			Room room;
			if(locURIsuffix!=null){
					room = new Room(Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX+locURIsuffix);
			}else{
				room = new Room(Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX+"Undefined");
			}
			user.setLocation(room);
			ContextEvent event = new ContextEvent(user, PhysicalThing.PROP_PHYSICAL_LOCATION);
			cp.publish(event);
		}
	}

	public static void start(ModuleContext context) {
		panel=new GUIPanel(context);
		panel.setVisible(true);		
	}
	
	public static void stop (ModuleContext context) {
		panel.dispose();
	}

}
