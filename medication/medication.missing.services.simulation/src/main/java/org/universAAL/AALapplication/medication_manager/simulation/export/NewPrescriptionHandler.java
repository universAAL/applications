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


package org.universAAL.AALapplication.medication_manager.simulation.export;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.PrescriptionDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.TreatmentDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.IntakeDTO;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.MealRelationDTO;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.MedicineDTO;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.PrescriptionDTO;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.TimeDTO;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Medicine;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Treatment;
import org.universAAL.AALapplication.medication_manager.simulation.impl.Log;
import org.universAAL.AALapplication.medication_manager.simulation.impl.MedicationManagerSimulationServicesException;
import org.universAAL.AALapplication.medication_manager.simulation.impl.NewPrescriptionContextProvider;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.ontology.medMgr.CaregiverNotifier;
import org.universAAL.ontology.medMgr.CaregiverNotifierData;
import org.universAAL.ontology.medMgr.Intake;
import org.universAAL.ontology.medMgr.IntakeUnit;
import org.universAAL.ontology.medMgr.MealRelation;
import org.universAAL.ontology.medMgr.MedicationException;
import org.universAAL.ontology.medMgr.MedicationTreatment;
import org.universAAL.ontology.medMgr.NewPrescription;
import org.universAAL.ontology.profile.User;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import static org.universAAL.AALapplication.medication_manager.persistence.layer.Util.*;


/**
 * @author George Fournadjiev
 */
public abstract class NewPrescriptionHandler {

  protected final ServiceCaller serviceCaller;
  private final NewPrescriptionContextProvider newPrescriptionContextProvider;

  private static final String MEDICATION_TREATMENT = "MedicationTreatment";

  public NewPrescriptionHandler(ModuleContext context, NewPrescriptionContextProvider contextProvider) {

    serviceCaller = new DefaultServiceCaller(context);
    newPrescriptionContextProvider = contextProvider;
  }

  public void callHealthServiceWithNewPrescription(PersistentService persistentService,
                                                   PrescriptionDTO prescriptionDTO) {

    try {
      PrescriptionDao prescriptionDao = persistentService.getPrescriptionDao();
      prescriptionDao.save(prescriptionDTO);
      NewPrescription newPrescription = createNewPrescription(prescriptionDTO);
      handleNewPrescription(prescriptionDTO, persistentService, newPrescription);
      newPrescriptionContextProvider.publishNewPrescriptionEvent(newPrescription);
      notifyCaregiverNotificationService(persistentService, prescriptionDTO);
    } catch (DatatypeConfigurationException e) {
      throw new MedicationException(e);
    }

  }

  private void notifyCaregiverNotificationService(PersistentService persistentService,
                                                  PrescriptionDTO prescriptionDTO) {


    Person person = prescriptionDTO.getPatient();

    User user = new User(person.getPersonUri());

    ServiceRequest serviceRequest = new ServiceRequest(new CaregiverNotifier(), user);

    CaregiverNotifierData caregiverNotifierData = new CaregiverNotifierData();
    String smsNumber = getCaregiverSms(person, persistentService.getPatientLinksDao());
    caregiverNotifierData.setSmsNumber(smsNumber);
    String smsText = getSmsText(person, prescriptionDTO);
    caregiverNotifierData.setSmsText(smsText);


    serviceRequest.addAddEffect(new String[]{CaregiverNotifier.PROP_CAREGIVER_NOTIFIER_DATA}, caregiverNotifierData);
    serviceRequest.addRequiredOutput(OUTPUT_CAREGIVER_RECEIVED_MESSAGE,
        new String[]{CaregiverNotifier.PROP_RECEIVED_MESSAGE});

    ServiceResponse serviceResponse = serviceCaller.call(serviceRequest);

    CallStatus callStatus = serviceResponse.getCallStatus();

    String msg;
    if (callStatus.toString().contains("call_succeeded")) {
      msg = getMessage(serviceResponse);
    } else {
      msg = "The Medication Manager service was unable notified the Caregiver Notification Service";
    }
    Log.info("Caregiver Notification callStatus %s\n" + msg, getClass(), callStatus);


  }

