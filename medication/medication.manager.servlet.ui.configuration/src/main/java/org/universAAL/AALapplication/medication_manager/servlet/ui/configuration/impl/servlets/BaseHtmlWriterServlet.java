/*******************************************************************************
 * Copyright 2012 , http://www.prosyst.com - ProSyst Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package org.universAAL.AALapplication.medication_manager.servlet.ui.configuration.impl.servlets;

import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.SessionTracking;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.HtmlParser;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.forms.ScriptForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers.ServletUtil.*;


/**
 * @author George Fournadjiev
 */
public abstract class BaseHtmlWriterServlet extends BaseServlet {

  protected final HtmlParser htmlParser;

  protected BaseHtmlWriterServlet(String htmlFileName, SessionTracking sessionTracking) {
    super(sessionTracking);

    String htmlText = getHtml(htmlFileName, getClass().getClassLoader());
    htmlParser = new HtmlParser(htmlText);
  }

  public void sendResponse(HttpServletRequest req, HttpServletResponse resp,
                           ScriptForm scriptForm) throws IOException {

    String scriptText = scriptForm.createScriptText();

    sendSuccessfulResponse(resp, scriptText);


  }

  private void sendSuccessfulResponse(HttpServletResponse resp, String script) throws IOException {
    String htmlOutput = htmlParser.addScriptElement(script);

    PrintWriter writer = resp.getWriter();

    writer.println(htmlOutput);
  }


}
