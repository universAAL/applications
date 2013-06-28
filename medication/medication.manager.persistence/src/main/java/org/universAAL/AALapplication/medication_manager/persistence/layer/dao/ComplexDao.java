package org.universAAL.AALapplication.medication_manager.persistence.layer.dao;

import org.universAAL.AALapplication.medication_manager.persistence.impl.Log;
import org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.AbstractDao;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;
import org.universAAL.AALapplication.medication_manager.persistence.layer.AssistedPersonUserInfo;
import org.universAAL.AALapplication.medication_manager.persistence.layer.CaregiverUserInfo;
import org.universAAL.AALapplication.medication_manager.persistence.layer.NotificationInfoComplexId;
import org.universAAL.AALapplication.medication_manager.persistence.layer.NotificationsInfo;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Medicine;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.MedicineInventory;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Role;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Treatment;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author George Fournadjiev
 */
public final class ComplexDao extends AbstractDao {

  private final Database database;
  private final PersonDao personDao;
  private final DispenserDao dispenserDao;
  private final PatientLinksDao patientLinksDao;
  private final TreatmentDao treatmentDao;
  private final MedicineInventoryDao medicineInventoryDao;

  public ComplexDao(Database database, PersonDao personDao,
                    DispenserDao dispenserDao, PatientLinksDao patientLinksDao,
                    TreatmentDao treatmentDao, MedicineInventoryDao medicineInventoryDao) {

    super(database, "This is complex dao no specific table");

    this.database = database;
    this.personDao = personDao;
    this.dispenserDao = dispenserDao;
    this.patientLinksDao = patientLinksDao;
    this.treatmentDao = treatmentDao;
    this.medicineInventoryDao = medicineInventoryDao;
  }

  @Override
  public <T> T getById(int id) {
    throw new UnsupportedOperationException("Not implemented");
  }

  public void save(AssistedPersonUserInfo patient, CaregiverUserInfo doctor,
                   CaregiverUserInfo caregiver, int dispenserId, boolean dueIntakeAlert,
                   boolean successfulIntakeAlert, boolean missedIntakeAlert, boolean upsideDownAlert) {

    Connection connection = database.getConnection();
    try {
      connection.setAutoCommit(false);
      saveData(patient, doctor, caregiver, dispenserId, dueIntakeAlert,
          successfulIntakeAlert, missedIntakeAlert, upsideDownAlert);
      connection.commit();
    } catch (Exception e) {
      rollback(connection, e);
      throw new MedicationManagerPersistenceException(e);
    } finally {
      setAutoCommitToTrue(connection);
    }


  }

  private void saveData(AssistedPersonUserInfo patient, CaregiverUserInfo doctor,
                        CaregiverUserInfo caregiver, int dispenserId, boolean dueIntakeAlert,
                        boolean successfulIntakeAlert, boolean missedIntakeAlert, boolean upsideDownAlert) {

    if (!patient.isPresentInDatabase()) {
      saveInPersonTable(patient);
      patient.setPresentInDatabase(true);
    }

    if (!doctor.isPresentInDatabase()) {
      saveInPersonTable(doctor, Role.PHYSICIAN);
      doctor.setPresentInDatabase(true);
    }

    if (!caregiver.isPresentInDatabase()) {
      saveInPersonTable(caregiver, Role.CAREGIVER);
      caregiver.setPresentInDatabase(true);
    }

    if (dispenserId > 0) {
      dispenserDao.updateDispenser(dispenserId, patient.getId(), dueIntakeAlert,
          successfulIntakeAlert, missedIntakeAlert, upsideDownAlert);
    } else {
      dispenserDao.updateDispenserRemovePatientForeignKey(patient.getId());
    }

    updatePatientLinks(patient, doctor, caregiver);

  }

  private void updatePatientLinks(AssistedPersonUserInfo patient,
                                  CaregiverUserInfo doctor, CaregiverUserInfo caregiver) {

    int id = database.getNextIdFromIdGenerator();
    patientLinksDao.saveOrUpdate(id, patient.getId(), doctor.getId(), caregiver.getId());

  }

