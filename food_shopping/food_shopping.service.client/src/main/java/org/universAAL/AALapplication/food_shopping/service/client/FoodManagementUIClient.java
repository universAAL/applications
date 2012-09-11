package org.universAAL.AALapplication.food_shopping.service.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.universAAL.ontology.Shopping.FoodItem;
import org.universAAL.ontology.Shopping.Refrigerator;
import org.universAAL.ontology.phThing.Device;

public class FoodManagementUIClient extends javax.swing.JPanel {
	private static MyJPanel[] myPanels;
	private JTextField tempTextField = null;
	private JTextField addItemTextField = null;
	private JTextField updateItemTextField = null;
	private JTextField removeItemTextField = null;
	private JTextField quantityTextField = null;
	private JTextField sizeTextField = null;
	private JTextField companyTextField = null;
	private JTextField codeTextField = null;
	private JTextField tagTextField = null;
	private JTextField insDateTextField = null;
	private JTextField expDateTextField = null;
	private AbstractAction Off;
	private AbstractAction On;
	public JFrame frame;
    private static JComboBox foodItemComboBox, itemNumComboBox, sizeComboBox, companyComboBox, codeComboBox, tagComboBox, insComboBox, expComboBox;
    private JButton applyButton = new JButton("Edit");
    private static String itemList[], itemNum[], sizeList[], companyList[], codeList[], tagList[], insList[], expList[];
    private static JTabbedPane tabbedPane = new JTabbedPane();
    private static ShoppingList sl;
    private static RefrigeratorList rl;
    
    //
    private javax.swing.JPanel controls = new JPanel(new BorderLayout());
    
    public static void setTabbedFocus(int index){
    	tabbedPane.setSelectedIndex(index);
    }
    
    public void initDeviceFoodItems(Device device) {
		itemList = new String[1];
		itemNum = new String[1];
		sizeList = new String[1];
		companyList = new String[1];
		//codeList = new String[1];
		tagList = new String[1];
		insList = new String[1];
		expList = new String[1];
		itemList[0] = "item";
		itemNum[0] = "0";
		sizeList[0] = "size";
		companyList[0] = "company";
		tagList[0] = "tag";
		insList[0] = "date";
		expList[0] = "date";
    	foodItemComboBox = new JComboBox(itemList);
    	itemNumComboBox = new JComboBox(itemNum);
    	sizeComboBox = new JComboBox(sizeList);
    	companyComboBox = new JComboBox(companyList);
    	tagComboBox = new JComboBox(tagList);
    	insComboBox = new JComboBox(insList);
    	expComboBox = new JComboBox(expList);

    	getFoodItems(device);
    	foodItemComboBox = new JComboBox(itemList);
    	itemNumComboBox = new JComboBox(itemNum);
    	sizeComboBox = new JComboBox(sizeList);
    	companyComboBox = new JComboBox(companyList);
    	tagComboBox = new JComboBox(tagList);
    	insComboBox = new JComboBox(insList);
    	expComboBox = new JComboBox(expList);
    }
    
