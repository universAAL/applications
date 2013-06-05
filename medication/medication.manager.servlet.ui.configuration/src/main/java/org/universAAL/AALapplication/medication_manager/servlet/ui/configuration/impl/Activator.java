package org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.universAAL.AALapplication.medication_manager.configuration.ConfigurationProperties;
import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.DebugWriter;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.DebugWriterDummy;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.DebugWriterImpl;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.SessionTracking;
import org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets.DisplayErrorPageWriterServlet;
import org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets.DisplayLoginHtmlWriterServlet;
import org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets.DisplaySelectConfigActionHtmlWriterServlet;
import org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets.DisplayUserManagementHtmlWriterServlet;
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

  public void start(final BundleContext context) throws Exception {
    mc = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[]{context});

    bundleContext = context;

    ConfigurationProperties configurationProperties = getConfigurationProperties();

    long httpSessionExpireTimeoutInMinutes = configurationProperties.getHttpSessionExpireTimeoutInMinutes();

    int httpSessionTimerCheckerIntervalInMinutes = configurationProperties.getHttpSessionTimerCheckerIntervalInMinutes();

    boolean isDebugOn = configurationProperties.isDebugWriterOn();

    registerServlets(context, httpSessionExpireTimeoutInMinutes, httpSessionTimerCheckerIntervalInMinutes, isDebugOn);

  }

  public void stop(BundleContext context) throws Exception {

    bundleContext = null;
    HttpService service = getHttpService(context);

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


    HttpService httpService = getHttpService(context);


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

  }

  private HttpService getHttpService(BundleContext context) {
    ServiceReference sr = context.getServiceReference(HttpService.class.getName());
    if (sr == null) {
      throw new MedicationManagerServletUIConfigurationException("Missing ServiceReference for service: HttpService");
    }

    HttpService httpService = (HttpService) context.getService(sr);

    if (httpService == null) {
      throw new MedicationManagerServletUIConfigurationException("Missing HttpService service");
    }
    return httpService;
  }

  public static PersistentService getPersistentService() {
    if (bundleContext == null) {
      throw new MedicationManagerServletUIConfigurationException("The bundleContext is not set");
    }

    ServiceReference srPS = bundleContext.getServiceReference(PersistentService.class.getName());

    if (srPS == null) {
      throw new MedicationManagerServletUIConfigurationException("The ServiceReference is null for PersistentService");
    }

    PersistentService persistentService = (PersistentService) bundleContext.getService(srPS);

    if (persistentService == null) {
      throw new MedicationManagerServletUIConfigurationException("The PersistentService is missing");
    }

    return persistentService;
  }

  public static UserManager getUserManager() {
    if (bundleContext == null) {
      throw new MedicationManagerServletUIConfigurationException("The bundleContext is not set");
    }

    ServiceReference srPS = bundleContext.getServiceReference(UserManager.class.getName());

    if (srPS == null) {
      throw new MedicationManagerServletUIConfigurationException("The ServiceReference is null for UserManager");
    }

    UserManager userManager = (UserManager) bundleContext.getService(srPS);

    if (userManager == null) {
      throw new MedicationManagerServletUIConfigurationException("The UserManager is missing");
    }

    return userManager;
  }

  public static ConfigurationProperties getConfigurationProperties() {
    if (bundleContext == null) {
      throw new MedicationManagerServletUIConfigurationException("The bundleContext is not set");
    }

    ServiceReference srPS = bundleContext.getServiceReference(ConfigurationProperties.class.getName());

    if (srPS == null) {
      throw new MedicationManagerServletUIConfigurationException("The ServiceReference is null for ConfigurationProperties");
    }

    ConfigurationProperties service = (ConfigurationProperties) bundleContext.getService(srPS);

    if (service == null) {
      throw new MedicationManagerServletUIConfigurationException("The ConfigurationProperties is missing");
    }

    return service;
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

  public static NewPrescriptionHandler getNewPrescriptionHandler() {
    if (bundleContext == null) {
      throw new MedicationManagerServletUIConfigurationException("The bundleContext is not set");
    }

    ServiceReference sr = bundleContext.getServiceReference(NewPrescriptionHandler.class.getName());

    if (sr == null) {
      throw new MedicationManagerServletUIConfigurationException("The ServiceReference is null for NewPrescriptionHandler");
    }

    NewPrescriptionHandler newPrescriptionHandler = (NewPrescriptionHandler) bundleContext.getService(sr);
    if (newPrescriptionHandler == null) {
      throw new MedicationManagerServletUIConfigurationException("The NewPrescriptionHandler service is missing");
    }
    return newPrescriptionHandler;
  }

}
