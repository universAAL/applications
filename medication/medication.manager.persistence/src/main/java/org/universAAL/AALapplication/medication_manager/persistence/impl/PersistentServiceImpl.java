package org.universAAL.AALapplication.medication_manager.persistence.impl;

import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;
import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.SqlUtility;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.DispenserDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.IntakeDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.InventoryLogDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.MedicineDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.MedicineInventoryDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.PersonDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.PrescriptionDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.TreatmentDao;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class PersistentServiceImpl implements PersistentService {

  private final SqlUtility sqlUtility;
  private final DispenserDao dispenserDao;
  private final IntakeDao intakeDao;
  private final InventoryLogDao inventoryLogDao;
  private final MedicineInventoryDao medicineInventoryDao;
  private final PrescriptionDao prescriptionDao;
  private final PersonDao personDao;
  private final MedicineDao medicineDao;
  private final TreatmentDao treatmentDao;

  public PersistentServiceImpl(Database database) {
    validateParameter(database, "database");

    this.sqlUtility = database.getSqlUtility();
    this.personDao = new PersonDao(database);
    this.medicineDao = new MedicineDao(database);
    this.dispenserDao = new DispenserDao(database);
    this.intakeDao = new IntakeDao(database);
    this.inventoryLogDao = new InventoryLogDao(database);
    this.medicineInventoryDao = new MedicineInventoryDao(database);
    this.prescriptionDao = new PrescriptionDao(database);
    this.treatmentDao = new TreatmentDao(database);

    //Set DAOs

    personDao.setDispenserDao(dispenserDao);
    dispenserDao.setPersonDao(personDao);
    intakeDao.setDispenserDao(dispenserDao);
    intakeDao.setMedicineDao(medicineDao);
    intakeDao.setTreatmentDao(treatmentDao);
    treatmentDao.setPrescriptionDao(prescriptionDao);
    treatmentDao.setMedicineDao(medicineDao);
    prescriptionDao.setPersonDao(personDao);
  }

  public SqlUtility getSqlUtility() {
    return sqlUtility;
  }

  public PersonDao getPersonDao() {
    return personDao;
  }

  public MedicineDao getMedicineDao() {
    return medicineDao;
  }

  public DispenserDao getDispenserDao() {
    return dispenserDao;
  }

  public IntakeDao getIntakeDao() {
    return intakeDao;
  }

  public InventoryLogDao getInventoryLogDao() {
    return inventoryLogDao;
  }

  public MedicineInventoryDao getMedicineInventoryDao() {
    return medicineInventoryDao;
  }

  public PrescriptionDao getPrescriptionDao() {
    return prescriptionDao;
  }

  public TreatmentDao getTreatmentDao() {
    return treatmentDao;
  }
}
