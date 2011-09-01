package na.services.shoppinglist.ui;



import java.awt.Desktop;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


import na.utils.lang.Messages;
import na.miniDao.FoodItem;
import na.oasisUtils.profile.ProfileConnector;
import na.services.shoppinglist.ShoppingListSubServiceLauncher;
import na.services.shoppinglist.VIsualShoppingList;
import na.services.shoppinglist.business.Mail;
import na.services.shoppinglist.business.PDFGenerator;
import na.services.shoppinglist.business.ShoppingData;
import na.services.shoppinglist.ui.actionsPanel.DayPanel;
import na.services.shoppinglist.ui.goingShop.ShoppingMainPanel;
import na.utils.ExtraShoppingItems;
import na.utils.FoodInventory;
import na.utils.OASIS_ServiceUnavailable;
import na.utils.Utils;
import na.widgets.button.AdaptiveButton;
import na.widgets.checkbox.AdaptiveCheckBox;
import na.widgets.label.AdaptiveLabel;
import na.widgets.list.AdaptiveList;
import na.widgets.panel.AdaptivePanel;
import na.widgets.spinner2.AdaptiveSpinner2;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.itextpdf.text.DocumentException;


@SuppressWarnings("serial")
public class SubServiceFrame extends AdaptivePanel implements ActionListener, ListSelectionListener, ChangeListener {
	public static final int PDF = 1;
	public static final int PRINTER = 2;
	private static Log log = LogFactory.getLog(SubServiceFrame.class);	
	private ShoppingListSubServiceLauncher launcher;
	private Menu secNavBar;
	public AdaptivePanel content;
	
	public AdaptiveLabel lab_info;
	//buttons
	public AdaptiveSpinner2 spinner;
	public na.widgets.button.AdaptiveButton but_previousItems;
	public na.widgets.button.AdaptiveButton but_moreItems;
	public AdaptiveButton but_addFood;
	//list items
	public List<AdaptiveLabel> listItemsName = new ArrayList<AdaptiveLabel>();
	public List<AdaptiveLabel> listItemsQuantity = new ArrayList<AdaptiveLabel>();
	public List<AdaptiveCheckBox> listItemsAlready = new ArrayList<AdaptiveCheckBox>();
	public List<AdaptiveLabel> listItemsExtra = new ArrayList<AdaptiveLabel>();
//	public List<AdaptiveCheckBox> listDayBox = new ArrayList<AdaptiveCheckBox>();
	public List<AdaptiveButton> listCategoryButtons = new ArrayList<AdaptiveButton>();
//	public List<AdaptiveLabel> listDayInfo;
	// temporary buttons
//	public AdaptiveButton moreBut;
//	public AdaptiveButton lessBut;
	
	//others
	public AdaptiveList foodListing;
	
	//needed vars
	public Calendar today;
	public int print_method = -1;
	public int sizeSelection;
	public int sizeAlreadyGenerated;
	public VIsualShoppingList superSL;
	private final int LIST_LENGTH_PER_PAGE = 10;
	private int list_pos = 0;
//	private int vertical_dist = 45;
//	private int dist_columnQuantity = 150;
//	private int dist_columnAlready = 120;
//	private String box_name_list = "box_shoppingList";
//	private int x_shoppingList_box = 20;
//	private int y_shoppingList_box = 65;
	//add food selections
	private int selectedItemID = -1;
	private String selectedItemName = "";
	private String selectedCategory;
	
	// extraLink to left panel (action buttons)
	public AdaptivePanel leftSide;
	public ShoppingMainPanel leftPanel;
	private String[]foodList;
	public List<DayPanel> listDayPanel;
	public AdaptiveLabel daysLabel;
	
