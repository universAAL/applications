package org.persona.service.helpwhenoutside;
// For testing purposes. This class simulates the change of position of the user by throwing GPS events to the context bus.
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.ContextEventPattern;
import org.universAAL.middleware.context.ContextPublisher;
import org.universAAL.middleware.context.DefaultContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.ontology.location.position.CoordinateSystem;
import org.universAAL.ontology.location.position.Point;
import org.universAAL.ontology.profile.AssistedPerson;





public class Walker {
	
	private HelpWhenOutsideDataService dataService;
	private List<Point> userpath;
	private int contador;
	Properties config;
	private ContextPublisher cp;
	
	
	private static final Logger log = LoggerFactory
			.getLogger(Walker.class);
	
	public Walker(Properties config, HelpWhenOutsideDataService dataService,ModuleContext context) {
		this.config = config;
		this.dataService = dataService;
		userpath =  new ArrayList<Point>();
		userpath.add(new Point(39.4592018,-0.3845417,CoordinateSystem.WGS84));
		userpath.add(new Point(39.4587724,-0.3856196,CoordinateSystem.WGS84));
		userpath.add(new Point(39.4536763,-0.3922401,CoordinateSystem.WGS84));
		contador=0;
		//ContextProvider info =  new ContextProvider("Walker");
		//info.setType(ContextProviderType.controller);             // every ContextProvider have a type that give a first idea of its background
		ContextProvider cpinfo = new ContextProvider("Walker"
				+ "TestMassContextProvider");
			cpinfo.setType(ContextProviderType.gauge);
			cpinfo.setProvidedEvents(new ContextEventPattern[] { new ContextEventPattern() });
		cp = new DefaultContextPublisher(context, cpinfo);  //EEESTO HAY QUE CAMBIARLO!!
		
	}
	
	
public void changePosEvent(){ //esto simula el gps
	AssistedPerson ap = new AssistedPerson(HwoNamespace.NAMESPACE + "HwoAP");
	//myloc = new Locator();
	Point punto = null;
	punto = userpath.get(contador); //consigo una posicion nueva
	log.info("Enviando evento de cambio de localizacion");
    String message = String.format("Nueva Localizacion \n Longitude: %1$s \n Latitude: %2$s",punto.getX(),punto.getY());
    log.debug(message);
  //  myloc.setCoordinates(new Coordinates(punto.getlongitude(),punto.getlatitude()));
    ap.setLocation(new Point(punto.getX(),punto.getY(),CoordinateSystem.WGS84));
    cp.publish(new ContextEvent(ap,ap.PROP_PHYSICAL_LOCATION));
    
   //	cp.publish(new ContextEvent(myloc,Locator.PROP_COORDINATES));
   	
   	
   	log.info("Enviado evento de cambio de localizacion");
   	if (contador<2) contador++;
	
	
}
	
public void changePos(){ //unused
	Point punto = null;
	punto = userpath.get(contador);
	dataService.setLatitude(punto.getX());
	dataService.setLongitude(punto.getY());
	log.info("New point:" + punto.getX()+punto.getY());
	dataService.setHistoryEntry(new Date().getTime());
	if (contador<2) contador++;
		
}

}

