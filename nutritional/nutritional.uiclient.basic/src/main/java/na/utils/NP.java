package na.utils;

public class NP {

	public static class StatusResponse {
		public static String STATUS_INVALID = "Invalid Version, sending complete";
		public static String STATUS_EXPIRED = "Expired Version, sending complete";
		public static String STATUS_CURRENT = "Current Version, sending empty";
	}
	
	public static class Common {
		
		public static final String COMMON = "/common";
		public static final String EMAIL = COMMON + "/user_email";
		public static final String USER_ID = COMMON + "/user_id";
		public static final String USERNAME = COMMON + "/username";
		public static final String AGE = COMMON + "/age";
		public static final String AGE_CATEGORY = COMMON + "/age_category";
		public static final String SPOKEN_LANGUAGES = COMMON + "/spoken_language";
		public static final String COUNTRY = COMMON + "/country";
		public static final String FIRST_NAME = COMMON + "/name";
		public static final String LAST_NAME = COMMON + "/surname";
		public static final String CITY = COMMON + "/city";
//		public static final String HEALTH_STATUS = COMMON + "/health_status";
//		public static final String HEIGHT = COMMON + "/height";
//		public static final String HOME_PHONENUMBER = COMMON + "/mobile"; 
		public static final String IMPAIRED_CATEGORY = COMMON + "/impaired_category";
		public static final String MOBILE_PHONE = COMMON + "/mobile";
//		public static final String NICKNAME = COMMON + "/nickname";
		public static final String PREFERRED_LANGUAGE = COMMON + "/preferred_language"; 
//		public static final String RELIGION = COMMON + "/religion";
		public static final String SEX = COMMON + "/sex";
		public static final String RELATIONSHIP_STATUS = COMMON + "/relationship_status";
	}
	
	public static class External {
		// HEALTH MONITORING UPM
		public static class HealthMonitoring {
			public static final String CONDITION_HEALTHY				= "/apps/health/HealthCond_Healthy";
			public static final String CONDITION_CARDIO				= "/apps/health/HealthCond_Cardio";
			public static final String CONDITION_OTHER					= "/apps/health/HealthCond_Other";
			public static final String CONDITION_OVERWEIGHT			= "/apps/health/HealthCond_Overweight";
			public static final String CONDITION_CHOLESTEROL			= "/apps/health/HealthCond_Cholesterol";
			public static final String CONDITION_DIABETES				= "/apps/health/HealthCond_Diabetes";
			public static final String CONDITION_RESPIRATORY			= "/apps/health/HealthCond_Respiratory";
		}
		
		public static class ActivityCoach {
			public static final String FATIGUE_LEVEL			= "/apps/activity_monitoring/fatigue_level";
			public static final String ACTIVITY_LEVEL			= "/apps/activity_monitoring/activity_level";
		}
		
		public static class Social {
			public static final String USERNAME					= "/apps/social_community/social_username";
			public static final String PASSWORD					= "/apps/social_community/social_password";
		}
	}
	
	static public class Nutrition {
		public static final String ROOT = "/apps";
		public static final String NUTRITIONAL = "/nutritional";
		public static final String STATIC = ROOT + NUTRITIONAL + "/static";
		public static final String DYNAMIC = ROOT + NUTRITIONAL + "/dynamic";
		
