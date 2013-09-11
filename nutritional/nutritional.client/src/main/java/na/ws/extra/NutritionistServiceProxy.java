package na.ws.extra;

import java.rmi.RemoteException;

import na.miniDao.profile.PatientList;
import na.ws.NutriSecurityException;
import na.ws.TokenExpiredException;

public class NutritionistServiceProxy implements NutritionistService {
    private String _endpoint = null;
    private NutritionistService nutritionistService = null;

    public NutritionistServiceProxy() {
	_initNutritionistServiceProxy();
    }

    public NutritionistServiceProxy(String endpoint) {
	_endpoint = endpoint;
	_initNutritionistServiceProxy();
    }

    private void _initNutritionistServiceProxy() {
	try {
	    nutritionistService = (new NutritionistServiceServiceLocator())
		    .getNutritionistServicePort();
	    if (nutritionistService != null) {
		if (_endpoint != null)
		    ((javax.xml.rpc.Stub) nutritionistService)
			    ._setProperty(
				    "javax.xml.rpc.service.endpoint.address",
				    _endpoint);
		else
		    _endpoint = (String) ((javax.xml.rpc.Stub) nutritionistService)
			    ._getProperty("javax.xml.rpc.service.endpoint.address");
	    }

	} catch (javax.xml.rpc.ServiceException serviceException) {
	}
    }

    public String getEndpoint() {
	return _endpoint;
    }

    public void setEndpoint(String endpoint) {
	_endpoint = endpoint;
	if (nutritionistService != null)
	    ((javax.xml.rpc.Stub) nutritionistService)._setProperty(
		    "javax.xml.rpc.service.endpoint.address", _endpoint);

    }

    public NutritionistService getNutritionistService() {
	if (nutritionistService == null)
	    _initNutritionistServiceProxy();
	return nutritionistService;
    }

    public java.lang.String setSingleProfileProperty(String token, int type,
	    String propertyParentName, String propertyChildrenName,
	    int patientID, String[] values, boolean isMultiple)
	    throws NutriSecurityException, TokenExpiredException,
	    RemoteException {
	if (nutritionistService == null)
	    _initNutritionistServiceProxy();
	return nutritionistService.setSingleProfileProperty(token, type,
		propertyParentName, propertyChildrenName, patientID, values,
		isMultiple);
    }

    public java.lang.String setProfileProperty(String token, int patientID,
	    String propertyName, String value) throws NutriSecurityException,
	    TokenExpiredException, RemoteException {
	if (nutritionistService == null)
	    _initNutritionistServiceProxy();
	return nutritionistService.setProfileProperty(token, patientID,
		propertyName, value);
    }

    public PatientList[] getPatientsList(String token) throws RemoteException {
	if (nutritionistService == null)
	    _initNutritionistServiceProxy();
	return nutritionistService.getPatientsList(token);
    }

    public User getToken(String clientVersion, String username, String password)
	    throws RemoteException, na.ws.extra.NutriSecurityException {
	if (nutritionistService == null)
	    _initNutritionistServiceProxy();
	return nutritionistService.getToken(clientVersion, username, password);
    }

}