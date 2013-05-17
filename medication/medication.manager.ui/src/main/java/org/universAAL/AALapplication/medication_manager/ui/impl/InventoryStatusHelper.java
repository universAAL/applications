package org.universAAL.AALapplication.medication_manager.ui.impl;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.IntakeDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.MedicineInventoryDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Intake;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Medicine;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.MedicineInventory;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Treatment;

import java.util.List;

/**
 * @author George Fournadjiev
 */
public final class InventoryStatusHelper {


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

    sb.append("Medicine inventory for : ");
    sb.append(name);

    sb.append('\n');

    int counter = 0;
    for (MedicineInventory medicineInventory : medicineInventories) {
      String row = createRow(medicineInventory, intakes);
      Log.info("Added single medicineInventory row: %s", getClass(), row);
      sb.append("\n\n\t");
      counter++;
      sb.append(counter);
      sb.append(". ");
      sb.append(row);
    }

    if (counter == 0) {
      sb.append("There is no available medicine inventory");
    }

    sb.append('\n');


    return sb.toString();
  }

  private String createRow(MedicineInventory medicineInventory, List<Intake> intakes) {
    StringBuffer sb = new StringBuffer();

    sb.append("Medicine: ");
    sb.append(medicineInventory.getMedicine().getMedicineName());
    sb.append(", units: ");
    sb.append(medicineInventory.getUnitClass().getType());
    sb.append(", quantity: ");
    sb.append(medicineInventory.getQuantity());
    sb.append(", threshold: ");
    sb.append(medicineInventory.getWarningThreshold());
    sb.append(", needed quantity: ");
    sb.append(getNeededQuantity(medicineInventory.getMedicine(), intakes));

    return sb.toString();
  }

  private int getNeededQuantity(Medicine medicine, List<Intake> intakes) {
    int quantity = 0;

    for (Intake intake : intakes) {
      Treatment treatment = intake.getTreatment();
      Medicine med = treatment.getMedicine();
      if (med.getId() == medicine.getId()) {
         quantity = quantity + intake.getQuantity();
      }
    }

    return quantity;
  }
}
