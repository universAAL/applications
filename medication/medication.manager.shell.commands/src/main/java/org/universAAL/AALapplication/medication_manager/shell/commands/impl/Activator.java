package org.universAAL.AALapplication.medication_manager.shell.commands.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;

import java.util.Hashtable;


/**
 * @author George Fournadjiev
 */
public class Activator implements BundleActivator {

  public static ModuleContext mc;

  public static final Logger logger = LoggerFactory.getLogger(Activator.class);
  private static final String OSGI_COMMAND_SCOPE = "osgi.command.scope";
  private static final String OSGI_COMMAND_FUNCTION = "osgi.command.function";

  public void start(final BundleContext context) throws Exception {
    mc = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[]{context});

    Hashtable props = new Hashtable();
    props.put(OSGI_COMMAND_SCOPE, MedicationConsoleCommands.COMMAND_PREFIX);
    props.put(OSGI_COMMAND_FUNCTION, new String[]{
        MedicationConsoleCommands.HELP_COMMMAND,
        MedicationConsoleCommands.LISTIDS_COMMMAND,
        MedicationConsoleCommands.USECASE_COMMMAND}
    );
    context.registerService(MedicationManagerCommands.class.getName(),
        new MedicationManagerCommands(), props);

  }

  public void stop(BundleContext context) throws Exception {
  }
}
