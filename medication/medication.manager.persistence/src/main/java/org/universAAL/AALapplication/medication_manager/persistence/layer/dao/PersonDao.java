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
import org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.AbstractDao;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Column;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Dispenser;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Role;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;


/**
 * @author George Fournadjiev
 */
public final class PersonDao extends AbstractDao {

  private DispenserDao dispenserDao;

  public static final String NAME = "NAME";
  public static final String PERSON_URI = "PERSON_URI";
  public static final String ROLE = "ROLE";
  public static final String CAREGIVER_SMS = "CAREGIVER_SMS";

  static final String TABLE_NAME = "PERSON";

  public PersonDao(Database database) {
    super(database, TABLE_NAME);
  }

  public void setDispenserDao(DispenserDao dispenserDao) {
    this.dispenserDao = dispenserDao;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Person getById(int id) {
    Log.info("Looking for the person with id=%s", getClass(), id);
    Map<String, Column> columns = getTableColumnsValuesById(id);

    return getPerson(columns);

  }

  private Person getPerson(Map<String, Column> columns) {
    Column col = columns.get(ID);
    int personId = (Integer) col.getValue();

    col = columns.get(NAME);
    String name = (String) col.getValue();

    col = columns.get(PERSON_URI);
    String personUri = (String) col.getValue();

    col = columns.get(ROLE);
    String roleString = (String) col.getValue();
    Role role = Role.getEnumValueFor(roleString);

    col = columns.get(CAREGIVER_SMS);
    String caregiverSms = (String) col.getValue();

    Person person = new Person(personId, name, personUri, role, caregiverSms);

//    Log.info("Person found: %s", getClass(), person);
    Log.info("Person found with id : %s", getClass(), person.getId());

    return person;
  }

  public Person findPersonByPersonUri(String personUri) {
    Log.info("Looking for the person with personUri=%s", getClass(), personUri);

    String sql = "select * from MEDICATION_MANAGER.PERSON where UPPER(PERSON_URI) = UPPER('" + personUri + "')";

    Map<String, Column> personRecordMap = executeQueryExpectedSingleRecord(TABLE_NAME, sql);

    if (personRecordMap == null || personRecordMap.isEmpty()) {
      throw new MedicationManagerPersistenceException("Missing the record in the table:" +
          TABLE_NAME + " for the following sql:\n" + sql);
    }

    return getPerson(personRecordMap);
  }

  public Person getPersonByPersonUri(String personUri) {
    Log.info("Looking for the person with personUri=%s", getClass(), personUri);

    String sql = "select * from MEDICATION_MANAGER.PERSON where UPPER(PERSON_URI) = UPPER('" + personUri + "')";

    Map<String, Column> personRecordMap = executeQueryExpectedSingleRecord(TABLE_NAME, sql);

    if (personRecordMap == null || personRecordMap.isEmpty()) {
      return null;
    }

    return getPerson(personRecordMap);
  }

  public Person findPersonByDeviceUri(String deviceUri) {
    Log.info("Looking for the person with deviceUri=%s", getClass(), deviceUri);

    checkForSetDao(dispenserDao, "dispenserDao");

    Dispenser dispenser = dispenserDao.getByDispenserUri(deviceUri);

    if (dispenser == null) {
      throw new MedicationManagerPersistenceException("There is no record for a " +
          "dispenser with the following uri:" + deviceUri);
    }

    return dispenser.getPatient();
  }

  public Person findPerson(String username, String password, Role role) {
    Log.info("Looking for the doctor with username=%s", getClass(), username);

    String sql = "select * from MEDICATION_MANAGER.PERSON where UPPER(USERNAME) = ? AND " +
        "PASSWORD = ? AND UPPER(ROLE) = ?";

    PreparedStatement ps = null;

    try {
      ps = getPreparedStatement(sql);
      ps.setString(1, username.trim().toUpperCase());
      ps.setString(2, password);
      ps.setString(3, role.getValue());
      Map<String, Column> personRecordMap = executeQueryExpectedSingleRecord(TABLE_NAME, ps);
      if (personRecordMap == null || personRecordMap.isEmpty()) {
        return null;
      }
      return getPerson(personRecordMap);
    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException(e);
    } finally {
      closeStatement(ps);
    }


  }

  public List<Person> getAllPersons() {
    String sql = "select * from MEDICATION_MANAGER.PERSON";

    PreparedStatement ps = null;

    List<Person> persons = new ArrayList<Person>();

    try {
      ps = getPreparedStatement(sql);
      List<Map<String, Column>> personRecords = executeQueryExpectedMultipleRecord(TABLE_NAME, sql, ps);
      if (personRecords == null || personRecords.isEmpty()) {
        return persons;
      }

      for (Map<String, Column> columnMap : personRecords) {
        Person p = getPerson(columnMap);
        persons.add(p);
      }

      return persons;
    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException(e);
    } finally {
      closeStatement(ps);
    }
  }

  public void savePerson(Person person) {

    Log.info("Trying to insert person: %s", getClass(), person);

    String sql = "insert into medication_manager.person " +
        "(id, name, person_uri, role, username, password, caregiver_sms) values (?, ?, ?, ?, ?, ?, ?)";

    PreparedStatement ps = null;


    try {
      ps = getPreparedStatement(sql);
      ps.setInt(1, person.getId());
      ps.setString(2, person.getName());
      ps.setString(3, person.getPersonUri());
      ps.setString(4, person.getRole().getValue());
      String username = person.getUsername();
      setPossibleNullString(ps, username, 5);
      String password = person.getPassword();
      setPossibleNullString(ps, password, 6);
      String gsmNumber = person.getCaregiverSms();
      setPossibleNullString(ps, gsmNumber, 7);

      ps.execute();
      Log.info("Person was successfully inserted into the Person table", getClass());
    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException(e);
    } finally {
      closeStatement(ps);
    }
  }

  private void setPossibleNullString(PreparedStatement ps, String value, int index) throws SQLException {

    if (value != null) {
      ps.setString(index, value);
    } else {
      ps.setNull(index, Types.VARCHAR);
    }
  }


  public void updateRole(int id, Role role) {
    Log.info("Setting Role from Role.UNASSIGNED to %s for the person with id : %s",
        getClass(), role, id);

    String sql = "update MEDICATION_MANAGER.PERSON set ROLE = ? where ID = ?";

    PreparedStatement ps = null;

    try {
      ps = getPreparedStatement(sql);
      ps.setString(1, role.getValue());
      ps.setInt(2, id);

      ps.execute();
    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException(e);
    } finally {
      closeStatement(ps);
    }
  }
}
