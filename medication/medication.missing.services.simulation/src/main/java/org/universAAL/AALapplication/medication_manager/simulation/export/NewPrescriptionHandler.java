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

import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.IntakeDTO;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.MealRelationDTO;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.MedicineDTO;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dto.PrescriptionDTO;
import org.universAAL.AALapplication.medication_manager.simulation.impl.Log;
import org.universAAL.AALapplication.medication_manager.simulation.impl.NewPrescriptionContextProvider;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.DefaultServiceCaller;
import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.service.ServiceRequest;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.ontology.medMgr.Intake;
import org.universAAL.ontology.medMgr.IntakeUnit;
import org.universAAL.ontology.medMgr.MealRelation;
import org.universAAL.ontology.medMgr.MedicationException;
import org.universAAL.ontology.medMgr.MedicationTreatment;
import org.universAAL.ontology.medMgr.Medicine;
import org.universAAL.ontology.medMgr.NewMedicationTreatmentNotifier;
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

  private final ServiceCaller serviceCaller;
  private final NewPrescriptionContextProvider newPrescriptionContextProvider;

  private static final String NEW_MEDICATION_TREATMENT_NOTIFIER_NAMESPACE =
      "http://ontology.igd.fhg.de/NewMedicationTreatmentNotifier.owl#";
  private static final String OUTPUT_NEW_PRESCRIPTION_RECEIVED_MESSAGE =
      NEW_MEDICATION_TREATMENT_NOTIFIER_NAMESPACE + "receivedMessage";
  private static final String MEDICATION_TREATMENT = "MedicationTreatment";

  public NewPrescriptionHandler(ModuleContext context, NewPrescriptionContextProvider contextProvider) {

    serviceCaller = new DefaultServiceCaller(context);
    newPrescriptionContextProvider = contextProvider;
  }

  public void callHealthServiceWithNewPrescription(PrescriptionDTO prescriptionDTO) {

    try {
      NewPrescription newPrescription = createNewPrescription(prescriptionDTO);
      handleNewPrescription(prescriptionDTO, newPrescription);
      newPrescriptionContextProvider.publishNewPrescriptionEvent(newPrescription);
    } catch (DatatypeConfigurationException e) {
      throw new MedicationException(e);
    }

  }

  private void handleNewPrescription(PrescriptionDTO prescriptionDTO,
                                            NewPrescription newPrescription)
      throws DatatypeConfigurationException {

    List<MedicationTreatment> medicationTreatmentList = new ArrayList<MedicationTreatment>();
    Set<MedicineDTO> medicineDTOSet = prescriptionDTO.getMedicineDTOSet();
    for (MedicineDTO medicineDTO : medicineDTOSet) {
      Medicine medicine = new Medicine();
      medicine.setMedicineId(medicineDTO.getId());
      medicine.setDays(medicineDTO.getDays());
      medicine.setDescription(medicineDTO.getDescription());
      medicine.setName(medicineDTO.getName());
      setMealRelationEnum(medicine, medicineDTO.getMealRelationDTO());
      setIntakes(medicine, medicineDTO);
      MedicationTreatment medicationTreatment = createMedicationTreatment(prescriptionDTO);
      medicationTreatment.setMedicine(medicine);
      boolean hasTheCallSucceed = callHealthService(medicationTreatment);
      if (!hasTheCallSucceed) {
        throw new MedicationException("Error sending the MedicationTreatment for the medicine with id:" + medicine.getMedicineId());
      }
      medicationTreatmentList.add(medicationTreatment);
    }

    newPrescription.setMedicationTreatments(medicationTreatmentList);
  }

  private NewPrescription createNewPrescription(PrescriptionDTO prescriptionDTO)
      throws DatatypeConfigurationException {

    NewPrescription newPrescription = new NewPrescription();
    newPrescription.setPrescriptionId(prescriptionDTO.getId());
    newPrescription.setDescription(prescriptionDTO.getDescription());
    Date startDate = prescriptionDTO.getStartDate();
    newPrescription.setDate(getXMLGregorianCalendar(startDate));
    return newPrescription;
  }

  private boolean callHealthService(MedicationTreatment medicationTreatment) {
    ServiceRequest serviceRequest = new ServiceRequest(new NewMedicationTreatmentNotifier(), UserIDs.getSaiedUser());
    serviceRequest.addAddEffect(new String[]{NewMedicationTreatmentNotifier.PROP_MEDICATION_TREATMENT}, medicationTreatment);
    serviceRequest.addRequiredOutput(OUTPUT_NEW_PRESCRIPTION_RECEIVED_MESSAGE,
        new String[]{NewMedicationTreatmentNotifier.PROP_RECEIVED_MESSAGE});

    ServiceResponse serviceResponse = serviceCaller.call(serviceRequest);

    CallStatus callStatus = serviceResponse.getCallStatus();
    Log.info("callStatus %s", NewPrescriptionHandler.class, callStatus);

    String receivedMessage = getReceivedMessage(serviceResponse);
    if (callStatus.equals(CallStatus.succeeded) && receivedMessage != null) {
      Log.info("The received message from the Health Service is %s", NewPrescriptionHandler.class, receivedMessage);
      return true;
    } else {
      Log.error("There is the problem with the response with the Health Service", NewPrescriptionHandler.class);
      return false;
    }


  }

  private String getReceivedMessage(ServiceResponse serviceResponse) {
    List receivedMessageList = serviceResponse.getOutput(OUTPUT_NEW_PRESCRIPTION_RECEIVED_MESSAGE, true);
    if (receivedMessageList.isEmpty() || receivedMessageList.size() > 1) {
      Log.info("received response object as a null or list is bigger than 1", NewPrescriptionHandler.class);
      return null;
    }

    return (String) receivedMessageList.get(0);

  }

  private MedicationTreatment createMedicationTreatment(PrescriptionDTO prescriptionDTO)
      throws DatatypeConfigurationException {

    MedicationTreatment medicationTreatment = new MedicationTreatment();

    medicationTreatment.setPrescriptionId(prescriptionDTO.getId());
    medicationTreatment.setName(MEDICATION_TREATMENT);
    medicationTreatment.setDoctorName(prescriptionDTO.getPhysician().getName());
    medicationTreatment.setDescription(prescriptionDTO.getDescription());

    Date startDate = prescriptionDTO.getStartDate();

    GregorianCalendar gregorianCalendar = new GregorianCalendar();
    gregorianCalendar.setTime(startDate);
    XMLGregorianCalendar date = (DatatypeFactory.newInstance()).newXMLGregorianCalendar(gregorianCalendar);
    medicationTreatment.checkStatus(date);
    medicationTreatment.setMedicationTreatmentStartDate(date);
    setTreatmentPlaning(medicationTreatment, date, gregorianCalendar, prescriptionDTO);

    return medicationTreatment;
  }

  private XMLGregorianCalendar getXMLGregorianCalendar(Date startDate) throws DatatypeConfigurationException {
    GregorianCalendar gregorianCalendar = new GregorianCalendar();
    gregorianCalendar.setTime(startDate);
    return (DatatypeFactory.newInstance()).newXMLGregorianCalendar(gregorianCalendar);
  }


  private void setTreatmentPlaning(MedicationTreatment medicationTreatment, XMLGregorianCalendar startDate,
                                          GregorianCalendar gregorianCalendar,
                                          PrescriptionDTO prescriptionDTO) throws DatatypeConfigurationException {

    TreatmentPlanning treatmentPlanning = new TreatmentPlanning();
    treatmentPlanning.setStartDate(startDate);
    XMLGregorianCalendar endDate = getEndDate(gregorianCalendar, prescriptionDTO);
    treatmentPlanning.setEndDate(endDate);
    medicationTreatment.setTreatmentPlanning(treatmentPlanning);
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

  private void setMealRelationEnum(Medicine medicine, MealRelationDTO mealRelationDTO) {

    String value = MealRelationDTO.getStringValueFor(mealRelationDTO);

    MealRelation mealRelation = MealRelation.valueOf(value);
    medicine.setMealRelation(mealRelation);
  }

  private void setIntakes(Medicine medicine, MedicineDTO medicineDTO) {
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

  private void setIntakeEnum(Intake intake, IntakeDTO intakeDTO) {
    String value = IntakeDTO.Unit.getStringValueFromEnum(intakeDTO.getUnit());
    IntakeUnit intakeUnit = IntakeUnit.valueOf(value);
    intake.setUnit(intakeUnit);
  }

}