		public static final String NUTRITIONAL_AND_PERSONAL = STATIC + "/nutritional_and_personal_habits";
		public static final String GENERAL = STATIC + "/general";
		public static final String NUTRITIONAL_PREFERENCES = STATIC + "/nutritional_preferences";
		public static final String HEALTH_RELATED = STATIC + "/health_related";
		public static final String BIOMEDICAL_BODY = DYNAMIC + "/biomedical_body_parameters";
		public static final String ACTIVITY_RELATED = DYNAMIC + "/activity_related";
		public static final String REPORT = DYNAMIC + "/report"; //new
		public static final String MACRO_MICRO = DYNAMIC + "/macro_micro_nutrients";
		public static final String USERNAME = ROOT + NUTRITIONAL + "/username";
		public static final String PASSWORD = ROOT + NUTRITIONAL + "/password";
		
		
		// NUTRITIONAL AND PERSONAL HABITS
		public static class Habits {
			public static final String DIET_TYPE 			= NUTRITIONAL_AND_PERSONAL + "/diet_type";
			public static final String BREAKFAST_HOUR 		= NUTRITIONAL_AND_PERSONAL + "/breakfast_hour";
			public static final String MORNING_SNACK_HOUR	= NUTRITIONAL_AND_PERSONAL + "/morning_snack_hour";
			public static final String MORNING_SNACK_PLACE	= NUTRITIONAL_AND_PERSONAL + "/morning_snack_place";
			public static final String AFTERNOON_SNACK_HOUR	= NUTRITIONAL_AND_PERSONAL + "/afternoon_snack_hour";
			public static final String AFTERNOON_SNACK_PLACE	= NUTRITIONAL_AND_PERSONAL + "/afternoon_snack_place";
			public static final String BREAKFAST_PLACE 		= NUTRITIONAL_AND_PERSONAL + "/breakfast_place";
			public static final String LUNCH_HOUR 			= NUTRITIONAL_AND_PERSONAL + "/lunch_hour";
			public static final String LUNCH_PLACE	 		= NUTRITIONAL_AND_PERSONAL + "/lunch_place";
			public static final String DINNER_HOUR 			= NUTRITIONAL_AND_PERSONAL + "/dinner_hour";
			public static final String DINNER_PLACE			= NUTRITIONAL_AND_PERSONAL + "/dinner_place";
			public static final String AFTERDINNER_SNACK_HOUR	= NUTRITIONAL_AND_PERSONAL + "/afterdinner_snack_hour"; //new!
			public static final String AFTERDINNER_SNACK_PLACE	= NUTRITIONAL_AND_PERSONAL + "/afterdinner_snack_place"; // new!
			public static final String NUTRITIONAL_GOAL		= NUTRITIONAL_AND_PERSONAL + "/nutritional_goal";
			public static final String TYPE_DAILY_MEALS= DYNAMIC + "/diary_food" + "/type_of_daily_meals"; //new
			public static final String RECOMMENDED_DAILY_MEALS= NUTRITIONAL_AND_PERSONAL + "/recommended_number_of_daily_meals";
			public static final String NUMBER_DAILY_MEALS=  DYNAMIC + "/diary_food" + "/number_of_daily_meals";
			public static final String RELIGION_CONSTRAINT	= NUTRITIONAL_AND_PERSONAL + "/religion_constraint";
			public static final String SELF_COOKING			= NUTRITIONAL_AND_PERSONAL + "/self_cooking";
			public static final String SELF_SHOPPING		= NUTRITIONAL_AND_PERSONAL + "/self_shopping";
			public static final String GET_UP_HOUR			= NUTRITIONAL_AND_PERSONAL + "/get_up_hour";
			public static final String BED_HOUR				= NUTRITIONAL_AND_PERSONAL + "/bed_hour";
			public static final String COMPANIONS_AT_TABLE	= NUTRITIONAL_AND_PERSONAL + "/has_companions_at_table";
			public static final String NUMBER_COMPANIONS_AT_TABLE	= NUTRITIONAL_AND_PERSONAL + "/number_of_companions_at_table";
			public static final String CONSUMES_ALCOHOL	= NUTRITIONAL_AND_PERSONAL + "/number_of_companions_at_table";
			public static final String CONSUMES_TOBACCO	= NUTRITIONAL_AND_PERSONAL + "/smokes_regularly";
			public static final String CONSUMES_DRUGS	= NUTRITIONAL_AND_PERSONAL + "/consumes_drugs_regularly";
			public static final String RECOMMENDED_FOOD_INTAKE_CALORIES = NUTRITIONAL_AND_PERSONAL +"/recommended_food_intake_calories"; 
			
