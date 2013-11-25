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

package org.universAAL.AALapplication.medication_manager.ui.impl;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.IntakeDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.MedicineInventoryDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Intake;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.MedicineInventory;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.UnitClass;

import java.util.List;

import static org.universAAL.AALapplication.medication_manager.persistence.layer.entities.UnitClass.*;
import static org.universAAL.AALapplication.medication_manager.ui.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class InventoryStatusHelper {


  public static final String PILL = "pill";

  public String getInventoryStatusText(Person patient, PersistentService persistentService) {

    MedicineInventoryDao medicineInventoryDao = persistentService.getMedicineInventoryDao();

    List<MedicineInventory> medicineInventories = medicineInventoryDao.getAllMedicineInventoriesForPatient(patient);
    IntakeDao intakeDao = persistentService.getIntakeDao();
    List<Intake> intakes = intakeDao.getIntakesByUserAndTimePlanDue(patient);
    String inventoryStatusText = createInventoryText(patient.getName(), medicineInventories, intakes);

    Log.info("Loaded from the database the following inventory status text:\n%s", getClass(), inventoryStatusText);

    return inventoryStatusText;

  }

  private String createInventoryText(String name, List<MedicineInventory> medicineInventories, List<Intake> intakes) {
    StringBuffer sb = new StringBuffer();


    String title = getMessage("medication.manager.ui.inventory.title", name);
    sb.append(title);

    sb.append('\n');

    int counter = 0;
    for (MedicineInventory medicineInventory : medicineInventories) {
      String row = createRow(medicineInventory, intakes);
      Log.info("Added single medicineInventory row: %s", getClass(), row);
      sb.append("\n\n\t");
      counter++;

      String medicineName = medicineInventory.getMedicine().getMedicineName();
      int quantity = medicineInventory.getQuantity();
      UnitClass unitClass = medicineInventory.getUnitClass();

      String type;

      if (DROPS == unitClass) {
        type = Activator.getMessage("medication.manager.ui.intake.type.drops");
      } else if (PILLS == unitClass) {
        type = Activator.getMessage("medication.manager.ui.intake.type.pills");
      } else {

        throw new MedicationManagerUIException("Unexpected Enum type for the UnitClass: " + unitClass);
      }

      String rowMessage = getMessage("medication.manager.ui.inventory.row",
          counter, medicineName, quantity, type);

      sb.append(rowMessage);

    }

    if (counter == 0) {
      sb.append(getMessage("medication.manager.ui.inventory.no"));
    }

    sb.append('\n');


    return sb.toString();
  }

  private String createRow(MedicineInventory medicineInventory, List<Intake> intakes) {
    StringBuffer sb = new StringBuffer();

    sb.append(medicineInventory.getMedicine().getMedicineName());
    sb.append(" available ");
    sb.append(medicineInventory.getQuantity());
    sb.append(' ');
    sb.append(medicineInventory.getUnitClass().getType());

    return sb.toString();
  }

}
