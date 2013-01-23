package org.universAAL.AALapplication.medication_manager.persistence.layer;

import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.MedicineDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.PersonDao;

/**
 * @author George Fournadjiev
 */
public interface PersistentService {


  SqlUtility getSqlUtility();

  PersonDao getPersonDao();

  MedicineDao getMedicineDao();

}
