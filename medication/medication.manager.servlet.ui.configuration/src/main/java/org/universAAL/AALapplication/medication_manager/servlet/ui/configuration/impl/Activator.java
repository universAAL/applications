package org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.osgi.util.tracker.ServiceTracker;
import org.universAAL.AALapplication.medication_manager.configuration.ConfigurationProperties;
import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.DebugWriter;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.DebugWriterDummy;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.DebugWriterImpl;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.SessionTracking;
import org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets.DisplayErrorPageWriterServlet;
import org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets.DisplayLoginHtmlWriterServlet;
import org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets.DisplayNotificationsHtmlWriterServlet;
import org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets.DisplayParametersHtmlWriterServlet;
import org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets.DisplaySelectConfigActionHtmlWriterServlet;
import org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets.DisplayUserManagementHtmlWriterServlet;
import org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets.HandleNotifications;
import org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets.HandleParameters;
import org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets.HandleUserManagement;
import org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets.LoginServlet;
import org.universAAL.AALapplication.medication_manager.simulation.export.NewPrescriptionHandler;
import org.universAAL.AALapplication.medication_manager.user.management.UserManager;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;

import javax.servlet.ServletException;
import java.io.File;

import static java.io.File.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.Util.*;

/**
 * @author George Fournadjiev
 */
public final class Activator implements BundleActivator {

  public static ModuleContext mc;
  public static BundleContext bundleContext;
  private static ServiceTracker configurationPropertiesServiceTracker;
  private static ServiceTracker persistenceServiceTracker;
  private static ServiceTracker httpServiceTracker;
  private static ServiceTracker newPrescriptionHandlerTracker;
  private static ServiceTracker userManagerTracker;

  public void start(final BundleContext context) throws Exception {
    mc = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[]{context});

    bundleContext = context;

    configurationPropertiesServiceTracker = new ServiceTracker(context, ConfigurationProperties.class.getName(), null);
    persistenceServiceTracker = new ServiceTracker(context, PersistentService.class.getName(), null);
    httpServiceTracker = new ServiceTracker(context, HttpService.class.getName(), null);
    newPrescriptionHandlerTracker = new ServiceTracker(context, NewPrescriptionHandler.class.getName(), null);
    userManagerTracker = new ServiceTracker(context, UserManager.class.getName(), null);

    configurationPropertiesServiceTracker.open();
    persistenceServiceTracker.open();
    httpServiceTracker.open();
    newPrescriptionHandlerTracker.open();
    userManagerTracker.open();

    ConfigurationProperties configurationProperties = getConfigurationProperties();

    long httpSessionExpireTimeoutInMinutes = configurationProperties.getHttpSessionExpireTimeoutInMinutes();

    int httpSessionTimerCheckerIntervalInMinutes = configurationProperties.getHttpSessionTimerCheckerIntervalInMinutes();

    boolean isDebugOn = configurationProperties.isDebugWriterOn();

