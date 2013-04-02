package org.universAAL.AALapplication.medication_manager.persistence.layer.dao;

import org.universAAL.AALapplication.medication_manager.persistence.impl.Log;
import org.universAAL.AALapplication.medication_manager.persistence.impl.MedicationManagerPersistenceException;
import org.universAAL.AALapplication.medication_manager.persistence.impl.SoftCache;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.AbstractDao;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Column;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.IntakeDTO;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.MealRelationDTO;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.MedicineDTO;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.PrescriptionDTO;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.TimeDTO;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Intake;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.MealRelation;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Medicine;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Prescription;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.PrescriptionStatus;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Treatment;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.TreatmentStatus;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.UnitClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.universAAL.AALapplication.medication_manager.persistence.impl.Activator.*;
import static org.universAAL.AALapplication.medication_manager.persistence.layer.entities.PrescriptionStatus.*;

/**
 * @author George Fournadjiev
 */
public final class PrescriptionDao extends AbstractDao {

  private PersonDao personDao;
  private MedicineDao medicineDao;
  private TreatmentDao treatmentDao;
  private IntakeDao intakeDao;

  private static boolean isPrescriptionDTOLoaded = false;
  private static final SoftCache<Integer, PrescriptionDTO> SOFT_CACHE = new SoftCache<Integer, PrescriptionDTO>();

  private static final String PATIENT_FK_ID = "PATIENT_FK_ID";
  private static final String TIME_OF_CREATION = "TIME_OF_CREATION";
  private static final String PHYSICIAN_FK_ID = "PHYSICIAN_FK_ID";
  private static final String DESCRIPTION = "DESCRIPTION";
  private static final String STATUS = "STATUS";
  private static final String TABLE_NAME = "PRESCRIPTION";

  public PrescriptionDao(Database database) {
    super(database, TABLE_NAME);

  }

