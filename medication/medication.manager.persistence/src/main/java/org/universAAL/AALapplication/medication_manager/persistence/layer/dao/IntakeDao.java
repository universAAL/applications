package org.universAAL.AALapplication.medication_manager.persistence.layer.dao;

import org.universAAL.AALapplication.medication_manager.configuration.ConfigurationProperties;
import org.universAAL.AALapplication.medication_manager.persistence.impl.Log;
import org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.AbstractDao;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Column;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Dispenser;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Intake;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Treatment;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.TreatmentStatus;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.UnitClass;
import org.universAAL.ontology.medMgr.Time;
import org.universAAL.ontology.profile.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class IntakeDao extends AbstractDao {

  public static final TreatmentStatus ACTIVE = TreatmentStatus.ACTIVE;
  private DispenserDao dispenserDao;
  private TreatmentDao treatmentDao;
  private MedicineDao medicineDao;

  private static final String TABLE_NAME = "INTAKE";
  private static final String TREATMENT_FK_ID = "TREATMENT_FK_ID";
  private static final String QUANTITY = "QUANTITY";
  private static final String UNITS = "UNITS";
  private static final String TIME_PLAN = "TIME_PLAN";
  private static final String TIME_TAKEN = "TIME_TAKEN";
  private static final String DISPENSER_FK_ID = "DISPENSER_FK_ID";

  public IntakeDao(Database database) {
    super(database, TABLE_NAME);

  }

  public void setDispenserDao(DispenserDao dispenserDao) {
    this.dispenserDao = dispenserDao;
  }

  public void setTreatmentDao(TreatmentDao treatmentDao) {
    this.treatmentDao = treatmentDao;
  }

  public void setMedicineDao(MedicineDao medicineDao) {
    this.medicineDao = medicineDao;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Intake getById(int id) {
    Log.info("Looking for the intake with id=%s", getClass(), id);

    checkForSetDao(dispenserDao, "dispenserDao");
    checkForSetDao(treatmentDao, "treatmentDao");
    checkForSetDao(medicineDao, "medicineDao");

    Map<String, Column> columns = getTableColumnsValuesById(id);

    return getIntake(columns);
  }

  private Intake getIntake(Map<String, Column> columns) {

    checkForSetDao(dispenserDao, "dispenserDao");
    checkForSetDao(treatmentDao, "treatmentDao");
    checkForSetDao(medicineDao, "medicineDao");


    Column col = columns.get(ID);
    int intakeId = (Integer) col.getValue();

    col = columns.get(TREATMENT_FK_ID);
    int treatmentId = (Integer) col.getValue();
    Treatment treatment = treatmentDao.getById(treatmentId);

    col = columns.get(QUANTITY);
    int quantity = (Integer) col.getValue();

    col = columns.get(UNITS);
    String unitsText = (String) col.getValue();
    UnitClass unitClass = UnitClass.getEnumValueFor(unitsText);

    col = columns.get(TIME_PLAN);
    Date timePlan = (Date) col.getValue();

    col = columns.get(TIME_TAKEN);
    Date timeTaken = (Date) col.getValue();

    col = columns.get(DISPENSER_FK_ID);
    Integer dispenserId = (Integer) col.getValue();

    Dispenser dispenser = null;
    if (dispenserId > 0) {
      dispenser = dispenserDao.getById(dispenserId);
    }

    Intake intake = new Intake(intakeId, treatment, quantity, unitClass, timePlan, timeTaken, dispenser);

    Log.info("Intake found: %s", getClass(), intake);
    return intake;
  }


  public List<Intake> getIntakesByUserAndTime(User inputUser, Time time) {
    String sql = "SELECT INTA.* \n" +
        "  FROM MEDICATION_MANAGER.INTAKE INTA,\n" +
        "  MEDICATION_MANAGER.TREATMENT TR,\n" +
        "  MEDICATION_MANAGER.PRESCRIPTION PR,\n" +
        "      MEDICATION_MANAGER.PERSON P\n" +
        "    \n" +
        "  WHERE UPPER(P.PERSON_URI) = ? \n" +
        "  AND INTA.TREATMENT_FK_ID = TR.ID \n" +
        "  AND UPPER(TR.STATUS) = ? \n" +
        "  AND TR.PRESCRIPTION_FK_ID = PR.ID \n" +
        "  AND PR.PATIENT_FK_ID = P.ID \n" +
        "  AND INTA.TIME_PLAN > ? \n" +
        "  AND INTA.TIME_PLAN < ? ";

    System.out.println("sql = " + sql);
    System.out.println("time = " + time);

    PreparedStatement statement = null;
    try {
      statement = getPreparedStatement(sql, statement);
      return getIntakes(inputUser, time, sql, statement);
    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException(e);
    } finally {
      closeStatement(statement);
    }

  }

  private List<Intake> getIntakes(User inputUser, Time time, String sql, PreparedStatement statement) throws SQLException {
    statement.setString(1, inputUser.getURI().toUpperCase());
    statement.setString(2, ACTIVE.getValue().toUpperCase());
    ConfigurationProperties configurationProperties = getConfigurationProperties();
    int minutesInterval = configurationProperties.getIntakeIntervalMinutes();
    statement.setTimestamp(3, createDate(time, -minutesInterval));
    statement.setTimestamp(4, createDate(time, minutesInterval));
    List<Map<String, Column>> results = executeQueryExpectedMultipleRecord(TABLE_NAME, sql, statement);
    return createIntakes(results);
  }

  private PreparedStatement getPreparedStatement(String sql, PreparedStatement statement) throws SQLException {
    Connection connection = database.getConnection();
    statement = connection.prepareStatement(sql);

    return statement;
  }

  private Timestamp createDate(Time time, int minutes) {

    GregorianCalendar calendar =
        new GregorianCalendar(time.getYear(), time.getMonth(), time.getDay(),
            time.getHour(), time.getMinutes() + minutes);

    Date date = calendar.getTime();

    Timestamp sqlDate = new Timestamp(date.getTime());

    System.out.println("sqlDate = " + sqlDate);

    return sqlDate;
  }

  private List<Intake> createIntakes(List<Map<String, Column>> results) {

    List<Intake> intakes = new ArrayList<Intake>();

    for (Map<String, Column> columns : results) {
      Intake intake = getIntake(columns);
      intakes.add(intake);
    }

    return intakes;
  }

  public List<Intake> getByTreatments(List<Treatment> treatments) {

    try {
      return findTreatments(treatments);
    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException(e);
    }
  }

  private List<Intake> findTreatments(List<Treatment> treatments) throws SQLException {
    StringBuffer sqlBuffer = new StringBuffer();

    sqlBuffer.append("select * from MEDICATION_MANAGER.INTAKE\n" +
        "  where TREATMENT_FK_ID in(");

    int size = treatments.size();

    int i = 0;
    for (Treatment treatment : treatments) {
      int id = treatment.getId();
      sqlBuffer.append(id);
      i++;
      if (i < size) {
        sqlBuffer.append(", ");
      }
    }

    sqlBuffer.append(')');


    String sql = sqlBuffer.toString();

    List<Map<String, Column>> results = database.executeQueryExpectedMultipleRecord(TABLE_NAME, getPreparedStatement(sql));

    return createIntakes(results);
  }
}
