package org.universAAL.AALapplication.medication_manager.persistence.layer.dao;

import org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.AbstractDao;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;
import org.universAAL.AALapplication.medication_manager.persistence.layer.AssistedPersonUserInfo;
import org.universAAL.AALapplication.medication_manager.persistence.layer.CaregiverUserInfo;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Role;

import java.sql.Connection;

/**
 * @author George Fournadjiev
 */
public final class ComplexDao extends AbstractDao {

  private final Database database;
  private final PersonDao personDao;
  private final DispenserDao dispenserDao;
  private final PatientLinksDao patientLinksDao;

  public ComplexDao(Database database, PersonDao personDao,
                    DispenserDao dispenserDao, PatientLinksDao patientLinksDao) {

    super(database, "This is complex dao no specific table");

    this.database = database;
    this.personDao = personDao;
    this.dispenserDao = dispenserDao;
    this.patientLinksDao = patientLinksDao;
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

}
