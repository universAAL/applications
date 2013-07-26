/*******************************************************************************
 * Copyright 2012 , http://www.prosyst.com - ProSyst Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package org.universAAL.AALapplication.medication_manager.servlet.ui.impl;

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
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.DisplayErrorPageWriterServlet;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.DisplayLoginHtmlWriterServlet;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.HandleMedicineServlet;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.HandleNewPrescriptionServlet;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.ListPrescriptionsHtmlWriterServlet;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.LoginServlet;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.MedicineHtmlWriterServlet;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.NewPrescriptionHtmlWriterServlet;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.SelectUserHtmlWriterServlet;
import org.universAAL.AALapplication.medication_manager.simulation.export.NewPrescriptionHandler;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;

import javax.servlet.ServletException;
import java.io.File;

import static java.io.File.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.impl.Util.*;

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

  private static final String JS_ALIAS = "/prescription/js";
  private static final String CSS_ALIAS = "/prescription/css";

  public void start(final BundleContext context) throws Exception {
    mc = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[]{context});

    bundleContext = context;

    configurationPropertiesServiceTracker = new ServiceTracker(context, ConfigurationProperties.class.getName(), null);
    persistenceServiceTracker = new ServiceTracker(context, PersistentService.class.getName(), null);
    httpServiceTracker = new ServiceTracker(context, HttpService.class.getName(), null);
    newPrescriptionHandlerTracker = new ServiceTracker(context, NewPrescriptionHandler.class.getName(), null);

    configurationPropertiesServiceTracker.open();
    persistenceServiceTracker.open();
    httpServiceTracker.open();
    newPrescriptionHandlerTracker.open();

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
    service.unregister(LIST_PRESCRIPTIONS_SERVLET_ALIAS);
    service.unregister(NEW_PRESCRIPTION_SERVLET_ALIAS);
    service.unregister(HANDLE_NEW_PRESCRIPTION_SERVLET_ALIAS);
    service.unregister(LOGIN_HTML_SERVLET_ALIAS);
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
    ListPrescriptionsHtmlWriterServlet listPrescriptionsServlet = new ListPrescriptionsHtmlWriterServlet(sessionTracking);
    httpService.registerServlet(LIST_PRESCRIPTIONS_SERVLET_ALIAS, listPrescriptionsServlet, null, null);
    NewPrescriptionHtmlWriterServlet newPrescriptionServlet = new NewPrescriptionHtmlWriterServlet(sessionTracking);
    httpService.registerServlet(NEW_PRESCRIPTION_SERVLET_ALIAS, newPrescriptionServlet, null, null);
    LoginServlet loginServlet = new LoginServlet(sessionTracking);
    httpService.registerServlet(LOGIN_SERVLET_ALIAS, loginServlet, null, null);
    HandleNewPrescriptionServlet handleNewPrescriptionServlet = new HandleNewPrescriptionServlet(sessionTracking);
    httpService.registerServlet(HANDLE_NEW_PRESCRIPTION_SERVLET_ALIAS, handleNewPrescriptionServlet, null, null);
    DisplayLoginHtmlWriterServlet displayServlet = new DisplayLoginHtmlWriterServlet(sessionTracking);
    httpService.registerServlet(LOGIN_HTML_SERVLET_ALIAS, displayServlet, null, null);
    MedicineHtmlWriterServlet medicineHtmlWriterServlet = new MedicineHtmlWriterServlet(sessionTracking);
    httpService.registerServlet(MEDICINE_SERVLET_ALIAS, medicineHtmlWriterServlet, null, null);
    HandleMedicineServlet handleMedicineServlet = new HandleMedicineServlet(sessionTracking);
    httpService.registerServlet(HANDLE_MEDICINE_SERVLET_ALIAS, handleMedicineServlet, null, null);
    DisplayErrorPageWriterServlet displayErrorPageWriterServlet = new DisplayErrorPageWriterServlet(sessionTracking);
    httpService.registerServlet(ERROR_PAGE_SERVLET_ALIAS, displayErrorPageWriterServlet, null, null);
    httpService.registerResources(JS_ALIAS, "js", null);
    httpService.registerResources(CSS_ALIAS, "css", null);


    //set servlets

    displayServlet.setDisplayErrorPageWriterServlet(displayErrorPageWriterServlet);

    loginServlet.setDisplayServlet(displayServlet);
    loginServlet.setSelectUserServlet(selectUserServlet);
    loginServlet.setDisplayErrorPageWriterServlet(displayErrorPageWriterServlet);

    selectUserServlet.setDisplayServlet(displayServlet);
    selectUserServlet.setDisplayErrorPageWriterServlet(displayErrorPageWriterServlet);
    selectUserServlet.setListPrescriptionsHtmlWriterServlet(listPrescriptionsServlet);

    listPrescriptionsServlet.setDisplayServlet(displayServlet);
    listPrescriptionsServlet.setSelectUserHtmlWriterServlet(selectUserServlet);
    listPrescriptionsServlet.setDisplayErrorPageWriterServlet(displayErrorPageWriterServlet);

    newPrescriptionServlet.setDisplayLoginHtmlWriterServlet(displayServlet);
    newPrescriptionServlet.setSelectUserHtmlWriterServlet(selectUserServlet);
    newPrescriptionServlet.setDisplayErrorPageWriterServlet(displayErrorPageWriterServlet);

    medicineHtmlWriterServlet.setDisplayLoginHtmlWriterServlet(displayServlet);
    medicineHtmlWriterServlet.setListPrescriptionsHtmlWriterServlet(listPrescriptionsServlet);
    medicineHtmlWriterServlet.setNewPrescriptionHtmlWriterServlet(newPrescriptionServlet);
    medicineHtmlWriterServlet.setDisplayErrorPageWriterServlet(displayErrorPageWriterServlet);

    handleMedicineServlet.setNewPrescriptionHtmlWriterServlet(newPrescriptionServlet);
    handleMedicineServlet.setDisplayLoginHtmlWriterServlet(displayServlet);
    handleMedicineServlet.setSelectUserHtmlWriterServlet(selectUserServlet);
    handleMedicineServlet.setDisplayErrorPageWriterServlet(displayErrorPageWriterServlet);

    handleNewPrescriptionServlet.setDisplayLoginHtmlWriterServlet(displayServlet);
    handleNewPrescriptionServlet.setSelectUserHtmlWriterServlet(selectUserServlet);
    handleNewPrescriptionServlet.setListPrescriptionsHtmlWriterServlet(listPrescriptionsServlet);
    handleNewPrescriptionServlet.setNewPrescriptionHtmlWriterServlet(newPrescriptionServlet);
    handleNewPrescriptionServlet.setDisplayErrorPageWriterServlet(displayErrorPageWriterServlet);

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
      throw new MedicationManagerServletUIException(e);
    }

    File directory = new File(pathToMedicationManagerConfigurationDirectory);
    if (!directory.exists()) {
      throw new MedicationManagerServletUIException("The directory does not exists:" + directory);
    }

    if (!directory.isDirectory()) {
      throw new MedicationManagerServletUIException("The following file:" + directory + " is not a valid directory");
    }

    return directory;
  }

  public static ConfigurationProperties getConfigurationProperties() {
    if (configurationPropertiesServiceTracker == null) {
      throw new MedicationManagerServletUIException("The ConfigurationProperties ServiceTracker is not set");
    }
    ConfigurationProperties service = (ConfigurationProperties) configurationPropertiesServiceTracker.getService();
    if (service == null) {
      throw new MedicationManagerServletUIException("The ConfigurationProperties is missing");
    }

    return service;
  }

  public static PersistentService getPersistentService() {
    if (persistenceServiceTracker == null) {
      throw new MedicationManagerServletUIException("The PersistentService ServiceTracker is not set");
    }
    PersistentService service = (PersistentService) persistenceServiceTracker.getService();
    if (service == null) {
      throw new MedicationManagerServletUIException("The PersistentService is missing");
    }

    return service;
  }

  private static HttpService getHttpService() {
    if (httpServiceTracker == null) {
      throw new MedicationManagerServletUIException("The HttpService ServiceTracker is not set");
    }
    HttpService service = (HttpService) httpServiceTracker.getService();
    if (service == null) {
      throw new MedicationManagerServletUIException("The HttpService is missing");
    }

    return service;
  }

  public static NewPrescriptionHandler getNewPrescriptionHandler() {
    if (newPrescriptionHandlerTracker == null) {
      throw new MedicationManagerServletUIException("The NewPrescriptionHandler ServiceTracker is not set");
    }
    NewPrescriptionHandler service = (NewPrescriptionHandler) newPrescriptionHandlerTracker.getService();
    if (service == null) {
      throw new MedicationManagerServletUIException("The NewPrescriptionHandler is missing");
    }

    return service;
  }

}
