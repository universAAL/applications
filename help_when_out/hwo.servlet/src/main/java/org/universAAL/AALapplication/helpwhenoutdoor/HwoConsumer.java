/**
 * This class is intended to handle location context events
 * 
 * @author Arturo Domingo
 * @author <a href="mailto:geibsan@upvnet.upv.es">Gema Ibanez&064;UPVLC</a> 
 * @version %I%
 * @since 1.0
 * 
 */

package org.universAAL.AALapplication.helpwhenoutdoor;

// TODO: replace this with the full HwoConsumer class in hwo.mobile project

import java.util.Date;

import org.universAAL.AALapplication.helpwhenoutdoor.common.DataStorage;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextSubscriber;
//import org.universAAL.middleware.service.DefaultServiceCaller; //At this time we don't need to call services from this class
//import org.universAAL.middleware.service.ServiceCaller;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.ontology.location.position.Point;
import org.universAAL.ontology.profile.AssistedPerson;

class HwoConsumer extends ContextSubscriber {
	
	//private static ServiceCaller caller;	
	//private static final String HWO_CONSUMER_NAMESPACE = "http://ontology.universAAL.org/HwoConsumer.owl#";
	
	 private HelpWhenOutdoorDataService dataService;
	 private DataStorage dataStorage;
	
	public static ContextEventPattern[] getContextSubscriptionParams() {
		//We are interested in any events related to "Locator", class that will carry GPS' changes events.
		
		ContextEventPattern cep = new ContextEventPattern();
		//prevoius version was bugged
		MergedRestriction subjectRestriction = MergedRestriction
			.getAllValuesRestrictionWithCardinality(
				ContextEvent.PROP_RDF_SUBJECT,
				AssistedPerson.MY_URI, 1, 1);
		cep.addRestriction(subjectRestriction);  
		return new ContextEventPattern[] {cep};
		
	}  
	
	HwoConsumer(ModuleContext context,HelpWhenOutdoorDataService dataService){
		
		super(context,getContextSubscriptionParams()); //To be registered to the bus.
		//caller = new DefaultServiceCaller(context);
		this.dataService = dataService;
		dataStorage = DataStorage.getInstance();
		
	}
	
	
	// *****************************************************************
	// Listening to the bus 
	// *****************************************************************
	

	
	

	public void handleContextEvent(ContextEvent event) {
		
		System.out.println("recibido evento!");
	    LogUtils.logInfo(Activator.context, this.getClass(), "handleContextEvent",
		    new Object[] {
				" Recibido Evento de cambio de coordenadas.:\n" +
				"    Subject     ="+event.getSubjectURI()+"\n" +
				"    Subject type="+event.getSubjectTypeURI()+"\n" +
				"    Predicate   ="+event.getRDFPredicate()+"\n" +
				"    Object      ="+event.getRDFObject() },
				null);
	    
	 
	   Point p = (Point)event.getRDFObject();
	   
	   dataService.setLatitude(p.getX());
	   dataService.setLongitude(p.getY());
	   dataService.setHistoryEntry(new Date().getTime());
	   System.out.println("Las coordenadas del evento son "+ p.getX()+", "+p.getY()+ " Y el tiempo: "+ new Date().getTime());
	   System.out.println("enviando al Wandering detector");
	   String wd = Activator.wanderingdetector.isWandering(p, new Date().getTime());
	} 
	

	public void communicationChannelBroken() {
		
	}
}
