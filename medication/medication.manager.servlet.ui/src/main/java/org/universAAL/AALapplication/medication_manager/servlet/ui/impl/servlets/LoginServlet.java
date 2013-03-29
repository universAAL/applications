package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets;

import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.DatabaseSimulation;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.helpers.Session;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.helpers.SessionTracking;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.impl.Util.*;

/**
 * @author George Fournadjiev
 */
public final class LoginServlet extends BaseServlet {


  private static final String USERNAME = "username";
  private static final String PASSWORD = "password";
  private final Object lock = new Object();
  private SelectUserHtmlWriterServlet selectUserServlet;
  private DisplayLoginHtmlWriterServlet displayServlet;

  public LoginServlet(SessionTracking sessionTracking) {
    super(sessionTracking);
  }

  public void setSelectUserServlet(SelectUserHtmlWriterServlet selectUserServlet) {
    this.selectUserServlet = selectUserServlet;
  }

  public void setDisplayServlet(DisplayLoginHtmlWriterServlet displayServlet) {
    this.displayServlet = displayServlet;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    synchronized (lock) {
      isServletSet(selectUserServlet, "selectUserServlet");
      isServletSet(displayServlet, "displayServlet");

      Session session = getSession(req, resp, getClass());

      Person person = (Person) session.getAttribute(LOGGED_DOCTOR);

      if (person == null) {
        debugSessions(session.getId(), "if (person is null) the servlet doGet/doPost method", getClass());
        person = findDoctor(req, session);
      }

      String cancel = req.getParameter(CANCEL);

      if (cancel != null && cancel.equalsIgnoreCase(TRUE)) {
        debugSessions(session.getId(), "cancel button (invalidate) the servlet doGet/doPost method", getClass());
        invalidateSession(req, resp);
        displayServlet.doGet(req, resp);
        return;
      }

      if (person != null) {
        debugSessions(session.getId(), "End of the servlet doGet/doPost method (person is not null", getClass());
        selectUserServlet.doGet(req, resp);
      } else {
        debugSessions(session.getId(), "End of the servlet doGet/doPost method (person is null)", getClass());
        displayServlet.doGet(req, resp);
      }

    }


  }

  private Person findDoctor(HttpServletRequest req, Session session) throws ServletException, IOException {

    String username = req.getParameter(USERNAME);
    String password = req.getParameter(PASSWORD);

    if (!validateParameters(username, password)) {
      return null;
    }

    Person person = findPerson(username, password);

    if (person != null) {
      session.setAttribute(LOGGED_DOCTOR, person);
    } else {
      session.setAttribute(LOGIN_ERROR, LOGIN_ERROR);
    }

    return person;
  }

  private boolean validateParameters(String username, String password) {

    boolean usernameCheck = username != null && !username.trim().isEmpty();
    boolean passwordCheck = password != null && !password.trim().isEmpty();

    return usernameCheck && passwordCheck;
  }

  private Person findPerson(String username, String password) {
    String doctorUsername = DatabaseSimulation.DOCTOR.getUsername();
    String doctorPassword = DatabaseSimulation.DOCTOR.getPassword();
    if (doctorUsername.equalsIgnoreCase(username) && doctorPassword.equalsIgnoreCase(password)) {
      return DatabaseSimulation.DOCTOR;
    }

    return null;
  }

}
