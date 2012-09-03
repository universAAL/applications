package na.services.myNutritionalProfile.cache;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import na.oasisUtils.ami.AmiConnector;
import na.utils.NP;
import na.oasisUtils.profile.ProfileConnector;
import na.oasisUtils.trustedSecurityNetwork.TSFConnector;
import na.ws.Translation;
import na.ws.UProperty;
import na.utils.OASIS_ServiceUnavailable;
import na.utils.ServiceInterface;
import na.utils.Utils;
import na.utils.lang.Messages;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TranslationCache {
	
	private static final String TRANSLATION_CACHE_FILE = ServiceInterface.CACHE_FOLDER +"transalation_cache.dat";
	private static TranslationCache instance = null;
	private Log log = LogFactory.getLog(TranslationCache.class);
	
	private Map<String, String[]> data;
	
	private TranslationCache() {
		this.data = (Map<String, String[]>) this.getTranslationFile();
		if (this.data == null)
			this.data = new HashMap<String, String[]>();
	}
	
	public static TranslationCache getInstance() {
		if (instance == null)
			instance =  new TranslationCache();
		return instance;
	}
	
	public UProperty getLocalisedValue(String type, String[] values) {
		if (type.compareTo(NP.Nutrition.Health.CHRONIC_DISEASES)==0) {
			String[] result = new String[values.length]; 
			int i = 0;
			for (String val : values) {
				if (val.compareTo(NP.Nutrition.Health.HEALTH_CONDITION_CHOLESTEROL)==0)
					result[i] = Messages.Profile_ChronicDisease_CHOLESTEROL;
				i++;
			}
//			return result;
		}
//		System.out.println("Values is: "+Arrays.toString(values));
		// 1. buscar en cache
		// 2. si encontrado, devolver valor
		// 3. si no encontrado preguntar a WS
			// 3.1 respuesta ok, guardar en cache
			// error, devolver null
		int codeLang = ProfileConnector.getInstance().getCodeLang();
//		String[] result = this.getTranslation(type, codeLang, values);
		String[] result = null;
		if (result!=null) {
			//found return value
//			System.out.println("Found translation, returning: "+result);
//			return result;
		} else {
			//not found, ask web server
			AmiConnector ami = AmiConnector.getAMI();
			if (ami!=null) {
				Translation trans = new Translation();
				trans.setFieldType(type);
				trans.setValues(values);
				trans.setCodeLang(codeLang);
				Object[] input = {TSFConnector.getInstance().getToken(), trans};
				UProperty t;
				try {
					t = (UProperty)ami.invokeOperation(ServiceInterface.DOMAIN_Nutrition, ServiceInterface.OP_GetLocalisedProfile, input, false);
					if (t != null) {
//						log.info("TranslationCache, found translation for: " + t.getFieldType());
//						String[] response = t.getTranslations();
//						for (String transla : t.getTranslations()) {
//							String key = t.getFieldType()+";" + transla + ";" + codeLang;
//							String[] array = {transla};
//							this.data.put(key, array);
////							log.info("Storing: key: "+key  + "  Value: "+ transla);
//							this.storeTranslation(this.data);							
//						}
//						return response;
						return t;
					} else { 
						log.info("TranslationCache, no translation found");
						return null;
					}
				} catch (OASIS_ServiceUnavailable e) {
					e.printStackTrace();
					log.info("TranslationCache, ami problem");
					return null;
				}
			} else {
				log.info("AMI Bundle not available!");
//				throw new OASIS_ServiceUnavailable(OASIS_ServiceUnavailable.ERROR_ON_AMI_SIDE);
			}
		}
		
		return null;
	}
	
	
	
	///////////////////////
	////////////////////////////////
	private String[] getTranslation(String type, int codeLang, String[] values) {
//		String key = type+";" + codeLang;
		ArrayList<String> translatedValues = new ArrayList<String>();
		for (String val : values) {
			String key = type+";"+ val + ";" + codeLang;
//			log.info("Looking for: "+key);
			if (this.data.containsKey(key)) {
//				System.out.println("Found: "+ key);
//				Translation f = this.data.get(key);
//				log.info("   translations: "+ Arrays.toString(this.data.get(key)));
				translatedValues.add(this.data.get(key)[0]);
//				return f.getTranslations() ;
			} else {
//				log.info("Not found: "+ key);
				return null;
			}
		}
		return translatedValues.toArray(new String[translatedValues.size()]);
	}
	
	
	
	//////////////////////////////////////////////
	/////////////////////////////////////////////
	////////////////////////////////////////////
	private Object getTranslationFile() {
		try {
			// Read from disk using FileInputStream
			FileInputStream f_in = new FileInputStream(TRANSLATION_CACHE_FILE);
			// Read object using ObjectInputStream
			ObjectInputStream obj_in = new ObjectInputStream (f_in);
			// Read an object
			Object obj = obj_in.readObject();
			if (obj instanceof PersistentObject) {
				f_in.close(); //Cose file!
				PersistentObject data = (PersistentObject) obj;
						return data.object;
			}
		} catch (FileNotFoundException e1) {
//			e1.printStackTrace();
			log.info("no cached file for: "+TRANSLATION_CACHE_FILE);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		return null;
	}
	
	private void storeTranslation(Map<String, String[]> data2) {
		if (data2!=null) {
			try {
				// Write to disk with FileOutputStream
				FileOutputStream f_out = new FileOutputStream(TRANSLATION_CACHE_FILE);
				// Write object with ObjectOutputStream
				ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
				// Write object out to disk
				PersistentObject p = new PersistentObject();
				p.object = data2;
				p.time = Utils.Dates.getTodayCalendar().getTimeInMillis();
				obj_out.writeObject(p);
				f_out.close(); // close file!
//				log.info("translation stored in cache: ");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}

}
