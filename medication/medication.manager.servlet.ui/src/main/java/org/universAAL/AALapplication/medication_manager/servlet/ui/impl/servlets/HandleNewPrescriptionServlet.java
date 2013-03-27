package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets;

import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.helpers.SessionTracking;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * @author George Fournadjiev
 */
public final class HandleNewPrescriptionServlet extends BaseServlet {


  public HandleNewPrescriptionServlet(SessionTracking sessionTracking) {
    super(sessionTracking);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doPost(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    PrintWriter writer = resp.getWriter();
    Enumeration en = req.getParameterNames();
    while (en.hasMoreElements()) {
      String parameterName = (String) en.nextElement();
      String value = req.getParameter(parameterName);
      writer.println(parameterName + " : " + value);
    }

    writer.println("done");
  }
}
