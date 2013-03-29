package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.servlets.helpers;

import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.MedicationManagerServletUIException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * @author George Fournadjiev
 */
public final class DebugWriter {

  private final PrintStream ps;

  public DebugWriter() {
    try {
      FileOutputStream out = new FileOutputStream("D:\\debug.txt");
      this.ps = new PrintStream(out);
    } catch (FileNotFoundException e) {
      throw new MedicationManagerServletUIException(e);
    }

  }

  public void println(String line) {
     ps.println(line);
  }
}
