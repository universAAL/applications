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

package org.universAAL.ontology.impl;

import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.impl.ResourceFactoryImpl;
import org.universAAL.ontology.medMgr.CaregiverNotifier;
import org.universAAL.ontology.medMgr.CaregiverNotifierData;
import org.universAAL.ontology.medMgr.DispenserUpsideDown;
import org.universAAL.ontology.medMgr.DueIntake;
import org.universAAL.ontology.medMgr.Intake;
import org.universAAL.ontology.medMgr.MedicationTreatment;
import org.universAAL.ontology.medMgr.Medicine;
import org.universAAL.ontology.medMgr.MissedIntake;
import org.universAAL.ontology.medMgr.NewMedicationTreatmentNotifier;
import org.universAAL.ontology.medMgr.NewPrescription;
import org.universAAL.ontology.medMgr.Precaution;
import org.universAAL.ontology.medMgr.Time;

/**
 * @author George Fournadjiev
 */
public final class MedicationFactory extends ResourceFactoryImpl {

    public static final int PRECAUTION_FACTORY_INDEX = 0;
    public static final int TIME_FACTORY_INDEX = 1;
    public static final int MISSED_INTAKE_FACTORY_INDEX = 2;
    public static final int DUE_INTAKE_FACTORY_INDEX = 3;
    public static final int DISPENSER_UPSIDE_DOWN_FACTORY_INDEX = 4;
    public static final int INTAKE_FACTORY_INDEX = 5;
    public static final int MEDICINE_FACTORY_INDEX = 6;
    public static final int MEDICATION_TREATMENT_FACTORY_INDEX = 7;
    public static final int NEW_MEDICATION_TREATMENT_NOTIFIER_FACTORY_INDEX = 8;
    public static final int NEW_PRESCRIPTION_FACTORY_INDEX = 9;
    public static final int CAREGIVER_NOTIFIER_DATA_FACTORY_INDEX = 10;
    public static final int CAREGIVER_NOTIFIER_FACTORY_INDEX = 11;

    public Resource createInstance(String classURI, String instanceURI,
	    int factoryIndex) {

	if (factoryIndex == PRECAUTION_FACTORY_INDEX) {
	    return new Precaution(instanceURI);
	} else if (factoryIndex == TIME_FACTORY_INDEX) {
	    return new Time(instanceURI);
	} else if (factoryIndex == MISSED_INTAKE_FACTORY_INDEX) {
	    return new MissedIntake(instanceURI);
	} else if (factoryIndex == DUE_INTAKE_FACTORY_INDEX) {
	    return new DueIntake(instanceURI);
	} else if (factoryIndex == DISPENSER_UPSIDE_DOWN_FACTORY_INDEX) {
	    return new DispenserUpsideDown(instanceURI);
	} else if (factoryIndex == INTAKE_FACTORY_INDEX) {
	    return new Intake(instanceURI);
	} else if (factoryIndex == MEDICINE_FACTORY_INDEX) {
	    return new Medicine(instanceURI);
	} else if (factoryIndex == MEDICATION_TREATMENT_FACTORY_INDEX) {
	    return new MedicationTreatment(instanceURI);
	} else if (factoryIndex == NEW_MEDICATION_TREATMENT_NOTIFIER_FACTORY_INDEX) {
	    return new NewMedicationTreatmentNotifier(instanceURI);
	} else if (factoryIndex == NEW_PRESCRIPTION_FACTORY_INDEX) {
	    return new NewPrescription(instanceURI);
	} else if (factoryIndex == CAREGIVER_NOTIFIER_DATA_FACTORY_INDEX) {
	    return new CaregiverNotifierData(instanceURI);
	} else if (factoryIndex == CAREGIVER_NOTIFIER_FACTORY_INDEX) {
	    return new CaregiverNotifier(instanceURI);
	}

	return null;
    }
}
