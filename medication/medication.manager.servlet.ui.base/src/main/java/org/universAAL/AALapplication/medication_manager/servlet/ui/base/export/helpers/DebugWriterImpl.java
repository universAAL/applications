package org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.helpers;

import org.universAAL.AALapplication.medication_manager.servlet.ui.base.impl.MedicationManagerServletUIBaseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.base.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class DebugWriterImpl implements DebugWriter {

  private final PrintStream ps;

  public DebugWriterImpl() {
    try {
      File dir = getMedicationManagerConfigurationDirectory();
      File file = new File(dir, "debug.log");
      FileOutputStream out = new FileOutputStream(file);
      this.ps = new PrintStream(out);
    } catch (FileNotFoundException e) {
      throw new MedicationManagerServletUIBaseException(e);
    }

  }

  public void println(String line) {
     ps.println(line);
  }
}
