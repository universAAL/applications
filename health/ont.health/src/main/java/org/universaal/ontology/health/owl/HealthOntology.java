/*******************************************************************************
 * Copyright 2012 UPM, http://www.upm.es - Universidad Politécnica de Madrid
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
package org.universaal.ontology.health.owl;

//import net.fortuna.ical4j.model.Date;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import org.universAAL.middleware.owl.DataRepOntology;
import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.OntClassInfoSetup;
import org.universAAL.middleware.owl.Ontology;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.middleware.service.owl.Service;
import org.universAAL.middleware.service.owl.ServiceBusOntology;
import org.universAAL.ontology.HealthProfileOntology;
import org.universAAL.ontology.ProfileOntology;
import org.universAAL.ontology.location.LocationOntology;
import org.universAAL.ontology.profile.AssistedPerson;
import org.universAAL.ontology.profile.AssistedPersonProfile;
import org.universAAL.ontology.profile.CaregiverProfile;
import org.universAAL.ontology.profile.health.Illness;
import org.universaal.ontology.health.HealthOntologyFactory;
import org.universaal.ontology.health.owl.services.EditTreatmentService;
import org.universaal.ontology.health.owl.services.HealthService;
import org.universaal.ontology.health.owl.services.ListPerformedSessionBetweenTimeStampsService;
import org.universaal.ontology.health.owl.services.ListPerformedSessionService;
import org.universaal.ontology.health.owl.services.ListTreatmentBetweenTimeStampsService;
import org.universaal.ontology.health.owl.services.ListTreatmentService;
import org.universaal.ontology.health.owl.services.NewTreatmentService;
import org.universaal.ontology.health.owl.services.PerformedSessionManagementService;
import org.universaal.ontology.health.owl.services.PlannedSessionManagementService;
import org.universaal.ontology.health.owl.services.RemoveTreatmentService;
import org.universaal.ontology.health.owl.services.SessionPerformedService;
import org.universaal.ontology.health.owl.services.TreatmentManagementService;
import org.universaal.ontology.healthmeasurement.owl.BloodPressure;
import org.universaal.ontology.healthmeasurement.owl.HealthMeasurement;
import org.universaal.ontology.healthmeasurement.owl.HealthMeasurementOntology;
import org.universaal.ontology.healthmeasurement.owl.HeartRate;
import org.universaal.ontology.healthmeasurement.owl.PersonWeight;

/**
 * @author AAL Studio 
 */
public final class HealthOntology extends Ontology {

  private static HealthOntologyFactory factory = new HealthOntologyFactory();
  public static final String NAMESPACE ="http://health.ontology.universaal.org/HealthOntology#";
  
  public static final String PROP_HAS_HEALTH_PROFILE = HealthOntology.NAMESPACE
  + "hasHealthProfile";
  public static final String PROP_PRESCRIBES_TREATMENT = HealthOntology.NAMESPACE
  + "prescribesTreatment";
  
  public HealthOntology() {
    super(NAMESPACE);
  }

