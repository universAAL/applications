
package org.universAAL.ontology.activity;

import javax.xml.datatype.XMLGregorianCalendar;

import org.universAAL.middleware.owl.DataRepOntology;
import org.universAAL.middleware.owl.ManagedIndividual;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.owl.OntClassInfoSetup;
import org.universAAL.middleware.owl.Ontology;
import org.universAAL.middleware.owl.supply.AbsLocation;
import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.TypeMapper;
import org.universAAL.ontology.ActivityFactory;
import org.universAAL.ontology.location.LocationOntology;
import org.universAAL.ontology.profile.ProfileOntology;
import org.universAAL.ontology.profile.User;


/**
 * @author AAL Studio: UML2Java transformation
 */
public final class ActivityOntology extends Ontology {

  private static ActivityFactory factory = new ActivityFactory();
  public static final String NAMESPACE ="http://ontology.universAAL.org/Activity#";
  private static final String PROP_PERFORMS_ACTION = NAMESPACE + "performsAction";
	
  public ActivityOntology() {
    super(NAMESPACE);
  }

  public void create() {
    Resource r = getInfo();
    r.setResourceComment("");
    r.setResourceLabel("activity");
    addImport(DataRepOntology.NAMESPACE);
    addImport(LocationOntology.NAMESPACE);
    addImport(ProfileOntology.NAMESPACE);


    // ******* Declaration of regular classes of the ontology ******* //
    OntClassInfoSetup oci_Cooking = createNewOntClassInfo(Cooking.MY_URI, factory, 0);
    OntClassInfoSetup oci_MedicationIntake = createNewOntClassInfo(MedicationIntake.MY_URI, factory, 1);
    OntClassInfoSetup oci_PersonalHygeneActivity = createNewAbstractOntClassInfo(PersonalHygeneActivity.MY_URI);
    OntClassInfoSetup oci_Activity = createNewAbstractOntClassInfo(Activity.MY_URI);
    OntClassInfoSetup oci_Sleep = createNewOntClassInfo(Sleep.MY_URI, factory, 2);
    OntClassInfoSetup oci_LeisureActivity = createNewAbstractOntClassInfo(LeisureActivity.MY_URI);
    OntClassInfoSetup oci_Eating = createNewOntClassInfo(Eating.MY_URI, factory, 3);
    OntClassInfoSetup oci_HouseHoldActivity = createNewAbstractOntClassInfo(HouseHoldActivity.MY_URI);
    OntClassInfoSetup oci_TVViewing = createNewOntClassInfo(TVViewing.MY_URI, factory, 4);
    OntClassInfoSetup oci_RadioListening = createNewOntClassInfo(RadioListening.MY_URI, factory, 5);
    OntClassInfoSetup oci_IndoorActivity = createNewAbstractOntClassInfo(IndoorActivity.MY_URI);
    OntClassInfoSetup oci_Laundry = createNewOntClassInfo(Laundry.MY_URI, factory, 6);
    OntClassInfoSetup oci_MedicalComplianceActivity = createNewAbstractOntClassInfo(MedicalComplianceActivity.MY_URI);
    OntClassInfoSetup oci_Monitoring = createNewOntClassInfo(Monitoring.MY_URI, factory, 7);
    OntClassInfoSetup oci_DishWashing = createNewOntClassInfo(DishWashing.MY_URI, factory, 8);
    OntClassInfoSetup oci_BathroomUsage = createNewOntClassInfo(BathroomUsage.MY_URI, factory, 9);
    OntClassInfoSetup oci_Exercice = createNewOntClassInfo(Exercice.MY_URI, factory, 10);
    OntClassInfoSetup oci_OutdoorActivity = createNewAbstractOntClassInfo(OutdoorActivity.MY_URI);
    OntClassInfoSetup oci_Showering = createNewOntClassInfo(Showering.MY_URI, factory, 11);
    OntClassInfoSetup oci_ToothBrushing = createNewOntClassInfo(ToothBrushing.MY_URI, factory, 12);
    OntClassInfoSetup oci_WakingUp = createNewOntClassInfo(WakingUp.MY_URI, factory, 13);


    // ******* Add content to regular classes of the ontology ******* //
    oci_Cooking.setResourceComment("");
    oci_Cooking.setResourceLabel("Cooking");
    oci_Cooking.addSuperClass(HouseHoldActivity.MY_URI); 
    oci_MedicationIntake.setResourceComment("");
    oci_MedicationIntake.setResourceLabel("MedicationIntake");
    oci_MedicationIntake.addSuperClass(MedicalComplianceActivity.MY_URI); 
    oci_PersonalHygeneActivity.setResourceComment("");
    oci_PersonalHygeneActivity.setResourceLabel("PersonalHygeneActivity");
    oci_PersonalHygeneActivity.addSuperClass(Activity.MY_URI); 
    oci_Activity.setResourceComment("");
    oci_Activity.setResourceLabel("Activity");
    oci_Activity.addSuperClass(ManagedIndividual.MY_URI); 
    oci_Activity.addDatatypeProperty(Activity.PROP_STOP_DATE).setFunctional();
    oci_Activity.addRestriction(MergedRestriction.getAllValuesRestrictionWithCardinality(
    		Activity.PROP_STOP_DATE, TypeMapper.getDatatypeURI(XMLGregorianCalendar.class), 0, 1));
    
    oci_Activity.addDatatypeProperty(Activity.PROP_START_DATE).setFunctional();
    oci_Activity.addRestriction(MergedRestriction.getAllValuesRestrictionWithCardinality(
    		Activity.PROP_START_DATE,TypeMapper.getDatatypeURI(XMLGregorianCalendar.class), 1, 1));
    
    oci_Activity.addDatatypeProperty(Activity.PROP_NAME).addEquivalentProperty(Activity.PROP_RDFS_LABEL);
   
    oci_Activity.addDatatypeProperty(Activity.PROP_LOCATION).setFunctional();
    oci_Activity.addRestriction(MergedRestriction.getAllValuesRestrictionWithCardinality(
    		Activity.PROP_LOCATION, AbsLocation.MY_URI, 0, 1));

    
    oci_Sleep.setResourceComment("");
    oci_Sleep.setResourceLabel("Sleep");
    oci_Sleep.addSuperClass(PersonalHygeneActivity.MY_URI); 
    oci_LeisureActivity.setResourceComment("");
    oci_LeisureActivity.setResourceLabel("LeisureActivity");
    oci_LeisureActivity.addSuperClass(Activity.MY_URI); 
    oci_Eating.setResourceComment("");
    oci_Eating.setResourceLabel("Eating");
    oci_Eating.addSuperClass(MedicalComplianceActivity.MY_URI); 
    oci_HouseHoldActivity.setResourceComment("");
    oci_HouseHoldActivity.setResourceLabel("HouseHoldActivity");
    oci_HouseHoldActivity.addSuperClass(Activity.MY_URI); 
    oci_HouseHoldActivity.addSuperClass(IndoorActivity.MY_URI); 
    oci_TVViewing.setResourceComment("");
    oci_TVViewing.setResourceLabel("TVViewing");
    oci_TVViewing.addSuperClass(LeisureActivity.MY_URI); 
    oci_TVViewing.addSuperClass(IndoorActivity.MY_URI); 
    oci_RadioListening.setResourceComment("");
    oci_RadioListening.setResourceLabel("RadioListening");
    oci_RadioListening.addSuperClass(LeisureActivity.MY_URI); 
    oci_RadioListening.addSuperClass(IndoorActivity.MY_URI); 
    oci_IndoorActivity.setResourceComment("");
    oci_IndoorActivity.setResourceLabel("IndoorActivity");
    oci_IndoorActivity.addSuperClass(Activity.MY_URI); 
    oci_Laundry.setResourceComment("");
    oci_Laundry.setResourceLabel("Laundry");
    oci_Laundry.addSuperClass(HouseHoldActivity.MY_URI); 
    oci_MedicalComplianceActivity.setResourceComment("");
    oci_MedicalComplianceActivity.setResourceLabel("MedicalComplianceActivity");
    oci_MedicalComplianceActivity.addSuperClass(Activity.MY_URI); 
    oci_Monitoring.setResourceComment("");
    oci_Monitoring.setResourceLabel("Monitoring");
    oci_Monitoring.addSuperClass(MedicalComplianceActivity.MY_URI); 
    oci_DishWashing.setResourceComment("");
    oci_DishWashing.setResourceLabel("DishWashing");
    oci_DishWashing.addSuperClass(HouseHoldActivity.MY_URI); 
    oci_BathroomUsage.setResourceComment("");
    oci_BathroomUsage.setResourceLabel("BathroomUsage");
    oci_BathroomUsage.addSuperClass(PersonalHygeneActivity.MY_URI); 
    oci_Exercice.setResourceComment("");
    oci_Exercice.setResourceLabel("Exercice");
    oci_Exercice.addSuperClass(MedicalComplianceActivity.MY_URI); 
    oci_OutdoorActivity.setResourceComment("");
    oci_OutdoorActivity.setResourceLabel("OutdoorActivity");
    oci_OutdoorActivity.addSuperClass(Activity.MY_URI); 
    oci_Showering.setResourceComment("");
    oci_Showering.setResourceLabel("Showering");
    oci_Showering.addSuperClass(PersonalHygeneActivity.MY_URI); 
    oci_ToothBrushing.setResourceComment("");
    oci_ToothBrushing.setResourceLabel("ToothBrushing");
    oci_ToothBrushing.addSuperClass(PersonalHygeneActivity.MY_URI); 
    oci_WakingUp.setResourceComment("");
    oci_WakingUp.setResourceLabel("WakingUp");
    oci_WakingUp.addSuperClass(PersonalHygeneActivity.MY_URI); 
    
    // ** Adding properties **//
    OntClassInfoSetup oci_user = extendExistingOntClassInfo(User.MY_URI);
    oci_user.addObjectProperty(PROP_PERFORMS_ACTION);
    oci_user.addRestriction(MergedRestriction.getAllValuesRestriction(PROP_PERFORMS_ACTION, Activity.MY_URI));
  }
}
