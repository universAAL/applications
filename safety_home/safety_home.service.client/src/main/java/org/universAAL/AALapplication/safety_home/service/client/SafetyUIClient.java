package org.universAAL.AALapplication.safety_home.service.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.Vector;
import java.io.*;

import javax.sound.sampled.*; 

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;

import org.universAAL.ontology.Safety.Door;
import org.universAAL.ontology.Safety.Window;
import org.universAAL.ontology.Safety.LightSensor;
import org.universAAL.ontology.Safety.TemperatureSensor;
import org.universAAL.ontology.Safety.HumiditySensor;
import org.universAAL.ontology.Safety.MotionSensor;
import org.universAAL.ontology.phThing.Device;


enum SoundEffect {
	
	   OPEN("/sounds/door_open.wav"),
	   CLOSE("/sounds/door_close.wav"),     
	   LOCK("/sounds/door_lock.wav"),
	   UNLOCK("/sounds/door_unlock.wav"),
	   DOORBELL("/sounds/door_bell.wav"),
	   SMOKE("/sounds/smoke_detection.wav"),
	   MOTION("/sounds/motion_detection.wav"),
	   WINDOW("/sounds/window_open.wav");
	   
	   // Nested class for specifying volume
	   public static enum Volume {
	      MUTE, LOW, MEDIUM, HIGH
	   }
	   
	   public static Volume volume = Volume.LOW;
	   
	   // Each sound effect has its own clip, loaded with its own sound file.
	   private Clip clip;
	   
	   // Constructor to construct each element of the enum with its own sound file.
	   SoundEffect(String soundFileName) {
	      try {
	    	  /* Open File and create audio input stream  */
	    	  //File soundFile = new File(soundFileName);
	    	  //AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
	    	  /* Open input stream and create audio input stream  */
	    	  AudioInputStream audioIn = AudioSystem.getAudioInputStream(SafetyUIClient.class.getResourceAsStream(soundFileName));
	    	  // Get a clip resource.
	    	  clip = AudioSystem.getClip();
	    	  // Open audio clip and load samples from the audio input stream.
	    	  clip.open(audioIn);
	      } catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (LineUnavailableException e) {
	         e.printStackTrace();
	      }
	   }
	   
	   // Play or Re-play the sound effect from the beginning, by rewinding.
	   public void play() {
	      if (volume != Volume.MUTE) {
	         if (clip.isRunning())
	            clip.stop();   // Stop the player if it is still running
	         clip.setFramePosition(0); // rewind to the beginning
	         clip.start();     // Start playing
	      }
	   }
	   
	   // Optional static method to pre-load all the sound files.
	   static void init() {
	      values(); // calls the constructor for all the elements
	   }
}

public class SafetyUIClient extends javax.swing.JPanel {
	private static MyJPanel[] myPanels;
	private JTextField tempTextField = null;
	private AbstractAction Off;
	private AbstractAction On;
	private JFrame frame;
    private JButton applyButton = new JButton("Set");
    private static javax.swing.JLabel jLabel5;
    private static javax.swing.JLabel jLabel10;
    private static javax.swing.JLabel jLabel13;
    private static javax.swing.JLabel jLabel14;
    private static javax.swing.JLabel jLabel15;
    private static javax.swing.JLabel jLabel16;
    private static javax.swing.JLabel jLabel17;
    private static javax.swing.JLabel jLabel18;
    private static javax.swing.JLabel jLabel19;
    private static javax.swing.JLabel jLabel20;
    private static javax.swing.JLabel jLabel21;
   
    private static int motionWarnings = 0;
    private static int smokeWarnings = 0;
    
