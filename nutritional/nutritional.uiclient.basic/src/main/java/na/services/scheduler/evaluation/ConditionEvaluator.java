package na.services.scheduler.evaluation;

import java.util.List;

import na.miniDao.OtherCondition;
import na.oasisUtils.ami.AmiConnector;
import na.oasisUtils.profile.ProfileConnector;
import na.services.scheduler.ExtraAdvise;
import na.utils.OASIS_ServiceUnavailable;
import na.utils.ServiceInterface;
import na.utils.Setup;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ConditionEvaluator {
	private Log log = LogFactory.getLog(ConditionEvaluator.class);
	public static final int NUTRITION_CONDITION_Valid = 1;
	public static final int NUTRITION_CONDITION_InValid = 2;
	
	public static final int TYPE_STRING = 1;
	public static final int TYPE_INTEGER = 2;
	public static final int TYPE_DATE = 3;
	public static final int TYPE_TIME = 4;
	public static final int TYPE_DOUBLE = 5;
	public static final int TYPE_BOOLEAN = 6;
	
	public static final int COMPARE_TYPE_UNIC_VALUE = 1;
	public static final int COMPARE_TYPE_BELONGS_TO_LIST = 2;
	public static final int COMPARE_TYPE_SAME_LIST = 3;
	
	
	/**
	 * Check conditions on the item.
	 * 
	 * @param item the item
	 * @return the int
	 */
	public int checkConditions(ExtraAdvise item) {
		OtherCondition o = item.advise.getOtherConditions();
//		log.info("Cond: checking: "+item.advise.getTitle());
		if (o.isHigh_temperature()) {
//			log.info("Cond: Comprobar temperatura");
			// get temperature from Environmental Control
			AmiConnector ami = AmiConnector.getAMI();
			if (ami != null) {
				String[] input = {"0"};
				try {
					String temp = (String) ami.invokeOperation(ServiceInterface.DOMAIN_EnvironmentalControl, ServiceInterface.OP_GetTemperature, input, false);
					if (temp!=null) {
						Double tempValue = new Double(temp);
						if (tempValue!=null) {
							double maxTemp = Setup.getMaxTemperatureAllowed();
							if (tempValue> maxTemp) {
								log.info("Cond: TEMPERATURE IN ROOM IS: "+tempValue + " over: "+maxTemp);
							} else {
								log.info("Cond: TEMPERATURE IS BELOW "+maxTemp+": "+tempValue);
								return NUTRITION_CONDITION_InValid;
							}
						} else {
							log.error("Temperature is null");
							return NUTRITION_CONDITION_InValid;
						}
					} else {
						log.error("Temperature is null");
						return NUTRITION_CONDITION_InValid;
					}
				} catch (OASIS_ServiceUnavailable e) {
					log.error("Couldn't get temperature...");
					e.printStackTrace();
					return NUTRITION_CONDITION_InValid;
				}
			} else {
				log.warn("Couldn't connect to ami!");
			}
		}
		if (o.isConsumes_alcohol()) {
			log.info("Cond: Comprobar consumo de alcohol");
			String result = ProfileConnector.getInstance().getSmokes();
			if (result.compareTo("true")==0) {
				// continue
				log.info("Cond: This patient is a smoker");
			} else {
				return NUTRITION_CONDITION_InValid;
			}
		}
		if (o.isSmokes()) {
			log.info("Cond: Comprobar si fuma");
			String result = ProfileConnector.getInstance().getConsumesAlcohol();
			if (result.compareTo("true")==0) {
				// continue
				log.info("Cond: This patient drinks alcohol");
			} else {
				return NUTRITION_CONDITION_InValid;
			}
		}
		if (o.isHas_highFatigue() == true) {
//			log.info("Cond: checking fatigue");
			String fatigue_level = ProfileConnector.getInstance().getHasHighActivityLevel();
			if (fatigue_level!=null && fatigue_level.compareTo("high")==0) {
//			if (this.isDiseaseInList(result, NP.Nutrition.ClinicalHistory.CHRONIC_DISEASE_Value_Cholesterol)==true) {
				log.info("Cond: Patient has high fatigue: "+fatigue_level);
			} else {
				log.info("Cond: Couldn't find high fatigue for this patient: "+fatigue_level);
				return NUTRITION_CONDITION_InValid;
			}
		}
		if (o.isHas_chronic_disease()) {
//			log.info("Cond: Comprobar si tiene alguna enfermedad cronica");
//			List<String> result = ProfileConnector.getInstance().getChronic_diseases();
//			if (result != null) {
				if (o.isHas_cholesterol() == true) {
//					log.info("Cond: checking cholesterol");
					if (ProfileConnector.getInstance().getHasExternalCholesterol()) {
//					if (this.isDiseaseInList(result, NP.Nutrition.ClinicalHistory.CHRONIC_DISEASE_Value_Cholesterol)==true) {
						log.info("Cond: Patient has cholesterol and the advise too! :D");
					} else {
						return NUTRITION_CONDITION_InValid;
					}
				}
				//TODO restore
//				if (o.isHas_cardiovascular() == true) {
//					if (this.isDiseaseInList(result, NP.Nutrition.ClinicalHistory.CHRONIC_DISEASE_Value_Cardiovascular)==true) {
//						log.info("Cond: Patient has cardiovascular and the advise too! :D");
//					} else {
//						return NUTRITION_CONDITION_InValid;
//					}
//				}
//				if (o.isHas_diabetes() == true) {
//					if (this.isDiseaseInList(result, NP.Nutrition.ClinicalHistory.CHRONIC_DISEASE_Value_Diabetes)==true) {
//						log.info("Cond: Patient has diabetes and the advise too! :D");
//					} else {
//						return NUTRITION_CONDITION_InValid;
//					}
//				}
//				if (o.isHas_digestive_problem() == true) {
//					if (this.isDiseaseInList(result, NP.Nutrition.ClinicalHistory.CHRONIC_DISEASE_Value_DigestiveProblem)==true) {
//						log.info("Cond: Patient has digestive problem and the advise too! :D");
//					} else {
//						return NUTRITION_CONDITION_InValid;
//					}
//				}
//				if (o.isHas_overweight() == true) {
//					if (this.isDiseaseInList(result, NP.Nutrition.ClinicalHistory.CHRONIC_DISEASE_Value_Overweight)==true) {
//						log.info("Cond: Patient has overweight and the advise too! :D");
//					} else {
//						return NUTRITION_CONDITION_InValid;
//					}
//				}
//				if (o.isHas_urine_sugar() == true) {
//					if (this.isDiseaseInList(result, NP.Nutrition.ClinicalHistory.CHRONIC_DISEASE_Value_UrineSugar)==true) {
//						log.info("Cond: Patient has urine sugar and the advise too! :D");
//					} else {
//						return NUTRITION_CONDITION_InValid;
//					}
//				}
//				if (o.isHas_respiratory_problem() == true) {
//					if (this.isDiseaseInList(result, NP.Nutrition.ClinicalHistory.CHRONIC_DISEASE_Value_RespiratoryProblem)==true) {
//						log.info("Cond: Patient has respiratory problem and the advise too! :D");
//					} else {
//						return NUTRITION_CONDITION_InValid;
//					}
//				}
//			} else {
//				log.info("Cond: no tiene enfermedad cronica");
//				return NUTRITION_CONDITION_InValid;
//			}
			
		}
		return NUTRITION_CONDITION_Valid;
		
		
//		log.info("Cond: Item has "+ item.getNutritionalConditions().length);
//		boolean at_least_one_matches = false;
//		for (NutritionalCondition nutriCondition : item.getNutritionalConditions()) {
//			log.info("Cond: Found nutriCondition: "+ nutriCondition.getID());
//			
//			// 1. Get the Key
//			String xmlKey = nutriCondition.getFieldName();
//			//get value from NutritionalProfile
//			String userValue = this.getValueFromNutritionalProfile(xmlKey);
//			String targetValue = nutriCondition.getValue();
//			if (userValue==null) {
//				log.info("Cond: couldn't get userValue, problem reading Profile");
//				if (nutriCondition.getCompulsory()) { // si es obligatoria, rechazar
//					return NUTRITION_CONDITION_InValid;
//				}
//				else {
//					continue;
//				}
//			}
//			log.info("Cond: target is: "+userValue);
//			int type = nutriCondition.getType();
//			int compareType = nutriCondition.getCompare_type();
//			int result = NUTRITION_CONDITION_InValid;
//			
//			switch (type) {
//			/*
//			 * STRING
//			 */
//			case TYPE_STRING: {
//				log.info("Cond: Nutrition Condition type: String");
//				result = this.compareString(compareType, userValue, targetValue);
//				break;
//			}
//			
//			/*
//			 * INTEGER
//			 */
//			case TYPE_INTEGER: {
//				log.info("Cond: Nutrition Condition type: Integer");
//				result = this.compareInteger(compareType, userValue, targetValue);
//				break;
//			}
//			
//			/*
//			 * DOUBLE
//			 */
//			case TYPE_DOUBLE: {
//				log.info("Cond: Nutrition Condition type: Double");
//				result = this.compareDouble(compareType, userValue, targetValue);
//				break;
//			}
//			
//			/*
//			 * BOOLEAN
//			 */
//			case TYPE_BOOLEAN: {
//				log.info("Cond: Nutrition Condition type: Boolean");
//				result = this.compareInteger(compareType, userValue, targetValue);
//				break;
//			}
//			
//			/*
//			 * DATE
//			 */
//			case TYPE_DATE:
//				log.info("Cond: Nutrition Condition type: Date");
//				result = this.compareDate(compareType, userValue, targetValue);
//				break;
//			
//			/*
//			 * TiME
//			 */
//			case TYPE_TIME:
//				log.info("Cond: Nutrition Condition type: Time");
//				result = this.compareTime(compareType, userValue, targetValue);
//				break;
//				
//			default:
//				log.info("Cond: Error!! Tipo de condicion nutricional desconocido");
//				result = NUTRITION_CONDITION_InValid;
//			}
//			log.info("Cond: Result is: "+result);
//			if (nutriCondition.getCompulsory() && result==NUTRITION_CONDITION_InValid) // si es obligatoria, rechazar
//				return NUTRITION_CONDITION_InValid;
//		}
//		if (at_least_one_matches==true) {
//			return NUTRITION_CONDITION_Valid;
//		}
//		return NUTRITION_CONDITION_Valid;
	}

	private boolean isDiseaseInList(List<String> diseaseList, String compareDisease) {
		boolean found = false;
		for (String disease : diseaseList) {
			if (disease.compareTo(compareDisease)==0) {
				found = true;
				continue;
			}
		}
		return found;
	}
	

//	private int compareDouble(int compareType, String userValue, String targetValue) {
//		// check compareType
//		switch (compareType) {
//		case COMPARE_TYPE_UNIC_VALUE: {
//			Double valueDouble = new Double(userValue);
//			Double targetDouble = new Double(targetValue);
//			if (valueDouble == targetDouble) {
//				log.info("Cond: Integer Se cumple la condicion!");
//				return NUTRITION_CONDITION_Valid;
//			} else {
//				log.info("Cond: Integer No se cumple la condicion");
//				return NUTRITION_CONDITION_InValid;
//			}
//		}	
//		case COMPARE_TYPE_BELONGS_TO_LIST: {
//			/*
//			 * Check that values is in target
//			 */
//			Double valueDouble = new Double(userValue);
//			String separador = "@@";
//			String[] parts = targetValue.split(separador);
//			boolean found = false;
//			for (String elem : parts) {
//				Double targetDouble = new Double(elem);
//				if (valueDouble==targetDouble) {
//					found = true;
//					break;
//				}
//			}
//			if (found)
//				return NUTRITION_CONDITION_Valid;
//			else
//				return NUTRITION_CONDITION_InValid;
//		}
//		case COMPARE_TYPE_SAME_LIST: {
//			Double valueDouble = new Double(userValue);
//			String separador = "@@";
//			String[] parts = targetValue.split(separador);
//			boolean found = true;
//			for (String elem : parts) {
//				Double targetDouble = new Double(elem);
//				if (valueDouble!=targetDouble) {
//					found = false;
//					break;
//				}
//			}
//			if (found)
//				return NUTRITION_CONDITION_Valid;
//			else
//				return NUTRITION_CONDITION_InValid;
//		}
//			
//		default:
//			log.info("Cond: Error, unknown compare type");
//			break;
//		}
//		return NUTRITION_CONDITION_InValid;
//	}

//	private int compareInteger(int compareType, String userValue, String targetValue) {
//		// check compareType
//		switch (compareType) {
//		case COMPARE_TYPE_UNIC_VALUE: {
//			int valueInt = new Integer(userValue).intValue();
//			int targetInt = new Integer(targetValue).intValue();
//			if (valueInt == targetInt) {
//				log.info("Cond: Integer Se cumple la condicion!");
//				return NUTRITION_CONDITION_Valid;
//			} else {
//				log.info("Cond: Integer No se cumple la condicion");
//				return NUTRITION_CONDITION_InValid;
//			}
//		}	
//		case COMPARE_TYPE_BELONGS_TO_LIST: {
//			/*
//			 * Check that values is in target
//			 */
//			int valueInt = new Integer(userValue).intValue();
//			String separador = "@@";
//			String[] parts = targetValue.split(separador);
//			boolean found = false;
//			for (String elem : parts) {
//				int targetInt = new Integer(elem).intValue();
//				if (valueInt==targetInt) {
//					found = true;
//					break;
//				}
//			}
//			if (found)
//				return NUTRITION_CONDITION_Valid;
//			else
//				return NUTRITION_CONDITION_InValid;
//		}
//		case COMPARE_TYPE_SAME_LIST: {
//			int valueInt = new Integer(userValue).intValue();
//			String separador = "@@";
//			String[] parts = targetValue.split(separador);
//			boolean found = true;
//			for (String elem : parts) {
//				int targetInt = new Integer(elem).intValue();
//				if (valueInt!=targetInt) {
//					found = false;
//					break;
//				}
//			}
//			if (found)
//				return NUTRITION_CONDITION_Valid;
//			else
//				return NUTRITION_CONDITION_InValid;
//		}
//			
//		default:
//			log.info("Cond: Error, unknown compare type");
//			break;
//		}
//		return NUTRITION_CONDITION_InValid;
//	}
//
//	private int compareString(int compareType, String userValue, String targetValue) {
//		// check compareType
//		switch (compareType) {
//		case COMPARE_TYPE_UNIC_VALUE:
//			if (targetValue.compareTo(userValue) == 0) {
//				log.info("Cond: String Se cumple la condicion!");
//				return NUTRITION_CONDITION_Valid;
//			} else {
//				log.info("Cond: String No se cumple la condicion");
//				return NUTRITION_CONDITION_InValid;
//			}
//			
//		case COMPARE_TYPE_BELONGS_TO_LIST:
//		{
//			/*
//			 * Check that values is in target
//			 */
//			String separador = "@@";
//			String[] parts = targetValue.split(separador);
//			boolean found = false;
//			for (String elem : parts) {
//				if (elem.compareTo(userValue)==0) {
//					found = true;
//					break;
//				}
//			}
//			if (found)
//				return NUTRITION_CONDITION_Valid;
//			else
//				return NUTRITION_CONDITION_InValid;
//		}
//
//		case COMPARE_TYPE_SAME_LIST: {
//			String separador = "@@";
//			String[] parts = targetValue.split(separador);
//			boolean found = true;
//			for (String elem : parts) {
//				if (elem.compareTo(userValue)!=0) {
//					found = false;
//					break;
//				}
//			}
//			if (found)
//				return NUTRITION_CONDITION_Valid;
//			else
//				return NUTRITION_CONDITION_InValid;
//		}
//			
//		default:
//			log.info("Cond: Error, unknown compare type");
//			break;
//		}
//		return NUTRITION_CONDITION_InValid;
//	}
//	
//
//	private int compareTime(int compareType, String userValue,
//			String targetValue) {
//		log.info("Cond: Compare TIME");
//		return 0;
//	}
//
//	private int compareDate(int compareType, String userValue,
//			String targetValue) {
//		log.info("Cond: Compare DATE");
//		return 0;
//	}
//
//	private String getValueFromNutritionalProfile(String xmlKey) {
//		return ProfileConnector.getInstance().getOldProperty(xmlKey);
//	}
}
