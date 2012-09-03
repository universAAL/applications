package na.services.myNutritionalProfile;


import na.miniDao.Exercise;
import na.services.myNutritionalProfile.business.Business;
import na.services.myNutritionalProfile.ui.MyProfilePanelRenewed;
import na.services.myNutritionalProfile.ui.PendingQuestionnairesPanel;
import na.services.myNutritionalProfile.ui.SubServiceFrame;
import na.services.myNutritionalProfile.ui.question.QuestionPanel;
import na.services.myNutritionalProfile.ui.question.QuestionnaireEnded;
import na.utils.OASIS_ServiceUnavailable;
import na.utils.Utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class NutriProfileSubServiceLauncher {

	public na.widgets.panel.AdaptivePanel canvas;
	private Business business = new Business();
	private SubServiceFrame subServiceFrame;
	private Log log = LogFactory.getLog(NutriProfileSubServiceLauncher.class);
	
	public void showSubService() {
		log.info("MyNutritionalProfile main window");

		this.subServiceFrame = new SubServiceFrame();
		this.subServiceFrame.getReady(this);
		this.canvas.add(this.subServiceFrame);
//		this.repaint(this.canvas);
		this.redraw();
		this.showUserNutritionalProfile();
//		this.showPendingQuestionnaires();
	}
	
	public void showUserNutritionalProfile() {
		this.emptyBox(); // clean and create box
//		MyProfilePanel prof = new MyProfilePanel();
		MyProfilePanelRenewed prof = new MyProfilePanelRenewed();
		prof.setBusiness(this.business);
		prof.getReady(this.subServiceFrame);
		this.subServiceFrame.content.add(prof);
//		this.repaint(this.subServiceFrame.content);
		this.redraw();
	}
	
	public void showPendingQuestionnaires() {
		this.emptyBox(); // clean and create box
		try {
			// get pending questionnaires
			Exercise[] exercises = business.getMyPendingQuestionnaires();
			if (exercises==null) {
				log.info("there are no questionnaires");
//				throw new OASIS_ServiceUnavailable(OASIS_ServiceUnavailable.ERROR_QUESTIONNAIRES_LoadingQuestionnaire);
			}
			// draw basic components
			PendingQuestionnairesPanel pending = new PendingQuestionnairesPanel();
			pending.getReady(this.subServiceFrame);
			// set basic info
			this.subServiceFrame.setExercises(exercises);
			// draw data
			this.subServiceFrame.drawPendingQuestionnaires(0);
			this.subServiceFrame.content.add(pending);
//			this.repaint(this.subServiceFrame.content);
			this.redraw();
		} catch (OASIS_ServiceUnavailable e) {
//			this.showServiceNotAvailableError();
			Utils.Errors.showError(this.subServiceFrame.content, e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void showStartQuestionnaire(int userQuestionnaireID, int exerciseID) {
		this.emptyBox(); // clean and create box
		try {
	//		get questionnaire start
			Exercise exercise = business.getStartingQuestionnaire(userQuestionnaireID, exerciseID);
			if (exercise==null) {
				log.error("error, ami returned an exercise= null");
				throw new OASIS_ServiceUnavailable(OASIS_ServiceUnavailable.ERROR_QUESTIONNAIRES_LoadingQuestionnaire);
			}
	//		draw basic components
			QuestionPanel question = new QuestionPanel();
			question.getReady(this.subServiceFrame);
	//		set basic info
	//		draw data
			this.subServiceFrame.drawQuestionnaire(exercise, true);
			this.subServiceFrame.content.add(question);
		} catch (OASIS_ServiceUnavailable e) {
			Utils.Errors.showError(this.subServiceFrame.content, e.getMessage());
			e.printStackTrace();
//			this.showServiceNotAvailableError();
		}
//		this.repaint(this.subServiceFrame.content);
		this.redraw();
	}
	
	public void showContinueQuestionnaire(int exerciseID, int userQuestionnaire, int newQuestion) {
		try {
			log.info("Continuing questionnaire: "+userQuestionnaire + "  exercise: "+exerciseID + " question: "+newQuestion);
			Exercise exercise = business.getQuestion(exerciseID, userQuestionnaire, newQuestion);
			this.subServiceFrame.answers.clear();
			if (exercise!=null) {
				this.emptyBox(); // clean and create box
	//			draw basic components
				QuestionPanel question = new QuestionPanel();
				question.getReady(this.subServiceFrame);
//				exercise.get
				this.subServiceFrame.drawQuestionnaire(exercise, false);
				this.subServiceFrame.content.add(question);
			} else {
				log.error("EL cuestionario es null :(");
				throw new OASIS_ServiceUnavailable(OASIS_ServiceUnavailable.ERROR_QUESTIONNAIRES_LoadingQuestionnaire);
			}
		} catch (OASIS_ServiceUnavailable e) {
//			this.showServiceNotAvailableError();
			Utils.Errors.showError(this.subServiceFrame.content, e.getMessage());
			e.printStackTrace();
		}
//		this.repaint(this.subServiceFrame.content);
		this.redraw();
	}
	
	public void loadAndshowNextQuestion(int exerciseID, int userQuestionnaire, int questionID, na.miniDao.Answer[] userAnswers) {
		log.trace("exerciseID: " + exerciseID +
				" questionID: "+questionID);
		/*
		 * 1. Actualizar BD
		 * 2. Obtener Siguiente
		 * 3. Dibujar
		 */
		try {
			String[] result = business.setQuestionnaireAnswer(exerciseID, questionID, userAnswers);
			if (result!= null ) {
				log.trace("setQuestionnaire result[0]: "+result[0]);			
				if (result[0].compareTo(Constants.ANSWER_SENT_SUCCESFULLY)==0) {
					int newQuestion = Integer.parseInt(result[1]);
					Exercise e = business.getQuestion(exerciseID, userQuestionnaire, newQuestion);
					if (e!=null) {
						this.emptyBox(); // clean and create box
						QuestionPanel question = new QuestionPanel();
						question.getReady(this.subServiceFrame);
						this.subServiceFrame.drawQuestionnaire(e, false);
						this.subServiceFrame.content.add(question);
					} else {
						log.error("EL cuestionario es null :(");
					}
					
				} else if (result[0].compareTo(Constants.END_OF_QUESTIONNAIRE)==0) {
					log.info("End of questionnaire reached!");
					this.emptyBox();
					QuestionnaireEnded ended = new QuestionnaireEnded();
					ended.getReady(subServiceFrame);
					this.subServiceFrame.content.add(ended);
				} else{
					log.error("There was an error sending your answer...");
					throw new OASIS_ServiceUnavailable(OASIS_ServiceUnavailable.ERROR_QUESTIONNAIRES_Sending_Answer);
				}
			} else {
				log.error("Null! There was an error sending your answer...");
				throw new OASIS_ServiceUnavailable(OASIS_ServiceUnavailable.ERROR_QUESTIONNAIRES_Sending_Answer);
			}
		} catch (OASIS_ServiceUnavailable e) {
//			this.showServiceNotAvailableError();
			Utils.Errors.showError(this.subServiceFrame.content, e.getMessage());
			e.printStackTrace();
		}
//		this.repaint(this.subServiceFrame.content);
		this.redraw();
	}
	
	public void loadAndShowPreviousQuestion(int exerciseID, int userQuestionnaire) {
		log.info("loading previous...");
		log.info("exerciseID: " + exerciseID + " userQuestionnaire: "+userQuestionnaire);

		Exercise e;
		try {
			e = business.getPreviousQuestion(exerciseID, userQuestionnaire);
			if (e!=null) {
				this.emptyBox();
//				draw basic components
				QuestionPanel question = new QuestionPanel();
				question.getReady(this.subServiceFrame);
				this.subServiceFrame.drawQuestionnaire(e, false);
				this.subServiceFrame.content.add(question);
			} else {
				log.error("EL cuestionario es null :(");
				throw new OASIS_ServiceUnavailable(OASIS_ServiceUnavailable.ERROR_QUESTIONNAIRES_Loading_Previous_Question);
			}
		} catch (OASIS_ServiceUnavailable er) {
//			this.showServiceNotAvailableError();
			Utils.Errors.showError(this.subServiceFrame.content, er.getMessage());
			er.printStackTrace();
		}
		
//		this.repaint(this.subServiceFrame.content);
		this.redraw();
	}
	
//	private void repaint(JPanel panel) {
//		panel.repaint();
//		panel.revalidate();
//	}
	
	private void redraw() {
    	this.canvas.validate();
		this.canvas.repaint();
	}
	
	private void emptyBox() {
		this.subServiceFrame.content.removeAll();
		this.subServiceFrame.content.validate();
		this.subServiceFrame.content.repaint();
	}
	
//	private void showServiceNotAvailableError() {
//		this.subServiceFrame.content.add(Utils.Errors.showServiceNotAvailableError(Messages.Questionnaire_ServiceNotAvailable));
//	}
	
}
