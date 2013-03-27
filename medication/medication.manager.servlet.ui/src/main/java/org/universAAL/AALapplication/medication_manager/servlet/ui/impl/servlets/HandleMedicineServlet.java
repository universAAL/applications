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
public final class HandleMedicineServlet extends BaseServlet {


  public HandleMedicineServlet(SessionTracking sessionTracking) {
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

    String[] value = req.getParameterValues("hours");
    writer.println("Printing array of hours " + value);
    if (value != null) {
      for (String s : value) {
        writer.println("hour = " + s);
      }
    }
    writer.println("done (Medicine)");
  }
}
