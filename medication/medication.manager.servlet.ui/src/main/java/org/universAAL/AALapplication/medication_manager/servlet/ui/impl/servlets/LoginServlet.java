package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets;

import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.DatabaseSimulation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

      HttpSession httpSession = getSession(req);

      Person person = (Person) httpSession.getAttribute(LOGGED_DOCTOR);

      if (person == null) {
        person = findDoctor(req, httpSession);
      }

      String cancel = req.getParameter(CANCEL);

      if (cancel != null && cancel.equalsIgnoreCase(TRUE)) {
        invalidateSession(req.getRequestedSessionId());
        displayServlet.doGet(req, resp);
        return;
      }

      if (person != null) {
        selectUserServlet.doGet(req, resp);
      } else {
        displayServlet.doGet(req, resp);
      }

    }


  }

  private Person findDoctor(HttpServletRequest req, HttpSession httpSession) throws ServletException, IOException {

    String username = req.getParameter(USERNAME);
    String password = req.getParameter(PASSWORD);

    if (!validateParameters(username, password)) {
      return null;
    }

    Person person = findPerson(username, password);

    if (person != null) {
      httpSession.setAttribute(LOGGED_DOCTOR, person);
    } else {
      httpSession.setAttribute(LOGIN_ERROR, LOGIN_ERROR);
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
