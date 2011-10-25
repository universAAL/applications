package org.universAAL.AALapplication.food_shopping.service.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;

import org.universAAL.ontology.foodDevices.Refrigerator;
import org.universAAL.ontology.phThing.Device;

public class FoodManagementUIClient extends javax.swing.JPanel {
	private MyJPanel[] myPanels;
	private JTextField tempTextField = null;
	private JTextField addItemTextField = null;
	private JTextField updateItemTextField = null;
	private JTextField removeItemTextField = null;
	private JTextField quantityTextField = null;
	private AbstractAction Off;
	private AbstractAction On;
	private JFrame frame;
    private JComboBox foodItemComboBox;
    private JComboBox itemNumComboBox;
    private JButton applyButton = new JButton("Edit");
    private String itemList[];
    private String itemNum[];
    
    //
    private javax.swing.JPanel controls = new javax.swing.JPanel();
    
    public void initDeviceFoodItems(Device device) {
    	getFoodItems(device);
    	foodItemComboBox = new JComboBox(itemList);
    	itemNumComboBox = new JComboBox(itemNum);
    }
    
    // create the GUI
	public void start(Device[] d) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e){}
		frame = new JFrame();
		javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
		javax.swing.JPanel downPanel = new javax.swing.JPanel();
		javax.swing.JButton jButton2 = new javax.swing.JButton();
		javax.swing.JButton jButton1 = new javax.swing.JButton();
		javax.swing.JPanel centerPanel = new javax.swing.JPanel();
		frame.setTitle("Food Manager Service");
		frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		jPanel1.setBackground(Color.WHITE);	
		
