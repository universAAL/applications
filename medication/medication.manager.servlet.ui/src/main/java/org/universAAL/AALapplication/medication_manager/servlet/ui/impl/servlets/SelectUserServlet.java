package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets;

import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.HtmlParser;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author George Fournadjiev
 */
public final class SelectUserServlet extends HttpServlet {


  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


    String htmlText = Util.getHtml("user.html");
    HtmlParser htmlParser = new HtmlParser(htmlText);

    String script = getScript();
    String htmlOutput = htmlParser.addScriptElement(script);

    PrintWriter writer = resp.getWriter();

    writer.println(htmlOutput);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }


  private static String getScript() {

    String script = "<script type=\"text/javascript\">\t\n" +
        "\tusers.push({'id':'user1', \"name\": \"Elena Ivanova\"});\n" +
        "\tusers.push({'id':'user2', \"name\": \"Polq Ivanova\"});\n" +
        "\tusers.push({'id':'user3', \"name\": \"Zoi Ivanova\"});\n" +
        "\tusers.push({'id':'user4', \"name\": \"Petq Ivanova\"});\n" +
        "\t</script>";

    return script;

  }
}
