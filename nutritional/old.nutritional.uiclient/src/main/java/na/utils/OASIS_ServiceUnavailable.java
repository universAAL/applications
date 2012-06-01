package na.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings("serial")
public class OASIS_ServiceUnavailable extends Exception {
	public static final String ERROR_ON_AMI_SIDE = "AMI not working properly";
	public static final String ERROR_ON_AMI_TIME_EXCEEDED = "AMI time exceeded";
	public static final String ERROR_ON_AMI_REMOTE_CALL_EXCEPTION = "AMI remote call exception";
	public static final String ERROR_ON_AMI_NOT_READY = "AMI not running";
	
	public static final String ERROR_ON_WebService = "WebService not working properly";
	public static final String ERROR_QUESTIONNAIRES_Sending_Answer = "Error sending your answer. Please try again later";
	public static final String ERROR_QUESTIONNAIRES_Loading_Previous_Question = "Error loading the previous question. Please try again later";
	public static final String ERROR_QUESTIONNAIRES_LoadingQuestionnaire = "Error loading the questionnaire. Please try again later";
	
	private Log log = LogFactory.getLog(OASIS_ServiceUnavailable.class);
	public OASIS_ServiceUnavailable(String message) {
		super(message);
		log.info("OASIS_ServiceUnavailable Exception: "+message);
	}
}

