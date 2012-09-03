package na.services.myNutritionalProfile.ui.question;

import na.widgets.checkbox.AdaptiveCheckBox;

@SuppressWarnings("serial")
public class MyCheckBox extends AdaptiveCheckBox {
	
	private int ID;
	private AnswerItem reference;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
	
	public void setReference(AnswerItem reference) {
		this.reference = reference;
	}

	public AnswerItem getReference() {
		return reference;
	}
}
