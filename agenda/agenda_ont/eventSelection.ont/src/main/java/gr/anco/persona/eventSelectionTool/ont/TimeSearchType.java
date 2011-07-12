package gr.anco.persona.eventSelectionTool.ont;

import org.persona.ontology.ManagedIndividual;

public class TimeSearchType extends ManagedIndividual {
	public static final String MY_URI;
		
		static {
			MY_URI = EventSelectionTool.EVENT_SELECTION_TOOL_NAMESPACE + "TimeSearchType";
			register(TimeSearchType.class);
		}
		
		public static final int STARTS_BETWEEN = 0;
		public static final int ENDS_BETWEEN = 1;
		public static final int STARTS_AND_ENDS_BETWEEN = 2;
		public static final int START_BEFORE_AND_ENDS_AFTER = 3;
		public static final int ALL_BEFORE = 4;
		public static final int ALL_AFTER = 5;
		public static final int ALL_CASES = 6;
		
		private static final String[] names = {
			"Starts_between", "Ends_between", "Starts_and_ends_between", "Starts_before_and_ends_after", "allBefore", "allAfter", "allCases"
		};
		
		//when an event's START TIME is BETWEEN START and END TIME of search interval
		public static final TimeSearchType startsBetween = new TimeSearchType(STARTS_BETWEEN);
		//when an event's END TIME is between START and END TIME of search interval
		public static final TimeSearchType endsBetween = new TimeSearchType(ENDS_BETWEEN);
		//when event's START and END TIME is between START and ENT TIME of search interval
		public static final TimeSearchType startsAndEndsBetween = new TimeSearchType(STARTS_AND_ENDS_BETWEEN);
		//when event's START TIME is before START TIME of search interval, and
		//event's END TIME is after END TIME of search interval
		public static final TimeSearchType startsBeforeAndEndsAfter = new TimeSearchType(START_BEFORE_AND_ENDS_AFTER);
		//when event's END TIME is before START TIME of search interval
		public static final TimeSearchType allBefore = new TimeSearchType(ALL_BEFORE);
		//when event's START TIME is after END TIME of search interval
		public static final TimeSearchType allAfter = new TimeSearchType(ALL_AFTER);
		//when one of the previous cases exists
		public static final TimeSearchType allCases = new TimeSearchType(ALL_CASES);
				
		/**
		 * Returns the list of all class members guaranteeing that no other members
		 * will be created after a call to this method. 
		 */
		public static ManagedIndividual[] getEnumerationMembers() {
			return new ManagedIndividual[] { startsBetween, endsBetween, startsAndEndsBetween, startsBeforeAndEndsAfter, allBefore, allAfter, allCases };
		}
		
		/**
		 * Returns the rating with the given URI.  
		 */
		public static ManagedIndividual getIndividualByURI(String instanceURI) {
			return (instanceURI != null && instanceURI.startsWith(EventSelectionTool.EVENT_SELECTION_TOOL_NAMESPACE)) ?
					valueOf(instanceURI.substring(EventSelectionTool.EVENT_SELECTION_TOOL_NAMESPACE.length())) : null;
		}
		
		public static final TimeSearchType valueOf(String name) {
			if (name == null) return null;
			
			if (name.startsWith(EventSelectionTool.EVENT_SELECTION_TOOL_NAMESPACE))
				name = name.substring(EventSelectionTool.EVENT_SELECTION_TOOL_NAMESPACE.length());
			
			for (int i = STARTS_BETWEEN; i <= ALL_CASES; ++i)
				if (names[i].equals(name))
					return getTimeSearchTypeByOrder(i);
			
			return null;
		}
		
		public static TimeSearchType getTimeSearchTypeByOrder(int order) {
			switch (order) {
			case STARTS_BETWEEN:
				return startsBetween;
			case ENDS_BETWEEN:
				return endsBetween;
			case STARTS_AND_ENDS_BETWEEN:
				return startsAndEndsBetween;
			case START_BEFORE_AND_ENDS_AFTER:
				return startsBeforeAndEndsAfter;
			case ALL_BEFORE:
				return allBefore;
			case ALL_AFTER:
				return allAfter;
			case ALL_CASES:
				return allCases;
			default:
				return null;				
			}
		}
		
		public static String getRDFSComment() {
			return "The type of temporal search.";
		}
		
		public static String getRDFSLabel() {
			return "Temporal search type";
		}
		
		public int getPropSerializationType(String propURI) {
			return PROP_SERIALIZATION_OPTIONAL;
		}

		public boolean isWellFormed() {
			return true;
		}
		
		private int order;
		
		private TimeSearchType(int order) {
			super(EventSelectionTool.EVENT_SELECTION_TOOL_NAMESPACE + names[order]);
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
