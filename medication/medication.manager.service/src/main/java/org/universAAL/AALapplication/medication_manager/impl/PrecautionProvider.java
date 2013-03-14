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


package org.universAAL.AALapplication.medication_manager.impl;

import org.universAAL.AALapplication.medication_manager.persistence.layer.PersistentService;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.PersonDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.dao.TreatmentDao;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Medicine;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Person;
import org.universAAL.AALapplication.medication_manager.persistence.layer.entities.Treatment;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.ontology.medMgr.Precaution;
import org.universAAL.ontology.profile.User;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.universAAL.AALapplication.medication_manager.impl.Activator.*;

/**
 * @author George Fournadjiev
 */
public final class PrecautionProvider extends ServiceCallee {

  private final MyPrecautionDatabase myPrecaution;

  // this is just to prepare a standard error message for later use
  private static final ServiceResponse invalidInput = new ServiceResponse(
      CallStatus.serviceSpecificFailure);

  static {
    invalidInput.addOutput(new ProcessOutput(
        ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR, "Invalid input!"));
  }

  public PrecautionProvider(ModuleContext context) {
    super(context, ProviderPrecautionService.profiles);

    myPrecaution = new MyPrecautionDatabase();
  }

  public void communicationChannelBroken() {
    //Not implemented yet
  }

  public ServiceResponse handleCall(ServiceCall call) {
    String processURI = call.getProcessURI();

    Log.info("Received call %s", getClass(), processURI);

    User involvedUser = (User) call.getInvolvedUser();

    Log.info("involvedUser %s", getClass(), involvedUser);

    if (involvedUser == null) {
      return invalidInput;
    }

    if (processURI.startsWith(ProviderPrecautionService.SERVICE_GET_PRECAUTION)) {
      return getSuccessfulServiceResponse(involvedUser);
    }

    return invalidInput;
  }

  private ServiceResponse getSuccessfulServiceResponse(User involvedUser) {
    Log.info("Successful Service Response for the user %s", getClass(), involvedUser);
    return getPrecaution(involvedUser);
  }

  private ServiceResponse getPrecaution(User user) {
    ServiceResponse response = new ServiceResponse(CallStatus.succeeded);


    PersistentService persistentService = getPersistentService();
    PersonDao personDao = persistentService.getPersonDao();
    Person person = personDao.findPersonByPersonUri(user.getURI());

    Precaution[] precautions = getPrecautions(person.getId(), persistentService);

    Log.info("Found the following precaution: %s", getClass(), Arrays.asList(precautions));

    if (precautions == null || precautions.length != 2) {
      throw new MedicationManagerException("Wrong construction of the Precaution array");
    }

    response.addOutput(new ProcessOutput(ProviderPrecautionService.OUTPUT_PRECAUTION_SIDEEFFECT, precautions[0]));
    response.addOutput(new ProcessOutput(ProviderPrecautionService.OUTPUT_PRECAUTION_INCOMPLIANCE, precautions[1]));

    return response;
  }

  private Precaution[] getPrecautions(int personId, PersistentService persistentService) {
    TreatmentDao treatmentDao = persistentService.getTreatmentDao();
    List<Treatment> treatments = treatmentDao.getByPersonAndActive(personId);

    Map<Medicine, String> sideeffectMap = new LinkedHashMap<Medicine, String>();
    Map<Medicine, String> incompliancesMap = new LinkedHashMap<Medicine, String>();


    for (int i = 0; i < treatments.size(); i++) {
      Treatment treatment = treatments.get(i);
      Medicine medicine = treatment.getMedicine();
      String se = medicine.getMedicineSideEffects();
      if (se != null && !se.trim().isEmpty()) {
        sideeffectMap.put(medicine, se);
      }
      String in = medicine.getIncompliances();
      if (in != null && !in.trim().isEmpty()) {
        incompliancesMap.put(medicine, in);
      }
    }

    return getPrecautions(sideeffectMap, incompliancesMap);
  }

  private Precaution[] getPrecautions(Map<Medicine, String> sideeffectMap, Map<Medicine, String> incompliancesMap) {
    String sideeffect = getSideefect(sideeffectMap);
    Precaution precautionSideeffect = new Precaution();
    precautionSideeffect.setSideEffect(sideeffect);
    String incompliances = getIncompliances(incompliancesMap);
    Precaution precautionIncompliances = new Precaution();
    precautionIncompliances.setIncompliance(incompliances);

    return new Precaution[]{precautionSideeffect, precautionIncompliances};
  }

  private String getSideefect(Map<Medicine, String> sideeffectMap) {
    return getString(sideeffectMap, "sideeffects");
  }

  private String getString(Map<Medicine, String> map, String item) {
    StringBuffer sb = new StringBuffer();
    sb.append("\nListing " + item + "of the following medicines\n");

    if (map.isEmpty()) {
      sb.append("Missing treatments for that user, so no medicine to list " + item);
      return sb.toString();
    }

    Set<Medicine> medicines = map.keySet ();

    int count = 0;
    for (Medicine med : medicines) {
      count++;
      sb.append(count);
      sb.append(". ");
      sb.append(med.getMedicineName());
      sb.append(" - ");
      sb.append(map.get(med));
      sb.append('\n');
    }

    return sb.toString();
  }

  private String getIncompliances(Map<Medicine, String> incompliancesList) {
    return getString(incompliancesList, "incompliances");
  }


}