			public static class Values_DietType {
				public static class BG {
					public static final String MEDITERRANEAN 	= "\u0441\u0440\u0435\u0434\u0438\u0437\u0435\u043C\u043D\u043E\u043C\u043E\u0440\u0441\u043A\u0438";
					public static final String SAXON 			= "\u0441\u0430\u043A\u0441\u043E\u043D\u0441\u043A\u0438";
					public static final String VEGETARIAN		= "\u0432\u0435\u0433\u0435\u0442\u0430\u0440\u0438\u0430\u043D\u0441\u043A\u0438";
					public static final String VEGAN 			= "\u043D\u0435 \u043A\u043E\u043D\u0441\u0443\u043C\u0438\u0440\u0430\u0449 \u0436\u0438\u0432\u043E\u0442\u0438\u043D\u0441\u043A\u0438 \u043F\u0440\u043E\u0434\u0443\u043A\u0442\u0438";
					public static final String OTHER 			= "\u0434\u0440\u0443\u0433\u0438";
				}
				
				public static class EN {
					public static final String MEDITERRANEAN 	= "Mediterranean";
					public static final String SAXON 			= "Saxon";
					public static final String VEGETARIAN 		= "Vegetarian";
					public static final String VEGAN 			= "Vegan";
					public static final String OTHER 			= "Other";
				}
				
				public static class SP {
					public static final String MEDITERRANEAN 	= "Mediterránea";
					public static final String SAXON 			= "Sajona";
					public static final String VEGETARIAN 		= "Vegetariana";
					public static final String VEGAN 			= "Vegana";
					public static final String OTHER 			= "Otra";
				}
			}
			
		}
		
		// ANTHROPOMETRICS
		public static class Anthropometrics {
			public static final String HEIGHT = BIOMEDICAL_BODY + "/height";
			public static final String WEIGHT				= BIOMEDICAL_BODY + "/weight";
			public static final String WAIST_CIRCUMFERENCE	= BIOMEDICAL_BODY + "/waist_circumference";
			public static final String ARM_CIRCUMFERENCE	= BIOMEDICAL_BODY + "/arm_circumference";
			public static final String THIGH_CIRCUMFERENCE	= BIOMEDICAL_BODY + "/thigh_circumference";
			public static final String THIGH_SKINFOLD		= BIOMEDICAL_BODY + "/thigh_skinfold";
			public static final String SUBSCAPULAR_SKINFOLD	= BIOMEDICAL_BODY + "/subscapular_skinfold";
			public static final String SUPRAILIAC_SKINFOLD	= BIOMEDICAL_BODY + "/suprailiac_skinfold";
			public static final String TRICEPS_SKINFOLD		= BIOMEDICAL_BODY + "/triceps_skinfold";
			public static final String ABDOMINAL_SKINFOLD	= BIOMEDICAL_BODY + "/abdominal_skinfold";
		}
		
		// NUTRITIONAL PREFERENCES
		public static class NutriPreferences {
			public static final String PREFERRED_COOKING_TECHNIQUES = NUTRITIONAL_PREFERENCES + "/preferred_cooking_techniques";
			public static final String PREFERRRED_FOODS 			= NUTRITIONAL_PREFERENCES + "/preferred_foods";
			public static final String PREFERRRED_CUISINES 			= NUTRITIONAL_PREFERENCES + "/preferred_cuisines";
			public static final String FAVOURITE_RECIPES 			= NUTRITIONAL_PREFERENCES + "/favourite_recipes";
			public static final String SPECIALTY_FOODS	 			= NUTRITIONAL_PREFERENCES + "/speciality_foods";
			public static final String PREF_CONVENIENCE	 			= NUTRITIONAL_PREFERENCES + "/preference_use_of_foods";
			public static final String FOOD_DISLIKES 				= NUTRITIONAL_PREFERENCES + "/food_dislikes";
			public static final String MEDICINE_FOOD_INTERACTIONS 	= NUTRITIONAL_PREFERENCES + "/medicine_food_interactions";
		}

		// HEALT RELATED
		public static class Health {
			public static final String FOOD_ALLERGIES 		= HEALTH_RELATED + "/food_allergies";
			public static final String CHRONIC_DISEASES 	= HEALTH_RELATED + "/chronic_diseases";
			public static final String FOOD_INTOLERANCES 	= HEALTH_RELATED + "/food_intolerances";
			
