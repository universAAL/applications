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
import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.DerbyDatabase;
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

  public void start(final BundleContext context) throws Exception {
    //this method call is used to help maven bnd plugin to generate corrrect import-package statements
    dummyMethodToHelpMavenBmdPlugin();

    mc = uAALBundleContainer.THE_CONTAINER.registerModule(new Object[]{context});

    connection = getConnection();
    Database derbyDatabase = new DerbyDatabase(connection);

    derbyDatabase.initDatabase();

    context.registerService(PersistentService.class.getName(),
        new PersistentServiceImpl(derbyDatabase), null);

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
      if (statement == null || statement.isClosed()) {
        return;
      }

      statement.close();
    } catch (SQLException e) {
      //nothing we could here except logging
      Log.error(e, "Unexpected error while closing statement", Activator.class);
    }
  }
}
