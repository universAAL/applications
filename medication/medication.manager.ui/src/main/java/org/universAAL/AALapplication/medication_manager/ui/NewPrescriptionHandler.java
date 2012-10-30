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


package org.universAAL.AALapplication.medication_manager.ui;

import org.universAAL.AALapplication.medication_manager.ui.impl.Log;
import org.universAAL.AALapplication.medication_manager.ui.prescription.IntakeDTO;
import org.universAAL.AALapplication.medication_manager.ui.prescription.MealRelationDTO;
import org.universAAL.AALapplication.medication_manager.ui.prescription.MedicineDTO;
import org.universAAL.AALapplication.medication_manager.ui.prescription.PrescriptionDTO;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.ontology.medMgr.Intake;
import org.universAAL.ontology.medMgr.IntakeUnit;
import org.universAAL.ontology.medMgr.MealRelation;
import org.universAAL.ontology.medMgr.MedicationException;
import org.universAAL.ontology.medMgr.MedicationTreatment;
import org.universAAL.ontology.medMgr.Medicine;
import org.universAAL.ontology.medMgr.NewPrescription;
import org.universAAL.ontology.medMgr.UserIDs;
import org.universaal.ontology.health.owl.TreatmentPlanning;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

/**
 * @author George Fournadjiev
 */
public final class NewPrescriptionHandler {

  private static ServiceCaller serviceCaller;

  private static final String NEW_PRESCRIPTION_NAMESPACE = "http://ontology.igd.fhg.de/NewPrescription.owl#";
  private static final String OUTPUT_NEW_PRESCRIPTION_RECEIVED_MESSAGE = NEW_PRESCRIPTION_NAMESPACE + "receivedMessage";
  private static final String MEDICATION_TREATMENT = "MedicationTreatment";

  public NewPrescriptionHandler(ServiceCaller serviceCaller) {
    this.serviceCaller = serviceCaller;
  }

  public static void callHealthServiceWithNewPrescription(PrescriptionDTO prescriptionDTO) {

    MedicationTreatment medicationTreatment = new MedicationTreatment();

    try {

      populateMedicationTreatment(prescriptionDTO, medicationTreatment);

      callHealthService(medicationTreatment);

    } catch (DatatypeConfigurationException e) {
      throw new MedicationException(e);
    }

  }

  private static void callHealthService(MedicationTreatment medicationTreatment) {
    ServiceRequest serviceRequest = new ServiceRequest(new NewPrescription(), UserIDs.getSaiedUser());
    serviceRequest.addAddEffect(new String[]{NewPrescription.PROP_MEDICATION_TREATMENT}, medicationTreatment);
    serviceRequest.addRequiredOutput(OUTPUT_NEW_PRESCRIPTION_RECEIVED_MESSAGE,
        new String[]{NewPrescription.PROP_RECEIVED_MESSAGE});

    ServiceResponse serviceResponse = serviceCaller.call(serviceRequest);

    CallStatus callStatus = serviceResponse.getCallStatus();
    Log.info("callStatus %s", NewPrescriptionHandler.class, callStatus);

    String receivedMessage = getReceivedMessage(serviceResponse);
    if (callStatus.equals(CallStatus.succeeded) && receivedMessage != null) {
      Log.info("The received message from the Health Service is %s", NewPrescriptionHandler.class, receivedMessage);
    } else {
      Log.info("There is the problem with the response with the Health Service", NewPrescriptionHandler.class);
    }


  }

  private static String getReceivedMessage(ServiceResponse serviceResponse) {
    List receivedMessageList = serviceResponse.getOutput(OUTPUT_NEW_PRESCRIPTION_RECEIVED_MESSAGE, true);
    if (receivedMessageList.isEmpty() || receivedMessageList.size() > 1) {
      Log.info("received response object as a null or list is bigger than 1", NewPrescriptionHandler.class);
      return null;
    }

    return (String) receivedMessageList.get(0);

  }