			public static final String HEALTH_CONDITION_DIABETES = HEALTH_RELATED + "/HealthCond_Diabetes";
			public static final String HEALTH_CONDITION_HEALTHY = HEALTH_RELATED + "/HealthCond_Healthy";
			public static final String HEALTH_CONDITION_CARDIO = HEALTH_RELATED + "/HealthCond_Cardio";
			public static final String HEALTH_CONDITION_OTHER = HEALTH_RELATED + "/HealthCond_Other";
			public static final String HEALTH_CONDITION_OVERWEIGHT = HEALTH_RELATED + "/HealthCond_Overweight";
			public static final String HEALTH_CONDITION_CHOLESTEROL = HEALTH_RELATED + "/HealthCond_Cholesterol";
			public static final String HEALTH_CONDITION_RESPIRATORY = HEALTH_RELATED + "/HealthCond_Respiratory";
			public static final String HEALTH_CONDITION_DIGESTIVE = HEALTH_RELATED + "/HealthCond_Digestive"; // NEW! Add to schema!!
			public static final String HEALTH_CONDITION_URINE_SUGAR = HEALTH_RELATED + "/HealthCond_UrineSugar"; // NEW! Add to schema!!
			
			public static class Values_ChronicDiseases {
				public static class BG {
					public static final String CHOLESTEROL		= "\u0425\u043E\u043B\u0435\u0441\u0442\u0435\u0440\u043E\u043B";
					public static final String CARDIOVASCULAR	= "\u041A\u0430\u0440\u0434\u0438\u043E\u0432\u0430\u0441\u043A\u0443\u043B\u0430\u0440\u043D\u0438 \u043F\u0440\u043E\u0431\u043B\u0435\u043C\u0438";
					public static final String DIABETES			= "\u0414\u0438\u0430\u0431\u0435\u0442";
					public static final String DIGESTIVE		= "\u0413\u0430\u0441\u0442\u0440\u043E\u0435\u043D\u0442\u0435\u0440\u043E\u043B\u043E\u0433\u0438\u0447\u0435\u043D \u043F\u0440\u043E\u0431\u043B\u0435\u043C";
					public static final String OVERWEIGHT		= "\u041D\u0430\u0434\u043D\u043E\u0440\u043C\u0435\u043D\u043E \u0442\u0435\u0433\u043B\u043E";
					public static final String URINE			= "\u0423\u0440\u043E\u043B\u043E\u0433\u0438\u0447\u043D\u0438 \u043F\u0440\u043E\u0431\u043B\u0435\u043C\u0438";
					public static final String RESPIRATORY		= "\u0417\u0430\u0445\u0430\u0440 \u0432 \u0443\u0440\u0438\u043D\u0430\u0442\u0430, \u0420\u0435\u0441\u043F\u0438\u0440\u0430\u0442\u043E\u0440\u0435\u043D \u043F\u0440\u043E\u0431\u043B\u0435\u043C";
				}
				
				public static class EN {
					public static final String CHOLESTEROL		= "Cholesterol";
					public static final String CARDIOVASCULAR	= "Cardiovascular";
					public static final String DIABETES			= "Diabetes";
					public static final String DIGESTIVE		= "Digestive";
					public static final String OVERWEIGHT		= "Overweight";
					public static final String URINE			= "Urine sugar";
					public static final String RESPIRATORY		= "Respiratory problem";
				}
				
				public static class SP {
					public static final String CHOLESTEROL		= "Colesterol";
					public static final String CARDIOVASCULAR	= "Cardiovascular";
					public static final String DIABETES			= "Diabetes";
					public static final String DIGESTIVE		= "Problema digestivo";
					public static final String OVERWEIGHT		= "Sobrepeso";
					public static final String URINE			= "Azucar en orina";
					public static final String RESPIRATORY		= "Problema respiratorio";
				}
			}
		}
		
