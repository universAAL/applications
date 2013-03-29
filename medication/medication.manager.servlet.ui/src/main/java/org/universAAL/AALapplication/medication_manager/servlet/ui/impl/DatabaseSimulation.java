package org.universAAL.AALapplication.medication_manager.servlet.ui.impl;

import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.IntakeDTO;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.MealRelationDTO;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.MedicineDTO;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.PrescriptionDTO;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.TimeDTO;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Role.*;

/**
 * @author George Fournadjiev
 */
public final class DatabaseSimulation {

  private static int idConter = 100;

  public static final Person DOCTOR = new Person("Penchno", "uri", PHYSICIAN, "us", "pa");
  public static final Person PATIENT_1 = new Person(100, "Penchno", "uriPencho", PATIENT);
  public static final Person PATIENT_2 = new Person(101, "Penka", "uriPenka", PATIENT);
  public static final Person PATIENT_3 = new Person(102, "Dummy", "uriDummy", PATIENT);
  public static final Person[] PATIENTS = new Person[]{PATIENT_1, PATIENT_2, PATIENT_3};
  private static final Set<IntakeDTO> INTAKE_DTO_SET_1 = new HashSet<IntakeDTO>();
  private static final Set<MedicineDTO> MEDICINE_DTO_SET_1 = new HashSet<MedicineDTO>();
  private static final Set<IntakeDTO> INTAKE_DTO_SET_2 = new HashSet<IntakeDTO>();
  private static final Set<MedicineDTO> MEDICINE_DTO_SET_2 = new HashSet<MedicineDTO>();
  private static final PrescriptionDTO PRESCRIPTION_DTO_1;
  private static final Set<IntakeDTO> INTAKE_DTO_SET_3 = new HashSet<IntakeDTO>();
  private static final Set<MedicineDTO> MEDICINE_DTO_SET_3 = new HashSet<MedicineDTO>();
  private static final PrescriptionDTO PRESCRIPTION_DTO_2;
  private static final PrescriptionDTO PRESCRIPTION_DTO_3;
  private static final PrescriptionDTO PRESCRIPTION_DTO_4;
  private static final Map<Integer, List<PrescriptionDTO>> PRESCRIPTION_DTO_MAP =
      new HashMap<Integer, List<PrescriptionDTO>>();

  static {

    IntakeDTO i11 = new IntakeDTO(TimeDTO.createTimeDTO("8:00"), IntakeDTO.Unit.PILL, 2);
    IntakeDTO i12 = new IntakeDTO(TimeDTO.createTimeDTO("12:00"), IntakeDTO.Unit.PILL, 1);
    IntakeDTO i13 = new IntakeDTO(TimeDTO.createTimeDTO("19:00"), IntakeDTO.Unit.PILL, 2);

    INTAKE_DTO_SET_1.add(i11);
    INTAKE_DTO_SET_1.add(i12);
    INTAKE_DTO_SET_1.add(i13);

    MedicineDTO med1 = new MedicineDTO("aspirin", new Date(), 5, "aspirin description", "aspirin  sideeffects",
        "aspirin incompliances", MealRelationDTO.AFTER, INTAKE_DTO_SET_1);
    med1.setMedicineIdId(1);

    MEDICINE_DTO_SET_1.add(med1);

    IntakeDTO i21 = new IntakeDTO(TimeDTO.createTimeDTO("10:00"), IntakeDTO.Unit.PILL, 1);
    IntakeDTO i22 = new IntakeDTO(TimeDTO.createTimeDTO("18:00"), IntakeDTO.Unit.PILL, 2);
    IntakeDTO i23 = new IntakeDTO(TimeDTO.createTimeDTO("22:00"), IntakeDTO.Unit.PILL, 3);

    INTAKE_DTO_SET_2.add(i21);
    INTAKE_DTO_SET_2.add(i22);
    INTAKE_DTO_SET_2.add(i23);

    MedicineDTO med2 = new MedicineDTO("benalgin", new Date(), 7, "benalgin description", "benalgin  sideeffects",
        "benalgin incompliances", MealRelationDTO.WITH_MEAL, INTAKE_DTO_SET_2);
    med2.setMedicineIdId(2);

    MEDICINE_DTO_SET_1.add(med2);

    PRESCRIPTION_DTO_1 = new PrescriptionDTO("prescription 1", new Date(), MEDICINE_DTO_SET_1, DOCTOR, PATIENT_1);
    PRESCRIPTION_DTO_1.setPrescriptionId(1);

    IntakeDTO in11 = new IntakeDTO(TimeDTO.createTimeDTO("6:00"), IntakeDTO.Unit.PILL, 2);
    IntakeDTO in12 = new IntakeDTO(TimeDTO.createTimeDTO("14:00"), IntakeDTO.Unit.PILL, 2);

    INTAKE_DTO_SET_3.add(in11);
    INTAKE_DTO_SET_3.add(in12);

    MedicineDTO m1 = new MedicineDTO("viteral", new Date(), 5, "viteral description", "viteral  sideeffects",
        "viteral incompliances", MealRelationDTO.AFTER, INTAKE_DTO_SET_3);
    m1.setMedicineIdId(3);

    MEDICINE_DTO_SET_3.add(m1);

    PRESCRIPTION_DTO_2 = new PrescriptionDTO("prescription 2", new Date(), MEDICINE_DTO_SET_3, DOCTOR, PATIENT_2);
    PRESCRIPTION_DTO_2.setPrescriptionId(2);

    PRESCRIPTION_DTO_3 = new PrescriptionDTO("prescription 3", new Date(), MEDICINE_DTO_SET_3, DOCTOR, PATIENT_1);
    PRESCRIPTION_DTO_3.setPrescriptionId(3);

    PRESCRIPTION_DTO_4 = new PrescriptionDTO("prescription 4", new Date(), MEDICINE_DTO_SET_1, DOCTOR, PATIENT_2);
    PRESCRIPTION_DTO_4.setPrescriptionId(4);

    int id1 = PRESCRIPTION_DTO_1.getPatient().getId();
    List<PrescriptionDTO> prescriptionDTOs1 = new ArrayList<PrescriptionDTO>();
    prescriptionDTOs1.add(PRESCRIPTION_DTO_1);
    prescriptionDTOs1.add(PRESCRIPTION_DTO_3);
    PRESCRIPTION_DTO_MAP.put(id1, prescriptionDTOs1);
    int id2 = PRESCRIPTION_DTO_2.getPatient().getId();
    List<PrescriptionDTO> prescriptionDTOs2 = new ArrayList<PrescriptionDTO>();
    prescriptionDTOs2.add(PRESCRIPTION_DTO_2);
    prescriptionDTOs2.add(PRESCRIPTION_DTO_4);
    PRESCRIPTION_DTO_MAP.put(id2, prescriptionDTOs2);

  }


  public static Person getPatientById(int id) {
    for (Person person : PATIENTS) {
      if (id == person.getId()) {
        return person;
      }
    }

    throw new MedicationManagerServletUIException("Missing patient with the following id: " + id);
  }

  public static synchronized List<PrescriptionDTO> getDescriptions(Person patient) {

    return PRESCRIPTION_DTO_MAP.get(patient.getId());

  }

  public static synchronized void addDescriptionDTO(Person patient, PrescriptionDTO dto) {

    List<PrescriptionDTO> prescriptionDTOs = PRESCRIPTION_DTO_MAP.get(patient.getId());
    prescriptionDTOs.add(dto);

  }

  public static synchronized int generateId() {
    idConter++;
    return idConter;
  }
}
