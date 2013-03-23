package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets;

import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Role;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class LoginServlet extends HttpServlet {

  private static final Person DOCTOR = new Person("Penchno", "uri", Role.PHYSICIAN);
  private static final String LOGGED_DOCTOR = "Doctor";

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    HttpSession httpSession = req.getSession(true);

    Person person = (Person) httpSession.getAttribute(LOGGED_DOCTOR);

    if (person == null) {
      httpSession.setAttribute(LOGGED_DOCTOR, DOCTOR);
    }


    resp.sendRedirect(LIST_PRESCRIPTIONS_SERVLET_ALIAS);


  }

}
