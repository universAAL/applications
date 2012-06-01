package na.services.myNutritionalProfile.ui.question.handler;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import na.services.myNutritionalProfile.ui.question.AnswerItem;
import na.widgets.spinner.AdaptiveSpinner;
import na.widgets.spinner2.AdaptiveSpinner2;



public class HourHandler implements ChangeListener {
	
	private AnswerItem myAnswer = null;
	
	public HourHandler(AnswerItem a) {
		this.myAnswer = a;
	}
	
//	@Override
	public void stateChanged(ChangeEvent e) {
//		log.info("HourHandler");
		/*
		 * Cuando cambia, modificar la answer. hora-minuto
		 */
		AdaptiveSpinner2 source = (AdaptiveSpinner2) e.getSource();
		Integer numValue = (Integer)source.getValue();
//		log.info(" value is: "+ numValue);
		String fullTime = this.myAnswer.getValue();
		if (fullTime!=null && fullTime.length()==5) {
			String minutes = fullTime.substring(fullTime.length()-3);
			if (numValue <10)
				this.myAnswer.setValue("0"+ numValue + minutes );
			else
				this.myAnswer.setValue(numValue + minutes);
		} else {
		}
		
	}


}
