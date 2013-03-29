package org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.forms;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.IntakeDTO;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.MedicineDTO;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.PrescriptionDTO;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.DatabaseSimulation;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.MedicationManagerServletUIException;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.JavaScriptObjectCreator;
import org.universAAL.AALapplication.medication_manager.servlet.ui.impl.parser.script.Pair;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author George Fournadjiev
 */
public final class ListPrescriptionsScriptForm extends ScriptForm {

  private final PersistentService persistentService;
  private final Person patient;

  private static final String LIST_PRESCRIPTIONS_FUNCTION_CALL_TEXT = "userObj.prescriptions.push";
  private static final String DATE = "date";
  private static final String NOTES = "notes";
  private static final String MEDICINE = "medicines";
  private static final String HOW = "how";
  private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

  public ListPrescriptionsScriptForm(PersistentService persistentService, Person patient) {
    super(LIST_PRESCRIPTIONS_FUNCTION_CALL_TEXT);

    this.persistentService = persistentService;
    this.patient = patient;
    setSingleJavascriptObjects();
  }

  @Override
  public void setSingleJavascriptObjects() {
    String name = patient.getName();
    singleJavascriptObjects = new String[]{"\n\t userObj.name = \'" + name + "\';"};
  }

  @Override
  public void process() {

    List<PrescriptionDTO> prescriptionDTOs = DatabaseSimulation.getDescriptions(patient);

    for (PrescriptionDTO pr : prescriptionDTOs) {
      String startDate = getStartDateText(pr.getStartDate());
      Pair<String> date = new Pair<String>(DATE, startDate);
      Pair<String> name = new Pair<String>(NOTES, pr.getDescription());
      String medicinesTextValue = getMedicinesValue(pr.getMedicineDTOSet(), pr.getPrescriptionId());
      Pair<String> medicine = new Pair<String>(MEDICINE, medicinesTextValue, true);

      addRow(date, name, medicine);
    }

  }

  private String getMedicinesValue(Set<MedicineDTO> medicineDTOSet, int prescriptionId) {
    if (medicineDTOSet.isEmpty()) {
      throw new MedicationManagerServletUIException("Missing medicines for " +
          "the prescriptionDTOs with id: " + prescriptionId);
    }

    StringBuffer sb = new StringBuffer();

    sb.append('[');

    int i = 0;
    int size = medicineDTOSet.size();
    for (MedicineDTO dto : medicineDTOSet) {
      String jo = getSingeMedicineValue(dto);
      sb.append(jo);
      i++;
      if (i < size) {
        sb.append(',');
      }
    }

    sb.append(']');
    return sb.toString();
  }

  private String getSingeMedicineValue(MedicineDTO medicineDTO) {
    String name = medicineDTO.getName();
    Set<IntakeDTO> intakeDTOSet = medicineDTO.getIntakeDTOSet();
    if (intakeDTOSet.isEmpty()) {
      throw new MedicationManagerServletUIException("Missing intakes for the medicine with id: " + medicineDTO.getMedicineId());
    }
    int doseCount = 0;
    String unit = null;
    for (IntakeDTO dto : intakeDTOSet) {
      doseCount = doseCount + dto.getDose();
      unit = dto.getUnit().getValue();
    }
    StringBuffer sb = new StringBuffer();
    sb.append(medicineDTO.getDays());
    sb.append(" days X ");
    sb.append(doseCount);
    sb.append(" ");
    sb.append(unit);


    return createMedicineObject(name, sb.toString());
  }

  private String getStartDateText(Date startDate) {
    try {
      return SIMPLE_DATE_FORMAT.format(startDate);
    } catch (Exception e) {
      throw new MedicationManagerServletUIException(e);
    }
  }

  private String createMedicineObject(String name, String how) {

    JavaScriptObjectCreator creator = new JavaScriptObjectCreator();

    Pair<String> pairName = new Pair<String>(NAME, name);
    creator.addPair(pairName);

    Pair<String> pairHow = new Pair<String>(HOW, how);
    creator.addPair(pairHow);

    return creator.createJavascriptObject();

  }
}
