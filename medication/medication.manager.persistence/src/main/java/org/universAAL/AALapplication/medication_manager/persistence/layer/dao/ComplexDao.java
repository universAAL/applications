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

  public ComplexDao(Database database, PersonDao personDao, DispenserDao dispenserDao) {
    super(database, "This is complex dao no specific table");

    this.database = database;
    this.personDao = personDao;
    this.dispenserDao = dispenserDao;
  }

  @Override
  public <T> T getById(int id) {
    throw new UnsupportedOperationException("Not implemented");
  }

  public void save(AssistedPersonUserInfo patient, CaregiverUserInfo doctor,
                   CaregiverUserInfo caregiver, int dispenserId) {

    Connection connection = database.getConnection();
    try {
      connection.setAutoCommit(false);
      saveData(patient, doctor, caregiver, dispenserId);
      connection.commit();
    } catch (Exception e) {
      rollback(connection, e);
      throw new MedicationManagerPersistenceException(e);
    } finally {
      setAutoCommitToTrue(connection);
    }


  }

  private void saveData(AssistedPersonUserInfo patient, CaregiverUserInfo doctor,
                        CaregiverUserInfo caregiver, int dispenserId) {

    if (!patient.isPresentInDatabase()) {
      saveInPersonTable(patient);
    }

    if (!doctor.isPresentInDatabase()) {
      saveInPersonTable(doctor);
    }

    if (!doctor.isPresentInDatabase()) {
      saveInPersonTable(doctor);
    }

  }

  private void saveInPersonTable(CaregiverUserInfo caregiverUserInfo) {
    Person person = new Person(
        caregiverUserInfo.getId(),
        caregiverUserInfo.getName(),
        caregiverUserInfo.getUri(),
        Role.PATIENT,
        caregiverUserInfo.getGsmNumber()
    );

    personDao.savePerson(person);
  }


  private void saveInPersonTable(AssistedPersonUserInfo patient) {


    Person person = new Person(
        patient.getId(),
        patient.getName(),
        patient.getUri(),
        Role.PATIENT
    );

    personDao.savePerson(person);
  }

}
