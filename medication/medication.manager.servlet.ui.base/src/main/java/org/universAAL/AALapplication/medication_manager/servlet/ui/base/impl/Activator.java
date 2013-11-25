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

package org.universAAL.AALapplication.medication_manager.servlet.ui.base.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import org.universAAL.AALapplication.medication_manager.configuration.ConfigurationProperties;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.io.File.*;

/**
 * @author George Fournadjiev
 */
public final class Activator implements BundleActivator {

  public static ModuleContext mc;
  public static BundleContext bundleContext;
  private static ServiceTracker configurationPropertiesServiceTracker;

  public void start(final BundleContext context) throws Exception {
    mc = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[]{context});

    bundleContext = context;

    configurationPropertiesServiceTracker = new ServiceTracker(context, ConfigurationProperties.class.getName(), null);
    configurationPropertiesServiceTracker.open();

  }

  public void stop(BundleContext context) throws Exception {

    bundleContext = null;
    mc = null;


  }

  public static void validateParameter(Object parameter, String parameterName) {

    if (parameter == null) {
      throw new MedicationManagerServletUIBaseException("The parameter : " + parameterName + " cannot be null");
    }

  }

  public static File getMedicationManagerConfigurationDirectory() {

    String pathToMedicationManagerConfigurationDirectory;
    try {
      File currentDir = new File(".");
      String pathToCurrentDir = currentDir.getCanonicalPath();
      String bundlesConfigurationLocationProperty = System.getProperty("bundles.configuration.location");
      pathToMedicationManagerConfigurationDirectory = pathToCurrentDir + separator +
          bundlesConfigurationLocationProperty + separator + "medication_manager";
    } catch (Exception e) {
      throw new MedicationManagerServletUIBaseException(e);
    }

    File directory = new File(pathToMedicationManagerConfigurationDirectory);
    if (!directory.exists()) {
      throw new MedicationManagerServletUIBaseException("The directory does not exists:" + directory);
    }

    if (!directory.isDirectory()) {
      throw new MedicationManagerServletUIBaseException("The following file:" + directory + " is not a valid directory");
    }

    return directory;
  }

  public static String getHtml(String resourcePath) {
    InputStream inputStream = Activator.class.getClassLoader().getResourceAsStream(resourcePath);

    if (inputStream == null) {
      throw new MedicationManagerServletUIBaseException("The resource: " + resourcePath + " cannot be found");
    }

    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

    return getHtmlText(br);
  }

  private static String getHtmlText(BufferedReader br) {
    StringBuffer sb = new StringBuffer();
    try {
      String line = br.readLine();
      while (line != null) {
        sb.append(line);
        sb.append('\n');
        line = br.readLine();
      }
    } catch (IOException e) {
      throw new MedicationManagerServletUIBaseException(e);
    }

    return sb.toString();
  }

  public static ConfigurationProperties getConfigurationProperties() {
    if (configurationPropertiesServiceTracker == null) {
      throw new MedicationManagerServletUIBaseException("The ConfigurationProperties ServiceTracker is not set");
    }
    ConfigurationProperties service = (ConfigurationProperties) configurationPropertiesServiceTracker.getService();
    if (service == null) {
      throw new MedicationManagerServletUIBaseException("The ConfigurationProperties is missing");
    }

    return service;
  }


}