  public void create() {
    Resource r = getInfo();
    r.setResourceComment("The ontology defining the health service, based on the treatment concept.");
    r.setResourceLabel("HealthOntology");
    addImport(DataRepOntology.NAMESPACE);
    addImport(ServiceBusOntology.NAMESPACE);
    addImport(LocationOntology.NAMESPACE);
    addImport(HealthMeasurementOntology.NAMESPACE);
    addImport(ProfileOntology.NAMESPACE);
	addImport(HealthProfileOntology.NAMESPACE);	
   
	OntClassInfoSetup oci;


    // ******* Enumeration classes of the ontology ******* //

    // load StatusType
    oci = createNewAbstractOntClassInfo(StatusType.MY_URI);
    oci.setResourceComment("This class defines the types of possible status of the treatment: planned, actived, finished or cancelled.");
    oci.setResourceLabel("StatusType");
    oci.toEnumeration(new ManagedIndividual[] {
       StatusType.planned, StatusType.actived, StatusType.finished, StatusType.cancelled });

    // load MotivationalStatusType
    oci = createNewAbstractOntClassInfo(MotivationalStatusType.MY_URI);
    oci.setResourceComment("This class defines the types of motivational status of the treatment: precontemplation, contemplation, action or maintenance.");
    oci.setResourceLabel("MotivationalStatusType");
    oci.toEnumeration(new ManagedIndividual[] {
       MotivationalStatusType.precontemplation, MotivationalStatusType.contemplation, MotivationalStatusType.action, MotivationalStatusType.maintenance });


    // ******* Regular classes of the ontology ******* //

    //load PlannedSession 
    oci = createNewOntClassInfo(PlannedSession.MY_URI, factory, 4);
    oci.setResourceComment("A planned session is a session that has been schedule so as to perform an assigned activity.");
    oci.setResourceLabel("PlannedSession");
    
    oci.addDatatypeProperty(PlannedSession.PROP_DATE).setFunctional();
    oci.addRestriction(MergedRestriction 
    		.getAllValuesRestrictionWithCardinality(PlannedSession.PROP_DATE, 
    		TypeMapper.getDatatypeURI(XMLGregorianCalendar.class), 1, 1));
    
    oci.addDatatypeProperty(PlannedSession.PROP_START_TIME).setFunctional();
    oci.addRestriction(MergedRestriction 
    		.getAllValuesRestrictionWithCardinality(PlannedSession.PROP_START_TIME, 
    		TypeMapper.getDatatypeURI(XMLGregorianCalendar.class), 1, 1));
    
    oci.addDatatypeProperty(PlannedSession.PROP_DURATION).setFunctional();
    oci.addRestriction(MergedRestriction.getCardinalityRestriction(PlannedSession.PROP_DURATION, 1, 1));
    oci.addRestriction(MergedRestriction 
    		.getAllValuesRestrictionWithCardinality(PlannedSession.PROP_DURATION, 
    		TypeMapper.getDatatypeURI(Duration.class), 1, 1));
    
    oci.addDatatypeProperty(PlannedSession.PROP_DETAILS);
    oci.addRestriction(MergedRestriction
      .getAllValuesRestrictionWithCardinality(PlannedSession.PROP_DETAILS, 
      TypeMapper.getDatatypeURI(String.class), 1, 1));
    
    oci.addObjectProperty(PlannedSession.PROP_PERFORMED).setFunctional();
    oci.addRestriction(MergedRestriction 
    		.getAllValuesRestrictionWithCardinality(PlannedSession.PROP_PERFORMED, 
    		PerformedSession.MY_URI, 0, 1));
    
    //load PerformedSession
    oci = createNewOntClassInfo(PerformedSession.MY_URI, factory, 13);
    oci.setResourceComment("A performed session is a session that has been performed.");
    oci.setResourceLabel("PerformedSession");
    
    oci.addDatatypeProperty(PerformedSession.PROP_DATE).setFunctional();
    oci.addRestriction(MergedRestriction 
    		.getAllValuesRestrictionWithCardinality(PerformedSession.PROP_DATE, 
    		TypeMapper.getDatatypeURI(XMLGregorianCalendar.class), 1, 1));
    
    oci.addDatatypeProperty(PerformedSession.PROP_START_TIME).setFunctional();
    oci.addRestriction(MergedRestriction 
    		.getAllValuesRestrictionWithCardinality(PerformedSession.PROP_START_TIME, 
    		TypeMapper.getDatatypeURI(XMLGregorianCalendar.class), 1, 1));
    
    oci.addDatatypeProperty(PerformedSession.PROP_DURATION).setFunctional();
    oci.addRestriction(MergedRestriction.getCardinalityRestriction(PerformedSession.PROP_DURATION, 1, 1));
    oci.addRestriction(MergedRestriction 
    		.getAllValuesRestrictionWithCardinality(PerformedSession.PROP_DURATION, 
    		TypeMapper.getDatatypeURI(Duration.class), 1, 1));
    
  //load TreatmentPlanning 
    oci = createNewOntClassInfo(TreatmentPlanning.MY_URI, factory, 9);
    oci.setResourceComment("This class describes the planning of the sessions that composes the treatment.");
    oci.setResourceLabel("TreatmentPlanning");
    
    oci.addDatatypeProperty(TreatmentPlanning.PROP_RECURRENCE).setFunctional();
    oci.addRestriction(MergedRestriction
      .getAllValuesRestrictionWithCardinality(TreatmentPlanning.PROP_RECURRENCE, 
      TypeMapper.getDatatypeURI(String.class), 0, 1));
    
    oci.addObjectProperty(TreatmentPlanning.PROP_START_DATE).setFunctional();
    oci.addRestriction(MergedRestriction 
    		.getAllValuesRestrictionWithCardinality(TreatmentPlanning.PROP_START_DATE, 
    		TypeMapper.getDatatypeURI(XMLGregorianCalendar.class), 1, 1));
    
    oci.addObjectProperty(TreatmentPlanning.PROP_END_DATE).setFunctional();
    oci.addRestriction(MergedRestriction 
    		.getAllValuesRestrictionWithCardinality(TreatmentPlanning.PROP_END_DATE, 
    		TypeMapper.getDatatypeURI(XMLGregorianCalendar.class), 0, 1));
    
    oci.addObjectProperty(TreatmentPlanning.PROP_HAS_PLANNED_SESSIONS).setFunctional();
  	oci.addRestriction(MergedRestriction.getAllValuesRestriction(TreatmentPlanning.PROP_HAS_PLANNED_SESSIONS,  
  			PlannedSession.MY_URI));
  	
    oci.addDatatypeProperty(TreatmentPlanning.PROP_DESCRIPTION);
    oci.addRestriction(MergedRestriction
      .getAllValuesRestrictionWithCardinality(TreatmentPlanning.PROP_DESCRIPTION, 
      TypeMapper.getDatatypeURI(String.class), 0, 1));
    
   
    //load PerformedSession
    oci = createNewOntClassInfo(PerformedSession.MY_URI, factory, 13);
    oci.setResourceComment("This class describes a session that has been performed.");
    oci.setResourceLabel("PerformedSession");
    
    
    //load PerformedMeasurementSession
    oci = createNewOntClassInfo(PerformedMeasurementSession.MY_URI, factory, 5);
    oci.setResourceComment("This class describes a session with a health measurement involved that has been performed.");
    oci.setResourceLabel("PerformedSession");
    
    oci.addObjectProperty(PerformedMeasurementSession.PROP_HAS_HEALTH_MEASUREMENT).setFunctional();
    oci.addRestriction(MergedRestriction
    	      .getAllValuesRestrictionWithCardinality(PerformedMeasurementSession.PROP_HAS_HEALTH_MEASUREMENT, 
    	      HealthMeasurement.MY_URI, 1, 1));
    
  //load Privacy 
    /*
    oci = createNewOntClassInfo(Privacy.MY_URI, factory, 12);
    oci.setResourceComment("This class describes the concept of privacy, that is who has access to the treatment information of a especific assisted person.");
    oci.setResourceLabel("Privacy");
    */
    
    
    //load MeasurementRequirements 
    oci = createNewOntClassInfo(MeasurementRequirements.MY_URI, factory, 2);
    oci.setResourceComment("This concept involves all the measurement types that are required in a treatment.");
    oci.setResourceLabel("MeasurementRequirements");
    
    oci.addDatatypeProperty(MeasurementRequirements.PROP_MAX_VALUE_ALLOWED);
    oci.addRestriction(MergedRestriction
      .getAllValuesRestrictionWithCardinality(MeasurementRequirements.PROP_MAX_VALUE_ALLOWED, 
      TypeMapper.getDatatypeURI(Integer.class), 0, 1));
    
    oci.addDatatypeProperty(MeasurementRequirements.PROP_MIN_VALUE_ALLOWED);
    oci.addRestriction(MergedRestriction
      .getAllValuesRestrictionWithCardinality(MeasurementRequirements.PROP_MIN_VALUE_ALLOWED, 
      TypeMapper.getDatatypeURI(Integer.class), 0, 1));
    
    
  //load WeightRequirement 
    oci = createNewOntClassInfo(WeightRequirement.MY_URI, factory, 3);
    oci.setResourceComment("This class describes a weight measurement requirement.");
    oci.setResourceLabel("WeightMeasurement");
    oci.addSuperClass(MeasurementRequirements.MY_URI); 

    oci.addDatatypeProperty(WeightRequirement.PROP_MIN_VALUE_ALLOWED)
    .addSuperProperty(MeasurementRequirements.PROP_MIN_VALUE_ALLOWED);
    oci.addRestriction(MergedRestriction
    		.getAllValuesRestrictionWithCardinality(WeightRequirement.PROP_MIN_VALUE_ALLOWED, 
    				PersonWeight.MY_URI, 1, 1));

    oci.addDatatypeProperty(WeightRequirement.PROP_MAX_VALUE_ALLOWED)
    .addSuperProperty(MeasurementRequirements.PROP_MAX_VALUE_ALLOWED);
    oci.addRestriction(MergedRestriction
    		.getAllValuesRestrictionWithCardinality(WeightRequirement.PROP_MAX_VALUE_ALLOWED, 
    				PersonWeight.MY_URI, 1, 1));
    
    
    //load BloodPressureRequirement 
    oci = createNewOntClassInfo(BloodPressureRequirement.MY_URI, factory, 0);
    oci.setResourceComment("This class describes a blood pressure measurement requirement.");
    oci.setResourceLabel("BloodPressureMeasurement");
    oci.addSuperClass(MeasurementRequirements.MY_URI); 
    
    
    //load DiastolicBloodPressureRequirement
    oci = createNewOntClassInfo(DiastolicBloodPressureRequirement.MY_URI, factory, 14);
    oci.setResourceComment("This class describes a diastolic blood pressure measurement requirement.");
    oci.setResourceLabel("DiastolicBloodPressureRequirement");
    oci.addSuperClass(BloodPressureRequirement.MY_URI);
    
    
    oci.addDatatypeProperty(DiastolicBloodPressureRequirement.PROP_MAX_VALUE_ALLOWED)
    .addSuperProperty(MeasurementRequirements.PROP_MAX_VALUE_ALLOWED);
    oci.addRestriction(MergedRestriction
    		.getAllValuesRestrictionWithCardinality(DiastolicBloodPressureRequirement.PROP_MAX_VALUE_ALLOWED, 
    				BloodPressure.MY_URI, 1, 1));
    
    oci.addDatatypeProperty(DiastolicBloodPressureRequirement.PROP_MIN_VALUE_ALLOWED)
    .addSuperProperty(MeasurementRequirements.PROP_MIN_VALUE_ALLOWED);
    oci.addRestriction(MergedRestriction
    		.getAllValuesRestrictionWithCardinality(DiastolicBloodPressureRequirement.PROP_MIN_VALUE_ALLOWED, 
    				BloodPressure.MY_URI, 1, 1));
    
    //load SystolicBloodPressureRequirement
    oci = createNewOntClassInfo(SystolicBloodPressureRequirement.MY_URI, factory, 15);
    oci.setResourceComment("This class describes a systolic blood pressure measurement requirement.");
    oci.setResourceLabel("SystolicBloodPressureRequirement");
    oci.addSuperClass(BloodPressureRequirement.MY_URI);
    
    oci.addDatatypeProperty(SystolicBloodPressureRequirement.PROP_MIN_VALUE_ALLOWED)
    .addSuperProperty(MeasurementRequirements.PROP_MIN_VALUE_ALLOWED);
    oci.addRestriction(MergedRestriction
    		.getAllValuesRestrictionWithCardinality(SystolicBloodPressureRequirement.PROP_MIN_VALUE_ALLOWED, 
    				BloodPressure.MY_URI, 1, 1));
    oci.addDatatypeProperty(SystolicBloodPressureRequirement.PROP_MAX_VALUE_ALLOWED)
    .addSuperProperty(MeasurementRequirements.PROP_MAX_VALUE_ALLOWED);
    oci.addRestriction(MergedRestriction
    		.getAllValuesRestrictionWithCardinality(SystolicBloodPressureRequirement.PROP_MAX_VALUE_ALLOWED, 
    				BloodPressure.MY_URI, 1, 1));
    
    //load HeartRateRequirement 
    oci = createNewOntClassInfo(HeartRateRequirement.MY_URI, factory, 6);
    oci.setResourceComment("This class describes a heart rate measurement requirement.");
    oci.setResourceLabel("HeartRateRequirement");
    oci.addSuperClass(MeasurementRequirements.MY_URI); 
    
    //load ActivityHeartRateRequirement
    oci = createNewOntClassInfo(ActivityHeartRateRequirement.MY_URI, factory, 16);
    oci.setResourceComment("This class describes a heart rate measurement requirement during the activity.");
    oci.setResourceLabel("HeartRateRequirement");
    oci.addSuperClass(HeartRateRequirement.MY_URI); 
    
    oci.addDatatypeProperty(ActivityHeartRateRequirement.PROP_MIN_VALUE_ALLOWED)
    .addSuperProperty(MeasurementRequirements.PROP_MIN_VALUE_ALLOWED);
    oci.addRestriction(MergedRestriction
    		.getAllValuesRestrictionWithCardinality(ActivityHeartRateRequirement.PROP_MIN_VALUE_ALLOWED, 
    				HeartRate.MY_URI, 1, 1));

    oci.addDatatypeProperty(ActivityHeartRateRequirement.PROP_MAX_VALUE_ALLOWED)
    .addSuperProperty(MeasurementRequirements.PROP_MAX_VALUE_ALLOWED);
    oci.addRestriction(MergedRestriction
    		.getAllValuesRestrictionWithCardinality(ActivityHeartRateRequirement.PROP_MAX_VALUE_ALLOWED, 
    				HeartRate.MY_URI, 1, 1));
    
  //load ReposeHeartRateRequirement
    oci = createNewOntClassInfo(ReposeHeartRateRequirement.MY_URI, factory, 17);
    oci.setResourceComment("This class describes a reposed heart rate measurement requirement.");
    oci.setResourceLabel("HeartRateMeasurement");
    oci.addSuperClass(HeartRateRequirement.MY_URI); 
    
    
    oci.addDatatypeProperty(ReposeHeartRateRequirement.PROP_MIN_VALUE_ALLOWED)
    .addSuperProperty(MeasurementRequirements.PROP_MIN_VALUE_ALLOWED);
    oci.addRestriction(MergedRestriction
    		.getAllValuesRestrictionWithCardinality(ReposeHeartRateRequirement.PROP_MIN_VALUE_ALLOWED, 
    				HeartRate.MY_URI, 1, 1));

    oci.addDatatypeProperty(ReposeHeartRateRequirement.PROP_MAX_VALUE_ALLOWED)
    .addSuperProperty(MeasurementRequirements.PROP_MAX_VALUE_ALLOWED);
    oci.addRestriction(MergedRestriction
    		.getAllValuesRestrictionWithCardinality(ReposeHeartRateRequirement.PROP_MAX_VALUE_ALLOWED, 
    				HeartRate.MY_URI, 1, 1));

    //load Caregiver
    oci = createNewOntClassInfo(Caregiver.MY_URI, factory, 8);
    oci.setResourceComment("This class describes the caregiver who has set the treatment.");
    oci.setResourceLabel("Caregiver");
    oci.addSuperClass(CaregiverProfile.MY_URI);

    
    //load Treatment 
    oci = createNewAbstractOntClassInfo(Treatment.MY_URI);
    oci.setResourceComment("This class describes the concept of treatment, that is the set of activities that an assisted person is suggested to do.");
    oci.setResourceLabel("Treatment");
    oci.addSuperClass(ManagedIndividual.MY_URI); 
    
    oci.addDatatypeProperty(Treatment.PROP_NAME);
    oci.addRestriction(MergedRestriction
      .getAllValuesRestrictionWithCardinality(Treatment.PROP_NAME, 
      TypeMapper.getDatatypeURI(String.class), 1, 1));
    
    oci.addDatatypeProperty(Treatment.PROP_COMPLETENESS);
    oci.addRestriction(MergedRestriction
      .getAllValuesRestrictionWithCardinality(Treatment.PROP_COMPLETENESS, 
      TypeMapper.getDatatypeURI(Float.class), 1, 1));
    
    oci.addDatatypeProperty(Treatment.PROP_STATUS);
    oci.addRestriction(MergedRestriction
      .getAllValuesRestrictionWithCardinality(Treatment.PROP_STATUS, 
      StatusType.MY_URI, 1, 1));
    
    oci.addObjectProperty(Treatment.PROP_ILLNESS).setFunctional();
    oci.addRestriction(MergedRestriction
      .getAllValuesRestrictionWithCardinality(Treatment.PROP_ILLNESS, 
      Illness.MY_URI, 1, 1));
    
    oci.addObjectProperty(Treatment.PROP_HAS_TREATMENT_PLANNING).setFunctional();
    oci.addRestriction(MergedRestriction
      .getAllValuesRestrictionWithCardinality(Treatment.PROP_HAS_TREATMENT_PLANNING, 
      TreatmentPlanning.MY_URI, 1, 1));
    
    oci.addObjectProperty(Treatment.PROP_IS_PRESCRIBED_BY_CAREGIVER).setFunctional();
    oci.addRestriction(MergedRestriction
      .getAllValuesRestrictionWithCardinality(Treatment.PROP_IS_PRESCRIBED_BY_CAREGIVER, 
    		  Caregiver.MY_URI, 1, 1));
    
    oci.addDatatypeProperty(Treatment.PROP_MOTIVATIONAL_STATUS);
    oci.addRestriction(MergedRestriction
      .getAllValuesRestrictionWithCardinality(Treatment.PROP_MOTIVATIONAL_STATUS, 
      MotivationalStatusType.MY_URI, 1, 1));
    /*
    oci.addObjectProperty(Treatment.PROP_HAS_PRIVACY).setFunctional();
    oci.addRestriction(MergedRestriction
      .getAllValuesRestrictionWithCardinality(Treatment.PROP_HAS_PRIVACY, 
      Privacy.MY_URI, 1, 1));
    */
    oci.addDatatypeProperty(Treatment.PROP_DESCRIPTION);
    oci.addRestriction(MergedRestriction
      .getAllValuesRestrictionWithCardinality(Treatment.PROP_DESCRIPTION, 
      TypeMapper.getDatatypeURI(String.class), 1, 1));
    
    oci.addObjectProperty(Treatment.PROP_MEASUREMENT_REQUIREMENTS);
    oci.addRestriction(MergedRestriction
      .getAllValuesRestrictionWithCardinality(Treatment.PROP_MEASUREMENT_REQUIREMENTS, 
      MeasurementRequirements.MY_URI, 0, 1));
    
    oci.addObjectProperty(Treatment.PROP_HAS_PERFORMED_SESSION);
    oci.addRestriction(MergedRestriction
      .getAllValuesRestriction(Treatment.PROP_HAS_PERFORMED_SESSION, 
      PerformedSession.MY_URI));
    
  //load HealthyHabitsAdoption 
    oci = createNewAbstractOntClassInfo(HealthyHabitsAdoption.MY_URI);
    oci.setResourceComment("This concept describes all the activities that can be considered as healthy habits");
    oci.setResourceLabel("HealthyHabitsAdoption");
    oci.addSuperClass(Treatment.MY_URI); 
    
    //load TakeMeasurementActivity 
    oci = createNewOntClassInfo(TakeMeasurementActivity.MY_URI, factory, 7);
    oci.setResourceComment("This is a treatment that consists of taking measurements.");
    oci.setResourceLabel("TakeMeasurementActivity");
    oci.addSuperClass(Treatment.MY_URI); 
    oci.addObjectProperty(TakeMeasurementActivity.PROP_HAS_MEASUREMENT_REQUIREMENTS).setFunctional();
    oci.addRestriction(MergedRestriction
      .getAllValuesRestrictionWithCardinality(TakeMeasurementActivity.PROP_HAS_MEASUREMENT_REQUIREMENTS, 
      MeasurementRequirements.MY_URI, 0, 1));
    
    
    //load PhysicalActivity 
    oci = createNewOntClassInfo(PhysicalActivity.MY_URI, factory, 1);
    oci.setResourceComment("This concept describes a physical activity.");
    oci.setResourceLabel("PhysicalActivity");
    oci.addSuperClass(HealthyHabitsAdoption.MY_URI); 
    oci.addObjectProperty(PhysicalActivity.PROP_HAS_ASSOCIATED_MEASUREMENT).setFunctional();
    oci.addRestriction(MergedRestriction
      .getAllValuesRestrictionWithCardinality(PhysicalActivity.PROP_HAS_ASSOCIATED_MEASUREMENT, 
      TakeMeasurementActivity.MY_URI, 1, 1));
    
    
    // extension for AssistedPersonProfile: an assisted person can contain a health profile, where treatments are specified.
    oci=extendExistingOntClassInfo(AssistedPersonProfile.MY_URI);
    oci.addObjectProperty(PROP_HAS_HEALTH_PROFILE);
    oci.addRestriction(MergedRestriction
    	      .getAllValuesRestrictionWithCardinality(PROP_HAS_HEALTH_PROFILE, 
    	      HealthProfile.MY_URI, 0, 1));//NO ES LA MÍA, TIENE QUE SER LA DE CARSTEN
    
    
    // extension for Caregiver: a caregiver can prescribe treatments
    oci=extendExistingOntClassInfo(org.universAAL.ontology.profile.Caregiver.MY_URI);
    oci.addObjectProperty(PROP_PRESCRIBES_TREATMENT);
    oci.addRestriction(MergedRestriction
    	      .getAllValuesRestriction(PROP_PRESCRIBES_TREATMENT, 
    	      Treatment.MY_URI));
    


    /*
     * Services
     */

    //load HealthService
    oci = createNewOntClassInfo(HealthService.MY_URI, factory, 21);
    oci.setResourceComment("Description of the health service.");
    oci.setResourceLabel("HealthService");
    oci.addSuperClass(Service.MY_URI); 
    
    oci.addObjectProperty(HealthService.PROP_ASSISTED_USER);
    oci.addRestriction(MergedRestriction
    		.getAllValuesRestrictionWithCardinality(
    				HealthService.PROP_ASSISTED_USER, AssistedPerson.MY_URI, 1, 1));
    
    //load TreatmentManagementService
    oci = createNewOntClassInfo(TreatmentManagementService.MY_URI, factory, 18);
    oci.setResourceComment("Description of the treatment management service.");
    oci.setResourceLabel("TreatmentManagementService");
    oci.addSuperClass(HealthService.MY_URI); 

    oci.addObjectProperty(TreatmentManagementService.PROP_MANAGES_TREATMENT);
    oci.addRestriction(MergedRestriction.getAllValuesRestrictionWithCardinality(
    		TreatmentManagementService.PROP_MANAGES_TREATMENT,  Treatment.MY_URI, 1,1));
    
    //load NewTreatment
    oci = createNewOntClassInfo(NewTreatmentService.MY_URI, factory, 22);
    oci.setResourceComment("Description of the adding treatment service.");
    oci.setResourceLabel("NewTreatmentService");
    oci.addSuperClass(TreatmentManagementService.MY_URI);
    
    //load EditTreatment
    oci = createNewOntClassInfo(EditTreatmentService.MY_URI, factory, 23);
    oci.setResourceComment("Description of the editing treatment service.");
    oci.setResourceLabel("EditTreatmentService");
    oci.addSuperClass(TreatmentManagementService.MY_URI);
    
    //load RemoveTreatment
    oci = createNewOntClassInfo(RemoveTreatmentService.MY_URI, factory, 22);
    oci.setResourceComment("Description of the removing treatment service.");
    oci.setResourceLabel("NewTreatmentService");
    oci.addSuperClass(TreatmentManagementService.MY_URI);
    
    //load ListTreatment
    oci = createNewOntClassInfo(ListTreatmentService.MY_URI, factory, 25);
    oci.setResourceComment("Description of the treatment listing service.");
    oci.setResourceLabel("ListTreatmentService");
    oci.addSuperClass(TreatmentManagementService.MY_URI);
    
    oci.addObjectProperty(ListTreatmentService.PROP_LISTS_TREATMENTS);
    oci.addRestriction(MergedRestriction.getAllValuesRestriction(
    		ListTreatmentService.PROP_LISTS_TREATMENTS,  Treatment.MY_URI));
    
    //load ListTreatmentBetweenTimeStamps
    oci = createNewOntClassInfo(ListTreatmentBetweenTimeStampsService.MY_URI, factory, 26);
    oci.setResourceComment("Description of the treatment listing service.");
    oci.setResourceLabel("ListTreatmentBetweenTimeStampsService");
    oci.addSuperClass(ListTreatmentService.MY_URI);
    
    //load PlannedSessionManagementService
    
    oci = createNewOntClassInfo(PlannedSessionManagementService.MY_URI, factory, 19);
    oci.setResourceComment("Description of the planned session management service.");
    oci.setResourceLabel("PlannedSessionManagementService");
    oci.addSuperClass(HealthService.MY_URI); 

    oci.addObjectProperty(PlannedSessionManagementService.PROP_MANAGES_SESSION);
    oci.addRestriction(MergedRestriction.getAllValuesRestrictionWithCardinality(
    		PlannedSessionManagementService.PROP_MANAGES_SESSION, PlannedSession.MY_URI, 1,1));
    
    oci.addObjectProperty(PlannedSessionManagementService.PROP_LISTS_SESSIONS);
    oci.addRestriction(MergedRestriction.getAllValuesRestriction(
    		PlannedSessionManagementService.PROP_LISTS_SESSIONS, PlannedSession.MY_URI));    
    
    //load PerformedSessionManagementService
    oci = createNewOntClassInfo(PerformedSessionManagementService.MY_URI, factory, 20);
    oci.setResourceComment("Description of the performed session management service.");
    oci.setResourceLabel("PerformedSessionManagementService");
    oci.addSuperClass(HealthService.MY_URI); 

    oci.addObjectProperty(PerformedSessionManagementService.PROP_MANAGES_SESSION);
    oci.addRestriction(MergedRestriction.getAllValuesRestriction(
    		PerformedSessionManagementService.PROP_MANAGES_SESSION,  PlannedSession.MY_URI));
    
    // load ListPerformedSessionService
    oci = createNewOntClassInfo(ListPerformedSessionService.MY_URI, factory, 27);
    oci.setResourceComment("Service listing the performed sessions.");
    oci.setResourceLabel("ListPerformedSessionService");
    oci.addSuperClass(PerformedSessionManagementService.MY_URI);

    oci.addObjectProperty(ListPerformedSessionService.PROP_LISTS_PERFORMED_SESSIONS);
    oci.addRestriction(MergedRestriction.getAllValuesRestrictionWithCardinality(
    		ListPerformedSessionService.PROP_LISTS_PERFORMED_SESSIONS, PlannedSession.MY_URI,1,1));  
    
    // load ListPerformedSessionBetweenTimeStampsService
    oci = createNewOntClassInfo(ListPerformedSessionBetweenTimeStampsService.MY_URI, factory, 28);
    oci.setResourceComment("Service listing the performed sessions between 2 timestamps.");
    oci.setResourceLabel("ListPerformedBetweenTimeStampsSessionService");
    oci.addSuperClass(ListPerformedSessionService.MY_URI);
    
    // load SessionPerformedService
    oci = createNewOntClassInfo(SessionPerformedService.MY_URI, factory, 29);
    oci.setResourceComment("Service to add a performed sessions.");
    oci.setResourceLabel("SessionPerformedService");
    oci.addSuperClass(PerformedSessionManagementService.MY_URI);    
    
  }
}