		// PHYSICAL ACTIVITY
		public static class PhysicalActivity {
			public static final String NUM_HOURS_DAY_LOW_INTENSITY 		= ACTIVITY_RELATED + "/number_hours_per_day_low_intensity_activity"; 
			public static final String NUM_HOURS_DAY_MODERATE_INTENSITY = ACTIVITY_RELATED + "/number_hours_per_day_moderate_intensity_activity";
			public static final String NUM_HOURS_DAY_HIGH_INTENSITY 	= ACTIVITY_RELATED + "/number_hours_per_day_high_intensity_activity";
			public static final String LEVEL_TODAY_ACTIVITY 			= ACTIVITY_RELATED + "/level_today_activity";
			public static final String LEVEL_WEEKLY_ACTIVITY 			= ACTIVITY_RELATED + "/level_weekly_activity";
			public static final String ACTIVITY_LEVEL 					= ACTIVITY_RELATED + "/activity_level";
			public static final String TOTAL_ENERGY_EXPENDITURE 		= ACTIVITY_RELATED + "/total_energy_expenditure";
			public static final String WEEKLY_ENERGY_EXPENDITURE 		= ACTIVITY_RELATED + "/weekly_energy_expenditure";
		}
		
		// NUTRI REPORT
		public static class Report { //new!
			public static final String GOAL_KCAL_PER_DAY 				= REPORT + "/goal_kcal_per_day"; 
			public static final String GOAL_WEIGHT 						= REPORT + "/goal_weight";
			public static final String PLAN_DURATION 					= REPORT + "/plan_duration";
		}
		
		// CLINICAL HISTORY
		public static class ClinicalHistory {
			public static final String CHRONIC_DISEASES 	= HEALTH_RELATED + "/chronic_diseases"; 
//			public static final String DISABILITIES 		= HEALTH_RELATED + "/disability";
			public static final String IS_MEDICATED 		= HEALTH_RELATED + "/medicated";
			public static final String PAST_INTERVENTIONS 	= HEALTH_RELATED + "/hospitalizations";
			public static final String MEDICATION 			= HEALTH_RELATED + "/medication";

			public static final String BLOOD_SUGAR			= BIOMEDICAL_BODY + "/blood_sugar";
			public static final String BLOOD_PRESURE_SYSTOLIC	= BIOMEDICAL_BODY + "/blood_pressure_systolic";
			public static final String BLOOD_PRESURE_DIASTOLIC	= BIOMEDICAL_BODY + "/blood_pressure_diastolic";
			public static final String CHOLESTEROL			= MACRO_MICRO + "/cholesterol";
			public static final String ECG					= BIOMEDICAL_BODY + "/ecg";
			public static final String PULSE_OXIMETRY			= BIOMEDICAL_BODY + "/pulse_oximetry";
			public static final String RESPIRATION_FREQUENCY	= BIOMEDICAL_BODY + "/respiration_frequency";
			public static final String TEMPERATURE	= BIOMEDICAL_BODY + "/temperature";
			public static final String CHRONIC_DISEASE_Value_DigestiveProblem = "Digestive problem";
			public static final String CHRONIC_DISEASE_Value_UrineSugar = "Urine sugar";
			public static final String CHRONIC_DISEASE_Value_Cholesterol = "Cholesterol";
			public static final String CHRONIC_DISEASE_Value_Cardiovascular = "Cardiovascular";
			public static final String CHRONIC_DISEASE_Value_Diabetes = "Diabetes";
			public static final String CHRONIC_DISEASE_Value_Overweight = "Overweight";
			public static final String CHRONIC_DISEASE_Value_RespiratoryProblem = "Respiratory problem";
			
//			public static final String PAST_ILLNESS 		= HEALTH_RELATED + "/past_illness";
			public static final String PAST_ILLNESS_LiverGastro 		= HEALTH_RELATED + "/PastIllnesHistory_LiverGastro";
			public static final String PAST_ILLNESS_Hematological 		= HEALTH_RELATED + "/PastIllnesHistory_Hematological";
			public static final String PAST_ILLNESS_Renal 		= HEALTH_RELATED + "/PastIllnesHistory_Renal";
			public static final String PAST_ILLNESS_Other 		= HEALTH_RELATED + "/PastIllnesHistory_Other";
			public static final String PAST_ILLNESS_Rheuma 		= HEALTH_RELATED + "/PastIllnesHistory_Rheuma";
			public static final String PAST_ILLNESS_Pulmonary 		= HEALTH_RELATED + "/PastIllnesHistory_Pulmonary";
			public static final String PAST_ILLNESS_Neuro 		= HEALTH_RELATED + "/PastIllnesHistory_Neuro";
			public static final String PAST_ILLNESS_Infectious 		= HEALTH_RELATED + "/PastIllnesHistory_Infectious";
			public static final String PAST_ILLNESS_Cardiovascular 		= HEALTH_RELATED + "/PastIllnesHistory_Cardiovascular";
			public static final String PAST_ILLNESS_Endocrine 		= HEALTH_RELATED + "/PastIllnesHistory_Endocrine";
			public static final String PAST_ILLNESS_Immuncomp 		= HEALTH_RELATED + "/PastIllnesHistory_Immuncomp";
			public static final String PAST_ILLNESS_Inmunological 		= HEALTH_RELATED + "/PastIllnesHistory_Inmunological";
			
		}
		
