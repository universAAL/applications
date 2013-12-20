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

package org.universAAL.AALapplication.food_shopping.service.uiclient.dialogs.shopping;

import java.awt.Desktop;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.universAAL.AALapplication.food_shopping.service.uiclient.FoodManagementClient;
import org.universAAL.AALapplication.food_shopping.service.uiclient.SharedResources;
import org.universAAL.AALapplication.food_shopping.service.uiclient.UIProvider;
import org.universAAL.AALapplication.food_shopping.service.uiclient.utils.Utils;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.util.BundleConfigHome;
import org.universAAL.middleware.owl.supply.LevelRating;
import org.universAAL.middleware.rdf.PropertyPath;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.ui.UICaller;
import org.universAAL.middleware.ui.UIRequest;
import org.universAAL.middleware.ui.UIResponse;
import org.universAAL.middleware.ui.owl.PrivacyLevel;
import org.universAAL.middleware.ui.rdf.Form;
import org.universAAL.middleware.ui.rdf.Group;
import org.universAAL.middleware.ui.rdf.InputField;
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.MediaObject;
import org.universAAL.middleware.ui.rdf.Select;
import org.universAAL.middleware.ui.rdf.Select1;
import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.ontology.Shopping.FoodItem;
import org.universAAL.ontology.nutrition.Ingredient;

/**
 * @author dimokas
 * 
 */
public class Shopping extends UICaller {

	// NA server location - http://158.42.166.200:8080/
    public final static String IMG_URL = "http://127.0.0.1:8080/resources/shopping/images/";
	private final static String window = "UIShopping#";

    static final String MY_UI_NAMESPACE = SharedResources.CLIENT_SHOPPING_UI_NAMESPACE + window;
    static final String SUBMISSION_CREATE = MY_UI_NAMESPACE + "create";
    static final String SUBMISSION_BROWSE = MY_UI_NAMESPACE + "browse";
    static final String SUBMISSION_EDIT = MY_UI_NAMESPACE + "edit";
    static final String SUBMISSION_SAVE = MY_UI_NAMESPACE + "save";
	static final String SUBMISSION_GOBACK = MY_UI_NAMESPACE + "back";
	static final String SUBMISSION_GOBACK_BROWSE_SHOPPING = MY_UI_NAMESPACE + "backbrowse";
	static final String SUBMISSION_GOBACK_EDIT_SHOPPING = MY_UI_NAMESPACE + "backedit";
	static final String SUBMISSION_BROWSE_SHOPPING_LIST = MY_UI_NAMESPACE + "browseshoppinglist";
	static final String SUBMISSION_SEND_SHOPPING_LIST = MY_UI_NAMESPACE + "sendshoppinglist";
	static final String SUBMISSION_PRINT_SHOPPING_LIST = MY_UI_NAMESPACE + "printshoppinglist";
	static final String SUBMISSION_REMOVE_SHOPPING_LIST = MY_UI_NAMESPACE + "removeshoppinglist";
	static final String SUBMISSION_EDIT_SHOPPING_LIST = MY_UI_NAMESPACE + "editshoppinglist";
	static final String SUBMISSION_ADD_NUTRITION_PRODUCT = MY_UI_NAMESPACE + "addnutritionproduct";
	static final String SUBMISSION_REMOVE_NUTRITION_PRODUCT = MY_UI_NAMESPACE + "removenutritionproduct";
	static final String SUBMISSION_CHANGE_SHOPPINGLIST_NAME = MY_UI_NAMESPACE + "changeshoppinglistname";
	static final String SUBMISSION_ADD_NUTRITION_PRODUCT_TO_LIST = MY_UI_NAMESPACE + "addproducttolist";
	static final String SUBMISSION_REMOVE_NUTRITION_PRODUCT_FROM_LIST = MY_UI_NAMESPACE + "removeproductfromlist";
	static final String SUBMISSION_NEW_SHOPPINGLIST_NAME = MY_UI_NAMESPACE + "newshoppinglistname";
	static final String SUBMISSION_TODAY_SHOPPING_LIST = MY_UI_NAMESPACE + "todayshoppinglist";
	static final String SUBMISSION_WEEKLY_SHOPPING_LIST = MY_UI_NAMESPACE + "weeklyshoppinglist";
	 
	public static final String USER_INPUT_SELECTED_ITEMS = MY_UI_NAMESPACE + "SelectedItems";
	static final PropertyPath PROP_PATH_SELECTED_ITEMS = new PropertyPath(
			null, false, new String[] { USER_INPUT_SELECTED_ITEMS });

	public static final String USER_INPUT_SHOPPING_LIST_NAME = MY_UI_NAMESPACE + "ShoppingListName";
	static final PropertyPath PROP_PATH_SHOPPING_LIST_NAME = new PropertyPath(
			null, false, new String[] { USER_INPUT_SHOPPING_LIST_NAME });

	public static final String USER_INPUT_SELECTED_LIST = MY_UI_NAMESPACE + "SelectedList";
	static final PropertyPath PROP_PATH_SELECTED_LIST = new PropertyPath(
			null, false, new String[] { USER_INPUT_SELECTED_LIST });

	public static final String USER_INPUT_SHOPPING_ITEMS = MY_UI_NAMESPACE + "ShoppingItems";
	static final PropertyPath PROP_PATH_SHOPPING_ITEMS = new PropertyPath(
			null, false, new String[] { USER_INPUT_SHOPPING_ITEMS });

	public static final String USER_INPUT_NUTRITION_PRODUCTS = MY_UI_NAMESPACE + "NutritionProducts";
	static final PropertyPath PROP_PATH_NUTRITION_PRODUCTS = new PropertyPath(
			null, false, new String[] { USER_INPUT_NUTRITION_PRODUCTS });
	