    // create the GUI
	public void start(Device[] d) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e){}
		frame = new JFrame();
        this.tabbedPane = new JTabbedPane();
        frame.setPreferredSize(new Dimension(800,500));
        frame.setMinimumSize(new Dimension(800,500));
		frame.setTitle("Food Manager Service");
		frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().add(this.tabbedPane, java.awt.BorderLayout.CENTER);

		/* First Tab */
		javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
		jPanel1.setLayout(new java.awt.GridLayout(1, 8));
		//jPanel1.setSize(200, 500);

		myPanels = new MyJPanel[d.length];
		for (int i = 0; i < d.length; i++) {
			MyJPanel panel1 = createPanel(d[i]);
			myPanels[i] = panel1;
			//panel1.setMinimumSize(new Dimension(800, 350));
			panel1.setVisible(true);
			panel1.validate();
			jPanel1.add(panel1);
		}
		
		this.tabbedPane.insertTab("Food Repository",null,jPanel1,"",0);
        /* End of First Tab */
		
        sl.setFrame(frame);
        /* Second Tab */
        javax.swing.JPanel jPanel2 = new javax.swing.JPanel();
        jPanel2 = sl.createShoppingList();
        jPanel2.setOpaque(true);
        jPanel2.setBackground(Color.WHITE);	
		this.tabbedPane.insertTab("Create Shopping List",null,jPanel2,"",1);
        /* End of Second Tab */

        /* Third Tab */
        javax.swing.JPanel jPanel3 = new javax.swing.JPanel();
        jPanel3 = sl.browseShoppingList();
        jPanel3.setOpaque(true);
        jPanel3.setBackground(Color.WHITE);	
		this.tabbedPane.insertTab("Browse Shopping List",null,jPanel3,"",2);
        /* End of Third Tab */

        /* Fourth Tab */
        javax.swing.JPanel jPanel4 = new javax.swing.JPanel();
        jPanel4 = sl.editShoppingList();
        jPanel4.setOpaque(true);
        jPanel4.setBackground(Color.WHITE);	
		this.tabbedPane.insertTab("Edit Shopping List",null,jPanel4,"",3);
        /* End of Third Tab */
        frame.setJMenuBar(sl.createMenuBar());

        ChangeListener changeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
            	JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
            	int index = sourceTabbedPane.getSelectedIndex();
            	System.out.println("Tab changed to: " + sourceTabbedPane.getTitleAt(index));
            	if (index==1){
                    ShoppingList.list2Model.clear();
                    ShoppingList.nameTextField.setText("");
                    ShoppingList.dateTextField.setText("");
            	}
            	if (index==2){
                    org.universAAL.ontology.Shopping.ShoppingList[] shoppinglists = FoodManagementClient.getShoppingLists();
                    ShoppingList.list3Model.clear();
                    ShoppingList.list4Model.clear();
                    if (shoppinglists != null){
                    	for (int i=shoppinglists.length-1; i>=0; i--){
                    		String item = shoppinglists[i].getName();
                    		ShoppingList.list3Model.addElement(item);
                    	}
                    }
            	}
            	if (index==3){
                    org.universAAL.ontology.Shopping.ShoppingList[] shoppinglists = FoodManagementClient.getShoppingLists();
                    ShoppingList.list5Model.clear();
                    ShoppingList.list6Model.clear();
                    if (shoppinglists != null){
                    	for (int i=shoppinglists.length-1; i>=0; i--){
                    		String item = shoppinglists[i].getName();
                    		ShoppingList.list5Model.addElement(item);
                    	}
                    }
            	}
            }
        };
        tabbedPane.addChangeListener(changeListener);

        
		frame.pack();
		frame.validate();
		frame.setVisible(true);
		
        //ImageIcon icon = createImageIcon("images/middle.gif");
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

		//initDeviceFoodItems(device);
		
		jPanel1.setLayout(new java.awt.BorderLayout());
		if (device instanceof Refrigerator) {
			jLabel1.setText("Food Repository");
			jLabel1.setFont(new Font(Font.MONOSPACED,Font.BOLD,14));
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

		
/*  
		// Panel with textfields for add-remove-update
		addItemPanel.setLayout(new java.awt.GridLayout(0,2));
        addItemPanel.add(new javax.swing.JLabel("\n"));
        addItemPanel.add(new javax.swing.JLabel("\n"));
		addItemPanel.add(new javax.swing.JLabel("Food Item:"));
        addItemTextField = new JTextField(10);
        addItemPanel.add(addItemTextField);
        addItemPanel.add(new javax.swing.JLabel("Size:"));
        sizeTextField = new JTextField(5);
        addItemPanel.add(sizeTextField);
        addItemPanel.add(new javax.swing.JLabel("Company:"));
        companyTextField = new JTextField(5);
        addItemPanel.add(companyTextField);
        addItemPanel.add(new javax.swing.JLabel("Quantity:"));
        quantityTextField = new JTextField(5);
        addItemPanel.add(quantityTextField);
        addItemPanel.add(new javax.swing.JLabel("Insertion Date:"));
        insDateTextField = new JTextField(5);
        addItemPanel.add(insDateTextField);
        addItemPanel.add(new javax.swing.JLabel("Expiration Date:"));
        expDateTextField = new JTextField(5);
        addItemPanel.add(expDateTextField);
        //addItemPanel.add(new javax.swing.JLabel("Code:"));
        //codeTextField = new JTextField(5);
        //addItemPanel.add(codeTextField);
        addItemPanel.add(new javax.swing.JLabel("Tag:"));
        tagTextField = new JTextField(5);
        addItemPanel.add(tagTextField);
        
        addItemPanel.add(new javax.swing.JLabel("\n"));
        addItemPanel.add(new javax.swing.JLabel("\n"));
      
        // Panel with add-remove-update buttons
        updateItemPanel.setLayout(new java.awt.GridLayout(0,3));
        addItemButton.setText("Add");
        addItemButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addfoodItemsActionPerformed(evt, device, addItemTextField.getText(), quantityTextField.getText(), sizeTextField.getText(), companyTextField.getText(), tagTextField.getText(), insDateTextField.getText(), expDateTextField.getText());
			}
		});
        
        updateItemButton.setText("Update");
        updateItemButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				updatefoodItemsActionPerformed(evt, device, addItemTextField.getText(), quantityTextField.getText(), sizeTextField.getText(), companyTextField.getText(), tagTextField.getText(), insDateTextField.getText(), expDateTextField.getText());
			}
		});
        
        removeItemButton.setText("Remove");
        removeItemButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				removefoodItemsActionPerformed(evt, device, tagTextField.getText());
			}
		});
        
        updateItemPanel.add(addItemButton);
        updateItemPanel.add(updateItemButton);
        updateItemPanel.add(removeItemButton);
        updateItemPanel.add(new javax.swing.JLabel("\n"));
        updateItemPanel.add(new javax.swing.JLabel("\n"));
        updateItemPanel.add(new javax.swing.JLabel("\n"));

        addUpdateRemoveItemPanel.setLayout(new java.awt.BorderLayout());
        //addUpdateRemoveItemPanel.add(addItemPanel, java.awt.BorderLayout.NORTH);
        addUpdateRemoveItemPanel.add(updateItemPanel, java.awt.BorderLayout.CENTER);
*/        
		
        /* ComboBox solution 

        controls.add(new javax.swing.JLabel("Food Items:"));
        controls.add(new javax.swing.JLabel(" "));
        controls.add(foodItemComboBox);
        controls.add(sizeComboBox);
        controls.add(companyComboBox);
        controls.add(itemNumComboBox);
        controls.add(insComboBox);
        controls.add(expComboBox);
        controls.add(tagComboBox);
        controls.add(applyButton);

        this.applyButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt)
            {
            	editfoodItemsActionPerformed(evt, device, (String)foodItemComboBox.getSelectedItem(), (String)itemNumComboBox.getSelectedItem(), (String)sizeComboBox.getSelectedItem(), (String)companyComboBox.getSelectedItem(), (String)tagComboBox.getSelectedItem(), (String)insComboBox.getSelectedItem(), (String)expComboBox.getSelectedItem());
            	
            	//getfoodItemsActionPerformed(evt, device);
            }
        });
    	this.controls.add(this.applyButton);
		/* End of Upper Components */
		
        
        this.controls = rl.createRefrigeratorList(device.getURI()); 
        
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
		sl = new ShoppingList();
		rl = new RefrigeratorList();
		initGUI();
		start(d);
	}

	private void initGUI() {
		try {
			//setPreferredSize(new Dimension(800, 350));
			//setMinimumSize(new Dimension(800, 350));

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
					pan.setText("\n\nMessage: Food Repository is on.");

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
					pan.setText("\n\n Message: Food Repository is off.");

				}
			}
		}		
	}

	private void editfoodItemsActionPerformed(java.awt.event.ActionEvent evt, Device device, String item, String quantity, String size, String company, String tagID, String ins, String exp) {
		this.addItemTextField.setText(item);
		this.quantityTextField.setText(quantity);
		this.sizeTextField.setText(size);
		this.companyTextField.setText(company);
		this.tagTextField.setText(tagID);
		this.insDateTextField.setText(ins);
		this.expDateTextField.setText(exp);
	}
