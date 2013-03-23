package org.universAAL.AALapplication.medication_manager.servlet.ui.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.ListPrescriptionsServlet;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.LoginServlet;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.NewPrescriptionServlet;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.SelectUserServlet;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;

import javax.servlet.ServletException;

/**
 * @author George Fournadjiev
 */
public final class Activator implements BundleActivator {

  public static ModuleContext mc;
  public static BundleContext bundleContext;

  public static final String LOGIN_SERVLET_ALIAS = "/login";
  public static final String SELECT_USER_SERVLET_ALIAS = "/user";
  public static final String LIST_PRESCRIPTIONS_SERVLET_ALIAS = "/list";
  public static final String NEW_PRESCRIPTION_SERVLET_ALIAS = "/new";
  public static final String LOGIN_HTML = "/login.html";
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

    service.unregister(SELECT_USER_SERVLET_ALIAS);
    service.unregister(LIST_PRESCRIPTIONS_SERVLET_ALIAS);
    service.unregister(NEW_PRESCRIPTION_SERVLET_ALIAS);
    service.unregister(LOGIN_SERVLET_ALIAS);
    service.unregister(LOGIN_HTML);
    service.unregister(JS_ALIAS);
    service.unregister(CSS_ALIAS);

  }

  private void registerServlets(BundleContext context) throws ServletException, NamespaceException {
    HttpService httpService = getHttpService(context);

    SelectUserServlet selectUserServlet = new SelectUserServlet();
    httpService.registerServlet(SELECT_USER_SERVLET_ALIAS, selectUserServlet, null, null);
    ListPrescriptionsServlet listPrescriptionsServlet = new ListPrescriptionsServlet();
    httpService.registerServlet(LIST_PRESCRIPTIONS_SERVLET_ALIAS, listPrescriptionsServlet, null, null);
    NewPrescriptionServlet newPrescriptionServlet = new NewPrescriptionServlet();
    httpService.registerServlet(NEW_PRESCRIPTION_SERVLET_ALIAS, newPrescriptionServlet, null, null);
    LoginServlet loginServlet = new LoginServlet();
    httpService.registerServlet(LOGIN_SERVLET_ALIAS, loginServlet, null, null);
    httpService.registerResources(LOGIN_HTML, LOGIN_HTML, null);
    httpService.registerResources(JS_ALIAS, "js", null);
    httpService.registerResources(CSS_ALIAS, "css", null);
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

  public static void validateParameter(int parameter, String parameterName) {

    if (parameter <= 0) {
      throw new MedicationManagerServletUIException("The parameter : " +
          parameterName + " must be positive number");
    }

  }

  public static void validateParameter(Object parameter, String parameterName) {

    if (parameter == null) {
      throw new MedicationManagerServletUIException("The parameter : " + parameterName + " cannot be null");
    }

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
