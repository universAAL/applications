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
