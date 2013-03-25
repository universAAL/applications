package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.forms.ScriptForm;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.forms.UserSelectScriptForm;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.impl.Activator.*;
import static org.universAAL.AALapplication.medication_manager.servlet.ui.impl.Util.*;

/**
 * @author George Fournadjiev
 */
public final class SelectUserServlet extends BaseServlet {

  private final Object lock = new Object();
  private DisplayServlet displayServlet;

  private static final String USER_HTML_FILE_NAME = "user.html";

  public SelectUserServlet() {
    super(USER_HTML_FILE_NAME);
  }

  public void setDisplayServlet(DisplayServlet displayServlet) {
    this.displayServlet = displayServlet;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    synchronized (lock) {
      isServletSet(displayServlet, "displayServlet");

      HttpSession httpSession = req.getSession(true);

      Person person = (Person) httpSession.getAttribute(LOGGED_DOCTOR);

      if (person == null) {
        displayServlet.doGet(req, resp);
        return;
      }

      resp.addCookie(new Cookie("sxqasq", "xwdwdwd"));
      resp.setHeader("wdsxqwdsw", "xdwdwdsw");

      handleResponse(resp);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }


  private void handleResponse(HttpServletResponse resp) throws IOException {
    try {
      PersistentService persistentService = getPersistentService();
      ScriptForm scriptForm = new UserSelectScriptForm(persistentService);
      sendResponse(resp, scriptForm);
    } catch (Exception e) {
      sendErrorResponse(resp, e);
    }
  }


}
