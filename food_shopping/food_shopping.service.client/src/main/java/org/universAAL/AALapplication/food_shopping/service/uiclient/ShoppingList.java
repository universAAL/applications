/*****************************************************************************************
 * Copyright 2012 CERTH, http://www.certh.gr - Center for Research and Technology Hellas
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *****************************************************************************************/

package org.universAAL.AALapplication.food_shopping.service.uiclient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import javax.swing.border.Border;

import org.universAAL.ontology.Shopping.FoodItem;

/**
 * @author dimokas
 * 
 */
public class ShoppingList extends JPanel {
    public ListTransferHandler lh;
    public ListTransferHandler lh2;
    public static JPanel createpanel = new JPanel(new BorderLayout());
    public static JPanel browsepanel = new JPanel(new BorderLayout());
    public static JPanel editpanel = new JPanel(new BorderLayout());
    public static JTextField nameTextField = null;
    public static JTextField dateTextField = new JTextField(10);
    public static JTextField editNameTextField = null;
    public static JTextField editDateTextField = new JTextField(10);
    public static DefaultListModel list2Model = null;
    public static DefaultListModel list3Model = null;
    public static DefaultListModel list4Model = null;
    public static DefaultListModel list5Model = null;
    public static DefaultListModel list6Model = null;
    public static DefaultListModel list7Model = null;
    private JList list3 = null;
    private JList list5 = null;
    private JList list6 = null;
    private JList list7 = null;
    private JFrame frame;
    private int currentListID = 0;
    
    public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public ShoppingList() {
        super(new BorderLayout());
    }
    
    public JPanel createShoppingList() {
        lh = new ListTransferHandler();
		FoodItem[] fi = FoodManagementClient.getShoppingItems();
        
        JPanel panel = new JPanel(new FlowLayout());
        JPanel pan1 = new JPanel(new BorderLayout());
        JPanel pan2 = new JPanel(new BorderLayout());
        panel.setBackground(Color.white);
        DefaultListModel list1Model = new DefaultListModel();
        if (fi != null){
        	for (int i=fi.length-1; i>=0; i--){
        		String item = fi[i].getCode()+", "+fi[i].getName()+", "+fi[i].getSize()+", "+fi[i].getCompany();
        		list1Model.addElement(item);
        	}
        }
        JList list1 = new JList(list1Model);
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane sp1 = new JScrollPane(list1);
        sp1.setPreferredSize(new Dimension(200,220));
        list1.setDragEnabled(false);
        //list1.setDropMode(DropMode.USE_SELECTION);
        //list1.setTransferHandler(lh);
        //list1.setDropMode(DropMode.ON_OR_INSERT);
        //setMappings(list1);
		Border blueline = BorderFactory.createLineBorder(Color.blue);
		pan1.setBorder(javax.swing.BorderFactory.createTitledBorder(blueline,"Food items"));
        pan1.add(sp1, BorderLayout.CENTER);
        pan1.setBackground(Color.white);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,pan1, pan2);
        splitPane.setOneTouchExpandable(true);

