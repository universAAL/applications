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

package org.universAAL.AALapplication.food_shopping.service.uiclient.dialogs.repository;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

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
import org.universAAL.middleware.ui.rdf.Label;
import org.universAAL.middleware.ui.rdf.MediaObject;
import org.universAAL.middleware.ui.rdf.Repeat;
import org.universAAL.middleware.ui.rdf.SimpleOutput;
import org.universAAL.middleware.ui.rdf.Submit;
import org.universAAL.ontology.Shopping.FoodItem;
import org.universAAL.ontology.profile.User;

/**
 * @author dimokas
 * 
 */
public class Repository extends UICaller {

    public final static String IMG_URL = "http://127.0.0.1:8080/resources/shopping/images/";
	private final static String window = "UIRepository#";
	static final String MY_UI_NAMESPACE = SharedResources.CLIENT_SHOPPING_UI_NAMESPACE + window;
	static final String SUBMISSION_GOBACK = MY_UI_NAMESPACE + "back";

	private Form mainDialog = null;
	private FoodItem[] fooditems = null;
	//private String active = ""; 
	
	public static final String REPOSITORY_ITEMS = MY_UI_NAMESPACE + "RepositoryItems";
	static final PropertyPath PROP_PATH_REPOSITORY_ITEMS = new PropertyPath(
			null, false, new String[] { REPOSITORY_ITEMS });

