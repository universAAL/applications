package org.universAAL.AALapplication.medication_manager.servlet.ui.review.impl;

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
import org.universAAL.AALapplication.medication_manager.servlet.ui.review.impl.servlets.DisplayErrorPageWriterServlet;
import org.universAAL.AALapplication.medication_manager.servlet.ui.review.impl.servlets.DisplayIntakesHtmlWriterServlet;
import org.universAAL.AALapplication.medication_manager.servlet.ui.review.impl.servlets.DisplayLoginHtmlWriterServlet;
import org.universAAL.AALapplication.medication_manager.servlet.ui.review.impl.servlets.HandleSelectUserServlet;
import org.universAAL.AALapplication.medication_manager.servlet.ui.review.impl.servlets.LoginServlet;
import org.universAAL.AALapplication.medication_manager.servlet.ui.review.impl.servlets.SelectUserHtmlWriterServlet;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;

import javax.servlet.ServletException;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.review.impl.Util.*;

/**
 * @author George Fournadjiev
 */
public final class Activator implements BundleActivator {

  public static ModuleContext mc;
  public static BundleContext bundleContext;
  private static ServiceTracker configurationPropertiesServiceTracker;
  private static ServiceTracker persistenceServiceTracker;
  private static ServiceTracker httpServiceTracker;

  private static final String JS_ALIAS = "/review/js";
  private static final String CSS_ALIAS = "/review/css";

  public void start(final BundleContext context) throws Exception {
    mc = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[]{context});

    bundleContext = context;

    configurationPropertiesServiceTracker = new ServiceTracker(context, ConfigurationProperties.class.getName(), null);
    persistenceServiceTracker = new ServiceTracker(context, PersistentService.class.getName(), null);
    httpServiceTracker = new ServiceTracker(context, HttpService.class.getName(), null);

    configurationPropertiesServiceTracker.open();
    persistenceServiceTracker.open();
    httpServiceTracker.open();

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
    service.unregister(SELECT_USER_SERVLET_ALIAS);
    service.unregister(LOGIN_HTML_SERVLET_ALIAS);
    service.unregister(ERROR_PAGE_SERVLET_ALIAS);
    service.unregister(HANDLE_USER_SERVLET_ALIAS);
    service.unregister(INTAKES_PAGE_SERVLET_ALIAS);
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

    SelectUserHtmlWriterServlet selectUserServlet = new SelectUserHtmlWriterServlet(sessionTracking);
    httpService.registerServlet(SELECT_USER_SERVLET_ALIAS, selectUserServlet, null, null);
    LoginServlet loginServlet = new LoginServlet(sessionTracking);
    httpService.registerServlet(LOGIN_SERVLET_ALIAS, loginServlet, null, null);
    DisplayLoginHtmlWriterServlet displayServlet = new DisplayLoginHtmlWriterServlet(sessionTracking);
    httpService.registerServlet(LOGIN_HTML_SERVLET_ALIAS, displayServlet, null, null);
    DisplayErrorPageWriterServlet displayErrorPageWriterServlet = new DisplayErrorPageWriterServlet(sessionTracking);
    httpService.registerServlet(ERROR_PAGE_SERVLET_ALIAS, displayErrorPageWriterServlet, null, null);
    HandleSelectUserServlet handleSelectUserServlet = new HandleSelectUserServlet(sessionTracking);
    httpService.registerServlet(HANDLE_USER_SERVLET_ALIAS, handleSelectUserServlet, null, null);
    DisplayIntakesHtmlWriterServlet displayIntakesHtmlWriterServlet = new DisplayIntakesHtmlWriterServlet(sessionTracking);
    httpService.registerServlet(INTAKES_PAGE_SERVLET_ALIAS, displayIntakesHtmlWriterServlet, null, null);

    httpService.registerResources(JS_ALIAS, "js", null);
    httpService.registerResources(CSS_ALIAS, "css", null);


    //set servlets

    displayServlet.setDisplayErrorPageWriterServlet(displayErrorPageWriterServlet);

    loginServlet.setDisplayServlet(displayServlet);
    loginServlet.setSelectUserServlet(selectUserServlet);
    loginServlet.setDisplayErrorPageWriterServlet(displayErrorPageWriterServlet);

    selectUserServlet.setDisplayServlet(displayServlet);
    selectUserServlet.setDisplayErrorPageWriterServlet(displayErrorPageWriterServlet);

    handleSelectUserServlet.setDisplayServlet(displayServlet);
    handleSelectUserServlet.setDisplayErrorPageWriterServlet(displayErrorPageWriterServlet);
    handleSelectUserServlet.setDisplayIntakesHtmlWriterServlet(displayIntakesHtmlWriterServlet);

    displayIntakesHtmlWriterServlet.setDisplayErrorPageWriterServlet(displayErrorPageWriterServlet);
    displayIntakesHtmlWriterServlet.setDisplayServlet(displayServlet);
    displayIntakesHtmlWriterServlet.setSelectUserHtmlWriterServlet(selectUserServlet);

  }

  public static ConfigurationProperties getConfigurationProperties() {
    if (configurationPropertiesServiceTracker == null) {
      throw new MedicationManagerReviewException("The ConfigurationProperties ServiceTracker is not set");
    }
    ConfigurationProperties service = (ConfigurationProperties) configurationPropertiesServiceTracker.getService();
    if (service == null) {
      throw new MedicationManagerReviewException("The ConfigurationProperties is missing");
    }

    return service;
  }

  public static PersistentService getPersistentService() {
    if (persistenceServiceTracker == null) {
      throw new MedicationManagerReviewException("The PersistentService ServiceTracker is not set");
    }
    PersistentService service = (PersistentService) persistenceServiceTracker.getService();
    if (service == null) {
      throw new MedicationManagerReviewException("The PersistentService is missing");
    }

    return service;
  }

  private static HttpService getHttpService() {
    if (httpServiceTracker == null) {
      throw new MedicationManagerReviewException("The HttpService ServiceTracker is not set");
    }
    HttpService service = (HttpService) httpServiceTracker.getService();
    if (service == null) {
      throw new MedicationManagerReviewException("The HttpService is missing");
    }

    return service;
  }

}
