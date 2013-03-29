package org.universAAL.AALapplication.medication_manager.servlet.ui.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.DisplayErrorPageWriterServlet;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.DisplayLoginHtmlWriterServlet;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.HandleMedicineServlet;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.HandleNewPrescriptionServlet;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.ListPrescriptionsHtmlWriterServlet;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.LoginServlet;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.MedicineHtmlWriterServlet;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.NewPrescriptionHtmlWriterServlet;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.SelectUserHtmlWriterServlet;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.helpers.SessionTracking;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;

import javax.servlet.ServletException;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.impl.Util.*;

/**
 * @author George Fournadjiev
 */
public final class Activator implements BundleActivator {

  public static ModuleContext mc;
  public static BundleContext bundleContext;

  private static final String JS_ALIAS = "/js";
  private static final String CSS_ALIAS = "/css";

  public void start(final BundleContext context) throws Exception {
    mc = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[]{context});

    bundleContext = context;

    registerServlets(context);

  }

  public void stop(BundleContext context) throws Exception {

    bundleContext = null;
    HttpService service = getHttpService(context);

    service.unregister(LOGIN_SERVLET_ALIAS);
    service.unregister(SELECT_USER_SERVLET_ALIAS);
    service.unregister(LIST_PRESCRIPTIONS_SERVLET_ALIAS);
    service.unregister(NEW_PRESCRIPTION_SERVLET_ALIAS);
    service.unregister(HANDLE_NEW_PRESCRIPTION_SERVLET_ALIAS);
    service.unregister(LOGIN_HTML_SERVLET_ALIAS);
    service.unregister(JS_ALIAS);
    service.unregister(CSS_ALIAS);

  }

  private void registerServlets(BundleContext context) throws ServletException, NamespaceException {
    HttpService httpService = getHttpService(context);

    SessionTracking sessionTracking = new SessionTracking();

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

  private HttpService getHttpService(BundleContext context) {
    ServiceReference sr = context.getServiceReference(HttpService.class.getName());
    if (sr == null) {
      throw new MedicationManagerServletUIException("Missing ServiceReference for service: HttpService");
    }

    HttpService httpService = (HttpService) context.getService(sr);

    if (httpService == null) {
      throw new MedicationManagerServletUIException("Missing HttpService service");
    }
    return httpService;
  }

  public static PersistentService getPersistentService() {
    if (bundleContext == null) {
      throw new MedicationManagerServletUIException("The bundleContext is not set");
    }

    ServiceReference srPS = bundleContext.getServiceReference(PersistentService.class.getName());

    if (srPS == null) {
      throw new MedicationManagerServletUIException("The ServiceReference is null for PersistentService");
    }

    PersistentService persistentService = (PersistentService) bundleContext.getService(srPS);

    if (persistentService == null) {
      throw new MedicationManagerServletUIException("The PersistentService is missing");
    }

    return persistentService;
  }

}
