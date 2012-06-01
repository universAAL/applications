package na.services.myNutritionalProfile.ui.question.handler;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import na.services.myNutritionalProfile.ui.question.AnswerItem;
import na.widgets.spinner2.AdaptiveSpinner2;



public class NumberHandler implements ChangeListener {
	
	private AnswerItem myAnswer = null;
	
	public NumberHandler(AnswerItem a) {
		this.myAnswer = a;
	}
	
//	@Override
	public void stateChanged(ChangeEvent e) {
//		log.info("WeightHandler");
		/*
		 * Cuando cambia, modificar la answer
		 */
		AdaptiveSpinner2 source = (AdaptiveSpinner2) e.getSource();
		System.out.println("Min is: "+source.getMin()+ " max is: "+source.getMax());
		Integer value = (Integer)source.getValue();
		this.myAnswer.setValue(value.toString());
//		log.info(" value is: "+ value);
	}


}