	public Repository(ModuleContext context) {
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
		}
		Utils.println(window + " Continues");
	}
	
	public void startMainDialog() {
		Utils.println(window + "startMainDialog");

		mainDialog = initMainDialog();
		
		UIRequest out = new UIRequest(SharedResources.testUser, mainDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}

	private Form initMainDialog() {
		Utils.println(window + "createMenusMainDialog");
		Form f = Form.newDialog("Repository", new Resource());
		
		ArrayList l = new ArrayList();
		for (int i=0; i<this.fooditems.length; i++)
			l.add(this.fooditems[i]);
		
		Repeat r = new Repeat(f.getIOControls(),new Label("Repository", (String) null),
				PROP_PATH_REPOSITORY_ITEMS,null, l);
		Group g = new Group(r, null, null, null, null);
		
		new SimpleOutput(g, new Label("Name", (String) null),
			    new PropertyPath(null, false,
				    new String[] { FoodItem.PROP_NAME }), null);
		new SimpleOutput(g, new Label("Size", (String) null),
				new PropertyPath(null, false,
				    new String[] { FoodItem.PROP_SIZE }), null);
		new SimpleOutput(g, new Label("Company", (String) null),
				new PropertyPath(null, false,
				    new String[] { FoodItem.PROP_COMPANY }), null);
		new SimpleOutput(g, new Label("Quantity", (String) null),
				new PropertyPath(null, false,
				    new String[] { FoodItem.PROP_QUANTITY }), null);
		new SimpleOutput(g, new Label("Insertion", (String) null),
				new PropertyPath(null, false,
				    new String[] { FoodItem.PROP_INSERTION_DATE }), null);
		new SimpleOutput(g, new Label("Expiration", (String) null),
				new PropertyPath(null, false,
				    new String[] { FoodItem.PROP_EXPIRATION_DATE }), null);
		new SimpleOutput(g, new Label("Tag", (String) null),
				new PropertyPath(null, false,
				    new String[] { FoodItem.PROP_TAGID }), null);

		f = submitButtons(f);
		
		return f;
	}
	
	public void startMainDialog(FoodItem[] lfi) {
		Utils.println(window + "updateMainDialog");

		updateMainDialog(lfi);
	}
	
	private void updateMainDialog(FoodItem[] lfi) {
		Utils.println(window + "createMenusUpdateDialog");
		Form f = Form.newDialog("Update Repository", new Resource());

		this.fooditems = new FoodItem[lfi.length];
		this.fooditems = lfi;
	}

/*
	public void startMainDialog(FoodItem[] lfi) {
		Utils.println(window + "updateMainDialog");

		mainDialog = updateMainDialog(lfi);
		
		UIRequest out = new UIRequest(SharedResources.testUser, mainDialog,
				LevelRating.middle, Locale.ENGLISH, PrivacyLevel.insensible);
		sendUIRequest(out);
	}
	
	private Form updateMainDialog(FoodItem[] lfi) {
		Utils.println(window + "createMenusUpdateDialog");
		Form f = Form.newDialog("Update Repository", new Resource());

		this.fooditems = new FoodItem[lfi.length];
		this.fooditems = lfi;
		ArrayList l = new ArrayList();
		for (int i=0; i<lfi.length; i++)
			l.add(lfi[i]);
		
		Repeat r = new Repeat(f.getIOControls(),new Label("Repository", (String) null),
				PROP_PATH_REPOSITORY_ITEMS,null, l);
		Group g = new Group(r, null, null, null, null);
		
		new SimpleOutput(g, new Label("Name", (String) null),
			    new PropertyPath(null, false,
				    new String[] { FoodItem.PROP_NAME }), null);
		new SimpleOutput(g, new Label("Size", (String) null),
				new PropertyPath(null, false,
				    new String[] { FoodItem.PROP_SIZE }), null);
		new SimpleOutput(g, new Label("Company", (String) null),
				new PropertyPath(null, false,
				    new String[] { FoodItem.PROP_COMPANY }), null);
		new SimpleOutput(g, new Label("Quantity", (String) null),
				new PropertyPath(null, false,
				    new String[] { FoodItem.PROP_QUANTITY }), null);
		new SimpleOutput(g, new Label("Insertion", (String) null),
				new PropertyPath(null, false,
				    new String[] { FoodItem.PROP_INSERTION_DATE }), null);
		new SimpleOutput(g, new Label("Expiration", (String) null),
				new PropertyPath(null, false,
				    new String[] { FoodItem.PROP_EXPIRATION_DATE }), null);
		new SimpleOutput(g, new Label("Tag", (String) null),
				new PropertyPath(null, false,
				    new String[] { FoodItem.PROP_TAGID }), null);

		f = submitButtons(f);
		
		return f;
	}
*/
/*	
	private Form updateMainDialog(FoodItem[] lfi) {
		Utils.println(window + "createMenusUpdateDialog");
		Form f = Form.newDialog("Update Repository", new Resource());
		output = "Name \t Size \t Company \t Quantity \t Insertion \t Expiration \t TagID \n";
		
        for (int i=0; i<lfi.length; i++){
        	if ((!lfi[i].getTagID().equals("000000000000000000")) && (!lfi[i].getName().equals("item"))){
	       		output += lfi[i].getName() + "\t";
	       		output += lfi[i].getSize() + "\t";
	       		output += lfi[i].getCompany() + "\t";
	       		output += lfi[i].getQuantity() + "\t";
	       		output += lfi[i].getInsDate() + "\t";
	       		output += lfi[i].getExpDate() + "\t";
	       		output += lfi[i].getTagID() + "\t";
	       		output += "\n";
	       		//System.out.println("*********************************************");
	       		//System.out.println(output);
        	}
        }

		SimpleOutput welcome = new SimpleOutput(f.getIOControls(), null, null, output);

		f = submitButtons(f);
		
		return f;
	}
*/
	
	private Form submitButtons(Form f){
		new Submit(f.getSubmits(), new Label("Go back", null), SUBMISSION_GOBACK);
		//new Submit(f.getSubmits(), new Label("", IMG_URL + "icons_back_small.png"), SUBMISSION_GOBACK);
				
		return f;
	}

	public void communicationChannelBroken() {
		Utils.println(window + " communicationChannelBroken");
	}

	public void dialogAborted(String dialogID) {
		Utils.println(window + " dialogAborted: " + dialogID);
	}

	@Override
	public void dialogAborted(String arg0, Resource arg1) {
		// TODO Auto-generated method stub
		
	}
}