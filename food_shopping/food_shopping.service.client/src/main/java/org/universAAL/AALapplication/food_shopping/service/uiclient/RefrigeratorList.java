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

import java.io.*;
import java.util.Vector;
import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.ontology.Shopping.FoodItem;
import org.universAAL.ontology.phThing.Device;

/**
 * @author dimokas
 * 
 */
public class RefrigeratorList extends JPanel {
	private boolean DEBUG = false;
    private boolean ALLOW_COLUMN_SELECTION = false;
    private boolean ALLOW_ROW_SELECTION = true;
    public static JPanel createpanel = new JPanel(new BorderLayout());
    public static Object[][] data = null;
    public static JTable table = null;
    
	public RefrigeratorList() {
        super(new BorderLayout());
        //super(new GridLayout(1,0));
    }
	
    public void updateRefrigeratorList(FoodItem[] lfi) {
    	String[] columnNames = {"Food Item","Size","Company","Qunatity","Insertion Date","Expiration Date","Tag ID"};
        FoodItem[] fi = lfi;
        if (fi==null){
        	data = new Object[1][columnNames.length];
       		data[0][0]=data[0][1]=data[0][2]=data[0][3]=data[0][4]=data[0][5]=data[0][6]="";
        }
        else{	
	        data = new Object[fi.length+1][columnNames.length];
	
	        int i=0;
	        for (i=0; i<fi.length; i++){
	       		data[i][0]=fi[i].getName();
	       		data[i][1]=fi[i].getSize();
	       		data[i][2]=fi[i].getCompany();
	       		data[i][3]=fi[i].getQuantity();
	       		data[i][4]=fi[i].getInsDate();
	       		data[i][5]=fi[i].getExpDate();
	       		data[i][6]=fi[i].getTagID();
	        }
	   		data[i][0]=data[i][1]=data[i][2]=data[i][3]=data[i][4]=data[i][5]=data[i][6]="";
        }
	   	DefaultTableModel model = new DefaultTableModel(data,columnNames);
   		table.setModel(model);
    }
	
/* OLD implementation with previous version of Context Event
 * 
    public void updateRefrigeratorList(final String deviceURI) {
 
    	String[] columnNames = {"Food Item","Size","Company","Qunatity","Insertion Date","Expiration Date","Tag ID"};
        FoodItem[] fi = FoodManagementClient.getRefrigeratorItems(deviceURI);
        if (fi==null){
        	data = new Object[1][columnNames.length];
       		data[0][0]=data[0][1]=data[0][2]=data[0][3]=data[0][4]=data[0][5]=data[0][6]="";
        }
        else{	
	        data = new Object[fi.length+1][columnNames.length];
	
	        int i=0;
	        for (i=0; i<fi.length; i++){
	       		data[i][0]=fi[i].getName();
	       		data[i][1]=fi[i].getSize();
	       		data[i][2]=fi[i].getCompany();
	       		data[i][3]=fi[i].getQuantity();
	       		data[i][4]=fi[i].getInsDate();
	       		data[i][5]=fi[i].getExpDate();
	       		data[i][6]=fi[i].getTagID();
	        }
	   		data[i][0]=data[i][1]=data[i][2]=data[i][3]=data[i][4]=data[i][5]=data[i][6]="";
        }
	   	DefaultTableModel model = new DefaultTableModel(data,columnNames);
   		table.setModel(model);
    }
*/	
    public JPanel createRefrigeratorList(final String deviceURI) {
    //public JPanel createRefrigeratorList() {
 
    	String[] columnNames = {"Food Item","Size","Company","Qunatity","Insertion Date","Expiration Date","Tag ID"};
        FoodItem[] fi = FoodManagementClient.getRefrigeratorItems(deviceURI);
   		System.out.println("###################################################################");

        if (fi==null){
        	data = new Object[1][columnNames.length];
       		data[0][0]=data[0][1]=data[0][2]=data[0][3]=data[0][4]=data[0][5]=data[0][6]="";
        }
        else{	
        	data = new Object[fi.length+1][columnNames.length];
        	int i=0;
	        for (i=0; i<fi.length; i++){
	       		data[i][0]=fi[i].getName();
	       		data[i][1]=fi[i].getSize();
	       		data[i][2]=fi[i].getCompany();
	       		data[i][3]=fi[i].getQuantity();
	       		data[i][4]=fi[i].getInsDate();
	       		data[i][5]=fi[i].getExpDate();
	       		data[i][6]=fi[i].getTagID();
	       		
	       		System.out.println("Name="+data[i][0]+"Company="+data[i][2]+"Quantity="+data[i][3]+"TagID="+data[i][6]);
	        }
	   		data[i][0]=data[i][1]=data[i][2]=data[i][3]=data[i][4]=data[i][5]=data[i][6]="";
        } 
   		DefaultTableModel model = new DefaultTableModel(data,columnNames);
   		table = new JTable(model){
   		  public boolean isCellEditable(int rowIndex, int colIndex) {
   			  if(colIndex==0 ){return false;}
   			  return true;
   		  }
   		  public Object getValueAt(int row, int col) {
   			  return data[row][col];
   		  }	   		  
   		  public void setValueAt(Object value, int row, int col) {
   			  if (DEBUG) {
            	System.out.println("Setting value at " + row + "," + col
	                                   + " to " + value
	                                   + " (an instance of "
	                                   + value.getClass() + ")");
   			  }
   			  data[row][col] = value;
   		  }
   		};
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        table.setBackground(Color.white);
        JTableHeader header = table.getTableHeader();
        header.setForeground(Color.blue);

        //header.setBackground(Color.yellow);
        
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        if (ALLOW_ROW_SELECTION) { // true by default
            ListSelectionModel rowSM = table.getSelectionModel();
            rowSM.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    //Ignore extra messages.
                    if (e.getValueIsAdjusting()) return;
 
                    ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                    if (lsm.isSelectionEmpty()) {
                        System.out.println("No rows are selected.");
                    } else {
                        int selectedRow = lsm.getMinSelectionIndex();
                        System.out.println("Row " + selectedRow + " is now selected.");
                    }
                }
            });
        } else {
            table.setRowSelectionAllowed(false);
        }
 
        if (ALLOW_COLUMN_SELECTION) { // false by default
            if (ALLOW_ROW_SELECTION) {
                //We allow both row and column selection, which
                //implies that we *really* want to allow individual
                //cell selection.
                table.setCellSelectionEnabled(true);
            }
            table.setColumnSelectionAllowed(true);
            ListSelectionModel colSM = table.getColumnModel().getSelectionModel();
            colSM.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    //Ignore extra messages.
                    if (e.getValueIsAdjusting()) return;
 
                    ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                    if (lsm.isSelectionEmpty()) {
                        System.out.println("No columns are selected.");
                    } else {
                        int selectedCol = lsm.getMinSelectionIndex();
                        System.out.println("Column " + selectedCol
                                           + " is now selected.");
                    }
                }
            });
        }
 
        if (DEBUG) {
            table.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    printDebugData(table);
                }
            });
        }
 
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
 
        // Panel for add-update-remove button
        JPanel downCenterPanel = new JPanel(new FlowLayout());
        downCenterPanel.setBackground(Color.white);
		javax.swing.JButton addButton = new javax.swing.JButton();
		addButton.setText("Add");
		addButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				Vector data = model.getDataVector();
				//for (int i=0; i<data.size(); i++){
				//	Vector row = (Vector)data.get(i);
				//	System.out.println(row.get(0)+","+row.get(1));
				//}
				Vector lastRow=(Vector)data.get(data.size()-1);
				String item = (String)lastRow.get(0);
				String quantity = (String)lastRow.get(1);
				String size = (String)lastRow.get(2);
				String company = (String)lastRow.get(3);
				String tag = (String)lastRow.get(4);
				String insDate = (String)lastRow.get(5);
				String expDate = (String)lastRow.get(6);
				
				addfoodItemsActionPerformed(evt, deviceURI, item, quantity, size, company, tag, insDate, expDate);
			}
		});

		javax.swing.JButton updateButton = new javax.swing.JButton();
		updateButton.setText("Update");
		updateButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				Vector data = model.getDataVector();
				
				Vector lastRow=(Vector)data.get(data.size()-1);
				String item = (String)lastRow.get(0);
				String quantity = (String)lastRow.get(1);
				String size = (String)lastRow.get(2);
				String company = (String)lastRow.get(3);
				String tag = (String)lastRow.get(4);
				String insDate = (String)lastRow.get(5);
				String expDate = (String)lastRow.get(6);
				System.out.println("quantity="+quantity);
				updatefoodItemsActionPerformed(evt, deviceURI, item, quantity, size, company, tag, insDate, expDate);
			}
		});
		
		javax.swing.JButton removeButton = new javax.swing.JButton();
		removeButton.setText("Remove");
		removeButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
	            ListSelectionModel rowSM = table.getSelectionModel();
	            rowSM.addListSelectionListener(new ListSelectionListener() {
	                public void valueChanged(ListSelectionEvent e) {
	                    //Ignore extra messages.
	                    if (e.getValueIsAdjusting()) return;
	 
	                    ListSelectionModel lsm = (ListSelectionModel)e.getSource();
	                    if (lsm.isSelectionEmpty()) {
	                        System.out.println("@@@@@@ No rows are selected.");
	                    } else {
	                        int selectedRow = lsm.getMinSelectionIndex();
	                        System.out.println("@@@@@@ Row " + selectedRow + " is now selected.");
	                    }
	                }
	            });
