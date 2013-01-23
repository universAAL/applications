package org.universAAL.AALapplication.medication_manager.persistence.layer.dao;

import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.MealRelation;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Medicine;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;

/**
 * @author George Fournadjiev
 */
public final class MedicineDao extends AbstractDao {


  public MedicineDao(Database database) {
    super(database);
  }

  public Medicine getByName(String name) {

    return new Medicine(11, "1", "2", "3", "4", MealRelation.WITH_MEAL);

  }
}
