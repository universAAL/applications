package org.universAAL.AALapplication.medication_manager.servlet.ui.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.SelectUserServlet;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;

import javax.servlet.ServletException;

/**
 * @author George Fournadjiev
 */
public final class Activator implements BundleActivator {


  private static final String SELECT_USER_SERVLET_ALIAS = "/user";
  private static final String JS_ALIAS = "/js";
  private static final String CSS_ALIAS = "/css";
  public static ModuleContext mc;

  public void start(final BundleContext context) throws Exception {
    mc = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[]{context});

    registerServlet(context);

  }

  private void registerServlet(BundleContext context) throws ServletException, NamespaceException {
    HttpService httpService = getHttpService(context);

    SelectUserServlet selectUserServlet = new SelectUserServlet();
    httpService.registerServlet(SELECT_USER_SERVLET_ALIAS, selectUserServlet, null, null);
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

  public void stop(BundleContext context) throws Exception {

    HttpService service = getHttpService(context);

    service.unregister(SELECT_USER_SERVLET_ALIAS);
    service.unregister(JS_ALIAS);
    service.unregister(CSS_ALIAS);

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

}
