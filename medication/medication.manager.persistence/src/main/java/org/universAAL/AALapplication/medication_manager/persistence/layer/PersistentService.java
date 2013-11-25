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

package org.universAAL.AALapplication.medication_manager.persistence.layer;

import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.ComplexDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.DispenserDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.MedicationPropertiesDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.PatientLinksDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.IntakeDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.InventoryLogDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.MedicineDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.MedicineInventoryDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.PersonDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.PrescriptionDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.TreatmentDao;

/**
 * @author George Fournadjiev
 */
public interface PersistentService {


  SqlUtility getSqlUtility();

  DispenserDao getDispenserDao();

  IntakeDao getIntakeDao();

  InventoryLogDao getInventoryLogDao();

  MedicineDao getMedicineDao();

  MedicineInventoryDao getMedicineInventoryDao();

  PersonDao getPersonDao();

  PrescriptionDao getPrescriptionDao();

  TreatmentDao getTreatmentDao();

  PatientLinksDao getPatientLinksDao();

  ComplexDao getComplexDao();

  MedicationPropertiesDao getMedicationPropertiesDao();

  int generateId();

}