        list2Model = new DefaultListModel();
        JList list2 = new JList(list2Model);
        list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);        
        list2.setDragEnabled(false);
        JScrollPane sp2 = new JScrollPane(list2);
        sp2.setPreferredSize(new Dimension(200,220));
        list2.setTransferHandler(lh);
        list2.setDropMode(DropMode.INSERT);
        setMappings(list2);
        pan2.add(sp2, BorderLayout.CENTER);
		pan2.setBorder(javax.swing.BorderFactory.createTitledBorder(blueline,"Shopping List has"));
		pan2.setBackground(Color.white);

		javax.swing.JLabel alabel = new javax.swing.JLabel("");
		alabel.setPreferredSize(new Dimension(20,20));
		panel.add(new javax.swing.JLabel(""));
        panel.add(pan1);
        panel.add(alabel);
		panel.add(pan2);
        panel.add(new javax.swing.JLabel(""));
        
		javax.swing.JLabel heading = new javax.swing.JLabel();
		heading.setText("Create Shopping List");
		heading.setFont(new Font(Font.MONOSPACED,Font.BOLD,14));
		heading.setForeground(Color.BLUE);
		heading.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		heading.setVerticalAlignment(javax.swing.SwingConstants.CENTER);

        JPanel tail = new JPanel(new GridLayout(6,1));
        tail.setBackground(Color.white);

		// Panel for Shopping List Name textfield 		
        JPanel shoppingListName = new JPanel(new GridLayout(1,6));
        shoppingListName.setBackground(Color.white);
		this.nameTextField = new JTextField(10);
		javax.swing.JLabel textFieldLabel = new javax.swing.JLabel("Shopping List Name: ");
		textFieldLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		shoppingListName.add(new javax.swing.JLabel(""));
		shoppingListName.add(new javax.swing.JLabel(""));
		shoppingListName.add(textFieldLabel);
		shoppingListName.add(this.nameTextField);
		shoppingListName.add(new javax.swing.JLabel(""));
		shoppingListName.add(new javax.swing.JLabel(""));

		// Panel for Shopping List Date textfield 		
        JPanel shoppingListDate = new JPanel(new GridLayout(1,6));
        shoppingListDate.setBackground(Color.white);
		javax.swing.JLabel dateFieldLabel = new javax.swing.JLabel("Shopping List Date: ");
		dateFieldLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        JPanel selectDate = new JPanel(new GridLayout(1,2));
        selectDate.setBackground(Color.white);
		JButton select = new JButton("select");
		selectDate.add(new javax.swing.JLabel(""));
		selectDate.add(select);
		shoppingListDate.add(new javax.swing.JLabel(""));
		shoppingListDate.add(new javax.swing.JLabel(""));
		shoppingListDate.add(dateFieldLabel);
		shoppingListDate.add(this.dateTextField);
        shoppingListDate.add(selectDate);
		shoppingListDate.add(new javax.swing.JLabel(""));

        select.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
            	dateTextField.setText(new DatePicker(frame).setPickedDate());
            }
        });
        
        // Panel for save button
        JPanel downCenterPanel = new JPanel(new GridLayout(1,5));
        downCenterPanel.setBackground(Color.white);
		javax.swing.JButton saveButton = new javax.swing.JButton();
		saveButton.setText("Save");
		saveButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if (nameTextField.getText().length()>0){
					String[] shoppingItems = new String[list2Model.size()]; 
					for (int i=0; i<list2Model.size(); i++){
						shoppingItems[i] = (String)list2Model.getElementAt(i);
					}
					addShoppingListActionPerformed(evt, nameTextField.getText(), dateTextField.getText(), shoppingItems);
				}
			}
		});
		downCenterPanel.add(new javax.swing.JLabel(""));
		downCenterPanel.add(new javax.swing.JLabel(""));
		downCenterPanel.add(saveButton);
		downCenterPanel.add(new javax.swing.JLabel(""));
		downCenterPanel.add(new javax.swing.JLabel(""));
		
		// Add the above two panels in tail panel
		tail.add(new javax.swing.JLabel(""));
		tail.add(shoppingListName);
		tail.add(shoppingListDate);
		tail.add(new javax.swing.JLabel(""));
		tail.add(downCenterPanel);
		tail.add(new javax.swing.JLabel(""));
		
		createpanel.setBackground(Color.WHITE);
        createpanel.setPreferredSize(new Dimension(800, 350));
		createpanel.add(heading, java.awt.BorderLayout.NORTH);
        createpanel.add(panel, java.awt.BorderLayout.CENTER);
        createpanel.add(tail, java.awt.BorderLayout.SOUTH);
        
        return createpanel;
    }

    public JPanel browseShoppingList() {
        org.universAAL.ontology.Shopping.ShoppingList[] shoppinglists = FoodManagementClient.getShoppingLists();
        
        //JPanel panel = new JPanel(new GridLayout(1, 5));
        JPanel panel = new JPanel(new FlowLayout());
        JPanel pan1 = new JPanel(new BorderLayout());
        JPanel pan2 = new JPanel(new BorderLayout());
        panel.setBackground(Color.white);
        list3Model = new DefaultListModel();
        if (shoppinglists != null){
        	for (int i=shoppinglists.length-1; i>=0; i--){
        		String item = shoppinglists[i].getName();
        		list3Model.addElement(item);
        	}
        }
        list3 = new JList(list3Model);
        list3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane sp1 = new JScrollPane(list3);
        sp1.setPreferredSize(new Dimension(200,250));
        list3.setDragEnabled(false);
		Border blueline = BorderFactory.createLineBorder(Color.blue);
		pan1.setBorder(javax.swing.BorderFactory.createTitledBorder(blueline,"Shopping Lists"));
        pan1.add(sp1, BorderLayout.CENTER);
        pan1.setBackground(Color.white);

        JPanel intermediate = new JPanel(new BorderLayout());
        javax.swing.JButton selectButton = new javax.swing.JButton();
		selectButton.setPreferredSize(new Dimension(50,30));
		selectButton.setText(">>");
		selectButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				int index = list3.getSelectedIndex();
				if (index>=0){
					String element = (String)list3Model.get(index);
					getShoppingListItemsActionPerformed(evt, element);
				}
			}
		});
        intermediate.add(selectButton, java.awt.BorderLayout.CENTER);
		
        //JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,pan1, pan2);
        //splitPane.setOneTouchExpandable(true);

        list4Model = new DefaultListModel();
        JList list2 = new JList(list4Model);
        list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);        
        //list2.setDragEnabled(false);
        JScrollPane sp2 = new JScrollPane(list2);
        sp2.setPreferredSize(new Dimension(200,250));
        //list2.setTransferHandler(lh2);
        //list2.setDropMode(DropMode.INSERT);
        //setMappings(list2);
        pan2.add(sp2, BorderLayout.CENTER);
		pan2.setBorder(javax.swing.BorderFactory.createTitledBorder(blueline,"Shopping List has"));
		pan2.setBackground(Color.white);

        //panel.add(new javax.swing.JLabel(""),BorderLayout.CENTER);
        panel.add(pan1);
        panel.add(intermediate);
		panel.add(pan2);
       // panel.add(new javax.swing.JLabel(""),BorderLayout.CENTER);
        
		javax.swing.JLabel heading = new javax.swing.JLabel();
		heading.setText("Browse Shopping List");
		heading.setFont(new Font(Font.MONOSPACED,Font.BOLD,14));
		heading.setForeground(Color.BLUE);
		heading.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		heading.setVerticalAlignment(javax.swing.SwingConstants.CENTER);

        JPanel tail = new JPanel(new GridLayout(3,1));
        tail.setBackground(Color.white);

        // Panel for remove button
        //JPanel downCenterPanel = new JPanel(new GridLayout(1,5));
        JPanel downCenterPanel = new JPanel(new FlowLayout());
        downCenterPanel.setBackground(Color.white);
		javax.swing.JButton removeButton = new javax.swing.JButton();
		removeButton.setText("Remove");
		removeButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				System.out.println("Remove");
				int index = list3.getSelectedIndex();
				if (index>=0){
					String element = (String)list3Model.get(index);
					removeShoppingListActionPerformed(evt, element);
				}
			}
		});
		javax.swing.JButton printButton = new javax.swing.JButton();
		printButton.setText("Print");
		printButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				System.out.println("Print");
				int index = list3.getSelectedIndex();
				if (index>=0){
					String element = (String)list3Model.get(index);
				}
			}
		});
		javax.swing.JButton sendButton = new javax.swing.JButton();
		sendButton.setText("Send");
		sendButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				System.out.println("Send");
				int index = list3.getSelectedIndex();
				if (index>=0){
					String element = (String)list3Model.get(index);
				}
			}
		});

		downCenterPanel.add(new javax.swing.JLabel(""));
		downCenterPanel.add(printButton);
		downCenterPanel.add(removeButton);
		downCenterPanel.add(sendButton);
		downCenterPanel.add(new javax.swing.JLabel(""));
		
		// Add the above two panels in tail panel
		tail.add(new javax.swing.JLabel(""));
		tail.add(downCenterPanel);
		tail.add(new javax.swing.JLabel(""));
		
		browsepanel.setBackground(Color.WHITE);
		browsepanel.setPreferredSize(new Dimension(800, 350));
		browsepanel.add(heading, java.awt.BorderLayout.NORTH);
		browsepanel.add(panel, java.awt.BorderLayout.CENTER);
		browsepanel.add(tail, java.awt.BorderLayout.SOUTH);
        
        return browsepanel;
    }

    public JPanel editShoppingList() {
        org.universAAL.ontology.Shopping.ShoppingList[] shoppinglists = FoodManagementClient.getShoppingLists();
		FoodItem[] fi = FoodManagementClient.getShoppingItems();
        lh2 = new ListTransferHandler();
		
        JPanel panel = new JPanel(new FlowLayout());
        JPanel pan1 = new JPanel(new BorderLayout());
        JPanel pan2 = new JPanel(new BorderLayout());
        panel.setBackground(Color.white);
        list5Model = new DefaultListModel();
        if (shoppinglists != null){
        	for (int i=shoppinglists.length-1; i>=0; i--){
        		String item = shoppinglists[i].getName();
        		list5Model.addElement(item);
        	}
        }
        list5 = new JList(list5Model);
        list5.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane sp1 = new JScrollPane(list5);
        sp1.setPreferredSize(new Dimension(200,220));
        list5.setDragEnabled(false);
		Border blueline = BorderFactory.createLineBorder(Color.blue);
		pan1.setBorder(javax.swing.BorderFactory.createTitledBorder(blueline,"Shopping Lists"));
        pan1.add(sp1, BorderLayout.CENTER);
        pan1.setBackground(Color.white);

        JPanel intermediate = new JPanel(new BorderLayout());
        javax.swing.JButton selectEditButton = new javax.swing.JButton();
		selectEditButton.setPreferredSize(new Dimension(50,30));
		selectEditButton.setText(">>");
		selectEditButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				int index = list5.getSelectedIndex();
				if (index>=0){
					String element = (String)list5Model.get(index);
					System.out.println("element="+element);
					getShoppingListItems2ActionPerformed(evt, element);
				}
			}
		});
        intermediate.add(selectEditButton, java.awt.BorderLayout.CENTER);
		
        list6Model = new DefaultListModel();
        list6 = new JList(list6Model);
        list6.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);        
        list6.setDragEnabled(false);
        JScrollPane sp2 = new JScrollPane(list6);
        sp2.setPreferredSize(new Dimension(200,220));
        list6.setTransferHandler(lh2);
        list6.setDropMode(DropMode.INSERT);
        setMappings(list6);
        pan2.add(sp2, BorderLayout.CENTER);
		pan2.setBorder(javax.swing.BorderFactory.createTitledBorder(blueline,"Shopping List has"));
		pan2.setBackground(Color.white);

        javax.swing.JLabel separatorLabel = new javax.swing.JLabel("");
        separatorLabel.setPreferredSize(new Dimension(50,30));

        JPanel pan3 = new JPanel(new BorderLayout());
        list7Model = new DefaultListModel();
        if (fi != null){
        	for (int i=fi.length-1; i>=0; i--){
        		String item = fi[i].getCode()+", "+fi[i].getName()+", "+fi[i].getSize()+", "+fi[i].getCompany();
        		list7Model.addElement(item);
        	}
        }
        list7 = new JList(list7Model);
        list7.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane sp3 = new JScrollPane(list7);
        sp3.setPreferredSize(new Dimension(200,220));
        list7.setDragEnabled(false);
		pan3.setBorder(javax.swing.BorderFactory.createTitledBorder(blueline,"Food items"));
        pan3.add(sp3, BorderLayout.CENTER);
        pan3.setBackground(Color.white);
        
		panel.add(pan1);
        panel.add(intermediate);
		panel.add(pan2);
        panel.add(separatorLabel);
		panel.add(pan3);
        
		javax.swing.JLabel heading = new javax.swing.JLabel();
		heading.setText("Edit Shopping List");
		heading.setFont(new Font(Font.MONOSPACED,Font.BOLD,14));
		heading.setForeground(Color.BLUE);
		heading.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		heading.setVerticalAlignment(javax.swing.SwingConstants.CENTER);

        JPanel editTail = new JPanel(new GridLayout(6,1));
        editTail.setBackground(Color.white);

		// Panel for Shopping List Name textfield 		
        JPanel editShoppingListName = new JPanel(new GridLayout(1,6));
        editShoppingListName.setBackground(Color.white);
		this.editNameTextField = new JTextField(10);
		javax.swing.JLabel editTextFieldLabel = new javax.swing.JLabel("Shopping List Name: ");
		editTextFieldLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		editShoppingListName.add(new javax.swing.JLabel(""));
		editShoppingListName.add(new javax.swing.JLabel(""));
		editShoppingListName.add(editTextFieldLabel);
		editShoppingListName.add(this.editNameTextField);
		editShoppingListName.add(new javax.swing.JLabel(""));
		editShoppingListName.add(new javax.swing.JLabel(""));

		// Panel for Shopping List Date textfield 		
        JPanel editShoppingListDate = new JPanel(new GridLayout(1,6));
        editShoppingListDate.setBackground(Color.white);
		javax.swing.JLabel editDateFieldLabel = new javax.swing.JLabel("Shopping List Date: ");
		editDateFieldLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        JPanel editSelectDate = new JPanel(new GridLayout(1,2));
        editSelectDate.setBackground(Color.white);
		JButton editSelect = new JButton("select");
		
		editSelectDate.add(new javax.swing.JLabel(""));
		editSelectDate.add(editSelect);
		editShoppingListDate.add(new javax.swing.JLabel(""));
		editShoppingListDate.add(new javax.swing.JLabel(""));
		editShoppingListDate.add(editDateFieldLabel);
		editShoppingListDate.add(this.editDateTextField);
		editShoppingListDate.add(editSelectDate);
		editShoppingListDate.add(new javax.swing.JLabel(""));

		editSelect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
            	editDateTextField.setText(new DatePicker(frame).setPickedDate());
            }
        });

        
        // Panel for update button
        JPanel downCenterPanel = new JPanel(new GridLayout(1,5));
        //JPanel downCenterPanel = new JPanel(new FlowLayout());
        downCenterPanel.setBackground(Color.white);
		javax.swing.JButton updateButton = new javax.swing.JButton();
		updateButton.setText("Update");
		updateButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				System.out.println("Update");
				int index = list5.getSelectedIndex();
				if (index>=0){
					String element = (String)list5Model.get(index);
					//System.out.println("-> "+element);
					String[] shoppingItems = new String[list6Model.size()]; 
					for (int i=0; i<list6Model.size(); i++){
						shoppingItems[i] = (String)list6Model.getElementAt(i);
					}
					addShoppingListActionPerformed(evt, editNameTextField.getText(), editDateTextField.getText(), shoppingItems);
				}
			}
		});

		downCenterPanel.add(new javax.swing.JLabel(""));
		downCenterPanel.add(new javax.swing.JLabel(""));
		downCenterPanel.add(updateButton);
		downCenterPanel.add(new javax.swing.JLabel(""));
		downCenterPanel.add(new javax.swing.JLabel(""));
		
		// Add the above two panels in tail panel
		editTail.add(new javax.swing.JLabel(""));
		editTail.add(editShoppingListName);
		editTail.add(editShoppingListDate);
		editTail.add(new javax.swing.JLabel(""));
		editTail.add(downCenterPanel);
		editTail.add(new javax.swing.JLabel(""));
		
		editpanel.setBackground(Color.WHITE);
		editpanel.setPreferredSize(new Dimension(800, 350));
		editpanel.add(heading, java.awt.BorderLayout.NORTH);
		editpanel.add(panel, java.awt.BorderLayout.CENTER);
		editpanel.add(editTail, java.awt.BorderLayout.SOUTH);
        
        return editpanel;
    }

    
    private void addShoppingListActionPerformed(java.awt.event.ActionEvent evt, String shoppingListName, String shoppingListDate, String[] shoppingItems) {    
    	String uri = org.universAAL.ontology.Shopping.ShoppingList.MY_URI;
    	
		try {
/*
			System.out.println("shopping list name="+shoppingListName);
			System.out.println("shopping list date="+shoppingListDate);
			for (int i=0; i<shoppingItems.length; i++)
				System.out.println("item="+shoppingItems[i]);
			System.out.println("shopping uri="+uri);
*/
			boolean addList = FoodManagementClient.addShoppingList(uri, shoppingListName, shoppingListDate, shoppingItems);
		}
		catch (Exception e){
			System.out.println("checking " + uri);
		}
	}

    private void updateShoppingListActionPerformed(java.awt.event.ActionEvent evt, String shoppingListName, String shoppingListDate, String[] shoppingItems) {    
    	String uri = org.universAAL.ontology.Shopping.ShoppingList.MY_URI;
    	
		try {
			boolean updateList = FoodManagementClient.updateShoppingList(uri, shoppingListName, shoppingListDate, shoppingItems);
		}
		catch (Exception e){
			System.out.println("checking " + uri);
		}
	}
    
    // Use in Browse Tab
    private void getShoppingListItemsActionPerformed(java.awt.event.ActionEvent evt, String shoppingListName) {    
    	String uri = org.universAAL.ontology.Shopping.ShoppingList.MY_URI;
    	
		try {
			FoodItem[] itemList = FoodManagementClient.getShoppingListItems(uri, shoppingListName);
			if (itemList!=null){
				list4Model.clear();
				java.util.Hashtable ht = new java.util.Hashtable();
				for (int i=0; i<itemList.length; i++){
					int code = itemList[i].getCode();
					ht.put(new Integer(code), new Vector());
				}
				for (int i=0; i<itemList.length; i++){
					Vector tmp = (Vector)ht.get(new Integer(itemList[i].getCode()));
					String item = itemList[i].getName() + ", "+itemList[i].getSize()+", "+itemList[i].getCompany();  
					tmp.add(item);
					ht.put(new Integer(itemList[i].getCode()), tmp);
				}
				java.util.Enumeration en = ht.keys();
				while (en.hasMoreElements()){
					int code = ((Integer)en.nextElement()).intValue();
					Vector tmp = (Vector)ht.get(new Integer(code));
					int cnt = tmp.size();
					String item = (String)tmp.get(0);
					if (cnt>1)
						item += " -- x"+cnt;
					list4Model.addElement(item);
				}
			}
		}
		catch (Exception e){
			System.out.println("checking " + uri);
		}
	}

    // Use in Edit Tab
    private void getShoppingListItems2ActionPerformed(java.awt.event.ActionEvent evt, String shoppingListName) {    
    	String uri = org.universAAL.ontology.Shopping.ShoppingList.MY_URI;
    	
		try {
			FoodItem[] itemList = FoodManagementClient.getShoppingListItems(uri, shoppingListName);
			org.universAAL.ontology.Shopping.ShoppingList[] shoppingLists = FoodManagementClient.getShoppingLists();
			if (itemList!=null){
				list6Model.clear();
				for (int i=0; i<itemList.length; i++){
					String item = itemList[i].getCode() + ", " + itemList[i].getName() + ", "+itemList[i].getSize()+", "+itemList[i].getCompany();  
					list6Model.addElement(item);
				}
				String name = "";
				String date = "";
				for (int i=0; i<shoppingLists.length; i++){
					name = shoppingLists[i].getName();
					date = shoppingLists[i].getDate();
					if (name.equals(shoppingListName))
						break;
				}
				editNameTextField.setText(name);
				editDateTextField.setText(date);
			}
		}
		catch (Exception e){
			System.out.println("checking " + uri);
		}
	}

    private boolean removeShoppingListActionPerformed(java.awt.event.ActionEvent evt, String shoppingListName) {    
    	String uri = org.universAAL.ontology.Shopping.ShoppingList.MY_URI;
    	boolean removeAction = false;
		try {
			removeAction = FoodManagementClient.removeShoppingList(uri, shoppingListName, currentListID);
	        org.universAAL.ontology.Shopping.ShoppingList[] shoppinglists = FoodManagementClient.getShoppingLists();
	        if (shoppinglists != null){
	        	list3Model.clear();
	        	for (int i=shoppinglists.length-1; i>=0; i--){
	        		String item = shoppinglists[i].getName();
	        		list3Model.addElement(item);
	        	}
	        	list4Model.clear();
	        }
		}
		catch (Exception e){
			System.out.println("checking " + uri);
		}
		return removeAction;
	}

    /**
     * Create a File menu to support quit.
     * Create an Edit menu to support cut/copy/paste.
     * Create an Shopping List menu to support new.
     */
    public JMenuBar createMenuBar() {
        JMenuItem menuItem = null;
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu mainMenu = new JMenu("Edit");
        JMenu shoppingListMenu = new JMenu("Shopping List");
        mainMenu.setMnemonic(KeyEvent.VK_E);
        TransferActionListener actionListener = new TransferActionListener();

        menuItem = new JMenuItem("Quit");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }

        });
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        menuItem.setMnemonic(KeyEvent.VK_Q);
        fileMenu.add(menuItem);

        menuItem = new JMenuItem("Copy");
        menuItem.setActionCommand((String)TransferHandler.getCopyAction().getValue(Action.NAME));
        menuItem.addActionListener(actionListener);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        menuItem.setMnemonic(KeyEvent.VK_C);
        mainMenu.add(menuItem);
        
        menuItem = new JMenuItem("Paste");
        menuItem.setActionCommand((String)TransferHandler.getPasteAction().getValue(Action.NAME));
        menuItem.addActionListener(actionListener);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        menuItem.setMnemonic(KeyEvent.VK_V);
        mainMenu.add(menuItem);

        menuItem = new JMenuItem("Delete");
        menuItem.setActionCommand((String)TransferHandler.getCutAction().getValue(Action.NAME));
        menuItem.addActionListener(actionListener);
        menuItem.setAccelerator(KeyStroke.getKeyStroke("Delete"));
        //menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, ActionEvent.CTRL_MASK));
        menuItem.setMnemonic(KeyEvent.VK_DELETE);
        mainMenu.add(menuItem);

        menuItem = new JMenuItem("New");
        menuItem.setActionCommand("New");
        menuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
	    		FoodManagementUIClient.setTabbedFocus(1);
			}
		});        
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        menuItem.setMnemonic(KeyEvent.VK_N);
        shoppingListMenu.add(menuItem);

        menuItem = new JMenuItem("Edit");
        menuItem.setActionCommand("Edit");
        menuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
	    		FoodManagementUIClient.setTabbedFocus(3);
			}
		});        
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        menuItem.setMnemonic(KeyEvent.VK_E);
        shoppingListMenu.add(menuItem);

        menuItem = new JMenuItem("Browse");
        menuItem.setActionCommand("Browse");
        menuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
	    		FoodManagementUIClient.setTabbedFocus(2);
			}
		});        
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
        menuItem.setMnemonic(KeyEvent.VK_B);
        shoppingListMenu.add(menuItem);

        menuBar.add(fileMenu);
        menuBar.add(mainMenu);
        menuBar.add(shoppingListMenu);
        return menuBar;
    }
    
    /**
     * Add the cut/copy/paste actions to the action map.
     */
    private static void setMappings(JList list) {
        ActionMap map = list.getActionMap();
        map.put(TransferHandler.getCutAction().getValue(Action.NAME),
                TransferHandler.getCutAction());
        map.put(TransferHandler.getCopyAction().getValue(Action.NAME),
                TransferHandler.getCopyAction());
        map.put(TransferHandler.getPasteAction().getValue(Action.NAME),
                TransferHandler.getPasteAction());

    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("ListCutPaste");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the menu bar and content pane.
        ShoppingList demo = new ShoppingList();
        demo.createShoppingList();
        frame.setJMenuBar(demo.createMenuBar());
        demo.setOpaque(true); //content panes must be opaque
        frame.setContentPane(demo);
        frame.getContentPane().add(createpanel, java.awt.BorderLayout.CENTER);
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {           
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
    }
}
