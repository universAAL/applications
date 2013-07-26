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

package org.universAAL.AALapplication.medication_manager.persistence.layer.dao;

import org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.AbstractDao;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.InventoryLog;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class InventoryLogDao extends AbstractDao {


  private static final String TABLE_NAME = "INVENTORY_LOG";

  public InventoryLogDao(Database database) {
    super(database, TABLE_NAME);
  }

  @Override
  @SuppressWarnings("unchecked")
  public InventoryLog getById(int id) {
    throw new UnsupportedOperationException("Not implemented yet");
  }


  public void save(InventoryLog inventoryLog) {

    String sql = "insert into medication_manager.inventory_log " +
        "(id, time_of_creation, patient_fk_id, medicine_fk_id, change_quantity, units, reference)" +
        "values (?, ?, ?, ?, ?, ?, ?)";

    PreparedStatement ps = null;

    try {
      ps = getPreparedStatement(sql);
      ps.setInt(1, inventoryLog.getId());
      Timestamp timeOfCreation = new Timestamp(inventoryLog.getTimeOfCreation().getTime());
      ps.setTimestamp(2, timeOfCreation);
      ps.setInt(3, inventoryLog.getPatient().getId());
      ps.setInt(4, inventoryLog.getMedicine().getId());
      ps.setInt(5, inventoryLog.getChangedQuantity());
      ps.setString(6, inventoryLog.getUnitClass().getType());
      ps.setString(7, inventoryLog.getReference().getType());

      ps.execute();
    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException(e);
    } finally {
      closeStatement(ps);
    }

  }
}