    // create the GUI
	public void start(Device[] d) {
		//try{
		//	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		//}
		//catch(Exception e){}
		
		frame = new JFrame();
		javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
		javax.swing.JPanel downPanel = new javax.swing.JPanel();
		javax.swing.JButton jButton2 = new javax.swing.JButton();
		javax.swing.JButton jButton1 = new javax.swing.JButton();
		javax.swing.JPanel centerPanel = new javax.swing.JPanel();
		frame.setTitle("Safety Manager");
		frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		jPanel1.setBackground(Color.WHITE);	
		//jPanel1.setSize(450, 0);
		
		frame.getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);
		frame.getContentPane().add(centerPanel, java.awt.BorderLayout.CENTER);
		frame.setPreferredSize(new Dimension(900,900));
		centerPanel.setLayout(new java.awt.GridLayout(2, 3));
		centerPanel.setBackground(Color.WHITE);
		centerPanel.setPreferredSize(new Dimension(500,900));
		//centerPanel.setSize(450, 0);
		myPanels = new MyJPanel[d.length];
		
		for (int i = 0; i < d.length; i++) {
			MyJPanel panel1 = createPanel(d[i]);
			Border blueline = BorderFactory.createLineBorder(Color.blue);
			panel1.setBorder(javax.swing.BorderFactory.createTitledBorder(blueline,"Safety Device " + i));
			panel1.setSize(900,500);
			myPanels[i] = panel1;
			//panel1.setMinimumSize(new Dimension(150, 150));
			panel1.setVisible(true);
			panel1.validate();
			centerPanel.add(panel1);
		}
		
