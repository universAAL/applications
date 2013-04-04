package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.helpers;

import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.Activator;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.MedicationManagerServletUIException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * @author George Fournadjiev
 */
public final class DebugWriterImpl implements DebugWriter {

  private final PrintStream ps;

  public DebugWriterImpl() {
    try {
      File dir = Activator.getMedicationManagerConfigurationDirectory();
      File file = new File(dir, "debug.log");
      FileOutputStream out = new FileOutputStream(file);
      this.ps = new PrintStream(out);
    } catch (FileNotFoundException e) {
      throw new MedicationManagerServletUIException(e);
    }

  }

  public void println(String line) {
     ps.println(line);
  }
}
