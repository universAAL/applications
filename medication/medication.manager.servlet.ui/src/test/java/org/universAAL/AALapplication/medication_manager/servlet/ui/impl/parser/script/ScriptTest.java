package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script;

import org.junit.Test;

/**
 * @author George Fournadjiev
 */
public final class ScriptTest {

  @Test
  public void testGetScriptText() throws Exception {
    UserScriptFactory userScriptFactory = new UserScriptFactory();
    Pair<String> id = new Pair<String>("id", "u1");
    Pair<String> name = new Pair<String>("name", "Pencho Penchev");
    userScriptFactory.addRow(id, name);

    Pair<String> id1 = new Pair<String>("id", "u2");
    Pair<String> name1 = new Pair<String>("name", "Ivan Ivanov");
    userScriptFactory.addRow(id1, name1);

    String scriptText = userScriptFactory.createSelectUserScript();

    System.out.println(scriptText);
  }
}
