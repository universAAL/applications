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


package org.universAAL.AALapplication.medication_manager.simulation.impl;

import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.ontology.medMgr.Intake;
import org.universAAL.ontology.medMgr.MedicationTreatment;
import org.universAAL.ontology.medMgr.Medicine;
import org.universAAL.ontology.profile.User;

import java.util.List;


/**
 * @author George Fournadjiev
 */
public final class HealthPrescriptionServiceProvider extends ServiceCallee {


  // this is just to prepare a standard error message for later use
  private static final ServiceResponse invalidInput = new ServiceResponse(
      CallStatus.serviceSpecificFailure);

  static {
    invalidInput.addOutput(new ProcessOutput(
        ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR, "Invalid input!"));
  }

  public HealthPrescriptionServiceProvider(ModuleContext context) {
    super(context, ProviderHealthPrescriptionService.profiles);

  }

  public void communicationChannelBroken() {
    //Not implemented yet
  }

  public ServiceResponse handleCall(ServiceCall call) {
    try {
      String processURI = call.getProcessURI();

      Log.info("Received call %s", getClass(), processURI);

      User involvedUser = (User) call.getInvolvedUser();

      Log.info("involvedUser %s", getClass(), involvedUser);

      if (involvedUser == null) {
        return invalidInput;
      }

      if (!processURI.startsWith(ProviderHealthPrescriptionService.SERVICE_NOTIFY)) {
        return invalidInput;
      }

      MedicationTreatment medicationTreatment =
          (MedicationTreatment) call.getInputValue(ProviderHealthPrescriptionService.INPUT_MEDICATION_TREATMENT);

      if (medicationTreatment != null && medicationTreatment.isWellFormed()) {
        printMedicationTreatmentData(medicationTreatment);
        return getSuccessfulServiceResponse(involvedUser);
      }

      return invalidInput;
    } catch (Exception e) {
      Log.error(e, "Error while processing the client call", getClass());
      return invalidInput;
    }
  }

  private ServiceResponse getSuccessfulServiceResponse(User involvedUser) {
    String userId = involvedUser.getURI();
    Log.info("Health Service Response for the user %s", getClass(), userId);
    ServiceResponse response = new ServiceResponse(CallStatus.succeeded);

    response.addOutput(new ProcessOutput(ProviderHealthPrescriptionService.OUTPUT_RECEIVED_MESSAGE,
        "The MedicationTreatment object received"));

    return response;
  }

  private void printMedicationTreatmentData(MedicationTreatment medicationTreatment) {
    Log.info("medicationTreatment.getPrescriptionId() = %s",
        HealthPrescriptionServiceProvider.class, medicationTreatment.getPrescriptionId());
    Log.info("medicationTreatment.getName() = %s",
        HealthPrescriptionServiceProvider.class, medicationTreatment.getName());
    Log.info("medicationTreatment.getDescription() = %s",
        HealthPrescriptionServiceProvider.class, medicationTreatment.getDescription());
    Log.info("medicationTreatment.getDoctorName() = %s",
        HealthPrescriptionServiceProvider.class, medicationTreatment.getDoctorName());
    Log.info("medicationTreatment.getStatus() = %s",
        HealthPrescriptionServiceProvider.class, medicationTreatment.getStatus());
    Log.info("medicationTreatment.getMedicationTreatmentStartDate() = %s",
        HealthPrescriptionServiceProvider.class, medicationTreatment.getMedicationTreatmentStartDate());
    Log.info("medicationTreatment.getTreatmentPlanning().getStartDate() = %s",
        HealthPrescriptionServiceProvider.class, medicationTreatment.getMedicationTreatmentStartDate());
    Log.info("medicationTreatment.getTreatmentPlanning().getEndDate() = %s",
        HealthPrescriptionServiceProvider.class, medicationTreatment.getMedicationTreatmentEndDate());
//   List<Medicine> medicines = medicationTreatment.getMedicine();
    Medicine medicine = medicationTreatment.getMedicine();
    Log.info("Printing medicine", HealthPrescriptionServiceProvider.class);
    temporaryMethodPrintMedicine(medicine);

  }

  private void temporaryMethodPrintMedicine(Medicine medicine) {
    Log.info("************** Medicine ***************************", HealthPrescriptionServiceProvider.class);
    Log.info("medicine.getMedicineId() = %s", HealthPrescriptionServiceProvider.class, medicine.getMedicineId());
    Log.info("medicine.getName() = %s", HealthPrescriptionServiceProvider.class, medicine.getName());
    Log.info("medicine.getDescription() = %s", HealthPrescriptionServiceProvider.class, medicine.getDescription());
    Log.info("medicine.getDays() = %s", HealthPrescriptionServiceProvider.class, medicine.getDays());
    Log.info("medicine.getMealRelation() = %s", HealthPrescriptionServiceProvider.class, medicine.getMealRelation());
    temporaryMethodPrintIntakes(medicine);
    Log.info("************** end Medicine ***************************", HealthPrescriptionServiceProvider.class);
  }

  private void temporaryMethodPrintIntakes(Medicine medicine) {

    List<Intake> intakeList = medicine.getIntakes();

    for (Intake intake : intakeList) {
      Log.info("  &&&&&&&&& Intake &&&&&&&&&&&&&&&&&&", HealthPrescriptionServiceProvider.class);
      Log.info("intake.getTime() = %s", HealthPrescriptionServiceProvider.class, intake.getTime());
      Log.info("intake.getDose() = %s", HealthPrescriptionServiceProvider.class, intake.getDose());
      Log.info("intake.getUnit() = %s", HealthPrescriptionServiceProvider.class, intake.getUnit());
      Log.info("  &&&&&&&&& end Intake &&&&&&&&&&&&&&&&&&", HealthPrescriptionServiceProvider.class);
    }

  }

}