	private Form mainDialog = null;
	private Form createDialog = null;
	private Form browseDialog = null;
	private Form editDialog = null;
	private Form createNADialog = null;
	private String active = ""; 
	
	public static String[] createShoppingListItems = null;
	public static String[] shoppingListItems = null;
	public static String[] shoppingItems = null;
	public static FoodItem[] nutritionProducts = null;
	public static Hashtable shoppingLists = null;
	public static String shoppingListName = null;
	public static String previousShoppingListName = null;
	public static String uri = org.universAAL.ontology.Shopping.ShoppingList.MY_URI;
	public static Hashtable recipes = null;

	public Shopping(ModuleContext context) {
		super(context);
	}

	public void handleUIResponse(UIResponse uir) {
		Utils.println(window + " Response ID: " + uir.getSubmissionID());
		
		if (uir != null) {
			if (SUBMISSION_GOBACK.equals(uir.getSubmissionID())) {
				Utils.println(window+"  go back to previous screen");
				SharedResources.uIProvider.startMainDialog();
				return; 
			}
			if (SUBMISSION_GOBACK_BROWSE_SHOPPING.equals(uir.getSubmissionID())) {
				Utils.println(window+"  go back to previous screen");
		    	this.startBrowseMainDialog();
			}
			if (SUBMISSION_GOBACK_EDIT_SHOPPING.equals(uir.getSubmissionID())) {
				Utils.println(window+"  go back to previous screen");
		    	this.startEditDialog();
			}
			if (SUBMISSION_CREATE.equals(uir.getSubmissionID())) {
				this.active="create";
				this.startCreateDialog();
			} 
			else if (SUBMISSION_BROWSE.equals(uir.getSubmissionID())) {
		    	this.active="browse";
		    	this.startBrowseMainDialog();
			} 
			else if (SUBMISSION_BROWSE_SHOPPING_LIST.equals(uir.getSubmissionID())) {
		    	this.active="browse";
		    	this.shoppingListName = "";
		    	Object o1 = uir.getUserInput(PROP_PATH_SELECTED_LIST.getThePath());
				if (o1!=null){
					System.out.println("sln="+o1.toString());
					this.shoppingListName = o1.toString();
				}
		    	this.startBrowseSecondaryDialog(this.shoppingListName);
			}
			else if (SUBMISSION_SEND_SHOPPING_LIST.equals(uir.getSubmissionID())) {
		    	this.active="browse";
		    	this.shoppingListName = "";
		    	Object o1 = uir.getUserInput(PROP_PATH_SELECTED_LIST.getThePath());
				if (o1!=null){
					System.out.println("sln="+o1.toString());
					this.shoppingListName = o1.toString();
				}
		    	this.sendEmail(this.shoppingListName);
		    	this.startBrowseMainDialog();
			}
			else if (SUBMISSION_PRINT_SHOPPING_LIST.equals(uir.getSubmissionID())) {
		    	this.active="browse";
		    	this.shoppingListName = "";
		    	Object o1 = uir.getUserInput(PROP_PATH_SELECTED_LIST.getThePath());
				if (o1!=null){
					System.out.println("sln="+o1.toString());
					this.shoppingListName = o1.toString();
				}
		    	this.printShoppingList(this.shoppingListName);
		    	this.startBrowseMainDialog();
			}
			else if (SUBMISSION_REMOVE_SHOPPING_LIST.equals(uir.getSubmissionID())) {
				this.active="remove";
				if (this.shoppingListName!=null && (!this.shoppingListName.equals(""))){
					new Thread() {
						public void run() {
							// Remove shopping list
							boolean removeList = FoodManagementClient.removeShoppingList(uri, shoppingListName, 0);
							// Get the shopping lists
							setShoppingLists(FoodManagementClient.shoppingLists());
						}
					}.start();
				}
				this.removeShoppingList(this.shoppingListName);
		    	this.startBrowseMainDialog();
			} 
			else if (SUBMISSION_EDIT.equals(uir.getSubmissionID())) {
				this.active="edit";
				this.startEditDialog();
			}
			else if (SUBMISSION_EDIT_SHOPPING_LIST.equals(uir.getSubmissionID())) {
		    	this.active="edit";
		    	this.shoppingListName = "";
		    	Object o1 = uir.getUserInput(PROP_PATH_SELECTED_LIST.getThePath());
				if (o1!=null)
					this.shoppingListName = o1.toString();
		    	this.startEditSecondaryDialog(this.shoppingListName);
			}
			else if (SUBMISSION_ADD_NUTRITION_PRODUCT.equals(uir.getSubmissionID())) {
				this.active="edit";
				this.startAddNutritionDialog(this.shoppingListName);
			}
			else if (SUBMISSION_ADD_NUTRITION_PRODUCT_TO_LIST.equals(uir.getSubmissionID())) {
				this.active="edit";
				this.shoppingItems = null;
				Object o1 = uir.getUserInput(PROP_PATH_SHOPPING_ITEMS.getThePath());
				if (o1!=null){
					ArrayList alTmp = (ArrayList)o1;
					ArrayList al = new ArrayList();
					for (int i=0; i<alTmp.size(); i++){
						if (alTmp.get(i)!=null){
							al.add(alTmp.get(i));
						}
					}

					int previousItems=0;
					if (this.shoppingLists.containsKey(this.shoppingListName)){
						FoodItem[] itemList = (FoodItem[])shoppingLists.get(shoppingListName);
						previousItems=itemList.length;
					}

					shoppingItems = new String[al.size()+previousItems];
					FoodItem[] newItemList = new FoodItem[al.size()+previousItems];
					for (int i=0; i<al.size(); i++){
						shoppingItems[i] = (al.get(i)).toString();
						FoodItem fi = splitNutritionString(shoppingItems[i]);
						newItemList[i]=fi;
						System.out.println("*** shoppingItems="+shoppingItems[i]);
					}
					if (this.shoppingLists.containsKey(this.shoppingListName)){
						FoodItem[] itemList = (FoodItem[])shoppingLists.get(shoppingListName);
						for (int j=al.size(),m=0; j<shoppingItems.length; j++,m++){
							shoppingItems[j] = itemList[m].getCode() +", "+ itemList[m].getName()+", "+itemList[m].getSize()+", "+itemList[m].getCompany();
							newItemList[j]=itemList[m];
							System.out.println(itemList[m].getCode()+","+itemList[m].getName());
						}
						shoppingLists.put(shoppingListName, newItemList);
					}
				}
				if (shoppingListName!=null && shoppingItems!=null){
					new Thread() {
						public void run() {
							Calendar javaCalendar = null;
							String currentDate = "";
							javaCalendar = Calendar.getInstance();
							currentDate = javaCalendar.get(Calendar.YEAR) + "-" + (javaCalendar.get(Calendar.MONTH) + 1) + "-" + javaCalendar.get(Calendar.DATE);
							// Add the new product to shopping list
							boolean addList = FoodManagementClient.updateShoppingList(uri,  shoppingListName, currentDate, shoppingItems);
							// Get the shopping lists
							setShoppingLists(FoodManagementClient.shoppingLists());
						}
					}.start();
				}
				this.startEditSecondaryDialog(this.shoppingListName);
			}
			else if (SUBMISSION_REMOVE_NUTRITION_PRODUCT.equals(uir.getSubmissionID())) {
				this.active="edit";
				this.startRemoveNutritionDialog(this.shoppingListName);
			}
			else if (SUBMISSION_REMOVE_NUTRITION_PRODUCT_FROM_LIST.equals(uir.getSubmissionID())) {
				this.active="edit";
				this.shoppingItems = null;
				Object o1 = uir.getUserInput(PROP_PATH_SHOPPING_ITEMS.getThePath());
				if (o1!=null){
					ArrayList alTmp = (ArrayList)o1;
					ArrayList al = new ArrayList();
					for (int i=0; i<alTmp.size(); i++){
						if (alTmp.get(i)!=null){
							al.add(alTmp.get(i));
						}
					}
					int previousItems=0;
					if (this.shoppingLists.containsKey(this.shoppingListName)){
						FoodItem[] itemList = (FoodItem[])shoppingLists.get(shoppingListName);
						previousItems=itemList.length;
						System.out.println("previousItems="+previousItems);
						System.out.println("to be removed="+al.size());

						if (previousItems>0 && previousItems>al.size()){
							shoppingItems = new String[previousItems-(al.size())];
							FoodItem[] newItemList = new FoodItem[previousItems-al.size()];
							String[] toBeRemoved = new String[al.size()];
							for (int i=0; i<al.size(); i++){
								String codeID = getCode((al.get(i)).toString());
								toBeRemoved[i] = codeID;
								System.out.println("*** to be removed="+codeID);						
							}
							for (int j=0,m=0; j<itemList.length; j++){
								int cID = itemList[j].getCode();
								int found=0;
								for (int i=0; i<toBeRemoved.length; i++){
									if (cID==Integer.parseInt(toBeRemoved[i]))
										found=1;
								}
								if (found==0){
									newItemList[m]=itemList[j];
									System.out.println("+++ "+newItemList[m].getCode() +", "+ newItemList[m].getName()+", "+newItemList[m].getSize()+", "+newItemList[m].getCompany());
									shoppingItems[m]=newItemList[m].getCode() +", "+ newItemList[m].getName()+", "+newItemList[m].getSize()+", "+newItemList[m].getCompany();
									m++;
								}
									
							}
							if (newItemList.length>0){
								new Thread() {
									public void run() {
										Calendar javaCalendar = null;
										String currentDate = "";
										javaCalendar = Calendar.getInstance();
										currentDate = javaCalendar.get(Calendar.YEAR) + "-" + (javaCalendar.get(Calendar.MONTH) + 1) + "-" + javaCalendar.get(Calendar.DATE);
										// Add the new product to shopping list
										boolean addList = FoodManagementClient.updateShoppingList(uri,  shoppingListName, currentDate, shoppingItems);
										// Get the shopping lists
										setShoppingLists(FoodManagementClient.shoppingLists());
									}
								}.start();
							}
							shoppingLists.put(shoppingListName, newItemList);
						}
						else{ // There are no nutrition products. So, remove the entire shopping list
							System.out.println("+++ There are no food items. So, remove the entire shopping list.");
							new Thread() {
								public void run() {
									// Remove shopping list
									boolean removeList = FoodManagementClient.removeShoppingList(uri, shoppingListName, 0);
									// Get the shopping lists
									setShoppingLists(FoodManagementClient.shoppingLists());
								}
							}.start();
							
							removeShoppingList(shoppingListName);
						}
						
					}
				}
				this.startEditSecondaryDialog(this.shoppingListName);
			}
			else if (SUBMISSION_CHANGE_SHOPPINGLIST_NAME.equals(uir.getSubmissionID())) {
				this.active="edit";
				this.startChangeShoppingNameDialog(this.shoppingListName);
			}
			else if (SUBMISSION_NEW_SHOPPINGLIST_NAME.equals(uir.getSubmissionID())) {
				this.active="edit";
				shoppingItems = null;
				previousShoppingListName = null;
				FoodItem[] itemList = null;
				// Retrieve first the items of the shoppinglist. shoppingListName refers to previous shopping list name.
				if (this.shoppingLists.containsKey(this.shoppingListName)){
					previousShoppingListName = this.shoppingListName;
					itemList = (FoodItem[])shoppingLists.get(this.shoppingListName);
					this.shoppingItems = new String[itemList.length];
					for (int i=0; i<itemList.length; i++)
						this.shoppingItems[i]=itemList[i].getCode() +", "+ itemList[i].getName()+", "+itemList[i].getSize()+", "+itemList[i].getCompany();
				}
				
				Object o1 = uir.getUserInput(PROP_PATH_SHOPPING_LIST_NAME.getThePath());
				if (o1!=null)
					shoppingListName = o1.toString();
				if (shoppingListName!=null && shoppingItems!=null){
					new Thread() {
						public void run() {
							Calendar javaCalendar = null;
							String currentDate = "";
							javaCalendar = Calendar.getInstance();
							currentDate = javaCalendar.get(Calendar.YEAR) + "-" + (javaCalendar.get(Calendar.MONTH) + 1) + "-" + javaCalendar.get(Calendar.DATE);
							System.out.println("4. >>> current date="+currentDate);
							// Remove shopping list
							boolean removeList = FoodManagementClient.removeShoppingList(uri, previousShoppingListName, 0);
							// Add the new shopping list
							boolean addList = FoodManagementClient.addShoppingList(uri, shoppingListName, currentDate, shoppingItems);
							// Get the shopping lists
							setShoppingLists(FoodManagementClient.shoppingLists());
						}
					}.start();
					
					removeShoppingList(previousShoppingListName);
					shoppingLists.put(shoppingListName, itemList);
				}
				this.startEditSecondaryDialog(this.shoppingListName);
			} 
			else if (SUBMISSION_SAVE.equals(uir.getSubmissionID())) {
				this.active="save";
				Object o1 = uir.getUserInput(PROP_PATH_SHOPPING_LIST_NAME.getThePath());
				if (o1!=null)
					shoppingListName = o1.toString();
				Object o2 = uir.getUserInput(PROP_PATH_SELECTED_ITEMS.getThePath());
				if (o2!=null){
					ArrayList alTmp = (ArrayList)o2;
					ArrayList al = new ArrayList();
					for (int i=0; i<alTmp.size(); i++){
						if (alTmp.get(i)!=null){
							al.add(alTmp.get(i));
						}
					}

					shoppingItems = new String[al.size()];
					System.out.println("SHOPPING ITEMS="+shoppingItems);
					System.out.println("SHOPPING ITEMS SIZE="+al.size());
					for (int i=0; i<al.size(); i++){
						System.out.println(al.get(i));
						shoppingItems[i] = (al.get(i)).toString();
					}
				}
				if (shoppingListName!=null && shoppingItems!=null){
					new Thread() {
						public void run() {
							Calendar javaCalendar = null;
							String currentDate = "";
							javaCalendar = Calendar.getInstance();
							currentDate = javaCalendar.get(Calendar.YEAR) + "-" + (javaCalendar.get(Calendar.MONTH) + 1) + "-" + javaCalendar.get(Calendar.DATE);
							System.out.println("5. >>> current date="+currentDate);
							// Add the new shopping list
							boolean addList = FoodManagementClient.addShoppingList(uri, shoppingListName, currentDate, shoppingItems);
							// Get the shopping lists
							setShoppingLists(FoodManagementClient.shoppingLists());
						}
					}.start();
				}
				this.startCreateDialog();
			}
			
			else if (SUBMISSION_TODAY_SHOPPING_LIST.equals(uir.getSubmissionID())) {
				this.active="Today";
				this.startCreateNADialog(this.active);
			} 
		}
		Utils.println(window + " Continues");
	}
	