	public SubServiceFrame(ShoppingListSubServiceLauncher launch) {
		this.launcher = launch;
		this.setBorder(BorderFactory.createEmptyBorder());
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{175, 100, 0};
		gridBagLayout.rowHeights = new int[]{20, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0E-4};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0E-4};
		setLayout(gridBagLayout);
		{
			this.secNavBar = new Menu();
			secNavBar.launcher = this.launcher;
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(0, 0, 0, 5);
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 0;
			gbc.gridy = 0;
			add(secNavBar, gbc);
		}
		{
			this.content = new AdaptivePanel();
			content.setBorder(BorderFactory.createEmptyBorder());
			content.setLayout(new GridLayout(1, 0, 0, 0));
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			gbc.gridx = 1;
			gbc.gridy = 0;
			add(content, gbc);
		}
	}

	public void drawShoppingList() {
		
		if (superSL!= null) {
			int list_length = superSL.items.size();
			log.info("Shop: List Length= " + list_length);
			
			// show if list is too long to show at once
			if (list_length > this.LIST_LENGTH_PER_PAGE) {
				// Previous button
				but_previousItems.setText(Messages.ShoppingList_PreviousItems);
				Utils.removeActionsListeners(but_previousItems);
				but_previousItems.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						butPreviousItems(evt);
					}
				});
				
				// Next button
				but_moreItems.setText(Messages.ShoppingList_MoreItems);
				Utils.removeActionsListeners(but_moreItems);
				but_moreItems.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						butMoreItems(evt);
					}
				});
				if ((this.LIST_LENGTH_PER_PAGE) >= list_length) { //lista más pequeña que el muestreo
					but_previousItems.setEnabled(false);
					but_previousItems.setVisible(false);
					but_moreItems.setEnabled(false);
					but_moreItems.setVisible(false);
				} else {
					but_previousItems.setEnabled(false);
					but_previousItems.setVisible(false);
					but_moreItems.setEnabled(true);
					but_moreItems.setVisible(true);
				}
			}
		} else {
			log.info("Shop: ShoppingList is null");
		}
		
		this.drawItems();
	}

	public void showError(String string, JPanel boxRight) {
		log.info("Shop: TODO");
	}
	

	
	/**
	 * Draw Shopping List Items.
	 */
	private void drawItems() {
		Map<String, String> foodInventoryMAP = new HashMap<String, String>();
		// get food inventory hashMap
		String[] foodsInventory = FoodInventory.getFoodInventory();
		// checks if a food is already in Food Inventory
		if (foodsInventory!= null) {
			for (String food : foodsInventory) {
				String value = food;
				foodInventoryMAP.put(food, value);
			}
		}
		
		// Drawing list stuff
		if (superSL != null) {
			// Draw items!
//			int list_length = superSL.items.size();
			
			FoodItem[] selecctedItems = superSL.getSomeItems(this.list_pos, this.LIST_LENGTH_PER_PAGE);
//			for (int i=0,j=0; (i<list_length) && (j<this.LIST_LENGTH_PER_PAGE); i++,j++) {
			for (int i=0; i<this.LIST_LENGTH_PER_PAGE; i++) {
				if (selecctedItems[i]==null) {
					// empty item
					log.info("Shop: Jarl is null! i="+i);
					this.listItemsName.get(i).setText("");
					this.listItemsQuantity.get(i).setText("");
					this.listItemsAlready.get(i).setVisible(false);
					this.listItemsExtra.get(i).setText("");
					continue;
				}
				String quantity = selecctedItems[i].getAmount();
				String name = selecctedItems[i].getName();
				if (name==null)
					name = "empty!";
				name = Utils.Strings.capitalize(name);
				String category = selecctedItems[i].getCategory();
				int ID = selecctedItems[i].getFoodID();
//				log.info("Shop: Showing food: '"+name+"'");
				
				// Draw Name
				this.listItemsName.get(i).setText(name);
				
				// Draw Quantity
				if (quantity!=null)
					quantity = Utils.Strings.roundUp(quantity);
				this.listItemsQuantity.get(i).setText(quantity);
				
				// Draw Exists CheckBox
				// if foodItem belongs to Inventory -> check
				this.listItemsAlready.get(i).setVisible(true);
				if (selecctedItems[i].getSource()!= null && selecctedItems[i].getSource().compareTo(VIsualShoppingList.Source_EXTRA)==0) {
					this.listItemsExtra.get(i).setText("Extra");
				} else {
					this.listItemsExtra.get(i).setText("");
				}
				if (selecctedItems[i].getSource()!= null && selecctedItems[i].getSource().compareTo(VIsualShoppingList.Source_INVENTORY)==0
						 || (foodInventoryMAP.containsKey(ID)) ) {
					this.listItemsAlready.get(i).setSelected(true);
				} else {
					this.listItemsAlready.get(i).setSelected(false);
				}
				this.listItemsAlready.get(i).setName(ID+"@"+name+"@"+category);
			}
			
		} else {
//			this.showError(Messages.ShoppingList_NoiTemsFound, parent);
			log.info("Shop: ERROR: +Messages.ShoppingList_NoiTemsFound");
		}
		
	}
	