  private String getSmsText(Person person, PrescriptionDTO prescriptionDTO) {
    StringBuffer sb = new StringBuffer();

    sb.append("New prescription has been prescribed for Person with id: ");
    sb.append(person.getId());
    sb.append(" and name: ");
    sb.append(person.getName());
    sb.append(". The prescription description is:");
    sb.append(prescriptionDTO.getDescription());
    sb.append(" and the starting date is:");
    sb.append(prescriptionDTO.getStartDate());
    sb.append(".\n");

    return sb.toString();
  }

  private void handleNewPrescription(PrescriptionDTO prescriptionDTO, PersistentService persistentService,
                                     NewPrescription newPrescription) throws DatatypeConfigurationException {

    List<MedicationTreatment> medicationTreatmentList = new ArrayList<MedicationTreatment>();
    TreatmentDao treatmentDao = persistentService.getTreatmentDao();
    List<Treatment> treatments = treatmentDao.getByPrescriptionAndNotInActive(prescriptionDTO.getPrescriptionId());
    for (Treatment tr : treatments) {
      handleTreatment(prescriptionDTO, medicationTreatmentList, tr);
    }

    newPrescription.setMedicationTreatments(medicationTreatmentList);
  }

  private void handleTreatment(PrescriptionDTO prescriptionDTO,
                               List<MedicationTreatment> medicationTreatmentList,
                               Treatment tr) throws DatatypeConfigurationException {

    Medicine med = tr.getMedicine();
    org.universAAL.ontology.medMgr.Medicine medicine = new org.universAAL.ontology.medMgr.Medicine();
    medicine.setMedicineId(med.getId());
    MedicineDTO medicineDTO = getMedicineDTO(med.getId(), prescriptionDTO.getMedicineDTOSet());
    medicine.setDays(medicineDTO.getDays());
    String medicineInfo = med.getMedicineInfo();
    if (medicineInfo != null) {
      medicine.setDescription(medicineInfo);
    }
    String medicineName = med.getMedicineName();
    if (medicineName != null) {
      medicine.setName(medicineName);
    }
    setMealRelationEnum(medicine, medicineDTO.getMealRelationDTO());
    setIntakes(medicine, medicineDTO);
    MedicationTreatment medicationTreatment = createMedicationTreatment(prescriptionDTO);
    medicationTreatment.setMedicine(medicine);
    Person patient = prescriptionDTO.getPatient();
    User user = new User(patient.getPersonUri());
    boolean hasTheCallSucceed = callHealthService(medicationTreatment, user);
    if (!hasTheCallSucceed) {
      throw new MedicationException("Error sending the MedicationTreatment for the medicine with id:" + medicine.getMedicineId());
    }
    medicationTreatmentList.add(medicationTreatment);
  }

  private MedicineDTO getMedicineDTO(int id, Set<MedicineDTO> medicineDTOSet) {
    MedicineDTO medicineDTO = null;
    for (MedicineDTO dto : medicineDTOSet) {
      if (dto.getMedicineId() == id) {
        medicineDTO = dto;
        break;
      }
    }

    if (medicineDTO == null) {
      throw new MedicationManagerSimulationServicesException("No matching medicineDTO");
    }

    return medicineDTO;
  }

  public abstract boolean callHealthService(MedicationTreatment medicationTreatment, User user);

  private NewPrescription createNewPrescription(PrescriptionDTO prescriptionDTO)
      throws DatatypeConfigurationException {

    NewPrescription newPrescription = new NewPrescription();
    newPrescription.setPrescriptionId(prescriptionDTO.getPrescriptionId());
    newPrescription.setDescription(prescriptionDTO.getDescription());
    Date startDate = prescriptionDTO.getStartDate();
    newPrescription.setDate(getXMLGregorianCalendar(startDate));
    return newPrescription;
  }

