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
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.universAAL.ontology.safetyDevices.Door;
import org.universAAL.ontology.phThing.Device;


enum SoundEffect {
	
	
	   OPEN("/sounds/door_open.wav"),
	   CLOSE("/sounds/door_close.wav");     
	   
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
	private MyJPanel[] myPanels;
	private JTextField tempTextField = null;
	private AbstractAction Off;
	private AbstractAction On;
	private JFrame frame;
    private JButton applyButton = new JButton("Set");
    private BufferedImage img;
    
    // create the GUI
	public void start(Device[] d) {
		frame = new JFrame();
		javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
		javax.swing.JPanel downPanel = new javax.swing.JPanel();
		javax.swing.JButton jButton2 = new javax.swing.JButton();
		javax.swing.JButton jButton1 = new javax.swing.JButton();
		javax.swing.JPanel centerPanel = new javax.swing.JPanel();
		frame.setTitle("Safety Manager");
		frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		jPanel1.setBackground(Color.WHITE);	
		
		frame.getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);
		frame.getContentPane().add(centerPanel, java.awt.BorderLayout.CENTER);
		centerPanel.setLayout(new java.awt.GridLayout(2, 3));
		centerPanel.setBackground(Color.WHITE);
		centerPanel.setSize(900, 600);
		myPanels = new MyJPanel[d.length];
		for (int i = 0; i < d.length; i++) {
			MyJPanel panel1 = createPanel(d[i]);
			Border blueline = BorderFactory.createLineBorder(Color.blue);
			panel1.setBorder(javax.swing.BorderFactory.createTitledBorder(blueline,"Safety Device " + i));
			
			myPanels[i] = panel1;
			panel1.setMinimumSize(new Dimension(150, 150));
			panel1.setVisible(true);
			panel1.validate();
			centerPanel.add(panel1);
		}
		
		frame.pack();
		frame.validate();
		frame.setVisible(true);
	}

	private MyJPanel createPanel(final Device device) {
		
		MyJPanel jPanel1 = new MyJPanel(device);
		javax.swing.JPanel topPanel = new javax.swing.JPanel();
		javax.swing.JPanel centerPanel = new javax.swing.JPanel();
		javax.swing.JPanel jPanel2 = new javax.swing.JPanel();
		javax.swing.JPanel jPanel3 = new javax.swing.JPanel();
		javax.swing.JPanel jPanel4 = new javax.swing.JPanel();
		javax.swing.JPanel jPanel5 = new javax.swing.JPanel();
		jPanel1.setBackground(Color.WHITE);	
		jPanel2.setBackground(Color.WHITE);	
		jPanel3.setBackground(Color.WHITE);	
		jPanel4.setBackground(Color.WHITE);	
		jPanel5.setBackground(Color.WHITE);	
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
			jLabel1.setText("Door");
			jLabel1.setFont(new Font(Font.SANS_SERIF,Font.BOLD,18));
			jLabel1.setForeground(Color.BLUE);
		}
		topPanel.add(jLabel1);
		jPanel1.add(topPanel, java.awt.BorderLayout.NORTH);
		jPanel2.setLayout(new java.awt.BorderLayout());
		jPanel3.setLayout(new java.awt.BorderLayout());
		
		jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel3.setText("\n\nMessage: The Door is locked");
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

		// Load Image and create Open Button
		
		ImageIcon openButtonIcon = new ImageIcon((java.net.URL)SafetyUIClient.class.getResource("/images/door_open.jpg"));
		jButton1 = new JButton("Unlock", openButtonIcon);
		jButton1.setVerticalTextPosition(AbstractButton.BOTTOM);
		jButton1.setHorizontalTextPosition(AbstractButton.CENTER);
		jButton1.setBackground(Color.WHITE);
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				OnActionPerformed(evt, device);
			}
		});
		jPanel4.add(jButton1);
		
		// Load Image and create Close Button
		ImageIcon closeButtonIcon = new ImageIcon((java.net.URL)SafetyUIClient.class.getResource("/images/door_close.jpg"));
		jButton2 = new JButton("Lock", closeButtonIcon);
		jButton2.setVerticalTextPosition(AbstractButton.BOTTOM);
		jButton2.setHorizontalTextPosition(AbstractButton.CENTER);
		jButton2.setBackground(Color.WHITE);
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				OffActionPerformed(evt, device);
			}
		});
		jPanel4.add(jButton2);

		ImageIcon infoButtonIcon = new ImageIcon((java.net.URL)SafetyUIClient.class.getResource("/images/info.jpg"));
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

		jPanel3.add(jPanel4, java.awt.BorderLayout.CENTER);
        jPanel2.add(jPanel5, java.awt.BorderLayout.CENTER);
        jPanel2.add(jPanel3, java.awt.BorderLayout.SOUTH);
        centerPanel.add(jPanel2);
		jPanel1.add(centerPanel, java.awt.BorderLayout.CENTER);

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
			setPreferredSize(new Dimension(150, 150));
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

	private void OnActionPerformed(java.awt.event.ActionEvent evt, Device device) {
		String uri = device.getURI();
		boolean open = SafetyClient.unlock(uri);
		if (open) {
			SoundEffect.OPEN.play();
			for (int i = 0; i < myPanels.length; i++) {
				MyJPanel pan = myPanels[i];

				if (pan.getDevice().equals(device)) {
					System.out.println("checking " + pan.getDevice().getURI()
							+ " with " + device.getURI());
					pan.setText("\n\nMessage: The Door is unlocked");

				}
			}
		}
	}

	private void OffActionPerformed(java.awt.event.ActionEvent evt,
			Device device) {
		String uri = device.getURI();
		boolean close = SafetyClient.lock(uri);
		if (close) {
			SoundEffect.CLOSE.play();

			for (int i = 0; i < myPanels.length; i++) {
				MyJPanel pan = myPanels[i];

				if (pan.getDevice().equals(device)) {
					System.out.println("checking " + pan.getDevice().getURI()
							+ " with " + device.getURI());
					pan.setText("\n\n Message: The Door is locked");

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
					//pan.setText("\n\n Message: " + values.get(0) + " \n " + values.get(1) + " \n ");
					if (((String)values.get(1)).equals("[100]"))
						pan.setText("\n\n Message: The Door is unlocked \n ");
					else
						pan.setText("\n\n Message: The Door is locked \n ");
					values.clear();
				}
			}
		}
	}

}