  private static void populateMedicationTreatment(PrescriptionDTO prescriptionDTO,
                                                  MedicationTreatment medicationTreatment) throws DatatypeConfigurationException {

    medicationTreatment.setPrescriptionId(prescriptionDTO.getId());
    medicationTreatment.setName(MEDICATION_TREATMENT);
    medicationTreatment.setDoctorName(prescriptionDTO.getDoctor().getName());
    medicationTreatment.setDescription(prescriptionDTO.getDescription());

    Date startDate = prescriptionDTO.getStartDate();

    GregorianCalendar gregorianCalendar = new GregorianCalendar();
    gregorianCalendar.setTime(startDate);
    XMLGregorianCalendar date = (DatatypeFactory.newInstance()).newXMLGregorianCalendar(gregorianCalendar);
    medicationTreatment.checkStatus(date);
    medicationTreatment.setMedicationTreatmentStartDate(date);
    setTreatmentPlaning(medicationTreatment, date, gregorianCalendar, prescriptionDTO);

    Set<MedicineDTO> medicineDTOSet = prescriptionDTO.getMedicineDTOSet();
    List<Medicine> medicineList = new ArrayList<Medicine>();
    for (MedicineDTO medicineDTO : medicineDTOSet) {
      Medicine medicine = new Medicine();
      medicine.setMedicineId(medicineDTO.getId());
      medicine.setDays(medicineDTO.getDays());
      medicine.setDescription(medicineDTO.getDescription());
      medicine.setName(medicineDTO.getName());
      setMealRelationEnum(medicine, medicineDTO.getMealRelationDTO());
      setIntakes(medicine, medicineDTO);
      medicineList.add(medicine);
    }
    if (!medicineList.isEmpty()) {
      medicationTreatment.setMedicines(medicineList);
    }
  }

  private static void setTreatmentPlaning(MedicationTreatment medicationTreatment, XMLGregorianCalendar startDate,
                                          GregorianCalendar gregorianCalendar,
                                          PrescriptionDTO prescriptionDTO) throws DatatypeConfigurationException {

    TreatmentPlanning treatmentPlanning = new TreatmentPlanning();
    treatmentPlanning.setStartDate(startDate);
    XMLGregorianCalendar endDate = getEndDate(gregorianCalendar, prescriptionDTO);
    treatmentPlanning.setEndDate(endDate);
    medicationTreatment.setTreatmentPlanning(treatmentPlanning);
  }

  private static XMLGregorianCalendar getEndDate(GregorianCalendar gregorianCalendar,
                                                 PrescriptionDTO prescriptionDTO) throws DatatypeConfigurationException {

    GregorianCalendar cal = new GregorianCalendar();
    cal.setTime(gregorianCalendar.getTime());

    int maxDays = getMaxDays(prescriptionDTO.getMedicineDTOSet());

    cal.add(Calendar.DAY_OF_MONTH, maxDays);

    return (DatatypeFactory.newInstance()).newXMLGregorianCalendar(cal);

  }

  private static int getMaxDays(Set<MedicineDTO> medicineDTOSet) {
    int maxDays = -1;

    for (MedicineDTO medicineDTO : medicineDTOSet) {
      int days = medicineDTO.getDays();
      if (days > maxDays) {
        maxDays = days;
      }
    }

    return maxDays;
  }

  private static void setMealRelationEnum(Medicine medicine, MealRelationDTO mealRelationDTO) {

    String value = MealRelationDTO.getStringValueFor(mealRelationDTO);

    MealRelation mealRelation = MealRelation.valueOf(value);
    medicine.setMealRelation(mealRelation);
  }

  private static void setIntakes(Medicine medicine, MedicineDTO medicineDTO) {
    Set<IntakeDTO> intakeDTOSet = medicineDTO.getIntakeDTOSet();
    List<Intake> intakeList = new ArrayList<Intake>();
    for (IntakeDTO intakeDTO : intakeDTOSet) {
      Intake intake = new Intake();
      intake.setDose(intakeDTO.getDose());
      intake.setTime(intakeDTO.getTime());
      setIntakeEnum(intake, intakeDTO);
      intakeList.add(intake);
    }

    if (!intakeList.isEmpty()) {
      medicine.setIntakes(intakeList);
    }

  }

  private static void setIntakeEnum(Intake intake, IntakeDTO intakeDTO) {
    String value = IntakeDTO.Unit.getStringValueFromEnum(intakeDTO.getUnit());
    IntakeUnit intakeUnit = IntakeUnit.valueOf(value);
    intake.setUnit(intakeUnit);
  }

}