  private MedicationTreatment createMedicationTreatment(PrescriptionDTO prescriptionDTO)
      throws DatatypeConfigurationException {

    MedicationTreatment medicationTreatment = new MedicationTreatment();

    medicationTreatment.setPrescriptionId(prescriptionDTO.getPrescriptionId());
    medicationTreatment.setName(MEDICATION_TREATMENT);
    medicationTreatment.setDoctorName(prescriptionDTO.getPhysician().getName());
    medicationTreatment.setDescription(prescriptionDTO.getDescription());

    Date startDate = prescriptionDTO.getStartDate();

    GregorianCalendar gregorianCalendar = new GregorianCalendar();
    gregorianCalendar.setTime(startDate);
    XMLGregorianCalendar date = (DatatypeFactory.newInstance()).newXMLGregorianCalendar(gregorianCalendar);
    medicationTreatment.setMedicationTreatmentStartDate(date);
    XMLGregorianCalendar endDate = getEndDate(gregorianCalendar, prescriptionDTO);
    medicationTreatment.setMedicationTreatmentEndDate(endDate);
    medicationTreatment.setStatus(true);

    return medicationTreatment;
  }

  private XMLGregorianCalendar getXMLGregorianCalendar(Date startDate) throws DatatypeConfigurationException {
    GregorianCalendar gregorianCalendar = new GregorianCalendar();
    gregorianCalendar.setTime(startDate);
    return (DatatypeFactory.newInstance()).newXMLGregorianCalendar(gregorianCalendar);
  }


  private XMLGregorianCalendar getEndDate(GregorianCalendar gregorianCalendar,
                                          PrescriptionDTO prescriptionDTO) throws DatatypeConfigurationException {

    GregorianCalendar cal = new GregorianCalendar();
    cal.setTime(gregorianCalendar.getTime());

    int maxDays = getMaxDays(prescriptionDTO.getMedicineDTOSet());

    cal.add(Calendar.DAY_OF_MONTH, maxDays);

    return (DatatypeFactory.newInstance()).newXMLGregorianCalendar(cal);

  }

  private int getMaxDays(Set<MedicineDTO> medicineDTOSet) {
    int maxDays = -1;

    for (MedicineDTO medicineDTO : medicineDTOSet) {
      int days = medicineDTO.getDays();
      if (days > maxDays) {
        maxDays = days;
      }
    }

    return maxDays;
  }

  private void setMealRelationEnum(org.universAAL.ontology.medMgr.Medicine medicine, MealRelationDTO mealRelationDTO) {

    String value = MealRelationDTO.getStringValueFor(mealRelationDTO);

    MealRelation mealRelation = MealRelation.valueOf(value);
    medicine.setMealRelation(mealRelation);
  }

  private void setIntakes(org.universAAL.ontology.medMgr.Medicine medicine, MedicineDTO medicineDTO) {
    Set<IntakeDTO> intakeDTOSet = medicineDTO.getIntakeDTOSet();
    List<Intake> intakeList = new ArrayList<Intake>();
    for (IntakeDTO intakeDTO : intakeDTOSet) {
      Intake intake = new Intake();
      intake.setDose(intakeDTO.getDose());
      TimeDTO time = intakeDTO.getTime();
      intake.setTime(time.getTimeText());
      setIntakeEnum(intake, intakeDTO);
      intakeList.add(intake);
    }

    if (!intakeList.isEmpty()) {
      medicine.setIntakes(intakeList);
    }

  }

  private void setIntakeEnum(Intake intake, IntakeDTO intakeDTO) {
    String value = IntakeDTO.Unit.getStringValueFromEnum(intakeDTO.getUnit());
    IntakeUnit intakeUnit = IntakeUnit.valueOf(value);
    intake.setUnit(intakeUnit);
  }

}
