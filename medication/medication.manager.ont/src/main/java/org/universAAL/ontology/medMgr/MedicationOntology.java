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


package org.universAAL.ontology.medMgr;

import org.universAAL.middleware.owl.DataRepOntology;
import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.OntClassInfoSetup;
import org.universAAL.middleware.owl.Ontology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.service.owl.Service;
import org.universAAL.middleware.service.owl.ServiceBusOntology;
import org.universAAL.ontology.impl.MedicationFactory;
import org.universAAL.ontology.location.LocationOntology;
import org.universAAL.ontology.profile.ProfileOntology;
import org.universAAL.ontology.profile.User;
import org.universaal.ontology.health.owl.HealthOntology;
import org.universaal.ontology.health.owl.Treatment;

import javax.xml.datatype.XMLGregorianCalendar;

import static org.universAAL.ontology.impl.MedicationFactory.*;

/**
 * @author George Fournadjiev
 */

public final class MedicationOntology extends Ontology {

  private static final MedicationFactory FACTORY = new MedicationFactory();

  public static final String NAMESPACE = "http://ontology.universaal.org/Medication.owl#";

  public MedicationOntology() {
    super(NAMESPACE);
  }


  public void create() {
    Resource resource = getInfo();
    resource.setResourceComment("The medication ontology defining the precaution(warning) server response");
    resource.setResourceLabel("Medication");
    addImport(DataRepOntology.NAMESPACE);
    addImport(ServiceBusOntology.NAMESPACE);
    addImport(LocationOntology.NAMESPACE);
    addImport(ProfileOntology.NAMESPACE);
    addImport(HealthOntology.NAMESPACE);

    OntClassInfoSetup oci;

    // load Precaution
    oci = createNewOntClassInfo(Precaution.MY_URI, FACTORY, PRECAUTION_FACTORY_INDEX);
    oci.setResourceComment("The type of a Precaution");
    oci.setResourceLabel("Precaution");
    oci.addSuperClass(Service.MY_URI);
    oci.addDatatypeProperty(Precaution.PROP_SIDEEFFECT);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            Precaution.PROP_SIDEEFFECT,
            TypeMapper.getDatatypeURI(String.class), 0, 1));
    oci.addDatatypeProperty(Precaution.PROP_INCOMPLIANCE);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            Precaution.PROP_INCOMPLIANCE,
            TypeMapper.getDatatypeURI(String.class), 0, 1));

    // load Time
    oci = createNewOntClassInfo(Time.MY_URI, FACTORY, TIME_FACTORY_INDEX);
    oci.setResourceComment("The type of a Time");
    oci.setResourceLabel("Time");
    oci.addDatatypeProperty(Time.PROP_YEAR);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            Time.PROP_YEAR,
            TypeMapper.getDatatypeURI(Integer.class), 0, 1));
    oci.addDatatypeProperty(Time.PROP_MONTH);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            Time.PROP_MONTH,
            TypeMapper.getDatatypeURI(Integer.class), 0, 1));
    oci.addDatatypeProperty(Time.PROP_DAY);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            Time.PROP_DAY,
            TypeMapper.getDatatypeURI(Integer.class), 0, 1));
    oci.addDatatypeProperty(Time.PROP_HOUR);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            Time.PROP_HOUR,
            TypeMapper.getDatatypeURI(Integer.class), 0, 1));
    oci.addDatatypeProperty(Time.PROP_MINUTES);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            Time.PROP_MINUTES,
            TypeMapper.getDatatypeURI(Integer.class), 0, 1));

    // load MissedIntake
    oci = createNewOntClassInfo(MissedIntake.MY_URI, FACTORY, MISSED_INTAKE_FACTORY_INDEX);
    oci.setResourceComment("The type of a MissedIntake");
    oci.setResourceLabel("MissedIntake");
    oci.addObjectProperty(MissedIntake.PROP_TIME).setFunctional();
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            MissedIntake.PROP_TIME, Time.MY_URI, 1, 1));
    oci.addObjectProperty(MissedIntake.PROP_USER).setFunctional();
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            MissedIntake.PROP_USER, User.MY_URI, 1, 1));

    // load DueIntake
    oci = createNewOntClassInfo(DueIntake.MY_URI, FACTORY, DUE_INTAKE_FACTORY_INDEX);
    oci.setResourceComment("The type of a DueIntake");
    oci.setResourceLabel("DueIntake");
    oci.addDatatypeProperty(DueIntake.PROP_DEVICE_ID);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            DueIntake.PROP_DEVICE_ID, TypeMapper.getDatatypeURI(String.class), 1, 1));
    oci.addObjectProperty(DueIntake.PROP_TIME).setFunctional();
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            DueIntake.PROP_TIME, Time.MY_URI, 1, 1));

    // load DispenserUpsideDown
    oci = createNewOntClassInfo(DispenserUpsideDown.MY_URI, FACTORY, DISPENSER_UPSIDE_DOWN_FACTORY_INDEX);
    oci.setResourceComment("The type of a DispenserUpsideDown");
    oci.setResourceLabel("DispenserUpsideDown");
    oci.addDatatypeProperty(DispenserUpsideDown.PROP_DEVICE_ID);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            DispenserUpsideDown.PROP_DEVICE_ID, TypeMapper.getDatatypeURI(String.class), 1, 1));

    // load MealRelation
    oci = createNewAbstractOntClassInfo(MealRelation.MY_URI);
    oci.setResourceComment("The type MealRelation enumeration");
    oci.setResourceLabel("MealRelation");
    oci.addSuperClass(ManagedIndividual.MY_URI);
    oci.toEnumeration(new ManagedIndividual[]{MealRelation.MEAL_RELATION_BEFORE, MealRelation.MEAL_RELATION_WITH_MEAL,
        MealRelation.MEAL_RELATION_AFTER, MealRelation.MEAL_RELATION_ANY});

    // load IntakeUnit
    oci = createNewAbstractOntClassInfo(IntakeUnit.MY_URI);
    oci.setResourceComment("The type IntakeUnit enumeration");
    oci.setResourceLabel("IntakeUnit");
    oci.addSuperClass(ManagedIndividual.MY_URI);
    oci.toEnumeration(new ManagedIndividual[]{IntakeUnit.INTAKE_UNIT_PILL, IntakeUnit.INTAKE_UNIT_DROPS});

    // load Intake
    oci = extendExistingOntClassInfo(ManagedIndividual.MY_URI);
    oci = createNewOntClassInfo(Intake.MY_URI, FACTORY, INTAKE_FACTORY_INDEX);
    oci.addSuperClass(ManagedIndividual.MY_URI);
    oci.setResourceComment("The type of a Intake");
    oci.setResourceLabel("Intake");
    oci.addDatatypeProperty(Intake.PROP_DOSE);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            Intake.PROP_DOSE, TypeMapper.getDatatypeURI(Integer.class), 1, 1));
    oci.addDatatypeProperty(Intake.PROP_TIME);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            Intake.PROP_TIME, TypeMapper.getDatatypeURI(String.class), 1, 1));

    // load Medicine
    oci = extendExistingOntClassInfo(ManagedIndividual.MY_URI);
    oci = createNewOntClassInfo(Medicine.MY_URI, FACTORY, MEDICINE_FACTORY_INDEX);
    oci.addSuperClass(ManagedIndividual.MY_URI);
    oci.setResourceComment("The type of a Medicine");
    oci.setResourceLabel("Medicine");
    oci.addDatatypeProperty(Medicine.PROP_MEDICINE_ID);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            Medicine.PROP_MEDICINE_ID, TypeMapper.getDatatypeURI(Integer.class), 1, 1));
    oci.addDatatypeProperty(Medicine.PROP_NAME);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            Medicine.PROP_NAME, TypeMapper.getDatatypeURI(String.class), 1, 1));
    oci.addDatatypeProperty(Medicine.PROP_DAYS);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            Medicine.PROP_DAYS, TypeMapper.getDatatypeURI(Integer.class), 1, 1));
    oci.addDatatypeProperty(Medicine.PROP_DESCRIPTION);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            Medicine.PROP_DESCRIPTION, TypeMapper.getDatatypeURI(String.class), 1, 1));
    oci.addObjectProperty(Medicine.PROP_INTAKES);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestriction(Medicine.PROP_INTAKES, Intake.MY_URI));

    // load MedicationTreatment
    oci = extendExistingOntClassInfo(Treatment.MY_URI);
    oci = createNewOntClassInfo(MedicationTreatment.MY_URI, FACTORY, MEDICATION_TREATMENT_FACTORY_INDEX);
    oci.addSuperClass(Treatment.MY_URI);
    oci.setResourceComment("The type of a MedicationTreatment");
    oci.setResourceLabel("MedicationTreatment");
    oci.addDatatypeProperty(MedicationTreatment.PROP_PRESCRIPTION_ID);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            MedicationTreatment.PROP_PRESCRIPTION_ID, TypeMapper.getDatatypeURI(Integer.class), 1, 1));
    oci.addDatatypeProperty(MedicationTreatment.PROP_DOCTOR_NAME);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            MedicationTreatment.PROP_DOCTOR_NAME, TypeMapper.getDatatypeURI(String.class), 1, 1));
    oci.addDatatypeProperty(MedicationTreatment.PROP_MEDICATION_TREATMENT_START_DATE).setFunctional();
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(MedicationTreatment.PROP_MEDICATION_TREATMENT_START_DATE,
            TypeMapper.getDatatypeURI(XMLGregorianCalendar.class), 1, 1));
    oci.addObjectProperty(MedicationTreatment.PROP_MEDICINE);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(MedicationTreatment.PROP_MEDICINE, Medicine.MY_URI, 1, 1));

    // load NewMedicationTreatmentNotifier
    oci = createNewOntClassInfo(NewMedicationTreatmentNotifier.MY_URI, FACTORY, NEW_MEDICATION_TREATMENT_NOTIFIER_FACTORY_INDEX);
    oci.setResourceComment("The type of a NewMedicationTreatmentNotifier");
    oci.setResourceLabel("NewMedicationTreatmentNotifier");
    oci.addObjectProperty(NewMedicationTreatmentNotifier.PROP_MEDICATION_TREATMENT).setFunctional();
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            NewMedicationTreatmentNotifier.PROP_MEDICATION_TREATMENT, MedicationTreatment.MY_URI, 1, 1));
    oci.addDatatypeProperty(NewMedicationTreatmentNotifier.PROP_RECEIVED_MESSAGE);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            NewMedicationTreatmentNotifier.PROP_RECEIVED_MESSAGE, TypeMapper.getDatatypeURI(String.class), 1, 1));

    // load NewPrescription
    oci = createNewOntClassInfo(NewPrescription.MY_URI, FACTORY, NEW_PRESCRIPTION_FACTORY_INDEX);
    oci.setResourceComment("The type of a NewPrescription");
    oci.setResourceLabel("NewPrescription");
    oci.addObjectProperty(NewPrescription.PROP_MEDICATION_TREATMENTS);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestriction(
            NewPrescription.PROP_MEDICATION_TREATMENTS, MedicationTreatment.MY_URI));
    oci.addDatatypeProperty(NewMedicationTreatmentNotifier.PROP_RECEIVED_MESSAGE);
    oci.addRestriction(MergedRestriction
        .getAllValuesRestrictionWithCardinality(
            NewMedicationTreatmentNotifier.PROP_RECEIVED_MESSAGE, TypeMapper.getDatatypeURI(String.class), 1, 1));

  }
}
