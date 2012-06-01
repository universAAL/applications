package na.services.myNutritionalProfile.ui.question;

import na.widgets.radiobutton.AdaptiveRadioButton;

@SuppressWarnings("serial")
public class MyRadioButton extends AdaptiveRadioButton {
	private int ID;
	private Object source;
	private AnswerItem reference;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public void setSource(Object source) {
		this.source = source;
	}

	public Object getSource() {
		return source;
	}

	public void setReference(AnswerItem reference) {
		this.reference = reference;
	}

	public AnswerItem getReference() {
		return reference;
	}
	
	
}