/*
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				ListSelectionModel rowSM = table.getSelectionModel();				
                //ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                if (lsm.isSelectionEmpty()) {
                    System.out.println("No rows are selected.");
                } else {
                    int selectedRow = lsm.getMinSelectionIndex();
                    System.out.println("Row " + selectedRow + " is now selected.");
                }

				
				Vector data = model.getDataVector();
				
				Vector lastRow=(Vector)data.get(data.size()-1);
				String item = (String)lastRow.get(0);
				String quantity = (String)lastRow.get(1);
				String size = (String)lastRow.get(2);
				String company = (String)lastRow.get(3);
				String tag = (String)lastRow.get(4);
				String insDate = (String)lastRow.get(5);
				String expDate = (String)lastRow.get(6);
				
				updatefoodItemsActionPerformed(evt, deviceURI, item, quantity, size, company, tag, insDate, expDate);
*/				
			}
		});
		
		downCenterPanel.add(new javax.swing.JLabel(""));
		downCenterPanel.add(addButton);
		downCenterPanel.add(updateButton);
		downCenterPanel.add(removeButton);
		downCenterPanel.add(new javax.swing.JLabel(""));

        
        //Add the scroll pane to this panel.
        createpanel.setBackground(Color.WHITE);
        createpanel.add(scrollPane, java.awt.BorderLayout.CENTER);
        createpanel.add(downCenterPanel, java.awt.BorderLayout.SOUTH);
        return createpanel;
    }
 
	private void addfoodItemsActionPerformed(java.awt.event.ActionEvent evt, String uri, String itemName, String itemNum, String size, String company, String tagID, String insDate, String expDate) {
		double quantity = Double.parseDouble(itemNum);

		boolean addFoodItems = FoodManagementClient.addFoodItems(uri,itemName,quantity,size,company, tagID, insDate, expDate);
		//updateRefrigeratorList(uri);
	}

	private void updatefoodItemsActionPerformed(java.awt.event.ActionEvent evt, String uri, String itemName, String itemNum, String size, String company, String tagID, String insDate, String expDate) {
		double quantity = Double.parseDouble(itemNum);

		boolean updateFoodItems = FoodManagementClient.updateFoodItems(uri,itemName,quantity,size,company, tagID, insDate, expDate);
		//updateRefrigeratorList(uri);
	}

	private void removefoodItemsActionPerformed(java.awt.event.ActionEvent evt, String uri, String tagName) {
		boolean removeFoodItems = FoodManagementClient.removeFoodItems(uri,tagName);
		//updateRefrigeratorList(uri);
	}

    
    private void printDebugData(JTable table) {
        int numRows = table.getRowCount();
        int numCols = table.getColumnCount();
        javax.swing.table.TableModel model = table.getModel();
 
        System.out.println("Value of data: ");
        for (int i=0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j=0; j < numCols; j++) {
                System.out.print("  " + model.getValueAt(i, j));
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }
 
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("SimpleTableSelectionDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Create and set up the content pane.
        RefrigeratorList newContentPane = new RefrigeratorList();
        //newContentPane.createRefrigeratorList();
        createpanel.setOpaque(true); 
        frame.setContentPane(createpanel);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
 
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}
