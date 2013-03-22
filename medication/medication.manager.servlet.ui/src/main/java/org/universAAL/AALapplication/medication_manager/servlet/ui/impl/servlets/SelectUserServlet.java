package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets;

import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.HtmlParser;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.Util;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.JavasrciptObjectCreator;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.Pair;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.Script;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * @author George Fournadjiev
 */
public final class SelectUserServlet extends HttpServlet {


  private static final String USER_HTML = "user.html";
  private final HtmlParser selectUserHtmlParser;

  public SelectUserServlet() {
    String htmlText = Util.getHtml(USER_HTML);
    selectUserHtmlParser = new HtmlParser(htmlText);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    synchronized (selectUserHtmlParser) {
      String script = getScript();
      String htmlOutput = selectUserHtmlParser.addScriptElement(script);

      PrintWriter writer = resp.getWriter();

      writer.println(htmlOutput);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }


  private static String getScript() {

    List<String> pairs = new LinkedList<String>();

    Pair<String> id = new Pair<String>("id", "1");
    Pair<String> name = new Pair<String>("name", "Pencho Penchev");
    addRow(pairs, id, name);

    Pair<String> id1 = new Pair<String>("id", "2");
    Pair<String> name1 = new Pair<String>("name", "Ivan Ivanov");
    addRow(pairs, id1, name1);

    Pair<String> id2 = new Pair<String>("id", "3");
    Pair<String> name2 = new Pair<String>("name", "Petar Petrov");
    addRow(pairs, id2, name2);

    return createSelectUserScript(pairs);

  }

  private static void addRow(List<String> pairs, Pair id, Pair<String> name) {
    JavasrciptObjectCreator creator = new JavasrciptObjectCreator();

    creator.addPair(id);
    creator.addPair(name);

    String javascriptObject = creator.createJavascriptObject();

    pairs.add(javascriptObject);
  }

  private static String createSelectUserScript(List<String> pairs) {


    String[] objects = new String[pairs.size()];
    objects = pairs.toArray(objects);
    Script script = new Script("users.push", objects);

    return script.getScriptText();

  }
}
