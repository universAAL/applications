package na.services.myNutritionalProfile.business;

/**
 * Métodos de alto nivel de la capa de negocio correspondiente a:
 * Nutritional Profile (Nutritional Advisor)
 */

import na.miniDao.Exercise;
import na.oasisUtils.ami.AmiConnector;
import na.oasisUtils.trustedSecurityNetwork.TSFConnector;
import na.utils.ServiceInterface;
import na.utils.OASIS_ServiceUnavailable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Business {
	private Log log = LogFactory.getLog(Business.class);
	public Exercise[] getMyPendingQuestionnaires() throws OASIS_ServiceUnavailable {
		AmiConnector ami = AmiConnector.getAMI();
		if (ami!=null) {
			String[] input = {TSFConnector.getInstance().getToken()};
			Exercise[] exercises = (Exercise[])ami.invokeOperation(ServiceInterface.DOMAIN_Nutrition, ServiceInterface.OP_GetPendingQuestionnaires, input, false);

			if (exercises != null) {
				log.info("NutritionalProfileBusiness, questionnaires found tamaño: " + exercises.length);
				return exercises;
			}
			else 
				log.info("NutritionalProfileBusiness, no questionnaires found.");
		} else {
			log.info("AMI Bundle not available!");
			throw new OASIS_ServiceUnavailable(OASIS_ServiceUnavailable.ERROR_ON_AMI_SIDE);
		}
		return null;
	}
	
	
	public Exercise getStartingQuestionnaire(int userQuestionnaireID, int exerciseID) throws OASIS_ServiceUnavailable {
		AmiConnector ami = AmiConnector.getAMI();
		if (ami!=null) {
			String[] input = {TSFConnector.getInstance().getToken(), String.valueOf(userQuestionnaireID), String.valueOf(exerciseID)};
			Exercise exercise = (Exercise)ami.invokeOperation(ServiceInterface.DOMAIN_Nutrition, ServiceInterface.OP_GetStartQuestionnaire, input, false);

			if (exercise != null) {
				log.info("NutritionalProfileBusiness, questionnaires found title: " + exercise.getQuestionnaire().getTitle());
				return exercise;
			}
			else 
				log.info("NutritionalProfileBusiness, no questionnaire found.");
		} else {
			log.info("AMI Bundle not available!");
			throw new OASIS_ServiceUnavailable(OASIS_ServiceUnavailable.ERROR_ON_AMI_SIDE);
		}
		return null;
	}


	public String[] setQuestionnaireAnswer(int exerciseID, int questionID, na.miniDao.Answer[] userAnswers) throws OASIS_ServiceUnavailable {
		AmiConnector ami = AmiConnector.getAMI();
		if (ami!=null) {
			Object[] input = {TSFConnector.getInstance().getToken(), String.valueOf(exerciseID), String.valueOf(questionID), userAnswers};
			String[] result = (String[])ami.invokeOperation(ServiceInterface.DOMAIN_Nutrition, ServiceInterface.OP_SetQuestionnaireAnswer, input, false);

			if (result != null) {
				log.info("NutritionalProfileBusiness, questionnaires updated result: " + result);
				return result;
			}
			else {
				log.info("NutritionalProfileBusiness, error updating database");
				return result;
			}
		} else {
			log.info("AMI Bundle not available!");
			throw new OASIS_ServiceUnavailable(OASIS_ServiceUnavailable.ERROR_ON_AMI_SIDE);
		}
	}


	public Exercise getQuestion(int exerciseID, int userQuestionnaireID, int questionID) throws OASIS_ServiceUnavailable {
		AmiConnector ami = AmiConnector.getAMI();
		if (ami!=null) {
			String[] input = {TSFConnector.getInstance().getToken(), String.valueOf(exerciseID), String.valueOf(userQuestionnaireID), String.valueOf(questionID)};
			Exercise exercise = (Exercise)ami.invokeOperation(ServiceInterface.DOMAIN_Nutrition, ServiceInterface.OP_GetNextQuestion, input, false);

			if (exercise != null) {
				log.info("NutritionalProfileBusiness, questionnaires found title: " + exercise.getQuestionnaire().getTitle());
				return exercise;
			}
			else 
				log.info("NutritionalProfileBusiness, no questionnaire found.");
		} else {
			log.info("AMI Bundle not available!");
			throw new OASIS_ServiceUnavailable(OASIS_ServiceUnavailable.ERROR_ON_AMI_SIDE);
		}
		return null;
	}

	public Exercise getPreviousQuestion(int exerciseID, int userQuestionnaireID) throws OASIS_ServiceUnavailable {
		AmiConnector ami = AmiConnector.getAMI();
		if (ami!=null) {
			String[] input = {TSFConnector.getInstance().getToken(), String.valueOf(exerciseID), String.valueOf(userQuestionnaireID)};
			Exercise exercise = (Exercise)ami.invokeOperation(ServiceInterface.DOMAIN_Nutrition, ServiceInterface.OP_GetPreviousQuestion, input, false);

			if (exercise != null) {
				log.info("NutritionalProfileBusiness, questionnaires found title: " + exercise.getQuestionnaire().getTitle());
				return exercise;
			}
			else 
				log.info("NutritionalProfileBusiness, no questionnaire found.");
		} else {
			log.info("AMI Bundle not available!");
			throw new OASIS_ServiceUnavailable(OASIS_ServiceUnavailable.ERROR_ON_AMI_SIDE);
		}
		return null;
	}
	
//	public LocalisedProfile getLocalisedProfile() throws OASIS_ServiceUnavailable {
//		AmiConnector ami = AmiConnector.getAMI();
//		if (ami!=null) {
//			String[] input = {TSFConnector.getInstance().getToken()};
//			LocalisedProfile profile = (LocalisedProfile)ami.invokeOperation(ServiceInterface.DOMAIN_Nutrition, ServiceInterface.OP_GetLocalisedProfile, input, false);
//
//			if (profile != null) {
//				log.info("NutritionalProfileBusiness, found profile: " + profile);
//				return profile;
//			}
//			else 
//				log.info("NutritionalProfileBusiness, no profile found");
//		} else {
//			log.info("AMI Bundle not available!");
//			throw new OASIS_ServiceUnavailable(OASIS_ServiceUnavailable.ERROR_ON_AMI_SIDE);
//		}
//		return null;
//	}
}