/*		
		jButton1.setText("Open All Devices");
		jButton2.setText("Close All Devices");
		jPanel1.add(jButton1);
		jPanel1.add(jButton2);
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				openAllDevices();
			}
		});
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				closeAllDevices();
			}
		});
*/		
		
		frame.getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);
		frame.getContentPane().add(centerPanel, java.awt.BorderLayout.CENTER);
		//frame.getContentPane().add(downPanel, java.awt.BorderLayout.SOUTH);
		centerPanel.setLayout(new java.awt.GridLayout(2, 3));
		centerPanel.setBackground(Color.WHITE);
		centerPanel.setSize(900, 600);
		myPanels = new MyJPanel[d.length];
		for (int i = 0; i < d.length; i++) {
			MyJPanel panel1 = createPanel(d[i]);
			Border blueline = BorderFactory.createLineBorder(Color.blue);
			panel1.setBorder(javax.swing.BorderFactory.createTitledBorder(blueline,"Food Device " + i));

			myPanels[i] = panel1;
			panel1.setMinimumSize(new Dimension(250, 250));
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
		javax.swing.JPanel jPanel6 = new javax.swing.JPanel();
		javax.swing.JPanel addItemPanel = new javax.swing.JPanel();
		javax.swing.JPanel addUpdateRemoveItemPanel = new javax.swing.JPanel();
		javax.swing.JPanel updateItemPanel = new javax.swing.JPanel();
		jPanel1.setBackground(Color.WHITE);	
		jPanel2.setBackground(Color.WHITE);	
		jPanel3.setBackground(Color.WHITE);	
		jPanel4.setBackground(Color.WHITE);	
		jPanel5.setBackground(Color.WHITE);	
		jPanel6.setBackground(Color.WHITE);	
		topPanel.setBackground(Color.WHITE);	
		centerPanel.setBackground(Color.WHITE);	
		addItemPanel.setBackground(Color.WHITE);	
		addUpdateRemoveItemPanel.setBackground(Color.WHITE);	
		updateItemPanel.setBackground(Color.WHITE);	
		controls.setBackground(Color.WHITE);	
		
		javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
		javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
		javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
		javax.swing.JLabel jLabel4 = new javax.swing.JLabel();
		javax.swing.JButton jButton1 = new javax.swing.JButton();
		javax.swing.JButton jButton2 = new javax.swing.JButton();
		javax.swing.JButton jButton3 = new javax.swing.JButton();
		javax.swing.JButton jButton4 = new javax.swing.JButton();
		javax.swing.JButton addItemButton= new javax.swing.JButton();
		javax.swing.JButton updateItemButton= new javax.swing.JButton();
		javax.swing.JButton removeItemButton= new javax.swing.JButton();

		initDeviceFoodItems(device);
		
		jPanel1.setLayout(new java.awt.BorderLayout());
		if (device instanceof Refrigerator) {
			jLabel1.setText("Refrigerator");
			jLabel1.setFont(new Font(Font.SANS_SERIF,Font.BOLD,16));
			jLabel1.setForeground(Color.BLUE);
		}
		topPanel.add(jLabel1);
		jPanel1.add(topPanel, java.awt.BorderLayout.NORTH);

		jPanel2.setLayout(new java.awt.BorderLayout());
		if (device instanceof Refrigerator) {
			jLabel2.setIcon(new javax.swing.ImageIcon(""));
		}
		jPanel2.add(jLabel2, java.awt.BorderLayout.NORTH);

		jPanel3.setLayout(new java.awt.BorderLayout());
		jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel3.setText("\n\nMessage: The Device is Off");
		jLabel3.setFont(new Font(Font.SANS_SERIF,Font.ITALIC,12));
		jLabel3.setForeground(Color.BLUE);		
		jPanel1.setMyLabel(jLabel3);
		jPanel3.add(jLabel3, java.awt.BorderLayout.SOUTH);

		//jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		//jLabel4.setText(device.getURI());
		jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel4.setText("\n\n");
		jPanel3.add(jLabel4, java.awt.BorderLayout.NORTH);

		jButton1.setText("On");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				OnActionPerformed(evt, device);
			}
		});
		jPanel4.add(jButton1);
		
		jButton2.setText("Off");
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				OffActionPerformed(evt, device);
			}
		});
		jPanel4.add(jButton2);

		jButton4.setText("Get Info");
		jButton4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				getInfoActionPerformed(evt, device);
			}
		});
		jPanel4.add(jButton4);
		
		tempTextField = new JTextField(5);
		javax.swing.JLabel textFieldLabel = new javax.swing.JLabel("Temperature: ");
		textFieldLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		jPanel5.add(textFieldLabel);
		jPanel5.add(tempTextField);

		jPanel6.setLayout(new java.awt.BorderLayout());
		jButton3.setText("Set");
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				temperatureActionPerformed(evt, device, tempTextField.getText());
			}
		});
		jPanel6.add(jButton3);

		
		/* Add Food items */
		// Panel with textfields for add-remove-update
		addItemPanel.setLayout(new java.awt.GridLayout(0,2));
        addItemPanel.add(new javax.swing.JLabel("\n"));
        addItemPanel.add(new javax.swing.JLabel("\n"));
		addItemPanel.add(new javax.swing.JLabel("Food Item:"));
        addItemTextField = new JTextField(10);
        addItemPanel.add(addItemTextField);
        addItemPanel.add(new javax.swing.JLabel("Quantity:"));
        quantityTextField = new JTextField(5);
        addItemPanel.add(quantityTextField);
        addItemPanel.add(new javax.swing.JLabel("\n"));
        addItemPanel.add(new javax.swing.JLabel("\n"));
        
        // Panel with add-remove-update buttons
        updateItemPanel.setLayout(new java.awt.GridLayout(0,3));
        addItemButton.setText("Add");
        addItemButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addfoodItemsActionPerformed(evt, device, addItemTextField.getText(), quantityTextField.getText());
			}
		});
        
        updateItemButton.setText("Update");
        updateItemButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				updatefoodItemsActionPerformed(evt, device, addItemTextField.getText(), quantityTextField.getText());
			}
		});
        
        removeItemButton.setText("Remove");
        removeItemButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				removefoodItemsActionPerformed(evt, device, addItemTextField.getText());
			}
		});
        
        updateItemPanel.add(addItemButton);
        updateItemPanel.add(updateItemButton);
        updateItemPanel.add(removeItemButton);
        updateItemPanel.add(new javax.swing.JLabel("\n"));
        updateItemPanel.add(new javax.swing.JLabel("\n"));
        updateItemPanel.add(new javax.swing.JLabel("\n"));

        addUpdateRemoveItemPanel.setLayout(new java.awt.BorderLayout());
        addUpdateRemoveItemPanel.add(addItemPanel, java.awt.BorderLayout.NORTH);
        addUpdateRemoveItemPanel.add(updateItemPanel, java.awt.BorderLayout.CENTER);
        
		
        /* ComboBox solution */

        controls.add(new javax.swing.JLabel("Food Items:"));
        controls.add(new javax.swing.JLabel(" "));
        controls.add(foodItemComboBox);
        controls.add(itemNumComboBox);
        controls.add(applyButton);

        this.applyButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt)
            {
            	editfoodItemsActionPerformed(evt, device, (String)foodItemComboBox.getSelectedItem(), (String)itemNumComboBox.getSelectedItem());
            	
            	//getfoodItemsActionPerformed(evt, device);
            }
        });
    	this.controls.add(this.applyButton);
		/* End of Upper Components */
		
		jPanel5.add(jPanel6, java.awt.BorderLayout.SOUTH);		
        jPanel3.add(jPanel4, java.awt.BorderLayout.CENTER);
        jPanel2.add(addUpdateRemoveItemPanel, java.awt.BorderLayout.NORTH);
        jPanel2.add(jPanel5, java.awt.BorderLayout.CENTER);
        jPanel2.add(jPanel3, java.awt.BorderLayout.SOUTH);
        
		
		centerPanel.add(jPanel2);
		//jPanel1.add(controls, java.awt.BorderLayout.CENTER);
		jPanel1.add(this.controls, java.awt.BorderLayout.CENTER);
		jPanel1.add(centerPanel, java.awt.BorderLayout.SOUTH);

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

	private void openAllDevices() {
		for (int i = 0; i < myPanels.length; i++) {
			FoodManagementClient.turnOn(myPanels[i].getDevice().getURI());
			MyJPanel pan = myPanels[i];
			pan.setText("\n\nMessage: The Device is On");
		}
	}

	private void closeAllDevices() {
		for (int i = 0; i < myPanels.length; i++) {
			FoodManagementClient.turnOff(myPanels[i].getDevice().getURI());
			MyJPanel pan = myPanels[i];
			pan.setText("\n\nMessage: The Device is Off");
		}
	}

	public FoodManagementUIClient(Device[] d) {
		super();
		initGUI();
		start(d);
	}

	private void initGUI() {
		try {
			setPreferredSize(new Dimension(400, 400));
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
		boolean turnedOn = FoodManagementClient.turnOn(uri);
		if (turnedOn) {
			for (int i = 0; i < myPanels.length; i++) {
				MyJPanel pan = myPanels[i];

				if (pan.getDevice().equals(device)) {
					System.out.println("checking " + pan.getDevice().getURI()
							+ " with " + device.getURI());
					pan.setText("\n\nMessage: The Refrigerator is On.");

				}
			}
		}
	}

	private void OffActionPerformed(java.awt.event.ActionEvent evt,
			Device device) {
		String uri = device.getURI();
		boolean turnedOn = FoodManagementClient.turnOff(uri);
		if (turnedOn) {
			for (int i = 0; i < myPanels.length; i++) {
				MyJPanel pan = myPanels[i];

				if (pan.getDevice().equals(device)) {
					System.out.println("checking " + pan.getDevice().getURI()
							+ " with " + device.getURI());
					pan.setText("\n\n Message: The Refrigerator is Off.");

				}
			}
		}		
	}

	private void editfoodItemsActionPerformed(java.awt.event.ActionEvent evt, Device device, String item, String quantity) {
		this.addItemTextField.setText(item);
		this.quantityTextField.setText(quantity);
	}

	private void addfoodItemsActionPerformed(java.awt.event.ActionEvent evt, Device device, String itemName, String itemNum) {
		String uri = device.getURI();
		int quantity = Integer.parseInt(itemNum);

		boolean addFoodItems = FoodManagementClient.addFoodItems(uri,itemName,quantity);
		if (addFoodItems) {
			for (int i = 0; i < myPanels.length; i++) {
				MyJPanel pan = myPanels[i];

				if (pan.getDevice().equals(device)) {
					System.out.println("checking " + pan.getDevice().getURI()
							+ " with " + device.getURI());
					pan.setText("\n\n");
					pan.setText("Message: New food item added.");

				}
			}
			getFoodItems(device);
		}
	}

	private void updatefoodItemsActionPerformed(java.awt.event.ActionEvent evt, Device device, String itemName, String itemNum) {
		String uri = device.getURI();
		int quantity = Integer.parseInt(itemNum);

		boolean updateFoodItems = FoodManagementClient.updateFoodItems(uri,itemName,quantity);
		if (updateFoodItems) {
			for (int i = 0; i < myPanels.length; i++) {
				MyJPanel pan = myPanels[i];

				if (pan.getDevice().equals(device)) {
					System.out.println("checking " + pan.getDevice().getURI()
							+ " with " + device.getURI());
					pan.setText("\n\n");
					pan.setText("Message: New food item updated.");

				}
			}
			getFoodItems(device);
		}
	}

	private void removefoodItemsActionPerformed(java.awt.event.ActionEvent evt, Device device, String itemName) {
		String uri = device.getURI();

		boolean removeFoodItems = FoodManagementClient.removeFoodItems(uri,itemName);
		if (removeFoodItems) {
			for (int i = 0; i < myPanels.length; i++) {
				MyJPanel pan = myPanels[i];

				if (pan.getDevice().equals(device)) {
					System.out.println("checking " + pan.getDevice().getURI()
							+ " with " + device.getURI());
					pan.setText("\n\n");
					pan.setText("Message: Food item removed.");

				}
			}
			getFoodItems(device);
		}
	}
	
	/* ComboBox solution */
	private void getfoodItemsActionPerformed(java.awt.event.ActionEvent evt, Device device) {
		String uri = device.getURI();
		
		boolean getInfo = FoodManagementClient.getFoodItems(uri);
		if (getInfo) {
			for (int i = 0; i < myPanels.length; i++) {
				MyJPanel pan = myPanels[i];
				Vector items = FoodManagementClient.getItems();
				Vector quantities = FoodManagementClient.getQuantities();
				this.itemList = new String[items.size()];
				this.itemList = (String [])items.toArray(new String[items.size()]); 
				this.itemNum = new String[quantities.size()];
				this.itemNum = (String [])quantities.toArray(new String[items.size()]); 
				items.clear();
				quantities.clear();
			}
			foodItemComboBox.removeAllItems();
			
			for (int j=0;j<itemList.length;j++){
				this.foodItemComboBox.addItem((String)itemList[j]);
			}
			
			itemNumComboBox.removeAllItems();
			for (int j=0;j<itemNum.length;j++){
				this.itemNumComboBox.addItem((String)itemNum[j]);
			}
			//validate();
		}
	}

	private void temperatureActionPerformed(java.awt.event.ActionEvent evt, Device device, String newTemp) {
		String uri = device.getURI();
		try {
			int setTemp = Integer.parseInt(newTemp);

			boolean changeTemp = FoodManagementClient.changeTemperature(uri, setTemp);
			if (changeTemp) {
				for (int i = 0; i < myPanels.length; i++) {
					MyJPanel pan = myPanels[i];
	
					if (pan.getDevice().equals(device)) {
						System.out.println("checking " + pan.getDevice().getURI()
								+ " with " + device.getURI());
						pan.setText("\n\n");
						pan.setText("Message: Temperature has been set");
	
					}
				}
			}
			else{
				for (int i = 0; i < myPanels.length; i++) {
					MyJPanel pan = myPanels[i];
	
					if (pan.getDevice().equals(device)) {
						System.out.println("checking " + pan.getDevice().getURI()
								+ " with " + device.getURI());
						pan.setText("\n\n");
						pan.setText("Message: Error! The value must be between -18 and 20.");
	
					}
				}
			}
		}
		catch (Exception e){
			for (int i = 0; i < myPanels.length; i++) {
				MyJPanel pan = myPanels[i];

				if (pan.getDevice().equals(device)) {
					System.out.println("checking " + pan.getDevice().getURI()
							+ " with " + device.getURI());
					pan.setText("\n\n");
					pan.setText("Message: Error! The value must be between -18 and 20!");

				}
			}			
		}
	}

	private void getInfoActionPerformed(java.awt.event.ActionEvent evt, Device device) {
		String uri = device.getURI();

		boolean getInfo = FoodManagementClient.getDeviceInfo(uri);
		if (getInfo) {
			for (int i = 0; i < myPanels.length; i++) {
				MyJPanel pan = myPanels[i];
				Vector values = FoodManagementClient.getValues();
				if (pan.getDevice().equals(device)) {
					System.out.println("checking " + pan.getDevice().getURI()
							+ " with " + device.getURI());
					//pan.setText("\n\n Message: " + values.get(0) + " \n " + values.get(1) + " \n " + values.get(2));
					if (((String)values.get(2)).equals("[100]"))
						pan.setText("The Refrigerator is on. Temperature is "+((String)values.get(0)).substring(1, ((String)values.get(0)).length()-1) + ".");
					else
						pan.setText("\n\n Message: The Refrigerator is off.");
					
					values.clear();

				}
			}
		}
	}

	/* ComboBox solution */
	private void getFoodItems(Device device) {
		String uri = device.getURI();
		
		boolean getInfo = FoodManagementClient.getFoodItems(uri);
		if (getInfo) {
			for (int i = 0; i < myPanels.length; i++) {
				MyJPanel pan = myPanels[i];
				Vector items = FoodManagementClient.getItems();
				Vector quantities = FoodManagementClient.getQuantities();
				this.itemList = new String[items.size()];
				this.itemList = (String [])items.toArray(new String[items.size()]); 
				this.itemNum = new String[quantities.size()];
				this.itemNum = (String [])quantities.toArray(new String[items.size()]); 
				items.clear();
				quantities.clear();
			}
			foodItemComboBox.removeAllItems();
			
			for (int j=0;j<itemList.length;j++){
				this.foodItemComboBox.addItem((String)itemList[j]);
			}
			
			itemNumComboBox.removeAllItems();
			for (int j=0;j<itemNum.length;j++){
				this.itemNumComboBox.addItem((String)itemNum[j]);
			}
			//validate
		}
		else{
			this.itemList = new String[1];
			this.itemNum = new String[1];
			this.itemList[0] = "item";
			this.itemNum[0] = "0";

			if (this.foodItemComboBox != null){
				this.foodItemComboBox.removeAllItems();
				this.foodItemComboBox.addItem((String)itemList[0]);
			}
			
			if (this.itemNumComboBox != null){
				this.itemNumComboBox.removeAllItems();
				this.itemNumComboBox.addItem((String)itemNum[0]);
			}
		}
	}
}
