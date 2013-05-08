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


package org.universAAL.AALapplication.medication_manager.persistence.impl;

import org.apache.derby.jdbc.ResourceAdapterImpl;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.universAAL.AALapplication.medication_manager.configuration.ConfigurationProperties;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.DerbyDatabase;
import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.osgi.uAALBundleContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.database.DerbyDatabase.*;


/**
 * @author George Fournadjiev
 */
public class Activator implements BundleActivator {

  private Connection connection = null;
  public static ModuleContext mc;
  public static BundleContext bundleContext;

  public void start(final BundleContext context) throws Exception {
    //this method call is used to help maven bnd plugin to generate corrrect import-package statements
    dummyMethodToHelpMavenBmdPlugin();

    bundleContext = context;
    mc = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[]{context});

    connection = getConnection();
    ConfigurationProperties configurationProperties = getConfigurationProperties();
    Database derbyDatabase = new DerbyDatabase(connection, configurationProperties);

    derbyDatabase.initDatabase();

    PersistentService persistentService = new PersistentServiceImpl(derbyDatabase, configurationProperties);

    context.registerService(PersistentService.class.getName(),
        persistentService, null);

  }

  public void stop(BundleContext context) throws Exception {
    connection.close();
    connection = null;
  }

  public void dummyMethodToHelpMavenBmdPlugin() {
    Class dummyClass = ResourceAdapterImpl.class;
  }

  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection("jdbc:derby:" + MEDICATION_MANAGER + ";create=true;");
  }

  public static void closeStatement(Statement statement) {
    try {
      if (statement == null) {
        return;
      }

      statement.close();
    } catch (SQLException e) {
      //nothing we could here except logging
      Log.error(e, "Unexpected error while closing statement", Activator.class);
    }
  }

  public static void validateParameter(int parameter, String parameterName) {

    if (parameter <= 0) {
      throw new MedicationManagerPersistenceException("The parameter : " +
          parameterName + " must be positive number");
    }

  }

  public static void validateParameter(Object parameter, String parameterName) {

    if (parameter == null) {
      throw new MedicationManagerPersistenceException("The parameter : " + parameterName + " cannot be null");
    }

  }

  public static ConfigurationProperties getConfigurationProperties() {
    if (bundleContext == null) {
      throw new MedicationManagerPersistenceException("The bundleContext is not set");
    }

    ServiceReference srPS = bundleContext.getServiceReference(ConfigurationProperties.class.getName());

    if (srPS == null) {
      throw new MedicationManagerPersistenceException("The ServiceReference is null for ConfigurationProperties");
    }

    ConfigurationProperties service = (ConfigurationProperties) bundleContext.getService(srPS);

    if (service == null) {
      throw new MedicationManagerPersistenceException("The ConfigurationProperties is missing");
    }
    return service;
  }
}
