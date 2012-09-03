package na.services.myNutritionalProfile.ui.question.handler;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import na.services.myNutritionalProfile.ui.question.AnswerItem;
import na.widgets.spinner.AdaptiveSpinner;
import na.widgets.spinner2.AdaptiveSpinner2;



public class MinuteHandler implements ChangeListener {
	
	private AnswerItem myAnswer = null;
	
	public MinuteHandler(AnswerItem a) {
		this.myAnswer = a;
	}
	
//	@Override
	public void stateChanged(ChangeEvent e) {
//		log.info("MinuteHandler");
		/*
		 * Cuando cambia, modificar la answer. hora-minuto
		 */
		AdaptiveSpinner2 source = (AdaptiveSpinner2) e.getSource();
		Integer numValue = (Integer)source.getValue();
//		log.info(" value is: "+ numValue);
		String fullTime = this.myAnswer.getValue();
		if (fullTime!=null && fullTime.length()==5) {
			String hours = fullTime.substring(0,3);
			if (numValue <10)
				this.myAnswer.setValue(hours + "0"+ numValue );
			else
				this.myAnswer.setValue(hours + numValue );
//			log.info("The time is: "+ this.myAnswer.getValue());
		} else {
//			log.info("answers value = null or length != 5");
		}
	}


}