		frame.pack();
		frame.validate();
		frame.setVisible(true);
	}

	private static Device dev;
	private MyJPanel createPanel(final Device device) {
		
		this.dev = device;
		MyJPanel jPanel1 = new MyJPanel(device);
		javax.swing.JPanel topPanel = new javax.swing.JPanel();
		javax.swing.JPanel centerPanel = new javax.swing.JPanel();
		javax.swing.JPanel jPanel2 = new javax.swing.JPanel();
		javax.swing.JPanel jPanel3 = new javax.swing.JPanel();
		javax.swing.JPanel jPanel4 = new javax.swing.JPanel();
		javax.swing.JPanel jPanel5 = new javax.swing.JPanel();
		javax.swing.JPanel jPanel6 = new javax.swing.JPanel();
		jPanel1.setBackground(Color.WHITE);	
		jPanel2.setBackground(Color.WHITE);	
		jPanel3.setBackground(Color.WHITE);	
		jPanel4.setBackground(Color.WHITE);	
		jPanel5.setBackground(Color.WHITE);	
		jPanel6.setBackground(Color.WHITE);	
		topPanel.setBackground(Color.WHITE);	
		centerPanel.setBackground(Color.WHITE);	
		
		javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
		javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
		javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
		javax.swing.JLabel jLabel4 = new javax.swing.JLabel();

		javax.swing.JButton jButton1;
		javax.swing.JButton jButton2;
		javax.swing.JButton jButton4 = new javax.swing.JButton();

		jPanel1.setLayout(new java.awt.BorderLayout());
		if (device instanceof Door) {
			jLabel1.setText("Front Door");
			jLabel1.setFont(new Font(Font.SANS_SERIF,Font.BOLD,18));
			jLabel1.setForeground(Color.BLUE);
		topPanel.add(jLabel1);
		jPanel1.add(topPanel, java.awt.BorderLayout.NORTH);
		jPanel2.setLayout(new java.awt.BorderLayout());
		jPanel3.setLayout(new java.awt.BorderLayout());
		
		jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel3.setText("\n\nThe Door is locked");
		jLabel3.setFont(new Font(Font.SANS_SERIF,Font.ITALIC,14));
		jLabel3.setForeground(Color.BLUE);
		
		jPanel1.setMyLabel(jLabel3);
		jPanel3.add(jLabel3, java.awt.BorderLayout.SOUTH);

		jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel4.setText("\n\n");
		jPanel3.add(jLabel4, java.awt.BorderLayout.NORTH);

		// Initialize Sound Effect
		SoundEffect.init();
		SoundEffect.volume = SoundEffect.Volume.MEDIUM;  // un-mute

		// Load Image and create Unlock Button
		ImageIcon unlockButtonIcon = new ImageIcon((java.net.URL)SafetyUIClient.class.getResource("/images/door_unlock_small.jpg"));
		jButton1 = new JButton("Unlock", unlockButtonIcon);
		jButton1.setVerticalTextPosition(AbstractButton.BOTTOM);
		jButton1.setHorizontalTextPosition(AbstractButton.CENTER);
		jButton1.setBackground(Color.WHITE);
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				OnActionPerformed(evt, device);
			}
		});
		jPanel4.add(jButton1);
		
		// Load Image and create Lock Button
		ImageIcon lockButtonIcon = new ImageIcon((java.net.URL)SafetyUIClient.class.getResource("/images/door_lock_small.jpg"));
		jButton2 = new JButton("Lock", lockButtonIcon);
		jButton2.setVerticalTextPosition(AbstractButton.BOTTOM);
		jButton2.setHorizontalTextPosition(AbstractButton.CENTER);
		jButton2.setBackground(Color.WHITE);
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				OffActionPerformed(evt, device);
			}
		});
		jPanel4.add(jButton2);

		ImageIcon infoButtonIcon = new ImageIcon((java.net.URL)SafetyUIClient.class.getResource("/images/info_small.jpg"));
		jButton4 = new JButton("Info", infoButtonIcon);
		jButton4.setVerticalTextPosition(AbstractButton.BOTTOM);
		jButton4.setHorizontalTextPosition(AbstractButton.CENTER);
		jButton4.setBackground(Color.WHITE);
		jButton4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				getInfoActionPerformed(evt, device);
			}
		});
		jPanel4.add(jButton4);
		
		// Load Image and create Open Button
		ImageIcon openButtonIcon = new ImageIcon((java.net.URL)SafetyUIClient.class.getResource("/images/door_open_small.jpg"));
		jButton1 = new JButton("Open", openButtonIcon);
		jButton1.setVerticalTextPosition(AbstractButton.BOTTOM);
		jButton1.setHorizontalTextPosition(AbstractButton.CENTER);
		jButton1.setBackground(Color.WHITE);
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				OpenActionPerformed(evt, device);
			}
		});
		jPanel5.add(jButton1);

		// Load Image and create Close Button
		ImageIcon closeButtonIcon = new ImageIcon((java.net.URL)SafetyUIClient.class.getResource("/images/door_close_small.jpg"));
		jButton2 = new JButton("Close", closeButtonIcon);
		jButton2.setVerticalTextPosition(AbstractButton.BOTTOM);
		jButton2.setHorizontalTextPosition(AbstractButton.CENTER);
		jButton2.setBackground(Color.WHITE);
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				CloseActionPerformed(evt, device);
			}
		});
		jPanel5.add(jButton2);

		
		ImageIcon whoIsIcon = new ImageIcon((java.net.URL)SafetyUIClient.class.getResource("/images/door_bell_small.jpg"));
		jLabel5 = new javax.swing.JLabel(whoIsIcon);
		jLabel5.setVerticalTextPosition(AbstractButton.BOTTOM);
		jLabel5.setHorizontalTextPosition(AbstractButton.CENTER);
		jLabel5.setBackground(Color.WHITE);
		jLabel5.setFont(new Font("SansSerif", Font.BOLD, 16));
		//jLabel5.setText("Nobody is knocking at the door");
		jPanel5.add(jLabel5);

		//jPanel4.add(jPanel6, java.awt.BorderLayout.SOUTH);
		jPanel3.add(jPanel5, java.awt.BorderLayout.CENTER);
		//jPanel2.add(jPanel6, java.awt.BorderLayout.NORTH);
        jPanel2.add(jPanel4, java.awt.BorderLayout.CENTER);
        jPanel2.add(jPanel3, java.awt.BorderLayout.SOUTH);
        centerPanel.add(jPanel2);
		jPanel1.add(centerPanel, java.awt.BorderLayout.CENTER);

		}
		if (device instanceof Window) {
			jLabel1.setText("Window");
			jLabel1.setFont(new Font(Font.SANS_SERIF,Font.BOLD,18));
			jLabel1.setForeground(Color.BLUE);

			topPanel.add(jLabel1);
			jPanel1.add(topPanel, java.awt.BorderLayout.NORTH);
			jPanel2.setLayout(new java.awt.BorderLayout());
			jPanel3.setLayout(new java.awt.BorderLayout());
			
			jLabel13 = new javax.swing.JLabel();
			jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			jLabel13.setText("\n\n The Window is closed");
			jLabel13.setFont(new Font(Font.SANS_SERIF,Font.ITALIC,14));
			jLabel13.setForeground(Color.BLUE);
			
			jPanel1.setMyLabel(jLabel13);
			jPanel3.add(jLabel13, java.awt.BorderLayout.SOUTH);

			// Initialize Sound Effect
			SoundEffect.init();
			SoundEffect.volume = SoundEffect.Volume.MEDIUM;  // un-mute

			
			ImageIcon whoIsIcon = new ImageIcon((java.net.URL)SafetyUIClient.class.getResource("/images/closed_window_small.jpg"));
			jLabel10 = new javax.swing.JLabel(whoIsIcon);
			jLabel10.setVerticalTextPosition(AbstractButton.BOTTOM);
			jLabel10.setHorizontalTextPosition(AbstractButton.CENTER);
			jLabel10.setBackground(Color.WHITE);
			jLabel10.setFont(new Font("SansSerif", Font.BOLD, 16));
			//jLabel5.setText("Nobody is knocking at the door");
			jPanel2.add(jLabel10, java.awt.BorderLayout.CENTER);
	        jPanel2.add(jPanel3, java.awt.BorderLayout.SOUTH);
		
	        centerPanel.add(jPanel2);
			jPanel1.add(centerPanel, java.awt.BorderLayout.CENTER);
		}
		if (device instanceof LightSensor) {
			jLabel1.setText("Light");
			jLabel1.setFont(new Font(Font.SANS_SERIF,Font.BOLD,18));
			jLabel1.setForeground(Color.BLUE);

			topPanel.add(jLabel1);
			jPanel1.add(topPanel, java.awt.BorderLayout.NORTH);
			jPanel2.setLayout(new java.awt.BorderLayout());
			jPanel3.setLayout(new java.awt.BorderLayout());
			
			jLabel14 = new javax.swing.JLabel();
			jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			jLabel14.setText("\n\n The light is off");
			jLabel14.setFont(new Font(Font.SANS_SERIF,Font.ITALIC,14));
			jLabel14.setForeground(Color.BLUE);
			
			jPanel1.setMyLabel(jLabel14);
			jPanel3.add(jLabel14, java.awt.BorderLayout.SOUTH);

			ImageIcon lightIcon = new ImageIcon((java.net.URL)SafetyUIClient.class.getResource("/images/light_off_small.jpg"));
			jLabel15 = new javax.swing.JLabel(lightIcon);
			jLabel15.setVerticalTextPosition(AbstractButton.BOTTOM);
			jLabel15.setHorizontalTextPosition(AbstractButton.CENTER);
			jLabel15.setBackground(Color.WHITE);
			jLabel15.setFont(new Font("SansSerif", Font.BOLD, 16));
			//jLabel5.setText("Nobody is knocking at the door");
			jPanel2.add(jLabel15, java.awt.BorderLayout.CENTER);
	        jPanel2.add(jPanel3, java.awt.BorderLayout.SOUTH);
		
	        centerPanel.add(jPanel2);
			jPanel1.add(centerPanel, java.awt.BorderLayout.CENTER);
		}
		if (device instanceof TemperatureSensor) {
			jLabel1.setText("Temperature");
			jLabel1.setFont(new Font(Font.SANS_SERIF,Font.BOLD,18));
			jLabel1.setForeground(Color.BLUE);

			topPanel.add(jLabel1);
			jPanel1.add(topPanel, java.awt.BorderLayout.NORTH);
			jPanel2.setLayout(new java.awt.BorderLayout());
			jPanel3.setLayout(new java.awt.BorderLayout());
			
			jLabel16 = new javax.swing.JLabel();
			jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			jLabel16.setText("\n\n Temperature sensor is active.");
			jLabel16.setFont(new Font(Font.SANS_SERIF,Font.ITALIC,14));
			jLabel16.setForeground(Color.BLUE);
			
			jPanel1.setMyLabel(jLabel16);
			jPanel3.add(jLabel16, java.awt.BorderLayout.SOUTH);

			ImageIcon tempIcon = new ImageIcon((java.net.URL)SafetyUIClient.class.getResource("/images/temperature_small.jpg"));
			jLabel17 = new javax.swing.JLabel(tempIcon);
			jLabel17.setVerticalTextPosition(AbstractButton.BOTTOM);
			jLabel17.setHorizontalTextPosition(AbstractButton.CENTER);
			jLabel17.setBackground(Color.WHITE);
			jLabel17.setFont(new Font("SansSerif", Font.BOLD, 16));
			
			jPanel2.add(jLabel17, java.awt.BorderLayout.CENTER);
	        jPanel2.add(jPanel3, java.awt.BorderLayout.SOUTH);
		
	        centerPanel.add(jPanel2);
			jPanel1.add(centerPanel, java.awt.BorderLayout.CENTER);
		}
		if (device instanceof HumiditySensor) {
			jLabel1.setText("Humidity");
			jLabel1.setFont(new Font(Font.SANS_SERIF,Font.BOLD,18));
			jLabel1.setForeground(Color.BLUE);

			topPanel.add(jLabel1);
			jPanel1.add(topPanel, java.awt.BorderLayout.NORTH);
			jPanel2.setLayout(new java.awt.BorderLayout());
			jPanel3.setLayout(new java.awt.BorderLayout());
			
			jLabel21 = new javax.swing.JLabel();
			jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			jLabel21.setText("\n\n Humidity sensor is active.");
			jLabel21.setFont(new Font(Font.SANS_SERIF,Font.ITALIC,14));
			jLabel21.setForeground(Color.BLUE);
			
			jPanel1.setMyLabel(jLabel21);
			jPanel3.add(jLabel21, java.awt.BorderLayout.SOUTH);

			ImageIcon humidityIcon = new ImageIcon((java.net.URL)SafetyUIClient.class.getResource("/images/humidity_small.jpg"));
			jLabel18 = new javax.swing.JLabel(humidityIcon);
			jLabel18.setVerticalTextPosition(AbstractButton.BOTTOM);
			jLabel18.setHorizontalTextPosition(AbstractButton.CENTER);
			jLabel18.setBackground(Color.WHITE);
			jLabel18.setFont(new Font("SansSerif", Font.BOLD, 16));
			
			jPanel2.add(jLabel18, java.awt.BorderLayout.CENTER);
	        jPanel2.add(jPanel3, java.awt.BorderLayout.SOUTH);
		
	        centerPanel.add(jPanel2);
			jPanel1.add(centerPanel, java.awt.BorderLayout.CENTER);
		}
		if (device instanceof MotionSensor) {
			jLabel1.setText("Motion");
			jLabel1.setFont(new Font(Font.SANS_SERIF,Font.BOLD,18));
			jLabel1.setForeground(Color.BLUE);

			topPanel.add(jLabel1);
			jPanel1.add(topPanel, java.awt.BorderLayout.NORTH);
			jPanel2.setLayout(new java.awt.BorderLayout());
			jPanel3.setLayout(new java.awt.BorderLayout());
			
			jLabel19 = new javax.swing.JLabel();
			jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			jLabel19.setText("\n The motion detector is active");
			jLabel19.setFont(new Font(Font.SANS_SERIF,Font.ITALIC,14));
			jLabel19.setForeground(Color.BLUE);
			
			jPanel1.setMyLabel(jLabel19);
			jPanel3.add(jLabel19, java.awt.BorderLayout.SOUTH);

			ImageIcon motionIcon = new ImageIcon((java.net.URL)SafetyUIClient.class.getResource("/images/motion_small.jpg"));
			jLabel20 = new javax.swing.JLabel(motionIcon);
			jLabel20.setVerticalTextPosition(AbstractButton.BOTTOM);
			jLabel20.setHorizontalTextPosition(AbstractButton.CENTER);
			jLabel20.setBackground(Color.WHITE);
			jLabel20.setFont(new Font("SansSerif", Font.BOLD, 16));
			jPanel2.add(jLabel20, java.awt.BorderLayout.CENTER);
	        jPanel2.add(jPanel3, java.awt.BorderLayout.SOUTH);
		
	        centerPanel.add(jPanel2);
			jPanel1.add(centerPanel, java.awt.BorderLayout.CENTER);
		}
		return jPanel1;
	}

	private class MyJPanel extends javax.swing.JPanel {
		private Device device;
		private javax.swing.JLabel myLabel;

		public MyJPanel(Device device) {
			this.device = device;
		}

		public Device getDevice() {
			return device;
		}

		public void setMyLabel(javax.swing.JLabel label) {
			this.myLabel = label;
		}

		public void setText(String text) {
			myLabel.setText(text);
			this.validate();
		}
	}

	public SafetyUIClient(Device[] d) {
		super();
		initGUI();
		start(d);
	}

	private void initGUI() {
		try {
			setPreferredSize(new Dimension(900, 900));
			this.setLayout(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setComponentPopupMenu(final java.awt.Component parent,
			final javax.swing.JPopupMenu menu) {
		parent.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
				if (e.isPopupTrigger())
					menu.show(parent, e.getX(), e.getY());
			}

			public void mouseReleased(java.awt.event.MouseEvent e) {
				if (e.isPopupTrigger())
					menu.show(parent, e.getX(), e.getY());
			}
		});
	}

	private void OpenActionPerformed(java.awt.event.ActionEvent evt, Device device) {
		String uri = device.getURI();
		boolean open = SafetyClient.open(uri);
		if (open) {
			SoundEffect.OPEN.play();
			for (int i = 0; i < myPanels.length; i++) {
				MyJPanel pan = myPanels[i];

				if (pan.getDevice().equals(device)) {
					System.out.println("checking " + pan.getDevice().getURI()
							+ " with " + device.getURI());
					pan.setText("\n\n The Door is open");
				}
			}
		}
	}

	private static void OpenAction(Device device) {
		String uri = device.getURI();
		boolean open = SafetyClient.open(uri);
		if (open) {
			SoundEffect.OPEN.play();
			for (int i = 0; i < myPanels.length; i++) {
				MyJPanel pan = myPanels[i];

				if (pan.getDevice().equals(device)) {
					System.out.println("checking " + pan.getDevice().getURI()
							+ " with " + device.getURI());
					pan.setText("\n\n The Door is open");
				}
			}
		}
	}

	private void CloseActionPerformed(java.awt.event.ActionEvent evt, Device device) {
		String uri = device.getURI();
		boolean close = SafetyClient.close(uri);
		if (close) {
			SoundEffect.CLOSE.play();
			for (int i = 0; i < myPanels.length; i++) {
				MyJPanel pan = myPanels[i];

				if (pan.getDevice().equals(device)) {
					System.out.println("checking " + pan.getDevice().getURI()
							+ " with " + device.getURI());
					pan.setText("\n\n The Door is closed");
				}
			}
		}
	}

	private void OnActionPerformed(java.awt.event.ActionEvent evt, Device device) {
		String uri = device.getURI();
		boolean open = SafetyClient.unlock(uri);
		if (open) {
			SoundEffect.UNLOCK.play();
			for (int i = 0; i < myPanels.length; i++) {
				MyJPanel pan = myPanels[i];

				if (pan.getDevice().equals(device)) {
					System.out.println("checking " + pan.getDevice().getURI()
							+ " with " + device.getURI());
					pan.setText("\n\n The Door is unlocked");

				}
			}
		}
	}

	private static void OnAction(Device device) {
		String uri = device.getURI();
		boolean open = SafetyClient.unlock(uri);
		if (open) {
			SoundEffect.UNLOCK.play();
			for (int i = 0; i < myPanels.length; i++) {
				MyJPanel pan = myPanels[i];

				if (pan.getDevice().equals(device)) {
					System.out.println("checking " + pan.getDevice().getURI()
							+ " with " + device.getURI());
					pan.setText("\n\n The Door is unlocked");

				}
			}
		}
	}

	private void OffActionPerformed(java.awt.event.ActionEvent evt,
			Device device) {
		String uri = device.getURI();
		boolean close = SafetyClient.lock(uri);
		if (close) {
			SoundEffect.LOCK.play();

			for (int i = 0; i < myPanels.length; i++) {
				MyJPanel pan = myPanels[i];

				if (pan.getDevice().equals(device)) {
					System.out.println("checking " + pan.getDevice().getURI()
							+ " with " + device.getURI());
					pan.setText("\n\n The Door is locked");

				}
			}
		}		
	}

	private void getInfoActionPerformed(java.awt.event.ActionEvent evt, Device device) {
		String uri = device.getURI();
		
		boolean getInfo = SafetyClient.getDeviceInfo(uri);
		if (getInfo) {
			for (int i = 0; i < myPanels.length; i++) {
				MyJPanel pan = myPanels[i];
				Vector values = SafetyClient.getValues();
				if (pan.getDevice().equals(device)) {
					System.out.println("checking " + pan.getDevice().getURI()
							+ " with " + device.getURI());
					//pan.setText("\n\n " + values.get(0) + " \n " + values.get(1) + " \n ");
					if (((String)values.get(1)).equals("[100]"))
						pan.setText("\n\n The Door is unlocked \n ");
					else
						pan.setText("\n\n The Door is locked \n ");
					values.clear();
				}
			}
		}
	}

	public static void setKnockingPerson(String person) {
	  try{	
		String uri = dev.getURI();
		if (person.indexOf("Unknown Person")!=-1){
			person = person.replaceAll(" ", "_");
			MyJPanel pan = myPanels[0];
			jLabel5.setIcon(new ImageIcon((java.net.URL)SafetyUIClient.class.getResource("/images/"+person+".jpg")));
			SoundEffect.DOORBELL.play();
			pan.setText("\n\n " + person.replaceAll("_", " ") + " is knocking on the door." + "\n ");
		}
		else{
			String newperson = person.replaceAll(" ", "_");
			newperson = newperson.substring(newperson.indexOf(":")+2,newperson.length());
			MyJPanel pan = myPanels[0];
			jLabel5.setIcon(new ImageIcon((java.net.URL)SafetyUIClient.class.getResource("/images/"+newperson+".jpg")));
			SoundEffect.DOORBELL.play();
			pan.setText("\n\n " + newperson.replaceAll("_", " ") + " is knocking on the door." + "\n ");
			if (person.indexOf("Formal Caregiver")!=-1){
	    		// Unlock the door
	    		OnAction(dev);
	    		Thread.sleep(10*1000);
	    		// Open the door
	    		OpenAction(dev);
			}
		}
	  }
	  catch (Exception e) {e.printStackTrace();}
	}

	public static void setWindowStatus(int state) {
		MyJPanel pan = myPanels[1];
		if (state==0){
			jLabel10.setIcon(new ImageIcon((java.net.URL)SafetyUIClient.class.getResource("/images/closed_window_small.jpg")));
			pan.setText("\n\n The window is closed." + "\n ");
		}
		if (state==100){
			ImageIcon windowIcon = new ImageIcon((java.net.URL)SafetyUIClient.class.getResource("/images/window_open.jpg"));
			SoundEffect.WINDOW.play();
			JOptionPane pane = new JOptionPane();
			pane.setMessage("Window is open");
			pane.setMessageType(JOptionPane.WARNING_MESSAGE);
			pane.setIcon(windowIcon);
			JDialog d = pane.createDialog(null, "Window");
			d.setVisible(true);
			
			jLabel10.setIcon(new ImageIcon((java.net.URL)SafetyUIClient.class.getResource("/images/opened_window_small.jpg")));
			pan.setText("\n\n The window is opened." + "\n ");
		}
	}

	public static void setLightStatus(int state) {
		MyJPanel pan = myPanels[2];
		if (state==0){
			jLabel15.setIcon(new ImageIcon((java.net.URL)SafetyUIClient.class.getResource("/images/light_off_small.jpg")));
			pan.setText("\n\n The light is off." + "\n ");
		}
		if (state==1000){
			jLabel15.setIcon(new ImageIcon((java.net.URL)SafetyUIClient.class.getResource("/images/light_on_small.jpg")));
			pan.setText("\n\n The light is on." + "\n ");
		}
	}

	public static void setTemperatureValue(float temperature) {
		MyJPanel pan = myPanels[3];
		//jLabel17.setIcon(new ImageIcon((java.net.URL)SafetyUIClient.class.getResource("/images/temperature.jpg")));
		pan.setText("\n\n The temperature is " + temperature +"." + "\n ");
	}

	public static void setHumidityValue(float humidity) {
		MyJPanel pan = myPanels[4];
		pan.setText("\n\n The humidity is " + humidity +"." + "\n ");
	}

	public static void setMotionValue(double motion) {
		MyJPanel pan = myPanels[5];
		long secs = (long)motion/1000;
		long hours = secs / 3600;
		long tmp = secs % 3600;
		long min = tmp / 60;
		long sec = tmp % 60;
		String val = "" + motion;
		
		if (motion < 120000.0 && (motionWarnings == 0)){
			ImageIcon motionIcon = new ImageIcon((java.net.URL)SafetyUIClient.class.getResource("/images/motion_detection.jpg"));
			SoundEffect.MOTION.play();
			
			JOptionPane pane = new JOptionPane();
			pane.setMessage("Motion Detection");
			pane.setMessageType(JOptionPane.WARNING_MESSAGE);
			pane.setIcon(motionIcon);
		    JDialog d = pane.createDialog(null, "Motion");
		    d.setVisible(true);
			//JOptionPane.showMessageDialog(null, "Motion Detection", "Motion", JOptionPane.WARNING_MESSAGE, motionIcon);
			
			int selection = getSelection(pane);
		    switch (selection) {
		    case JOptionPane.OK_OPTION:
		    	motionWarnings = 1;
		    	break;
		    default:
		    	System.out.println("Others");
		    }

		}
		else if (motion > 120000)
			motionWarnings = 0;
		pan.setText("\n Motion detected before \n" + hours + "hours " + min + "min. " + sec + "sec." + "\n ");
		pan.setText(hours + "hours " + min + "min. " + sec + "sec." + "\n ");
	}

	public static void setSmokeValue(boolean smoke) {
		if (smokeWarnings < 3){
			ImageIcon smokeIcon = new ImageIcon((java.net.URL)SafetyUIClient.class.getResource("/images/smoke.jpg"));
			SoundEffect.SMOKE.play();
			JOptionPane pane = new JOptionPane();
			pane.setMessage("Smoke Detection");
			pane.setMessageType(JOptionPane.WARNING_MESSAGE);
			pane.setIcon(smokeIcon);
		    JDialog d = pane.createDialog(null, "Smoke");
		    d.setVisible(true);
			//pane.showMessageDialog(null, "Smoke Detection", "Smoke", JOptionPane.WARNING_MESSAGE, smokeIcon);
			
			int selection = getSelection(pane);
		    switch (selection) {
		    case JOptionPane.OK_OPTION:
		    	smokeWarnings++;
		    	break;
		    default:
		    	System.out.println("Others");
		    }
		}
	}
	public static int getSelection(JOptionPane optionPane) {
	    int returnValue = JOptionPane.CLOSED_OPTION;

	    Object selectedValue = optionPane.getValue();
	    if (selectedValue != null) {
	      Object options[] = optionPane.getOptions();
	      if (options == null) {
	        if (selectedValue instanceof Integer) {
	          returnValue = ((Integer) selectedValue).intValue();
	        }
	      } else {
	        for (int i = 0, n = options.length; i < n; i++) {
	          if (options[i].equals(selectedValue)) {
	            returnValue = i;
	            break; // out of for loop
	          }
	        }
	      }
	    }
	    return returnValue;
	}
	
}