		// OTHER 
		public static final String CARER_EMAIL 			= GENERAL + "/carer_email";
		
		public static String[] arrayOfKeys = {
			Nutrition.CARER_EMAIL,
			Nutrition.Habits.NUMBER_DAILY_MEALS,
			Nutrition.Habits.TYPE_DAILY_MEALS,
			Nutrition.Report.GOAL_KCAL_PER_DAY,
			Nutrition.Report.GOAL_WEIGHT,
			Nutrition.Report.PLAN_DURATION,
			Nutrition.Anthropometrics.ABDOMINAL_SKINFOLD, Nutrition.Anthropometrics.ARM_CIRCUMFERENCE,
			Nutrition.Anthropometrics.HEIGHT, Nutrition.Anthropometrics.SUBSCAPULAR_SKINFOLD,
			Nutrition.Anthropometrics.SUPRAILIAC_SKINFOLD, Nutrition.Anthropometrics.THIGH_CIRCUMFERENCE,
			Nutrition.Anthropometrics.THIGH_SKINFOLD, Nutrition.Anthropometrics.TRICEPS_SKINFOLD, 
			Nutrition.Anthropometrics.WAIST_CIRCUMFERENCE, Nutrition.Anthropometrics.WEIGHT,
			
			Nutrition.Habits.AFTERDINNER_SNACK_HOUR, Nutrition.Habits.AFTERDINNER_SNACK_PLACE,
			Nutrition.Habits.AFTERNOON_SNACK_HOUR, Nutrition.Habits.AFTERNOON_SNACK_PLACE, Nutrition.Habits.BED_HOUR,
			Nutrition.Habits.BREAKFAST_HOUR, Nutrition.Habits.BREAKFAST_PLACE, Nutrition.Habits.COMPANIONS_AT_TABLE,
			Nutrition.Habits.CONSUMES_ALCOHOL, Nutrition.Habits.CONSUMES_DRUGS, Nutrition.Habits.CONSUMES_TOBACCO,
			Nutrition.Habits.DIET_TYPE, Nutrition.Habits.DINNER_HOUR, Nutrition.Habits.DINNER_PLACE,
			Nutrition.Habits.GET_UP_HOUR, Nutrition.Habits.LUNCH_HOUR, Nutrition.Habits.LUNCH_PLACE,
			Nutrition.Habits.MORNING_SNACK_HOUR, Nutrition.Habits.MORNING_SNACK_PLACE, Nutrition.Habits.NUMBER_COMPANIONS_AT_TABLE,
			Nutrition.Habits.NUTRITIONAL_GOAL, Nutrition.Habits.RECOMMENDED_DAILY_MEALS, Nutrition.Habits.RELIGION_CONSTRAINT,
			Nutrition.Habits.SELF_COOKING, Nutrition.Habits.SELF_SHOPPING,
			
			Nutrition.ClinicalHistory.BLOOD_PRESURE_DIASTOLIC, Nutrition.ClinicalHistory.BLOOD_PRESURE_SYSTOLIC,
			Nutrition.ClinicalHistory.BLOOD_SUGAR, Nutrition.ClinicalHistory.CHOLESTEROL, 
			
//			Nutrition.ClinicalHistory.DISABILITIES,
			Nutrition.ClinicalHistory.ECG, Nutrition.ClinicalHistory.IS_MEDICATED, 
			Nutrition.ClinicalHistory.MEDICATION, Nutrition.ClinicalHistory.PAST_ILLNESS_LiverGastro,
			Nutrition.ClinicalHistory.PAST_ILLNESS_Hematological,
			Nutrition.ClinicalHistory.PAST_ILLNESS_Renal,
			Nutrition.ClinicalHistory.PAST_ILLNESS_Other,
			Nutrition.ClinicalHistory.PAST_ILLNESS_Rheuma,
			Nutrition.ClinicalHistory.PAST_ILLNESS_Pulmonary,
			Nutrition.ClinicalHistory.PAST_ILLNESS_Neuro,
			Nutrition.ClinicalHistory.PAST_ILLNESS_Infectious,
			Nutrition.ClinicalHistory.PAST_ILLNESS_Cardiovascular,
			Nutrition.ClinicalHistory.PAST_ILLNESS_Endocrine,
			Nutrition.ClinicalHistory.PAST_ILLNESS_Immuncomp,
			Nutrition.ClinicalHistory.PAST_ILLNESS_Inmunological,
			
			
			Nutrition.ClinicalHistory.PAST_INTERVENTIONS, Nutrition.ClinicalHistory.PULSE_OXIMETRY,
			Nutrition.ClinicalHistory.RESPIRATION_FREQUENCY, Nutrition.ClinicalHistory.TEMPERATURE,
			
//			Nutrition.Health.CHRONIC_DISEASES, 
			Nutrition.Health.HEALTH_CONDITION_DIABETES,
			Nutrition.Health.HEALTH_CONDITION_HEALTHY,
			Nutrition.Health.HEALTH_CONDITION_CARDIO,
			Nutrition.Health.HEALTH_CONDITION_OTHER,
			Nutrition.Health.HEALTH_CONDITION_OVERWEIGHT,
			Nutrition.Health.HEALTH_CONDITION_CHOLESTEROL,
			Nutrition.Health.HEALTH_CONDITION_RESPIRATORY,
			Nutrition.Health.FOOD_ALLERGIES,
			Nutrition.Health.FOOD_INTOLERANCES, Nutrition.NutriPreferences.MEDICINE_FOOD_INTERACTIONS,
			Nutrition.Health.HEALTH_CONDITION_URINE_SUGAR, Nutrition.Health.HEALTH_CONDITION_DIGESTIVE,
			
			Nutrition.NutriPreferences.FAVOURITE_RECIPES, Nutrition.NutriPreferences.FOOD_DISLIKES,
			Nutrition.NutriPreferences.PREF_CONVENIENCE, Nutrition.NutriPreferences.PREFERRED_COOKING_TECHNIQUES,
			Nutrition.NutriPreferences.PREFERRRED_CUISINES, Nutrition.NutriPreferences.PREFERRRED_FOODS,
			Nutrition.NutriPreferences.SPECIALTY_FOODS,
			
			Nutrition.PhysicalActivity.ACTIVITY_LEVEL, Nutrition.PhysicalActivity.LEVEL_TODAY_ACTIVITY,
			Nutrition.PhysicalActivity.LEVEL_WEEKLY_ACTIVITY, Nutrition.PhysicalActivity.NUM_HOURS_DAY_HIGH_INTENSITY,
			Nutrition.PhysicalActivity.NUM_HOURS_DAY_LOW_INTENSITY, Nutrition.PhysicalActivity.NUM_HOURS_DAY_MODERATE_INTENSITY,
			Nutrition.PhysicalActivity.TOTAL_ENERGY_EXPENDITURE, Nutrition.PhysicalActivity.WEEKLY_ENERGY_EXPENDITURE
		};
	}

	public static class UserPropertyType {
		// coinciden con los IDs de la tabla UserPropertyType
		public static final int simple_INT 				= 1;
		public static final int simple_DOUBLE 			= 2;
		public static final int simple_TEXT 			= 3;
		public static final int simple_BOOLEAN 			= 4;
		public static final int Custom_TEXT 			= 5;
		public static final int ARRAY			 		= 6;
		public static final int FOOD 					= 7;
		public static final int FOOD_CATEGORY 			= 8;
		public static final int FOOD_SUBCATEGORY 		= 9;
	}
}
