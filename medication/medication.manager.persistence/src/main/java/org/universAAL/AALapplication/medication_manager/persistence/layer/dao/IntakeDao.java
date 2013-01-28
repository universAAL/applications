package org.universAAL.AALapplication.medication_manager.persistence.layer.dao;

import org.universAAL.AALapplication.medication_manager.persistence.impl.Log;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.AbstractDao;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Column;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Dispenser;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Intake;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Medicine;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.UnitClass;

import java.util.Date;
import java.util.Map;

/**
 * @author George Fournadjiev
 */
public final class IntakeDao extends AbstractDao {

  private final DispenserDao dispenserDao;
  private final PersonDao personDao;
  private final MedicineDao medicineDao;

  private static final String TABLE_NAME = "INTAKE";
  private static final String PATIENT_FK_ID = "PATIENT_FK_ID";
  private static final String MEDICINE_FK_ID = "MEDICINE_FK_ID";
  private static final String QUANTITY = "QUANTITY";
  private static final String UNITS = "UNITS";
  private static final String TIME_PLAN = "TIME_PLAN";
  private static final String TIME_TAKEN = "TIME_TAKEN";

  public IntakeDao(Database database, DispenserDao dispenserDao, PersonDao personDao, MedicineDao medicineDao) {
    super(database, TABLE_NAME);

    this.dispenserDao = dispenserDao;
    this.personDao = personDao;
    this.medicineDao = medicineDao;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Intake getById(int id) {
    Log.info("Looking for the intake with id=%s", getClass(), id);

    Map<String, Column> columns = getTableColumnsValuesById(id);

    Column col = columns.get(PATIENT_FK_ID);
    int personId = (Integer) col.getValue();
    Person person = personDao.getById(personId);

    col = columns.get(MEDICINE_FK_ID);
    int medicineId = (Integer) col.getValue();

    Medicine medicine = medicineDao.getById(medicineId);

    col = columns.get(QUANTITY);
    int quantity = (Integer) col.getValue();

    col = columns.get(UNITS);
    String unitsText = (String) col.getValue();
    UnitClass unitClass = UnitClass.getEnumValueFor(unitsText);

    col = columns.get(TIME_PLAN);
    Date timePlan = (Date) col.getValue();

    col = columns.get(TIME_TAKEN);
    Date timeTaken = (Date) col.getValue();

    col = columns.get("DISPENSER_FK_ID");
    Integer dispenserId = (Integer) col.getValue();

    Dispenser dispenser = null;
    if (dispenserId > 0) {
      dispenser = dispenserDao.getById(dispenserId);
    }

    Intake intake = new Intake(id, person, medicine, quantity, unitClass, timePlan, timeTaken, dispenser);

    Log.info("Intake found: %s", getClass(), intake);

    return intake;
  }


}