/*
	private void addfoodItemsActionPerformed(java.awt.event.ActionEvent evt, String uri, String itemName, String itemNum, String size, String company, String tagID, String insDate, String expDate) {
		double quantity = Double.parseDouble(itemNum);

		boolean addFoodItems = FoodManagementClient.addFoodItems(uri,itemName,quantity,size,company, tagID, insDate, expDate);
		if (addFoodItems) {
			for (int i = 0; i < myPanels.length; i++) {
				MyJPanel pan = myPanels[i];

				if (pan.getDevice().equals(uri)) {
					System.out.println("checking " + pan.getDevice().getURI()
							+ " with " + uri);
					pan.setText("\n\n");
					pan.setText("Message: New food item added.");

				}
			}
			rl.updateRefrigeratorList(uri);
		}
	}


	private void updatefoodItemsActionPerformed(java.awt.event.ActionEvent evt, String uri, String itemName, String itemNum, String size, String company, String tagID, String insDate, String expDate) {
		double quantity = Double.parseDouble(itemNum);

		boolean updateFoodItems = FoodManagementClient.updateFoodItems(uri,itemName,quantity,size,company,tagID,insDate,expDate);
		if (updateFoodItems) {
			for (int i = 0; i < myPanels.length; i++) {
				MyJPanel pan = myPanels[i];

				if (pan.getDevice().equals(uri)) {
					System.out.println("checking " + pan.getDevice().getURI()
							+ " with " + uri);
					pan.setText("\n\n");
					pan.setText("Message: New food item updated.");

				}
			}
			rl.updateRefrigeratorList(uri);
		}
	}
*/
/*	
	private void updatefoodItemsActionPerformed(java.awt.event.ActionEvent evt, Device device, String itemName, String itemNum, String size, String company, String tagID, String insDate, String expDate) {
		String uri = device.getURI();
		double quantity = Double.parseDouble(itemNum);

		boolean updateFoodItems = FoodManagementClient.updateFoodItems(uri,itemName,quantity,size,company,tagID,insDate,expDate);
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

	private void removefoodItemsActionPerformed(java.awt.event.ActionEvent evt, Device device, String tagName) {
		String uri = device.getURI();

		boolean removeFoodItems = FoodManagementClient.removeFoodItems(uri,tagName);
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
*/	
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
						pan.setText("Food Repository is on. Temperature is "+((String)values.get(0)).substring(1, ((String)values.get(0)).length()-1) + ".");
					else
						pan.setText("\n\n Message: Food Repository is off.");
					
					values.clear();

				}
			}
		}
	}

