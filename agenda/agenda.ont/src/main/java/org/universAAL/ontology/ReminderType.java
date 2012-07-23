package org.universAAL.ontology;

import org.universAAL.middleware.owl.ManagedIndividual;

/**
 * @author kagnantis
 * @author eandgrg
 * 
 */
public class ReminderType extends ManagedIndividual {
    public static final String MY_URI = AgendaOntology.NAMESPACE+"ReminderType";

    public static final int NO_REMINDER = 0;
    public static final int BLINKING_LIGHT = 1;
    public static final int SOUND = 2;
    public static final int VISUAL_MESSAGE = 3;
    public static final int VOICE_MESSAGE = 4;
    protected static final int DEFAULT_REMINDER_TYPE = VISUAL_MESSAGE;

    private static final String[] names = {
	    Messages.getString("ReminderType.No_reminder"), Messages.getString("ReminderType.Blinking_light"), Messages.getString("ReminderType.Sound"), Messages.getString("ReminderType.Visual_message"), Messages.getString("ReminderType.Voice_message") //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
    };

    public static final ReminderType noReminder = new ReminderType(NO_REMINDER);
    public static final ReminderType blinkingLight = new ReminderType(
	    BLINKING_LIGHT);
    public static final ReminderType sound = new ReminderType(SOUND);
    public static final ReminderType visualMessage = new ReminderType(
	    VISUAL_MESSAGE);
    public static final ReminderType voiceMessage = new ReminderType(
	    VOICE_MESSAGE);

    /**
     * Returns the list of all class members guaranteeing that no other members
     * will be created after a call to this method.
     */
    public static ManagedIndividual[] getEnumerationMembers() {
	return new ManagedIndividual[] { noReminder, blinkingLight, sound,
		visualMessage, voiceMessage };
    }

    /**
     * Returns the rating with the given URI.
     */
    public static ManagedIndividual getIndividualByURI(String instanceURI) {
	return (instanceURI != null && instanceURI
		.startsWith(AgendaOntology.NAMESPACE)) ? valueOf(instanceURI
		.substring(AgendaOntology.NAMESPACE.length())) : null;
    }

    public static final ReminderType valueOf(String name) {
	if (name == null)
	    return noReminder;

	if (name.startsWith(AgendaOntology.NAMESPACE))
	    name = name.substring(AgendaOntology.NAMESPACE.length());

	for (int i = NO_REMINDER; i <= VOICE_MESSAGE; ++i)
	    if (names[i].equals(name))
		return getReminderTypeByOrder(i);

	return noReminder;
    }

    public static ReminderType getReminderTypeByOrder(int order) {
	switch (order) {
	case BLINKING_LIGHT:
	    return blinkingLight;
	case SOUND:
	    return sound;
	case VISUAL_MESSAGE:
	    return visualMessage;
	case VOICE_MESSAGE:
	    return voiceMessage;
	default:
	    return noReminder;
	}
    }

    public static String getRDFSComment() {
	return "The type of reminders."; //$NON-NLS-1$
    }

    public static String getRDFSLabel() {
	return "Reminder"; //$NON-NLS-1$
    }

    public int getPropSerializationType(String propURI) {
	return PROP_SERIALIZATION_OPTIONAL;
    }

    public boolean isWellFormed() {
	return true;
    }

    private int order;

    private ReminderType(int order) {
	super(AgendaOntology.NAMESPACE + names[order]);
	this.order = order;
    }

    public String name() {
	return names[this.order];
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.universAAL.middleware.rdf.Resource#toString()
     */
    public String toString() {
	return name().replace('_', ' ');
    }

    public int ord() {
	return this.order;
    }

    public void setProperty(String propURI, Object o) {
	// do nothing
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.universAAL.middleware.owl.ManagedIndividual#getClassURI()
     */
    public String getClassURI() {
	return MY_URI;
    }

    // test worked before new dataRep (before 1.1.0 release)
    // public static void main(String[] str) {
    // ManagedIndividual rt = new ReminderType(1);
    // ManagedIndividual[] all = ManagedIndividual.getEnumerationMembers();
    // for (int i = 0; i < all.length; ++i) {
    // System.out.println(all[i].getURI());
    // }
    // }
}
