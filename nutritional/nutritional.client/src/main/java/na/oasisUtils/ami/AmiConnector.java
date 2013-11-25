package na.oasisUtils.ami;

import java.rmi.RemoteException;

import javax.naming.ServiceUnavailableException;

import na.utils.OASIS_ServiceUnavailable;
import na.utils.ServiceInterface;
import na.utils.Setup;
import na.utils.Utils;
import na.utils.ws.NutritionalAdvisorWSConnector;
import na.ws.NutriSecurityException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AmiConnector {

    private static Log log = LogFactory.getLog(AmiConnector.class);
    // public static final boolean AVOID_OASIS_AMI = true;

    private static AmiConnector instance;

    // private Map<String, IdealOperationDescriptor> na_ideal_ops = new
    // HashMap<String, IdealOperationDescriptor>();
    // private Map<String, IdealOperationDescriptor> environment_ideal_ops = new
    // HashMap<String, IdealOperationDescriptor>();

    static public AmiConnector getAMI() {
	if (instance == null)
	    instance = new AmiConnector(false);
	return instance;
    }

    private AmiConnector(boolean forceUseAMI) {
	long start = System.nanoTime();
	if (forceUseAMI || Setup.getAvoidOASIS_AMI() == false) {
	    log.info("AMI: AmiConnector: Creating AMI Instance...");
	}
	long elapsedTime = System.nanoTime() - start;
	double seconds = (double) elapsedTime / 1000000000.0;
	log.info("AMI: >>>>>>>>>>>>> AMI took: " + seconds
		+ " seconds to start");
    }

    /**
     * Invokes an operation. Depending on the attribute on user.properties
     * avoid_ami it will avoid using the ami or not
     * 
     * @param operationName
     *            the operation name
     * @param input3
     *            the input
     * @param forceWebServiceCall
     * @return the object
     * @throws ServiceUnavailableException
     * @throws RemoteException
     */
    public Object invokeOperation(String domain, String operationName,
	    Object[] input3, boolean forceWebServiceCall)
	    throws OASIS_ServiceUnavailable {
	if (Setup.getAvoidOASIS_AMI() || forceWebServiceCall) {
	    return this.directWSInvokeOperation(domain, operationName, input3);
	} else {
	    log.fatal("AMI:  invoking operation through AMI: " + operationName);
	    return null;
	}
    }

    /**
     * Calls the Nutritional Advisor Web Service directly, without using OASIS
     * AMI framework.
     * 
     * @param operationName
     *            the operation name
     * @param input3
     *            the input
     * @return the object
     * @throws RemoteException
     */
    private Object directWSInvokeOperation(String domain, String operationName,
	    Object[] input3) {
	if (domain.compareTo(ServiceInterface.DOMAIN_Nutrition) == 0) {
	    if (operationName.compareTo(ServiceInterface.OP_GetToken) == 0) {
		NutritionalAdvisorWSConnector nutritionalWS = new NutritionalAdvisorWSConnector();
		Object output;
		try {
		    output = nutritionalWS.invokeOperation(operationName, 1,
			    input3);
		    if (output != null) {
			return output;
		    } else
			log.info("AMI: No output found.");
		} catch (NutriSecurityException e) {
		    log.error("AMI: SECURITY EXCEPTION CATCHED");
		    // e.printStackTrace();
		    Utils.showLoginErrorPopup(e.getMessage1());
		} catch (RemoteException e) {
		    // e.printStackTrace();
		    log.error("AMI: Error, couldn't connect to nutritional web server");
		    if (e != null && e.getLocalizedMessage() != null)
			Utils.showConnectionErrorPopup(e.getLocalizedMessage());
		    else
			Utils.showConnectionErrorPopup("Couldn't connect");
		}
	    } else {
		NutritionalAdvisorWSConnector nutritionalWS = new NutritionalAdvisorWSConnector();
		Object output;
		try {
		    output = nutritionalWS.invokeOperation(operationName, 1,
			    input3);
		    if (output != null) {
			return output;
		    } else
			log.error("AMI: No output found.");
		} catch (Exception e) {
		    log.info("AMI: EXCEPTION!");
		    e.printStackTrace();
		}
	    }
	    // } else if
	    // (domain.compareTo(ServiceInterface.DOMAIN_SocialCommunity)==0) {
	    // SocialCommunityWSConnector socialWS = new
	    // SocialCommunityWSConnector();
	    // Object output;
	    // output = socialWS.invokeOperation(operationName, input3);
	    // if (output != null) {
	    // return output;
	    // }
	    // else
	    // log.info("AMI: No output found.");
	    // } else if
	    // (domain.compareTo(ServiceInterface.DOMAIN_EnvironmentalControl)==0)
	    // {
	    // EnvironmentWSConnector envWS = new EnvironmentWSConnector();
	    // Object output;
	    // output = envWS.invokeOperation(operationName, input3);
	    // if (output != null) {
	    // return output;
	    // }
	    // else
	    // log.info("AMI: No output found.");
	} else {
	    log.error("Unknown domain");
	}
	return null;
    }

    /**
     * Gets an instance that uses the AMI always
     * 
     * @return the true instance
     * @throws RemoteCallException
     * @throws RequestTimeExceedException
     */
    protected static AmiConnector getInstanceUsingAmi() {
	if (instance == null)
	    instance = new AmiConnector(true);
	return instance;
    }

    public static void closeFrontend() {
	if (AmiConnector.instance != null) {
	    AmiConnector.instance = null;
	    log.info("AMI: Ami instance closed");
	}
    }

    // public void testTemperature() throws RequestTimeExceedException,
    // RemoteCallException {
    // log.info("AMI: Sample test starts!");
    //
    // IdealOperationDescriptor idealOperation = new IdealOperationDescriptor();
    // idealOperation.setDomain_name("nutrition");
    // idealOperation.setName("getTokenIdeal");
    // idealOperation.setUiName("getTokenIdeal");
    // long start = System.nanoTime();
    // List<AlignedOperationDescriptor> alignedOps =
    // this.frontEnd.requestAlignedOperations(idealOperation);
    // log.info("Found: "+alignedOps.size() + " operations");
    // AlignedOperationDescriptor alignedOp = alignedOps.get(0);
    // // for (AlignedOperationDescriptor alignedOp : alignedOps) {
    // log.info("alignedOp: "+alignedOp.getOperationName());
    // // if (alignedOp.getOperationName().compareTo("getToken")!=0) {
    // // log.info("don't like it");
    // // continue;
    // // }
    // WSOperationInput wsInput =
    // this.frontEnd.requestAlignedOperationInput(alignedOp);
    // String username = LoginManager.getUsername();
    // String password = LoginManager.getPassword();
    // String lang = ProfileConnector.getInstance().getScreenLanguage();
    // log.info("Username: "+username);
    // log.info("Password: "+password);
    // log.info("Language: "+lang);
    // IOValuesHandler vh = new IOValuesHandler(wsInput);
    // List<IOElement> elements = vh.getFlattenList();
    // for (IOElement elem : elements) {
    // log.info("ioElement fullPath: "+elem.getFullname()+ ": ");
    // if (elem.getName().equalsIgnoreCase("username")) {
    // elem.setValue(username);
    // log.info("OK");
    // continue;
    // }
    // if (elem.getName().equalsIgnoreCase("password")) {
    // elem.setValue(password);
    // log.info("OK");
    // continue;
    // }
    // if (elem.getName().equalsIgnoreCase("preferredLanguage")) {
    // elem.setValue(password);
    // log.info("OK");
    // continue;
    // }
    // }
    //
    // // vh.setValue("/username", username);
    // // vh.setValue("/password", password);
    // // vh.setValue("/preferredLanguage",lang);
    // log.info("updating ws operation");
    // vh.updateWSOperationIO();
    // log.info("getting input");
    // WSOperationInput myInput = vh.getInput();
    // log.info("setting input");
    // alignedOp.setOperationInput(myInput);
    // log.info("requesting op result");
    // WSOperationOutput output =
    // this.frontEnd.requestAlignedOperationResult(alignedOp);
    // log.info("result received");
    // IOValuesHandler ovh = new IOValuesHandler(output);
    // List<IOElement> resElements = ovh.getFlattenList();
    // for (IOElement elem : resElements) {
    // log.info("Found: "+elem.getName());
    // if (elem.getName().equalsIgnoreCase("username")) {
    // log.info("result: "+elem.getValue());
    // }
    // }
    // log.info("End of loooop");
    // // String answer = resElements.get(0).getValue();
    // // log.info("Answer: "+answer);
    // // }
    // long elapsedTime = System.nanoTime() - start;
    // double seconds = (double)elapsedTime / 1000000000.0;
    // log.info(">>>>>>>>>>>>> Elapsed time: "+ seconds + " seconds");
    // // } else {
    // //
    // log.error("AmiConnector: operation not supported by remote AMI: "+operationName);
    // // }
    //
    // log.info("Sample test ends!");
    // }

}
