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

import org.universAAL.AALapplication.medication_manager.persistence.impl.Log;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.AbstractDao;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Column;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.MedicineDTO;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.MealRelation;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Medicine;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.UnitClass;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author George Fournadjiev
 */
public final class MedicineDao extends AbstractDao {


  private static final String TABLE_NAME = "MEDICINE";
  private static final String MEDICINE_NAME = "MEDICINE_NAME";
  private static final String MEDICINE_INFO = "MEDICINE_INFO";
  private static final String SIDE_EFFECTS = "SIDE_EFFECTS";
  private static final String INCOMPLIANCES = "INCOMPLIANCES";
  private static final String MEAL_RELATION = "MEAL_RELATION";
  private static final String UNITS = "UNITS";

  public MedicineDao(Database database) {
    super(database, TABLE_NAME);
  }

  @Override
  @SuppressWarnings("unchecked")
  public Medicine getById(int id) {
    Log.info("Looking for the medicine with id=%s", getClass(), id);

    Map<String, Column> columns = getTableColumnsValuesById(id);

    return getMedicine(columns);
  }

  private Medicine getMedicine(Map<String, Column> columns) {
    Column col = columns.get(ID);
    int medicineId = (Integer) col.getValue();

    col = columns.get(MEDICINE_NAME);
    String medicineName = (String) col.getValue();

    col = columns.get(MEDICINE_INFO);
    String medicineInfo = (String) col.getValue();

    col = columns.get(SIDE_EFFECTS);
    String sideEffects = (String) col.getValue();

    col = columns.get(INCOMPLIANCES);
    String incompliances = (String) col.getValue();

    col = columns.get(MEAL_RELATION);
    String mealRelationText = (String) col.getValue();
    MealRelation mealRelation = MealRelation.getEnumValueFor(mealRelationText);

    col = columns.get(UNITS);
    String units = (String) col.getValue();
    UnitClass unitClass = UnitClass.getEnumValueFor(units);

    Medicine medicine = new Medicine(
        medicineId,
        medicineName,
        medicineInfo,
        unitClass,
        sideEffects,
        incompliances,
        mealRelation
    );

//    Log.info("Medicine found: %s", getClass(), medicine);
    Log.info("Medicine found with id : %s", getClass(), medicine.getId());

    return medicine;
  }

  public List<Medicine> getMedicinesWithName(Set<MedicineDTO> medicineDTOSet) throws SQLException {
    if (medicineDTOSet.isEmpty()) {
      return new ArrayList<Medicine>();
    }
    StringBuffer sqlBuffer = new StringBuffer();

    sqlBuffer.append("select * from MEDICATION_MANAGER.MEDICINE\n" +
        "  where UPPER(MEDICINE_NAME) in(");

    int size = medicineDTOSet.size();

    int i = 0;
    for (MedicineDTO dto : medicineDTOSet) {
      String name = dto.getName();
      sqlBuffer.append('\'');
      sqlBuffer.append(name.toUpperCase());
      sqlBuffer.append('\'');
      i++;
      if (i < size) {
        sqlBuffer.append(", ");
      }
    }

    sqlBuffer.append(')');

    String sql = sqlBuffer.toString();

    List<Map<String, Column>> results = database.executeQueryExpectedMultipleRecord(TABLE_NAME, getPreparedStatement(sql));

    return createMedicines(results);
  }

  private List<Medicine> createMedicines(List<Map<String, Column>> results) {
    List<Medicine> medicinesList = new ArrayList<Medicine>();

    for (Map<String, Column> columnMap : results) {
      Medicine medicine = getMedicine(columnMap);
      medicinesList.add(medicine);
    }

    return medicinesList;

  }
}
