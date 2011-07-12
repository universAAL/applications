/**
 * 
 */
package gr.anco.persona.agenda.ont.service;

import gr.anco.persona.agenda.ont.Calendar;

import java.util.Hashtable;

import org.persona.ontology.Service;
import org.persona.ontology.expr.Restriction;


/**
 * @author kagnantis
 *
 */
public class CalendarUIService extends Service {
	public static final String MY_URI;
	public static final String PROP_CONTROLS;
	
	private static Hashtable calendarRestrictions = new Hashtable(1);
	static {
		MY_URI = Calendar.CALENDAR_NAMESPACE + "CalendarUIService";
		PROP_CONTROLS = Calendar.CALENDAR_NAMESPACE + "controls";
		register(CalendarUIService.class);
		
		addRestriction(
				Restriction.getAllValuesRestriction(PROP_CONTROLS, Calendar.MY_URI),
				new String[] {PROP_CONTROLS},
				calendarRestrictions);
	}
	
	public static Restriction getClassRestrictionsOnProperty(String propURI) {
		if (propURI == null)
			return null;
		Object r = calendarRestrictions.get(propURI);
		if (r instanceof Restriction)
			return (Restriction) r;
		return Service.getClassRestrictionsOnProperty(propURI);
	}
	
	public static String getRDFSComment() {
		return "The class of services controling the UI of Personal Agenda.";
	}
	
	public static String getRDFSLabel() {
		return "Calendar/Agenda UI service";
	}
	
	public CalendarUIService(String uri) {
		super(uri);
	}

	/* (non-Javadoc)
	 * @see org.persona.ontology.Service#getClassLevelRestrictions()
	 */
	protected Hashtable getClassLevelRestrictions() {
		return calendarRestrictions;
	}

	/* (non-Javadoc)
	 * @see org.persona.ontology.ManagedIndividual#getPropSerializationType(java.lang.String)
	 */
	public int getPropSerializationType(String propURI) {
		return PROP_SERIALIZATION_FULL;
	}

	public boolean isWellFormed() {
		return true;
	}
}
