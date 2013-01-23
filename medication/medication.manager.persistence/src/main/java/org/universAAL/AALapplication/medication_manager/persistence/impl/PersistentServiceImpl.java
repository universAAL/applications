package org.universAAL.AALapplication.medication_manager.persistence.impl;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.SqlUtility;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.MedicineDao;
import org.universAAL.AALapplication.medication_manager.persistence.impl.database.Database;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.PersonDao;

import static org.universAAL.AALapplication.medication_manager.configuration.Util.*;

/**
 * @author George Fournadjiev
 */
public final class PersistentServiceImpl implements PersistentService {

  private final SqlUtility sqlUtility;
  private final PersonDao personDao;
  private final MedicineDao medicineDao;

  public PersistentServiceImpl(Database database) {
    validateParameter(database, "database");

    this.sqlUtility = database.getSqlUtility();
    this.personDao = new PersonDao(database);
    this.medicineDao = new MedicineDao(database);
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
}