/* OLD implementation with previous version of Context Event
 * 
 	public static void getItems(Device device){
		rl.updateRefrigeratorList(device.getURI());
	}
*/
	public static void getItems(FoodItem[] lfi){
		rl.updateRefrigeratorList(lfi);
	}

	private static void getFoodItems(Device device) {
		String uri = device.getURI();
		
		boolean getInfo = FoodManagementClient.getFoodItems(uri);
		if (getInfo) {
			for (int i = 0; i < myPanels.length; i++) {
				MyJPanel pan = myPanels[i];
				Vector items = FoodManagementClient.getItems();
				Vector quantities = FoodManagementClient.getQuantities();
				Vector sizes = FoodManagementClient.getSizes();
				Vector companies = FoodManagementClient.getCompanies();
				Vector tags = FoodManagementClient.getTags();
				Vector ins = FoodManagementClient.getIns();
				Vector exps = FoodManagementClient.getExps();
				itemList = new String[items.size()];
				itemList = (String [])items.toArray(new String[items.size()]); 
				itemNum = new String[quantities.size()];
				itemNum = (String [])quantities.toArray(new String[items.size()]); 
				sizeList = new String[sizes.size()];
				sizeList = (String [])sizes.toArray(new String[items.size()]); 
				companyList = new String[companies.size()];
				companyList = (String [])companies.toArray(new String[items.size()]); 
				tagList = new String[tags.size()];
				tagList = (String [])tags.toArray(new String[items.size()]); 
				insList = new String[ins.size()];
				insList = (String [])ins.toArray(new String[items.size()]); 
				expList = new String[exps.size()];
				expList = (String [])exps.toArray(new String[items.size()]); 
			}
			//System.out.println("taglist len="+tagList.length);
			//System.out.println("inslist len="+insList.length);
			foodItemComboBox.removeAllItems();
			for (int j=0;j<itemList.length;j++)
				foodItemComboBox.addItem((String)itemList[j]);
			itemNumComboBox.removeAllItems();
			for (int j=0;j<itemNum.length;j++)
				itemNumComboBox.addItem((String)itemNum[j]);
			sizeComboBox.removeAllItems();
			for (int j=0;j<sizeList.length;j++)
				sizeComboBox.addItem((String)sizeList[j]);
			companyComboBox.removeAllItems();
			for (int j=0;j<companyList.length;j++)
				companyComboBox.addItem((String)companyList[j]);
			tagComboBox.removeAllItems();
			for (int j=0;j<tagList.length;j++)
				tagComboBox.addItem((String)tagList[j]);
			insComboBox.removeAllItems();
			for (int j=0;j<insList.length;j++)
				insComboBox.addItem((String)insList[j]);
			expComboBox.removeAllItems();
			for (int j=0;j<expList.length;j++)
				expComboBox.addItem((String)expList[j]);
		}
		else{
			itemList = new String[1];
			itemNum = new String[1];
			sizeList = new String[1];
			companyList = new String[1];
			tagList = new String[1];
			insList = new String[1];
			expList = new String[1];

			itemList[0] = "item";
			itemNum[0] = "0";
			sizeList[0] = "size";
			companyList[0] = "company";
			tagList[0] = "tag";
			insList[0] = "date";
			expList[0] = "date";
			if (foodItemComboBox != null){
				foodItemComboBox.removeAllItems();
				foodItemComboBox.addItem((String)itemList[0]);
			}
			if (itemNumComboBox != null){
				itemNumComboBox.removeAllItems();
				itemNumComboBox.addItem((String)itemNum[0]);
			}
			if (sizeComboBox != null){
				sizeComboBox.removeAllItems();
				sizeComboBox.addItem((String)sizeList[0]);
			}
			if (companyComboBox != null){
				companyComboBox.removeAllItems();
				companyComboBox.addItem((String)companyList[0]);
			}
			if (tagComboBox != null){
				tagComboBox.removeAllItems();
				tagComboBox.addItem((String)tagList[0]);
			}
			if (insComboBox != null){
				insComboBox.removeAllItems();
				insComboBox.addItem((String)insList[0]);
			}
			if (expComboBox != null){
				expComboBox.removeAllItems();
				expComboBox.addItem((String)expList[0]);
			}
			
		}
	}
}