//	@Override
	public void actionPerformed(ActionEvent e) {
		log.info("Shop: checkBox clicked");
		AdaptiveCheckBox box = (AdaptiveCheckBox) e.getSource();
		String[] elem = ((AdaptiveCheckBox)e.getSource()).getName().split("@");
		int id = Integer.parseInt(elem[0]);
		String name = elem[1].toLowerCase();
		if (elem[2]==null)
			elem[2]="no category";
		String category = elem[2].toLowerCase();
		log.info("Shop: Selected: "+id + " "+ name);
//		for (int i=0; i<this.superSL.items.keySet().toArray().length; i++) {
//			log.info("Shop:  key: "+this.superSL.items.keySet().toArray()[i]);
//		}
		try {
			if (box.isSelected() == true) {// == ItemEvent.SELECTED) {
				log.info("Shop: itemStateChanged: selected");
				if (this.superSL.items.containsKey(id)) {
					// es un extra food?
					if (this.superSL.items.get(id).getSource().compareTo(VIsualShoppingList.Source_EXTRA)==0) {
						// eliminar de la SL y del extraFoods
						ExtraShoppingItems.removeFood(id,name);
						this.superSL.items.remove(id);
					} else if (this.superSL.items.get(id).getSource().compareTo(VIsualShoppingList.Source_INVENTORY)==0) {
						log.info("Shop: Unknown action");
						ExtraShoppingItems.removeFood(id,name);
						this.superSL.items.remove(id);
					} else { //normal, pertence a la SL original
						log.info("Shop: item original");
						// marcado como alreadyBought it-> añadir al foodInventory
						FoodInventory.addFood(id,name);
						this.superSL.items.get(id).setSource(VIsualShoppingList.Source_INVENTORY);
						log.info("Shop: item ahora es: "+this.superSL.items.get(id).getSource());
					}
				} else {
					log.info("Shop: Food id:"+id +" "+name+" not found in Tree!!");
				}
			} else {
				log.info("Shop: itemStateChanged: deselected");
				if (this.superSL.items.containsKey(id)) {
					if (this.superSL.items.get(id).getSource().compareTo(VIsualShoppingList.Source_EXTRA)==0) {
						this.superSL.items.get(id).setSource(VIsualShoppingList.Source_EXTRA);
						ExtraShoppingItems.addFood(id, name, category);
					} else if (this.superSL.items.get(id).getSource().compareTo(VIsualShoppingList.Source_INVENTORY)==0) {
						this.superSL.items.get(id).setSource(VIsualShoppingList.Source_WEB);
						FoodInventory.removeFood(id, name);
					} else {
						this.superSL.items.get(id).setSource(VIsualShoppingList.Source_WEB);
						FoodInventory.removeFood(id, name);
					}
				} else {
					log.info("Shop: Food id:"+id +" "+name+" not found in Tree!!");
				}
			}
		} catch (NullPointerException e1) {
			log.info("Shop: Stack[0]: "+e1.getStackTrace()[0].getMethodName());
			log.info("Shop: Message: "+e1.getMessage());
			log.info("Shop: Cause: "+e1.getCause());
			e1.printStackTrace();
		}
	}
	
	private void updateSelectedDays() {
//		this.spinner.setValue(this.sizeSelection);
		if (this.sizeSelection == 1) {
			this.daysLabel.setText(""+this.sizeSelection +" "+ Messages.ShoppingList_GoingShopping_ShoppingList_DAY);
		} else {
			this.daysLabel.setText(""+this.sizeSelection +" "+ Messages.ShoppingList_DAYS);
		}
//		if (this.sizeSelection>0) {
//			for (int i=0; i<this.sizeSelection && i<this.listDayBox.size(); i++) {
//				this.listDayBox.get(i).setSelected(true);
//			}
//			for (int i=(this.sizeSelection); i<7; i++) {
//				this.listDayBox.get(i).setSelected(false);
//			}
//		} else
//			log.info("Shop: size selection 0 or -1");

		this.applyShowRules(sizeAlreadyGenerated, sizeSelection);
	}

	/****************
	 * Button Actions
	 ****************/
	
	private void butMoreItems(java.awt.event.ActionEvent evt) {
		log.info("Shop: Show next items!");
		this.list_pos += this.LIST_LENGTH_PER_PAGE;
		this.drawItems();
		
		but_previousItems.setText(Messages.ShoppingList_PreviousItems);
		this.but_previousItems.setEnabled(true);
		this.but_previousItems.setVisible(true);
		if ((this.list_pos+this.LIST_LENGTH_PER_PAGE)>=this.superSL.items.size()) {
			log.info("Shop: No hace falta otro boton more items, porque no quedan más elementos");
			this.but_moreItems.setEnabled(false);
			this.but_moreItems.setVisible(false);
		} else {
			this.but_moreItems.setEnabled(true);
			this.but_moreItems.setVisible(true);
		}
	}

	private void butPreviousItems(java.awt.event.ActionEvent evt) {
		log.info("Shop: Show previous items!");
		this.list_pos -= this.LIST_LENGTH_PER_PAGE;
		this.drawItems();
		this.but_moreItems.setEnabled(true);
		this.but_moreItems.setVisible(true);
		this.but_moreItems.setText(Messages.ShoppingList_MoreItems);
		if (this.list_pos <= 0) {
			this.but_previousItems.setEnabled(false);
			but_previousItems.setText(Messages.ShoppingList_NoPreviousItems);
			but_previousItems.setVisible(false);
		}
	}

	
	// Buttons actions
	public void butPrintShoppingList(java.awt.event.ActionEvent evt, boolean useSocial) {
		// ask social community for today events
		String[] events = null;
		if (useSocial==true) {
			log.info("Shop: Ask social: events?");
	//		String[] events = {"2@3"}; // friday, lunch
			try {
				events = this.launcher.getMyOutsideEvents(this.today, this.sizeSelection);
			} catch (OASIS_ServiceUnavailable e) {
				log.error("There was an error trying to load outsideEvents: "+e.getMessage());
				e.printStackTrace();
			}
		} else {
			log.info("Shop: user is not interested in asking social communities");
		}
		// if there are events
		if (events!=null && events.length>0) {
			log.info("Shop: found interesting events: "+events.length);
			Object[] options = {Messages.Answer_Yes,
					Messages.Answer_No,
					Messages.Answer_Cancel};
			int selection = JOptionPane.showOptionDialog(null,
		    Messages.Shopping_lunchOutside,
		    Messages.ShoppingQuestion,
		    JOptionPane.YES_NO_CANCEL_OPTION,
		    JOptionPane.QUESTION_MESSAGE,
		    null,
		    options,
		    options[2]);
			switch (selection) {
				case 0:
					log.info("Shop: user said yes");
					log.info("Shop: printing shopping list without events");
					list_pos = 0;
					this.butUpdateSL();
					this.printShoppingList();
					break;
				case 1:
					log.info("Shop: user said no");
					log.info("Shop: printing shopping list taking into account outsideEvents");
					list_pos = 0;
					this.butUpdateSmartSL(events);
					this.printShoppingList();
					break;
				default:
					log.info("Shop: user doens't know");
					break;
			}
		} else {
			log.info("Shop: printing shopping list without events");
			list_pos = 0;
			this.butUpdateSL();
			this.printShoppingList();
		}
	}
	
	public void butSendToCarer(java.awt.event.ActionEvent evt) {
		log.info("Shop: Shopping list sent to your carer email account");
		String carerMail = ProfileConnector.getInstance().getCarerEmail();
		try {
			if (carerMail != null && carerMail.length()!=0) {
				this.generatePDF(this.superSL, this.today, this.sizeSelection);
				String subject = Messages.Shopping_email_Subject;
				String selectedDays = this.computeShoppingListDays(this.today, this.sizeSelection);
				String patientName = ProfileConnector.getInstance().getName() + " " + ProfileConnector.getInstance().getSurname();
//				String patientName = "";
				Date d = new Date();
				String content = Messages.Shopping_email_letter_carerHeader+",\r\n\r\n" +
						Messages.Shopping_email_letter_carerBody+":\r\n" +
						Messages.Shopping_email_letter_patientName+"Patient name: "+patientName+"\r\n" +
						Messages.Shopping_email_letter_periodOfTime+ ": "+ selectedDays +"\r\n" +
						Messages.Shopping_email_letter_generated+": "+ d +"\r\n" +
						"\r\n\r\n" +
						Messages.Shopping_email_letter_signature;
				String file = na.utils.ServiceInterface.PATH_PDF_FILE;
				this.sendMail(ProfileConnector.getInstance().getCarerEmail(), subject, content, file);
				showMessage("Information",Messages.Shopping_email_mailSentCarer+" ("+carerMail+")",JOptionPane.INFORMATION_MESSAGE);
			} else {
				showMessage("Warning","Carer email is not set!",JOptionPane.WARNING_MESSAGE);
			}
		} catch (FileNotFoundException e) {
			log.info("Shop: Error, fileNotFound...");
			e.printStackTrace();
		} catch (DocumentException e) {
			log.info("Shop: Error, doc exception");
			e.printStackTrace();
		} catch (Exception e) {
			log.error("Error sending email...");
			e.printStackTrace();
			showMessage("Error",Messages.Shopping_email_carerNotSent+ " ("+carerMail+") reason: "+e.getLocalizedMessage(),JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void butSendToMobile(java.awt.event.ActionEvent evt) {
		log.info("Shop: Shopping list sent to your email account");
		String eMail = ProfileConnector.getInstance().getEmail();
		try {
			if (eMail != null && eMail.length()!=0) {
				this.generatePDF(this.superSL, this.today, this.sizeSelection);
				String subject = Messages.Shopping_email_Subject;
				String selectedDays = this.computeShoppingListDays(this.today, this.sizeSelection);
//				String patientName = ProfileConnector.getInstance().getName() + " " + ProfileConnector.getInstance().getSurname();
				String patientName = "";
				Date d = new Date();
				String content = Messages.Shopping_email_letter_header+",\r\n\r\n" +
						Messages.Shopping_email_letter_body + ":\r\n" +
						Messages.Shopping_email_letter_name+": "+patientName+"\r\n" +
						Messages.Shopping_email_letter_periodOfTime+": "+ selectedDays +"\r\n" +
						Messages.Shopping_email_letter_generated+": "+ d +"\r\n" +
						"\r\n\r\n" +
						Messages.Shopping_email_letter_signature;
				String file = na.utils.ServiceInterface.PATH_PDF_FILE;
				this.sendMail(eMail, subject, content, file);
				showMessage(Messages.Shopping_email_information, Messages.Shopping_email_mailSent+" ("+eMail+")",JOptionPane.INFORMATION_MESSAGE);
			} else {
				showMessage("Warning","The email is not set!",JOptionPane.WARNING_MESSAGE);
			}
		} catch (FileNotFoundException e) {
			log.info("Shop: Error, fileNotFound...");
			e.printStackTrace();
		} catch (DocumentException e) {
			log.info("Shop: Error, doc exception");
			e.printStackTrace();
		} catch (Exception e) {
			log.error("Error sending email...");
			e.printStackTrace();
			showMessage("Error",Messages.Shopping_email_mainNotSent + " ("+eMail+") reason: "+e.getLocalizedMessage(),JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void butUpdateSL() {
		log.info("Shop: updating Shopping List");
		this.list_pos = 0;
//		launcher.loadAndShowShoppingList();
		launcher.reLoad();
	}
	
	private void butUpdateSmartSL(String[] outsideEvents) {
		log.info("Shop: updating smart Shopping List");
		this.list_pos = 0;
		launcher.reLoadSmart(outsideEvents);
	}
	

	
//	public void butIncreaseDays(java.awt.event.ActionEvent evt) {
//		if (this.sizeSelection < 7) {
//			this.moreBut.setEnabled(true);
//			this.sizeSelection++;
//			// redraw week label
//			this.updateSelectedDays();
//			// store changes
//			ShoppingData.storeSelectionChanges(this.sizeSelection, Utils.Dates.getTodayDate());
//			butUpdateSL();
//		} else {
//			log.info("Shop: Nothing to do");
//		}
//	}
//	
//	public void butDecreaseDays(java.awt.event.ActionEvent evt) {
//		if (this.sizeSelection > 1) {
//			this.sizeSelection--;
//			// redraw week label
//			this.updateSelectedDays();
//			// store changes
//			ShoppingData.storeSelectionChanges(this.sizeSelection, Utils.Dates.getTodayDate());
//			butUpdateSL();
//		} else {
//			log.info("Shop: Nothing to do");
//		}
//	}
	
	public void applyShowRules(int sizeAlreadyGenerated, int sizeSelection ) {
		for (int i=0; i<7; i++) {
	//		this.listDayBox.get(i).setSelected(true);
			// está generado previamente? -> naranja
			if (i<sizeAlreadyGenerated) {
				if (i<sizeSelection) { // está seleccionado previamente? -> rojo
					this.listDayPanel.get(i).setType(DayPanel.RED);
//					this.listDayInfo.get(i).setText("already generated");
				} else {
//					this.listDayInfo.get(i).setText("previously generated");
					this.listDayPanel.get(i).setType(DayPanel.DEFAULT);
				}
			} else {
				if (i<sizeSelection) { // está seleccionado previamente? -> verde
//					this.listDayInfo.get(i).setText("never generated before");
					this.listDayPanel.get(i).setType(DayPanel.GREEN);
				} else {
//					this.listDayInfo.get(i).setText("");
					this.listDayPanel.get(i).setType(DayPanel.DEFAULT);
				}
			}
		}
	}
	
	public void butAddToShoppingList(java.awt.event.ActionEvent evt) {
		log.info("Shop: Current Selected item ID: " + this.selectedItemID);
		this.showTemporaryError("");
		if (selectedItemID != -1)
			this.addNewItem(this.selectedItemID, this.selectedItemName, this.selectedCategory);
	}
	
	// Other action

	private static void open(File document) throws IOException {
		log.info("Shop: opening file using Desktop");
	    Desktop dt = Desktop.getDesktop();
	    dt.open(document);
	    log.info("Shop: Done opening file");
	}
	
	private void showPDF(Calendar startDate, int size) {
		try {
			if (this.print_method == SubServiceFrame.PDF) {
				this.generatePDF(this.superSL, startDate, size);
				String pdfFileString = na.utils.ServiceInterface.PATH_PDF_FILE;
				File f = new File(pdfFileString);
				SubServiceFrame.open(f);
			} else if (this.print_method == SubServiceFrame.PRINTER) {
//				this.sendToPrinter(this.superSL, startDate, size);
			} else {
				log.error("Printing... unknown printing method");
			}
		} catch (IOException e) {
			log.error("Couldn't open PDF reader.");
			log.info("Shop: Couldn't open PDF reader.");
			e.printStackTrace();
			showMessage("Error","Couldn't open Pdf reader.", JOptionPane.ERROR_MESSAGE);
		} catch (DocumentException e) {
			log.error("Error en fichero");
			log.info("Shop: Error en el PDF");
			e.printStackTrace();
		} 
//		catch (JposException e) {
//			log.error("Error while printing :(");
//			e.printStackTrace();
//			showMessage("Error","There was a problem with the printer: "+e.getLocalizedMessage(), JOptionPane.ERROR_MESSAGE);
//		}
	}

	private void showMessage(String title, String message, int type) {
//		LookAndFeel look = null;
//		try {
//			look =  UIManager.getLookAndFeel();
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
		JOptionPane.showMessageDialog(null, message,title, type);
//		try {
//			UIManager.setLookAndFeel(look);
//		} catch (UnsupportedLookAndFeelException e) {
//			e.printStackTrace();
//		}

	}

	public void emptyBoxLeft() {
		if (this.leftSide!=null) {
			this.leftSide.removeAll();
			this.leftSide.validate();
			this.leftSide.repaint();
		}
	}

	public void drawFoodListByCategory(int foodCategory) {
		try {
			//Get food by Category
			foodList = launcher.getFoodsByCategory(foodCategory);
		} catch (OASIS_ServiceUnavailable e) {
			e.printStackTrace();
			log.error("couldn't get foods by category, service unavailable");
		}
		
		if (foodList != null) {
			foodListing.setModel(new javax.swing.AbstractListModel() {
	            public int getSize() { return foodList.length; }
	            public Object getElementAt(int i) {
	            	if (foodList[i]!=null) {
//	            		selectedItemID = Integer.parseInt(foodList[i].split("@")[1]);
	            		selectedItemName = foodList[i].split("@")[0];
//	            		selectedCategory = foodList[i].split("@")[3];
//	            		log.info("Shop: Selected: "+selectedItemID + " " + selectedItemName);
	            		return selectedItemName;
	            	} else
	            		return "";
	            }
	        });
		}
	}

	public void selectFoodListButton(int i) {
		for (int j=0; j<this.listCategoryButtons.size(); j++) {
			this.listCategoryButtons.get(j).setSelected(false);
		}
		this.listCategoryButtons.get(i).setSelected(true);
	}

	public void showTemporaryError(String string) {
		if (this.lab_info!=null)
			this.lab_info.setText(string);
	}
	
	/*
	 * PRIVATE METHODS
	 */
	public int getReducedSLSize() {
		int size = this.sizeSelection - this.sizeAlreadyGenerated;
		if (this.sizeAlreadyGenerated > this.sizeSelection) 
			size = this.sizeSelection;
		return size;
	}
	
	private void addNewItem(int foodID, String foodName, String category) {
		String name = foodName.toLowerCase();
		log.info("Shop: adding: '"+name+"'" + "cat: "+category);
		/*
		 * if (foodID is not in (FoodInventory or shoppingList))
		 * 		addFoodToInventory	
		 * 		addFoodToShoppingList()
		 *		reDrawShoppingListNewItem()
		*/
		// check that food is not in shopping list already
		if (this.superSL.items.containsKey(foodID)) { //found in shopping list
			log.info("Shop: Food already found in shopping list");
			this.showTemporaryError(Messages.Shopping_FoodItemAlreadyExists);
//			box_right.repaint(); box_right.revalidate();
		}else { //not found
			try {
				int returnValue = ExtraShoppingItems.addFood(foodID, name, category);
				if (returnValue==1) {
					log.info("Shop: Inserted OK");
					//add to map
					FoodItem newFood = new FoodItem();
					newFood.setFoodID(foodID);
					newFood.setCategory(category);
					newFood.setSource(VIsualShoppingList.Source_EXTRA);
					newFood.setName(name);
					
					this.superSL.items.put(foodID, newFood);
					//redraw shopping list
					this.drawItems();
				} else if (returnValue==2) {
					log.info("Shop: Already existed, nothing to do?¿?");
					log.info("Shop: Inserted OK");
					//add to map
					FoodItem newFood = new FoodItem();
					newFood.setFoodID(foodID);
					newFood.setCategory(category);
					newFood.setSource(VIsualShoppingList.Source_EXTRA);
					newFood.setName(name);
					
					this.superSL.items.put(foodID, newFood);
					//redraw shopping list
					this.drawItems();
				} else if (returnValue == 0) {
					log.info("Shop: There was an error, stop.");
				}
			} catch (Exception e) {
				log.info("Shop: Error, al insertar un extra food");
				e.printStackTrace();
			}
			
			try {
				FoodInventory.removeFood(foodID, name);
			} catch (Exception e) {
				log.info("Shop: Error, al eliminar un food del Food inventory");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Creates a PDF file for current Shopping List.
	 * If there are already generated days, asks the user
	 */
	private void printShoppingList() {
		log.info("Shop: Generating pdf...");
		if (this.sizeAlreadyGenerated > 0) {
			//Custom button text
			Object[] options = {Messages.Answer_Yes,
					Messages.Answer_No,
					Messages.Answer_Cancel};
			int selection = JOptionPane.showOptionDialog(null,
					Messages.ShoppingQuestionAlreadyGenerated,
			    Messages.ShoppingQuestion,
			    JOptionPane.YES_NO_CANCEL_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    options,
			    options[2]);
			switch (selection) {
			case 0:
				log.info("Shop: Don't touch my SL");
				this.showPDF(this.today,this.sizeSelection);
				if (this.sizeSelection > this.sizeAlreadyGenerated)
					ShoppingData.storeGeneratedChanges(this.sizeSelection, Utils.Dates.getTodayDate());
				this.sizeAlreadyGenerated = this.sizeSelection;
				this.computeSelectedAndGeneratedDays();
				this.launcher.loadGoingShoppingActionPanel();
				break;
			     ////////////////////////////////////////////////////////////////
				////////////////////////////////////////////////////////////////
			case 1:
				/*
				 * Obtener dias repetidos
				 * Obtener shoppingList de esos dias
				 * Recorrer la shoppingList actual y restar los elementos encontrados
				 * actualizar ShoppingData generated=today, generated_size=selection_size
				 */
				if (this.sizeAlreadyGenerated == this.sizeSelection) {
					this.showMessage("Error","There are no days selected!", JOptionPane.INFORMATION_MESSAGE);
				} else {
					this.launcher.loadReducedShoppingList();
					this.showPDF(this.today,this.getReducedSLSize());
					if (this.sizeSelection > this.sizeAlreadyGenerated)
						ShoppingData.storeGeneratedChanges(this.sizeSelection, Utils.Dates.getTodayDate());
					log.info("Shop: Remove already generated items");
					this.computeSelectedAndGeneratedDays();
					this.launcher.loadGoingShoppingActionPanel();
				}
				break;
			     ////////////////////////////////////////////////////////////////
				////////////////////////////////////////////////////////////////
			default:
				log.info("Shop: Do nothing!");
				break;
			}
		} else {
			log.info("Shop: Don't touch my SL");
			this.showPDF(this.today, this.sizeSelection);
			ShoppingData.storeGeneratedChanges(this.sizeSelection, Utils.Dates.getTodayDate());
			this.computeSelectedAndGeneratedDays();
			this.launcher.loadGoingShoppingActionPanel();
		}
		log.info("Shop:  DONE");
	}
	

	private void generatePDF(VIsualShoppingList superSL, Calendar startDate, int size) throws DocumentException, IOException {
		PDFGenerator pdf = new PDFGenerator();
		pdf.createPDF(superSL, startDate, size);
	}
	
	
//	private void sendToPrinter(VIsualShoppingList superSL, Calendar startDate, int size) throws JposException {
	private void sendToPrinter(VIsualShoppingList superSL, Calendar startDate, int size) {
//		PrinterGenerator print = new PrinterGenerator();
//		print.sendToPrinter(superSL, startDate, size);
	}

	private boolean sendMail(String to, String subject, String content, String file) throws Exception {
		Mail m = new Mail();
		return m.sendMail(to, subject, content, file);
	}
	
	/**
	 * Compute selected and generated days.
	 * Escribe en this.sizeSelection         
	 * Escribe en this.sizeAlreadyGenerated
	 */
	public void computeSelectedAndGeneratedDays() {
		this.today = Utils.Dates.getTodayCalendar();
		try {
			// get selected days
			int seleccionTemporal = ShoppingData.getNumberOfDaysSelected();
			this.sizeSelection = 7;
			if (seleccionTemporal!=0) {
				Calendar firstSelectedDay = ShoppingData.getFirstSelectedDay();
				if (firstSelectedDay != null) {
					firstSelectedDay.add(Calendar.DAY_OF_MONTH, seleccionTemporal);
					int seleccionActual = Utils.Dates.daysBetween(this.today,firstSelectedDay);
					if (seleccionActual>0)
						this.sizeSelection = seleccionActual;
				}
			}
			
			// get generated days
			int generationTemporal = ShoppingData.getNumberOfDaysGenerated();
			log.info("Shop: generationTemporal:" + generationTemporal);
			
			if (generationTemporal != 0 && (this.sizeAlreadyGenerated < generationTemporal)) {
				this.sizeAlreadyGenerated = 0;
				Calendar firstGeneratedDay = ShoppingData.getFirstGeneratedDay();
				if (firstGeneratedDay != null) {
					firstGeneratedDay.add(Calendar.DAY_OF_MONTH, generationTemporal);
					int generationActual = Utils.Dates.daysBetween(this.today, firstGeneratedDay);
					if (generationActual>0)
						this.sizeAlreadyGenerated = generationActual;
				}
			}
		} catch (Exception e) {
			log.error("Ha ocurrido una excepcion... valores de SL por defecto!");
			this.sizeSelection = 7;
			this.sizeAlreadyGenerated = 0;
		}
		log.info("Shop: Size Generation: " + this.sizeAlreadyGenerated);
		log.info("Shop: La seleccion final es = " + this.sizeSelection);
	}

//	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {

	        if (this.foodListing.getSelectedIndex() == -1) {
	        //No selection, disable fire button.
	        	log.info("Shop: nothing selected");
	        } else {
	        //Selection, enable the fire button.
	        	log.info("Shop: something selected");
	        	int i = this.foodListing.getSelectedIndex();
	        	selectedItemID = Integer.parseInt(foodList[i].split("@")[1]);
	            selectedItemName = foodList[i].split("@")[0];
	            selectedCategory = foodList[i].split("@")[3];
	            log.info("Shop: Selected: "+selectedItemID + " " + selectedItemName);
	        }
	    }
	}

//	@Override
	public void stateChanged(ChangeEvent e) {
		AdaptiveSpinner2 sp = (AdaptiveSpinner2)e.getSource();
		sp.getValue();
		this.sizeSelection = (Integer) sp.getValue();
		// redraw week label
		this.updateSelectedDays();
		// store changes
		ShoppingData.storeSelectionChanges(this.sizeSelection, Utils.Dates.getTodayDate());
		butUpdateSL();
	}


	private String computeShoppingListDays(Calendar startDate, int size) {
		String selectedDays = "";
		String startMonth = Utils.Dates.getStringMonth(startDate.get(Calendar.MONTH));
		Calendar end = (Calendar) startDate.clone();
//		log.info("Shop: sizeDays: "+size);
		if (size <= 1) {
			selectedDays += startMonth + " " + startDate.get(Calendar.DAY_OF_MONTH);
		} else {
			end.add(Calendar.DAY_OF_MONTH, size-1);
			String endMonth = Utils.Dates.getStringMonth(end.get(Calendar.MONTH));
			selectedDays += startMonth + " " + startDate.get(Calendar.DAY_OF_MONTH) +
						" - " + endMonth + " " + end.get(Calendar.DAY_OF_MONTH);			
		}
		return selectedDays;
	}
	
}
