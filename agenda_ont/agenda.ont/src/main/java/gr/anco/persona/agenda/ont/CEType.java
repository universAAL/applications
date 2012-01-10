package gr.anco.persona.agenda.ont;

import org.persona.ontology.ManagedIndividual;

/**
 * @author kagnantis
 *
 */
public class CEType extends ManagedIndividual {
	public static final String MY_URI;
	
	static {
		MY_URI = Calendar.CALENDAR_NAMESPACE + "CEType";
		register(CEType.class);
	}
	
	public static final int NEW_EVENT = 0;
	public static final int UPDATED_EVENT = 1;
	public static final int DELETED_EVENT = 2;
	public static final int REMINDER = 3;
	public static final int EVENT_STARTED = 4;
	public static final int EVENT_ENDED = 5;
	public static final int EVENT_PLANNED = 6;
	
	private static final String[] names = {
		"new", "updated", "deleted", "reminder", "started", "ended", "planned"
	};
	
	public static final CEType newEvent = new CEType(NEW_EVENT);
	public static final CEType updatedEvent = new CEType(UPDATED_EVENT);
	public static final CEType deletedEvent = new CEType(DELETED_EVENT);
	public static final CEType reminder = new CEType(REMINDER);
	public static final CEType startEvent = new CEType(EVENT_STARTED);
	public static final CEType endEvent = new CEType(EVENT_ENDED);
	public static final CEType plannedEvent = new CEType(EVENT_PLANNED);
	
	/**
	 * Returns the list of all class members guaranteeing that no other members
	 * will be created after a call to this method. 
	 */
	public static ManagedIndividual[] getEnumerationMembers() {
		return new ManagedIndividual[] { newEvent, updatedEvent, deletedEvent, reminder, startEvent, endEvent, plannedEvent };
	}
	
	/**
	 * Returns the rating with the given URI.  
	 */
	public static ManagedIndividual getIndividualByURI(String instanceURI) {
		return (instanceURI != null && instanceURI.startsWith(Calendar.CALENDAR_NAMESPACE)) ?
				valueOf(instanceURI.substring(Calendar.CALENDAR_NAMESPACE.length())) : null;
	}
	
	public static final CEType valueOf(String name) {
		if (name == null) return null;
		
		if (name.startsWith(Calendar.CALENDAR_NAMESPACE))
			name = name.substring(Calendar.CALENDAR_NAMESPACE.length());
		
		for (int i = NEW_EVENT; i <= EVENT_PLANNED; ++i)
			if (names[i].equals(name))
				return getReminderTypeByOrder(i);
		
		return null;
	}
	
	public static CEType getReminderTypeByOrder(int order) {
		switch (order) {
		case NEW_EVENT:
			return newEvent;
		case UPDATED_EVENT:
			return updatedEvent;
		case DELETED_EVENT:
			return deletedEvent;
		case REMINDER:
			return reminder;
		case EVENT_STARTED:
			return startEvent;
		case EVENT_ENDED:
			return endEvent;
		case EVENT_PLANNED:
			return plannedEvent;
		default:
			return null;				
		}
	}
	
	public static String getRDFSComment() {
		return "The type of the event when it is beeing published.";
	}
	
	public static String getRDFSLabel() {
		return "Published Type";
	}
	
	public int getPropSerializationType(String propURI) {
		return PROP_SERIALIZATION_OPTIONAL;
	}

	public boolean isWellFormed() {
		return true;
	}
	
	private int order;
	
	private CEType(int order) {
		super(Calendar.CALENDAR_NAMESPACE + names[order]);
		this.order = order;
	}
	
	public String name() {
		return names[this.order];
	}
	
	public int ord() {
		return this.order;
	}
	
	public void setProperty(String propURI, Object o) {
		//do nothing
	}
}
