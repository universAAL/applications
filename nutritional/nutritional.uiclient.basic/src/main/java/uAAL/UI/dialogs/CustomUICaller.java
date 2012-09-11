/*
	Copyright 2011-2012 Itaca-TSB, http://www.tsb.upv.es
	Tecnologías para la Salud y el Bienestar
	
	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	  http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package uAAL.UI.dialogs;

import nna.utils.Utils;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.ui.UICaller;
import org.universAAL.middleware.ui.rdf.Form;

public abstract class CustomUICaller extends UICaller {

	private ModuleContext myContext;
	
	protected CustomUICaller(ModuleContext context) {
		super(context);
		this.myContext = context;
	}

	/**
	 * Creates and launches the main form. Implement this method so a child Dialog can return to the parent Dialog.
	 * Hecgamar
	 */
	public abstract void callMainForm();
	
	private Form parentForm;
	
	/**
	 * Return the Form who called it. Hecgamar
	 */
	public abstract Form getMainForm();
	
	public void backToMainMenusDialog(ModuleContext context) {
		Utils.println("show Main MainDialog");
		UIMainProvider main = new UIMainProvider(context);
		main.startMainDialog();
	}
	
	public ModuleContext getContext() {
		return this.myContext;
	}
}
