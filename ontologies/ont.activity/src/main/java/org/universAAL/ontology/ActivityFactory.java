
package org.universAAL.ontology;

import org.universAAL.middleware.rdf.Resource;
import org.universAAL.middleware.rdf.ResourceFactory;
import org.universAAL.ontology.activity.BathroomUsage;
import org.universAAL.ontology.activity.Cooking;
import org.universAAL.ontology.activity.DishWashing;
import org.universAAL.ontology.activity.Eating;
import org.universAAL.ontology.activity.Exercice;
import org.universAAL.ontology.activity.Laundry;
import org.universAAL.ontology.activity.MedicationIntake;
import org.universAAL.ontology.activity.Monitoring;
import org.universAAL.ontology.activity.RadioListening;
import org.universAAL.ontology.activity.Showering;
import org.universAAL.ontology.activity.Sleep;
import org.universAAL.ontology.activity.TVViewing;
import org.universAAL.ontology.activity.ToothBrushing;
import org.universAAL.ontology.activity.WakingUp;

public class ActivityFactory implements ResourceFactory {


  public Resource createInstance(String classURI, String instanceURI, int factoryIndex) {

	switch (factoryIndex) {
     case 0:
       return new Cooking(instanceURI);
     case 1:
       return new MedicationIntake(instanceURI);
     case 2:
       return new Sleep(instanceURI);
     case 3:
       return new Eating(instanceURI);
     case 4:
       return new TVViewing(instanceURI);
     case 5:
       return new RadioListening(instanceURI);
     case 6:
       return new Laundry(instanceURI);
     case 7:
       return new Monitoring(instanceURI);
     case 8:
       return new DishWashing(instanceURI);
     case 9:
       return new BathroomUsage(instanceURI);
     case 10:
       return new Exercice(instanceURI);
     case 11:
       return new Showering(instanceURI);
     case 12:
       return new ToothBrushing(instanceURI);
     case 13:
       return new WakingUp(instanceURI);

	}
	return null;
  }
}