  private void saveInPersonTable(CaregiverUserInfo caregiverUserInfo, Role role) {
    Person person = new Person(
        caregiverUserInfo.getId(),
        caregiverUserInfo.getName(),
        caregiverUserInfo.getUri(),
        role,
        caregiverUserInfo.getUsername(),
        caregiverUserInfo.getPassword(),
        caregiverUserInfo.getGsmNumber()
    );

    try {
      personDao.savePerson(person);
    } catch (Exception e) {
      caregiverUserInfo.setPresentInDatabase(false);
      throw new MedicationManagerPersistenceException(e);
    }
  }


  private void saveInPersonTable(AssistedPersonUserInfo patient) {


    Person person = new Person(
        patient.getId(),
        patient.getName(),
        patient.getUri(),
        Role.PATIENT
    );

    try {
      personDao.savePerson(person);
    } catch (Exception e) {
      patient.setPresentInDatabase(false);
      throw new MedicationManagerPersistenceException(e);
    }
  }

  public Set<NotificationsInfo> getAllNotificationsInfo() {

    Set<NotificationsInfo> infos = new HashSet<NotificationsInfo>();

    Set<Treatment> treatmentSet = treatmentDao.getAllActiveTreatments();

    Map<Person, List<MedicineInventory>> map = new HashMap<Person, List<MedicineInventory>>();

    fillMap(map, treatmentSet);

    for (Treatment treatment : treatmentSet) {
      Person patient = treatment.getPrescription().getPatient();
      List<MedicineInventory> inventories = map.get(patient);
      NotificationsInfo notificationsInfo = createNotificationInfo(treatment, patient, inventories);
      infos.add(notificationsInfo);
    }

    return infos;

  }

  private void fillMap(Map<Person, List<MedicineInventory>> map,
                       Set<Treatment> treatmentSet) {

    for (Treatment treatment : treatmentSet) {
      Person patient = treatment.getPrescription().getPatient();
      if (!map.containsKey(patient)) {
        List<MedicineInventory> allMedicineInventoriesForPatient =
            medicineInventoryDao.getAllMedicineInventoriesForPatient(patient);
        map.put(patient, allMedicineInventoriesForPatient);
      }

    }

  }

  private NotificationsInfo createNotificationInfo(Treatment treatment, Person patient,
                                                   List<MedicineInventory> inventories) {

    Medicine medicine = treatment.getMedicine();

    MedicineInventory inventory = null;

    for (MedicineInventory medicineInventory : inventories) {
      Medicine med = medicineInventory.getMedicine();
      if (medicine.getId() == med.getId()) {
        inventory = medicineInventory;
        break;
      }
    }

    if (inventory == null) {
      throw new MedicationManagerPersistenceException("Missing MedicineInventory for a patient with id:" +
          patient.getId() + " for medicine with id: " + medicine.getId());
    }

    int threshold = inventory.getWarningThreshold();

    NotificationInfoComplexId complexId =
        new NotificationInfoComplexId(patient.getId(), treatment.getId(), inventory.getId());

    return new NotificationsInfo(
        complexId,
        patient.getName(),
        medicine.getMedicineName(),
        treatment.isMissedIntakeAlert(),
        threshold,
        treatment.isShortageAlert(),
        treatment.isNewDoseAlert()
    );

  }

  public void updateNotifications(NotificationsInfo info) {
    Log.info("Updating the following NotificationsInfo : %s", getClass(), info);

    Connection connection = database.getConnection();
    try {
      connection.setAutoCommit(false);
      updateTables(info);
      connection.commit();
      Log.info("Successfully commited", getClass());
    } catch (Exception e) {
      rollback(connection, e);
      throw new MedicationManagerPersistenceException(e);
    } finally {
      setAutoCommitToTrue(connection);
    }

  }

  private void updateTables(NotificationsInfo info) throws SQLException {
    NotificationInfoComplexId complexId = info.getComplexId();

    medicineInventoryDao.updateMedicineInventoryTableWithNewThreshold(complexId.getMedicineInventoryId(),
        complexId.getPatientId(), info.getThreshold());

    treatmentDao.updateTreatmentTable(complexId.getTreatmentId(), info.isMissed(), info.isShortage(), info.isDose());
  }


}