    registerServlets(context, httpSessionExpireTimeoutInMinutes, httpSessionTimerCheckerIntervalInMinutes, isDebugOn);

  }

  public void stop(BundleContext context) throws Exception {

    bundleContext = null;
    HttpService service = getHttpService();

    service.unregister(LOGIN_SERVLET_ALIAS);
    service.unregister(LOGIN_HTML_SERVLET_ALIAS);
    service.unregister(ERROR_PAGE_SERVLET_ALIAS);
    service.unregister(CONFIG_ACTION_SELECTOR);
    service.unregister(USER_MANAGEMENT);
    service.unregister(JS_ALIAS);
    service.unregister(CSS_ALIAS);

  }

  private void registerServlets(BundleContext context,
                                long httpSessionExpireTimeoutInMinutes,
                                int httpSessionTimerCheckerIntervalInMinutes, boolean debugOn)
      throws ServletException, NamespaceException {


    HttpService httpService = getHttpService();


    DebugWriter debugWriter;
    if (debugOn) {
      debugWriter = new DebugWriterImpl();
    } else {
      debugWriter = new DebugWriterDummy();
    }

    SessionTracking sessionTracking =
        new SessionTracking(httpSessionExpireTimeoutInMinutes, debugWriter, httpSessionTimerCheckerIntervalInMinutes);

    LoginServlet loginServlet = new LoginServlet(sessionTracking);
    httpService.registerServlet(LOGIN_SERVLET_ALIAS, loginServlet, null, null);
    DisplayLoginHtmlWriterServlet displayLoginServlet = new DisplayLoginHtmlWriterServlet(sessionTracking);
    httpService.registerServlet(LOGIN_HTML_SERVLET_ALIAS, displayLoginServlet, null, null);
    DisplayErrorPageWriterServlet displayErrorPageWriterServlet = new DisplayErrorPageWriterServlet(sessionTracking);
    httpService.registerServlet(ERROR_PAGE_SERVLET_ALIAS, displayErrorPageWriterServlet, null, null);
    httpService.registerResources(JS_ALIAS, "/configuration/js", null);
    httpService.registerResources(CSS_ALIAS, "/configuration/css", null);
    DisplaySelectConfigActionHtmlWriterServlet displaySelectConfigActionHtmlWriterServlet =
        new DisplaySelectConfigActionHtmlWriterServlet(sessionTracking);
    httpService.registerServlet(CONFIG_ACTION_SELECTOR, displaySelectConfigActionHtmlWriterServlet, null, null);
    DisplayUserManagementHtmlWriterServlet displayUserManagementHtmlWriterServlet =
        new DisplayUserManagementHtmlWriterServlet(sessionTracking);
    httpService.registerServlet(USER_MANAGEMENT, displayUserManagementHtmlWriterServlet, null, null);
    HandleUserManagement handleUserManagement = new HandleUserManagement(sessionTracking);
    httpService.registerServlet(HANDLE_USER, handleUserManagement, null, null);
    DisplayParametersHtmlWriterServlet displayParametersHtmlWriterServlet =
        new DisplayParametersHtmlWriterServlet(sessionTracking);
    httpService.registerServlet(PARAMETERS_HANDLER, displayParametersHtmlWriterServlet, null, null);
    HandleParameters handleParameters = new HandleParameters(sessionTracking);
    httpService.registerServlet(HANDLE_PARAMETERS, handleParameters, null, null);

    DisplayNotificationsHtmlWriterServlet displayNotificationsHtmlWriterServlet =
        new DisplayNotificationsHtmlWriterServlet(sessionTracking);
    httpService.registerServlet(NOTIFICATIONS_HANDLER, displayNotificationsHtmlWriterServlet, null, null);
    HandleNotifications handleNotifications = new HandleNotifications(sessionTracking);
    httpService.registerServlet(HANDLE_NOTIFICATIONS, handleNotifications, null, null);


    //set servlets

    displayLoginServlet.setDisplayErrorPageWriterServlet(displayErrorPageWriterServlet);

    loginServlet.setDisplayLoginHtmlWriterServlet(displayLoginServlet);
    loginServlet.setDisplayErrorPageWriterServlet(displayErrorPageWriterServlet);
    loginServlet.setDisplaySelectConfigActionHtmlWriterServlet(displaySelectConfigActionHtmlWriterServlet);

    displaySelectConfigActionHtmlWriterServlet.setDisplayServlet(displayLoginServlet);
    displaySelectConfigActionHtmlWriterServlet.setDisplayErrorPageWriterServlet(displayErrorPageWriterServlet);

    displayUserManagementHtmlWriterServlet.setDisplayLoginHtmlWriterServlet(displayLoginServlet);
    displayUserManagementHtmlWriterServlet.setDisplayErrorPageWriterServlet(displayErrorPageWriterServlet);

    handleUserManagement.setDisplayLoginHtmlWriterServlet(displayLoginServlet);
    handleUserManagement.setDisplayErrorPageWriterServlet(displayErrorPageWriterServlet);
    handleUserManagement.setUserManagementHtmlWriterServlet(displayUserManagementHtmlWriterServlet);

    displayParametersHtmlWriterServlet.setDisplayLoginHtmlWriterServlet(displayLoginServlet);
    displayParametersHtmlWriterServlet.setDisplayErrorPageWriterServlet(displayErrorPageWriterServlet);

    handleParameters.setDisplayLoginHtmlWriterServlet(displayLoginServlet);
    handleParameters.setDisplayErrorPageWriterServlet(displayErrorPageWriterServlet);
    handleParameters.setDisplayParametersHtmlWriterServlet(displayParametersHtmlWriterServlet);

    displayNotificationsHtmlWriterServlet.setDisplayLoginHtmlWriterServlet(displayLoginServlet);
    displayNotificationsHtmlWriterServlet.setDisplayErrorPageWriterServlet(displayErrorPageWriterServlet);

    handleNotifications.setDisplayLoginHtmlWriterServlet(displayLoginServlet);
    handleNotifications.setDisplayErrorPageWriterServlet(displayErrorPageWriterServlet);
    handleNotifications.setDisplayNotificationsHtmlWriterServlet(displayNotificationsHtmlWriterServlet);

  }

  public static File getMedicationManagerConfigurationDirectory() {

    String pathToMedicationManagerConfigurationDirectory;
    try {
      File currentDir = new File(".");
      String pathToCurrentDir = currentDir.getCanonicalPath();
      String bundlesConfigurationLocationProperty = System.getProperty("bundles.configuration.location");
      pathToMedicationManagerConfigurationDirectory = pathToCurrentDir + separator +
          bundlesConfigurationLocationProperty + separator + "medication_manager";
    } catch (Exception e) {
      throw new MedicationManagerServletUIConfigurationException(e);
    }

    File directory = new File(pathToMedicationManagerConfigurationDirectory);
    if (!directory.exists()) {
      throw new MedicationManagerServletUIConfigurationException("The directory does not exists:" + directory);
    }

    if (!directory.isDirectory()) {
      throw new MedicationManagerServletUIConfigurationException("The following file:" + directory + " is not a valid directory");
    }

    return directory;
  }


  public static ConfigurationProperties getConfigurationProperties() {
    if (configurationPropertiesServiceTracker == null) {
      throw new MedicationManagerServletUIConfigurationException("The ConfigurationProperties ServiceTracker is not set");
    }
    ConfigurationProperties service = (ConfigurationProperties) configurationPropertiesServiceTracker.getService();
    if (service == null) {
      throw new MedicationManagerServletUIConfigurationException("The ConfigurationProperties is missing");
    }

    return service;
  }

  public static PersistentService getPersistentService() {
    if (persistenceServiceTracker == null) {
      throw new MedicationManagerServletUIConfigurationException("The PersistentService ServiceTracker is not set");
    }
    PersistentService service = (PersistentService) persistenceServiceTracker.getService();
    if (service == null) {
      throw new MedicationManagerServletUIConfigurationException("The PersistentService is missing");
    }

    return service;
  }

  private static HttpService getHttpService() {
    if (httpServiceTracker == null) {
      throw new MedicationManagerServletUIConfigurationException("The HttpService ServiceTracker is not set");
    }
    HttpService service = (HttpService) httpServiceTracker.getService();
    if (service == null) {
      throw new MedicationManagerServletUIConfigurationException("The HttpService is missing");
    }

    return service;
  }

  public static NewPrescriptionHandler getNewPrescriptionHandler() {
    if (newPrescriptionHandlerTracker == null) {
      throw new MedicationManagerServletUIConfigurationException("The NewPrescriptionHandler ServiceTracker is not set");
    }
    NewPrescriptionHandler service = (NewPrescriptionHandler) newPrescriptionHandlerTracker.getService();
    if (service == null) {
      throw new MedicationManagerServletUIConfigurationException("The NewPrescriptionHandler is missing");
    }

    return service;
  }

  public static UserManager getUserManager() {
    if (userManagerTracker == null) {
      throw new MedicationManagerServletUIConfigurationException("The UserManager ServiceTracker is not set");
    }
    UserManager service = (UserManager) userManagerTracker.getService();
    if (service == null) {
      throw new MedicationManagerServletUIConfigurationException("The UserManager is missing");
    }

    return service;
  }

}
