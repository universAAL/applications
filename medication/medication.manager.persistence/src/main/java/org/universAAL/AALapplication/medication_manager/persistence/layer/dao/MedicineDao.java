package org.universAAL.AALapplication.medication_manager.persistence.layer.dao;

import org.universAAL.AALapplication.medication_manager.persistence.impl.Log;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.AbstractDao;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Column;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.MealRelation;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Medicine;

import java.util.Map;

/**
 * @author George Fournadjiev
 */
public final class MedicineDao extends AbstractDao {


  private static final String TABLE_NAME = "MEDICINE";
  private static final String MEDICINE_NAME = "MEDICINE_NAME";
  private static final String MEDICINE_INFO = "MEDICINE_INFO";
  private static final String SIDE_EFFECTS = "SIDE_EFFECTS";
  private static final String INCOMPLIANCES = "INCOMPLIANCES";
  private static final String MEAL_RELATION = "MEAL_RELATION";

  public MedicineDao(Database database) {
    super(database, TABLE_NAME);
  }

  @Override
  @SuppressWarnings("unchecked")
  public Medicine getById(int id) {
    Log.info("Looking for the medicine with id=%s", getClass(), id);

    Map<String, Column> columns = getTableColumnsValuesById(id);

    Column col = columns.get(MEDICINE_NAME);
    String medicineName = (String) col.getValue();

    col = columns.get(MEDICINE_INFO);
    String medicineInfo = (String) col.getValue();

    col = columns.get(SIDE_EFFECTS);
    String sideEffects = (String) col.getValue();

    col = columns.get(INCOMPLIANCES);
    String incompliances = (String) col.getValue();

    col = columns.get(MEAL_RELATION);
    String mealRelationText = (String) col.getValue();
    MealRelation mealRelation = MealRelation.getEnumValueFor(mealRelationText);

    Medicine medicine = new Medicine(id, medicineName, medicineInfo, sideEffects, incompliances, mealRelation);

    Log.info("Medicine found: %s", getClass(), medicine);

    return medicine;
  }

}
