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

package org.universAAL.AALapplication.medication_manager.servlet.ui.acquire.medicine.impl.servlets.forms;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.MedicineInventoryDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.TreatmentDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Medicine;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.MedicineInventory;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Treatment;
import org.universAAL.AALapplication.medication_manager.servlet.ui.acquire.medicine.impl.MedicationManagerAcquireMedicineException;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.JavaScriptObjectCreator;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.Pair;
import org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.forms.ScriptForm;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.universAAL.AALapplication.medication_manager.servlet.ui.base.export.parser.script.Script.*;

/**
 * @author George Fournadjiev
 */
public final class MedicineSelectScriptForm extends ScriptForm {

  public static final String UNIT = "UNIT";
  public static final String QTY = "qty";
  private final Person patient;
  private final PersistentService persistentService;
  private final List<String> availablePairs = new LinkedList<String>();
  private final String availableFunctionCallText;

  private static final String MEDICINES_SELECT_FUNCTION_CALL_TEXT = "medicines.push";

  public MedicineSelectScriptForm(Person patient, PersistentService persistentService) {
    super(MEDICINES_SELECT_FUNCTION_CALL_TEXT);

    this.patient = patient;
    this.persistentService = persistentService;
    availableFunctionCallText = "available_medicines.push";
  }

  @Override
  public void setSingleJavascriptObjects() {
    // nothing to do here
  }

  @Override
  public void process() {

    TreatmentDao treatmentDao = persistentService.getTreatmentDao();
    Set<Treatment> treatments = treatmentDao.findTreatments(patient);
    if (treatments.isEmpty()) {
      throw new MedicationManagerAcquireMedicineException("Missing active or pending " +
          "treatments for the patient: " + patient.getName());
    }
    Set<Medicine> medicines = getMedicines(treatments);
    for (Medicine med : medicines) {
      String medId = String.valueOf(med.getId());
      Pair<String> id = new Pair<String>(ID, medId);
      Pair<String> name = new Pair<String>(NAME, med.getMedicineName());
      Pair<String> unit = new Pair<String>(UNIT.toLowerCase(), med.getUnitClass().getType().toLowerCase());
      addRow(id, name, unit);
    }

  }

  public void processAvailableMedicines() {

    MedicineInventoryDao medicineInventoryDao = persistentService.getMedicineInventoryDao();

    List<MedicineInventory> inventoriesForPatient = medicineInventoryDao.getAllMedicineInventoriesForPatient(patient);

    for (MedicineInventory inventory : inventoriesForPatient) {
      Medicine med = inventory.getMedicine();
      Pair<String> name = new Pair<String>(NAME, med.getMedicineName());
      Pair<String> qty = new Pair<String>(QTY, String.valueOf(inventory.getQuantity()));
      Pair<String> unit = new Pair<String>(UNIT.toLowerCase(), med.getUnitClass().getType().toLowerCase());
      addRowForAvailableMedicines(name, qty, unit);
    }

  }

  public void addRowForAvailableMedicines(Pair... pair) {
    JavaScriptObjectCreator creator = new JavaScriptObjectCreator();

    for (Pair p : pair) {
      if (p.isNotEmpty()) {
        creator.addPair(p);
      }
    }

    String javascriptObject = creator.createJavascriptObject();

    availablePairs.add(javascriptObject);
  }

  private Set<Medicine> getMedicines(Set<Treatment> treatments) {
    Set<Medicine> medicines = new HashSet<Medicine>();

    for (Treatment treatment : treatments) {
      Medicine med = treatment.getMedicine();
      if (okToInsert(medicines, med)) {
        medicines.add(med);
      }
    }

    return medicines;
  }

  private boolean okToInsert(Set<Medicine> medicines, Medicine med) {
    for (Medicine medicine : medicines) {
      if (medicine.getId() == med.getId()) {
        return false;
      }
    }

    return true;
  }

  @Override
  public String createScriptText() {
    process();
    processAvailableMedicines();

    StringBuffer sb = new StringBuffer();
    sb.append(SCRIPT_START);
    sb.append(NEW_LINE_WITH_TAB);

    for (String objectText : pairs) {
      sb.append(functionCallText);
      sb.append('(');
      sb.append(objectText);
      sb.append(");");
      sb.append(NEW_LINE_WITH_TAB);
    }

    sb.append(NEW_LINE);

    sb.append(NEW_LINE_WITH_TAB);

    for (String objectText : availablePairs) {
      sb.append(availableFunctionCallText);
      sb.append('(');
      sb.append(objectText);
      sb.append(");");
      sb.append(NEW_LINE_WITH_TAB);
    }

    sb.append(NEW_LINE);

    sb.append(SCRIPT_END);

    return sb.toString();
  }
}