	public FoodItem splitNutritionString(String tmp){
		FoodItem fi = null;
		StringTokenizer st = new StringTokenizer(tmp,",");
		String code = "-1";
		String name = null;
		String size = null;
		String company = null;

		while (st.hasMoreTokens()){
			code = st.nextToken();
			name = st.nextToken();
			size = st.nextToken();
			company = st.nextToken();
			fi = new FoodItem(FoodItem.MY_URI,Integer.parseInt(code), name,size,company);
		}
		return fi;
	}	

	public String getCode(String tmp){
		StringTokenizer st = new StringTokenizer(tmp,",");
		String code = null;
		String name = null;
		String size = null;
		String company = null;

		while (st.hasMoreTokens()){
			code = st.nextToken();
			name = st.nextToken();
			size = st.nextToken();
			company = st.nextToken();
		}
		return code;
	}	

	public void startCreateNADialog(String status) {
		Utils.println(window + "startNADialog");
		createNADialog = createNAMainDialog(status);

		UIRequest out = new UIRequest(SharedResources.testUser, createNADialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}

	private Form createNAMainDialog(String status) {
		Utils.println(window + "createCreateNAMainDialog");
		Form f = Form.newDialog(status+" Shopping List", new Resource());

		if (this.recipes.size()>0){
			Enumeration en = recipes.keys();
			String output = "The Shopping List based on today menu contains:\n\n";
			while (en.hasMoreElements()){
				String key = (String)en.nextElement();
				System.out.println("### Recipe: "+key);
				Ingredient[] ingredients = (Ingredient[])recipes.get(key);
				for (int i=0; i<ingredients.length; i++)
					output += "- " + ingredients[i].getFood().getName() + "\n";
					//output += "- " + ingredients[i].getFood().getName() + "\t" + ingredients[i].getQuantity() + "\n";
			}
			SimpleOutput todayMenuPage = new SimpleOutput(f.getIOControls(), null, null, output);
		}
		else{
			SimpleOutput todayMenuPage = new SimpleOutput(f.getIOControls(), null, null, "There are no items in today menu yet.\n\n");
		}
		
		f = submitButtons(f);
		
		return f;
	}

	public void startCreateDialog() {
		Utils.println(window + "startCreateDialog");
		createDialog = createMainDialog();

		UIRequest out = new UIRequest(SharedResources.testUser, createDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}

	private Form createMainDialog() {
		Utils.println(window + "createCreateMainDialog");
		Form f = Form.newDialog("Create Shopping List", new Resource());

		Group g1 = new Group(f.getIOControls(), new Label("Create Shopping List",
			      (String) null), null, null, (Resource) null);
		Select ms1 = new Select(g1, new Label("Food Items",null), PROP_PATH_SELECTED_ITEMS, null, (Resource) null);
		ms1.generateChoices(this.getCreateShoppingListItems());
		InputField in1 = new InputField(g1, new Label("Shopping List Name",null), PROP_PATH_SHOPPING_LIST_NAME, null, (Resource) null);
		
		//Submit save = new Submit(g1, new Label("", IMG_URL+"icons_save_small.png"), SUBMISSION_SAVE);
		Submit save = new Submit(g1, new Label("Save", null), SUBMISSION_SAVE);
		save.addMandatoryInput(in1);
		save.addMandatoryInput(ms1);
/*
		Select ms1 = new Select(f.getIOControls(), new Label("Food Items",null), PROP_PATH_SELECTED_ITEMS, null, (Resource) null);
		ms1.generateChoices(this.getCreateShoppingListItems());
		InputField in1 = new InputField(f.getIOControls(), new Label("Shopping List Name",null), PROP_PATH_SHOPPING_LIST_NAME, null, (Resource) null);
		
		Submit save = new Submit(f.getIOControls(), new Label("", IMG_URL+"icons_save_small.png"), SUBMISSION_SAVE);
		save.addMandatoryInput(in1);
		save.addMandatoryInput(ms1);
*/
		f = submitButtons(f);
		
		return f;
	}

	public void startBrowseMainDialog() {
		Utils.println(window + "startBrowseMainDialog");
		browseDialog = browseMainDialog();

		UIRequest out = new UIRequest(SharedResources.testUser, browseDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}

	private Form browseMainDialog() {
		Utils.println(window + "createBrowseMainDialog");
		Form f = Form.newDialog("Browse Shopping List", new Resource());

		Group g1 = new Group(f.getIOControls(), new Label("Browse Shopping Lists",
			      (String) null), null, null, (Resource) null);
		Select1 s1 = new Select1(g1, new Label("Shopping Lists",null), PROP_PATH_SELECTED_LIST, null, (Resource) null);
		
		String[] sl = {""};
		if (this.getShoppingLists().size()>0){
			Enumeration en = (Enumeration)this.getShoppingLists().keys();
			sl = new String[this.getShoppingLists().size()];
			int i=0;
			while (en.hasMoreElements()){
				sl[i] = (String)en.nextElement();
				//System.out.println(">>> "+sl[i]);
				i++;
			}
		}
		s1.generateChoices(sl);

		Submit browse = new Submit(g1, new Label("Browse", null),SUBMISSION_BROWSE_SHOPPING_LIST);
		Submit send = new Submit(g1, new Label("Send Email", null), SUBMISSION_SEND_SHOPPING_LIST);
		Submit print = new Submit(g1, new Label("Print", null), SUBMISSION_PRINT_SHOPPING_LIST);

		//Submit browse = new Submit(g1, new Label("", IMG_URL+"icons_browse_small.png"), SUBMISSION_BROWSE_SHOPPING_LIST);
		//Submit send = new Submit(g1, new Label("", IMG_URL+"icons_mail_small.png"), SUBMISSION_SEND_SHOPPING_LIST);
		//Submit print = new Submit(g1, new Label("", IMG_URL+"icons_print_small.png"), SUBMISSION_PRINT_SHOPPING_LIST);

		browse.addMandatoryInput(s1);

		f = submitButtons(f);
		
		return f;
	}

	private void sendEmail(String shoppingListName) {
		String[] slItems = {""};
		String body = "Product, Size, Company \n\n";
		if (this.getShoppingLists().containsKey(shoppingListName)){
			FoodItem[] fi = (FoodItem[])this.getShoppingLists().get(shoppingListName);
			slItems = new String[fi.length];
			for (int i=0; i<fi.length; i++){
				slItems[i] = fi[i].getName() + ", " + fi[i].getSize() + ", " + fi[i].getCompany();
				body+=slItems[i]+"\n"; 
			}
		}
		body = body.replaceAll(" ", "%20");
		body = body.replaceAll("\n", "%0A");
		shoppingListName = shoppingListName.replaceAll(" ", "%20");
		try {
			Desktop.getDesktop().mail( new URI("mailto:myaddress@somewhere.com?subject="+shoppingListName+"&body="+body ) );
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void printShoppingList(String shoppingListName) {
		String[] slItems = {""};
		String body = "";
		if (this.getShoppingLists().containsKey(shoppingListName)){
			FoodItem[] fi = (FoodItem[])this.getShoppingLists().get(shoppingListName);
			slItems = new String[fi.length];
			for (int i=0; i<fi.length; i++){
				slItems[i] = fi[i].getName() + ", " + fi[i].getSize() + ", " + fi[i].getCompany();
				body+=slItems[i]+" \n"; 
			}
		}
        Printing p = new Printing();
        p.setData(body);
		PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(p);
        boolean ok = job.printDialog();
        if (ok) {
            try {
                 job.print();
            } catch (PrinterException ex) {
             /* The job did not successfully complete */
            }
        }
	}

	
	public void startBrowseSecondaryDialog(String shoppingListName) {
		Utils.println(window + "startBrowseSecondaryDialog");
		browseDialog = browseSecondaryDialog(shoppingListName);

		UIRequest out = new UIRequest(SharedResources.testUser, browseDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}

	private Form browseSecondaryDialog(String shoppingListName) {
		Utils.println(window + "createBrowseSecondaryDialog");
		Form f = Form.newDialog("Browse Shopping List", new Resource());

		Group g1 = new Group(f.getIOControls(), new Label("Shopping List Items",
			      (String) null), null, null, (Resource) null);
		
		Select ms1 = new Select(g1, new Label("Food Items",null), PROP_PATH_SHOPPING_ITEMS, null, (Resource) null);
		String[] slItems = {""};
		if (this.getShoppingLists().containsKey(shoppingListName)){
			FoodItem[] fi = (FoodItem[])this.getShoppingLists().get(shoppingListName);
			slItems = new String[fi.length];
			for (int i=0; i<fi.length; i++)
				slItems[i] = fi[i].getName() + ", " + fi[i].getSize() + ", " + fi[i].getCompany();
		}
		ms1.generateChoices(slItems);

		//Submit remove = new Submit(g1, new Label("", IMG_URL+"icons_remove_small.png"), SUBMISSION_REMOVE_SHOPPING_LIST);
		Submit remove = new Submit(g1, new Label("Remove", null), SUBMISSION_REMOVE_SHOPPING_LIST);
		
		//new Submit(f.getSubmits(), new Label("", IMG_URL+"icons_back_small.png"), SUBMISSION_GOBACK_BROWSE_SHOPPING);
		new Submit(f.getSubmits(), new Label("Go Back", null), SUBMISSION_GOBACK_BROWSE_SHOPPING);
		
		return f;
	}

	public void startEditDialog() {
		Utils.println(window + "startEditDialog");
		editDialog = editMainDialog();

		UIRequest out = new UIRequest(SharedResources.testUser, editDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}

	private Form editMainDialog() {
		Utils.println(window + "createEditMainDialog");
		Form f = Form.newDialog("Edit Shopping List", new Resource());

		Group g1 = new Group(f.getIOControls(), new Label("Edit Shopping List",
			      (String) null), null, null, (Resource) null);
		Select1 s1 = new Select1(g1, new Label("Shopping Lists",null), PROP_PATH_SELECTED_LIST, null, (Resource) null);
		
		String[] sl = {""};
		if (this.getShoppingLists().size()>0){
			Enumeration en = (Enumeration)this.getShoppingLists().keys();
			sl = new String[this.getShoppingLists().size()];
			int i=0;
			while (en.hasMoreElements()){
				sl[i] = (String)en.nextElement();
				i++;
			}
		}
		s1.generateChoices(sl);

		//Submit select = new Submit(g1, new Label("", IMG_URL+"icons_select_small.png"),SUBMISSION_EDIT_SHOPPING_LIST);
		Submit select = new Submit(g1, new Label("Edit Shopping List", null),SUBMISSION_EDIT_SHOPPING_LIST);
		select.addMandatoryInput(s1);

		f = submitButtons(f);
		
		return f;
	}

	public void startEditSecondaryDialog(String shoppingListName) {
		Utils.println(window + "startEditSecondaryDialog");
		editDialog = editSecondaryDialog(shoppingListName);

		UIRequest out = new UIRequest(SharedResources.testUser, editDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}

	private Form editSecondaryDialog(String shoppingListName) {
		Utils.println(window + "createEditSecondaryDialog");
		Form f = Form.newDialog("Edit Shopping List", new Resource());

		Group g1 = new Group(f.getIOControls(), new Label("Edit Shopping List: "+this.shoppingListName,
			      (String) null), null, null, (Resource) null);

		Select ms1 = new Select(g1, new Label("Food Items: ",null), PROP_PATH_SHOPPING_ITEMS, null, (Resource) null);
		String[] slItems = {""};
		if (this.getShoppingLists().containsKey(shoppingListName)){
			FoodItem[] fi = (FoodItem[])this.getShoppingLists().get(shoppingListName);
			slItems = new String[fi.length];
			for (int i=0; i<fi.length; i++)
				slItems[i] = fi[i].getCode() + ", " + fi[i].getName() + ", " + fi[i].getSize() + ", " + fi[i].getCompany();
		}
		ms1.generateChoices(slItems);
/*
		new Submit(f.getSubmits(), new Label("", IMG_URL+"icons_foodAdd_small.png"), SUBMISSION_ADD_NUTRITION_PRODUCT);
		new Submit(f.getSubmits(), new Label("", IMG_URL+"icons_foodRemove_small.png"), SUBMISSION_REMOVE_NUTRITION_PRODUCT);
		new Submit(f.getSubmits(), new Label("", IMG_URL+"icons_shLstName_small.png"), SUBMISSION_CHANGE_SHOPPINGLIST_NAME);
		new Submit(f.getSubmits(), new Label("", IMG_URL+"icons_back_small.png"), SUBMISSION_GOBACK_EDIT_SHOPPING);
*/
		new Submit(f.getSubmits(), new Label("Add Food Item", null), SUBMISSION_ADD_NUTRITION_PRODUCT);
		new Submit(f.getSubmits(), new Label("Remove Food Item", null), SUBMISSION_REMOVE_NUTRITION_PRODUCT);
		new Submit(f.getSubmits(), new Label("Shopping List Name", null), SUBMISSION_CHANGE_SHOPPINGLIST_NAME);
		new Submit(f.getSubmits(), new Label("Go Back", null), SUBMISSION_GOBACK_EDIT_SHOPPING);

		return f;
	}

	public void startRemoveNutritionDialog(String shoppingListName) {
		Utils.println(window + "startRemoveNutritionDialog");
		editDialog = removeNutritionDialog(shoppingListName);

		UIRequest out = new UIRequest(SharedResources.testUser, editDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}

	private Form removeNutritionDialog(String shoppingListName) {
		Utils.println(window + "createRemoveNutritionDialog");
		Form f = Form.newDialog("Remove Food Item", new Resource());

		Group g1 = new Group(f.getIOControls(), new Label("Remove Food Items",
			      (String) null), null, null, (Resource) null);

		Select ms1 = new Select(g1, new Label("Shopping List's Food Items: ",null), PROP_PATH_SHOPPING_ITEMS, null, (Resource) null);
		String[] slItems = {""};
		if (this.getShoppingLists().containsKey(shoppingListName)){
			FoodItem[] fi = (FoodItem[])this.getShoppingLists().get(shoppingListName);
			slItems = new String[fi.length];
			for (int i=0; i<fi.length; i++)
				slItems[i] = fi[i].getCode() + ", " + fi[i].getName() + ", " + fi[i].getSize() + ", " + fi[i].getCompany();
		}
		ms1.generateChoices(slItems);

		//Submit remove = new Submit(g1, new Label("", IMG_URL+"icons_remove_small.png"), SUBMISSION_REMOVE_NUTRITION_PRODUCT_FROM_LIST);
		Submit remove = new Submit(g1, new Label("Remove", null), SUBMISSION_REMOVE_NUTRITION_PRODUCT_FROM_LIST);
		remove.addMandatoryInput(ms1);
		
		new Submit(f.getSubmits(), new Label("Add Food Item", null), SUBMISSION_ADD_NUTRITION_PRODUCT);
		new Submit(f.getSubmits(), new Label("Remove Food Item", null), SUBMISSION_REMOVE_NUTRITION_PRODUCT);
		new Submit(f.getSubmits(), new Label("Shopping List Name", null), SUBMISSION_CHANGE_SHOPPINGLIST_NAME);
		new Submit(f.getSubmits(), new Label("Go Back", null), SUBMISSION_GOBACK_EDIT_SHOPPING);
/*
		new Submit(f.getSubmits(), new Label("", IMG_URL+"icons_foodAdd_small.png"), SUBMISSION_ADD_NUTRITION_PRODUCT);
		new Submit(f.getSubmits(), new Label("", IMG_URL+"icons_foodRemove_small.png"), SUBMISSION_REMOVE_NUTRITION_PRODUCT);
		new Submit(f.getSubmits(), new Label("", IMG_URL+"icons_shLstName_small.png"), SUBMISSION_CHANGE_SHOPPINGLIST_NAME);
		new Submit(f.getSubmits(), new Label("", IMG_URL+"icons_back_small.png"), SUBMISSION_GOBACK_EDIT_SHOPPING);
*/
		return f;
	}

	public void startChangeShoppingNameDialog(String shoppingListName) {
		Utils.println(window + "startChangeShoppingNameDialog");
		editDialog = changeShoppingNameDialog(shoppingListName);

		UIRequest out = new UIRequest(SharedResources.testUser, editDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}

	private Form changeShoppingNameDialog(String shoppingListName) {
		Utils.println(window + "createChangeShoppingNameDialog");
		Form f = Form.newDialog("Modify Shopping List Name", new Resource());

		Group g1 = new Group(f.getIOControls(), new Label("Modify Shopping List Name",
			      (String) null), null, null, (Resource) null);

		InputField in1 = new InputField(g1, new Label("Shopping List Name: ",null), PROP_PATH_SHOPPING_LIST_NAME, null, (Resource) null);
		
		//Submit save = new Submit(g1, new Label("", IMG_URL+"icons_save_small.png"), SUBMISSION_NEW_SHOPPINGLIST_NAME);
		Submit save = new Submit(g1, new Label("Save", null), SUBMISSION_NEW_SHOPPINGLIST_NAME);
		save.addMandatoryInput(in1);

		new Submit(f.getSubmits(), new Label("Add Food Item", null), SUBMISSION_ADD_NUTRITION_PRODUCT);
		new Submit(f.getSubmits(), new Label("Remove Food Item", null), SUBMISSION_REMOVE_NUTRITION_PRODUCT);
		new Submit(f.getSubmits(), new Label("Shopping List Name", null), SUBMISSION_CHANGE_SHOPPINGLIST_NAME);
		new Submit(f.getSubmits(), new Label("Go Back", null), SUBMISSION_GOBACK_EDIT_SHOPPING);

/*
		new Submit(f.getSubmits(), new Label("", IMG_URL+"icons_foodAdd_small.png"), SUBMISSION_ADD_NUTRITION_PRODUCT);
		new Submit(f.getSubmits(), new Label("", IMG_URL+"icons_foodRemove_small.png"), SUBMISSION_REMOVE_NUTRITION_PRODUCT);
		new Submit(f.getSubmits(), new Label("", IMG_URL+"icons_shLstName_small.png"), SUBMISSION_CHANGE_SHOPPINGLIST_NAME);
		new Submit(f.getSubmits(), new Label("", IMG_URL+"icons_back_small.png"), SUBMISSION_GOBACK_EDIT_SHOPPING);
*/
		return f;
	}

	public void startAddNutritionDialog(String shoppingListName) {
		Utils.println(window + "startAddNutritionDialog");
		editDialog = addNutritionDialog(shoppingListName);

		UIRequest out = new UIRequest(SharedResources.testUser, editDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}

	private Form addNutritionDialog(String shoppingListName) {
		Utils.println(window + "createAddNutritionDialog");
		Form f = Form.newDialog("Add Food Item", new Resource());

		Group g1 = new Group(f.getIOControls(), new Label("Add Food Items",
			      (String) null), null, null, (Resource) null);

		Select ms1 = new Select(g1, new Label("Food Items: ",null), PROP_PATH_SHOPPING_ITEMS, null, (Resource) null);
		String[] fItems = {""};
		if (this.getNutritionProducts().length>0){
			FoodItem[] fi = (FoodItem[])this.getNutritionProducts();
			fItems = new String[fi.length];
			for (int i=0; i<fi.length; i++)
				fItems[i] = fi[i].getCode()+", "+fi[i].getName()+", "+fi[i].getSize()+", "+fi[i].getCompany();
		}
		ms1.generateChoices(fItems);

		//Submit add = new Submit(g1, new Label("", IMG_URL+"icons_add_small.png"), SUBMISSION_ADD_NUTRITION_PRODUCT_TO_LIST);		
		Submit add = new Submit(g1, new Label("Add", null), SUBMISSION_ADD_NUTRITION_PRODUCT_TO_LIST);		
		add.addMandatoryInput(ms1);

		new Submit(f.getSubmits(), new Label("Add Food Item", null), SUBMISSION_ADD_NUTRITION_PRODUCT);
		new Submit(f.getSubmits(), new Label("Remove Food Item", null), SUBMISSION_REMOVE_NUTRITION_PRODUCT);
		new Submit(f.getSubmits(), new Label("Shopping List Name", null), SUBMISSION_CHANGE_SHOPPINGLIST_NAME);
		new Submit(f.getSubmits(), new Label("Go Back", null), SUBMISSION_GOBACK_EDIT_SHOPPING);

/*
		new Submit(f.getSubmits(), new Label("", IMG_URL+"icons_foodAdd_small.png"), SUBMISSION_ADD_NUTRITION_PRODUCT);
		new Submit(f.getSubmits(), new Label("", IMG_URL+"icons_foodRemove_small.png"), SUBMISSION_REMOVE_NUTRITION_PRODUCT);
		new Submit(f.getSubmits(), new Label("", IMG_URL+"icons_shLstName_small.png"), SUBMISSION_CHANGE_SHOPPINGLIST_NAME);
		new Submit(f.getSubmits(), new Label("", IMG_URL+"icons_back_small.png"), SUBMISSION_GOBACK_EDIT_SHOPPING);
*/
		return f;
	}
	
	public void startMainDialog() {
		Utils.println(window + "startMainDialog");
		if (mainDialog == null){
			mainDialog = initMainDialog();
		}
		
		new Thread() {
			public void run() {
				// Get today menu from NA
				boolean todayMenu = FoodManagementClient.getTodayMenu();
			}
		}.start();
		
		UIRequest out = new UIRequest(SharedResources.testUser, mainDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}

	private Form initMainDialog() {
		Utils.println(window + "createMenusMainDialog");
		Form f = Form.newDialog("Shopping List", new Resource());

		SimpleOutput welcome = new SimpleOutput(f.getIOControls(), null, null, "Welcome to the Shopping List application.\n\n" +
		"- Press the button \"Create Shopping List\" to create a shopping list.\n"+
		"- Press the button \"Browse Shopping List\" to browse or remove a shopping list.\n"+
		"- Press the button \"Edit Shopping List\" to edit a shopping list.\n"+
		"- Press the button \"Today Shopping List\" to create a shopping list based on today menu from Nutritional Advisor.\n"+
		"- Press the button \"Back\" to return to previous page.\n");		
		
		f = submitButtons(f);
		
		return f;
	}

	private Form submitButtons(Form f){
		new Submit(f.getSubmits(), new Label("Create Shopping List", null),SUBMISSION_CREATE);
		new Submit(f.getSubmits(), new Label("Browse Shopping List", null), SUBMISSION_BROWSE);
		new Submit(f.getSubmits(), new Label("Edit Shopping List", null), SUBMISSION_EDIT);
		new Submit(f.getSubmits(), new Label("Today Shopping List", null), SUBMISSION_TODAY_SHOPPING_LIST);
		new Submit(f.getSubmits(), new Label("Go Back", null), SUBMISSION_GOBACK);
		
/*
		new Submit(f.getSubmits(), new Label("", IMG_URL+"icons_shLstCreate_small.png"),SUBMISSION_CREATE);
		new Submit(f.getSubmits(), new Label("", IMG_URL+"icons_shLstBrowse_small.png"), SUBMISSION_BROWSE);
		new Submit(f.getSubmits(), new Label("", IMG_URL+"icons_shLstEdit_small.png"), SUBMISSION_EDIT);
		new Submit(f.getSubmits(), new Label("", IMG_URL+"icon_list_today.png"), SUBMISSION_TODAY_SHOPPING_LIST);
		new Submit(f.getSubmits(), new Label("", IMG_URL+"icons_back_small.png"), SUBMISSION_GOBACK);
*/
		return f;
	}

	public void communicationChannelBroken() {
		Utils.println(window + " communicationChannelBroken");
	}

	@Override
	public void dialogAborted(String dialogID) {
		Utils.println(window + " dialogAborted: " + dialogID);
	}

	public static Hashtable getShoppingLists() {
		return shoppingLists;
	}

	public static void setShoppingLists(Hashtable shoppingLists) {
		Shopping.shoppingLists = shoppingLists;
	}

	public static void removeShoppingList(String shoppingListName) {
		if (shoppingLists.containsKey(shoppingListName))
			shoppingLists.remove(shoppingListName);
	}

	public static String[] getShoppingListItems() {
		return shoppingListItems;
	}

	public void setShoppingListItems(String[] shoppingListItems) {
		this.shoppingListItems = shoppingListItems;
	}

	public static String[] getCreateShoppingListItems() {
		return createShoppingListItems;
	}

	public static void setCreateShoppingListItems(String[] createShoppingListItems) {
		Shopping.createShoppingListItems = createShoppingListItems;
	}

	public static FoodItem[] getNutritionProducts() {
		return nutritionProducts;
	}

	public static void setNutritionProducts(FoodItem[] nutritionProducts) {
		Shopping.nutritionProducts = nutritionProducts;
	}

}