  public void loadPrescriptionDTOs() {
    if (!isPrescriptionDTOLoaded) {
      List<PrescriptionDTO> prescriptionDTOs = getAllActivePrescriptions();

      for (PrescriptionDTO dto : prescriptionDTOs) {
        SOFT_CACHE.put(dto.getPrescriptionId(), dto);
      }

      isPrescriptionDTOLoaded = true;
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public Prescription getById(int id) {
    Log.info("Looking for the Prescription with id=%s", getClass(), id);
    Map<String, Column> columns = getTableColumnsValuesById(id);

    return getPrescription(columns);
  }

  public void setPersonDao(PersonDao personDao) {
    this.personDao = personDao;
  }

  public void setMedicineDao(MedicineDao medicineDao) {
    this.medicineDao = medicineDao;
  }

  public void setTreatmentDao(TreatmentDao treatmentDao) {
    this.treatmentDao = treatmentDao;
  }

  public void setIntakeDao(IntakeDao intakeDao) {
    this.intakeDao = intakeDao;
  }

  public List<Prescription> getByPerson(int personId) {
    String sql = "select * from MEDICATION_MANAGER.PRESCRIPTION where PATIENT_FK_ID = ? and UPPER(STATUS) = ?";

    System.out.println("sql = " + sql);

    PreparedStatement statement = null;
    try {
      statement = getPreparedStatement(sql);
      return getPrescriptions(personId, sql, statement);
    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException(e);
    } finally {
      closeStatement(statement);
    }
  }

  private List<Prescription> getPrescriptions(int personId, String sql,
                                              PreparedStatement statement) throws SQLException {
    statement.setInt(1, personId);
    statement.setString(2, ACTIVE.getType().toUpperCase());
    List<Map<String, Column>> results = executeQueryExpectedMultipleRecord(TABLE_NAME, sql, statement);

    return createPrescriptions(results);
  }

  private List<Prescription> createPrescriptions(List<Map<String, Column>> results) {

    List<Prescription> prescriptions = new ArrayList<Prescription>();

    for (Map<String, Column> columns : results) {
      Prescription prescription = getPrescription(columns);
      prescriptions.add(prescription);
    }

    return prescriptions;
  }

  private Prescription getPrescription(Map<String, Column> columns) {
    checkForSetDao(personDao, "personDao");

    Column col = columns.get(ID);
    int prescriptionId = (Integer) col.getValue();

    col = columns.get(TIME_OF_CREATION);
    Date timeOfCreation = (Date) col.getValue();

    col = columns.get(PATIENT_FK_ID);
    int personId = (Integer) col.getValue();
    Person patient = personDao.getById(personId);

    col = columns.get(PHYSICIAN_FK_ID);
    int physicianId = (Integer) col.getValue();
    Person physician = personDao.getById(physicianId);

    col = columns.get(DESCRIPTION);
    String description = (String) col.getValue();

    col = columns.get(STATUS);
    String statusValue = (String) col.getValue();
    PrescriptionStatus prescriptionStatus = PrescriptionStatus.getEnumValueFor(statusValue);

    Prescription prescription =
        new Prescription(prescriptionId, timeOfCreation, patient, physician, description, prescriptionStatus);

    Log.info("Prescription found: %s", getClass(), prescription);

    return prescription;
  }

  public void save(PrescriptionDTO prescriptionDTO) {

    checkForSetDao(medicineDao, "medicineDao");

    Connection connection = null;
    try {
      connection = database.getConnection();
      connection.setAutoCommit(false);
      int prescriptionId = database.getNextIdFromIdGenerator();
      persistPrescriptionDto(prescriptionId, prescriptionDTO, connection);
      connection.commit();
      prescriptionDTO.setPrescriptionId(prescriptionId);
      SOFT_CACHE.put(prescriptionId, prescriptionDTO);
    } catch (Exception e) {
      rollback(connection, e);
      throw new MedicationManagerPersistenceException(e);
    } finally {
      setConnectionAutoCommitToTrue(connection);
    }

  }

  private void persistPrescriptionDto(int prescriptionId, PrescriptionDTO prescriptionDTO,
                                      Connection connection) throws SQLException {

    String sql = "INSERT INTO MEDICATION_MANAGER.PRESCRIPTION " +
        "(ID, TIME_OF_CREATION, PATIENT_FK_ID, PHYSICIAN_FK_ID, DESCRIPTION, STATUS) " +
        "VALUES (?, ?, ?, ?, ?, ?)";

    PreparedStatement psPrescription = null;

    try {
      psPrescription = connection.prepareStatement(sql);
      prescriptionDTO.setPrescriptionId(prescriptionId);
      insertRecordIntoPrescriptionTable(prescriptionId, prescriptionDTO, psPrescription);
      insertMedicineDTOs(prescriptionDTO, connection, prescriptionId);
    } finally {
      closeStatement(psPrescription);
    }

  }

  private void insertMedicineDTOs(PrescriptionDTO prescriptionDTO,
                                  Connection connection, int prescriptionId) throws SQLException {

    Set<MedicineDTO> medicineDTOSet = prescriptionDTO.getMedicineDTOSet();
    List<Medicine> medicines = medicineDao.getMedicinesWithName(medicineDTOSet);
    insertMissingRecordsIntoMedicineTable(medicineDTOSet, connection, medicines);
    insertIntoTreatments(medicineDTOSet, connection, prescriptionId);
  }


  private void insertIntoTreatments(Set<MedicineDTO> medicineDTOSet,
                                    Connection connection, int prescriptionId) throws SQLException {

    for (MedicineDTO med : medicineDTOSet) {
      int treatmentId = database.getNextIdFromIdGenerator();
      persistTreatment(med, connection, prescriptionId, treatmentId);
      persistIntakesDTO(med, treatmentId, connection);
    }
  }

  private void persistIntakesDTO(MedicineDTO med, int treatmentId,
                                 Connection connection) throws SQLException {

    Set<IntakeDTO> intakeDTOSet = med.getIntakeDTOSet();

    for (IntakeDTO intakeDTO : intakeDTOSet) {
      persistIntakeDTO(intakeDTO, connection, treatmentId, med);
    }


  }

  private void persistIntakeDTO(IntakeDTO intakeDTO, Connection connection,
                                int treatmentId, MedicineDTO med) throws SQLException {


    int days = med.getDays();

    Date startDate = med.getTreatmentStartDate();

    TimeDTO time = intakeDTO.getTime();

    List<Date> timePlanDates = createTimePlanDates(time, startDate, days);

    for (Date date : timePlanDates) {
      persistIntake(intakeDTO, date, connection, treatmentId);
    }

  }

  private List<Date> createTimePlanDates(TimeDTO time, Date startDate, int days) {
    Calendar startCalendar = new GregorianCalendar();
    startCalendar.setTime(startDate);

    if (!startFromStartDate(startCalendar, time)) {
      startCalendar.add(Calendar.DAY_OF_MONTH, 1);
    }

    List<Date> dates = new ArrayList<Date>();
    startCalendar.set(Calendar.HOUR_OF_DAY, time.getHour());
    startCalendar.set(Calendar.MINUTE, time.getMinutes());

    for (int i = 0; i < days; i++) {
      Date d = startCalendar.getTime();
      dates.add(d);
      startCalendar.add(Calendar.DAY_OF_MONTH, 1);
    }

    return dates;
  }

  private boolean startFromStartDate(Calendar startCalendar, TimeDTO time) {
    int hour = startCalendar.get(Calendar.HOUR_OF_DAY);
    if (hour > time.getHour()) {
      return false;
    }

    int minutes = startCalendar.get(Calendar.MINUTE);
    if (minutes > time.getMinutes()) {
      return false;
    }

    return true;
  }

  private void persistIntake(IntakeDTO intakeDTO, Date timePlan, Connection connection,
                             int treatmentId) throws SQLException {

    String sql = "INSERT INTO MEDICATION_MANAGER.INTAKE " +
        "(ID, TREATMENT_FK_ID, QUANTITY, UNITS, TIME_PLAN) " +
        "VALUES (?, ?, ?, ?, ?)";

    PreparedStatement ps = null;

    try {
      ps = connection.prepareStatement(sql);
      insertRecordIntoIntakeTable(intakeDTO, timePlan, ps, treatmentId);
    } finally {
      closeStatement(ps);
    }
  }

  private void insertRecordIntoIntakeTable(IntakeDTO intakeDTO, Date timePlan,
                                           PreparedStatement ps, int treatmentId) throws SQLException {

    int id = database.getNextIdFromIdGenerator();
    ps.setInt(1, id);
    ps.setInt(2, treatmentId);
    ps.setInt(3, intakeDTO.getDose());
    IntakeDTO.Unit unit = intakeDTO.getUnit();
    UnitClass unitClass = UnitClass.getEnumValueFor(unit.getValue());
    ps.setString(4, unitClass.getType());
    ps.setTimestamp(5, new Timestamp(timePlan.getTime()));

    ps.execute();
  }

  private void persistTreatment(MedicineDTO med, Connection connection,
                                int prescriptionId, int id) throws SQLException {

    String sql = "INSERT INTO MEDICATION_MANAGER.TREATMENT " +
        "(ID, PRESCRIPTION_FK_ID, MEDICINE_FK_ID, START_DATE, END_DATE, STATUS) " +
        "VALUES (?, ?, ?, ?, ?, ?)";

    PreparedStatement ps = null;

    try {
      ps = connection.prepareStatement(sql);
      insertRecordIntoTreatmentTable(med, ps, prescriptionId, id);
    } finally {
      closeStatement(ps);
    }
  }

  private void insertRecordIntoTreatmentTable(MedicineDTO med, PreparedStatement ps,
                                              int prescriptionId, int id) throws SQLException {

    ps.setInt(1, id);
    ps.setInt(2, prescriptionId);
    ps.setInt(3, med.getMedicineId());
    Date treatmentStartDate = med.getTreatmentStartDate();
    java.sql.Date startDate = new java.sql.Date(treatmentStartDate.getTime());
    ps.setDate(4, startDate);
    Date treatmentEndDate = med.getTreatmentEndDate();
    java.sql.Date endDate = new java.sql.Date(treatmentEndDate.getTime());
    ps.setDate(5, endDate);
    ps.setString(6, TreatmentStatus.ACTIVE.getValue());

    ps.execute();

  }

  private void insertMissingRecordsIntoMedicineTable(Set<MedicineDTO> medicineDTOSet,
                                                     Connection connection,
                                                     List<Medicine> medicines) throws SQLException {

    for (MedicineDTO medicineDTO : medicineDTOSet) {
      if (!medicineExists(medicines, medicineDTO)) {
        int id = persistMedicineDTO(medicineDTO, connection);
        medicineDTO.setMedicineIdId(id);
      }

    }

  }

  private boolean medicineExists(List<Medicine> medicines, MedicineDTO medicineDTO) {
    boolean found = false;

    for (Medicine med : medicines) {
      String medicineDTOName = medicineDTO.getName();
      String medicineName = med.getMedicineName();
      if (medicineDTOName.equalsIgnoreCase(medicineName)) {
        found = true;
        medicineDTO.setMedicineIdId(med.getId());
      }
    }

    return found;
  }

  private int persistMedicineDTO(MedicineDTO medicineDTO,
                                 Connection connection) throws SQLException {
    String sql = "INSERT INTO MEDICATION_MANAGER.MEDICINE " +
        "(ID, MEDICINE_NAME, MEDICINE_INFO, SIDE_EFFECTS, INCOMPLIANCES, MEAL_RELATION) " +
        "VALUES (?, ?, ?, ?, ?, ?)";

    PreparedStatement ps = null;
    int medicineId = database.getNextIdFromIdGenerator();

    try {
      ps = connection.prepareStatement(sql);
      insertRecordIntoMedicineTable(medicineDTO, ps, medicineId);
    } finally {
      closeStatement(ps);
    }

    return medicineId;
  }

  private void insertRecordIntoMedicineTable(MedicineDTO medicineDTO,
                                             PreparedStatement ps, int medicineId) throws SQLException {

    ps.setInt(1, medicineId);
    ps.setString(2, medicineDTO.getName());
    ps.setString(3, medicineDTO.getDescription());
    ps.setString(4, medicineDTO.getSideeffects());
    ps.setString(5, medicineDTO.getIncompliances());
    ps.setString(6, medicineDTO.getMealRelationDTO().getValue());
    ps.execute();
  }

  private void insertRecordIntoPrescriptionTable(int id, PrescriptionDTO prescriptionDTO,
                                                 PreparedStatement ps) throws SQLException {

    ps.setInt(1, id);
    Date now = new Date();
    long time = now.getTime();
    Timestamp timeOfCreation = new Timestamp(time);
    ps.setTimestamp(2, timeOfCreation);
    ps.setInt(3, prescriptionDTO.getPatient().getId());
    ps.setInt(4, prescriptionDTO.getPhysician().getId());
    ps.setString(5, prescriptionDTO.getDescription());
    ps.setString(6, PrescriptionStatus.ACTIVE.getType());
    ps.execute();
  }

  private void setConnectionAutoCommitToTrue(Connection connection) {
    try {
      if (connection != null) {
        connection.setAutoCommit(true);
      }
    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException(e);
    }
  }

  public List<PrescriptionDTO> getPrescriptionDTO(Person patient, Person doctor) {

    List<PrescriptionDTO> prescriptionDTOs = getFromCache(patient.getId(), doctor.getId());
    if (!prescriptionDTOs.isEmpty()) {
      return prescriptionDTOs;
    }

    try {
      List<Prescription> prescriptions = getByPersonAndDoctor(patient.getId(), doctor.getId());
      return convertToDTO(prescriptions);
    } catch (MedicationManagerPersistenceException e) {
      if (MedicationManagerPersistenceException.MISSING_RECORD == e.getCode()) {
        return new ArrayList<PrescriptionDTO>();
      }

      throw e;
    }


  }

  private List<PrescriptionDTO> getFromCache(int patientId, int doctorId) {
    List<PrescriptionDTO> prescriptionDTOs = new ArrayList<PrescriptionDTO>();

    for (PrescriptionDTO dto : SOFT_CACHE.values()) {
      if (dto.getPatient().getId() == patientId && dto.getPhysician().getId() == doctorId) {
         prescriptionDTOs.add(dto);
      }
    }

    return prescriptionDTOs;
  }

  private List<PrescriptionDTO> convertToDTO(List<Prescription> prescriptions) {
    List<PrescriptionDTO> prescriptionDTOs = new ArrayList<PrescriptionDTO>();

    for (Prescription prescription : prescriptions) {
      PrescriptionDTO dto = createPrescriptionDTO(prescription);
      prescriptionDTOs.add(dto);
    }

    return prescriptionDTOs;
  }

  private PrescriptionDTO createPrescriptionDTO(Prescription prescription) {


    checkForSetDao(treatmentDao, "treatmentDao");
    checkForSetDao(intakeDao, "intakeDao");

    List<Treatment> treatments = treatmentDao.getByPrescriptionAndActive(prescription.getId());
    Date startDate = treatments.get(0).getStartDate();

    List<Intake> intakes = intakeDao.getByTreatments(treatments);

    Set<MedicineDTO> medicineDTOSet = createMedicineDTOSet(treatments, intakes);

    PrescriptionDTO prescriptionDTO = new PrescriptionDTO(
        prescription.getDescription(),
        startDate,
        medicineDTOSet,
        prescription.getPhysician(),
        prescription.getPatient()
    );

    prescriptionDTO.setPrescriptionId(prescription.getId());

    return prescriptionDTO;
  }

  private Set<MedicineDTO> createMedicineDTOSet(List<Treatment> treatments, List<Intake> intakes) {

    Set<MedicineDTO> medicineDTOSet = new HashSet<MedicineDTO>();
    for (Treatment tr : treatments) {
      MedicineDTO medicineDTO = createMedicineDTO(tr, intakes);
      medicineDTO.setMedicineIdId(tr.getMedicine().getId());
      medicineDTOSet.add(medicineDTO);
    }

    return medicineDTOSet;
  }

  private MedicineDTO createMedicineDTO(Treatment tr, List<Intake> intakes) {

    Medicine medicine = tr.getMedicine();
    Date startDate = tr.getStartDate();

    return new MedicineDTO(
        medicine.getMedicineName(),
        startDate,
        getDays(startDate, tr.getEndDate()),
        medicine.getMedicineInfo(),
        medicine.getMedicineSideEffects(),
        medicine.getIncompliances(),
        getMealRelation(medicine),
        getIntakeDTOSet(intakes, tr)
    );
  }

  private Set<IntakeDTO> getIntakeDTOSet(List<Intake> intakes, Treatment tr) {

    List<Intake> intakesPerTreatment = getIntakesPerTreatment(intakes, tr);

    if (intakesPerTreatment.isEmpty()) {
      throw new MedicationManagerPersistenceException("Missing intakes for the treatment: " + tr);
    }

    Set<IntakeDTO> intakeDTOs = new HashSet<IntakeDTO>();

    if (intakesPerTreatment.size() == 1) {
      IntakeDTO dto = createIntakeDao(intakesPerTreatment.get(0));
      intakeDTOs.add(dto);
      return intakeDTOs;
    }

    Set<Intake> realIntakesPerDaySet = new HashSet<Intake>();

    List<Intake> intakesPerDay = findDayOfMonthWithFullNumberOfIntakes(intakesPerTreatment);

    return convertIntakeToDTO(intakesPerDay);

  }

  private Set<IntakeDTO> convertIntakeToDTO(List<Intake> intakesPerDay) {
    Set<IntakeDTO> intakeDTOs = new HashSet<IntakeDTO>();

    for (Intake intake : intakesPerDay) {
      IntakeDTO dto = createIntakeDao(intake);
      intakeDTOs.add(dto);
    }

    return intakeDTOs;
  }

  private List<Intake> findDayOfMonthWithFullNumberOfIntakes(List<Intake> intakesPerTreatment) {

    Map<Integer, List<Intake>> daysMap = new HashMap<Integer, List<Intake>>();

    for (Intake in : intakesPerTreatment) {
      int d = getDayFromIntakeTimePlan(in.getTimePlan());
      List<Intake> intakesPerDay = daysMap.get(d);
      if (intakesPerDay == null) {
        intakesPerDay = new ArrayList<Intake>();
      }
      intakesPerDay.add(in);
      daysMap.put(d, intakesPerDay);
    }

    return findMaxIntakesPerDay(daysMap);

  }

  private List<Intake> findMaxIntakesPerDay(Map<Integer, List<Intake>> daysMap) {

    List<Intake> intakes = new ArrayList<Intake>();

    for (int day : daysMap.keySet()) {
      List<Intake> intakesPerDay = daysMap.get(day);
      if (intakesPerDay.size() > intakes.size()) {
        intakes = intakesPerDay;
      }
    }

    return intakes;

  }

  private int getDayFromIntakeTimePlan(Date timePlan) {
    Calendar plan = new GregorianCalendar();
    plan.setTime(timePlan);

    return plan.get(Calendar.DAY_OF_MONTH);
  }

  private IntakeDTO createIntakeDao(Intake intake) {

    Date timePlan = intake.getTimePlan();

    Calendar plan = new GregorianCalendar();
    plan.setTime(timePlan);

    int hour = plan.get(Calendar.HOUR_OF_DAY);
    int minutes = plan.get(Calendar.MINUTE);

    TimeDTO timeDTO = new TimeDTO(hour, minutes);

    UnitClass unitClass = intake.getUnitClass();
    IntakeDTO.Unit unit = IntakeDTO.Unit.getEnumValueFor(unitClass.getType());
    return new IntakeDTO(
        timeDTO,
        unit,
        intake.getQuantity()
    );
  }

  private List<Intake> getIntakesPerTreatment(List<Intake> intakes, Treatment tr) {
    List<Intake> intakeList = new ArrayList<Intake>();

    for (Intake in : intakes) {
      if (in.getTreatment().getId() == tr.getId()) {
        intakeList.add(in);
      }
    }

    return intakeList;
  }

  private MealRelationDTO getMealRelation(Medicine medicine) {
    MealRelation mealRelation = medicine.getMealRelation();
    return MealRelationDTO.getEnumValueFor(mealRelation.getValue());
  }

  private int getDays(Date startDate, Date endDate) {
    if (startDate.after(endDate)) {
      throw new MedicationManagerPersistenceException("The startDate: " + startDate +
          " is after the endDate: " + endDate);
    }
    return (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
  }

  private List<Prescription> getByPersonAndDoctor(int personId, int doctorId) {
    String sql = "select * from MEDICATION_MANAGER.PRESCRIPTION where " +
        "PATIENT_FK_ID = ? and PHYSICIAN_FK_ID = ? and UPPER(STATUS) = ?";

    System.out.println("sql = " + sql);

    PreparedStatement statement = null;
    try {
      statement = getPreparedStatement(sql);
      return getPrescriptions(personId, doctorId, sql, statement);
    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException(e);
    } finally {
      closeStatement(statement);
    }
  }

  private List<Prescription> getPrescriptions(int personId, int doctorId, String sql,
                                              PreparedStatement statement) throws SQLException {
    statement.setInt(1, personId);
    statement.setInt(2, doctorId);
    statement.setString(3, ACTIVE.getType().toUpperCase());
    List<Map<String, Column>> results = executeQueryExpectedMultipleRecord(TABLE_NAME, sql, statement);
    return createPrescriptions(results);
  }


  private List<PrescriptionDTO> getAllActivePrescriptions() {
    String sql = "select * from MEDICATION_MANAGER.PRESCRIPTION  where UPPER(STATUS) = ?";

    System.out.println("sql = " + sql);

    PreparedStatement statement = null;
    try {
      statement = getPreparedStatement(sql);
      statement.setString(1, ACTIVE.getType().toUpperCase());
      List<Map<String, Column>> results = executeQueryExpectedMultipleRecord(TABLE_NAME, sql, statement);
      List<Prescription> prescriptions = createPrescriptions(results);
      return convertToDTO(prescriptions);
    } catch (SQLException e) {
      throw new MedicationManagerPersistenceException(e);
    } finally {
      closeStatement(statement);
    }
  }

}
